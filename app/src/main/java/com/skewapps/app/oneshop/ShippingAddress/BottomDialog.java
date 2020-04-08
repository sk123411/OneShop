package com.skewapps.app.oneshop.ShippingAddress;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skewapps.app.oneshop.Common.Common;
import com.skewapps.app.oneshop.PaymentHandler.FinalOrder;
import com.skewapps.app.oneshop.R;

public class BottomDialog extends BottomSheetDialogFragment {
   private EditText c_name;
   private EditText c_city;
   private EditText c_state;
    private  EditText c_cout;
    private EditText c_address;
    private EditText c_phone;
    private  EditText c_pincode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_dialog,container,false);


         c_name = v.findViewById(R.id.customer_name);
         c_city = v.findViewById(R.id.customer_City);
         c_state = v.findViewById(R.id.customer_state);
         c_cout = v.findViewById(R.id.customer_county);
         c_address = v.findViewById(R.id.customer_address);
         c_phone = v.findViewById(R.id.customer_phone);
         c_pincode = v.findViewById(R.id.customer_pincode);
        c_phone = v.findViewById(R.id.customer_phone);

        SendCustomerAdress();




        final String fp= getArguments().getString(Common.FINALPRICE);



        Button button1 = v.findViewById(R.id.b1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fp!=null) {
                    Intent intent = new Intent(getContext(), FinalOrder.class);
                    intent.putExtra(Common.FINALPRICE,fp);
                    SendCustomerAdress();
                    startActivity(intent);
                    try {
                        getActivity().finish();
                    }catch (NullPointerException e){
                        Toast.makeText(getActivity(),"Error, Please try again",Toast.LENGTH_SHORT).show();

                    }

                }else {

                    Toast.makeText(getActivity(),"Error, Please try again",Toast.LENGTH_SHORT).show();
                }


            }
        });
        return v;

    }

    private void SendCustomerAdress(){
        Common.FO_NAME = c_name.getText().toString();
        Common.FO_CITY = c_city.getText().toString();
        Common.FO_STATE = c_state.getText().toString();
        Common.FO_COUNTRY = c_cout.getText().toString();
        Common.FO_FULLADRESS = c_address.getText().toString();
        Common.FO_PINCODE = c_pincode.getText().toString();
        Common.FO_PHONE = c_phone.getText().toString();
    }



}
