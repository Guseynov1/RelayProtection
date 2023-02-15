package process.protection.distance;

import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.NHMIP;
import iec61850.nodes.gui.other.NHMIPoint;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.measurements.MSQI;
import iec61850.nodes.measurements.control.CSWI;
import iec61850.nodes.measurements.control.primary.XCBR;
import iec61850.nodes.measurements.filter.Fourier;
import iec61850.nodes.protection.PDIS;
import iec61850.nodes.protection.PTRC;
//import iec61850.nodes.protection.relatedFunction.RPSB;
//import iec61850.nodes.protection.relatedFunction.RPSB1;
import iec61850.nodes.protection.relatedFunction.RPSB;
import iec61850.objects.measurements.Vector;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        NHMI nhmi1 = new NHMI();
        NHMI nhmi2 = new NHMI();
        NHMI nhmi3 = new NHMI();
        NHMIP nhmip1 = new NHMIP();
        NHMIP nhmip2 = new NHMIP();
        MMXU mmxu = new MMXU();
        RPSB rpsb = new RPSB();
        RPSB rpsb1Dir = new RPSB();
        RPSB rpsb2Dir = new RPSB();
        RPSB rpsb1NonDir = new RPSB();
        RPSB rpsb2NonDir = new RPSB();
        RPSB rpsb3NonDir = new RPSB();
        PDIS pdis1 = new PDIS();
        PDIS pdis2 = new PDIS();
        PDIS pdis1Non = new PDIS();
        PDIS pdis2Non = new PDIS();
        PDIS pdis3Non = new PDIS();
        CSWI cswi = new CSWI();
        XCBR xcbr = new XCBR();
        MSQI msqi = new MSQI();
        PTRC ptrc = new PTRC();
        Fourier fourier = new Fourier(1);

        lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\ТНЗНП\\Опыты\\KZ1");

        mmxu.setInstUa(lsvc.getSignals().get(2));
        mmxu.setInstUb(lsvc.getSignals().get(1));
        mmxu.setInstUc(lsvc.getSignals().get(0));
        mmxu.setInstIa(lsvc.getSignals().get(3));
        mmxu.setInstIb(lsvc.getSignals().get(4));
        mmxu.setInstIc(lsvc.getSignals().get(5));


        msqi.setA(mmxu.getA());
        msqi.setPhV(mmxu.getPhV());

        // блокировка по ОП
        rpsb.setSeqA(msqi.getSeqA());
        // по производной через зоны и время
        // выставим уставку по времени нахождения в зоне блокировки
        rpsb1Dir.setSeqA(msqi.getSeqA());
        rpsb2Dir.setSeqA(msqi.getSeqA());

        rpsb1NonDir.setSeqA(msqi.getSeqA());
        rpsb2NonDir.setSeqA(msqi.getSeqA());
        rpsb3NonDir.setSeqA(msqi.getSeqA());

        rpsb1Dir.getEvTmms().setSetVal(8);
        rpsb2Dir.getEvTmms().setSetVal(8);

        rpsb1NonDir.getEvTmms().setSetVal(8);
        rpsb2NonDir.getEvTmms().setSetVal(8);
        rpsb3NonDir.getEvTmms().setSetVal(8);
        // выставим уставку по времени нахождения в зоне блокировки для разблокировки
        rpsb1Dir.getUnBlkTmms().setSetVal(10);
        rpsb2Dir.getUnBlkTmms().setSetVal(10);

        rpsb1NonDir.getUnBlkTmms().setSetVal(10);
        rpsb2NonDir.getUnBlkTmms().setSetVal(10);
        rpsb3NonDir.getUnBlkTmms().setSetVal(10);

        //rpsb1.getSwgVal().getSetMag().getF().setValue(0.001f);
        //rpsb1.process(mmxu.getZab());
        //rpsb1.getZ_before().getPhsAB().setcVal(mmxu.getZab());
        //rpsb2.getSwgVal().getSetMag().getF().setValue(0.001f);
        //rpsb2.process(mmxu.getZbc());
        //rpsb2.process();
        //rpsb2.getZ_before().getPhsBC().setcVal(mmxu.getZbc());
        //rpsb3.getSwgVal().getSetMag().getF().setValue(0.001f);
        //rpsb3.getZ_before().getPhsCA().setcVal(mmxu.getZca());
        //rpsb3.process(mmxu.getZca());

