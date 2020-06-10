package tomek.szypula.tests;

import tomek.szypula.math.PeakDetector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PeakDetectionTest {


    public static void main(String[] args) throws Exception {
        DecimalFormat df = new DecimalFormat("#0.000");

//        ArrayList<Double> data = new ArrayList<Double>(Arrays.asList(1d, 1d, 1.1d, 1d, 0.9d, 1d, 1d, 1.1d, 1d, 0.9d, 1d,
//                1.1d, 1d, 1d, 0.9d, 1d, 1d, 1.1d, 1d, 1d, 1d, 1d, 1.1d, 0.9d, 1d, 1.1d, 1d, 1d, 0.9d, 1d, 1.1d, 1d, 1d,
//                1.1d, 1d, 0.8d, 0.9d, 1d, 1.2d, 0.9d, 1d, 1d, 1.1d, 1.2d, 1d, 1.5d, 1d, 3d, 2d, 5d, 3d, 2d, 1d, 1d, 1d,
//                0.9d, 1d, 1d, 3d, 2.6d, 4d, 3d, 3.2d, 2d, 1d, 1d, 0.8d, 4d, 4d, 2d, 2.5d, 1d, 1d, 1d));

        ArrayList<Double > data = new ArrayList<Double>(Arrays.asList(1d, 1d, 1d ,1d ,1d ,1d ,1d ,1d ,1d,1d ,2d ,3d ,3d, 3d,3d ,2d, 1d,1d, 1d, 1d, 2d, 3d, 3d ,3d ,3d ,3d ,3d ,3d ,2d ,1d ,1d ,2d ,3d ,3d ,3d ,3d ,3d, 2d, 1d, 1d, 1d, 1d ,1d ,1d ,1d ,1d ,1d ,1d));

        PeakDetector signalDetector = new PeakDetector();
        int lag = 10;
        double threshold = 3.5;
        double influence = 0;

        HashMap<String, List> resultsMap = signalDetector.analyzeDataForSignals(data, lag, threshold, influence);
        // print algorithm params
        System.out.println("lag: " + lag + "\t\tthreshold: " + threshold + "\t\tinfluence: " + influence);

        System.out.println("Data size: " + data.size());
        System.out.println("Signals size: " + resultsMap.get("signals").size());

        // print data
        System.out.print("Data:\t\t");
        for (double d : data) {
            System.out.print(df.format(d) + "\t");
        }
        System.out.println();

        // print signals
        System.out.print("Signals:\t");
        List<Integer> signalsList = resultsMap.get("signals");
        for (int i : signalsList) {
            System.out.print(df.format(i) + "\t");
        }
        System.out.println();

        // print filtered data
        System.out.print("Filtered Data:\t");
        List<Double> filteredDataList = resultsMap.get("filteredData");
        for (double d : filteredDataList) {
            System.out.print(df.format(d) + "\t");
        }
        System.out.println();

        // print running average
        System.out.print("Avg Filter:\t");
        List<Double> avgFilterList = resultsMap.get("avgFilter");
        for (double d : avgFilterList) {
            System.out.print(df.format(d) + "\t");
        }
        System.out.println();

        // print running std
        System.out.print("Std filter:\t");
        List<Double> stdFilterList = resultsMap.get("stdFilter");
        for (double d : stdFilterList) {
            System.out.print(df.format(d) + "\t");
        }
        System.out.println();

        System.out.println();
        for (int i = 0; i < signalsList.size(); i++) {
            if (signalsList.get(i) != 0) {
                System.out.println("Point " + i + " gave signal " + signalsList.get(i));
            }
        }
    }
}

