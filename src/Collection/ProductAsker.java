package Collection;
import common.Data.*;
import common.Exceptions.EmptyIO;
import common.Exceptions.WrongFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ProductAsker {

    private Random random = new Random();


    public final Scanner scanner;
//    private Object FileManager_1;


    public ProductAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    public Product createProduct() {
        return createProduct(random.nextInt(1_000_000));
    }

    public Product createProduct(int id) {
        String name = null;
        while (name == null) {
            try {
                System.out.println("Product Name: ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(";")) throw  new WrongFormat();
                name = nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }

        float x;
        while (true) {
            try{
                System.out.println("Write coordinate X: ");
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (name.equals("")) throw new EmptyIO();
                x = Float.parseFloat(st);
                break;
            }
            catch (EmptyIO e)
            {
                System.out.println("Cant be Empty");
            }catch (NumberFormatException exception) {
                System.out.println("should be on reqired format!");
            }catch (NoSuchElementException exception) {
                System.out.println("NO Such Element!");
            }
        }

        float y;
        while (true) {
            try{
                System.out.println("Write Corrdinate Y: ");
                String st =scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                y = Float.parseFloat(st);
                break;
            }
            catch (EmptyIO e)
            {
                System.out.println("Cant be Empty");
            }catch (NumberFormatException exception) {
                System.out.println("should be on reqired format!");
            }catch (NoSuchElementException exception) {
                System.out.println("NO Such Element!");
            }
        }

        int price;
        while (true) {
            try{
                System.out.println("Enter the Price of Your Product: ");
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                price = Integer.parseInt(st);
                if (price < 0 || price > 191928932) throw new WrongFormat();
                break;
            }
            catch (EmptyIO e)
            {
                System.out.println("Cant be Empty");
            }catch (WrongFormat e){
                System.out.println("Incorrect Format, Number should be bigger then 0 and smaller then 191928932");
            }catch (NumberFormatException exception) {
                System.out.println("Please write in right Format");
            }catch (NoSuchElementException exception) {
                System.out.println("No Such Element");
            }
        }

        String partNumber = null;
        while (partNumber == null) {
            try{
                System.out.println("Write the PartNumber: ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(";")) throw  new WrongFormat();
                partNumber = nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }
        UnitOfMeasure unitOfMeasure;
        while (true) {
            try{
                System.out.println("Write Unit of Measurement:");
                System.out.println(java.util.Arrays.asList(UnitOfMeasure.values()));
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                unitOfMeasure = UnitOfMeasure.valueOf(st);
                break;
            }
            catch (EmptyIO e) {
                System.out.println("Cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("NO Such Element!");
            }catch (IllegalArgumentException exception) {
                System.out.println("NOt in the List!");
            }
        }


        String mname = null;
        while (mname == null) {
            try{
                System.out.println("Wrtie the Name of Manufacturar ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(",")) throw  new WrongFormat();
                mname = nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }
        String fulname = null;
        while (fulname == null) {
            try{
                System.out.println("Write Fullname of Manufacturar ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(";")) throw  new WrongFormat();
                fulname = nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }

        OrganizationType organizationType;
        while (true) {
            try{
                System.out.println("Write Type of Organization: ");
                System.out.println(java.util.Arrays.asList(OrganizationType.values()));
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                organizationType=OrganizationType.valueOf(st);
                break;
            }
            catch (EmptyIO e) {
                System.out.println("Cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("NO Such Element!");
            }catch (IllegalArgumentException exception) {
                System.out.println("NOt in the List!");
            }
        }
        String street = null;
        while (street == null) {
            try{
                System.out.println("Wrtie Address");
                System.out.println("Write the street Name: ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(";")) throw  new WrongFormat();
                street = nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }
        String zipcode = null;
        while (zipcode == null) {
            try{
                System.out.println("Write ZIPCODE: ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(";")) throw  new WrongFormat();
                zipcode= nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }
        Double tx;
        while (true) {
            try{
                System.out.println("Enter information about Town");
                System.out.println("Enter x(double) of your Town: ");
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                tx = Double.parseDouble(st);
                if (tx < 0 || tx > 191928932) throw new WrongFormat();
                break;
            }
            catch (EmptyIO e)
            {
                System.out.println("Cant be Empty");
            }catch (WrongFormat e){
                System.out.println("Incorrect Format, Number should be bigger then 0 and smaller then 191928932");
            }catch (NumberFormatException exception) {
                System.out.println("Please write in right Format");
            }catch (NoSuchElementException exception) {
                System.out.println("No Such Element");
            }
        }
        Integer ty;
        while (true) {
            try{
                System.out.println("Enter y of your Town: ");
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                ty = Integer.parseInt(st);
                if (ty < 0 || ty > 191928932) throw new WrongFormat();
                break;
            }
            catch (EmptyIO e)
            {
                System.out.println("Cant be Empty");
            }catch (WrongFormat e){
                System.out.println("Incorrect Format, Number should be bigger then 0 and smaller then 191928932");
            }catch (NumberFormatException exception) {
                System.out.println("Please write in right Format");
            }catch (NoSuchElementException exception) {
                System.out.println("No Such Element");
            }
        }
        float tz;
        while (true) {
            try{
                System.out.println("Enter z of your Town: ");
                String st = scanner.nextLine().trim();
                if (st == null) throw new EmptyIO();
                if (st.equals("")) throw new EmptyIO();
                tz = Float.parseFloat(st);
                if (tz < 0 || tz > 191928932) throw new WrongFormat();
                break;
            }
            catch (EmptyIO e)
            {
                System.out.println("Cant be Empty");
            }catch (WrongFormat e){
                System.out.println("Incorrect Format, Number should be bigger then 0 and smaller then 191928932");
            }catch (NumberFormatException exception) {
                System.out.println("Please write in right Format");
            }catch (NoSuchElementException exception) {
                System.out.println("No Such Element");
            }
        }
        String tname = null;
        while (tname == null) {
            try{
                System.out.println("Write the Town Name: ");
                String nm = scanner.nextLine().trim();
                if (nm == null) throw new EmptyIO();
                if (nm.equals("")) throw new EmptyIO();
                if (nm.contains(";")) throw  new WrongFormat();
                tname = nm;
            }
            catch (EmptyIO e)
            {
                System.out.println("Please write sth cant be Empty");
            }catch (NoSuchElementException exception) {
                System.out.println("Couldnt understand! ");
            } catch (WrongFormat wrongFormat) {
                System.out.println("Dont use \";\"");
            }
        }

        return new Product(
                id,
                name,
                LocalDateTime.now(),
                new Coordinates(x, y),
                price,
                partNumber,
                unitOfMeasure,
                new Organization(
                        random.nextInt(1_000_000),
                        mname,
                        fulname,
                        organizationType,
                        new Address(
                                street,
                                zipcode,
                                new Location(
                                        tx,
                                        ty,
                                        tz,
                                        tname
                                )
                        )
                )
        );
    }
}
