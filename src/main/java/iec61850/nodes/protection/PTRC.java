package iec61850.nodes.protection;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.settings.ING;
import iec61850.objects.protection.ACD;
import iec61850.objects.protection.ACT;
import iec61850.objects.statusControl.control.INC;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class PTRC extends LN {

    private ArrayList<ACT> Op = new ArrayList<>();
    private ACT Tr = new ACT();
    private ACD Str = new ACD();
    private INC OpCntRs = new INC();
    private ING TrMod = new ING();
    private ING TrPlsTmms = new ING();


    @Override
    public void process() {
        for (int i = 0; i < Op.size(); i++) {
            if (Op.get(i).getGeneral().getValue()) {
                Tr.getGeneral().setValue(true);
                break;
            } else {
                Tr.getGeneral().setValue(false);
            }

        }
    }

}
