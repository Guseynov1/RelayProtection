package process.protection.direction;

import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.measurements.MSQI;
import iec61850.nodes.measurements.control.CSWI;
import iec61850.nodes.measurements.control.primary.XCBR;
import iec61850.nodes.protection.PTOC;
import iec61850.nodes.protection.relatedFunction.RDIR;

public class Main {

    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        NHMI nhmi = new NHMI();
        MMXU mmxu = new MMXU();
        MSQI msqi = new MSQI();
        RDIR rdir = new RDIR();
        PTOC ptoc1dir = new PTOC();
        PTOC ptoc2dir = new PTOC();
        PTOC ptoc3dir = new PTOC();
        PTOC ptoc1nonDir = new PTOC();
        PTOC ptoc2nonDir = new PTOC();
        CSWI cswi = new CSWI();
        XCBR xcbr = new XCBR();

        double count = 0;

        lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\ТНЗНП\\Опыты\\KZ7");

        mmxu.setInstUa(lsvc.getSignals().get(0));
        mmxu.setInstUb(lsvc.getSignals().get(1));
        mmxu.setInstUc(lsvc.getSignals().get(2));
        mmxu.setInstIa(lsvc.getSignals().get(3));
        mmxu.setInstIb(lsvc.getSignals().get(4));
        mmxu.setInstIc(lsvc.getSignals().get(5));

        msqi.setA(mmxu.getA()); // на вход передаем - берем тройку векторов тока узла mmxu
        msqi.setPhV(mmxu.getPhV()); // фазовые значения напряжения

        rdir.setW(mmxu.getW());
        rdir.getSeqS().getC0().setCVal(msqi.getSeqS().getC0().getCVal());
        // Направленные ступени ТЗНП //
        // Первая ступень
        ptoc1dir.getA().setPhsA(msqi.getSeqA().getC0());
        ptoc1dir.getA().setPhsB(msqi.getSeqA().getC0());
        ptoc1dir.getA().setPhsC(msqi.getSeqA().getC0());
        ptoc1dir.setA(mmxu.getA());

        ptoc1dir.getStrVal().getSetVal().getF().setValue(500.0f);
        ptoc1dir.getOpDlTmms().setSetVal(0);
        ptoc1dir.getDirMod().setSetVal(2);
        ptoc1dir.setDir(rdir.getDir());

        // Вторая ступень
        ptoc2dir.getA().setPhsA(msqi.getSeqA().getC0());
        ptoc2dir.getA().setPhsB(msqi.getSeqA().getC0());
        ptoc2dir.getA().setPhsC(msqi.getSeqA().getC0());

        ptoc2dir.getStrVal().getSetVal().getF().setValue(300.0f);
        ptoc2dir.getOpDlTmms().setSetVal(250);
        ptoc2dir.getDirMod().setSetVal(2);
        ptoc2dir.setDir(rdir.getDir());

        // Третья ступень
        ptoc3dir.getA().setPhsA(msqi.getSeqA().getC0());
        ptoc3dir.getA().setPhsB(msqi.getSeqA().getC0());
        ptoc3dir.getA().setPhsC(msqi.getSeqA().getC0());

        ptoc3dir.getStrVal().getSetVal().getF().setValue(200.0f);
        ptoc3dir.getOpDlTmms().setSetVal(500);
        ptoc3dir.getDirMod().setSetVal(2);
        ptoc3dir.setDir(rdir.getDir());


        // Ненаправленные ступени ТЗНП //
        // Первая ступень
        ptoc1nonDir.getA().setPhsA(msqi.getSeqA().getC0());
        ptoc1nonDir.getA().setPhsB(msqi.getSeqA().getC0());
        ptoc1nonDir.getA().setPhsC(msqi.getSeqA().getC0());

        ptoc1nonDir.getStrVal().getSetVal().getF().setValue(800.0f);
        ptoc1nonDir.getOpDlTmms().setSetVal(0);

