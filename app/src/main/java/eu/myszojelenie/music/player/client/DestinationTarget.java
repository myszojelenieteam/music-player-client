package eu.myszojelenie.music.player.client;

public class DestinationTarget {

    private final String ip;
    private final int port;
    private final int bufferSize;

    public DestinationTarget(String ip, int port, int bufferSize) {
        this.ip = ip;
        this.port = port;
        this.bufferSize = bufferSize;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
