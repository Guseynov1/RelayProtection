//package iec61850.nodes.protection.relatedFunction;
//
//import iec61850.nodes.common.LN;
//import iec61850.nodes.measurements.control.sequence.SEQ;
//import iec61850.objects.measurements.DEL;
//import iec61850.objects.measurements.WYE;
//import iec61850.objects.measurements.settings.ASG;
//import iec61850.objects.measurements.settings.ING;
//import iec61850.objects.protection.ACD;
//import iec61850.objects.statusControl.status.SPS;
//import lombok.Getter;
//import lombok.Setter;
//import java.util.Deque;
//import java.util.LinkedList;
//@Getter @Setter
///* Класс для определения колебаний мощности **/
//public class RPSB3 extends LN {
//
//    private ASG SwgRis = new ASG();// Изменение колебаний мощности - Delta R
//    private ASG SwgReact = new ASG();// Изменение колебаний мощности - Delta X
//    private SPS BlkZn = new SPS();// Блокировка коррелированной зоны PDIS
//    private ING UnBlkTmms = new ING();// Время разблокировки
//
//    private WYE Z = new WYE();// Тройка векторов
//    private DEL PPZ = new DEL();// Тройка векторов
//    private SEQ SeqA = new SEQ();
//    private ING EvTmms = new ING();
//    private double x0, y0, r, r1;
//
//    private ACD Str = new ACD();
//    private int count = 0;
//    private float samples = 10;
//    private Deque<Float> Ax = new LinkedList<>();
//    private Deque<Float> Bx = new LinkedList<>();
//    private Deque<Float> Cx = new LinkedList<>();
//    private Deque<Float> Ay = new LinkedList<>();
//    private Deque<Float> By = new LinkedList<>();
//    private Deque<Float> Cy = new LinkedList<>();
//
//    @Override
//    public void process() {
//
//        if (!BlkZn.getStVal().getValue()) {
//            if (Ax.size() < samples) {
//                Ax.addLast(PPZ.getPhsA().getCVal().getOrtX().getF().getValue());
//                Bx.addLast(PPZ.getPhsB().getCVal().getOrtX().getF().getValue());
//                Cx.addLast(PPZ.getPhsC().getCVal().getOrtX().getF().getValue());
//                Ay.addLast(PPZ.getPhsA().getCVal().getOrtY().getF().getValue());
//                By.addLast(PPZ.getPhsB().getCVal().getOrtY().getF().getValue());
//                Cy.addLast(PPZ.getPhsC().getCVal().getOrtY().getF().getValue());
//            } else {
//                Ax.addLast(PPZ.getPhsA().getCVal().getOrtX().getF().getValue());
//                Ax.removeFirst();
//                Bx.addLast(PPZ.getPhsB().getCVal().getOrtX().getF().getValue());
//                Bx.removeFirst();
//                Cx.addLast(PPZ.getPhsC().getCVal().getOrtX().getF().getValue());
//                Cx.removeFirst();
//                Ay.addLast(PPZ.getPhsA().getCVal().getOrtY().getF().getValue());
//                Ay.removeFirst();
//                By.addLast(PPZ.getPhsB().getCVal().getOrtY().getF().getValue());
//                By.removeFirst();
//                Cy.addLast(PPZ.getPhsC().getCVal().getOrtY().getF().getValue());
//                Cy.removeFirst();
//            }
//// реализация блокировки
//            if ((Math.abs((Ax.getLast() - Ax.getFirst()) / 2500) > 1) ||
//                    (Math.abs((Bx.getLast() - Bx.getFirst()) / 2500) > 1) ||
//                    (Math.abs((Cx.getLast() - Cx.getFirst()) / 2500) > 1) ||
//                    (Math.abs((Ay.getLast() - Ay.getFirst()) / 2500) > 1) ||
//                    (Math.abs((By.getLast() - By.getFirst()) / 2500) > 1) ||
//                    (Math.abs((Cy.getLast() - Cy.getFirst()) / 2500) > 1)) {
//                BlkZn.getStVal().setValue(true);
//            }
//        }
//        if (BlkZn.getStVal().getValue() && ++count >= UnBlkTmms.getSetVal()) {
//            BlkZn.getStVal().setValue(false);
//            count = 0;
//        }
//
//    }
//
//}

