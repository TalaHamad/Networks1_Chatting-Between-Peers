/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Client_frame extends javax.swing.JFrame {
    public static DefaultListModel<String> chatModel = new DefaultListModel<>();
    public static DefaultListModel<String> peersModel = new DefaultListModel<>();
    public static List<String> archivedMessages = new ArrayList<>();
    private Map<String, Timer> archiveTimers = new HashMap<>();
    public static DefaultListModel<String> archiveListModel;
    private JList<String> archiveList;
    private JDialog archiveDialog;
    public static ArrayList<Message_packet> online_User_Name = new ArrayList<Message_packet>() ; 
    public static Map<String, String> userLastLoginMap = new HashMap<>();

    String Server_IP ;
    String Client_IP ;
    int Server_port ;
    int Client_port ;
    Server_peer SP ;
    
    
    public Client_frame() {
        initComponents();
        chatModel = new DefaultListModel<>();
        Public_chat_box.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        Public_chat_box.setModel(chatModel);
        peersModel = new DefaultListModel<>();
        Peer_Online_Users.setModel(peersModel);
        ListNets();
        
        Peer_Online_Users.addListSelectionListener(new ListSelectionListener() {
             @Override
             public void valueChanged(ListSelectionEvent e) {
                 if (!e.getValueIsAdjusting()) {
                     String selectedUser = Peer_Online_Users.getSelectedValue().toString();
                     if (selectedUser != null && !selectedUser.isEmpty()) {
                         // The string is in the format "username IP PORT"
                         String[] userDetails = selectedUser.split(" ");
                         if (userDetails.length >= 3) {
                             String ip = userDetails[1];
                             String port = userDetails[2];
                             Remote_IP.setText(ip);
                             Remote_Port.setText(port);
                         }
                     }
                 }
             }
         });

    
    }
    
    
    
    public void ListNets(){     
        String ip ;
        ArrayList<String> Net_interfaces = new ArrayList<String>();     // use to store the activated interfaces with there ipv4
        try{
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
            NetworkInterface Net_int = interfaces.nextElement();
       
            if (Net_int.isLoopback() || !Net_int.isUp())  // filters out localhost and any inactive interfaces
                continue;

            Enumeration<InetAddress> addresses = Net_int.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    if (addr instanceof Inet6Address) continue; // remove the IPv6 from the address (network interface)

                    ip = addr.getHostAddress();
                    if(Net_int.getName().contains("wireless")){     // if the hostname contains wlanx such x is number equal 1 or larger then it will be wifi interface so host nname will be change
                        String wlan = "Wifi";               
                        Net_interfaces.add(wlan);           // add the interface new hostname 
                        Net_interfaces.add(ip);             // add the interface ip
                    }
                    else if(Net_int.getName().contains("eth")){ // if the hostname contains ethx such x is number equal 1 or larger then it will be ethernet interface so host nname will be change
                        String eth = "Ethernet";
                        Net_interfaces.add(eth);            // add the interface new hostname
                        Net_interfaces.add(ip);             // add the interface ip
                    }
                    else if(Net_int.getName().contains("enp")){ // if the hostname contains ethx such x is number equal 1 or larger then it will be ethernet interface so host nname will be change
                        String enp = "VM_Wifi";
                        Net_interfaces.add(enp);            // add the interface new hostname
                        Net_interfaces.add(ip);             // add the interface ip
                    }
                }
            }
            for(int i = 0 ; i < Net_interfaces.size() ; i+=2){  // loop on each 2 consecutive values, and concatenate them to represent the network interface
                String Name = Net_interfaces.get(i);
                String ipv4 = Net_interfaces.get(i+1);
                String Net_INT = Name + " : " + ipv4 ;
                Interface_List.addItem(Net_INT);        // add the network interface to combobox
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main_frame = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        LogoutButton = new javax.swing.JButton();
        Test_Connection = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Public_chat_box = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        Message_sent = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TCP_SER_Port = new javax.swing.JTextField();
        Remote_Port = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Interface_List = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TCP_SER_IP = new javax.swing.JTextField();
        Local_IP = new javax.swing.JTextField();
        Local_port = new javax.swing.JTextField();
        Remote_IP = new javax.swing.JTextField();
        LoginButton = new javax.swing.JButton();
        Status = new javax.swing.JLabel();
        Send_message = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        Send_message_to_all = new javax.swing.JButton();
        Delete_Conv = new javax.swing.JButton();
        Archive = new javax.swing.JButton();
        Delete_Message = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        password = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        Peer_Online_Users = new javax.swing.JList();
        LoginStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client Frame");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Main_frame.setBackground(new java.awt.Color(51, 51, 51));
        Main_frame.setForeground(new java.awt.Color(255, 255, 255));
        Main_frame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Password: ");
        Main_frame.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 90, 30));

        username.setBackground(new java.awt.Color(51, 51, 51));
        username.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        Main_frame.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 190, 30));

        LogoutButton.setBackground(new java.awt.Color(51, 51, 51));
        LogoutButton.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        LogoutButton.setForeground(new java.awt.Color(255, 255, 255));
        LogoutButton.setText("Logout");
        LogoutButton.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("")));
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });
        Main_frame.add(LogoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 110, 30));

        Test_Connection.setBackground(new java.awt.Color(51, 51, 51));
        Test_Connection.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        Test_Connection.setForeground(new java.awt.Color(255, 255, 255));
        Test_Connection.setText("Test Connection");
        Test_Connection.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Test_Connection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Test_Connection_Action(evt);
            }
        });
        Main_frame.add(Test_Connection, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 420, 140, 50));

        Public_chat_box.setBackground(new java.awt.Color(51, 51, 51));
        Public_chat_box.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        Public_chat_box.setEnabled(true);
        jScrollPane1.setViewportView(Public_chat_box);

        Main_frame.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 570, 180));

        Message_sent.setBackground(new java.awt.Color(51, 51, 51));
        Message_sent.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        Message_sent.setForeground(new java.awt.Color(255, 255, 255));
        Message_sent.setText("enter your message ..");
        Message_sent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Message_sentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Message_sent);

        Main_frame.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 570, 80));

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Status : ");
        Main_frame.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 60, 40));

        jSeparator1.setBackground(new java.awt.Color(51, 51, 51));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        Main_frame.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 3, -1, 550));

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Available Interface : ");
        Main_frame.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, -1, 30));

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Remote Port : ");
        Main_frame.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, -1, 30));

        TCP_SER_Port.setBackground(new java.awt.Color(51, 51, 51));
        TCP_SER_Port.setForeground(new java.awt.Color(255, 255, 255));
        TCP_SER_Port.setPreferredSize(new java.awt.Dimension(112, 19));
        Main_frame.add(TCP_SER_Port, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 170, 30));

        Remote_Port.setBackground(new java.awt.Color(51, 51, 51));
        Remote_Port.setForeground(new java.awt.Color(255, 255, 255));
        Remote_Port.setPreferredSize(new java.awt.Dimension(112, 19));
        Main_frame.add(Remote_Port, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 370, 170, 30));

        jLabel5.setBackground(new java.awt.Color(51, 51, 51));
        jLabel5.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TCP Server Port : ");
        Main_frame.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, -1, 30));

        Interface_List.setBackground(new java.awt.Color(51, 51, 51));
        Interface_List.setFont(new java.awt.Font("Lucida Bright", 0, 16)); // NOI18N
        Interface_List.setForeground(new java.awt.Color(255, 255, 255));
        Interface_List.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Interface_ListActionPerformed(evt);
            }
        });
        Main_frame.add(Interface_List, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 300, 50));

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Online Users :");
        Main_frame.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, -1, 30));

        jLabel7.setBackground(new java.awt.Color(51, 51, 51));
        jLabel7.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Local IP : ");
        Main_frame.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 220, -1, 30));

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Local Port : ");
        Main_frame.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 260, -1, 30));

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Remote IP : ");
        Main_frame.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, -1, 30));

        TCP_SER_IP.setBackground(new java.awt.Color(51, 51, 51));
        TCP_SER_IP.setForeground(new java.awt.Color(255, 255, 255));
        TCP_SER_IP.setPreferredSize(new java.awt.Dimension(112, 19));
        TCP_SER_IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCP_SER_IPActionPerformed(evt);
            }
        });
        Main_frame.add(TCP_SER_IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 170, 30));

        Local_IP.setBackground(new java.awt.Color(51, 51, 51));
        Local_IP.setForeground(new java.awt.Color(255, 255, 255));
        Local_IP.setEnabled(false);
        Local_IP.setPreferredSize(new java.awt.Dimension(112, 19));
        Main_frame.add(Local_IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, 170, 30));

        Local_port.setBackground(new java.awt.Color(51, 51, 51));
        Local_port.setForeground(new java.awt.Color(255, 255, 255));
        Local_port.setPreferredSize(new java.awt.Dimension(112, 19));
        Main_frame.add(Local_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 260, 170, 30));

        Remote_IP.setBackground(new java.awt.Color(51, 51, 51));
        Remote_IP.setForeground(new java.awt.Color(255, 255, 255));
        Remote_IP.setPreferredSize(new java.awt.Dimension(112, 19));
        Main_frame.add(Remote_IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 330, 170, 30));

        LoginButton.setBackground(new java.awt.Color(51, 51, 51));
        LoginButton.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        LoginButton.setForeground(new java.awt.Color(255, 255, 255));
        LoginButton.setText("Login");
        LoginButton.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        Main_frame.add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 110, 30));

        Status.setBackground(new java.awt.Color(51, 51, 51));
        Status.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        Status.setForeground(new java.awt.Color(255, 255, 255));
        Main_frame.add(Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 500, 500, 40));

        Send_message.setBackground(new java.awt.Color(51, 51, 51));
        Send_message.setFont(new java.awt.Font("Lucida Bright", 0, 16)); // NOI18N
        Send_message.setForeground(new java.awt.Color(255, 255, 255));
        Send_message.setText("Send Message");
        Send_message.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Send_message.setMaximumSize(new java.awt.Dimension(114, 19));
        Send_message.setMinimumSize(new java.awt.Dimension(114, 19));
        Send_message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Send_messageActionPerformed(evt);
            }
        });
        Main_frame.add(Send_message, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 420, 150, 50));

        jLabel11.setBackground(new java.awt.Color(51, 51, 51));
        jLabel11.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("TCP Server IP : ");
        Main_frame.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, 30));

        Send_message_to_all.setBackground(new java.awt.Color(51, 51, 51));
        Send_message_to_all.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        Send_message_to_all.setForeground(new java.awt.Color(255, 255, 255));
        Send_message_to_all.setText("Send to all");
        Send_message_to_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Send_message_to_allActionPerformed(evt);
            }
        });
        Main_frame.add(Send_message_to_all, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 480, 170, 40));

        Delete_Conv.setBackground(new java.awt.Color(51, 51, 51));
        Delete_Conv.setFont(new java.awt.Font("Lucida Bright", 0, 16)); // NOI18N
        Delete_Conv.setForeground(new java.awt.Color(255, 255, 255));
        Delete_Conv.setText("Delete Conversation");
        Delete_Conv.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Delete_Conv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_ConvActionPerformed(evt);
            }
        });
        Main_frame.add(Delete_Conv, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 320, 180, 40));

        Archive.setBackground(new java.awt.Color(51, 51, 51));
        Archive.setFont(new java.awt.Font("Lucida Bright", 0, 16)); // NOI18N
        Archive.setForeground(new java.awt.Color(255, 255, 255));
        Archive.setText("Archive");
        Archive.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Archive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArchiveActionPerformed(evt);
            }
        });
        Main_frame.add(Archive, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, 180, 40));

        Delete_Message.setBackground(new java.awt.Color(51, 51, 51));
        Delete_Message.setFont(new java.awt.Font("Lucida Bright", 0, 16)); // NOI18N
        Delete_Message.setForeground(new java.awt.Color(255, 255, 255));
        Delete_Message.setText("Delete Message");
        Delete_Message.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        Delete_Message.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_MessageActionPerformed(evt);
            }
        });
        Main_frame.add(Delete_Message, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 180, 40));

        jLabel10.setBackground(new java.awt.Color(51, 51, 51));
        jLabel10.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("User Name : ");
        Main_frame.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 90, 30));

        password.setBackground(new java.awt.Color(51, 51, 51));
        password.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        Main_frame.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 190, 30));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(130, 258));

        Peer_Online_Users.setBackground(new java.awt.Color(51, 51, 51));
        Peer_Online_Users.setFont(new java.awt.Font("Lucida Bright", 0, 15)); // NOI18N
        Peer_Online_Users.setForeground(new java.awt.Color(255, 255, 255));
        Peer_Online_Users.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        Peer_Online_Users.setEnabled(true);
        jScrollPane3.setViewportView(Peer_Online_Users);

        Main_frame.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 60, 210, 460));

        LoginStatus.setBackground(new java.awt.Color(51, 51, 51));
        LoginStatus.setFont(new java.awt.Font("Lucida Bright", 0, 14)); // NOI18N
        LoginStatus.setForeground(new java.awt.Color(255, 255, 255));
        Main_frame.add(LoginStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 260, 30));

        getContentPane().add(Main_frame, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 550));

        setSize(new java.awt.Dimension(1166, 593));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Interface_ListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Interface_ListActionPerformed
        // TODO add your handling code here:
        String network_int = Interface_List.getSelectedItem().toString(); 
        String []separate = network_int.split(" : ");   // separate the interface host name from its own ipv4
        Local_IP.setText(separate[1]);  // set the IP address in the local IP text field
        
    }//GEN-LAST:event_Interface_ListActionPerformed

    private void Message_sentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Message_sentMouseClicked
        Message_sent.setText("");      // clear the data found
    }//GEN-LAST:event_Message_sentMouseClicked

    private void Send_messageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Send_messageActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
         
        String sent_message = Message_sent.getText();
        Client_IP = Remote_IP.getText();
        String client_port = Remote_Port.getText();
        Client_port = Integer.parseInt(client_port);
        
        String Message = Server_IP + " , " + Server_port + " , " + sent_message + " , " + timestamp;
        Client_Peer CP = new Client_Peer(Message , Client_IP , Client_port);
        CP.start();
        
         // Assuming blue color for sent messages
        String formattedMessage = "<html><span style='color: blue;'>" + Local_IP.getText() + ": " + sent_message + ": " + timestamp + "</span></html>";

        // Add the message to the JList via the DefaultListModel
        chatModel.addElement(formattedMessage);
    
        // Reset the message input area to be empty
        Message_sent.setText("");    
    }//GEN-LAST:event_Send_messageActionPerformed

    private void Test_Connection_Action(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Test_Connection_Action
        // this method is used to run the server peer thread, so it can communicate with the other peer when connect.

        Server_IP = Local_IP.getText() ;
        String Ser_port = Local_port.getText();
        Server_port = Integer.parseInt(Ser_port);
        
        System.out.println(Server_IP);
        System.out.println(Server_port);

        SP = new Server_peer(Server_IP , Server_port);
        SP.start();
                
    }//GEN-LAST:event_Test_Connection_Action

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // this button is for login (send TCP data)
        String Username = username.getText();
        String Password = password.getText();
        String Loc_IP = Local_IP.getText();
        String Loc_port = Local_port.getText();
        String TCP_Server_IP = TCP_SER_IP.getText();
        String TCP_Server_Port = TCP_SER_Port.getText();
        int Server_port = Integer.parseInt(TCP_Server_Port);
 
        String Message = Username + ", " + Loc_IP + ", " + Loc_port;
        
        boolean isValidUser = false;
        String lastLoginTimeMessage = "This is your first login.";
        
        GlobalResources.loadUserCredentials("C:\\Users\\AB\\Downloads\\Network_project_part1andpart2\\user_credentials.txt");

            for (String[] credentials : GlobalResources.userCredentials) {
                if (credentials[0].equalsIgnoreCase(Username) && credentials[1].equalsIgnoreCase(Password)) {
                    isValidUser = true;
                    // If there's a last login time stored, use it
                    if (userLastLoginMap.containsKey(Username)) {
                        lastLoginTimeMessage = "Last login time: " + userLastLoginMap.get(Username);
                    }
                    // Update the last login time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    userLastLoginMap.put(Username, sdf.format(new Date()));
                    break; // Exit the loop early since we found a match
                }
            }

           if (isValidUser) {  
                 JOptionPane.showMessageDialog(null, "Login Successful", "Login", JOptionPane.INFORMATION_MESSAGE);
                 LoginStatus.setText(lastLoginTimeMessage);
                 Login_Thread peer_send = new Login_Thread(Message, TCP_Server_IP, Server_port, peersModel);
                 peer_send.start();

            } else {
       
        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
      
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        String Username = username.getText();
        String Loc_IP = Local_IP.getText();
        String Loc_port = Local_port.getText();
        String TCP_Server_IP = TCP_SER_IP.getText();
        String TCP_Server_Port = TCP_SER_Port.getText();
        int Server_port = Integer.parseInt(TCP_Server_Port);
        String MSG_From_server ;
        String Message = Username + ", " + Loc_IP + ", " + Loc_port;
        
        Logout_Thread peer_send = new Logout_Thread(Message, TCP_Server_IP, Server_port , true);
        peer_send.start();
        
        username.setText("");
        password.setText("");
        LoginStatus.setText("");
        Local_port.setText("");
        peersModel.clear();
        chatModel.clear();
        
    }//GEN-LAST:event_LogoutButtonActionPerformed


    private void Send_message_to_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Send_message_to_allActionPerformed
      
      String sentMessage = Message_sent.getText(); 
      String currentUser = username.getText(); 
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String timestamp = sdf.format(new Date());

      // Iterate over the DefaultListModel containing online users.
      for (int i = 0; i < peersModel.getSize(); i++) {
          String userDetails = peersModel.getElementAt(i); // Get user details as a single string.
          String[] userParts = userDetails.split(" "); // Split the string to extract details.
          // Ensure the string format is "username IP PORT".
          if (userParts.length >= 3) {
              String userName = userParts[0];
              String clientIP = userParts[1];
              String clientPortStr = userParts[2];

              if (!userName.equals(currentUser)) { // Check if the user is not the current user.
                  try {
                    
                      int clientPort = Integer.parseInt(clientPortStr); // Parse the port of the recipient.

                      String messageToSend = Server_IP + " , " + Server_port + " , " + sentMessage + " , " + timestamp; // Construct the message.

                      // Send the message.
                      Client_Peer CP = new Client_Peer(messageToSend, clientIP, clientPort);
                      CP.start(); // Start the thread to send the message.

                  } catch (NumberFormatException e) {
                      e.printStackTrace(); // Handle error if parsing the port fails.
                  }
              }
          }
      }
      
      // Assuming blue color for sent messages.
                      String formattedMessage = "<html><span style='color: blue;'>" + Local_IP.getText() + ": " + sentMessage + ": " + timestamp + "</span></html>";

                      // Add the message to the JList via the DefaultListModel.
                      chatModel.addElement(formattedMessage);


      // Clear the message input area after sending.
      Message_sent.setText("");    

        
    }//GEN-LAST:event_Send_message_to_allActionPerformed

    private void TCP_SER_IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCP_SER_IPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCP_SER_IPActionPerformed

    private void Delete_ConvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_ConvActionPerformed
        deleteAllMessages();
    }//GEN-LAST:event_Delete_ConvActionPerformed

    private void ArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArchiveActionPerformed
          showArchive();
    }//GEN-LAST:event_ArchiveActionPerformed

    private void Delete_MessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_MessageActionPerformed
        deleteSelectedMessages();
    }//GEN-LAST:event_Delete_MessageActionPerformed

    /**
     *  
     * 
     * @param args the command line arguments
     */

    // Delete selected messages and sent it to the archive list
    private void deleteSelectedMessages() {
          List<String> selectedMessagesBox = Public_chat_box.getSelectedValuesList();
          for(int j=0 ; j <selectedMessagesBox.size(); j++ )
          {
               chatModel.removeElement(selectedMessagesBox.get(j));
                    archiveMessage(selectedMessagesBox.get(j));
          }
          if(!selectedMessagesBox.isEmpty()){
           sendDeleteSelectedMessagesCommand(selectedMessagesBox);
          }

    }

    private void sendDeleteSelectedMessagesCommand(List<String> selectedMessagesBox) {
        try {
            String command = "DELETE_SELECTED_MESSAGES";
            // Join the messages into a single string, separated by a unique delimiter, e.g., "|||"
            String messagesJoined = String.join("|||", selectedMessagesBox);
            String Message = Server_IP + " , " + Server_port + " , " + command + " , " + messagesJoined;

            Client_Peer CP = new Client_Peer(Message, Client_IP, Client_port);
            CP.start();
        } catch (Exception e) {
            e.printStackTrace();
            Status.setText("Failed to send delete selected messages command to the server.");
        }
    }

    private void deleteAllMessages() {
          for (int i = 0; i < chatModel.size(); i++) {
            String msg = chatModel.get(i);
            archiveMessage(msg);
    }
        chatModel.clear();
       
        sendDeleteAllMessagesCommand();
    }

    private void sendDeleteAllMessagesCommand() {
    try {
        String command = "DELETE_ALL_MESSAGES";
        String Message = Server_IP + " , " + Server_port + " , " + command;
        
        Client_Peer CP = new Client_Peer(Message, Client_IP, Client_port);
        CP.start();

    } catch (Exception e) {
        e.printStackTrace();
        Status.setText("Failed to send delete all messages command to the server.");
    }
}

    private void archiveMessage(String message) {
        archivedMessages.add(message);
        // Start a timer to remove the message from the archive after 2 minutes.
        Timer timer = new Timer(120000, e -> {
            archivedMessages.remove(message);
            ((Timer)e.getSource()).stop();
            notifyServerArchiveMessageExpired(message);

        });
        timer.setRepeats(false); // Ensure the timer only runs once
        timer.start();
        // Store the timer because we need to cancel it (if the message is restored).
        archiveTimers.put(message, timer);
    }

    private void notifyServerArchiveMessageExpired(String message) {
        try {
            
            String command = "ARCHIVE_MESSAGE_EXPIRED";
        
            String fullMessage = Server_IP + " , " + Server_port + " , " + command + " , " + message;

            Client_Peer CP = new Client_Peer(fullMessage, Client_IP, Client_port);
            CP.start();
        } catch (Exception e) {
            e.printStackTrace();
            Status.setText("Failed to notify server about archive expiration.");
        }
    }

    private void restoreArchivedMessage(String message) {
    if (archivedMessages.contains(message)) {
        sendRestoreMessageCommand(message);
        
        Timer timer = archiveTimers.get(message);
        if (timer != null) {
            timer.stop();
            archiveTimers.remove(message);
        }

        archivedMessages.remove(message);

        String timestamp = extractTimestampFromMessage(message);

        int insertAtPosition = findInsertPositionByTimestamp(timestamp);

        if (insertAtPosition >= 0 && insertAtPosition <= chatModel.size()) {
            chatModel.add(insertAtPosition, message);
        } else {
            chatModel.addElement(message); 
        }

    }
}

    private void sendRestoreMessageCommand(String message) {
    try {
        String command = "RESTORE_MESSAGE";

        String fullMessage = Server_IP + " , " + Server_port + " , " + command + " , " + message;

        Client_Peer CP = new Client_Peer(fullMessage, Client_IP, Client_port);
        CP.start();
    } catch (Exception e) {
        e.printStackTrace();
        Status.setText("Failed to send restore message command to the server.");
    }
}

    private String extractTimestampFromMessage(String message) {
    // Attempt to extract the timestamp from the HTML structure
    try {
        // Remove HTML tags to simplify extraction
        String noHtml = message.replaceAll("<[^>]*>", "");
        // Use regex to find the timestamp pattern directly
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(noHtml);
        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new IllegalArgumentException("No timestamp found in message");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null; // Consider more appropriate error handling
    }
}

    private int findInsertPositionByTimestamp(String timestamp) {
    // Convert timestamp string to a Date object for comparison
    Date timestampDate = parseTimestamp(timestamp);
    for (int i = 0; i < chatModel.size(); i++) {
        String currentMessage = chatModel.get(i);
        String msgTimestamp = extractTimestampFromMessage(currentMessage);
        Date msgDate = parseTimestamp(msgTimestamp);
        
        // Compare the timestampDate with each message's date in the chatModel
        if (timestampDate.before(msgDate) || timestampDate.equals(msgDate)) {
            // If the timestamp is before or equal to the current message's timestamp, return the current index
            return i;
        }
    }
    return chatModel.size();
}

    private Date parseTimestamp(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        try {
            return dateFormat.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; 
        }
    }

    private void showArchive() {
    archiveDialog = new JDialog(this, "Archived Messages", true);
    archiveDialog.setLayout(new BorderLayout());
    archiveListModel = new DefaultListModel<>();
    archiveList = new JList<>(archiveListModel);
    archiveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(archiveList);
    archiveDialog.add(scrollPane, BorderLayout.CENTER);
    
    // Populate the list model
    for (String msg : archivedMessages) {
        archiveListModel.addElement(msg);
    }
    
    JButton restoreButton = new JButton("Restore Selected Message");
    restoreButton.setBackground(Color.LIGHT_GRAY); 
    restoreButton.addActionListener(e -> {
        List<String> selectedMessages = archiveList.getSelectedValuesList();
        for (String message : selectedMessages) {
            restoreArchivedMessage(message);
            archiveListModel.removeElement(message);
        }
    });
    
    archiveDialog.add(restoreButton, BorderLayout.SOUTH);
    archiveDialog.setSize(400, 300);
    archiveDialog.setLocationRelativeTo(null);
    archiveDialog.setVisible(true);
}

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Client_frame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Archive;
    private javax.swing.JButton Delete_Conv;
    private javax.swing.JButton Delete_Message;
    private javax.swing.JComboBox<String> Interface_List;
    private javax.swing.JTextField Local_IP;
    private javax.swing.JTextField Local_port;
    private javax.swing.JButton LoginButton;
    public static javax.swing.JLabel LoginStatus;
    private javax.swing.JButton LogoutButton;
    private javax.swing.JPanel Main_frame;
    private javax.swing.JTextPane Message_sent;
    public static javax.swing.JList Peer_Online_Users;
    public static javax.swing.JList Public_chat_box;
    private javax.swing.JTextField Remote_IP;
    private javax.swing.JTextField Remote_Port;
    private javax.swing.JButton Send_message;
    private javax.swing.JButton Send_message_to_all;
    public static javax.swing.JLabel Status;
    private javax.swing.JTextField TCP_SER_IP;
    private javax.swing.JTextField TCP_SER_Port;
    private javax.swing.JButton Test_Connection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    
}

