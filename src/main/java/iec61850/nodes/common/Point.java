package iec61850.nodes.common;

import iec61850.objects.samples.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Point extends LN {

    private Attribute<Float> xVal = new Attribute<>((float) 0);
    private Attribute<Float> yVal = new Attribute<>((float) 0);
    private Attribute<Float> Coefficient = new Attribute<>((float) 0);

    public Point(Float x, Float y) {
        xVal.setValue(x);
        yVal.setValue(y);
    }


    public void setValue(float coef, float RstCur){
        xVal.setValue(RstCur);
        yVal.setValue(RstCur * coef);
        Coefficient.setValue(coef);
    }


    @Override
    public void process() {

    }

}
