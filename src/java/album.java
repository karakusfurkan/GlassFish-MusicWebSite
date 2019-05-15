
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
@ManagedBean ( name="album" )
@SessionScoped
public class album {

    /**
     * Creates a new instance of artist
     */
    private int id;
    private String title;
    private String artistName;
    private String imagefile;
    
    public album() {
    }
    public album(int aid) {
        Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("SELECT * FROM artists WHERE id = (?)");
		ps.setInt(1, aid);
               ResultSet rs =  ps.executeQuery();
                rs.next();
                this.id = rs.getInt("id");
                this.title = rs.getString("title");
            
	} catch (SQLException ex) {

		
            } finally {
		DataConnect.close(con);
            }   

    }
    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public void setArtistName(int aid){
        Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("SELECT * FROM artists WHERE id = (?)");
		ps.setInt(1, aid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    this.artistName = rs.getString("name");
                    this.imagefile = rs.getString("image");
                }

	} catch (SQLException ex) {
		
            } finally {
		DataConnect.close(con);
            }   


    }
    public String getArtistName() {
        return this.artistName;
    }
    public String getImagefile() {
        return this.imagefile;
    }
    public List<album> getAlbums()
    {
        List<album> list = new ArrayList<album>();
        //int idindex = (pagenumber - 1) * 10;
        //String betweenSql = "BETWEEN " + idindex + " AND " + idindex + 10 + "";
        //String sql = "SELECT * FROM artists WHERE id " + betweenSql + "";
        String sql = "SELECT * FROM albums";
        ResultSet rs = dbconnection.c.Query(sql);
        try {

            while (rs.next()) {
            album albm = new album();
            albm.setId(rs.getInt("id"));
            albm.setTitle(rs.getString("title"));
            albm.setArtistName(rs.getInt("artistid"));
            list.add(albm);
            } 
        }
        catch(Exception e) {
        e.printStackTrace();
        }

        return list;
    }
    public List<song> getSongsFromAlbum(int albumid)
    {
        List<song> list = new ArrayList<song>();
        String sql = "SELECT * FROM songs WHERE albumid = " + albumid + " ORDER BY id";
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