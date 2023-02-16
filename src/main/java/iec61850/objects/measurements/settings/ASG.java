package iec61850.objects.measurements.settings;

import iec61850.objects.samples.AnalogValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ASG { // analog set-point
    private AnalogValue setVal = new AnalogValue(); // when setting the setVal value, it must be greater than min and less than max
    private AnalogValue SetMag = new AnalogValue();

    private AnalogValue SetMagA = new AnalogValue();
    private AnalogValue SetMagB = new AnalogValue();
    private AnalogValue SetMagC = new AnalogValue();

    private AnalogValue minVal = new AnalogValue();
    private AnalogValue maxVal = new AnalogValue();
    private AnalogValue stepSize = new AnalogValue();
}
