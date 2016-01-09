package com.hik.trendycraftshow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.Dbhelper;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;
import com.hik.trendycraftshow.Utils.Utils;
import com.hik.trendycraftshow.Utils.Validation;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

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
import java.util.List;
import java.util.Map;

public class ProfileActivity extends NavigationDrawer {
    boolean isTablet;
    IsTablet tablet;
    Consts consts;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    CircularImageView ivImage;
    RoundImage roundImage;
    private static final int PICK_FROM_GALLERY = 4;
    Dbhelper mHelper;
    SQLiteDatabase database;
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
    String photo="";
    File file;
    Map<String, String> params = new HashMap<String, String>();
    String spinneritem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.titletoolbar);
        title.setText("My Profile");
        mHelper=new Dbhelper(getApplicationContext());
        database=mHelper.getWritableDatabase();

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
        cancel_profile=(Button)findViewById(R.id.cancel_profile);
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
                title.setText("Change Password");

            }
        });

        cancel_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
            finish();
            startActivity(i);
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
                    Crop.pickImage(ProfileActivity.this);
                } else if (items[item].equals("Choose from Library")) {
                    Crop.pickGallery(ProfileActivity.this);
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


        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "TrendyUser.png");
            Uri u=Uri.fromFile(file);
            //beginCrop(result.getData());
            beginCrop(u);
        }
        else if(requestCode == Crop.REQUEST_GALLERY && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        }
        else if (requestCode == Crop.REQUEST_CROP) {
            if(data!=null)
                Consts.isPhotoUpload=true;

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

        file = new File(Environment.getExternalStorageDirectory()+File.separator + "TrendyUser.png");
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Consts.UserPhoto=thumbnail;
        ivImage.setImageBitmap(thumbnail);
        ivImage.setBorderWidth(5);
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
        if(thumbnail!=null)
        photo=Utils.BitMapToString(thumbnail);


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
if(Consts.UserPhoto==null&&Consts.Photo.trim().isEmpty())
{
    Vphoto=false;
    Toast.makeText(getApplicationContext(), "Please upload photo!!!", Toast.LENGTH_SHORT).show();
}else{
    Vphoto=true;
}

        if (VCName && VHPhone && VCPhone && VStreet && VCity && VState && VZip&&VState&&Vphoto) {
            if (internetStatus.InternetStatus(getApplicationContext())) {
                    spinneritem=String.valueOf(state.getSelectedItemPosition());
                new PostData().execute();


            } else {
                Toast.makeText(getApplicationContext(), "Trendy Craft Show requires internet. Please check!!!", Toast.LENGTH_SHORT).show();
            }


        }


    }


    public void SendPhotoQuickBlox()
    {
        Boolean fileIsPublic = true;

        QBContent.uploadFileTask(file, fileIsPublic, null, new QBEntityCallbackImpl<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle params) {

                int uploadedFileID = qbFile.getId();

                // Connect image to user
                QBUser user = new QBUser();
                user.setId(Integer.parseInt(Consts.QuickBloxId));
                user.setFileId(uploadedFileID);

                QBUsers.updateUser(user, new QBEntityCallbackImpl<QBUser>() {
                    @Override
                    public void onSuccess(QBUser user, Bundle args) {

                    }

                    @Override
                    public void onError(List<String> errors) {

                    }
                });
            }

            @Override
            public void onError(List<String> errors) {

            }
        }, new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {

            }
        });
    }
    public void SetData() {
        try {
            name.setText(Consts.FirstName);
            email.setText(Consts.UserName);
            homephone.setText(Consts.Phone);
            cellphone.setText(Consts.Cellphone);
            street.setText(Consts.Street);
            city.setText(Consts.City);
            zip.setText(Consts.Zip);
            company_name.setText(Consts.Company_Name);
            state.setSelection(Consts.SpinnerItem);
            Log.d("photo", Consts.PhotoDownloadPath + Consts.Photo);
            if (Consts.UserPhoto == null) {
                Picasso.with(this)
                        .load(Consts.PhotoDownloadPath + Consts.Photo)
                        .placeholder(R.drawable.avator)
                        .skipMemoryCache()
                        .error(R.drawable.avator)         // optional
                        .into(ivImage);
                ivImage.setBorderWidth(5);
            } else {
                ivImage.setImageBitmap(Consts.UserPhoto);
            }
        }catch (Exception e){}
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
        if(Consts.UserPhoto!=null)
        params.put("photo", Utils.BitMapToString(Consts.UserPhoto));
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
                        String status = obj.getString("msg");
                        if (status.equals("success")) {
                            Consts.Password = Confirm_password;
                            consts.hideDialog();
                            profile_layout.setVisibility(View.VISIBLE);
                            change_psw_layout.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Password changed successfully!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();
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
        changepwd.execute();
    }

    class UpdateProfile extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileActivity.this);
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

                        Map<String, String> param=params;
                        StringBuffer requestParams = new StringBuffer();
                        try {
                            if (param != null && param.size() > 0) {
                                // creates the params string, encode them using URLEncoder
                                Iterator<String> paramIterator = param.keySet().iterator();
                                while (paramIterator.hasNext()) {
                                    String key = paramIterator.next();
                                    String value = param.get(key);
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
                                        String status = obj.getString("msg");
                                        if (status.equals("success")) {
                                            Toast.makeText(getApplicationContext(), "Profile successfully update!!!", Toast.LENGTH_SHORT).show();
                                            Consts.FirstName = Name;
                                            Consts.UserName = Email;
                                            Consts.Phone = HomePhone;
                                            Consts.Cellphone = CellNo;
                                            Consts.City = City;
                                            Consts.Street = Street;
                                            Consts.Company_Name = Company_Name;
                                            Consts.Zip = Zip;
                                            Consts.State = State;
                                            Consts.UserPhoto=null;
                                            String sql1="delete from "+ Dbhelper.USER_TABLE;
                                            database.execSQL(sql1);
                                            ContentValues values=new ContentValues();
                                            values.put(Dbhelper.UserName,Consts.UserName);
                                            values.put(Dbhelper.Password, Consts.Password);
                                            values.put(Dbhelper.FirstName, Consts.FirstName);
                                            values.put(Dbhelper.Phone, Consts.Phone);
                                            values.put(Dbhelper.Street, Consts.Street);
                                            values.put(Dbhelper.City, Consts.City);
                                            values.put(Dbhelper.State, Consts.State);
                                            values.put(Dbhelper.Zip, Consts.Zip);
                                            values.put(Dbhelper.UserId, Consts.UserId);
                                            values.put(Dbhelper.Company_Name, Consts.Company_Name);
                                            values.put(Dbhelper.CellPhone, Consts.Cellphone);
                                            values.put(Dbhelper.QuickBloxId, Consts.QuickBloxId);
                                            values.put(Dbhelper.Photo, Consts.Photo);
                                            values.put(Dbhelper.UserSince, Consts.UserSince);
                                            database.insert(Dbhelper.USER_TABLE, null, values);


                                        } else {

                                            Toast.makeText(getApplicationContext(), "Something Wrong!!!", Toast.LENGTH_SHORT).show();

                                        }


                                    } catch (JSONException e) {
                                        if (pDialog.isShowing())
                                            pDialog.dismiss();
                                    }

                                }}
                        });
                        sendprofile.execute();

                    }
                });







            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
                //SendPhotoQuickBlox();
                pDialog.dismiss();




        }

    }


    class PostData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            try{


                params.put("username", Email);
                params.put("firstname", Name);
                params.put("phone", HomePhone);
                params.put("street", Street);
                params.put("city", City);
                params.put("state", State);
                params.put("zipcode", Zip);
                params.put("cellphone", CellNo);
                params.put("companyname", Company_Name);
                params.put("statecode", spinneritem);
                if(Consts.UserPhoto!=null)
                    params.put("photo", Utils.BitMapToString(Consts.UserPhoto));





            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products

            pDialog.dismiss();
            new UpdateProfile().execute();



        }

    }


}
