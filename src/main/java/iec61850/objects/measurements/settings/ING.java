package iec61850.objects.measurements.settings;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ING { // integer values
    private Integer SetVal;
    private Attribute<Integer> minVal = new Attribute<>(0);
    private Attribute<Integer> maxVal = new Attribute<>(0);
    private Attribute<Integer> stepSize = new Attribute<>(0);

}
