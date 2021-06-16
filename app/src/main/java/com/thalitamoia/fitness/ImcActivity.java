package com.thalitamoia.fitness;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static androidx.core.os.LocaleListCompat.create;

public class ImcActivity extends AppCompatActivity {
    private EditText editHeigth;
    private EditText editweigth;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        editHeigth = findViewById(R.id.edit_imc_height);
        editweigth = findViewById(R.id.edit_imc_weight);
        Button btnsend = findViewById(R.id.btn_imc_send);


        btnsend.setOnClickListener(v -> {
            if (!validate()) {
                Toast.makeText(ImcActivity.this, R.string.erro, Toast.LENGTH_SHORT).show();
                return;
            }

            String sHeigth = editHeigth.getText().toString();
            String sweigth = editweigth.getText().toString();
            int heigth = Integer.parseInt(sHeigth);
            int weigth = Integer.parseInt(sweigth);
            double result = calculateimc(heigth,weigth);

            int imcResponseId = imcResponse(result);
            AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                   .setTitle(getString(R.string.imc_response,result))
                    .setMessage(imcResponseId)
                    .setPositiveButton(android.R.string.ok, (dialog1, which) -> {

                    })
                    .setNegativeButton(R.string.save, (dialog1, which) -> {
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc", result);
                            runOnUiThread(() -> {
                                if (calcId > 0) {
                                    Toast.makeText(ImcActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ImcActivity.this,ListCalcActivity.class);
                                    intent.putExtra("type","imc");
                                    startActivity(intent);
                                }});

                        }).start();

                    }).create();

                    dialog.show();
         InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(editweigth.getWindowToken(),0);
         imm.hideSoftInputFromWindow(editHeigth.getWindowToken(),0);
        });
    }
    // estruras de if else com retorno das strings ->
    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15)
            return R.string.imc_severely_low_weight;
        else if (imc < 16)
            return R.string.imc_very_low_weight;
        else if (imc < 18.5)
            return R.string.imc_low_weight;
        else if (imc < 25)
            return R.string.normal;
        else  if (imc < 30)
            return R.string.imc_high_weight;
        else  if (imc < 35)
            return R.string.imc_so_high_weight;
        else  if (imc < 40)
            return R.string.imc_severely_high_weight;
        else
            return R.string.imc_extreme_weight;
}

// calculo imc
    private double calculateimc(int heigth,int weigth){
        // peso / (altura * altura)

       return weigth / (((double) heigth / 100)* ((double) heigth/100));
    }


    // fazendo validaçoa
    private Boolean validate(){

        if (!editHeigth.getText().toString().startsWith("0")
                && !editweigth.getText().toString().startsWith("0")
                && !editHeigth.getText().toString().isEmpty()
                && !editweigth.getText().toString().isEmpty())
        { return true;

        }

        else {
            return false;
        }

    }


}