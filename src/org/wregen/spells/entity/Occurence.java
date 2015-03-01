package org.wregen.spells.entity;

public class Occurence {
    public Book book;
    public int page;
    public String description;
    
    public Occurence(Book book, int page, String description) {
        this.book = book;
        this.page = page;
        this.description = description;
    }


}
