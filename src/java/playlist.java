
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
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ihsan
 */
@ManagedBean ( name="playlist" )
@SessionScoped
public class playlist {

    /**
     * Creates a new instance of artist
     */
    private int id;
    private int songid;
    private int userid;
    
    public playlist() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
        public int getSongid() {
        return this.id;
    }
    public void setSongid(int id) {
        this.id = id;
    }
        public int getUserid() {
        return this.id;
    }
    public void setUserid(int id) {
        this.id = id;
    }
    public String addSong(int sid) {
        Connection con = null;
	PreparedStatement ps = null;
        HttpSession session = SessionUtils.getSession();
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("INSERT INTO playlists (userid,songid) VALUES(?,?)");
		ps.setInt(1, userid);
                ps.setInt(2, sid);
                ps.executeUpdate();

	} catch (SQLException ex) {
            return ex.getMessage();
		
            } finally {
		DataConnect.close(con);
            }   
        return "playlist";
    }
    public String removeSong(int pid) {
        Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("DELETE FROM playlists WHERE id = ?");
		ps.setInt(1, pid);
                ps.executeUpdate();

	} catch (SQLException ex) {
            return ex.getMessage();
		
            } finally {
		DataConnect.close(con);
            }   
        return "playlist";
    }

    public List<song> getSongsFromPlaylist()
    {
        List<song> list = new ArrayList<song>();
        HttpSession session = SessionUtils.getSession();
        int userid = Integer.parseInt(session.getAttribute("userid").toString());
        String sql = "SELECT * FROM playlists WHERE userid = " + userid + "";
        ResultSet rs = dbconnection.c.Query(sql);
        try {

            while (rs.next()) {
                song sng = new song();
                sng.setSong(rs.getInt("songid"));
                sng.setPlaylistid(rs.getInt("id"));
                list.add(sng);
            } 
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}