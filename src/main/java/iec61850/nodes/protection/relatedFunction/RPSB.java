package iec61850.nodes.protection.relatedFunction;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.control.sequence.SEQ;
import iec61850.objects.measurements.DEL;
import iec61850.objects.measurements.WYE;
import iec61850.objects.measurements.settings.ASG;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.measurements.settings.SPG;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.ACT;
import iec61850.objects.statusControl.control.INC;
import iec61850.objects.statusControl.status.SPS;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RPSB extends LN {

    private SPS BlkZn = new SPS();
    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();
    private WYE Z = new WYE();
    private DEL PPZ = new DEL();

    private ACD Str = new ACD(); // пуск (колебания мощности обнаружены)
    private ACT Op = new ACT(); // срабатывание (отключение по асинхронному режиму)

    private INC OpCntRs = new INC(); // счетчик числа переключений со сбросом

    private SPG ZeroEna = new SPG(); // ноль разрешен
    private SPG NgEna = new SPG(); // контроль тока ОП разрешен
    private SPG MaxEna = new SPG(); // контроль максимального тока разрешен
    private ASG SwgVal = new ASG(); // изменение колебаний мощности - Delta
    private ASG SwgRis = new ASG(); // изменение колебаний мощности - Delta R
    private ASG SwgReact = new ASG(); // изменение колебаний мощности - Delta X
    private ING SwgTmms = new ING(); // продолжительность колебаний мощности
    private ING UnBlkTmms = new ING(); // время разблокировки
    private ING MaxNumSlp = new ING(); // максимальное число фаз проскальзывания до отключения (Op, отключение по асинхронному режиму)
    private ING EvTmms = new ING(); // время выполнения оценки (временной интервал, отключение по асинхронному режиму)

    private WYE A = new WYE();
    private WYE PhV = new WYE();
    private static final double count = 0.25;
    private double countA = 0, countB = 0, countC = 0, count0 = 0;

    // координаты центра
    private double x0, y0, r, r1;

    private ACD str = new ACD(); // пуск - колебания мощности обнаружены

    public void process() {
        // блокировка по ОП
        if (Math.abs(SeqA.getC2().getCVal().getMag().getF().getValue()) < 50.) {
            str.getGeneral().setValue(true);
        } else {
            str.getGeneral().setValue(false);
        }

        double Za = Math.pow(PPZ.getPhsA().getCVal().getOrtX().getF().getValue() - x0, 2) +
                Math.pow(PPZ.getPhsA().getCVal().getOrtY().getF().getValue() - y0, 2);
        double Zb = Math.pow(PPZ.getPhsB().getCVal().getOrtX().getF().getValue() - x0, 2) +
                Math.pow(PPZ.getPhsB().getCVal().getOrtY().getF().getValue() - y0, 2);
        double Zc = Math.pow(PPZ.getPhsC().getCVal().getOrtX().getF().getValue() - x0, 2) +
                Math.pow(PPZ.getPhsC().getCVal().getOrtY().getF().getValue() - y0, 2);

        // если в зоне - начинается отсчет
        if ((Math.pow(r + r1, 2) > Za) && (Za > Math.pow(r, 2))) {
            countA += count;
        }
        if ((Math.pow(r + r1, 2) > Zb) && (Zb > Math.pow(r, 2))) {
            countB += count;
        }
        if ((Math.pow(r + r1, 2) > Zc) && (Zc > Math.pow(r, 2))) {
            countC += count;
        }

        // если счетчик достиг значения по уставке
        if ((countA >= EvTmms.getSetVal())) {
            Str.getPhsA().setValue(true);
        }
        if ((countB >= EvTmms.getSetVal())) {
            Str.getPhsB().setValue(true);
        }
        if ((countC >= EvTmms.getSetVal())) {
            Str.getPhsC().setValue(true);
        }

        if (Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue()) {
            Str.getGeneral().setValue(true);
            count0 += count;
        }

        // вывод блокировки по каунтеру либо при возникновении несимметрии
        if ((count0 >= UnBlkTmms.getSetVal()) || str.getGeneral().getValue().equals(false)) {
            Str.getPhsA().setValue(false);
            Str.getPhsB().setValue(false);
            Str.getPhsC().setValue(false);
            Str.getGeneral().setValue(false);
            countA = 0;
            countB = 0;
            countC = 0;
            count0 = 0;
        }
    }

}



