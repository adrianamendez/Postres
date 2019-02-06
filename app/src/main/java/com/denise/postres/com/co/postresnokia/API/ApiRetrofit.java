package com.denise.postres.com.co.postresnokia.API;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

import com.denise.postres.com.co.postresnokia.Models.PostresResponse;

public interface ApiRetrofit {
    @GET("5c424e4832000077037327b0")
    Observable<List<PostresResponse>> getPostresList();
}
