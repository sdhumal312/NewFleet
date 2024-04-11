var data = [];
var gridObject;
var slickGridWrapper3;

$(function() {
	function a(a, b) {
		$("#TripCollectionExpenseRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
	}
	a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseRange").daterangepicker( {
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
	$("#requisitionlocation").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getPartRequistionLocationList.in?Action=FuncionarioSelect2",
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
	                            slug: a.slug,
	                            id: a.partlocation_id
	                        }
	                    })
	                }
	            }
	        }
    }),  $("#requisitionAssignedTo").select2( {
    	minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getUserEmailId_Subscrible",
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
                            text: e.firstName + " " + e.lastName,
                            slug: e.slug,
                            id: e.user_id
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

				var jsonObject			= new Object();

				jsonObject["requisitionlocation"] 			=  $('#requisitionlocation').val();
				jsonObject["requisitionAssignedTo"] 		=  $('#requisitionAssignedTo').val();
				jsonObject["dateRange"] 	  				=  $('#TripCollectionExpenseRange').val();
				
				
				if($('#TripCollectionExpenseRange').val() == ""){
					showMessage('info', 'Please Select Date Range!');
					return false;
				}
				
				
				showLayer();
				$.ajax({
					
					url: "getSearchPartRequisition",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setSearchPartRequisition(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
					}
				});


			});

		});


function setSearchPartRequisition(data) {

	$("#tripCollExpenseName").empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<td>');

	tr1.append(th1.append("ID"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Sent_By"));
	tr1.append(th4.append("Req.Date"));
	tr1.append(th5.append("Req.Assign"));
	tr1.append(th6.append("Status"));
	tr1.append(th7.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.SearchPartRequisition != undefined && data.SearchPartRequisition.length > 0) {
		var searchPartRequisition = data.SearchPartRequisition;
		$("#reportHeader").html("Part Requisition Report");
		for(var i = 0; i < searchPartRequisition.length; i++) {
			var tr2 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td>');
			
			tr2.append(td1.append('<a href="/'+searchPartRequisition[i].requisition_STATUS_ID+'/1/showInventoryReq.in?ID='+searchPartRequisition[i].invrid+'" target="_blank">CI-'+searchPartRequisition[i].invrid_NUMBER+'</a>'));
			tr2.append(td2.append(searchPartRequisition[i].requited_LOCATION));
			tr2.append(td3.append(searchPartRequisition[i].requited_SENDNAME));
			tr2.append(td4.append(searchPartRequisition[i].requited_DATE));
			tr2.append(td5.append(searchPartRequisition[i].requisition_RECEIVEDNAME));
			if(searchPartRequisition[i].requisition_STATUS_ID == 4){
				tr2.append(td6.append('<span class="label label-default label-success">'+searchPartRequisition[i].requisition_STATUS));	
			}else {
				tr2.append(td6.append('<span class="label label-default label-warning">'+searchPartRequisition[i].requisition_STATUS));	
			}
			
			var curl = "/"+searchPartRequisition[i].requisition_STATUS_ID+"/1/showInventoryReq.in?ID="+searchPartRequisition[i].invrid
			//var curl1 = "fleetopgroup/deleteAllInventoryReq.in?ID="+searchPartRequisition[i].invrid
			tr2.append(td7.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Show Quantity</a></li>'
			+'<li><a href="deleteAllInventoryReq.in?ID='+searchPartRequisition[i].invrid+'" class="confirmation" "><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr2);
		}
		$("#tripCollExpenseName").append(thead);
		$("#tripCollExpenseName").append(tbody);
		
	}else{
		showMessage('info','No record found !')
	}
	
}





