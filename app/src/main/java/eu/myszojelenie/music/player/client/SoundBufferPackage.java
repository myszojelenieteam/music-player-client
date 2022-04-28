package eu.myszojelenie.music.player.client;

import java.io.Serializable;

public class SoundBufferPackage implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int BUFFER_SIZE = 32*1024;

    private byte[] arrBuff = null;
    private int numOfBytesRead = -1;

    public SoundBufferPackage(byte[] arrBuff, int numOfBytesRead) {
        this.arrBuff = arrBuff;
        this.numOfBytesRead = numOfBytesRead;
    }

    public byte[] getArrBuff() {
        return arrBuff;
    }

    public int getNumOfBytesRead() {
        return numOfBytesRead;
    }
}
