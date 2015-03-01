
package org.wregen.spells.entity;

import java.util.ArrayList;

public class Spell {

    public int id;
    public String spell;
    public String description;
    public Category category;
    public ArrayList<Occurence> occurences;

    public Spell(String spell, String description, Category category, ArrayList<Occurence> occurences) {
        this.spell = spell;
        this.description = description;
        this.category = category;
        this.occurences = occurences;
    }

    public Spell(int id, String spell, String description, Category category, ArrayList<Occurence> occurences) {
        this.id = id;
        this.spell = spell;
        this.description = description;
        this.category = category;
        this.occurences = occurences;
    }

    public Spell(int id, String spell, String description) {
        this.id = id;
        this.spell = spell;
        this.description = description;
    }
}
