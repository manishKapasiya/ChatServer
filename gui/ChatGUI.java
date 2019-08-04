package chatserver.gui;

import chatserver.client.Client;
import chatserver.commons.SettingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatGUI implements ActionListener{

    private Client client;
    private JButton sendButton, logoutButton,loginButton, exitButton;
    private JFrame chatWindow;
    private JTextArea txtBroadcast;
    private JTextArea txtMessage;
    private JList usersList;

    public ChatGUI(Client client){
        this.client = client;
        displayGUI();
    }

    private JButton createButton(String text){
        JButton button=new JButton(text);
        button.setForeground(SettingUtils.GFG_TEXT_COLOR);
        button.setBackground(SettingUtils.GFG_BACKGROUND_COLOR);
        return button;
    }
    public void displayGUI()
    {
        chatWindow=new JFrame();
        txtBroadcast=new JTextArea(5,30);
        txtBroadcast.setEditable(false);
        txtMessage=new JTextArea(2,20);
        usersList=new JList();

        sendButton = this.createButton("Send");
        logoutButton = this.createButton("Log out");
        loginButton = this.createButton("Log in");
        exitButton=this.createButton("Exit");

        JPanel center1=new JPanel();
        center1.setLayout(new BorderLayout());
        JLabel heading = new JLabel("GeeksforGeeks Chat Server",JLabel.CENTER);
        heading.setForeground(SettingUtils.GFG_TEXT_COLOR);
        center1.setBackground(SettingUtils.GFG_BACKGROUND_COLOR);
        center1.add(heading,"North");
        center1.add(new JScrollPane(txtBroadcast),"Center");

        JPanel south1=new JPanel();
        south1.setLayout(new FlowLayout());
        south1.add(new JScrollPane(txtMessage));
        south1.add(sendButton);


        JPanel south2=new JPanel();
        south2.setLayout(new FlowLayout());
        south2.add(loginButton);
        south2.add(logoutButton);
        south2.add(exitButton);

        JPanel south=new JPanel();
        south.setLayout(new GridLayout(2,1));
        south.add(south1);
        south.add(south2);

        JPanel east=new JPanel();
        east.setLayout(new BorderLayout());
        east.setBackground(SettingUtils.GFG_BACKGROUND_COLOR);
        JLabel onlineUsers = new JLabel("Online Users",JLabel.CENTER);
        onlineUsers.setForeground(SettingUtils.GFG_TEXT_COLOR);
        east.add(onlineUsers,"East");
        east.add(new JScrollPane(usersList),"South");

        chatWindow.add(east,"East");

        chatWindow.add(center1,"Center");
        chatWindow.add(south,"South");

        chatWindow.pack();
        chatWindow.setTitle("Login for Chat");
        chatWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        chatWindow.setVisible(true);
        sendButton.addActionListener(this);
        logoutButton.addActionListener(this);
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        logoutButton.setEnabled(false);
        loginButton.setEnabled(true);

        txtMessage.addFocusListener(new FocusAdapter()
        {
            public void focusGained(FocusEvent fe)
            {
                txtMessage.selectAll();
            }
        });

        chatWindow.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {
                if(client.getS()!=null)
                {
                    JOptionPane.showMessageDialog(chatWindow,"u r logged out right now. ","Exit",JOptionPane.INFORMATION_MESSAGE);
                    client.logoutSession();
                }
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent ev)
    {
        JButton temp=(JButton)ev.getSource();
        if(temp==sendButton)
        {
            if(this.client.getS()==null)
            {
                JOptionPane.showMessageDialog(chatWindow,"u r not logged in. plz login first");
                return;
            }
            try{
                this.client.getDos().writeUTF(txtMessage.getText());
                txtMessage.setText("");
            }
            catch(Exception excp)
            {
                txtBroadcast.append("\nsend button click :"+excp);
            }
        }
        if(temp==loginButton)
        {
            String uname=JOptionPane.showInputDialog(chatWindow,"Enter Your lovely nick name: ");
            if(uname!=null)
                client.clientChat(uname);
        }
        if(temp==logoutButton)
        {
            if(this.client.getS()!=null)
                client.logoutSession();
        }
        if(temp==exitButton)
        {
            if(this.client.getS()!=null)
            {
                JOptionPane.showMessageDialog(chatWindow,"u r logged out right now. ","Exit",JOptionPane.INFORMATION_MESSAGE);
                client.logoutSession();
            }
            System.exit(0);
        }
    }

    public JFrame getChatWindow() {
        return chatWindow;
    }

    public void setChatWindow(JFrame chatWindow) {
        this.chatWindow = chatWindow;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(JButton logoutButton) {
        this.logoutButton = logoutButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(JButton exitButton) {
        this.exitButton = exitButton;
    }

    public JTextArea getTxtBroadcast() {
        return txtBroadcast;
    }

    public void setTxtBroadcast(JTextArea txtBroadcast) {
        this.txtBroadcast = txtBroadcast;
    }

    public JTextArea getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(JTextArea txtMessage) {
        this.txtMessage = txtMessage;
    }

    public JList getUsersList() {
        return usersList;
    }

    public void setUsersList(JList usersList) {
        this.usersList = usersList;
    }

}
