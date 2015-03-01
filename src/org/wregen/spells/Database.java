
package org.wregen.spells;

import java.util.ArrayList;

import org.wregen.spells.entity.Book;
import org.wregen.spells.entity.Category;
import org.wregen.spells.entity.Occurence;
import org.wregen.spells.entity.Spell;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "spells.db";
    private static final int DATABASE_VERSION = 12;
    private static final String[] IMAGE_FIELDS = new String[] {
            "id",
            "id_spell",
            "blob"
    };
    private static final String[] SPELL_FIELDS = new String[] {
            "id",
            "spell",
            "description",
            "id_category"
    };
    private static final String[] CATEGORY_FIELDS = new String[] {
            "id",
            "name",
            "description"
    };
    private static final String[] BOOK_FIELDS = new String[] {
            "id",
            "title",
            "original_title",
            "author",
            "translator",
            "publisher",
            "publication_date",
            "issue_number",
            "pages",
            "idx"

    };
    private static final String[] SPELL_BOOK_FIELDS = new String[] {
            "id",
            "id_spell",
            "id_book",
            "page",
            "description"
    };

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    @SuppressWarnings("deprecation")
    public Drawable getImage(int id_spell) {
        Drawable out = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("images", IMAGE_FIELDS, "id_spell=" + id_spell, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            byte[] data = cursor.getBlob(2);
            out = new BitmapDrawable(BitmapFactory.decodeByteArray(data, 0, data.length));
            cursor.close();
        }
        db.close();
        return out;
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Category out = getCategory(id, db);
        db.close();
        return out;
    }

    private Category getCategory(int id, SQLiteDatabase db) {
        Category out = null;
        Cursor cursor = db.query("categories", CATEGORY_FIELDS, "id=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            out = cursorToCategory(cursor);
            cursor.close();
        }
        return out;
    }

    public Book getBook(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Book out = getBook(id, db);
        db.close();
        return out;
    }

    private Book getBook(int id, SQLiteDatabase db) {
        Book out = null;
        Cursor cursor = db.query("books", BOOK_FIELDS, "id=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            out = cursorToBook(cursor);
            cursor.close();
        }
        return out;
    }

    public ArrayList<Occurence> getOccurences(int id_spell) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Occurence> items = getOccurences(id_spell, db);
        db.close();
        return items;
    }

    private ArrayList<Occurence> getOccurences(int id_spell, SQLiteDatabase db) {
        ArrayList<Occurence> items = new ArrayList<Occurence>();
        Cursor cursor = db.query("spells_books", SPELL_BOOK_FIELDS, "id_spell=" + id_spell, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); cursor.isAfterLast() == false; cursor.moveToNext()) {
                items.add(cursorToOccurence(cursor, db));
            }
            cursor.close();
        }
        return items;
    }

    /**
     * retrieve single spells
     */
    public Spell getSpell(int id) {
        Spell out = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("spells", SPELL_FIELDS, "id=" + id, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            out = cursorToSpell(cursor, db);
            cursor.close();
        }
        db.close();
        return out;
    }

    public ArrayList<Spell> getAllSpells() {
        return getAllSpells(true);
    }

    public ArrayList<Spell> getAllSpells(boolean simpleList) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("spells", SPELL_FIELDS, null, null, null, null, "spell ASC", null);
        ArrayList<Spell> items = new ArrayList<Spell>();
        if (cursor != null) {
            for (cursor.moveToFirst(); cursor.isAfterLast() == false; cursor.moveToNext()) {
                if (simpleList == true) {
                    items.add(cursorToSimpleSpell(cursor));
                } else {
                    items.add(cursorToSpell(cursor, db));
                }
            }
            cursor.close();
        }
        db.close();
        return items;
    }

    private Book cursorToBook(Cursor cursor) {
        return new Book(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("original_title")),
                cursor.getString(cursor.getColumnIndex("author")),
                cursor.getString(cursor.getColumnIndex("translator")),
                cursor.getString(cursor.getColumnIndex("publisher")),
                cursor.getString(cursor.getColumnIndex("publication_date")),
                cursor.getString(cursor.getColumnIndex("issue_number")),
                cursor.getString(cursor.getColumnIndex("pages")),
                cursor.getString(cursor.getColumnIndex("idx")));

    }

    private Category cursorToCategory(Cursor cursor) {
        return new Category(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getString(cursor.getColumnIndex("description")));
    }

    private Occurence cursorToOccurence(Cursor cursor, SQLiteDatabase db) {
        Book book = getBook(cursor.getInt(cursor.getColumnIndex("id_book")), db);
        return new Occurence(
                book,
                cursor.getInt(cursor.getColumnIndex("page")),
                cursor.getString(cursor.getColumnIndex("description")));
    }

    private Spell cursorToSpell(Cursor cursor, SQLiteDatabase db) {
        int categoryIndex = cursor.getColumnIndexOrThrow("id_category");
        Category category = null;
        if (!cursor.isNull(categoryIndex)) {
            category = getCategory(cursor.getInt(categoryIndex), db);
        }
        ArrayList<Occurence> occurences = getOccurences(cursor.getInt(cursor.getColumnIndex("id")), db);
        return new Spell(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("spell")),
                cursor.getString(cursor.getColumnIndex("description")),
                category,
                occurences);
    }

    private Spell cursorToSimpleSpell(Cursor cursor) {
        return new Spell(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("spell")),
                cursor.getString(cursor.getColumnIndex("description")));
    }
}
