package com.hik.trendycraftshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hik.trendycraftshow.Adapters.SlidingImage_Adapter;
import com.hik.trendycraftshow.GoogleMaps.MapsActivity;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.ListAdapters.ProductListAdapter;
import com.hik.trendycraftshow.QuickChat.UI.ChatActivity;
import com.hik.trendycraftshow.QuickChat.core.ChatService;
import com.hik.trendycraftshow.Utils.Consts;
import com.hik.trendycraftshow.Utils.GPSTracker;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.hik.trendycraftshow.Utils.ListConsts;
import com.hik.trendycraftshow.Utils.ResultDate;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProductDetail extends NavigationDrawer implements  View.OnClickListener{
    IsTablet tablet;
    boolean isTablet;
    Timer timer;
    String source;
    int page = 0;
    double ratingValue=0.0;
    ViewPager viewPager;
    TextView title,category,postedDate,location,rating,description,eventDate,price,price_symbol;
    RadioButton wishlist,online;
    RatingBar ratebar;
    LinearLayout ratingBarLayout;
    Button viewmap,contact,buynow;
    int catid,fileid;
    private List<QBUser> selected = new ArrayList<QBUser>();
    Consts consts;
    String Category;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    public RadioButton img1,img2,img3,img4;
    ArrayList<String> images=new ArrayList<>();
    private WebServiceRequest.HttpURLCONNECTION addtoWishlist;
    private WebServiceRequest.HttpURLCONNECTION sendPayment;
    private WebServiceRequest.HttpURLCONNECTION removewishlist;
    private WebServiceRequest.HttpURLCONNECTION productPurchase;
    public final int PAYPAL_RESPONSE = 100;
    String amount;
    boolean iswishlist=false;
    GPSTracker gps;
    Double latitude,longitude;
    boolean isWishlist;
    RadioButton rating1,rating2,rating3,rating4,rating5;
    int addRating;
    String Paymentid;
    LinearLayout RatingLayout;
    TextView RatTitle,NoThanksBtn,RateBtn;
    RatingBar ratingBar;
    ScrollView ProductLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_product_detail, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_product_detail_mob, container);
        }
        GpsLocation();
        consts=new Consts(ProductDetail.this);
        title=(TextView)findViewById(R.id.title_pr);
        ProductLayout=(ScrollView)findViewById(R.id.Product_Layout);
        price=(TextView)findViewById(R.id.price);
        img1=(RadioButton)findViewById(R.id.img1);
        img2=(RadioButton)findViewById(R.id.img2);
        img3=(RadioButton)findViewById(R.id.img3);
        img4=(RadioButton)findViewById(R.id.img4);
        category=(TextView)findViewById(R.id.cat_pr);
        price_symbol=(TextView)findViewById(R.id.price_symblol);
        postedDate=(TextView)findViewById(R.id.posteddate);
        eventDate=(TextView)findViewById(R.id.eventdate);
        location=(TextView)findViewById(R.id.location_pr);
        rating=(TextView)findViewById(R.id.total_rating);
        ratingBarLayout=(LinearLayout)findViewById(R.id.ratingbar);
        description=(TextView)findViewById(R.id.desc_pr);
        wishlist=(RadioButton)findViewById(R.id.wishlist_pr);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        online=(RadioButton)findViewById(R.id.status);
        rating1=(RadioButton)findViewById(R.id.rateing1);
        rating2=(RadioButton)findViewById(R.id.rateing2);
        rating3=(RadioButton)findViewById(R.id.rateing3);
        rating4=(RadioButton)findViewById(R.id.rateing4);
        rating5=(RadioButton)findViewById(R.id.rateing5);
        RatingLayout=(LinearLayout)findViewById(R.id.Rating_layout);
        NoThanksBtn=(TextView)findViewById(R.id.no_thanks);
        RateBtn=(TextView)findViewById(R.id.rate);
        RatTitle=(TextView)findViewById(R.id.addtitle);

        viewmap=(Button)findViewById(R.id.viewmap_pr);
        contact=(Button)findViewById(R.id.contact_pr);
        buynow=(Button)findViewById(R.id.buynow);
        mPager = (ViewPager) findViewById(R.id.pager);
        img1.setVisibility(View.INVISIBLE);
        img1.setVisibility(View.INVISIBLE);
        img1.setVisibility(View.INVISIBLE);
        img1.setVisibility(View.INVISIBLE);

        catid=getIntent().getExtras().getInt("catid");
        fileid=getIntent().getExtras().getInt("fileid");
        iswishlist=getIntent().getExtras().getBoolean("isWishlist");
        source=getIntent().getExtras().getString("source");
        title.setText(ListConsts.category.get(catid));
        RatTitle.setText(ListConsts.Title.get(fileid));
        addRating=ListConsts.Rating.get(fileid);
        Log.d("image1", "" + ListConsts.image1.size());
        Log.d("image2",""+ListConsts.image2.size());
        Log.d("image3",""+ListConsts.image3.size());
        Log.d("image4",""+ListConsts.image4.size());

        Log.d("File ID",""+fileid);
        Log.d("Category ID",""+catid);
        if(!ListConsts.Price.get(fileid).trim().isEmpty())
        amount=ListConsts.Price.get(fileid);

        if(catid>4&&catid<8)
        {
            buynow.setVisibility(View.VISIBLE);
            viewmap.setVisibility(View.GONE);
            price.setVisibility(View.VISIBLE);
            price_symbol.setVisibility(View.VISIBLE);
            ratingBarLayout.setVisibility(View.VISIBLE);
        }else{
            price.setVisibility(View.GONE);
            ratingBarLayout.setVisibility(View.INVISIBLE);
            price_symbol.setVisibility(View.GONE);
            buynow.setVisibility(View.GONE);
        }
        if(ListConsts.image1.size()>0&&!ListConsts.image1.get(fileid).trim().isEmpty())
        images.add(ListConsts.image1.get(fileid));
        if(ListConsts.image2.size()>0&&!ListConsts.image2.get(fileid).trim().isEmpty())
        images.add(ListConsts.image2.get(fileid));
        if(ListConsts.image3.size()>0&&!ListConsts.image3.get(fileid).trim().isEmpty())
        images.add(ListConsts.image3.get(fileid));
        if(ListConsts.image4.size()>0&&!ListConsts.image4.get(fileid).trim().isEmpty())
        images.add(ListConsts.image4.get(fileid));
        ListConsts.addCategory();

        setData();
        init();
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iswishlist) {
                    RemoveWishlist();
                } else {
                    AddedWishlist();
                }

            }
        });
        viewmap.setOnClickListener(this);
        contact.setOnClickListener(this);
        buynow.setOnClickListener(this);
        NoThanksBtn.setOnClickListener(this);
        RateBtn.setOnClickListener(this);

    }
