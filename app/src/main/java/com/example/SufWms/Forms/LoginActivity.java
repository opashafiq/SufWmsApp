package com.example.SufWms.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SufWms.ApiHelpers.GetDataService;
import com.example.SufWms.ApiHelpers.RetrofitClientInstance;
import com.example.SufWms.Classes.Passwd;
import com.example.SufWms.Classes.ProjectVariables;
import com.example.SufWms.MainActivity;
import com.example.SufWms.Models.PartDetails;
import com.example.SufWms.R;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    String strIsValidPass="";
    Passwd passwd = new Passwd();
    TextView tvMessage;
    EditText txtUserID,txtPassword;
    Intent intent;
    private String TAG="Login Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale("en");
        setContentView(R.layout.activity_login);
        ProjectVariables.url = loadBaseIPAddress();
    }

    private void initObjects(){
        tvMessage=(TextView)findViewById(R.id.tvLoginMessage);
        tvMessage.setText(getString(R.string.label_default_login));
        txtUserID=(EditText) findViewById(R.id.txtUserName);
        txtPassword=(EditText) findViewById(R.id.txtPassword);
        //intent = new Intent(this, MainActivity.class);
        intent = new Intent(this, MainActivity.class);
    }

    private void initObjectListeners(){
        Button btnSave = (Button)findViewById(R.id.btnSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSettings(v);
            }
        });


        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPassword(v);
            }
        });
    }

    private void showMessage(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
    }
    public void ShowSettings(View v)
    {
        Intent intentL = new Intent(this, SettingsActivity.class);
        startActivity(intentL);
    }

    public void CheckPassword(View v)
    {
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Passwd>> call = service.doGetAuth(txtUserID.getText().toString(),txtPassword.getText().toString());
        call.enqueue(new Callback<List<Passwd>>() {
            @Override
            public void onResponse(Call<List<Passwd>> call, Response<List<Passwd>> response) {
                pDialog.dismiss();
                ProcessPass(response.body());
            }

            @Override
            public void onFailure(Call<List<Passwd>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ProcessPass(List<Passwd> _passwd)
    {
        passwd=_passwd.get(0);
        if(passwd.getIsValid().equals("true"))
        {
            setProjectVariables();
            startActivity(intent);
            overridePendingTransition(R.anim.right_in,R.anim.left_out);

            finish();
        }
        else
        {
            tvMessage.setText(getString(R.string.failed_login));
        }
    }

    private void setProjectVariables() {
        //Email server configurations
        ProjectVariables.EmailUser = passwd.getEmailUser();
        ProjectVariables.EmailPassword = passwd.getEmailPassowrd();
        ProjectVariables.EmailServerAddress = passwd.getServerAddress();
        ProjectVariables.EmailServerPort = passwd.getPortNo();
        ProjectVariables.SSLEnabled = passwd.getSSLEnabled();
        Log.e("SSL Enabled : " , ProjectVariables.SSLEnabled);
        //Login Id
        ProjectVariables.LoginId = txtUserID.getText().toString();
        ProjectVariables.userid = passwd.getUserId();
        ProjectVariables.UserGroup = passwd.getUserGroup();

    }

    /** Load The Base IP/Web Address from preference */
    private String loadBaseIPAddress() {
        /*
         * Open a reference to the "ip_web_address"  using SharedPreferences
         * */
        SharedPreferences sharedPref =getSharedPreferences(getString(R.string.ip_web_address), Context.MODE_PRIVATE);

        /*
         * Retrieve The values the KEY : ip_web_address_1
         * */
        String strPreferedVal = sharedPref.getString(getString(R.string.ip_web_address_1), "http://162.214.97.103/DMR/DMR/");
        return strPreferedVal;
    }

    //Set Language
    public void setLocale(String localeName) {
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }
}