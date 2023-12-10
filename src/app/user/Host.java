package app.user;

import lombok.Data;

@Data
/**
 * The type Host.
 * This class extends User and is used to create a host.
 */
public class Host extends User {

    public Host() {
    }

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
    }

    /**
     * Switch connection status of users
     * @return
     */
    final public String switchConnectionStatus() {
        return this.getUsername() + "is not a normal user";
    }

}
