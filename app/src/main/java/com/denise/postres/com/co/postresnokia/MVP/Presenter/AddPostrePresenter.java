package com.denise.postres.com.co.postresnokia.MVP.Presenter;


import android.content.Context;

import com.denise.postres.com.co.postresnokia.DB.SQLHelper;
import com.denise.postres.com.co.postresnokia.Models.Batters;
import com.denise.postres.com.co.postresnokia.Models.PostresResponse;
import com.denise.postres.com.co.postresnokia.Models.Topping;

import java.util.List;


public class AddPostrePresenter {

    private VMController.AddPostresView view;

    public AddPostrePresenter(VMController.AddPostresView view) {
        this.view = view;
    }

    public void addDessert(String name, String type, Double ppu, int id, Batters b, List<Topping> t, Context c){
        SQLHelper dc = new SQLHelper(c);
       PostresResponse d = new PostresResponse(id, type, name, ppu, b, t);
        dc.create(d);
        view.addPostres();
    }
}
