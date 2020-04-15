package tomek.szypula.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter {
    PrintWriter printWriter;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss.");
    String fileHeader;
    String filenameSuffix;

    public DataWriter(String fileHeader, String filenameSuffix) {
        this.fileHeader = fileHeader;
        this.filenameSuffix = filenameSuffix;
        setupFile();
    }

    //Set up the formatter and the header of the file, clear file if it exists, autoflush set to true to avoid extra flushing at every update
    private void setupFile(){
        String filename = dateFormatter.format(new Date()).concat(filenameSuffix);
        try {
            printWriter = new PrintWriter(new FileWriter(filename,false),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.printf(fileHeader);
    }

    //The actual implementation of every updatefile type method
//    public void updateFile(int time, double... doubles) {
//        // StringBuilder creating the string for printf
//        StringBuilder builder = new StringBuilder();
//        builder.append(time);
//        for(double item : doubles) {
//            builder.append("\t");
//            builder.append(item);
//
//        }
//        builder.append("\n");
//        try {
//            printWriter.printf(builder.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    public void updateFile(int time, String... values) {
        // StringBuilder creating the string for printf
        StringBuilder builder = new StringBuilder();
        builder.append(time);
        for(String item : values) {
            builder.append("\t");
            builder.append(item);

        }
        builder.append("\n");
        try {
            printWriter.printf(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
