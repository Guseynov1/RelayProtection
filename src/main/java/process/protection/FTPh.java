package process.protection;

public class FTPh {
    
    /** Быстрое преобразование Фурье (общая версия, без оптимизации)
     * @param inputReal
     *                массив длины n, вещественная часть
     * @param inputImag
     *                массив длины n, мнимая часть
     * @param DIRECT
     *                TRUE = прямое преобразование, False = обратное
     * @return новый массив длиной 2n
     */

public static double[] fft(final double[] inputReal, double[] inputImag, boolean DIRECT){

    // n is the dimension of the problem
    // - nu is its logarithm in base e
    int n = inputReal.length;

    // If n is a power of 2, then ld is an integer (_without_ decimals)
    double ld = Math.log(n) / Math.log(2.0);

    // здесь проверяется, не является ли n степенью 2. Если в ld существуют десятичные дроби, я выхожу из функции, возвращая null
    if (((int) ld) - ld != 0){
        System.out.println("Число элементов не является степенью 2");
        return null;
    }

    // объявление и инициализация переменных ld должно быть целым числом, на самом деле, чтобы я не потерял никакой инфы при приведении
    int nu = (int) ld;
    int n2 = n / 2;
    int nu1 = nu - 1;
    double[] xReal = new double[n];
    double[] xImag = new double[n];
    double tReal, tImag, p, arg, c, s;

    // здесь я проверяю, собираюсь ли я выполнить прямое или обратное преобразование

    double constant;
    if (DIRECT)
        constant = -2 * Math.PI;
    else
        constant = 2 * Math.PI;


    // Я не хочу перезаписывать входные массивы, поэтому здесь я их копирую. Этот выбор добавляет \Theta(2n) к сложности.
    for (int i = 0; i < n; i++){
        xReal[i] = inputReal[i];
        xImag[i] = inputImag[i];
    }

    // Первая фаза - расчет
    int k = 0;
    for (int l = 0; l <= nu; l++){
        while (k < n){
            for (int i = 1; i <= n2; i++){
                p = bitreverseReference(k >> nu1, nu);
                // прямое или обратное FFT
                arg = constant * p / n;
                c = Math.cos(arg);
                s = Math.sin(arg);
                tReal = xReal[k + n2] * c + xImag[k + n2] * s;
                tImag = xImag[k + n2] * c - xReal[k + n2] * s;
                xReal[k + n2] = xReal[k] - tReal;
                xImag[k + n2] = xImag[k] - tImag;
                xReal[k] += tReal;
                xImag[k] += tImag;
                k++;
            }
            k += n2;
        }
        k = 0;
        nu1--;
        n2 /= 2;
    }

    // Вторая фаза - рекомбинация
    k = 0;
    int r;
    while (k < n){
        r = bitreverseReference(k, nu);
        if (r > k){
            tReal = xReal[k];
            tImag = xImag[k];
            xReal[k] = xReal[r];
            xImag[k] = xImag[r];
            xReal[r] = tReal;
            xImag[r] = tImag;
        }
        k++;
    }

    // Смешаем xReal and xImag, чтобы получить массив (да, это можно было бы сделать и в более ранних частях кода, но здесь это для читабельности).
    double[] newArray = new double[xReal.length * 2];
    double radice = 1 / Math.sqrt(n);
    for (int i = 0; i < newArray.length; i += 2){
        int i2 = i / 2;
        // Я использовал программу Mathematica Стивена Вольфрама в качестве справочника, поэтому собираюсь нормализовать вывод во время копирования элементов.
        newArray[i] = xReal[i2] * radice;
        newArray[i + 1] = xImag[i2] * radice;
    }
    return newArray;
}

/** функция реверса опорных битов **/

private static int bitreverseReference(int j, int nu){
    int j2;
    int j1 = j;
    int k = 0;
    for (int i = 1; i <= nu; i++){
        j2 = j1 / 2;
        k = 2 * k + j1 - 2 * j2;
        j1 = j2;
    }
    return k;
}
}
