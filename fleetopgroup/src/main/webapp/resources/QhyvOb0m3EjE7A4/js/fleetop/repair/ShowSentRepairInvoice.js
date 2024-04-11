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
	 console.log("repairStockToDetailsList",repairStockToDetailsList)
	 
	 if(repairStockToDetailsList != undefined || repairStockToDetailsList != null){
		 for(var index = 0 ; index < repairStockToDetailsList.length; index++){
			 var columnArray = new Array();
			 columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			 columnArray.push("<td class='fit'ar><a href='"+StockHyperLink+""+repairStockToDetailsList[index].repairStockPartId+"' target='_blank'>"+ repairStockToDetailsList[index].repairToStockName  +"</a></td>");
			 columnArray.push("<td class='fit ar'>" + repairStockToDetailsList[index].repairStatus +"</td>");
			 columnArray.push("<td class='fit ar'>" + repairStockToDetailsList[index].workDetails +"</td>");
			 if(repairStockToDetailsList[index].repairStatusId  != 2){
			 if($("#partWiseLabourCofig").val() == true || $("#partWiseLabourCofig").val() == 'true'){
				 if(repairStockToDetailsList[index].repairStatusId == 0 ){
					 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='showPartModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Part</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='showLabourModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Labour</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='rejectStock("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStockPartId+");'><span class='fa fa-edit'></span> Reject</a></td>");
				 }else{
					 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='showPartModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Part</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='showLabourModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Labour</a></td>");
				 }
			 }else{
				 console.log("repairStockToDetailsList[index].repairStatusId ",repairStockToDetailsList[index].repairStatusId )
				 console.log("HHHH",repairStockToDetailsList[index].serialNoAddedForParts)
				
				 if(repairStockToDetailsList[index].repairStatusId == 0 ){
					 if(repairStockToDetailsList[index].serialNoAddedForParts > 0 ){
						 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='showPartModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Part</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='rejectStock("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStockPartId+");'><span class='fa fa-edit'></span> Reject</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='checkAssetNumber("+repairStockToDetailsList[index].repairStockPartId+","+repairStockToDetailsList[index].inventoryLocationPartId+","+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].inventoryLocationId+" );'><span class='fa fa-edit'></span> Asset Number</a></td>");
					 }else{
						 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='showPartModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Part</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='rejectStock("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStockPartId+");'><span class='fa fa-edit'></span> Reject</a></td>");
					 }
				 }else{
					 if(repairStockToDetailsList[index].serialNoAddedForParts > 0 ){
						 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='showPartModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Part</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='checkAssetNumber("+repairStockToDetailsList[index].repairStockPartId+","+repairStockToDetailsList[index].inventoryLocationPartId+","+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].inventoryLocationId+");'><span class='fa fa-edit'></span> Asset Number</a></td>");
					 }else{
						 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='showPartModal("+repairStockToDetailsList[index].repairToStockDetailsId+","+repairStockToDetailsList[index].repairStatusId+");'><span class='fa fa-edit'></span> Add Part</a></td>");
					 }
				 }
			 }
			 }else{
				 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><em class='label label-danger'>"+repairStockToDetailsList[index].repairStatus+"</em></td>");
			 }
			 $('#sentRepairtockTableBody').append("<tr id='penaltyID"+repairStockToDetailsList[index].repairStockPart+"' >" + columnArray.join(' ') + "</tr>");

		 }
		 columnArray = [];
	 }else{
		 showMessage('info','No Record Found!')
	 }
}


function setRepairStockToPartAndLabourDetails(data){
	console.log("data",data)
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
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name/Number</th><th >Qty</th><th >UnitCost</th><th >TotalCost</th><th >Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name/Number</th><th >Qty</th><th >UnitCost</th><th >Discount</th><th >Gst</th><th >TotalCost</th><th >Action</th></tr>");
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
					columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeAdditionalPart("+repairStockToPartDetailsList[index].repairStockToPartDetailsId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+" );'><span class='fa fa-bin'></span> Remove</a></td>");

					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
					partTotalCost += repairStockToPartDetailsList[index].totalCost;
				}
				//totalCostRow
				$('#inprocessPartBody').append("<tr style='background: greenyellow;'><td "+ownPartTotalCostColSpanClass+">Total Part Cost</td><td >"+partTotalCost+"</td><td></td></tr>");
			}
			
			if(repairStockToLabourDetailsList != undefined){
				$('#inprocessPartBody').append("<tr  style='background: orchid;'><td colspan='8'>Labour Details</td></tr>");
				if( $("#repairWorkshopId").val() == 1){
					$('#inprocessPartBody').append("<tr><th>Sr</th><th "+ownLabourColSpanClass+" >Name</th><th "+ownLabourColSpanClass+">Hours</th><th>Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th>Sr</th><th  >Name</th><th >Hour</th><th>Rate</th><th>Discount</th><th>Gst</th><th>TotalCost</th><th>Action</th></tr>");
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
					columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeLabour("+repairStockToLabourDetailsList[index].repairToLabourDetailsId+");'><span class='fa fa-bin'></span> Remove</a></td>");

					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
					labourTotalCost += repairStockToLabourDetailsList[index].totalCost;
				}
				if($("#repairWorkshopId").val() == 2){
					$('#inprocessPartBody').append("<tr style='background: orchid;'><td colspan='6'>Total Labour Cost</td><td >"+labourTotalCost+"</td><td></td></tr>");
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
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name/Number</th><th >Qty</th><th >UnitCost</th><th >TotalCost</th><th >Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name/Number</th><th >Qty</th><th >UnitCost</th><th >Discount</th><th >Gst</th><th >TotalCost</th><th >Action</th></tr>");
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
					columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeAdditionalPart("+repairStockToPartDetailsList[index].repairStockToPartDetailsId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+" );'><span class='fa fa-bin'></span> Remove</a></td>");


					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
					partTotalCost += repairStockToPartDetailsList[index].totalCost;
				}
				//totalCostRow
				$('#inprocessPartBody').append("<tr style='background: greenyellow;'><td "+ownPartTotalCostColSpanClass+">Total Part Cost</td><td >"+partTotalCost+"</td><td></td></tr>");
			}
			$('#inprocessPartBody').append("<br><br><br>");

		}


	}else if( (data.repairStockToPartDetailsHM == undefined || Number(Object.keys(data.repairStockToPartDetailsHM).length) ==  0 )&& data.repairLabourDetailsHM != null && Number(Object.keys(data.repairLabourDetailsHM).length)  > 0){
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
					$('#inprocessPartBody').append("<tr><th>Sr</th><th "+ownLabourColSpanClass+">Name</th><th "+ownLabourColSpanClass+">Hours</th><th>Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th>Sr</th><th>Name</th><th >Hour</th><th >Rate</th><th>Discount</th><th>Gst</th><th>TotalCost</th><th>Action</th></tr>");
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
					columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeLabour("+repairStockToLabourDetailsList[index].repairToLabourDetailsId+");'><span class='fa fa-bin'></span> Remove</a></td>");

					$('#inprocessPartBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
				}

			}

			$('#inprocessPartBody').append("<br><br><br>");

		}

	}



}


