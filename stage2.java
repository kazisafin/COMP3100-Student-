import java.io.*;
import java.net.*;

public class stage2 {
	public static void main(String[] args) throws Exception{
		Socket s=new Socket("localhost",50000);
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader dis=new BufferedReader(new
		InputStreamReader(s.getInputStream()));
		
		String strServer;
	    //int smallest = 0;// yellow code are redudnsant 
		int count = 0;
		
		String client = "HELO\n";
		
		dout.write(client.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		System.out.println("Message = " + strServer);	
		
		String username = System.getProperty("user.name");
		client = "AUTH "+username+"\n";
		
		dout.write(client.getBytes());
		dout.flush();

		strServer = dis.readLine();
		System.out.println("Message = " + strServer);
		
		while(!(strServer.contains("NONE"))) {
			client = "REDY\n";
			
			dout.write(client.getBytes());
			dout.flush();
			
			strServer = dis.readLine(); //JOB
			System.out.println("Job = " + strServer);
			
			if (strServer.contains("JOBN")) {
				String[] core = strServer.split(" ");
				int coree = Integer.parseInt(core[4]);// redundant code 
				System.out.println("Core: " + core[4]);// no need for print statemaent 
				
				client = "GETS Avail " + core[4] + " " + core[5] + " " + core[6] + "\n";
				
				dout.write(client.getBytes());
				dout.flush();
				
				strServer = dis.readLine(); //DATA
				System.out.println("Data = " + strServer);
				
				client = "OK\n";
				
				dout.write(client.getBytes());
				dout.flush();
				
				String[] data = strServer.split(" ");
				//checking first idex in the data array then if it is no equal to 0 excute ... if is is equ; tp 0 run bf
				//if the if condition succeds it is first fit other wies is bf 
				// if if the data is not == 0 assing it to first fit otherwise best fit 
				if (Integer.parseInt(data[1]) != 0) {
					int availServers = Integer.parseInt(data[1]);
				
					String[] nRec = new String[availServers];
					// for llop to while llop 


					// for (int i = 0; i < availServers; i++) {
					// 	strServer = dis.readLine();
					// 	nRec[i] = strServer;
					// //System.out.println(nRec[i]);
					// }



					//
					
					int i =0;
					while(i < availServers){
						strServer = dis.readLine();
						nRec[i] =strServer;
						i++;
					}
				
					client = "OK\n";
				
					dout.write(client.getBytes());
					dout.flush();
				
					strServer = dis.readLine();
		
					String[] first = nRec[0].split(" "); //1st the schdueling command look at the format of the command and that what is storing
					// the first server posp up it is chucnk the work on to that server.
					client = "SCHD " + count + " " + first[0] + " " + first[1] + "\n";
				
					dout.write(client.getBytes());
					dout.flush();
		
					strServer = dis.readLine();
					System.out.println("Message = " + strServer); 
					count++;	
					//bf is algorith checks which serverr can handle the job and assigns it accordingly
				} else {
					client = "GETS Capable " + core[4] + " " + core[5] + " " + core[6] + "\n";
					
					dout.write(client.getBytes());
					dout.flush();
					
					strServer = dis.readLine(); //DATA
					System.out.println("Data = " + strServer);
					
					client = "OK\n";
					
					dout.write(client.getBytes());
					dout.flush();
					
					strServer = dis.readLine();
					
					String[] capSer = strServer.split(" ");
					
					int capServ = Integer.parseInt(capSer[1]);
					
					String[] nRec = new String[capServ];

					// for loop into while loop 
					
					// for (int i = 0; i < capServ; i++) {
					// 	strServer = dis.readLine();
					// 	nRec[i] = strServer;
					// 	//System.out.println(nRec[i]);
					// }

					int i = 0;
					
					while( i < capServ){
						strServer =dis.readLine();
						nRec[i] = strServer;
						i++;
					}

					
					client = "OK\n";
				
					dout.write(client.getBytes());
					dout.flush();
				
					strServer = dis.readLine();
		
					String[] first = nRec[0].split(" "); //1st
		
					client = "SCHD " + count + " " + first[0] + " " + first[1] + "\n";
				
					dout.write(client.getBytes());
					dout.flush();
		
					strServer = dis.readLine();
					System.out.println("Message = " + strServer); 
					count++;
					
				}
			}
		}
		String quit = "QUIT\n";
		
		dout.write(quit.getBytes());
		dout.flush();
		
		strServer = dis.readLine();
		System.out.println("Message = " + strServer);
		
		dout.close();
		s.close();
	}
}
