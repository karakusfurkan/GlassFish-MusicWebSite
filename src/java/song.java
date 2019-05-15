
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.swing.FilePane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ihsan
 */
@ManagedBean ( name="song" )
@SessionScoped
public class song {
    private int id;
    private String filename;
    private String title;
    private int index;
    private String imagefile;
    private int artistid;
    private int playlistid;

    public int getPlaylistid(){
        return this.playlistid;
    }
    public void setPlaylistid(int pid) {
        this.playlistid = pid;
    }
    public int getId(){
        return this.id;
    }
    public String getFilename(){
        return this.filename;
    }
    public String getTitle(){
        return this.title;
    }
     public void setId(int id) {
        this.id = id;
    }
      public void setFilename(String filename) {
        this.filename = filename;
    }
       public void setTitle(String title) {
        this.title = title;
    }
    public void setImagefile(String imagefile) {
        this.imagefile = imagefile;
    }
    public void setImagefile(int aid) {
                Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("SELECT * FROM artists WHERE id = (?)");
		ps.setInt(1, aid);
                ResultSet rs =  ps.executeQuery();
                rs.next();
                this.imagefile = rs.getString("image");
            
	} catch (SQLException ex) {

		
            } finally {
		DataConnect.close(con);
            } 
    }
    public String getImagefile(){
        return this.imagefile;
    }
    public void setSong(int id) {
        String sql = "SELECT * FROM songs WHERE id = " + id + "";
        ResultSet rs = dbconnection.c.Query(sql);
        try {
             rs.next();
             this.id = rs.getInt("id");
             this.title = rs.getString("title");
             this.setImagefile(getImageFile(rs.getInt("artistid")));   
        }
        catch (SQLException e) {
            
        }
    }
    public int getAlbumId(int songid)
    {
      String sql = "SELECT * FROM songs WHERE id = " + songid + "";
        ResultSet rs = dbconnection.c.Query(sql);
        int ret = 0;
        try {
            rs.next();
            ret = rs.getInt("albumid");
        }
        catch (SQLException e) {
            
        }
        return ret;
    }
    public int getIndex(){
        return this.index;
    }
    public song getSong(int songid)
    {
        song sng = new song();
        String sql = "SELECT * FROM songs WHERE id = " + songid + "";
        ResultSet rs = dbconnection.c.Query(sql);
        
        int i = 0;
        try {
            while (rs.next()){
                sng.setId(rs.getInt("id"));
                sng.setTitle(rs.getString("title"));
                
            }

        }
        catch (SQLException e) {
            
        }
        return sng;
    }
    public void increaseCount(int id){
        Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("UPDATE songs SET listencount = listencount + 1 WHERE id = (?)");
		ps.setInt(1, id);
                ps.executeUpdate();

            
	} catch (SQLException ex) {

		
            } finally {
		DataConnect.close(con);
            } 
        
    }
    public String getJson(int id, String playlistid)
    {
        if (id == 0)
            return null;
        String page = FacesContext.getCurrentInstance().getViewRoot().getViewId();  
        JSONObject jo = new JSONObject(); 
        HttpSession session = SessionUtils.getSession();
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        int albumid = getAlbumId(id);
        String sql = "SELECT * FROM songs WHERE albumid = " + albumid + "";
        if (page.contains("playlist")) {
            sql = "SELECT * FROM songs JOIN playlists on songid = songs.id WHERE playlists.userid = " + userid + "";
        }
        ResultSet rs = dbconnection.c.Query(sql);
        String ret = ""; 
        JSONArray ja = new JSONArray();
        increaseCount(id);
        int i = 0;
        try {
            while (rs.next()){
                 Map m = new LinkedHashMap();
                 m.put("title", rs.getString("title"));
                 m.put("file", rs.getString("filename"));
                 if (rs.getInt("id") == id)
                     this.index = i;
                 i++;
                 ja.add(m);
            }

        }
        catch (SQLException e) {
            ret =" "+ e.getMessage();
        }
        //jo.put("liste", ja); 
      //  return ret + i;
        //return jo.toJSONString();
        return ja.toJSONString();
    }
    public String getImageFile(int aid){
        Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("SELECT * FROM artists WHERE id = (?)");
		ps.setInt(1, aid);
                ResultSet rs =  ps.executeQuery();
                rs.next();
                return rs.getString("image");
            
	} catch (SQLException ex) {

		
            } finally {
		DataConnect.close(con);
            } 
        return "";
    }
    public List<song> getHitSongs()
    {
        List<song> list = new ArrayList<song>();
        String sql = "SELECT * FROM songs ORDER BY listencount DESC FETCH FIRST 20 ROWS ONLY";
        ResultSet rs = dbconnection.c.Query(sql);
        try {

            while (rs.next()) {
            song sng = new song();
            sng.setId(rs.getInt("id"));
            sng.setTitle(rs.getString("title"));
            sng.setImagefile(rs.getInt("artistid"));
            list.add(sng);
            } 
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
