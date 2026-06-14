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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER-PC
 */
public class CheckPort 
{
  public String checkPort(int port, String host) 
  {
    Socket socket = new Socket(); // open port

    try {
        socket.connect(new InetSocketAddress(host, port), 100);
        return "open";
         }
    catch (SocketTimeoutException e) 
    {
        return "filtered";
    }
    catch (ConnectException e) 
    {
        return "closed";
    }
    catch (IOException e) 
    {
        return "error";
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
