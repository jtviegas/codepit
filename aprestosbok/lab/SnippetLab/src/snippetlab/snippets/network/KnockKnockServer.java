/*
 * KnockKnockServer.java Copyright (C) Wincor Nixdorf.
 */
package snippetlab.snippets.network;

import java.net.*;
import java.io.*;

public class KnockKnockServer 
{
    
	public static void main(String[] args) throws IOException 
	{
        ServerSocket serverSocket = null;
        String inputLine = null;
        String outputLine = null;
        
        try 
        {
            serverSocket = new ServerSocket(4444);
        } 
        catch (IOException e) 
        {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        Socket clientSocket = null;
        
        try 
        {
            clientSocket = serverSocket.accept();
        } 
        catch (IOException e) 
        {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        //writer for the client socket
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        //reader for the client socket
        BufferedReader in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
        
        //protocol implementer
        KnockKnockProtocol kkp = new KnockKnockProtocol();

        //process some input
//        outputLine = kkp.processInput(null);
//        
//        //send the response
//        out.println(outputLine);
//
//        //expect for another input
//        while ((inputLine = in.readLine()) != null) 
//        {
//             outputLine = kkp.processInput(inputLine);
//             out.println(outputLine);
//             if (outputLine.equals("Bye."))
//                break;
//        }
        
        
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}

