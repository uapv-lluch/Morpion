package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class AI {
    //CHAMPS ...
    public static HashMap<double[], double[]> mapTrain;
    public static HashMap<double[], double[]> mapTest;
    public static HashMap<double[], double[]> mapDev;

    private String name;
    private int[] layers;
    private double error;
    private double samples;
    private MultiLayerPerceptron net;

    public AI(String name) {
        this.name = name;
        this.samples = samples;
        layers = new int[]{9, 5, 9};
        net = MultiLayerPerceptron.load("ai/" + name);
        if (net == null) {
            net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
        }
    }

    public static void test() {
        try {
//            AI ai = new AI("Easy");
//            AI ai = new AI("Medium");
            AI ai = new AI("Hard");
            File file = new File("data/data");
            ai.trainFromData(file, 1000000);
            double[] inputs = {
                    1, 0, 2,
                    1, 0, 0,
                    0, 0, 2
            };
            ai.play(inputs);
        } catch (Exception e) {
            System.out.println("AI.test()");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /*public static void train(MultiLayerPerceptron net, double samples, double error) {
        //TRAINING ...
        Random random = new Random();
        for (int i = 0; i < samples; i++) {
            double[] inputs = new double[] {
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3),
                random.nextInt(3)
            };
            double[] output = new double[9];
            double[] temp = Arrays.stream(inputs).filter(item -> item == 0.0).toArray();
            int choice = random.nextInt(9);
            if (temp.length == 0) {
                choice = -1;
            } else {
                while (inputs[choice] != 0) {
                    choice = random.nextInt(9);
                }
            }
            output[0] = choice;

            error += net.backPropagate(inputs, output);

            if (i % 100000 == 0) {
                System.out.println("Error at step " + i + " is " + (error / (double) i));
                System.out.println(choice);
            }
        }
        error /= samples;
        System.out.println("Error is " + error);
        System.out.println("Learning completed!");
        net.save("saves/save1");
    }*/

    public void trainFromData(File file, double samples) {
        try {
            if (file.exists()) {
                HashMap<double[], double[]> data = new HashMap<>();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    double[] in = getVector(line.split("\t")[0].split(","));
                    double[] out = getVector(line.split("\t")[1].split(","));
                    data.put(in, out);
                }
                for (int i = 0; i < samples; ++i) {
                    for (double[] input : data.keySet()) {
                        double[] output = data.get(input);
                        net.backPropagate(input, output);
                    }
                    System.out.println(i);
                }
                System.out.println("Learning complete");
                File directory = new File("ai");
                if (!directory.exists()) {
                    directory.mkdir();
                }
                net.save("ai/" + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int play(double[] inputs) {
        //TEST ...
        double[] outputs = net.forwardPropagation(inputs);

        for (double d : outputs) {
            System.out.println(d);
        }

        if (Arrays.stream(outputs).max().isPresent()) {
//            double max = Arrays.stream(outputs).max().getAsDouble();
            double max = 0;
            int indexMax = 0;
            ArrayList<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < outputs.length; ++i) {
                if (outputs[i] > max) {
                    max = outputs[i];
                    indexMax = i;
                    indexes.add(i);
                }
            }
            max = 0;
            while (inputs[indexMax] != 0) {
                for (int i = 0; i < outputs.length; ++i) {
                    if (outputs[i] > max && !indexes.contains(i)) {
                        max = outputs[i];
                        indexMax = i;
                        indexes.add(i);
                    }
                }
                max = 0;
            }
            return indexMax;
        }
        return -1;
    }

    public static double[] getVector(String[] t) {
        try {
            double[] vector = new double[t.length];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = new Double(t[i]);
            }
            return vector;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            System.out.println("Test.main()");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
