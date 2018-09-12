package com.franvara.ricklantis.presentation.detail;

import android.arch.lifecycle.MutableLiveData;

import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.domain.threading.UseCase;
import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.GetCharacterDetailUseCase;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharacterDetailRequest;
import com.franvara.ricklantis.presentation.base.BaseViewModel;

import static com.franvara.ricklantis.presentation.app_model.ShowState.HIDE;
import static com.franvara.ricklantis.presentation.app_model.ShowState.SHOW;

/**
 * {@link BaseViewModel} for {@link DetailActivity}
 */
public class DetailViewModel extends BaseViewModel {

    private final GetCharacterDetailUseCase getCharacterDetailUseCase;

    private MutableLiveData<Character> characterDetail = new MutableLiveData<>();

    public DetailViewModel(UseCaseHandler useCaseHandler,
                           GetCharacterDetailUseCase getCharacterDetailUseCase) {
        super(useCaseHandler);
        this.getCharacterDetailUseCase = getCharacterDetailUseCase;
    }

    void loadCharacterDetail(int characterId) {

        commonProgressLayoutState.setValue(SHOW);

        GetCharacterDetailRequest request = new GetCharacterDetailRequest();
        request.setId(characterId);

        useCaseHandler.execute(getCharacterDetailUseCase,
                new GetCharacterDetailUseCase.RequestValues(request),
                new UseCase.UseCaseCallback<GetCharacterDetailUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(GetCharacterDetailUseCase.ResponseValue response) {

                        Character characterResponse = response.getCharactersResponse();

                        characterDetail.setValue(characterResponse);

                        commonProgressLayoutState.setValue(HIDE);
                    }

                    @Override
                    public void onError(String error) {
                        commonProgressLayoutState.setValue(HIDE);
                        snackbarMessage.setValue(error);
                    }
                });
    }

    MutableLiveData<Character> getCharacterDetail() {
        return characterDetail;
    }

}
