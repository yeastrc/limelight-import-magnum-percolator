package org.yeastrc.limelight.xml.magnum.objects;

import java.util.Objects;

public class ScanNumber {

    @Override
    public String toString() {
        return "ScanNumber{" +
                "scanNumber=" + scanNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScanNumber that = (ScanNumber) o;
        return scanNumber.equals(that.scanNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scanNumber);
    }

    private Integer scanNumber;

    public ScanNumber(Integer scanNumber) {
        this.scanNumber = scanNumber;
    }

    public Integer getScanNumber() {
        return scanNumber;
    }
}
