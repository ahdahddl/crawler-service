package com.samsung.index;

import com.samsung.model.Document;
import com.samsung.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentWriter implements ItemWriter<Document> {
    private ElasticsearchTemplate elasticsearchTemplate;
    private Date date;
    private Long runId;


    @Autowired
    public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public void write(List<? extends Document> items, String indexName, String type){
        List<IndexQuery> indexQueries = items.stream()
                .map(item -> new IndexQueryBuilder()
                        .withObject(item)
                        .withId(((Document) item).getId()))
                        .map(builder -> builder.withType(type))
                        .map(builder -> builder.withIndexName(indexName))
                        .map(IndexQueryBuilder::build)
                        .collect(Collectors.toList());

        this.elasticsearchTemplate.bulkIndex(indexQueries);
    }
}
