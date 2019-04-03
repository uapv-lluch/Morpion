package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
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

    public AI(String name, double samples) {
        this.name = name;
        this.samples = samples;
        layers = new int[] {9, 5, 9};
        net = MultiLayerPerceptron.load("saves/" + name);
        if (net == null) {
            net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
            File file = new File("saves/data");
            trainFromData(file, net, samples);
        }
    }

    public static void test() {
        try {
            int[] layers = new int[]{9, 5, 9};

            double error = 0.0;
//            MultiLayerPerceptron net;
//            double samples = 100000;

            net = MultiLayerPerceptron.load("saves/aiSave");
            if (net == null) {
                net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
//                train(net, samples, error);
                File file = new File("saves/data");
                trainFromData(file, net, samples);
            }

            double[] inputs = {
                    1, 0, 2,
                    1, 0, 0,
                    0, 0, 2
            };
            play(net, inputs);
        } catch (Exception e) {
            System.out.println("Test.test()");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void train(MultiLayerPerceptron net, double samples, double error) {
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
    }

    public static void trainFromData(File file, MultiLayerPerceptron net, double samples) {
        try {
            if (file.exists()) {
                HashMap<double[],double[]> data = new HashMap<>();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    double[] in = getVector(line.split("\t")[0].split(","));
                    double[] out = getVector(line.split("\t")[1].split(","));
                    data.put(in, out);
                }
                for (int i = 0; i < samples; ++i) {
                    for (double[] input: data.keySet()) {
                        double[] output = data.get(input);
                        net.backPropagate(input, output);
                    }
                    System.out.println(i);
                }
                System.out.println("Learning complete");
                net.save("saves/aiSave");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(MultiLayerPerceptron net, double[] inputs) {
        //TEST ...
        double[] outputs = net.forwardPropagation(inputs);

        for (double d: outputs) {
            System.out.println(d);
        }
    }

    public static double[] getVector(String[] t) {
        try {
            double[] vector = new double[t.length];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = new Double(t[i]);
            }
            return vector ;
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
