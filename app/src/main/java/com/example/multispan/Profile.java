package com.example.multispan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String EMPLOYEE_KEY_PREF = "employee_key";

    private EditText employeeNameEdt, employeePhoneEdt, employeeAddressEdt ,employeeDepartmentEdt;
    private ImageView addImgbtn,sendDatabtn , updateDatabtn ;
    private ImageView profileImageView; // Added ImageView for profile picture

    private EmployeeInfo employeeInfo;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        employeeNameEdt = findViewById(R.id.idEdtEmployeeName);
        employeePhoneEdt = findViewById(R.id.idEdtEmployeePhoneNumber);
        employeeAddressEdt = findViewById(R.id.idEdtEmployeeAddress);
        employeeDepartmentEdt = findViewById(R.id.idEdtEmployeeDepartment);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUserUid = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("EmployeeInfo").child(currentUserUid);
            employeeInfo = new EmployeeInfo();
        } else {
            Toast.makeText(Profile.this, "Errorrr occure", Toast.LENGTH_SHORT).show();
        }

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("ProfileImages");

        sendDatabtn = findViewById(R.id.idBtnSendData);
        profileImageView = findViewById(R.id.profileIconID);
        addImgbtn = findViewById(R.id.profileBtnID);
        updateDatabtn = findViewById(R.id.idBtnEditData);



        // Retrieve the employee data from Firebase and set it to the respective EditText fields
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    employeeInfo = dataSnapshot.getValue(EmployeeInfo.class);
                    if (employeeInfo != null) {
                        employeeNameEdt.setText(employeeInfo.getEmployeeName());
                        employeePhoneEdt.setText(employeeInfo.getEmployeeContactNumber());
                        employeeAddressEdt.setText(employeeInfo.getEmployeeAddress());
                        employeeDepartmentEdt.setText(employeeInfo.getEmployeeDepartment());
                        loadImageFromFirebaseStorage(currentUserUid);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, "Failed to retrieve employee data", Toast.LENGTH_SHORT).show();
            }
        });

        // save data to firebase
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = employeeNameEdt.getText().toString();
                String phone = employeePhoneEdt.getText().toString();
                String address = employeeAddressEdt.getText().toString();
                String department = employeeDepartmentEdt.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(department)){
                    Toast.makeText(Profile.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addDataToFirebase(name, phone, address,department);
                }
            }
        });

        addImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });


        updateDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfilePopup();
            }
        });



    }
    // image picker to upload it
        private void openImagePicker() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
        }



        // it gives the result to get img
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        // add data to firebase
        private void addDataToFirebase(String name, String phone, String address,String department) {
            employeeInfo.setEmployeeName(name);
            employeeInfo.setEmployeeContactNumber(phone);
            employeeInfo.setEmployeeAddress(address);
            employeeInfo.setEmployeeDepartment(department);

            userRef.setValue(employeeInfo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Profile.this, "Employee data added", Toast.LENGTH_SHORT).show();
                                uploadProfileImage(currentUserUid);
                            } else {
                                Toast.makeText(Profile.this, "Failed to add employee data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    //upload img in storage
    private void uploadProfileImage(String employeeKey) {
        if (imageUri != null) {
            StorageReference imageRef = storageReference.child(employeeKey + ".jpg");

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Profile.this, "Profile image uploaded", Toast.LENGTH_SHORT).show();
                            finish(); // Optional: Go back to the main screen after uploading
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profile.this, "Failed to upload profile image", Toast.LENGTH_SHORT).show();
                            finish(); // Optional: Go back to the main screen even if uploading fails
                        }
                    });
        } else {
            finish(); // Optional: Go back to the main screen if no profile image is selected
        }
    }

    // load image from storage
    private void loadImageFromFirebaseStorage(String employeeKey) {
        StorageReference imageRef = storageReference.child(employeeKey + ".jpg");

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Use any image loading library or method of your choice to load the image into the ImageView
                Glide.with(Profile.this)
                        .load(uri)
                        .into(profileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Failed to load profile image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Edit function
    private void showEditProfilePopup() {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.edit_profile_popup, null);

        // Get references to the views in the popup layout
        EditText edtEmployeeName = view.findViewById(R.id.idEditEmployeeName);
        EditText edtEmployeePhone = view.findViewById(R.id.idEditEmployeePhoneNumber);
        EditText edtEmployeeAddress = view.findViewById(R.id.idEditEmployeeAddress);
        EditText edtEmployeeDepartment = view.findViewById(R.id.idEditEmployeeDepartment);
        ImageView btnSave = view.findViewById(R.id.idEditUpdateBtn);
        ImageView btncancle = view.findViewById(R.id.idEditCancleBtn);

        // Set the current employee data to the EditText fields
        edtEmployeeName.setText(employeeInfo.getEmployeeName());
        edtEmployeePhone.setText(employeeInfo.getEmployeeContactNumber());
        edtEmployeeAddress.setText(employeeInfo.getEmployeeAddress());
        edtEmployeeDepartment.setText(employeeInfo.getEmployeeDepartment());
        // Create the dialog
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        // Set click listener for the Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated data from the EditText fields
                String updatedName = edtEmployeeName.getText().toString().trim();
                String updatedPhone = edtEmployeePhone.getText().toString().trim();
                String updatedAddress = edtEmployeeAddress.getText().toString().trim();
                String updateDepartment = edtEmployeeDepartment.getText().toString().trim();

                // Validate the data
                if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedPhone) || TextUtils.isEmpty(updatedAddress) || TextUtils.isEmpty(updateDepartment)) {
                    Toast.makeText(Profile.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the employee data
                employeeInfo.setEmployeeName(updatedName);
                employeeInfo.setEmployeeContactNumber(updatedPhone);
                employeeInfo.setEmployeeAddress(updatedAddress);
                employeeInfo.setEmployeeDepartment(updateDepartment);

                // Save the updated data to Firebase
                userRef.setValue(employeeInfo)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Profile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                dialog.dismiss(); // Dismiss the dialog
            }
        });

        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the popup without saving changes
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }


}
