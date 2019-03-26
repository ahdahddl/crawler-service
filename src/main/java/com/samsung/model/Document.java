package com.samsung.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName = "default", type = "default")
public class Document {

    @Id
    private String id;

//    @Field
    private String title;
    private String author;
    private Date releaseDate;
    private String text;

    public Document(){

    }

    public Document(String id, String title, String author, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public Document(String id, String title, String author, Date releaseDate, String text) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.text = text;
    }

    //getters and setters

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
