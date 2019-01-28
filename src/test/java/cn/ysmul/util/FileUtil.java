package cn.ysmul.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(o -> {
                sb.append(o).append("\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
