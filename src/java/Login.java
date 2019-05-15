

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String pwd;
	private String msg;
	private String user;
        private int uid;
        private String firstName;
        private String lastName;

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
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
        public boolean validate(String user, String password) {
            String sql = "SELECT * FROM users WHERE username = '" + user + "' AND password = '" + password + "'";
            //String sql = "SELECT * FROM users";
            ResultSet rs = dbconnection.c.Query(sql);
                
            try {

		if (rs.next()) {
		    this.uid = rs.getInt("id");		//result found, means valid inputs
                    firstName = rs.getString("firstname");
                    lastName = rs.getString("lastname");
                    return true;
		}
            } catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
            }
            return false;
	}
	//validate login
	public String validateUsernamePassword() {
		boolean valid = validate(user, pwd);
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", user);
                        session.setAttribute("userid", uid);
			return "index";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			return "login";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}
}