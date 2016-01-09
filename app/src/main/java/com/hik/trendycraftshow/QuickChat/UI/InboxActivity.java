package com.hik.trendycraftshow.QuickChat.UI;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.hik.trendycraftshow.QuickChat.ApplicationSingleton;
import com.hik.trendycraftshow.QuickChat.UI.adapters.DialogsAdapter;
import com.hik.trendycraftshow.QuickChat.core.ChatService;
import com.hik.trendycraftshow.QuickChat.pushnotifications.Consts;
import com.hik.trendycraftshow.QuickChat.pushnotifications.PlayServicesHelper;
import com.hik.trendycraftshow.R;
import com.hik.trendycraftshow.Utils.IsTablet;
import com.quickblox.chat.model.QBDialog;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends BaseActivity {
    private static final String TAG = InboxActivity.class.getSimpleName();

    private ListView dialogsListView;
    private ProgressBar progressBar;
    private List<QBUser> selected = new ArrayList<QBUser>();

    private PlayServicesHelper playServicesHelper;
IsTablet tablet;
    boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setText("Inbox");
        isTablet = tablet.isTablet(getApplicationContext());
        if (isTablet) {
            getLayoutInflater().inflate(R.layout.activity_inbox, container);
        } else {
            getLayoutInflater().inflate(R.layout.activity_inbox_mob, container);
        }
        QBSettings.getInstance().fastConfigInit(ApplicationSingleton.APP_ID, ApplicationSingleton.AUTH_KEY, ApplicationSingleton.AUTH_SECRET);
        playServicesHelper = new PlayServicesHelper(this);

        dialogsListView = (ListView) findViewById(R.id.roomsList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //
        LocalBroadcastManager.getInstance(this).registerReceiver(mPushReceiver,
                new IntentFilter(Consts.NEW_PUSH_EVENT));

        // Get dialogs if the session is active
        //
        if(isSessionActive()){
            getDialogs();
        }
    }

    private void getDialogs(){
        progressBar.setVisibility(View.VISIBLE);

        // Get dialogs
        //

        ChatService.getInstance().getDialogs(new QBEntityCallbackImpl() {
            @Override
            public void onSuccess(Object object, Bundle bundle) {
                progressBar.setVisibility(View.GONE);

                final ArrayList<QBDialog> dialogs = (ArrayList<QBDialog>)object;

                // build list view
                //
                buildListView(dialogs);
            }

            @Override
            public void onError(List errors) {
                progressBar.setVisibility(View.GONE);

                AlertDialog.Builder dialog = new AlertDialog.Builder(InboxActivity.this);
                dialog.setMessage("get dialogs errors: " + errors).create().show();
            }
        });
    }

    public QBDialog compare(int userId,List<QBDialog> dialogs){
        for(QBDialog dialog:dialogs){
            if(dialog.getUserId().equals(userId)){
                return dialog;
            }
        }
        return null;
    }

    void buildListView(final List<QBDialog> dialogs){
        final DialogsAdapter adapter = new DialogsAdapter(dialogs, InboxActivity.this);
        dialogsListView.setAdapter(adapter);


        // choose dialog
        //
        dialogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QBDialog selectedDialog = (QBDialog) adapter.getItem(position);


                Bundle bundle = new Bundle();
                bundle.putSerializable(ChatActivity.EXTRA_DIALOG, selectedDialog);

                // Open chat activity
                //
                Log.d("qbdia", selectedDialog.toString());
                Log.d("bun",bundle.toString());
                ChatActivity.start(InboxActivity.this, bundle);

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playServicesHelper.checkPlayServices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    // Our handler for received Intents.
    //
    private BroadcastReceiver mPushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get extra data included in the Intent
            String message = intent.getStringExtra(Consts.EXTRA_MESSAGE);

            Log.i(TAG, "Receiving event " + Consts.NEW_PUSH_EVENT + " with data: " + message);
        }
    };


    //
    // ApplicationSessionStateCallback
    //

    @Override
    public void onStartSessionRecreation() {

    }

    @Override
    public void onFinishSessionRecreation(final boolean success) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    getDialogs();
                }
            }
        });
    }
}
