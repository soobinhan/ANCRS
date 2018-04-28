package edu.cornell.cs.cs5412.ancrs.models;

import lombok.Data;

@Data
public class Article {
    Source source;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String publishedAt;
}
