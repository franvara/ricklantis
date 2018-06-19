package com.franvara.ricklantis.data.repository.remote;

import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.domain.use_cases.responses.GetCharactersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("https://rickandmortyapi.com/api/character/")
    Call<GetCharactersResponse> getCharacters(@Query("page") int page);

}
