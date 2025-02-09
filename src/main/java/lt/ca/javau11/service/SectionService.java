package lt.ca.javau11.service;

import lt.ca.javau11.entities.SectionEntity;
import lt.ca.javau11.models.Section;
import lt.ca.javau11.repositories.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final NytApiService nytApiService;
    private boolean sectionsFetched = false;

    public SectionService(SectionRepository sectionRepository, NytApiService nytApiService) {
        this.sectionRepository = sectionRepository;
        this.nytApiService = nytApiService;
    }

    public void fetchAndSaveSections() {
        if (!sectionsFetched || sectionRepository.count() == 0) {
            System.out.println("Fetching and saving sections");
            List<Section> sections = nytApiService.fetchSections();
            System.out.println("Number of sections fetched: " + sections.size());
            List<SectionEntity> sectionEntities = sections.stream()
                    .filter(section -> !section.getSection().equalsIgnoreCase("Admin")) // Add Admin section filtering.
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
            sectionRepository.saveAll(sectionEntities);
            System.out.println("Number of sections saved:" + sectionEntities.size());
            sectionsFetched = true;
        }
    }

    private SectionEntity convertToEntity(Section section) {
        SectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setSection(section.getSection());
        sectionEntity.setDisplayName(section.getDisplay_name());
        return sectionEntity;
    }

    public String getSectionByName(String displayName) {
        return sectionRepository.findAll().stream()
                .filter(sectionEntity -> sectionEntity.getDisplayName().equalsIgnoreCase(displayName))
                .map(SectionEntity::getSection).findFirst().orElse(null);
    }

    public List<SectionEntity> getListSections() {
        List<SectionEntity> sectionEntities = sectionRepository.findAll();
        System.out.println("Sections retrieved from database: " + sectionEntities.size());
        return sectionEntities;
    }
}