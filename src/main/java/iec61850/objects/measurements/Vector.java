package iec61850.objects.measurements;

import iec61850.objects.samples.AnalogValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Vector {

    private AnalogValue mag = new AnalogValue();
    private AnalogValue ang = new AnalogValue();
    private AnalogValue rad = new AnalogValue();
    private AnalogValue ortX = new AnalogValue();
    private AnalogValue ortY = new AnalogValue();


    public void setValueD(float magValue, float angValue){
        mag.getF().setValue(magValue);
        ang.getF().setValue(angValue);
        rad.getF().setValue((float) Math.toRadians(ang.getF().getValue()));
        ortX.getF().setValue((float) (magValue * Math.cos(rad.getF().getValue())));
        ortY.getF().setValue((float) (magValue * Math.sin(rad.getF().getValue())));
    }

    public void setValue0(float x, float y){
        ortX.getF().setValue(x);
        ortY.getF().setValue(y);
        mag.getF().setValue((float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        rad.getF().setValue((float) Math.atan2(y, x));
        ang.getF().setValue((float) Math.toDegrees(rad.getF().getValue()));
    }

    public void setZe(Vector current, Vector voltage){
        ang.getF().setValue(voltage.getAng().getF().getValue() - current.getAng().getF().getValue());
        mag.getF().setValue(voltage.getMag().getF().getValue() / current.getMag().getF().getValue());
    }


}
