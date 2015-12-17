package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.CustomImageView;
import com.hik.trendycraftshow.Utils.Utils;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;
import com.hik.trendycraftshow.Utils.Validation;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProfileActivity extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;
Consts consts;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    CircularImageView ivImage;
    RoundImage roundImage;
    private static final int PICK_FROM_GALLERY = 4;

    TextView email,name;
    EditText company_name, homephone, cellphone, street, city, zip,current_password,new_password,conf_edit_password;
    Spinner state;
    String Name,Email,Company_Name, HomePhone, CellNo, Street, City, State, Zip,Current_password,New_password,Confirm_password;
    Button change_psw_main,confirm_psw_sub, save_profile, cancel_profile;

    LinearLayout change_psw_layout, profile_layout;
    private WebServiceRequest.HttpURLCONNECTION sendprofile;
    private WebServiceRequest.HttpURLCONNECTION changepwd;
Api api;
    InternetStatus internetStatus;
    ProgressDialog pDialog;
    Bitmap thumbnail=null;



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
        state=(Spinner)findViewById(R.id.state);
        name=(TextView)findViewById(R.id.name);
        current_password = (EditText) findViewById(R.id.current_password);
        new_password = (EditText) findViewById(R.id.new_password);
        conf_edit_password =(EditText)findViewById(R.id.conf_password);
        consts=new Consts(ProfileActivity.this);
        roundImage = new RoundImage();
        ivImage = (CircularImageView) findViewById(R.id.profile_image);
        change_psw_layout = (LinearLayout) findViewById(R.id.change_psw_layout);
        profile_layout = (LinearLayout) findViewById(R.id.profile_layout);
        change_psw_main = (Button) findViewById(R.id.change_psw_main);
        save_profile = (Button) findViewById(R.id.save_profile);
        confirm_psw_sub=(Button)findViewById(R.id.confirm_password_sub);
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

        change_psw_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profile_layout.setVisibility(View.GONE);
                change_psw_layout.setVisibility(View.VISIBLE);
                title.setText("CHANGE PASSWORD");

            }
        });


        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internetStatus.InternetStatus(getApplicationContext())) {
                    Validation();

                } else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        confirm_psw_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetStatus.InternetStatus(getApplicationContext())) {
                    Validation2();

                } else {
                    Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
SetData();



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
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
    /*put uri as extra in intent object*/
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    /*start activity for result pass intent as argument and request code */
                    startActivityForResult(intent, 1);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
// call android default gallery
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
// ******** code for crop image
                    intent.putExtra("crop", "true");
                    intent.putExtra("aspectX", 100);
                    intent.putExtra("aspectY", 100);
                    intent.putExtra("outputX", 200);
                    intent.putExtra("outputY", 150);

                    try {

                        intent.putExtra("return-data", true);
                        startActivityForResult(Intent.createChooser(intent,
                                "Complete action using"), PICK_FROM_GALLERY);

                    } catch (ActivityNotFoundException e) {
// Do nothing for now
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cropCapturedImage(Uri picUri) {

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1){
            //create instance of File with same name we created before to get image from storage
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "img.jpg");
            //Crop the captured image using an other intent
            try {
    /*the user's device may not support cropping*/
                cropCapturedImage(Uri.fromFile(file));
            }
            catch(ActivityNotFoundException aNFE){
                //display an error message if user device doesn't support
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if(requestCode==2) {
            //Create an instance of bundle and get the returned data
            Bundle extras = data.getExtras();
            //get the cropped bitmap from extras
            Bitmap thePic = extras.getParcelable("data");
            //set image bitmap to image view
            onCaptureImageResult(data);
            //ivImage.setImageBitmap(thePic);
        }
        if (requestCode == PICK_FROM_GALLERY) {
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo = extras2.getParcelable("data");
                onCaptureImageResult(data);
                //ivImage.setImageBitmap(photo);

            }
        }
    }

    private void onCaptureImageResult(Intent data) {
         thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);

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
        thumbnail = BitmapFactory.decodeFile(selectedImagePath, options);
        ivImage.setImageBitmap(thumbnail);
    }




    public void Validation() {
        boolean VCName = false,  VHPhone = false, VCPhone = false, VCity = false, VStreet = false, VZip = false, VState = false,Vphoto=false;
        Email = email.getText().toString();
        Name=name.getText().toString();
        Company_Name = company_name.getText().toString();
        HomePhone = homephone.getText().toString();
        CellNo = cellphone.getText().toString();
        Street = street.getText().toString();
        City = city.getText().toString();
        State=state.getSelectedItem().toString();
        Zip = zip.getText().toString();


        if (Validation.isEmpty(Company_Name)) {
            VCName = false;
            company_name.setError("Please enter valid name!!!");
        } else {

            VCName = true;

        }

        if (Validation.isValidPhone(HomePhone)) {
            VHPhone = true;
        } else {
            VHPhone = false;
            homephone.setError("Invalid phone number");
        }

        if (Validation.isValidPhone(CellNo)) {
            VCPhone = true;
        } else {
            VCPhone = false;
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

       if (state.getSelectedItemPosition() > 0) {
               VState = true;
       }
       else {
               VState = false;
               Toast.makeText(getApplicationContext(), "Please choose a state!!!", Toast.LENGTH_SHORT).show();
       }
if(thumbnail==null||thumbnail.equals(null))
{
    Vphoto=false;
    Toast.makeText(getApplicationContext(), "Please upload photo!!!", Toast.LENGTH_SHORT).show();
}else{
    Vphoto=true;
}

        if (VCName && VHPhone && VCPhone && VStreet && VCity && VState && VZip&&VState&&Vphoto) {
            if (internetStatus.InternetStatus(getApplicationContext())) {
                consts.showDialog(ProfileActivity.this);
                SendProfile();

            } else {
                Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
            }


        }


    }

    public void SendProfile()
    {
        Map<String, String> params=SendProfileParams();
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
        sendprofile = api.SEND_PROFILE(requestParams.toString(), new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                if (responseCode == 200) {
                    try {
                        Log.d("response", responseMessage);
                        JSONObject obj = new JSONObject(responseMessage);
                        String status=obj.getString("msg");
                        if(status.equals("success"))
                        {
                            Toast.makeText(getApplicationContext(), "Profile successfully update!!!", Toast.LENGTH_SHORT).show();
                            Consts.FirstName=Name;
                            Consts.UserName=Email;
                            Consts.Phone=HomePhone;
                            Consts.Cellphone=CellNo;
                            Consts.City=City;
                            Consts.Street=Street;
                            Consts.Company_Name=Company_Name;
                            Consts.Zip=Zip;
                            Consts.State=State;
                            MainActivity.consts.setPhoto(thumbnail);
                            consts.hideDialog();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();
                            consts.hideDialog();
                        }


                    }
                    catch (JSONException e){consts.hideDialog();}
                }else
                {
                    consts.hideDialog();
                }
            }
        });
        sendprofile.execute();
    }
    public void SetData() {
        name.setText(Consts.FirstName);
        email.setText(Consts.UserName);
        homephone.setText(Consts.Phone);
        cellphone.setText(Consts.Cellphone);
        street.setText(Consts.Street);
        city.setText(Consts.City);
        zip.setText(Consts.Zip);
        company_name.setText(Consts.Company_Name);
        state.setSelection(Consts.SpinnerItem);
        if(Consts.Photo==null)
        {
            ivImage.setBackgroundResource(R.drawable.avator);

        } else
        {
            ivImage.setImageBitmap(Consts.Photo);
            ivImage.setBorderWidth(5);
        }
    }
    private Map<String, String> SendProfileParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", Email);
        params.put("firstname", Name);
        params.put("phone", HomePhone);
        params.put("street", Street);
        params.put("city", City);
        params.put("state", State);
        params.put("zipcode", Zip);
        params.put("cellphone", CellNo);
        params.put("companyname", Company_Name);
        params.put("statecode", String.valueOf(state.getSelectedItemPosition()));
        params.put("photo", Utils.BitMapToString(thumbnail));
        return params;
    }
    private Map<String, String> ChangePasswordParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", Email);
        params.put("password", Confirm_password);

        return params;
    }

    public void Validation2() {
        boolean  VcuPass = false, VnPass = false, VcnPass = false;
        Email=email.getText().toString();
        Current_password=current_password.getText().toString();
        New_password=new_password.getText().toString();
        Confirm_password=conf_edit_password.getText().toString();
        //current password
        if(Current_password.equals(Consts.Password))
        {
            VcuPass=true;
        }
        else {
            VcuPass=false;
            current_password.setError("Incorrect current password!!!");
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
        if(Validation.isValidConfirmPassword(New_password, Confirm_password ))
        {
            VcnPass=true;
        }
        else {
            VcnPass=false;
            conf_edit_password.setError("Password & Confirm password doesn't match. Please check!!!");
        }
        if (VcuPass && VnPass && VcnPass ) {
            if (internetStatus.InternetStatus(getApplicationContext())) {
                consts.showDialog(ProfileActivity.this);
                SendChangePassword();

            } else
            {
                Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void SendChangePassword()
    {
        Map<String, String> params=ChangePasswordParams();
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
        changepwd = api.CHANGE_PASSWORD(requestParams.toString(), new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                if (responseCode == 200) {
                    try {
                        Log.d("response", responseMessage);
                        JSONObject obj = new JSONObject(responseMessage);
                        String status=obj.getString("msg");
                        if(status.equals("success"))
                        {
                            Consts.Password=Confirm_password;
                            consts.hideDialog();
                            profile_layout.setVisibility(View.VISIBLE);
                            change_psw_layout.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Password changed successfully!!!", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();
                            consts.hideDialog();
                        }


                    }
                    catch (JSONException e){consts.hideDialog();}
                } else {
                    consts.hideDialog();
                }
            }
        });
        changepwd.execute();
    }

    }
