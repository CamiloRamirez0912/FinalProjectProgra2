package co.edu.uptc.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiConfig {
    private static Properties properties;
    private static ApiConfig instance;

    private ApiConfig() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar el archivo de configuración", e);
        }
    }

    public static synchronized ApiConfig getInstance() {
        if (instance == null) {
            instance = new ApiConfig();
        }
        return instance;
    }

    public String getBaseUrl() {
        return properties.getProperty("api.base-url");
    }

    public String getElementsBaseEndpoint() {
        return properties.getProperty("api.endpoints.elements.base");
    }

    public String getElementsAllEndpoint() {
        return properties.getProperty("api.endpoints.elements.all");
    }

    public String getElementsAddEndpoint() {
        return properties.getProperty("api.endpoints.elements.add");
    }

    public String getElementsUpdateEndpoint() {
        return properties.getProperty("api.endpoints.elements.update");
    }

    public String getElementsDeleteEndpoint() {
        return properties.getProperty("api.endpoints.elements.delete");
    }

    public String getElementsGetByIdEndpoint() {
        return properties.getProperty("api.endpoints.elements.get-by-id");
    }
}