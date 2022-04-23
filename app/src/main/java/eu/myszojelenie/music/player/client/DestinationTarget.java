package eu.myszojelenie.music.player.client;

public class DestinationTarget {

    private final int port;
    private final int bufferSize;

    public DestinationTarget(int port, int bufferSize) {
        this.port = port;
        this.bufferSize = bufferSize;
    }

    public int getPort() {
        return port;
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
