/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

// this class to recieve message from the tcp client

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import static Interface.TCP_SERVER.Server_Online_Users;
import static Interface.TCP_SERVER.Server_Status ;


/**
 *
 * @author Administrator
 */
public class Server_tcp extends Thread{
    int Server_port ;
    ServerSocket Server_S ;
    String Message_recieved ; 
    String online_Users_message;
    ArrayList<Socket> Connection_Acc = new ArrayList<Socket>();         // to store the sockets from the users
    public static ArrayList<Message_packet> online_Users = new ArrayList<Message_packet>() ; // to store all the Messages data
    String logoutUser="";
    
    public Server_tcp(int Port){
        this.Server_port = Port ;
        try{
            Server_S = new ServerSocket(Server_port);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
      

    @Override
    public void run(){
        try{
            while(true){
                online_Users_message = "" ;
                Socket Con_Soc = Server_S.accept();
                Connection_Acc.add(Con_Soc);
                BufferedReader Client_input = new BufferedReader (new InputStreamReader(Con_Soc.getInputStream()));
                DataOutputStream MSG_To_Client = new DataOutputStream(Con_Soc.getOutputStream());
                
                Message_recieved = Client_input.readLine();
                String []Message = Message_recieved.split(", ");
                logoutUser = Message[0]+ " "+ Message[1]+ " "+ Message[2];
                
                if(Message.length == 4){
                    String Users[] = Server_Online_Users.getItems();
                    int index = -1 ;
                    for(int i = 0 ; i < Users.length; i++){
                        if(Users[i].equals(logoutUser)){
                            index = i ;
                        }
                        else continue ;
                    }
                    if(index>-1)
                    {
                    online_Users.remove(index);
                    Server_Online_Users.delItem(index);
                   
                    }
                    String logoutMessage = "LOGOUT_USER---" + logoutUser;
    
                    // Broadcast the logout message to all clients
                    for (Socket clientSocket : Connection_Acc) {
                        try {
                            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                            out.writeBytes(logoutMessage + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //MSG_To_Client.writeBytes(index+"\n");
                    Server_Status.setText("User "+Message[0]+ " with IP: " + Message[1] + " and Port: " + Message[2] + " is now offline");
                    
                } // Logout request 
                
                else {
                    Server_Status.setText("User "+Message[0]+ " with IP: " + Message[1] + " and Port: " + Message[2] + " is now online");
                    Server_Online_Users.add(Message[0]+" "+Message[1]+" "+Message[2]);
                    Message_packet M = new Message_packet(Message[0] , Message[1] , Message[2]);
                    online_Users.add(M);
                    
                    for (Message_packet MP : online_Users){
                        online_Users_message += MP.username + ", " + MP.IP + ", " + MP.PORT + "---" ;
                    }
                    for (Socket X : Connection_Acc){
                        DataOutputStream Send = new DataOutputStream(X.getOutputStream());
                        Send.writeBytes(online_Users_message + "\n");
                    }
                    //MSG_To_Client.writeBytes(Message_recieved+"\n");
                } // Login request
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
// send all the online users to the last user has logged in and then send the online users by udp from the last users to all the connected users he can see