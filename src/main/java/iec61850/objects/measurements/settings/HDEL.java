package iec61850.objects.measurements.settings;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.Vector;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class HDEL extends LN {

    private List<Vector> phsABHar = new ArrayList<>();
    private List<Vector> phsBCHar = new ArrayList<>();
    private List<Vector> phsCAHar = new ArrayList<>();

    @Override
    public void process() {

    }
}
