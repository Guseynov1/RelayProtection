package process.protection.PDIF;

import iec61850.nodes.common.Point;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.NHMIP;
import iec61850.nodes.gui.other.NHMIPoint;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MHAI;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.measurements.control.CSWI;
import iec61850.nodes.measurements.control.primary.XCBR;
import iec61850.nodes.measurements.filter.Fourier;
import iec61850.nodes.protection.PDIF;
import iec61850.nodes.protection.PTRC;
import iec61850.nodes.protection.relatedFunction.RMXU;
import iec61850.objects.statusControl.control.DPC;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        Fourier.size = 20;

        LSVC lsvc = new LSVC();
        NHMI nhmi1 = new NHMI();
        NHMI nhmi2 = new NHMI();
//        NHMI nhmi3 = new NHMI();
//        NHMI nhmi4 = new NHMI();
//        NHMIP nhmip = new NHMIP();
        MMXU mmxu1 = new MMXU();
        MMXU mmxu2 = new MMXU();
        MMXU mmxu3 = new MMXU();
        MMXU mmxu4 = new MMXU();
        MHAI mhai1 = new MHAI(1,5);
        MHAI mhai2 = new MHAI(1,5);
        MHAI mhai3 = new MHAI(1,5);
        MHAI mhai4 = new MHAI(1,5);
        PDIF pdif = new PDIF();
        RMXU rmxu = new RMXU();
        CSWI cswi = new CSWI();
//        CSWI cswi1 = new CSWI();
//        CSWI cswi2 = new CSWI();
//        CSWI cswi3 = new CSWI();
//        CSWI cswi4 = new CSWI();
        XCBR xcbr = new XCBR();
//        XCBR xcbr1 = new XCBR();
//        XCBR xcbr2 = new XCBR();
//        XCBR xcbr3 = new XCBR();
//        XCBR xcbr4 = new XCBR();

//        PTRC ptrc = new PTRC();


        lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\ДФЗ\\Опыты\\DPB\\4 sections\\KzA");
        //lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\ДФЗ\\Опыты\\DPB\\4 sections\\KzBC");
        //lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\ДФЗ\\Опыты\\DPB\\4 sections\\Vkl");

        mmxu1.setInstIa(lsvc.getSignals().get(0));
        mmxu1.setInstIb(lsvc.getSignals().get(1));
        mmxu1.setInstIc(lsvc.getSignals().get(2));

        mmxu2.setInstIa(lsvc.getSignals().get(3));
        mmxu2.setInstIb(lsvc.getSignals().get(4));
        mmxu2.setInstIc(lsvc.getSignals().get(5));

        mmxu3.setInstIa(lsvc.getSignals().get(6));
        mmxu3.setInstIb(lsvc.getSignals().get(7));
        mmxu3.setInstIc(lsvc.getSignals().get(8));

        mmxu4.setInstIa(lsvc.getSignals().get(9));
        mmxu4.setInstIb(lsvc.getSignals().get(10));
        mmxu4.setInstIc(lsvc.getSignals().get(11));


        mhai1.setInstIa(lsvc.getSignals().get(0));
        mhai1.setInstIb(lsvc.getSignals().get(1));
        mhai1.setInstIc(lsvc.getSignals().get(2));

        mhai2.setInstIa(lsvc.getSignals().get(3));
        mhai2.setInstIb(lsvc.getSignals().get(4));
        mhai2.setInstIc(lsvc.getSignals().get(5));

        mhai3.setInstIa(lsvc.getSignals().get(6));
        mhai3.setInstIb(lsvc.getSignals().get(7));
        mhai3.setInstIc(lsvc.getSignals().get(8));



        mhai4.setInstIa(lsvc.getSignals().get(9));
        mhai4.setInstIb(lsvc.getSignals().get(10));
        mhai4.setInstIc(lsvc.getSignals().get(11));



        rmxu.getInputs().add(mmxu1);
        rmxu.getInputs().add(mmxu2);
        rmxu.getInputs().add(mmxu3);
        rmxu.getInputs().add(mmxu4);

        pdif.setDifAClc(rmxu.getDifAClc());
        pdif.setRstA(rmxu.getRstA());

        pdif.getHinputs().add(mhai1.getHA());
        pdif.getHinputs().add(mhai2.getHA());
        pdif.getHinputs().add(mhai3.getHA());
        pdif.getHinputs().add(mhai4.getHA());



//        pdif.setHA1(mhai1.getHA());
//        pdif.setHA2(mhai2.getHA());
//        pdif.setHA3(mhai3.getHA());
//        pdif.setHA4(mhai4.getHA());


        pdif.getMinOpTmms().setSetVal(100);
        pdif.setBlkValue(0.1);
        //pdif.getHBlock().getSetVal().getF().setValue(0.15f);

        pdif.getTmASt().getCrvPts().add(new Point(0F, 2500F));
        pdif.getTmASt().getCrvPts().add(new Point(6000F, 2500F));
        pdif.getTmASt().getCrvPts().add(new Point(27500F, 6000F));
        pdif.setting();

