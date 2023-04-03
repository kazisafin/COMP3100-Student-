import java.io.*;  
import java.net.*;
import java.util.ArrayList; 

public class MyClient {  
public static void main(String args[])throws Exception{  


        String name = System.getProperty("user.name");

        ArrayList<String[]> data = new ArrayList<String[]>();

        String last_message = ""; // stores the last message.

        String[] info; //  stores the specfic information from last message

        String server_type = ""; //used for to store server type.
        
        int server_num = 0; // stores number of largest server types.

        String jobID = ""; // stores the information regarding jonID

        String[] records; // stores the record section.

        try {
            // intializing the connection.
            Socket socket = new Socket("localhost", 50000);
            DataOutputStream dataout = new DataOutputStream(socket.getOutputStream());
            BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hanshake process 
            dataout.write(("HELO\n").getBytes());
            bin.readLine();
            dataout.write(("AUTH " + name + "\n").getBytes());
            bin.readLine();

            dataout.write(("REDY\n").getBytes());
            last_message = bin.readLine(); // receives job submission information.
            info = last_message.split(" ");
            jobID = info[2];

            // Client sends GETS command to obtain server information request.
            dataout.write(("GETS Capable " + info[4] + " " + info[5] + " " + info[6] + " \n").getBytes());
            records = bin.readLine().split(" "); 
            int num_records = Integer.parseInt(records[1]);
            dataout.write(("OK\n").getBytes());

           

            int num1 =0;
            while(num1 < num_records){
                data.add(bin.readLine().split(" "));
                num1++;

          }

            // obtains information of server based on the core size.
            server_type = data.get(0)[0];
            

            int temp1 = 1;
            while( temp1<num_records){
                if (Integer.parseInt(data.get(temp1)[4]) > Integer.parseInt(data.get(temp1 - 1)[4])) {
                           server_type = data.get(temp1)[0];
                 }
                       temp1++;
                    
            
               
            }
             //Gets the count of the largest server type.
            for (String[] count : data) { 
                if (count[0].equals(server_type)) {
                    server_num++;
                }
            }

           


            dataout.write(("OK\n").getBytes());
            bin.readLine();
            int value = 0;

            while (true) {
                if (last_message.startsWith("JOBN")) {
                    dataout.write(("SCHD " + jobID + " " + server_type + " " + value + "\n").getBytes()); // iterate
                                                                                                          // through 0
                                                                                                          // to
                    
                    value += 1;
                    if (value >= server_num) {
                        value = 0;
                    }

                    bin.readLine();
                    dataout.write(("REDY\n").getBytes());
                    last_message = bin.readLine();

                    while (last_message.startsWith("JCPL")) {
                        dataout.write(("REDY\n").getBytes());
                        last_message = bin.readLine();
                    }

                    if (last_message.startsWith("NONE")) {
                        break;
                    }

                    info = last_message.split(" ");
                    jobID = info[2];
                    dataout.write(("GETS Capable " + info[4] + " " + info[5] + " " + info[6] + "\n").getBytes());
                    records = bin.readLine().split(" ");
                    num_records = Integer.parseInt(records[1]);
                    dataout.write(("OK\n").getBytes());
                    

                    int temp2 = 0;
                    while(temp2 < num_records){
                        bin.readLine();
                        temp2++;
                    }
                    
                

                    dataout.write(("OK\n").getBytes());
                    bin.readLine();
                }
            }

            dataout.write(("QUIT\n").getBytes());

            dataout.flush();

            bin.readLine();

            dataout.close();

            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


