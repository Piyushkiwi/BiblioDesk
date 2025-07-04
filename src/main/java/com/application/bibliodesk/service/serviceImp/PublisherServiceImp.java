package com.application.bibliodesk.service.serviceImp;

import com.application.bibliodesk.payload.PublisherDTO;
import com.application.bibliodesk.entity.Publisher;
import com.application.bibliodesk.repository.PublisherRepository;
import com.application.bibliodesk.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImp implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public List<PublisherDTO> findAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDTO findPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + id));
        return convertToDTO(publisher);
    }

    @Override
    public PublisherDTO createPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        Publisher saved = publisherRepository.save(publisher);
        return convertToDTO(saved);
    }

    @Override
    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + id));
        publisher.setName(publisherDTO.getName());
        Publisher updated = publisherRepository.save(publisher);
        return convertToDTO(updated);
    }

    @Override
    public void deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + id));
        publisherRepository.delete(publisher);
    }

    // ----------------------
    // Helper method
    // ----------------------
    private PublisherDTO convertToDTO(Publisher publisher) {
        return PublisherDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }
}