//        boolean swings = rpsb1.getOp().getGeneral().getValue() || rpsb2.getOp().getGeneral().getValue() || rpsb3.getOp().getGeneral().getValue();
//        Z.getPhsAB().setcVal(mmxu.getZab());
//        Z.getPhsBC().setcVal(mmxu.getZbc());
//        Z.getPhsCA().setcVal(mmxu.getZca());

        // выставим уставки для внутренней хар-ки блокировки
        rpsb1NonDir.setR(76.925);
        rpsb2NonDir.setR(135.773);
        rpsb3NonDir.setR(292.557);
        //////////////////////
        rpsb1Dir.setR(76.925);
        rpsb2Dir.setR(135.773);
        //////////////////////
        rpsb1NonDir.setX0(0);
        rpsb1NonDir.setY0(0);
        rpsb2NonDir.setX0(0);
        rpsb2NonDir.setY0(0);
        rpsb3NonDir.setX0(0);
        rpsb3NonDir.setY0(0);
        //////////////////////
        rpsb1Dir.setX0(11.139);
        rpsb1Dir.setY0(76.114);
        rpsb2Dir.setX0(19.661);
        rpsb2Dir.setY0(134.342);

        // выставим уставки для внешней хар-ки блокировки
        rpsb1NonDir.setR1(1.);
        rpsb2NonDir.setR1(1.);
        rpsb3NonDir.setR1(1.);
        //////////////////////
        rpsb1Dir.setR1(1.);
        rpsb2Dir.setR1(1.);

        // передаем значения сопротивления в блокировку
        rpsb1NonDir.setPPZ(mmxu.getPPZ());
        rpsb2NonDir.setPPZ(mmxu.getPPZ());
        rpsb3NonDir.setPPZ(mmxu.getPPZ());
        //////////////////////
        rpsb1Dir.setPPZ(mmxu.getPPZ());
        rpsb2Dir.setPPZ(mmxu.getPPZ());

        // передадим значения сопротивления в защиту
        pdis1Non.setPPZ(mmxu.getPPZ());
        pdis2Non.setPPZ(mmxu.getPPZ());
        pdis3Non.setPPZ(mmxu.getPPZ());
        ///////////////////////
        pdis1.setPPZ(mmxu.getPPZ());
        pdis2.setPPZ(mmxu.getPPZ());

        // передадим значения блокировки
        pdis1Non.setBlkStr(rpsb1NonDir.getStr());
        pdis2Non.setBlkStr(rpsb2NonDir.getStr());
        pdis3Non.setBlkStr(rpsb3NonDir.getStr());
        ///////////////////////
        pdis1.setBlkStr(rpsb1Dir.getStr());
        pdis2.setBlkStr(rpsb2Dir.getStr());

        // передаем уставки в защиту
        pdis1Non.setR(76.925);
        pdis2Non.setR(135.773);
        pdis3Non.setR(292.557);
        //////////////////////
        pdis1.setR(76.925);
        pdis2.setR(135.773);
        //////////////////////
        pdis1Non.setX0(0);
        pdis1Non.setY0(0);
        pdis2Non.setX0(0);
        pdis2Non.setY0(0);
        pdis3Non.setX0(0);
        pdis3Non.setY0(0);
        //////////////////////
        pdis1.setX0(11.139);
        pdis1.setY0(76.114);
        pdis2.setX0(19.661);
        pdis2.setY0(134.342);
        //////////////////////
        pdis1Non.getOpDlTmms().setSetVal(100);
        pdis2Non.getOpDlTmms().setSetVal(300);
        pdis3Non.getOpDlTmms().setSetVal(900);
        //////////////////////
        pdis1.getOpDlTmms().setSetVal(100);
        pdis2.getOpDlTmms().setSetVal(300);


