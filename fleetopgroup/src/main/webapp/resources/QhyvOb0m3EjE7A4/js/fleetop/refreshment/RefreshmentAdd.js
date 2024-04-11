$(document).ready(function() {
	if(Number($('#tripSheetId').val()) > 0){
		getRefreshmentListDetails();
		$('#FuelSelectVehicle' ).prop("readonly", true);
		$('#tripSheetDetails').show();
		$('#tripNumberCol').show();
		$('#entry').hide();
		$('#TripSheetRow').hide();
		
	}else{
		$('#FuelSelectVehicle' ).prop("readonly", false);
		$('#DriverFuel' ).prop("readonly", false);
		$('#FuelRouteList' ).prop("readonly", false);
		$('#tripSheetDetails').hide();
		$('#tripNumberCol').hide();
		$('#entry').show();
		$('#TripSheetRow').show();
		
	}
	
	$("#FuelSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }), $("#DriverFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListFuel.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#FuelRouteList").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTripRouteSerachList.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
            	console.log("e",e)
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    }), $("#locationId").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getSearchPartLocations.in",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
            return {
                term: a
            }
        },
        results: function(a) {
            return {
                results: $.map(a, function(a) {
                    return {
                        text: a.partlocation_name,
                        id: a.partlocation_id
                    }
                })
            }
        }
    }
}), $("#partid").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getPartForRefreshment.in",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
        	if(Number($("#locationId").val()) <= 0){
        		showMessage('info', 'Please select Location first !');
        		return false;
        	}
            return {
                term: a,
                locationId: $("#locationId").val()
            }
        },
        results: function(a) {
            return {
                results: $.map(a, function(a) {
                    return {
                        text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
                        slug: a.slug,
                        id: a.inventory_location_id,
                        location_quantity:a.location_quantity
                    
                    }
                })
            }
        }
    }
}),$("#partid").change(function(){
	var data = $("#partid").select2('data');
	
	if(data != undefined && data != null){
		$('#locationQuantity').val(data.location_quantity);
	}else{
		$('#locationQuantity').val(0);
	}
	
})
setTripSheetData();
})


