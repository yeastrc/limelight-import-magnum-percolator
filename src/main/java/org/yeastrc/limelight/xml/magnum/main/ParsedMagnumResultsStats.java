package org.yeastrc.limelight.xml.magnum.main;

import org.yeastrc.limelight.xml.magnum.objects.MagnumResults;
import org.yeastrc.limelight.xml.magnum.objects.ReportedPeptide;
import org.yeastrc.limelight.xml.magnum.objects.ScanNumber;
import org.yeastrc.limelight.xml.magnum.objects.SubSearchName;

import java.util.HashSet;
import java.util.Set;

public class ParsedMagnumResultsStats {

    private int scanCount = 0;
    private int psmCount = 0;
    private int peptideCount = 0;

    public ParsedMagnumResultsStats(MagnumResults results) {
        Set scanNumberSet = new HashSet<Integer>();

        for(ReportedPeptide reportedPeptide : results.getMagnumResultMap().keySet()) {
            this.peptideCount++;

            for(SubSearchName subSearchName : results.getMagnumResultMap().get(reportedPeptide).keySet()) {
                for(ScanNumber scanNumber : results.getMagnumResultMap().get(reportedPeptide).get(subSearchName).keySet()) {
                    scanNumberSet.add(scanNumber.getScanNumber());

                    this.psmCount += results.getMagnumResultMap().get(reportedPeptide).get(subSearchName).get(scanNumber).size();
                }
            }
        }

        this.scanCount = scanNumberSet.size();
    }

    public int getScanCount() {
        return scanCount;
    }

    public int getPsmCount() {
        return psmCount;
    }

    public int getPeptideCount() {
        return peptideCount;
    }
}
