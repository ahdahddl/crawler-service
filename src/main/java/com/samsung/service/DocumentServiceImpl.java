package com.samsung.service;


import com.samsung.model.Document;
import com.samsung.repository.DocumentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public void delete(Document document) {
        documentRepository.delete(document);
    }

    public Document findOne(String id) {
        return null;//documentRepository.findOne(id);
    }

    public Iterable<Document> findAll() {
        return documentRepository.findAll();
    }

    public Page<Document> findByAuthor(String author, PageRequest pageRequest) {
        return documentRepository.findByAuthor(author, pageRequest);
    }

    public List<Document> findByTitle(String title) {
        return documentRepository.findByTitle(title);
    }

}
