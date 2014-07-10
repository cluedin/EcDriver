package com.ecdriver.v1;

public class OrderInfo {
	
	//private variables
    int id;
    String customer_name;
    String customer_number;
    String customer_add;
    String restaurant_name;
    String restaurant_num ;
    String restaurant_add ;
     
    // Empty constructor
    public OrderInfo(){
         
    }
    // constructor
    public OrderInfo(int id, String customer_name, String customer_number, String customer_add, String restaurant_name,String restaurant_num, String restaurant_add){
        this.id = id;
        this.customer_name = customer_name;
        this.customer_number = customer_number;
        this.customer_add = customer_add;
        this.restaurant_name = restaurant_name;
        this.restaurant_num = restaurant_num;
        this.restaurant_add = restaurant_add;
    }
     
    // constructor
    public OrderInfo(String customer_name, String customer_add, String restaurant_name){
        this.customer_name = customer_name;
        this.customer_add = customer_add;
        this.restaurant_name = restaurant_name;
    }
    // getting order ID
    public int getID(){
        return this.id;
    }
     
    // setting order id
    public void setID(int id){
        this.id = id;
    }
     
    // getting customer name
    public String getCusName(){
        return this.customer_name;
    }
     
    // setting customer name ( I don't see why we will need this)
    public void setCusName(String name){
        this.customer_name = name;
    }
     
    // getting customer phone number
    public String getCusNumber(){
        return this.customer_number;
    }
     
    // setting customer phone number ( I don't see why we will need this)
    public void setCusNumber(String phone_number){
        this.customer_number = phone_number;
    }
    
    // getting customer address
    public String getCusAdd(){
        return this.customer_add;
    }
     
    // setting customer address ( I don't see why we will need this)
    public void setCusAdd(String add){
        this.customer_add = add;
    }
    
    // getting restaurant name
    public String getResName(){
        return this.restaurant_name;
    }
     
    // setting restaurant name ( I don't see why we will need this)
    public void setResName(String res_name){
        this.restaurant_name = res_name;
    }
    
    // getting restaurant phone number
    public String getResNumber(){
        return this.restaurant_num;
    }
     
    // setting restaurant phone number ( I don't see why we will need this)
    public void setResNumber(String phone_number){
        this.restaurant_num = phone_number;
    }
    
    // getting restaurant address
    public String getResAdd(){
        return this.restaurant_add;
    }
     
    // setting restaurant address ( I don't see why we will need this)
    public void setResAdd(String add){
        this.restaurant_add = add;
    }
	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", customer_name=" + customer_name
				+ ", customer_number=" + customer_number + ", customer_add="
				+ customer_add + ", restaurant_name=" + restaurant_name
				+ ", restaurant_num=" + restaurant_num + ", restaurant_add="
				+ restaurant_add + "]";
	}
    
    

}
