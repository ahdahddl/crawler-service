package com.samsung.repository;

import com.samsung.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DocumentRepository extends ElasticsearchRepository<Document, String> {

    Page<Document> findByAuthor(String author, Pageable pageable);

    List<Document> findByTitle(String title);

    void save(List<? extends Document> items);

}
