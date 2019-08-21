/*
    This file is part of the Painel de Aulas UFRB Open Source Project.
    Painel de Aulas UFRB is licensed under the Apache 2.0.

    Copyright 2019 UFRBuild - Marcos Bomfim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bumptech.glide.load.engine.Resource;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.adapters.ClassRoomAdapter;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.dialogs.DialogItemClassRoom;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.models.LocateModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment  extends BaseFragment<HomeViewModel>
        implements ClassRoomAdapter.OnClassRoomAdapter{


    private String TAG = "HomeFragment";
    ClassRoomAdapter ClassRoomAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.empty_view)
    ConstraintLayout emptyView;

    @BindView(R.id.image_state)
    ImageView mImageState;

    @BindView(R.id.title_states)
    TextView mTitleStates;

    @BindView(R.id.subtitle_states)
    TextView mSubTitleStates;


    @BindView(R.id.floatingButtonAction)
    FloatingActionButton mFloatingButton;

    DataManager mDataManager;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private String mLastQuery = "";
    private  FloatingSearchView mSearchView;

    HomeViewModel viewModel;

    private View mView;


    private HomeViewModel createViewModel() {
        HomeViewModelFactory factory = new HomeViewModelFactory(DataManager.getInstance().getMovieService(), DataManager.getInstance().getTimeService());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataManager = DataManager.getInstance();

        setUnBinder(ButterKnife.bind(this, mView));

        ClassRoomAdapter = new ClassRoomAdapter(this);
        recyclerView.setAdapter(ClassRoomAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewModel = createViewModel();
        super.SetupAll();


        viewModel.getLoadingStatus().observe(this, new HomeFragment.LoadingObserver());
        viewModel.getNetworkErrorStatus().observe(this, new HomeFragment.NetworkObserver());
        viewModel.getIsEmptyViewStatus().observe(this, new HomeFragment.EmptyViewObserver());
        viewModel.getDisciplines().observe(this, new HomeFragment.MovieObserver());


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

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getDisciplineData();
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


    private ArrayList<LocateModel> createSampleData(){
        ArrayList<LocateModel> items = new ArrayList<>();
        List<String> area = Arrays.asList(getResources().getStringArray(R.array.locate_campus));

        for (int i = 0; i < area.size();i++){
            items.add(new LocateModel(area.get(i)));
        }
        return items;
    }


    private void toggle() {
        new SimpleSearchDialogCompat(getContext(), getString(R.string.title_name_dialog),
                getString(R.string.title_select_area), null, createSampleData(),
                new SearchResultListener<LocateModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           LocateModel item, int position) {
                        dialog.dismiss();
                        mDataManager.getPrefs().put(getString(R.string.locate_campus), item);
                        MainActivity.getInstance().getmSubTitleHome().setText(item.getTitle());
                        viewModel.getDisciplineData();
                        viewModel.showSnackbarMessage(R.string.message_change_local_campus);
                    }
                }).show();
    }

    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;

            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                mSwipeRefresh.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
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


    private class MovieObserver implements Observer<List<Discipline>> {

        @Override
        public void onChanged(@Nullable List<Discipline> disciplines) {
            if (disciplines == null) return;
            ClassRoomAdapter.setItems(disciplines);

            if (disciplines.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public HomeViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onClassRoomClicked(Discipline discipline) {
//        Intent intent = new Intent(getContext(), DetailsActivity.class);
//        intent.putExtra("discipline", discipline);
//        startActivity(intent);


        // implementation dialogbox show up single discipline status
        HashMap data = new  HashMap<String, String>();
        data.put("matter", discipline.getName());
        data.put("professor", discipline.getDescription());
        data.put("duration", CommonUtils.intToTimeString(discipline.getDuration(), 0));
        data.put("status", String.valueOf(discipline.getStatus()));
        data.put("class_room", String.valueOf(discipline.getRoom_name()));
        data.put("start_timer", CommonUtils.intToTimeString(discipline.getStart_time(), -3));
        DialogItemClassRoom generalDialogFragment = DialogItemClassRoom.newInstance(data);
        generalDialogFragment.show(MainActivity.getInstance().getSupportFragmentManager(),"dialog");
    }

    private void setupFloatingSearch() {

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {



                    mSearchView.clearSuggestions();
                    mSearchView.hideProgress();
                    ClassRoomAdapter.getFilter().filter(newQuery);
                } else {
                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    ClassRoomAdapter.getFilter().filter(newQuery);
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