package iec61850.nodes.measurements.control.primary;

import iec61850.nodes.common.LN;
import iec61850.objects.statusControl.BCR;
import iec61850.objects.statusControl.control.DPC;
import iec61850.objects.statusControl.control.SPC;
import iec61850.objects.statusControl.status.INS;
import iec61850.objects.statusControl.status.SPS;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class XCBR extends LN {

    private DPC Pos = new DPC(); // положение переключателя
    private SPC BlkOpn = new SPC(); // блокировка отключения
    private SPC BlkCls = new SPC(); // блокировка включения
    private SPS Loc = new SPS(); // поведение локального контроля
    private INS OpCnt = new INS(); // счетчик операций
    private INS MaxOpCap = new INS(); // функциональные характеристики выключателя при полной нагрузке
    private BCR SumSwARs = new BCR(); // общее количество переключаемых амперов, со сбросом
    private INS CBOpCap = new INS(); // функциональные возможности выключателя
    private INS POWCap = new INS(); // возможности переключения по совпадению фазы

    @Override
    public void process() {

        // если значение положения выключателя OFF - выключаем
        if(Pos.getStVal().equals(DPC.State.OFF)) {
            Pos.getCtlVal().setValue(false);
        }

//        // если положение true, то выключатель выключен
//        if(Pos.getCtlVal().getValue() && !BlkOpn.getStVal().getValue()){
//            Pos.setStVal(DPC.State.OFF); // выключатель выключен
//        }
//        else {
//            Pos.setStVal(DPC.State.ON);
//        }



//        if(!Pos.getCtlVal().getValue() && Pos.getStVal().getValue() == 2){
//            Pos.getStVal().setValue((byte) 1);
//        }
//        if(Pos.getCtlVal().getValue() && Pos.getStVal().getValue() == 1){
//            Pos.getStVal().setValue((byte) 2);
//        }

    }


}
