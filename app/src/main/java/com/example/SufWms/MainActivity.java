package com.example.SufWms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.SufWms.Adapters.RV_PartDetails_Adapter;
import com.example.SufWms.ApiHelpers.GetDataService;
import com.example.SufWms.ApiHelpers.RetrofitClientInstance;
import com.example.SufWms.Models.DamageRepairParts;
import com.example.SufWms.Models.InsertionMessage;
import com.example.SufWms.Models.PartDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    RecyclerView rvPartDetails;
    //RecyclerView Adapter Class
    ArrayList<PartDetails> listPartDetails = new ArrayList<>();
    ArrayList<InsertionMessage> listInsertionMessage = new ArrayList<>();
    ArrayList<DamageRepairParts> listDamageRepairParts = new ArrayList<>();
    RV_PartDetails_Adapter rvAdapterPartDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        initObjectListeners();

        //Set RecyclerView Adapter
        rvAdapterPartDetails = new RV_PartDetails_Adapter(this,listPartDetails);
        rvPartDetails.setAdapter(rvAdapterPartDetails);

    }

    private void initObjects(){
        rvPartDetails = (RecyclerView) findViewById(R.id.recyclePartDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvPartDetails.setLayoutManager(layoutManager);
        pDialog = new ProgressDialog(MainActivity.this);
    }

    private void initObjectListeners(){
        Button btnLoadData = (Button)findViewById(R.id.btnLoadData);
        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();

                /*Create handle for the RetrofitInstance interface*/
                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<List<PartDetails>> call = service.doGetPartDetails("101");
                call.enqueue(new Callback<List<PartDetails>>() {
                    @Override
                    public void onResponse(Call<List<PartDetails>> call, Response<List<PartDetails>> response) {
                        pDialog.dismiss();
                        loadRecycleView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<PartDetails>> call, Throwable t) {
                        pDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Button btnDamageEntry = (Button)findViewById(R.id.btnDamageEntry);
        btnDamageEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }

    private void loadRecycleView(List<PartDetails> _listPartDetails){
        listPartDetails.clear();
        listPartDetails.addAll(_listPartDetails);
        rvAdapterPartDetails.notifyDataSetChanged();
    }

    private void saveData(){
        listDamageRepairParts = new ArrayList<>();
        DamageRepairParts damageRepairParts = new DamageRepairParts();
        damageRepairParts.setAutoId("0");
        damageRepairParts.setDamageId("24232");
        damageRepairParts.setPartId("1501");
        damageRepairParts.setQty("600");
        damageRepairParts.setUserId("1");
        listDamageRepairParts.add(damageRepairParts);



        Log.e("Count of list: ","Size: " + listDamageRepairParts.size());

        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<InsertionMessage>> call = service.doInsertPartDetails(listDamageRepairParts);
        //Call<List<InsertionMessage>> call = service.doInsertPartDetails("tstparr");
        call.enqueue(new Callback<List<InsertionMessage>>() {
            @Override
            public void onResponse(Call<List<InsertionMessage>> call, Response<List<InsertionMessage>> response) {
                pDialog.dismiss();
                Toast.makeText(MainActivity.this, response.body().get(0).getStrMessage() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<InsertionMessage>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}