package chatserver.client;

import chatserver.server.ChatServer;

import java.util.*;
import java.io.*;

/*************************/


/*********************************/
class ClientThread implements Runnable
{
    DataInputStream dis;
    Client client;

    ClientThread(DataInputStream dis,Client client)
    {
        this.dis=dis;
        this.client=client;
    }
    ////////////////////////
    public void run()
    {
        String s2="";
        do
        {
            try{
                s2=dis.readUTF();
                if(s2.startsWith(ChatServer.UPDATE_USERS))
                    updateUsersList(s2);
                else if(s2.equals(ChatServer.LOGOUT_MESSAGE))
                    break;
                else
                    client.getGui().getTxtBroadcast().append("\n"+s2);
                int lineOffset=client.getGui().getTxtBroadcast().getLineStartOffset(client.getGui().getTxtBroadcast().getLineCount()-1);
                client.getGui().getTxtBroadcast().setCaretPosition(lineOffset);
            }
            catch(Exception e){client.getGui().getTxtBroadcast().append("\nClientThread run : "+e);}
        }
        while(true);
    }
    //////////////////////////
    public void updateUsersList(String ul)
    {
        Vector ulist=new Vector();

        ul=ul.replace("[","");
        ul=ul.replace("]","");
        ul=ul.replace(ChatServer.UPDATE_USERS,"");
        StringTokenizer st=new StringTokenizer(ul,",");

        while(st.hasMoreTokens())
        {
            String temp=st.nextToken();
            ulist.add(temp);
        }
        client.getGui().getUsersList().setListData(ulist);
    }
/////////////////////////
}
/*********************************/

