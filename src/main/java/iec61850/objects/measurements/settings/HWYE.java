package iec61850.objects.measurements.settings;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.Vector;
import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter @Setter
public class HWYE extends LN { // harmonic composition of the signal


    private ArrayList<Vector> phsAHar = new ArrayList<>();
    private ArrayList<Vector> phsBHar = new ArrayList<>();
    private ArrayList<Vector> phsCHar = new ArrayList<>();
    private ArrayList<Vector> neutHar = new ArrayList<>();

    private Attribute<Integer> numHar = new Attribute<>(6);


    @Override
    public void process() {

    }

    public HWYE(){
        for(int i=0; i< numHar.getValue(); i++){
            phsAHar.add(new Vector());
            phsBHar.add(new Vector());
            phsCHar.add(new Vector());
            neutHar.add(new Vector());



        }
    }




}
