$(document).ready(function() {
			var dateObj = new Date();
			var month = dateObj.getUTCMonth() + 1; //months from 1-12
			var day = dateObj.getUTCDate();
			var year = dateObj.getUTCFullYear();

			var shiftdate = year + '-' + month + '-' + day;

			//  alert(shiftdate);

			var MonthType = '';
			var startDate = $("#start").val();
			var startEnd = $("#end").val();
			var vehicleId = $("#vid").val();
			load(vehicleId, '' + startDate + '', '' + startEnd + '');
});

function load(vehicleId, startDate, startEnd){
	var jsonObject			= new Object();

	jsonObject["vehicleId"] 	  	=  vehicleId;
	jsonObject["startDate"] 		=  startDate;
	jsonObject["startEnd"] 			=  startEnd;
			$.ajax({
				 url: "getVehicleBreakDownDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
				success : function(data) {
					renderData(data, vehicleId, startDate, startEnd);
					renderSummaryData(data);
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					debugger;
				}
			});

	$('div[id*=fullcal]').show();
}

function renderSummaryData(data){
	var serviceList	= data.serviceEntriesList;
	var workList	= data.workOrdersList;
	var issueList	= data.issuesList;
	var commentList	= data.vehicleCommentList;
	var tripList	= data.tripSheetList;
	
	if(serviceList != undefined && serviceList.length > 0){
		var srNo = 1;
		var serviceTotal = 0;
		for(var i=0; i<serviceList.length; i++){
			var service = serviceList[i];
			serviceTotal += service.totalserviceROUND_cost;
			var tr =' <tr data-object-id="">'
						+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
						+'<td><a target="_blank" '
						+'href="ServiceEntriesParts.in?SEID='+service.serviceEntries_id+'"'
								+'data-toggle="tip"'
								+'data-original-title="Click Here For Details">'+service.serviceEntries_Number+' </a></td>'
						//+'<td >'+service.serviceEntries_Number+'</td>'
						+'<td >'+service.invoiceDateStr+'</td>'
						+'<td style="text-align: right;">'+service.totalserviceROUND_cost+'</td>'
					  +'</tr>';
			
			$('#serviceEntrySummaryTableBody').append(tr);
			srNo++;
		}
		var footer =' <tr data-object-id="">'
							+'<th colspan="3"style="text-align: left;">Total :</td>'
							+'<th style="text-align: right;">'+serviceTotal+'</td>'
					+'</tr>';
			$('#serviceEntrySummaryTableBody').append(footer);
			$('#serviceEntrySummaryTable').removeClass("hide");
	}

	if(workList != undefined && workList.length > 0){
		var srNo = 1;
		var workTotal = 0;
		for(var i=0; i<workList.length; i++){
			var work = workList[i];
			workTotal += work.totalworkorder_cost;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td><a target="_blank" '
				+'href="showWorkOrder?woId='+work.workorders_id+'"'
				+'data-toggle="tip"'
				+'data-original-title="Click Here For Details">'+work.workorders_Number+' </a></td>'
				+'<td >'+work.start_date+'</td>'
				+'<td >'+work.due_date+'</td>'
				+'<td style="text-align: right;">'+work.totalworkorder_cost+'</td>'
				+'</tr>';
			
			$('#workOrderSummaryTableBody').append(tr);
			srNo++;
		}
		var footer =' <tr data-object-id="">'
			+'<th colspan="4"style="text-align: left;">Total :</td>'
			+'<th style="text-align: right;">'+workTotal+'</td>'
			+'</tr>';
		$('#workOrderSummaryTableBody').append(footer);
		$('#workOrderSummaryTable').removeClass("hide");
	}

	if(issueList != undefined && issueList.length > 0){
		var srNo = 1;
		for(var i=0; i<issueList.length; i++){
			var issue = issueList[i];
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td><a target="_blank" '
				+'href="showIssues.in?Id='+issue.issues_ID_ENCRYPT+'"'
				+'data-toggle="tip"'
				+'data-original-title="Click Here For Details">'+issue.issues_NUMBER+' </a></td>'
				+'<td >'+issue.issues_REPORTED_DATE+'</td>'
				+'<td style="text-align: right;">'+issue.issues_STATUS+'</td>'
				+'</tr>';
			
			$('#issueSummaryTableBody').append(tr);
			srNo++;
		}
		$('#issueSummaryTable').removeClass("hide");
	}

	if(commentList != undefined && commentList.length > 0){
		var srNo = 1;
		for(var i=0; i<commentList.length; i++){
			var comment = commentList[i];
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td >'+comment.created_DATE+'</td>'
				+'<td >'+comment.vehicle_TITLE+'</td>'
				+'<td style="text-align: right;">'+comment.vehicle_COMMENT+'</td>'
				+'</tr>';
			
			$('#vehicleCommentSummaryTableBody').append(tr);
			srNo++;
		}
		$('#vehicleCommentSummaryTable').removeClass("hide");
	}
	
	if(tripList != undefined && tripList.length > 0){
		var srNo = 1;
//		for(var i=0; i<tripList.length; i++){
//			var tripSheet = tripList[i];
//			var tr =' <tr data-object-id="">'
//						+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
//						+'<td><a target="_blank" '
//						+'href="showTripSheet.in?tripSheetID='+tripSheet.tripSheetID+'"'
//								+'data-toggle="tip"'
//								+'data-original-title="Click Here For Details">'+tripSheet.tripSheetNumber+' </a></td>'
//						+'<td >'+tripSheet.tripOpenDate+'</td>'
//						+'<td style="text-align: right;">'+tripSheet.closetripDate+'</td>'
//					  +'</tr>';
//			
//			$('#tripSheetSummaryTableBody').append(tr);
//			srNo++;
//		}
		var footer =' <tr data-object-id="">'
							+'<th colspan="4"style="text-align: left;"> No Of Days in Trip : '+data.noOftripDays+'</td>'
					+'</tr>'
					+' <tr data-object-id="">'
							+'<th colspan="4"style="text-align: left;"> No Of Days Idle : '+data.noOfidleDays+'</td>'
					+'</tr>';
			$('#tripSheetSummaryTableBody').append(footer);
			$('#tripSheetSummaryTable').removeClass("hide");
	}
}

