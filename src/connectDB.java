package com.Freelance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    public static Connection connect(){
        Connection c = null;
        String url = "jdbc:postgresql://localhost/Freelance_Platform";
        String user = "postgres";
        String password = "123";
        try {
            c= DriverManager.getConnection(url,user,password);
            System.out.println("Successful connection!");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return c;
    }
}
