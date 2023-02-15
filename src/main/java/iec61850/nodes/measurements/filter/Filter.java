package iec61850.nodes.measurements.filter;

import iec61850.objects.measurements.Vector;
import iec61850.objects.samples.SAV;

public abstract class Filter {
    public abstract void process(SAV sav, Vector vector);

}
