package com.application.bibliodesk.service;

import com.application.bibliodesk.payload.PublisherDTO;

import java.util.List;

public interface PublisherService {

    List<PublisherDTO> findAllPublishers();

    PublisherDTO findPublisherById(Long id);

    PublisherDTO createPublisher(PublisherDTO publisherDTO);

    PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO);

    void deletePublisher(Long id);
}
