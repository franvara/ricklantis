package com.franvara.ricklantis.presentation.main;

import android.arch.lifecycle.MutableLiveData;

import com.franvara.ricklantis.domain.entities.Character;
import com.franvara.ricklantis.domain.entities.Info;
import com.franvara.ricklantis.domain.threading.UseCase;
import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.GetCharactersUseCase;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.presentation.base.BaseViewModel;

import java.util.List;

import static com.franvara.ricklantis.presentation.app_model.ShowState.HIDE;
import static com.franvara.ricklantis.presentation.app_model.ShowState.SHOW;

/**
 * {@link BaseViewModel} for {@link MainActivity}
 */
public class MainViewModel extends BaseViewModel {

    private final int INITIAL_PAGE = 1;

    private final GetCharactersUseCase getCharactersUseCase;

    private MutableLiveData<List<Character>> characters = new MutableLiveData<>();
    private MutableLiveData<List<Character>> nextCharacters = new MutableLiveData<>();
    private MutableLiveData<Boolean> moreCharacters = new MutableLiveData<>();
    private MutableLiveData<Integer> nextPage = new MutableLiveData<Integer>();

    private int page = 0;

    MainViewModel(UseCaseHandler useCaseHandler,
                  GetCharactersUseCase getCharactersUseCase) {
        super(useCaseHandler);
        this.getCharactersUseCase = getCharactersUseCase;
    }

    void loadCharacters(int pageRequest) {

        if (pageRequest == -1) page = INITIAL_PAGE;
        else page = pageRequest;

        if (page == INITIAL_PAGE) commonProgressLayoutState.setValue(SHOW);

        GetCharactersRequest request = new GetCharactersRequest();
        request.setPage(page);

        useCaseHandler.execute(getCharactersUseCase,
                new GetCharactersUseCase.RequestValues(request),
                new UseCase.UseCaseCallback<GetCharactersUseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(GetCharactersUseCase.ResponseValue response) {

                        Info info = response.getCharactersResponse().getInfo();

                        List<Character> charactersResponse = response.getCharactersResponse().getCharacters();

                        if (pageRequest == INITIAL_PAGE) {
                            characters.setValue(charactersResponse);
                        } else {
                            nextCharacters.setValue(charactersResponse);
                        }

                        if (info != null) {
                            moreCharacters.setValue(!info.getNext().isEmpty());
                            nextPage.setValue(!info.getNext().isEmpty() ? info.getActual() + 1 : -1);
                        } else {
                            moreCharacters.setValue(true);
                        }

                        commonProgressLayoutState.setValue(HIDE);
                    }

                    @Override
                    public void onError(String error) {
                        if (page == INITIAL_PAGE) commonProgressLayoutState.setValue(HIDE);
                        snackbarMessage.setValue(error);
                    }
                });
    }

    MutableLiveData<List<Character>> getCharacters() {
        return characters;
    }

    MutableLiveData<List<Character>> getNextCharacters() {
        return nextCharacters;
    }

    MutableLiveData<Boolean> getMoreCharacters() {
        return moreCharacters;
    }

    MutableLiveData<Integer> getNextPage() {
        return nextPage;
    }

}
