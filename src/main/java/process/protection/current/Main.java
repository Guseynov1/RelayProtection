package process.protection.current;

import iec61850.nodes.measurements.control.CSWI;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.measurements.control.primary.XCBR;
import iec61850.nodes.protection.PTOC;

public class Main {


    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        CSWI cswi = new CSWI();
        MMXU mmxu = new MMXU();
        NHMI nhmi = new NHMI();
        PTOC ptoc1 = new PTOC();
        PTOC ptoc2 = new PTOC();
        XCBR xcbr = new XCBR();

        /** Начало линии */
        lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\МТЗ\\Опыты\\Начало линии\\PhA80");
        /** Конец линии */
        //lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\МТЗ\\Опыты\\Конец линии\\PhABC20");

        mmxu.setInstIa(lsvc.getSignals().get(0));
        mmxu.setInstIb(lsvc.getSignals().get(1));
        mmxu.setInstIc(lsvc.getSignals().get(2));


        ptoc1.getStrVal().getSetVal().getF().setValue(2500f);
        ptoc2.getStrVal().getSetVal().getF().setValue(1200f);
        ptoc1.getOpDlTmms().setSetVal(100);
        ptoc2.getOpDlTmms().setSetVal(500);

        ptoc1.setA(mmxu.getA());
        ptoc2.setA(mmxu.getA());




        //cswi.setOpOpn(ptoc1.getOp()); // берем узел ptoc и сигнал operate - передаем сигнал отключения
        //cswi.setOpOpn(ptoc2.getOp());
        // зададим значения по умолчанию для выключателя - чтобы при старте программы у нас был управляющий сигнал setValue(true) - по умолчанию выключатель был включен
        //cswi.getPos().getCtlVal().setValue(true);
        // и его состояние равнялось 2 (включение)
        //cswi.getPos().getStVal().setValue((byte) 2);

        cswi.setOpOpn1(ptoc1.getOp());
        cswi.setOpOpn2(ptoc2.getOp());

        xcbr.setPos(cswi.getPos());

        ////////////////
        nhmi.addSignals(new NHMISignal("Ia", mmxu.getInstIa().getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("Ib", mmxu.getInstIb().getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("Ic", mmxu.getInstIc().getInstMag().getF()));
        //////////////// добавим то, что получается из Фурье
        nhmi.addSignals(new NHMISignal("IaF", mmxu.getA().getPhsA().getCVal().getMag().getF()),
                new NHMISignal("StrVal", ptoc1.getStrVal().getSetMag().getF()));
        nhmi.addSignals(new NHMISignal("IbF", mmxu.getA().getPhsB().getCVal().getMag().getF()),
                new NHMISignal("StrVal", ptoc1.getStrVal().getSetMag().getF()));
        nhmi.addSignals(new NHMISignal("IcF", mmxu.getA().getPhsC().getCVal().getMag().getF()),
                new NHMISignal("StrVal", ptoc1.getStrVal().getSetMag().getF()));
        ////////////////
        nhmi.addSignals(new NHMISignal("PTOC1_Str", ptoc1.getStr().getGeneral()));
        nhmi.addSignals(new NHMISignal("PTOC1_Op", ptoc1.getOp().getGeneral()));
        nhmi.addSignals(new NHMISignal("PTOC2_Str", ptoc2.getStr().getGeneral()));
        nhmi.addSignals(new NHMISignal("PTOC2_Op", ptoc2.getOp().getGeneral()));
        ////////////////
        // сигнал состояния выключателя
        nhmi.addSignals(new NHMISignal("Breaker", xcbr.getPos().getCtlVal()));

        while(lsvc.getIterator().hasNext()) {
            lsvc.process();
            nhmi.process();
            mmxu.process();
            ptoc1.process();
            ptoc2.process();
            cswi.process();
            xcbr.process();
            System.out.println(lsvc.getSignals().get(0).getInstMag().getF().getValue());

        }
    }
}
