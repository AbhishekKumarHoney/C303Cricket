package com.example.honey.c303cricket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddTeam extends AppCompatActivity {

    EditText editTextTeamId, editTextTeamName;
    ImageButton imageButtonAddImage;
    Button buttonAddTeam;


    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath = null;

    private StorageReference mStorageRef;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private Uri imageDownloadUrl = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        editTextTeamId = (EditText) findViewById(R.id.editTextTeamId);
        editTextTeamName = (EditText) findViewById(R.id.editTextTeamName);

        imageButtonAddImage = (ImageButton) findViewById(R.id.imageButtonAddImage);

        buttonAddTeam = (Button) findViewById(R.id.buttonAddTeam);

        mStorageRef = FirebaseStorage.getInstance().getReference();

//        database = FirebaseDatabase.getInstance("Teams");
//        database.getReference("message");
        myRef = FirebaseDatabase.getInstance().getReference("Teams");


    }

    public void onAddImageClicked(View view) {
        showFileChooser();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageButtonAddImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onAddTeamClicked(View view) {
        String teamId = editTextTeamId.getText().toString();
        String teamName = editTextTeamName.getText().toString();

        uploadFile(teamId, teamName);


    }

    private void uploadFile(final String teamId, final String teamName) {
        if (filePath != null && !TextUtils.isEmpty(teamId) && !TextUtils.isEmpty(teamName)) {
            StorageReference riversRef = mStorageRef.child("teamImages/" + System.currentTimeMillis() + "test.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddTeam.this,
                                    "Success", Toast.LENGTH_LONG).show();


                            // Get a URL to the uploaded content
                            @SuppressWarnings("VisibleForTests")
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            imageDownloadUrl = downloadUrl;

                            if (imageDownloadUrl != null) {
                                final DatabaseReference newPost = myRef.push();
                                newPost.child("ID").setValue(teamId);
                                newPost.child("NAME").setValue(teamName);
                                newPost.child("IMAGE").setValue(imageDownloadUrl.toString());
                            } else {
                                Toast.makeText(AddTeam.this,
                                        "Failed", Toast.LENGTH_LONG).show();
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddTeam.this,
                                    "Failed", Toast.LENGTH_LONG).show();

                        }
                    })

            ;
        }

    }
}
