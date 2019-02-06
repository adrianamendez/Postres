package com.denise.postres.com.co.postresnokia.MVP.Presenter;

import android.content.Intent;
import android.view.View;
import com.denise.postres.com.co.postresnokia.Models.PostresResponse;

import com.denise.postres.com.co.postresnokia.API.ApiClient;
import com.denise.postres.com.co.postresnokia.API.ApiError;
import com.denise.postres.com.co.postresnokia.DB.SQLHelper;
import com.denise.postres.com.co.postresnokia.MVP.View.AddPostreActivity;
import com.denise.postres.com.co.postresnokia.MVP.View.PostresMoreActivity;


import com.denise.postres.com.co.postresnokia.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class PostresPresenter {
    private final ApiClient networkClient;
    private final VMController.PostresView view;
    private CompositeSubscription subscriptions;
    private List<String> typesL = new ArrayList<>();
    private SQLHelper dc;
    private List<PostresResponse> dessertsList;

    public PostresPresenter(ApiClient networkClient, VMController.PostresView view) {
        this.networkClient = networkClient;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        dc = new SQLHelper(view.getViewContext());
    }

    public void getDessertList() {
        view.showWait();

        Subscription subscription = networkClient.getPostresList(new ApiClient.GePostresListCallback(){
            @Override
            public void onSuccess(List<PostresResponse> postresListResponse) {
                dessertsList = dc.getAll();
                if (dessertsList.size() <= 0) {
                    dc.createAll(postresListResponse);
                    dessertsList = dc.getAll();
                } else {
                    dessertsList = dc.getAll();
                }
                view.removeWait();
                view.getPostresListSuccess(dessertsList);
            }


            @Override
            public void onError(ApiError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }

        });

        subscriptions.add(subscription);
    }

    public void clickMenuButton(View v) {
        switch (v.getId()) {
            case R.id.addFBtn:
                Intent addDessert = new Intent(view.getViewContext(), AddPostreActivity.class);
                addDessert.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getViewContext().startActivity(addDessert);
                break;
            case R.id.filterFBtn:
                typesL.add("Todos");
                for (PostresResponse d : dc.getAll()) {
                    typesL.add(d.getType());
                }
                typesL = new ArrayList(new LinkedHashSet(typesL));
                String[] types = typesL.toArray(new String[0]);
                view.showFilterDialog(types);
                break;
            default:
                break;
        }
    }

    public void filterDessertByType(String type) {
        if (type.equalsIgnoreCase("todos")) {
            view.getPostresListSuccess(dc.getAll());
        } else {
            view.getPostresListSuccess(dc.getFiltered(type));
        }
    }

    public void showDessertSelected(PostresResponse dessertSelected) {
        Intent More = new Intent(view.getViewContext(), PostresMoreActivity.class);
        More.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Gson gson = new Gson();
        String myJson = gson.toJson(dessertSelected);
        More.putExtra("dessertSelected", myJson);
        view.getViewContext().startActivity(More);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }

}