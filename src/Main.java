package com.Freelance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[]args) {
        Connection c = connectDB.connect();
        Scanner scanner = new Scanner(System.in);
        List<String> query_spec = new ArrayList<>();
        List<String> query_sql = new ArrayList<>();
        List<String> params = new ArrayList<>();
        params = Utils.getParams();
        try {
            query_spec = Utils.getTextual();
            query_sql = Utils.getSql();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Choose operation:\n1:Insert\n2:Select");
        int number = scanner.nextInt();
        scanner.nextLine();
        if (number == 1) {
            System.out.println("Choose table:\n1:User\n2:Job offer\n3:Service offer");
            int table=scanner.nextInt();
            scanner.nextLine();
            String attributes= Utils.getAttributes(table-1);
            System.out.println("Enter following fields:"+attributes);
            String values=scanner.nextLine();
            try {
                Statement st = c.createStatement();
                String query= Utils.getInsertQueries(table-1)+"values("+values+")";
                //System.out.println(query);
                st.execute(query);
                System.out.println("Insert successfully completed");
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Choose query number:");
            for (int i = 1; i < query_spec.size(); i++) {
                System.out.println("\t" + i + ": " + query_spec.get(i - 1));
            }
            String arg = "";
            number = scanner.nextInt();
            scanner.nextLine();
            if (params.get(number - 1).length() > 0) {
                System.out.println("Enter: " + params.get(number - 1));
                arg = scanner.nextLine();
            }
            scanner.close();
            if (number == 10 || number == 11) {
                arg = Utils.toSQL(arg);
            }
            try {
                Statement st = c.createStatement();
                List<String> queries = Utils.get_Query(query_sql.get(number - 1), arg);
                for (int i = 0; i < queries.size() - 1; i++) {
                    st.execute(queries.get(i));
                }
                ResultSet set = st.executeQuery(queries.get(queries.size() - 1));
                int columns = set.getMetaData().getColumnCount();
                for (int i = 1; i <=columns; i++) {
                    System.out.print(set.getMetaData().getColumnName(i)+" ");
                }
                System.out.println("");
                while (set.next()) {
                    for (int i = 1; i <= columns; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = set.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println("");
                }
                set.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
