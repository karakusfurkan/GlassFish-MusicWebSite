
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
@ManagedBean ( name="search" )
@SessionScoped
public class search {
    private String searchText;
    private List <song> songs;

    public String getSearchText(){
        return this.searchText;
    }
    public void setSearchText(String stext) {
        this.searchText = stext;
    }
    public List <song> getSongs() {
        return this.songs;
    }
    public void setSongs(List <song> sng) {
        this.songs = sng;
    }
    public String doSearch()
    {
        Connection con = null;
	PreparedStatement ps = null;
        this.songs = new ArrayList<song>();
	try {
		con = DataConnect.getConnection();
		//ps = con.prepareStatement("SELECT * FROM songs WHERE (title LIKE ? OR title LIKE ? OR title LIKE ?)");
                ps = con.prepareStatement("SELECT * FROM songs WHERE (LOWER(title) LIKE LOWER(?))");
		ps.setString(1, '%'+ searchText + '%');
                //ps.setString(2, '%'+searchText);
               // ps.setString(3, searchText + '%');
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    song sng = new song();
                    sng.setId(rs.getInt("id"));
                    sng.setTitle(rs.getString("title"));
                    sng.setImagefile(rs.getInt("artistid"));
                    songs.add(sng);
                }

	} catch (SQLException ex) {
		
            } finally {
		DataConnect.close(con);
                return "search";
                
            }   
       
    }

}
