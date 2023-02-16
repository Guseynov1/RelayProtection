package iec61850.nodes.measurements;

import iec61850.nodes.common.LN;
import iec61850.nodes.measurements.filter.Fourier;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.settings.ASG;
import iec61850.objects.measurements.settings.HDEL;
import iec61850.objects.measurements.settings.HWYE;
import iec61850.objects.samples.MV;
import iec61850.objects.samples.SAV;
import iec61850.objects.statusControl.status.SPS;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Getter @Setter
public class MHAI extends LN {

    private SAV Ia, Ib, Ic;
    private List<Fourier> fIaList = new ArrayList<>();
    private List<Fourier> fIbList = new ArrayList<>();
    private List<Fourier> fIcList = new ArrayList<>();

    private MV Hz = new MV(); // основная частота
    private HWYE HPhV = new HWYE(); // Последовательность фазного напряжения гармоник или интергармоник
    private HWYE HW = new HWYE(); // Последовательность активной мощности гармоник или интергармоник
    private HWYE HVar = new HWYE(); // Последовательность реактивной мощности гармоник или интергармоник
    private HWYE HVA = new HWYE(); // Последовательность фиксируемой мощности гармоник или интергармоник
    private HDEL HPPV = new HDEL(); // Последовательность линейного напряжения гармоник или интергармоник

    private int harmNum;
    private List<Integer> harmonics = new ArrayList<>(6); // требуемый набор гармоник

    private List<HWYE> hinputs = new ArrayList<>();
    private SPS BlockA = new SPS(), BlockB = new SPS(), BlockC = new SPS();
    private ASG hBlock = new ASG();

    private boolean trip = false;
    private HWYE HA = new HWYE(); // Последовательность тока гармоник или интергармоник

    /** Входные сигналы */
    private SAV instIa = new SAV(), instIb = new SAV(), instIc = new SAV();
    private SAV instUa = new SAV(), instUb = new SAV(), instUc = new SAV();

    public MHAI(int... harmonics){ // заполним массив по ФФ, их экземплярами
        this.harmonics.clear();
        for (int harmonic : harmonics) {
            this.harmonics.add(harmonic);
        }
        IntStream.range(0, 6).forEach(i -> {
            fIaList.add(new Fourier(i));
            fIbList.add(new Fourier(i));
            fIcList.add(new Fourier(i));
//            HA.getPhsAHar().add(new Vector());
//            HA.getPhsBHar().add(new Vector());
//            HA.getPhsCHar().add(new Vector());
        });
    }

    @Override
    public void process() { // расчет гармоник
        for(int h: harmonics){ // указали какие гарм необх перечислять из нашего массива гармоник harmonics
            fIaList.get(h).process(instIa, HA.getPhsAHar().get(h));
            fIbList.get(h).process(instIb, HA.getPhsBHar().get(h));
            fIcList.get(h).process(instIc, HA.getPhsCHar().get(h));
        }
    }


}
