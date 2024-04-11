<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Fuel Chart</span>
				</div>
				<div class="pull-right">

					<sec:authorize access="hasAuthority('ADD_FUEL')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/VehicleFuelDetailsAdd.in?vid=${vehicle.vid}"/>">
							<i class="fa fa-plus"></i> Add Fuel
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_FUEL')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/VehicleFuelGraph.in?vid=${vehicle.vid}"/>">
							Fuel Chart </a>
					</sec:authorize>
					<a class="btn btn-link btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Amazing Nature"
							data-footer="The beauty of nature" data-type="image"
							data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>">
								<c:out value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>

							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_FUEL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_FUEL')">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<div class="row">
						<div class="main-body">
							<div class="box box-success">
								<button class="btn btn-primary btn-sm daterange pull-right">
									<i class="fa fa-calendar"></i>
								</button>
								<div id="FuelChart"
									style="min-width: 310px; height: 400px; margin: 0 auto">
									<span class="loading ng-hide" id="loading"> <img
										alt="Loading" class="loading-img"
										src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
									</span>
								</div>
							</div>

						</div>
					</div>
				</div>
			</sec:authorize>
			<div class="col-md-1 col-sm-1 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript">
		//window.onload =getVehicleOdometerGraph(v);
		$(document).ready(function() {
			var vid = ${vehicle.vid}, ExMFrom = ${vehicle.vehicle_ExpectedMileage}, ExMTo =${vehicle.vehicle_ExpectedMileage_to};
		
			function GetVehicleFuelChart(a,b,c){var d=[];$.getJSON("GetVehicleFuelChart.in",{dateFrom:a,dateTo:b,vid:c,ajax:"true"},function(a){if(null!=a){t=a;for(var b=a.length,c=b-1;b>c&&-1!=c;c--)item={},item.id="FT-"+a[c].fuel_id,item.name=a[c].fuel_date,item.y=a[c].fuel_kml,d.push(item);$("#FuelChart").highcharts({rangeSelecter:{selected:1},title:{text:""},xAxis:{type:"category"},yAxis:{plotLines:[{label:{text:"Expect mileage from "+ExMFrom,x:ExMFrom},color:"orange",width:2,value:ExMFrom},{label:{text:"Expect mileage to "+ExMTo,x:ExMTo},color:"orange",width:2,value:ExMTo}]},legend:{enabled:!1},series:[{name:"KM/L",threshold:ExMFrom,color:"green",negativeColor:"red",data:d},{name:"KM/L",threshold:ExMTo,color:"blue",negativeColor:"transparent",data:d}],tooltip:{headerFormat:'<span style="font-size:11px">{series.name}</span><br>',pointFormat:"<span >{point.id}</span>: <b>{point.y:.2f}</b><br/>"},credits:{enabled:!1}})}else $("#FuelChart").html("Odometer is Empty")})}$(document).ready(function(){function a(a,b){GetVehicleFuelChart(a.format("YYYY-MM-DD"),b.format("YYYY-MM-DD"),vid)}a(moment().subtract(15,"days"),moment()),$("#reportrange").daterangepicker({ranges:{Today:[moment(),moment()],Yesterday:[moment().subtract(1,"days"),moment().subtract(1,"days")],"Last 7 Days":[moment().subtract(6,"days"),moment()],"Last 30 Days":[moment().subtract(29,"days"),moment()],"This Month":[moment().startOf("month"),moment().endOf("month")],"Last Month":[moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")]}},a)}),$(function(){$(".daterange").daterangepicker({ranges:{"Last 7 Days":[moment().subtract(6,"days"),moment()],"This Month":[moment().startOf("month"),moment().endOf("month")],"Last Month":[moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")]},startDate:moment().subtract(29,"days"),endDate:moment()},function(a,b){GetVehicleFuelChart(a.format("YYYY-MM-DD"),b.format("YYYY-MM-DD"),vid)})});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/charts/highcharts.js" />"></script>
	<!--  <script type="text/javascript"  src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/charts/exporting.js" />"></script> -->
</div>