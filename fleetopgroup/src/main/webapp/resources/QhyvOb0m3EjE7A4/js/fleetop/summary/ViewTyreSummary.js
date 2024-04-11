$(document).ready(function() {
	$("#rowHeader").html('All Location Tyre Count');
	
	$.getJSON("allLocationDropdown.in", function(e) {
		console.log("e",e)
		companyList	= e;//To get All Company Name 
		$("#locationId").empty();
		$("#locationId").append($("<option>").text("All ").attr("value",0));
		$('#locationId').select2();

		for(var k = 0; k <companyList.length; k++){
			$("#locationId").append($("<option>").text(companyList[k].partlocation_name).attr("value", companyList[k].partlocation_id));
		}

	});	
	
	/*$("#locationId").select2( {
        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
                        }
                    }
                    )
                }
            }
        }
    }), */$("#vid").select2( {
		minimumInputLength:2, minimumResultsForSearch:10, ajax: {
			url:"getActiveVehicleDropdown.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
				return {
					term: a
				}
			}
		, results:function(a) {
			return {
				results:$.map(a, function(a) {
					return {
						text: a.vehicle_registration, slug: a.slug, id: a.vid, vehicleModelId: a.vehicleModelId
					}
				})
			}
		}
	}
})
});


$(document).ready(function() {
	getTyreStockCount();
	getAssignedTyreAllocation();
	getVehicleWithoutTyre();
	getMaxTyreRun();
	getMinTyreRun();
	getIssueTyreDetails();
});

