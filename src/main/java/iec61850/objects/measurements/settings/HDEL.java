package iec61850.objects.measurements.settings;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.Vector;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class HDEL extends LN {

    private ArrayList<Vector> phsABHar = new ArrayList<>();
    private ArrayList<Vector> phsBCHar = new ArrayList<>();
    private ArrayList<Vector> phsCAHar = new ArrayList<>();

    @Override
    public void process() {

    }
}
