package Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobalResources {
    // A static ArrayList to hold username-password pairs
    public static List<String[]> userCredentials = new ArrayList<>();
   

    // Method to read usernames and passwords from a file and store them in the ArrayList
    public static void loadUserCredentials(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) { // Ensure that there is a username and a password
                    userCredentials.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean addNewUser(String username, String password) {
        for (String[] credentials : userCredentials) {
            if (credentials[0].equals(username)) {
                // Username already exists
                return false;
            }
        }
        // Username does not exist, add new user
        userCredentials.add(new String[]{username, password});
        
            // Now write the new user to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\AB\\Downloads\\Network_project_part1andpart2\\user_credentials.txt", true))) {
            bw.newLine(); // Make sure to add a newline to separate entries
            bw.write(username + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if there was an error writing to the file
        }

        return true; // User added successfully
    }
    
}
