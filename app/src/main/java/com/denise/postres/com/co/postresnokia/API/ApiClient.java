package com.denise.postres.com.co.postresnokia.API;

import com.denise.postres.com.co.postresnokia.Models.PostresResponse;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ApiClient {

    private final ApiRetrofit networkService;

    public ApiClient(ApiRetrofit networkService) {
        this.networkService = networkService;
    }

    public Subscription getPostresList(final GePostresListCallback callback) {

        return networkService.getPostresList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<PostresResponse>>>() {
                    @Override
                    public Observable<? extends List<PostresResponse>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<PostresResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new ApiError(e));

                    }

                    @Override
                    public void onNext(List<PostresResponse> postresListResponse) {
                        callback.onSuccess(postresListResponse);

                    }
                });
    }

    public interface GePostresListCallback{
        void onSuccess(List<PostresResponse> postresListResponse);

        void onError(ApiError networkError);
    }
}
