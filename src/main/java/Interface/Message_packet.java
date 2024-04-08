/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

/**
 *
 * @author Administrator
 */
public class Message_packet {
    String username ;
    String IP ;
    String PORT ;
    Message_packet(String name , String ip , String port){
        this.username = name ;
        this.IP = ip ;
        this.PORT = port ;
    }
}
