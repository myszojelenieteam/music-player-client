package eu.myszojelenie.music.player.client.streaming;

import java.io.Serializable;

public class SoundBufferPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int BUFFER_SIZE = 32*1024;

    private byte[] arrBuff = null;
    private int numOfBytesRead = -1;
    private boolean songChange = false;

    public SoundBufferPackage(byte[] arrBuff, int numOfBytesRead, boolean songChange) {
        this.arrBuff = arrBuff;
        this.numOfBytesRead = numOfBytesRead;
        this.songChange = songChange;
    }

    public byte[] getArrBuff() {
        return arrBuff;
    }

    public int getNumOfBytesRead() {
        return numOfBytesRead;
    }

    public boolean isSongChange() {
        return songChange;
    }
}
