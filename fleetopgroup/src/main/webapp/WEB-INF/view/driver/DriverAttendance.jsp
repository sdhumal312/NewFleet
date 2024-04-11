<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/fullcalendar/fullcalendar.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/fullcalendar/fullcalendar.print.css" />"
	media="print">
<style>
.box-partheader{border:none;position:relative}.partboxheader{width:100%;margin:0 auto}.partboxheader h3{font-size:15px;font-weight:700;text-align:center}.partboxheader p{font-size:13px;font-weight:600;text-align:center}.workingday{margin:10 auto;width:100%;padding:5px}.workingday .name{float:left;width:60%;font-size:14px;text-align:right}.workingday .value{float:left;width:30%;font-size:18px;text-align:center}.workingday .value span{font-size:13px}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <span>Driver
						Attendance details</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_DRIVER_ATTENDANCE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_DRIVER_ATTENDANCE')">
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-12">
						<div class="box">
							<div class="box-body">
								<!-- Show the User Profile -->
								<div class="col-md-9 col-sm-10 col-xs-12">
									<c:choose>
										<c:when test="${driver.driver_photoid != null}">
											<a
												href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
												class="zoom" data-title="Driver Photo" data-footer=""
												data-type="image" data-toggle="lightbox"> <img
												src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
												class="img-circle img-responsive" alt="Driver Profile"
												width="200" height="200" />
											</a>
										</c:when>
										<c:otherwise>
											<img src="resources/images/User-Icon.png"
												alt="Driver Profile" class="img-circle img-responsive"
												width="200" height="200" align="left" />
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col-md-9 col-sm-9 col-xs-12">

									<h3>
										<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
												value="${driver.driver_firstname}" /> <c:out
												value="${driver.driver_Lastname}" /></a>
									</h3>

									<div class="partboxheader">
										<span class="fa fa-black-tie" aria-hidden="true"
											data-toggle="tip" data-original-title="Job Role"> </span> <span
											class="text-muted"><c:out
												value="${driver.driver_jobtitle}" /></span>
									</div>
									<div class="partboxheader">
										<span class="fa fa-users" aria-hidden="true" data-toggle="tip"
											data-original-title="Group Service"> </span> <a href=""><c:out
												value="${driver.driver_group}" /></a>
									</div>
									<div class="partboxheader">
										<span class="fa fa-empire" aria-hidden="true"
											data-toggle="tip" data-original-title="Emp Number"> </span> <span
											class="text-muted"><c:out
												value="${driver.driver_empnumber}" /></span>
									</div>
									<div class="partboxheader">
										<h3>Monthly Attandance</h3>
										<p>From : ${startDateNew} To: ${startEndNew}
										<p>
									</div>
									<div class="workingday">
										<c:if test="${DriverSalaryType == 1 }">
											<span class="name">Driver Worked =</span>
											<span class="value">${Worked}
												<span>days</span>
											</span>
											<span class="name">per day Salary =</span>
											<span class="value">${PerDay}</span>										
										</c:if>
										<c:if test="${DriverSalaryType == 2 }">
											<span class="name">Driver Trips =</span>
											<span class="value">${TripCount}
												<span>trips</span>
											</span>
											<span class="name">per Trip Salary =</span>
											<span class="value">${PerDay}</span>										
										</c:if>
										<c:if test="${DriverSalaryType == 3 }">
											<span class="name">Driver KM =</span>
											<span class="value">${TotalKM}
												<span>kms</span>
											</span>
											<span class="name">per KM Salary =</span>
											<span class="value">${PerDay}</span>										
										</c:if>
									</div><hr>
									<div class="workingday">
										<c:if test="${DriverSalaryType == 1 }">
											<span class="name">Salary ${Worked} X ${PerDay} =</span>																					
										</c:if>
										<c:if test="${DriverSalaryType == 2 }">
											<span class="name">Salary ${TripCount} X ${PerDay} =</span>																																										
										</c:if>
										<c:if test="${DriverSalaryType == 3 }">
											<span class="name">Salary ${TotalKM} X ${PerDay} =</span>																																																															
										</c:if>
										<span class="value">${Salary}
										</span>
										<span class="name">ESI =</span>
										<span class="value">${ESIAmount}</span>
										<span class="name">PF =</span>
										<span class="value">${PFAmount}</span>
										<span class="name">Tripheet Bhatta = </span>
										<span class="value">${totalDriverBhatta}</span>
										<span class="name">Total= </span>
										<span class="value">&nbsp;<fmt:formatNumber type="number" pattern="#.##" value="${ Total + totalDriverBhatta}" /></span>
										
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-7 col-xs-12">
						<div class="box">
							<div class="box-body">
								<input type="hidden" id="start" value="${startDate}"> <input
									type="hidden" id="end" value="${startEnd}">
								<!-- THE CALENDAR -->
								<div id="fullcal"></div>

							</div>
						</div>
						
					</div>
					<div class="col-sm-1 col-md-2  col-xs-12">
					<table class="table">
							<tbody>
								<tr>
									<td style="background-color:green; width: 20px;"></td>
									<td>Tripsheet Attandance</td>
								</tr>
								<tr>
									<td style="background-color: blue;"></td>
									<td>Halt Attandance</td>
								</tr>
							</tbody>
						</table>
					<%@include file="DriverSideMenu.jsp"%>
				</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fullcalendar/fullcalendar.min.js" />"></script>
	<!-- Page specific script -->

	<script type="text/javascript">
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
			var driverId = ${driver.driver_id};
			load(driverId, '' + startDate + '', '' + startEnd + '');
		});

		function load(driverId, shiftdate, MonthType) {
			$.ajax({
				type 		: "GET",
				contentType : "application/json",
				data 		: {
					Id 		: driverId,
					start 	: shiftdate,
					end 	: MonthType
				},	
					url 		: "scheduleDriverAttendance.in",
					dataType 	: "json",
					success 	: function(data) {
						console.log('data : ', data);
						$('div[id*=fullcal]').fullCalendar(
							{
								theme 	: false,
								header 	: {
									left 	: '',
									center 	: 'prev title next',
									right 	: ''
									},
								lazyFetching 	: false,
								defaultDate 	: '' + shiftdate + '',
								defaultView 	: 'month',
								allDay 			: false,
								editable 		: true,
								eventLimit 		: true, // allow "more" link when too many events
								events 			: $.map(data, function(item, i) {
													var event	= new Object();
													event.start = new Date(
																	item.DAYEAR,
																	item.DAMONTH,
																	item.DADATE);
													// event.start = new Date(item.ATTENDANCE_DATE);
													//event.end = new Date(item.ATTENDANCE_DATE);
													event.title 			= 'TS-' + item.TRIPSHEETNUMBER;
													event.url  	 			= 'getTripsheetDetails.in?tripSheetID=' + item.TRIPSHEETID + '';
													event.candidateStatus 	= item.ATTENDANCE_STATUS;
													event.recuriterName 	= item.TRIP_ROUTE_NAME;
		
													return event;
												}), eventRender : function(event, eventElement) {
													if ((event.candidateStatus == "PASS")) {
														eventElement.css(
															'background-color',
															'Green');
				
													} else {
														eventElement.css(
															'background-color',
															'Blue');
													}
				
													var eventDate 	= event.start;
													var calendarDate = $('#fullcal').fullCalendar('getDate');
													if (eventDate.get('month') !== calendarDate.get('month')) {
														return false;
													}
												},loading : function(bool) {
													if (bool)
														$('#loading').show();
													else
														$('#loading').hide();
												}
											});

						$('.fc-prev-button').click(function() {
							window.location.href = '<c:url value="/ShowDriverAdPrev.in?Id='
									+ driverId
									+ '&start='
									+ shiftdate
									+ '"></c:url>';

						});

						$('.fc-next-button').click(function() {
							window.location.href = '<c:url value="/ShowDriverAdNext.in?Id='
									+ driverId
									+ '&end='
									+ MonthType
									+ ' "></c:url>';

						});
					},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							debugger;
						}
					});

			$('div[id*=fullcal]').show();
		}
	</script>
</div>