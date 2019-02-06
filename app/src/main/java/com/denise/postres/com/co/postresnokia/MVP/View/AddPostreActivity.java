package com.denise.postres.com.co.postresnokia.MVP.View;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.denise.postres.com.co.postresnokia.MVP.Presenter.AddPostrePresenter;
import com.denise.postres.com.co.postresnokia.MVP.Presenter.VMController;
import com.denise.postres.com.co.postresnokia.Models.Batter;
import com.denise.postres.com.co.postresnokia.Models.Batters;
import com.denise.postres.com.co.postresnokia.Models.Topping;
import com.denise.postres.com.co.postresnokia.R;

import java.util.ArrayList;
import java.util.List;


public class AddPostreActivity extends AppCompatActivity implements VMController.AddPostresView {
    private AddPostrePresenter presenter;
    private EditText edt0, edt1, edt2, edt3;
    private boolean isBatter = true;
    private List<Topping> t = new ArrayList<>();
    private List<Batter> b = new ArrayList<>();
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddPostrePresenter(this);
        manageGElements();
    }

    @Override
    public void addPostres() {
        finish();
    }

    public void clickButton(View v) {
        switch (v.getId()) {
            case R.id.batterBtn:
                isBatter = true;
                showDialog();
                break;
            case R.id.toppingBtn:
                isBatter = false;
                showDialog();
                break;
            case R.id.addBtn:
                v.startAnimation(buttonClick);
                String name = edt0.getText().toString();
                String type = edt1.getText().toString();
                String ppu = edt2.getText().toString();
                String id = edt3.getText().toString();
                if (!name.isEmpty() && !type.isEmpty() && !ppu.isEmpty() && !id.isEmpty()) {
                    Batters bs = new Batters(b);
                    presenter.addDessert(edt0.getText().toString(), edt1.getText().toString(),
                            Double.parseDouble(edt2.getText().toString()),
                            Integer.parseInt(edt3.getText().toString()), bs, t, getApplicationContext());
                } else {
                    Toast.makeText(this, "No dejes campos vacios", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void showDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_add_bt, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(AddPostreActivity.this);
        alertDialogBuilderUserInput.setView(view);
        final EditText id = view.findViewById(R.id.edt0);
        final EditText type = view.findViewById(R.id.edt1);
        final TextView title = view.findViewById(R.id.title);
        String textTitle = isBatter?"Agrega un Batido":"Agrega una Cubierta";
        title.setText(textTitle);
        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        final Button cancelBtn = view.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        final Button addBtn = view.findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!id.getText().toString().isEmpty() && !type.getText().toString().isEmpty() && isBatter) {
                    b.add(new Batter(Integer.valueOf(id.getText().toString()), type.getText().toString()));
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "No dejes campos vacios", Toast.LENGTH_SHORT).show();
                }
                if (!id.getText().toString().isEmpty() && !type.getText().toString().isEmpty() && !isBatter) {
                    t.add(new Topping(Integer.valueOf(id.getText().toString()), type.getText().toString()));
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "No dejes campos vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void manageGElements() {
        setContentView(R.layout.activity_add_dessert);
        edt0 = (EditText) findViewById(R.id.edt0);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
    }
}
