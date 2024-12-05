package co.edu.uptc.models;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import co.edu.uptc.interfaces.ModelInterface;

import java.util.List;

public class ApiService implements ModelInterface{

    private static final String BASE_URL = "http://localhost:8080/elements";

    private RestTemplate restTemplate;

    public ApiService() {
        restTemplate = new RestTemplate();
    }

    @Override
    public List<ElementModel> getAllElements() {
        String url = BASE_URL + "/all";
        ResponseEntity<List<ElementModel>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ElementModel>>() {});
        return response.getBody();
    }

    @Override
    public ElementModel addElement(ElementModel element) {
        String url = BASE_URL + "/add";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ElementModel> request = new HttpEntity<>(element, headers);

        ResponseEntity<ElementModel> response = restTemplate.exchange(url, HttpMethod.POST, request, ElementModel.class);
        return response.getBody();
    }

    @Override
    public ElementModel updateElement(int id, ElementModel element) {
        String url = BASE_URL + "/update/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ElementModel> request = new HttpEntity<>(element, headers);

        ResponseEntity<ElementModel> response = restTemplate.exchange(url, HttpMethod.PUT, request, ElementModel.class);
        return response.getBody();
    }

    @Override
    public ElementModel deleteElement(int id) {
        String url = BASE_URL + "/delete/" + id;
        ResponseEntity<ElementModel> response = restTemplate.exchange(url, HttpMethod.DELETE, null, ElementModel.class);
        return response.getBody();
    }

    @Override
    public ElementModel getElementById(int id) {
        String url = BASE_URL + "/id/" + id;
        ResponseEntity<ElementModel> response = restTemplate.exchange(url, HttpMethod.GET, null, ElementModel.class);
        return response.getBody();
    }
}
