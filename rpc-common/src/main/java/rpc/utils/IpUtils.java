package rpc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IpUtils {
    private static Logger logger = LoggerFactory.getLogger(IpUtils.class);

    public static final String IP_LAN = getIpLAN();
    public static final String IP_WAN = getIpWAN();

    public static final String HOST_NAME;

    static {
        String hostname = "localhost";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            logger.error("error to get host:", e);
        }
        HOST_NAME = hostname;
    }

    /**
     * support multi network-card, useful in linux env
     */
    private static String getIpLAN() {
        try {
            Enumeration<NetworkInterface> netInterfaces = null;
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    String ip = ips.nextElement().getHostAddress();
                    if (ip.startsWith("192.") || ip.startsWith("172.") || ip.startsWith("10."))
                        return ip;

                }
            }
        } catch (Exception e) {
            logger.error("getIpLAN error!", e);
        }
        return "127.0.0.1";
    }

    /**
     * support multi network-card, useful in linux env
     */
    private static String getIpWAN() {
        try {
            Enumeration<NetworkInterface> netInterfaces = null;
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    String ip = ips.nextElement().getHostAddress();
                    //FIXME
                    if (ip.startsWith("56."))
                        return ip;
                }
            }
        } catch (Exception e) {
            logger.error("getIpWAN error!", e);
        }
        return "127.0.0.1";
    }
}
