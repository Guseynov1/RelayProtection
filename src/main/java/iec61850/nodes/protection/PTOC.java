package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.WYE;
import iec61850.objects.measurements.settings.ASG;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.ACT;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PTOC extends LN {

    private ASG StrVal = new ASG(); // уставка по току
    private ING OpDlTmms = new ING(); // уставка по времени
    private ING DirMod = new ING(); // направление защиты

    private double counter = 0; // счетчик для моделирования ВВ

    private boolean trip = false;

    // входные сигналы
    private WYE A = new WYE(); // токи
    private ACT Op = new ACT(); // срабатывание защиты
    private ACD Str = new ACD(); // пуск защиты
    private ACD Dir = new ACD(); // сигнал направления защиты
    //private double countA = 0, countB = 0, countC = 0; // внутренние сигналы для накопления сигналов срабатывания защиты


    @Override
    public void process() {

        boolean phsA;
        boolean phsB;
        boolean phsC;

        phsA = A.getPhsA().getCVal().getMag().getF().getValue() > StrVal.getSetVal().getF().getValue();

        phsB = A.getPhsB().getCVal().getMag().getF().getValue() > StrVal.getSetVal().getF().getValue();

        phsC = A.getPhsC().getCVal().getMag().getF().getValue() > StrVal.getSetVal().getF().getValue();


        boolean general = phsA || phsB || phsC;

        Str.getGeneral().setValue(general);
        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);

        if (general) {
            counter += 0.25;
        }

        Op.getGeneral().setValue(counter > OpDlTmms.getSetVal());
        Op.getPhsA().setValue(counter > OpDlTmms.getSetVal());
        Op.getPhsB().setValue(counter > OpDlTmms.getSetVal());
        Op.getPhsC().setValue(counter > OpDlTmms.getSetVal());


    }



        }



