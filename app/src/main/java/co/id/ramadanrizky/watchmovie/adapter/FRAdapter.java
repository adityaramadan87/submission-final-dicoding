package co.id.ramadanrizky.watchmovie.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import co.id.ramadanrizky.watchmovie.fragment.FavoriteFragment;
import co.id.ramadanrizky.watchmovie.fragment.MovieFragment;
import co.id.ramadanrizky.watchmovie.fragment.SearchFragment;
import co.id.ramadanrizky.watchmovie.fragment.TVFragment;

public class FRAdapter extends FragmentStatePagerAdapter {

    private int tabsItem;

    public FRAdapter(FragmentManager fm, int tabsItem) {
        super(fm);
        this.tabsItem = tabsItem;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MovieFragment();
            case 1:
                return new TVFragment();
            case 2:
                return new FavoriteFragment();
            case 3:
                return new SearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsItem;
    }
}
