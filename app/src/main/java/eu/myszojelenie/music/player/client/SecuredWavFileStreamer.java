package eu.myszojelenie.music.player.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class SecuredWavFileStreamer extends FileStreamer {

    private static final Logger log = LoggerFactory.getLogger(SecuredWavFileStreamer.class);
    private static final String DESIRED_EXTENSION = "wav";

    @Override
    public void streamFile(final File file, final DestinationTarget destination) throws IOException {
        validateFileFormat(file);
        super.streamFile(file, destination);
    }

    private void validateFileFormat(final File file) {
        final String fileExtension = getFileExtension(file);
        if(!file.exists() || !file.isFile() || !fileExtension.equals(DESIRED_EXTENSION)) {
            log.info("Provided file {} cannot be streamed", file.getName());
            throw new IllegalArgumentException("Invalid file");
        }
    }

    private String getFileExtension(final File file) {
        final String name = file.getName();
        final int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
}
