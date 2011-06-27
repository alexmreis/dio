package com.alexmreis.dio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Mar 25, 2011
 * Time: 12:19:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DioRequestHandler implements Runnable {

    private ServerSocket serverSocket;
    private static Map<String, DioController> controllerCache = new HashMap<String, DioController>();
    private static Map<String, byte[]> staticResourceCache = new HashMap<String, byte[]>();

    public DioRequestHandler(ServerSocket socket) {
        this.serverSocket = socket;
    }

    public void run() {
        while (true) {
            try {
                Socket connection = serverSocket.accept();
                System.out.println("Received connection");
                String requestLine = readHttpRequest(connection);
                byte[] response = executeApplicationLogic(requestLine);
                writeResponse(connection, response);
                connection.close();
            } catch (Exception e) {
                System.out.println("oops!" + e.getMessage());
            }
        }

    }

    private void writeResponse(Socket connection, byte[] response) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write("HTTP/1.1 200 OK\n" +
                "Date: Fri, 31 Dec 1999 23:59:59 GMT\n" +
                "Content-Length: " + response.length +
                "\n\n");
        writer.flush();

        connection.getOutputStream().write(response);

        writer.write("\n");
        writer.close();
    }

    private byte[] executeApplicationLogic(String requestLine) throws IOException {
        DioController controller = instantiateControllerBasedOnRequest(requestLine);
        if(controller == null)
            return readStaticFile(requestLine);

        StringTokenizer tokenizer = new StringTokenizer(requestLine);
        String method = tokenizer.nextToken();
        String response = "";
        if (method.equals("GET"))
            response = controller.get(new HashMap<String, String>());
        if (method.equals("POST"))
            response = controller.post(new HashMap<String, String>());
        if (method.equals("PUT"))
            response = controller.put(new HashMap<String, String>());
        if (method.equals("DELETE"))
            response = controller.delete(new HashMap<String, String>());
        return response.getBytes();
    }

    private byte[] readStaticFile(String requestLine) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(requestLine);

        if (tokenizer.countTokens() < 2)
            throw new IllegalArgumentException("Invalid request");

        String method = tokenizer.nextToken();
        String path = tokenizer.nextToken();

        if(staticResourceCache.get(path) != null)
            return staticResourceCache.get(path);

        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream("public" + path));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int i;
        while((i = fileInput.read()) >= 0){
            output.write(i);
        }
        output.close();

        staticResourceCache.put(path, output.toByteArray());        
        return output.toByteArray();
    }

    private String readHttpRequest(Socket connection) throws IOException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String pathAndMethod = inputReader.readLine();
        return pathAndMethod;
    }


    private DioController instantiateControllerBasedOnRequest(String input) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(input);

        if (tokenizer.countTokens() < 2)
            throw new IllegalArgumentException("Invalid request");

        String method = tokenizer.nextToken();
        String path = tokenizer.nextToken();

        if(controllerCache.get(path) != null)
            return controllerCache.get(path);

        if(staticResourceCache.get(path) != null)
            return null;

        int pathLength = path.indexOf("/", 1);
        if (pathLength < 0)
            pathLength = path.length();
        String controllerName = path.substring(1, pathLength);
        String className =
                DioConfig.basePackage + "."
                        + controllerName.substring(0, 1).toUpperCase()
                        + controllerName.substring(1);

        DioController controller;
        try {
            System.out.println("Creating controller: " + className);
            controller = (DioController) Class.forName(className).newInstance();
        } catch (Exception e) {
            return null;
        }
        controllerCache.put(path, controller);
        return controller;
    }
}
