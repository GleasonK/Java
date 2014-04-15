//File: ClientTest.java
//Author: Kevin Gleason
//Date: 4/14/14
//Use: Test case for the Client implementation

import javax.swing.JFrame;

public class ClientTest {
    public static void main(String[] args){
        Client other = new Client("KGFriend" ,"127.0.0.1");
        other.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        other.startRunning();
    }
}
