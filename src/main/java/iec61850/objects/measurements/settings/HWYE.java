package iec61850.objects.measurements.settings;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.Vector;
import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter @Setter
public class HWYE extends LN { // harmonic composition of the signal


    private List<Vector> phsAHar = new ArrayList<>();
    private List<Vector> phsBHar = new ArrayList<>();
    private List<Vector> phsCHar = new ArrayList<>();
    private List<Vector> neutHar = new ArrayList<>();

    private Attribute<Integer> numHar = new Attribute<>(6);


    @Override
    public void process() {

    }

    public HWYE(){
        IntStream.range(0, numHar.getValue()).forEach(i -> {
            phsAHar.add(new Vector());
            phsBHar.add(new Vector());
            phsCHar.add(new Vector());
            neutHar.add(new Vector());
        });
    }
}
