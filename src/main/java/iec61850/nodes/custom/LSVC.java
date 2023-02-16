package iec61850.nodes.custom;

import iec61850.nodes.common.LN;
import iec61850.objects.samples.SAV;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @description Node for reading Comtrade file
 */
@Getter @Setter
@NoArgsConstructor
public class LSVC extends LN {

    private int Ib = 600;

    private List<SAV> signals = new ArrayList<>();

    /** Read CFG file */
    private List<String> cfgFileLines = new ArrayList<>();

    /** Read DAT file */
    private List<String> datFileLines = new ArrayList<>();

    private List<Float> aBuffer = new ArrayList<>();

    private List<Float> bBuffer = new ArrayList<>();

    private Iterator<String> iterator;

    private int signalNumber;

    private float t;

    /** Upload Comtrade file (.cfg) */
    public void readComtrade(String cfgPath){
        cfgFileLines = readFile(cfgPath + ".cfg");
        datFileLines = readFile(cfgPath + ".dat");

        iterator = datFileLines.iterator();

        /* Extracting the number of signals */
        int analogNumber = Integer.parseInt(cfgFileLines.get(1).split(",")[1].replace("A", ""));
        int discreteNumber = Integer.parseInt(cfgFileLines.get(1).split(",")[2].replace("D", ""));
        signalNumber = analogNumber + discreteNumber;

        if (signals.size() < signalNumber) {
            IntStream.range(0, 100).forEach(i -> signals.add(new SAV()));
        }

        /* Extraction of scaling signals (for analog signals) */
        IntStream.range(2, 2 + analogNumber).forEach(i -> {
            String line = cfgFileLines.get(i);
            String[] lSplit = line.split(",");
            aBuffer.add(Float.parseFloat(lSplit[5]));
            bBuffer.add(Float.parseFloat(lSplit[6]));
        });
        System.out.printf("The waveform is loaded, the number of signals: %s, number of samples: %s %n%n", signalNumber, datFileLines.size());
    }


    @Override
    public void process() {
        if(iterator.hasNext()){
            String[] split = iterator.next().split(",");

            for(int s=0; s < signalNumber; s++){
                float value = Float.parseFloat(split[s + 2]);
                if (s < aBuffer.size()) value = value * aBuffer.get(s) + bBuffer.get(s);
                SAV sav = signals.get(s);
                sav.getInstMag().getF().setValue(value * 1000);
//                sav.getInstMag().getF().setValue((value * 1000) / Ib); - for relative units

            }

        }
    }


    /** Upload file contents */
    private static List<String> readFile(String path){
        List<String> fileEntry = new ArrayList<>();

        try {
            File file = new File(path);
            if(!file.exists()) System.err.println(path + " - The file was not found, the path was specified incorrectly");

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while(line!=null){
                fileEntry.add(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) { e.printStackTrace(); }

        return fileEntry;
    }


}
