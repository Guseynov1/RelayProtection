package iec61850.nodes.measurements;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.DEL;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.WYE;
import iec61850.nodes.measurements.control.sequence.SEQ;
import iec61850.objects.samples.MV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MSQI extends LN {

    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();
    private SEQ SeqS = new SEQ();
    private SEQ DQ0Seq = new SEQ(); // последовательность по прямой, поперечной и нулевой осям

    private WYE A = new WYE();
    private WYE PhV = new WYE();
    private WYE ImbA = new WYE(); // Ток небаланса
    private WYE ImbV = new WYE(); // Напряжение небаланса

    private MV ImbNgA = new MV(); // Ток небаланса ОП
    private MV ImbNgV = new MV(); // Напряжение небаланса ОП
    private MV ImbZroA = new MV(); // Ток небаланса НП
    private MV ImbZroV = new MV(); // Напряжение небаланса НП
    private MV MaxImbA = new MV(); // Максимальный ток небаланса
    private MV MaxImbPPV = new MV(); // Междуфазное напряжение небаланса
    private MV MaxImbV = new MV(); // Максимальное напряжение небаланса

    private DEL ImbPPV = new DEL(); // Междуфазное напряжение небаланса

    // вспомогательные вектора для расчетов
    // для вычисления ПП и ОП необходимо делать поворот вектора B и C
    private Vector shiftB = new Vector();
    private Vector shiftC = new Vector();




    @Override
    public void process() {
        calcSEQ(A, SeqA);
        calcSEQ(PhV, SeqV);
        calcS(SeqA, SeqV, SeqS);

    }
    private void calcSEQ(WYE input, SEQ output){

        shiftB.setValueD(input.getPhsB().getCVal().getMag().getF().getValue(), input.getPhsB().getCVal().getAng().getF().getValue() + 120);
        shiftC.setValueD(input.getPhsC().getCVal().getMag().getF().getValue(), input.getPhsC().getCVal().getAng().getF().getValue() - 120);
        // расчет ПП
        output.getC1().getCVal().setValue0(
                (input.getPhsA().getCVal().getOrtX().getF().getValue() + shiftB.getOrtX().getF().getValue() + shiftC.getOrtX().getF().getValue()) / 3,
                (input.getPhsA().getCVal().getOrtY().getF().getValue() + shiftB.getOrtY().getF().getValue() + shiftC.getOrtY().getF().getValue()) / 3);

        shiftB.setValueD(input.getPhsB().getCVal().getMag().getF().getValue(), input.getPhsB().getCVal().getAng().getF().getValue() - 120);
        shiftC.setValueD(input.getPhsC().getCVal().getMag().getF().getValue(), input.getPhsC().getCVal().getAng().getF().getValue() + 120);
        // расчет ОП
        output.getC2().getCVal().setValue0(
                (input.getPhsA().getCVal().getOrtX().getF().getValue() + shiftB.getOrtX().getF().getValue() + shiftC.getOrtX().getF().getValue()) / 3,
                (input.getPhsA().getCVal().getOrtY().getF().getValue() + shiftB.getOrtY().getF().getValue() + shiftC.getOrtY().getF().getValue()) / 3);
        // расчет НП
        output.getC0().getCVal().setValue0(
                (input.getPhsA().getCVal().getOrtX().getF().getValue() + input.getPhsB().getCVal().getOrtX().getF().getValue() + input.getPhsC().getCVal().getOrtX().getF().getValue()) / 3,
                (input.getPhsA().getCVal().getOrtY().getF().getValue() + input.getPhsB().getCVal().getOrtY().getF().getValue() + input.getPhsC().getCVal().getOrtY().getF().getValue()) / 3);

    }
    private void calcS(SEQ input1, SEQ input2, SEQ output){
        output.getC0().getCVal().setValueD(input1.getC0().getCVal().getMag().getF().getValue() - input2.getC0().getCVal().getMag().getF().getValue(),
                input1.getC0().getCVal().getAng().getF().getValue() - input2.getC0().getCVal().getAng().getF().getValue());

    }

}
