console.log(">>>>")
$(document).ready(function() {
	$("#partId").select2( {
		minimumInputLength: 2,
		minimumResultsForSearch: 10,
		ajax: {
			url: "getRepairablePartListByLocation.in",
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
				console.log("a",a)
				return {
					results: $.map(a, function(a) {
						return $("#locationId").select2('data').text == a.location ? {
							text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
							slug: a.slug,
							id: a.inventory_location_id,
							warranty : a.isWarrantyApplicable,
							repairable : a.isRepairable,
							partId : a.partid,
							locationId : a.locationId,
							maxQuantity : a.location_quantity,
							inventory_id : a.inventory_id
						} : {
							text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
							slug: a.slug,
							id: a.inventory_location_id,
							disabled: !0,
							warranty : a.isWarrantyApplicable,
							repairable : a.isRepairable,
							partId : a.partid,
							locationId : a.locationId,
							maxQuantity : a.location_quantity,
							inventory_id : a.inventory_id
						}
					})
				}
			}
		}
}),$("#locationId").select2({
    ajax: {
        url: "getWorkOrderPartlocation.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "GET",
        contentType: "application/json",
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
                        slug: a.slug,
                        id: a.partlocation_id
                    }
                })
            }
        }
    }
})

});


$(document).ready(
	function($) {
		$('button[type=submit]').click(function(e) {
			e.preventDefault();
			showLayer();
			
			var jsonObject						= new Object();
			
			jsonObject["repairStockTypeId"] 	=  $('#repairStockTypeId').val();
			jsonObject["workShopId"] 			=  $('#workShopId').val();
			jsonObject["locationId"] 			=  $('#locationId').val();
			if($('#partId').val() >  0 ){
				jsonObject["partId"] 				=  $('#partId').select2('data').partId;
			}
			jsonObject["tyreId"] 				=  $('#tyreId').val();
			jsonObject["batteryId"] 			=  $('#batteryId').val();
			jsonObject["companyId"] 			=  $('#companyId').val();
			jsonObject["userId"] 				=  $('#userId').val();
			
			if(Number($('#locationId').val()) <= 0){
				showMessage('info','Please Select Location!');
				hideLayer();
				return false;
				
			}	
			showLayer();
			
			$.ajax({
				url: "getRepairStockReportData",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					renderReportData(data);
					hideLayer();
				},
				error: function (e) {
					showMessage('errors', 'Some error occured!')
					hideLayer();
				}
			});


		});

	});


