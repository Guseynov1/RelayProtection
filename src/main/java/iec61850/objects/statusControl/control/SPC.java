package iec61850.objects.statusControl.control;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SPC { // single point status
    private Attribute<Boolean> stVal = new Attribute<>(false);
    private Attribute<Boolean> ctlVal = new Attribute<>(false);

}
