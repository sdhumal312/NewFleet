var ownLabourColSpanClass = "";
var ownPartTotalCostColSpanClass = "";
var StockHyperLink = "";
$(document).ready(function(){
	if($("#partDiscountTaxTypeId").val() == 2){
		$(".partPercentAmountId").html("");
		$('#finalPartDiscountTaxTypId').val(2);
	}else{
		$(".partPercentAmountId").html("%");
		$('#finalPartDiscountTaxTypId').val(1);
	}
	if($("#labourDiscountTaxTypeId").val() == 2){
		$(".labourPercentAmountId").html("");
		$('#finalLabourDiscountTaxTypId').val(2);
	}else{
		$(".labourPercentAmountId").html("%");
		$('#finalLabourDiscountTaxTypId').val(1);
	}
	if($("#repairTypeId").val() == 2){
		StockHyperLink = "showTyreInfo.in?Id=";
	}else if($("#repairTypeId").val() == 3){
		StockHyperLink = "showBatteryInformation?Id=";
	}
	
	var jsonObject = new Object();
	jsonObject["repairStockId"] 			= $("#repairStockId").val();
	jsonObject["repairTypeId"] 				= $("#repairTypeId").val();
	jsonObject["partWiseLabourCofig"] 		= $("#partWiseLabourCofig").val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	
	$.ajax({
		url: "getRepairToStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setRepairToDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	$.ajax({
		url: "getRepairStockToPartAndLabourDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if($("#partWiseLabourCofig").val() == true || $("#partWiseLabourCofig").val() == 'true'){
				setRepairStockToPartAndLabourDetails(data);
			}else{
				setRepairStockToPartAndDefaultLabourDetails(data);
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	if($("#repairTypeId").val()== 1){
		$(".partDiv").show();
	}else if($("#repairTypeId").val() == 2){
		$(".tyreDiv").show();
	}else{
		$(".batteryDiv").show();
	}
	
});

function setRepairToDetails(data){
	 var repairStockToDetailsList 		= data.repairStockToDetailsList
	 
	 if(repairStockToDetailsList != undefined || repairStockToDetailsList != null){
		 for(var index = 0 ; index < repairStockToDetailsList.length; index++){
			 var columnArray = new Array();
			 columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			 columnArray.push("<td class='fit'ar> <a href='"+StockHyperLink+""+repairStockToDetailsList[index].repairStockPartId+"' target='_blank'>"+ repairStockToDetailsList[index].repairToStockName  +"</a></td>");
			 columnArray.push("<td class='fit ar'>" + repairStockToDetailsList[index].repairStatus +"</td>");
			 columnArray.push("<td class='fit ar'>" + repairStockToDetailsList[index].workDetails +"</td>");
			 if($("#repairWorkshopId").val() == 1){
				 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='checkAssetNumber("+repairStockToDetailsList[index].repairStockPartId+","+repairStockToDetailsList[index].inventoryLocationPartId+","+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].inventoryLocationId+" );'><span class='fa fa-edit'></span> Asset Number</a></td>");
			 }
			 $('#sentRepairtockTableBody').append("<tr id='penaltyID"+repairStockToDetailsList[index].repairStockPart+"' >" + columnArray.join(' ') + "</tr>");

		 }
		 columnArray = [];
	 }else{
		 showMessage('info','No Record Found!')
	 }
}


function setRepairStockToPartAndLabourDetails(data){
	
	if(data.noAdditionalPartAndLabourFound != undefined && (data.noAdditionalPartAndLabourFound == true || data.noAdditionalPartAndLabourFound =='true')){
		  $("#noAdditionalPartAndLabourFound").val(true);
	 }else{
		 $("#noAdditionalPartAndLabourFound").val(false);
	 }
	
	
	if( $("#repairWorkshopId").val() == 1){
		ownLabourColSpanClass 		= " colspan='2'";
		ownPartTotalCostColSpanClass = " colspan='4'";
	}else{
		ownPartTotalCostColSpanClass = " colspan='6'";
	}
	var partTotalCost = 0;
	var labourTotalCost = 0;
	if(Number(Object.keys(data.repairStockToPartDetailsHM).length) > 0 && Number(Object.keys(data.repairLabourDetailsHM).length)  > 0){
		console.log("fsfsdf")
		$("#additionalParLabourId").show();
		for (var i in  data.repairStockHM) {
			var repairStockList 				= data.repairStockHM[i];
			var repairStockToPartDetailsList 	= data.repairStockToPartDetailsHM[i];
			var repairStockToLabourDetailsList 	= data.repairLabourDetailsHM[i];
			var columnStockArray		 		= new Array();
			
			columnStockArray.push("<br><br>")
			columnStockArray.push("<th colspan='8' style='text-align: left;background: salmon;font-size: medium;'>  Additional Part Details For Stock : "+ repairStockList[0].repairToStockName  +"</th>");

			$('#inprocessPartBody').append("<tr >" + columnStockArray.join(' ') + "</tr>");

			if(repairStockToPartDetailsList != undefined){
				$('#inprocessPartBody').append("<tr style='background: greenyellow;'><td colspan='8'>Part Details</td></tr>");
				if( $("#repairWorkshopId").val() == 1){
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name</th><th >Qty</th><th >UnitCost</th><th >TotalCost</th><th>Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name</th><th >Qty</th><th >UnitCost</th><th >Discount</th><th >Gst</th><th >TotalCost</th></tr>");
				}
				console.log("repairStockToPartDetailsList",repairStockToPartDetailsList)
				for(var index = 0 ; index < repairStockToPartDetailsList.length; index++){
					var columnPartArray = new Array();

					columnPartArray.push("<td class='fit'>"+(index+1)+"</td>");
					columnPartArray.push("<td class='fit'>"+ repairStockToPartDetailsList[index].partName  +"</td>");
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].quantity +"</td>");
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].unitCost +"</td>");
					if( $("#repairWorkshopId").val() == 2){
						columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].gst +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].discount +"</td>");
					}
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].totalCost +"</td>");
					if( $("#repairWorkshopId").val() == 1){
						columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='checkAssetNumberForAdditionalPart("+repairStockToPartDetailsList[index].partId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+","+repairStockToPartDetailsList[index].repairStockToPartDetailsId+");'><span class='fa fa-edit'></span> Asset Number </a></td>");
					}
					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
					partTotalCost += repairStockToPartDetailsList[index].totalCost;
				}
				//totalCostRow
				$('#inprocessPartBody').append("<tr style='background: greenyellow;'><td "+ownPartTotalCostColSpanClass+">Total Part Cost</td><td >"+partTotalCost+"</td></tr>");
			}
			
			if(repairStockToLabourDetailsList != undefined){
				$('#inprocessPartBody').append("<tr  style='background: orchid;'><td colspan='8'>Labour Details</td></tr>");
				if( $("#repairWorkshopId").val() == 1){
					$('#inprocessPartBody').append("<tr><th>Sr</th><th "+ownLabourColSpanClass+" >Name</th><th "+ownLabourColSpanClass+">Hours</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th>Sr</th><th  >Name</th><th >Hour</th><th>Rate</th><th>Discount</th><th>Gst</th><th>TotalCost</th></tr>");
				}
				for(var index = 0 ; index < repairStockToLabourDetailsList.length; index++){
					var columnPartArray = new Array();

					columnPartArray.push("<td class='fit'>"+(index+1)+"</td>");
					columnPartArray.push("<td class='fit' "+ownLabourColSpanClass+">"+ repairStockToLabourDetailsList[index].labourName  +"</td>");
					columnPartArray.push("<td class='fit' "+ownLabourColSpanClass+">" + repairStockToLabourDetailsList[index].hours +"</td>");
					if( $("#repairWorkshopId").val() == 2){
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].unitCost +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].discount +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].gst +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].totalCost +"</td>");
					}

					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
					labourTotalCost += repairStockToLabourDetailsList[index].totalCost;
				}
				if($("#repairWorkshopId").val() == 2){
					$('#inprocessPartBody').append("<tr style='background: orchid;'><td colspan='6'>Total Labour Cost</td><td >"+labourTotalCost+"</td></tr>");
				}

			}

			$('#inprocessPartBody').append("<br><br><br>");

		}
	} else if( Number(Object.keys(data.repairStockToPartDetailsHM).length) >  0 && Number(Object.keys(data.repairLabourDetailsHM).length)  == 0){
		console.log("2")
		$("#additionalParLabourId").show();
		for (var i in  data.repairStockHM) {
			var repairStockList 	= data.repairStockHM[i];
			var repairStockToPartDetailsList 	= data.repairStockToPartDetailsHM[i];

			var columnStockArray = new Array();
			columnStockArray.push("<br><br>")
			columnStockArray.push("<th colspan='8' style='text-align: left;background: salmon;font-size: medium;'> Additional Part Details For Stock : "+ repairStockList[0].repairToStockName  +"</th>");

			$('#inprocessPartBody').append("<tr >" + columnStockArray.join(' ') + "</tr>");

			// for(var obj in objectArr){
			if(repairStockToPartDetailsList != undefined){
				$('#inprocessPartBody').append("<tr style='background: greenyellow;'><td colspan='8'>Part Details</td></tr>");
				if( $("#repairWorkshopId").val() == 1){
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name</th><th >Qty</th><th >UnitCost</th><th >TotalCost</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name</th><th >Qty</th><th >UnitCost</th><th >Discount</th><th >Gst</th><th >TotalCost</th></tr>");
				}
				console.log("sds",repairStockToPartDetailsList)
				for(var index = 0 ; index < repairStockToPartDetailsList.length; index++){

					var columnPartArray = new Array();

					columnPartArray.push("<td class='fit'>"+(index+1)+"</td>");
					columnPartArray.push("<td class='fit'>"+ repairStockToPartDetailsList[index].partName  +"</td>");
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].quantity +"</td>");
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].unitCost +"</td>");
					if( $("#repairWorkshopId").val() == 2){
						columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].gst +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].discount +"</td>");
					}
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].totalCost +"</td>");


					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
					partTotalCost += repairStockToPartDetailsList[index].totalCost;
				}
				//totalCostRow
				$('#inprocessPartBody').append("<tr style='background: greenyellow;'><td "+ownPartTotalCostColSpanClass+">Total Part Cost</td><td >"+partTotalCost+"</td></tr>");
			}
			$('#inprocessPartBody').append("<br><br><br>");

		}


	}else if(Number(Object.keys(data.repairStockToPartDetailsHM).length) ==  0 && Number(Object.keys(data.repairLabourDetailsHM).length)  > 0){
		console.log("3")
		$("#additionalParLabourId").show();
		for (var i in  data.repairStockHM) {
			var repairStockList 	= data.repairStockHM[i];
			var repairStockToLabourDetailsList = data.repairLabourDetailsHM[i];

			console.log("repairStockToLabourDetailsList",repairStockToLabourDetailsList)
			var columnStockArray = new Array();
			columnStockArray.push("<br><br>")
			columnStockArray.push("<th colspan='8' style='text-align: left;background: salmon;font-size: medium;'>  Additional Part Details For Stock :"+ repairStockList[0].repairToStockName  +"</th>");

			$('#inprocessPartBody').append("<tr >" + columnStockArray.join(' ') + "</tr>");


			console.log("repairStockToLabourDetailsList",repairStockToLabourDetailsList)
			if(repairStockToLabourDetailsList != undefined){
				
				$('#inprocessPartBody').append("<tr  style='background: orchid;'><td colspan='8'>Labour Details</td></tr>");
				if( $("#repairWorkshopId").val() == 1){
					$('#inprocessPartBody').append("<tr><th>Sr</th><th "+ownLabourColSpanClass+">Name</th><th "+ownLabourColSpanClass+">Hours</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th>Sr</th><th>Name</th><th >Hour</th><th >Rate</th><th>Discount</th><th>Gst</th><th>TotalCost</th></tr>");
				}
				
				for(var index = 0 ; index < repairStockToLabourDetailsList.length; index++){
					var columnPartArray = new Array();

					columnPartArray.push("<td class='fit'>"+(index+1)+"</td>");
					columnPartArray.push("<td class='fit' "+ownLabourColSpanClass+">"+ repairStockToLabourDetailsList[index].labourName  +"</td>");
					columnPartArray.push("<td class='fit' "+ownLabourColSpanClass+">" + repairStockToLabourDetailsList[index].hours +"</td>");
					if( $("#repairWorkshopId").val() == 2){
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].unitCost +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].discount +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].gst +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToLabourDetailsList[index].totalCost +"</td>");
					}

					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
				}

			}

			$('#inprocessPartBody').append("<br><br><br>");

		}

	}



}


