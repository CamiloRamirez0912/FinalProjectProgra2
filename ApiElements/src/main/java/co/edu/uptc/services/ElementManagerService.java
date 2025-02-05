package co.edu.uptc.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.models.UnitOfWeight;
import co.edu.uptc.exceptions.ParameterErrorException;
import co.edu.uptc.helpers.ErrorCodes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ElementManagerService {

    private final ObjectMapper mapper;
    private Path pathDirectory;

    @Value("${source.file.element}")
    private String pathPeopleFile;

    @Value("${source.file.idElement}")
    private String pathIdPeople;

    public ElementManagerService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        pathDirectory = Paths.get(System.getProperty("user.dir"));
    }

    public void initFile() throws IOException {
        Path filePath = getAbsPathElements();
        File file = filePath.toFile();

        if (!file.exists()) {
            file.createNewFile();
        }

        if (file.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("[]");
            }
        }
    }

    public ElementModel addElement(ElementModel element) throws IOException {
        validateElement(element);
        initFile();

        RandomAccessFile raf = new RandomAccessFile(getAbsPathElements().toString(), "rw");
        long fileLength = raf.length();
        int id = getAndUpdateLastId();
        element.setId(id);

        raf.seek(fileLength - 1);

        if (fileLength > 2)
            raf.writeBytes(",\n");

        raf.writeBytes(chainObjectJson(element) + "\n]");
        raf.close();

        return element;
    }

    public ElementModel updateElement(int id, ElementModel updatedElement) throws IOException {
        validateElement(updatedElement);

        String fullContent = Files.readString(getAbsPathElements());
        List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
        });

        ElementModel elementToUpdate = elements.stream()
                .filter(element -> element.getId() == id && !element.isDeleted())
                .findFirst()
                .orElseThrow(() -> new ParameterErrorException("El elemento con id " + id + " no fue encontrado",
                        List.of(ErrorCodes.VALUE_EXCEEDS_MAXIMUM)));

        elementToUpdate.setName(updatedElement.getName());
        elementToUpdate.setDescription(updatedElement.getDescription());
        elementToUpdate.setPrice(updatedElement.getPrice());
        elementToUpdate.setUnitOfWeight(updatedElement.getUnitOfWeight());

        mapper.writerWithDefaultPrettyPrinter().writeValue(getAbsPathElements().toFile(), elements);

        return elementToUpdate;
    }

    public List<ElementModel> getFileElements() throws IOException {
        initFile();
        String fullContent = Files.readString(getAbsPathElements());
        List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
        });

        return elements.stream()
                .filter(element -> !element.isDeleted())
                .collect(Collectors.toList());
    }

    public ElementModel getElementById(int id) throws IOException {
        initFile();
        String fullContent = Files.readString(getAbsPathElements());
        List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
        });

        return elements.stream()
                .filter(element -> element.getId() == id && !element.isDeleted())
                .findFirst()
                .orElseThrow(() -> new ParameterErrorException("El elemento con id " + id + " no fue encontrado",
                        List.of(ErrorCodes.VALUE_EXCEEDS_MAXIMUM)));
    }

    public ElementModel deleteElementById(int id) throws IOException {
        initFile();
        String fullContent = Files.readString(getAbsPathElements());
        List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
        });

        ElementModel elementToDelete = elements.stream()
                .filter(element -> element.getId() == id && !element.isDeleted())
                .findFirst()
                .orElseThrow(() -> new ParameterErrorException("El elemento con id " + id + " no fue encontrado",
                        List.of(ErrorCodes.VALUE_EXCEEDS_MAXIMUM)));

        elementToDelete.setDeleted(true);
        mapper.writerWithDefaultPrettyPrinter().writeValue(getAbsPathElements().toFile(), elements);

        return elementToDelete;
    }

    private int getAndUpdateLastId() throws IOException {
        Path filePath = getAbsPathIdElements();
        File file = filePath.toFile();
        Map<String, Integer> idInfo = new HashMap<>();

        if (file.exists() && file.length() > 0) {
            idInfo = mapper.readValue(file, new TypeReference<Map<String, Integer>>() {
            });
        }

        int lastIdElement = idInfo.getOrDefault("lastId", 0);
        idInfo.put("lastId", lastIdElement + 1);

        mapper.writeValue(file, idInfo);
        return lastIdElement;
    }

    private String chainObjectJson(ElementModel element) {
        try {
            return mapper.writeValueAsString(element);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validateElement(ElementModel element) {
        if (element.getName() == null || element.getName().length() < 10) {
            throw new ParameterErrorException("El nombre debe tener minimo 10 caracteres", List.of(ErrorCodes.NAME_REQUIRED));
        }

        if (element.getDescription() == null || element.getDescription().length() < 100) {
            throw new ParameterErrorException("La descripcion debe tener minimo 100 caracteres",
                    List.of(ErrorCodes.DESCRIPTION_REQUIRED));
        }

        if (element.getPrice() <= 0) {
            throw new ParameterErrorException("El precio tiene que ser superior a 0", List.of(ErrorCodes.PRICE_REQUIRED));
        }

        if (element.getUnitOfWeight() == null) {
            throw new ParameterErrorException("La unidad de peso es requerida.",
                    List.of(ErrorCodes.UNIT_OF_WEIGHT_REQUIRED));
        }
    }

    private Path getAbsPathElements() {
        return Paths.get(pathDirectory.toString(), pathPeopleFile);
    }

    private Path getAbsPathIdElements() {
        return Paths.get(pathDirectory.toString(), pathIdPeople);
    }

    //parcial
    public double calculateAveragePrice() throws IOException {
        List<ElementModel> elements = getFileElements();
        if (elements.isEmpty()) {
            return 0.0;
        }
        double total = elements.stream().mapToDouble(ElementModel::getPrice).sum();
        return total / elements.size();
    }

    public Map<UnitOfWeight, Long> getSummaryByUnit() throws IOException {
    List<ElementModel> elements = getFileElements();
    return elements.stream()
            .collect(Collectors.groupingBy(ElementModel::getUnitOfWeight, Collectors.counting()));
    }
}
