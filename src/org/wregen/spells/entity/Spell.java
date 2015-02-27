
package org.wregen.spells.entity;

public class Spell {

    public int id;
    public String spell;
    public String description;
    public String book;
    public int page;

    public Spell(int id, String spell, String description, String book, int page) {
        this.id = id;
        this.spell = spell;
        this.description = description;
        this.book = book;
        this.page = page;
    }

    public Spell(String spell, String description, String book, int page) {
        this.spell = spell;
        this.description = description;
        this.book = book;
        this.page = page;
    }

    public Spell(String spell, String description) {
        this.spell = spell;
        this.description = description;
    }
}
