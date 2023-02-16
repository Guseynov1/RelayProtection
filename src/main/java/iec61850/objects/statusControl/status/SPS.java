package iec61850.objects.statusControl.status;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SPS { // single point status
    private Attribute<Boolean> stVal = new Attribute<>(false);
}


