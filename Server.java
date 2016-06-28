import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
   

    public static void main(String[] args) throws IOException {

    	try{
        serverSocket = new ServerSocket(7891);  //Server socket

        System.out.println("Server started. Listening to the port 4445");

        while(true)
        {
        clientSocket = serverSocket.accept();
        	System.out.println("Server accepted client request");
        	new Thread(new MyClass(clientSocket)).start();
        }
    	}catch(IOException e){}
    }
}
class MyClass implements Runnable{

    Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static FileOutputStream fileOutputStream;
    private static BufferedOutputStream bufferedOutputStream;
    private static int filesize = 10000000; // filesize temporary hardcoded 
    private static int bytesRead;
    private static int current = 0;

    public MyClass(Socket s){socket = s;}

    public void run(){
        try{
        byte[] mybytearray = new byte[filesize];    //create byte array to buffer the file
        byte[] filename=new byte[256];

        inputStream = socket.getInputStream();
	 DataInputStream dip=new DataInputStream(inputStream);
       String name=dip.readUTF();
       System.out.println("name:"+name);
 	String path="/home/ubuntu/Backup/"+name;
        	   System.out.println("path:"+path);
       	fileOutputStream = new FileOutputStream(path); 	
	bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        outputStream=socket.getOutputStream();
        outputStream.write("YES".getBytes());

        System.out.println("Receiving...");

        //following lines read the input slide file byte by byte
        
        bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        do {
            bytesRead = inputStream.read(mybytearray, current, (mybytearray.length - current));
            if (bytesRead >= 0) {
                current += bytesRead;
            }
        } while (bytesRead > -1);


        bufferedOutputStream.write(mybytearray, 0, current);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        inputStream.close();
        socket.close();
        

        System.out.println("Sever recieved the file");
    }
        catch(IOException e){}
}


}
