package iec61850.objects.measurements;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CMV {

    private Vector cVal = new Vector();
    private Vector instCVal = new Vector();

}
