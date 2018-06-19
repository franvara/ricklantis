package com.franvara.ricklantis.presentation.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.franvara.ricklantis.R;
import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.presentation.utils.PicassoCache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int visibleThreshold = 2;
    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_CHARACTER = 1;

    private RecyclerView recyclerView;

    //region Interfaces

    interface OnItemClickListener {
        void onItemClick(String characterId);
    }

    /**
     * Interface to send callback when the user scrolls to load more data
     */
    interface OnLoadMoreListener {

        void onLoadMore();
    }

    //endregion

    //region Fields
    private List<Character> characterList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnLoadMoreListener onLoadMoreListener;
    private Context context;
    private String selectedItemId;
    private boolean isLoading;
    private int lastVisibleItem, totalItemCount;


    //endregion

    //region ViewHolder
    class MainItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_character_item_clickable)
        FrameLayout clickableContainer;
        @BindView(R.id.iv_character)
        ImageView image;
        @BindView(R.id.tv_name)
        TextView name;

        String characterId = null;

        public MainItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            clickableContainer.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    selectedItemId = characterId;
                    onItemClickListener.onItemClick(selectedItemId);
                }
            });

        }
    }

    /**
     * ViewHolder representation of a Loading item
     */
    class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ib_refresh)
        ImageButton refresh;

        LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            refresh.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick("0");
                }
            });
        }
    }

    //endregion

    //region Lifecycle

    @Override
    public int getItemViewType(int position) {
        return characterList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_CHARACTER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CHARACTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_character, parent, false);
            return new MainItemViewHolder(v);

        } else if (viewType == VIEW_TYPE_LOADING) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_loading, parent, false);
            return new LoadingViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainItemViewHolder) {
            final MainItemViewHolder mainHolder = (MainItemViewHolder) holder;

            final Character character = characterList.get(position);

            mainHolder.characterId = character.getId().toString();

            Picasso pic = PicassoCache.getPicassoInstance(context);

            pic.load(String.valueOf(character.getImage())).error(R.drawable.character_image_default).placeholder(R.drawable.character_image_default).into(mainHolder.image);

            mainHolder.name.setText(character.getName());

        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return characterList == null ? 0 : characterList.size();
    }

    //endregion

    //region Public methods

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    void setCharacters(List<Character> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
        setScrollListener();
    }


    void addCharacters(List<Character> characters) {
        removeLoadingItem();
        characterList.addAll(characters);
        notifyDataSetChanged();
        isLoading = false;
    }

    void setLoadingItem() {
        characterList.add(null);
        notifyItemInserted(characterList.size() - 1);
    }

    //endregion

    //region Private methods

    private void setScrollListener() {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.OnScrollListener rvOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                        isLoading = true;
                    }
                }
            }
        };
        recyclerView.addOnScrollListener(rvOnScrollListener);
    }

    private void removeLoadingItem() {
        if (characterList != null && !characterList.isEmpty() &&
                characterList.get(characterList.size() - 1) == null)

            characterList.remove(characterList.size() - 1);
    }

    //endregion


}
