jQuery(document).ready(
		function($) {

			$("#btn-save").click(function(event) {
				var jsonObject			= new Object();

				jsonObject["depotId"]		= $("#TCGroupWise").val();
				jsonObject["dateRange"]		= $("#LocWorkOrder").val();
				jsonObject["status"]		=2;
				
				if(($("#TCGroupWise").val())=="ALL" ){					
					showMessage('info','Please Select Depot Name!');
					return false;
				}
				
				showLayer();
				$.ajax({
					url: "tripCollectionReportWS/getDailyMonthlyYearlyTripCollectionReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						
						console.log('data '+data);
						setDailyMonthlyYearlyTripCollectionReportData(data);
						hideLayer();
					},
					error: function (e) {
						console.log('inside error ',e)
						hideLayer();
					}
				});
			});

		});



function setDailyMonthlyYearlyTripCollectionReportData(data) {
	
	var config = $("#showChalo").val();		
	
	var wt 				= $('#wt').val();
	var epk 			= $('#epk').val();
	var ot 				= $('#ot').val();
	var netCollection 	= $('#netCollection').val();
	var totalBalance 	= $('#totalBalance').val();
	var dieselAmount 	= $('#dieselAmount').val();
	var diffTotalNetCollectionFormula 	= $('#diffTotalNetCollectionFormula').val();
	
	if(data.TDGroupCol != undefined) {

		var company 		= null;
		var TDGroupCol 		= null;
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

		$("#reportHeader").html(data.GROUP_NAME+" Daily Trip Collection Month &amp; CashBook Report - "+data.SEARCHDATE);

		$("#advanceTable").empty();
		TDGroupCol	= data.TDGroupCol;
		
		console.log("TDGroupCol",TDGroupCol)
		var totalChaloKM     = 0;
		var totalChaloAmount = 0.0;
		
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
		var th16	= $('<th>');
		var th17	= $('<th>');
		if(config == 'true'){
		var th18	= $('<th>');
		var th19    = $('<th>');
		}
		th1.append('No');
		th2.append('Date');
		th3.append('Depot');
		th4.append('Total.K.M');
		th5.append('Diesel');
		th6.append('Diesel Amount');
		th7.append('KMPL');
		th8.append('PSNGR');
		th9.append('RFID Pass');
		th10.append('RFID AMOUNT');
		th11.append('Collection');
		th12.append('WT');	
		th13.append('NET Collection');
		th14.append('EPK');
		th15.append('Expense');
		th16.append('OT');
		th17.append('Balance');
		if(config == 'true'){
		th18.append('Chalo Km');
		th19.append('Chalo Amount');
		}
		

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		if(dieselAmount == true || dieselAmount == 'true'){
			tr1.append(th6);
		}
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		tr1.append(th10);
		tr1.append(th11);
		
		
		if(wt==true || wt == 'true'){
			tr1.append(th12);	
		}
		
		tr1.append(th13);
		
		if(epk==true || epk == 'true'){
			tr1.append(th14);
		}
		
		tr1.append(th15);
		
		if(ot==false || ot == 'false'){
			tr1.append(th16);
		}
		tr1.append(th17);

		if(config == 'true'){
		tr1.append(th18);
		tr1.append(th19);
		}
		tbody.append(tr1);

		for(var i = 0; i < TDGroupCol.length; i++ ) {
			
			var tr = $('<tr>');

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
			
			if(config == 'true'){
			var td18	= $('<td>');
			var td19	= $('<td>');
			}			
			
			td1.append(i+1);
			td2.append(TDGroupCol[i].trip_OPEN_DATE);
			td3.append(TDGroupCol[i].vehicle_GROUP);
			td4.append(TDGroupCol[i].total_USAGE_KM);
			td5.append(TDGroupCol[i].total_DIESEL);
			

			if(dieselAmount == true || dieselAmount == 'true'){
				td6.append(TDGroupCol[i].diesel_Amount.toFixed(2));
			}
			
			td7.append(TDGroupCol[i].total_DIESEL_MILAGE);
			td8.append(TDGroupCol[i].total_TOTALPASSNGER);
			td9.append(TDGroupCol[i].total_RFIDPASS);
			td10.append(TDGroupCol[i].total_RFID_AMOUNT);
			td11.append(TDGroupCol[i].total_COLLECTION);
			td12.append(TDGroupCol[i].total_WT);
			if(netCollection == true || netCollection == 'true'){
				td13.append(TDGroupCol[i].total_RFID_AMOUNT+TDGroupCol[i].total_COLLECTION)
			}else{
				td13.append(TDGroupCol[i].total_NET_COLLECTION);
			}
			td14.append(TDGroupCol[i].total_EPK);
			td15.append(TDGroupCol[i].total_EXPENSE);
			td16.append(TDGroupCol[i].total_OVERTIME);
			
			if(totalBalance == true || totalBalance == 'true'){
				td17.append(((TDGroupCol[i].total_RFID_AMOUNT+TDGroupCol[i].total_COLLECTION)-TDGroupCol[i].total_EXPENSE).toFixed(2))
			}else{
				td17.append(TDGroupCol[i].total_BALANCE.toFixed(2));
			}
			
			if(config == 'true'){				
			td18.append(TDGroupCol[i].chalo_KM);
			totalChaloKM += TDGroupCol[i].chalo_KM;
			td19.append(TDGroupCol[i].chalo_AMOUNT);
			totalChaloAmount += TDGroupCol[i].chalo_AMOUNT; 
			
			}
			
			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			
			if(dieselAmount == true || dieselAmount == 'true'){
				tr.append(td6);
			}
			
			tr.append(td7);
			tr.append(td8);
			tr.append(td9);
			tr.append(td10);
			tr.append(td11);
			
			if(wt==true|| wt == 'true'){
				tr.append(td12);
			}
			
			tr.append(td13);
			
			if(wt==true|| wt == 'true'){
				tr.append(td14);
			}
			
			tr.append(td15);
			if(ot==false|| ot == 'false'){
				tr.append(td16);
			}
			tr.append(td17);
			if(config == 'true'){
			tr.append(td18);
			tr.append(td19);
			}
			tbody.append(tr);
		}


		var tr2 = $('<tr>');

		var td1		= $('<td colspan="3">');
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
		if(config == 'true'){
			
		var td16	= $('<td>');
		var td17	= $('<td>');
		}


		td1.append("Total "+data.GROUP_NAME+" :");
		td2.append(data.TotalUsageKM);
		td3.append(data.TotalDiesel);
		td4.append(data.totalDieselAmount);
		td5.append(data.TotalDieselKMPL);
		td6.append(data.TotalPassenger);
		td7.append(data.TotalRFID);
		td8.append(data.TotalRFIDAmount);
		td9.append(data.TotalCollection);
		td10.append(data.TotalWT);
		
		
		var TotalRFIDAmount	= Number(data.TotalRFIDAmount.replace(/\,/g,""));
		var TotalCollection	= Number(data.TotalCollection.replace(/\,/g,""));
		if(diffTotalNetCollectionFormula == true || diffTotalNetCollectionFormula == 'true'){
			td11.append(TotalRFIDAmount + TotalCollection);
		}
		else{
			td11.append(data.TotalNetCollection);
		}
		td12.append(data.TotalEPK);
		td13.append(data.TotalExpense);
		td14.append(data.TotalOT);
		td15.append(data.TotalBalance);
		if(config == 'true'){
		td16.append(totalChaloKM);
		td17.append(totalChaloAmount);
		}
		
		

		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		if(dieselAmount == true || dieselAmount == 'true'){
			tr2.append(td4);
		}
		
		tr2.append(td5);
		tr2.append(td6);
		tr2.append(td7);
		tr2.append(td8);
		tr2.append(td9);
		
		if(wt == true || wt == 'true'){
			tr2.append(td10);
		}
		
		tr2.append(td11);
		
		if(wt == true || wt == 'true'){
			tr2.append(td12);
		}
		
		tr2.append(td13);
		if(wt == true || wt == 'true'){
			tr2.append(td14);
		}
		tr2.append(td15);
		if(config == 'true'){
		tr2.append(td16);
		tr2.append(td17);
		}
		
		

		tbody.append(tr2);

		$("#advanceTable").append(tbody);

		var secondHeader = "";
		if(data.CASHBOOK_DATE != undefined) {
			secondHeader = data.CASHBOOK_NAME+"-"+data.CASHBOOK_DATE;
		} else {
			secondHeader = data.CASHBOOK_NAME
		}
		$("#secondTableHeader").html(secondHeader);

		$("#cashBookTable").empty();

		var tbody = $('<tbody>');
		var tr1 = $('<tr>');

		var th1	= $('<th>');
		var th2	= $('<th>');
		var th3	= $('<th>');
		var th4	= $('<th>');
		var th5	= $('<th>');

		th1.append("No");
		th2.append("Payment");
		th3.append("Date");
		th4.append("CREDIT");
		th5.append("DEBIT");

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);

		tbody.append(tr1);

		var i = 1;
		for (var key in data.cashbookMap) {
			cashBook = data.cashbookMap[key];

			var tr = $('<tr>');

			var td1	= $('<td>');
			var td2	= $('<td>');
			var td3	= $('<td>');
			var td4	= $('<td>');
			var td5	= $('<td>');


			td1.append(i);
			
			if (cashBook.multiplePayment != true) {
				td2.append(cashBook.cash_NATURE_PAYMENT);
				td3.append(cashBook.cash_DATE);
			} else {
				if(cashBook.cash_NATURE_PAYMENT_ID != null){
					var curl = "getPaymentNatureDetails.in?PAYMENT="+cashBook.cash_NATURE_PAYMENT_ID+"&from="+data.CurrentMonth_FromDATE+"&to="+data.CurrentMonth_ToDATE+"&CASH_BOOK_ID="+data.CASH_BOOK_ID;
				}else{
					var curl = "getPaymentDetails.in?PAYMENT="+cashBook.cash_NATURE_PAYMENT+"&from="+data.CurrentMonth_FromDATE+"&to="+data.CurrentMonth_ToDATE+"&CASH_BOOK_ID="+data.CASH_BOOK_ID;
				}
				td2.append('<a target="_blank" href="' + curl + '" value="'+cashBook.cash_NATURE_PAYMENT+'">'+cashBook.cash_NATURE_PAYMENT+'</a>');
				td3.append("Multiple Payments");
			}

			if(cashBook.payment_TYPE_ID == 1) {
				td4.append("");
				td5.append(cashBook.cash_AMT_STR);
			} else {
				td4.append(cashBook.cash_AMT_STR);
				td5.append("");
			}

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);

			tbody.append(tr);
			i++;
		}

		var tr2 = $('<tr>');
		var tr3 = $('<tr>');
		var tr4 = $('<tr>');

		var td1	= $('<td colspan="2">');
		var td2	= $('<td>');
		var td3	= $('<td>');
		var td4	= $('<td>');
		var td5	= $('<td colspan="5">');
		var td6	= $('<td colspan="3">');
		var td7	= $('<td colspan="2">');

		td1.append("Total :");
		td2.append("");
		td3.append(data.CreditTotal);
		td4.append(data.DebitTotal);
		if(data.TotalBalanceWorld != undefined) {
			td5.append(data.TotalBalanceWorld);
		}
		td6.append(data.CurrentMonth_FromDATE+" To "+data.CurrentMonth_ToDATE +" BALANCE AMOUNT:");
		td7.append(data.Balance);

		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);
		tr3.append(td5);
		tr4.append(td6);
		tr4.append(td7);

		tbody.append(tr2);
		tbody.append(tr3);
		tbody.append(tr4);

		$("#cashBookTable").append(tbody);
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} 
	else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}