package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.ResultDate;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyBusinessCard extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;

    ImageView bcimg1, bcimg2, bcimg3, bcimg4,userimg;
    Spinner category;
    EditText brief;
    Bitmap bitmap;
    Map<String, String> parameters = new HashMap<String, String>();
    String categoryid;
    Button preview, sixmonths, oneyear;
    LinearLayout previewlay;
    TextView home, cell, compnyname, name, mail, address;

    private WebServiceRequest.HttpURLCONNECTION postCard;
    private WebServiceRequest.HttpURLCONNECTION sendPaymentstatus;
    String Category, Brief;
    Bitmap thumbnail=null;
    private static final int PICK_FROM_GALLERY = 4;
    String enddate="";
    ProgressDialog pDialog;
    public final int PAYPAL_RESPONSE = 100;
    boolean paymentStatus;
    int addid=0;
    double price=0.01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titletoolbar);
        title.setText("My Business Card");
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_my_business_card, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_my_business_card_mob, container);
        }
        bcimg1 = (ImageView) findViewById(R.id.bcimg1);
        bcimg2 = (ImageView) findViewById(R.id.bcimg2);
        bcimg3 = (ImageView) findViewById(R.id.bcimg3);
        previewlay = (LinearLayout) findViewById(R.id.previewlayout);
        bcimg4 = (ImageView) findViewById(R.id.bcimg4);
        userimg = (ImageView) findViewById(R.id.userimg);
        category = (Spinner) findViewById(R.id.bccategory);
        brief = (EditText) findViewById(R.id.bcard_brief);
        home = (TextView) findViewById(R.id.home);
        cell = (TextView) findViewById(R.id.cell);
        compnyname = (TextView) findViewById(R.id.cmpnyname);
        name = (TextView) findViewById(R.id.name);
        preview = (Button) findViewById(R.id.preview);
        sixmonths = (Button) findViewById(R.id.btn6months);
        oneyear = (Button) findViewById(R.id.btn12months);
        mail = (TextView) findViewById(R.id.mail);
        address = (TextView) findViewById(R.id.addressbcard);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
                userimg.setImageBitmap(Utils.StringToBitMap( Consts.image1));
                mail.setText(Consts.UserName);
                home.setText(Consts.Phone);
                cell.setText(Consts.Cellphone);
                compnyname.setText(Consts.Company_Name);
                name.setText(Consts.FirstName);
                address.setText(Consts.Street+","+Consts.City+","+Consts.State+","+Consts.Zip);



            }
        });
        sixmonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enddate=ResultDate.SixMonth();
                price=0.01;
                new PostParameters().execute();
            }
        });
        oneyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enddate=ResultDate.OneYear();
                price=0.02;
                new PostParameters().execute();
            }
        });
        bcimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consts.imagecode=1;
                selectImage();
            }
        });

        bcimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consts.imagecode=2;
                selectImage();
            }
        });
        bcimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consts.imagecode=3;
                selectImage();
            }
        });
        bcimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consts.imagecode=4;
                selectImage();
            }
        });

setData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return false;

    }
