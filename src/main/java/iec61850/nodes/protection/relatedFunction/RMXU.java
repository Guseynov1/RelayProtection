package iec61850.nodes.protection.relatedFunction;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.MMXU;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.WYE;
import iec61850.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class RMXU extends LN {

    private WYE ALoc = new WYE();
    private SAV AmpLocPhsA = new SAV();
    private SAV AmpLocPhsB = new SAV();
    private SAV AmpLocPhsC = new SAV();
    private SAV AmpLocRes = new SAV();

    private WYE DifAClc = new WYE(); // диф ток
    private WYE RstA = new WYE();
    private List<MMXU> inputs = new ArrayList<>();
    private Vector RstCur = new Vector(); // промежуточные значения тормозного тока


    @Override
    public void process() {
        /* Тормозной ток */
        float difAx = 0, difAy = 0, difBx = 0, difBy = 0, difCx = 0, difCy = 0;
        RstCur.getMag().getF().setValue(0.F);
        RstCur.getAng().getF().setValue(0.F);

        for (MMXU mmxu : inputs) {

            difAx += mmxu.getA().getPhsA().getCVal().getOrtX().getF().getValue();
            difAy += mmxu.getA().getPhsA().getCVal().getOrtY().getF().getValue();

            difBx += mmxu.getA().getPhsB().getCVal().getOrtX().getF().getValue();
            difBy += mmxu.getA().getPhsB().getCVal().getOrtY().getF().getValue();

            difCx += mmxu.getA().getPhsC().getCVal().getOrtX().getF().getValue();
            difCy += mmxu.getA().getPhsC().getCVal().getOrtY().getF().getValue();

            // ищем максимальное значение тока по всем присоединениям и по всем фазам
            if (mmxu.getA().getPhsA().getCVal().getMag().getF().getValue() > RstCur.getMag().getF().getValue()) {
                RstCur.getMag().getF().setValue(mmxu.getA().getPhsA().getCVal().getMag().getF().getValue());
            }
            if (mmxu.getA().getPhsB().getCVal().getMag().getF().getValue() > RstCur.getMag().getF().getValue()) {
                RstCur.getMag().getF().setValue(mmxu.getA().getPhsB().getCVal().getMag().getF().getValue());
            }
            if (mmxu.getA().getPhsC().getCVal().getMag().getF().getValue() > RstCur.getMag().getF().getValue()) {
                RstCur.getMag().getF().setValue(mmxu.getA().getPhsC().getCVal().getMag().getF().getValue());
            }

        }

        // диф ток
        DifAClc.getPhsA().getCVal().setValue0(difAx, difAy);
        DifAClc.getPhsB().getCVal().setValue0(difBx, difBy);
        DifAClc.getPhsC().getCVal().setValue0(difCx, difCy);

        // тормозной ток
        RstA.getPhsA().getCVal().getMag().getF().setValue(RstCur.getMag().getF().getValue());
        RstA.getPhsB().getCVal().getMag().getF().setValue(RstCur.getMag().getF().getValue());
        RstA.getPhsC().getCVal().getMag().getF().setValue(RstCur.getMag().getF().getValue());

        RstA.getPhsA().getCVal().getAng().getF().setValue(0.F);
        RstA.getPhsB().getCVal().getAng().getF().setValue(0.F);
        RstA.getPhsC().getCVal().getAng().getF().setValue(0.F);


//        RstA.getPhsA().getCVal().setValue0(RstCur.getOrtX().getF().getValue(), RstCur.getOrtY().getF().getValue());
//        RstA.getPhsB().getCVal().setValue0(RstCur.getOrtX().getF().getValue(), RstCur.getOrtY().getF().getValue());
//        RstA.getPhsC().getCVal().setValue0(RstCur.getOrtX().getF().getValue(), RstCur.getOrtY().getF().getValue());

    }


    }



