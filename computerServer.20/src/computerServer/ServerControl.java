package computerServer;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ServerControl{
	private static char port = 80;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Server is running!!!");
		
		while(true) {
			ServerSocket serverSocket = new ServerSocket(port);
			// when client try connecting than create work socket
			Socket workingSocket = serverSocket.accept(); 
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(workingSocket.getInputStream()));
			//BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(einAS.getOutputStream()));
			System.out.println("Client connected!!!");
			String inputString;
			try{
				while((inputString = fromClient.readLine()) != null){	//check for message
				System.out.println("> "+ inputString);
				
				JSONParser parse = new JSONParser();
				Object obj = parse.parse(inputString);
				JSONObject androidMessage = (JSONObject) obj;
				
				mouseControl refreshMouse = new mouseControl();
				keyboardControl refreshKeyboard = new keyboardControl();
				
				switch((String) androidMessage.get("action")) {
					//CMD actions
					case "CMD":			//handles incoming CMD commands
						cmdCommand newcommand = new cmdCommand();
						newcommand.printCmd(newcommand.selectCommand((String) androidMessage.get("message")));
						break;
					//mouse action
					case "MOUSE_MOVE":	//handles incoming mouse coordinates
						refreshMouse.refreshXYPosition(Integer.parseInt((String) androidMessage.get("xPosition")),Integer.parseInt((String) androidMessage.get("yPosition")));
						break;	
					case "LEFT_CLICK":	//handles incoming incoming single mouse tap
						refreshMouse.leftClick();
						break;
					case "RIGHT_CLICK":
						refreshMouse.rightClick();
						break;
					case "LONG_PRESS":	//handles incoming incoming mouse long pressed tap
						refreshMouse.longPress();
						break;
					case "MOUSE_WHEEL":
						refreshMouse.mouseWheel(Integer.parseInt((String) androidMessage.get("yPosition")));
						break;	
					//keyboard action
					case "MESSAGE":		//handles incoming messages
						refreshKeyboard.writeMessage((String)androidMessage.get("message"));
						break;
					case "ESC":
						refreshKeyboard.esc();
						break;
					case "TAB":
						refreshKeyboard.tab();
						break;
					case "WIN_E":
						refreshKeyboard.winE();
						break;
					case "BACK":
						refreshKeyboard.back();
						break;
					case "WINDOWS":
						refreshKeyboard.windows();
						break;
					case "ENTER":
						refreshKeyboard.enter();
						break;
					case "LEFT_ARROW":
						refreshKeyboard.leftArrow();
						break;
					case "RIGHT_ARROW":
						refreshKeyboard.rightArrow();
						break;
					case "UP_ARROW":
						refreshKeyboard.upArrow();
						break;
					case "DOWN_ARROW":
						refreshKeyboard.downArrow();
						break;
					default:
						break;
				}
//				toClient.write("Echo from Java-server: "+ s);
//				toClient.newLine();
//				toClient.flush();
			}
		} catch(Exception e) { }
		System.out.println("Client disconnected!!!");
		fromClient.close();
		workingSocket.close();
		serverSocket.close();
		}
	}
}

/*next work:
 * - looking for shutdown							done
 * - integrate in start menu						done
 * if its working:
 * - is it working in lock screen?					done
 * - which what I want to control?					at the first time I´m looking to enclose the features by Alexa
 * - create a structure or array for commands
 * - create Android interface for ComputerControl
 * 
 * list of control commands:
 * - shutdown:	shutdown /s
 * - cancel shutdown:	shutdown /a
 * - power saving mode:		rundll32.exe powrprof.dll,SetSuspendState
 * - change to lock screen:	rundll32.exe user32.dll,LockWorkStation
 */