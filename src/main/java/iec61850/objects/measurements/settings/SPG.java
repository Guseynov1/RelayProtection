package iec61850.objects.measurements.settings;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SPG { // single-element state setting
    private Attribute<Boolean> setVal = new Attribute<>(false);

}