function setRepairStockToPartAndDefaultLabourDetails(data){
	console.log("kkkiik",data)
	if(data.noAdditionalPartAndLabourFound != undefined && (data.noAdditionalPartAndLabourFound == true || data.noAdditionalPartAndLabourFound =='true')){
		  $("#noAdditionalPartAndLabourFound").val(true);
	 }else{
		 $("#noAdditionalPartAndLabourFound").val(false);
	 }
	if(Number(Object.keys(data.repairStockToPartDetailsHM).length) > 0 ){
		console.log("dsfjsdfhsdjfhgdfhdsfk")
		$("#additionalParId").show();
		
		for (var i in  data.repairStockHM) {
			
			console.log("dsfjsdfhsdjfhgdfhdsfk")
			var repairStockList 	= data.repairStockHM[i];
			
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
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name/Number</th><th >Qty</th><th >UnitCost</th><th >TotalCost</th><th >Action</th></tr>");
				}else{
					$('#inprocessPartBody').append("<tr><th >Sr</th><th >Name/Number</th><th >Qty</th><th >UnitCost</th><th >Discount</th><th >Gst</th><th >TotalCost</th><th >Action</th></tr>");
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
					console.log("yyyyyy")
					if($("#repairWorkshopId").val() == 1){
						columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeAdditionalPart("+repairStockToPartDetailsList[index].repairStockToPartDetailsId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+" );'><span class='fa fa-bin'></span> Remove</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='checkAssetNumberForAdditionalPart("+repairStockToPartDetailsList[index].partId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+","+repairStockToPartDetailsList[index].repairStockToPartDetailsId+");'><span class='fa fa-edit'></span> Asset Number </a></td>");
					}else{
						columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeAdditionalPart("+repairStockToPartDetailsList[index].repairStockToPartDetailsId+ ","+ repairStockToPartDetailsList[index].inventoryLocationId+ "," + repairStockToPartDetailsList[index].quantity+" );'><span class='fa fa-bin'></span> Remove</a></td>");
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
			columnPartArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeLabour("+defaultLabourList[index].repairToLabourDetailsId+");'><span class='fa fa-bin'></span> Remove</a></td>");

			$('#defaultLabourTableBody').append("<tr>" + columnPartArray.join(' ') + "</tr>");
		}
	}
}

function removeAdditionalPart(repairStockToPartDetailsId,inventoryLocationId,partQty){
	
	if(confirm("Are You Sure!, Do You Want To Remove Additional Part")){
	var jsonObject = new Object();
	jsonObject["repairStockToPartDetailsId"] 			= repairStockToPartDetailsId;
	jsonObject["inventoryLocationPartId"] 				= inventoryLocationId;
	jsonObject["partQty"] 								= partQty;
	jsonObject["repairStockId"] 						= $("#repairStockId").val();
	jsonObject["repairWorkshopId"] 						= $("#repairWorkshopId").val() ;
	jsonObject["companyId"] 							= $("#companyId").val();
	jsonObject["userId"] 								= $("#userId").val();
	
	$.ajax({
		url: "removeAdditionalPart",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Successully Removed')
			location.reload();
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
function removeLabour(repairToLabourDetailsId){
	if(confirm("Are You Sure!, Do You Want To Remove Labour")){
	var jsonObject = new Object();
	jsonObject["repairToLabourDetailsId"] 	= repairToLabourDetailsId;
	jsonObject["repairStockId"] 			= $("#repairStockId").val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	
	$.ajax({
		url: "removeLabour",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Successully Removed')
			location.reload();
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

function showPartModal(repairToStockDetailsId,repairStatusId){
	$("#repairToStockDetailsId").val(repairToStockDetailsId) /*** because every  time this  will change its according to each repair part*/
	$("#repairStatusId").val(repairStatusId)/***status Id of repairToStockDetails***/
	if($("#repairWorkshopId").val() == 1 ){
		$("#ownPartModal").modal('show')
	}else{
		$("#dealerPartModal").modal('show')
	}
}
function showLabourModal(repairToStockDetailsId,repairStatusId){
	$("#repairToStockDetailsId").val(repairToStockDetailsId)
	$("#repairStatusId").val(repairStatusId)/***status Id of repairToStockDetails***/
	if($("#repairWorkshopId").val() == 1 ){
		$("#ownLabourModal").modal('show')
	}else{
		$("#dealerLabourModal").modal('show')
	}
}

/** ***************** More Part According to workshop *************** */

$(document).ready(function() {
	var a = 500,
	partType =1,
	b = $("#moreOwnParts"),
	c = $(".moreOwnPartFieldButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row">'
					+'<div class="col col-sm-1 col-md-8">'
						+'<label class="has-float-label">'
						+'<input type="hidden" name="partName" id="ownPartId'+d+'" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;" >'
						+'<span style="color: #2e74e6;font-size: 18px;" >Part Name </span>'
						+'</label>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
						+'<label class="has-float-label">'
						+'<input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="ownPartQty"'
						+'onkeypress="return isNumberKeyWithDecimal(event,this.id);" >'
						+'<span style="color: #2e74e6;font-size: 18px;" >Qty</span>'
						+'</label>'
					+'</div>'
				+'<a href="#" class="removeOwnPart col col-sm-1 col-md-2"><font color="FF00000"><i class="fa fa-trash"></i> </a></font><br></div>'), 
					$("#ownPartId"+d).select2({
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
								return {
									results: $.map(a, function(a) {
										return $("#locationName").val() == a.location ? {
											text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
											slug: a.slug,
											id: a.inventory_location_id,
											warranty : a.isWarrantyApplicable,
											repairable : a.isRepairable,
											partId : a.partid,
											locationId : a.locationId,
											maxQuantity : a.location_quantity
										} : {
											text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
											slug: a.slug,
											id: a.inventory_location_id,
											disabled: !0,
											warranty : a.isWarrantyApplicable,
											repairable : a.isRepairable,
											partId : a.partid,
											locationId : a.locationId,
											maxQuantity : a.location_quantity
										}
									})
								}
							}
						}
					})
				)
		}), $(b).on("click", ".removeOwnPart", function(a) {
			a.preventDefault(), $(this).parent("div").remove(), d--
		})
	});

$(document).ready(function() {
	var a = 500,
	partType =1,
	b = $("#moreDealerParts"),
	c = $(".moreDealerPartFieldButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row">'
				+'<div class="col col-sm-1 col-md-3">'
				+'<label class="has-float-label">'
				+'<input type="hidden" name="partName" id="dealerPartId'+d+'" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;width:100%">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Part Name </span>'
				+'</label>'
				+'</div>'				
				+'	<div class="col col-sm-1 col-md-2">'
				+'	 <label class="has-float-label">'
				+'	    <input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="dealerPartQty'+d+'"'
				+'	     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );"'
				+'	     onblur="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Qty</span>'
				+'	  </label>'
				+'	</div>'
				+'	<div class="col col-sm-1 col-md-2">'
				+'	 <label class="has-float-label">'
				+'	    <input type="text" class="form-control browser-default custom-select noBackGround "  name="partEachCost" id="dealerPartEachCost'+d+'" maxlength="8"'
				+'	     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );"'
				+'	     onblur="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Cost</span>'
				+'	  </label>'
				+'	  <samp id="lastPartCost"> </samp>'
				+'	</div>'
				+'	<div class="col col-sm-1 col-md-1">'
				+'	 <label class="has-float-label">'
				+'	    <input type="text" class="form-control  browser-default  noBackGround allPartDiscount" name="partDiscount" id="dealerPartDiscount'+d+'"'
				+'	     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );"'
				+'	     onblur="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Dis<span class="partPercentAmountId"></span></span>'
				+'	  </label>'
				+'	   <samp id="lastPartDis"> </samp>'
				+'	</div>'
				+'	<div class="col col-sm-1 col-md-1">'
				+'	 <label class="has-float-label">'
				+'	    <input type="text" class="form-control  browser-default  noBackGround allPartTax" name="partGst" id="dealerPartTax'+d+'"'
				+'	     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );"'
				+'	     onblur="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Tax<span class="partPercentAmountId"></span></span>'
				+'	  </label>'
				+'	  <samp id="lastPartTax"> </samp>'
				+'	</div>'
				+'	<div class="col col-sm-1 col-md-2">'
				+'	 <label class="has-float-label">'
				+'	    <input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="partTotalCost" id="dealerPartTotalCost'+d+'" readonly="readonly"'
				+'	     onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );"'
				+'	     onblur="javascript:sumthere(\'dealerPartQty' + d + "', 'dealerPartEachCost" + d + "', 'dealerPartDiscount" + d + "','dealerPartTax" + d + "', 'dealerPartTotalCost" + d + "','"+partType+'\' );">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>'
				+'	  </label>'
				+'	  <samp id="lastPartTotalCost"> </samp>'
				+'	</div>'
				+'<a href="#" class="removeDealerPart col col-sm-1 col-md-1"><font color="FF00000"><em class="fa fa-trash"></em></a></font><br></div>'), 
					$("#dealerPartId"+d).select2({
						minimumInputLength: 3,
						minimumResultsForSearch: 10,
						ajax: {
							url: "searchAllMasterParts.in?Action=FuncionarioSelect2",
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
											text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
											slug: e.slug,
											id: e.partid,
											partNumber: e.partnumber,
											isWarrantyApplicable: e.isWarrantyApplicable,
											warrantyInMonths: e.warrantyInMonths
										}
									})
								}
							}
						},
						
					})
				)
				
			if($('#finalPartDiscountTaxTypId').val() == 2){
				$(".partPercentAmountId").html("");
			}else{
				$(".partPercentAmountId").html("%");
			}
		}), $(b).on("click", ".removeDealerPart", function(a) {
			a.preventDefault(), $(this).parent("div").remove(), d--
		})
	});

