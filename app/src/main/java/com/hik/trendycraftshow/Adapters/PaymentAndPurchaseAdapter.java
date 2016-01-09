package com.hik.trendycraftshow.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.IsTablet;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentAndPurchaseAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> title;
	private ArrayList<String> price;
	private ArrayList<String> category;
	private ArrayList<String> date;
	private ArrayList<String> address;


	public PaymentAndPurchaseAdapter(Context mContext,ArrayList<String> title, ArrayList<String> price, ArrayList<String> category, ArrayList<String> date, ArrayList<String> address) {
		this.address = address;
		this.category = category;
		this.date = date;
		this.mContext = mContext;
		this.price = price;
		this.title = title;
	}

	IsTablet tablet;



	public int getCount() {
		// TODO Auto-generated method stub
		return title.size();
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
					child = layoutInflater.inflate(R.layout.paymentandpurchase_list, null);
				}else{
					child = layoutInflater.inflate(R.layout.paymentandpurchase_list, null);
				}
				mHolder = new Holder();

				mHolder.title = (TextView) child.findViewById(R.id.title);
				mHolder.price = (TextView) child.findViewById(R.id.price);
				mHolder.category = (TextView) child.findViewById(R.id.category);
				mHolder.date = (TextView) child.findViewById(R.id.date);
				mHolder.address = (TextView) child.findViewById(R.id.address);
				child.setTag(mHolder);
			} else {
				mHolder = (Holder) child.getTag();
			}


			mHolder.title.setText(title.get(pos));
			Double amount = Double.parseDouble(price.get(pos));
			DecimalFormat decim = new DecimalFormat("0.00");
			mHolder.price.setText("Price: $ "+decim.format(amount));
			mHolder.category.setText("Category: "+category.get(pos));
			mHolder.date.setText("Credited date: "+date.get(pos));
			String add=address.get(pos);
			String[] temp=add.split(",");
			String finaladdress="";
			for (int i=0;i<temp.length;i++){
				if(i==0){
					finaladdress+=temp[i];
				}else{
					finaladdress+=", "+temp[i];
				}
			}
			Log.d("add",finaladdress);
			mHolder.address.setText(finaladdress);



		}catch (Exception e){}
		return child;

	}

	public class Holder {
		TextView title;
		TextView price;
		TextView category;
		TextView date;
		TextView address;


	}

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


}
