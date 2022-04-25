package eu.myszojelenie.music.player.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class StreamingService {

    private static final Logger log = LoggerFactory.getLogger(StreamingService.class);
    private static final int PORT = 7077;
    private static final int BUFFER = 2048;

    private final Streamer streamer = new Streamer();

    public void stream(final InputStream stream) {
        try {
            log.info("Starting streaming file");
            streamer.streamFile(stream, new DestinationTarget(PORT, BUFFER));
            log.info("Finished streaming file");
        }
        catch (final IOException e) {
            log.warn("Error occurred during streaming file");
        }
    }

}
