package iec61850.nodes.common;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter @Setter
public class SOR {

    // TODO: 22.06.2022 - successive over-relaxation (SOR)

    static boolean check = true;

    public static void main(String[] args) {

        final int x = 90;
        final int y = 20;
        int[] T1 = new int[x - 2];
        int[] T2 = new int[y - 2];
        int[] T3 = new int[y - 2];
        int[] T4 = new int[x - 2];
        float[][] matrx = new float[x][y];
        float k = 0f; // 0.5...2
        float d = 0;
        float delta = 0.0001f;

        Arrays.fill(T1, 170);
        Arrays.fill(T2, 350);
        Arrays.fill(T3, 50);
        Arrays.fill(T4, 0);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (j != 0 && j != y - 1) {
                    matrx[0][j] = T2[j - 1];
                    matrx[x - 1][j] = T3[j - 1];
                }
                if (i != 0 && i != x - 1) {
                    matrx[i][0] = T1[i - 1];
                    matrx[i][y - 1] = T4[i - 1];
                }
            }
        }
        while (check) {
            float[][] oldMatrx = matrx.clone();

            System.out.println("Iteration #" + d);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i >= 1 && i <= x - 2 && j >= 1 && j <= y - 2) {
                        float a = (matrx[i - 1][j] + matrx[i][j - 1] + matrx[i + 1][j] + matrx[i][j + 1]) / 4;
                        float b = Math.abs(a - matrx[i][j]) * k;
                        matrx[i][j] = a + b;
                        d++;
                    }

                    System.out.printf("%3.20f ", matrx[i][j]);
                }

                System.out.println();
            }

            outerloop:
            {
                check = false;
                for (int i = 1; i < x - 1; i++) {
                    for (int j = 1; j < y - 1; j++) {
                        float difference = Math.abs(matrx[i][j] - oldMatrx[i][j]);
                        if (difference > delta) {
                            check = true;
                            break outerloop;
                        }
                    }
                }
            }

        }
        System.out.println(d);
        System.out.println();
    }
}




















