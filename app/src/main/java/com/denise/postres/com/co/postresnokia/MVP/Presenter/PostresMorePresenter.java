package com.denise.postres.com.co.postresnokia.MVP.Presenter;

import android.content.Context;

import com.denise.postres.com.co.postresnokia.DB.SQLHelper;
import com.denise.postres.com.co.postresnokia.Models.PostresResponse;


public class PostresMorePresenter {
    private  VMController.PostresDetailView view;
    private SQLHelper dc;

    public PostresMorePresenter(
            VMController.PostresDetailView view) {
        this.view = view;
    }

    public void showDialog(){
        view.showDialog();
    }

    public void deleteDessert(Context c, PostresResponse dessertSelected){
        dc = new SQLHelper(c.getApplicationContext());
        // dc.removeDessert(dessertSelected.getId());
    }

}
