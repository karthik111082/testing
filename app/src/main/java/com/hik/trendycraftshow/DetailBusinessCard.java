package com.hik.trendycraftshow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.Adapters.ProductAdapter;
import com.hik.trendycraftshow.Adapters.SlidingImage_Adapter;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.QuickChat.UI.ChatActivity;
import com.hik.trendycraftshow.QuickChat.core.ChatService;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.ListConsts;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailBusinessCard extends NavigationDrawer implements View.OnClickListener {
TextView name,company,useraddress,mail,phone,cell,desc;
    ImageButton MsgMe,OnlineStatus,RateMe,WishList;
    private WebServiceRequest.HttpURLCONNECTION addtoWishlist;
    public WebServiceRequest.HttpURLCONNECTION getProducts;
    private WebServiceRequest.HttpURLCONNECTION removewishlist;
    private WebServiceRequest.HttpURLCONNECTION productPurchase;
    ListView listView;
    ViewPager image;
    private List<QBUser> selected = new ArrayList<QBUser>();

    ArrayList<String> userid=new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    int fileid;
    ArrayList<String> images=new ArrayList<>();
    ProductAdapter adapter;
    boolean hasNext;
    int start=0;
    int limit=5;
    ProgressDialog pDialog;
    int categoryId;
    boolean iswishlist;
    boolean isRated;
    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isTablet=tablet.isTablet(getApplicationContext());
        if(isTablet)
        {
            getLayoutInflater().inflate(R.layout.activity_detail_business_card, container);}
        else
        {
            getLayoutInflater().inflate(R.layout.activity_detail_business_card, container);
        }
        name=(TextView)findViewById(R.id.name_bc);
        company=(TextView)findViewById(R.id.company_bc);
        useraddress=(TextView)findViewById(R.id.address_bc);
        mail=(TextView)findViewById(R.id.mail_bc);
        phone=(TextView)findViewById(R.id.home_bc);
        cell=(TextView)findViewById(R.id.cell_bc);
        desc=(TextView)findViewById(R.id.desc_bc);

        MsgMe=(ImageButton)findViewById(R.id.msgme_bc);
        OnlineStatus=(ImageButton)findViewById(R.id.status_bc);
        RateMe=(ImageButton)findViewById(R.id.rateme_bc);
        WishList=(ImageButton)findViewById(R.id.wishlist_bc);
        listView=(ListView)findViewById(R.id.list_bc);
        image=(ViewPager)findViewById(R.id.userimg_bc);
        fileid=getIntent().getExtras().getInt("fileid");
        iswishlist=ListConsts.BusinessWishList.get(fileid);
        isRated=ListConsts.BusinessRate.get(fileid);
        if(ListConsts.BusinessImage1.size()>0&&!ListConsts.BusinessImage1.get(fileid).trim().isEmpty())
            images.add(ListConsts.BusinessImage1.get(fileid));
        if(ListConsts.BusinessImage2.size()>0&&!ListConsts.BusinessImage2.get(fileid).trim().isEmpty())
            images.add(ListConsts.BusinessImage2.get(fileid));
        if(ListConsts.BusinessImage3.size()>0&&!ListConsts.BusinessImage3.get(fileid).trim().isEmpty())
            images.add(ListConsts.BusinessImage3.get(fileid));
        if(ListConsts.BusinessImage4.size()>0&&!ListConsts.BusinessImage4.get(fileid).trim().isEmpty())
            images.add(ListConsts.BusinessImage4.get(fileid));
        init();

        MsgMe.setOnClickListener(this);
        RateMe.setOnClickListener(this);
        WishList.setOnClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    if (hasNext) {

                        ++start;
                        if (!pDialog.isShowing())
                            new LoadData().execute();

                    }
                }


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Consts.isGuest) {
                    Intent i = new Intent(getApplicationContext(), ProductDetail.class);
                    i.putExtra("fileid", position);
                    i.putExtra("catid", categoryId);
                    i.putExtra("isWishlist", iswishlist);
                    i.putExtra("source", "business");
                    finish();
                    startActivity(i);

                }

            }
        });
        SetData();
        userid.addAll(ListConsts.UserId);

        ListConsts.clearList();
        new LoadData().execute();

    }
