package iec61850.nodes.protection.relatedFunction;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.control.sequence.SEQ;
import iec61850.objects.measurements.WYE;
import iec61850.objects.measurements.settings.ASG;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.dir.Direction;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RDIR extends LN {

    private ACD Dir = new ACD(); // Направление
    private WYE W = new WYE();
    private SEQ SeqS = new SEQ();

    private ING PolQty = new ING(); // Поляризационное квантование

    private ASG ChrAng = new ASG(); // Характеристический угол
    private ASG MinFwdAng = new ASG(); // Минимальный угол сдвига фаз в прямом направлении
    private ASG MinRvAng = new ASG(); // Минимальный угол сдвига фаз в обратном направлении
    private ASG MaxFwdAng = new ASG(); // Максимальный угол сдвига фаз в прямом направлении
    private ASG MaxRvAng = new ASG(); // Максимальный угол сдвига фаз в обратном направлении
    private ASG BlkValA = new ASG(); // Минимальный рабочий ток
    private ASG BlkValV = new ASG(); // Минимальное рабочее напряжение
    private ASG MinPPV = new ASG(); // Минимальное междуфазное напряжение


    @Override
    public void process() { // направление фаз по знаку активной мощности
        if (W.getPhsA().getCVal().getMag().getF().getValue() < 0) {
            Dir.getDirPhsA().setValue(Direction.BACKWARD);
        }
        else Dir.getDirPhsA().setValue(Direction.FORWARD);

        if (W.getPhsB().getCVal().getMag().getF().getValue() < 0) {
            Dir.getDirPhsB().setValue(Direction.BACKWARD);
        }
        else Dir.getDirPhsB().setValue(Direction.FORWARD);

        if (W.getPhsC().getCVal().getMag().getF().getValue() < 0) {
            Dir.getDirPhsC().setValue(Direction.BACKWARD);
        }
        else Dir.getDirPhsC().setValue(Direction.FORWARD);



        if (Dir.getDirPhsA().getValue() == Direction.BACKWARD &&
                (Dir.getDirPhsB().getValue() == Direction.FORWARD &&
                        Dir.getDirPhsC().getValue() == Direction.FORWARD)){
            Dir.getDirGeneral().setValue(Direction.FORWARD);
        }
        else Dir.getDirGeneral().setValue(Direction.BACKWARD);
    }
}
