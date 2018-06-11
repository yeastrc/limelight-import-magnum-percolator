Magnum + Percolator to limelight XML converter
============================================

Use this program to convert the results of a Magnum + Percolator analysis to limelight XML suitable for import into the limelight web application.


How To Run
-------------
1. Download the [latest release](https://github.com/yeastrc/limelight-import-magnum-percolator/releases).
2. Run the program ``java -jar magnumToLimelightXML.jar`` with no arguments to see the possible parameters.
3. Run the program, e.g., ``java -jar magnum2LimelightXML.jar -c /path/to/magnum.conf -o ./magnum.limelight.xml -m /path/to/magnum.txt -p /path/to/percolator-out.xml -f /path/to/fasta.fa``

In the above example, ``magnum.limelight.xml`` will be created and be suitable for import into limelight.
