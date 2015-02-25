
package org.wregen.spells;

import java.util.ArrayList;

import org.wregen.spells.entity.Spell;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
