
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ihsan
 */
public class dbconnection {
    public static final DB c = new DB();
}
class DB {
    private Connection  connection  = null;
    private Statement   statement   = null;
    private ResultSet   set         = null;
    String login = "abc";
    String password = "123";
    String url;
    public DB () {
        url = "jdbc:derby://localhost:1527/mpotify";
        Baglanti();
    }
    private void Baglanti() {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            System.out.println("hata1" + e);
        }
    }
    public ResultSet Query(String query){
        try {
            statement = connection.createStatement();
            set = statement.executeQuery(query);
        }
        catch (Exception e) {
            System.out.println("hata2" + e);
        }
        return set;
    }
    public boolean Update(String update) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(update);
        }
        catch (SQLException e) {
            System.out.println("hata3" + e);
            return false;
        }
        return true;
    }
}
