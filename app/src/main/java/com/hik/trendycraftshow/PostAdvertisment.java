package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.GPSTracker;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;
import com.hik.trendycraftshow.Utils.Utils;
import com.hik.trendycraftshow.Utils.Validation;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PostAdvertisment extends NavigationDrawer {

    ImageView aimg1,aimg2,aimg3,aimg4;
    int REQUEST_CAMERA1 = 11,REQUEST_CAMERA2 = 22,REQUEST_CAMERA3 = 33,REQUEST_CAMERA4 = 44, SELECT_FILE = 1, SELECT_FILE2 = 2;
    IsTablet tablet;
    boolean isTablet;
    boolean Gps=false;
    RoundImage roundImage;
    Bitmap bitmap;

    TextView et1,et2;
    Calendar calendar;
    int year,day,month;
    Consts consts;
    Map<String, String> parameters = new HashMap<String, String>();
    String categoryid;

    Bitmap thumbnail=null;
    private static final int PICK_FROM_GALLERY = 4;
    Spinner category;
    EditText addtitle,street,city,zip,brief;
    Spinner state;
    String  Category,Addtitle,Street,City,State,Zip,Brief,Startdate="",Enddate="";
    int addid;
    boolean paymentStatus;
    Button cancel_advertisement,submit_advertisement;
    boolean date1,date2;
    double latitude;
    double longitude;
    GPSTracker gps;
    InternetStatus internetStatus;
    ProgressDialog pDialog;
    public final int PAYPAL_RESPONSE = 100;
    private WebServiceRequest.HttpURLCONNECTION postAdd;
    private WebServiceRequest.HttpURLCONNECTION sendPaymentstatus;
    Api api;

    int imagecode=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_post_advertisment, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_post_advertisment_mob, container);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title=(TextView)findViewById(R.id.titletoolbar);
        title.setText("Post Advertisement");
        int categoryid=getIntent().getExtras().getInt("category");



        consts=new Consts(getApplicationContext());
        aimg1=(ImageView)findViewById(R.id.aimg1);
        aimg2=(ImageView)findViewById(R.id.aimg2);
        aimg3=(ImageView)findViewById(R.id.aimg3);
        aimg4=(ImageView)findViewById(R.id.aimg4);
        et1=(TextView)findViewById(R.id.pd1);
        et2=(TextView)findViewById(R.id.pd2);
        category=(Spinner)findViewById(R.id.category);
        state=(Spinner)findViewById(R.id.state);
        addtitle=(EditText)findViewById(R.id.addtitle);
        street=(EditText)findViewById(R.id.street_address);
        city=(EditText)findViewById(R.id.city);
        zip=(EditText)findViewById(R.id.zip);
        brief=(EditText)findViewById(R.id.brief_edit);
        submit_advertisement=(Button)findViewById(R.id.submit_advertisement);
        cancel_advertisement=(Button)findViewById(R.id.cancel_advertisement);
        category.setSelection(categoryid);
        setData();

        GpsLocation();


        cancel_advertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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



        aimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Consts.imagecode=1;
                selectImage();
            }
        });

        aimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Consts.imagecode=2;
                selectImage();
            }
        });
        aimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Consts.imagecode=3;
                selectImage();
            }
        });
        aimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Consts.imagecode=4;
                selectImage();
            }
        });
        et1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                date1=true;
                date2=false;
                showDialog(999);
                return false;
            }
        });
        et2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(Startdate.equals(null)||Startdate==null||Startdate.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please select start date first!!!",Toast.LENGTH_SHORT).show();
                }else{
                    date1=false;
                    date2=true;
                    showDialog(999);
                }

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
            if (date2) {
                //return new DatePickerDialog(this, myDateListener, year, month, day);
                String expectedPattern = "MM-dd-yyyy";
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


            et1.setText(new StringBuilder().append(String.format("%02d", month)).append("-").append(String.format("%02d", day)).append("-").append(year));
            Startdate=et1.getText().toString();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
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

            et2.setText(new StringBuilder().append(String.format("%02d", month)).append("-").append(String.format("%02d",day)).append("-").append(year));
            Enddate=et2.getText().toString();
        }
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PostAdvertisment.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Crop.pickImage(PostAdvertisment.this);
                } else if (items[item].equals("Choose from Library")) {
                    Crop.pickGallery(PostAdvertisment.this);
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
                Consts.image1= Utils.BitMapToString(bitmap);
                break;
            case 2:
                Consts.image2= Utils.BitMapToString(bitmap);
                break;
            case 3:
                Consts.image3= Utils.BitMapToString(bitmap);
                break;
            case 4:
                Consts.image4= Utils.BitMapToString(bitmap);
                break;


        }
        if(!Consts.image1.trim().isEmpty())
            aimg1.setImageBitmap(Utils.StringToBitMap(Consts.image1));
        if(!Consts.image2.trim().isEmpty())
            aimg2.setImageBitmap(Utils.StringToBitMap(Consts.image2));
        if(!Consts.image3.trim().isEmpty())
            aimg3.setImageBitmap(Utils.StringToBitMap(Consts.image3));
        if(!Consts.image4.trim().isEmpty())
            aimg4.setImageBitmap(Utils.StringToBitMap(Consts.image4));

    }
    private void cropCapturedImage(Uri picUri) {

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1000);
        cropIntent.putExtra("aspectY", 600);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 1000);
        cropIntent.putExtra("outputY", 600);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_RESPONSE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    //The payment succeeded
                    String email = PayPal.getInstance().getAccountEmail();
                    Log.d("Email", email);
                    String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                    Log.d("pavan", "success " + payKey);

                    paymentStatus=true;
                    sendPaymentStatus(payKey);

                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getApplicationContext(), "Payment Canceled , Try again ", Toast.LENGTH_LONG).show();


                    break;
                case PayPalActivity.RESULT_FAILURE:
                    Toast.makeText(getApplicationContext(), "Payment failed , Try again ", Toast.LENGTH_LONG).show();


                    break;
            }
        }
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "TrendyUser.jpg");
            Uri u=Uri.fromFile(file);
            //beginCrop(result.getData());
            beginCrop(u);
        }
        else if(requestCode == Crop.REQUEST_GALLERY && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        }
        else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }

    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).withAspect(5, 3).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            try {
                onCaptureImageResult(result);
            }catch (Exception e)
            {
                Log.d("FileError",e.toString());
            }
           // aimg1.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void onCaptureImageResult(Intent data)throws FileNotFoundException{
        thumbnail = BitmapFactory.decodeStream(getContentResolver().openInputStream(Crop.getOutput(data)));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory()+File.separator + "TrendyUser.jpg");
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
        if(Consts.imagecode==1)
        {
            BitmapToString(thumbnail,1);
            aimg1.setImageBitmap(Utils.StringToBitMap(Consts.image1));
        }else if(Consts.imagecode==2)
        {
            BitmapToString(thumbnail,2);
            aimg2.setImageBitmap(Utils.StringToBitMap(Consts.image2));
        }else if(Consts.imagecode==3)
        {
            BitmapToString(thumbnail,3);
            aimg3.setImageBitmap(Utils.StringToBitMap(Consts.image3));

        }else {
            BitmapToString(thumbnail,4);
            aimg4.setImageBitmap(Utils.StringToBitMap(Consts.image4));
        }

        if (destination.exists()) {
            if (destination.delete()) {
                System.out.println("file Deleted :" + destination.getPath());
            } else {
                System.out.println("file not Deleted :" + destination.getPath());
            }
        }


    }



    public void PayPalButtonClick(String primary_id, String primary_amount) {
        // Create a basic PayPal payment



        Log.d("pavan", "primary " + primary_id);
        Log.d("pavan", "primary_amount " + primary_amount);



        // config reciever1
        PayPalReceiverDetails receiver;
        receiver = new PayPalReceiverDetails();
        receiver.setRecipient(primary_id);
        receiver.setSubtotal(new BigDecimal(primary_amount));
        receiver.setMerchantName("Trendy Craft Show");


        // adding payment type
        PayPalAdvancedPayment advPayment = new PayPalAdvancedPayment();
        advPayment.setCurrencyType("USD");



        advPayment.getReceivers().add(receiver);

        Intent paypalIntent = PayPal.getInstance().checkout(advPayment, this);
        this.startActivityForResult(paypalIntent, PAYPAL_RESPONSE);

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

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
        boolean VCategory = false,VDate=false,Vimage=false,VAddtitle = false,VStreet = false,VState = false,  VCity = false,  VZip = false,Vdate=false, VBrief=false;
        Category = category.getSelectedItem().toString();
        Addtitle = addtitle.getText().toString();
        Street = street.getText().toString();
        City = city.getText().toString();
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
            Toast.makeText(getApplicationContext(), "Please choose category!!!", Toast.LENGTH_SHORT).show();
        }

        // Add title validation

        if (Validation.isEmpty(Addtitle)) {
            VAddtitle = false;
            addtitle.setError("Please enter advertisement title!!!");
        } else {
            VAddtitle = true;
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
            city.setError("Please enter city!!!");
        } else {
            VCity = true;
        }

