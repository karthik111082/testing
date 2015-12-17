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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.GPSTracker;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;
import com.hik.trendycraftshow.Utils.Utils;
import com.hik.trendycraftshow.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PostAdvertismentTrendyMarket extends NavigationDrawer {
    ImageView img1,img2,img3,img4;
    int REQUEST_CAMERA1 = 11,REQUEST_CAMERA2 = 22,REQUEST_CAMERA3 = 33,REQUEST_CAMERA4 = 44, SELECT_FILE = 1, SELECT_FILE2 = 2;
    IsTablet tablet;
    boolean isTablet;
    RoundImage roundImage;
    Bitmap bitmap;
    String image1="",image2="",image3="",image4="";
    double latitude;
    double longitude;
    private WebServiceRequest.HttpURLCONNECTION postAdd;
    Api api;
    Calendar calendar;
    int year,day,month;
    Consts consts;
    GPSTracker gps;
    Spinner category,state;
    EditText addtitle,price,street,city,zip,brief;
    String  Category,Addtitle,State,Street,City,Zip,Brief,Startdate="",Enddate="";
    double Price;

    Button cancel_advertisement,submit_advertisement;
    boolean date1,date2;
    InternetStatus internetStatus;
    ProgressDialog pDialog;
    TextView et1,et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet=tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_post_advertisment_trendymarket, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_post_advertisment_trendymarket_mob, container);
        }
        consts=new Consts(getApplicationContext());

        category=(Spinner)findViewById(R.id.category);
        addtitle=(EditText)findViewById(R.id.addtitle);
        street=(EditText)findViewById(R.id.street_address);
        state=(Spinner)findViewById(R.id.state);
        price=(EditText)findViewById(R.id.trendy_post_price);
        city=(EditText)findViewById(R.id.city);
        et1=(TextView)findViewById(R.id.startdate);
        et2=(TextView)findViewById(R.id.enddate);
        zip=(EditText)findViewById(R.id.zip);
        brief=(EditText)findViewById(R.id.brief_edit);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        submit_advertisement=(Button)findViewById(R.id.submit_advertisement);
        cancel_advertisement=(Button)findViewById(R.id.cancel_advertisement);
        setData();
        cancel_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                finish();
                startActivity(i);

            }
        });


        submit_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Validation();
            }
        });




        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(1);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
         selectImage(2);
    }
});
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(3);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(4);
            }
        });
        et1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                date1 = true;
                date2 = false;
                showDialog(999);
                return false;
            }
        });
        et2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Startdate.equals(null) || Startdate == null || Startdate.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please select start date first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    date1 = false;
                    date2 = true;
                    showDialog(999);
                }

                return false;
            }
        });

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
            if (date2) {
                //return new DatePickerDialog(this, myDateListener, year, month, day);
                String expectedPattern = "MM/dd/yyyy";
                Date date=null;
                Calendar c=null;
                SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
                try
                {
                    date = formatter.parse(Startdate);
                    System.out.println(date);
                    c = Calendar.getInstance();
                    c.setTime(formatter.parse(Startdate));
                    c.add(Calendar.DATE, 30);
                }
                catch (ParseException e)
                {

                    e.printStackTrace();
                }
                DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
                dialog.getDatePicker().setMinDate(date.getTime());
                dialog.getDatePicker().setMaxDate(Long.parseLong(formatter.format(c.getTime())));

                Log.d("date","test:"+date.getTime());

                return dialog;
            }else {

                DatePickerDialog dialog = new DatePickerDialog(this, myDateListener, year, month, day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

                return dialog;
            }
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
        if(date1) {

            et1.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
            Startdate=et1.getText().toString();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(Startdate));
                c.add(Calendar.DATE, 30);
                // number of days to add
                et2.setText(sdf.format(c.getTime()));
                Enddate=et2.getText().toString();
            }catch (ParseException e){}
        }
        if(date2)
        {

            et2.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
            Enddate=et2.getText().toString();
        }
    }
    private void selectImage(final int requestcode) {

        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PostAdvertismentTrendyMarket.this);
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
    public void BitmapToString(Bitmap bitmap,int image)
    {

        switch (image)
        {
            case 1:
                image1= Utils.BitMapToString(bitmap);
                break;
            case 2:
                image2= Utils.BitMapToString(bitmap);
                break;
            case 3:
                image3= Utils.BitMapToString(bitmap);
                break;
            case 4:
                image4= Utils.BitMapToString(bitmap);
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
                    img1.setImageBitmap(thumbnail);
                    BitmapToString(thumbnail, 1);
                    break;
                case 2:
                    img2.setImageBitmap(thumbnail);
                    BitmapToString(thumbnail, 2);
                    break;
                case 3:
                    img3.setImageBitmap(thumbnail);
                    BitmapToString(thumbnail, 3);
                    break;
                case 4:
                    img4.setImageBitmap(thumbnail);
                    BitmapToString(thumbnail, 4);


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
                img1.setImageBitmap(bitmap);
                BitmapToString(bitmap, 1);
                break;
            case 2:
                img2.setImageBitmap(bitmap);
                BitmapToString(bitmap, 2);
                break;
            case 3:
                img3.setImageBitmap(bitmap);
                BitmapToString(bitmap, 3);
                break;
            case 4:
                img4.setImageBitmap(bitmap);
                BitmapToString(bitmap, 4);


        }
    }




    // ShowDialog
    public void showDialog()
    {
        pDialog = new ProgressDialog(PostAdvertismentTrendyMarket.this);
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
        boolean VCategory = false,VAddtitle = false,VPrice = false,VStreet = false,VState = false,  VCity = false,  VZip = false, VBrief=false;
        Category = category.getSelectedItem().toString();
        Addtitle = addtitle.getText().toString();
        Price=Double.parseDouble(price.getText().toString());
        Street = street.getText().toString();
        City = city.getText().toString();
        // State=state.getSelectedItem().toString();
        State = state.getSelectedItem().toString();
        Zip = zip.getText().toString();
        Brief = brief.getText().toString();

        // category validation
        if (category.getSelectedItemPosition() > 0) {
            VCategory = true;
        }
        else
        {
            VCategory = false;
            Toast.makeText(getApplicationContext(), "Please choose a category!!!", Toast.LENGTH_SHORT).show();
        }

        // Add title validation

        if (Validation.isEmpty(Addtitle)) {
            VAddtitle = false;
            addtitle.setError("Please enter add title!!!");
        } else {
            VAddtitle = true;
        }
        // Price validation

        if (String.valueOf(Price).length()==0 || Price<=0 ) {
             VPrice= false;
            price.setError("Please enter valid price!!!");
        } else {
            VPrice = true;
        }


        // Street address validation
        if (Validation.isEmpty(Street)) {
            VStreet = false;
            street.setError("Please enter street address!!!");
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

        // State validation
        if (state.getSelectedItemPosition()==0) {
            VState = false;
            Toast.makeText(getApplicationContext(),"Please select state!!!",Toast.LENGTH_SHORT).show();
        } else {
            VState = true;
        }
        // Zip validation

        if (Validation.isZip(Zip)) {
            VZip = true;
        } else {
            VZip = false;
            zip.setError("Please enter valid zip code!!!");
        }

        if (VCategory && VAddtitle && VPrice && VStreet && VCity && VState && VZip)
        {
            {  if(internetStatus.InternetStatus(getApplicationContext())) {
                consts.showDialog(PostAdvertismentTrendyMarket.this);
                PostAdd();

            }
            else{
                Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
            }

            }
        }    }

    public void PostAdd()
    {
        Map<String, String> params=PostAddParams();
        StringBuffer requestParams = new StringBuffer();
        try {
            if (params != null && params.size() > 0) {
                // creates the params string, encode them using URLEncoder
                Iterator<String> paramIterator = params.keySet().iterator();
                while (paramIterator.hasNext()) {
                    String key = paramIterator.next();
                    String value = params.get(key);
                    requestParams.append(URLEncoder.encode(key, "UTF-8"));
                    requestParams.append("=").append(
                            URLEncoder.encode(value, "UTF-8"));
                    requestParams.append("&");
                }


            }
        }catch (Exception e){consts.hideDialog();}
        postAdd = api.POST_ADD(requestParams.toString(), new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                if (responseCode == 200) {
                    try {
                        Log.d("response", responseMessage);
                        JSONObject obj = new JSONObject(responseMessage);
                        String status = obj.getString("msg");
                        if (status.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Add posted successfully!!!", Toast.LENGTH_SHORT).show();
                            consts.hideDialog();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something Wrong! please try after sometime!!!", Toast.LENGTH_SHORT).show();
                            consts.hideDialog();
                        }


                    } catch (JSONException e) {
                        consts.hideDialog();
                    }
                } else {
                    consts.hideDialog();
                }
            }
        });
        postAdd.execute();

    }
    private Map<String, String> PostAddParams() {
        GpsLocation();
        Map<String, String> params = new HashMap<String, String>();

        params.put("userid", Consts.UserId);
        params.put("catid",String.valueOf(category.getSelectedItemPosition()+4));
        params.put("addtitle",Addtitle);
        params.put("street", Street);
        params.put("city", City);
        params.put("state", State);
        params.put("zip", Zip);
        params.put("startdate", Startdate);
        params.put("enddate", Enddate);
        params.put("latitude", String.valueOf(latitude));
        params.put("langitude", String.valueOf(longitude));
        params.put("description", Brief);
        params.put("price", "0");
        params.put("photo1", image1);
        params.put("photo2",image2);
        params.put("photo3",image3);
        params.put("photo4",image4);
        return params;
    }
    public void setData()
    {
        street.setText(Consts.Street);
        state.setSelection(Consts.SpinnerItem);
        city.setText(Consts.City);
        zip.setText(Consts.Zip);

    }
    public void GpsLocation()
    {
        gps = new GPSTracker(PostAdvertismentTrendyMarket.this);
        // check if GPS enabled
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

        }else{

            gps.showSettingsAlert();
        }
    }
}
