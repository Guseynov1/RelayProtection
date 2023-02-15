package iec61850.objects.statusControl;

import iec61850.nodes.common.LN;
import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BCR extends LN { // reading the binary counter readings

    private Attribute<Integer> actVal = new Attribute<>(0);
    private Attribute<Integer> frVal = new Attribute<>(0);
    private Attribute<Float> pulsQty = new Attribute<>((float) 0.);
    private Attribute<Boolean> frEna = new Attribute<>(false);
    private Attribute<Integer> frPd = new Attribute<>(0);
    private Attribute<Boolean> frRs = new Attribute<>(false);
    private String d;
    private String dU;
    private String cdcNs;
    private String cdcName;
    private String dataNs;


    @Override
    public void process() {

    }

}
