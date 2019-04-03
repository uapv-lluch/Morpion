package ai;

import java.util.HashMap;

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
            int[] layers = new int[]{9, 5, 1};

            double error = 0.0;
            MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.1, new SigmoidalTransferFunction());
            double samples = 1000000000;

            train(net, 10000000, error);

            play(net);
        } catch (Exception e) {
            System.out.println("Test.test()");
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public static void train(MultiLayerPerceptron net, double samples, double error) {
        //TRAINING ...
        for (int i = 0; i < samples; i++) {
            double[] inputs = new double[9];
            double[] output = new double[1];



            error += net.backPropagate(inputs, output);

            if (i % 100000 == 0) System.out.println("Error at step " + i + " is " + (error / (double) i));
        }
        error /= samples;
        System.out.println("Error is " + error);
        System.out.println("Learning completed!");
    }

    public static void play(MultiLayerPerceptron net) {
        //TEST ...
        double[] inputs = new double[]{0.0, 1.0};
        double[] output = net.forwardPropagation(inputs);

        System.out.println(inputs[0] + " or " + inputs[1] + " = " + Math.round(output[0]) + " (" + output[0] + ")");
    }
}
