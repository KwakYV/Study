package ru.gb.java2.chat.client;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Logging {
    private static Logging INSTANCE;

    private Logging(){
    }

    public static Logging getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Logging();
        }
        return INSTANCE;
    }

    public void log(File file, String message){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
            writer.write(message);
        } catch (IOException err){
            err.printStackTrace();
        }
    }

    public String readLastLines(File aFile, int number){
        try(RandomAccessFile file = new RandomAccessFile(aFile, "r")){
            StringBuilder historyBuilder = new StringBuilder();

            int x = 0;
            long startPosition = file.length() -1;
            int count = 0;

            while (count <= number + 1 ){
                x = file.read();
                if (x == 10){
                    count++;
                }
                startPosition = startPosition - 1;
                file.seek(startPosition);
            }

            while (startPosition != file.length()){
                historyBuilder.append((char)file.read());
                startPosition = startPosition + 1;
            }
            return new String(historyBuilder.toString().getBytes(StandardCharsets.UTF_8), "UTF-8");
        }catch(IOException err){
            err.printStackTrace();
        }
        return "";
    }
}
