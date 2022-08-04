package org.example;
import com.opencsv.CSVReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
public class App {

    private static final String csvPath = "/home/arus/Documents/boc_branches/Bank Branch Directory.xlsx - Branch NEW.csv";
    private static JSONArray array = new JSONArray();

    public static void compare(){
        CSVReader reader = null;
        try
        {
            reader = new CSVReader(new FileReader(csvPath));
            String [] nextLine;
            int i = 0;
            JSONObject object = null;
            while ((nextLine = reader.readNext()) != null)
            {
                if(nextLine[1].isEmpty()){
                    nextLine = reader.readNext();
                }
                if(nextLine[2].isEmpty()&&!nextLine[1].isEmpty()){
                    if(object != null){
                        array.add(object);
                    }
                    object = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    object.put("bank_name",nextLine[1]);
                    nextLine = reader.readNext();
                    object.put("bank_code",nextLine[1]);
                    object.put("branches",jsonArray);
                }
                JSONObject branch = new JSONObject();
                branch.put("branch_code",nextLine[2]);
                branch.put("branch_name",nextLine[3]);
                branch.put("address",nextLine[4]);
                branch.put("tel_no1",nextLine[5]);
                branch.put("tel_no2",nextLine[6]);
                branch.put("tel_no3",nextLine[7]);
                branch.put("tel_no4",nextLine[8]);
                branch.put("fax",nextLine[9]);
                branch.put("district",nextLine[10]);
                JSONArray arr = (JSONArray) object.get("branches");
                arr.add(branch);
            }
            array.add(object);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void compareJson(){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("/home/arus/Documents/boc_branches/bank_branches.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;

            for(int i = 0;i< employeeList.size();i++){
                JSONObject object1 = (JSONObject) employeeList.get(i);
                JSONObject object2 = (JSONObject) array.get(i);
                JSONArray arr1 = (JSONArray) object1.get("branches");
                JSONArray arr2 = (JSONArray) object2.get("branches");
                for(int j = 0;j<arr1.size();j++){
                    JSONObject a = (JSONObject) arr1.get(j);
                    JSONObject b = (JSONObject) arr2.get(j);
                    System.out.println(a.get("tel_no3"));
                    System.out.println(b.get("tel_no3"));
                    System.out.println();
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void main( String[] args )
    {
        compare();
        compareJson();
    }
}
