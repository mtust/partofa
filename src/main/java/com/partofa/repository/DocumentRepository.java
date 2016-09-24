package com.partofa.repository;

import com.partofa.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tust on 24.09.2016.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
