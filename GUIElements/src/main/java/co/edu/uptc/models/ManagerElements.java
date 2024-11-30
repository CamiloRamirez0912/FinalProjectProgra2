package co.edu.uptc.models;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.uptc.dto.ElementDto;

public class ManagerElements {
    private int statusCode;
    private String responseString;

    public void loadElements() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/elements/all")).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();
            responseString = response.body();
            if (statusCode == 200) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<ElementModel> cloneList = mapper.readValue(responseString, new TypeReference<ArrayList<ElementModel>>() {
                });

                for (ElementModel element : cloneList) {
                    System.out.println(element.getName());
                }
            }
            System.out.println(statusCode);
            //System.out.println(responseString);
        } catch (Exception e) {

        }

    }
}