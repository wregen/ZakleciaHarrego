
package org.wregen.spells;

import org.wregen.spells.entity.Spell;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * SingleSpellFragment
 */
public class SingleSpellFragment extends Fragment {
    private static final String SPELL_ID = "spell_id";
    private Database mDb;

    /**
     * Returns a new instance of this fragment for the given spell id.
     */
    public static SingleSpellFragment newInstance(int spellId) {
        SingleSpellFragment fragment = new SingleSpellFragment();
        Bundle args = new Bundle();
        args.putInt(SPELL_ID, spellId);
        fragment.setArguments(args);
        return fragment;
    }

    public SingleSpellFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDb = new Database(getActivity());
        int spellId = getArguments().getInt(SPELL_ID);
        Spell item = mDb.getSpell(spellId);

        View rootView = inflater.inflate(R.layout.fragment_singlespell, container, false);
        TextView title = (TextView)rootView.findViewById(R.id.title);
        title.setText(item.spell);
        TextView description = (TextView)rootView.findViewById(R.id.description);
        description.setText(item.description);
        
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}
