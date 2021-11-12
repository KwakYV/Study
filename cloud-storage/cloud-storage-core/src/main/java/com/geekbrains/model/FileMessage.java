package com.geekbrains.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.nio.ByteBuffer;

@Data
@AllArgsConstructor
public class FileMessage extends AbstractMessage implements Serializable {
    private String fileName;
    private String filePath;
    private Long fileSize;
    private byte[] bytes;
}
