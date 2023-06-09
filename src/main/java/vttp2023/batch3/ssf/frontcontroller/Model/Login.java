package vttp2023.batch3.ssf.frontcontroller.Model;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Login implements Serializable {
    
    @NotNull(message="please provide a valid username")
    @Size(min=2, message="please provide a longer username")
    private String username;

    @NotNull(message = "please provide a valid password")
    @Size(min = 2, message = "please provide a longer password")
    private String password;

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
