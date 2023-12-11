package app.user;

import lombok.Data;

@Data
public class Merch {
    private String name;
    private Integer price;
    private String description;

    public Merch(final String name, final String description, final Integer price) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
