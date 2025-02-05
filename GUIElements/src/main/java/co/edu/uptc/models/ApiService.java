package co.edu.uptc.models;

import co.edu.uptc.config.ApiConfig;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.view.MainFrame;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ApiService implements ModelInterface {

    private RestTemplate restTemplate;
    private MainFrame view;
    private ApiConfig apiConfig;

    public ApiService() {
        restTemplate = new RestTemplate();
        view = MainFrame.getInstance();
        apiConfig = ApiConfig.getInstance();
    }

    private String buildUrl(String endpoint) {
        return apiConfig.getBaseUrl() +
                apiConfig.getElementsBaseEndpoint() +
                endpoint;
    }

    @Override
    public List<ElementModel> getAllElements() {
        try {
            String url = buildUrl(apiConfig.getElementsAllEndpoint());
            ResponseEntity<List<ElementModel>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ElementModel>>() {
                    });
            return response.getBody();
        } catch (ResourceAccessException e) {
            view.showErrorMessage("Error de conexión: El servidor no está disponible");
            return null;
        } catch (HttpClientErrorException e) {
            view.showErrorMessage("Error al obtener elementos: " + parseErrorMessage(e.getResponseBodyAsString()));
            return null;
        }
    }

    @Override
    public ElementModel addElement(ElementModel element) {
        try {
            String url = buildUrl(apiConfig.getElementsAddEndpoint());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ElementModel> request = new HttpEntity<>(element, headers);

            ResponseEntity<ElementModel> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    ElementModel.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            view.showErrorMessage("Error de conexión: El servidor no está disponible");
            return null;
        } catch (HttpClientErrorException e) {
            String errorMessage = parseErrorMessage(e.getResponseBodyAsString());
            view.showErrorMessage(errorMessage);
            return null;
        }
    }

    @Override
    public ElementModel updateElement(int id, ElementModel element) {
        try {
            String url = buildUrl(apiConfig.getElementsUpdateEndpoint()) + "/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ElementModel> request = new HttpEntity<>(element, headers);

            ResponseEntity<ElementModel> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    ElementModel.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            view.showErrorMessage("Error de conexión: El servidor no está disponible");
            return null;
        } catch (HttpClientErrorException e) {
            String errorMessage = parseErrorMessage(e.getResponseBodyAsString());
            view.showErrorMessage(errorMessage);
            return null;
        }
    }

    @Override
    public ElementModel deleteElement(int id) {
        try {
            String url = buildUrl(apiConfig.getElementsDeleteEndpoint()) + "/" + id;
            ResponseEntity<ElementModel> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    ElementModel.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            view.showErrorMessage("Error de conexión: El servidor no está disponible");
            return null;
        } catch (HttpClientErrorException e) {
            view.showErrorMessage("Error al eliminar: " + parseErrorMessage(e.getResponseBodyAsString()));
            return null;
        }
    }

    @Override
    public ElementModel getElementById(int id) {
        try {
            String url = buildUrl(apiConfig.getElementsGetByIdEndpoint()) + "/" + id;
            ResponseEntity<ElementModel> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    ElementModel.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            view.showErrorMessage("Error de conexión: El servidor no está disponible");
            return null;
        } catch (HttpClientErrorException e) {
            view.showErrorMessage("Error al obtener elemento: " + parseErrorMessage(e.getResponseBodyAsString()));
            return null;
        }
    }

    public double getAveragePrice() throws IOException {
        String url = buildUrl("/average-price");
        ResponseEntity<Double> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Double.class);
        return response.getBody();
    }

    public Map<UnitOfWeight, Long> getSummaryByUnit() throws IOException {
        String url = buildUrl("/summary-by-unit");
        ResponseEntity<Map<UnitOfWeight, Long>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<UnitOfWeight, Long>>() {
                });
        return response.getBody();
    }

    private String parseErrorMessage(String errorResponse) {
        try {
            if (errorResponse.contains("message")) {
                int startIndex = errorResponse.indexOf("\"message\":") + 11;
                int endIndex = errorResponse.indexOf("\"", startIndex);
                return errorResponse.substring(startIndex, endIndex);
            }
            return "Error desconocido del servidor";
        } catch (Exception e) {
            return "Error al procesar el mensaje de error";
        }
    }
}