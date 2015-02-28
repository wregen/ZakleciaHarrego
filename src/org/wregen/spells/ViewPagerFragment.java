
package org.wregen.spells;

import java.util.ArrayList;

import org.wregen.spells.entity.Spell;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * SingleSpellFragment
 */
public class ViewPagerFragment extends Fragment {
    private Database mDb;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    
    private int mCurrentPosition;
    private ArrayList<Spell> mData;

    /**
     * Returns a new instance of this fragment for the given spell id.
     */
    public static ViewPagerFragment newInstance(int currentPosition, ArrayList<Spell> data) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.mData = data;
        fragment.mCurrentPosition = currentPosition;
        return fragment;
    }

    public ViewPagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public ViewPager onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mPager = (ViewPager)inflater.inflate(R.layout.fragment_viewpager, container, false);
        mPagerAdapter = new SlidePagerAdapter(getActivity());

        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(mCurrentPosition);
        return mPager;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    public class SlidePagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public SlidePagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ScrollView)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_singlespell, container, false);

            mDb = new Database(getActivity());
            int spellId = mData.get(position).id;
            Spell item = mDb.getSpell(spellId);
            Drawable image = mDb.getImage(spellId);

            TextView title = (TextView)itemView.findViewById(R.id.title);
            title.setText(item.spell);

            TextView description = (TextView)itemView.findViewById(R.id.description);
            description.setText(item.description);

            if (image != null) {
                ImageView picture = (ImageView)itemView.findViewById(R.id.image);
                picture.setImageDrawable(image);
            }

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ScrollView)object);
        }
    }
}
