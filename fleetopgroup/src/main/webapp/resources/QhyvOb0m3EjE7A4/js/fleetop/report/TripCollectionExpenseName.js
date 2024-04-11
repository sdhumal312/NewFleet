$(document).ready(function() {
   $("#ReportExpenseName").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchExpenseName.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.expenseName, slug: a.slug, id: a.expenseID
                        }
                    }
                    )
                }
            }
        }
    } 
    ),$(function() {
        function a(a, b) {
            $("#TripCollectionExpenseNameRange").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
        }
        a(moment().subtract(1, "days"), moment()), $("#TripCollectionExpenseNameRange").daterangepicker( {
            ranges: {
                Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
            }
        }
        , a)
    }

    ),$("#btn-save").click(function(event) {
		showLayer();
		var jsonObject					= new Object();
		jsonObject["ExpenseId"]		= $("#ReportExpenseName").val();
		jsonObject["dateRange"]		= $("#TripCollectionExpenseNameRange").val();
		
		$.ajax({
			url: "tripCollectionReportWS/getTripCollectionExpenseName.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setTripCollectionExpenseNameReport(data);
				hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
		
    })
});





function setTripCollectionExpenseNameReport(data) {
	var tripCollectionExpenseName	= null;
	if(data.TripCollectionExpenseName != undefined) {
		$("#reportHeader").html("Trip Collection Expense Name Report");

		$("#tripCollExpenseName").empty();
		tripCollectionExpenseName	= data.TripCollectionExpenseName;
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		

		th1.append('Sr No');
		th2.append('TripSheet Number');
		th3.append('Vehicle Number');
		th4.append('Date');
		th5.append('Expense Name');
		th6.append('Expense Amount');
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);

		thead.append(tr1);
		
		var totalExpenseAmount=0;

		for(var i = 0; i < tripCollectionExpenseName.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			

			td1.append(i+1);
			//td2.append(tripCollectionExpenseName[i].tripdailynumber);
			td2.append('<a href="showTripDaily?ID='+tripCollectionExpenseName[i].tripDailysheetID+'" >'+tripCollectionExpenseName[i].tripdailynumber+'</a>');
			td3.append('<a href="showVehicle?vid='+tripCollectionExpenseName[i].vid+'" >'+tripCollectionExpenseName[i].vehicle_registration+'</a>');
			
			td4.append(tripCollectionExpenseName[i].createdDate);
			td5.append(tripCollectionExpenseName[i].expenseName);
			td6.append(tripCollectionExpenseName[i].expenseAmount);
			
			totalExpenseAmount += tripCollectionExpenseName[i].expenseAmount;

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);

			tbody.append(tr);
		}
		
		var tr2 = $('<tr>');

		var td1		= $('<td colspan="5">');
		var td2		= $('<td>');
		


		td1.append("Total :");
		td2.append(totalExpenseAmount.toFixed(2));
		

		tr2.append(td1);
		tr2.append(td2);
	

		tbody.append(tr2);

		$("#tripCollExpenseName").append(thead);
		$("#tripCollExpenseName").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}