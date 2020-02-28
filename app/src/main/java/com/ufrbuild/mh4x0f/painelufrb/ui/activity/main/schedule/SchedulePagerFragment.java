package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.adapters.SchedulePagerAdapter;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.viewmodels.SchedulePagerViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.viewmodels.ScheduleViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SchedulePagerFragment extends BaseFragment<SchedulePagerViewModel> {

    private String TAG = "SchedulePagerFragment";


    @Inject
    DataManager mDataManager;
    @Inject
    ViewModelProvider.Factory factory;
    private SchedulePagerViewModel viewModel;

    @Inject
    CommonUtils utils;

    //@Inject
    //DisciplineRepository mRepository;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    SchedulePagerAdapter mPagerAdapter;
    @BindView(R.id.pager)
    ViewPager viewPager;

    private FloatingSearchView mSearchView;


    private View mView;

    private void refresh() {
        Log.i(TAG, "refresh: ");
    }

    @Override
    protected void setUp(View view) {
        setActivity(MainActivity.getInstance()); // set observer activity
    }


    @Override
    public SchedulePagerViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(SchedulePagerViewModel.class);
        return viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_schedule_main, container, false);
        setUnBinder(ButterKnife.bind(this, mView));
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPagerAdapter = new SchedulePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

    }

    @Override
    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }

}
