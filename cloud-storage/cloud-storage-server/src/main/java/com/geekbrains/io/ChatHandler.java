package com.geekbrains.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChatHandler implements Runnable {

    private static int counter = 0;
    private final String userName;
    private final Server server;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Path root;
    private Path userPath;

    public ChatHandler(Socket socket, Server server) throws Exception {
        this.server = server;
        counter++;
        userName = "User#" + counter;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        root = Path.of("root");
        if (!Files.exists(root)){
            Files.createDirectory(root);
        }

        userPath = root.resolve(userName);
        if (!Files.exists(userPath)){
            Files.createDirectory(userPath);
        }


    }

    @Override
    public void run() {
        try {
            //TODO - Read File name here and its size
            byte[] buffer = new byte[8192];
            String meta;
            while (true) {
                meta = dis.readUTF();
                String[] metaData = meta.split("@SEP@");

                long size = Long.parseLong(metaData[1]);
                Path filePath = userPath.resolve(metaData[2]);

                if (!Files.exists(filePath)){
                    Files.createFile(filePath);
                }

                try(FileOutputStream fos = new FileOutputStream(filePath.toFile())){
                    for (int i = 0; i < (size + buffer.length)/buffer.length; i++) {
                        int bytes = dis.read(buffer);
                        fos.write(buffer,0, bytes);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            System.err.println("Connection was broken");
            e.printStackTrace();
        }
    }

}