function renderData(data, vehicleId, startDate, startEnd){
	$('div[id*=fullcal]')
			.fullCalendar(
					{
						theme : false,
						header : {
							left : '',
							center : 'prev title next',
							right : ''
						},
						lazyFetching : false,
						defaultDate : '' + startDate
								+ '',
						defaultView : 'month',
						allDay : false,
						editable : true,
						eventLimit : true, // allow "more" link when too many events
						events : $.map(
										data.calenderEventList,
										function(item,
												i) {
											var event = new Object();

											event.start = new Date(
													item.eventYear,
													item.eventMonth,
													item.eventDay);

											event.title = item.eventTitle;
											event.url 	= item.eventUrl;
											event.color = item.eventColor;
											event.description	= item.eventDescription;

											return event;
										}),

						eventRender : function(event, eventElement) {
							eventElement.css('background-color',event.color);
							var eventDate 		= event.start;
							var calendarDate 	= $('#fullcal').fullCalendar('getDate');
							if (eventDate.get('month') !== calendarDate.get('month')) {
								return false;
							}
							if(event.description != null && event.description != 'null') {
								eventElement.popover({
									title		: event.title,
									content		: event.description,
									trigger		: 'hover',
									placement	: 'top',
									container	: 'body'
								});
							}
							
						},
						loading : function(bool) {
							if (bool)
								$('#loading').show();
							else
								$('#loading').hide();
						}
					});
	
			$('.fc-prev-button').click(function() {
						showLayer();
						window.location.href = 'ShowServiceEntriesPrevious.in?vehid='+ vehicleId + '&start=' + startDate;
					});
		
			$('.fc-next-button').click(function() {
						showLayer();
						window.location.href = 'ShowServiceEntriesNext.in?vehid='+ vehicleId + '&end=' + startEnd;
					});
			jQuery('.fc-event-container').on( 'click', '.fc-event', function(e){
			    e.preventDefault();
			    window.open( jQuery(this).attr('href'), '_blank' );
			});
				
			hideLayer();
}