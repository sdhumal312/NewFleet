jQuery(document).ready(
		function($) {

			$("#btn-search").click(function(event) {
				
				
				var jsonObject			= new Object();

				jsonObject["depotId"]		= $("#TCGroupWise").val();
				jsonObject["dateRange"]		= $("#TCDailydate").val();
				jsonObject["status"]		= 2;
				
				if($("#TCGroupWise").val()=="ALL"){
					showMessage('info', 'Please Select Depot name!');
					return false;
				}
				showLayer();
				$.ajax({
					url: "tripCollectionReportWS/getDailyTripDailyCashBookReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setDailyTripDailyCashBookData(data);
						hideLayer();
					},
					error: function (e) {
						hideLayer();
					}
				});
			});

		});

function setDailyTripDailyCashBookData(data) {
	var config = $("#showChalo").val();
	
	if(data.TDRoute != undefined) {
		var company = null;
		var TDRoute = null;
		var cashbook = null;
		$("#companyDetails").empty();
		company	= data.company;
		var tbody = $('<tbody>');
		var tr1 = $('<tr>');
		var tr2 = $('<tr>');

		var td1	= $('<td>');
		var td2	= $('<td>');
		var td3	= $('<td colspan="2">');

		var logo = "downloadlogo/"+company.company_id_encode+".in"
		td1.append('<img class="img-rounded " alt="Company Logo" src="'+logo+'" width="280" height="40">')
		td2.append("Print By: "+ company.firstName+"_"+company.lastName);
		td3.append("Branch : "+company.branch_name+" , Department : "+ company.department_name);

		tr1.append(td1);
		tr1.append(td2);
		tr2.append(td3);

		tbody.append(tr1);
		tbody.append(tr2);

		$("#companyDetails").append(tbody);

		$("#reportHeader").html(data.GROUP_NAME+" Daily Trip Collection &amp; CashBook Report - "+data.SEARCHDATE);

		$("#advanceTable").empty();
		TDRoute	= data.TDRoute;

		var tbody = $('<tbody>');
		var tr1 = $('<tr>');

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
		var th12	= $('<th>');
		var th13	= $('<th>');
		var th14	= $('<th>');
		var th15	= $('<th>');
		var th16 	= $('<th>');
		var th17	= $('<th>');
		var th18	= $('<th>');
		var th19	= $('<th>');
		var th20	= $('<th>');	
		console.log('config ', config);
		if(config == 'true' || config == true){
			var th21	= $('<th>');
			var th22	= $('<th>');	
		}
		
		tr1.append(th1.append("No"));
		tr1.append(th2.append("Bus Name"));
		if(data.configuration.routeInDiffColumn){
			console.log('fasdfsdfsd ', tr1);
			tr1.append(th20.append("Route Name"));
		}
		tr1.append(th3.append("Driver"));
		tr1.append(th4.append(" Conductor"));
		tr1.append(th5.append("Total.K.M"));
		tr1.append(th6.append("Diesel"));
		if(data.configuration.showDieselAmount){
			tr1.append(th19.append("Diesel Amt"));
		}
		tr1.append(th7.append("KMPL"));
		tr1.append(th8.append("PSNGR"));
		tr1.append(th9.append("PASS"));
		tr1.append(th10.append("RFID Pass"));
		tr1.append(th11.append("RFID AMOUNT"));
		tr1.append(th12.append("Collection"));
		if(!data.configuration.hideWtPenalty){
			tr1.append(th13.append("WT"));
		}
		
		tr1.append(th14.append("NET Collection"));
		if(!data.configuration.showEPK){
		tr1.append(th15.append("EPK"));
		}
		tr1.append(th16.append("Expense"));
		if(!data.configuration.hideOT){
			tr1.append(th17.append("OT"));
		}
		
		tr1.append(th18.append("Balance"));
		if(config == 'true' || config == true){
		tr1.append(th21.append("Chalo KM"));
		tr1.append(th22.append("Chalo Amt"));
		}
		

		tbody.append(tr1);
		var sr = 1;
		for(var i = 0; i < TDRoute.length; i++) {
			
			if(TDRoute[i].trip_CLOSE_STATUS == 'TOTAL:') {
				if(!data.configuration.routeInDiffColumn){
					var tr = $('<tr class="closeRouteAmount">');

					var td1		= $('<td colspan="4">');
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
					var td12	= $('<td>');
					var td13	= $('<td>');
					var td14	= $('<td>');
					var td15	= $('<td>');
					var td16	= $('<td>');
					var td17	= $('<td>');
					if(config == 'true'){
					var td18	= $('<td>');
					var td19	= $('<td>');
					}

					tr.append(td1.append(TDRoute[i].trip_CLOSE_STATUS+TDRoute[i].trip_ROUTE_NAME));
					tr.append(td2.append(TDRoute[i].total_USAGE_KM));
					tr.append(td3.append(TDRoute[i].total_DIESEL));
					tr.append(td4.append(TDRoute[i].total_DIESELKML));
					tr.append(td5.append(TDRoute[i].total_TOTALPASSNGER));
					tr.append(td6.append(TDRoute[i].total_PASS_PASSNGER));
					tr.append(td7.append(TDRoute[i].total_RFIDPASS));
					tr.append(td8.append(TDRoute[i].total_RFID_AMOUNT));
					tr.append(td9.append(TDRoute[i].total_COLLECTION));
					tr.append(td10.append(TDRoute[i].total_WT));
					tr.append(td11.append(TDRoute[i].total_NET_COLLECTION));
					tr.append(td12.append(TDRoute[i].total_EPK));
					tr.append(td13.append(TDRoute[i].total_EXPENSE));
					tr.append(td14.append(TDRoute[i].total_OVERTIME));
					tr.append(td15.append(TDRoute[i].total_BALANCE));
					if(config == 'true'){
					if(TDRoute[i].chalo_AMOUNT != null){
					tr.append(td18.append(TDRoute[i].chalo_AMOUNT));
					}else{
					tr.append(td18.append(0.0));	
					}
					if(TDRoute[i].chalo_KM != null){
					tr.append(td19.append(TDRoute[i].chalo_KM));
					}else{
					tr.append(td19.append(0));	
					}
					}

				}
				
			} else {
				var tr = $('<tr class="closeAmount">');

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
				var td12	= $('<td>');
				var td13	= $('<td>');
				var td14	= $('<td>');
				var td15	= $('<td>');
				var td16	= $('<td>');
				var td17	= $('<td>');
				var td18	= $('<td>');
				var td19	= $('<td>');
				var td20	= $('<td>');
				if(config == 'true'){
					var td21	= $('<td>');
					var td22	= $('<td>');
				}
				
				tr.append(td1.append(sr));
				var curl = "showTripDaily.in?ID="+TDRoute[i].triprouteid;
				tr.append(td2.append('<a target="_blank" href="' + curl + '" value="'+TDRoute[i].trip_CLOSE_STATUS+'">'+TDRoute[i].trip_CLOSE_STATUS+'</a><br>'));
				if(data.configuration.routeInDiffColumn){
					tr.append(td20.append(TDRoute[i].trip_ROUTE_NAME));
				}
				tr.append(td3.append(TDRoute[i].trip_DRIVER_NAME));
				tr.append(td4.append(TDRoute[i].trip_CONDUCTOR_NAME));
				tr.append(td5.append(TDRoute[i].total_USAGE_KM));
				tr.append(td6.append(TDRoute[i].total_DIESEL));
				if(data.configuration.showDieselAmount){
					tr.append(td19.append(TDRoute[i].total_DIESEL_AMOUNT));
				}
				tr.append(td7.append(TDRoute[i].total_DIESELKML));
				tr.append(td8.append(TDRoute[i].total_TOTALPASSNGER));
				tr.append(td9.append(TDRoute[i].total_PASS_PASSNGER));
				tr.append(td10.append(TDRoute[i].total_RFIDPASS));
				tr.append(td11.append(TDRoute[i].total_RFID_AMOUNT));
				tr.append(td12.append(TDRoute[i].total_COLLECTION));
				if(!data.configuration.hideWtPenalty){
					tr.append(td13.append(TDRoute[i].total_WT));
				}
				if(data.configuration.diffBalanceFormula){
					tr.append(td14.append(TDRoute[i].total_RFID_AMOUNT + TDRoute[i].total_COLLECTION));
				}else{
					tr.append(td14.append(TDRoute[i].total_NET_COLLECTION));
				}
				if(!data.configuration.showEPK){
				tr.append(td15.append(TDRoute[i].total_EPK));
				}
				//tr.append(td16.append(TDRoute[i].total_EXPENSE));
				if(TDRoute[i].total_EXPENSE != null){
				tr.append(td16.append('<a onclick="getExpensesDetails('+TDRoute[i].triprouteid+')" href="#">'+(TDRoute[i].total_EXPENSE).toFixed(2)+'</a><br>'));
				} else{
				tr.append(td16.append('<a onclick="getExpensesDetails('+TDRoute[i].triprouteid+')" href="#">'+(0.00)+'</a><br>'));	
				}
				if(!data.configuration.hideOT){
					tr.append(td17.append(TDRoute[i].total_OVERTIME));
				}
				if(data.configuration.diffBalanceFormula){
					tr.append(td18.append(((TDRoute[i].total_RFID_AMOUNT + TDRoute[i].total_COLLECTION) - TDRoute[i].total_EXPENSE).toFixed(2)));
				}else{
					tr.append(td18.append(TDRoute[i].total_BALANCE));
				}
				if(config == 'true'){
				if(TDRoute[i].chalo_KM != null){
					tr.append(td21.append(TDRoute[i].chalo_KM));
				}else{
					tr.append(td21.append(0));
				}
				if(TDRoute[i].chalo_AMOUNT != null){
					tr.append(td22.append(TDRoute[i].chalo_AMOUNT));
				}else{
					tr.append(td22.append(0.0));	
				}
				}
				
				sr++;
			}

			tbody.append(tr);
		}

		var tr2 = $('<tr class="closeGroupAmount">');
		var tr3 = $('<tr class="closeGroupAmount">');
		var tr4 = $('<tr class="closeGroupAmount">');
		var td1;
		if(data.configuration.showDieselAmount){
			td1		= $('<td colspan="5">');
		}else{
			td1		= $('<td colspan="4">');
		}
		
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
		var td12	= $('<td>');
		var td13	= $('<td>');
		var td14	= $('<td>');
		var td15	= $('<td>');
		var td16	= $('<td>');
		var td17	= $('<td>');
		if(config == 'true'){
		var td18	= $('<td>');
		var td19	= $('<td>');
		}

		tr2.append(td1.append("TOTAL : "));
		console.log('data.TDGroupCol ', data.TDGroupCol);
		if(data.TDGroupCol != undefined){
			tr2.append(td2.append(data.TDGroupCol.total_USAGE_KM));
			tr2.append(td3.append(data.TDGroupCol.total_DIESEL));
			if(data.configuration.showDieselAmount){
				tr2.append(td16.append(data.totalDieselAmount));
			}
			tr2.append(td4.append(data.TDGroupCol.total_DIESEL_MILAGE));
			tr2.append(td5.append(data.TDGroupCol.total_TOTALPASSNGER));
			tr2.append(td6.append(data.TDGroupCol.total_PASS_PASSNGER));
			tr2.append(td7.append(data.TDGroupCol.total_RFIDPASS));
			tr2.append(td8.append(data.TDGroupCol.total_RFID_AMOUNT));
			tr2.append(td9.append(data.TDGroupCol.total_COLLECTION));
			if(!data.configuration.hideWtPenalty){
				tr2.append(td10.append(data.TDGroupCol.total_WT));
			}
			
			
			if(data.configuration.diffBalanceFormula){
				tr2.append(td11.append(data.TDGroupCol.total_RFID_AMOUNT + data.TDGroupCol.total_COLLECTION));
			}else{
				tr2.append(td11.append(data.TDGroupCol.total_NET_COLLECTION));
			}
			if(!data.configuration.showEPK){
			tr2.append(td12.append(data.TDGroupCol.total_EPK));
			}
			tr2.append(td13.append(data.TDGroupCol.total_EXPENSE));
			if(!data.configuration.hideOT){
				tr2.append(td14.append(data.TDGroupCol.total_OVERTIME));
			}
			var balanceAmount = (data.TDGroupCol.total_RFID_AMOUNT + data.TDGroupCol.total_COLLECTION) -  data.TDGroupCol.total_EXPENSE;
			if(data.configuration.diffBalanceFormula){
				tr2.append(td15.append(balanceAmount.toFixed(2)));
			}else{
				tr2.append(td15.append(data.TDGroupCol.total_BALANCE));
			}
			if(config == 'true'){
			if(data.TDGroupCol.totalCHALO_KM != null){
			tr2.append(td18.append(data.TDGroupCol.totalCHALO_KM));
			} else{
			tr2.append(td18.append(0));	
			}
			if(data.TDGroupCol.totalCHALO_AMOUNT != null){
			tr2.append(td19.append(data.TDGroupCol.totalCHALO_AMOUNT));
			} else{
			tr2.append(td19.append(0.0));	
			}
		}
			
	}
		
		
		
		

		tbody.append(tr2);
		if(data.configuration.diffBalanceFormula){
			var td1 =$('<td colspan="16">');
			var td2 =$('<td colspan="2" style="text-align: right;">');
			tr3.append(td1.append("RFID AMOUNT : "));
			tr3.append(td2.append(data.TDGroupCol.total_RFID_AMOUNT));
			tbody.append(tr3);
			var td3 =$('<td colspan="16">');
			var td4 =$('<td colspan="2" style="text-align: right;">');
			tr4.append(td3.append("Balance AMOUNT : "));
			tr4.append(td4.append((balanceAmount - data.TDGroupCol.total_RFID_AMOUNT).toFixed(2)));
			tbody.append(tr4);
		}

		$("#advanceTable").append(tbody);

		var secondHeader = "";
		if(data.CASHBOOK_DATE != undefined) {
			secondHeader = data.CASHBOOK_NAME+"-"+data.CASHBOOK_DATE;
		} else {
			secondHeader = data.CASHBOOK_NAME;
		}
		$("#secondTableHeader").html(secondHeader);

		$("#cashBookTable").empty();

		cashbook	= data.cashbook;
		var tbody = $('<tbody>');
		var tr1 = $('<tr>');
		var tr2 = $('<tr>');

		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td colspan="3">');
		var td8		= $('<td>');
		var td9		= $('<td>');
		var td10	= $('<td>');

		tr1.append(td1.append("No"));
		tr1.append(td2.append("ID"));
		tr1.append(td3.append("Reference"));
		tr1.append(td4.append("Payment"));
		tr1.append(td5.append("CREDIT"));
		tr1.append(td6.append("DEBIT"));
		tr2.append(td7.append("Cash Book : "+data.CashBookStatus));
		tr2.append(td8.append("Opening Balance :"));
		tr2.append(td9.append(data.OpenBalance));
		tr2.append(td10.append(""));

		tbody.append(tr1);
		tbody.append(tr2);

		for(var i = 0; i < cashbook.length; i++) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');

			td1.append(i);
			var curl = "showCashBook.in?Id="+cashbook[i].cashid;
			td2.append('<a target="_blank" href="' + curl + '" value="'+cashbook[i].cash_NUMBER+'">'+cashbook[i].cash_NUMBER+'</a>');
			td3.append(cashbook[i].cash_PAID_RECEIVED);
			td4.append(cashbook[i].cash_NATURE_PAYMENT);

			if(cashbook[i].payment_TYPE_ID == 1) {
				td5.append("");
				td6.append(cashbook[i].cash_AMT_STR);
			} else {
				td5.append(cashbook[i].cash_AMT_STR);
				td6.append("");
			}

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);

			tbody.append(tr);
		}
		
		var tr3 = $('<tr>');
		var tr4 = $('<tr>');
		var tr5 = $('<tr>');

		var td1		= $('<td colspan="4">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td colspan="6">');
		var td5		= $('<td colspan="4">');
		var td6		= $('<td colspan="2">');

		tr3.append(td1.append("Total :"));
		tr3.append(td2.append(data.CreditTotal));
		tr3.append(td3.append(data.DebitTotal));
		tr4.append(td4.append(data.BalanceWorld));
		tr5.append(td5.append("Balance : "));
		tr5.append(td6.append(data.Balance));

		tbody.append(tr3);
		tbody.append(tr4);
		tbody.append(tr5);

		$("#cashBookTable").append(tbody);

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

