package com.application.bibliodesk.repository;

import com.application.bibliodesk.entity.Publisher; // ✅ Corrected import
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
