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

@ManagedBean(name = "contact")
@RequestScoped
public class contact {

    private String name;
    private String email;
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String em) {
        this.email = em;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String sb) {
        this.subject = sb;
    }

    public String add() {
        int i = 0;
        if (name != null) {
            PreparedStatement ps = null;
            Connection con = null;
            try {
                    con = DataConnect.getConnection();
                    if (con != null) {
                        String sql = "INSERT INTO messages(name, email, subject) VALUES(?,?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, name);
                        ps.setString(2, email);
                        ps.setString(3, subject);
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
            return "contact.xhtml";
        } else
            return "unsuccess";
    }

}