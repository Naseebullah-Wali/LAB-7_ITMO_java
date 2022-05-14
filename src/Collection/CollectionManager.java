package Collection;

import Server.utility.DatabaseCollectionManager;
import Server.utility.models.ProductModel;
import common.Exceptions.DatabaseHandlingException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.TreeSet;

public class CollectionManager {

    // private final FileM fileM;

    private final DatabaseCollectionManager databaseCollectionManager;
    TreeSet<ProductModel> products = new TreeSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    public CollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        this.databaseCollectionManager = databaseCollectionManager;

        try {
            loadCollection();
        } catch (DatabaseHandlingException e) {
            e.printStackTrace();
        }
    }

//    public CollectionManager(FileM fileM) {
//        this.lastInitTime = null;
//        this.lastSaveTime = null;
//        this.fileM = fileM;
//
//        loadCollection();
//    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return products.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return products.size();
    }


    public ProductModel getWithMinPrice() {
        return products.stream().min(
                Comparator.comparingLong(value -> value.getProduct().getPrice())
        ).orElse(null);
    }


    public ProductModel getWithMaxPrice() {
        return products.stream().max(Comparator.comparingLong((value -> value.getProduct().getPrice()))).orElse(null);
    }

    public ProductModel getById(int id) {
        return products.stream().filter(product -> product.getProduct().getId() == id).findFirst().orElse(null);
    }

    public double getSumOfPrice() {
        return products.stream()
                .reduce(0.0, (sum, p) -> sum += p.getProduct().getPrice(), Double::sum);
    }

    /**
     * @return Collection content or corresponding string if collection is empty.
     */
    public String showCollection() {
        if (products.isEmpty()) return "Collection is Empty!";
        return products.stream().reduce("", (sum, m) -> sum += m.getProduct() + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }


    public ProductModel addToCollection(ProductModel product) {
        products.add(product);
        return product;
    }


    public boolean removeFromCollection(ProductModel product) {
        return products.remove(product);
    }

    /**
     * Clears the collection.
     * @return
     */
    public ProductModel clearCollection() {
        products.clear();
        return null;
    }


    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        //fileM.writeCSV(products);//.writeCollection(marinesCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() throws DatabaseHandlingException {
        //  products = fileM.readCSV();//.readCollection();
        System.out.println("problem in load method in collection class");
        databaseCollectionManager.getCollection();
        lastInitTime = LocalDateTime.now();
    }


}





