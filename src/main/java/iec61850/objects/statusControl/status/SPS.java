package iec61850.objects.statusControl.status;

import iec61850.objects.samples.Attribute;

public class SPS { // single point status
    private Attribute<Boolean> stVal = new Attribute<>(false);

    public Attribute<Boolean> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Boolean> stVal) {
        this.stVal = stVal;
    }
}


