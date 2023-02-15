package iec61850.objects.measurements.settings;

import iec61850.objects.samples.AnalogValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ASG { // analog setpoint
    private AnalogValue setVal = new AnalogValue();
    private AnalogValue SetMag = new AnalogValue();

    private AnalogValue SetMagA = new AnalogValue();
    private AnalogValue SetMagB = new AnalogValue();
    private AnalogValue SetMagC = new AnalogValue();

    private AnalogValue minVal = new AnalogValue();
    private AnalogValue maxVal = new AnalogValue();
    private AnalogValue stepSize = new AnalogValue();
    // при установке значения setVal, оно должно быть больше min и меньше max

}
