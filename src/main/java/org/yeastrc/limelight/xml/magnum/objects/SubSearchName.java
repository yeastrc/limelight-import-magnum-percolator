package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Objects;

public class SubSearchName {

    private String subSearchName;

    @Override
    public String toString() {
        return "SubSearchName{" +
                "subSearchName='" + subSearchName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubSearchName that = (SubSearchName) o;
        return subSearchName.equals(that.subSearchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subSearchName);
    }

    public String getSubSearchName() {
        return subSearchName;
    }

    public SubSearchName(String subSearchName) {
        this.subSearchName = subSearchName;
    }
}
