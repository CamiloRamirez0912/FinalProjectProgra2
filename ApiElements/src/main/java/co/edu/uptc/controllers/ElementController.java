package co.edu.uptc.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uptc.models.ElementModel;
import co.edu.uptc.services.ElementManagerService;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/elements")
public class ElementController {

  @Autowired
  ElementManagerService elementManagerService;

  @GetMapping("/all")
  public ResponseEntity<String> getFileJsonElements() {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(elementManagerService.getFileElements());
  }

  @PostMapping("/add")
  public ElementModel addElement(@RequestBody ElementModel element) {
    try {
      elementManagerService.addElement(element);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return element;
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<ElementModel> getElementById(@PathVariable int id) {
    try {
      ElementModel element = elementManagerService.getElementById(id);
      if (element != null) {
        return ResponseEntity.ok(element);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(500).build();
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ElementModel> deleteElement(@PathVariable int id) {
    try {
      ElementModel deletedElement = elementManagerService.deleteElementById(id);
      if (deletedElement != null) {
        return ResponseEntity.ok(deletedElement);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(500).build();
    }
  }
}