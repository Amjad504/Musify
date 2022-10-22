package com.AmjadArshadi190504;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.print.PrintJob;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class sign_up extends AppCompatActivity {

    TextView Signup;
    EditText Name;
    EditText Email;
    EditText number;
    EditText pass;
    EditText Roll;
    String sessionId;


    String name,email,Dp,Number,Pass;


    CircleImageView dp;
    byte[] bytes;
    Bitmap bitmap;
    Uri dpp;
    private final int PICK_IMAGE_REQUEST = 22;




    // creating a variable for our
    // Firebase Database.
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Profiledata profiledata;


    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
       dp = (CircleImageView) findViewById(R.id.dp);
        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        number = (EditText) findViewById(R.id.number);
        pass = (EditText) findViewById(R.id.password) ;

        // getting text from our edittext fields.

       dp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               chooseImage();
           }
       });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profiledata");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profiledata = new Profiledata();

        Signup = findViewById(R.id.Signup);






        // adding on click listener for our button.
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = Name.getText().toString();
                email = Email.getText().toString();
                Number = ("+92"+number);
                Pass = pass.getText().toString();

                if( (TextUtils.isEmpty(name))  &&  (TextUtils.isEmpty(email)) &&  (TextUtils.isEmpty(Number))  &&  (TextUtils.isEmpty(Pass)) ){
                    Toast.makeText(sign_up.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase();
                }
            }
        });
    }



    private void addDatatoFirebase() {

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();

        // getting text from our edittext fields.
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String Num = ("+92"+number.getText().toString());
        String Pass = pass.getText().toString();



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String Id = currentUser.getUid();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(50));


        uploader.putFile(dpp)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri){

                                dialog.dismiss();
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("users");
                                Profiledata obj=new Profiledata(uri.toString(),name,email,Num,Pass);
                                root.child(Id).setValue(obj);

//                                Name.setText("");
//                                Email.setText("");
//                                Number.setText("");
//                                Password.setText("");
//                                dp.setImageResource(R.drawable.ic_launcher_background);
                                Toast.makeText(sign_up.this, "Data added Successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(sign_up.this, MainActivity.class);
                                startActivity(i);



                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded :"+(int)percent+" %");
                    }
                });
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            dpp = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), dpp);
                dp.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }




}
