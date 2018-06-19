package com.franvara.ricklantis.data.repository.remote;

import android.support.annotation.NonNull;

import com.franvara.ricklantis.BuildConfig;
import com.franvara.ricklantis.data.utils.LogUtils;
import com.franvara.ricklantis.domain.entities.BaseError;
import com.franvara.ricklantis.domain.entities.DataCallback;
import com.franvara.ricklantis.domain.threading.UseCaseHandler;
import com.franvara.ricklantis.domain.use_cases.requests.GetCharactersRequest;
import com.franvara.ricklantis.domain.use_cases.responses.GetCharactersResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource implements IRemoteDataSource {

    //region Fields

    private ApiServices apiServices = null;

    //endregion

    //region Constructor

    public RemoteDataSource() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.LOG_ENABLED) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .callbackExecutor(UseCaseHandler.getThreadPoolExecutor())
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServices = retrofit.create(ApiServices.class);
    }

    //endregion

    //region IRemoteDataSource implementation

    @Override
    public void getCharacters(GetCharactersRequest request,
                              final DataCallback<GetCharactersResponse, BaseError> callback) {
        Call<GetCharactersResponse> call = apiServices.getCharacters(request.getPage());
        try {
            call.enqueue(new Callback<GetCharactersResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetCharactersResponse> call,
                                       @NonNull Response<GetCharactersResponse> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                        return;
                    }

                    callback.onFailure(new BaseError(response.code(), response.message()));
                }

                @Override
                public void onFailure(@NonNull Call<GetCharactersResponse> call,
                                      @NonNull Throwable t) {
                    callback.onFailure(BaseError.getStandardError());
                }
            });
        } catch (Exception e) {
            callback.onFailure(BaseError.getStandardError());
            LogUtils.LogDebug("Exception", e);
        }
    }

//endregion

}
