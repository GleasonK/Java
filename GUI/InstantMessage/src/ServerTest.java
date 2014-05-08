//File: ServerTest.java
//Author: Kevin Gleason
//Date: 4/14/14
//Use: Test case for the server

import javax.swing.JFrame;

public class ServerTest {
    public static void main(String[] args) {
        Server kevin = new Server("GleasonK");
        kevin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        kevin.startIM();
    }

}