public void setData()
{
    if(!Consts.image1.trim().isEmpty())
        bcimg1.setImageBitmap(Utils.StringToBitMap(Consts.image1));
    if(!Consts.image2.trim().isEmpty())
        bcimg2.setImageBitmap(Utils.StringToBitMap(Consts.image2));
    if(!Consts.image3.trim().isEmpty())
        bcimg3.setImageBitmap(Utils.StringToBitMap(Consts.image3));
    if(!Consts.image4.trim().isEmpty())
        bcimg4.setImageBitmap(Utils.StringToBitMap(Consts.image4));
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
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory()+File.separator + "TrendyImage.jpg");
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
            bcimg1.setImageBitmap(thumbnail);
        }else if(Consts.imagecode==2)
        {
            BitmapToString(thumbnail,2);
            bcimg2.setImageBitmap(thumbnail);
        }else if(Consts.imagecode==3)
        {
            BitmapToString(thumbnail,3);
            bcimg3.setImageBitmap(thumbnail);
        }else {
            BitmapToString(thumbnail,4);
            bcimg4.setImageBitmap(thumbnail);
        }

        if (destination.exists()) {
            if (destination.delete()) {
                System.out.println("file Deleted :" + destination.getPath());
            } else {
                System.out.println("file not Deleted :" + destination.getPath());
            }
        }


    }
    private void selectImage() {
            Log.d("ImageCode",""+Consts.imagecode);
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyBusinessCard.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Crop.pickImage(MyBusinessCard.this);
                } else if (items[item].equals("Choose from Library")) {
                    Crop.pickGallery(MyBusinessCard.this);
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
            bcimg1.setImageBitmap(Utils.StringToBitMap(Consts.image1));
        if(!Consts.image2.trim().isEmpty())
            bcimg2.setImageBitmap(Utils.StringToBitMap(Consts.image2));
        if(!Consts.image3.trim().isEmpty())
            bcimg3.setImageBitmap(Utils.StringToBitMap(Consts.image3));
        if(!Consts.image4.trim().isEmpty())
            bcimg4.setImageBitmap(Utils.StringToBitMap(Consts.image4));

    }


    public void sendPaymentStatus(String paymentid)
    {

        String params = "directoryid=" + addid + "&paymentstatus="+paymentStatus+"&paymentid="+paymentid;
        Log.d("parameters", params);
        params=params.replaceAll(" ","%20");
        sendPaymentstatus = api.SEND_CARD_PAYMENT(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {

                if (responseCode == 200) {
                    try {
                        JSONObject obj = new JSONObject(responseMessage);
                        Log.d("response", responseMessage.toString());
                        String status = obj.getString("msg");

                        if (status.equalsIgnoreCase("success")) {

                            Consts.image1 = "";
                            Consts.image2 = "";
                            Consts.image3 = "";
                            Consts.image4 = "";
                            Toast.makeText(getApplicationContext(), "Add posted successfully!!!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            finish();
                            startActivity(i);


                        } else {

                        }


                    } catch (JSONException e) {

                        Log.d("Login Error", e.toString());
                    }
                }
            }
        });
        sendPaymentstatus.execute();
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

    public void Validation() {
        boolean VCategory = false, VAddtitle = false,Vimg=false,Vphoto=false;
        Category = category.getSelectedItem().toString();
        Brief = brief.getText().toString();



        // category validation
        if (category.getSelectedItemPosition() > 0) {
            VCategory = true;
        } else {
            VCategory = false;
            Toast.makeText(getApplicationContext(), "Please choose category!!!", Toast.LENGTH_SHORT).show();
        }

        // Add title validation

        if (Validation.isEmpty(Brief)) {
            VAddtitle = false;
            brief.setError("Please enter description about your business!!!");
        } else {
            VAddtitle = true;
        }
        if( Consts.image1.trim().isEmpty()&& Consts.image2.trim().isEmpty()&& Consts.image3.trim().isEmpty()&& Consts.image4.trim().isEmpty())
        {
            Vphoto=false;
            Toast.makeText(getApplicationContext(), "Please upload photo!!!", Toast.LENGTH_SHORT).show();
        }else{
            if(Consts.image1.trim().isEmpty())
            {
                Vphoto=false;
                Toast.makeText(getApplicationContext(), "Please upload photo!!!", Toast.LENGTH_SHORT).show();
            }else{
                Vphoto=true;
            }


        }

        if (VCategory && VAddtitle && Vphoto) {
            {
                if (internetStatus.InternetStatus(getApplicationContext())) {

                    Getcategory();
                    previewlay.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }

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


    public  int Getcategory() {
        int id = category.getSelectedItemPosition();
        int catid=0;
        switch (id) {

            case 1:
                catid=5;
                break;

            case 2:
                catid=6;
                break;
            case 3:
                catid=7;
                break;
            case 4:
                catid=8;
                break;


        }return catid;
    }

public void PostAdd()
{
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
    postCard = api.POST_BUSSINESSCARD(requestParams.toString(), new WebServiceRequest.Callback() {
        @Override
        public void onResult(int responseCode, String responseMessage, Exception exception) {
            if (responseCode == 200) {
                try {
                    Log.d("response", responseMessage);
                    JSONObject obj = new JSONObject(responseMessage);
                    String status = obj.getString("msg");
                    if (status.equals("success")) {
                    PayPalButtonClick(Consts.AdminPaypalID,String.valueOf(price));
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Wrong! please try after sometime!!!", Toast.LENGTH_SHORT).show();
                        consts.hideDialog();
                    }


                } catch (JSONException e) {

                }
            } else {

            }
        }
    });
    postCard.execute();
}


    private Map<String, String> PostAddParams() {

        Map<String, String> params = new HashMap<String, String>();

        params.put("userid", Consts.UserId);
        params.put("catid", String.valueOf(Getcategory()));
        params.put("description", Brief);
        params.put("photo1",  Consts.image1);
        params.put("photo2", Consts.image2);
        params.put("photo3", Consts.image3);
        params.put("photo4", Consts.image4);
        params.put("startdate", ResultDate.CurrentDate());
        params.put("enddate", enddate);

        return params;
    }


    class PostData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyBusinessCard.this);
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
                        postCard = api.POST_BUSSINESSCARD(requestParams.toString(), new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                if (responseCode == 200) {
                                    try {
                                        Log.d("response", responseMessage);
                                        JSONObject obj = new JSONObject(responseMessage);
                                        String status = obj.getString("msg");
                                        addid=Integer.parseInt(obj.getString("directoryId"));
                                        if (status.equals("success")) {

                                            PayPalButtonClick(Consts.AdminPaypalID,String.valueOf(price));

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Something Wrong! please try after sometime!!!", Toast.LENGTH_SHORT).show();
                                            consts.hideDialog();
                                        }


                                    } catch (JSONException e) {

                                    }
                                } else {

                                }
                            }
                        });
                        postCard.execute();



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
            pDialog = new ProgressDialog(MyBusinessCard.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            try{
                parameters.put("userid", Consts.UserId);
                parameters.put("catid", String.valueOf(Getcategory()));
                parameters.put("description", Brief);
                parameters.put("photo1", Consts.image1);
                parameters.put("photo2", Consts.image2);
                parameters.put("photo3", Consts.image3);
                parameters.put("photo4", Consts.image4);
                parameters.put("startdate", ResultDate.CurrentDate());
                parameters.put("enddate", enddate);



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
