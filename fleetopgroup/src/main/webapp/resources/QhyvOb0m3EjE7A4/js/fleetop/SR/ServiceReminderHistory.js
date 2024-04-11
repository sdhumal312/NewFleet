$(document).ready(function() {
    
    
    var jsonObject  = new  Object();
    
    jsonObject["serviceReminderId"]  = $("#serviceReminderId").val();
    jsonObject[""]
    
   console.log("log........")
    $.ajax({
		url: "getServiceReminderHistory",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			 hideLayer();
			  console.log("jsonObject" + JSON.stringify(data.srHistoryList));
			  getServiceReminderHistoryTable(data);
    
		},
		error: function (e) {
			console.log("Error");
		}
	});
	
function getServiceReminderHistoryTable(data){
	
	var historyLIst = data.srHistoryList;
	
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	$("#TableBodyHistory tr").remove();
	$("#tableHeaderHistory tr").remove();
	$("#navigationBar").empty();
	
	$("#historyDetails").addClass("table table-bordered table-striped");
	
	if(data.srHistoryList != undefined && historyLIst.length > 0){
		//$('#paymentCount').html(data.TripSheetCount);
		var trHead ='<tr>'
			+'<th style="padding: 10px;">Completed Date</th>'
			+'<th style="padding: 10px;"> Completed Odometer</th>'
			+'<th style="padding: 10px;">Due Date</th>'
			+'<th style="padding: 10px;"> Due Odometer</th>'
			+'<th style="padding: 10px;">DSE/WORK ORDER/FIRST SERVICE ENTRY</th>'
			+'<th style="padding: 10px;">Compliance</th>'
			+'</tr>';
		console.log("trHead " + trHead);
		
		$('#tableHeaderHistory').append(trHead);
		
		for(var i = 0; i< historyLIst.length; i++){
			
			 var div = "";
			 
			 
			if (historyLIst[i].compliance == "ON TIME") {
			    div = '<div style="border:1px solid white; border-radius:50px;background-color:lightgreen; color:green; height:20px; width:60%; margin:auto; text-align: center; line-height: 20px;">' + historyLIst[i].compliance + '</div>'
			        +historyLIst[i].date_compliances + ' . ' + historyLIst[i].odometer_compliances;
			} else if (historyLIst[i].compliance == "LATE") {
			    div = '<div style="border:1px solid white; border-radius:50px;background-color: lightred; color:red; height:20px; width:60%;margin:auto; text-align: center; line-height: 20px;">' + historyLIst[i].compliance + '</div>'
			        + '<br>' + historyLIst[i].date_compliances + ' . ' + historyLIst[i].odometer_compliances;
			}
			var td = '<td style ="font-size:14px;">' + div + '</td>';
			
			var tr =' <tr data-object-id="">'
				+'<td style ="font-size:12px;">'+ historyLIst[i].completed_Date  +'</td>'
				+'<td style ="font-size:12px;">'+ historyLIst[i].completed_odometer +' Km ' +'</td>'
				+'<td style ="font-size:12px;"> ' + historyLIst[i].due_Date +'</td>'
				+'<td style ="font-size:12px;"> ' + historyLIst[i].due_odometer+' Km ' +'</td>'
				+'<td style ="font-size:12px;">' + historyLIst[i].service_Type+'</td>'
				+ td
				+ '</tr>'
				
			
			console.log("tr "+ tr);
			$('#TableBodyHistory').append(tr);
		}
	}
}	
});