/** ***************** End More Part According to workshop *************** */


/** ***************** More Labour According to workshop *************** */

$(document).ready(function() {
	var a = 500,
	partType =1,
	b = $("#moreOwnLabour"),
	c = $(".moreOwnLabourFieldButton"),
	d = 1;
	$(c).click(function(c) {
		console.log("moreOwnLabour")
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row1">'
					+'<div class="col col-sm-1 col-md-8">'
						+'<label class="has-float-label">'
						+'<input type="hidden" name="labourName" id="ownLabourId'+d+'" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;" onchange="getLastOccurredDsePartDetails(this,lastPartOccurred,lastPartCost,lastPartDis,lastPartTax,true,partEachCost,partDiscount,partTax);">'
						+'<span style="color: #2e74e6;font-size: 18px;" >Labour Name </span>'
						+'</label>'
						+'<samp id="lastPartOccurred"> </samp>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround" name="labourHour"  id="ownLabourHour'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" >'
					+'<span style="color: #2e74e6;font-size: 18px;">Hours</span>'
					+'</label>'
					+'</div>'
				+'<a href="#" class="removeOwnLabour col col-sm-1 col-md-2"><font color="FF00000"><i class="fa fa-trash"></i></a></font><br></div>'), 
					$("#ownLabourId"+d).select2({
				        minimumInputLength: 2,
				        minimumResultsForSearch: 10,
				        ajax: {
				            url: "getTechinicianWorkOrder.in",
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
				                            text: a.driver_firstname + " - " + a.driver_Lastname,
				                            slug: a.slug,
				                            id: a.driver_id
				                        }
				                    })
				                }
				            }
				        },
				        createSearchChoice:function(term, results) {
				        	 if($('#autoLabourAdd').val()==true || $('#autoLabourAdd').val()=='true'){
				        	if ($(results).filter( function() {
				                return term.localeCompare(this.text)===0; 
				            }).length===0) {
				                return {id:term, text:term + ' [New]'};
				            }
				        	   }
				        },
				    })
				)
		}), $(b).on("click", ".removeOwnLabour", function(a) {
			a.preventDefault(), $(this).parent("div").remove(), d--
		})
	});

