
package org.wregen.spells;

import java.util.ArrayList;
import java.util.List;

import org.wregen.spells.entity.Spell;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "spells.db";
    private static final int DATABASE_VERSION = 3;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    /**
     * retrieve all items from the database
     */
    public List<Spell> getAllSpells() {
        SQLiteDatabase db = getReadableDatabase();
        String[] fields = new String[] {
                "spell",
                "description",
                "book",
                "page"
        };
        // get all rows
        Cursor cursor = db.query("spells", fields, null, null, null, null, null, null);

        // initialize the list
        List<Spell> items = new ArrayList<Spell>();

        if (cursor != null) {
            // add items to the list
            for (cursor.moveToFirst(); cursor.isAfterLast() == false; cursor.moveToNext()) {
                items.add(new Spell(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            }

            // close the cursor
            cursor.close();
        }

        // close the database connection
        db.close();

        // return the list
        return items;
    }

}
