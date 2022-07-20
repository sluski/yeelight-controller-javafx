package pl.slupski.yeelight;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

public class BulbFinder extends Thread {

    private static final String MULTICAST_GROUP_ADDRESS = "239.255.255.250";
    private static final int MULTICAST_GROUP_PORT = 1982;
    private static final int SOCKET_PORT = 65074;
    private static final int SERVER_TIMEOUT = 10000; // in milliseconds
    private static final String MULTICAST_SEARCH_BULB_MSG = "M-SEARCH * HTTP/1.1\r\n" +
            "HOST:239.255.255.250:1982\r\n" +
            "ST:wifi_bulb\r\n" +
            "MX:2\r\n" +
            "MAN:\"ssdp:discover\"\r\n" +
            "\r\n";

    private DatagramSocket socket;

    @SneakyThrows
    @Override
    public void run() {
        socket = new DatagramSocket(SOCKET_PORT);
        System.out.println("Server started");
        sendMulticastSearchPacket();
        listenForResponse();
    }

    private void sendMulticastSearchPacket() throws IOException {
        InetAddress group = InetAddress.getByName(MULTICAST_GROUP_ADDRESS);
        byte[] buffer = MULTICAST_SEARCH_BULB_MSG.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, MULTICAST_GROUP_PORT);
        socket.send(packet);
    }

    private void listenForResponse() throws IOException {
        try {
            socket.setSoTimeout(SERVER_TIMEOUT);
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                BulbData.add(convertToBulb(data));
            }
        } catch (SocketTimeoutException ex) {
            socket.close();
            System.out.println("Server stopped");
        }
    }

    private BulbProps convertToBulb(String data) {
        String[] args = data.split("\n");
        String cacheControl = args[1].split(":")[1].trim();
        String location = (args[4].split(":")[2] + ":" + args[4].split(":")[3]).trim();
        location = location.substring(2, location.length());
        String id = args[6].split(":")[1].trim();
        String model = args[7].split(":")[1].trim();
        String firmwareVersion = args[8].split(":")[1].trim();
        List<String> supportedMethods = Arrays.asList(args[9].split(":")[1].trim().split(" "));
        boolean on = args[10].split(":")[1].trim().equals("off") ? false : true;
        int brightness = Integer.parseInt(args[11].split(":")[1].trim());
        int colorMode = Integer.parseInt(args[12].split(":")[1].trim());
        int ct = Integer.parseInt(args[13].split(":")[1].trim());
        int rgb = Integer.parseInt(args[14].split(":")[1].trim());
        int hue = Integer.parseInt(args[15].split(":")[1].trim());
        int saturation = Integer.parseInt(args[16].split(":")[1].trim());
        String name = args[17].split(":")[1].trim();
        return new BulbProps(cacheControl, location, id, model, firmwareVersion, supportedMethods, on, brightness, colorMode, ct, rgb, hue, saturation, name);

    }


}
