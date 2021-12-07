package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Objects;

public class PercolatorPsmId {

    private String psmId;

    @Override
    public String toString() {
        return "PercolatorPsmId{" +
                "psmId='" + psmId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercolatorPsmId that = (PercolatorPsmId) o;
        return psmId.equals(that.psmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(psmId);
    }

    public String getPsmId() {
        return psmId;
    }

    public PercolatorPsmId(String psmId) {
        this.psmId = psmId;
    }
}
