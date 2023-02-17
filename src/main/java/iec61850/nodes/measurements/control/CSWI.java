package iec61850.nodes.measurements.control;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.ACT;
import iec61850.objects.statusControl.control.DPC;
import iec61850.objects.statusControl.control.INC;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CSWI extends LN {


    private DPC Pos = new DPC(); // control - общее состояние
    private DPC PosA = new DPC(); // по-фазное управление
    private DPC PosB = new DPC();
    private DPC PosC = new DPC();

    private INC OpCntRS = new INC(); // сбрасываем счетчик операций
    private ACT OpOpn = new ACT();
    private ACT OpCls = new ACT();

    // отключить выключатель
    private ACT OpOpn1 = new ACT(), OpOpn2 = new ACT(), OpOpn3 = new ACT(), OpOpn4 = new ACT(), OpOpn5 = new ACT();
    private double count = 0;

    @Override
    public void process() {

        OpOpn.getGeneral().setValue(OpOpn1.getGeneral().getValue() ||
                OpOpn2.getGeneral().getValue() ||
                OpOpn3.getGeneral().getValue() ||
                OpOpn4.getGeneral().getValue() ||
                OpOpn5.getGeneral().getValue());
        if(OpOpn.getGeneral().getValue() && Pos.getCtlVal().getValue()){
            Pos.setStVal(DPC.State.OFF); // если сработало и выключатель включен, то присваиваем выключить
        }

//        // если есть команда на выключение, то выключить
//        if(OpOpn1.getGeneral().getValue() ||
//                OpOpn2.getGeneral().getValue() ||
//                OpOpn3.getGeneral().getValue() ||
//                OpOpn4.getGeneral().getValue() ||
//                OpOpn5.getGeneral().getValue()){
//            Pos.getCtlVal().setValue(false);
//        }



        // переводит выключатель в состояние ОТКЛ, если пришел сигнал от защиты и в данный момент выключатель в состоянии ВКЛ
//        if(OpOpn1.getGeneral().getValue().equals(true) && Pos.getCtlVal().getValue().equals(true) ||
//                OpOpn2.getGeneral().getValue().equals(true) && Pos.getCtlVal().getValue().equals(true) ||
//                OpOpn3.getGeneral().getValue().equals(true) && Pos.getCtlVal().getValue().equals(true) ||
//                OpOpn4.getGeneral().getValue().equals(true) && Pos.getCtlVal().getValue().equals(true) ||
//                OpOpn5.getGeneral().getValue().equals(true) && Pos.getCtlVal().getValue().equals(true)){
//            Pos.getCtlVal().setValue(false);
//            Pos.setStVal(DPC.State.OFF);
//        }

//        if (OpOpn.getGeneral().getValue() && Pos.getStVal().getValue() == 2 ){
//            Pos.getCtlVal().setValue(false);
//        }
//        if (!OpCls.getGeneral().getValue() && Pos.getStVal().getValue() == 1 ){
//            Pos.getCtlVal().setValue(true);
//        }



    }




//        // если у нас появился сигнал на то, что необходимо отключить выключатель И (&&) при этом 2 - выключатель включен, тогда мы делаем отключение выключателя
//        if(OpOpn.getGeneral().getValue() && Pos.getStVal().getValue() == 2) Pos.getCtlVal().setValue(false);
//
//            if(Str.getPhsA().getValue()) count = 500; else count = 0;
//            Op.getPhsA().setValue(count > OpDlTmms.getSetVal().getValue());
//
//        if(Pos.getStVal().getValue() == 2) getOpDlTmms().getSetVal().setValue(300);
//        if(Pos.getStVal().getValue() == 1) getOpDlTmms().getSetVal().setValue(400);
//
//
//
//
//        if(!Pos.getCtlVal().getValue() && Pos.getStVal().getValue() == 2);
//        if(Pos.getCtlVal().getValue() && Pos.getStVal().getValue() == 1);
//
//    }



}
