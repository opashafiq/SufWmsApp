package com.example.SufWms.Forms.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SufWms.Adapters.RV_BookingMasterInDetails_Adapter;
import com.example.SufWms.Adapters.RV_BookingMasterIn_Adapter;
import com.example.SufWms.ApiHelpers.GetDataService;
import com.example.SufWms.ApiHelpers.RetrofitClientInstance;
import com.example.SufWms.Classes.BookingIn;
import com.example.SufWms.Classes.BookingInUpdate;
import com.example.SufWms.Classes.BookingMasterIn;
import com.example.SufWms.Classes.CustomerInfo;
import com.example.SufWms.Classes.ProjectVariables;
import com.example.SufWms.Interface.onRVClickInterface;
import com.example.SufWms.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingInFragment extends Fragment {
    private ProgressDialog pDialog;
    private String TAG="Booking In Master";

    //Combo Customer
    ArrayList arrCustomer=new ArrayList();
    ArrayList arrCustomerId=new ArrayList();
    AutoCompleteTextView comboCustomer;
    ArrayAdapter arrayAdapterCustomer;
    int intCustomerPosition=0;

    //RV Booking Master
    RecyclerView rvBookingMasterIn;
    ArrayList<BookingMasterIn> listBookingMasterIn = new ArrayList<>();
    RV_BookingMasterIn_Adapter rvAdapterBookingMasterIn;

    //RV Booking Details
    RecyclerView rvBookingDetailsIn;
    ArrayList<BookingIn> listBookingDetailsIn = new ArrayList<>();
    RV_BookingMasterInDetails_Adapter rvAdapterBookingDetailsIn;

    // RV OnClickInterface For master
    private onRVClickInterface onrvclickMasterInterface;

    // RV OnClickInterface For details
    private onRVClickInterface onrvclickDetailsInterface;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingInragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingInFragment newInstance(String param1, String param2) {
        BookingInFragment fragment = new BookingInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_booking_in, container, false);

        initObjects(v);

        //*load dummy data to master
//        BookingMasterIn bookingMasterIn = new BookingMasterIn();
//        bookingMasterIn.setBookingId("B0001");
//        bookingMasterIn.setOrderDate("21-04-2022");
//        bookingMasterIn.setCo_nm1("Company Name 1");
//        listBookingMasterIn.clear();
//        listBookingMasterIn.add(bookingMasterIn);
//        bookingMasterIn = new BookingMasterIn();
//        bookingMasterIn.setBookingId("B0002");
//        bookingMasterIn.setOrderDate("21-04-2022");
//        bookingMasterIn.setCo_nm1("Company Name 2");
//        listBookingMasterIn.add(bookingMasterIn);
//        rvAdapterBookingMasterIn.notifyDataSetChanged();
        //////////////////

        initObjectListener();
        getCustomerList();

        return v;
    }

    private void initObjects(View v){
        //Set up onRVOnclickInterface for Master
        onrvclickMasterInterface = new onRVClickInterface() {
            @Override
            public void setClick(int position) {
                Toast.makeText(getActivity().getApplicationContext(),"Position is: "+position,Toast.LENGTH_LONG).show();
                //ProjectVariables.bookingMasterIn=listBookingMasterIn.get(position);
                //getBookingDetailsIn(listBookingDetailsIn.get(position).getBookingId().toString());
            }
        };

        //Set up onRVOnclickInterface for Details
        onrvclickDetailsInterface = new onRVClickInterface() {
            @Override
            public void setClick(int position) {
                Toast.makeText(getActivity().getApplicationContext(),"Position is: "+position,Toast.LENGTH_LONG).show();
                ProjectVariables.bookingIn=listBookingDetailsIn.get(position);
                Intent _intent = new Intent(getActivity().getApplicationContext(), BookingInUpdate.class);
                startActivityForResult(_intent, 1);
            }
        };

        //Customer AutoCompleteText Box
        comboCustomer =(AutoCompleteTextView)v.findViewById(R.id.comboClientName_BookingIn);
//        arrayAdapterCustomer=new ArrayAdapter<String>(this.getContext(),R.layout.simple_spinner_item,R.id.tvListItemSpinnerVAT, arrCustomer);
//        comboCustomer.setAdapter(arrayAdapterCustomer);


        //Booking Master In RV
        rvBookingMasterIn = (RecyclerView) v.findViewById(R.id.recycleBookingInMaster);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvBookingMasterIn.setLayoutManager(layoutManager);
        rvAdapterBookingMasterIn = new RV_BookingMasterIn_Adapter(this.getContext(),listBookingMasterIn,onrvclickMasterInterface);
        rvBookingMasterIn.setAdapter(rvAdapterBookingMasterIn);

        //Booking Details In RV
        rvBookingDetailsIn = (RecyclerView) v.findViewById(R.id.recycleBookingInDetails);
        RecyclerView.LayoutManager layoutManagerDet = new LinearLayoutManager(this.getContext());
        rvBookingDetailsIn.setLayoutManager(layoutManagerDet);
        rvAdapterBookingDetailsIn = new RV_BookingMasterInDetails_Adapter(this.getContext(),listBookingDetailsIn,onrvclickDetailsInterface);
        rvBookingDetailsIn.setAdapter(rvAdapterBookingDetailsIn);


        pDialog = new ProgressDialog(this.getContext());
    }

    private void initObjectListener(){
        //Combo Customer info listener
//        comboCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selection = (String) parent.getItemAtPosition(position);
//                int pos = -1;
//
//                for (int i = 0; i < arrCustomer.size(); i++) {
//                    if (arrCustomer.get(i).toString().equals(selection)) {
//                        pos = i;
//                        break;
//                    }
//                }
//                intCustomerPosition = pos;
//                ProjectVariables.Rec_Id=arrCustomerId.get(intCustomerPosition).toString();
//                getBookingMasterIn(arrCustomerId.get(intCustomerPosition).toString());
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        comboCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.e(TAG,"In Autotextview Item Click Listener");
                String selection = (String) parent.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < arrCustomer.size(); i++) {
                    if (arrCustomer.get(i).toString().equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                intCustomerPosition = pos;
                ProjectVariables.Rec_Id=arrCustomerId.get(intCustomerPosition).toString();
                getBookingMasterIn(arrCustomerId.get(intCustomerPosition).toString());
            }
        });

        //Booking Master In Click
