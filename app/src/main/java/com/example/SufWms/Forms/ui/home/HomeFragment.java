package com.example.SufWms.Forms.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.SufWms.Forms.TestActivity;
import com.example.SufWms.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        initObjectListener(v);
        return v;
    }


    public void initObjectListener(View v){
        Button b = (Button)v.findViewById(R.id.btnCallActivityTest);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getActivity().getApplicationContext(), TestActivity.class);
                startActivityForResult(_intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {//Returned from insert call

            if(resultCode == RESULT_OK){
                //Update List
            }
            if (resultCode == RESULT_CANCELED) {
                showMessage("Returned From activity");
            }
        }
    }//onActivityResult

    private void showMessage(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}