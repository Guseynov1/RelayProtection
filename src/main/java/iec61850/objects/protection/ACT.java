package iec61850.objects.protection;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ACT {

    private Attribute<Boolean> general = new Attribute<>(false);
    private Attribute<Boolean> phsA = new Attribute<>(false);
    private Attribute<Boolean> phsB = new Attribute<>(false);
    private Attribute<Boolean> phsC = new Attribute<>(false);
    private Attribute<Boolean> neut = new Attribute<>(false);

}
