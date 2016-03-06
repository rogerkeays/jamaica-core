package jamaica.core.net;

import static jamaica.core.strings.is_empty.is_empty;
import jamaica.core.io.UncheckedIOException;
import jamaica.core.testing.TestGrouper.NetworkLayer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.IOException;
import org.testng.annotations.Test;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class is_tcp_port_open implements NetworkLayer {
    
    /**
     * Check if the given tcp is open on the given ip. If no IP is set, the 
     * local interface is used.
     */
    public static boolean is_tcp_port_open(String ip, int port) {
        ServerSocket socket = null;
        try {
            if (is_empty(ip)) {
                socket = new ServerSocket(port);
            } else {
                socket = new ServerSocket(port, 5, 
                        InetAddress.getByName(ip));
            }
            socket.setReuseAddress(true);
            socket.close();
            return false;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
        return true;
    }
    public static boolean is_tcp_port_open(int port) {
        return is_tcp_port_open("127.0.0.1", port); 
    }
   

    @Test
    public void test_open_tcp_port_returns_true() throws Exception {
        final ServerSocket socket = new ServerSocket(0);
        assertTrue(is_tcp_port_open(socket.getLocalPort()));
    }

    @Test
    public void test_closed_tcp_port_returns_false() throws Exception {
        final ServerSocket socket = new ServerSocket(0);
        socket.close();
        assertFalse(is_tcp_port_open(socket.getLocalPort()));
    }

    @Test
    public void test_ip_address_can_be_queried() throws Exception {
        final ServerSocket socket = new ServerSocket(0);
        assertTrue(is_tcp_port_open("127.0.0.1", socket.getLocalPort()));
    }
}
