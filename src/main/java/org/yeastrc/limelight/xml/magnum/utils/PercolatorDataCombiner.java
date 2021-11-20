package org.yeastrc.limelight.xml.magnum.utils;

import org.yeastrc.limelight.xml.magnum.objects.PercolatorPeptideResult;
import org.yeastrc.limelight.xml.magnum.objects.PercolatorResults;

import java.util.HashMap;
import java.util.HashSet;

public class PercolatorDataCombiner {

    public static PercolatorResults combineData(PercolatorResults openModsPercolatorResults, PercolatorResults standardPercolatorResults) {

        PercolatorResults percolatorResults = new PercolatorResults();
        percolatorResults.setPercolatorVersion(openModsPercolatorResults.getPercolatorVersion());
        percolatorResults.setPeptideResults(new HashMap<>());

        addPercolatorResultstoNewResults(openModsPercolatorResults, percolatorResults);
        addPercolatorResultstoNewResults(standardPercolatorResults, percolatorResults);

        return percolatorResults;
    }

    private static void addPercolatorResultstoNewResults(PercolatorResults percolatorResultsToAdd, PercolatorResults newPercolatorResults) {
        for(String reportedPeptide : percolatorResultsToAdd.getPeptideResults().keySet()) {
            PercolatorPeptideResult newPeptideResult = null;

            if(newPercolatorResults.getPeptideResults().containsKey(reportedPeptide)) {

                newPeptideResult = newPercolatorResults.getPeptideResults().get(reportedPeptide);
            } else {

                newPeptideResult = new PercolatorPeptideResult();
                newPeptideResult.setReportedPeptide( reportedPeptide );
                newPeptideResult.setPercolatorPeptideStats( null );
                newPeptideResult.setPsmsIndexedByScanNumber(new HashMap<>());

                newPercolatorResults.getPeptideResults().put(reportedPeptide, newPeptideResult);
            }


            PercolatorPeptideResult peptideResultToAdd = percolatorResultsToAdd.getPeptideResults().get(reportedPeptide);
            for(int scanNumber : peptideResultToAdd.getPsmsIndexedByScanNumber().keySet()) {

                if(!(newPeptideResult.getPsmsIndexedByScanNumber().containsKey(scanNumber))) {
                    newPeptideResult.getPsmsIndexedByScanNumber().put(scanNumber, new HashSet<>());
                }

                newPeptideResult.getPsmsIndexedByScanNumber().get(scanNumber).addAll(peptideResultToAdd.getPsmsIndexedByScanNumber().get(scanNumber));
            }
        }
    }
}
