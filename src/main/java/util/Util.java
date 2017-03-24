package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Util {

    static List<URL> getResources(final String folderPath) throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (
                InputStream is = loader.getResourceAsStream(folderPath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)) {
            return br.lines()
                    .map(l -> folderPath + "/" + l)
                    .map(loader::getResource)
                    .collect(toList());
        }
    }
}
