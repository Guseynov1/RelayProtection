package iec61850.objects.statusControl.status;

import iec61850.objects.samples.Attribute;

public class DPS {
    private Attribute<Byte> stVal = new Attribute<>((byte) 0);

    public Attribute<Byte> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Byte> stVal) {
        this.stVal = stVal;
    }

}
