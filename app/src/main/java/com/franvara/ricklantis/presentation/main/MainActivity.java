package com.franvara.ricklantis.presentation.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.franvara.ricklantis.Injector;
import com.franvara.ricklantis.R;
import com.franvara.ricklantis.data.utils.SharedPreferencesUtils;
import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.presentation.ConnectivityChangeReceiver;
import com.franvara.ricklantis.presentation.app_model.ShowState;
import com.franvara.ricklantis.presentation.base.BaseActivity;
import com.franvara.ricklantis.presentation.detail.DetailActivity;
import com.franvara.ricklantis.presentation.utils.GenericUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.franvara.ricklantis.presentation.app_model.ShowState.SHOW;

public class MainActivity extends BaseActivity implements
        MainRVAdapter.OnItemClickListener, MainRVAdapter.OnLoadMoreListener {

    //region Fields & Constants
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.cl_empty_list)
    ConstraintLayout clEmptyList;

    Unbinder unbinder;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private MainViewModel viewModel;
    private MainRVAdapter rvAdapter;

    //endregion


    //region Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        connectivityChangeReceiver = Injector.provideConnectivityChangeReceiver(this);

        setupViewModel();
        setupSwipeRefresh();
        setupRecyclerView();
        observeViewModel();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectivityChangeReceiver.setConnectivityChangeListener(this);

        if (rvAdapter.getItemCount() == 0) viewModel.loadCharacters(1);
    }

    //endregion

    //region OnItemClickListener implementation
    @Override
    public void onItemClick(int characterId, ImageView image, TextView name) {
        if (characterId != 0) {
            Intent intent = new Intent(this, DetailActivity.class);

            ActivityOptionsCompat options;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    Pair.create(image, getString(R.string.transition_imagen)),
                    Pair.create(name, getString(R.string.transition_name)));

            intent.putExtra(getString(R.string.key_extra_character_id), characterId);
            startActivity(intent, options.toBundle());
        } else {
            if (GenericUtils.isNetworkConnectionAvailable(this)) {
                viewModel.loadCharacters(getNextPageData());
            } else showNotConnectedSnackbar();
        }
    }
    //endregion

    //region OnLoadMoreListener implementation
    @Override
    public void onLoadMore() {
        if (GenericUtils.isNetworkConnectionAvailable(this) && viewModel != null) {
            viewModel.loadCharacters(getNextPageData());
        } else showNotConnectedSnackbar();
    }
    //endregion

    //region Private
    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this,
                Injector.provideMainViewModelFactory(this)).get(MainViewModel.class);
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.loadCharacters(1);
            swipeRefreshLayout.setRefreshing(false);
            //if (!GenericUtils.isNetworkConnectionAvailable(this)) showNotConnectedSnackbar();
        });
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        rvAdapter = new MainRVAdapter();
        recyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(this);
    }

    private void observeViewModel() {
        viewModel.getCharacters().observe(this, this::setCharacters);
        viewModel.getNextCharacters().observe(this, this::addCharacters);
        viewModel.getMoreCharacters().observe(this, this::handleMoreCharacters);
        viewModel.getNextPage().observe(this, this::saveNextPage);
        viewModel.getCommonProgressLayoutState().observe(this, this::toogleLocalProgress);
        viewModel.getSnackbarMessage().observe(this, this::showCommonToast);
    }

    private void setCharacters(List<Character> characters) {

        showEmptyList(characters.isEmpty());

        rvAdapter.setCharacters(characters);

        swipeRefreshLayout.setRefreshing(false);
    }

    private void addCharacters(List<Character> characters) {
        rvAdapter.addCharacters(characters);
    }

    private void handleMoreCharacters(boolean moreCharacters) {
        if (moreCharacters) {
            rvAdapter.setOnLoadMoreListener(this);
            rvAdapter.setLoadingItem();
        } else {
            rvAdapter.setOnLoadMoreListener(null);
        }
    }

    private void saveNextPage(Integer nextPage) {
        SharedPreferencesUtils.storeIntPref(getApplicationContext(), SharedPreferencesUtils.NEXT_PAGE, nextPage);
    }

    private int getNextPageData() {
        return SharedPreferencesUtils.getIntPref(getApplicationContext(), SharedPreferencesUtils.NEXT_PAGE);
    }

    private void toogleLocalProgress(ShowState showState) {
        swipeRefreshLayout.setRefreshing(showState == SHOW);
    }

    private void showEmptyList(boolean show) {
        if (show) {
            clEmptyList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            clEmptyList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    //endregion

    //region onConnectionChangedListener

    @Override
    public void onConnectionRestored() {
        if (rvAdapter.getItemCount() == 0) viewModel.loadCharacters(1);
    }

    //endregion

    //region View Events
    @OnClick(R.id.bt_empty_list)
    public void onClickRetry() {
        if (GenericUtils.isNetworkConnectionAvailable(this)) viewModel.loadCharacters(1);
        else showNotConnectedSnackbar();
    }


    //endregion


}
