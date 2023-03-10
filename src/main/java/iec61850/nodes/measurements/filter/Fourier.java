package iec61850.nodes.measurements.filter;

import iec61850.objects.measurements.Vector;
import iec61850.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Fourier extends Filter {
    public static int size = 80;
    private float k = (float) Math.sqrt(2)/size;
    private float[] sin = new float[size];
    private float[] cos = new float[size];

    private int count = 0;
    private float intX = 0, intY = 0;
    private float[] xBuf = new float[size], yBuf = new float[size];

    public Fourier(double harm) {
        for (int v = 0; v < size; v++) {
            float radians = (float) (harm * 2 * Math.PI * v / size);
            sin[v] = (float) Math.sin(radians);
            cos[v] = (float) Math.cos(radians);
        }
    }
    @Override
    public void process(SAV sav, Vector vector) {
        // к нам приходит какая-то мгновенная величина instMag, умножаем ее на sin и cos
        float x = sav.getInstMag().getF().getValue() * sin[count];
        float y = sav.getInstMag().getF().getValue() * cos[count];
        // далее необходимо данные произведения проинтегрировать, т.е. добавить в наш интеграл
        intX += (x - xBuf[count]); xBuf[count] = x;
        intY += (y - yBuf[count]); yBuf[count] = y;
        vector.setValue0(k * intX, k * intY); // setValue0 - задать вектор по орто составляющим
        if(++count > size - 1) count = 0;
    }






}
