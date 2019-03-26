package com.samsung.com.crawlerservice.index;


import com.samsung.Application;
import com.samsung.index.DocumentWriter;
import com.samsung.model.Document;
import com.samsung.service.DocumentService;
import org.junit.Before;
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
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DocumentDynamicIndexTest {

    @Autowired
    private DocumentWriter documentWriter;

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
        documentWriter.setElasticsearchTemplate(elasticsearchTemplate);
    }

    @Test
    public void testFindByAuthor() {

        List<Document> documentList1 = new ArrayList<>();

        documentList1.add(new Document("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(),"이건 첫번째 텍스트이다."));
        documentList1.add(new Document("1002", "Apache Lucene Basics", "Rambabu Posa", new Date(), "이건 두번째 텍스트이다."));
        documentList1.add(new Document("1003", "Apache Solr Basics", "Rambabu Posa", new Date(), "이건 세번째 텍스트이다."));
        documentList1.add(new Document("1007", "Spring Data + ElasticSearch", "Rambabu Posa", new Date(), "이건 네번째 텍스트이다."));
        documentList1.add(new Document("1008", "Spring Boot + MongoDB", "Mkyong", new Date()));

        documentWriter.write(documentList1, "new_index1", "doc");


        List<Document> documentList2 = new ArrayList<>();

        documentList2.add(new Document("1010", "Elasticsearch Basics", "Rambabu Posa", new Date(),"이건 첫번째 텍스트이다."));
        documentList2.add(new Document("1020", "Apache Lucene Basics", "Rambabu Posa", new Date(), "이건 두번째 텍스트이다."));
        documentList2.add(new Document("1030", "Apache Solr Basics", "Rambabu Posa", new Date(), "이건 세번째 텍스트이다."));
        documentList2.add(new Document("1047", "Spring Data + ElasticSearch", "Rambabu Posa", new Date(), "이건 네번째 텍스트이다."));
        documentList2.add(new Document("1048", "Spring Boot + MongoDB", "Mkyong", new Date()));

        documentWriter.write(documentList1, "new_index2", "db");


//        Page<Document> byAuthor = documentService.findByAuthor("Rambabu Posa", PageRequest.of(0, 10));
//        assertThat(byAuthor.getTotalElements(), is(4L));
//
//        Page<Document> byAuthor2 = documentService.findByAuthor("Mkyong", PageRequest.of(0, 10));
//        assertThat(byAuthor2.getTotalElements(), is(1L));

    }
}
