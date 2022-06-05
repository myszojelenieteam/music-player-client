package eu.myszojelenie.music.player.client.streaming;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import eu.myszojelenie.music.player.client.utils.Consts;
import eu.myszojelenie.music.player.client.utils.DestinationTarget;
import eu.myszojelenie.music.player.client.utils.Listener;
import eu.myszojelenie.music.player.client.utils.NotificationManager;
import eu.myszojelenie.music.player.client.utils.NotificationType;
import eu.myszojelenie.music.player.client.wav.WavFileException;

public class StreamingService {

    private static final int BUFFER = 2048;

    private String ip;
    private int port;
    private InputStream toStream;

    private final Streamer streamer = new Streamer();

    private final NotificationManager manager = new NotificationManager();

    public void registerListener(Listener listener) {
        this.manager.registerListener(listener);
    }

    public void startStreaming() {
        try {
            Log.i(Consts.loggerTag, "Starting streaming file");
            streamer.streamFile(toStream, new DestinationTarget(ip, port, BUFFER));
            Log.i(Consts.loggerTag, "Finished streaming file");
        } catch (WavFileException | IOException e) {
            this.manager.notify(NotificationType.CONNECTION_ERROR, null);
        }
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setToStream(InputStream toStream) {
        this.toStream = toStream;
    }
}
