package com.example.edmt_dimo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edmt_dimo.Common.Common;
import com.example.edmt_dimo.Model.UserModel;
import com.example.edmt_dimo.Remote.ICloudFunctions;
import com.example.edmt_dimo.Remote.RetrofitCloudClient;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    private static int APP_REQUEST_CODE = 1717;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private AlertDialog dialog;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ICloudFunctions cloudFunctions;

    private DatabaseReference userRef;
    private List<AuthUI.IdpConfig> providers;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        

        if (listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        compositeDisposable.clear();


        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();


    }

    private void init() {


        providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());
        userRef = FirebaseDatabase.getInstance().getReference(Common.USER_REFERENCES);
        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(this).build();
        cloudFunctions = RetrofitCloudClient.getInstance().create(ICloudFunctions.class);
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //    Toast.makeText(MainActivity.this, "Alrady login", Toast.LENGTH_SHORT).show();
                    cheackUserFromFirbase(user);
                } else {
                    phoneLogin();
                }
            }
        };

    }


    private void cheackUserFromFirbase(FirebaseUser user) {
        dialog.show();
        userRef.child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                            getToHomeActivity(userModel);
                        } else {
                            showRegisterDialog(user);

                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void showRegisterDialog(FirebaseUser user) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Register");
        builder.setMessage("Please fill information");

        View itemview = LayoutInflater.from(this).inflate(R.layout.layout_register, null);
        EditText edt_name = itemview.findViewById(R.id.edt_name);
        EditText edt_address = itemview.findViewById(R.id.edt_address);
        EditText edt_phone = itemview.findViewById(R.id.edt_phone);

        edt_phone.setText(user.getPhoneNumber());

        builder.setView(itemview);
        builder.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(edt_name.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_address.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                UserModel userModel = new UserModel();
                userModel.setUid(user.getUid());
                userModel.setName(edt_name.getText().toString());
                userModel.setName(edt_address.getText().toString());
                userModel.setName(edt_phone.getText().toString());

                userRef.child(user.getUid()).setValue(userModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    dialogInterface.dismiss();
                                    getToHomeActivity(userModel);
                                    Toast.makeText(MainActivity.this, "Congratulation ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

        builder.setView(itemview);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getToHomeActivity(UserModel userModel) {
        Common.currentUser = userModel;
    }

    private void phoneLogin() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        } else {
            Toast.makeText(this, "Failed to sing in", Toast.LENGTH_SHORT).show();
        }
    }


}