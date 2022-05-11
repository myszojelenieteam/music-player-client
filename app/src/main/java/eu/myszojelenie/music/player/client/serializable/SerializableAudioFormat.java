package eu.myszojelenie.music.player.client.serializable;


import eu.myszojelenie.music.player.client.wav.WavFile;

public class SerializableAudioFormat {

    private static final long serialVersionUID = 1L;
    private String encoding;
    private float sampleRate;
    private int sampleSizeInBits;
    private int channels;
    private int frameSize;
    private float frameRate;
    private boolean bigEndian;

    public SerializableAudioFormat() {
    }

    public SerializableAudioFormat(WavFile wavFile) {
        this.encoding = "PCM_SIGNED";
        this.sampleRate = wavFile.getSampleRate();
        this.sampleSizeInBits = wavFile.getBitsPerSample();
        this.channels = wavFile.getNumChannels();
        this.frameSize = wavFile.getFrameSize();
        this.frameRate = sampleRate;    // leave it for now as it is
        this.bigEndian = false;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEncoding() {
        return encoding;
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public int getSampleSizeInBits() {
        return sampleSizeInBits;
    }

    public int getChannels() {
        return channels;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public boolean isBigEndian() {
        return bigEndian;
    }
}