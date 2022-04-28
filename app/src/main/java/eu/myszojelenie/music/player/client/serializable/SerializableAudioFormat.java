package eu.myszojelenie.music.player.client.serializable;

import java.io.*;

import eu.myszojelenie.music.player.client.wav.WavFile;

public class SerializableAudioFormat implements Serializable {

    private static final long serialVersionUID = 1L;
    private String encoding;
    private float sampleRate;
    private int sampleSizeInBits;
    private int channels;
    private int frameSize;
    private float frameRate;
    private boolean bigEndian;

    public SerializableAudioFormat(WavFile wavFile) {
        this.encoding = "PCM_SIGNED";
        this.sampleRate = wavFile.getSampleRate();
        this.sampleSizeInBits = wavFile.getBitsPerSample();
        this.channels = wavFile.getNumChannels();
        this.frameSize = wavFile.getFrameSize();
        this.frameRate = sampleRate;    // leave it for now as it is
        this.bigEndian = false;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(encoding);
        out.writeFloat(sampleRate);
        out.writeInt(sampleSizeInBits);
        out.writeInt(channels);
        out.writeInt(frameSize);
        out.writeFloat(frameRate);
        out.writeBoolean(bigEndian);
    }
}