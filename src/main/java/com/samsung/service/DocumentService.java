package com.samsung.service;

import com.samsung.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DocumentService {

    Document save(Document document);

    void delete(Document document);

    Document findOne(String id);

    Iterable<Document> findAll();

    Page<Document> findByAuthor(String author, PageRequest pageRequest);

    List<Document> findByTitle(String title);
}
