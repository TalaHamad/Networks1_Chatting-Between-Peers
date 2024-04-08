/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import static Interface.Client_frame.online_User_Name;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrator
 */
public class Login_Thread extends Thread {
    DefaultListModel<String> onlineUsersModel;
    String Message;
    String TCP_IP;
    int TCP_Port;
    

    //String USER ;
    public Login_Thread(String Mess, String IP, int Port, DefaultListModel<String> onlineUsersModel) {
        this.TCP_IP = IP;
        this.TCP_Port = Port;
        this.Message = Mess;
        this.onlineUsersModel = onlineUsersModel;
    }

    @Override
    public void run() {
        try {
            Socket Peer_send = new Socket(TCP_IP, TCP_Port);
            DataOutputStream MSG_TO_Server = new DataOutputStream(Peer_send.getOutputStream());
            BufferedReader Server_input = new BufferedReader(new InputStreamReader(Peer_send.getInputStream()));

            MSG_TO_Server.writeBytes(Message.trim() + "\n");

            online_User_Name = new ArrayList<Message_packet>();
            while (true) {
                String MSG_From_server = Server_input.readLine();
                if (MSG_From_server == null) { // Server has closed the connection
                    break;
                }
                if (MSG_From_server.startsWith("LOGOUT_USER---")) {
                    String logoutUserDetails = MSG_From_server.split("---")[1];
                    // Update GUI to remove logout user
                    SwingUtilities.invokeLater(() -> {
                        onlineUsersModel.removeElement(logoutUserDetails);
                    });
                } 
                
                else {

                    String[] Message_server = MSG_From_server.split("---");

                    SwingUtilities.invokeLater(() -> {
                        // Prepare to update the UI component on the Event Dispatch Thread (EDT)
                        List<String> currentUsers = new ArrayList<>();
                        for (int i = 0; i < onlineUsersModel.getSize(); i++) {
                            currentUsers.add(onlineUsersModel.getElementAt(i));
                        }

                        for (String Mess : Message_server) {
                            String[] Online = Mess.split(", ");
                            if (Online.length >= 3) { // Ensure that the message is properly formatted
                                String userDetails = Online[0] + " " + Online[1] + " " + Online[2];

                                // Check if this specific combination of username, IP, and port exists
                                if (!currentUsers.contains(userDetails)) {
                                    onlineUsersModel.addElement(userDetails);
                                }
                                
                            }
                        }

                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
