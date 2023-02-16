package iec61850.nodes.measurements;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.filter.Filter;
import iec61850.nodes.measurements.filter.Fourier;
import iec61850.objects.measurements.DEL;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.WYE;
import iec61850.objects.samples.Attribute;
import iec61850.objects.samples.MV;
import iec61850.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MMXU extends LN {

    private MV TotW = new MV(); // полная активная мощность
    private MV TotVar = new MV(); // полная реактивная
    private MV TotVA = new MV(); // полная вольт-амперная
    private MV TotPF = new MV(); // коэффициент мощности Фи

    private WYE W = new WYE(); // трехфазная система трех активных мощностей
    private WYE VAr = new WYE(); // три реактивные мощности
    private WYE VA = new WYE(); // три вольт-амперные полные мощности
    private WYE PF = new WYE(); // три cos Фи по-фазно

    private WYE Hz = new WYE(); // по-фазная частота
    private WYE A = new WYE(); // три вектора токов
    private WYE PhV = new WYE(); // три вектора напряжений
    private WYE PPA = new WYE();
    private WYE Z = new WYE(); // полное сопротивление фазы
    private DEL PPV = new DEL(); // три вектора по системе треугольника
    private DEL Aa = new DEL();
    private DEL PPZ = new DEL(); // междуфазное сопротивление

    // входные сигналы
    private SAV instIa = new SAV();
    private SAV instIb = new SAV();
    private SAV instIc = new SAV();

    private SAV instUa = new SAV();
    private SAV instUb = new SAV();
    private SAV instUc = new SAV();


    private DEL Zi = new DEL();
    private Vector Zab = new Vector();
    private Vector Zbc = new Vector();
    private Vector Zca = new Vector();

    private float cosFiAB, cosFiBC, cosFiCA, sinFiAB, sinFiBC, sinFiCA;

    private Vector Za = new Vector();
    private Vector Zb = new Vector();
    private Vector Zc = new Vector();
    private Vector Iab = new Vector();
    private Vector Ibc = new Vector();
    private Vector Ica = new Vector();

    private Vector Uab = new Vector();
    private Vector Ubc = new Vector();
    private Vector Uca = new Vector();

    private Filter fIa = new Fourier( 1), fIb = new Fourier( 1), fIc = new Fourier( 1);
    private Filter fUa = new Fourier( 1), fUb = new Fourier( 1), fUc = new Fourier( 1);


    @Override
    public void process() {

        // действующие значения I и U
        fIa.process(instIa, A.getPhsA().getCVal());
        fIb.process(instIb, A.getPhsB().getCVal());
        fIc.process(instIc, A.getPhsC().getCVal());

        fUa.process(instUa, PhV.getPhsA().getCVal());
        fUb.process(instUb, PhV.getPhsB().getCVal());
        fUc.process(instUc, PhV.getPhsC().getCVal());

        float sa = PhV.getPhsA().getCVal().getMag().getF().getValue() * A.getPhsA().getCVal().getMag().getF().getValue();
        float sb = PhV.getPhsB().getCVal().getMag().getF().getValue() * A.getPhsB().getCVal().getMag().getF().getValue();
        float sc = PhV.getPhsC().getCVal().getMag().getF().getValue() * A.getPhsC().getCVal().getMag().getF().getValue();
        // полная мощность
        float s = sa + sb + sc;

        VA.getPhsA().getCVal().getMag().getF().setValue(sa);
        VA.getPhsB().getCVal().getMag().getF().setValue(sb);
        VA.getPhsC().getCVal().getMag().getF().setValue(sc);
        TotVA.getMag().getF().setValue(s);

        // коэффициент мощности
        float angA = (float) Math.toRadians(PhV.getPhsA().getCVal().getAng().getF().getValue() - A.getPhsA().getCVal().getAng().getF().getValue());
        float angB = (float) Math.toRadians(PhV.getPhsB().getCVal().getAng().getF().getValue() - A.getPhsB().getCVal().getAng().getF().getValue());
        float angC = (float) Math.toRadians(PhV.getPhsC().getCVal().getAng().getF().getValue() - A.getPhsC().getCVal().getAng().getF().getValue());
        // вычислим cos и sin
        float cosPhiA = (float) Math.cos(angA), sinPhiA = (float) Math.sin(angA);
        float cosPhiB = (float) Math.cos(angB), sinPhiB = (float) Math.sin(angB);
        float cosPhiC = (float) Math.cos(angC), sinPhiC = (float) Math.sin(angC);
        // средний cos/sin Фи
        float cosPhi = (cosPhiA + cosPhiB + cosPhiC) / 3;
        float sinPhi = (sinPhiA + sinPhiB + sinPhiC) / 3;
        // занесем значения
        PF.getPhsA().getCVal().getMag().getF().setValue(cosPhiA);
        PF.getPhsB().getCVal().getMag().getF().setValue(cosPhiB);
        PF.getPhsC().getCVal().getMag().getF().setValue(cosPhiC);
        // итоговый cos/sin на все присоединения
        TotPF.getMag().getF().setValue(cosPhi);

        // активная/реактивная мощности
        W.getPhsA().getCVal().getMag().getF().setValue(sa * cosPhiA);
        W.getPhsB().getCVal().getMag().getF().setValue(sb * cosPhiB);
        W.getPhsC().getCVal().getMag().getF().setValue(sc * cosPhiC);
        TotW.getMag().getF().setValue(s * cosPhi);

        VAr.getPhsA().getCVal().getMag().getF().setValue(sa * sinPhiA);
        VAr.getPhsB().getCVal().getMag().getF().setValue(sb * sinPhiB);
        VAr.getPhsC().getCVal().getMag().getF().setValue(sc * sinPhiC);
        TotVar.getMag().getF().setValue(s * sinPhi);

        // расчет полных сопротивлений фаз
        double zA = PhV.getPhsA().getCVal().getMag().getF().getValue() / A.getPhsA().getCVal().getMag().getF().getValue();
        double zB = PhV.getPhsB().getCVal().getMag().getF().getValue() / A.getPhsB().getCVal().getMag().getF().getValue();
        double zC = PhV.getPhsC().getCVal().getMag().getF().getValue() / A.getPhsC().getCVal().getMag().getF().getValue();
        // расчет углов сопротивлений фаз
        double zAph = PhV.getPhsA().getCVal().getAng().getF().getValue() - A.getPhsA().getCVal().getAng().getF().getValue();
        double zBph = PhV.getPhsB().getCVal().getAng().getF().getValue() - A.getPhsB().getCVal().getAng().getF().getValue();
        double zCph = PhV.getPhsC().getCVal().getAng().getF().getValue() - A.getPhsC().getCVal().getAng().getF().getValue();
        // задаем переменной из стандарта значение сопротивления и угол
        Z.getPhsA().getCVal().getMag().getF().setValue((float) zA);
        Z.getPhsB().getCVal().getMag().getF().setValue((float) zB);
        Z.getPhsC().getCVal().getMag().getF().setValue((float) zC);

        Z.getPhsA().getCVal().getAng().setF(new Attribute<>((float) zAph));
        Z.getPhsB().getCVal().getAng().setF(new Attribute<>((float) zBph));
        Z.getPhsC().getCVal().getAng().setF(new Attribute<>((float) zCph));

        double Ua_bx = PhV.getPhsA().getCVal().getOrtX().getF().getValue() - PhV.getPhsB().getCVal().getOrtX().getF().getValue();
        double Ub_cx = PhV.getPhsB().getCVal().getOrtX().getF().getValue() - PhV.getPhsC().getCVal().getOrtX().getF().getValue();
        double Uc_ax = PhV.getPhsC().getCVal().getOrtX().getF().getValue() - PhV.getPhsA().getCVal().getOrtX().getF().getValue();

        double Ua_by = PhV.getPhsA().getCVal().getOrtY().getF().getValue() - PhV.getPhsB().getCVal().getOrtY().getF().getValue();
        double Ub_cy = PhV.getPhsB().getCVal().getOrtY().getF().getValue() - PhV.getPhsC().getCVal().getOrtY().getF().getValue();
        double Uc_ay = PhV.getPhsC().getCVal().getOrtY().getF().getValue() - PhV.getPhsA().getCVal().getOrtY().getF().getValue();

        PPV.getPhsAB().getCVal().setValue0((float) Ua_bx, (float) Ua_by);
        PPV.getPhsBC().getCVal().setValue0((float) Ub_cx, (float) Ub_cy);
        PPV.getPhsCA().getCVal().setValue0((float) Uc_ax, (float) Uc_ay);



        double Ia_bx = A.getPhsA().getCVal().getOrtX().getF().getValue() - A.getPhsB().getCVal().getOrtX().getF().getValue();
        double Ib_cx = A.getPhsB().getCVal().getOrtX().getF().getValue() - A.getPhsC().getCVal().getOrtX().getF().getValue();
        double Ic_ax = A.getPhsC().getCVal().getOrtX().getF().getValue() - A.getPhsA().getCVal().getOrtX().getF().getValue();

        double Ia_by = A.getPhsA().getCVal().getOrtY().getF().getValue() - A.getPhsB().getCVal().getOrtY().getF().getValue();
        double Ib_cy = A.getPhsB().getCVal().getOrtY().getF().getValue() - A.getPhsC().getCVal().getOrtY().getF().getValue();
        double Ic_ay = A.getPhsC().getCVal().getOrtY().getF().getValue() - A.getPhsA().getCVal().getOrtY().getF().getValue();

        PPA.getPhsAB().getCVal().setValue0((float) Ia_bx, (float) Ia_by);
        PPA.getPhsBC().getCVal().setValue0((float) Ib_cx, (float) Ib_cy);
        PPA.getPhsCA().getCVal().setValue0((float) Ic_ax, (float) Ic_ay);

        double Ua_bAngle = calcAngle(Ua_bx, Ua_by); double Ub_cAngle = calcAngle(Ub_cx, Ub_cy); double Uc_aAngle = calcAngle(Uc_ax, Uc_ay);
        double Ia_bAngle = calcAngle(Ia_bx, Ia_by); double Ib_cAngle = calcAngle(Ib_cx, Ib_cy); double Ic_aAngle = calcAngle(Ic_ax, Ic_ay);


        PPZ.getPhsAB().getCVal().setValueD(PPV.getPhsAB().getCVal().getMag().getF().getValue() / PPA.getPhsAB().getCVal().getMag().getF().getValue(),
                PPV.getPhsAB().getCVal().getAng().getF().getValue() - PPA.getPhsAB().getCVal().getAng().getF().getValue());
        PPZ.getPhsBC().getCVal().setValueD(PPV.getPhsBC().getCVal().getMag().getF().getValue() / PPA.getPhsBC().getCVal().getMag().getF().getValue(),
                PPV.getPhsBC().getCVal().getAng().getF().getValue() - PPA.getPhsBC().getCVal().getAng().getF().getValue());
        PPZ.getPhsCA().getCVal().setValueD(PPV.getPhsCA().getCVal().getMag().getF().getValue() / PPA.getPhsCA().getCVal().getMag().getF().getValue(),
                PPV.getPhsCA().getCVal().getAng().getF().getValue() - PPA.getPhsCA().getCVal().getAng().getF().getValue());

    }
    public double calcAngle(double ortX, double ortY) {
        double angle = 0;
        if(ortX < 0) {
            angle = (float) Math.toDegrees(Math.atan(ortY / ortX) + 180);
        }
        else if(ortX > 0) {
            angle = (float) Math.toDegrees(Math.atan(ortY / ortX));
        }
//        else if(ortX == 0 && ortY > 0) {
//            angle = 90;
//        }
//        else if(ortX == 0 && ortY < 0) {
//            angle = -90;
//        }
//        else if(ortX == 0 && ortY == 0) {
//            angle = 0;
//        }
        return angle;
    }




    //        PPZ.getPhsAB().getCVal().getAng().getF().setValue((float) Za_bAngle);
//        PPZ.getPhsBC().getCVal().getAng().getF().setValue((float) Zb_cAngle);
//        PPZ.getPhsCA().getCVal().getAng().getF().setValue((float) Zc_aAngle);





//        Uab.setValue0((PhV.getPhsA().getCVal().getOrtX().getF().getValue() - PhV.getPhsB().getCVal().getOrtX().getF().getValue()),
//                (PhV.getPhsA().getCVal().getOrtY().getF().getValue() - PhV.getPhsB().getCVal().getOrtY().getF().getValue()));
//        Ubc.setValue0((PhV.getPhsB().getCVal().getOrtX().getF().getValue() - PhV.getPhsC().getCVal().getOrtX().getF().getValue()),
//                (PhV.getPhsB().getCVal().getOrtY().getF().getValue() - PhV.getPhsC().getCVal().getOrtY().getF().getValue()));
//        Uca.setValue0((PhV.getPhsC().getCVal().getOrtX().getF().getValue() - PhV.getPhsA().getCVal().getOrtX().getF().getValue()),
//                (PhV.getPhsC().getCVal().getOrtY().getF().getValue() - PhV.getPhsA().getCVal().getOrtY().getF().getValue()));
//
//        Iab.setValue0((A.getPhsA().getCVal().getOrtX().getF().getValue() - A.getPhsB().getCVal().getOrtX().getF().getValue()),
//                (A.getPhsA().getCVal().getOrtY().getF().getValue() - A.getPhsB().getCVal().getOrtY().getF().getValue()));
//        Ibc.setValue0((A.getPhsB().getCVal().getOrtX().getF().getValue() - A.getPhsC().getCVal().getOrtX().getF().getValue()),
//                (A.getPhsB().getCVal().getOrtY().getF().getValue() - A.getPhsC().getCVal().getOrtY().getF().getValue()));
//        Ica.setValue0((A.getPhsC().getCVal().getOrtX().getF().getValue() - A.getPhsA().getCVal().getOrtX().getF().getValue()),
//                (A.getPhsC().getCVal().getOrtY().getF().getValue() - A.getPhsA().getCVal().getOrtY().getF().getValue()));
//
//
//        Zab.setZe(Iab, Uab);
//        Zbc.setZe(Ibc, Ubc);
//        Zca.setZe(Ica, Uca);
//
//        Zab.setValueD(Zab.getMag().getF().getValue(), Zab.getAng().getF().getValue());
//        Zbc.setValueD(Zbc.getMag().getF().getValue(), Zbc.getAng().getF().getValue());
//        Zca.setValueD(Zca.getMag().getF().getValue(), Zca.getAng().getF().getValue());
//
//        Zi.getPhsAB().setCVal(Zab);
//        Zi.getPhsBC().setCVal(Zbc);
//        Zi.getPhsCA().setCVal(Zca);
//
//        float angAB = (float) Math.toRadians(Uab.getAng().getF().getValue() - Iab.getAng().getF().getValue());
//        float angBC = (float) Math.toRadians(Ubc.getAng().getF().getValue() - Ibc.getAng().getF().getValue());
//        float angCA = (float) Math.toRadians(Uca.getAng().getF().getValue() - Ica.getAng().getF().getValue());
//
//        cosFiAB = (float) Math.cos(angAB);
//        cosFiBC = (float) Math.cos(angBC);
//        cosFiCA = (float) Math.cos(angCA);
//
//        sinFiAB = (float) Math.sin(angAB);
//        sinFiBC = (float) Math.sin(angBC);
//        sinFiCA = (float) Math.sin(angCA);
//
//        Za.setZe(A.getPhsA().getCVal(), PhV.getPhsA().getCVal());
//        Zb.setZe(A.getPhsB().getCVal(), PhV.getPhsB().getCVal());
//        Zc.setZe(A.getPhsC().getCVal(), PhV.getPhsC().getCVal());
//
//        Z.getPhsA().setCVal(Za);
//        Z.getPhsB().setCVal(Zb);
//        Z.getPhsC().setCVal(Zc);

}
