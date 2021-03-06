package pl.coderslab.Customer;

import pl.coderslab.dao.service.DbService;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerDao {

    public static Customer findByName(String name) throws Exception{
        String query = "Select name, lastname, date_of_birth from customer WHERE name = ?";
        List<String> params = new ArrayList<>();
        params.add(name);
        try {
            List<String[]> result = DbService.getData(query, params);

            if(result.size()!=0 ){
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(result.get(0)[0]));
                customer.setName(result.get(0)[1]);
                customer.setLastName(result.get(0)[2]);
                customer.setDateOfBirth(result.get(0)[3]);
                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Customer find (String customerName, String customerLastName) throws SQLException{
        String query = "SELECT * FROM customer WHERE name =? AND lastName =?";
        try (Connection conn = DbService.createConn()) {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, customerName);
            st.setString(2, customerLastName);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(0);
                String birthday = rs.getString(3);
                return new Customer(customerName, customerLastName, birthday, id);
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;


    }

    public static void delete (int customerId){
        String query = "DELETE FROM customer WHERE id =?";
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(customerId));

        try {
            DbService.executeQuery(query, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void save(Customer customer) throws Exception{

        String query = "Insert INTO customer VALUES (null, ?, ?, ?)";
        List<String> params = new ArrayList<>();
        params.add(customer.getName());
        params.add(customer.getLastName());
        if(customer.getDateOfBirth()!=null){

            params.add(customer.getDateOfBirth());
        } else {
            params.add(null);
        }
        DbService.insertIntoDatabase(query,params);

    }

    public static List<Customer> printAllCustomers() throws SQLException {
        String query = "SELECT * from customer";

        try (Connection conn = DbService.createConn()) {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                String birthday = rs.getString(4);
                customers.add(new Customer(name, lastName, birthday, id));
            }
            return customers;

        } catch (SQLException e) {
            throw e;
        }
    }

        public static void editCustomer(Customer customer) throws SQLException {
            String query = "Update customer set name=?, lastname=?, date_of_birth=? where id=?";
            List<String> params = new ArrayList<>();
            params.add(customer.getName());
            params.add(customer.getLastName());
            if(customer.getDateOfBirth()!=null){

                params.add(customer.getDateOfBirth());
            } else {
                params.add(null);
            }
            params.add(String.valueOf(customer.getId()));
            DbService.executeQuery(query,params);
    }
}
