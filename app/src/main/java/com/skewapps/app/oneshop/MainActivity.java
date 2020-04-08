package com.skewapps.app.oneshop;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {



    private static final int REQUEST_CODE = 7142;
    Button SignInButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    private String TAG = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        SignInButton = (Button) findViewById(R.id.sign_in_btn);

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Home.class));


            }
        });





     /*
        Log.d(TAG,"im here");
       // printKey();


        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Users");



        SignInButton = (Button) findViewById(R.id.sign_in_btn);

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginProcess();
            }
        });


        if (AccountKit.getCurrentAccessToken()!=null){
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(Account account) {
                   progressDialog.dismiss();

                    reference.child(account.getPhoneNumber().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            User localUser = dataSnapshot.getValue(User.class);
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = localUser;
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onError(AccountKitError accountKitError) {

                }
            });

        }


        */

    }

    /*
    private void startLoginProcess() {

        Intent intent = new Intent(MainActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder = new AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,AccountKitActivity.ResponseType.TOKEN
        );
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE){

            AccountKitLoginResult result =  data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError()!=null)
            {
                Toast.makeText(this,"" + result.getError().getErrorType().getMessage()
                ,Toast.LENGTH_SHORT).show();
                return;
            }
            else if (result.wasCancelled()){
                Toast.makeText(this,"Cancel"
                        ,Toast.LENGTH_SHORT).show();
            }
            else {
                if (result.getAccessToken()!=null){

                    final ProgressDialog progressDialog =  new ProgressDialog(this);
                    progressDialog.setMessage("Wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            final String userPhone = account.getPhoneNumber().toString();

                            reference.orderByKey().equalTo(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.child(userPhone).exists()){

                                        User newUser = new User();
                                        newUser.setPhone(userPhone);
                                        newUser.setName(account.getId());


                                        reference.child(userPhone).setValue(newUser).addOnCompleteListener(
                                                new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                            Toast.makeText(MainActivity.this,"User register successfully",
                                                                    Toast.LENGTH_LONG).show();


                                                        //LOGIN
                                                        reference.child(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                User localUser = dataSnapshot.getValue(User.class);
                                                                Intent intent = new Intent(MainActivity.this, Home.class);
                                                                Common.currentUser = localUser;
                                                                startActivity(intent);
                                                                progressDialog.dismiss();
                                                                finish();
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                        );
                                    }else {
                                        reference.child(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                User localUser = dataSnapshot.getValue(User.class);
                                                Intent intent = new Intent(MainActivity.this, Home.class);
                                                Common.currentUser = localUser;
                                                startActivity(intent);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                        Toast.makeText(MainActivity.this,"" + accountKitError.getErrorType().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }        }

    }

    */
}
