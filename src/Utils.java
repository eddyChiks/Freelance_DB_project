package com.Freelance;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {
    public static List<String> getTextual() throws FileNotFoundException,IOException {
        List<String> output=new ArrayList<>();
        String file = "src/textual.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line=reader.readLine()) != null) {
            output.add(line);
        }
        reader.close();
        return output;
    }
    public static List<String> getSql()throws FileNotFoundException,IOException{
        List<String>output = new ArrayList<>();
        String file = "src/sql.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line=reader.readLine()) != null) {
            output.add(line);
        }
        reader.close();
        return output;
    }

    public static List<String>getParams(){
        List<String>params=new ArrayList<>();
        params.add("Job offer ID");
        params.add("Employer ID");
        params.add("Job offer ID");
        params.add("Job offer ID");
        params.add("User ID");
        params.add("Employee ID");
        params.add("");
        params.add("Chat ID");
        params.add("Moderator ID");
        params.add("set of tags");
        params.add("set of tags");
        return params;
    }

    public static List<String> get_Query(String sql,String arg){
        if(arg.length()>0) {
            String query = "";
            int index = sql.indexOf('?');
            int next_index = 0;
            query += sql.substring(0, index);
            while (index >= 0) {
                next_index = sql.indexOf('?', index + 1);
                if (next_index >= 0) {
                    query += arg + sql.substring(index + 1, next_index);
                    index = next_index;
                } else {
                    query += arg + sql.substring(index + 1);
                    break;
                }
            }
            sql=query;
        }
        List<String>queries=new ArrayList<>();
        int semicol=sql.indexOf(';');
        if(semicol<0) queries.add(sql);
        else{queries.add(sql.substring(0,semicol));}
        while(semicol>=0) {
            int next_semicol=sql.indexOf(';',semicol+1);
            if(next_semicol<0){
                queries.add(sql.substring(semicol + 1));
                break;
            }
            queries.add(sql.substring(semicol+1,next_semicol));
            semicol=next_semicol;
        }
        return queries;
    }

    public static String toSQL(String s){
        List<String> list=new ArrayList<>();
        String res="";
        int index =s.indexOf(',');
        if(index<0) list.add(s);
        else list.add(s.substring(0,index));
        while (index>=0){
            int next_index = s.indexOf(',',index+1);
            if (next_index<0){
                list.add(s.substring(index+1));
                break;
            }
            list.add(s.substring(index+1,next_index));
            index=next_index;
        }
        for (int i = 0; i < list.size() ; i++) {
            if (i< list.size()-1) {res+="'"+list.get(i)+"',";}
            else {res+="'"+list.get(i)+"'";}
        }
        return res;
    }

    public static String getInsertQueries(int n){
        List<String>queries=new ArrayList<>();
        String[]tables={"User","Job offer","Service offer"};
        for (int i = 0; i < tables.length; i++) {
            queries.add("insert into \""+tables[i]+"\"("+getAttributes(i)+")");
        }
        return queries.get(n);
    }
    public static String getAttributes(int i){
        String[] attributes = {"\"ID\",name,surname,email,password","\"ID\",\"employer_ID\",\"employee_ID\",status,title,description,application_deadline,salary","\"ID\",\"employee_ID\",title,description,wage"};
        return attributes[i];
    }
}
