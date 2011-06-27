package com.alexmreis.dio;

import java.net.ServerSocket;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Mar 25, 2011
 * Time: 11:30:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class DioApplication {

    private static final int NUM_THREADS = 1;
    public static void main(String[] args) throws Exception {

        ServerSocket socket = new ServerSocket(3000);
        DioRequestHandler handler = new DioRequestHandler(socket);
        Thread serverThread;
        for(int i = 0; i < NUM_THREADS; i++){
            serverThread = new Thread(handler);
            serverThread.setDaemon(true);
            serverThread.start();
        }
        while(true)
            Thread.sleep(5000);
    }
}
