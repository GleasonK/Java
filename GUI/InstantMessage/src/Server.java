//File: Server.java
//Author: Kevin Gleason
//Date: 4/14/14
//Use: Implementation of the server for the Chat

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
    //Instance variable for chat
    private JTextField userText;
    private JTextArea chatWindow;
    //Connection between two comps through socket (stream)
    private ObjectOutputStream output;
    private ObjectInputStream input;
    //Server for the transfer
    //Configure the server to wait for others to log onto server
    private ServerSocket server;
    //Set up the connection between computers
    private Socket connection;
    private String username;

    //Constructor
    public Server(String username){
        super("Kevin's Instant Messenger");
        this.username = username;
        this.userText =  new JTextField();
        this.userText.setEditable(false);
        this.userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");

                    }
                }
        );
        //Style the userText
        Font font = new Font("Verdana", Font.BOLD, 12);
        this.userText.setPreferredSize(new Dimension(450,50));
        this.userText.setMargin(new Insets(0,0,0,0));
        this.userText.setFont(font);
        add(this.userText, BorderLayout.SOUTH);

        //Style the chat window
        this.chatWindow = new JTextArea();
        this.chatWindow.setLineWrap(true);

        this.chatWindow.setFont(font);
        //this.chatWindow.setBackground();
        this.chatWindow.setForeground(Color.RED);
        this.chatWindow.setMargin(new Insets(5,3,5,3));



        add(new JScrollPane(this.chatWindow));
        setSize(450,400);
        setVisible(true);
    }

    public void startIM(){
        try {
            //6789 is the Server port number. Remember for testing.
            //100 is for Server backlog, number of people waiting to access.
            //Loop runtime until closed
            this.server = new ServerSocket(6789, 100);
            while(true){
                try{
                    waitForConnect();
                    setupStreams();
                    whileChatting();
                }
                catch(EOFException eofExc){
                    //Used for end of connection
                    showMessage("\n Server ended the connection.");
                }
                finally{
                    closeStream();
                }
            }
        }
        catch(IOException ioExc){
            ioExc.printStackTrace();
        }
    }

    //Wait for connection then tell you connection exists.
    //Keep looping until connection
    private void waitForConnect() throws IOException{
        showMessage("Waiting for user to connect...\n");
        this.connection = this.server.accept();
        showMessage("Connected to " + this.connection.getInetAddress().getHostName());

    }

    //Make Streams to send and return data
    //Create pathway allowing connection to another computer
    //Flush for housekeeping, flushes out bytes get left over in the buffer
    //Only other user can flush bytes out of their program with flush, only output not input
    private void setupStreams() throws IOException{
        this.output = new ObjectOutputStream(this.connection.getOutputStream());
        this.output.flush();
        this.input = new ObjectInputStream(this.connection.getInputStream());
        showMessage("Streams are now setup \n");

    }

    //During the chat conversation
    private void whileChatting() throws IOException {
        String message = "You are now connected \n";
        sendMessage(message);
        ableToType(true);
        do{
            //Have convesation while client is still there.
            try {
                message = (String) this.input.readObject();
                showMessage("\n" + message);
            }
            catch(ClassNotFoundException cnfExc){
                showMessage("\n ERROR: User sent bad object \n");
            }
        }
        while(!message.equals("CLIENT: END"));
    }

    //Once done chatting closes streams and sockets
    private void closeStream(){
        showMessage("\n Closing connection... \n");
        ableToType(false);
        try {
            this.output.close();
            this.input.close();
            this.connection.close();
        }
        catch(IOException ioExc){
            ioExc.printStackTrace();
        }
    }

    //Sends message through socket
    private void sendMessage(String message){
        try {
            this.output.writeObject(this.username + ": " + message);
            this.output.flush();
            showMessage("\n" + this.username + ": " + message);
        }
        catch(IOException ioExc){
            this.chatWindow.append("Error - Cannot send message.");
        }
    }

    //Update chatWindow
    private void showMessage(final String message){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(message);
                    }
                }
        );
    }

    //Let the user type into the box
    private void ableToType(final boolean tf) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tf);
                    }
                }
        );
    }

    public String getUsername() {
        return this.username;
    }



    public static void main(String[] args){

    }
}