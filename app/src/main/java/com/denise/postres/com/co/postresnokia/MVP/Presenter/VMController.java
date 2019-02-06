package com.denise.postres.com.co.postresnokia.MVP.Presenter;


import android.content.Context;


import com.denise.postres.com.co.postresnokia.Models.Item;
import com.denise.postres.com.co.postresnokia.Models.PostresResponse;

import java.util.List;

public interface VMController {
    interface PostresDetailView {
        void showDialog();
    }
    interface PostresView {
        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void getPostresListSuccess(List<PostresResponse> postresListResponse);

        void showFilterDialog(String[] types);

        Context getViewContext();
    }

    interface AddPostresView {
        void addPostres();
    }

    interface BatterToppingView{
        void populateList(List<Item> elements);
    }


}
