package process.protection;

import iec61850.nodes.custom.LSTB;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.control.CSWI;
import iec61850.nodes.measurements.control.primary.XCBR;
import iec61850.nodes.protection.PTOC;

public class main {

    public static void main(String[] args) {

        NHMI nhmi = new NHMI();
        LSTB lstb = new LSTB();
        MMXU1 mmxu = new MMXU1();
        PTOC ptoc = new PTOC();
        CSWI cswi = new CSWI();
        XCBR xcbr = new XCBR();


        mmxu.setInstIa1(lstb.getInstIa1());
        mmxu.setInstIb1(lstb.getInstIb1());
        mmxu.setInstIc1(lstb.getInstIc1());

        mmxu.setInstUa1(lstb.getInstUa1());
        mmxu.setInstUb1(lstb.getInstUb1());
        mmxu.setInstUc1(lstb.getInstUc1());

        ptoc.getStrVal().getSetVal().getF().setValue(600f);
        ptoc.getOpDlTmms().setSetVal(100);

        ptoc.setA(mmxu.getA1());

        cswi.setOpOpn1(ptoc.getOp());
        xcbr.setPos(cswi.getPos());



        nhmi.addSignals(new NHMISignal("Ia", mmxu.getInstIa1().getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("Ib", mmxu.getInstIb1().getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("Ic", mmxu.getInstIc1().getInstMag().getF()));

        nhmi.addSignals(new NHMISignal("Ua", mmxu.getInstUa1().getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("Ub", mmxu.getInstUb1().getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("Uc", mmxu.getInstUc1().getInstMag().getF()));

//        nhmi.addSignals(new NHMISignal("IaF", mmxu.getA1().getPhsA().getCVal().getMag().getF()),
//                new NHMISignal("StrVal", ptoc.getStrVal().getSetMag().getF()));
//        nhmi.addSignals(new NHMISignal("IbF", mmxu.getA1().getPhsB().getCVal().getMag().getF()),
//                new NHMISignal("StrVal", ptoc.getStrVal().getSetMag().getF()));
//        nhmi.addSignals(new NHMISignal("IcF", mmxu.getA1().getPhsC().getCVal().getMag().getF()),
//                new NHMISignal("StrVal", ptoc.getStrVal().getSetMag().getF()));

        nhmi.addSignals(new NHMISignal("Пуск", ptoc.getStr().getGeneral()));
        nhmi.addSignals(new NHMISignal("Срабатывание", ptoc.getOp().getGeneral()));

        nhmi.addSignals(new NHMISignal("Breaker", xcbr.getPos().getCtlVal()));



        float i = 0;
        while (i < 8000){

            lstb.process();
            nhmi.process();
            mmxu.process();
            ptoc.process();
            cswi.process();
            xcbr.process();

            i++;

        }

    }

}
