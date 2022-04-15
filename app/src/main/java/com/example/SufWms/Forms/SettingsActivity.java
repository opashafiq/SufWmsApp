package com.example.SufWms.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.SufWms.Classes.ProjectVariables;
import com.example.SufWms.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadPref();
        initObjectListeners();

    }

    private void initObjectListeners(){
        Button btnSave = (Button)findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePref(v);
            }
        });
    }

    /** Called when the user clicks loadPref button */
    public void loadPref() {
        EditText txtIP = (EditText) findViewById(R.id.txtServiceURL);
        txtIP.setText(ProjectVariables.url);
        RadioButton rbe=(RadioButton)findViewById(R.id.rbEnglish);
        RadioButton rbs=(RadioButton)findViewById(R.id.rbSpanish);

        Log.e("PV Value",ProjectVariables.language);
        showMessage("Language Found: " + ProjectVariables.language);
        if(ProjectVariables.language.equals("en")) {
            rbe.setChecked(true);
            rbs.setChecked(false);
        }else {
            rbe.setChecked(false);
            rbs.setChecked(true);
        }
    }

    private void showMessage(String msg){
        Toast.makeText(SettingsActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    /** Called when the user clicks btnSave button */
    public void savePref(View view) {



        EditText txtUrl = (EditText) findViewById(R.id.txtServiceURL);
        String strPreferedVal = txtUrl.getText().toString();
        String strPreferedLanguage="";
        RadioButton rbe = (RadioButton)findViewById(R.id.rbEnglish);
        if(rbe.isChecked()){
            strPreferedLanguage="en";
        }else {
            strPreferedLanguage="sp";
        }
        //Log.e("RB Value",strPreferedLanguage);
        //return;
        /*
         * In String file create a string element "ip_web_address"
         * This element is the file where KEY-VALUE pair will be saved
         * Create a sharedpreference using this key value
         * */
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.ip_web_address), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        /*
         * In String file create one string element
         * "ip_web_address_1"
         * This element represents the KEY in the "ip_web_address" created earliar.
         * Now save values in this KEY using editor
         * */
        editor.putString(getString(R.string.ip_web_address_1), strPreferedVal);
        editor.commit();
        ProjectVariables.url = strPreferedVal;

        //Save prefered language
        SharedPreferences sharedPrefLanguage = getSharedPreferences(getString(R.string.language), Context.MODE_PRIVATE);
        SharedPreferences.Editor editorLanguage = sharedPrefLanguage.edit();
        editorLanguage.putString(getString(R.string.language_1), strPreferedLanguage);
        editorLanguage.commit();
        ProjectVariables.language = strPreferedLanguage;

        Toast.makeText(this,getString(R.string.save_success_message),Toast.LENGTH_LONG).show();
    }
}