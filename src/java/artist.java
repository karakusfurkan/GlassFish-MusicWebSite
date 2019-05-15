
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
@ManagedBean ( name="artist" )
@SessionScoped
public class artist {

    /**
     * Creates a new instance of artist
     */
    private int id;
    private String name;
    private String image;
    
    public artist() {
    }
    public artist(int aid) {
        Connection con = null;
	PreparedStatement ps = null;
	try {
		con = DataConnect.getConnection();
		ps = con.prepareStatement("SELECT * FROM artists WHERE id = (?)");
		ps.setInt(1, aid);
               ResultSet rs =  ps.executeQuery();
                rs.next();
                this.id = rs.getInt("id");
                this.name = rs.getString("name");
                this.image = rs.getString("image");
            
	} catch (SQLException ex) {

		
            } finally {
		DataConnect.close(con);
            }   

    }
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getImage() {
        return this.image;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
    public List<artist> getArtists()
    {
        List<artist> list = new ArrayList<artist>();
        //int idindex = (pagenumber - 1) * 10;
        //String betweenSql = "BETWEEN " + idindex + " AND " + idindex + 10 + "";
        //String sql = "SELECT * FROM artists WHERE id " + betweenSql + "";
        String sql = "SELECT * FROM artists";
        ResultSet rs = dbconnection.c.Query(sql);
        try {

            while (rs.next()) {
            artist artst = new artist();
            artst.setId(rs.getInt("id"));
            artst.setName(rs.getString("name"));
            artst.setImage(rs.getString("image"));
            list.add(artst);
            } 
        }
        catch(Exception e) {
        e.printStackTrace();
        }

        return list;
    }
    public List<song> getSongsFromArtist(int artistid)
    {
        List<song> list = new ArrayList<song>();
        String sql = "SELECT * FROM songs WHERE artistid = " + artistid + " ORDER BY id";
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