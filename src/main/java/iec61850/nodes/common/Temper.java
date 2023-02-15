package iec61850.nodes.common;

import java.util.ArrayList;
import java.util.List;

public class Temper {

        public static void main(String[] args) {

            int x = 40;
            int y = 50;
            int [] t1 = new int[x - 2];
            int [] t2 = new int[y - 2];
            int [] t3 = new int[y - 2];
            int [] t4 = new int[x - 2];
            double [][] matrix = new double[x][y];
            int k = 1, p = 0;
            double delta = 0.0001;
            List<Double> buff = new ArrayList<>();

            for (int i = 0; i < t1.length; i++) {
                t1[i] = 70;
                t4[i] = -20;
            }

            for (int i = 0; i < t2.length; i++) {
                t2[i] = 10;
                t3[i] = 80;
            }

            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (j != 0 && j != y - 1) {
                        matrix[0][j] = t2[j - 1];
                        matrix[x - 1][j] = t3[j - 1];
                    }
                    if (i != 0 && i != x - 1) {
                        matrix[i][0] = t1[i - 1];
                        matrix[i][y - 1] = t4[i - 1];
                    }
                }
            }


            while (true) {
                System.out.println("Iteration #" + k);
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (i >= 1 && i <= x - 2 && j >= 1 && j <= y - 2) {
                            matrix[i][j] = (matrix[i - 1][j] + matrix[i][j - 1] + matrix[i + 1][j] + matrix[i][j + 1]) / 4;
                            buff.add(matrix[i][j]);
                            p++;
                        }
                        System.out.printf("%3.1f ", matrix[i][j]);
                    }
                    System.out.println();
                }
                if (p >= 1825 && Math.abs(buff.get(p - 1) - buff.get(p - 1825)) < delta){
                    break;
                }
                System.out.println(p);
                System.out.println();
                k++;
            }
        }
    }

