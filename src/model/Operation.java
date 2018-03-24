package model;

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
}
