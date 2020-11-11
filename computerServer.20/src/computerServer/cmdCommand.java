package computerServer;

import java.io.PrintWriter;

public class cmdCommand {	

	public String selectCommand(String command) {
		switch(command) {
			case "shutdown":
				return "shutdown /s /t 10";
			case "lockScreen":
				return "Rundll32.exe user32.dll,LockWorkStation";
			case "powerSavingMode":
				return "rundll32.exe powrprof.dll,SetSuspendState";
			default:
				break;
		}
		return command;
	}
	
	public void printCmd(String invokatedCommand) {
		String[] command =
	    {
	        "cmd",
	    };
	    Process p;
	    
		try {
			p = Runtime.getRuntime().exec(command);
		        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
	                new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
	                PrintWriter stdin = new PrintWriter(p.getOutputStream());
	                stdin.println(invokatedCommand);
	                stdin.close();
	                p.waitFor();
	    } catch (Exception e) 
		{
	 		e.printStackTrace();
		}
	}	
}