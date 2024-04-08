/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import static Interface.Client_frame.chatModel;
import static Interface.Client_frame.Status;
import static Interface.Client_frame.archivedMessages;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class Server_peer extends Thread {

    int Loc_port;
    String IP_ADD;
    byte[] Client_msg = new byte[1024];
    int Client_PORT;
    String chat_box_msg;
    DatagramSocket Server_DS;

    public Server_peer(String Server_IP, int Server_Port) { // Runnable method of the class to pass the parameter to the thread
        this.IP_ADD = Server_IP;
        this.Loc_port = Server_Port;
        try {
            Server_DS = new DatagramSocket(Server_Port);    // initalize a socket to recieve the data from any client
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            while (true) {
                DatagramPacket Receieved_Client = new DatagramPacket(Client_msg, Client_msg.length);
                Server_DS.receive(Receieved_Client);
                Client_msg = new byte[1024];
                chat_box_msg = new String(Receieved_Client.getData()).trim();

                if (chat_box_msg != null && !chat_box_msg.isEmpty()) { // MESSAGE RECIEVED 
                    String[] NET_DATA = chat_box_msg.split(" , ", 4);

                    String command = NET_DATA[2].trim();

                    System.out.println("command is " + command);

                    if ("DELETE_ALL_MESSAGES".equals(command)) {

                        for (int i = 0; i < chatModel.size(); i++) {
                            String msg = chatModel.get(i);
                            archivedMessages.add(msg);
                        }

                        chatModel.clear();
                    } else if ("DELETE_SELECTED_MESSAGES".equals(command)) {
                        String[] messagesArray = NET_DATA[3].split("\\|\\|\\|"); // Split back into an array
                        List<String> selectedMessages = new ArrayList<>(Arrays.asList(messagesArray));
                        List<String> tempArchivedMessages = new ArrayList<>();

                        for (int i = chatModel.size() - 1; i >= 0; i--) {
                            String chatModelMsg = stripHtml(chatModel.get(i));

                            for (String selectedMsg : selectedMessages) {
                                String plainSelectedMsg = stripHtml(selectedMsg);

                                if (chatModelMsg.equals(plainSelectedMsg)) {
                                    tempArchivedMessages.add(chatModel.get(i));
                                    chatModel.removeElementAt(i);
                                    break;
                                }
                            }
                        }
                        for (int j = tempArchivedMessages.size() - 1; j >= 0; j--) {
                            archivedMessages.add(tempArchivedMessages.get(j));
                        }
                        tempArchivedMessages.clear();
                        selectedMessages.clear();
                    } else if ("ARCHIVE_MESSAGE_EXPIRED".equals(command)) {
                        String message = NET_DATA[3].trim();
                        String formattedMsg1 = stripHtml(message);

                        for (String archivedMsg : archivedMessages) {
                            String formattedMsg2 = stripHtml(archivedMsg);

                            if (formattedMsg1.equals(formattedMsg2)) {
                                archivedMessages.remove(archivedMsg);
                                break;
                            }
                        }

                    } else if ("RESTORE_MESSAGE".equals(command)) {
                        String message = NET_DATA[3].trim();
                        message = switchMessageColor(message);

                        String timestamp = extractTimestampFromMessage(message);

                        String formattedMsg1 = stripHtml(message);

                        for (String archivedMsg : archivedMessages) {
                            String formattedMsg2 = stripHtml(archivedMsg);

                            if (formattedMsg1.equals(formattedMsg2)) {
                                archivedMessages.remove(archivedMsg);
                                break;
                            }
                        }

                        int insertAtPosition = findInsertPositionByTimestamp(timestamp);

                        if (insertAtPosition >= 0 && insertAtPosition <= chatModel.size()) {
                            chatModel.add(insertAtPosition, message);

                        } else {
                            chatModel.addElement(message);
                        }

                    } else {
                        Status.setText("Data has been recieved from => IP: " + NET_DATA[0] + ", Port: " + NET_DATA[1]);
                        String formattedMessage = "<html><span style='color: red;'>" + NET_DATA[0] + ": " + NET_DATA[2] + ": " + NET_DATA[3] + "</span></html>";
                        chatModel.addElement(formattedMessage);
                        Status.setText("Data has been received from => IP: " + NET_DATA[0] + ", Port: " + NET_DATA[1]);

                        System.out.println("Data has been recieved from => IP: " + NET_DATA[0] + ", Port: " + NET_DATA[1] + "\n"
                                + NET_DATA[0] + ": " + NET_DATA[2].trim() + "\n");      // used to keep track with data if it was recieved correctly
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String stripHtml(String htmlMessage) {
        String textOnly = htmlMessage.replaceAll("<html><span style='color: .*?;'>", "").replaceAll("</span></html>", "");
        System.out.println(textOnly.trim());
        return textOnly.trim(); // Remove leading and trailing whitespace
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

    private String switchMessageColor(String htmlMessage) {
        // Pattern to find the color style in the HTML
        Pattern pattern = Pattern.compile("(style='color: )(red|blue)(;')");
        Matcher matcher = pattern.matcher(htmlMessage);

        // Replace red with blue or blue with red
        if (matcher.find()) {
            String currentColor = matcher.group(2);
            String newColor = "red".equals(currentColor) ? "blue" : "red";
            htmlMessage = matcher.replaceFirst("$1" + newColor + "$3");
        }

        return htmlMessage;
    }

    public String Retrieve_IP() {
        return IP_ADD;
    }

    public int Retrieve_Port() {
        return Client_PORT;
    }

    public String Retrieve_MSG() {
        return chat_box_msg;
    }
}
