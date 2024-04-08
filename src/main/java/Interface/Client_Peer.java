/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import java.net.*;

public class Client_Peer extends Thread {  
    byte[] Data_sent = new byte[1024];   
    String Rem_IP ;
    String Sent_Message ;
    int Rem_port ;
    
    public Client_Peer (String message , String Remote_IP , int Remote_port){ // Runnable method to pass parameters for the client thread
        this.Sent_Message = message;
        this.Rem_IP = Remote_IP;
        this.Rem_port = Remote_port;
    }
    
    @Override
    public void run(){
        int port = Rem_port;
        try {
            Data_sent = Sent_Message.getBytes();
            InetAddress IP_add = InetAddress.getByName(Rem_IP);
            DatagramSocket Client_DS = new DatagramSocket();
            
            DatagramPacket Packet_Sent;
            Packet_Sent = new DatagramPacket(Data_sent , Data_sent.length , IP_add , port);
            Client_DS.send(Packet_Sent);
            Client_DS.close();
            
        }catch (Exception e) {
            e.printStackTrace();
        }
         
    }
}
