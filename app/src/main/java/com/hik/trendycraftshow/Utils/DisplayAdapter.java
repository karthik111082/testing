package com.hik.trendycraftshow.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hik.trendycraftshow.ImageProcess.ImageLoader;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<byte[]> image1;
	private ArrayList<String> name;
	private ArrayList<String> imageurl;
	private ArrayList<Integer> score;
	public ImageLoader imageLoader;
	IsTablet tablet;
	ImageView image;
	RoundImage roundedImage=new RoundImage();

	public DisplayAdapter(Context mContext, ArrayList<String> image, ArrayList<String> name, ArrayList<Integer> score) {
		this.mContext = mContext;
		this.imageurl = image;
		this.name = name;

		this.score = score;
		imageLoader=new ImageLoader(mContext.getApplicationContext());
		System.out.print("name:"+name.toString());
		System.out.print("score:"+score.toString());
		System.out.print("image:"+image.toString());
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		/*try {
			Holder mHolder;
			LayoutInflater layoutInflater;
			if (child == null) {
				layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				boolean isTablet=tablet.isTablet(mContext);
				if(isTablet) {
					child = layoutInflater.inflate(R.layout.list_item, null);
				}else{
					child = layoutInflater.inflate(R.layout.list_item_mob, null);
				}
				mHolder = new Holder();

				mHolder.txt_id = (TextView) child.findViewById(R.id.rank);
				mHolder.txt_Name = (TextView) child.findViewById(R.id.name);
				mHolder.txt_score = (TextView) child.findViewById(R.id.getScore);
				mHolder.imageview = (ImageView) child.findViewById(R.id.avatar);
				child.setTag(mHolder);
			} else {
				mHolder = (Holder) child.getTag();
			}
            if((pos+1)==1)
            {
                mHolder.txt_id.setBackgroundResource(R.drawable.gold);
            }
            else if((pos+1)==2)
            {
                mHolder.txt_id.setBackgroundResource(R.drawable.silver);
            }
            else if((pos+1)==3)
            {
                mHolder.txt_id.setBackgroundResource(R.drawable.bronze);
            }
            else
            {
                mHolder.txt_id.setBackgroundResource(R.drawable.gray);
            }

			mHolder.txt_id.setText("" + (pos + 1));
			mHolder.txt_Name.setText(name.get(pos));
			mHolder.txt_score.setText(Integer.toString(score.get(pos)));
			imageLoader.DisplayImage(imageurl.get(pos), mHolder.imageview);
		*//*Bitmap bmp = BitmapFactory.decodeByteArray(image.get(pos), 0, image.get(pos).length);
		Bitmap bm1 = roundedImage.getRoundedShape(bmp, 200);
		*//**//*Matrix mat = new Matrix();
		mat.postRotate(270);
		Bitmap bMapRotate = Bitmap.createBitmap(bm1, 0, 0, bm1.getWidth(), bm1.getHeight(), mat, true);*//**//*
      	mHolder.image.setImageBitmap(bm1);*//*
		}catch (Exception e){}*/
		return child;

	}

	public class Holder {
		TextView txt_id;
		TextView txt_Name;
		TextView txt_score;
		ImageView imageview;

	}

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
