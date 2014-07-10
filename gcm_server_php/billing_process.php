<?php
	include("include/dbcon.php");
	include("functionPHP.php");
	
	
	if(isset($_POST['submit']) and $_POST['submit'] != '')
	{
			
		date_default_timezone_set('America/New_York');
		
		$next_day_date = $_POST['next_day_date'];
		$_SESSION['next_day_date'] = $next_day_date;
		$day_date = strtotime($_SESSION['next_day_date']);
		
		$delivery_time = $_POST['delivery_time'];
		$day_timeing = strtotime($delivery_time);
		$_SESSION['delivery_time'] = $delivery_time;
		$day_time = strtotime($_SESSION['delivery_time']);
		
		$rest_id = $_SESSION['restaurant_id'];
		$selRestaurants = "SELECT * FROM `restaurants` WHERE status =1 and restaurant_id = '$rest_id'";
		$recRestaurants = mysql_query($selRestaurants) or die(mysql_error());
		while($resRestaurants = mysql_fetch_array($recRestaurants)){
		$currentDate = strtotime(date('m/d/Y', time()));
		$currentDate_show = strtolower(date('m/d/Y'));
		
		$currentDay = strtolower(date("l"));
		
		if($currentDay == 'monday')
		{
		$weekId = 1;
		}else if($currentDay == 'tuesday'){
		$weekId = 2;
		}else if($currentDay == 'wednesday'){
		$weekId = 3;
		}else if($currentDay == 'thursday'){
		$weekId = 4;
		}else if($currentDay == 'friday'){
		$weekId = 5;
		}else if($currentDay == 'saturday'){
		$weekId = 6;
		}else if($currentDay == 'sunday'){
		$weekId = 7;
		}
		
		$selRest_work = "select * from restaurants_working_hours where restaurant_id = '$restaurant_id' AND weekdays_id = '$weekId'";
		$recRest_work = mysql_query($selRest_work) or die(mysql_error());
		while($resRest_work = mysql_fetch_array($recRest_work)){
		$open_time = $resRest_work['opening_time'];
		$close_time = $resRest_work['closeing_time'];
		$open_timeing = strtotime($resRest_work['opening_time']);
		$close_timeing = strtotime($resRest_work['closeing_time']);
		$todayTime = strtotime(date("h:i A"));
		
		if($courentDate == $day_date){
			
			if($day_timeing >= $open_timeing && $day_timeing <= $close_timeing){ 
			
			$_SESSION['next_day_date'] = $next_day_date;
			$_SESSION['delivery_time'] = $delivery_time;
			
		}else{
			
				$_SESSION['time'] = "Select Time between ".$open_time." And ".$close_time;
				echo "<script>window.location='billing.php'</script >";
				exit();
			}
		}else{
				date_default_timezone_set("Asia/Calcutta");
				$NextDay = strtolower(date("l","-1 day"));
				$rest_id = $_SESSION['restaurant_id'];
				$day_timeing = strtotime($delivery_time);
				$selrest_table = "SELECT * FROM `restaurants` WHERE restaurant_id = '$rest_id'";
				$recrest_table = mysql_query($selrest_table) or die(mysql_error());
				while($resrest_table = mysql_fetch_array($recrest_table)){
					
				
					if($NextDay == 'monday')
					{
					$weekId = 1;
					}else if($NextDay == 'tuesday'){
					$weekId = 2;
					}else if($NextDay == 'wednesday'){
					$weekId = 3;
					}else if($NextDay == 'thursday'){
					$weekId = 4;
					}else if($NextDay == 'friday'){
					$weekId = 5;
					}else if($NextDay == 'saturday'){
					$weekId = 6;
					}else if($NextDay == 'sunday'){
					$weekId = 7;
					}
					
					$selRest_work = "select * from restaurants_working_hours where restaurant_id = '$rest_id' AND weekdays_id = '$weekId'";
					$recRest_work = mysql_query($selRest_work) or die(mysql_error());
					while($resRest_work = mysql_fetch_array($recRest_work)){
					$open_time1ing = $resRest_work['opening_time'];
					$open_time2ing = strtotime("$open_time1ing + 30 mins");
					
					$close_time1ing = $resRest_work['closeing_time'];
					$close_time2ing = strtotime("$close_time1ing - 30 mins");
					
					//$time2 = date("h:i A",1396431000);
					$todayTimeing = strtotime(date("h:i A"));
					
					
						if($day_timeing >= $open_time2ing && $day_timeing <= $close_time2ing){ 
							
							$_SESSION['next_day_date'] = $next_day_date;
							$_SESSION['delivery_time'] = $delivery_time;
						}else{
								
								$_SESSION['time'] = "Select Time between ".$open_time1ing." And ".$close_time1ing;
								echo "<script>window.location='billing.php'</script >";	
								exit();
						}
				
			}
				
		}
	}
		}
	}
			
			$user_id = $_SESSION['UID'];
			
			$bill_full_name = addslashes($_POST['bill_full_name']); 
			$bill_country = addslashes($_POST['bill_country']);
			$bill_country_name = getCountryName($bill_country);
			
			$bill_state = addslashes($_POST['bill_state']);
			$bill_state_name = getStateName($bill_state);
			
			$bill_city = addslashes($_POST['bill_city']);
			$bill_city_name = getCityName($bill_city);
			
			$bill_pin_code = addslashes($_POST['bill_pin_code']);
			$bill_address = addslashes($_POST['bill_address']);
			
			$default_delivery_address = $_POST['default_delivery_address'];
			if($default_delivery_address == 1 ){
				$selBillingAddress = "SELECT customer_id FROM `customer_billing_address` WHERE `customer_id` = $user_id";
				$resBillingAddress = mysql_query($selBillingAddress) or die(mysql_error());
				$nrBillingAddress = mysql_num_rows($resBillingAddress);
				if($nrBillingAddress > 0)
				{
					 $sql = "UPDATE `customer_billing_address` SET full_name = '$bill_full_name', city_id = '$bill_city', city_name = '$bill_city_name', state_id = '$bill_state', state_name = '$bill_state_name', country_id = '$bill_country', country_name = '$bill_country_name', postcode = '$bill_pin_code', `address` = '$bill_address' WHERE `customer_id` = '$user_id'";
					mysql_query($sql) or die(mysql_error());	
					
				}else{
					
		
					 $sql = "INSERT INTO  `customer_billing_address` VALUES ('','$user_id','$bill_full_name','$bill_city','$bill_city_name','$bill_state','$bill_state_name','$bill_country','$bill_country_name','$bill_pin_code','$bill_address')";
					mysql_query($sql) or die(mysql_error());
					
				}
			}
			
			$ship_full_name = addslashes($_POST['ship_full_name']); 
			$ship_country = addslashes($_POST['ship_country']);
			$ship_country_name = getCountryName($ship_country);
			
			$ship_state = addslashes($_POST['ship_state']);
			$ship_state_name = getStateName($ship_state);
			
			$ship_city = addslashes($_POST['ship_city']);
			$ship_city_name = getCityName($ship_city);
			
			$ship_pin_code = addslashes($_POST['ship_pin_code']);
			$ship_address = addslashes($_POST['ship_address']);
			$default_billing_address = $_POST['default_billing_address'];
			if($default_billing_address == 1){
					$selShippingAddress = "SELECT * FROM `customer_shipping_address` WHERE `customer_id` = $user_id";
					$resShippingAddress = mysql_query($selShippingAddress) or die(mysql_error());
					$nrShippingAddress = mysql_num_rows($resShippingAddress);
					if($nrShippingAddress > 0)
					{
						 $sql = "UPDATE `customer_shipping_address` SET full_name = '$ship_full_name', city_id = '$ship_city', city_name = '$ship_city_name', state_id = '$ship_state', state_name = '$ship_state_name', country_id = '$ship_country', country_name = '$ship_country_name', postcode = '$ship_pin_code', `address` = '$ship_address' WHERE `customer_id` = '$user_id'";
						mysql_query($sql) or die(mysql_error());	
						
					}else{
						 $sql = "INSERT INTO  `customer_shipping_address` VALUES ('','$user_id','$ship_full_name','$ship_city','$ship_city_name','$ship_state','$ship_state_name','$ship_country','$ship_country_name','$ship_pin_code','$ship_address')";
						mysql_query($sql) or die(mysql_error());
						
					}
			}
			
				$delivery_date1 = $_SESSION['next_day_date'];
				$delivery_time1 = $_SESSION['delivery_time'];
				$subtotal = addslashes($_POST['subTotal']);
				$tax_percent = addslashes($_POST['tax']);
				$tax_amount = addslashes($_POST['totalTax']);
				$packaging_charge = addslashes($_POST['packaging']);
				$delivery_charge = addslashes($_POST['delivery']);
				$net_amount = addslashes($_POST['fulltotal']);
				$discount = $_SESSION['$discount'];
				$totaldis = addslashes($_POST['totaldis']);
				$restaurant_id = addslashes($_POST['restaurant_id']);
				
				$payment_mode = addslashes($_POST['payment_mode']);
				
				if(isset($user_id) && $user_id != ''){
						
					$cusEmail = addslashes($_POST['cusEmail']);
					$cusMobile = addslashes($_POST['cusMobile']);
				}else{
						
					$cusEmail = addslashes($_POST['guest_email']);
					$cusMobile = addslashes($_POST['guest_mobile']);
					$Custmer_name = $ship_full_name;
				
				}
				
				
				$Rest_name = getRestaurantName($restaurant_id);
				
				if(isset($user_id) && $user_id != ''){
				$Custmer_name = getUserName($user_id);
				}
				
				$payment_status = paymentStatus($payment_mode);
				$order_status = orderStatus($payment_mode);
				//echo $usingTime;
				 $sql = "INSERT INTO  `orders` VALUES ('','$restaurant_id','$Rest_name','$user_id','$Custmer_name','$cusEmail','$cusMobile','$subtotal','$tax_percent','$tax_amount','$packaging_charge','$delivery_charge','$discount','$totaldis','$net_amount','$payment_mode',now(),now(),now(),'$delivery_date1','$delivery_time1','$payment_status','$order_status','','','')";
				mysql_query($sql) or die(mysql_error());
				$id = mysql_insert_id();
				$_SESSION['orders_confirm'] = $id;
				
				sendAdminMail($cusEmail,$Rest_name,$Custmer_name,$subTotal,$tax_amount,$payment_mode,$ship_city_name,$ship_pin_code,$ship_address,$bill_city_name,$bill_pin_code,$bill_address, $packaging_charge,$delivery_charge,$totaldis,$net_amount);
				
				$sql = "INSERT INTO  `order_shipping_address` VALUES ('','$id','$ship_full_name','$ship_city','$ship_city_name','$ship_state','$ship_state_name','$ship_country','$ship_country_name','$ship_pin_code','$ship_address')";
				mysql_query($sql) or die(mysql_error());
				
				$sql = "INSERT INTO  `order_billing_address` VALUES ('','$id','$bill_full_name','$bill_city','$bill_city_name','$bill_state','$bill_state_name','$bill_country','$bill_country_name','$bill_pin_code','$bill_address')";
				mysql_query($sql) or die(mysql_error());
				
				
			  if(isset($_SESSION['feilds']) && count($_SESSION['feilds']) > 0){ 
			  
				$subTotal = 0;
				$total = 0;
				for($i = 0;$i<count($_SESSION['feilds']);$i++)
				{ 
				
					if($_SESSION['feilds'][$i]['qty'] > 0)
					{
						$subTotal = 0;
						$total = 0;
						$itemid = $_SESSION['feilds'][$i]['menu_id'];
						$itemname = $_SESSION['feilds'][$i]['item'];
						$itemqty = $_SESSION['feilds'][$i]['qty'];
						$itemprice = $_SESSION['feilds'][$i]['price'];
						
						$selMenu = "SELECT * FROM `menu` WHERE menu_id = '$itemid' and status = 1 and parent_id != 0";
						$recMenu = mysql_query($selMenu) or die(mysql_error());
						$resMenu = mysql_fetch_array($recMenu);
						$memu_prise = $resMenu['prise'];
						
						$price = $itemprice * $itemqty;
						$subTotal = $subTotal + $price;
						
						$ins = "INSERT INTO  `order_detail` VALUES ('','$id','$restaurant_id','$itemid','$itemname','$memu_prise','$itemqty','$subTotal')";
						mysql_query($ins) or die(mysql_error());
					}
				}
			  }
			echo "<script>window.location='confirm_order_process.php'</script >";			
}
			
			
			
	
	
?>