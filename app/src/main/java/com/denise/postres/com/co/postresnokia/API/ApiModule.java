package com.denise.postres.com.co.postresnokia.API;




import java.io.IOException;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class ApiModule {
    public ApiModule() {

    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ApiRetrofit providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(ApiRetrofit.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ApiClient providesService(
            ApiRetrofit networkService) {
        return new ApiClient(networkService);
    }
}
