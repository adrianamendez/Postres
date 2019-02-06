package com.denise.postres.com.co.postresnokia.MVP.View;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.denise.postres.com.co.postresnokia.MVP.Presenter.PostresMorePresenter;
import com.denise.postres.com.co.postresnokia.MVP.Presenter.VMController;
import com.denise.postres.com.co.postresnokia.Models.Batter;
import com.denise.postres.com.co.postresnokia.Models.PostresResponse;
import com.denise.postres.com.co.postresnokia.Models.Topping;
import com.denise.postres.com.co.postresnokia.R;
import com.google.gson.Gson;

import java.util.List;

public class PostresMoreActivity  extends AppCompatActivity implements VMController.PostresDetailView {
    private TextView textView0, textView1, textView2;
    private ListView lstBatters, lstToppings;
    private PostresMorePresenter presenter;
    private PostresResponse dessertSelected;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PostresMorePresenter(this);
        manageViewElements();
        init();
    }


    public void init() {
        Gson gson = new Gson();
        dessertSelected = gson.fromJson(getIntent().getStringExtra("dessertSelected"), PostresResponse.class);
        textView0.setText(dessertSelected.getName());
        textView1.setText(dessertSelected.getType());
        textView2.setText(String.valueOf(dessertSelected.getPpu()));
        List<Topping> toppings = dessertSelected.getTopping();
        String[] t = new String[toppings.size()];
        for (int i = 0; i < toppings.size(); i++) {
            t[i] = toppings.get(i).getType();
        }

        List<Batter> batters = dessertSelected.getBatters().getBatter();
        String[] b = new String[batters.size()];
        for (int i = 0; i < batters.size(); i++) {
            b[i] = batters.get(i).getType();
        }
        ArrayAdapter<String> adapterB =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, b);
        lstBatters.setAdapter(adapterB);
        ArrayAdapter<String> adapterT =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, t);
        lstToppings.setAdapter(adapterT);
    }

    private void manageViewElements() {
        setContentView(R.layout.activity_dessert_detail);
        textView0 = (TextView) findViewById(R.id.textView0);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        lstToppings = (ListView) findViewById(R.id.listTopping);
        lstBatters = (ListView) findViewById(R.id.listBatter);
    }

    public void deleteAction(View v){
        v.startAnimation(buttonClick);
        presenter.showDialog();
    }

    @Override
    public void showDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_delete, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(PostresMoreActivity.this);
        alertDialogBuilderUserInput.setView(view);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        })
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                      //   presenter.deleteDessert(getApplicationContext(),dessertSelected);
                        finish();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

    }

}
