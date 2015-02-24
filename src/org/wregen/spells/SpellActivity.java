
package org.wregen.spells;

import java.util.ArrayList;

import org.wregen.spells.entity.Spell;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SpellActivity extends ActionBarActivity {

    ListView mListview;
    ArrayList<Spell> mData = new ArrayList<Spell>();
    SpellAdapter mAdapter;
    Database mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mDb = new Database(this);
        ArrayList<Spell> data = (ArrayList<Spell>)mDb.getAllSpells();
        mAdapter = new SpellAdapter(SpellActivity.this, data);
        mListview = (ListView)findViewById(R.id.spellList);
        mListview.setAdapter(mAdapter);

        // OnCLickListiner For List Items

        mListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Spell item =(Spell) listView.getItemAtPosition(position);
                
                
                Toast.makeText(getApplicationContext(),
                        item.spell, Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