$(document).ready(function() {
	var a = 500,
	b = $("#moreDealerLabour"),
	c = $(".moreDealerLabourFieldButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row1" >'
				+'<div class="col col-sm-1 col-md-3">'
				+' <label class="has-float-label">'
				+'	 <input type="hidden" name="labourName" id="dealerLabourId'+d+'" class="browser-default " style="line-height: 30px;font-size: 15px;height: 35px;width:100%;">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Labour Name EE</span>'
				+'	</label>'
				+'</div>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround" name="labourHour"  id="dealerLabourHour'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerLabourHour' + d + "', 'dealerLabourPerHourCost" + d + "', 'dealerLabourDiscount" + d + "','dealerLabourTax" + d + "', 'dealerTotalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Hours</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround" name="labourUnitCost"  id="dealerLabourPerHourCost'+d+'"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerLabourHour' + d + "', 'dealerLabourPerHourCost" + d + "', 'dealerLabourDiscount" + d + "','dealerLabourTax" + d + "', 'dealerTotalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Rate/Hour</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-1">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default  noBackGround allLabourDiscount" id="dealerLabourDiscount'+d+'" name="labourDiscount"    onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerLabourHour' + d + "', 'dealerLabourPerHourCost" + d + "', 'dealerLabourDiscount" + d + "','dealerLabourTax" + d + "', 'dealerTotalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;">Dis<span class="labourPercentAmountId"></span></span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-1">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default  noBackGround allLabourTax " id="dealerLabourTax'+d+'" name="labourTax"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerLabourHour' + d + "', 'dealerLabourPerHourCost" + d + "', 'dealerLabourDiscount" + d + "','dealerLabourTax" + d + "', 'dealerTotalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Tax<span class="labourPercentAmountId"></span></span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="text" class="form-control browser-default custom-select noBackGround allLabourTotalCost" name="labourTotalCost"  id="dealerTotalLabourCost'+d+'"  readonly="readonly"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'dealerLabourHour' + d + "', 'dealerLabourPerHourCost" + d + "', 'dealerLabourDiscount" + d + "','dealerLabourTax" + d + "', 'dealerTotalLabourCost" + d + "','"+labourType+'\' );">'
				+'<span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>'
				+'</label>'
				+'</div>'
				+'<a href="#" class="removeDealerLabour col col-sm-1 col-md-1"><font color="FF00000"><i class="fa fa-trash"></i>  </a></font>'
				+'</div>'),
				$("#dealerLabourId"+d).select2({
			        minimumInputLength: 2,
			        minimumResultsForSearch: 10,
			        ajax: {
			            url: "getTechinicianWorkOrder.in",
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
			                            text: a.driver_firstname + " - " + a.driver_Lastname,
			                            slug: a.slug,
			                            id: a.driver_id
			                        }
			                    })
			                }
			            }
			        },
			        createSearchChoice:function(term, results) {
			        	 if($('#autoLabourAdd').val()==true || $('#autoLabourAdd').val()=='true'){
			        	if ($(results).filter( function() {
			                return term.localeCompare(this.text)===0; 
			            }).length===0) {
			                return {id:term, text:term + ' [New]'};
			            }
			        	   }
			        },
			    }))
			    if($('#finalLabourDiscountTaxTypId').val() == 2){
					$(".labourPercentAmountId").html("");
				}else{
					$(".labourPercentAmountId").html("%");
				}
	}), $(b).on("click", ".removeDealerLabour", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});


$(document).ready(function(){
	$("#dealerPartId").select2({
		minimumInputLength: 0,
		minimumResultsForSearch: 10,
		ajax: {
			url: "searchAllMasterParts.in?Action=FuncionarioSelect2",
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
							text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
							slug: e.slug,
							id: e.partid,
							partNumber: e.partnumber,
							isWarrantyApplicable: e.isWarrantyApplicable,
							warrantyInMonths: e.warrantyInMonths
						}
					})
				}
			}
		}
	}), $("#ownPartId").select2({
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
				return {
					results: $.map(a, function(a) {
						return $("#locationName").val() == a.location ? {
							text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
							slug: a.slug,
							id: a.inventory_location_id,
							warranty : a.isWarrantyApplicable,
							repairable : a.isRepairable,
							partId : a.partid,
							locationId : a.locationId,
							maxQuantity : a.location_quantity
						} : {
							text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
							slug: a.slug,
							id: a.inventory_location_id,
							disabled: !0,
							warranty : a.isWarrantyApplicable,
							repairable : a.isRepairable,
							partId : a.partid,
							locationId : a.locationId,
							maxQuantity : a.location_quantity
						}
					})
				}
			}
		}
})
	
	,$("#ownLabourId, #dealerLabourId").select2({
		minimumInputLength: 3,
		minimumResultsForSearch: 10,
		ajax: {
			url: "labourAutoComplete.in?Action=FuncionarioSelect2",
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
							text: a.labourName,
							slug: a.slug,
							id: a.labourId
						}
					})
				}
			}
		},createSearchChoice:function(term, results) {
		if ($(results).filter( function() {
			return term.localeCompare(this.text)===0; 
		}).length===0) {
			return {id:term, text:term + ' [New]'};
		}
		},
	}),$("#ownLabourId, #ownLabourId1").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTechinicianWorkOrder.in",
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
                            text: a.driver_firstname + " - " + a.driver_Lastname,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        },
        createSearchChoice:function(term, results) {
        	 if($('#autoLabourAdd').val()==true || $('#autoLabourAdd').val()=='true'){
        	if ($(results).filter( function() {
                return term.localeCompare(this.text)===0; 
            }).length===0) {
                return {id:term, text:term + ' [New]'};
            }
        	   }
        },
    })
})


