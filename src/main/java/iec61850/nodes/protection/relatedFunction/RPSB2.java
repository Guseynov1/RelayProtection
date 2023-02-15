//package iec61850.nodes.protection.relatedFunction;
//
//import iec61850.nodes.common.LN;
//import iec61850.nodes.measurements.control.sequence.SEQ;
//import iec61850.objects.measurements.WYE;
//import iec61850.objects.measurements.settings.ASG;
//import iec61850.objects.measurements.settings.ING;
//import iec61850.objects.measurements.settings.SPG;
//import iec61850.objects.protection.ACD;
//import iec61850.objects.protection.ACT;
//import iec61850.objects.statusControl.control.INC;
//import iec61850.objects.statusControl.status.SPS;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter @Setter
//public class RPSB2 extends LN{
//
//    private SPS BlkZn = new SPS();
//    private SEQ SeqA = new SEQ();
//    private SEQ SeqV = new SEQ();
//
//    private ACD Str = new ACD(); // пуск (колебания мощности обнаружены)
//    private ACT Op = new ACT(); // срабатывание (отключение по асинхронному режиму)
//
//    private INC OpCntRs = new INC(); // счетчик числа переключений со сбросом
//
//    private SPG ZeroEna = new SPG(); // ноль разрешен
//    private SPG NgEna = new SPG(); // контроль тока ОП разрешен
//    private SPG MaxEna = new SPG(); // контроль максимального тока разрешен
//    private ASG SwgVal = new ASG(); // изменение колебаний мощности - Delta
//    private ASG SwgRis = new ASG(); // изменение колебаний мощности - Delta R
//    private ASG SwgReact = new ASG(); // изменение колебаний мощности - Delta X
//    private ING SwgTmms = new ING(); // продолжительность колебаний мощности
//    private ING UnBlkTmms = new ING(); // время разблокировки
//    private ING MaxNumSlp = new ING(); // максимальное число фаз проскальзывания до отключения (Op, отключение по асинхронному режиму)
//    private ING EvTmms = new ING(); // время выполнения оценки (временной интервал, отключение по асинхронному режиму)
//
//    private WYE A = new WYE();
//    private WYE PhV = new WYE();
//    double dIa = 0, dIb = 0, dIc = 0;
//    double dUa = 0, dUb = 0, dUc = 0;
//    public double dt = 0.001;
//    private boolean block = false;
//    private double blockTrip = 0; // уставка блокировки, если выше - КЗ, иначе качание
//    double lastIa = 0; double lastIb = 0; double lastIc = 0; double lastUa = 0; double lastUb = 0; double lastUc = 0;
//
//    public void process() {
//        BlkZn.getStVal().setValue(blocked());
//    }
//
//    private boolean blocked(){ // функция блокировки по производной, если производная маленькая - качания, если большая - КЗ
//        dIa=Math.abs(A.getPhsA().getCVal().getMag().getF().getValue()-lastIa)/dt;
//        dIb=Math.abs(A.getPhsB().getCVal().getMag().getF().getValue()-lastIb)/dt;
//        dIc=Math.abs(A.getPhsC().getCVal().getMag().getF().getValue()-lastIc)/dt;
//        lastIa=A.getPhsA().getCVal().getMag().getF().getValue();
//        lastIb=A.getPhsB().getCVal().getMag().getF().getValue();
//        lastIc=A.getPhsC().getCVal().getMag().getF().getValue();
//        dUa=Math.abs(PhV.getPhsA().getCVal().getMag().getF().getValue()-lastUa)/dt;
//        dUb=Math.abs(PhV.getPhsB().getCVal().getMag().getF().getValue()-lastUb)/dt;
//        dUc=Math.abs(PhV.getPhsC().getCVal().getMag().getF().getValue()-lastUc)/dt;
//        lastUa=PhV.getPhsA().getCVal().getMag().getF().getValue();
//        lastUb=PhV.getPhsA().getCVal().getMag().getF().getValue();
//        lastUc=PhV.getPhsA().getCVal().getMag().getF().getValue();
//        block=((dIa>blockTrip)|(dIb>blockTrip)|(dIc>blockTrip))|((dUa>blockTrip)|(dUb>blockTrip)|(dUc>blockTrip));
//        return block;
//    }
//
//    // Функция срабатывания реле
////    private boolean operate(ASG Zust, DEL Z, ASG x0, ASG z0){
////        boolean trip;
////        trip = Math.pow((Zx-Xcentr), 2) + Math.pow((Zy-Ycentr), 2) <= Math.pow(radius, 2);
////        return trip;
////    }
//
//}
