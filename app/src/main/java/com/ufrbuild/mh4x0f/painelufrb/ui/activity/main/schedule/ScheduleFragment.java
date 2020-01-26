package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.models.LocateModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.adapters.ScheduleSectionAdapter;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SectionHeader;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SemanaEnum;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
        import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class ScheduleFragment extends BaseFragment<ScheduleViewModel> {

    private String TAG = "ScheduleFragment";
    public int ID_WEEK;

    @Inject
    ViewModelProvider.Factory factory;
    private ScheduleViewModel viewModel;

    @Inject
    DataManager mDataManager;

    @Inject
    CommonUtils utils;

    //@Inject
    //DisciplineRepository mRepository;



    @BindView(R.id.recycler_view_schedule)
    RecyclerView recyclerView;

    //TODO: custom mesage empty in schedule fragement
    @BindView(R.id.empty_view_schedule)
    ConstraintLayout emptyView;

    @BindView(R.id.image_state_schedule)
    ImageView mImageState;

    @BindView(R.id.title_states_schedule)
    TextView mTitleStates;

    @BindView(R.id.subtitle_states_schedule)
    TextView mSubTitleStates;

//    @BindView(R.id.floatingButtonAction_schedule)
//    FloatingActionButton mFloatingButton;
//
//    @BindView(R.id.progress_bar_schedule)
//    ProgressBar progressBar;

    private ScheduleRepository mRepository;



    private View mView;
    private String mLastQuery = "";
    private FloatingSearchView mSearchView;

    @BindView(R.id.swipe_refresh_schedule)
    SwipeRefreshLayout mSwipeRefresh;

    ScheduleSectionAdapter RoomFavoritesAdapter;

    List<SectionHeader> list_section =  new ArrayList<>();

    @Override
    public ScheduleViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ScheduleViewModel.class);
        return viewModel;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_schedule, container, false);
//        Bundle args = getArguments();
//        ID_WEEK = args.getInt("ID_WEEK");
        //viewModel.getmRepository().setAllData(getID_WEEK());


        setUnBinder(ButterKnife.bind(this, mView));
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onCreateView: " + getID_WEEK());
        mRepository = new ScheduleRepository(getActivity().getApplication(), getID_WEEK() );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //viewModel.getmRepository().setAllData(getID_WEEK());


        //List<SectionHeader> sections = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.i(TAG, "onActivityCreated: day week: " + day);
        List<Discipline> disciplines = new ArrayList<Discipline>();
        Discipline d = new Discipline("1168565","GCET148 MV Cálculo Diferencial e Integral III",
                "Henrique dos Anjos",
                "006",
                1561496400,7200, 0);
        d.setDay_week(SemanaEnum.SEGUNDA.getValor());
        d.setPavilionName("Pavilhao de Aulas 1 - PA1");


        Discipline d1 = new Discipline("1168565","GCET148 MV Cálculo Diferencial e Integral III",
                "Henrique dos Anjos",
                "006",
                1561496400,7200, 0);
        d1.setDay_week(SemanaEnum.TERCA.getValor());
        d1.setPavilionName("Pavilhao de Aulas 1 - PA1");

        Discipline d2 = new Discipline("1168565","GCET148 MV Cálculo Diferencial e Integral III",
                "Henrique dos Anjos",
                "006",
                1561496400,7200, 0);
        d2.setDay_week(SemanaEnum.QUINTA.getValor());
        d2.setPavilionName("Pavilhao de Aulas 1 - PA1");

        Discipline d3 = new Discipline("1168565","GCET148 Cálculo Diferencial e Integral III",
                "Henrique dos Anjos",
                "006",
                1561496400,7200, 0);
        d3.setDay_week(SemanaEnum.SEXTA.getValor());
        d3.setPavilionName("Pavilhao de Aulas 1 - PA1");

        Discipline d4 = new Discipline("1168565","GCET148 Cálculo Diferencial e Integral I",
                "Henrique dos Anjos",
                "006",
                1561496400,7200, 0);
        d4.setDay_week(SemanaEnum.QUARTA.getValor());
        d4.setPavilionName("Pavilhao de Aulas 1 - PA1");

        disciplines.add(d);
//        disciplines.add(d1);
        disciplines.add(d2);
//        disciplines.add(d3);
        disciplines.add(d4);
        //list_section.add(new SectionHeader(disciplines, "Manhã"));
        mRepository.deleteAllData(d);

        mRepository.insertData(disciplines.get(0));
//        mRepository.insertData(disciplines.get(1));
        mRepository.insertData(disciplines.get(2));
//        mRepository.insertData(disciplines.get(3));
//        mRepository.insertData(disciplines.get(4));

        RoomFavoritesAdapter = new ScheduleSectionAdapter(getContext(), list_section);
        recyclerView.setAdapter(RoomFavoritesAdapter);
        //recyclerView.setVisibility(View.INVISIBLE);





        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);

        //viewModel = createViewModel();
        //super.SetupAll();


        viewModel.getLoadingStatus().observe(this, new ScheduleFragment.LoadingObserver());
        viewModel.getNetworkErrorStatus().observe(this, new ScheduleFragment.NetworkObserver());
        viewModel.getIsEmptyViewStatus().observe(this, new ScheduleFragment.EmptyViewObserver());
        viewModel.getDisciplines().observe(this, new ScheduleFragment.MovieObserver());


        mRepository.getAllDisciplines().observe(this, new Observer<List<Discipline>>() {
            @Override
            public void onChanged(@Nullable final List<Discipline> products) {
                for (Discipline item : products){
                    Log.i(TAG, "onChanged: " + item.getName());
                }
                viewModel.getDisciplineData(products);
            }
        });


        mSearchView = MainActivity.getInstance().getmSearchView();
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.accent),
                getResources().getColor(R.color.colorPrimary));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });


        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_selectArea){
                    toggle();
                }
            }
        });

