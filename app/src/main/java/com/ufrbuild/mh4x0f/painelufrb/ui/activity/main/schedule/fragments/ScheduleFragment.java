package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.fragments;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
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
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.ScheduleRepository;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.viewmodels.ScheduleViewModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.adapters.ScheduleSectionAdapter;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models.SectionHeader;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.ArrayList;
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


    private ScheduleRepository mRepository;



    private View mView;
    private String mLastQuery = "";
    private FloatingSearchView mSearchView;


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

        RoomFavoritesAdapter = new ScheduleSectionAdapter(getContext(), list_section);
        recyclerView.setAdapter(RoomFavoritesAdapter);




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
//        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.accent),
//                getResources().getColor(R.color.colorPrimary));
//        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//            }
//        });


        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_selectArea){
                    toggle();
                }
            }
        });

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
                getString(R.string.title_select_area), null, utils.getAllLocateModel(getContext(), MainActivity.getInstance().getViewModel()),
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
                //mSwipeRefresh.setRefreshing(true);
            } else {
                //mSwipeRefresh.setRefreshing(false);
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
                mTitleStates.setText(getString(R.string.title_emptyview_schedule));
                mSubTitleStates.setText(getString(R.string.sub_title_emptyview_schedule));
                mImageState.setImageResource(R.drawable.empty_schedule);
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
