<%@ include file="taglib.jsp"%>
<script type="text/javascript">	 
$(document).ready(function() {
	$("#renPT_option1").change(function(){
		if($(this).val() == 2){
			$("#warehouselocationdiv").addClass('hide');
		}
		
	})
	
	$("#submitButton").click(function(){
	 if(Number($('#workOrderGroup1').val()) <= 0){
			  showMessage('info','Please Select Group!');
			  return false;
		  }
		return true;
	})
	
});
</script>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<div>
		<marquee style="color: red;">Please Note that  <span style="color: blue;">1 : Vehicle Wise Repair Report,  2 : Vehicle Wise Repair Service Report,  3: Vehicle wise Part consumption/Usage report</span> has been Deprecated and will be removed in next release <span style="color: blue;">Please Use "Vehicle Repair And Part Consumption Report" for this</span> </marquee>
	</div>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-content">
					<!--  vehicle Report -->
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleReport"/>">Vehicle Basic Details Report</a>
								</h4>
							</div>
						</div>
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/RequisitionReport"/>">Requisition Report </a>
							</h4>
						</div>
					</div>
					<sec:authorize access="hasAuthority('VIEW_VE_WI_RE_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/RepairReport"/>">Vehicle Wise Repair Report </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_ALL_VE_WI_SE_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/ALLVehicleRepairReport"/>">Vehicle Wise Repair Service Report </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					

					<sec:authorize access="hasAuthority('VH_COMMENT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleCommentReport"/>">Vehicle
										Comment Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_DEPOT_WISE_PARTCONSUMED_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/PartsConsumedReport"/>">Depot wise Parts consumed report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
				
				<!-- Durgamba Vehicle Wise Part Consumption/Usage Report By Dev Yogi Start  -->
				<sec:authorize access="hasAuthority('VEHICLE_WISE_PART_CONSUMTION_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/VehicleWisePartConsumptionAndUsageReport"/>">Vehicle Wise Part Consumption/Usage Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!-- Durgamba Vehicle Wise Part Consumption/Usage Report By Dev Yogi End  --> 
				
				
				<!--For Office Use  Vehicle Creation Report By Dev Yogi Start-->  

				
				<c:if test="${configuration.showVehicleCreationReport}">
						
			<%-- <sec:authorize access="hasAuthority('VEHICLE_CREATION_REPORT')"> --%>

				 <div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/VehicleCreationReport"/>">Vehicle Creation Report</a>
								</h4>
							</div>
						</div>

				<%-- </sec:authorize>	 --%>	
				</c:if>		

				<!--For Office Use Vehicle Creation Report By Dev Yogi End-->
				
				<sec:authorize access="hasAuthority('VEHICLE_WISE_BATTERY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleWiseBatteryReport"/>">All Vehicle Battery Asignment Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('VEHICLE_WISE_TYRE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleWiseTyreReport"/>">All Vehicle Tyre Asignment Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('BANK_WISE_VEHICLE_EMI_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/bankWiseVehicleEmiDetails"/>">Bank
									wise EMI details Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<%-- <sec:authorize access="hasAuthority('VEHICLE_WISE_EMI_REPORT')"> --%>
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/vehicleWiseEmiDetails"/>">Vehicle
									wise EMI details Report</a>
							</h4>
						</div>
					</div>
			<%-- 	</sec:authorize> --%>
			
				<sec:authorize access="hasAuthority('VEHICLE_REPAIR_AND_PART_CONSUMPTION_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/vehicleRepairAndPartConsumptionReport"/>">Vehicle Repair And Part Consumption Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>	
				
					<sec:authorize access="hasAuthority('VEHICLE_ROUTE_CHANGE_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/VehicleRouteChangeReport"/>">Vehicle Route Change Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>

					<sec:authorize access="hasAuthority('VEHICLE_INCIDENT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/vehicleIncidentReport"/>">Vehicle Incident Report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	
		$(document).ready(function() {
			$("#renPT_option1").change(function(){
			if($(this).val() == 2){
			$("#wloc").addClass('hide');
			}
			})
			$("#submitButton1").click(function(){
			
			 if(Number($('#ReportSelectVehicle1').val()) <= 0){
			 showMessage('info','Please Select Vehicle!');
			 return false;
			 }
			 /* else if(Number($('#driverofdepot').val()) <= 0){
			 showMessage('info','Please Select Vehicle Name!');
			 return false;
			 } */
			return true;
			})
			
			});	
	</script>	
</div>