package com.geekbrains.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;


// ls -> список файлов в текущей папке +
// cat file -> вывести на экран содержание файла +
// cd path -> перейти в папку
// touch file -> создать пустой файл
public class NioServer {
    private final Path root;
    private Path currentPath;
    private ServerSocketChannel server;
    private Selector selector;
    private ByteBuffer buffer;


    public NioServer() throws Exception {
        buffer = ByteBuffer.allocate(256);
        server = ServerSocketChannel.open(); // accept -> SocketChannel
        server.bind(new InetSocketAddress(8189));
        selector = Selector.open();
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        root = Path.of("root");
        if (!Files.exists(root)){
            Files.createDirectory(root);
        }

        currentPath = Path.of("root");

        while (server.isOpen()) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    handleAccept(key);
                }
                if (key.isReadable()) {
                    handleRead(key);
                }
                iterator.remove();
            }
        }
    }

    private void handleRead(SelectionKey key) throws Exception {

        SocketChannel channel = (SocketChannel) key.channel();

        StringBuilder sb = new StringBuilder();

        while (true) {
            int read = channel.read(buffer);
            if (read == -1) {
                channel.close();
                return;
            }
            if (read == 0) {
                break;
            }
            buffer.flip();
            while (buffer.hasRemaining()) {
                sb.append((char) buffer.get());
            }
            buffer.clear();
        }

        if ("ls".equals(sb.toString().trim())){
            try (Stream<Path> stream = Files.list(currentPath)) {
                stream
                        .forEach(s -> {
                            try {
                                StringBuilder res = new StringBuilder();
                                if (Files.isDirectory(s)){
                                    res.append("dir").append("-- ").append(s.getFileName().toString()).append("\r\n");
                                } else {
                                    res.append("file").append("-- ").append(s.getFileName().toString()).append("\r\n");
                                }
                                channel.write(ByteBuffer.wrap(res.toString().getBytes(StandardCharsets.UTF_8)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }

        } else if ("cat".equals(sb.substring(0,3))){
            System.out.println("show contents of the file");
            Path filePath = currentPath.resolve(sb.substring(3).trim());
            if (Files.isDirectory(filePath) || !Files.exists(filePath)){
                channel.write(ByteBuffer.wrap("Wrong File name. Check if file exists or is not directory\r\n".getBytes(StandardCharsets.UTF_8)));
            }else{
                channel.write(ByteBuffer.wrap(Files.readAllBytes(filePath)));
                channel.write(ByteBuffer.wrap("\r\n".getBytes(StandardCharsets.UTF_8)));
            }

        } else if ("cd".equals(sb.substring(0,2))){
            System.out.println("Change root directory");
            Path changePath = currentPath.resolve(sb.substring(2).trim());
            if (!Files.exists(changePath) || !Files.isDirectory(changePath)){
                channel.write(ByteBuffer.wrap("Wrong dir name. Check if directory exists or is not file\r\n".getBytes(StandardCharsets.UTF_8)));
            } else {
                currentPath = changePath;
            }
        } else if ("touch".equals(sb.substring(0,5))){
            System.out.println("Create empty file");
            Path emptyPath = currentPath.resolve(sb.substring(5).trim());
            if (Files.exists(emptyPath)){
                channel.write(ByteBuffer.wrap("File already exists\r\n".getBytes(StandardCharsets.UTF_8)));
            } else {
                Files.createFile(emptyPath);
            }
        } else {
            String result = "[From server]: wrong command!!! Try ls, cat, cd, touch";
            channel.write(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8)));
        }

    }

    private void handleAccept(SelectionKey key) throws Exception {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, "Hello world!");
    }


    public static void main(String[] args) throws Exception {
        new NioServer();
    }
}
