package co.id.ramadanrizky.watchmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import co.id.ramadanrizky.watchmovie.adapter.FRAdapter;
import co.id.ramadanrizky.watchmovie.adapter.MovieAdapter;
import co.id.ramadanrizky.watchmovie.entity.MovieEntity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabsParent;
    private ViewPager vPager;
    private FRAdapter frAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabsParent = findViewById(R.id.tabsParent);
        addTab();

        vPager = findViewById(R.id.viewPager);
        frAdapter = new FRAdapter(getSupportFragmentManager(), tabsParent.getTabCount());
        listenerVpager();
    }

    private void listenerVpager() {
        vPager.setAdapter(frAdapter);
        vPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabsParent));
        tabsParent.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tabs) {
                vPager.setCurrentItem(tabs.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tabs) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tabs) {

            }
        });
    }

    private void addTab() {
        tabsParent.addTab(tabsParent.newTab().setText(String.format(getResources().getString(R.string.tab_movie))));
        tabsParent.addTab(tabsParent.newTab().setText(String.format(getResources().getString(R.string.tab_tv))));
        tabsParent.addTab(tabsParent.newTab().setText(String.format(getResources().getString(R.string.tab_favorite))));
        tabsParent.addTab(tabsParent.newTab().setText(String.format(getResources().getString(R.string.tab_search))));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.language:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
