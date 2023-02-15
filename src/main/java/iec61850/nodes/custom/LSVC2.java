//package iec61850.nodes.custom;
//
//import iec61850.nodes.common.LN;
//import iec61850.objects.samples.SAV;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class LSVC2 extends LN {
//
//    private List<SAV> signals = new ArrayList<>();
//
//    /** Считанный файл CSV */
//    private List<String> txtFileLines = new ArrayList<>();
//
//    private Iterator<String> iterator;
//
//    public LSVC() {
//    }
//
//    /** Загрузить файл (.csv) */
//    public void readComtrade(String txtPath){
//        txtFileLines = readFile(txtPath + ".csv");
//
//        iterator = txtFileLines.iterator();
//        iterator.next(); //跳过第一行
//
//        if(signals.size() < 7) {
//            for (int i = 0; i < 100; i++) {
//                signals.add(new SAV());
//            }
//        }
//
//        System.out.printf("Осциллограмма загружена, количество сигналов: %s, количество выборок: %s %n%n", 6, txtFileLines.size());
//    }
//
//    int i = 0;
//
//    @Override
//    public void process() {
//        if(iterator.hasNext()){
//
//            String[] split = iterator.next().split(",");
//
//            if (split.length == 7) {
//                for (int s = 1; s < 6; s = s + 2) {
//                    float Mag = Float.parseFloat(split[s]);
//                    float Phs = Float.parseFloat(split[s + 1]);
//                    float Value1 = (float) Math.sin(2 * Math.PI * 50 * 1 / txtFileLines.size() * i);
//                    float Value2 = (float) Math.cos(Phs / 180 * Math.PI);
//                    float Re = Mag * Value2;
//                    float Im = Mag * Value1;
//
//                    SAV sav = signals.get(s);
//                    sav.getInstMag().getF().setValue(Mag * 1000);
//                    sav.getInstAng().getF().setValue(Phs);
//                    sav.getInstMagRe().getF().setValue(Re * 1000);
//                    sav.getInstMagIm().getF().setValue(Im * 1000);
//// System.out.println(sav.getInstMag().getF().getValue() + ";" + sav.getInstAng().getF().getValue());
//                }
//
//            }
//
//            i++;
//            if (i >= 41) i = 0;
//            if (split.length == 4) {
//                for (int s = 0; s < 3; s++) {
//                    float Mag = Float.parseFloat(split[s + 1]);
//                    SAV sav = signals.get(s);
//                    sav.getInstMag().getF().setValue(Mag * 1000);
//// System.out.println(sav.getInstMag().getF().getValue());
//                }
//            }
//            if (split.length == 6) {
//                for (int s = 0; s < 6; s += 2) {
//                    float Mag = Float.parseFloat(split[s]);
//                    SAV sav = signals.get(s);
//                    sav.getInstMag().getF().setValue(Mag);
//// System.out.println(sav.getInstMag().getF().getValue());
//                }
//            }
//
//        }
//    }
//
//
//    /** Загрузить содержимое файла */
//    private static List<String> readFile(String path){
//        List<String> fileEntry = new ArrayList<>();
//
//        try {
//            File file = new File(path);
//            if(!file.exists()) System.err.println(path + " - Файл не найден, неправильно указан путь");
//
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            String line = bufferedReader.readLine();
//            while(line!=null){
//                fileEntry.add(line);
//                line = bufferedReader.readLine();
//            }
//
//            bufferedReader.close();
//            fileReader.close();
//        } catch (IOException e) { e.printStackTrace(); }
//
//        return fileEntry;
//    }
//
//    public boolean hasNext() {
//        return iterator.hasNext();
//    }
//
//    public List<SAV> getSignals() {
//        return signals;
//    }
//}