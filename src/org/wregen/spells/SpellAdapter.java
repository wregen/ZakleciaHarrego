
package org.wregen.spells;

import java.util.List;

import org.wregen.spells.entity.Spell;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpellAdapter extends ArrayAdapter<Spell> {
    private List<Spell> mSpells;

    public SpellAdapter(Context context, List<Spell> spells) {
        super(context, android.R.layout.simple_list_item_2);
        mSpells = spells;
    }

    @Override
    public int getCount() {
        return mSpells.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpellHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new SpellHolder();
            holder.spell = (TextView)convertView.findViewById(android.R.id.text1);
            holder.description = (TextView)convertView.findViewById(android.R.id.text2);
            convertView.setTag(holder);
        } else {
            holder = (SpellHolder)convertView.getTag();
        }

        Spell item = mSpells.get(position);
        holder.spell.setText(item.spell);
        holder.description.setText(item.description);

        return convertView;

    }

    private static class SpellHolder {
        TextView spell;
        TextView description;
    }

}
