package iec61850.objects.statusControl.control;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class INC { // integer status
    private Attribute<Integer> stVal = new Attribute<>(0);
    private Attribute<Integer> ctlVal = new Attribute<>(0);

}
