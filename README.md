# Magnum + Percolator to Limelight XML converter

Use this program to convert the results of a Magnum + Percolator analysis to limelight XML suitable for import into the limelight web application.


## How To Run

There are two ways to run this converter:

### Run using Java on your system

1. This method requires that Java version 8 (or higher) be installed on the system where you are running this
converter. You can download Java from https://www.java.com/en/download/.

2. After Java is installed, download the ``magnumToLimelightXML.jar`` associated with the 
[latest release](https://github.com/yeastrc/limelight-import-magnum-percolator/releases)
of this converter. Save this file in a location on your computer that you will remember.

3. The downloaded `magnumToLimelightXML.jar` may be run on the command line by opening
a terminal and typing the following:
`java -jar /path/to/magnumToLimelightXML.jar -V` on Linux or Mac, or `java -jar C:\path\to\magnumToLimelightXML.jar -V`
in Windows. This should print out the version of the converter.

### Run in Docker

This method does not require that you have Java installed on your system. However, it does require that
you have Docker installed. If you are on Linux, Docker is likely already installed. If you are on
Windows or a Mac, you may have to install Docker. To install Docker on Windows, Mac, or Linux; please see our
[tutorial for installing Docker](https://limelight-ms.readthedocs.io/en/latest/tutorials/install-docker.html).

If you install Docker according to our tutorial above, you can run this converter by first opening
a terminal (if you are on Windows and followed our tutorial, this will be a Windows Terminal running
Ubuntu Linux). Also, if you are on Windows, you may need to start the Docker process by typing
`sudo service docker up`.

Then to run this converter, type:

``sudo docker run --rm -it --user $(id -u):$(id -g) -v `pwd`:`pwd` -w `pwd` mriffle/magnum-percolator-to-limelight -V``

This should print out the version number of the converter. Note, you do not need to install the converter
software, this is pulled down automatically by Docker.

### Command Line Arguments

Regardless of how you run the converter, it will take the same command line parameters at the end of
the command line statement typed into the terminal. To see the available command line arguments, run
the converter with no arguments. For example:

- If you have your own Java installed: `java -jar /path/to/magnumToLimelightXML.jar -V`
- If you are using Docker: ``sudo docker run --rm -it --user $(id -u):$(id -g) -v `pwd`:`pwd` -w `pwd` mriffle/magnum-percolator-to-limelight``

An example command for converting real data would be the following. Note, these command assume you
are in the same directory as your data on the command line:

- If you have your own Java installed: `java -jar /path/to/magnumToLimelightXML.jar -c ./treated-Magnum.conf -p ./percout.xml -f ./SearchDatabase.fasta -m ./treated.pep.xml -o treated.limelight.xml`
- If you are using Docker: ``sudo docker run --rm -it --user $(id -u):$(id -g) -v `pwd`:`pwd` -w `pwd` mriffle/magnum-percolator-to-limelight -c ./treated-Magnum.conf -p ./percout.xml -f ./SearchDatabase.fasta -m ./treated.pep.xml -o treated.limelight.xml``

Remember that if you are on Windows, `/path/to/magnumToLimelightXML.jar` may look something like
`C:\path\to\magnumToLimelightXML.jar`.