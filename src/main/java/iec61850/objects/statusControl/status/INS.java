package iec61850.objects.statusControl.status;

import iec61850.objects.samples.Attribute;

public class INS { // integer status
    private Attribute<Integer> stVal = new Attribute<>(0);

    public Attribute<Integer> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Integer> stVal) {
        this.stVal = stVal;
    }

}
