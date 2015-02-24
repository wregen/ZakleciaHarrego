
package org.wregen.spells.db;

import java.util.ArrayList;
import java.util.List;

import org.wregen.spells.entity.Spell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "harry_spells";

    // Table name
    private static final String TABLE = "spells";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SPELL = "spell";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_BOOK = "book";
    private static final String COLUMN_PAGE = "page";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SPELL + " TEXT UNIQUE NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + COLUMN_BOOK + " TEXT,"
                + COLUMN_PAGE + " INT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // simple database upgrade operation:
        // 1) drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // 2) create a new database
        onCreate(db);
    }

    public void addSpells() {
        List<Spell> predefinedSpells = new ArrayList<Spell>();
        predefinedSpells.add(new Spell("Expelliarmus",
                "ofierze wymyka się z ręki różdżka lub zostaje ona odrzucona do tyłu czasami razem z czarodziejem użytkującym ową różdżkę."));
        predefinedSpells.add(new Spell("Rictusempra", "oszałamia przeciwnika, wywołuje skurcze mięśni"));
        predefinedSpells.add(new Spell("Protego", "odbija zaklęcie rzucone przez przeciwnika"));
        predefinedSpells.add(new Spell("Drętwota", "powoduje utratę przytomności osoby poszkodowanej."));
        predefinedSpells.add(new Spell("Petrificus totalus", "paraliżuje przeciwnika,"));
        predefinedSpells.add(new Spell("Levicorpus", "powoduje uniesienie przeciwnika na chwilę w powietrze,"));
        predefinedSpells.add(new Spell("Liberacorpus", "przeciwzaklęcie od levicorpus"));
        predefinedSpells.add(new Spell("Sectumsempra", "ogłusza przeciwnika i powoduje obficie krwawiące rany"));
        predefinedSpells.add(new Spell("Expulso", "powoduje odpychanie o działaniu odśrodkowym, nie powoduje większych obrażeń. Zaklęciem o podobnym działaniu jest Expelliarmus"));
        predefinedSpells.add(new Spell("Ascendio", "Przełamanie obrony."));
        addItems(predefinedSpells);
        
    }

    public void dropSpells() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE);
    }

    /**
     * retrieve all items from the database
     */
    public List<Spell> getAllItems() {
        // initialize the list
        List<Spell> items = new ArrayList<Spell>();

        // obtain a readable database
        SQLiteDatabase db = getReadableDatabase();

        // send query
        Cursor cursor = db.query(TABLE, new String[] {
                COLUMN_SPELL,
                COLUMN_DESCRIPTION,
                COLUMN_BOOK,
                COLUMN_PAGE
        },
                null, null, null, null, null, null); // get all rows

        if (cursor != null) {
            // add items to the list
            for (cursor.moveToFirst(); cursor.isAfterLast() == false; cursor.moveToNext()) {
                items.add(new Spell(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3)));
            }

            // close the cursor
            cursor.close();
        }

        // close the database connection
        db.close();

        // return the list
        return items;
    }

    /**
     * Add items to the list
     */
    public void addItems(List<Spell> items) {
        if (items != null && items.size() > 0) {
            // obtain a readable database
            SQLiteDatabase db = getWritableDatabase();

            for (Spell item : items) {
                // add the row
                db.insert(TABLE, null, prepareItem(item));
            }

            // close the database connection
            db.close();
        }
    }

    /**
     * update an existing item
     */
    public void updateItem(Spell item, int id) {
        if (item != null) {
            // obtain a readable database
            SQLiteDatabase db = getWritableDatabase();

            db.update(TABLE, prepareItem(item),
                    COLUMN_ID + "=" + id,
                    null);
            // close the database connection
            db.close();
        }
    }

    private ContentValues prepareItem(Spell item) {
        // prepare values
        ContentValues values = new ContentValues();
        values.put(COLUMN_SPELL, item.spell);
        values.put(COLUMN_DESCRIPTION, item.description);
        values.put(COLUMN_BOOK, item.book);
        values.put(COLUMN_PAGE, item.page);
        return values;

    }
}
