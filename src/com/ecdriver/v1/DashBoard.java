package com.ecdriver.v1;

import java.util.ArrayList;



import java.util.List;

import com.google.android.gcm.GCMRegistrar;
import com.ecdriver.v1.DatabaseHandler;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DashBoard extends ListActivity {
	
	private ArrayList<String> results = new ArrayList<String>();
	private String tableName = "orderInfo";
	private SQLiteDatabase newDB;  
	
	
	private static final int DATABASE_VERSION = 1;
	 
    // Database Name
    private static final String DATABASE_NAME = "orderManager";
	
	// label to display gcm messages
    TextView lblMessage;
     
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;
     
    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.activity_dash_board);
			
		DatabaseHandler db = new DatabaseHandler (this);
		
		Log.d("Insert: ", "Inserting ..");
		OrderInfo myOrder = new OrderInfo(1, "Valentine", "757", "my house", "Good burger","434","home of the good burger");
		db.addOrder(myOrder);		
            
        Log.d("Reading: ", "Reading all open order.."); 
		Log.d("Name", myOrder.customer_name);

        List<OrderInfo> orders = db.getAllOrders();   
        
        for (OrderInfo cn : orders) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getCusName() + " ,Phone: " + cn.getCusNumber();
                // 
        Log.d("Name: ", log);
    }

	
	
	

	
	
		  try { 
			//DatabaseHandler dbHelper = new DatabaseHandler(this.getApplicationContext());
			newDB = db.getWritableDatabase();
			String selectQuery = "SELECT  * FROM  orderInfo";
			Cursor c = newDB.rawQuery(selectQuery, null);
			
	    	if (c != null && newDB!=null) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String cusName = c.getString(c.getColumnIndex("CUS_NAME"));
	    				String cusNum = c.getString(c.getColumnIndex("CUS_PH_NO"));
	    				String cusAdd = c.getString(c.getColumnIndex("CUS_ADD"));
	    				String resName = c.getString(c.getColumnIndex("RES_NAME"));
	    				String resNum = c.getString(c.getColumnIndex("RES_PH_NO"));
	    				String resAdd = c.getString(c.getColumnIndex("RES_ADD"));
	    				results.add("Customer Name: " + cusName + "\n Customer Number: " + cusNum + "\n Customer Address: "
	    						   + "\n Restuarant Name: " + resName + "\n Restaurant Number: " + resNum
	    						   + cusAdd + "\n Restaurant Address: " + resAdd);

	    			}while (c.moveToNext());
	    		} 
	    		
	    		TextView tView = new TextView(this);
	            tView.setText("Open Order");
	            getListView().addHeaderView(tView);
	            
	            setListAdapter(new ArrayAdapter<String>(this,
	                    android.R.layout.simple_list_item_1, results));
	            getListView().setTextFilterEnabled(true);
	    		
	    	}
	    	
	    	
		} catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
        	if (newDB != null) 
        		newDB.execSQL("DELETE FROM " + tableName);
        		newDB.close();
        }
	}
	
	
	/**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
             
            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */
             
            // Showing received message
            lblMessage.append(newMessage + "\n");           
            Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
             
            // Releasing wake lock
            WakeLocker.release();
        }
    };
     
    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }


}
