var pageIndex = 1;
$(document).ready(function() {
	$("#vehicleId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    })
}),

$(function() {
    function a(a, b) {
        $("#ureaDateRange1").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#ureaDateRange1").daterangepicker( {
    	maxDate: new Date(),
		format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
})


$(document).ready(
function($) {
	$('button[id=searchUreaEntries]').click(function(e) {
		getUreaEntries(pageIndex);
	})
});


function getUreaEntries(pageIndex){
	showLayer();
	var jsonObject							= new Object();
	var dateRange 							= $('#ureaDateRange1').val().split("to");

	jsonObject["ureaNumber"]				= $('#ureaNumber').val();
	jsonObject["vid"]						= $('#vehicleId').val();
	jsonObject["vehicleId"] 				= $('#fuelVehicle').val();
	jsonObject["startDate"] 				= dateRange[0].trim().split("-").reverse().join("-");
	jsonObject["endDate"] 					= dateRange[1].trim().split("-").reverse().join("-");
	jsonObject["pageNumber"]				= pageIndex;
	
	$.ajax({
		url: "searchUreaEntries",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setUreaEntries(data)
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setUreaEntries (data){
	
	$("#ureaEntriesSerachModelTable").empty();
	
	var ureaEntries = data.ureaEntries;
	
	if(ureaEntries != undefined || ureaEntries != null){
		for(var index = 0 ; index < ureaEntries.length; index++){
			var tripsheetNumber = "";
			if(ureaEntries[index].tripSheetNumber != null){
				tripsheetNumber = ureaEntries[index].tripSheetNumber;
			}
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <a href='showUreaDetails?Id="+ureaEntries[index].ureaEntriesId+"' target='_blank'>UE-"+ureaEntries[index].ureaEntriesNumber+"</a> </td>");
			var curl = "showVehicle?vid="+ureaEntries[index].vid;
			columnArray.push("<td class='fit ar'><a target='_blank' href='" + curl + "'>"+ureaEntries[index].vehicle_registration+"</a></td>");
			columnArray.push("<td class='fit ar'>" + ureaEntries[index].ureaOdometerOld +"</td>");
			columnArray.push("<td class='fit ar'>" + ureaEntries[index].ureaOdometer +"</td>");
			columnArray.push("<td class='fit ar'>" + ureaEntries[index].ureaDateStr +"</td>");
			columnArray.push("<td class='fit ar'>" + ureaEntries[index].ureaLiters +"</td>");
			columnArray.push("<td class='fit ar'>" + ureaEntries[index].ureaAmount +"</td>");
			columnArray.push("<td class='fit ar'>" + tripsheetNumber +"</td>");
			
			var curl1 = "editUreaEntriesInvoice.in?Id="+ureaEntries[index].ureaEntriesId  
			
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='"+curl1+"' class='confirmation'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='deleteUreaEntriesInvoice("+ureaEntries[index].ureaEntriesId+")'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#ureaEntriesSerachModelTable').append("<tr id='penaltyID"+ureaEntries[index].ureaEntriesId+"' >" + columnArray.join(' ') + "</tr>");
			
			$("#ureaSearchTable").removeClass('hide');
		}
		
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	
	$("#navigationBar").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getUreaEntries(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getUreaEntries('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getUreaEntries('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getUreaEntries('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getUreaEntries('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getUreaEntries('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	 }
	
	
	}

function deleteUreaEntriesInvoice(ureaEntriesId){
	
	  if(confirm("Are You Sure to Delete ?")){
		  	
			var jsonObject			= new Object();
			jsonObject["ureaEntriesId"] =  ureaEntriesId;
			
			showLayer();
			$.ajax({
		             url: "deleteUreaEntryById",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 window.location.replace("ureaEntriesSearch.in?");
		            	 showMessage('success', 'Urea Entry Deleted Successfully!')
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
	  }
}