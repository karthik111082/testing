package com.hik.trendycraftshow.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DHARMA on 12/28/2015.
 */
public class ListConsts {

    public static ArrayList<String> image1=new ArrayList<>();
    public static ArrayList<String> image2=new ArrayList<>();
    public static  ArrayList<String> image3=new ArrayList<>();
    public static  ArrayList<String> image4=new ArrayList<>();
    public static  ArrayList<String> AddId=new ArrayList<>();
    public static  ArrayList<String> CatId=new ArrayList<>();
    public static  ArrayList<String> UserId=new ArrayList<>();
    public static  ArrayList<String> Title=new ArrayList<>();
    public static  ArrayList<String> Price=new ArrayList<>();
    public static  ArrayList<String> StartDate=new ArrayList<>();
    public static  ArrayList<String> EndDate=new ArrayList<>();
    public static  ArrayList<String> PostDate=new ArrayList<>();
    public static  ArrayList<String> Desc=new ArrayList<>();
    public static  ArrayList<String> State=new ArrayList<>();
    public static ArrayList<String> Street=new ArrayList<>();
    public static  ArrayList<String> Zip=new ArrayList<>();
    public static  ArrayList<String> Latitude=new ArrayList<>();
    public static  ArrayList<String> Langtitude=new ArrayList<>();
    public static  ArrayList<String> PaypalId=new ArrayList<>();
    public static  ArrayList<String> City=new ArrayList<>();
    public static  ArrayList<String> Address=new ArrayList<>();
    public static  ArrayList<String> Duration=new ArrayList<>();
    public static  ArrayList<Boolean> WishlistStatus=new ArrayList<Boolean>();
    public static  ArrayList<Boolean> OnlineStatus=new ArrayList<Boolean>();
    public static  ArrayList<String> QuickBloxIds=new ArrayList<String>();
    public static  ArrayList<Integer> Rating=new ArrayList<Integer>();


    public static  ArrayList<String> BusinessId=new ArrayList<>();
    public static  ArrayList<String> BusinessCompany=new ArrayList<>();
    public static  ArrayList<String> BusinessName=new ArrayList<>();
    public static  ArrayList<String> BusinessCell=new ArrayList<>();
    public static ArrayList<String> BusinessHomePhone=new ArrayList<>();
    public static  ArrayList<String> BusinessImage1=new ArrayList<>();
    public static  ArrayList<String> BusinessImage2=new ArrayList<>();
    public static  ArrayList<String> BusinessImage3=new ArrayList<>();
    public static  ArrayList<String> BusinessImage4=new ArrayList<>();
    public static  ArrayList<String> BusinessAddress=new ArrayList<>();
    public static  ArrayList<String> BusinessEmail=new ArrayList<>();
    public static  ArrayList<String> BusinessQuickId=new ArrayList<>();
    public static  ArrayList<Boolean> BusinessOnlineStatus=new ArrayList<Boolean>();
    public static  ArrayList<Boolean> BusinessWishList=new ArrayList<Boolean>();
    public static  ArrayList<Boolean> BusinessRate=new ArrayList<Boolean>();
    public static  ArrayList<String> BusinessDesc=new ArrayList<String>();






    public static Map<Integer,String> category=new HashMap<>();
    public static void addCategory()
    {
        category.put(1,"Craft Show");
        category.put(2,"Art Show");
        category.put(3,"Expo");
        category.put(4,"Fairs & Festival");
        category.put(5,"Craft Only");
        category.put(6,"Art Only");
        category.put(7,"Direct Sale");

    }

    public static void clearList()
    {
        ListConsts.PaypalId.clear();
        ListConsts.AddId.clear();
        ListConsts.CatId.clear();
        ListConsts.UserId.clear();
        ListConsts.Price.clear();
        ListConsts.StartDate.clear();
        ListConsts.EndDate.clear();
        ListConsts.PostDate.clear();
        ListConsts.Desc.clear();
        ListConsts.Street.clear();
        ListConsts. City.clear();
        ListConsts. State.clear();
        ListConsts.Zip.clear();
        ListConsts.Langtitude.clear();
        ListConsts.Latitude.clear();
        ListConsts.Title.clear();
        ListConsts.Address.clear();
        ListConsts.Duration.clear();
        ListConsts.WishlistStatus.clear();
        ListConsts.QuickBloxIds.clear();
        ListConsts.OnlineStatus.clear();
        ListConsts.image1.clear();
        ListConsts.image2.clear();
        ListConsts.image3.clear();
        ListConsts.image4.clear();
        ListConsts.Rating.clear();




    }

    public static void clearbusiness()
    {
        ListConsts.BusinessId.clear();
        ListConsts.BusinessCompany.clear();
        ListConsts.BusinessName.clear();
        ListConsts.BusinessCell.clear();
        ListConsts.BusinessHomePhone.clear();
        ListConsts.BusinessImage1.clear();
        ListConsts.BusinessImage2.clear();
        ListConsts.BusinessImage3.clear();
        ListConsts.BusinessImage4.clear();
        ListConsts.BusinessAddress.clear();
        ListConsts.BusinessEmail.clear();
        ListConsts.BusinessQuickId.clear();
        ListConsts.BusinessOnlineStatus.clear();
        ListConsts.BusinessWishList.clear();
        ListConsts.BusinessRate.clear();
        ListConsts.BusinessDesc.clear();
    }
}
