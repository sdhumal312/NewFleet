var serviceReminderList;

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
			load('' + startDate + '', '' + startEnd + '');
});

function load(startDate, startEnd){
	var jsonObject			= new Object();
	jsonObject["fromDate"] 		=  startDate.trim().split("-").reverse().join("-");
	jsonObject["toDate"] 			=  startEnd.trim().split("-").reverse().join("-");
			$.ajax({
				 url: "getServiceReminderDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
				success : function(data) {
					renderData(data, startDate.trim().split("-").reverse().join("-"), startEnd.trim().split("-").reverse().join("-"));
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					debugger;
				}
			});
	$('div[id*=fullcal]').show();
	
}

function renderData(data,startDate, startEnd){
	serviceReminderList = data.serviceReminder;
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
						showNonCurrentDates : false,
						events : $.map(
										data.serviceReminder,
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

						eventRender : function(event,
								eventElement) {
							eventElement.css('background-color',event.color);
							var eventDate = event.start;
							var calendarDate = $(
									'#fullcal')
									.fullCalendar(
											'getDate');
							if (eventDate.get('month') !== calendarDate
									.get('month')) {
								return false;
							}
							
							
							if(event.description != null && event.description != 'null') {
								eventElement.popover({
									title: event.title,
									content: event.description,
									trigger: 'hover',
									placement: 'top',
									container: 'body'
								});
							}
						},
						loading : function(bool) {
							if (bool)
								$('#loading').show();
							else
								$('#loading').hide();
						},dayRender: function (date, cell) {
							var today = new Date();
							
							for(var i=0; i<serviceReminderList.length;i++){
								
								if (date.isSame(moment(serviceReminderList[i].eventDate), "day")) {
									if(serviceReminderList[i].eventHistory){
										cell.css("background-color", "green");
									}else{
										if (date.isBefore(today, "day")) {
								            cell.css("background-color", "red");
								        }
										if (date.isAfter(today, "day") || date.isSame(today, "day")) {
								            cell.css("background-color", "blue");
								        }
									}
									
									
									break;
						        }
						       
							}
							
					    },dayClick: function(date, jsEvent, view) {
					    	
					    	var selectedDate =	moment(date.format());
					    	var startDate = moment($("#start").val());
							var startEnd =  moment($("#end").val());
							var jsonObject			= new Object();
					    	jsonObject["Date"] 		=  date.format();
					   
					    	//date.isSame(moment(serviceReminderList[i].eventDate), "day") date.isBefore(today, "day")
					    	if(!selectedDate.isBefore(startDate, "day") && !selectedDate.isAfter(startEnd, "day")){
					    		showLayer();
						    	$.ajax({
									 url: "getServiceReminderListOftheDay",
						             type: "POST",
						             dataType: 'json',
						             data: jsonObject,
									success : function(data) {
										renderModelData(data);
										hideLayer();
									},
									error : function(XMLHttpRequest, textStatus,
											errorThrown) {
										debugger;
										hideLayer();
									}
								});
						    	
						    	
					    	}else{
					    		showMessage('info', 'selected date is outside this month please select current month date !');
					    	}
	
						   }
						});
	
			$('.fc-prev-button')
			.click(
					function() {
						showLayer();
						window.location.href = 'ShowServiceReminderCalenderPre.in?start='
								+ startDate;
		
					});
		
			$('.fc-next-button')
				.click(
					function() {
						showLayer();
						window.location.href = 'ShowServiceReminderCalenderNext.in?end='
								+ startEnd;
			});
			jQuery('.fc-event-container').on( 'click', '.fc-event', function(e){
			    e.preventDefault();
			    window.open( jQuery(this).attr('href'), '_blank' );
			});
			hideLayer();
}

function renderModelData(data){
	if(data.serviceReminderfinalList != null && data.serviceReminderfinalList.length > 0){
		$('#dataTable').show();
		$("#tableBody tr").remove();
		var srNo = 1;
		var curl = "";
		var serviceUrl = "";
	    var vehicleUrl = "";
		for(var i = 0; i< data.serviceReminderfinalList.length; i++){
			
			var reminder = data.serviceReminderfinalList[i];
			if(reminder.workOrderId != null){
				curl = '<a target="_blank" href="showWorkOrder?woId='+reminder.workOrderId+'">'+"WO-"+reminder.workOrderNumberStr+'</a>';
			}
			serviceUrl	= '<a target="_blank" href="ShowService.in?service_id='+reminder.service_id+'">'+"SR-"+reminder.service_Number+'</a>';
			vehicleUrl	= '<a target="_blank" href="showVehicle?vid='+reminder.vid+'">'+""+reminder.vehicle_registration+'</a>';
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+serviceUrl+'</td>'
					+'<td>'+vehicleUrl+'</td>'
					+'<td>'+reminder.service_type+'</td>'
					+'<td>'+reminder.servceDate+'</td>'
					+'<td>'+reminder.threshHoldDate+'</td>'
					+'<td>'+curl+'</td>'
					+'<td>'+reminder.workOrderCompletedOn+'</td>'
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
		$('#editrenewalPeriod').modal('show');
	}else{
		$('#dataTable').hide();
		showMessage('info', 'No record found !');
	}
}