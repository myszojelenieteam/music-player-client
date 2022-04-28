package eu.myszojelenie.music.player.client;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class StreamingService {

    private static final String IP = "192.168.1.?";
    private static final int PORT = 7077;
    private static final int BUFFER = 2048;

    private final Streamer streamer = new Streamer();

    public void stream(final InputStream stream) {
        try {
            Log.i(Consts.loggerTag, "Starting streaming file");
            streamer.streamFile(stream, new DestinationTarget(IP, PORT, BUFFER));
            Log.i(Consts.loggerTag, "Finished streaming file");
        }
        catch (final IOException e) {
            Log.w(Consts.loggerTag, "Error occurred during streaming file");
        }
    }

}
