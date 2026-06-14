/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package testport;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author USER-PC
 */
public class TestPort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CheckPort ckp = new CheckPort();
        
       // System.out.println(ckp.checkPort(5000, "LocalHost"));
        
        String host = "127.0.0.1";
        ExecutorService pool = Executors.newFixedThreadPool(50);
        int startPort = 1;
        int endPort = 1000;
        
        for(int k = startPort; k<=endPort; k++)
        {
            //if(ckp.checkPort(k, host).equals("open"))
            
                PortCheckTask task = new PortCheckTask(host, k);
                pool.execute(task);
                //System.out.println(k);
        }   
            
            pool.shutdown();
            
        try 
        {
        pool.awaitTermination(1, TimeUnit.MINUTES);
        } 
        catch (InterruptedException e) 
        {
        System.out.println("Interrupted");
        }

    System.out.println("Scan complete");
    }   
}
        


   
    

