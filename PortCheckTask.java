/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testport;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author USER-PC
 */
public class PortCheckTask implements Runnable
{
    private String host;
    private int port;
    
    public PortCheckTask(String host, int port)
    {
        this.host = host;
        this.port = port;
    }
    @Override
    public void run()
    {
         Socket socket = new Socket(); // open port

    try {
        socket.connect(new InetSocketAddress(host, port), 100);
        System.out.println("open: " + port);
         }
    catch (SocketTimeoutException e) 
    {
        System.out.println("filtered: " + port);
    }
    catch (ConnectException e) 
    {
        System.out.println("closed: " + port);
    }
    catch (IOException e) 
    {
        System.out.println("error: " + port);
    }
    finally {
        try 
        {
            socket.close();
        }
        catch (IOException e) 
        {
            // ignore — nothing to do if close fails
        }
    }
    }
}