//        // State validation
        if (state.getSelectedItemPosition()==0) {
            VState = false;
            Toast.makeText(getApplicationContext(),"Please select state!!!",Toast.LENGTH_SHORT).show();
        } else {
            VState = true;
        }

        if (Validation.isZip(Zip)) {
            VZip = true;
        } else {
            VZip = false;
            zip.setError("Please enter valid zip code!!!");
        }
        if(Consts.image1.trim().isEmpty()&&Consts.image2.trim().isEmpty()&&Consts.image3.trim().isEmpty()&&Consts.image4.trim().isEmpty())
        {
            Vimage=false;
            Toast.makeText(getApplicationContext(),"Please upload at least one photo!!!",Toast.LENGTH_SHORT).show();
        }else{
            if(Consts.image1.trim().isEmpty())
            {
                Vimage=false;
                Toast.makeText(getApplicationContext(), "Please upload photo!!!", Toast.LENGTH_SHORT).show();
            }else{
                Vimage=true;
            }
        }
        if(Startdate.trim().isEmpty()||Enddate.trim().isEmpty())
        {
            VDate=false;
            Toast.makeText(getApplicationContext(),"Please select event date!!!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(Consts.DateValidation(Startdate, Enddate))
            {
                Vdate=true;
            }else{
                Vdate=false;
                Toast.makeText(getApplicationContext(),"The ad will be available for maximum period of 30 days from the start date!!!",Toast.LENGTH_SHORT).show();
            }
            VDate=true;

        }
        if(Brief.trim().isEmpty())
        {
            VBrief=false;
            Toast.makeText(getApplicationContext(), "Please enter description!!!", Toast.LENGTH_SHORT).show();
        }else
        {
            VBrief=true;
        }




        if (VCategory && VAddtitle && VStreet && VCity && VState && VZip&&Vdate&&VDate&&Vimage&&VBrief)
        {
            {  if(internetStatus.InternetStatus(getApplicationContext()))
            {
                categoryid=String.valueOf(category.getSelectedItemPosition());
               new PostParameters().execute();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Trendy Craft Show requires internet. Please check!!!",Toast.LENGTH_SHORT).show();
            }

            }    }
    }

    private Map<String, String> PostAddParams() {

        Map<String, String> params = new HashMap<String, String>();

        params.put("userid", Consts.UserId);
        params.put("catid",String.valueOf(category.getSelectedItemPosition()));
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
        params.put("photo1", Consts.image1);
        params.put("photo2",Consts.image2);
        params.put("photo3",Consts.image3);
        params.put("photo4",Consts.image4);
        return params;
    }

    public void sendPaymentStatus(String paymentid)
    {
        showDialog();
        String params = "addid=" + addid + "&paymentstatus="+paymentStatus+"&paymentid="+paymentid;
        Log.d("parameters", params);
        params=params.replaceAll(" ","%20");
        sendPaymentstatus = api.SEND_PAYMENT_STATUS(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(responseMessage);
                        Log.d("response", responseMessage.toString());
                        String status = obj.getString("msg");

                        if (status.equalsIgnoreCase("success")) {
                            hideDialog();
                            Consts.image1="";
                            Consts.image2="";
                            Consts.image3="";
                            Consts.image4="";
                            Toast.makeText(getApplicationContext(), "Add posted successfully!!!", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                            finish();
                            startActivity(i);


                        } else {
                            hideDialog();
                        }


                    } catch (JSONException e) {
                        hideDialog();
                        Log.d("Login Error", e.toString());
                    }
                }
            }
        });
        sendPaymentstatus.execute();
    }
    public void setData()
    {

        street.setText(Consts.Street);
        state.setSelection(Consts.SpinnerItem);
        city.setText(Consts.City);
        zip.setText(Consts.Zip);
        if(!Consts.image1.trim().isEmpty())
            aimg1.setImageBitmap(Utils.StringToBitMap(Consts.image1));
        if(!Consts.image2.trim().isEmpty())
            aimg2.setImageBitmap(Utils.StringToBitMap(Consts.image2));
        if(!Consts.image3.trim().isEmpty())
            aimg3.setImageBitmap(Utils.StringToBitMap(Consts.image3));
        if(!Consts.image4.trim().isEmpty())
            aimg4.setImageBitmap(Utils.StringToBitMap(Consts.image4));

    }
    public void GpsLocation()
    {
        gps = new GPSTracker(PostAdvertisment.this);

        // check if GPS enabled
        if(gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Gps=true;

            // \n is for new line

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            Gps=false;
        }
    }

    class PostData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PostAdvertisment.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            try{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Map<String, String> params=parameters;
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
                                        addid=Integer.parseInt(obj.getString("addid"));

                                        if (status.equals("success")) {

                                            PayPalButtonClick(Consts.AdminPaypalID, "0.01");


                                        } else {
                                            if(pDialog.isShowing())
                                                pDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Something Wrong! please try after sometime!!!", Toast.LENGTH_SHORT).show();

                                        }


                                    } catch (JSONException e) {
                                        if(pDialog.isShowing())
                                            pDialog.dismiss();
                                    }
                                } else {

                                }
                            }
                        });
                        postAdd.execute();

                    }
                });







            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();




        }

    }

    class PostParameters extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PostAdvertisment.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            try{
                parameters.put("userid", Consts.UserId);
                parameters.put("catid", categoryid);
                parameters.put("addtitle", Addtitle);
                parameters.put("street", Street);
                parameters.put("city", City);
                parameters.put("state", State);
                parameters.put("zip", Zip);
                parameters.put("startdate", Startdate);
                parameters.put("enddate", Enddate);
                parameters.put("latitude", String.valueOf(latitude));
                parameters.put("langitude", String.valueOf(longitude));
                parameters.put("description", Brief);
                parameters.put("price", "0");
                parameters.put("photo1", Consts.image1);
                parameters.put("photo2", Consts.image2);
                parameters.put("photo3", Consts.image3);
                parameters.put("photo4", Consts.image4);





            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

new PostData().execute();


        }

    }

}
