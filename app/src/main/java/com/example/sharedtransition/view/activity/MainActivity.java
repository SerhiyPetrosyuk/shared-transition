package com.example.sharedtransition.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.sharedtransition.R;
import com.example.sharedtransition.view.fragment.AppListFragment;

public class MainActivity extends AppCompatActivity {
    private final int mFragmentHolder = R.id.fl_fragment_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment appListFragment = AppListFragment.newInstance();
        replaceFragment(appListFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(mFragmentHolder, fragment)
                .commit();
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(fragment.getTag())
                .add(mFragmentHolder, fragment)
                .commit();
    }
}