var partType 			= 1;
var labourType 			= 2;



function addPartDetails(){
		showLayer();
		
		var jsonObject			= new Object();
		var partArr	 			= new Array();

		var inventoryLocationIdArr 			= new Array();
		var partIdArr 			= new Array();
		var partEachCostArr			= new Array();
		var partQtyArr			= new Array();
		var partGstArr			= new Array();
		var partDiscountArr		= new Array();
		var partTotalCostArr	= new Array();

		if($("#repairWorkshopId").val() == 1){
			$("input[name=partName]").each(function(){
				if($(this).val() != "" && $(this).val() != null && $(this).val() != undefined){
					partIdArr.push($("#"+this.id).select2('data').partId);
				}
				inventoryLocationIdArr.push($(this).val());
			});												
		}else{
			$("input[name=partName]").each(function(){
			//	console.log(">>",$(this).val())
				partIdArr.push($(this).val());
			});	
		}
		
		$("input[name=partEachCost]").each(function(){
			partEachCostArr.push($(this).val());
		});
		
		$("input[name=partQty]").each(function(){
			partQtyArr.push($(this).val());
		});
		$("input[name=partGst]").each(function(){
			partGstArr.push($(this).val());
		});
		$("input[name=partDiscount]").each(function(){
			partDiscountArr.push($(this).val());
		});
		$("input[name=partTotalCost]").each(function(){
			partTotalCostArr.push($(this).val());
		});

		if(partIdArr.length <= 0){
			showMessage('info','Please Select Part Details');
			hideLayer();
			return false;
		}
		for(var i =0 ; i< partIdArr.length; i++){
			var partDetails					= new Object();
				
			if($("#repairWorkshopId").val() == 1){
				if(inventoryLocationIdArr[i] == '' || inventoryLocationIdArr[i] == 0){
					showMessage('info','Please Select Part Details');
					hideLayer();
					return false;
				}
			}else{
				if(partIdArr[i] == '' || partIdArr[i] == 0){
					showMessage('info','Please Select Part Details');
					hideLayer();
					return false;
				}
			}
				if(partQtyArr[i] == '' || partQtyArr[i] == 0){
					showMessage('info','Please Enter Part Quantity');
					hideLayer();
					return false;
				}
				
				if($("#repairWorkshopId").val() == 1){
					partDetails.inventoryLocationId			= inventoryLocationIdArr[i];
				}
				partDetails.partId						= partIdArr[i];
				partDetails.partQty						= partQtyArr[i];
				partDetails.partUnitCost				= partEachCostArr[i];
				partDetails.partGst						= partGstArr[i];
				partDetails.partDiscount				= partDiscountArr[i];
				partDetails.partTotalCost				= partTotalCostArr[i];
				
				
				partArr.push(partDetails);
		}

		jsonObject.partDetails 					= JSON.stringify(partArr);
		jsonObject["repairStatusId"] 			= $("#repairStatusId").val();		/***status Id of repairToStockDetails***/		
		jsonObject["finalPartDiscountTaxTypId"] = $("#finalPartDiscountTaxTypId").val();				
		jsonObject["repairWorkshopId"] 			= $("#repairWorkshopId").val();				
		jsonObject["repairStockId"] 			= $("#repairStockId").val();				
		jsonObject["locationId"] 				= $("#locationId").val();				
		jsonObject["repairToStockDetailsId"] 	= $("#repairToStockDetailsId").val();				
		jsonObject["companyId"]					= $('#companyId').val();
		jsonObject["userId"]					= $('#userId').val();
		
		console.log("jsonObject",jsonObject)
		
		$.ajax({
			url: "saveRepairStockToPartDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			location.reload();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}



function addLabourDetails(){
	console.log("dsfsdf");
				showLayer();
				
				var jsonObject				= new Object();
				var labourArr	 				= new Array();
				var labourIdArr 			= new Array();
				var labourHourArr			= new Array();
				var labourUnitCostArr		= new Array();
				var labourTaxArr			= new Array();
				var labourDiscountArr		= new Array();
				var labourTotalCostArr		= new Array();

				$("input[name=labourName]").each(function(){
				// console.log($(this).val())
					labourIdArr.push($(this).val());
				});
				$("input[name=labourHour]").each(function(){
					labourHourArr.push($(this).val());
				});
				$("input[name=labourUnitCost]").each(function(){
					labourUnitCostArr.push($(this).val());
				});
				$("input[name=labourTax]").each(function(){
					labourTaxArr.push($(this).val());
				});
				$("input[name=labourDiscount]").each(function(){
					labourDiscountArr.push($(this).val());
				});
				$("input[name=labourTotalCost]").each(function(){
					labourTotalCostArr.push($(this).val());
				});
				

				for(var i =0 ; i< labourIdArr.length; i++){
					var labourDetails					= new Object();
						
						if(labourIdArr[i] == '' || labourIdArr[i] == 0){
							showMessage('info','Please Select Labour Details');
							hideLayer();
							return false;
						}
						if(labourHourArr[i] == '' || labourHourArr[i] == 0){
							showMessage('info','Please Enter Labour Hour');
							hideLayer();
							return false;
						}
						
						
						labourDetails.labourId			= labourIdArr[i];
						labourDetails.hours				= labourHourArr[i];
						labourDetails.unitCost			= labourUnitCostArr[i];
						labourDetails.gst				= labourTaxArr[i];
						labourDetails.discount			= labourDiscountArr[i];
						labourDetails.totalCost			= labourTotalCostArr[i];
						
						labourArr.push(labourDetails);
				}

				jsonObject.labourDetails 				= JSON.stringify(labourArr);
				jsonObject["repairStatusId"] 			= $("#repairStatusId").val();	/***status Id of repairToStockDetails***/
				jsonObject["finalLabourDiscountTaxTypId"] = $("#finalLabourDiscountTaxTypId").val();
				jsonObject["repairStockId"] 			= $("#repairStockId").val();	
				jsonObject["partWiseLabourCofig"] 		= $("#partWiseLabourCofig").val();				
				jsonObject["locationId"] 				= $("#locationId").val();				
				jsonObject["repairToStockDetailsId"] 	= $("#repairToStockDetailsId").val();				
				jsonObject["companyId"]					= $('#companyId').val();
				jsonObject["userId"]					= $('#userId').val();
				
				console.log("jsonObject",jsonObject)
				
				$.ajax({
					url: "saveRepairStockToLabourDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
					location.reload();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
}


function sumthere(a, b, c, d, e,partLabourTypeId ) {
	var disTaxId;
	if(partLabourTypeId == partType){
		disTaxId = $('#finalPartDiscountTaxTypId').val();
	}else if(partLabourTypeId == labourType){
		disTaxId = $('#finalLabourDiscountTaxTypId').val();
	}
	
	if(disTaxId == 2){
		var f = Number(document.getElementById(a).value),
		g = Number(document.getElementById(b).value),
		h = Number(document.getElementById(c).value),
		i = Number(document.getElementById(d).value),
		j = parseFloat(f) * parseFloat(g),
		k = Number(j) - Number(h),
		m = Number(k) + Number(i);
		isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
		if(m.toFixed(2) < 0){
			showMessage('info','Discount Amount Can Not Be Greater Than Total Cost')
			$('#'+c).val('');
			$('#'+d).val('');
			$('#'+e).val('');
		}else if($('#'+d).val() > j){
			showMessage('info','Tax Amount Can Not Be Greater Than Sub-Amount: '+j.toFixed(2)+'')
			$('#'+d).val('');
			$('#'+e).val('');
		}

	} else {
		var f = Number(document.getElementById(a).value),
		g = Number(document.getElementById(b).value),
		h = Number(document.getElementById(c).value),
		i = document.getElementById(d).value,
		j = parseFloat(f) * parseFloat(g),
		k = j * h / 100,
		l = j - k,
		a = l * i / 100,
		m = l + a;
		isNaN(m) || (document.getElementById(e).value = m.toFixed(2))

	}

}

function validatePartTaxDiscount(part_Tax_discountId){
	if($("#finalPartDiscountTaxTypId").val()  == 1){
		if($("#"+part_Tax_discountId).val() >= 100){
			showMessage('info','Tax And Discount Can Not Be Greater Than 100');
			$("#"+part_Tax_discountId).val('');
		}
	}
}
function validateLabourTaxDiscount(labour_Tax_discountId){
	if($("#finalLabourDiscountTaxTypId").val()  == 1){
		if($("#"+labour_Tax_discountId).val() >= 100){
			showMessage('info','Tax And Discount Can Not Be Greater Than 100');
			$("#"+labour_Tax_discountId).val('');
		}
	}
}


var partType 	= 1;
var labourType 	= 2;
var percentageType 	= 1;
var amountType 	= 2;
function selectDiscountTaxType(partLabourTypeId,percentageAmountTypeId){
	
	console.log("partLabourTypeId",partLabourTypeId)
	console.log("percentageAmountTypeId",percentageAmountTypeId)
	

//	var confirm = confirm("Are You Sure!, Do You Want To Change Discount/Tax Type, So You Need To Enter It Again"); 
	
	if(confirm("Are You Sure!, Do You Want To Change Discount/Tax Type, So You Need To Enter It Again")){
		console.log("gggg")
		
		if(partLabourTypeId == partType){
			if(percentageAmountTypeId == amountType){
				setTimeout(function(){ 
				$('#finalPartDiscountTaxTypId').val(2);
				$('#partAmountLabelId').addClass('focus active')
				$('#partPercentLabelId').removeClass('focus active')
				$(".partPercentAmountId").html("");
				}, 30);
			}else{
				setTimeout(function(){ 
				$('#finalPartDiscountTaxTypId').val(1);
				$('#partAmountLabelId').removeClass('focus active')
				$('#partPercentLabelId').addClass('focus active')
				$(".partPercentAmountId").html("%");
				}, 30);
			}
			
			$("input[name=partEachCost]").each(function(){
				$("#"+this.id).val('');
			});
			
			$("input[name=partQty]").each(function(){
				$("#"+this.id).val('');
			});
			$("input[name=partGst]").each(function(){
				$("#"+this.id).val('');
			});
			$("input[name=partDiscount]").each(function(){
				$("#"+this.id).val('');
			});
			$("input[name=partTotalCost]").each(function(){
				$("#"+this.id).val('');
			});
			
		}else{
			if(percentageAmountTypeId == amountType){
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(2);
				$('#labourAmountLabelId').addClass('focus active')
				$('#labourPercentLabelId').removeClass('focus active')
				$(".labourPercentAmountId").html("");
				}, 30);
			}else{
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(1);
				$('#labourAmountLabelId').removeClass('focus active')
				$('#labourPercentLabelId').addClass('focus active')
				$(".labourPercentAmountId").html("%");
				}, 30);
			}
			
			$("input[name=labourHour]").each(function(){
				$("#"+this.id).val('');
			});
			$("select[name=labourUnitCost]").each(function(){
				$("#"+this.id).val('');
			});
			$("input[name=labourTax]").each(function(){
				$("#"+this.id).val('');
			});
			$("input[name=labourDiscount]").each(function(){
				$("#"+this.id).val('');
			});
			$("input[name=labourTotalCost]").each(function(){
				$("#"+this.id).val('');
			});
			
		}
	}else{
		if(partLabourTypeId == partType){
			if(percentageAmountTypeId == percentageType){
				setTimeout(function(){ 
					console.log("11111111111")
				$('#finalPartDiscountTaxTypId').val(2);
				$(".partPercentAmountId").html("");
				$("#partAmountId").prop('checked',true);
				$("#partPercentId").prop('checked',false);
				$('#partAmountLabelId').addClass('focus active')
				$('#partPercentLabelId').removeClass('focus active')
				}, 30);
			}else{
				setTimeout(function(){ 
					console.log("22222222")
					$('#finalPartDiscountTaxTypId').val(1);
					$(".partPercentAmountId").html("%");
					$("#partPercentId").prop('checked',true);
					$("#partAmountId").prop('checked',false);
					$('#partPercentLabelId').addClass('focus active')
					$('#partAmountLabelId').removeClass('focus active')
					
					
				}, 30);
				
			}
		}else {
			if(percentageAmountTypeId == percentageType){
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(2);
				$(".labourPercentAmountId").html("");
				$('#labourAmountLabelId').addClass('focus active')
				$('#labourPercentLabelId').removeClass('focus active')
				$("#labourAmountId").prop('checked',true);
				$("#labourPercentId").prop('checked',false);
				}, 30);
			}else{
				setTimeout(function(){ 
				$('#finalLabourDiscountTaxTypId').val(1);
				$(".labourPercentAmountId").html("%");
				$('#labourPercentLabelId').addClass('focus active')
				$('#labourAmountLabelId').removeClass('focus active')
				$("#labourPercentId").prop('checked',true);
				$("#labourAmountId").prop('checked',false);
				}, 30);
			}
		}
	}
}

