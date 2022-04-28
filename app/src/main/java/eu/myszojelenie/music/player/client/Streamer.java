package eu.myszojelenie.music.player.client;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Streamer {

    public void streamFile(final InputStream stream, final DestinationTarget destination) throws IOException {
        Log.i(Consts.loggerTag, "Starting streaming");
        try(final Socket socket = new Socket(destination.getIp(), destination.getPort())) {
            if(socket.isBound()) {
                final OutputStream consumerStream = socket.getOutputStream();

                final byte[] buffer = new byte[destination.getBufferSize()];
                int count;
                while((count = stream.read(buffer)) != -1) {
                    consumerStream.write(buffer, 0, count);
                }
            }
        }
    }

}
