package eu.myszojelenie.music.player.client.streaming;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import eu.myszojelenie.music.player.client.utils.Consts;
import eu.myszojelenie.music.player.client.utils.DestinationTarget;
import eu.myszojelenie.music.player.client.wav.WavFileException;

public class StreamingService {

    private static final String IP = "192.168.1.???";
    private static final int PORT = 6666;
    private static final int BUFFER = 2048;

    private final Streamer streamer = new Streamer();

    public void stream(final InputStream stream) {
        try {
            Log.i(Consts.loggerTag, "Starting streaming file");
            streamer.streamFile(stream, new DestinationTarget(IP, PORT, BUFFER));
            Log.i(Consts.loggerTag, "Finished streaming file");
        }
        catch (final IOException | WavFileException e) {
            Log.w(Consts.loggerTag, "Error occurred during streaming file");
        }
    }

}