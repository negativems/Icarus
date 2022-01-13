package net.zargum.plugin.icarus.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class test {

    public static void main(String[] args) {
        //take json as string
        String jsonString = "{"
                + "  \"name\": \"coderolls\","
                + "  \"type\": \"blog\","
                + "  \"address\": {"
                + "    \"street\": \"1600 Pennsylvania Avenue NW\","
                + "    \"city\": \"Washington\","
                + "    \"state\": \"DC\""
                + "  },"
                + "  \"employees\": ["
                + "    {"
                + "      \"firstName\": \"John\","
                + "      \"lastName\": \"Doe\""
                + "    },"
                + "    {"
                + "      \"firstName\": \"Anna\","
                + "      \"lastName\": \"Smith\""
                + "    },"
                + "    {"
                + "      \"firstName\": \"Peter\","
                + "      \"lastName\": \"Jones\""
                + "    }"
                + "  ]"
                + "}";

        System.out.println("Parsing the json string in java using json-simple......\n");

        JSONParser parser = new JSONParser();
        JSONObject coderollsJSONObject = new JSONObject();
        try {
            coderollsJSONObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //now we can access the values
        String name = (String) coderollsJSONObject.get("name");
        System.out.println("Name: "+name+"\n");

        //we can get the JSON object present as value of any key in the parent JSON
        JSONObject addressJSONObject = (JSONObject) coderollsJSONObject.get("address");

        //access the values of the addressJSONObject
        String street = (String) addressJSONObject.get("street");
        System.out.println("Street: "+street+"\n");


        //we can get the json array present as value of any key in the parent JSON
        JSONArray jsonArray = (JSONArray) coderollsJSONObject.get("employees");
        System.out.println("Printing the employess json array: \n"+jsonArray.toString()+"\n");

        //we can get individual json object at an index from the jsonArray
        JSONObject employeeJSONObject = (JSONObject) jsonArray.get(0);
        String firstName = (String) employeeJSONObject.get("firstName");
        System.out.println("First Name of the employee at index 0: "+firstName);

        System.out.println(jsonArray.size());
    }
}
