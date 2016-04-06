package  com.hik.trendycraftshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hik.trendycraftshow.ListAdapters.ProductListAdapter;
import com.hik.trendycraftshow.Utils.InternetStatus;
import com.hik.trendycraftshow.Utils.IsTablet;

public class AddsListActivity extends NavigationDrawer implements View.OnClickListener{
    InternetStatus internet;
    Boolean internetstatus = false;
    String test;
    IsTablet tablet;
    public FrameLayout listContainer;
    ImageButton craftButton,artButton,expoButton,fairsButton,artOnly,craftOnly,directSale;
    boolean isSearch=false;
    Button listButton,mapButton;
    LinearLayout normalView,trendyView;
    int categoryId;
    String keyword,street,city,zip,startdate,enddate,minPrice,maxPrice,distance;
    boolean isListView=true;
    ProductListAdapter Padapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isTablet=tablet.isTablet(getApplicationContext());
       if(isTablet){
            getLayoutInflater().inflate(R.layout.activity_adds_list, container);
           search.setBackgroundResource(R.drawable.search);
       }
        else {
            getLayoutInflater().inflate(R.layout.activity_adds_list_mob, container);
           search.setBackgroundResource(R.drawable.searchmobile);
        }
        Padapter=new ProductListAdapter();
        search.setVisibility(View.VISIBLE);
        listContainer=(FrameLayout)findViewById(R.id.list_content);
        craftButton=(ImageButton)findViewById(R.id.craftButton);
        artButton=(ImageButton)findViewById(R.id.artButton);
        expoButton=(ImageButton)findViewById(R.id.expoButton);
        fairsButton=(ImageButton)findViewById(R.id.fairsButton);
        listButton=(Button)findViewById(R.id.listButton);
        mapButton=(Button)findViewById(R.id.mapButton);
        artOnly=(ImageButton)findViewById(R.id.artOnly);
        craftOnly=(ImageButton)findViewById(R.id.craftOnly);
        directSale=(ImageButton)findViewById(R.id.directSale);
        normalView=(LinearLayout)findViewById(R.id.normalView);
        trendyView=(LinearLayout)findViewById(R.id.trendyView);

        categoryId=getIntent().getExtras().getInt("catid");
        isSearch=getIntent().getExtras().getBoolean("isSearch");
        if(categoryId>=5&&categoryId<8)
        {
            normalView.setVisibility(View.GONE);
            trendyView.setVisibility(View.VISIBLE);
        }
        if(isSearch)
        {
            keyword=getIntent().getExtras().getString("keyword");
            street=getIntent().getExtras().getString("street");
            city=getIntent().getExtras().getString("city");
            zip=getIntent().getExtras().getString("zip");
            distance=getIntent().getExtras().getString("distance");
            startdate=getIntent().getExtras().getString("startdate");
            enddate=getIntent().getExtras().getString("enddate");
            minPrice=getIntent().getExtras().getString("minprice");
            maxPrice=getIntent().getExtras().getString("maxprice");
        }


        craftButton.setOnClickListener(this);
        artButton.setOnClickListener(this);
        expoButton.setOnClickListener(this);
        fairsButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
        artOnly.setOnClickListener(this);
        craftOnly.setOnClickListener(this);
        directSale.setOnClickListener(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryId<5)
                {
                    Intent i=new Intent(getApplicationContext(),Search.class);
                    i.putExtra("catid",categoryId);
                    i.putExtra("isListView",true);
                    finish();
                    startActivity(i);
                }else
                {
                    Intent i=new Intent(getApplicationContext(),MarketSearch.class);
                    i.putExtra("catid",categoryId);
                    i.putExtra("isListView",true);
                    finish();
                    startActivity(i);
                }

            }
        });




    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.craftButton:
                ChangeButton(1);
                categoryId=1;
                SendData();

                break;
            case R.id.artButton:
                ChangeButton(2);
                categoryId=2;
                SendData();

                break;
            case R.id.expoButton:
                ChangeButton(3);
                categoryId=3;
                SendData();

                break;
            case R.id.fairsButton:
                ChangeButton(4);
                categoryId=4;
                SendData();

                break;
            case R.id.craftOnly:
                ChangeButton(5);
                categoryId=5;
                SendData();

                break;
            case R.id.artOnly:
                ChangeButton(6);
                categoryId=6;
                SendData();

                break;
            case R.id.directSale:
                ChangeButton(7);
                categoryId=7;
                SendData();

                break;

            case R.id.listButton:
                ChangeButton(8);
                isListView=true;
                SendData();
                break;
            case R.id.mapButton:
                ChangeButton(9);
                isListView=false;
                SendData();


                break;

        }
    }

public void SendData() {
    Intent i = new Intent(getApplicationContext(), ProductListAdapter.class);
    i.putExtra("isListView", isListView);
    i.putExtra("isSearch", false);
    i.putExtra("catid", categoryId);
    //finish();
    startActivity(i);
    overridePendingTransition(0,0);
}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK) {

        }
        return false;
        // Disable back button..............
    }

public void ChangeButton(int id)
{
    switch (id)
    {
        case 1:
            title.setText("Craft Shows");
            craftButton.setBackgroundResource(R.drawable.craftshowtrue);
            artButton.setBackgroundResource(R.drawable.artshows);
            expoButton.setBackgroundResource(R.drawable.expofalse);
            fairsButton.setBackgroundResource(R.drawable.fairsandfesti);
            break;
        case 2:
            title.setText("Art Shows");
            craftButton.setBackgroundResource(R.drawable.craftshowfalse);
            artButton.setBackgroundResource(R.drawable.artshowstrue);
            expoButton.setBackgroundResource(R.drawable.expofalse);
            fairsButton.setBackgroundResource(R.drawable.fairsandfesti);
            break;
        case 3:
            title.setText("Expo");
            craftButton.setBackgroundResource(R.drawable.craftshowfalse);
            artButton.setBackgroundResource(R.drawable.artshows);
            expoButton.setBackgroundResource(R.drawable.expotrue);
            fairsButton.setBackgroundResource(R.drawable.fairsandfesti);
            break;
        case 4:
            title.setText("Fairs & Festivals");
            craftButton.setBackgroundResource(R.drawable.craftshowfalse);
            artButton.setBackgroundResource(R.drawable.artshows);
            expoButton.setBackgroundResource(R.drawable.expofalse);
            fairsButton.setBackgroundResource(R.drawable.fairsandfestitrue);
            break;
        case 5:
            title.setText("Craft Only");
            craftOnly.setBackgroundResource(R.drawable.craftonlytrue);
            artOnly.setBackgroundResource(R.drawable.artonlyfalse);
            directSale.setBackgroundResource(R.drawable.directsalefalse);
            break;
        case 6:
            title.setText("Art Only");
            craftOnly.setBackgroundResource(R.drawable.craftonlyfalse);
            artOnly.setBackgroundResource(R.drawable.artonlytrue);
            directSale.setBackgroundResource(R.drawable.directsalefalse);
            break;
        case 7:
            title.setText("Direct Sale");
            craftOnly.setBackgroundResource(R.drawable.craftonlyfalse);
            artOnly.setBackgroundResource(R.drawable.artonlyfalse);
            directSale.setBackgroundResource(R.drawable.directsaletrue);
            break;
        case 8:
            listButton.setBackgroundResource(R.drawable.lefttrue);
            mapButton.setBackgroundResource(R.drawable.rightfalse);
            break;
        case 9:
            listButton.setBackgroundResource(R.drawable.leftfalse);
            mapButton.setBackgroundResource(R.drawable.righttrue);
            break;


    }
}

}