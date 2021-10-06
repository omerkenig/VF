package vfro.framework;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UnixCommonFunction {
	
	
	 BufferedReader fromServer = null;
	 OutputStream  toServer = null;
	 InputStream in = null;
	 Channel channel = null;
	 Session session  = null;
	
	
	public UnixCommonFunction(Session session,Channel channel,BufferedReader fromServer, OutputStream toServer) {
        this.fromServer = fromServer;
        this.toServer = toServer;
        this.channel = channel;
        this.session = session;
          
    }		

	public BufferedReader getFromServer() {
        return fromServer;
    }

    public OutputStream getToServer() {
        return toServer;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public Session getSession() {
        return session;
    }
      
	
   
	//*****************************************************************************************
	//* Name            : fCreateUnixConnection
	//* Description     : Function to create unix connection
	//* Author          : Shridhar Patil
	//* Input Params    : 
	//* Return Values   : Session - Depending on the success
	//*****************************************************************************************
	public Session fCreateUnixConnection(){
		
		String host="illnqw4130";
	    String user="dexpwrk1";
	    String password="Unix11!";
	    String command1="ls -ltr";
		
		
		JSch jsch = new JSch();
	    Session session = null;
	    
//	    String path=System.getProperty("user.dir");
//	    String privateKeyPath =path+Environment.get("PRIVATE_KEY_PATH");
	    try {
//	        jsch.addIdentity(privateKeyPath);        
//	        session = jsch.getSession(Environment.get("UNIX_LOGIN"), serverName+Environment.get("SERVER_HOST"));
	        session = jsch.getSession(user, host, 22);
	        session.setPassword(password);
//	        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
	        java.util.Properties config = new java.util.Properties(); 
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	    } catch (JSchException e) {
	        throw new RuntimeException("Failed to create Jsch Session object.", e);
	    }
	    System.out.println("Connected");
//	    Reporter.fnWriteToHtmlOutput("Unix Connection should be created","Unix Connection is created", serverName+" Connection is created","Done");	
		return session;
		
	}
	
		
	
	
	
	//*****************************************************************************************
	//* Name            : fConnectUnixBox
	//* Description     : Function to connect to unix box
	//* Author          : Shridhar Patil
	//* Input Params    : 
	//* Return Values   : Session - Depending on the success
	//*****************************************************************************************
	
	public UnixCommonFunction fConnectUnixBox(){		
		
				
		try {
			session= fCreateUnixConnection();
			session.connect();
			channel=session.openChannel("shell");
			fromServer = new BufferedReader(new InputStreamReader(channel.getInputStream()));  		
			
			toServer = channel.getOutputStream();
			channel.connect(); 
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
			
		}
		
		 return new UnixCommonFunction(session,channel,fromServer, toServer);
	}
	
	
		
		
		//*****************************************************************************************
		//* Name            : fFireCommand
		//* Description     : Function to fire command
		//* Author          : Shridhar Patil
		//* Input Params    : 
		//* Return Values   : Session - Depending on the success
		//*****************************************************************************************		

		public String  fFireFreeUnixCommand(BufferedReader fromServer,OutputStream toServer ,String cmd){
			
			//To run this method first need to run method "fConnectUnixBox"
			
			String line = ""; 
			String data = ""; 
			
			try{				
				toServer.write((cmd+"\r\n").getBytes());					
				toServer.flush();				
			
				Thread.sleep(10000);	

				toServer.write(("$$$$"+"\r\n").getBytes());
				toServer.flush();	
				Thread.sleep(3000);	
				
				StringBuilder builder = new StringBuilder(); 			
			
				String text="";
			
				while(true) {  
			
					line = fromServer.readLine();						
					System.out.println(line);	
				
					//area.setText(line+"\n");
					text = text+line+"\n";
					//area.append(line+"\n");
					
//					if(cmd.contains("ls") || cmd.contains("cd") || cmd.contains("grep")){
					if(cmd.length()>0){
								
						if(line.contains("c=")){
							data = line.toString().replace("c= ", "");
							System.out.println(data);
						}
						
						if(line.contains("-ksh")){					
						  line = data;
						  break;
						}								
					}
							
					builder.append(line).append("\n");						
				
				}  		
				
			}catch(Exception e){
				e.printStackTrace();
				System.err.println("Error ");				
			}
			
			System.out.println("DONE");
			
			 return line;
			
		}
		
		
		
		
		
		//*****************************************************************************************
		//* Name            : closeUnixConnection
		//* Description     : Function to Close unix connection
		//* Author          : Shridhar Patil
		//* Input Params    : 
		//* Return Values   : Session - Depending on the success
		//*****************************************************************************************
	
		public boolean closeUnixConnection(Session sess,Channel chann){
			try{
				chann.disconnect();
				sess.disconnect();
				System.out.println("Connection closed");
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error in closing connection");
			}
			
			return true;
			
		}
		

	
	
	
    }

