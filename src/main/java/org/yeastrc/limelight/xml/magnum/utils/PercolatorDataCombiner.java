package org.yeastrc.limelight.xml.magnum.utils;

import org.yeastrc.limelight.xml.magnum.objects.*;

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
        for(ReportedPeptide reportedPeptide : percolatorResultsToAdd.getPeptideResults().keySet()) {
            PercolatorPeptideResult newPeptideResult = null;

            if(newPercolatorResults.getPeptideResults().containsKey(reportedPeptide)) {

                newPeptideResult = newPercolatorResults.getPeptideResults().get(reportedPeptide);
            } else {

                newPeptideResult = new PercolatorPeptideResult();
                newPeptideResult.setReportedPeptide( reportedPeptide.getReportedPeptideString() );
                newPeptideResult.setPercolatorPeptideStats( null );
                newPeptideResult.setPsmMap(new HashMap<>());

                newPercolatorResults.getPeptideResults().put(reportedPeptide, newPeptideResult);
            }


            PercolatorPeptideResult peptideResultToAdd = percolatorResultsToAdd.getPeptideResults().get(reportedPeptide);
            for(SubSearchName subSearchName : peptideResultToAdd.getPsmMap().keySet()) {

                if (!(newPeptideResult.getPsmMap().containsKey(subSearchName))) {
                    newPeptideResult.getPsmMap().put(subSearchName, new HashMap<>());
                }

                for (ScanNumber scanNumber : peptideResultToAdd.getPsmMap().get(subSearchName).keySet()) {

                    if (!(newPeptideResult.getPsmMap().get(subSearchName).containsKey(scanNumber))) {
                        newPeptideResult.getPsmMap().get(subSearchName).put(scanNumber, new HashSet<>());
                    }

                    newPeptideResult.getPsmMap().get(subSearchName).get(scanNumber).addAll(peptideResultToAdd.getPsmMap().get(subSearchName).get(scanNumber));
                }
            }
        }
    }
}