function getTyreStockCount(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["statusId"]			= $('#statusId').val();
	jsonObject["locationId"]		= $('#locationId').val();
	jsonObject["compId"]			= $('#compId').val();
	$.ajax({
		url: "tyreStockCount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTyreStockCount(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function setTyreStockCount(data){
	$("#allCount").html(data.tyreStockCount);
	$("#availableCount").html(data.availableCount);
	$("#inServiceCount").html(data.inServiceCount);
	$("#unAvailableCount").html(data.unAvailableCount);
	$("#sentRetreadCount").html(data.sentRetreadCount);
	$("#scrapCount").html(data.scrapCount);
	$("#soldCount").html(data.soldCount);
}

function getAssignedTyreAllocation(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["vid"]				= $('#vid').val();
	jsonObject["companyId"]			= $('#compId').val();
	$.ajax({
		url: "getAssignedTyreAllocation",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAssignedTyreAllocation(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setAssignedTyreAllocation(data){
	
	if(data.vehicleTyreLayoutPositionList != undefined){
		$('#vehicleTyreLayoutPositionTable').empty();
		var vehicleTyreLayoutPositionList = data.vehicleTyreLayoutPositionList;
		$("#assignCount").html(vehicleTyreLayoutPositionList.length);
		
		for(var index = 0 ; index < vehicleTyreLayoutPositionList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'><a target='_blank' href='showVehicleTyreAssignedDetails?id="+vehicleTyreLayoutPositionList[index].vehicle_ID+"'>"+ vehicleTyreLayoutPositionList[index].vehicleRegistration  +"</td>");
			columnArray.push("<td class='fit'>" + vehicleTyreLayoutPositionList[index].position +"</td>");
			columnArray.push("<td class='fit'>" + vehicleTyreLayoutPositionList[index].tyre_SERIAL_NO +"</td>");
			
			$('#vehicleTyreLayoutPositionTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
			
		}
		if($('#vid').val() != "" && $('#vid').val() != undefined){
			$("#vehicleTyreLayoutPositionModal").modal('show');
		}
	}
}

function getAllocation(){
	$("#vehicleTyreLayoutPositionModal").modal('show');
}

function getVehicleWithoutTyre(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#compId').val();
	$.ajax({
		url: "getVehicleWithoutTyre",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleWithoutTyre(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleWithoutTyre(data){
	var layoutNotCreatedCount = 0;
	var tyreNotAssignedCount = 0;
	if(data.vehicleWithoutTyreList != undefined){
		$('#vehicleWithoutTyreTable').empty();
		var vehicleWithoutTyreList = data.vehicleWithoutTyreList;
		tyreNotAssignedCount = data.tyreNotAssignedCount;
		var Rear = "Rear";
		
		for(var index = 0 ; index < vehicleWithoutTyreList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>" + vehicleWithoutTyreList[index].position +"</td>");
			columnArray.push("<td class='fit'>"+ vehicleWithoutTyreList[index].vehicleRegistration  +"</td>");
			if(vehicleWithoutTyreList[index].position == "RO-1"){
				columnArray.push("<td class='fit'><a target='_blank' class='btn btn-success' href='vehicleTyreAssignment.in?data="+ vehicleWithoutTyreList[index].vehicle_ID+","+ vehicleWithoutTyreList[index].lp_ID+",'Front Right'' >Asign Tyre</a></td>");
			}else if(vehicleWithoutTyreList[index].position == "LO-1"){
				columnArray.push("<td class='fit'><a target='_blank' class='btn btn-success' href='vehicleTyreAssignment.in?data="+ vehicleWithoutTyreList[index].vehicle_ID+","+ vehicleWithoutTyreList[index].lp_ID+",'Front Left'' >Asign Tyre</a></td>");
			}else{
				columnArray.push("<td class='fit'><a target='_blank' class='btn btn-success' href='vehicleTyreAssignment.in?data="+ vehicleWithoutTyreList[index].vehicle_ID+","+ vehicleWithoutTyreList[index].lp_ID+","+Rear +" "+ vehicleWithoutTyreList[index].position+"' >Asign Tyre</a></td>");
			}
			$('#vehicleWithoutTyreTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
			
		}
		vehicleGrouping();
		
	}
	
	if(data.tyreLayoutNotCreatedVehicleList != undefined){
		$('#tyreLayoutNotCreatedVehicleTable').empty();
		var tyreLayoutNotCreatedVehicleList = data.tyreLayoutNotCreatedVehicleList;
		console.log("with lentght",tyreLayoutNotCreatedVehicleList)
		console.log("lay",vehicleWithoutTyreList.length)
		layoutNotCreatedCount = 	tyreLayoutNotCreatedVehicleList.length;
		for(var index = 0 ; index < tyreLayoutNotCreatedVehicleList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'>"+ tyreLayoutNotCreatedVehicleList[index].vehicle_registration  +"</td>");
			columnArray.push("<td class='fit'>"+ tyreLayoutNotCreatedVehicleList[index].vehicle_Model  +"</td>");
			columnArray.push("<td class='fit'><a target='_blank'  class='btn btn-success' href='vehicleModelTyreLayout.in?id="+ tyreLayoutNotCreatedVehicleList[index].vehicleModelId+"' >Create Layout</a></td>");
			$('#tyreLayoutNotCreatedVehicleTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
			
		}
		
	}
	$("#tyreNotAssignAllVehicleCount").html(Number(tyreNotAssignedCount+layoutNotCreatedCount))

}

function showTyreNotAssignModal(){
	$("#vehicleWithoutTyreModal").modal('show');
}

function enterMaxTyreRun(e){
	
	var code = (e.keyCode ? e.keyCode : e.which);
	console.log("code",code)
	if(code == 13) { //Enter keycode
		getMaxTyreRun();
	}
}

function getMaxTyreRun(){

	showLayer();
	var jsonObject					= new Object();
	jsonObject["maxLimit"]			= $('#maxLimit').val();
	jsonObject["companyId"]			= $('#compId').val();
	
	$.ajax({
		url: "getMaxTyreRun",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setMaxTyreRun(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}


function setMaxTyreRun(data){
	console.log("maxx",data);
	if(data.maxTyreList != undefined){
		$('#maxTyreRunTable').empty();
		var maxTyreList = data.maxTyreList;
		$("#maxRun").html(maxTyreList.length);
		
		for(var index = 0 ; index < maxTyreList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'><a target='_blank' href='showTyreInfo?Id="+maxTyreList[index].tyre_ID+"' >"+ maxTyreList[index].tyre_NUMBER  +"</a></td>");
			columnArray.push("<td class='fit'>" + maxTyreList[index].tyre_USEAGE +"</td>");
			
			$('#maxTyreRunTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
			
		}
		
	}else{
		$("#maxRun").html(0);
	}
}
function getMaxTyre(){
	$("#maxTyreRunModal").modal('show');
}

function enterMinTyreRun(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		getMinTyreRun();
	}
}

function getMinTyreRun(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["minLimit"]			= $('#minLimit').val();
	jsonObject["companyId"]			= $('#compId').val();
	
	$.ajax({
		url: "getMinTyreRun",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setMinTyreRun(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setMinTyreRun(data){
	if(data.minTyreList != undefined){
		$('#minTyreRunTable').empty();
		var minTyreList = data.minTyreList;
		$("#minScrap").html(minTyreList.length);
		
		for(var index = 0 ; index < minTyreList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'><a target='_blank' href='showTyreInfo?Id="+minTyreList[index].tyre_ID+"' >"+ minTyreList[index].tyre_NUMBER  +"</a></td>");
			columnArray.push("<td class='fit'>" + minTyreList[index].tyre_USEAGE +"</td>");
			
			$('#minTyreRunTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
		}
		
	}else{
		$("#minScrap").html(0);
	}
}


function getMinScrapTyre(){
	$("#minTyreRunModal").modal('show');
}

function changeLocation(){
	getTyreStockCount();
	
	if($("#locationId").val() == "" || $("#locationId").val() == 0){
		$("#rowHeader").html('All Location Tyre Count');
		$("#allLocationRow").collapse('hide');
	}else{
		$("#rowHeader").html($('#locationId').select2('data').text +" Tyre Count");
	}
	$("#allLocationRow").collapse('hide');
}

function getTyreDetails(statusId){

	showLayer();
	var jsonObject					= new Object();
	jsonObject["statusId"]			= statusId;
	jsonObject["locationId"]		= $('#locationId').val();
	if($('#locationId').val() != ""){
		jsonObject["locationName"]		= $('#locationId').select2('data').text;
	}else{
		jsonObject["locationName"]		= "";
		
	}
	jsonObject["compId"]			= $('#compId').val();
	$.ajax({
		url: "getTyreStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTyreDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setTyreDetails(data){
	
	$("#tyreDetailsTable").empty();
	
	var inventoryTyreList = data.inventoryTyreList;
	
	if(inventoryTyreList != undefined || inventoryTyreList != null){
		for(var index = 0 ; index < inventoryTyreList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'><a target='_blank' href='showTyreInfo?Id="+inventoryTyreList[index].tyre_ID+"' >"+ inventoryTyreList[index].tyre_NUMBER  +"</a></td>");
			columnArray.push("<td class='fit'>" + inventoryTyreList[index].tyre_MODEL +"</td>");
			columnArray.push("<td class='fit'>" + inventoryTyreList[index].warehouse_LOCATION +"</td>");
			if(inventoryTyreList[index].tyre_ASSIGN_STATUS_ID == 7){
				columnArray.push("<td class='fit'>"+ inventoryTyreList[index].tyre_ASSIGN_STATUS  +" - "+inventoryTyreList[index].dismountedTyreStatus+"</td>");
			}else{
				columnArray.push("<td class='fit'>"+ inventoryTyreList[index].tyre_ASSIGN_STATUS  +"</td>");
			}
			
			$('#tyreDetailsTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
			
		}
		if(data.statusId != "" && data.statusId != undefined){
			if(data.statusId == 1){
				$("#tyreStatus").html("AVAILABLE");
			}else if(data.statusId == 2){
				$("#tyreStatus").html("IN SERVICE");
			}else if(data.statusId == 3){
				$("#tyreStatus").html("SCRAPED");
			}else if(data.statusId == 4){
				$("#tyreStatus").html("SENT-RETREAD");
			}else if(data.statusId == 6){
				$("#tyreStatus").html("SOLD");
			}else if(data.statusId == 7){
				$("#tyreStatus").html("UNAVAILABLE");
			}
			
		}else{
			$("#tyreStatus").html("All Status");
		}
		if(data.locationName != ""){
			$("#locationName").html(data.locationName);
		}else{
			$("#locationName").html("All Location");
			
		}
		$("#tyreDetailsModel").modal('show');
		columnArray = [];
		
	
		}else{
			showMessage('info','No Record Found!')
		}
	
}

	
function getAllLocationCount(statusId){

	showLayer();
	var jsonObject					= new Object();
	jsonObject["statusId"]			= statusId;
	jsonObject["locationId"]		= $('#locationId').val();
	jsonObject["compId"]			= $('#compId').val();
	$.ajax({
		url: "getAllLocationTyreStockDetailsByStatus",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAllLocationCount(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}	

function setAllLocationCount(data){
	
	var cardColor = "";
	//$("#allLocationRow").collapse('hide'); // toggle collapse
	$('#allLocationDiv').empty();
	$('#locationRowHeader').empty();
	$(".changeLocation").removeClass('boxShadow');
	$("#isCollaps").val(true); // toggle collapse
	var inventoryTyreList = data.inventoryTyreList;
	if(inventoryTyreList != undefined || inventoryTyreList != null){
		
		if(data.statusId == 1){
			cardColor = 'bg-gradient-test96';
			$("#locationRowHeader").html('Available Tyre Count Of All Location');
		}else if(data.statusId == 2){
			cardColor = 'bg-gradient-test95';
			$("#locationRowHeader").html('In-Service Tyre Count Of All Location');
		}else if(data.statusId == 3){
			cardColor = 'bg-gradient-test98';
			$("#locationRowHeader").html('Scraped Tyre Count Of All Location');
		}else if(data.statusId == 4){
			cardColor = 'bg-gradient-primary';
			$("#locationRowHeader").html('Sent-Retread Tyre Count Of All Location');
		}else if(data.statusId == 6){
			cardColor = 'bg-dark';
			$("#locationRowHeader").html('Sold Tyre Count Of All Location');
		}else if(data.statusId == 7){
			cardColor = 'bg-secondary';
			$("#locationRowHeader").html('Unavailable Tyre Count Of All Location');
		}
		
		
		for(var i = 0 ; i < inventoryTyreList.length; i++){
			
			var columnArray = new Array();
			$('#allLocationDiv').append('<div class="col col-sm-12 col-md-3 mb-3">'
			+'<div id="changeLocation'+i+'" class="card stretch-card grid-margin bg-secondary changeLocation '+cardColor+' text-white">'
			+'<div class="card-header font-weight-bold"><h4>'+inventoryTyreList[i].warehouse_LOCATION+'</h4></div>'
			+'<div class="card-body">'
			+'<a onclick="getLocationWiseTyreDetails('+inventoryTyreList[i].tyre_ASSIGN_STATUS_ID+','+inventoryTyreList[i].warehouse_LOCATION_ID+',\''+inventoryTyreList[i].warehouse_LOCATION +'\'  );"><h4 class="count" > '+inventoryTyreList[i].statusWiseTyreCount+' </h4></a>'
			+'</div>'
			+'</div>'
			+'</div>');
			
	//		$('#allLocationRow').append("<tr>" + columnArray.join(' ') + "</tr>");
			
			if(inventoryTyreList[i].warehouse_LOCATION_ID == $("#locationId").val()){
				$("#changeLocation"+i).addClass('boxShadow');
			}
			
		}
	
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
		if($('#locationId').val() == "" || $('#locationId').val() == undefined || $('#locationId').val() == 0){
			$("#allLocationRow").collapse('show'); // toggle collapse
			window.scrollTo(0,document.body.scrollHeight);
			$("#isCollaps").val(false); // toggle collapse
			$("#collapsStatusId").val(data.statusId); // toggle collapse
		}else{
			 getLocationWiseTyreDetails(data.statusId, $('#locationId').val(),$('#locationId').select2('data').text)
		}
		
	
		

}

function getLocationWiseTyreDetails(statusId, locationId,locationName){

	showLayer();
	var jsonObject					= new Object();
	jsonObject["statusId"]			= statusId;
	jsonObject["locationId"]		= locationId;
	jsonObject["locationName"]		= locationName;
	jsonObject["compId"]			= $('#compId').val();
	$.ajax({
		url: "getTyreStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTyreDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function getIssueTyreDetails(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["compId"]			= $('#compId').val();
	$.ajax({
		url: "getIssueTyreDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setIssueTyreDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setIssueTyreDetails(data){

	
	$("#issueTyreDetailsTable").empty();
	
	var issuesList = data.issuesList;
	
	if(issuesList != undefined || issuesList != null){
		$('#issueTyreCount').html(issuesList.length);
		for(var index = 0 ; index < issuesList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'><a target='_blank' href='showIssues.in?Id="+issuesList[index].issues_ID_ENCRYPT+"'>I-"+ issuesList[index].issues_NUMBER  +"</a></td>");
			columnArray.push("<td class='fit'>" + issuesList[index].vehicleNumber +"</td>");
			columnArray.push("<td class='fit'>" + issuesList[index].issues_SUMMARY +"</td>");
			
			
			$('#issueTyreDetailsTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
			
		}
		
		
		}else{
			showMessage('info','No Record Found!')
		}
	

}

function showIssueTyreDetails(){
	$("#issueTyreModal").modal('show');
}


function vehicleGrouping() {
    var table = $('#example').DataTable({
        "columnDefs": [
            { "visible": false, "targets": 1 }
        ],
        "order": [[ 1, 'asc' ]],
        "displayLength": 25,
        "drawCallback": function ( settings ) {
            var api = this.api();
            var rows = api.rows( {page:'current'} ).nodes();
            var last=null;
 
            api.column(1, {page:'current'} ).data().each( function ( group, i ) {
                if ( last !== group ) {
                    $(rows).eq( i ).before(
                        '<tr class="group"><td colspan="5">'+group+'</td></tr>'
                    );
 
                    last = group;
                }
            } );
        }
    } );
 
    // Order by the grouping
    $('#example tbody').on( 'click', 'tr.group', function () {
        var currentOrder = table.order()[0];
        if ( currentOrder[0] === 1 && currentOrder[1] === 'asc' ) {
            table.order( [ 1, 'desc' ] ).draw();
        }
        else {
            table.order( [ 1, 'asc' ] ).draw();
        }
    } );
}