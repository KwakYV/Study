package ru.gb.java2.chat.client;

import java.io.*;

public class Logging {
    public static final int NEW_LINE_CHAR = 10;
    private static Logging INSTANCE;
    private static BufferedWriter writer;

    private Logging(File file){
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logging getInstance(File file){
        if (INSTANCE == null){
            INSTANCE = new Logging(file);
        }
        return INSTANCE;
    }


    public void log(String message){
        try{
            writer.write(message);
        } catch (IOException err){
            err.printStackTrace();
        }
    }

    public String readLastLines(File aFile, int number){
        try(RandomAccessFile file = new RandomAccessFile(aFile, "r")){

            int x = 0;
            long startPosition = file.length() -1;
            int count = 0;

            while (count <= number + 1 ){
                x = file.read();
                if (x == NEW_LINE_CHAR){
                    count++;
                }
                startPosition = startPosition - 1;
                file.seek(startPosition);
            }

            long bytesLength = file.length() - startPosition;
            byte[] history = new byte[(int) bytesLength];
            file.read(history);
            return new String(history, "UTF-8");

        }catch(IOException err){
            err.printStackTrace();
        }
        return "";
    }
}
