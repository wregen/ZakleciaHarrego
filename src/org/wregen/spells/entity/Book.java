
package org.wregen.spells.entity;

public class Book {
    public int id;
    public String title;
    public String original_title;
    public String author;
    public String translator;
    public String publisher;
    public String publication_date;
    public String issue_number;
    public String pages;
    public String idx;

    public Book(String title, String original_title, String author, String translator, String publisher,
            String publication_date, String issue_number, String pages, String idx) {
        this.title = title;
        this.original_title = original_title;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.publication_date = publication_date;
        this.issue_number = issue_number;
        this.pages = pages;
        this.idx = idx;
    }

    public Book(int id, String title, String original_title, String author, String translator, String publisher,
            String publication_date, String issue_number, String pages, String idx) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.publication_date = publication_date;
        this.issue_number = issue_number;
        this.pages = pages;
        this.idx = idx;
    }

}
