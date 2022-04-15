package com.example.SufWms.Forms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SufWms.ApiHelpers.GetDataService;
import com.example.SufWms.ApiHelpers.RetrofitClientInstance;
import com.example.SufWms.Classes.LocationDetails;
import com.example.SufWms.Classes.Passwd;
import com.example.SufWms.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {
    static boolean isDetected=true;
    static int intDetectionCount = 0;

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    //List<ExecutionMessage> listExecutionMessage = new ArrayList<>();
    private ProgressDialog pDialog;
    //ExecutionMessage executionMessage = new ExecutionMessage();
    private String TAG="Main Activity";
    private static String BarCode="";
    private static String UpdateStatus="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }

    private void InitObjects(){
        TextView tvPageHeading = (TextView)findViewById(R.id.tvPageHeading);
        tvPageHeading.setText(getResources().getString(R.string.heading_scan_page));
    }

    protected void onPause() {
        super.onPause();
        try {
            cameraSource.release();
            Log.e("Cam Release","Camera Released");
        } catch (NullPointerException ignored) {  }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    private void initialiseDetectorsAndSources() {
        Log.e("CAM","In Cam Initialize");
        Toast.makeText(getApplicationContext(), getString(R.string.barcode_scanner_started), Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.CODE_128)
                .build();
        Log.e("CAM","In Cam Initialize 1");
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();
        Log.e("CAM","In Cam Initialize 2");
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.e("CAM","In Cam Initialize 3");
                try {
                    if (ActivityCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //showPlantDetails();
                        cameraSource.start(surfaceView.getHolder());
                        Log.e("CAM","Source Started");
                    } else {
                        ActivityCompat.requestPermissions(ScannerActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e("CAM","In Surface Changed");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e("CAM","In Surface Destroyed");
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //Toast.makeText(getApplicationContext(), getString(R.string.barcode_memory_leak), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                Log.e("BAR Detection","isDetected : " + isDetected);
                if (barcodes.size() != 0 && isDetected) {
                    // scanCount++;
                    //Log.e("BAR","Barcode recognized: " + barcodes.valueAt(0).displayValue);
                    isDetected = false;
                    Log.e("BAR",String.valueOf(isDetected));
                    Log.e("BAR","Detected !!");
                    //If we want to access any view in UI Thread from here , we have to call "runOnUiThread"
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //txtBarcodeValue.setText(barcodes.valueAt(0).displayValue);
                                BarCode = barcodes.valueAt(0).displayValue;

                                cameraSource.stop();
                                processBarCode(BarCode);
                            }
                        });
                        Thread.sleep(300);
                        Log.e("BAR","After Thread of Dialogue");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }



        });
    }

    private void processBarCode(String BarCode){
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<LocationDetails>> call = service.doGetLocationDetails(BarCode);
        call.enqueue(new Callback<List<LocationDetails>>() {
            @Override
            public void onResponse(Call<List<LocationDetails>> call, Response<List<LocationDetails>> response) {
                pDialog.dismiss();
                //ProcessPass(response.body());
            }

            @Override
            public void onFailure(Call<List<LocationDetails>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(ScannerActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}