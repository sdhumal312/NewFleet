var selectedServiceProgram = new Map();
var programROwMap	= new Map();
var headerRowAdded	= false;
var fromWOEdit	= false;

function makeWorkOrderApproval(){
	Swal.fire({
		  title: 'Are you sure to make Approval?',
		  text: "You won't be able to revert this !",
		  icon: 'info',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, Approve it!'
		}).then((result) => {
		  if (result.value) {
			  	showLayer();
			  var jsonObject		= new Object();
				jsonObject["workOrderId"]		= $('#workOrderId').val();
				jsonObject["approvalStatusId"]	= 1;
				$.ajax({
					url: "approveWorkOrderDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#approvalStatus').text('Approved');
						$('#approvalStatus').attr("class","btn btn-success");
						$('#makeApproval').hide();
						hideLayer();
						showAlert('success', 'WorkOrder Approved Successfully !');
						
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
		  }
		})
}


function vehicleOnChange() {
    	$.getJSON("getVehicleOdoMerete.in", {
            vehicleID: $("#select3").val(),
            ajax: "true"
        }, function(a) {
            var b = "";
            b = a.vehicle_Odometer, document.getElementById("vehicle_Odometer").placeholder = b, document.getElementById("vehicle_Odometer_old").value = b
            $('#Odometer').val(a.vehicle_Odometer);
            $('#vehicle_ExpectedOdameter').val(a.vehicle_ExpectedOdameter);
            if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
            	$('#vehicle_Odometer').val(a.vehicle_Odometer);
            	$('#gpsOdometer').val(a.gpsOdameter);
            	$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
            	$('#gpsWorkLocation').val(a.gpsLocation);
            	$('#gpsOdometerRow').show();
            	$('#gpsWorkLocationRow').show();
            	//$('#grpwoOdometer').hide();
            }else{
            	$('#gpsWorkLocationRow').hide();
            	$('#grpwoOdometer').show();
            }
        })
    }

function vehicleOnChange1(){
	
        var a = "",
            b = $("#select3").val();
        $("#vehicle_Meter option").each(function() {
            b == $(this).val() && (a = $(this).text())
        }), document.getElementById("vehicle_Odometer").placeholder = a, document.getElementById("vehicle_Odometer_old").value = a, $("#hidden").hide()
    }

function vehicleOnChange2() {
	
    	$.getJSON("getVehicleOdoMerete.in", {
            vehicleID:  $("#select3").val(),
            ajax: "true"
        }, function(a) {
            var b = "";
            b = a.vehicle_Odometer, document.getElementById("vehicle_Odometer").placeholder = b, document.getElementById("vehicle_Odometer_old").value = b
            $('#Odometer').val(a.vehicle_Odometer);
            $('#vehicle_ExpectedOdameter').val(a.vehicle_ExpectedOdameter);
            if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
            	$('#vehicle_Odometer').val(a.vehicle_Odometer);
            	$('#gpsOdometer').val(a.gpsOdameter);
            	$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
            	$('#gpsWorkLocation').val(a.gpsLocation);
            	$('#gpsOdometerRow').show();
            	$('#gpsWorkLocationRow').show();
            	//$('#grpwoOdometer').hide();
            }else{
            	$('#gpsWorkLocationRow').hide();
            	$('#grpwoOdometer').show();
            }
        })
} 

function vehicleOnChange3() {

		
	    $.getJSON("getVehicleServiceReminderList.in", {
	        vehicleID: $("#select3").val(),
	        ajax: "true"
	    },  function(a) {
	    	 for (var b = a.length - 1, c = "", d = a.length, e = 0; d > e; e++) b != e ? (c += '{"id":"' + a[e].service_id + '","date":"' + a[e].servceDate + '","text":"' + a[e].service_NumberStr + '" }', c += ",") : c += '{"id":"' + a[e].service_id + '","text":"'+ a[e].service_NumberStr + '" }';
	         var f = "[" + c + "]",
	             g = JSON.parse(f);
	         $("#ServiceReminder").select2({
	        	 multiple: !0,
	        	 allowClear: !0,
	             data: g
	         })
	    })

   
}

