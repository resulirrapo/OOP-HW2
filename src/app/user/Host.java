package app.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Host extends User {

    public Host() {
    }

    public Host(String username, int age, String city) {
        super(username, age, city);
    }

    public String switchConnectionStatus() {
        return this.getUsername() + "is not a normal user";
    }

}
