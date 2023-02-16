package iec61850.objects.statusControl.status;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DPS {
    private Attribute<Byte> stVal = new Attribute<>((byte) 0);

}
