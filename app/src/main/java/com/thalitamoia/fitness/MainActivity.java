package com.thalitamoia.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        rvmain = findViewById(R.id.rv_main);
        List<MainItem> MainItens = new ArrayList<>();
        MainItens.add(new MainItem(1,R.drawable.ic_baseline_wb_sunny_24,R.string.Imc, Color.BLACK));
        MainItens.add(new MainItem(2,R.drawable.ic_baseline_visibility_24,R.string.tmb, Color.BLUE));


        // 1 > definir o comportamento de layout da recycler view
        // mosaic
        //grid
        //linear (vert, hor)
        rvmain.setLayoutManager(new GridLayoutManager(this,2));
        MainAdapter adapter = new MainAdapter(MainItens);
        adapter.setListener(id -> {
            switch (id){
                case 1:
             startActivity(new Intent(MainActivity.this,ImcActivity.class));
              break;
                case 2 :
              startActivity(new Intent(MainActivity.this,TmbActivity.class));
              break;
            }


        });
        rvmain.setAdapter(adapter);


    }



    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private List<MainItem> mainItems;
        private OnItemClickListener listener;

        public MainAdapter(List<MainItem>mainItems){
            this.mainItems = mainItems;

        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            MainItem mainItemCurrent = mainItems.get(position);
            holder.bind(mainItemCurrent);

        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }
        // entenda como sendo a view da celula que esta dentro do recycler view
        private class MainViewHolder extends RecyclerView.ViewHolder{


            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(MainItem item){
                TextView txtName = itemView.findViewById(R.id.item_txt_name);
                ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
                LinearLayout btn_imc = (LinearLayout) itemView.findViewById(R.id.btn_imc);

                btn_imc.setOnClickListener(v -> {
                    listener.onClick(item.getId());

                });


                txtName.setText(item.getTextStringId());
                imgIcon.setImageResource(item.getDrawableId());
                btn_imc.setBackgroundColor(item.getColor());


            }
        }
    }



}