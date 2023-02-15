package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.DEL;
import iec61850.objects.measurements.WYE;
import iec61850.objects.measurements.settings.ASG;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.measurements.settings.SPG;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.ACT;
import iec61850.objects.statusControl.control.INC;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PDIS extends LN {

    // выходные сигналы
    private ACD Str = new ACD();
    private ACT Op = new ACT();
    private ACD BlkStr = new ACD();
    // входные сигналы
    private WYE Z = new WYE();
    // уставки
    private ING OpDlTmms = new ING();
    private ASG DirFMin = new ASG();
    private ASG DirFMax = new ASG();
    private ASG X1 = new ASG();
    private static final double counter = 0.25;
    private double r, x0, y0;
    // угол наклона линии
    private ASG LinAng = new ASG(); // угол сдвига фаз
    // правая и левая вертикальные границы
    private ASG RisPhRch = new ASG(); // область резистивной фазы
    private ASG RisGndRch = new ASG(); // область резистивного заземления
    private ASG K0Fact = new ASG(); // коэффициент остаточной компенсации K0
    private ASG K0FactAng = new ASG(); // угол коэффициента остаточной компенсации K0
    private ING RsDlTmms = new ING(); // Время задержки сброса

    private INC OpCntRs = new INC(); // счетчик числа переключений со сбросом
    private ASG PoRch = new ASG(); // полярная область - диаметр на круговой диаграмме проводимости
    private ASG PhStr = new ASG(); // начальное фазное значение
    private ASG GndStr = new ASG(); // начальное значение НП
    private ING DirMod = new ING(); // режим направленной защиты
    private ASG PctRch = new ASG(); // процент области действия
    private ASG Ofs = new ASG(); // смещение
    private ASG PctOfs = new ASG(); // процент смещения
    private ASG RisLod = new ASG(); // область сопротивления для зоны нагрузки
    private ASG AngLod = new ASG(); // угол для зоны нагрузки
    private SPG TmDlMod = new SPG(); // режим задержки времени срабатывания
    private ING PhDlTmms = new ING(); // время задержки срабатывания при многофазных КЗ
    private SPG PhDlMod = new SPG(); // многофазный режим задержки времени срабатывания
    private SPG GndDlMod = new SPG(); // время задержки срабатывания при однофазном режиме замыкания на землю
    private ING GndDlTmms = new ING(); // время задержки срабатывания при ОЗЗ
    private double count = 0; // счетчик ВВ
    private DEL PPZ = new DEL();

    @Override
    public void process() {


        Str.getPhsA().setValue(Math.pow(PPZ.getPhsAB().getCVal().getOrtX().getF().getValue() - x0, 2) +
                Math.pow(PPZ.getPhsAB().getCVal().getOrtY().getF().getValue() - y0, 2) < Math.pow(r, 2));
        Str.getPhsB().setValue(Math.pow(PPZ.getPhsBC().getCVal().getOrtX().getF().getValue() - x0, 2) +
                Math.pow(PPZ.getPhsBC().getCVal().getOrtY().getF().getValue() - y0, 2) < Math.pow(r, 2));
        Str.getPhsC().setValue(Math.pow(PPZ.getPhsCA().getCVal().getOrtX().getF().getValue() - x0, 2) +
                Math.pow(PPZ.getPhsCA().getCVal().getOrtY().getF().getValue() - y0, 2) < Math.pow(r, 2));

        Str.getGeneral().setValue
                (Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue());

        if (Str.getGeneral().getValue()) {
            count += counter;
        } else {
            count = 0;
        }
        if (BlkStr.getGeneral().getValue()) {
            count = 0;
        }
        Op.getGeneral().setValue(count > OpDlTmms.getSetVal());
    }


        // условие срабатывания реле сопротивления с круговой характеристикой
//        boolean ABPhs = Math.pow(Z.getPhsAB().getCVal().getOrtX().getF().getValue() - R0.getSetVal().getF().getValue(), 2) +
//                Math.pow(Z.getPhsAB().getCVal().getOrtY().getF().getValue() - X0.getSetVal().getF().getValue(), 2) <= Math.pow((Zu.getSetVal().getF().getValue()), 2);
//        boolean BCPhs = Math.pow(Z.getPhsAB().getCVal().getOrtX().getF().getValue() - R0.getSetVal().getF().getValue(), 2) +
//                Math.pow(Z.getPhsAB().getCVal().getOrtY().getF().getValue() - X0.getSetVal().getF().getValue(), 2) <= Math.pow((Zu.getSetVal().getF().getValue()), 2);
//        boolean CAPhs = Math.pow(Z.getPhsAB().getCVal().getOrtX().getF().getValue() - R0.getSetVal().getF().getValue(), 2) +
//                Math.pow(Z.getPhsAB().getCVal().getOrtY().getF().getValue() - X0.getSetVal().getF().getValue(), 2) <= Math.pow((Zu.getSetVal().getF().getValue()), 2);
//
//        if(ABPhs){countAB += 1;} else {countAB = 0;}
//        if(BCPhs){countBC += 1;} else {countBC = 0;}
//        if(CAPhs){countCA += 1;} else {countCA = 0;}
//        if(swings){countAB = 0; countBC = 0; countCA = 0;}
//
        // при наличии блокировки каунтеры = 0
       // if(blk.getStVal().getValue()) countA = countB = countC = 0; // если имеются качания ВВ не производить
        // сигнал срабатывания защиты (фиксация ВВ)
//        Op.getPhsA().setValue(countAB > OpDlTmms.getSetVal());
//        Op.getPhsB().setValue(countBC > OpDlTmms.getSetVal());
//        Op.getPhsC().setValue(countCA > OpDlTmms.getSetVal());
//        Op.getGeneral().setValue(countAB > OpDlTmms.getSetVal() || countBC > OpDlTmms.getSetVal() || countCA > OpDlTmms.getSetVal());

    }
//    private boolean draw(Vector z){
//        for(int i=0; i<=360; i++){
//            x1[i]=getXcentr1()+(getZ1()/2*Math.cos(i));
//            y1[i]=getYcentr1()+(getZ1()/2*Math.sin(i));
//        }
//
//        for(int i=0; i<x1.length; i++){
//
//        }
//    }


