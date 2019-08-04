package chatserver.client;

import chatserver.commons.SettingUtils;
import chatserver.gui.ChatGUI;
import chatserver.server.ChatServer;
import java.net.*;
import java.io.*;

public class Client
{
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ChatGUI gui;

    public void logoutSession()
    {
        if(s==null) return;
        try
        {
            dos.writeUTF(ChatServer.LOGOUT_MESSAGE);
            Thread.sleep(500);
            s=null;
        }
        catch(Exception e){
            this.gui.getTxtBroadcast().append("\n inside logoutSession Method"+e);
        }

        this.gui.getLogoutButton().setEnabled(false);
        this.gui.getLoginButton().setEnabled(true);
        this.gui.getChatWindow().setTitle("Login for Chat");
    }


    public void clientChat(String uname)
    {
        try
        {
            s=new Socket(InetAddress.getLocalHost(), SettingUtils.PORT);
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
            ClientThread ct=new ClientThread(dis,this);
            Thread t1=new Thread(ct);
            t1.start();
            dos.writeUTF(uname);
            this.gui.getChatWindow().setTitle(uname+" Chat Window");
        }
        catch(Exception e)
        {
            this.gui.getTxtBroadcast().append("\nClient Constructor " +e);
        }
        this.gui.getLogoutButton().setEnabled(true);
        this.gui.getLoginButton().setEnabled(false);
    }

    public Client()
    {
        this.gui = new ChatGUI(this);
    }


    public static void main(String []args)
    {
        new Client();
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public ChatGUI getGui() {
        return gui;
    }

    public void setGui(ChatGUI gui) {
        this.gui = gui;
    }
}
