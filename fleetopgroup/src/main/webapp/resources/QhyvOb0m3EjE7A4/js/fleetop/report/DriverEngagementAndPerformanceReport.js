$(document).ready(function() {
   $("#driverId").select2( {
	   minimumInputLength:3, minimumResultsForSearch:10, ajax: {
           url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
               return {
                   term: a
               }
           }
           , results:function(a) {
               return {
                   results:$.map(a, function(a) {
                       return {
                           text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
                       }
                   }
                   )
               }
           }
       }
    } 
  ) 
   ,$("#btn-save").click(function(event) {
		
		var jsonObject						= new Object();
		
		var startDate						= '01-';
		var startDateOfMonth				= $('#monthRangeSelector').val();

		jsonObject["driverId"] 				=  $('#driverId').val();
		jsonObject["startDateOfMonth"] 		= startDateOfMonth;
		
		
		if(Number($('#driverId').val()) <= 0){
			showMessage('errors', 'Please Select Driver!');
			return false;
		}
		if($('#monthRangeSelector').val() == ''){
			showMessage('errors', 'Please Select Month !');
			return false;
		}
		showLayer();
		$.ajax({
			url: "getDriverEngagementAndPerformanceReport.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("data",data)
				setDriverEngagementAndPerformanceReport(data);
				 hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
		//setTimeout(function(){ hideLayer(); }, 500);
    })
});





function setDriverEngagementAndPerformanceReport(data) {
	var trip	= null;
	var driverhalt	= null;
	var fuel	= null;
	var issues	= null;
	var fromDate = null;
	var toDate  = null;
	var comment = null; 
	
	if(data.Trip != undefined || data.Driverhalt != undefined || data.Fuel != undefined || data.Issues != undefined ||  data.Comment != undefined) {
		$("#reportHeader").html("Driver Engagement And Performance Report");

		$("#tripCollExpenseName").empty();
		trip	= data.Trip;
		driverhalt	= data.Driverhalt;
		fuel	= data.Fuel;
		issues	= data.Issues;
		fromDate = data.FromDate;
		toDate = data.ToDate;
		comment = data.Comment;
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		
		
		var totalNumberOfKM=0;
		var count=0;
		var driverName="";
		var driverEmpNumber=0;
		var driverCity="";
		var driverSalary=0;
		var totalSalary=0;
		var vehicle_ExpectedMileage=0;
		//var numberOfDays=0;
		var vehicleGroup = "";
		var noOftripDays = 0;
if(trip != undefined)
		for(var i = 0; i < trip.length; i++ ) {
			
			totalNumberOfKM += trip[i].tripUsageKM;
			driverName = trip[i].tripFristDriverName +" "+ trip[i].tripFristDriverLastName+" "+trip[i].tripFristDriverFatherName;
			driverEmpNumber = trip[i].driverEmpNumber
			driverCity = trip[i].driverCity
			driverSalary = trip[i].driverSalary
			vehicle_ExpectedMileage += trip[i].vehicle_ExpectedMileage
			vehicleGroup = trip[i].vehicle_Group
			
			if(trip[i].tripFristDriverID != 0){
				count++;
			}
			var date1 = new Date(trip[i].created);
			var date2 = new Date(trip[i].closetripDate);
			var timeDiff = Math.abs(date2.getTime() - date1.getTime());
			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
			//numberOfDays += diffDays;
		}
		noOftripDays	= data.noOftripDays;
		totalSalary = (driverSalary * noOftripDays).toFixed(2);
		if(trip != undefined)
			vehicle_ExpectedMileage = vehicle_ExpectedMileage/trip.length;
		
		var haltAmount=0;
		if(data.Driverhalt != undefined){
		
		for(var j = 0; j < driverhalt.length; j++ ) {
			
			haltAmount += driverhalt[j].halt_AMOUNT;
		}
		}
		
		var fuel_liters=0;
		var actualMilege=0;
		var lossOrSaveFuel=0;
		var lossOrSaveAmount=0;
		
			if(data.Fuel != undefined) {
			
			var expectedFuelConsumed=0;
			
			var fuelLengt=0;
			var fuelPrice=0;
			var fuelAmount=0;
			
			
			expectedFuelConsumed = (totalNumberOfKM/vehicle_ExpectedMileage);
			
			fuelLengt = fuel.length;
			
			for(var k = 0; k < fuel.length; k++ ) {
				//console.log('LOGANATHAN ',fuel[k].fuel_liters );
				fuel_liters += fuel[k].fuel_liters;
				fuelPrice += fuel[k].fuel_price;
				
				//console.log('fuel_liters ', fuel_liters)
			}
			
			
			
			actualMilege = (totalNumberOfKM / fuel_liters);
			lossOrSaveFuel = expectedFuelConsumed - fuel_liters;
			
			fuelPrice = fuelPrice;
			fuelAmount = (fuelPrice/fuelLengt);
			lossOrSaveAmount = (lossOrSaveFuel * fuelAmount);
			
			}
		
			var tr1 = $('<tr with="100%"> </tr>');
			var tr2 = $('<tr with="100%"> </tr>');
			var tr3 = $('<tr with="100%"> </tr>');
			var tr4 = $('<tr with="100%"> </tr>');
			var tr5 = $('<tr with="100%"> </tr>');
			var tr6 = $('<tr with="100%"> </tr>');
			var tr7 = $('<tr with="100%"> </tr>');
			if(data.Issues != undefined) {
			var tr8 = $('<tr style="background-color: aqua;" with="100%"> </tr>');
			}
			var tr9 = $('<tr style="font-weight: bold; font-size : 12px;"></tr>');
			if(data.Comment != undefined) {
			var tr10 = $('<tr style="background-color: aqua;" with="100%"> </tr>');
			}
			var tr11 = $('<tr style="font-weight: bold; font-size : 12px;"></tr>');
			
			var td1		= $('<td style="text-align : left;">');
			var td2		= $('<td style="text-align : left;">');
			var td3		= $('<td style="text-align : left;">');
			var td4		= $('<td style="text-align : left;">');
			var td5		= $('<td style="text-align : left;">');
			var td6		= $('<td style="text-align : left;">');
			var td7		= $('<td style="text-align : left;">');
			if(data.Issues != undefined) {
			var td8		= $('<td style="text-align : left;">');
			}
			var td9		= $('<td style="text-align : left;">');
			if(data.Comment != undefined) {
			var td10	= $('<td style="text-align : left;">');
			}
			var td11	= $('<td style="text-align : left;">');

			td1.append("<span style='font-weight: bold; font-size : 14px;'>Report Name : </span> <span>Driver Engagement And Performance Report</span>");
			td1.append("<span style ='font-size : 14px;padding-left : 50px;'>Month : </span>  <span>"+fromDate.split("-").reverse().join("-")+" - "+toDate.split("-").reverse().join("-")+" </span>");

			tr1.append(td1);
			tbody.append(tr1);
		
			

			td2.append("<span style='font-weight: bold; font-size : 14px;' >Driver Name :</span> "+driverName);
			td2.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Emp No:</span> "+driverEmpNumber);
			td2.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Group: </span>"+vehicleGroup);
			td2.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Location:</span> "+driverCity);
			
			tr2.append(td2);
			tbody.append(tr2);
			
			

			td3.append("<span style='font-weight: bold; font-size : 14px;'>Number of Trips Done :</span>"+count);
			td3.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Number of Days :</span> "+noOftripDays);
			td3.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Total Bata Earned:</span>  "+haltAmount);
			td3.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Per Day Salary :</span> "+driverSalary);
			td3.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;'>Total Salary :</span> "+totalSalary);
			//td3.append("<span style='padding-left : 30px;'>Total Salary : "+totalSalary+" </span>");
			
			tr3.append(td3);
			tbody.append(tr3);
			
			
			td4.append("<span style='margin-right:100px font-weight: bold; font-size : 14px;'> Total Number of KM Driven :</span>  "+totalNumberOfKM);
			
			tr4.append(td4);
			tbody.append(tr4);
			
			
			

			td5.append("<span style='margin-right:100px font-weight: bold; font-size : 14px;'> Total Fuel Consumed :</span> "+fuel_liters);
			
			tr5.append(td5);
			tbody.append(tr5);
			
			
			
			if(actualMilege == undefined){
				actualMilege = 0;
			}

			td6.append("<span style='margin-right:100px font-weight: bold; font-size : 14px;' > Vehicle Milege/Consumption :</span> "+actualMilege.toFixed(2));
			td6.append("<span style='padding-left : 30px; font-weight: bold; font-size : 14px;' >  Expected Milege : </span> "+vehicle_ExpectedMileage.toFixed(2));
			
			tr6.append(td6);
			tbody.append(tr6);
			
			
			
			

			td7.append("<span style='margin-right:100px font-weight: bold; font-size : 14px;'> Fuel loss/Save by Him :</span> "+lossOrSaveFuel);
			td7.append("<span style='margin-right:100px font-weight: bold; font-size : 14px;'> Amount : </span>"+lossOrSaveAmount);
			
			tr7.append(td7);
			tbody.append(tr7);
			
			if(data.Issues != undefined) {
			td8.append("<span style='margin-right:100px'> Driver Issue Report : </span>");
			
			tr8.append(td8);
			tbody.append(tr8);
			}
			
			if(issues != undefined) {
			var innerTable = $('<table id="issue" width="100%" border="1";>');
			var thead1 = $('<thead  width="100%" >');

			var th1		= $('<th>');
			var th2		= $('<th>');
			var th3		= $('<th>');
			var th4		= $('<th>');
			var th5		= $('<th>');
			

			th1.append('Sr No');
			th2.append('Issue Number');
			th3.append('Driver Name');
			th4.append('Issue Description');
			th5.append('Issue Reported Date');
			

			thead1.append(th1);
			thead1.append(th2);
			thead1.append(th3);
			thead1.append(th4);
			thead1.append(th5);

			innerTable.append(thead1);
			

			for(var i = 0; i < issues.length; i++ ) {
				var tr = $('<tr>');

				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>');
				var td5		= $('<td>');
				

				td1.append(i+1);
				td2.append(issues[i].issues_NUMBER);
				td3.append(issues[i].issues_DRIVER_NAME+' '+issues[i].issueDriverLastName+' '+issues[i].driver_fathername);
				td4.append(issues[i].issues_DESCRIPTION);
				td5.append(issues[i].created_DATE);
				
				/*td2.append('<a href="showTripDaily?ID='+tripCollectionExpenseName[i].tripDailysheetID+'" >'+tripCollectionExpenseName[i].tripdailynumber+'</a>');
				td3.append('<a href="showVehicle?vid='+tripCollectionExpenseName[i].vid+'" >'+tripCollectionExpenseName[i].vehicle_registration+'</a>');*/
				

				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);
				tr.append(td5);
				//tr.append(td6);

				innerTable.append(tr);
			}
			td9.append(innerTable);
			tr9.append(td9);
			tbody.append(tr9);
			}
			
			if(data.Comment != undefined) {
			td10.append("<span style='margin-right:100px'> Driver Comment Report : </span>");
			
			tr10.append(td10);
			tbody.append(tr10);
			}
			
			
			if(comment != undefined) {
			var innerTable1 = $('<table id="comment" width="100%" border="1";>');
			var thead2 = $('<thead  width="100%" >');

			var th1		= $('<th>');
			var th2		= $('<th>');
			var th3		= $('<th>');
			var th4		= $('<th>');
			

			th1.append('Sr No');
			th2.append('Comment Title');
			th3.append('Comment Description');
			th4.append('Comment Reported Date');
			

			thead2.append(th1);
			thead2.append(th2);
			thead2.append(th3);
			thead2.append(th4);

			innerTable1.append(thead2);
			

			for(var i = 0; i < comment.length; i++ ) {
				var tr = $('<tr>');

				var td1		= $('<td>');
				var td2		= $('<td>');
				var td3		= $('<td>');
				var td4		= $('<td>');
				var td5		= $('<td>');
				

				td1.append(i+1);
				td2.append(comment[i].driver_title);
				td3.append(comment[i].driver_comment);
				td4.append(comment[i].creationDate);
				
				

				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				tr.append(td4);

				innerTable1.append(tr);
			}
			td11.append(innerTable1);
			tr11.append(td11);
			tbody.append(tr11);
			}
			

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
	setTimeout(function(){ hideLayer(); }, 500);
}