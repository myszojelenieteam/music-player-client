package eu.myszojelenie.music.player.client.streaming;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import eu.myszojelenie.music.player.client.serializable.SerializableAudioFormat;
import eu.myszojelenie.music.player.client.utils.Consts;
import eu.myszojelenie.music.player.client.utils.DestinationTarget;
import eu.myszojelenie.music.player.client.wav.WavFile;
import eu.myszojelenie.music.player.client.wav.WavFileException;

public class Streamer {

    ObjectMapper mapper = new ObjectMapper();

    public void streamFile(final InputStream stream, final DestinationTarget destination) throws IOException, WavFileException {
        Log.i(Consts.loggerTag, "Starting streaming");
        try(final Socket socket = new Socket(destination.getIp(), destination.getPort())) {
            if(socket.isBound()) {
                final OutputStream outputStream = socket.getOutputStream();
                final PrintWriter prPlayer = new PrintWriter(outputStream);

                try {
                    WavFile wavFile = WavFile.openWavFile(stream);
                    SerializableAudioFormat saf = new SerializableAudioFormat(wavFile);

                    // Sending audio format to server
                    String headerJson = mapper.writeValueAsString(saf);
                    prPlayer.println(convertToBase64(headerJson));
                    prPlayer.flush();

                    // Buffering music to server
                    int bitRead = 0;
                    byte[] buff = new byte[SoundBufferPackage.BUFFER_SIZE];

                    while (bitRead != -1) {
                        bitRead = stream.read(buff, 0, buff.length);
                        if (bitRead >= 0) {
                            SoundBufferPackage msg = new SoundBufferPackage(buff, bitRead);
                            String dataJson = mapper.writeValueAsString(msg);
                            prPlayer.println(convertToBase64(dataJson));
                            prPlayer.flush();
                        }

                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                } catch (Exception e) {
                    Log.e(Consts.loggerTag, "CLIENT://Could not send music package!");
                }
            }
        }
    }

    private String convertToBase64(String jsonString) {
        return Base64.getEncoder().encodeToString(jsonString.getBytes());
    }
}
