package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

    EditText fname,email,phone,street,city,zip,cellphone;
    String Fname,Email,Phone,Street,City,Zip,State,CellNo;
    Spinner state;

    RoundImage roundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_profile, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_profile_mob, container);
        }

        fname=(EditText)findViewById(R.id.fname);
        email = (EditText) findViewById(R.id.email);
        cellphone=(EditText)findViewById(R.id.phoneno_cell);
        phone=(EditText)findViewById(R.id.phoneno);
        street=(EditText)findViewById(R.id.street_address);
        city=(EditText)findViewById(R.id.city);
        zip=(EditText)findViewById(R.id.zip);
        roundImage=new RoundImage();

        ivImage = (ImageView) findViewById(R.id.profile_image);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {

        }
        return false;

    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

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
                System.currentTimeMillis() + ".jpg");

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
        thumbnail=roundImage.getCircularBorder(thumbnail, 10);

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
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

       bm= roundImage.getCircularBorder(bm,10);


        ivImage.setImageBitmap(bm);
    }


    public void Validation()
    {
        boolean VName=false,VEmail=false,VPass=false,VcPass=false,VPhone=false,VCity=false,VStreet=false,VZip=false,VState=false;
        Fname = fname.getText().toString();
        Email=email.getText().toString();

        Phone=phone.getText().toString();
        Street=street.getText().toString();
        City=city.getText().toString();
        State=state.getSelectedItem().toString();
        Zip=zip.getText().toString();
        CellNo=cellphone.getText().toString();
        if(Validation.isEmpty(Fname))
        {
            VName=false;
            fname.setError("Please enter valid name!!!");
        }
        else {

            VName=true;

        }
        if(Validation.isValidEmail(Email))
        {
            VEmail=true;
        }
        else {
            VEmail=false;
            email.setError("Please enter valid email-address!!!");
        }


        if(Validation.isValidPhone(Phone))
        {
            VPhone=true;
        }
        else {
            VPhone=true;
            //zip.setError("Invalid phone number");
        }
        if(Validation.isZip(Zip))
        {
            VZip=true;
        }
        else {
            VZip=false;
            zip.setError("Please enter valid zip code!!!");
        }
        if(Validation.isEmpty(Street))
        {
            VStreet=false;
            street.setError("Please enter valid street address!!!");
        }
        else {
            VStreet=true;
        }
        if(Validation.isEmpty(City))
        {
            VCity=false;
            city.setError("Please enter valid city!!!");
        }
        else {
            VCity=true;
        }
        if(state.getSelectedItemPosition()>0)
        {
            VState=true;
        }
        else
        {
            VState=false;
            Toast.makeText(getApplicationContext(), "Please choose a valid state!!!", Toast.LENGTH_SHORT).show();
        }



        Log.d("Status", "" + VName + VEmail + VPass + VcPass + VPhone + VZip + VStreet + VCity);

        if (VName && VEmail  && VPhone && VZip && VStreet && VCity &&VState)
        {



        }
    }
}
