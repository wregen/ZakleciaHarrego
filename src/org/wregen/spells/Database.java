
package org.wregen.spells;

import java.util.ArrayList;
import java.util.List;

import org.wregen.spells.entity.Spell;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "spells.db";
    private static final int DATABASE_VERSION = 7;
    private static final String[] SPELL_FIELDS = new String[] {
            "id",
            "spell",
            "description",
            "book",
            "page"
    };
    private static final String[] IMAGE_FIELDS = new String[] {
            "id",
            "id_spell",
            "blob"
    };

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    /**
     * retrieve all spells
     */
    public List<Spell> getAllSpells() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("spells", SPELL_FIELDS, null, null, null, null, "spell ASC", null);
        List<Spell> items = new ArrayList<Spell>();
        if (cursor != null) {
            for (cursor.moveToFirst(); cursor.isAfterLast() == false; cursor.moveToNext()) {
                items.add(cursorToSpell(cursor));
            }
            cursor.close();
        }
        db.close();
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
            out = cursorToSpell(cursor);
            cursor.close();
        }
        db.close();
        return out;
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

    private Spell cursorToSpell(Cursor cursor) {
        return new Spell(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("spell")),
                cursor.getString(cursor.getColumnIndex("description")),
                cursor.getString(cursor.getColumnIndex("book")),
                cursor.getInt(cursor.getColumnIndex("page")));
    }
}
