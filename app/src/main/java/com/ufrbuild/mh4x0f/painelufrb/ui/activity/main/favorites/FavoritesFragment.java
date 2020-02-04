package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.ufrbuild.mh4x0f.painelufrb.R;
import com.ufrbuild.mh4x0f.painelufrb.data.DataManager;
import com.ufrbuild.mh4x0f.painelufrb.data.network.model.Discipline;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.MainActivity;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.adapters.AdapterSectionRecycler;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.adapters.RoomFavoritesAdapter;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.favorites.models.SectionHeaderFavorites;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.dialogs.DialogItemClassRoom;
import com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.home.models.LocateModel;
import com.ufrbuild.mh4x0f.painelufrb.ui.base.BaseFragment;
import com.ufrbuild.mh4x0f.painelufrb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class FavoritesFragment extends BaseFragment<FavoritesViewModel>
        implements RoomFavoritesAdapter.OnRoomAdapterFavorites {

    private String TAG = "FavoritesFragment";

    @Inject
    ViewModelProvider.Factory factory;
    private FavoritesViewModel viewModel;

    @Inject
    DataManager mDataManager;

    @Inject
    CommonUtils utils;

    //@Inject
    //DisciplineRepository mRepository;



    @BindView(R.id.recycler_view_favorites)
    RecyclerView recyclerView;

    @BindView(R.id.empty_view_favorites)
    ConstraintLayout emptyView;

    @BindView(R.id.image_state_favorites)
    ImageView mImageState;

    @BindView(R.id.title_states_favorites)
    TextView mTitleStates;

    @BindView(R.id.subtitle_states_favorites)
    TextView mSubTitleStates;

    @BindView(R.id.floatingButtonAction_favorites)
    FloatingActionButton mFloatingButton;

    @BindView(R.id.progress_bar_favorites)
    ProgressBar progressBar;


    private View mView;
    private String mLastQuery = "";
    private  FloatingSearchView mSearchView;

    @BindView(R.id.swipe_refresh_favorites)
    SwipeRefreshLayout mSwipeRefresh;

    AdapterSectionRecycler RoomFavoritesAdapter;

    List<SectionHeaderFavorites> list_section =  new ArrayList<>();

    @Override
    public FavoritesViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(FavoritesViewModel.class);
        return viewModel;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_favorites, container, false);
        setUnBinder(ButterKnife.bind(this, mView));
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //List<SectionHeader> sections = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        List<Discipline> disciplines = new ArrayList<Discipline>();
//        disciplines.add(new Discipline("1168565","GCET148 MV Cálculo Diferencial e Integral III",
//                "Henrique dos Anjos",
//                "006",
//                1561496400,7200, 0));
//        disciplines.add(new Discipline("1168545","GCET148 MV Cálculo Diferencial e Integral II",
//                "Guilherme Santos",
//                "008",
//                1561498200,7200, 0));
//        disciplines.add(new Discipline("1168548","GCET148 MV Cálculo Diferencial e Integral I",
//                "Pricila Nesstor ",
//                "007",
//                1561496100,7200, 0));
//        viewModel.getmRepository().insertData(disciplines.get(0));
//        viewModel.getmRepository().insertData(disciplines.get(1));
//        viewModel.getmRepository().insertData(disciplines.get(2));
        //list_section.add(new SectionHeaderFavorites(disciplines, "Manhã"));

        //mRepository.insertData(disciplines.get(0));

        RoomFavoritesAdapter = new AdapterSectionRecycler(getContext(), list_section);
        recyclerView.setAdapter(RoomFavoritesAdapter);


        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);

        //viewModel = createViewModel();
        //super.SetupAll();


        viewModel.getLoadingStatus().observe(this, new FavoritesFragment.LoadingObserver());
        viewModel.getNetworkErrorStatus().observe(this, new FavoritesFragment.NetworkObserver());
        viewModel.getIsEmptyViewStatus().observe(this, new FavoritesFragment.EmptyViewObserver());
        viewModel.getDisciplines().observe(this, new FavoritesFragment.MovieObserver());


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
        //viewModel.getDisciplineData();
    }

    private void refresh() {
        Log.i(TAG, "refresh: ");
        viewModel.getDisciplineData();
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

    @Override
    public void onRoomFavoritesClicked(Discipline discipline) {
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
        //TODO : check this
        //DialogItemClassRoom generalDialogFragment = DialogItemClassRoom.newInstance(data);
        //generalDialogFragment.show(MainActivity.getInstance().getSupportFragmentManager(),"dialog");
    }


    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;

            if (isLoading) {
                mTitleStates.setText(getString(R.string.title_loadview));
                mSubTitleStates.setText(getString(R.string.sub_title_loadview));
                mImageState.setImageResource(R.drawable.loading);
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
                mTitleStates.setText(getString(R.string.title_emptyview_favorites));
                mSubTitleStates.setText(getString(R.string.sub_title_emptyview_favorites));
                mImageState.setImageResource(R.drawable.developing);
            }
        }
    }


    private class MovieObserver implements Observer<List<Discipline>> {

        @Override
        public void onChanged(@Nullable List<Discipline> disciplines) {
            if (disciplines == null) return;
            // TODO: RoomFavoritesAdapter.setItems(disciplines);

//            RoomFavoritesAdapter.insertNewChild(disciplines.get(0), 0);
//            RoomFavoritesAdapter.insertNewChild(disciplines.get(1), 0);
//            RoomFavoritesAdapter.insertNewChild(disciplines.get(2), 0);

            if (disciplines.isEmpty()) {
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