function setRepairStockToPartAndDefaultLabourDetails(data){
	console.log("kkkiik",data)
	if(Number(Object.keys(data.repairStockToPartDetailsHM).length) > 0 ){
		$("#additionalParId").show();
		
		for (var i in  data.repairStockHM) {
			var repairStockList 	= data.repairStockHM[i];
			// if(repairStockList[i])
			
			/* console.log("repairStockList[i]",data.repairStockHM[i].repairToStockName) */
			var repairStockToPartDetailsList 	= data.repairStockToPartDetailsHM[i];
		
			console.log("LLLL0",repairStockToPartDetailsList)
			if(repairStockToPartDetailsList == undefined){
				continue;
			}
			var columnStockArray = new Array();
			columnStockArray.push("<br><br>")
			columnStockArray.push("<th colspan='8' style='text-align: left;background: greenyellow;font-size: medium;'>  Additional Part Details For Stock : "+ repairStockList[0].repairToStockName  +"</th>");

			$('#inprocessPartBody').append("<tr >" + columnStockArray.join(' ') + "</tr>");
			
			if(repairStockToPartDetailsList != undefined){
				if( $("#repairWorkshopId").val() == 1){
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name</th><th >Qty</th><th >UnitCost</th><th >TotalCost</th><th>Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name</th><th >Qty</th><th >UnitCost</th><th >Discount</th><th >Gst</th><th >TotalCost</th></tr>");
				}
				console.log("sds",repairStockToPartDetailsList)
				for(var index = 0 ; index < repairStockToPartDetailsList.length; index++){
					
					var columnPartArray = new Array();

					columnPartArray.push("<td class='fit'>"+(index+1)+"</td>");
					columnPartArray.push("<td class='fit'>"+ repairStockToPartDetailsList[index].partName  +"</td>");
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].quantity +"</td>");
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].unitCost +"</td>");
					if( $("#repairWorkshopId").val() == 2){
						columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].gst +"</td>");
						columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].discount +"</td>");
					}
					columnPartArray.push("<td class='fit'>" + repairStockToPartDetailsList[index].totalCost +"</td>");
					if( $("#repairWorkshopId").val() == 1){
						columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='checkAssetNumberForAdditionalPart("+repairStockToPartDetailsList[index].partId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+","+repairStockToPartDetailsList[index].repairStockToPartDetailsId+");'><span class='fa fa-edit'></span> Asset Number </a></td>");
					}

					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
				}
			}
			$('#inprocessPartBody').append("<br><br><br>");
		}
	}
	if(data.defaultLabourList != undefined){
		$("#defaultLabour").show();
		var defaultLabourList = data.defaultLabourList
		for(var index = 0 ; index < defaultLabourList.length; index++){
			var columnPartArray = new Array();

			columnPartArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnPartArray.push("<td class='fit'>"+ defaultLabourList[index].labourName  +"</td>");
			columnPartArray.push("<td class='fit'>" + defaultLabourList[index].hours +"</td>");

			$('#defaultLabourTableBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
		}
	}
}

