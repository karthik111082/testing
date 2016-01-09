package com.hik.trendycraftshow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hik.trendycraftshow.DetailBusinessCard;
import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.ListConsts;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 29-12-2015.
 */
public class BusinessDirectoryAdaptor extends BaseAdapter {
    private Context context;
    ArrayList<String> image1 = new ArrayList<>();
    ArrayList<Boolean> Status = new ArrayList<Boolean>();
    ArrayList<String> FirstName = new ArrayList<>();
    int pos;


    public BusinessDirectoryAdaptor(Context context, ArrayList<String> firstName, ArrayList<String> image1, ArrayList<Boolean> status) {
        this.context = context;
        FirstName = firstName;
        this.image1 = image1;
        Status = status;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.activity_grid_item, null);

            // set value into textview
            final TextView textView = (TextView) gridView.findViewById(R.id.gridusername);
            final CircularImageView gridImage = (CircularImageView) gridView.findViewById(R.id.grid_item_image);
            final Button onlineStatus = (Button) gridView.findViewById(R.id.statusButton);
pos=position;
            textView.setText(FirstName.get(position));
            Picasso.with(context)
                    .load(Consts.PhotoDownloadPath+image1.get(position))
                    .placeholder(R.drawable.addphoto) // optional
                    .error(R.drawable.addphoto)         // optional
                    .into(gridImage);
            if(Status.get(position))
            {
                onlineStatus.setBackgroundResource(R.drawable.onlinebtn);
            }else
            {
                onlineStatus.setBackgroundResource(R.drawable.offline);
            }
            gridImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Image", "Clicked");
                    Intent i = new Intent(context, DetailBusinessCard.class);
                    i.putExtra("fileid", pos);
                    i.putExtra("userid", ListConsts.UserId.get(pos));

                    context.startActivity(i);
                }
            });


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return image1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}