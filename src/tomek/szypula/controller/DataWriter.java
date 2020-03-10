package tomek.szypula.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter {
    PrintWriter printWriter;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
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
    public void updateFile(int time, double... doubles) throws IOException {
        // StringBuilder creating the string for printf
        StringBuilder builder = new StringBuilder();
        builder.append(time);
        builder.append("\t");
        for(double item : doubles) {
            builder.append(item);
            builder.append("\t");
        }
        builder.append("\n");
        printWriter.printf(builder.toString());
    }
}
