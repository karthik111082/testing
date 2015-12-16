package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;

public class PostAdvertisment extends NavigationDrawer {

    ImageView aimg1,aimg2,aimg3,aimg4;
    int REQUEST_CAMERA1 = 11,REQUEST_CAMERA2 = 22,REQUEST_CAMERA3 = 33,REQUEST_CAMERA4 = 44, SELECT_FILE = 1, SELECT_FILE2 = 2;
    IsTablet tablet;
    boolean isTablet;
    RoundImage roundImage;
    Bitmap bitmap;
    byte[] image1,image2,image3,image4;
    TextView et1,et2;
    Calendar calendar;
    int year,day,month;

    Spinner category;
    EditText addtitle,street,city,state,zip,brief;
    String  Category,Addtitle,Street,City,State,Zip,Brief;
    Button cancel_advertisement,submit_advertisement;

    InternetStatus internetStatus;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("POST ADVERTISMENT");
        isTablet=tablet.isTablet(getApplicationContext());
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_post_advertisment, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_post_advertisment_mob, container);
        }

        //setContentView(R.layout.activity_post_advertisment_trendymarket);

        aimg1=(ImageView)findViewById(R.id.aimg1);
        aimg2=(ImageView)findViewById(R.id.aimg2);
        aimg3=(ImageView)findViewById(R.id.aimg3);
        aimg4=(ImageView)findViewById(R.id.aimg4);
        et1=(TextView)findViewById(R.id.pd1);
        et2=(TextView)findViewById(R.id.pd2);

        category=(Spinner)findViewById(R.id.category);
        addtitle=(EditText)findViewById(R.id.addtitle);
        street=(EditText)findViewById(R.id.street_address);
        city=(EditText)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);
        zip=(EditText)findViewById(R.id.zip);
        brief=(EditText)findViewById(R.id.brief_edit);


        cancel_advertisement=(Button)findViewById(R.id.cancel_advertisement);
        cancel_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


            }
        });

        submit_advertisement=(Button)findViewById(R.id.submit_advertisement);
        submit_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            Validation();
            }
        });



        aimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                selectImage(1);
            }
        });

        aimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectImage(2);
            }
        });
        aimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                selectImage(3);
            }
        });
        aimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                selectImage(4);
            }
        });
        et1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                showDialog(999);
                return false;
            }
        });
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }











    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            //return new DatePickerDialog(this, myDateListener, year, month, day);

            DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        et1.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }

    private void selectImage(final int requestcode) {

        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PostAdvertisment.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (requestcode == 1) {

                        startActivityForResult(intent, REQUEST_CAMERA1);
                    }

                    if (requestcode == 2) {

                        startActivityForResult(intent, REQUEST_CAMERA2);
                    }

                    if (requestcode == 3) {

                        startActivityForResult(intent, REQUEST_CAMERA3);
                    }

                    if (requestcode == 4) {

                        startActivityForResult(intent, REQUEST_CAMERA4);
                    }

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            requestcode);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }
    public void BitmapToByte(Bitmap bitmap,int image)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        switch (image)
        {
            case 1:
                image1= stream.toByteArray();
                break;
            case 2:
                image2= stream.toByteArray();
                break;
            case 3:
                image3= stream.toByteArray();
                break;
            case 4:
                image4= stream.toByteArray();
                break;

        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1)
                onSelectFromGalleryResult(data,1);
            else if (requestCode == REQUEST_CAMERA1)
                onCaptureImageResult(data,1);
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2)
                onSelectFromGalleryResult(data,2);
            else if (requestCode == REQUEST_CAMERA2)
                onCaptureImageResult(data,2);
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 3)
                onSelectFromGalleryResult(data,3);
            else if (requestCode == REQUEST_CAMERA3)
                onCaptureImageResult(data,3);
        }
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 4)
                onSelectFromGalleryResult(data,4);
            else if (requestCode == REQUEST_CAMERA4)
                onCaptureImageResult(data,4);
        }
    }
    private void onCaptureImageResult(Intent data,int image) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() +image+ ".png");

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


            switch (image) {
                case 1:
                    aimg1.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail,1);
                    break;
                case 2:
                    aimg2.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail, 2);
                    break;
                case 3:
                    aimg3.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail, 3);
                    break;
                case 4:
                    aimg4.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail,4);


            }
        }



    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data,int image) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        bitmap = BitmapFactory.decodeFile(selectedImagePath);
        switch (image) {
            case 1:
                aimg1.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 1);
                break;
            case 2:
                aimg2.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 2);
                break;
            case 3:
                aimg3.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 3);
                break;
            case 4:
                aimg4.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 4);


        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {
         Intent i=new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(i);
        }
        return false;

    }

// ShowDialog
    public void showDialog()
    {
        pDialog = new ProgressDialog(PostAdvertisment.this);
        pDialog.setMessage("Please wait ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    //HideDialog
    public void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    // Set Validation

    public void Validation() {
        boolean VCategory = false,VAddtitle = false,VStreet = false,VState = false,  VCity = false,  VZip = false, VBrief=false;
        Category = category.getSelectedItem().toString();
        Addtitle = addtitle.getText().toString();
        Street = street.getText().toString();
        City = city.getText().toString();
        // State=state.getSelectedItem().toString();
        State = state.getText().toString();
        Zip = zip.getText().toString();
        Brief = brief.getText().toString();

        // category validation
        if (category.getSelectedItemPosition() > 0) {
                VCategory = true;
        }
        else
        {
            VCategory = false;
            Toast.makeText(getApplicationContext(), "Please choose a valid category!!!", Toast.LENGTH_SHORT).show();
        }

        // Add title validation

        if (Validation.isEmpty(Addtitle)) {
            VAddtitle = false;
            addtitle.setError("Please enter valid street address!!!");
        } else {
            VAddtitle = true;
        }


        // Street address validation
        if (Validation.isEmpty(Street)) {
            VStreet = false;
            street.setError("Please enter valid street address!!!");
        } else {
            VStreet = true;
        }


        // City validation
        if (Validation.isEmpty(City)) {
            VCity = false;
            city.setError("Please enter valid city!!!");
        } else {
            VCity = true;
        }

//        // State validation
        if (Validation.isEmpty(State)) {
            VState = false;
            state.setError("Please enter valid city!!!");
        } else {
            VState = true;
        }

        // State validation

//        if (state.getSelectedItemPosition() > 0) {
//                VState = true;
//        }
//        else {
//                VState = false;
//                Toast.makeText(getApplicationContext(), "Please choose a valid state!!!", Toast.LENGTH_SHORT).show();
//        }


        // Zip validation

        if (Validation.isZip(Zip)) {
            VZip = true;
        } else {
            VZip = false;
            zip.setError("Please enter valid zip code!!!");
        }




        if (VCategory && VAddtitle && VStreet && VCity && VState && VZip)
        {
            {  if(internetStatus.InternetStatus(getApplicationContext())) {

                Toast.makeText(getApplicationContext(),"Success!!!",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
            }

            }



        }


    }
}