//        ptrc.getOp().add(pdis1.getOp());
//        ptrc.getOp().add(pdis2.getOp());
//        ptrc.getOp().add(pdis1Non.getOp());
//        ptrc.getOp().add(pdis2Non.getOp());
//        ptrc.getOp().add(pdis3Non.getOp());
//
//        cswi.setOpOpn(ptrc.getTr());
//        cswi.setOpCls(ptrc.getTr());

        // управление выключателем
        cswi.setOpOpn1(pdis1.getOp());
        cswi.setOpOpn2(pdis2.getOp());
        cswi.setOpOpn3(pdis1Non.getOp());
        cswi.setOpOpn4(pdis2Non.getOp());
        cswi.setOpOpn5(pdis3Non.getOp());
        //////////////////////
        xcbr.setPos(cswi.getPos());





//        // Направленная ступень
//        double X0 = 0;
//        double Y0 = 0;
//        double R = 200;
//        for(double x= -2*R; x<= 2*R; x += 0.1) {
//            double y = Math.sqrt(Math.pow(R, 2) - Math.pow((x-X0), 2)) + Y0;
//            pointList.add(new NHMIPoint<>(x, y));
//            pointList.add(new NHMIPoint<>(x, -y));
//        }
//
//        // Ненаправленная ступень
//        double X0non = 155;
//        double Y0non = 0;
//        double Rnon = 200;
//        for(double xNon= -2*Rnon; xNon<= 2*Rnon; xNon += 0.1) {
//            double yNon = Math.sqrt(Math.pow(Rnon, 2) - Math.pow((xNon-X0non), 2)) + Y0non;
//            pointListNon.add(new NHMIPoint<>(xNon, yNon+150));
//            pointListNon.add(new NHMIPoint<>(xNon, -yNon+150));
//        }
//
//
//        //pdis1.setSwings(swings);
//        pdis1.setZ(mmxu.getZi());
//
//        pdis1.getR0().getSetMag().getF().setValue(0f);
//        pdis1.getX0().getSetMag().getF().setValue(0f);
//        pdis1.getZu().getSetMag().getF().setValue(200f);
//        //pdis1.getOpDlTmms().setSetVal(0);
//
//        //pdis2.setSwings(swings);
//        pdis2.setZ(mmxu.getZi());
//
//        pdis2.getR0().getSetMag().getF().setValue(0f);
//        pdis2.getX0().getSetMag().getF().setValue(0f);
//        pdis2.getZu().getSetMag().getF().setValue(200f);
//        //pdis2.getOpDlTmms().setSetVal(250);
//
//       // pdis3.setSwings(swings);
//        pdis3.setZ(mmxu.getZi());
//
//        pdis3.getR0().getSetMag().getF().setValue(0f);
//        pdis3.getX0().getSetMag().getF().setValue(0f);
//        pdis3.getZu().getSetMag().getF().setValue(200f);
//        //pdis3.getOpDlTmms().setSetVal(500);
//
//       // pdis1Non.setSwings(swings);
//        pdis1Non.setZ(mmxu.getZi());
//        pdis1Non.getR0().getSetMag().getF().setValue(0f);
//        pdis1Non.getX0().getSetMag().getF().setValue(0f);
//        pdis1Non.getZu().getSetMag().getF().setValue(200f);
//        //pdis1Non.getOpDlTmms().setSetVal(0);
//
//       // pdis2Non.setSwings(swings);
//        pdis2Non.setZ(mmxu.getZi());
//        pdis2Non.getR0().getSetMag().getF().setValue(0f);
//        pdis2Non.getX0().getSetMag().getF().setValue(0f);
//        pdis2Non.getZu().getSetMag().getF().setValue(200f);
//        //pdis2Non.getOpDlTmms().setSetVal(150);
//
//
//
//        pdis1.getOpDlTmms().setSetVal(15);
//        pdis2.getOpDlTmms().setSetVal(60);
//        pdis3.getOpDlTmms().setSetVal(110);
//        pdis1Non.getOpDlTmms().setSetVal(160);
//        pdis2Non.getOpDlTmms().setSetVal(200);
//
//
//
//        //cswi.getPos().getCtlVal().setValue(true);
//        //cswi.getPos().getStVal().setValue((byte) 2);
//
//        ptrc.getOp().add(pdis1.getOp());
//        ptrc.getOp().add(pdis2.getOp());
//        ptrc.getOp().add(pdis3.getOp());
//        ptrc.getOp().add(pdis1Non.getOp());
//        ptrc.getOp().add(pdis2Non.getOp());
//
//        cswi.setOpOpn1(pdis1.getOp());
//        cswi.setOpOpn2(pdis2.getOp());
//        cswi.setOpOpn3(pdis3.getOp());
//        cswi.setOpOpn4(pdis1Non.getOp());
//        cswi.setOpOpn5(pdis2Non.getOp());
//
//        xcbr.setPos(cswi.getPos());


        ////////////////
        nhmi1.addSignals(new NHMISignal("Ток фазы А", mmxu.getInstIa().getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("Ток фазы B", mmxu.getInstIb().getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("Ток фазы C", mmxu.getInstIc().getInstMag().getF()));
        ////////////////
        nhmi1.addSignals(new NHMISignal("Zab", mmxu.getPPZ().getPhsAB().getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("Zbc", mmxu.getPPZ().getPhsBC().getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("Zca", mmxu.getPPZ().getPhsCA().getCVal().getMag().getF()));

//        nhmi1.addSignals(new NHMISignal("Uab", mmxu.getPPV().getPhsAB().getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ubc", mmxu.getPPV().getPhsBC().getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Uca", mmxu.getPPV().getPhsCA().getCVal().getMag().getF()));

//        nhmi1.addSignals(new NHMISignal("Iab", mmxu.getPPA().getPhsAB().getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ibc", mmxu.getPPA().getPhsBC().getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ica", mmxu.getPPA().getPhsCA().getCVal().getMag().getF()));

        nhmi1.addSignals(new NHMISignal("BlkStr", pdis1.getBlkStr().getGeneral()));

        nhmi2.addSignals(new NHMISignal("DZ_ПО I", pdis1.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZ I", pdis1.getOp().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZ_ПО II", pdis2.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZ II", pdis2.getOp().getGeneral()));

        nhmi2.addSignals(new NHMISignal("DZNon_ПО I", pdis1Non.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZNon I", pdis1Non.getOp().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZNon_ПО II", pdis2Non.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZNon II", pdis2Non.getOp().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZNon_ПО III", pdis3Non.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("DZNon III", pdis3Non.getOp().getGeneral()));


        nhmi2.addSignals(new NHMISignal("Положение выключателя", xcbr.getPos().getCtlVal()));

        nhmi3.addSignals(new NHMISignal("Блокировка DZ I", rpsb1Dir.getStr().getGeneral()));
        nhmi3.addSignals(new NHMISignal("Блокировка DZ II", rpsb2Dir.getStr().getGeneral()));
        nhmi3.addSignals(new NHMISignal("Блокировка DZNon I", rpsb1NonDir.getStr().getGeneral()));
        nhmi3.addSignals(new NHMISignal("Блокировка DZNon II", rpsb2NonDir.getStr().getGeneral()));
        nhmi3.addSignals(new NHMISignal("Блокировка DZNon III", rpsb3NonDir.getStr().getGeneral()));
        /////////////////////////////////////////////////////////////
        nhmip1.drawCharacteristic("Fourier", Fourier(new Fourier(1)));


        //fourier.getK()


//        nhmip2.drawCharacteristic("DZ_1Non", distGodograph(pdis1Non));
//        nhmip2.drawCharacteristic("DZ_2Non", distGodograph(pdis2Non));
//        nhmip2.drawCharacteristic("DZ_3Non", distGodograph(pdis3Non));
//        /////////////////////////////////////////////////////////////
//        nhmip1.addSignals(new NHMISignal("Zab", mmxu.getPPZ().getPhsAB().getCVal().getOrtX().getF(), mmxu.getPPZ().getPhsAB().getCVal().getOrtY().getF()));
//        nhmip1.addSignals(new NHMISignal("Zbc", mmxu.getPPZ().getPhsBC().getCVal().getOrtX().getF(), mmxu.getPPZ().getPhsBC().getCVal().getOrtY().getF()));
//        nhmip1.addSignals(new NHMISignal("Zca", mmxu.getPPZ().getPhsCA().getCVal().getOrtX().getF(), mmxu.getPPZ().getPhsCA().getCVal().getOrtY().getF()));
//
//        nhmip2.addSignals(new NHMISignal("Zab", mmxu.getPPZ().getPhsAB().getCVal().getOrtX().getF(), mmxu.getPPZ().getPhsAB().getCVal().getOrtY().getF()));
//        nhmip2.addSignals(new NHMISignal("Zbc", mmxu.getPPZ().getPhsBC().getCVal().getOrtX().getF(), mmxu.getPPZ().getPhsBC().getCVal().getOrtY().getF()));
//        nhmip2.addSignals(new NHMISignal("Zca", mmxu.getPPZ().getPhsCA().getCVal().getOrtX().getF(), mmxu.getPPZ().getPhsCA().getCVal().getOrtY().getF()));


        while(lsvc.getIterator().hasNext()) {
            lsvc.process();
            mmxu.process();
            msqi.process();
            rpsb1Dir.process();
            rpsb2Dir.process();
            rpsb1NonDir.process();
            rpsb2NonDir.process();
            rpsb3NonDir.process();
            pdis1Non.process();
            pdis2Non.process();
            pdis3Non.process();
            pdis1.process();
            pdis2.process();
            cswi.process();
            xcbr.process();
            nhmip1.process();
            nhmip2.process();
            nhmi1.process();
            nhmi2.process();
            nhmi3.process();

            ptrc.process();

            System.out.println(lsvc.getSignals().get(0).getInstMag().getF().getValue());

        }

    }

    // отрисовка характеристики
//    private static List<NHMIPoint<Double, Double>> distGodograph (PDIS pdis){
//        // массив, куда будут записываться точки для построения
//        List<NHMIPoint<Double, Double>> points = new ArrayList<>();
//        // генерируем точки круговой хар-ки
//        for(double x = -(pdis.getR() - pdis.getX0()); x <= pdis.getR() + pdis.getX0(); x = x + 0.1) {
//            // верхняя часть окружности
//            double y1 = pdis.getY0() + Math.sqrt(pdis.getR() * pdis.getR() - Math.pow((x - pdis.getX0()), 2));
//            // нижняя часть окружности
//            double y2 = pdis.getY0() - Math.sqrt(pdis.getR() * pdis.getR() - Math.pow((x - pdis.getX0()), 2));
//            // добавим получившиеся точки в массив
//            points.add(new NHMIPoint<>(x, y1));
//            points.add(new NHMIPoint<>(x, y2));
//        }
//        return points;
//
//    }



    private static List<NHMIPoint<Double, Double>> Fourier (Fourier fourier) {
//        double f1 = 50; double f2 = 100; double f3 = 150;
//        double k = (float) 2/Fourier.size;
        double size = 80;
        List<NHMIPoint<Double, Double>> points = new ArrayList<>();
        for(double x = 0; x < Fourier.size; x ++){
            double y = x * Math.sin((float) 2*Math.PI * ((float) x/size));
            points.add(new NHMIPoint<>(x, y));
        }
        for(double x = 0; x < Fourier.size; x ++){
            double y = x * Math.sin((float) 4*Math.PI * ((float) x/size));
            points.add(new NHMIPoint<>(x, y));
        }
        for(double x = 0; x < Fourier.size; x ++){
            double y = x * Math.sin((float) 6*Math.PI * ((float) x/size));
            points.add(new NHMIPoint<>(x, y));
        }
        return points;

    }






}
