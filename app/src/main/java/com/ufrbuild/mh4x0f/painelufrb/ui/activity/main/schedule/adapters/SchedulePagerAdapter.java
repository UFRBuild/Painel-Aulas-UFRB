package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.Log;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments.ScheduleFragment;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.EnumDayWeek;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SemanaEnum;

import java.util.HashMap;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class SchedulePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "DemoCollection";
    HashMap<Integer, ScheduleFragment> mHFragements = new HashMap<>();
    private ScheduleFragment[] childFragments;

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new ScheduleFragment[] {
                new ScheduleFragment(), //0
                new ScheduleFragment(), //1
                new ScheduleFragment(), //2
                new ScheduleFragment(), //3
                new ScheduleFragment(), //4
                new ScheduleFragment() //5
        };
        childFragments[0].setID_WEEK(SemanaEnum.SEGUNDA.getValor());
        childFragments[1].setID_WEEK(SemanaEnum.TERCA.getValor());
        childFragments[2].setID_WEEK(SemanaEnum.QUARTA.getValor());
        childFragments[3].setID_WEEK(SemanaEnum.QUINTA.getValor());
        childFragments[4].setID_WEEK(SemanaEnum.SEXTA.getValor());
        childFragments[5].setID_WEEK(SemanaEnum.SABADO.getValor());
    }

    @Override
    public Fragment getItem(int position) {
        //ScheduleFragment fragment = new ScheduleFragment();
        //fragment.setID_WEEK(i);

        Log.i(TAG, "getItemLoad: " + getPageTitle(position));


        switch (position) {
            case 0:
                return childFragments[0];
            case 1:
                return childFragments[1];
            case 2:
                return childFragments[2];
            case 3:
                return childFragments[3];
            case 4:
                return childFragments[4];
            case 5:
                return childFragments[5];
            default: break;
        }
        return null;

    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return EnumDayWeek.SEGUNDA.getValor();
            case 1:
                return EnumDayWeek.TERCA.getValor();
            case 2:
                return EnumDayWeek.QUARTA.getValor();
            case 3:
                return EnumDayWeek.QUINTA.getValor();
            case 4:
                return EnumDayWeek.SEXTA.getValor();
            case 5:
                return EnumDayWeek.SABADO.getValor();
        }
        return null;
    }
}
