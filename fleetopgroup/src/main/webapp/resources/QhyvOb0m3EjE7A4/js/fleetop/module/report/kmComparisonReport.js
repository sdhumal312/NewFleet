var data = [];
var gridObject;
var slickGridWrapper3;

$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
		format:'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
}
);

$(document).ready(function() {
	
	$("#vid").select2({
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
    }),
    
    $("#groupId").select2({
        ajax: {
            url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vGroup, slug: a.slug, id: a.gid
                        }
                    }
                    )
                }
            }
        }
    }),
	
    $("#tripRouteList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getTripRouteList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.routeNo+" "+a.routeName, slug: a.slug, id: a.routeID
                        }
                    }
                    )
                }
            }
        }
    })
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				
				var jsonObject					= new Object();
				jsonObject["vid"] 				=  $('#vid').val();
				jsonObject["groupId"] 			=  $('#groupId').val();
				jsonObject["routeId"] 			=  $('#tripRouteList').val();
				jsonObject["dateRange"] 	  	=  $('#TripCollectionExpenseRange').val();
				
				showLayer();
				$.ajax({
					
					url: "getManualVsGpsKMComparison",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setManualVsGpsKMComparison(data);
						hideLayer();
					},
					error: function (e) {
						console.log("Error");
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});


function setManualVsGpsKMComparison(data){
	
		$("#reportHeader").html("TripSheet Manual Vs GPS KM Comparision Report");
		$("#issueTableDataDetails").empty();
		
		var thead = $('<thead>');
		var tr1 = $('<tr width="100%">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');
		var th10	= $('<th>');
		var th11	= $('<th>');

		tr1.append(th1.append("No"));
		tr1.append(th2.append("TripSheet No"));
		tr1.append(th3.append("Vehicle No"));
		tr1.append(th4.append("Vehicle Group"));
		tr1.append(th5.append("Route"));
		tr1.append(th6.append("Dispatch Time"));
		tr1.append(th7.append("Closed Time"));
		tr1.append(th8.append("Route KM"));
		tr1.append(th9.append("Manual Usage KM"));
		tr1.append(th10.append("GPS Usage KM"));
		tr1.append(th11.append("Difference"));

		thead.append(tr1);
		
		var tbody = $('<tbody>');
		if(data.TripDetails != undefined && data.TripDetails.length > 0) {
			
			var TripDetails = data.TripDetails;
		
			for(var i = 0; i < TripDetails.length; i++) {
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>');
				var td5		= $('<td>');
				var td6		= $('<td>');
				var td7		= $('<td>');
				var td8		= $('<td>');
				var td9		= $('<td>');
				var td10	= $('<td>');
				var td11	= $('<td>');
				
				tr1.append(td1.append(i + 1));
				tr1.append(td2.append('<a href="showTripSheet.in?tripSheetID='+TripDetails[i].tripSheetID+'" target="_blank" >'+"TS-"+TripDetails[i].tripSheetNumber));
				tr1.append(td3.append('<a href="showVehicle.in?vid='+TripDetails[i].vid+'" target="_blank" >'+TripDetails[i].vehicle_registration));
				tr1.append(td4.append(TripDetails[i].vehicle_Group));
				tr1.append(td5.append(TripDetails[i].routeName));
				tr1.append(td6.append(TripDetails[i].dispatchedTime));
				tr1.append(td7.append(TripDetails[i].closedByTime));
				tr1.append(td8.append(TripDetails[i].routeApproximateKM));
				tr1.append(td9.append(TripDetails[i].tripUsageKM));
				tr1.append(td10.append((TripDetails[i].tripGpsUsageKM).toFixed(2)));
				
				if(TripDetails[i].diffKMPercentDanger == true){
					
					tr1.append(td11.append('<span style="color: red;" class=" fa fa-arrow-up" >'+TripDetails[i].differenceKm.toFixed(2)+'('+(Number(TripDetails[i].differenceKmPercentage)).toFixed(2)+'%)</span>'));
				} else {
					tr1.append(td11.append('<span style="color: green;" class=" fa fa-arrow-down" >'+TripDetails[i].differenceKm.toFixed(2)+'('+(Number(TripDetails[i].differenceKmPercentage)).toFixed(2)+'%)</span>'));
				}
				
				

				tbody.append(tr1);
			}
		}else{
			showMessage('info','No record found !')
		}
		
		$("#issueTableDataDetails").append(thead);
		$("#issueTableDataDetails").append(tbody);
		
		hideLayer();
	
}

//tr1.append(td9.append('<span class="label bg-gradient-danger">'+TripDetails[i].tripDiffernceKM+"("+TripDetails[i].differenceKmPercentage+")"));






