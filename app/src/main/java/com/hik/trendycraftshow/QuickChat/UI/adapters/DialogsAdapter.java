package com.hik.trendycraftshow.QuickChat.UI.adapters;

/**
 * Created by igorkhomenko on 9/12/14.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hik.trendycraftshow.JSON.Api;
import com.hik.trendycraftshow.JSON.WebServiceRequest;
import com.hik.trendycraftshow.PaymentMethod;
import com.hik.trendycraftshow.QuickChat.core.ChatService;
import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.Consts;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.content.result.QBFileResult;
import com.quickblox.core.QBCallbackImpl;
import com.quickblox.core.result.Result;
import com.quickblox.users.model.QBUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DialogsAdapter extends BaseAdapter {
    private List<QBDialog> dataSource;

    private LayoutInflater inflater;
    Context context;
    Bitmap bmp=null;
    String fileURL="";
    private WebServiceRequest.HttpURLCONNECTION getPhoto;
    Api api;

    public DialogsAdapter(List<QBDialog> dataSource, Activity ctx) {
        this.dataSource = dataSource;
        this.inflater = LayoutInflater.from(ctx);
        this.context=ctx;


    }

    public List<QBDialog> getDataSource() {
        return dataSource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // initIfNeed view
        //
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_room, null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.roomName);
            holder.lastMessage = (TextView)convertView.findViewById(R.id.lastMessage);
            holder.groupType = (TextView)convertView.findViewById(R.id.textViewGroupType);
            holder.userImage = (CircularImageView)convertView.findViewById(R.id.roomImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set data
        //
        QBDialog dialog = dataSource.get(position);
        Log.d("total data",""+dataSource.size());
        if(dialog.getType().equals(QBDialogType.GROUP)){
            holder.name.setText(dialog.getName());
        }else{
            // get opponent name for private dialog
            //
            Integer opponentID = ChatService.getInstance().getOpponentIDForPrivateDialog(dialog);
            QBUser user = ChatService.getInstance().getDialogsUsers().get(opponentID);

            if(user != null){
                String url=Api.QUICKPHOTOURL+user.getId();
                Log.d("QuickPhotoUrl",url);
                holder.name.setText(user.getFullName());
                if(!url.trim().isEmpty())
                {
                    Picasso.with(context)
                            .load(url)
                            .placeholder(R.drawable.avator) // optional
                            .error(R.drawable.avator)         // optional
                            .into(holder.userImage);
                }
                else
                {
                    holder.userImage.setBackgroundResource(R.drawable.avator);
                }
            }
        }
        if (dialog.getLastMessage() != null ){
            holder.lastMessage.setText(dialog.getLastMessage());
        }
        holder.groupType.setText(dialog.getType().toString());
        Log.d("data",dataSource.toString());
        return convertView;

    }

    private static class ViewHolder{
        TextView name;
        TextView lastMessage;
        TextView groupType;
        CircularImageView userImage;
    }



}
