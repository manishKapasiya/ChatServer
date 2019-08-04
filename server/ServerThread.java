package chatserver.server;

import java.net.Socket;
import java.util.*;
import java.io.*;

public class ServerThread implements Runnable
{
    private Socket s;
    private List<Socket> ssList;
    private List<String> users;
    private String userName;
    ///////////////////////
    public ServerThread (Socket s, List<Socket> ssList,List<String> users)
    {
        this.s=s;
        this.ssList=ssList;
        this.users=users;
        try{
            DataInputStream dis=new DataInputStream(s.getInputStream());
            this.userName=dis.readUTF();
            ssList.add(s);
            users.add(this.userName);
            tellEveryOne("****** "+ this.userName+" Logged in at "+(new Date())+" ******");
            sendNewUserList();
        }
        catch(Exception e)
        {
            System.err.println("ServerThread constructor  "+e);
        }
    }
    ///////////////////////
    public void run()
    {
        String s1;
        try
        {
            DataInputStream dis=new DataInputStream(s.getInputStream());
            do
            {
                s1=dis.readUTF();
                if(s1.equalsIgnoreCase(ChatServer.LOGOUT_MESSAGE))
                    break;
                tellEveryOne(this.userName+" said: "+" : "+s1);
            }while(true);

            DataOutputStream tdos=new DataOutputStream(s.getOutputStream());
            tdos.writeUTF(ChatServer.LOGOUT_MESSAGE);
            tdos.flush();
            users.remove(this.userName);
            tellEveryOne("****** "+this.userName+" Logged out at "+(new Date())+" ******");
            sendNewUserList();
            ssList.remove(s);
            s.close();

        }
        catch(Exception e)
        {
            System.out.println("ServerThread Run"+e);
        }
    }

    ////////////////////////
    public void sendNewUserList()
    {
        tellEveryOne(ChatServer.UPDATE_USERS+users.toString());

    }

    ////////////////////////
    public void tellEveryOne(String s1)
    {
        Iterator<Socket> i=ssList.iterator();
        while(i.hasNext())
        {
            try
            {
                Socket temp=i.next();
                DataOutputStream dos=new DataOutputStream(temp.getOutputStream());
                dos.writeUTF(s1);
                dos.flush();
            }
            catch(Exception e)
            {
                System.err.println("TellEveryOne "+e);
            }
        }
    }

}