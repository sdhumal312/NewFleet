 $(document).ready(function() {
	 syncLHPVDataFromIVCARGO();
 });
 
 function syncLHPVDataFromIVCARGO(){
	 
	 	showLayer();
 
		var jsonObject					= new Object();
		jsonObject["vid"] 				=  $('#vid').val();
		jsonObject["tripSheetId"] 		=  $('#tripSheetId').val();
		
		$.ajax({
	             url: "syncLhpvDetailsToAddInTripSheet",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	setLhpvData(data);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	 
 }
 
 function setLhpvData(data){
	 if(data.lhpvDetailsList != undefined && data.lhpvDetailsList != null && data.lhpvDetailsList.length > 0){
		 $('#lhpvDiv').show();
		$("#lhpvBody").empty();
		 var lhpvDetailsList = data.lhpvDetailsList;
		var srNo = 1;
		 for(var i = 0; i < lhpvDetailsList.length; i++ ) {
				var tr = '<tr>'
					+'<td class="fit" >'+srNo+'</td>'
					+'<td class="fit" ><input name="select" type="checkBox" id="lhpv_'+lhpvDetailsList[i].lHPVDetailsId+'" /></td>'
					+'<td><a href "#">'+lhpvDetailsList[i].lHPVNumber+'</a></td>'
					+'<td>'+lhpvDetailsList[i].lhpvDateTime+'</td>'
					+'<td>'+lhpvDetailsList[i].advanceAmount+'</td>'
					+'<td>'+lhpvDetailsList[i].lorryHire+'</td>'
					/*+'<td><a href="addTripSheetEntries.in?lHPVDetailsId='+lhpvDetailsList[i].lHPVDetailsId+'">Create TripSheet</a></td>'*/
					+'</tr>';
				$('#lhpvBody').append(tr);
				srNo++;
			}
			
		 if(data.tripSheet != undefined){
			 $('#tripSheetNumber').html('<a target="_blank" href="showTripSheet?tripSheetID='+data.tripSheet.tripSheetID+'">'+data.tripSheet.tripSheetNumber+'</a>');
			 $('#vehicle').html('<a target="_blank" href="showVehicle.in?vid='+data.tripSheet.vid+'">'+data.tripSheet.vehicle_registration+'</a>');
			 $('#tripRoute').html(data.tripSheet.routeName);
			 $('#tripBookref').val(data.tripSheet.tripBookref);
			 
		 }
		 
	 }else{
		 	$('#lhpvDiv').hide();
			$("#lhpvBody").empty();
		 showMessage('info', 'No Record Found !');
	 }
 }
 
 function addLhpvDataToTripSheet(){
	 var anyChecked = false;
	 var lhpvIds	= '';
	 $('input[name*=select]' ).each(function(){
			var vehicleVal = this.id;
			if($('#'+vehicleVal).prop("checked")){
				anyChecked = true;
				var lhpvId = vehicleVal.split('_')[1];
				lhpvIds += lhpvId+',';
			}
	});
	 if(!anyChecked){
		 showMessage('info', 'Please select atleast one Lhpv !');
		 return false;
	 }
	 	showLayer();
	 
	 	lhpvIds = lhpvIds.slice(0,-1);
		var jsonObject					= new Object();
		jsonObject["vid"] 				=  $('#vid').val();
		jsonObject["tripSheetId"] 		=  $('#tripSheetId').val();
		jsonObject["lhpvIds"] 			=  lhpvIds;
		jsonObject["tripBookref"] 		=  $('#tripBookref').val();
		
		
		$.ajax({
	             url: "addLhpvDetailsToTripSheet",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	showMessage('success', 'Lhpv Details Added To TripSheet !')
	            	window.location.replace("showTripSheet.in?tripSheetID="+$('#tripSheetId').val());
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	 
	// 
 }
 
 function toHex(str){
	  try{
	   var  hex = unescape(encodeURIComponent(str))
	    .split('').map(function(v){
	      return v.charCodeAt(0).toString(16)
	    }).join('')
	  }
	  catch(e){
	    hex = str
	    console.log('invalid text input: ' + str)
	  }
	  return hex
	}