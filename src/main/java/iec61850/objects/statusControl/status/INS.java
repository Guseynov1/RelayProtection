package iec61850.objects.statusControl.status;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class INS { // integer status
    private Attribute<Integer> stVal = new Attribute<>(0);

}
