package tomek.szypula.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ReadFile {

    Path filePath;
    Scanner scanner;

    public ReadFile() {
    }

    private void openFile(String fileName){
        filePath = Paths.get(String.valueOf(fileName));
        try {
            scanner = new Scanner(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(List<Double> doubleList, List<Integer> integerList, String filename){
        openFile(filename);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                integerList.add(scanner.nextInt());
            } else if (scanner.hasNextDouble()) {
                doubleList.add(scanner.nextDouble());
            } else {
                scanner.next();
            }
        }
    }
}
