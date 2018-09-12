package com.franvara.ricklantis.presentation.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franvara.ricklantis.Injector;
import com.franvara.ricklantis.R;
import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.presentation.utils.PicassoCache;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    //region Fields & Constants
    @BindView(R.id.tv_detail_name)
    TextView name;
    @BindView(R.id.iv_detail_character)
    ImageView image;
    @BindView(R.id.tv_detail_status)
    TextView status;
    @BindView(R.id.tv_detail_species)
    TextView species;
    @BindView(R.id.tv_detail_gender)
    TextView gender;
    @BindView(R.id.tv_detail_origin)
    TextView origin;
    @BindView(R.id.tv_detail_last_location)
    TextView lastLocation;

    Unbinder unbinder;
    private DetailViewModel viewModel;
    private int characterId;

    //endregion

    //region Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        unbinder = ButterKnife.bind(this);

        getExtras();
        setupViewModel();
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
        viewModel.loadCharacterDetail(characterId);
    }

    //endregion

    //region Private

    private void getExtras() {
        characterId = getIntent().getIntExtra(getString(R.string.key_extra_character_id), 1);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this,
                Injector.provideDetailViewModelFactory(this)).get(DetailViewModel.class);
    }

    private void observeViewModel() {
        viewModel.getCharacterDetail().observe(this, this::setCharacterDetail);
    }

    private void setCharacterDetail(Character character) {

        name.setText(character.getName());

        Picasso pic = PicassoCache.getPicassoInstance(this);
        pic.load(String.valueOf(character.getImage()))
                .error(R.drawable.character_image_default)
                .placeholder(R.drawable.character_image_default)
                .into(image);

        status.setText(!character.getStatus().isEmpty() ?
                character.getStatus() : getString(R.string.detail_unknown));

        String speciesText;
        if (!character.getType().isEmpty()) {
            if (!character.getSpecies().isEmpty()) {
                speciesText = getString(R.string.detail_concatenate, character.getSpecies(), character.getType());
            } else {
                speciesText = character.getType();
            }
        } else {
            speciesText = character.getSpecies();
        }
        species.setText(!speciesText.isEmpty() ?
                speciesText : getString(R.string.detail_unknown));

        gender.setText(!character.getGender().isEmpty() ?
                character.getGender() : getString(R.string.detail_unknown));
        origin.setText(!character.getOrigin().getName().isEmpty() ?
                character.getOrigin().getName() : getString(R.string.detail_unknown));
        lastLocation.setText(!character.getLocation().getName().isEmpty() ?
                character.getLocation().getName() : getString(R.string.detail_unknown));

    }

    //endregion

    @OnClick(R.id.cd_detail)
    public void onClickDetail() {
        onBackPressed();
    }
}
