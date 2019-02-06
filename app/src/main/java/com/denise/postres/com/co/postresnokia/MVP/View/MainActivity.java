package com.denise.postres.com.co.postresnokia.MVP.View;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.denise.postres.com.co.postresnokia.API.ApiClient;
import com.denise.postres.com.co.postresnokia.API.DaggerDependency;
import com.denise.postres.com.co.postresnokia.MVP.Adapters.MainAdapter;
import com.denise.postres.com.co.postresnokia.MVP.Presenter.PostresPresenter;
import com.denise.postres.com.co.postresnokia.MVP.Presenter.VMController;
import com.denise.postres.com.co.postresnokia.Models.PostresResponse;
import com.denise.postres.com.co.postresnokia.R;
import com.denise.postres.com.co.postresnokia.API.ApiModule;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements VMController.PostresView {
    private RecyclerView list;
    private FloatingActionButton addBtn, filterBtn;
    @Inject
    public ApiClient networkClient;
    ProgressBar progressBar;
    PostresPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDependency.builder().apiModule(new ApiModule()).build().inject(this);
        manageViewElements();

        presenter = new PostresPresenter(networkClient, this);
        presenter.getDessertList();
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
    }

    @Override
    public void getPostresListSuccess(List<PostresResponse> dessertListResponse) {

        MainAdapter adapter = new MainAdapter(getApplicationContext(), dessertListResponse,
                new MainAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(PostresResponse Item) {
                        presenter.showDessertSelected(Item);

                    }
                });

        list.setAdapter(adapter);
    }

    @Override
    public void showFilterDialog(final String[] types) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_filter, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);
        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
        ArrayAdapter<String> adaptader =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, types);
        ListView lstTypes = (ListView) view.findViewById(R.id.listType);
        lstTypes.setAdapter(adaptader);
        lstTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                presenter.filterDessertByType(types[position]);
                alertDialog.dismiss();
            }
        });
        final Button cancelBtn = view.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public Context getViewContext() {
        return getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.filterDessertByType("Todos");
    }

    public void clickMenuButton(View v) {
        presenter.clickMenuButton(v);
    }

    private void manageViewElements() {
        setContentView(R.layout.activity_dessert);
        list = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        list.setLayoutManager(new LinearLayoutManager(this));
    }
}

