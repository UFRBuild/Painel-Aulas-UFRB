package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments.SchedulePagerAdapter;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Schedule extends Fragment {

    private String TAG = "ScheduleFragment";


    @Inject
    DataManager mDataManager;

    @Inject
    CommonUtils utils;

    //@Inject
    //DisciplineRepository mRepository;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    SchedulePagerAdapter mPagerAdapter;
    @BindView(R.id.pager)
    ViewPager viewPager;
    private Unbinder unBinder;


    private View mView;



    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
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

        //List<SectionHeader> sections = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
//        List<Discipline> disciplines = new ArrayList<Discipline>();
//        disciplines.add(new Discipline("1168565","GCET148 MV Cálculo Diferencial e Integral III",
//                "Henrique dos Anjos",
//                "006",
//                1561496400,7200, 0));
//        list_section.add(new SectionHeader(disciplines, "Manhã"));

        //mRepository.insertData(disciplines.get(0));



        mPagerAdapter = new SchedulePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mPagerAdapter);



        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);

        //viewModel = createViewModel();
        //super.SetupAll();


    }

}
