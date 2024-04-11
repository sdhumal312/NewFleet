function checkForWarrantyStatus(ele, warrantyStatus){
	var batteryId = ele.id.split('_');
	if($('#'+ele.id).prop("checked") == true && warrantyStatus == 1)
		{
		$('#initial_note_'+batteryId[1]).show();
		var txt;
		var reason = prompt("Battery Is Under Warranty! Are You Sure To  Scrap ? Please Enter The Reason below");
		if(reason == null || reason== "")
			{
			 txt = "User cancelled the prompt.";
			   $('#'+ele.id).prop("checked", false);
			}
		else {
		    txt = reason;
		    $('#initial_note_'+batteryId[1]).val(reason);
		  }
			
		}
	else 
		{
		 $('#initial_note_'+batteryId[1]).hide()
		}
}
function saveScrapRemark(){
	
}