package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Objects;

public class ReportedPeptide {

    private String reportedPeptideString;

    @Override
    public String toString() {
        return "ReportedPeptide{" +
                "reportedPeptideString='" + reportedPeptideString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportedPeptide that = (ReportedPeptide) o;
        return reportedPeptideString.equals(that.reportedPeptideString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportedPeptideString);
    }

    public ReportedPeptide(String reportedPeptideString) {
        this.reportedPeptideString = reportedPeptideString;
    }

    public String getReportedPeptideString() {
        return reportedPeptideString;
    }
}
