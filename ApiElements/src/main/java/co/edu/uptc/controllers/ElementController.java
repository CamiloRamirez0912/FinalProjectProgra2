package co.edu.uptc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.services.ElementManagerService;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/elements")
public class ElementController {

  @Autowired
  ElementManagerService elementManagerService;

  @GetMapping("/all")
  public ResponseEntity<List<ElementModel>> getFileJsonElements() throws IOException {
    return ResponseEntity.ok(elementManagerService.getFileElements());
  }

  @PostMapping("/add")
  public ResponseEntity<ElementModel> addElement(@RequestBody ElementModel element) throws IOException {
    ElementModel savedElement = elementManagerService.addElement(element);
    return ResponseEntity.ok(savedElement);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<ElementModel> getElementById(@PathVariable int id) throws IOException {
    ElementModel element = elementManagerService.getElementById(id);
    return ResponseEntity.ok(element);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ElementModel> deleteElement(@PathVariable int id) throws IOException {
    ElementModel deletedElement = elementManagerService.deleteElementById(id);
    return ResponseEntity.ok(deletedElement);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ElementModel> updateElement(
          @PathVariable int id, @RequestBody ElementModel updatedElement) throws IOException {
      ElementModel element = elementManagerService.updateElement(id, updatedElement);
      return ResponseEntity.ok(element);
  }
}