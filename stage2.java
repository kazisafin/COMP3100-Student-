import java.net.*;
import java.io.*;

public class stage2 {
	public static void main(String[] args) throws Exception {

		Socket soc = new Socket("localhost", 50000);
		DataOutputStream dout = new DataOutputStream(soc.getOutputStream());
		BufferedReader bin = new BufferedReader(new InputStreamReader(soc.getInputStream()));

		int jcount = 0; // keep track of the number of jobs processed.
		String resServer;// to store server response

		

		String client = "HELO\n";
		dout.write(client.getBytes());
		dout.flush();
		

		resServer = bin.readLine();

		String username = System.getProperty("user.name");
		client = "AUTH " + username + "\n";

		dout.write(client.getBytes());
		dout.flush();

		resServer = bin.readLine();

		// while loop continies until the server response in none.
		// loops is responsible for processing jobs received from the server.

		while (!(resServer.contains("NONE"))) {
			client = "REDY\n";

			dout.write(client.getBytes());
			dout.flush();

			resServer = bin.readLine();

			if (resServer.contains("JOBN")) {
				String[] core = resServer.split(" ");

				// inquire about available servers with the required information.
				client = "GETS Avail " + core[4] + " " + core[5] + " " + core[6] + "\n";

				dout.write(client.getBytes());
				dout.flush();

				resServer = bin.readLine();

				client = "OK\n";

				dout.write(client.getBytes());
				dout.flush();

				// Data of available servers is is split into an array of strings.

				String[] serverdata = resServer.split(" ");
				

				

				// if the number of avilable numbers is not equal to 0
				// select a server for scheduling.

				if (Integer.parseInt(serverdata[1]) != 0) {
					int availServers = Integer.parseInt(serverdata[1]);

					String[] avilServeStrings = new String[availServers];// stores the details of available servers

					int i = 0;
					while (i < availServers) {
						resServer = bin.readLine();
						avilServeStrings[i] = resServer;
						i++;
					}

					client = "OK\n";

					dout.write(client.getBytes());
					dout.flush();

					resServer = bin.readLine();

					// avilServeStrings to store details of available servers.

					String[] first = avilServeStrings[0].split(" "); // 1st the schdueling command look at the format of the command
					
					// the first server shows up it processes the work on to that server.
					client = "SCHD " + jcount + " " + first[0] + " " + first[1] + "\n";

					dout.write(client.getBytes());
					dout.flush();

					resServer = bin.readLine();
					jcount++;
					// if the condition fails the algorith checks which serverr can handle the job and assigns it accordingly
					
				} else {
					client = "GETS Capable " + core[4] + " " + core[5] + " " + core[6] + "\n";

					dout.write(client.getBytes());
					dout.flush();

					resServer = bin.readLine();

					client = "OK\n";

					dout.write(client.getBytes());
					dout.flush();

					resServer = bin.readLine();

					String[] cs = resServer.split(" ");

					int csv = Integer.parseInt(cs[1]);

					String[] avilServeStrings = new String[csv];

					int i = 0;

					while (i < csv) {
						resServer = bin.readLine();
						avilServeStrings[i] = resServer;
						i++;
					}

					client = "OK\n";

					dout.write(client.getBytes());
					dout.flush();

					resServer = bin.readLine();

					String[] first = avilServeStrings[0].split(" "); // 1st

					client = "SCHD " + jcount + " " + first[0] + " " + first[1] + "\n";

					dout.write(client.getBytes());
					dout.flush();

					resServer = bin.readLine();
					jcount++;

				}
			}
		}
		String quit = "QUIT\n";

		dout.write(quit.getBytes());
		dout.flush();

		resServer = bin.readLine();

		dout.close();
		soc.close();
	}
}
