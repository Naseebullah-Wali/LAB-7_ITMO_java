//package Collection;
//
//import Data.*;
//
//import java.io.*;
//import java.time.LocalDate;
//import java.util.TreeSet;
//
//public class FileM {
//    public String line = "";
//
//    public TreeSet<Product> readCSV() throws IOException, NumberFormatException {
//      //  String line = "";
//
//        TreeSet<Product> products = new TreeSet<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader("src/Collection/file.csv_2.txt"));) {
//            while ((line = reader.readLine()) != null) {
//                String[] row = line.split(",");
//
//                Location location = new Location(
//                        Double.parseDouble(row[14]),
//                        Integer.parseInt(row[15]),
//                        Float.parseFloat(row[16]),
//                        row[17]);
//                Address address = new Address(
//                        row[12],
//                        row[13],
//                        location
//                );
//                Organization organization = new Organization(
//                        Integer.parseInt(row[8]),
//                        row[9],
//                        row[10],
//                        OrganizationType.valueOf(row[11]),
//                        address
//                );
//                Coordinates coordinates = new Coordinates(
//                        Float.parseFloat(row[3]),
//                        Double.parseDouble(row[4])
//                );
//                try {
//
//
//                Product product = new Product (
//                        Integer.parseInt(row[0]),
//                           row[1],
//                        LocalDate.parse(row[2].replace("/", "-")),
//                        coordinates,
//                        Integer.parseInt(row[5]),
//                        row[6],
//                        UnitOfMeasure.valueOf(row[7]),
//                        organization
//                );
//                products.add(product);}
//                catch (NumberFormatException e){
//                    System.out.println("wrong format");
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        products.forEach(System.out::println);
//        return products;
//    }
//
//    public void writeCSV(TreeSet<Product> products) {
//        String filename = "src/Collection/file.csv_2.txt";
//
//        line = "";
//        products.forEach(product -> {
//            line += product.getId() + ",";
//            line += product.getName() + ",";
//            line += product.getCreationDate() + ",";
//            Coordinates coordinates = product.getCoordinates();
//            line += coordinates.getX() + "," + coordinates.getY() + ",";
//            line += product.getPrice() + ",";
//            line += product.getPartNumber() + ",";
//            line += product.getUnitOfMeasure().name() + ",";
//            Organization m = product.getManufacturer();
//            line += m.getId() + ",";
//            line += m.getName() + ",";
//            line += m.getFullname() + ",";
//            line += m.getType().name() + ",";
//            Address a = m.getOfficialAddress();
//            line += a.getStreet() + ",";
//            line += a.getZipcode() + ",";
//            Location l = a.getTown();
//            line += l.getX() + ",";
//            line += l.getY() + ",";
//            line += l.getZ() + ",";
//            line += l.getName() + "\n";
//        });
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
//            writer.write(line);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
