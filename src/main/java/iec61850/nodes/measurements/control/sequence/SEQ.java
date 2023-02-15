package iec61850.nodes.measurements.control.sequence;

import iec61850.objects.measurements.CMV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SEQ { // для написания симметричных составляющих

    // симметричные составляющие, представленные в виде векторов
    private CMV c1 = new CMV();
    private CMV c2 = new CMV();
    private CMV c0 = new CMV();

}
