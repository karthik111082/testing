package com.hik.trendycraftshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PostAdvertismentTrendyMarket extends AppCompatActivity {
    ImageView img1,img2,img3,img4;
    int REQUEST_CAMERA1 = 11,REQUEST_CAMERA2 = 22,REQUEST_CAMERA3 = 33,REQUEST_CAMERA4 = 44, SELECT_FILE = 1, SELECT_FILE2 = 2;
    IsTablet tablet;
    boolean isTablet;
    RoundImage roundImage;
    Bitmap bitmap;
    byte[] image1,image2,image3,image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet=tablet.isTablet(getApplicationContext());
        if(isTablet){
            setContentView(R.layout.activity_post_advertisment_trendymarket);
        }else {
            setContentView(R.layout.activity_post_advertisment_trendymarket_mob);

        }

        //setContentView(R.layout.activity_post_advertisment_trendymarket);

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
                    img1.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail,1);
                    break;
                case 2:
                    img2.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail, 2);
                    break;
                case 3:
                    img3.setImageBitmap(thumbnail);
                    BitmapToByte(thumbnail, 3);
                    break;
                case 4:
                    img4.setImageBitmap(thumbnail);
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
                img1.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 1);
                break;
            case 2:
                img2.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 2);
                break;
            case 3:
                img3.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 3);
                break;
            case 4:
                img4.setImageBitmap(bitmap);
                BitmapToByte(bitmap, 4);


        }
    }
}