//        rvBookingMasterIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

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


    ////// Api Calls Start //////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getCustomerList(){
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<CustomerInfo>> call = service.doGetCustomerInfo();
        call.enqueue(new Callback<List<CustomerInfo>>() {
            @Override
            public void onResponse(Call<List<CustomerInfo>> call, Response<List<CustomerInfo>> response) {
                pDialog.dismiss();
                setCustomerList(response.body());
            }

            @Override
            public void onFailure(Call<List<CustomerInfo>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCustomerList(List<CustomerInfo> listCustomerInfo){
        //arrCustomer.clear();
        //arrCustomerId.clear();
        for (CustomerInfo customerIno:listCustomerInfo) {
            arrCustomer.add(customerIno.getRec_id());
            arrCustomerId.add(customerIno.getRec_id());
        }
        showMessage("Size of customer: " + arrCustomer.size());
        showMessage("First Item of customer: " + arrCustomer.get(0).toString());
        arrayAdapterCustomer=new ArrayAdapter<String>(this.getContext(),R.layout.simple_spinner_item,R.id.tvListItemSpinnerVAT, arrCustomer);
        comboCustomer.setAdapter(arrayAdapterCustomer);
        comboCustomer.setThreshold(1);
        comboCustomer.setText(listCustomerInfo.get(0).getRec_id());
        //arrayAdapterCustomer.notifyDataSetChanged();
    }

    private void getBookingMasterIn(String Rec_id){
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<BookingMasterIn>> call = service.doGetBookingMasterIn(Rec_id);
        call.enqueue(new Callback<List<BookingMasterIn>>() {
            @Override
            public void onResponse(Call<List<BookingMasterIn>> call, Response<List<BookingMasterIn>> response) {
                pDialog.dismiss();
                setBookingMasterIn(response.body());
            }

            @Override
            public void onFailure(Call<List<BookingMasterIn>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBookingMasterIn(List<BookingMasterIn> _listBookingMasterIn){
        listBookingMasterIn.clear();
        listBookingMasterIn.addAll(_listBookingMasterIn);
        rvAdapterBookingMasterIn.notifyDataSetChanged();
    }

    private void getBookingDetailsIn(String Booking_Id){
        pDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<BookingIn>> call = service.doGetBookingIn(Booking_Id);
        call.enqueue(new Callback<List<BookingIn>>() {
            @Override
            public void onResponse(Call<List<BookingIn>> call, Response<List<BookingIn>> response) {
                pDialog.dismiss();
                setBookingDetailsIn(response.body());
            }

            @Override
            public void onFailure(Call<List<BookingIn>> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBookingDetailsIn(List<BookingIn> _listBookingDetailsIn){
        listBookingDetailsIn.clear();
        listBookingDetailsIn.addAll(_listBookingDetailsIn);
        rvAdapterBookingDetailsIn.notifyDataSetChanged();
    }

////// Api Calls End //////////////////////////////////////////////////////////////////////////////////////////////////////
}