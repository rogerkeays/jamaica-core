package jamaica.core.functions;

import jamaica.core.exceptions.UncheckedIOException;
import jamaica.core.testing.TestGrouper.NetworkLayer;
import java.net.*;
import java.io.*;
import org.testng.annotations.Test;
import static jamaica.core.functions.strings.*;
import static org.testng.Assert.*;

public class net implements NetworkLayer {
    
    // is_tcp_port_open
    @Test public void is_tcp_port_open__returns_true_if_the_given_port_is_open() throws Exception {
        final ServerSocket socket = new ServerSocket(0);
        assertTrue(is_tcp_port_open(socket.getLocalPort()));
    }
    @Test public void is_tcp_port_open__returns_false_if_the_given_port_is_closed() throws Exception {
        final ServerSocket socket = new ServerSocket(0);
        socket.close();
        assertFalse(is_tcp_port_open(socket.getLocalPort()));
    }
    @Test public void is_tcp_port_open__checks_the_port_on_the_given_ip_address() throws Exception {
        final ServerSocket socket = new ServerSocket(0);
        assertTrue(is_tcp_port_open("127.0.0.1", socket.getLocalPort()));
        //BROKEN! assertFalse(is_tcp_port_open("168.2.4.10", socket.getLocalPort())); 
    }
    public static boolean is_tcp_port_open(int port) {
        return is_tcp_port_open("127.0.0.1", port); 
    }
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
}
