package co.edu.uptc.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.uptc.models.ElementModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addElement(ElementModel element) throws IOException {
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
    }

    private int getAndUpdateLastId() throws IOException {
        Path filePath = getAbsPathIdElements();
        File file = filePath.toFile();
        Map<String, Integer> idInfo = new HashMap<>();

        if (file.exists() && file.length() > 0) {
            idInfo = mapper.readValue(file, new TypeReference<Map<String, Integer>>() {
            });
        }

        int lastIdelement = idInfo.getOrDefault("lastId", 0);
        idInfo.put("lastId", lastIdelement + 1);

        mapper.writeValue(file, idInfo);
        return lastIdelement;
    }

    private String chainObjectJson(ElementModel element) {
        try {
            return mapper.writeValueAsString(element);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getFileElements() {
        String contentFile = "";
        try {
            String fullContent = Files.readString(getAbsPathElements());
            List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
            });
            List<ElementModel> filteredPersons = new ArrayList<>();

            for (ElementModel element : elements) {
                if (!element.isDeleted()) {
                    filteredPersons.add(element);
                }
            }

            contentFile = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredPersons);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentFile;
    }

    public ElementModel getElementById(int id) throws IOException {
        initFile();
        String fullContent = Files.readString(getAbsPathElements());
        List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
        });

        for (ElementModel element : elements) {
            if (element.getId() == id && !element.isDeleted()) {
                return element;
            }
        }

        return null;
    }

    public ElementModel deleteElementById(int id) throws IOException {
        initFile();
        String fullContent = Files.readString(getAbsPathElements());
        List<ElementModel> elements = mapper.readValue(fullContent, new TypeReference<List<ElementModel>>() {
        });

        for (ElementModel element : elements) {
            if (element.getId() == id && !element.isDeleted()) {
                element.setDeleted(true);
                mapper.writerWithDefaultPrettyPrinter().writeValue(getAbsPathElements().toFile(), elements);
                return element;
            }
        }

        return null;
    }

    private Path getAbsPathElements() {
        return Paths.get(pathDirectory.toString(), pathPeopleFile);
    }

    private Path getAbsPathIdElements() {
        return Paths.get(pathDirectory.toString(), pathIdPeople);
    }
}