function renderReportData(resultData) { 
	
	console.log("resultData",resultData)
	
	if(resultData.repairToStockDetailsList != undefined){    
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		/*$('#reportHeader').html('<b>Tyre Expense Details Report</b>');
		var defaultVal ="All"
		
		var data 	= $('#manufacurer').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Manufacturer : '+data.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit"> Vehicle : '+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
		}*/
		
		
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			
			console.log("columnConfiguration",columnConfiguration)
			tableProperties	=  resultData.tableConfig.tableProperties;
			console.log("tableProperties",tableProperties)
			
		}
		
		setSlickData(resultData.repairToStockDetailsList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}

function changeLocation(){
	$.getJSON("getLocationWiseTyreList.in?fromLocationId="+$("#locationId").val()+"", function(e) {
		console.log("e",e)
		tyreList	= e;//To get All Company Name 
		$("#tyreId").empty();
		$("#tyreId").append($("<option>").text("Please Select ").attr("value",0));
		$('#tyreId').select2();

		console.log("tyreList.length",tyreList.length);
		
		for(var k = 0; k <tyreList.length; k++){
			$("#tyreId").append($("<option>").text(tyreList[k].TYRE_NUMBER).attr("value", tyreList[k].TYRE_ID));
		}

	}),$.getJSON("getLocationWiseBatteryList.in?fromLocationId="+$("#locationId").val()+"", function(e) {
		batteryList	= e;//To get All Company Name 
		$("#batteryId").empty();
		$("#batteryId").append($("<option>").text("Please Select").attr("value",0));
		$('#batteryId').select2();
		if(batteryList != null){
			for(var k = 0; k <batteryList.length; k++){
				$("#batteryId").append($("<option>").text(batteryList[k].batterySerialNumber).attr("value", batteryList[k].batteryId));
			}
		}

	})
}

function displayStock(){
	if(Number($("#repairStockTypeId").val()) == 2){
		$("#partDiv").hide();
		$("#tyreDiv").show();
		$("#batteryDiv").hide();
	}else if(Number($("#repairStockTypeId").val()) == 3){
		$("#partDiv").hide();
		$("#tyreDiv").hide();
		$("#batteryDiv").show();
	}else{
		$("#partDiv").show();
		$("#tyreDiv").hide();
		$("#batteryDiv").hide();
	}
	
	
	$.getJSON("getLocationWiseTyreList.in?fromLocationId="+$("#locationId").val()+"", function(e) {
		console.log("e",e)
		tyreList	= e;//To get All Company Name 
		$("#tyreId").empty();
		$("#tyreId").append($("<option>").text("Please Select ").attr("value",0));
		$('#tyreId').select2();

		for(var k = 0; k <tyreList.length; k++){
			$("#tyreId").append($("<option>").text(tyreList[k].TYRE_NUMBER).attr("value", tyreList[k].TYRE_ID));
		}

	}),$.getJSON("getLocationWiseBatteryList.in?fromLocationId="+$("#locationId").val()+"", function(e) {
		batteryList	= e;//To get All Company Name 
		$("#batteryId").empty();
		$("#batteryId").append($("<option>").text("Please Select").attr("value",0));
		$('#batteryId').select2();
		if(batteryList != null){
			for(var k = 0; k <batteryList.length; k++){
				$("#batteryId").append($("<option>").text(batteryList[k].batterySerialNumber).attr("value", batteryList[k].batteryId));
			}
		}

	})
	
}

function showAdditionalPartDetails(repairToStockDetailsId){
	console.log("repairToStockDetailsId",repairToStockDetailsId);
	
	var jsonObject							= new Object();
	jsonObject["repairToStockDetailsId"] 	=  repairToStockDetailsId
	jsonObject["companyId"] 				=  $('#companyId').val();
	jsonObject["userId"] 					=  $('#userId').val();
	
	showLayer();
	
	$.ajax({
		url: "getAdditionalPartDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderAdditionalPartDetails(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
}


function renderAdditionalPartDetails (data){
	//additionalPartDetails
	console.log("data",data)
	
	if(Number($('#workShopId').val()) == 1){
		$("#ownAdditionalPartTable").show();
		$("#dealerAdditionalPartTable").hide();
	}else{
		$("#ownAdditionalPartTable").hide();
		$("#dealerAdditionalPartTable").show();
	}
	
	$("#additionalOwnPartTable").empty();
	$("#additionalDealerPartTable").empty();
	
	var additionalPartDetails = data.additionalPartDetails;
	
	console.log("additionalPartDetails ::: ",additionalPartDetails)
	
	if(additionalPartDetails != undefined || additionalPartDetails != null){
		for(var index = 0 ; index < additionalPartDetails.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar'>"+ additionalPartDetails[index].partName  +"</td>");
			columnArray.push("<td class='fit'ar'>"+ additionalPartDetails[index].quantity  +"</td>");
			columnArray.push("<td class='fit ar'>"+ additionalPartDetails[index].unitCost +"</td>");
			if(Number($('#workShopId').val()) == 2){
				columnArray.push("<td class='fit'>" + additionalPartDetails[index].gst +"</td>");
				columnArray.push("<td class='fit'>" + additionalPartDetails[index].discount +"</td>");
			}
			columnArray.push("<td class='fit'>" + additionalPartDetails[index].totalCost +"</td>");
			
			if(Number($('#workShopId').val()) == 1){
				$('#additionalOwnPartTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			}else{
				$('#additionalDealerPartTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			}
		}
		
		$("#additionalPartModal").modal('show');
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}


function showLabourDetails(repairStockId){
	var jsonObject							= new Object();
	jsonObject["repairStockId"] 			=  repairStockId
	jsonObject["companyId"] 				=  $('#companyId').val();
	jsonObject["userId"] 					=  $('#userId').val();
	
	showLayer();
	
	$.ajax({
		url: "getAdditionalLabourDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderAdditionalLabourDetails(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
}


function renderAdditionalLabourDetails (data){
	
	console.log("data",data)
	
	if(Number($('#workShopId').val()) == 1){
		$("#ownAdditionalLabourTable").show();
		$("#dealerAdditionalLabourTable").hide();
	}else{
		$("#ownAdditionalLabourTable").hide();
		$("#dealerAdditionalLabourTable").show();
	}
	
	$("#additionalOwnLabourTable").empty();
	$("#additionalDealerLabourTable").empty();
	
	var additionalLabourDetails = data.additionalLabourDetails;
	
	if(additionalLabourDetails != undefined || additionalLabourDetails != null){
		for(var index = 0 ; index < additionalLabourDetails.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar'>"+ additionalLabourDetails[index].labourName  +"</td>");
			columnArray.push("<td class='fit'ar'>"+ additionalLabourDetails[index].hours  +"</td>");
			if(Number($('#workShopId').val()) ==  2){
				columnArray.push("<td class='fit ar'>"+ additionalLabourDetails[index].unitCost +"</td>");
				columnArray.push("<td class='fit'>" + additionalLabourDetails[index].gst +"</td>");
				columnArray.push("<td class='fit'>" + additionalLabourDetails[index].discount +"</td>");
				columnArray.push("<td class='fit'>" + additionalLabourDetails[index].totalCost +"</td>");
			}
			
			if(Number($('#workShopId').val()) == 1){
				$('#additionalOwnLabourTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			}else{
				$('#additionalDealerLabourTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			}
		}
		
		$("#additionalLabourModal").modal('show');
		
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

