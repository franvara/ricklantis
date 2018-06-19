package com.franvara.ricklantis.presentation.utils;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class PicassoCache {
    /**
     * We use a custom PicassoCache changing the cache time.
     * The first time, Picasso will download the image.
     * The client will only download an image over the network if one does not exist in
     * its local cache or if the custom cache time expired (1 week).
     */

    private static Picasso picassoInstance = null;

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());

        Response build = originalResponse.newBuilder()
                .header("Cache-Control", "max-age=" + 60 * 60 * 24 * 7) //1 week
                .header("expires", "")
                .build();
        return build;
    };

    private PicassoCache(Context context) {
        Cache cache = new Cache(new File(context.getCacheDir(), "PicassoCache"), Long.MAX_VALUE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache).addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);

        OkHttpClient client = builder.build();

        picassoInstance = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
    }

    public static Picasso getPicassoInstance(Context context) {
        if (picassoInstance == null) {
            new PicassoCache(context);
            return picassoInstance;
        }
        return picassoInstance;
    }
}
