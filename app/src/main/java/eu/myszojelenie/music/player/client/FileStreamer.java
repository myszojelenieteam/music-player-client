package eu.myszojelenie.music.player.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileStreamer {

    private static final Logger log = LoggerFactory.getLogger(FileStreamer.class);

    public void streamFile(final File file, final DestinationTarget destination) throws IOException {
        log.info("Starting streaming file {}", file.getName());
        try(final ServerSocket socket = new ServerSocket(destination.getPort())) {
            final FileInputStream fileStream = new FileInputStream(file);
            if(socket.isBound()) {
                final Socket consumer = socket.accept();
                final OutputStream consumerStream = consumer.getOutputStream();

                final byte[] buffer = new byte[destination.getBufferSize()];
                int count;
                while((count = fileStream.read(buffer)) != -1) {
                    consumerStream.write(buffer, 0, count);
                }
            }
        }
    }

}