function getServiceSecheduleList(evt){
		if(!fromWOEdit){
		 			var jsonObject						= new Object();
					jsonObject["serviceProgramId"]	= $('#'+evt.id+'').val();
					jsonObject["vid"]				= $('#select3').val();
					
					if($('#'+evt.id+'').val().trim() == ''){
						setServiceScheduleData(null);
					}
				getScheduleData(jsonObject);		
		}		
}

function getScheduleData(jsonObject){
				
				$.ajax({
						url: "getServiceReminderByProgramIdVid",
						type: "POST",
						dataType: 'json',
						data: jsonObject,
						success: function (data) {
							setServiceScheduleData(data);
						},
						error: function (e) {
							hideLayer();
							showMessage('errors', 'Some Error Occurred!');
						}
					});
}

function setServiceScheduleData(data){
	if(data != null && data.serviceSchedules != undefined && data.serviceSchedules.length > 0){
		$('#serviceSchedules').show();
					var table = "";
		            for (var key of Object.keys(data.scheduleHashMap)) {
					    var serviceSchedulesList	= data.scheduleHashMap[key];
			           		if(!(key in programROwMap)){
			           			programROwMap[key] = key;
				            	table += '<tr id="'+key+'">'
				            	+'<td colspan="4"><a style="font-size:16px;" href="#">'+key+'</a></td>'
				            	+'</tr>';
			           		}
			            for(var i= 0; i<serviceSchedulesList.length; i++){
			           if(!(serviceSchedulesList[i].service_id in selectedServiceProgram)){
				           selectedServiceProgram[serviceSchedulesList[i].service_id] = serviceSchedulesList[i].service_id;
				            table += '<tr id="row_'+serviceSchedulesList[i].service_id+'">'
				            	+'<td><input id="'+serviceSchedulesList[i].service_id+'" name="selectedSchedule" type="checkbox" class="se" /> </td>'
				            	+'<td>'+serviceSchedulesList[i].taskAndSchedule+'</td>'
				            	+'<td>'+serviceSchedulesList[i].nextDue+'</td>'
				            	+'</tr>';
			            }
			           }
					}
		         
		  $('#serviceSchedulesTable').append(table);   
		  
		   for (var key of Object.keys(selectedServiceProgram)) {
		   		var keyFound = false;
		   		 for(var j= 0; j<data.serviceSchedules.length; j++){
							  	if((data.serviceSchedules[j].service_id == key)){
							  		keyFound = true;
							  	}
			 	 }
			 	 if(!keyFound){
			 	 	$('#row_'+key+'').remove();
			 	 	delete selectedServiceProgram[key];
			 	 }
		 }
		 
		 for (var key of Object.keys(programROwMap)) {
		   		if(!(key in data.scheduleHashMap)){
		   			$('#'+key+'').remove();
		   			delete programROwMap[key];
		   		}
		 }
		         
	}else{
		$('#serviceSchedulesTable').empty(); 
		selectedServiceProgram = new Map();
		programROwMap	= new Map();
		$('#serviceSchedules').hide();
	}
}

function setServiceProgramList(data){
	if(data != null && data != ""){
	var serviceProgramList = JSON.parse(data);
	var array	 = new Array();
			
			for(var i = 0; i< serviceProgramList.length ; i++){
				var locationDetails	= new Object();
			   locationDetails.id = serviceProgramList[i].serviceProgramId;
			   locationDetails.text = serviceProgramList[i].serviceProgram;
			   array.push(locationDetails);
			}
			$('#ServiceReminder').data().select2.updateSelection(array);
			
				var jsonObject						= new Object();
				jsonObject["serviceProgramId"]	= $('#ServiceReminder').val();
				jsonObject["vid"]				= $('#select3').val();
				
				
			getScheduleData(jsonObject);
			setServiceReminderCheckBox();
	}
}

function setServiceReminderCheckBox(){
	var serviceReminderIds	= $('#serviceReminderIds').val().split(',');
		setTimeout(function(){ 
			 for(var i=0; i<serviceReminderIds.length;i++){
					$('#'+serviceReminderIds[i]+'').prop('checked', true);
					$('#'+serviceReminderIds[i]+'').attr('disabled', true);
			 }
		}, 500);
	
}

