package com.samsung.index;

import com.samsung.model.Document;

import java.util.List;

public interface ItemWriter<T> {
    public void write(List<? extends Document> items, String indexName, String type);
}
