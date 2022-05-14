package common.Data;

import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private String zipcode;
    private Location town;

    public Address(String street, String zipcode, Location town){
        this.street=street;
        this.zipcode=zipcode;
        this.town=town;
    }

    public String getStreet(){
        return street;
    }
    public String getZipcode(){
        return zipcode;
    }
    public Location getTown(){
        return town;
    }
    @Override
    public String toString() {
        String info = "Related to Address\n";
        info += ("Address: \n");
        info += ("             street: " + street + '\n');
        info += ("             zipcode: " + zipcode + '\n');
        info += ("             town: " + town+ '\n');
        return info;


    }
}
