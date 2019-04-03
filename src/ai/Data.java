package ai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Data {
    public static void saveData(HashMap<double[],double[]> hashMap) {
        String keyStr = null;
        String valueStr = null;
        try {
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File("data/data");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(writer);
            for(Map.Entry<double[],double[]> entry : hashMap.entrySet()) {
                keyStr = Arrays.stream(entry.getKey()).mapToObj(String::valueOf).collect(Collectors.joining(","));
                valueStr = Arrays.stream(entry.getValue()).mapToObj(String::valueOf).collect(Collectors.joining(","));
                bw.write(keyStr + "\t" + valueStr);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
