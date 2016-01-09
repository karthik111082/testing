package com.hik.trendycraftshow.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.PaymentMethod;
import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.DisplayAdapter;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.RoundImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class PaymentAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> paypalid;
	private ArrayList<String> status;
	private WebServiceRequest.HttpURLCONNECTION deleteAccount;
	private WebServiceRequest.HttpURLCONNECTION updateAccount;

	Api api;


	public PaymentAdapter(Context mContext, ArrayList<String> paypalid, ArrayList<String> status) {
		this.mContext = mContext;
		this.paypalid = paypalid;
		this.status = status;


	}

	IsTablet tablet;



	public int getCount() {
		// TODO Auto-generated method stub
		return status.size();
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
					child = layoutInflater.inflate(R.layout.payment_list, null);
				}else{
					child = layoutInflater.inflate(R.layout.payment_list_mob, null);
				}
				mHolder = new Holder();

				mHolder.paypalid = (TextView) child.findViewById(R.id.paypalid);
				mHolder.defaultText = (TextView) child.findViewById(R.id.defaultmsg);
				mHolder.deleteText = (TextView) child.findViewById(R.id.deletetext);
				mHolder.statusmsg = (TextView) child.findViewById(R.id.defaulttext);
				mHolder.defaultButton = (Button) child.findViewById(R.id.setdefault);
				mHolder.deleteButton = (ImageButton) child.findViewById(R.id.delete);
				child.setTag(mHolder);
			} else {
				mHolder = (Holder) child.getTag();
			}


			mHolder.paypalid.setText(paypalid.get(pos));
			if(Boolean.valueOf(status.get(pos)))
			{
				mHolder.statusmsg.setVisibility(View.VISIBLE);
				mHolder.deleteButton.setVisibility(View.INVISIBLE);
				mHolder.deleteText.setVisibility(View.INVISIBLE);
				mHolder.defaultText.setVisibility(View.INVISIBLE);
				mHolder.defaultButton.setVisibility(View.INVISIBLE);
			}else{
				mHolder.statusmsg.setVisibility(View.INVISIBLE);
				mHolder.deleteButton.setVisibility(View.VISIBLE);
				mHolder.defaultButton.setVisibility(View.VISIBLE);
				mHolder.deleteText.setVisibility(View.VISIBLE);
				mHolder.defaultText.setVisibility(View.VISIBLE);
			}
			mHolder.defaultButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				updateAccount(pos);
				}
			});
			mHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					deletePayPalAccount(pos);
				}
			});

		}catch (Exception e){}
		return child;

	}

	public class Holder {
		TextView paypalid;
		TextView statusmsg;
		TextView defaultText;
		TextView deleteText;
		Button defaultButton;
		ImageButton deleteButton;

	}

	public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	public void deletePayPalAccount(int pos)
	{

		String params = "userid="+ Consts.UserId+"&paypalmail="+paypalid.get(pos);
		Log.d("parameters", params);
		params=params.replaceAll(" ","%20");
		deleteAccount = api.DELETE_PAYPAL_ACCOUNT(params, new WebServiceRequest.Callback() {
			@Override
			public void onResult(int responseCode, String responseMessage, Exception exception) {

				if (responseCode == 200) {
					try {

						JSONArray obj = new JSONArray(responseMessage);
						Log.d("response", responseMessage.toString());


						paypalid.clear();
						status.clear();
						for (int i = 0; i < obj.length(); i++)

						{
							JSONObject data = obj.getJSONObject(i);
							String paypal = data.getString("paypalMail");
							String stat = data.getString("status");
							paypalid.add(paypal);
							status.add(stat);


						}
						PaymentAdapter adapter = new PaymentAdapter(mContext, paypalid, status);
						PaymentMethod.payment_list.setAdapter(adapter);


					} catch (JSONException e) {
						Log.d("Forgot Password Error", e.toString());

					}
				}
			}
		});
		deleteAccount.execute();

	}
	public void updateAccount(int pos)
	{

		String params = "userid="+ Consts.UserId+"&paypalmail="+paypalid.get(pos);
		Log.d("parameters", params);
		params=params.replaceAll(" ","%20");
		deleteAccount = api.UPDATE_PAYPAL(params, new WebServiceRequest.Callback() {
			@Override
			public void onResult(int responseCode, String responseMessage, Exception exception) {

				if (responseCode == 200) {
					try {

						JSONArray obj = new JSONArray(responseMessage);
						Log.d("response", responseMessage.toString());

						paypalid.clear();
						status.clear();

						for (int i = 0; i < obj.length(); i++)

						{
							JSONObject data = obj.getJSONObject(i);
							String paypal = data.getString("paypalMail");
							String stat = data.getString("status");

							paypalid.add(paypal);
							status.add(stat);


						}
						PaymentAdapter adapter = new PaymentAdapter(mContext, paypalid, status);
						PaymentMethod.payment_list.setAdapter(adapter);


					} catch (JSONException e) {
						Log.d("Forgot Password Error", e.toString());

					}
				}
			}
		});
		deleteAccount.execute();

	}

}