public void onClick(View v)
{
    switch (v.getId())
    {
        case R.id.viewmap_pr:

            Intent i=new Intent(getApplicationContext(), MapsActivity.class);
            i.putExtra("addname",ListConsts.Title.get(fileid));
            i.putExtra("yourlat",latitude);
            i.putExtra("yourlong",longitude);
            i.putExtra("addlat",Double.parseDouble(ListConsts.Latitude.get(fileid)));
            i.putExtra("addlong",Double.parseDouble(ListConsts.Langtitude.get(fileid)));
            startActivity(i);
            finish();

            break;
        case R.id.buynow:
            Log.d("Seller PayPal Account:",ListConsts.PaypalId.get(fileid));
            if(!ListConsts.PaypalId.get(fileid).trim().isEmpty()&&!ListConsts.Price.get(fileid).trim().isEmpty())
            PayPalButtonClick(ListConsts.PaypalId.get(fileid),ListConsts.Price.get(fileid));

            break;
        case R.id.contact_pr:
           // Toast.makeText(getApplicationContext(),"Content under development",Toast.LENGTH_SHORT).show();

            createChat();
            break;
        case R.id.no_thanks:
            ratingValue=ratingBar.getRating();
            PurchaseComplete(Paymentid);
            break;
        case R.id.rate:
            ratingValue=ratingBar.getRating();
            PurchaseComplete(Paymentid);
            break;

    }
}
    public void setData()
    {
        try {
            setRating();
            title.setText(ListConsts.Title.get(fileid));
            category.setText(ListConsts.category.get(catid));
            Double amount = Double.parseDouble(ListConsts.Price.get(fileid));
            DecimalFormat decim = new DecimalFormat("0.00");
            Log.d("Price",decim.format(amount));
            price.setText("" + decim.format(amount));
            postedDate.setText(ListConsts.PostDate.get(fileid));
            eventDate.setText(ListConsts.Duration.get(fileid));
            location.setText(ListConsts.Address.get(fileid));
            description.setText(ListConsts.Desc.get(fileid));
            if (iswishlist) {
                wishlist.setChecked(true);
            } else {
                if (!ListConsts.WishlistStatus.get(fileid).toString().trim().isEmpty())
                    iswishlist = ListConsts.WishlistStatus.get(fileid);
                if (ListConsts.WishlistStatus.get(fileid)) {
                    wishlist.setChecked(true);

                } else {
                    wishlist.setChecked(false);
                }
            }
            if (Boolean.valueOf(ListConsts.OnlineStatus.get(fileid))) {
                online.setChecked(true);
                online.setText("Seller Online");

            } else {
                online.setChecked(false);
                online.setText("Seller Offline");
            }

        }catch (Exception e){}
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(source.equalsIgnoreCase("wishlist"))
            {
                Intent i = new Intent(getApplicationContext(), Wishlist.class);
                finish();
                startActivity(i);
            }else if(source.equalsIgnoreCase("product")){
                Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                finish();
                i.putExtra("catid", catid);
                i.putExtra("isSearch",false);
                i.putExtra("isListView",true);
                startActivity(i);
            }else if(source.equalsIgnoreCase("followup")){
                Intent i = new Intent(getApplicationContext(), MyfollowUp.class);
                finish();

                startActivity(i);
            }

        }
        return false;

    }
    public void AddedWishlist()
    {
        consts.showDialog(ProductDetail.this);
        String params = "userid="+ Consts.UserId+"&addid="+ListConsts.AddId.get(fileid);
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        addtoWishlist = api.ADD_TO_WISHLIST(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {
                        iswishlist = true;
                        wishlist.setChecked(true);
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
                    wishlist.setChecked(false);
                    consts.hideDialog();
                }
            }
        });
        addtoWishlist.execute();
    }


    public void RemoveWishlist()
    {
        consts.showDialog(ProductDetail.this);
        String params = "userid="+ Consts.UserId+"&addid="+ListConsts.AddId.get(fileid);
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        removewishlist = api.REMOVE_WISHLIST(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {
                        iswishlist = false;
                        wishlist.setChecked(false);
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
                    wishlist.setChecked(false);
                    consts.hideDialog();
                }
            }
        });
        removewishlist.execute();
    }

    public void PurchaseComplete(String paymentid)
    {
        consts.showDialog(ProductDetail.this);
        String params = "orderuserid="+ Consts.UserId+"&rating="+ratingValue+"&paymentid="+paymentid+"&productuserid="+ListConsts.UserId.get(fileid)+"&addid="+ListConsts.AddId.get(fileid)+"&title="+ListConsts.Title.get(fileid)+"&price="+ListConsts.Price.get(fileid)+"&category="+category.getText().toString()+"&address="+ListConsts.Address.get(fileid)+"&ordereddate="+ ResultDate.CurrentDate();
        Log.d("parameters", params);
        params=params.replaceAll(" ", "%20");
        productPurchase = api.PURCHASE_COMPLETE(params, new WebServiceRequest.Callback() {
            @Override
            public void onResult(int responseCode, String responseMessage, Exception exception) {
                Log.d("response", responseMessage);
                if (responseCode == 200) {
                    try {

                        JSONObject obj = new JSONObject(responseMessage);
                        String status = obj.getString("msg");
                        if (status.equalsIgnoreCase("success")) {
                            if(iswishlist)
                            {
                                Intent i = new Intent(getApplicationContext(), Wishlist.class);
                                finish();
                                startActivity(i);
                            }else{
                                Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
                                finish();
                                i.putExtra("catid", catid);
                                i.putExtra("isSearch",false);
                                i.putExtra("isListView",true);
                                startActivity(i);
                            }

                        }

                        consts.hideDialog();
                    } catch (JSONException e) {
                        Log.d(getApplicationContext().toString(), e.toString());
                        consts.hideDialog();
                    }
                } else {
                    wishlist.setChecked(false);
                    consts.hideDialog();
                }
            }
        });
        productPurchase.execute();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_RESPONSE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    //The payment succeeded
                    String email = PayPal.getInstance().getAccountEmail();
                    Log.d("Email", email);
                    Paymentid = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                    Log.d("pavan", "success " + Paymentid);
                    ProductLayout.setVisibility(View.GONE);
                    RatingLayout.setVisibility(View.VISIBLE);



                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getApplicationContext(), "Payment Canceled , Try again ", Toast.LENGTH_LONG).show();


                    break;
                case PayPalActivity.RESULT_FAILURE:
                    Toast.makeText(getApplicationContext(), "Payment failed , Try again ", Toast.LENGTH_LONG).show();


                    break;
            }
        }
    }

    public void createChat()
    {
        try {

            final int chatId = Integer.parseInt(ListConsts.QuickBloxIds.get(fileid));
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
        }catch (Exception e)
        {

        }

    }
    public void startSingleChat(QBDialog dialog) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.EXTRA_DIALOG, dialog);
        ChatActivity.start(this, bundle);
    }
    public void GpsLocation()
    {
        gps = new GPSTracker(ProductDetail.this);

        // check if GPS enabled
        if(gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }
    }
    private void init() {


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(ProductDetail.this,images));


        CirclePageIndicator indicator=(CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
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
                mPager.setCurrentItem(currentPage++, true);
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
        }, 3000, 4000);
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

    public void setRating()
    {
        switch (addRating)
        {
            case 0:
                rating1.setChecked(false);
                rating2.setChecked(false);
                rating3.setChecked(false);
                rating4.setChecked(false);
                rating5.setChecked(false);
                rating.setText("0.0 Rating");
                break;
            case 1:
                rating1.setChecked(true);
                rating2.setChecked(false);
                rating3.setChecked(false);
                rating4.setChecked(false);
                rating5.setChecked(false);
                rating.setText("1.0 Rating");
                break;
            case 2:
                rating1.setChecked(true);
                rating2.setChecked(true);
                rating3.setChecked(false);
                rating4.setChecked(false);
                rating5.setChecked(false);
                rating.setText("2.0 Rating");
                break;
            case 3:
                rating1.setChecked(true);
                rating2.setChecked(true);
                rating3.setChecked(true);
                rating4.setChecked(false);
                rating5.setChecked(false);
                rating.setText("3.0 Rating");
                break;
            case 4:
                rating1.setChecked(true);
                rating2.setChecked(true);
                rating3.setChecked(true);
                rating4.setChecked(true);
                rating5.setChecked(false);
                rating.setText("4.0 Rating");
                break;
            case 5:
                rating1.setChecked(true);
                rating2.setChecked(true);
                rating3.setChecked(true);
                rating4.setChecked(true);
                rating5.setChecked(true);
                rating.setText("5.0 Rating");
                break;



        }
    }
}

