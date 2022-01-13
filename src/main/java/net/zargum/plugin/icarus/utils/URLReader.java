package net.zargum.plugin.icarus.utils;

import com.google.gson.JsonArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class URLReader {

    public static JsonArray getJson(String url) throws IOException, ParseException {
        URL seatURL = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(seatURL.openStream(), Charset.forName("UTF-8")));

        String readAPIResponse;
        StringBuilder line = new StringBuilder();
        while((readAPIResponse = br.readLine()) != null){
            line.append(readAPIResponse);
        }

        JSONParser parser = new JSONParser();
        Object obj  = parser.parse(line.toString());

        return (JsonArray) obj;
    }

    public static boolean getBoolean(String url) throws IOException {
        URL seatURL = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(seatURL.openStream(), Charset.forName("UTF-8")));

        String readAPIResponse;
        StringBuilder line = new StringBuilder();
        while((readAPIResponse = br.readLine()) != null){
            line.append(readAPIResponse);
        }

        return Boolean.parseBoolean(line.toString());
    }

}
