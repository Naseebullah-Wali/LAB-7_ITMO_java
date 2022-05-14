package common.Data;

import Server.utility.models.ProductModel;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Product extends Organization implements Comparable<Product>, Serializable {

//    User ower;
    private int id;//Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null,
    // Значение этого поля должно генерироваться автоматически
    private int price; //Значение поля должно быть больше 0
    private String partNumber; //Поле не может быть null
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Organization manufacturer; //Поле не может быть null

    public Product(
            int id,
            String name,
            LocalDateTime creationDate,
            Coordinates coordinates,
            int price,
            String partNumber,
            UnitOfMeasure unitOfMeasure,
            Organization manufacturer
//            User owner
    ) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.price = price;
        this.partNumber = partNumber;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
//        this.ower = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getPrice() {
        return price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Organization getManufacturer() {
        return manufacturer;
    }


    /**
     * @return
     */
    @Override
    public String toString() {
        String info = "Lets Have Information of Our Products\n";
        info += ("Name: " + name + '\n');
        info += ("ID: " + id + '\n');
        info += (coordinates.toString());
        info += ("creationDate: " + creationDate + '\n');
        info += ("Price: " + price + '\n');
        info += ("PartNumber:  " + partNumber + '\n');
        info += ("unit of measurement: " + unitOfMeasure + '\n');
        info += manufacturer.toString();
        return info;
    }

    public int compareTo(ProductModel product) {
        return (getId() - product.getProduct().getId());
    }

    @Override
    public int compareTo(Product o) {
        return 0;
    }
}




