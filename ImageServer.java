import java.io.*;
import java.net.*;

public class ImageServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8005);

            while (true) {
                System.out.println("server is waiting for connection request from clients");
                Socket s = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                /* Detailed requirement below*/

                //Step one: check the picture name sent from the client, if the picture name equals "Koala.jpg", go to step two, otherwise go to step three
                String fileName = in.readLine();
                String pathName = "";
                String correctFileName = "Koala.jpg";
                if (fileName.equals(correctFileName)) {
                    //Step two, read the picture "Koala.jpg" from the local disk, and send the content back to the client. 
                    FileInputStream fileInput = new FileInputStream(pathName + correctFileName);
                    byte[] buffer = new byte[1000];
                    int read = fileInput.read(buffer);
                    while (read != -1) {
                        out.write(buffer, 0, read);
                        read = fileInput.read(buffer);
                    }
                    fileInput.close();
                } else {
                    //step three, then reply to the client with "Sorry, no such picture",
                    String errorMessage = "Sorry, no such picture";
                    out.write(errorMessage.getBytes());
                }

                //step four, close the input/output streams, close the socket.
                in.close();
                out.close();
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}