package com.ecdriver.v1;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "orderManager";
 
    // Order information table name
    static final String ORDER_INFO = "orderInfo";
    public SQLiteDatabase DB;
    // Order information fields
    private static final String KEY_ID = "id";
    private static final String CUS_NAME = "cus_name";
    private static final String CUS_PH_NO = "cus_phone_number";
    private static final String CUS_ADD= "cus_add";
    private static final String RES_NAME = "res_name";
    private static final String RES_PH_NO = "res_phone_numbere";
    private static final String RES_ADD = "res_addr";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDER_TABLE = "CREATE TABLE " + ORDER_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + CUS_NAME + " TEXT,"
                + CUS_PH_NO + " TEXT," + CUS_ADD + "TEXT," + RES_NAME + "TEXT," + RES_PH_NO + "TEXT," 
                + RES_ADD + "TEXT" + ")";
        db.execSQL(CREATE_ORDER_TABLE);
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	       // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_INFO);
 
        // Create tables again
        onCreate(db);
	}
	
	
	// Adding new order
	public  void addOrder(OrderInfo order) {
		
	    SQLiteDatabase db = this.getWritableDatabase();    
	    ContentValues values = new ContentValues();
	    values.put(CUS_NAME, order.getCusName()); // Customers Name
	    values.put(CUS_PH_NO, order.getCusNumber()); // Contact Phone Number	
	    values.put(CUS_ADD, order.getCusAdd()); // Contact Name
	    values.put(RES_NAME, order.getResName()); // Contact Phone Number
	    values.put(RES_PH_NO, order.getResNumber()); // Contact Name
	    values.put(RES_ADD, order.getResAdd()); // Contact Phone Number
	    // Inserting Row
	    db.insert(ORDER_INFO, null, values);
	    db.close(); // Closing database connection		
	}
	 
	// Getting single order
	public OrderInfo getOrder(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(ORDER_INFO, new String[] { KEY_ID,
	            CUS_NAME, CUS_PH_NO, CUS_ADD,RES_NAME,RES_PH_NO,RES_ADD}, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    OrderInfo order = new OrderInfo(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
	    // return contact
	    return order;
		
	}
	 
	// Getting All Orders
	public List<OrderInfo> getAllOrders() {
		List<OrderInfo> OrderList = new ArrayList<OrderInfo>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + ORDER_INFO;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	OrderInfo order = new OrderInfo();
	            order.setID(Integer.parseInt(cursor.getString(0)));
	            order.setCusName(cursor.getString(1));
	            order.setCusNumber(cursor.getString(2));
	            order.setCusAdd(cursor.getString(3));
	            order.setResName(cursor.getString(4));
	            order.setResNumber(cursor.getString(5));
	            order.setResAdd(cursor.getString(6));
	            // Adding contact to list
	            OrderList.add(order);
	        } while (cursor.moveToNext());
	    }	 
	    // return contact list
	    return OrderList;	
	}
	 
	// Getting contacts Count
	public int getOrderCount() {
        String countQuery = "SELECT  * FROM " + ORDER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close(); 
        // return count
        return cursor.getCount();
		
	}
	// Updating single contact
	public int updateOrder(OrderInfo contact) {
		return 0;
		
	}
	 
	// Deleting single contact
	public void deleteOrder(OrderInfo order) {
		
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(ORDER_INFO, KEY_ID + " = ?",
	            new String[] { String.valueOf(order.getID()) });
	    db.close();
	}
	
	

}
