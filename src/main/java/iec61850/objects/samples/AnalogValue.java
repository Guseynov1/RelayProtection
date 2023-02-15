package iec61850.objects.samples;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnalogValue {

    private Attribute<Float> f = new Attribute<>(0f);

}
