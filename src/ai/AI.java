package ai;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

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
            AI aiEasy = new AI("Easy");
            AI aiMedium = new AI("Medium");
            AI aiHard = new AI("Hard");
            File file = new File("data/data");
            aiEasy.trainFromData(file, 1000);
            aiMedium.trainFromData(file, 10000);
            aiHard.trainFromData(file, 100000);
            /*double[] inputs = {
                    1, 0, 2,
                    1, 0, 0,
                    0, 0, 2
            };
            aiHard.play(inputs);*/
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
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    if (file.exists()) {
                        HashMap<double[], double[]> data = new HashMap<>();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            double[] in = getArray(line.split("\t")[0].split(","));
                            double[] out = getArray(line.split("\t")[1].split(","));
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
                    return null;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Task trainFromDataWithProgressBar(File file, double samples, ProgressBar progressBar) {
        try {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    if (file.exists()) {
                        HashMap<double[], double[]> data = new HashMap<>();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            double[] in = getArray(line.split("\t")[0].split(","));
                            double[] out = getArray(line.split("\t")[1].split(","));
                            data.put(in, out);
                        }
                        for (double i = 0; i < samples; ++i) {
                            for (double[] input : data.keySet()) {
                                double[] output = data.get(input);
                                net.backPropagate(input, output);
                            }
                            progressBar.setProgress(i/samples);
                        }
                        System.out.println("Learning complete");
                        File directory = new File("ai");
                        if (!directory.exists()) {
                            directory.mkdir();
                        }
                        net.save("ai/" + name);
                    }
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
            return task;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int play(double[] inputs) {
        //TEST ...
        double[] outputs = net.forwardPropagation(inputs);

        for (double d : outputs) {
            System.out.println(d);
        }

        if (Arrays.stream(outputs).max().isPresent()) {
            double max = 0;
            int indexMax = 0;
            for (int i = 0; i < outputs.length; ++i) {
                if (inputs[i] == 0 && outputs[i] > max) {
                    max = outputs[i];
                    indexMax = i;
                }
            }
            return indexMax;
        }
        return -1;
    }

    public static double[] getArray(String[] t) {
        try {
            double[] array = new double[t.length];
            for (int i = 0; i < array.length; i++) {
                array[i] = new Double(t[i]);
            }
            return array;
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
