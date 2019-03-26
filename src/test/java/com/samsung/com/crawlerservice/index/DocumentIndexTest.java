package com.samsung.com.crawlerservice.index;

import com.samsung.Application;
import com.samsung.model.Document;
import com.samsung.service.DocumentService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DocumentIndexTest {

    @Autowired
    DocumentService documentService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

 //   @Ignore
    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Document.class);
        elasticsearchTemplate.createIndex(Document.class);
        elasticsearchTemplate.putMapping(Document.class);
        elasticsearchTemplate.refresh(Document.class);
    }


    @Test
    public void testSave() {

        Document document = new Document("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), "이건 첫번째 텍스트이다.");
        Document testDocument = documentService.save(document);

        assertNotNull(testDocument.getId());
        assertEquals(testDocument.getTitle(), document.getTitle());
        assertEquals(testDocument.getAuthor(), document.getAuthor());
        assertEquals(testDocument.getReleaseDate(), document.getReleaseDate());

    }

    @Test
    public void testFindOne() {

        Document document = new Document("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), "이건 첫번째 텍스트이다.");
        documentService.save(document);

        Document testDocument = documentService.findOne(document.getId());

        assertNotNull(testDocument .getId());
        assertEquals(testDocument .getTitle(), document.getTitle());
        assertEquals(testDocument .getAuthor(), document.getAuthor());
        assertEquals(testDocument .getReleaseDate(), document.getReleaseDate());

    }


    @Test
    public void testFindByTitle() {

        Document document = new Document("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), "이건 첫번째 텍스트이다.");
        documentService.save(document);

        List<Document> byTitle = documentService.findByTitle(document.getTitle());
        assertThat(byTitle.size(), is(1));
    }


    @Test
    public void testFindByAuthor() {

        List<Document> documentList = new ArrayList<>();

        documentList.add(new Document("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(),"이건 첫번째 텍스트이다."));
        documentList.add(new Document("1002", "Apache Lucene Basics", "Rambabu Posa", new Date(), "이건 두번째 텍스트이다."));
        documentList.add(new Document("1003", "Apache Solr Basics", "Rambabu Posa", new Date(), "이건 세번째 텍스트이다."));
        documentList.add(new Document("1007", "Spring Data + ElasticSearch", "Rambabu Posa", new Date(), "이건 네번째 텍스트이다."));
        documentList.add(new Document("1008", "Spring Boot + MongoDB", "Mkyong", new Date()));

        for (Document document : documentList) {
            documentService.save(document);
        }

        Page<Document> byAuthor = documentService.findByAuthor("Rambabu Posa", PageRequest.of(0, 10));
        assertThat(byAuthor.getTotalElements(), is(4L));

        Page<Document> byAuthor2 = documentService.findByAuthor("Mkyong", PageRequest.of(0, 10));
        assertThat(byAuthor2.getTotalElements(), is(1L));

    }
}
