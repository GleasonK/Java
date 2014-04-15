//File: Client.java
//Author: Kevin Gleason
//Date: 4/14/14
//Use: The Client implementation for the messenger service

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame{

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connection;
    private String username;


    public Client(String username, String host){
        super("Client");
        this.serverIP = host;
        this.username = username;
        this.userText = new JTextField();
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
        this.userText.setPreferredSize(new Dimension(400, 50));
        this.userText.setFont(font);
        add(this.userText, BorderLayout.SOUTH);

        //Style the chat window
        this.chatWindow = new JTextArea();
        this.chatWindow.setLineWrap(true);

        this.chatWindow.setFont(font);
        //this.chatWindow.setBackground();
        this.chatWindow.setForeground(Color.BLUE);
        this.chatWindow.setMargin(new Insets(5, 3, 5, 3));

        add(new JScrollPane(this.chatWindow));


        setSize(450,400);
        setVisible(true);
    }

    //Connect to Server
    public void startRunning(){
        try {
            connectToServer();
            setupStreams();
            whileChatting();
        }
        catch (EOFException eofExc) {
            showMessage("\n Client terminated connection");
        }
        catch (IOException ioExc) {
            ioExc.printStackTrace();
        }
        finally{
            closeStream();
        }
    }

    //Connect to Server
    private void connectToServer() throws IOException{
        showMessage("Attempting connection... \n");
        this.connection = new Socket(InetAddress.getByName(this.serverIP),6789);
        showMessage("You are connected to " + this.connection.getInetAddress().getHostName() + " \n");
    }

    //Set up streams to send and receive messages
    private void setupStreams() throws IOException{
        this.output = new ObjectOutputStream(this.connection.getOutputStream());
        this.output.flush();
        this.input = new ObjectInputStream(this.connection.getInputStream());
        showMessage("\n Chat has been prepared. \n");
    }

    //While chatting with the server
    private void whileChatting() throws IOException{
        ableToType(true);
        do{
            try{
                message = (String) this.input.readObject();
                showMessage("\n" + message);
            }
            catch(ClassNotFoundException cnfExc) {
                showMessage("\n Message could not be sent \n");
            }
        }
        while(!message.equals("SERVER: END"));
    }

    //Closing the client connection
    private void closeStream(){
        showMessage("\n Closing connection stream...");
        ableToType(false);
        try{
            this.output.close();
            this.input.close();
            this.connection.close();
        }
        catch(IOException ioExc){
            ioExc.printStackTrace();
        }
    }

    //Send messages to server
    private void sendMessage(String message){
        try{
            this.output.writeObject(this.username + ": " + message);
            this.output.flush();
            showMessage("\n" + this.username + ": " + message);
        }
        catch(IOException ioExc){
            this.chatWindow.append("\n ERROR - Message could not be sent.");
        }
    }

    //Update the chatWindow
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

    //Give user permission to type in box
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

}