package iec61850.nodes.gui.other.info;

import iec61850.nodes.common.LN;
import iec61850.nodes.common.Point;
import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter @Setter
public class CSD extends LN { // describes the braking performance

    private ArrayList<Point> crvPts = new ArrayList<>();
    private Attribute<Integer> numPts = new Attribute<>(0); // number of points in the curve


    @Override
    public void process() {

    }

}
