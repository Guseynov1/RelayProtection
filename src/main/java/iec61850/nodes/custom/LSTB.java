package iec61850.nodes.custom;

import iec61850.nodes.common.LN;
import iec61850.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class LSTB extends LN {

    private List<SAV> signals = new ArrayList<>();
    private float t;
    float f = 50;
    private SAV instIa = new SAV(), instIb = new SAV(), instIc = new SAV();
    private SAV instUa = new SAV(), instUb = new SAV(), instUc = new SAV();


    @Override
    public void process() {

        SAV sav = new SAV();
        SAV sav1 = new SAV();

            if(t < 2000){
                sav.getInstMag().getF().setValue((float) (100 * Math.sin(2 * Math.PI * f * (0.02 * t) / 80)));
                sav1.getInstMag().getF().setValue((float) (220 * Math.sin(2 * Math.PI * f * (0.02 * t) / 80)));
                t++;
            }
            if(t >= 2000 & t < 6000){
                sav.getInstMag().getF().setValue((float) (1000 * Math.sin(2 * Math.PI * f * (0.02 * t) / 80)));
                sav1.getInstMag().getF().setValue((float) (110 * Math.sin(2 * Math.PI * f * (0.02 * t) / 80)));
                t++;
            }
            if(t >= 6000 & t < 8000){
                sav.getInstMag().getF().setValue((float) (120 * Math.sin(2 * Math.PI * f * (0.02 * t) / 80)));
                sav1.getInstMag().getF().setValue((float) (220 * Math.sin(2 * Math.PI * f * (0.02 * t) / 80)));
                t++;
            }
            instIa.getInstMag().getF().setValue(sav.getInstMag().getF().getValue());
            instIb.getInstMag().getF().setValue(sav.getInstMag().getF().getValue());
            instIc.getInstMag().getF().setValue(sav.getInstMag().getF().getValue());

            instUa.getInstMag().getF().setValue(sav1.getInstMag().getF().getValue());
            instUb.getInstMag().getF().setValue(sav1.getInstMag().getF().getValue());
            instUc.getInstMag().getF().setValue(sav1.getInstMag().getF().getValue());

        }


}



