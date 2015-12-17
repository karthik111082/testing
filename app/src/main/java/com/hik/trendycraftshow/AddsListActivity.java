package com.hik.trendycraftshow;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddsListActivity extends Fragment {


    private FragmentTabHost mTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.activity_adds_list);

        Bundle arg1 = new Bundle();
        arg1.putInt("Arg for Frag1", 1);
       // mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("Frag Tab1"), Mysales.class, arg1);

        Bundle arg2 = new Bundle();
        arg2.putInt("Arg for Frag2", 2);
       // mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Frag Tab2"), ProfileActivity.class, arg2);

        return mTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}
