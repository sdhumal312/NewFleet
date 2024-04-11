$(document).ready(function() {
			var dateObj = new Date();
			var month = dateObj.getUTCMonth() + 1; //months from 1-12
			var day = dateObj.getUTCDate();
			var year = dateObj.getUTCFullYear();

			var shiftdate = year + '-' + month + '-' + day;


			var MonthType = '';
			var startDate = $("#start").val().trim();
			var startEnd = $("#end").val().trim();
			load('' + startDate + '', '' + startEnd + '');
});

function load(startDate, startEnd){
	var jsonObject			= new Object();
	jsonObject["fromDate"] 		=  startDate.split("-").reverse().join("-");
	jsonObject["toDate"] 			=  startEnd.split("-").reverse().join("-");
			$.ajax({
				 url: "getBusBookingDetailsCelender",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
				success : function(data) {
					renderData(data, startDate.split("-").reverse().join("-"), startEnd.split("-").reverse().join("-"));
					totalMonthcount(data,startDate.split("-").reverse().join("-"), startEnd.split("-").reverse().join("-"));
					
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					debugger;
				}
			});
	$('div[id*=fullcal]').show();
	
}

function renderData(data,startDate, startEnd){
	busBookingList = data.busbookinglist;
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
										data.busbookinglist,
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
							
							for(var i=0; i<busBookingList.length;i++){
								if (date.isSame(moment(busBookingList[i].eventDate), "day")) {
										if (date.isBefore(today, "day")) {
								            cell.css("background-color", "red");
								        }
										if (date.isAfter(today, "day") || date.isSame(today, "day")) {
								            cell.css("background-color", "blue");
								        }
									
									break;
						        }
						       
							}
							
					    },dayClick: function(date, jsEvent, view){
					    	dayWiseList(date);
					    	
					    }
					    	
					    
						});
	
			$('.fc-prev-button')
			.click(
					function() {
						showLayer();
						window.location.href = 'ShowBusBookingCalenderPre.in?start='
								+ startDate;
		
					});
		
			$('.fc-next-button')
				.click(
					function() {
						showLayer();
						window.location.href = 'ShowBusBookingCalenderNext.in?end='
								+ startEnd;
			});
			jQuery('.fc-event-container').on( 'click', '.fc-event',  function(e){
				e.preventDefault();
				var date= e.currentTarget.attributes.href.value;
				var dateObj = new moment(date);
				dayWiseList(dateObj);
			});
			hideLayer();
}

function renderModelData(data){
	if(data.busbookinglist != null && data.busbookinglist.length > 0){
		$('#dataTable').show();
		$("#tableBody tr").remove();
		var srNo = 1;
		var curl = "";
		var serviceUrl = "";
	    var vehicleUrl = "";
		for(var i = 0; i< data.busbookinglist.length; i++){
			
			var reminder = data.busbookinglist[i];
			serviceUrl	= '<a target="_blank" href="showBusBookingDetails.in?Id='+reminder.busBookingDetailsId+'">'+"BB-"+reminder.busBookingNumber +'</a>';
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+serviceUrl+'</td>'
					+'<td>'+reminder.nameOfParty+'</td>'
					+'<td>'+reminder.vehicleType+'</td>'
					+'<td>'+data.busbookinglist[i].tripStartDateStr+'</td>'
					+'<td>'+data.busbookinglist[i].tripEndDateStr+'</td>'
					+'<td>'+reminder.pickUpAddress+'</td>'
					+'<td>'+curl+'</td>'
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


function dayWiseList(date){
    	var startDate = moment($("#start").val());
		var startEnd =  moment($("#end").val());
		var jsonObject			= new Object();
		var selectedDate =	moment(date.format());
    	jsonObject["Date"] 		=  date.format();
    	if(!selectedDate.isBefore(startDate, "day") && !selectedDate.isAfter(startEnd, "day")){
    		showLayer();
	    	$.ajax({
				 url: "getBusBookingListOftheDay",
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

function totalMonthcount(data,startDate, startEnd){
	var count=0;
	for(var i=0; i< data.busbookinglist.length; i++){
		count+=data.busbookinglist[i].count;
		console.log("Count",count);
	}
	
	$('#totalCountMonth').html(count);
	
	vehicleTypeWiseCount();
}

function vehicleTypeWiseCount(){
	var jsonObject= new Object();
	
	jsonObject["fromDate"] = $('#start').val().split("-").reverse().join("-");
	jsonObject["toDate"] = $('#end').val().split("-").reverse().join("-");
	$.ajax({
		 url: "getBusBookingVehicleTypewiseCount",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
		success : function(data) {
			console.log(data);
			showVehicleWiseMonthlyCount(data);
			
		},
		error : function(XMLHttpRequest, textStatus,
				errorThrown) {
			debugger;
		}
	});
	
}

function showVehicleWiseMonthlyCount(data){
	console.log(data);
	
	
	$("#VehicleTypeTable").show();
	$("#vehickeTypetableBody tr").remove();
	
	if(data.vehicleTypeWiseCount == null || data.vehicleTypeWiseCount.length >0){
		for(var i=0;i<data.vehicleTypeWiseCount.length;i++){
			
			var btn = document.createElement('button');
			
			
			var tr =' <tr>'
				+'<td><a href="#" class="btn btn-success btn-sm" id="'+data.vehicleTypeWiseCount[i].vehicleTypeId+'" onclick="getVehicleTypeWiseData(event,this.id);">'+data.vehicleTypeWiseCount[i].vehicleType+' '+data.vehicleTypeWiseCount[i].dateWiseCount+'</a></td>'
				+'</tr>';
			$('#vehickeTypetableBody').append(tr);
		}	
		
	}else{
		
		$("#VehicleTypeTable").hide();
	}
}
function getVehicleTypeWiseData(event,id){
	
	
	var jsonObject= new Object();
	
	jsonObject["fromDate"] = $('#start').val().split("-").reverse().join("-");
	
	jsonObject["toDate"] =$('#end').val().split("-").reverse().join("-");
	
	jsonObject["vehicleTypeId"] = id;
	
	console.log(jsonObject);
	
	$.ajax({
		
		url: "getVehicleTypeWiseList",
		type: "POST",
		dataType: "json",
		data: jsonObject,
		
		success: function(data){
			renderModelData(data);
			hideLayer();
		},
		error : function(XMLHttpRequest, textStatus,
				errorThrown) {
			debugger;
		}
		
		
	});
	
	
}