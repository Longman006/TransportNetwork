package tomek.szypula.tests;

import tomek.szypula.file.ReadFile;

import java.util.ArrayList;
import java.util.List;

public class readFromFileTest {


    public static void main(String[] args) throws Exception {

        ReadFile readFile = new ReadFile();

        String filename = "02-07-2020_19.29.03.Network";
        List<Double> doubleList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();

        readFile.readFromFile(doubleList,integerList,filename);

        System.out.println(doubleList);
        System.out.println(integerList);

    }
}
