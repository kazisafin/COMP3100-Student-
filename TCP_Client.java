// import java.net.Socket;
// import java.net.InetAddress;
// import java.io.BufferedReader;
// //import java.io.DataInputStream;
// //import java.io.DataOutputStream;
// //import java.util.concurrent.TimeUnit;
// import java.io.InputStreamReader;
// import java.io.DataOutputStream;

// public class TCP_Client{
//     public static void main(String[] args) {
        
//             try{
//                 //InetAddress aHost = InetAddress.getByName(args[0]);
//                 //int aPort = Integer.parseInt(args[1]);
//                // Socket s = new Socket(aHost,aPort);
//                Socket s = new Socket("127.0.0.1",50000);
//                 DataOutputStream dout = new DataOutputStream(s.getOutputStream());
//                // DataInputStream din   = new DataInputStream(s.getInputStream());
//                 BufferedReader bin = new BufferedReader(new InputStreamReader(s.getInputStream()));

//                 System.out.println("Target Ip: " + s.getInetAddress() + " Target Port: " + s.getPort());
//                 System.out.println("Local Ip: " + s.getLocalAddress() + " Local Port: " + s.getLocalPort());
//                 //try{TimeUnit.SECONDS.sleep(10);} catch(InterruptedException e){System.out.println(e);}

//                 dout.write(("HELO\n").getBytes());
//                 //System.out.println("Sent: HELO");
//                 dout.flush();
//                 System.out.println("Sent: HELO");

//                 String str = (String)bin.readLine();
//                 System.out.println("RCVD: "+str);




//                 /////
//                 //   dout.write(("AUTH\n").getBytes());
//                 //  dout.flush();
//                 //   System.out.println("Sent: HELO");

//                 //  String str = (String)bin.readLine();
//                 // System.out.println("RCVD: "+str);




//                 //dout.writeUTF("BYE");
//                 //System.out.println("Sent: BYE");

//                 //str =(String)din.readUTF();
//                 //System.out.println("RCVD: "+str);

//                // String client = "Helo\n";
//                 //sendClientMessage(client);
//                 //readServerMessage();

//                 //client ="AUTH" + username + "\n";
//                // sendClientMessage(client);
//                // readServerMessage();
//                /////
//                dout.writ(e("Auth " + System.getProperty("user.name")+ "\n").getBytes());
//                dout.flush();
//               System.out.println("SENT: AUTH" + System.getProperty("user.name"));
             
//               str = bin.readLine();
//               System.out.println("RCVD:" +str );
//               jobID = Integer.parseInt(jobInfo[2]);
              

//               if(flag == true){
//                 client =="GETS ALL\n";
//                 sendCLientMessage(client);
//                 readServerMessage();

//                 numberOfLinesOfRecords = numberOfServerDataLines(serverMessage);
//                 ServerInformation = new String[]



              




//                String[] jobInfo = str.split(" ");
//                jobID = Integer.parseInt(jobInfo[2]);



               




//                 bin.close();
//                 dout.close();
//                 s.close();


//             }
//             catch(Exception e){System.out.println(e);}
//            // try {TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){System.out.println(e);}


        
//     }


// }
import java.io.*;
import java.net.*;

public class TCP_Client{

    public static void main(String[] args){
        try{
            Socket s = new Socket("localhost", 50000);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            
            out.write(("HELO" + "\n").getBytes());

            String str =in.readLine();

            String username = System.getProperty("user.name");
            out.write(("AUTH " + username + "\n").getBytes());

            str=in.readLine();

            out.write(("REDY\n").getBytes());

            str=in.readLine();

            out.write(("QUIT\n").getBytes());

            str=in.readLine();

            in.close();
            out.close();
            s.close();
        
        }
        catch (IOException e){e.printStackTrace();System.exit(1);}

    }




}
