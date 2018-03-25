package model;

import java.util.ArrayList;

public class Operation {



    public boolean userLogin(String username, String pwd, Connections con) {

        return true;
    }

    public boolean deleteCustomer(String customerID) {

        return false;
    }

    public boolean deleteSeller(String sellerID) {

        return false;
    }

    public boolean addProduct(String productID, String quantity, String brand, String price, String name, String category) {
        return false;
    }

    public boolean deleteProduct(String productID) {

        return false;
    }


    public boolean updatePrice(String productID, String price) {
        return false;
    }


    // return -1 if there is any trouble
    // Otherwise return the totals sales of a product within a given time
    public int salesByProduct(String productID, String startDate, String endDate) {

        return -1;
    }

    // return a 2D string arrayList for all the products information
    // array[i][] represents the strings for one column, like productID,category
    // array[][j] represents the strings for one row, like all the attributes for one product
    public ArrayList<ArrayList<String>> viewProduct() {

        return new ArrayList<ArrayList<String>>();
    }
}