public void onClick(View v)
{
    switch (v.getId())
    {
        case R.id.msgme_bc:
            createChat();
            break;
        case R.id.wishlist_bc:
            if(iswishlist)
            {
                RemoveWishlist();
            }
            else {
                AddedWishlist();
            }
            break;
        case R.id.rateme_bc:
            if(isRated)
            {
                RemoveRateUser();
            }else{
                AddRateUser();
            }

            break;

    }
}
    private void init() {
        Log.d("total images",""+images.size());
        image.setAdapter(new SlidingImage_Adapter(DetailBusinessCard.this,images));
        CirclePageIndicator indicator=(CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(image);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES =images.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == NUM_PAGES)
                {
                    currentPage = 0;
                }
                image.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(Update);
            }
        }, 3000, 1000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2)
            {
            }
            @Override
            public void onPageScrollStateChanged(int pos)
            {
            }
        });
    }
    private void SetData()
    {
        try{
        name.setText(ListConsts.BusinessName.get(fileid));
        useraddress.setText(ListConsts.BusinessAddress.get(fileid));
        mail.setText(ListConsts.BusinessEmail.get(fileid));
        company.setText(ListConsts.BusinessCompany.get(fileid));
        phone.setText(ListConsts.BusinessHomePhone.get(fileid));
        cell.setText(ListConsts.BusinessCell.get(fileid));
        desc.setText(ListConsts.BusinessDesc.get(fileid));
        if(iswishlist)
        {
            WishList.setBackgroundResource(R.drawable.removewish);
        }
        else {
            WishList.setBackgroundResource(R.drawable.addwish);
        }

        if(isRated) {
            RateMe.setBackgroundResource(R.drawable.ratemetrue_bc);
        }
        else
        {
            RateMe.setBackgroundResource(R.drawable.ratemefalse_bc);
        }
        if(ListConsts.BusinessOnlineStatus.get(fileid))
        {
            OnlineStatus.setBackgroundResource(R.drawable.online_bc);
        }else
        {
            OnlineStatus.setBackgroundResource(R.drawable.offline_bc);
        }
        }catch (Exception e){}
    }

    public void AddedWishlist()
    {
        consts.showDialog(DetailBusinessCard.this);
        String params = "userid="+ Consts.UserId+"&directoryid="+ListConsts.BusinessId.get(fileid);
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        addtoWishlist = api.ADD_WISHLIST_CARD(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {
                        iswishlist = true;
                        WishList.setBackgroundResource(R.drawable.removewish);
                        consts.hideDialog();
                        JSONObject obj = new JSONObject(responseMessage);
                        String status = obj.getString("msg");
                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Log.d(getApplicationContext().toString(), e.toString());
                        consts.hideDialog();
                    }
                } else {

                    consts.hideDialog();
                }
            }
        });
        addtoWishlist.execute();
    }


    public void RemoveWishlist()
    {
        consts.showDialog(DetailBusinessCard.this);
        String params = "userid="+ Consts.UserId+"&directoryid="+ListConsts.BusinessId.get(fileid);
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        removewishlist = api.REMOVE_WISHLIST_CARD(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {
                        iswishlist = false;
                        WishList.setBackgroundResource(R.drawable.addwish);
                        Toast.makeText(getApplicationContext(), "Remove from wishlist", Toast.LENGTH_SHORT).show();
                        consts.hideDialog();
                        JSONObject obj = new JSONObject(responseMessage);
                        String status = obj.getString("msg");
                        if (status.equalsIgnoreCase("success")) {
                            // Toast.makeText(getApplicationContext(), "Remove from wishlist", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Log.d(getApplicationContext().toString(), e.toString());
                        consts.hideDialog();
                    }
                } else {

                    consts.hideDialog();
                }
            }
        });
        removewishlist.execute();
    }

    public void AddRateUser()
    {
        consts.showDialog(DetailBusinessCard.this);
        String params = "userid="+ Consts.UserId+"&directoryid="+ListConsts.BusinessId.get(fileid);
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        addtoWishlist = api.RATE_DIRECTORY(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {
                        isRated = true;
                        RateMe.setBackgroundResource(R.drawable.ratemetrue_bc);
                        consts.hideDialog();
                        JSONObject obj = new JSONObject(responseMessage);
                        String status = obj.getString("msg");
                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(), "Thanks for your Rating!!!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Log.d(getApplicationContext().toString(), e.toString());
                        consts.hideDialog();
                    }
                } else {
                    isRated = false;
                    RateMe.setBackgroundResource(R.drawable.ratemefalse_bc);
                    consts.hideDialog();
                }
            }
        });
        addtoWishlist.execute();
    }
    public void RemoveRateUser()
    {
        consts.showDialog(DetailBusinessCard.this);
        String params = "userid="+ Consts.UserId+"&directoryid="+ListConsts.BusinessId.get(fileid);
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        addtoWishlist = api.REMOVE_RATE_DIRECTORY(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {
                        isRated = false;
                        RateMe.setBackgroundResource(R.drawable.ratemefalse_bc);
                        consts.hideDialog();
                        JSONObject obj = new JSONObject(responseMessage);
                        String status = obj.getString("msg");
                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(), "Thanks for your Feedback!!!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        Log.d(getApplicationContext().toString(), e.toString());
                        consts.hideDialog();
                    }
                } else {
                    isRated = false;
                    RateMe.setBackgroundResource(R.drawable.ratemefalse_bc);
                    consts.hideDialog();
                }
            }
        });
        addtoWishlist.execute();
    }

    class LoadData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DetailBusinessCard.this);
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
                        String params = "userid=" + Consts.UserId + "&adduserid=" +userid.get(fileid)+ "&start=" + start + "&end=" + limit;
                        Log.d("parameters", params);
                        params = params.replaceAll(" ", "%20");
                        getProducts = api.GET_PRODUCTS_BY_USER(params, new WebServiceRequest.Callback() {
                            @Override
                            public void onResult(int responseCode, String responseMessage, Exception exception) {
                                Log.d("response", responseMessage);
                                if (responseCode == 200) {
                                    response = responseMessage;
                                    try {


                                        ArrayList<String> tempwish = new ArrayList<String>();
                                        JSONObject obj = new JSONObject(responseMessage);
                                        JSONArray arr = obj.getJSONArray("adds");
                                        hasNext = Boolean.parseBoolean(obj.getString("hasNext"));
                                        if (!Consts.UserId.trim().isEmpty()) {
                                            JSONArray wish = obj.getJSONArray("wishLists");
                                            Log.d("length1", "" + arr.length());
                                            Log.d("length2", "" + wish.length());
                                            hasNext = Boolean.parseBoolean(obj.getString("hasNext"));
                                            for (int w = 0; w < wish.length(); w++) {
                                                JSONObject data = wish.getJSONObject(w);
                                                tempwish.add(data.getString("addId"));
                                            }
                                        }

                                        for (int i = 0; i < arr.length(); i++)

                                        {
                                            JSONObject data = arr.getJSONObject(i);
                                            String addid = data.getString("addid");
                                            if (tempwish.contains(addid)) {
                                                ListConsts.WishlistStatus.add(true);
                                            } else {
                                                ListConsts.WishlistStatus.add(false);
                                            }
                                            String catid = data.getString("catid");
                                            String userid = data.getString("userid");
                                            String title = data.getString("addTitle");
                                            String price = data.getString("price");
                                            String startdate = data.getString("startdate");
                                            String enddate = data.getString("enddate");
                                            String postdate = data.getString("posteddate");
                                            String desc = data.getString("description");
                                            String street = data.getString("street");
                                            String city = data.getString("city");
                                            String state = data.getString("state");
                                            String zip = data.getString("zip");
                                            String latitude = data.getString("latitude");
                                            String langtitude = data.getString("langitude");
                                            String online = data.getString("onlineStatus");
                                            String quickid = data.getString("quickId");
                                            double rating = Double.parseDouble(data.getString("rating"));
                                            JSONArray photoArray = data.getJSONArray("photos");
                                            for (int j = 0; j < photoArray.length(); j++) {
                                                JSONObject photo = photoArray.getJSONObject(j);
                                                if (j == 0) {
                                                    ListConsts.image1.add(photo.getString("photo"));
                                                } else if (j == 1) {
                                                    ListConsts.image2.add(photo.getString("photo"));
                                                } else if (j == 2) {
                                                    ListConsts.image3.add(photo.getString("photo"));
                                                } else if (j == 3) {
                                                    ListConsts.image4.add(photo.getString("photo"));
                                                }
                                            }
                                            JSONObject paypal = data.getJSONObject("method");
                                            String Paypal = paypal.getString("paypalMail");
                                            ListConsts.PaypalId.add(Paypal);
                                            ListConsts.AddId.add(addid);
                                            ListConsts.CatId.add(catid);
                                            ListConsts.UserId.add(userid);
                                            ListConsts.Price.add(price);
                                            ListConsts.StartDate.add(startdate);
                                            ListConsts.EndDate.add(enddate);
                                            ListConsts.PostDate.add(postdate);
                                            ListConsts.Desc.add(desc);
                                            ListConsts.Street.add(street);
                                            ListConsts.City.add(city);
                                            ListConsts.State.add(state);
                                            ListConsts.Zip.add(zip);
                                            ListConsts.QuickBloxIds.add(quickid);
                                            ListConsts.OnlineStatus.add(Boolean.valueOf(online));
                                            ListConsts.Langtitude.add(langtitude);
                                            ListConsts.Latitude.add(latitude);
                                            int rnd = Integer.parseInt(String.valueOf(Math.round(rating)));
                                            Log.d("original", "" + rating);
                                            Log.d("Round", "" + rnd);
                                            ListConsts.Rating.add(rnd);
                                            ListConsts.Title.add(title);
                                            ListConsts.Address.add(street + ", " + city + ", " + state + ", " + zip);
                                            ListConsts.Duration.add(startdate + " - " + enddate);


                                        }

                                        adapter = new ProductAdapter(DetailBusinessCard.this, ListConsts.image1, ListConsts.Title, ListConsts.PostDate, ListConsts.Address, ListConsts.Duration);
                                        listView.setAdapter(adapter);
                                        int pos = start * limit;
                                        listView.setSelectionFromTop(pos, 0);


                                    } catch (JSONException e) {
                                        Log.d(getApplicationContext().toString(), e.toString());

                                    }
                                } else {

                                }
                            }
                        });
                        getProducts.execute();

                    }
                });






            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String responseMessage) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();




        }

    }

    public void createChat()
    {
        try {
            final int chatId = Integer.parseInt(ListConsts.BusinessQuickId.get(fileid));
            if (Integer.parseInt(Consts.QuickBloxId) != chatId) {
                QBUsers.getUser(chatId, new QBEntityCallbackImpl<QBUser>() {
                    @Override
                    public void onSuccess(QBUser user, Bundle args) {
                        selected.add(user);

                        ChatService.getInstance().addDialogsUsers(selected);
                        QBPrivateChatManager privateChatManager = QBChatService.getInstance().getPrivateChatManager();
                        privateChatManager.createDialog(chatId, new QBEntityCallbackImpl<QBDialog>() {
                            @Override
                            public void onSuccess(QBDialog dialog, Bundle args) {
                                startSingleChat(dialog);
                            }

                            @Override
                            public void onError(List<String> errors) {

                            }
                        });
                    }

                    @Override
                    public void onError(List<String> errors) {

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Sorry! can't connect chat because tThis is Your Ad", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e)
        {}
    }
    public void startSingleChat(QBDialog dialog) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.EXTRA_DIALOG, dialog);
        ChatActivity.start(this, bundle);
    }

}