function moveToCreatedRepairInvoice(){
	if(confirm("Are You Sure!, Do You Want To Re-Enter Data?")){
		if($("#noAdditionalPartAndLabourFound").val() == true || $("#noAdditionalPartAndLabourFound").val()	 == 'true'){
			
			var jsonObject = new Object();
			jsonObject["repairWorkshopId"] 			= $("#repairWorkshopId").val();	
			jsonObject["repairTypeId"] 				= $("#repairTypeId").val();
			jsonObject["repairStockId"] 			= $("#repairStockId").val();
			jsonObject["companyId"] 				= $("#companyId").val();
			jsonObject["userId"] 					= $("#userId").val();
			
			$.ajax({
				url: "moveToCreatedRepairInvoice",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					window.location.replace("showRepairInvoice?repairStockId="+$("#repairStockId").val()+"");
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
			 
		}else{
			showMessage('info','Please Remove All Additional Part/Labour')
		}
	}else{
		location.reload();
	}
}

function rejectStock(repairToStockDetailsId,repairStockPartId){
	$("#repairStockPartId").val(repairStockPartId);
	$("#repairToStockDetailsId").val(repairToStockDetailsId);
	$("#rejectModal").modal('show');
}
function saveRejectStock(repairToStockDetailsId){
	if(confirm("Are You Sure!, Do You Want To Reject Stock?")){
	var jsonObject = new Object();
	jsonObject["repairToStockDetailsId"] 	= $("#repairToStockDetailsId").val();
	jsonObject["repairTypeId"] 				= $("#repairTypeId").val();
	jsonObject["repairStockPartId"] 		= $("#repairStockPartId").val();
	jsonObject["rejectRemark"] 				= $("#rejectRemark").val();
	jsonObject["rejectStockMoveTo"] 		= $("#rejectStockMoveTo").val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	
	$.ajax({
		url: "rejectStock",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Reject Stock Successfully')
			location.reload();
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
function completeRepairModal(){
	$("#remarkModal").modal('show');
}

function completeRepair(){
	var message = "";
	if($("#noAdditionalPartAndLabourFound").val() == true || $("#noAdditionalPartAndLabourFound").val() == 'true'){
		message = "You Are Not Applied Additional Part/Labour Do You Want To Complete Repair Stock?"
	}else{
		message = "Do You Want To Complete Repair Stock?"
	}
	if($('#completedDate').val() == "" || $('#completedDate').val() == undefined){
		showMessage('info','Please Select completedDate Date');
		hideLayer();
		return false;
	}
	
	if(($('#remark').val() == undefined ||  $('#remark').val().trim() == "") ){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
	}
	
	var assetArr	 		= new Array();

	if(confirm("Are You Sure!, "+message+"")){
	var jsonObject = new Object();
	jsonObject["repairStockId"] 			= $("#repairStockId").val();
	jsonObject["repairTypeId"] 				= $("#repairTypeId").val();
	jsonObject["completedDate"]				= $('#completedDate').val();
	jsonObject["remark"]					= $('#remark').val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	jsonObject["assetIds"] 					= assetArr.toString();
	jsonObject["fromLocationId"] 			= $("#fromLocationId").val();
	jsonObject["toLocationId"] 				= $("#locationId").val();
	
	console.log("jsonObject",jsonObject)
	
	$.ajax({
		url: "completeRepair",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Completed Successfully..')
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
	
//	console.log("assetList",assetList)
//	console.log("assetRepairList",assetRepairList)
	
	if(assetList != undefined && assetList != null){
		for(var index = 0 ; index < assetList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'><input type='hidden' value="+assetList[index].partWarrantyDetailsId+" >"+ assetList[index].partSerialNumber  +"</td>");
			columnArray.push("<td class='fit'><input type='checkbox' value="+assetList[index].partWarrantyDetailsId+" id='assetId' name='assetId'  ></td>");
			
			$('#assetTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		
		columnArray = [];
		}
	
	if(assetRepairList != undefined && assetRepairList != null){
		for(var index = 0 ; index < assetRepairList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'><input type='hidden' id='saveAssetId' name='saveAssetId' value="+assetRepairList[index].repairFromAssetPartId+" >"+ assetRepairList[index].assetNumber  +"</td>");
			columnArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeAsset("+assetRepairList[index].repairFromAssetPartId+", "+assetRepairList[index].assetId+");'><span class='fa fa-bin'></span> Remove</a></td>");
			
			$('#assetRepairTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		
		columnArray = [];
		}
	
	if(assetList != undefined  || assetRepairList != undefined){
		$("#assetModal").modal('show');
	}
	
	
}

function sentAssetNumber(){
	showLayer();
	
	var jsonObject			= new Object();
	var assetFinalArr	 	= new Array();

	var assetArr	 		= new Array();
	$('input[name*=assetId]:checked').each(function(){
		assetArr.push($(this).val());
	});
	console.log('assetArr',assetArr.length)
	
	if(Number(assetArr.length) == 0 ){
		$("#assetModal").modal('hide');
		showMessage('info','Please Select Asset Number');
		hideLayer();
		return false;
	}
	
	for(var i =0 ; i< assetArr.length; i++){
		var assetDetails					= new Object();
		
		assetDetails.assetId					= assetArr[i];
		assetDetails.repairToStockDetailsId		= $("#repairToStockDetailsId").val();
		assetDetails.partId						= $("#repairToStockPartId").val();
		assetDetails.companyId 					= $("#companyId").val();
		assetDetails.userId						= $("#userId").val();
		assetFinalArr.push(assetDetails);
	}
	jsonObject["companyId"] 					= $("#companyId").val();
	jsonObject["userId"] 						= $("#userId").val();
	jsonObject["partQty"] 						= assetArr.length;
	jsonObject["partId"] 						= $("#repairToStockPartId").val();
	jsonObject["inventoryLocationId"] 			= $("#inventoryLocationId").val();
	jsonObject["assetNumberFlag"] 				= true;
	jsonObject.assetDetails 					= JSON.stringify(assetFinalArr);
	
	$.ajax({
		url: "sentAssetNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function removeAsset(repairFromAssetPartId,assetId){
	showLayer();
	
	var jsonObject			= new Object();
	jsonObject["repairFromAssetPartId"] 			= repairFromAssetPartId;
	jsonObject["assetId"] 							= assetId;
	jsonObject["repairToStockDetailsId"] 			= $("#repairToStockDetailsId").val();
	jsonObject["companyId"] 						= $("#companyId").val();
	jsonObject["userId"] 							= $("#userId").val();
	jsonObject["repairWorkshopId"] 					= $("#repairWorkshopId").val();
	jsonObject["inventoryLocationPartId"] 			= $("#inventoryLocationPartId").val();
	jsonObject["partQty"] 							= 1;
	
	$.ajax({
		url: "removeAsset",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
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
			console.log(">>>>>>>>>>>>>>>",data)

			setAssetNumberOfAdditionalPart(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	

}

function setAssetNumberOfAdditionalPart(data){
	$("#additionalPartAssetTable").empty();
	$("#additionalPartAssetRepairTable").empty();
	console.log("hemant",data.repairToAssetPartList.length)
	$("#savedAdditionalAssetQuantity").val(data.repairToAssetPartList.length);
	
	var assetList 		= data.assetNumberList;
	var assetRepairList = data.repairToAssetPartList;
	
	
	console.log(">>>> data",data.quantity)
	console.log("00000",assetRepairList)
	
	if(assetList != undefined || assetList != null){
		for(var index = 0 ; index < assetList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'><input type='hidden' value="+assetList[index].partWarrantyDetailsId+" >"+ assetList[index].partSerialNumber  +"</td>");
			columnArray.push("<td class='fit'><input type='checkbox' value="+assetList[index].partWarrantyDetailsId+" id='additionalAssetId' name='additionalAssetId'  ></td>");
			
			$('#additionalPartAssetTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		
		columnArray = [];
		}
	
	if(assetRepairList != undefined || assetRepairList != null){
		for(var index = 0 ; index < assetRepairList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+ assetRepairList[index].assetNumber  +"</td>");
			columnArray.push("<td class='fit'><a href='#' class='confirmation' onclick='removeAdditionalAsset("+assetRepairList[index].repairToAssetPartId+", "+assetRepairList[index].assetId+");'><span class='fa fa-bin'></span> Remove</a></td>");
			
			$('#additionalPartAssetRepairTable').append("<tr>" + columnArray.join(' ') + "</tr>");
		}
		
		columnArray = [];
		}
	
	$("#additionalPartQuantity").val(data.quantity);
	$("#additionalAssetModal").modal('show');
	
}


function saveAdditionalAsset(){
	showLayer();
	
	var jsonObject			= new Object();
	var assetArr	 		= new Array();
	var assetFinalArr	 	= new Array();
	var additionalAssetPartQuantity 			= 0;
	var savedAdditionalAssetPartQuantity 		= 0;

	$('input[name*=additionalAssetId]:checked').each(function(){
		assetArr.push($(this).val());
	});
	
	console.log(">>>", assetArr.length)
	console.log(":::",Number($("#additionalPartQuantity").val()))
	
	additionalAssetPartQuantity 		= Number($("#additionalPartQuantity").val());
	savedAdditionalAssetPartQuantity 	= Number($("#savedAdditionalAssetQuantity").val()); 
	
	console.log("additionalAssetPartQuantity",additionalAssetPartQuantity)
	console.log("asset Length",assetArr.length)
	console.log("saved qty",savedAdditionalAssetPartQuantity)
	
	if((assetArr.length+savedAdditionalAssetPartQuantity)  > additionalAssetPartQuantity){
		showMessage('info','Additional Quantity Can Not Be Greater Than Main Quantity');
		hideLayer();
		return false;
	}
	
	
	
	for(var i =0 ; i< assetArr.length; i++){
		var assetDetails					= new Object();
		
		assetDetails.assetId					= assetArr[i];
		assetDetails.repairToStockDetailsId		= $("#repairAdditionalPartId").val();
		assetDetails.companyId 					= $("#companyId").val();
		assetDetails.userId						= $("#userId").val();
		assetFinalArr.push(assetDetails);
	}

	jsonObject.assetDetails 					= JSON.stringify(assetFinalArr);
	
	$.ajax({
		url: "saveAdditionalAsset",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function removeAdditionalAsset(repairToAssetPartId, assetId){

	showLayer();
	
	var jsonObject			= new Object();
	jsonObject["repairToAssetPartId"] 				= repairToAssetPartId;
	jsonObject["assetId"] 							= assetId;
	jsonObject["repairToStockDetailsId"] 			= $("#repairAdditionalPartId").val();
	jsonObject["companyId"] 						= $("#companyId").val();
	jsonObject["userId"] 							= $("#userId").val();
	
	console.log(">>>",jsonObject)
	$.ajax({
		url: "removeAdditionalAsset",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
		location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	

}