package com.hik.trendycraftshow.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MySalesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> addImage;
    private ArrayList<String> addTitle;
    private ArrayList<String> price;
    private ArrayList<String> category;

    ProgressDialog pDialog;


    public MySalesAdapter(Context mContext, ArrayList<String> addImage, ArrayList<String> addTitle, ArrayList<String> price, ArrayList<String> category) {
        this.mContext = mContext;
        this.addImage = addImage;
        this.addTitle = addTitle;
        this.price = price;
        this.category = category;

    }

    IsTablet tablet;



    public int getCount() {
        // TODO Auto-generated method stub
        return addTitle.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(final int pos, View child, ViewGroup parent) {
        try {
            Holder mHolder;
            LayoutInflater layoutInflater;
            if (child == null) {
                layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                boolean isTablet=tablet.isTablet(mContext);
                if(isTablet) {
                    child = layoutInflater.inflate(R.layout.adslist, null);
                }else{
                    child = layoutInflater.inflate(R.layout.adslist, null);
                }
                mHolder = new Holder();
                mHolder.image = (ImageView) child.findViewById(R.id.image);
                mHolder.title = (TextView) child.findViewById(R.id.title);
                mHolder.price = (TextView) child.findViewById(R.id.price);
                mHolder.category = (TextView) child.findViewById(R.id.category);

                child.setTag(mHolder);
            } else {
                mHolder = (Holder) child.getTag();
            }
            Log.d("photo", Consts.PhotoDownloadPath + addImage.get(pos));
            Picasso.with(mContext)
                    .load(Consts.PhotoDownloadPath+addImage.get(pos))
                    .placeholder(R.drawable.avator) // optional
                    .error(R.drawable.avator)         // optional
                    .into(mHolder.image);

            mHolder.title.setText(addTitle.get(pos));

            mHolder.category.setText(category.get(pos));
            Double amount = Double.parseDouble(price.get(pos));
            DecimalFormat decim = new DecimalFormat("0.00");
            Log.d("Price",decim.format(amount));
            mHolder.price.setText("$ "+decim.format(amount));








        }catch (Exception e){}
        return child;

    }

    public class Holder {
        ImageView image;
        TextView title;
        TextView price;
        TextView category;



    }

    public Object getFilter() {
        // TODO Auto-generated method stub
        return null;
    }
    class LoadData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            try{









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

}
