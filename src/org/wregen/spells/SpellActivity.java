
package org.wregen.spells;

import java.util.ArrayList;

import org.wregen.spells.db.DatabaseHelper;
import org.wregen.spells.entity.Spell;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

public class SpellActivity extends ActionBarActivity {

    ListView mListview;
    ArrayList<Spell> mData = new ArrayList<Spell>();
    SpellAdapter mAdapter;
    DatabaseHelper mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            mListview = (ListView)findViewById(R.id.spellList);
            referashData();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        referashData();
    }

    public void referashData() {
        mDb = new DatabaseHelper(this);
        mDb.dropSpells();
        mDb.addSpells();
        ArrayList<Spell> data = (ArrayList<Spell>)mDb.getAllItems();
        mAdapter = new SpellAdapter(SpellActivity.this, data);
        mListview.setAdapter(mAdapter);
    }
}
