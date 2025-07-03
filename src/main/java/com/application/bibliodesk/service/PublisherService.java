package com.application.bibliodesk.service;

import com.application.bibliodesk.entity.Publisher; // ✅ Corrected package
import com.application.bibliodesk.repository.PublisherRepository; // ✅ Corrected package
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Transactional(readOnly = true)
    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher with ID " + id + " not found"));
    }

    @Transactional
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Transactional
    public Publisher updatePublisher(Publisher publisher) {
        if (publisher.getId() == null || !publisherRepository.existsById(publisher.getId())) {
            throw new RuntimeException("Publisher with ID " + publisher.getId() + " not found for update or ID is null.");
        }
        return publisherRepository.save(publisher);
    }

    @Transactional
    public void deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new RuntimeException("Publisher with ID " + id + " not found for deletion");
        }
        publisherRepository.deleteById(id);
    }
}
