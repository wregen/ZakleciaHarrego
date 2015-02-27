
package org.wregen.spells;

import java.util.ArrayList;

import org.wregen.spells.entity.Spell;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * AlphabeticListFragment
 */
public class AlphabeticListFragment extends Fragment {
    private ListView mListview;
    private SpellAdapter mAdapter;
    private Database mDb;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static AlphabeticListFragment newInstance(int sectionNumber) {
        AlphabeticListFragment fragment = new AlphabeticListFragment();
        return fragment;
    }

    public AlphabeticListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alphabeticallist, container, false);
        mDb = new Database(getActivity());
        ArrayList<Spell> data = (ArrayList<Spell>)mDb.getAllSpells();
        mAdapter = new SpellAdapter(getActivity(), data);
        mListview = (ListView)rootView.findViewById(R.id.spellList);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Spell item = (Spell)listView.getItemAtPosition(position);
                
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, SingleSpellFragment.newInstance(item.id));
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getActivity(),
                        item.spell, Toast.LENGTH_SHORT).show();

            }

        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((Main)activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));

    }
}
