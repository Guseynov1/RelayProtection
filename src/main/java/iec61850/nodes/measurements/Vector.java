//package iec61850.nodes.measurements;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter @Setter
//public class Vector {
//    private double[] cosFirst = new double[12]; // 4 * 3 ???? 4 - отходящих линий, 3 - фазы
//    private double[] sinFirst = new double[12]; // 4 * 3 ???? 4 - отходящих линий, 3 - фазы
//    private double[] cosSecond = new double[12]; // 4 * 3 ???? 4 - отходящих линий, 3 - фазы
//    private double[] sinSecond = new double[12]; // 4 * 3 ???? 4 - отходящих линий, 3 - фазы
//
//    public void getVectorsFirstAndSecondHarmonic(double[] x_harmonic1, double[] y_harmonic1, double[] x_harmonic2,
//                                                 double[] y_harmonic2, int numberFourie) {
//
//        for (int i = 0; i < 12; i = i + 5) { // 4 * 3 ???? 4 - отходящих линий, 3 - фазы
//            cosFirst[numberFourie + i] = x_harmonic1[i / 4]; //[AAAAA,BBBBB,CCCCC] - кос. составляющие по 1 гармонике
//            sinFirst[numberFourie + i] = y_harmonic1[i / 4];
//            cosSecond[numberFourie + i] = x_harmonic2[i / 4];
//            sinSecond[numberFourie + i] = y_harmonic2[i / 4];
//        }
//
//    }
//
//}





