package iec61850.objects.samples;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SAV {
    private AnalogValue instMag = new AnalogValue();
    private AnalogValue delta = new AnalogValue();
}
