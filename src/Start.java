import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

/**
 * Created by snid1 on 04.06.2015.
 */
public class Start {
    //DB
    protected static Connection bd;
    protected static Statement st;

    static SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm:ss; d MMM yyyy; E");
    static Map <Integer, String> mapConvers = new HashMap <Integer, String>(); //id`s and names of files
    public static int badNamesCounter = 0;


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        bd = DriverManager.getConnection("jdbc:sqlite:main.db");
        st = bd.createStatement();
        new File("results").mkdir();

        // get id`s and names of files
        ResultSet convers = st.executeQuery("SELECT id, displayname FROM Conversations WHERE is_blocked = '0' OR type = '2'");
        while (convers.next()){
            mapConvers.put(convers.getInt(1),convers.getString("displayname"));
        }

        //FOR EACH FILE
        for (Map.Entry<Integer, String> entry : mapConvers.entrySet()) {
            //open file, iter throug result, create-write to buffer, write to file, save
            String fileName = entry.getValue();
            if (fileName != null && !fileName.isEmpty())
                fileName = fileName.replace("\\","")+".txt";
            else{
                fileName = "Noname dialog #"+badNamesCounter+".txt";
                badNamesCounter++;}

            ResultSet chat = st.executeQuery("SELECT timestamp, from_dispname, body_xml FROM Messages" +
                    " WHERE convo_id = '"+ entry.getKey() +"' AND chatmsg_type ='3'");
            StringBuffer dialog = new StringBuffer();
            // FOR EACH MESSAGE
            while (chat.next()){
                dialog.append("-- "+chat.getString("from_dispname")+ " -- " + dateFormat.format(new Date((chat.getLong("timestamp")*1000))));
                dialog.append(System.getProperty("line.separator"));
                dialog.append(chat.getString("body_xml"));
                dialog.append(System.getProperty("line.separator"));
                dialog.append(System.getProperty("line.separator"));
            }

            if (dialog.toString().isEmpty()){
                System.out.print(dialog.toString());
                continue;}

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("results/"+fileName));
                out.write(dialog.toString());
                out.flush();
                out.close();
            }catch (IOException e){
                System.out.print("cannot write to file "+ fileName);
                e.printStackTrace();
            }
        }
    }
}
