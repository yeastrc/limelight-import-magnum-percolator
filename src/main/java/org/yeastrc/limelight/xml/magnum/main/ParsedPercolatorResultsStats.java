package org.yeastrc.limelight.xml.magnum.main;

import org.yeastrc.limelight.xml.magnum.objects.*;

import java.util.HashSet;
import java.util.Set;

public class ParsedPercolatorResultsStats {

    private int scanCount = 0;
    private int psmCount = 0;
    private int peptideCount = 0;

    public ParsedPercolatorResultsStats(PercolatorResults percolatorResults) {
        Set scanNumberSet = new HashSet<Integer>();

        for(ReportedPeptide reportedPeptide : percolatorResults.getPeptideResults().keySet()) {
            this.peptideCount++;

            PercolatorPeptideResult percolatorPeptideResult = percolatorResults.getPeptideResults().get(reportedPeptide);

            for(SubSearchName subSearchName : percolatorPeptideResult.getPsmMap().keySet()) {
                for(ScanNumber scanNumber : percolatorPeptideResult.getPsmMap().get(subSearchName).keySet()) {
                    scanNumberSet.add(scanNumber.getScanNumber());
                    psmCount += percolatorPeptideResult.getPsmMap().get(subSearchName).get(scanNumber).size();
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