//        pdif.getTmASt().getCrvPts().add(new Point((float) 0, 0.1F)); // T1
//        pdif.getTmASt().getCrvPts().add(new Point((float) 0.1, 0.1F)); // T2
//        pdif.getTmASt().getCrvPts().add(new Point()); // T3
//        pdif.getTmASt().getCrvPts().get(2).setValue(0.25f, 2);
//        pdif.getTmASt().getCrvPts().add(new Point()); // T4
//        pdif.getTmASt().getCrvPts().get(3).setValue(0.6f, 8);
//        pdif.getTmASt().getCrvPts().add(new Point()); // T5
//        pdif.getTmASt().getCrvPts().get(4).setValue(1f, 14);
//        pdif.setting();

//        cswi1.setOpOpn(pdif.getOp());
//        cswi1.getPos().getCtlVal().setValue(true);
//        cswi1.getPos().getStVal().setValue((byte) 2);
//        xcbr1.setPos(cswi1.getPos());
//
//        cswi2.setOpOpn(pdif.getOp());
//        cswi2.getPos().getCtlVal().setValue(true);
//        cswi2.getPos().getStVal().setValue((byte) 2);
//        xcbr2.setPos(cswi2.getPos());
//
//        cswi3.setOpOpn(pdif.getOp());
//        cswi3.getPos().getCtlVal().setValue(true);
//        cswi3.getPos().getStVal().setValue((byte) 2);
//        xcbr3.setPos(cswi3.getPos());
//
//        cswi4.setOpOpn(pdif.getOp());
//        cswi4.getPos().getCtlVal().setValue(true);
//        cswi4.getPos().getStVal().setValue((byte) 2);
//        xcbr4.setPos(cswi4.getPos());

        cswi.setOpOpn(pdif.getOp());
        cswi.getPos().getCtlVal().setValue(true);
        cswi.getPos().setStVal(DPC.State.ON);
