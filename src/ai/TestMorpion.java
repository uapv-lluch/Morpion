package ai;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TestMorpion {
    //CHAMPS ...
    public static HashMap<double[], double[]> mapTrain;
    public static HashMap<double[], double[]> mapTest;
    public static HashMap<double[], double[]> mapDev;

    public static void main(String[] args) {
        try {
            test();
        } catch (Exception e) {
            System.out.println("Test.main()");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void test() {
        try {
            int[] layers = new int[]{9, 5, 9};

            double error = 0.0;
            MultiLayerPerceptron net;
            double samples = 1000000000;

            net = MultiLayerPerceptron.load("saves/save1");
            if (net == null) {
                net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
                train(net, 10000000, error);
            }


            play(net);
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

    public static void play(MultiLayerPerceptron net) {
        //TEST ...
        double[] inputs = new double[]{
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                0.0,
        };
        double[] output = net.forwardPropagation(inputs);

        System.out.println(Math.round(output[0]) + " (" + output[0] + ")");
    }
}