        // Вторая ступень
        ptoc2nonDir.getA().setPhsA(msqi.getSeqA().getC0());
        ptoc2nonDir.getA().setPhsB(msqi.getSeqA().getC0());
        ptoc2nonDir.getA().setPhsC(msqi.getSeqA().getC0());

        ptoc2nonDir.getStrVal().getSetVal().getF().setValue(600.0f);
        ptoc2nonDir.getOpDlTmms().setSetVal(250);

        cswi.setOpOpn1(ptoc1dir.getOp());
        cswi.setOpOpn2(ptoc2dir.getOp());
        cswi.setOpOpn3(ptoc3dir.getOp());
        cswi.setOpOpn4(ptoc1nonDir.getOp());
        cswi.setOpOpn5(ptoc2nonDir.getOp());

        xcbr.setPos(cswi.getPos());


        /////////////////////////////// мгновенные величины
        nhmi.addSignals(new NHMISignal("U1", lsvc.getSignals().get(0).getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("U2", lsvc.getSignals().get(1).getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("U3", lsvc.getSignals().get(2).getInstMag().getF()));
        ///////////////////////////////
        nhmi.addSignals(new NHMISignal("I1", lsvc.getSignals().get(3).getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("I2", lsvc.getSignals().get(4).getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("I3", lsvc.getSignals().get(5).getInstMag().getF()));
        /////////////////////////////// результат Фурье
//        nhmi.addSignals("I1Fourier",
//                new NHMISignal("I1Fourier", msqi.getSeqA().getC1().getcVal().getMag()),
//                new NHMISignal("TripPoint", ptoc.getStrVal().getSetVal().getF()));
//        nhmi.addSignals("I2Fourier",
//                new NHMISignal("I2Fourier", msqi.getSeqA().getC2().getcVal().getMag()),
//                new NHMISignal("TripPoint", ptoc.getStrVal().getSetVal().getF()));
        ///////////////////////////////
        nhmi.addSignals(new NHMISignal("I0", msqi.getSeqA().getC0().getCVal().getMag().getF()),
                new NHMISignal("setP1", ptoc1dir.getStrVal().getSetVal().getF()),
                new NHMISignal("setP2", ptoc2dir.getStrVal().getSetVal().getF()),
                new NHMISignal("setP3", ptoc3dir.getStrVal().getSetVal().getF()));
        ///////////////////////////////
//        nhmi.addSignals(new NHMISignal("Пуск защиты А", ptoc1dir.getStr().getPhsA()),
//                new NHMISignal("setP2", ptoc2dir.getStr().getPhsA()),
//                new NHMISignal("setP3", ptoc3dir.getStr().getPhsA()));
//        nhmi.addSignals(new NHMISignal("Срабатывание А", ptoc1dir.getOp().getPhsA()),
//                new NHMISignal("setP2", ptoc2dir.getOp().getPhsA()),
//                new NHMISignal("setP3", ptoc3dir.getOp().getPhsA()));
        nhmi.addSignals(new NHMISignal("Пуск защиты B", ptoc1dir.getStr().getPhsB()));
        nhmi.addSignals(new NHMISignal("Пуск защиты B", ptoc2dir.getStr().getPhsB()));
        nhmi.addSignals(new NHMISignal("Пуск защиты B", ptoc3dir.getStr().getPhsB()));
                //new NHMISignal("setP2", ptoc2dir.getStr().getPhsB()),
                //new NHMISignal("setP3", ptoc3dir.getStr().getPhsB()));
        nhmi.addSignals(new NHMISignal("Срабатывание B", ptoc1dir.getOp().getPhsB()));
        nhmi.addSignals(new NHMISignal("Срабатывание B", ptoc2dir.getOp().getPhsB()));
        nhmi.addSignals(new NHMISignal("Срабатывание B", ptoc3dir.getOp().getPhsB()));
                //new NHMISignal("setP2", ptoc2dir.getOp().getPhsB()),
                //new NHMISignal("setP3", ptoc3dir.getOp().getPhsB()));
//        nhmi.addSignals(new NHMISignal("Пуск защиты С", ptoc1dir.getStr().getPhsC()),
//                new NHMISignal("setP2", ptoc2dir.getStr().getPhsC()),
//                new NHMISignal("setP3", ptoc3dir.getStr().getPhsC()));
//        nhmi.addSignals(new NHMISignal("Срабатывание С", ptoc1dir.getOp().getPhsC()),
//                new NHMISignal("setP2", ptoc2dir.getOp().getPhsC()),
//                new NHMISignal("setP3", ptoc3dir.getOp().getPhsC()));
        ////////////////////////////// сигнал состояния выключателя
        nhmi.addSignals(new NHMISignal("Выключатель", xcbr.getPos().getCtlVal()));
        //nhmi.addSignals(new NHMISignal("Выключатель", xcbr.getPos().getStVal()));
//        nhmi.addSignals(new NHMISignal("Switch1", xcbr.getPos().getCtlVal()));
        //////////////////////////////
        nhmi.addSignals(new NHMISignal("Мощность ", rdir.getSeqS().getC0().getCVal().getOrtX().getF()));
//        nhmi.addSignals(new NHMISignal("b", mmxu.getW().getPhsB().getcVal().getMag()));
//        nhmi.addSignals(new NHMISignal("c", mmxu.getW().getPhsC().getcVal().getMag()));
        nhmi.addSignals(new NHMISignal("U0", msqi.getSeqV().getC0().getCVal().getMag().getF()));

        while(lsvc.getIterator().hasNext()) {
            lsvc.process();
            nhmi.process();
            mmxu.process();
            msqi.process();
            rdir.process();
            ptoc1dir.process();
            ptoc2dir.process();
            ptoc3dir.process();
            ptoc1nonDir.process();
            ptoc2nonDir.process();
            cswi.process();
            xcbr.process();

            //if(ptoc3dir.getStr().getPhsA().getValue() || cswi.getOpCls().getGeneral().getValue() && xcbr.getPos().getStVal().getValue() == 1) count+=1000; else count = 0;
            if(count < 1000){
                ptoc1dir.getOpDlTmms().setSetVal(0);
                ptoc2dir.getOpDlTmms().setSetVal(0);
                ptoc3dir.getOpDlTmms().setSetVal(0);
                ptoc1nonDir.getOpDlTmms().setSetVal(0);
                ptoc2nonDir.getOpDlTmms().setSetVal(0);
            }
            if(count > 1000){
                ptoc1dir.getOpDlTmms().setSetVal(500);
                ptoc2dir.getOpDlTmms().setSetVal(300);
                ptoc3dir.getOpDlTmms().setSetVal(200);
                ptoc1nonDir.getOpDlTmms().setSetVal(800);
                ptoc2nonDir.getOpDlTmms().setSetVal(600);
            }
            System.out.println(lsvc.getSignals().get(0).getInstMag().getF().getValue());

        }
    }
}



















































// если идет сигнал на включение и выключатель при этом выключен (отключил кз - состояние выключателя перешло в 1 (выключен)
// идет сигнал на включение после того как кз пропало - убрать все выдержки последующих ступеней
// именно после кз проверить не сможем - он проверил - задом на перед менял условия - типа если на отключение выключателя
// т.е. делал ДО кз - они убирались и сразу все ступени без ВВ срабатывали

// это работает только в момент включения - должно работать больше промежуток времени, т.е. нужен count - на сколько это ускорение вводится, секунда-пол секунды
// - на это время убираем все ВВ, т.е. не просто убрал ВВ на мгновение, т.е. включается выключатель - тут же появляется ВВ - нет; должен убрать на время - т.е. включился выключатель - проходит секунда - кз нет и возвращаются уставки назад
