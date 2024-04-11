<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/css/vendor.bundle.base.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/mdi/css/materialdesignicons.min.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/consumption/consumption.css"/>">

<div class="content-wrapper">
	<section class="content">
		<div class="box">
			<div class="box-body">
				<div class="row" style="display: none;">
					<label class="L-size control-label"> Company : </label>
					<div class="I-size">
						<select id="companyId" style="width: 100%;" onchange="getAllCountData();"></select>
						<input type="hidden" id="compId" value="${CompanyId}">
						<input type="hidden" id="compName" value="${CompanyName}">
					</div>
				</div>
				<div class="row" id="proBanner">
					<div class="col-sm-6">
						<label class="L-size control-label">Date range: <abbr
							title="required">*</abbr>
						</label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="dateRange" class="form-text"
									name="TRIP_DATE_RANGE" required="required" onchange="dateChange();"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<label class="L-size control-label">Date Type: 
							<abbr title="required">*</abbr>
						</label>
						
						<div class="I-size btn-group"  >
							<label id="creationDateId" class="btn btn-success  btn-sm " onclick="selectDateType(1);">
								Creation
							</label> 
							<label id="transactionDateId" class="btn btn-default  btn-sm" onclick="selectDateType(2);"> 
								Transaction
							</label>
						</div>
					</div>
	      		</div>
	      	</div>	
      	</div>
      	<div class="main-body">
			<div class="box">
				<div class="box-body">
					<input type="hidden" id="companyDropdownConfig" value="${companyDropdownConfig}">
					<input type="hidden" id="defaultCompanyId" value="${CompanyId}">
					<input type="hidden" id="dateType">
					 <div class="row">
						<div id="fuelConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueOne card-img-holder text-white  ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Fuel" class="card-img-absolute">
									
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											 Fuel<br> Entry 
										</h4>
										<h2 class="mb-5" id="fuelEntryCount"></h2>
									</div>
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Fuel Consumption 
										</h4>
										<h2 class="mb-5" id="fuelConsumptionCount"></h2>
									</div>
								</div>
							</div>
						</div>
						<div id="fuelConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\fuel.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						<div id="ureaConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueTwo  card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Urea" class="card-img-absolute">
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Urea <br>Entry 
										</h4>
										<h2 class="mb-5" id="ureaEntryCount"></h2>
									</div>
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Urea Consumption 
										</h4>
										<h2 class="mb-5" id="ureaConsumptionCount"></h2>
									</div>
								</div>
							</div>
						</div>
						<div id="ureaConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueTwo card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\urea.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						<div id="refreshmentConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueThree card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Refreshment" class="card-img-absolute">
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Refreshment Entry 
										</h4>
										<h2 class="mb-5" id="refreshmentEntryCount"></h2>
									</div>
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Refreshment Consumption 
										</h4>
										<h2 class="mb-5" id="refreshmentConsumptionCount"></h2>
									</div>
								</div>
							</div>
						</div>
						<div id="refreshmentConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueThree card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\fuel.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>	
					 </div>
					  <div class="row">
					 	<div id="partConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Part" class="card-img-absolute">
									<h4 class="font-weight-normal mb-3">	
										Part Consumption <em class="mdi mdi-chart-line mdi-24px float-right"></em>
									</h4>
									<h2 class="mb-5" id="partConsumptionCount"></h2>
								</div>
							</div>
						</div>
						 <div id="partConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						<div id="tyreConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenTwo card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Tyre" class="card-img-absolute">
									<h4 class="font-weight-normal mb-3">	
										Tyre Consumption <em class="mdi mdi-chart-line mdi-24px float-right"></em>
									</h4>
									<h2 class="mb-5" id="tyreConsumptionCount"></h2>
								</div>
							</div>
						</div>
						<div id="tyreConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenTwo card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\tyre.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						<div id="batteryConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenThree card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Battery" class="card-img-absolute">
									<h4 class="font-weight-normal mb-3">	
										Battery Consumption <em class="mdi mdi-chart-line mdi-24px float-right"></em>
									</h4>
									<h2 class="mb-5" id="batteryConsumptionCount"></h2>
								</div>
							</div>
						</div>
						<div id="batteryConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenThree card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\battery.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
					 </div>
					  <div class="row">
					  <div id="upholsteryConsumptionDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-orangeOne card-img-holder text-white ">
								<div class="card-body">
								<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Upholstery`" class="card-img-absolute">
								<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Upholstery Entry 
										</h4>
										<h2 class="mb-5" id="upholteryEntryCount"></h2>
									</div>
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											Upholstery Consumption 
										</h4>
										<h2 class="mb-5" id="upholsteryConsumptionCount"></h2>
									</div>
									
								</div>
							</div>
						</div>
						 <div id="upholsteryConsumptionGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-orangeOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\upholstery.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</section>
</div>
<div class="modal fade" id="WO_SE_Modal" role="dialog">
	<div class="modal-dialog" style="width: 70%;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">WO-SE Part Consumption Count</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div id="workOrderConsumptionDiv" class="col-sm-6 stretch-card grid-margin">
							<div class="card bg-gradient-primary card-img-holder text-white  ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="WorkOrders" class="card-img-absolute">
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											WO Part Count 
										</h4>
										<h2 class="mb-5" id="WO_PartEntryCount"></h2>
									</div>
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											WO Part Consumption 
										</h4>
										<h2 class="mb-5" id="WO_PartConsumptionCount"></h2>
									</div>
								</div>
							</div>
						</div>
						<div id="serviceEntryConsumptionDiv"  class="col-sm-6 stretch-card grid-margin">
							<div class="card bg-gradient-primary  card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Urea" class="card-img-absolute">
									
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											SE Part Count 
										</h4>
										<h2 class="mb-5" id="SE_PartEntryCount"></h2>
									</div>
									<div class="col-sm-6">
										<h4 class="font-weight-normal mb-3">	
											SE Part Consumption 
										</h4>
										<h2 class="mb-5" id="SE_PartConsumptionCount"></h2>
									</div>
								</div>
							</div>
						</div>
				</div>
				
				<br />
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>



<%-- <div class="content-wrapper">
	
	<div class="row">
		<a id="tsCount" href="#">
			<div id="tripsheet1" class="col-md-4 stretch-card grid-margin">
				<div class="card bg-gradient-danger card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate" name="tripSheetSummaryDate" val="1"> 
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Trip Sheet 
							<i class="mdi mdi-chart-line mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="tripsheetCount"></h2>
					</div>
				</div>
			</div>
		</a> 
		<div id="tripsheet2" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		<a id="fCount" href="#">
			<div id="fuel1" class="col-md-4 stretch-card grid-margin ">
				<div class="card bg-gradient-info card-img-holder text-white ">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Fuel Entries 
						<i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="fuelCount"></h2>
					</div>
				</div>
			</div>
		</a> 
		<div id="fuel2" class="col-md-4  ">
			<div class="  card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		
		<a id="workOrdersCount" href="#">
			<div id="workOrder1" class="col-md-4 stretch-card grid-margin " width="20%">
				<div class="card bg-gradient-success card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Work Orders 
						<i class="mdi mdi-diamond mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="workOrderCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="workOrder2" class="col-md-4 stretch-card grid-margin " width="20%">
			<div class="card bg-gradient-success card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
	</div>
	<div class="row">
		<a id="srCount" href="#">
			<div id="serviceReminder1" class="col-md-4 stretch-card grid-margin ">
				<div class="card bg-gradient-primary card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Service Reminders 
						<i class="mdi mdi-chart-line mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="serviceEntriesCount"></h2>
					</div>
				</div>
			</div>
		</a> 
		<div id="serviceReminder2" class="col-md-4 stretch-card grid-margin ">
			<div class="card bg-gradient-primary card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
		<a id="rrCount" href="#">
			<div id="renwalReminder1" class="col-md-4 stretch-card grid-margin ">
				<div class="card bg-gradient-warning card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Renewal Reminders
						<i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="RRCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="renwalReminder2" class="col-md-4 stretch-card grid-margin ">
			<div class="card bg-gradient-primary card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
		<a id="issueCountDetails" href="#">
			<div id="issue1" class="col-md-4 stretch-card grid-margin " width="20%">
				<div class="card bg-gradient-secondary card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Issues 
						<i class="mdi mdi-diamond mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="issueCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="issue2" class="col-md-4 stretch-card grid-margin ">
			<div class="card bg-gradient-primary card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
	</div>
	
	<div class="row">
		<a href="<c:url value="/getServiceEntrySummaryData"/>">
		<a id="serviceEntryCountDetails" href="#">
			<div id="serviceEntry1" class="col-md-4 stretch-card grid-margin" >
				<div class="card bg-gradient-test1 card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate"
							name="tripSheetSummaryDate" val="1"> <img
							src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg"
							class="card-img-absolute">
						<h4 class="font-weight-normal mb-3">
							Service Entry <i class="mdi mdi-chart-line mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="serviceEntryCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="serviceEntry2" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		
		<sec:authorize access="hasAuthority('FLAVOR_FOUR_PRIVILEGE')">
		<a id="pickAndDropCountDetails" href="#">
			<div id="pickDrop1" class="col-md-4 stretch-card grid-margin" >
				<div class="card bg-gradient-lightDark card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate"
							name="tripSheetSummaryDate" val="1"> <img
							src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg"
							class="card-img-absolute">
						<h4 class="font-weight-normal mb-3">
							Tripsheet Pick & Drop <i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="pickAndDropCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="pickDrop2" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		</sec:authorize>
		
	</div>
	
	<section class="content">
		<div class="box">
			<div class="box-body">
			
				<div class="chart-container" id="chartContainer">
					<canvas id="bar-chartcanvas"></canvas>
				</div>
			
			</div>
		</div>
	</section> --%>
	
	<div id="loader"></div>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="popover"]').popover()
		})
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/consumption/viewConsumption.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
