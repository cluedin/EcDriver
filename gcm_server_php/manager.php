 <?php 
 
 // Connects to your Database 
 include_once './GCM.php';
 $gcm = new GCM();
 
 require_once 'config.php';
 
 
 mysql_connect(DB_HOST, DB_USER, DB_PASSWORD) or die(mysql_error()); 
 mysql_select_db("gcm") or die(mysql_error()); 
 
     $customer_name = $_POST["customer_name"];
    $restaurant = $_POST["restaurant"];
	$order_status = $_POST["order_status"];
 
  $result1 = "INSERT INTO `new_order` VALUES('','$order_status', '$restaurant', '$customer_name')";
 mysql_query($result1) or die(mysql_error());

 $data = mysql_query("SELECT restaurant_name FROM orders WHERE order_status=7") 
 or die(mysql_error()); 
 
 $regID = mysql_query("SELECT gcm_regid FROM gcm_users WHERE name='Tino'");
 if(is_resource($data) and mysql_num_rows($data)>0){
    $message = mysql_fetch_array($data);
    echo $message["restaurant_name"];
    }
 if(is_resource($regID) and mysql_num_rows($regID)>0){
    $dest = mysql_fetch_array($regID);
    echo $dest["gcm_regid"];
    }
	$final_dest_ids = array($dest["gcm_regid"]);
 //$new_check = mysql_query("SELECT order_status FROM new_order WHERE order_status=7")
 if($new_check == 7) {
 $result = $gcm->send_notification($final_dest_ids, $message);
 $sql = "UPDATE `new_order` SET order_status = '11' WHERE order_status=7";
		mysql_query($sql) or die(mysql_error());
 
 echo $result
 }
 ?> 