function reopenRepair(repairToStockDetailsId){
	
	if(confirm("Are You Sure!, Do You Want To Reopen Repair Stock?")){
	var jsonObject = new Object();
	jsonObject["repairStockId"] 			= $("#repairStockId").val();
	jsonObject["repairTypeId"] 				= $("#repairTypeId").val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	jsonObject["fromLocationId"] 			= $("#fromLocationId").val();
	jsonObject["toLocationId"] 				= $("#toLocationId").val();
	
	$.ajax({
		url: "reopenRepair",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Reopen Successfully')
			window.location.replace("showRepairInvoice?repairStockId="+$("#repairStockId").val()+"");
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	}else{
		location.reload();
	}
}



function checkAssetNumber(partId,inventoryId,repairToStockDetailsId,inventoryLocationId){
	var jsonObject = new Object();
	console.log("inventoryLocationId",inventoryLocationId)
	 $("#repairToStockDetailsId").val(repairToStockDetailsId);
	$("#inventoryLocationPartId").val(inventoryId);
	 $("#inventoryLocationId").val(inventoryLocationId);
	 $("#repairToStockPartId").val(partId);
	 
	jsonObject["partId"] 					= partId;
	jsonObject["inventoryId"] 				= inventoryId;
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	jsonObject["repairToStockDetailsId"] 	= repairToStockDetailsId;

	$.ajax({
		url: "getAssetNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			console.log("datafasa",data)

			setAssetNumber(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}


function setAssetNumber(data){
	$("#assetTable").empty();
	$("#assetRepairTable").empty();
	var assetList = data.assetNumberList;
	var assetRepairList = data.repairFromAssetPartList;
	
	console.log("data",data);
	

	
	if(assetRepairList != undefined && assetRepairList != null){
		for(var index = 0 ; index < assetRepairList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'><input type='hidden' id='saveAssetId' name='saveAssetId' value="+assetRepairList[index].repairFromAssetPartId+" >"+ assetRepairList[index].assetNumber  +"</td>");
			
			$('#assetRepairTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		
		$("#assetModal").modal('show');
		columnArray = [];
		}
	
	
	
	
}



function checkAssetNumberForAdditionalPart(partId,inventoryId,quantity,repairStockToPartDetailsId){

	var jsonObject = new Object();
	 $("#repairAdditionalPartId").val(repairStockToPartDetailsId);
	jsonObject["partId"] 					= partId;
	jsonObject["inventoryId"] 				= inventoryId;
	jsonObject["quantity"] 					= quantity;
	jsonObject["repairToStockDetailsId"] 	=  $("#repairAdditionalPartId").val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();

	console.log("jsonObject",jsonObject);
	
	$.ajax({
		url: "getAssetNumberForAdditionalPart",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			console.log("hello....",data)

			setAssetNumberOfAdditionalPart(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	

}

function setAssetNumberOfAdditionalPart(data){
	$("#additionalPartAssetRepairTable").empty();
	
	var assetList 		= data.assetNumberList;
	var assetRepairList = data.repairToAssetPartList;
	
	
	console.log(">>>> data",data.quantity)
	console.log("00000",assetRepairList)
	
		
	if(assetRepairList != undefined || assetRepairList != null){
		for(var index = 0 ; index < assetRepairList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+ assetRepairList[index].assetNumber  +"</td>");
			
			$('#additionalPartAssetRepairTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		
		columnArray = [];
		}
	
	$("#additionalAssetModal").modal('show');
	
}
