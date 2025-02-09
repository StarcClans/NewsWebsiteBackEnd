package lt.ca.javau11.controller;

import lt.ca.javau11.entities.SectionEntity;
import lt.ca.javau11.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionRestController {

    private final SectionService sectionService;

    public SectionRestController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<List<SectionEntity>> getAllSections() {
        List<SectionEntity> sections = sectionService.getListSections();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/{section}")
    public ResponseEntity<SectionEntity> getSectionByName(@PathVariable String section) {
        SectionEntity sectionEntity = sectionService.getSectionEntityBySection(section);
        if (sectionEntity != null) {
            return new ResponseEntity<>(sectionEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
      @DeleteMapping("/{section}")
    public ResponseEntity<Void> deleteSection(@PathVariable String section) {
         boolean deleted = sectionService.deleteSection(section);
        if(deleted){
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<SectionEntity> createSection(@RequestBody SectionEntity section) {
        SectionEntity createdSection = sectionService.createSection(section);
          return new ResponseEntity<>(createdSection, HttpStatus.CREATED);
    }

    @PutMapping("/{section}")
    public ResponseEntity<SectionEntity> updateSection(@PathVariable String section, @RequestBody SectionEntity updatedSection) {
        SectionEntity sectionEntity = sectionService.updateSection(section, updatedSection);
        if(sectionEntity != null) {
           return new ResponseEntity<>(sectionEntity, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}