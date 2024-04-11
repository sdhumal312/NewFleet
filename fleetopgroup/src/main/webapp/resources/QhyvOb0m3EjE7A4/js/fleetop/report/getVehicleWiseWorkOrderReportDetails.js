$(document).ready(function() {
	
    $("#SelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        multiple: !0,
        ajax: {
            url: "getVehicleListService.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }),
     $("#SelectVehicleType").select2( {
        ajax: {
            url:"getVehicleType.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vtype, slug: t.slug, id: t.tid
                        }
                    }
                    )
                }
            }
        }
    })
		$('button[id=submit]').click(function(e) {
			
			var jsonObject			= new Object();
			showLayer();
			jsonObject["VehicleGroupId"]    =  $("#workOrderGroup").val();
			jsonObject["vid"]               =  $("#SelectVehicle").val();
			jsonObject["dateRange"] 	    =  $("#VehWorkOrder").val();
			jsonObject["VehicleTypeId"]		=  $("#SelectVehicleType").val();
			jsonObject["workOrderTypeId"]	=  $("#workOrderType").val();
			jsonObject["vLocationId"]		=  $("#workOrderLocation2").val();
			
					
			$.ajax({
					url: "workOrderWS/getWorkOrderAndDse_Details",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						showWorkOrderAndDseDetails(data);
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			
	  })
				
})
function showWorkOrderAndDseDetails(data)
{
	
	
	$("#ResultContent").removeClass("hide")
	$("#reportHeader").empty();
	$("#WorkOrderTable").empty();
		
	var Report = null;
	var ReportType;
	
	if((data.IssuesWo != undefined) || (data.IssueDse != undefined) || (data.ServiceReminderWo != undefined) || (data.ServiceReminderDse !=undefined))
	{
		if(data.IssuesWo != undefined && data.IsIssuesWoList)
		{
			Report = data.IssuesWo;
			ReportType = 1
			$("#reportHeader").html("Issues Regarding Work Order");
		}	
		else if(data.IssueDse != undefined && data.IsIssuesDseList)
		{
			Report = data.IssueDse;
			ReportType = 2
			$("#reportHeader").html("Issues Regarding Dealer Service Entries");
		}
		else if(data.ServiceReminderWo != undefined && data.IsSRWoList)
		{
			Report = data.ServiceReminderWo;
			ReportType = 3
			$("#reportHeader").html("Service Reminder Regarding  Work Order");
		}	
		if(data.ServiceReminderDse != undefined && data.IsSRDseList)
		{
			ReportType = 4
			Report = data.ServiceReminderDse;
			$("#reportHeader").html("Service Reminder Regarding Dealer Service Entries");
		}	
			
		
		
		
		
		var FinalReport	= new Object();
		
		var IssueReport =false;
		var SrReport    = false;
		var WoDse =false;
		
		for(var i = 0; i < Report.length; i++ ) {
			var ReportObj = new Object();
			
			if(ReportType == 1 || ReportType == 2)
			{
				ReportObj.Date                = Report[i].issueCreated;
				ReportObj.IssueCategory       = Report[i].issueCategory;
				ReportObj.IssueDetails        = Report[i].issueSummary;
				IssueReport = true;
				
			}
			if(ReportType == 3 || ReportType == 4)
			{
				ReportObj.Date                = Report[i].srcreated;
				ReportObj.SRProgram           = Report[i].srprogram;
				ReportObj.SRDetails           = Report[i].srdetails;
			}
			ReportObj.vid 				  = Report[i].vehicle_vid;
			ReportObj.VehicleNo           = Report[i].vehicle_registration;
			ReportObj.vehicle_Odometer    = Report[i].vehicle_Odometer;
			ReportObj.Date2                = Report[i].created;
			
			if(ReportType == 1 || ReportType == 3)
			{
			   ReportObj.No                  = Report[i].workorders_Number;
			   ReportObj.Location 			 = Report[i].workLocation;
			   ReportObj.WoTaskDetails       = Report[i].job_Type + " " + Report[i].subType;
			   ReportObj.id				 	 = Report[i].workorders_id;
			   WoDse  = true;
			}
			  
			if(ReportType ==  2)
			{
				ReportObj.WoTaskDetails       = Report[i].issueSummary;
			} 
			if(ReportType == 4)
			{
				ReportObj.WoTaskDetails      = Report[i].srdetails;
			}
			if(ReportType == 2 || ReportType == 4)
			{
				ReportObj.No                 = Report[i].dseNo;
				ReportObj.InNo               = Report[i].invNo;
				ReportObj.VendorName         = Report[i].vendorName;
				ReportObj.id				 = Report[i].dseId;
				SrReport    = true;
			}
			
			
			
			ReportObj.PartName            = Report[i].partName;
			ReportObj.PartNo              = Report[i].partNo;
			ReportObj.PartQty             = Report[i].partQt;
			ReportObj.labername           = Report[i].labername;
			
			
			FinalReport[i] = ReportObj;
		}
		
		var table = $('<table border=2>').css({'box-shadow': ' 5px 15px 18px #888888'});
		var thead = $('<thead>').css({});
		
		var tr1 = $('<tr style="background-color:   #aeb6bf  ; color:black" >').css({
			'height': '30px',
			'width': '100%'
		});
		
		var th1 = $('<th rowspan="2">').css({'width': '0.5%', 'font-size': '14px','text-align': 'center'}).text('SrNo');
		var th2 = $('<th rowspan="2">').css({'width': '3%', 'font-size': '14px','text-align': 'center'}).text('Date');
		var th3 = $('<th rowspan="2">').css({'width': '3%', 'font-size': '14px','text-align': 'center'}).text('VehicleNo');
		var th4 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('VOdometer');
		
		var text1;
		var text2;
		var text3;
		var text4;
		var text5;
		var text6;
		
		if(IssueReport)
		{
			text1 = "IssueCat";
			text2 = "Issue Details";
		}
		else
		{
			text1 = "SRPName";
			text2 = "SR ProgramDetails";
		}
		
		var th5 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text(text1);
	    var th6 = $('<th rowspan="2">').css({'width': '8%','font-size': '14px','text-align': 'center'}).text(text2);
	    
		if(SrReport)
		{
			text3 = "DSE Date";
			text4 = "DSENo";
			text5 = "VendorName";
			text6 = "DSETaskDetails";
		}
		else
		{
			text3 = "WoDate";
			text4 = "WoNo";
			text5 = "WorkLocation";
			text6 = "WOTDetails"
		}
		var th7 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text(text3);
		var th8 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text(text4);
		
		if(SrReport)
		{
			var th9 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text("InNo");
		}
		
		var th10 = $('<th rowspan="2">').css({'width': '8%','font-size': '14px','text-align': 'center'}).text(text6);
		
		var th11 = $('<th colspan=3>').css({'width': '12%','font-size': '14px','text-align': 'center'}).text('Inventory');
		var th12 = $('<th>').css({'width': '4%','font-size': '14px','text-align': 'center'}).text('Labour');
		var th13 = $('<th rowspan=2>').css({'width': '5%','font-size': '14px','text-align': 'center'}).text(text5);
		
		if(!SrReport)
		{
		  tr1.append(th1, th2, th3, th4, th5, th6, th7, th8, th10, th11, th12,th13);
		}
		else
		{
		  tr1.append(th1, th2, th3, th4, th5, th6, th7, th8,th9, th10, th11, th12,th13);
		}
		
		var tr2 = $('<tr style="background-color: black; color:white">').css({
			'height': '17px',
			'width': '100%'
		});
		var th2_1 = $('<th>').css({'width': '10%', 'font-size': '14px','text-align': 'center'}).text('PartName');
		var th2_2 = $('<th>').css({'width': '3%', 'font-size': '14px','text-align': 'center'}).text('PartNo');
		var th2_3= $('<th>').css({'width':  '1%', 'font-size': '14px','text-align': 'center'}).text('PartQ');
		var th2_4 = $('<th>').css({'width': '5%', 'font-size': '14px','text-align': 'center'}).text('LabourName');
		tr2.append(th2_1,th2_2,th2_3,th2_4);
		
		
		var tbody = $('<tbody>')
		var srn=1;
		for(var key in FinalReport) {
			
				var tr = $('<tr>').css({
					'height': '40px',
					'width': '100%'
				});
			
			   var td1 = $('<td>').text(srn); srn++;
			   
			   var td2 = $('<td>').text(FinalReport[key].Date);
			   var td3 = $('<td>').html('<a href="showVehicle.in?vid='+FinalReport[key].vid+'" target="_blank">'+FinalReport[key].VehicleNo+'</a>');
			   var td4 = $('<td>').text(FinalReport[key].vehicle_Odometer);
			   
			   if(IssueReport)
			   {
				   var td5 = $('<td>').text(FinalReport[key].IssueCategory);
			       var td6 = $('<td>').text(FinalReport[key].IssueDetails);
			   }
			   else
			   {
				   var td5 = $('<td>').text(FinalReport[key].SRProgram);
			       var td6 = $('<td>').text(FinalReport[key].SRDetails);
			   }
			   
			   var td7 = $('<td>').text(FinalReport[key].Date2);
			   if(WoDse)
			   {
				   var td8 = $('<td>').html('<a href="showWorkOrder?woId='+FinalReport[key].id+'" target="_blank">WO-'+FinalReport[key].No+'</a>');
			   }
			   else
			   {
				   var td8 = $('<td>').html('<a href="showDealerServiceEntries?dealerServiceEntriesId='+FinalReport[key].id+'" target="_blank">DSE-'+FinalReport[key].No+'</a>');
			   }
			   
			   if(SrReport)
			   {
			   	var td9 = $('<td>').text(FinalReport[key].InNo);
			   	var td15 = $('<td>').text(FinalReport[key].VendorName);
			   }
			   else
			   {
				   var td15 = $('<td>').text(FinalReport[key].Location);
			   }
			   
			   var td10 = $('<td>').text(FinalReport[key].WoTaskDetails);
			   var td11 = $('<td>').text(FinalReport[key].PartName);
			   var td12 = $('<td>').text(FinalReport[key].PartNo);
			   var td13 = $('<td>').text(FinalReport[key].PartQty);
			   var td14 = $('<td>').text(FinalReport[key].labername);
			   
			   if(SrReport)
				    tr.append(td1,td2,td3,td4,td5,td6,td7,td8,td9,td10,td11,td12,td13,td14,td15);
			   else
				    tr.append(td1,td2,td3,td4,td5,td6,td7,td8,td10,td11,td12,td13,td14,td15);
			  
			   
			   tbody.append(tr);
	    }
		thead.append(tr1,tr2)	
		table.append(thead,tbody)
		$("#WorkOrderTable").append(table);
	}
	else {
		if($("#workOrderType").val() == 1 || $("#workOrderType").val()==2 || $("#workOrderType").val()==3 ||$("#workOrderType").val()==4 )
		{
			showMessage('info','No record found !');
		}
	}
	
	
	if(data.FinalList != undefined && data.IsFinalList)
	{
		$("#reportHeader").html("ALL");
		$("#WorkOrderTable").empty();

		
		var table = $('<table border=2>').css({'box-shadow': ' 5px 15px 18px #888888'});
		var thead = $('<thead>')
		
		var tr1 = $('<tr style="background-color: #aeb6bf; color:black" >').css({
			'height': '30px',
			'width': '100%'
		});
		
		var th1 = $('<th rowspan="2">').css({'width': '0.5%', 'font-size': '14px','text-align': 'center'}).text('SrNo');
		var th2 = $('<th rowspan="2">').css({'width': '3%', 'font-size': '14px','text-align': 'center'}).text('Date');
		var th3 = $('<th rowspan="2">').css({'width': '3%', 'font-size': '14px','text-align': 'center'}).text('VehicleNo');
		var th4 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('VOdometer');
		
		var th5 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('IssueCat\n/SrProName');
		var th6 = $('<th rowspan="2">').css({'width': '8%','font-size': '14px','text-align': 'center'}).text('IDetails\n/SRPDetails');
		
		var th7 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('WO/\nDSEDate');
		var th8 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('WO/\nDSENo');
		var th9 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('InNo');
		
		
		var th10 = $('<th rowspan="2">').css({'width': '8%','font-size': '14px','text-align': 'center'}).text('Wo/DSE\nTask');
		var th11 = $('<th colspan="3">').css({'width': '12%','font-size': '14px','text-align': 'center'}).text('Inventory');
		var th12 = $('<th>').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('Labour');
		var th13 = $('<th rowspan="2">').css({'width': '3%','font-size': '14px','text-align': 'center'}).text('WorkLocn\n/Vendor');
		
		tr1.append(th1,th2,th3,th4,th5,th6,th7,th8,th9,th10,th11,th12,th13);
		
		var tr2 = $('<tr style="background-color: black; color:white">').css({
			'height': '17px',
			'width': '100%'
		});
		var th2_1 = $('<th>').css({'width': '10%', 'font-size': '14px','text-align': 'center'}).text('PartName');
		var th2_2 = $('<th>').css({'width': '3%', 'font-size': '14px','text-align': 'center'}).text('PartNo');
		var th2_3= $('<th>').css({'width':  '1%', 'font-size': '14px','text-align': 'center'}).text('PartQ');
		var th2_4 = $('<th>').css({'width': '5%', 'font-size': '14px','text-align': 'center'}).text('LabourName');
		tr2.append(th2_1,th2_2,th2_3,th2_4);
		
		
		var FinalReport	= new Object();
		var Report = data.FinalList;
		
		for(var i = 0; i < Report.length; i++ ) {
			
			var ReportObj = new Object();
			
			if(Report[i].woType == 1 || Report[i].woType == 2)
			{
				ReportObj.Date                = Report[i].issueCreated;
				ReportObj.IssueCatorSRPname   = Report[i].issueCategory;
				ReportObj.IssueorSPDetails    = Report[i].issueSummary;
			}
			
			if(Report[i].woType == 3 || Report[i].woType == 4)
			{
				ReportObj.Date                = Report[i].srcreated;
				ReportObj.IssueCatorSRPname   = Report[i].srprogram;
				ReportObj.IssueorSPDetails    = Report[i].srdetails;
			}
			
			ReportObj.VehicleNo           = Report[i].vehicle_registration;
			ReportObj.vid 				  = Report[i].vehicle_vid;
			ReportObj.vehicle_Odometer    = Report[i].vehicle_Odometer;
			ReportObj.Date2               = Report[i].created;
			
			if(Report[i].woType ==1 || Report[i].woType ==3)
			{
				ReportObj.No  				= Report[i].workorders_Number;
				ReportObj.TaskDetails       = Report[i].job_Type + " " + Report[i].subType;
				ReportObj.WlonOrVendor      = Report[i].workLocation;
				ReportObj.id				= Report[i].workorders_id;
				ReportObj.type		        = 1
			}			
			if(Report[i].woType ==2 || Report[i].woType ==4)
			{
				ReportObj.No                 = Report[i].dseNo;
				ReportObj.InNo               = Report[i].invNo;
				ReportObj.WlonOrVendor       = Report[i].vendorName
				ReportObj.id				 = Report[i].dseId;
				ReportObj.type		         = 2
			}
			
			if(Report[i].woType ==2)
			{
				ReportObj.TaskDetails       = Report[i].issueSummary;
			} 
			if(Report[i].woType ==4)
			{
				ReportObj.TaskDetails      = Report[i].srdetails;
			}
			
			ReportObj.PartName            = Report[i].partName;
			ReportObj.PartNo              = Report[i].partNo;
			ReportObj.PartQty             = Report[i].partQt;
			ReportObj.labername           = Report[i].labername;
			
			FinalReport[i] = ReportObj;
		}
		
		
		
		var tbody = $('<tbody>')
		var srn=1;
		for(var key in FinalReport) {
			
				var tr = $('<tr>').css({
					'height': '40px',
					'width': '100%'
				});
			
			   var td1 = $('<td>').text(srn); srn++;
			   var td2 = $('<td>').text(FinalReport[key].Date);
			   var td3 = $('<td>').html('<a href="showVehicle.in?vid='+FinalReport[key].vid+'" target="_blank">'+FinalReport[key].VehicleNo+'</a>');
			   var td4 = $('<td>').text(FinalReport[key].vehicle_Odometer);
			   var td5 = $('<td>').text(FinalReport[key].IssueCatorSRPname);
			   var td6 = $('<td>').text(FinalReport[key].IssueorSPDetails);
			   var td7 = $('<td>').text(FinalReport[key].Date2);
			   
			   if(FinalReport[key].type==1)
			   {
				    var td8 = $('<td>').html('<a href="showWorkOrder?woId='+FinalReport[key].id+'" target="_blank">WO-'+FinalReport[key].No+'</a>');
			   }
			   if(FinalReport[key].type==2)
			   {
 						var td8 = $('<td>').html('<a href="showDealerServiceEntries?dealerServiceEntriesId='+FinalReport[key].id+'" target="_blank">DSE-'+FinalReport[key].No+'</a>');
			   }
			   var td9 = $('<td>').text(FinalReport[key].InNo);
			   var td10 = $('<td>').text(FinalReport[key].TaskDetails);
			   
			   var td11 = $('<td>').text(FinalReport[key].PartName);
			   var td12 = $('<td>').text(FinalReport[key].PartNo);
			   var td13 = $('<td>').text(FinalReport[key].PartQty);
			   var td14 = $('<td>').text(FinalReport[key].labername);
			   var td15 = $('<td>').text(FinalReport[key].WlonOrVendor);
			   
		       tr.append(td1,td2,td3,td4,td5,td6,td7,td8,td9,td10,td11,td12,td13,td14,td15);
		    
		     tbody.append(tr);
		     
	    }
	    
	    
	    thead.append(tr1,tr2)	
		table.append(thead,tbody)
		$("#WorkOrderTable").append(table);
	}
	else {
		if($("#workOrderType").val() ==0 )
		{
		showMessage('info','No record found !');
		}
	}
	
}
