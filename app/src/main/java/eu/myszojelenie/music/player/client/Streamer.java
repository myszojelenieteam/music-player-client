package eu.myszojelenie.music.player.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Streamer {

    private static final Logger log = LoggerFactory.getLogger(Streamer.class);

    public void streamFile(final InputStream stream, final DestinationTarget destination) throws IOException {
        log.info("Starting streaming");
        try(final ServerSocket socket = new ServerSocket(destination.getPort())) {
            if(socket.isBound()) {
                final Socket consumer = socket.accept();
                final OutputStream consumerStream = consumer.getOutputStream();

                final byte[] buffer = new byte[destination.getBufferSize()];
                int count;
                while((count = stream.read(buffer)) != -1) {
                    consumerStream.write(buffer, 0, count);
                }
            }
        }
    }

}
