package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.nodes.gui.other.info.CSD;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.WYE;
import iec61850.objects.measurements.settings.ASG;
import iec61850.objects.measurements.settings.HWYE;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.ACT;
import iec61850.objects.samples.Attribute;
import iec61850.objects.statusControl.control.INC;
import iec61850.objects.statusControl.status.SPS;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter @Setter
public class PDIF extends LN {

    private INC OpCntRs = new INC();

    private ACD Str = new ACD();
    private ACT Op = new ACT();
    private CSD TmASt = new CSD(); // динамические хар-ки кривой

    private WYE DifAClc = new WYE(); // диф ток
    private WYE RstA = new WYE(); // ограничение по току

    private ASG LinCapac = new ASG(); // емкость линии (для токов нагрузки)
    private ING LoSet = new ING(); // нижний порог срабатывания, процент номинального тока
    private ING HiSet = new ING(); // верхний порог срабатывания, процент номинального тока
    private ING MinOpTmms = new ING(); // минимальное время срабатывания
    private ING MaxOpTmms = new ING(); // максимальное время срабатывания
    private ING RstMod = new ING(); // время ограничения
    private ING RsDlTmms = new ING(); // время задержки сброса

    private boolean trip = false;
    private WYE SetPoint = new WYE();
    private HWYE HA1 = new HWYE();
    private HWYE HA2 = new HWYE();
    private HWYE HA3 = new HWYE();
    private HWYE HA4 = new HWYE();

    private Vector rstCurrent = new Vector();
    private double BlkValue;
    private ACD BlkHA = new ACD();
    private int block = 5;
    private Attribute<Integer> blk = new Attribute<>(0);






    private ASG hBlock = new ASG(); // блокировка по пятой гармонике - при каком значении содержание пятой гармоники в сигнале защита блокируется

    private ArrayList<HWYE> hinputs = new ArrayList<>(); // гармонический состав для данных сигналов, будет приниматься для блокировки какой-либо гармоники

    private SPS blkA = new SPS(), blkB = new SPS(), blkC = new SPS(), blkGen = new SPS(); // тип boolean


    /** Переменные характеристики срабатывания */

    private float dif0, rst0, dif3a, dif3b, dif3c = 0;

    private float S1 = 0.25f;
    private float S2 = 0.6f;
    private float S = 0;

    private float B1 = 2;
    private float B2 = 8;

    private float dto = 0; // ДТО выведена

    private float c0 = 0, c1 = 0, c2 = 0, c3 = 0;

    private float difMin = 0; // минимальный диф ток срабатывания
    private int countA = 0, countB = 0, countC = 0, counter = 0;

    private static final double swim = 1;

    public void setting(){

        /* Построение характеристики */

        dif0 = TmASt.getCrvPts().get(1).getYVal().getValue();
        rst0 = TmASt.getCrvPts().get(1).getXVal().getValue();

        S = (TmASt.getCrvPts().get(2).getYVal().getValue() - TmASt.getCrvPts().get(1).getYVal().getValue()) /
                (TmASt.getCrvPts().get(2).getXVal().getValue() - TmASt.getCrvPts().get(1).getXVal().getValue());

//        S1 = TmASt.getCrvPts().get(2).getCoef().getValue();
//        S2 = TmASt.getCrvPts().get(3).getCoef().getValue();

    }