function saveRefreshmentEntry(){
	
	if(!validateRefreshmentEntry()){
		return false;
	}
	
	var jsonObject			= new Object();
	
	jsonObject["vid"] 	  				=  $('#FuelSelectVehicle').val();
	if(Number($('#tripSheetId').val()) > 0){
		jsonObject["tripsheetId"] 	  		=  $('#tripSheetId').val();
	}else{
		jsonObject["tripsheetId"] 	  		=  $('#tripSheetNumberDrop').val();
	}
	
	jsonObject["refreshmentDate"] 	  	=  $('#refreshmentDate').val();
	jsonObject["locationId"] 	  		=  $('#locationId').val();
	jsonObject["partid"] 	  			=  $('#partid').val();
	jsonObject["quantity"] 	  			=  $('#quantity').val();
	jsonObject["driverId"] 	  			=  $('#DriverFuel').val();
	jsonObject["routeId"] 	  			=  $('#FuelRouteList').val();
	jsonObject["comment"] 	  			=  $('#comment').val();
	
	
	showLayer();
	$.ajax({
             url: "saveRefreshmentEntriesDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if(data.noStock){
            		 showMessage('errors', 'No Stock Available !')
            	 }if(data.sequenceCounter){
            		 showMessage('errors', 'Sequence Counter Not Found Please Contact To System Administrator !')
            	 }else{
            		 if(Number($('#tripSheetId').val()) > 0){
            			 window.location.replace("refreshmentEntry.in?tripSheetID="+$('#tripSheetId').val()+"");
            			 showMessage('success', 'Data Saved Successfully!')
            		 }else{
            			 window.location.replace("RefreshmentEntriesList.in?tripSheetID="+$('#tripSheetId').val()+"");
            			 showMessage('success', 'Data Saved Successfully!')
            		 }
            	 }
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}


function validateRefreshmentEntry(){
	valadateQuantity();
	if(Number($('#FuelSelectVehicle').val()) <= 0){
		showMessage('info', 'Please Select Vehicle !');
		$("#FuelSelectVehicle").select2('focus');
		return false;
	}
	var tripsheetId = 0;
	if(Number($('#tripSheetId').val()) > 0){
		tripsheetId	  		=  $('#tripSheetId').val();
	}else{
		tripsheetId 	  	=  $('#tripSheetNumberDrop').val();
	}
	
	if(Number(tripsheetId) <= 0){
		showMessage('info', 'Please Select TripSheet Number !');
		$("#tripSheetNumberDrop").select2('focus');
		return false;
	}
	
	if($('#refreshmentDate').val() == null || $('#refreshmentDate').val().trim() == '' ){
		showMessage('info', 'Please Select Date !');
		$("#refreshmentDate").focus();
		return false;
	}
	
	if(Number($('#locationId').val()) <= 0){
		showMessage('info', 'Please Select Location Details !');
		$("#locationId").select2('focus');
		return false;
	}
	
	return true;
}

function isNumberKey(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function isDecimalNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function isNumberKeyWithDecimal(evt,id){ 
	try{

        var charCode = (evt.which) ? evt.which : event.keyCode;
        var txt=document.getElementById(id).value;
  
        if(charCode==46){
         
            if(!(txt.indexOf(".") > -1)){
	
                return true;
            }
        }
        if (charCode > 31 && (charCode < 48 || charCode > 57) )
            return false;

        if(txt.indexOf(".") > -1 && (txt.split('.')[1].length > 1)){
            event.preventDefault();
        }
        return true;
	}catch(w){
		alert(w);
	}
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}


function valadateQuantity(){
	
	if(Number($('#partid').val()) <= 0){
		$('#quantity').val('');
		$("#partid").select2('focus');
		showMessage('info', 'Please select Part Name !');
		return false;
	}
	
	if(Number($('#locationQuantity').val()) < Number($('#quantity').val())){
		$('#quantity').val('');
		$('#quantity').focus();
		showMessage('info', 'We can enter quantity more than '+$('#locationQuantity').val());
		return false
	}
	return true;
}

function getRefreshmentListDetails(){

	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	  		=  $('#tripSheetId').val();
	
	showLayer();
	$.ajax({
             url: "getRefreshmentListDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setRefreshmentData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}
function setRefreshmentData(data){
	$("#refreshmentBody tr").remove();
	if(data.refreshment != null){
		$('#refreshmentTable').show();
		var consumption = 0;
		 for(var i = 0; i< data.refreshment.length; i++){
			var refreshment = data.refreshment[i];
			consumption = (refreshment.quantity - refreshment.returnQuantity).toFixed(2);
			var tr =' <tr data-object-id="">'
				+'<td  class="fit ar">'+refreshment.refreshmentEntryNumber+'</td>'
				+'<td  class="fit ar">'+refreshment.asignmentDateStr+'</td>'
				+'<td  class="fit ar">'+refreshment.quantity+'</td>'
				+'<td  class="fit ar">'+refreshment.returnQuantity+'</td>'
				+'<td  class="fit ar">'+consumption+'</td>'
				+'<td  class="fit ar">'+refreshment.totalAmount+'</td>';
				if(refreshment.returnQuantity <= 0){
					tr+= '<td  class="fit ar"><a href="#" onclick="removeRefreshment('+refreshment.refreshmentEntryId+');"><i class="fa fa-times"> Remove</i></a></td>';
				}else{
					tr+= '<td  class="fit ar">--</td>';
				}
				
				+'</tr>';
			
			$('#refreshmentBody').append(tr);
		}
	
	}
}
function removeRefreshment(refreshmentEntryId){
	  if(confirm("Are You Sure to Delete ?")){
			var jsonObject			= new Object();
			jsonObject["refreshmentEntryId"] =  refreshmentEntryId;
			
			showLayer();
			$.ajax({
		             url: "removeRefreshment",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 window.location.replace("refreshmentEntry.in?tripSheetID="+$('#tripSheetId').val()+"");
		            	 showMessage('success', 'Data Deleted Successfully!')
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
	  }

}
function getTripSheetForDate(){
	if($('#refreshmentDate').val() != null && $('#refreshmentDate').val().trim() != '' && Number($('#FuelSelectVehicle').val()) > 0){
		var jsonObject				  = new Object();
		jsonObject["refreshmentDate"] =  $('#refreshmentDate').val();
		jsonObject["vid"] 			  =  $('#FuelSelectVehicle').val();
		showLayer();
		$.ajax({
	             url: "getTripSheetForDate",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.tripSheetList != undefined){
	            		 var tripList = '<option value="0">Select TripSheet</option>';
	            		 for(var i = 0; i< data.tripSheetList.length ; i++){
	            			 var tripNumber = data.tripSheetList[i].tripSheetNumber+' ( Date : '+data.tripSheetList[i].tripOpenDate+' - '+data.tripSheetList[i].closetripDate+' )';
	            			 tripList += '<option value="'+data.tripSheetList[i].tripSheetID+'">'+tripNumber+'</option>';
	            		 }
	            		 $("#tripSheetNumberDrop").html(tripList);
	            	 }else{
	            		 showMessage('info', 'No TripSheet Found Please Create TripSheet First !');
	            	 }
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
  }
}
function setTripSheetData(){
	
	$('#FuelSelectVehicle').select2('data', {
	id : $('#vid').val(),
	text : $('#vehicle_registration').val()
	});
	var D1ID = $('#tripFristDriverID').val();
	var D1Text = $('#tripFristDriverName').val()+" "+$('#tripFristDriverLastName').val()+" - "+$('#tripFristDriverFatherName').val();
	$('#DriverFuel').select2('data', {
	id : D1ID,
	text : D1Text
	});
	
	var RID = $('#routeID').val();
	var RText = $('#routeName').val();
	$('#FuelRouteList').select2('data', {
		id : RID,
		text : RText
	});
	
}
function setSelectedTripSheetData(){
	
	
	var jsonObject				  = new Object();
	jsonObject["tripSheetId"] 	  =  $('#tripSheetNumberDrop').val();
	showLayer();
	$.ajax({
             url: "getTripSheetDataForRefreshmentAdd",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if(data.tripSheet != undefined){
            			$('#DriverFuel').select2('data', {
            			id : data.tripSheet.tripFristDriverID,
            			text : data.tripSheet.tripFristDriverName
            			});
            			
            			$('#FuelRouteList').select2('data', {
            				id : data.tripSheet.routeID,
            				text : data.tripSheet.routeName
            			});
            	 }
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
	
}