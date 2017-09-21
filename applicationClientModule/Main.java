import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class Main {
	public static void main(String[] args) {
		 InputStream in = null;
	        try{
	        	//BufferedInputStream是带缓冲区的输入流，默认缓冲区大小是8M，能够减少访问磁盘的次数，提高文件读取性能
	            in = new BufferedInputStream(new FileInputStream("src/nomal_io.txt"));

	            byte [] buf = new byte[1024];
	            int bytesRead = in.read(buf);
	            while(bytesRead != -1)
	            {
	                for(int i=0;i<bytesRead;i++)
	                    System.out.print((char)buf[i]);
	                bytesRead = in.read(buf);
	            }
	        }catch (IOException e)
	        {
	            e.printStackTrace();
	        }finally{
	            try{
	                if(in != null){
	                    in.close();
	                }
	            }catch (IOException e){
	                e.printStackTrace();
	            }
	        }
	}
	
	public static void method1(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("src/nio.txt","rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);

            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);

            while(bytesRead != -1)
            {
                buf.flip();
                while(buf.hasRemaining())
                {
                    System.out.print((char)buf.get());
                }

                buf.compact();
                bytesRead = fileChannel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
	
	public static void server(){
        ServerSocket serverSocket = null;
        InputStream in = null;
        try
        {	
            serverSocket = new ServerSocket(8080);
            int recvMsgSize = 0;
            byte[] recvBuf = new byte[1024];
            while(true){
                Socket clntSocket = serverSocket.accept();
                SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
                System.out.println("Handling client at "+clientAddress);
                in = clntSocket.getInputStream();
                while((recvMsgSize=in.read(recvBuf))!=-1){
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
                    System.out.println(new String(temp));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(serverSocket!=null){
                    serverSocket.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}