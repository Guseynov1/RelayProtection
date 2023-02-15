package iec61850.nodes.measurements.filter;

import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.NHMIP;
import iec61850.nodes.gui.other.NHMIPoint;
import iec61850.objects.measurements.Vector;
import iec61850.objects.samples.SAV;
import java.util.ArrayList;
import java.util.List;

public class frequencyResponse {

    public static void main(String[] args) {

        NHMIP nhmip = new NHMIP();
        LSVC lsvc = new LSVC();

        lsvc.readComtrade("C:\\Users\\Хамзат\\IdeaProjects\\Programming2022\\ТНЗНП\\Опыты\\KZ1");

        nhmip.drawCharacteristic("АЧХ", drawCharacteristic());

        while (lsvc.getIterator().hasNext()) {
            lsvc.process();
            nhmip.process();
        }
    }


    private static List<NHMIPoint<Double, Double>> drawCharacteristic() {

        List<NHMIPoint<Double, Double>> characteristicPoints = new ArrayList<>();
        Filter iF = new Fourier(1);
        SAV sav = new SAV();

        Vector vector = new Vector();
        for (double f = 0; f < 251; f += 0.01) {
            double omega = 2 * Math.PI * f;
//            double k = 0.1;
//            double T = 3;
//            double b = k / (Math.sqrt(Math.pow(T, 2) * Math.pow(omega, 2) + 1));
            for (int t = 0; t < 240; t++) {
                sav.getInstMag().getF().setValue((float) (1000 * Math.sin(omega * (0.02 * t) / 80)));
                iF.process(sav, vector);
            }
            double mag = vector.getMag().getF().getValue();
            characteristicPoints.add(new NHMIPoint<>(f, mag));
        }
        return characteristicPoints;
    }
    
}


















