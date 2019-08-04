package chatserver.server;

import chatserver.commons.SettingUtils;

import java.net.*;
import java.util.*;
/*

 **/
public class ChatServer
{

    private ServerSocket ss;
    private Socket s;
    private List<Socket> ssList=new ArrayList<>();
    private List<String> users=new ArrayList<>();



    public final static String UPDATE_USERS="updateuserslist:";
    public final static String LOGOUT_MESSAGE="@@logoutme@@:";

    public ChatServer()
    {
        try{
            ss=new ServerSocket(SettingUtils.PORT);
            System.out.println("Geeks Chat Server Started "+ss);
            while(true)
            {
                s=ss.accept();
                Runnable runnable=new ServerThread(s,ssList,users);
                Thread serverThread=new Thread(runnable);
                serverThread.start();
            }
        }
        catch(Exception e)
        {
            System.err.println("Chat Server constructor"+e);
        }
    }

    /////////////////////////
    public static void main(String [] args)
    {
        new ChatServer();
    }

    /////////////////////////
}