//        mFloatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewModel.getDisciplineData();
//            }
//        });

        // TODO: add this feature in future
//        ReactiveNetwork.observeInternetConnectivity()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Boolean>() {
//                    @Override public void call(Boolean isConnectedToInternet) {
//                        // do something with isConnectedToInternet value
//                        Log.i(TAG, "call: " + isConnectedToInternet);
//                    }
//                });


        setupFloatingSearch();
        setupDrawer();
        viewModel.getDisciplineData();
    }

    private void refresh() {
        Log.i(TAG, "refresh: ");
        viewModel.getDisciplineData();
    }

    public int getID_WEEK() {
        return ID_WEEK;
    }

    public void setID_WEEK(int ID_WEEK) {
        this.ID_WEEK = ID_WEEK;
    }

    private void toggle() {
        new SimpleSearchDialogCompat(getContext(), getString(R.string.title_name_dialog),
                getString(R.string.title_select_area), null, utils.getAllLocateModel(getContext()),
                new SearchResultListener<LocateModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           LocateModel item, int position) {
                        try {
                            if (item != null) {
                                mDataManager.getPrefs().putObject(getString(R.string.locate_campus), item);
                                MainActivity.getInstance().getmSubTitleHome().setText(item.getTitle());
                                viewModel.getDisciplineData();
                            }
                        } catch (Exception e){
                            Log.i(TAG, "onSelected: " + e.toString());
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

//    @Override
//    public void onRoomFavoritesClicked(Discipline discipline) {
//        //        Intent intent = new Intent(getContext(), DetailsActivity.class);
////        intent.putExtra("discipline", discipline);
////        startActivity(intent);
//
//
//        // implementation dialogbox show up single discipline status
//        HashMap data = new  HashMap<String, String>();
//        data.put("matter", discipline.getName());
//        data.put("professor", discipline.getDescription());
//        data.put("duration", CommonUtils.intToTimeString(discipline.getDuration(), 0));
//        data.put("status", String.valueOf(discipline.getStatus()));
//        data.put("class_room", String.valueOf(discipline.getRoom_name()));
//        data.put("start_timer", CommonUtils.intToTimeString(discipline.getStart_time(), -3));
//        DialogItemClassRoom generalDialogFragment = DialogItemClassRoom.newInstance(data);
//        generalDialogFragment.show(MainActivity.getInstance().getSupportFragmentManager(),"dialog");
//    }

    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;

            if (isLoading) {
                mTitleStates.setText(getString(R.string.title_loadview));
                mSubTitleStates.setText(getString(R.string.sub_title_loadview));
                mImageState.setImageResource(R.drawable.loading);
                mSwipeRefresh.setRefreshing(true);
            } else {
                mSwipeRefresh.setRefreshing(false);
            }
        }
    }

    private class NetworkObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isNetworkError) {
            if (isNetworkError == null) return;

            if (isNetworkError) {
                //mImageState;
                mTitleStates.setText(getString(R.string.msg_no_intenet));
                mSubTitleStates.setText(getString(R.string.sub_msg_no_intenet));
                mImageState.setImageResource(R.drawable.network_error);
            }
        }
    }

    private class EmptyViewObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isEmpty) {
            if (isEmpty == null) return;

            if (isEmpty) {
                //mImageState;
                mTitleStates.setText(getString(R.string.title_emptyview));
                mSubTitleStates.setText(getString(R.string.sub_title_emptyview));
                mImageState.setImageResource(R.drawable.empty_search);
            }
        }
    }


    private class MovieObserver implements Observer<List<SectionHeader>> {

        @Override
        public void onChanged(@Nullable List<SectionHeader> sectionHeaders) {
            if (sectionHeaders == null) return;
            // TODO: RoomFavoritesAdapter.setItems(disciplines);
            Log.i(TAG, "onChanged: " + sectionHeaders);
            RoomFavoritesAdapter.notifyDataChanged(sectionHeaders);

//            RoomFavoritesAdapter.insertNewChild(disciplines.get(0), 0);
//            RoomFavoritesAdapter.insertNewChild(disciplines.get(1), 0);
//            RoomFavoritesAdapter.insertNewChild(disciplines.get(2), 0);


            if (sectionHeaders.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void setUp(View view) {
        setActivity(MainActivity.getInstance()); // set observer activity
    }


    private void setupFloatingSearch() {

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {



                    mSearchView.clearSuggestions();
                    mSearchView.hideProgress();
                    // TODO: RoomFavoritesAdapter.getFilter().filter(newQuery);
                } else {
                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    // TODO: RoomFavoritesAdapter.getFilter().filter(newQuery);
                    mSearchView.showProgress();
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {
//                MainActivity.getInstance().viewHeaderHomeShow();
                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

                Log.d(TAG, "onFocusCleared()");
            }
        });


        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                Log.d(TAG, "onHomeClicked()");
            }
        });

        mSearchView.setOnLeftMenuClickListener(
                new FloatingSearchView.OnLeftMenuClickListener() {
                    @Override
                    public void onMenuOpened() {
                        // open menu
                        MainActivity.getInstance().getmMenuSideBar().openDrawer();
                    }

                    @Override
                    public void onMenuClosed() {

                    }
                } );


        mSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
                Log.d(TAG, "onClearSearchClicked()");
            }
        });
    }

    private void setupDrawer() {
//        attachSearchViewActivityDrawer(mSearchView);
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
