package iec61850.nodes.measurements.filter;

import iec61850.nodes.gui.NHMIP;
import iec61850.nodes.gui.other.NHMIPoint;
import iec61850.objects.measurements.Vector;
import iec61850.objects.samples.SAV;
import java.util.ArrayList;
import java.util.List;

public class ACHKH {

    public static void main(String[] args) {

        NHMIP nhmip = new NHMIP();
        int counter = 0;

        nhmip.drawCharacteristic("АЧХ", drawACHKH());

        while (counter < 250) {
            nhmip.process();
            counter++;
        }
    }

    private static List<NHMIPoint<Double, Double>> drawACHKH() {

        List<NHMIPoint<Double, Double>> points = new ArrayList<>();
        Filter iaF;
        Vector vector = new Vector();
        SAV sav = new SAV();


        for (double f = 0; f < 20; f += 0.02) {
            iaF = new Fourier(f);
            for (int t = 0; t < 40; t++) {
                sav.getInstMag().getF().setValue((float) (70 * Math.sin(314 * t)));
                iaF.process(sav, vector);
            }


        double mag = vector.getMag().getF().getValue();
        points.add(new NHMIPoint<>(f, mag));
        }
        return points;
    }

}


//    private static List<NHMIPoint<Double, Double>> drawCharacteristic() {
//
//        List<NHMIPoint<Double, Double>> points = new ArrayList<>();
//        Filter iF;
//        SAV sav = new SAV();
//        Vector vector = new Vector();
//        double omega = Math.PI * 2 * 50;
//        for (double f = 0; f < 5; f += 0.02) {
//            iF = new Fourier(1);
//            for(int t = 0; t < 240; t++) {
//                sav.getInstMag().getF().setValue((float) (1 * Math.sin(omega * (0.02 * t) / 80)));
//                iF.process(sav, vector);
//            }
//            double mag = vector.getMag().getF().getValue();
//            points.add(new NHMIPoint<>(f * 50, mag));
//        }
//        return points;
//    }


//}



