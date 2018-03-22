Magnum + Percolator to emozi XML converter
============================================

Use this program to convert the results of a Magnum + Percolator analysis to emozi XML suitable for import into the emozi web application.


How To Run
-------------
1. Download the [latest release](https://github.com/yeastrc/emozi-import-magnum-percolator/releases).
2. Run the program ``java -jar magnumToEmoziXML.jar`` with no arguments to see the possible parameters.
3. Run the program, e.g., ``java -jar magnum2EmoziXML.jar -c /path/to/magnum.conf -o ./magnum.emozi.xml -m /path/to/magnum.txt -p /path/to/percolator-out.xml -f /path/to/fasta.fa``

In the above example, ``magnum.emozi.xml`` will be created and be suitable for import into emozi.
