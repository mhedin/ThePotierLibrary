package com.morgane.repository.network;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FileUtils {

    private static final ClassLoader CLASS_LOADER = FileUtils.class.getClassLoader();

    private FileUtils() {
        // static class
    }

    public static String read(final String filename) throws IOException {
        final InputStream jsonStream = CLASS_LOADER.getResourceAsStream(filename);
        final String json = IOUtils.toString(jsonStream);
        assertThat(json).isNotNull();
        return json;
    }
}
