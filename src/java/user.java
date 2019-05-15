import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@ManagedBean(name = "user")
@RequestScoped
public class user {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String dbPassword;
    private String dbName;
    DataSource ds;

    public user() {
        
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String add() {
        int i = 0;
        if (firstName != null) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                    con = DataConnect.getConnection();
                    if (con != null) {
                        String sql = "INSERT INTO users(username, firstname, lastname, password) VALUES(?,?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, username);
                        ps.setString(2, firstName);
                        ps.setString(3, lastName);
                        ps.setString(4, password);
                        i = ps.executeUpdate();
                        System.out.println("Data Added Successfully");
                    }
                
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                try {
                    con.close();
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (i > 0) {
            return "login.xhtml";
        } else
            return "unsuccess";
    }

    public void dbData(String uName) {
        if (uName != null) {
            PreparedStatement ps = null;
            Connection con = null;
            ResultSet rs = null;

            if (ds != null) {
                try {
                    con = ds.getConnection();
                    if (con != null) {
                        String sql = "select firstname,password from user where firstname = '"
                                + uName + "'";
                        ps = con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        rs.next();
                        dbName = rs.getString("firstname");
                        dbPassword = rs.getString("password");
                    }
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    public String login() {
        dbData(firstName);
        if (firstName.equals(dbName) && password.equals(dbPassword)) {
            return "output";
        } else
            return "invalid";
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        FacesContext.getCurrentInstance()
                .getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");
    }
}