    @Override
    public void process() {

        if (RstA.getPhsA().getCVal().getMag().getF().getValue() < rst0){
            SetPoint.getPhsA().getCVal().getMag().getF().setValue(dif0);
            SetPoint.getPhsB().getCVal().getMag().getF().setValue(dif0);
            SetPoint.getPhsC().getCVal().getMag().getF().setValue(dif0);

        } else {
            SetPoint.getPhsA().getCVal().getMag().getF().setValue(dif0 + RstA.getPhsA().getCVal().getMag().getF().getValue() * S - 1000);
            SetPoint.getPhsB().getCVal().getMag().getF().setValue(dif0 + RstA.getPhsB().getCVal().getMag().getF().getValue() * S - 1000);
            SetPoint.getPhsC().getCVal().getMag().getF().setValue(dif0 + RstA.getPhsC().getCVal().getMag().getF().getValue() * S - 1000);

        }
        SetPoint.getPhsA().getCVal().getAng().getF().setValue(0.F);
        SetPoint.getPhsB().getCVal().getAng().getF().setValue(0.F);
        SetPoint.getPhsC().getCVal().getAng().getF().setValue(0.F);

        /* Блокировка */
        hinputs.forEach(elem -> {
            if (elem.getPhsAHar().get(block).getMag().getF().getValue() / elem.getPhsAHar().get(1).getMag().getF().getValue() > BlkValue
                    || elem.getPhsBHar().get(block).getMag().getF().getValue() / elem.getPhsBHar().get(1).getMag().getF().getValue() > BlkValue
                    || elem.getPhsCHar().get(block).getMag().getF().getValue() / elem.getPhsCHar().get(1).getMag().getF().getValue() > BlkValue) {
                BlkHA.getGeneral().setValue(true);
//                counter = 0;
//                countA = 0;
//                countB = 0;
//                countC = 0;
            } else BlkHA.getGeneral().setValue(false);
        });

        Str.getPhsA().setValue(DifAClc.getPhsA().getCVal().getMag().getF().getValue() > SetPoint.getPhsA().getCVal().getMag().getF().getValue());
        Str.getPhsB().setValue(DifAClc.getPhsB().getCVal().getMag().getF().getValue() > SetPoint.getPhsB().getCVal().getMag().getF().getValue());
        Str.getPhsC().setValue(DifAClc.getPhsC().getCVal().getMag().getF().getValue() > SetPoint.getPhsC().getCVal().getMag().getF().getValue());

        if (Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue()) {
            Str.getGeneral().setValue(true);
            counter += swim;
        } else {
            Str.getGeneral().setValue(false);
            counter = 0;
        }
        if (BlkHA.getGeneral().getValue()) counter = 0;

        Op.getGeneral().setValue(counter >= MinOpTmms.getSetVal());
        Op.getPhsA().setValue(counter >= MinOpTmms.getSetVal());
        Op.getPhsB().setValue(counter >= MinOpTmms.getSetVal());
        Op.getPhsC().setValue(counter >= MinOpTmms.getSetVal());



//        if((countA >= MinOpTmms.getSetVal()) || (countB >= MinOpTmms.getSetVal()) ||
//                (countC >= MinOpTmms.getSetVal())){
//            Op.getGeneral().setValue(true);
//        }



//        c0 = (float) (2*(S1-S2)*Math.pow(B1, 2) * Math.pow(B1, 2)) / (float) Math.pow((B1-B2), 3);
//        c1 = (float) (S2*B1*(Math.pow(B1, 2)+B1*B2+4*Math.pow(B2, 2)) -
//                S1*B2*(Math.pow(B2, 2)+B1*B2+4*Math.pow(B1, 2))) / (float) Math.pow((B1-B2), 3);
//        c2 = (float) (2*(S1-S2)*(Math.pow(B1, 1)+B1*B2+Math.pow(B2, 2))) / (float) Math.pow((B1-B2), 3);
//        c3 = ((S1-S2)*(B1+B2)) / (float) Math.pow((B1-B2), 3);
//
//        dif3a = c0+c1*RstA.getPhsA().getCVal().getMag().getF().getValue()+c2*Math.pow(RstA.getPhsA().getCVal().getMag().getF().getValue(), 2)+
//                c3*Math.pow(RstA.getPhsA().getCVal().getMag().getF().getValue(), 3);
//        dif3b = c0+c1*RstA.getPhsB().getCVal().getMag().getF().getValue()+c2*Math.pow(RstA.getPhsB().getCVal().getMag().getF().getValue(), 2)+
//                c3*Math.pow(RstA.getPhsB().getCVal().getMag().getF().getValue(), 3);
//        dif3c = c0+c1*RstA.getPhsC().getCVal().getMag().getF().getValue()+c2*Math.pow(RstA.getPhsC().getCVal().getMag().getF().getValue(), 2)+
//                c3*Math.pow(RstA.getPhsC().getCVal().getMag().getF().getValue(), 3);
//
//
//        if((getDifAClc().getPhsA().getCVal().getMag().getF().getValue() > dif0 &&
//                getRstA().getPhsA().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(0).getXVal().getValue() &&
//                getRstA().getPhsA().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(1).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsA().getCVal().getMag().getF().getValue() > S1*getRstA().getPhsA().getCVal().getMag().getF().getValue() &&
//                        getRstA().getPhsA().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(1).getXVal().getValue() &&
//                        getRstA().getPhsA().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(2).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsA().getCVal().getMag().getF().getValue() > dif3a && getRstA().getPhsA().getCVal().getMag().getF().getValue() >
//                        TmASt.getCrvPts().get(2).getXVal().getValue() && getRstA().getPhsA().getCVal().getMag().getF().getValue() <
//                        TmASt.getCrvPts().get(3).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsA().getCVal().getMag().getF().getValue() > S2*getRstA().getPhsA().getCVal().getMag().getF().getValue() &&
//                        getRstA().getPhsA().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(3).getXVal().getValue() &&
//                        getRstA().getPhsA().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(4).getXVal().getValue())){
//
//            Str.getPhsA().setValue(true);
//        }
//        if(Str.getPhsA().getValue()){
//            countA += swim;
//        } else {countA = 0;}
//
//        if((getDifAClc().getPhsB().getCVal().getMag().getF().getValue() > dif0 &&
//                getRstA().getPhsB().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(0).getXVal().getValue() &&
//                getRstA().getPhsB().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(1).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsB().getCVal().getMag().getF().getValue() > S1*getRstA().getPhsB().getCVal().getMag().getF().getValue() &&
//                        getRstA().getPhsB().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(1).getXVal().getValue() &&
//                        getRstA().getPhsB().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(2).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsB().getCVal().getMag().getF().getValue() > dif3b && getRstA().getPhsB().getCVal().getMag().getF().getValue() >
//                        TmASt.getCrvPts().get(2).getXVal().getValue() && getRstA().getPhsB().getCVal().getMag().getF().getValue() <
//                        TmASt.getCrvPts().get(3).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsB().getCVal().getMag().getF().getValue() > S2*getRstA().getPhsB().getCVal().getMag().getF().getValue() &&
//                        getRstA().getPhsB().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(3).getXVal().getValue() &&
//                        getRstA().getPhsB().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(4).getXVal().getValue())){
//
//            Str.getPhsB().setValue(true);
//        }
//        if(Str.getPhsB().getValue()){
//            countB += swim;
//        } else {countB = 0;}
//
//        if((getDifAClc().getPhsC().getCVal().getMag().getF().getValue() > dif0 &&
//                getRstA().getPhsC().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(0).getXVal().getValue() &&
//                getRstA().getPhsC().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(1).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsC().getCVal().getMag().getF().getValue() > S1*getRstA().getPhsC().getCVal().getMag().getF().getValue() &&
//                        getRstA().getPhsC().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(1).getXVal().getValue() &&
//                        getRstA().getPhsC().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(2).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsC().getCVal().getMag().getF().getValue() > dif3c && getRstA().getPhsC().getCVal().getMag().getF().getValue() >
//                        TmASt.getCrvPts().get(2).getXVal().getValue() && getRstA().getPhsC().getCVal().getMag().getF().getValue() <
//                        TmASt.getCrvPts().get(3).getXVal().getValue()) ||
//
//                (getDifAClc().getPhsC().getCVal().getMag().getF().getValue() > S2*getRstA().getPhsC().getCVal().getMag().getF().getValue() &&
//                        getRstA().getPhsC().getCVal().getMag().getF().getValue() > TmASt.getCrvPts().get(3).getXVal().getValue() &&
//                        getRstA().getPhsC().getCVal().getMag().getF().getValue() < TmASt.getCrvPts().get(4).getXVal().getValue())){
//
//            Str.getPhsC().setValue(true);
//        }
//        if(Str.getPhsC().getValue()){
//            countC += swim;
//        } else {countC = 0;}




        // блокировка по 5-ой гармонике
//        if(HA1.getPhsAHar().get(5).getMag().getF().getValue() / HA1.getPhsAHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA1.getPhsBHar().get(5).getMag().getF().getValue() / HA1.getPhsBHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA1.getPhsCHar().get(5).getMag().getF().getValue() / HA1.getPhsCHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA2.getPhsAHar().get(5).getMag().getF().getValue() / HA2.getPhsAHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA2.getPhsBHar().get(5).getMag().getF().getValue() / HA2.getPhsBHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA2.getPhsCHar().get(5).getMag().getF().getValue() / HA2.getPhsCHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA3.getPhsAHar().get(5).getMag().getF().getValue() / HA3.getPhsAHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA3.getPhsBHar().get(5).getMag().getF().getValue() / HA3.getPhsBHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA3.getPhsCHar().get(5).getMag().getF().getValue() / HA3.getPhsCHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA4.getPhsAHar().get(5).getMag().getF().getValue() / HA4.getPhsAHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA4.getPhsBHar().get(5).getMag().getF().getValue() / HA4.getPhsBHar().get(1).getMag().getF().getValue() >
//                BlkValue || HA4.getPhsCHar().get(5).getMag().getF().getValue() / HA4.getPhsCHar().get(1).getMag().getF().getValue() >
//                BlkValue) {
//            BlkHA.getGeneral().setValue(true);
//            counter = 0;
//            countA = 0;
//            countB = 0;
//            countC = 0;
//        } else {BlkHA.getGeneral().setValue(false);}








//        if(Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue()){
//            counter += swim;
//        } else {
//            counter = 0;
//        }


//        if (BlkHA.getGeneral().getValue()) {
//            counter = 0;
//        }
//        Op.getGeneral().setValue(counter > MinOpTmms.getSetVal());

        //} else { counter = 0; }
//        if(blk.getValue() == 1){
//            counter = 0;
//        }


//        if(BlkHA.getGeneral().getValue()) {
//            counter = 0;
//        }
//        if (blk.getValue() == 1) {
//            counter = 0;
//        }

        //Op.getGeneral().setValue(counter > MinOpTmms.getSetVal());








//        blkA.getStVal().setValue(false);
//        blkB.getStVal().setValue(false);
//        blkC.getStVal().setValue(false);
//        blkGen.getStVal().setValue(false);
//
//        blkGen.getStVal().setValue(blkA.getStVal().getValue() || blkB.getStVal().getValue() || blkC.getStVal().getValue());
//
//
//        for(int i=0; i<hinputs.size(); i++){
//            HWYE hwye = hinputs.get(i);
//            if(!blkA.getStVal().getValue()) {
//                blkGen.getStVal().setValue(hwye.getPhsAHar().get(5).getMag().getF().getValue() / hwye.getPhsAHar().get(1).getMag().getF().getValue() > hBlock.getSetVal().getF().getValue());
//            }
//            if(!blkB.getStVal().getValue()) {
//                blkGen.getStVal().setValue(hwye.getPhsBHar().get(5).getMag().getF().getValue() / hwye.getPhsBHar().get(1).getMag().getF().getValue() > hBlock.getSetVal().getF().getValue());
//            }
//            if(!blkC.getStVal().getValue()) {
//                blkGen.getStVal().setValue(hwye.getPhsCHar().get(5).getMag().getF().getValue() / hwye.getPhsCHar().get(1).getMag().getF().getValue() > hBlock.getSetVal().getF().getValue());
//            }
//        }
//
//        //blkGen.getStVal().setValue(blkA.getStVal().getValue() || blkB.getStVal().getValue() || blkC.getStVal().getValue());
//        //Str.getGeneral().setValue(Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue());
//
//        //сбрасываем накопление времени срабатывания если обнаружена блокировка
//        if (blkGen.getStVal().getValue())
//            counter ++;
//        else counter = 0;
//
//        // добавим ВВ
//        if (Str.getPhsA().getValue()) counter++;
//        else counter = 0;
//        if (Str.getPhsB().getValue()) counter++;
//        else counter = 0;
//        if (Str.getPhsC().getValue()) counter++;
//        else counter = 0;
//
//
//
//        // Фиксация ВВ
//        Op.getPhsA().setValue(counter > MinOpTmms.getSetVal());
//        Op.getPhsB().setValue(counter > MinOpTmms.getSetVal());
//        Op.getPhsC().setValue(counter > MinOpTmms.getSetVal());
//        Op.getGeneral().setValue(Op.getPhsA().getValue() || Op.getPhsB().getValue() || Op.getPhsC().getValue());



    }


}
