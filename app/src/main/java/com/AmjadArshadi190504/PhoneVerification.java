package com.AmjadArshadi190504;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {



    EditText Number;
    TextView Generatebtn;
    EditText Verificationcode;
    TextView Verifybtn;
    FirebaseAuth mAuth;
    String number;
    String VerificationID;
    ProgressBar bar;
    private static final String USERS = "users";
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);


        Number = (EditText) findViewById(R.id.number);
        Generatebtn = (TextView) findViewById(R.id.genOTP);
        Verificationcode = (EditText) findViewById(R.id.code);
        Verifybtn = (TextView) findViewById(R.id.verifyOTP);
        mAuth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.bar);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        Generatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Number.getText().toString()))
                {
                    Toast.makeText(PhoneVerification.this,"Enter Valid Phone Number",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    number = Number.getText().toString();
                    Verificationcode.setVisibility(View.VISIBLE);
                    Verifybtn.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.VISIBLE);
                    sendercode(number);
                }

            }
        });
        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Verificationcode.getText().toString()))
                {
                    Toast.makeText(PhoneVerification.this,"Wrong OTP Entered ",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    verifycode(Verificationcode.getText().toString());
                }

            }
        });



    }

    private void sendercode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+92"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if(code != null)
            {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneVerification.this,"Verification Failed",
                    Toast.LENGTH_LONG).show();

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            VerificationID = s;
            Toast.makeText(PhoneVerification.this,"Code Sent",
                    Toast.LENGTH_LONG).show();
            Verifybtn.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }
    };

    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationID,Code);
        signinbyCredential(credential);


    }

    private void signinbyCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(PhoneVerification.this,"Verification Completed",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }


                    }
                });
    }

    private void updateUI(FirebaseUser user) {


        Intent i = new Intent(PhoneVerification.this, sign_up.class);
        i.putExtra("key",("+92"+number));
        startActivity(i);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            Intent i = new Intent(PhoneVerification.this, MainActivity.class);
            i.putExtra("key",("+92"+number));
            startActivity(i);

        }
    }
}