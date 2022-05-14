package common.Data;

import java.io.Serializable;

public class Location implements Serializable {
    private Double x;
    private Integer y;
    private Float z;
    private String name;

   public Location ( Double x, Integer y, Float z, String name){
        this.x=x;
        this.y=y;
        this.z=z;
        this.name=name;
    }

    public Integer getY(){
        return y;
    }
    public Double getX(){
        return x;
    }
    public Float getZ(){
        return z;
    }
    public String getName(){
        return name;
    }

    public String toString() {
        String info = "Related to Loaction information\n";
        info += ("Location Info: \n");
        info += ("             Location for x: " + x + '\n');
        info += ("             Location for y: " + y + '\n');
        info += ("             Loaction for z: " + z+ '\n');
        info += ("             Name: " + name+ '\n');
        return info;}}