//        cswi.setOpOpn1(pdif.getOp());
//        cswi.setOpOpn2(pdif.getOp());
//        cswi.setOpOpn3(pdif.getOp());
//        cswi.setOpOpn4(pdif.getOp());

        //ptrc.getOp().add(pdi)

        xcbr.setPos(cswi.getPos());




        ///////////////////////////////////////////////////////////////////////////////////////////
        nhmi1.addSignals("Currents A", new NHMISignal("1", lsvc.getSignals().get(0).getInstMag().getF()),
                new NHMISignal("2", lsvc.getSignals().get(3).getInstMag().getF()),
                new NHMISignal("3", lsvc.getSignals().get(6).getInstMag().getF()),
                new NHMISignal("4", lsvc.getSignals().get(9).getInstMag().getF()));
        nhmi1.addSignals("Currents B", new NHMISignal("1", lsvc.getSignals().get(1).getInstMag().getF()),
                new NHMISignal("2", lsvc.getSignals().get(4).getInstMag().getF()),
                new NHMISignal("3", lsvc.getSignals().get(7).getInstMag().getF()),
                new NHMISignal("4", lsvc.getSignals().get(10).getInstMag().getF()));
        nhmi1.addSignals("Currents C", new NHMISignal("1", lsvc.getSignals().get(2).getInstMag().getF()),
                new NHMISignal("2", lsvc.getSignals().get(5).getInstMag().getF()),
                new NHMISignal("3", lsvc.getSignals().get(8).getInstMag().getF()),
                new NHMISignal("4", lsvc.getSignals().get(11).getInstMag().getF()));
        ////////////////////////////////////////////////////////////////////////////////////////////
        nhmi2.addSignals(new NHMISignal("DifA", rmxu.getDifAClc().getPhsA().getCVal().getMag().getF()),
                new NHMISignal("B", rmxu.getDifAClc().getPhsB().getCVal().getMag().getF()),
                new NHMISignal("C", rmxu.getDifAClc().getPhsC().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("RstA, A", rmxu.getRstA().getPhsA().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("RstA, B", rmxu.getRstA().getPhsB().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("RstA, C", rmxu.getRstA().getPhsC().getCVal().getMag().getF()));
        ////////////////////////////////////////////////////////////////////////////////////////////
        nhmi2.addSignals(new NHMISignal("ПО ДЗШ", pdif.getStr().getGeneral()));
        //nhmi3.addSignals(new NHMISignal("BlkA", pdif.getOp()));
        nhmi2.addSignals(new NHMISignal("Срабатывание ДЗШ", pdif.getOp().getPhsA()));
        //nhmi3.addSignals(new NHMISignal("ПО ДЗШ_b", pdif.getStr().getGeneral()));
        //nhmi3.addSignals(new NHMISignal("BlkA", pdif.getOp()));
        //nhmi3.addSignals(new NHMISignal("ДЗШ_b", pdif.getOp().getGeneral()));
        //nhmi3.addSignals(new NHMISignal("ПО ДЗШ_c", pdif.getStr().getGeneral()));
        //nhmi3.addSignals(new NHMISignal("BlkA", pdif.getOp()));
        //nhmi3.addSignals(new NHMISignal("ДЗШ_c", pdif.getOp().getGeneral()));
        nhmi2.addSignals(new NHMISignal("Блокировка", pdif.getBlkHA().getGeneral()));
        ////////////////////////////////////////////////////////////////////////////////////////////
//        nhmi3.addSignals(new NHMISignal("BlkA", pdif.getBlkHA().getGeneral()));
        nhmi2.addSignals(new NHMISignal("Switch", xcbr.getPos().getCtlVal()));
//        nhmi3.addSignals(new NHMISignal("Br1", xcbr2.getPos().getCtlVal()));
//        nhmi3.addSignals(new NHMISignal("Br1", xcbr3.getPos().getCtlVal()));
//        nhmi3.addSignals(new NHMISignal("Br1", xcbr4.getPos().getCtlVal()));
        ////////////////////////////////////////////////////////////////////////////////////////////
//        nhmi4.addSignals(new NHMISignal("Fa1", mhai1.getHA().getPhsAHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fa2", mhai2.getHA().getPhsAHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fa3", mhai3.getHA().getPhsAHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fa4", mhai4.getHA().getPhsAHar().get(5).getMag().getF()));
//
//        nhmi4.addSignals(new NHMISignal("Fb1", mhai1.getHA().getPhsBHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fb2", mhai2.getHA().getPhsBHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fb3", mhai3.getHA().getPhsBHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fb4", mhai4.getHA().getPhsBHar().get(5).getMag().getF()));
//
//        nhmi4.addSignals(new NHMISignal("Fc1", mhai1.getHA().getPhsCHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fc2", mhai2.getHA().getPhsCHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fc3", mhai3.getHA().getPhsCHar().get(5).getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Fc4", mhai4.getHA().getPhsCHar().get(5).getMag().getF()));




        //nhmip.drawCharacteristic("pdif", difGodograph(pdif));







        while(lsvc.getIterator().hasNext()) {
            lsvc.process();
            mmxu1.process();
            mmxu2.process();
            mmxu3.process();
            mmxu4.process();
            mhai1.process();
            mhai2.process();
            mhai3.process();
            mhai4.process();
            rmxu.process();
            pdif.process();
            cswi.process();
            xcbr.process();
            nhmi1.process();
            nhmi2.process();
//            nhmi3.process();
//            nhmi4.process();

//            cswi1.process();
//            cswi2.process();
//            cswi3.process();
//            cswi4.process();
//            xcbr1.process();
//            xcbr2.process();
//            xcbr3.process();
//            xcbr4.process();



            System.out.println(lsvc.getSignals().get(0).getInstMag().getF().getValue());

        }




    }


    // Метод для построения тормозной характеристики
//    private static List<NHMIPoint<Double, Double>> difGodograph(PDIF pdif) {
//
//// Создаем массив точек для построения тормозной характеристики
//        List<NHMIPoint<Double, Double>> characteristicPoints = new ArrayList<>();
//
//// Расчет координат для построения тормозной характеристики
//        for (double x = 0; x < pdif.getTmASt().getCrvPts().get(1).getXVal().getValue(); x += 0.05) {
//            double y = pdif.getTmASt().getCrvPts().get(1).getYVal().getValue();
//            characteristicPoints.add(new NHMIPoint<>(x, y));
//        }
//        for (double x = pdif.getTmASt().getCrvPts().get(2).getXVal().getValue();
//             x < pdif.getTmASt().getCrvPts().get(3).getYVal().getValue(); x += 0.05) {
//            double y = x * pdif.getTmASt().getCrvPts().get(2).getCoef().getValue();
//            characteristicPoints.add(new NHMIPoint<>(x, y));
//        }
//        for (double x = pdif.getTmASt().getCrvPts().get(2).getXVal().getValue();
//             x < pdif.getTmASt().getCrvPts().get(2).getYVal().getValue(); x += 0.05) {
//            double y = x * pdif.getTmASt().getCrvPts().get(3).getCoef().getValue();
//            characteristicPoints.add(new NHMIPoint<>(x, y));
//        }
//        for (double x = pdif.getTmASt().getCrvPts().get(2).getXVal().getValue();
//             x < pdif.getTmASt().getCrvPts().get(3).getYVal().getValue(); x += 0.05) {
//            double y = x * pdif.getTmASt().getCrvPts().get(4).getCoef().getValue();
//            characteristicPoints.add(new NHMIPoint<>(x, y));
//        }
//        return characteristicPoints;
//    }



}
