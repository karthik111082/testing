package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;
import com.hik.trendycraftshow.Utils.Validation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static ImageView ivImage;
    RoundImage roundImage;

    TextView email;
    EditText company_name, homephone, cellphone, street, city, zip,current_password,new_password,conf_edit_password;
    Spinner state;
    String Email,Campany_Name, HomePhone, CellNo, Street, City, State, Zip,Current_password,New_password,Confirm_edit_password;
    Button change_psw_main,confirm_psw_sub, save_profile, cancel_profile;

    LinearLayout change_psw_layout, profile_layout;

    InternetStatus internetStatus;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titletoolbar);
        title.setText("MY PROFILE");


        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_profile, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_profile_mob, container);
        }
        email = (TextView) findViewById(R.id.email);
        company_name = (EditText) findViewById(R.id.cname);
        homephone = (EditText) findViewById(R.id.home_phoneno);
        cellphone = (EditText) findViewById(R.id.cell_phoneno);
        street = (EditText) findViewById(R.id.street_address);
        city = (EditText) findViewById(R.id.city);
        zip = (EditText) findViewById(R.id.zip);

        current_password = (EditText) findViewById(R.id.current_password);
        new_password = (EditText) findViewById(R.id.new_password);
        conf_edit_password =(EditText)findViewById(R.id.conf_password);



        roundImage = new RoundImage();

        ivImage = (ImageView) findViewById(R.id.profile_image);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetStatus.InternetStatus(getApplicationContext())) {

                    selectImage();

                } else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        change_psw_layout = (LinearLayout) findViewById(R.id.change_psw_layout);
        profile_layout = (LinearLayout) findViewById(R.id.profile_layout);

        change_psw_main = (Button) findViewById(R.id.change_psw_main);
        change_psw_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profile_layout.setVisibility(View.GONE);
                change_psw_layout.setVisibility(View.VISIBLE);
                title.setText("CHANGE PASSWORD");

            }
        });

        save_profile = (Button) findViewById(R.id.save_profile);
        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internetStatus.InternetStatus(getApplicationContext())) {
                    Validation();
                    Toast.makeText(getApplicationContext(), " successfully saved your profile details!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_profile = (Button) findViewById(R.id.cancel_profile);
        cancel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profile_layout.setVisibility(View.VISIBLE);
                change_psw_layout.setVisibility(View.GONE);
                title.setText("MY PROFILE");


            }
        });

        confirm_psw_sub=(Button)findViewById(R.id.confirm_password_sub);
        confirm_psw_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetStatus.InternetStatus(getApplicationContext())) {
                    Validation2();
                    Toast.makeText(getApplicationContext(), " successfully saved your profile details!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return false;

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thumbnail = roundImage.getCircularBorder(thumbnail, 10);

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        bm = roundImage.getCircularBorder(bm, 10);


        ivImage.setImageBitmap(bm);
    }

    public void showDialog() {
        pDialog = new ProgressDialog(ProfileActivity.this);
        pDialog.setMessage("Please wait ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


    public void Validation() {
        boolean VCName = false, VEmail = false, VHPhone = false, VCPhone = false, VCity = false, VStreet = false, VZip = false, VState = false,Vphoto=false;
        Email = email.getText().toString();
        Campany_Name = company_name.getText().toString();
        HomePhone = homephone.getText().toString();
        CellNo = cellphone.getText().toString();
        Street = street.getText().toString();
        City = city.getText().toString();
//        State=state.getSelectedItem().toString();
        Zip = zip.getText().toString();


        if (Validation.isEmpty(Campany_Name)) {
            VCName = false;
            company_name.setError("Please enter valid name!!!");
        } else {

            VCName = true;

        }

        if (Validation.isValidPhone(HomePhone)) {
            VHPhone = true;
        } else {
            VHPhone = true;
            homephone.setError("Invalid phone number");
        }

        if (Validation.isValidPhone(CellNo)) {
            VCPhone = true;
        } else {
            VCPhone = true;
            cellphone.setError("Invalid phone number");
        }

        if (Validation.isZip(Zip)) {
            VZip = true;
        } else {
            VZip = false;
            zip.setError("Please enter valid zip code!!!");
        }
        if (Validation.isEmpty(Street)) {
            VStreet = false;
            street.setError("Please enter valid street address!!!");
        } else {
            VStreet = true;
        }
        if (Validation.isEmpty(City)) {
            VCity = false;
            city.setError("Please enter valid city!!!");
        } else {
            VCity = true;
        }

//
//        if (state.getSelectedItemPosition() > 0) {
//                VState = true;
//        }
//        else {
//                VState = false;
//                Toast.makeText(getApplicationContext(), "Please choose a valid state!!!", Toast.LENGTH_SHORT).show();
//        }


        if (VCName && VHPhone && VCPhone && VStreet && VCity && VState && VZip) {
            if (internetStatus.InternetStatus(getApplicationContext())) {
                showDialog();

            } else {
                Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
            }


        }


    }

    public void Validation2() {
        boolean  VcuPass = false, VnPass = false, VcnPass = false;
        Current_password=current_password.getText().toString();
        New_password=new_password.getText().toString();
        Confirm_edit_password=conf_edit_password.getText().toString();
        //current password
        if(Validation.isValidPassword(Current_password))
        {
            VcuPass=true;
        }
        else {
            VcuPass=false;
            current_password.setError("Password should be more then 5 digits!!!");
        }
        //new password
        if(Validation.isValidPassword(New_password))
        {
            VnPass=true;
        }
        else {
            VnPass=false;
            new_password.setError("Password should be more then 5 digits!!!");
        }
        //confirm password
        if(Validation.isValidConfirmPassword(New_password, Confirm_edit_password ))
        {
            VcnPass=true;
        }
        else {
            VcnPass=false;
            conf_edit_password.setError("Password & Confirm password doesn't match. Please check!!!");
        }
        if (VcuPass && VnPass && VcnPass ) {
            if (internetStatus.InternetStatus(getApplicationContext())) {
                showDialog();

            } else
            {
                Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
            }


        }
    }

    }
