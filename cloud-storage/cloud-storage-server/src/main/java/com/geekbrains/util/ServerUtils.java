package com.geekbrains.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerUtils {
    public static HashMap<String, List<String>> hierarchyMap(Path dir) throws IOException {
        HashMap<String, List<String>> map = new HashMap<>();
        Files.walk(dir).forEach((item) -> {
            // Check if key exists in map
            if (map.containsKey(item.getParent().getFileName().toString())) {
                List<String> values = map.get(item.getParent().getFileName().toString());
                values.add(item.getFileName().toString());
            } else {
                List<String> values = new ArrayList<>();
                values.add(item.getFileName().toString());
                map.put(item.getParent().getFileName().toString(), values);
            }
        });
        return map;
    }
}
