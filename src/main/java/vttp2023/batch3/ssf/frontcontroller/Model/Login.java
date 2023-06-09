package vttp2023.batch3.ssf.frontcontroller.Model;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Login implements Serializable {
    
    @NotNull(message="please provide a valid username")
    @NotEmpty(message="please provide a valid username")
    @Size(min=2, message="please provide a longer username")
    private String username;

    @NotNull(message = "please provide a valid password")
    @NotEmpty(message = "please provide a valid password")
    @Size(min = 2, message = "please provide a longer password")
    private String password;

    private boolean loginSuccessful;

    @Max(value=3)
    private int loginFails;

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
    public Login() {
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public int getLoginFails() {
        return loginFails;
    }

    public void setLoginFails(int loginFails) {
        this.loginFails = loginFails;
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                .add("username", this.username)
                .add("password", this.password)
                .build();
    }

    public static Login getFromJson (String json) {
        JsonReader jr = Json.createReader(new StringReader (json));
        JsonObject jo = jr.readObject();
        Login l = new Login();
        l.setUsername(jo.getString("username"));
        l.setPassword(jo.getString("password"));
        return l;
    }

    public static Login getFromJson (JsonObject json) {
        Login l = new Login();
        l.setUsername(json.getString("username"));
        l.setPassword(json.getString("password"));
        return l;
    }


    
}