function selecteAllServiceSchedule(){
	
	$("input[name=selectedSchedule]").each(function(){
		if($('#selectAll').prop('checked')){
			if(!$('#'+this.id+'').prop('disabled')){
				$('#'+this.id+'').prop('checked',true);
			}
		}else{
			if(!$('#'+this.id+'').prop('disabled')){
				$('#'+this.id+'').prop('checked',false);
			}
		}
	});
}

function getWarrantyPartsListToSelect(partid, NoOfParts, locationId, workordertaskid, assignedNoPart){

		if(Number(NoOfParts) == Number(assignedNoPart)){
			$('#saveAssign').hide();
		}else{
			$('#saveAssign').show();
		}
		
		var jsonObject				= new Object();
		jsonObject["partId"]		= partid;
		jsonObject["locationId"]	= locationId;
		jsonObject["servicePartId"]	= workordertaskid;
			$.ajax({
				url: "getWarrantyDetailsList",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
						$('#warrantyListBody tr').remove();
					if(data.partWarranty != undefined && data.partWarranty.length > 0){
						$('#warrantyPartId').val(partid);
						$('#warrantyPartTaskId').val(workordertaskid);
						if(Number(NoOfParts) > Number(assignedNoPart)){
							setWarrantyPartListToPopup(data, NoOfParts, assignedNoPart);
						}
						
					}else{
						if($('#statues').val() == undefined || $('#statues').val() != 'COMPLETE'){
					  		showMessage('info', 'Serial Number Not added for any part , Please add serial number first !');
						}
					}
					setAssignedWarrantyPartList(data);
					$('#addWarrantyParts').modal('show');
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
}

function setAssignedWarrantyPartList(data){
   
   $('#assingedDataBody').empty();
   
	if(data.assignedParts != undefined && data.assignedParts.length > 0){
	var SrNo = 1;
		var tableHead = '<tr>'
					   +'<th>Sr.</th>'
					   +'<th>Assigned Part</th>'
					   +'<th>Replaced With</th>'
		               +'</tr>';
		 $('#assingedDataBody').append(tableHead);                 
		               
		for(var i = 0; i < data.assignedParts.length ; i++){
			var tr = '<tr>'
			+'<td class="fit">'+SrNo+'</td>'
			+'<td class="fit">'+data.assignedParts[i].partSerialNumber+'</td>'
			+'<td class="fit">'+data.assignedParts[i].replacePartSerialNumber+'</td>'
			+'</tr>';
			
			SrNo++;
			
			 $('#assingedDataBody').append(tr);   
		}
	}
}

function setWarrantyPartListToPopup(data, NoOfParts, assignedNoPart){

	$('.warrantyData').show();
	
	var maxAllowedPart	= NoOfParts - assignedNoPart;
	for(var i = 0; i < data.partWarranty.length; i++){
		var tr = '<tr>'
			+'<td class="fit"><input id="'+data.partWarranty[i].partWarrantyDetailsId+'" name="partWarrantyDetailsId" onclick="getCheckedQuantity('+maxAllowedPart+', this);"  type ="checkbox" /></td>'
			+'<td class="fit">'+data.partWarranty[i].partSerialNumber+'</td>'
			+'<td class="fit"><input style="width:100%;" name="prePartWarrantyDetailsId" type="text" id="replace_'+data.partWarranty[i].partWarrantyDetailsId+'"  /></td>'
			+'</tr>';
			
			 $('#warrantyListBody').append(tr);   
			
			$("#replace_"+data.partWarranty[i].partWarrantyDetailsId+"").select2( {
				minimumInputLength:2, minimumResultsForSearch:10, ajax: {
					url:"getAlreadyAsignedPartsByVid.in", 
					dataType:"json", 
					type:"POST", 
					contentType:"application/json", 
					quietMillis:50, 
					data:function(a) {
						return {
							term: a,
							vid: 		$('#vid').val(),
							partId : 	$('#warrantyPartId').val(),
							serviceId : $('#workorders_id').val()
						}
					}
				, results:function(a) {
					return {
						results:$.map(a, function(a) {
							return {
								text: a.partSerialNumber +"-"+ a.serviceNumber, 
								slug: a.slug, 
								id: a.partWarrantyDetailsId,
								serviceId: a.serviceId
							}
						})
					}
				}
			}
			})
	}
	
}