function getExpensesDetails(tripSheetId){
	console.log("getting expenses details "+tripSheetId);

		var jsonObject			= new Object();
		
		jsonObject["tripSheetId"] 							=  tripSheetId;
		
		showLayer();
		$.ajax({
	        url: "tripDailySheetWS/getExpensesDetailsforModel",
	        type: "POST",
	        dataType: 'json',
	        data: jsonObject,
	        success: function (data) {
	        	console.log('data ',data);
	        	setModelData(data);
	        	//window.location.replace("showTripDaily.in?ID="+data.tripSheetId+"&updateExpense="+true+"");
	       	 hideLayer();
	        },
	        error: function (e) {
	       	 showMessage('errors', 'Some error occured!')
	       	 hideLayer();
	        }
	});

}
function setModelData(data){
	 $('#modelBody').html('');
	 
	 if(data.tripExpenses != undefined){
		 for(var i = 0 ; i< data.tripExpenses.length; i++){
			 var modelBody = "";
			 modelBody = '<div class="row1">'
				 +'<label class="col-md-3 col-sm-3 col-xs-12 control-label">'+data.tripExpenses[i].expenseName+' :</label>'
				 +'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" readonly = "readonly" class="form-text" value="'+data.tripExpenses[i].expenseAmount+'" name="expenses" id="expenses'+i+'">'
				 +'</div></div><br/><br/>';
			 $('#modelBody').append(modelBody);
			 
		 }
		 $('#getExpensesModel').modal('show');
	 }else{
		 showMessage('info','No Expenses Found !');
	 }
}