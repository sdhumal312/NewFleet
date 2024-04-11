$(document).ready(function(){
	$("#driverList1").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"/getDriver1List.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.driver_empnumber+" "+e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername, slug: e.slug, id: e.driver_id
                        	
                        }
                    }
                    )
                }
            }
        }
    }
    )
	
	
})


function showList(status,typeId){
	overAndDueData(status,typeId);
	var e = document.getElementById("statues").value;
	switch (e) {
	case e:
		document.getElementById(e).className = "";
	}
	if(typeId == 1){
		document.getElementById("overDueClr").className = "active";
		document.getElementById("dueSoonClr").className = "";
		document.getElementById("renewalReceiptClr").className = "";
	}else if(typeId == 2){
		document.getElementById("overDueClr").className = "";
		document.getElementById("dueSoonClr").className = "active";
		document.getElementById("renewalReceiptClr").className = "";
	}else if(typeId == 3){
		document.getElementById("overDueClr").className = "";
		document.getElementById("dueSoonClr").className = "";
		document.getElementById("renewalReceiptClr").className = "active";
	}
}	

function overAndDueData(status,typeId){	
	
	var jsonObject					= new Object();
	jsonObject["status"]			= status;
	jsonObject["typeId"]			= typeId;
	showLayer();
	$.ajax({
		url : "/getOverDueData",
		type : "POST",
		dataType : 'json',
		data : jsonObject,
		success : function (data) {
			if(typeId == 3){
				setRenewalReciptList(data)
				$(".driverData").addClass('hide');
				$(".overAndDue").removeClass('hide');
			}else{
				setRenewalOverAndSoonList(data);
				$(".driverData").addClass('hide');
				$(".overAndDue").removeClass('hide');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRenewalOverAndSoonList(data) {
	$("#viewOverAndDueData").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');

	tr1.append(th1.append("EMP-No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Type"));
	tr1.append(th4.append("Number"));
	tr1.append(th5.append("Validity_From"));
	tr1.append(th6.append("Validity_To"));
	//tr1.append(th7.append("Download"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.driverReminder != undefined && data.driverReminder.length > 0) {
		
		var driverReminder = data.driverReminder;
	
		for(var i = 0; i < driverReminder.length; i++) {
			
			var labelClass;
			
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
					
			tr1.append(td1.append('<a href="showDriver.in?driver_id='+driverReminder[i].driver_id+'" data-toggle="tip" data-original-title="Click driver" target="_blank">'+driverReminder[i].driver_empnumber+'</a>'));
			tr1.append(td2.append('<a href="showDriver.in?driver_id='+driverReminder[i].driver_id+'" target="_blank">'+driverReminder[i].driver_firstname+ '('+driverReminder[i].driver_Lastname+ ')'+'</a>'));
			tr1.append(td3.append(driverReminder[i].driver_remindertype));
			tr1.append(td4.append(driverReminder[i].driver_dlnumber));
			tr1.append(td5.append(driverReminder[i].driver_dlfrom_show));
			tr1.append(td6.append(driverReminder[i].driver_dlto_show +'<font color="#FF6666">('+driverReminder[i].driver_dueDifference+')</font><ul class="list-inline no-margin"><li><font color="#999999"> Due soon on ' + driverReminder[i].driver_renewaldate+'</font></li></ul>'));
			
			//tr1.append(td7.append('<a href="/fleetopgroup/download/driverReminder/'+driverReminder[i].driver_remid+'.in" target="_blank"><span class="fa fa-download"> Download</span></a>'));
			//tr1.append(td7.append('<a href="/download/driverReminder/'+driverReminder[i].driver_remid+'.in" target="_blank"><span class="fa fa-download"> Download</span></a>'));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#viewOverAndDueData").append(thead);
	$("#viewOverAndDueData").append(tbody);
	
}

function renewalReceipt(){
	
	$('#renewalReceipt').modal('show');
}

function validateRenewalReceipt(){
	
	if($('#driverList1').val() == "" ){
		showMessage('info','Please select Driver First !! ');
		return false;
	}
	if($('#fuelDate').val() == ""){
		showMessage('info','Please select receipt Date First !! ');
		return false;
	}
	
	if($('#fileUpload').val() == ""){
		showMessage('info','Please select file First !! ');
		return false;
	}
	
	return true;
	
}

function addRenewalReceipt(){
	
	if(!validateRenewalReceipt()){
		return false;
	}
	
	var object = new Object();
	showLayer();
	
	object['driverId'] = $('#driverList1').val();
	object['applicationNo'] = $('#applicationNo').val();
	object['receiptNo'] = $('#receiptNo').val();
	object['receiptDate'] = $('#fuelDate').val();
	object['driverRenewalTypeId'] = $('#driverRenewalTypeId').val();
	
	var doc = document.getElementById("fileUpload");
	var formdata = new FormData();
	formdata.append("renewalReceipt",JSON.stringify(object));
	formdata.append("file",doc.files[0]);
	
	$.ajax({
		
		type : "POST",
		encType : 'multipart/form-data',
		url : "/addDriverRenewalReceipt",
		data : formdata ,
		contentType : false,
		processData : false ,
		success : function(data)
		{
			hideLayer();
			$('#renewalReceipt').modal("hide");
			if(data.success == 'true' || data.success == true ){
				showMessage('success',' Renewal reciept saved successfully ')
			}
			if (data.noReminderFound == true || data.noReminderFound == 'true' ){
				showMessage('info',' Renewal Reminder Not found !!! ');
			}
			setTimeout(function(){
				
				location.reload();
				
			},1000)
			
		},
	error : function(e){
		
		hideLayer();
		showMessage("error","Some error occured ! ")
	}
	})
	
	
}


function setRenewalReciptList(data) {
	$('#renewalReceiptCount').text(data.renewalReceiptCount);
	$("#viewOverAndDueData").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="actions" class="fit ar">');

	tr1.append(th1.append("EMP-No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Type"));
	tr1.append(th4.append("Application no."));
	tr1.append(th5.append("Receipt no."));
	tr1.append(th6.append("Reciept Date"));
	tr1.append(th7.append("Download"));
	tr1.append(th8.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.driverReminder != undefined && data.driverReminder.length > 0) {
		
		var driverReminder = data.driverReminder;
		
	
		for(var i = 0; i < driverReminder.length; i++) {
			
			var labelClass;
			
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
					
			tr1.append(td1.append('<a href="showDriver.in?driver_id='+driverReminder[i].driver_id+'" data-toggle="tip" data-original-title="Click driver" target="_blank">'+driverReminder[i].driver_empnumber+'</a>'));
			tr1.append(td2.append('<a href="showDriver.in?driver_id='+driverReminder[i].driver_id+'" target="_blank">'+driverReminder[i].driver_firstname+ '('+driverReminder[i].driver_Lastname+ ')'+'</a>'));
			tr1.append(td3.append(driverReminder[i].driverRenewalType));
			tr1.append(td4.append(driverReminder[i].applicationNo));
			tr1.append(td6.append(driverReminder[i].receiptNo ));
			tr1.append(td5.append(driverReminder[i].receiptDateEtr));
			
			tr1.append(td7.append('<a href="/download/driverDocument/'+driverReminder[i].documentIdBig+'.in" target="_blank"><span class="fa fa-download"> Download</span></a>'));
			
			tr1.append(td8.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="deleteRenewalReciept('+driverReminder[i].driverRenewalReceiptIdBig+','+driverReminder[i].reminderId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#viewOverAndDueData").append(thead);
	$("#viewOverAndDueData").append(tbody);
	
}


function deleteRenewalReciept(renewalReceiptId,reminderId){
	var object = new Object();
	object["renewalReceiptId"] = renewalReceiptId;
	object["reminderId"] 		= reminderId;
	$.ajax({
		url : "/deleteRenewalReciept",
		type : "POST",
		dataType : 'json',
		data : object,
		success : function (data) {
			if(data.success == true || data.success == 'true'){
				showMessage('success',' Renewal reciept deleted successfully ')
				setTimeout(function(){
					location.reload();
				},1000)
			}
		},error : function(e){
			console.log("e",e);
			showMessage('error','some error occures')
		}
	})
}



