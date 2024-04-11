<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<style>
	     
  .count{
  font-weight: bold;
font-size: xx-large;
  }
  .boxShadow{
  box-shadow: 10px 10px 5px grey;
  }
  .bg-gradient-test96{
background: linear-gradient(45deg,#2ed8b6,#59e0c5);
}
.bg-gradient-test95{
background: linear-gradient(45deg,#4099ff,#73b4ff);
}
.bg-gradient-test97{
background: linear-gradient(45deg,#FFB64D,#ffcb80);
}
.bg-gradient-test98{
background: linear-gradient(45deg,#FF5370,#ff869a);
}
.bg-gradient-primary {
  background: -webkit-gradient(linear, left top, right top, from(#da8cff), to(#9a55ff)) !important;
  background: linear-gradient(to right, #da8cff, #9a55ff) !important; }
  
  .bg-gradient-danger {
  background: -webkit-gradient(linear, left top, right top, from(#ffbf96), to(#fe7096)) !important;
  background: linear-gradient(to right, #ffbf96, #fe7096) !important; }
  
  .bg-gradient-test100{
background: linear-gradient(to bottom, #00ffff 0%, #66ffff 100%);
}    
  .bg-gradient-test101{
background-color: #7ee8fa;
background-image: linear-gradient(315deg, #7ee8fa 0%, #80ff72 74%);
}    
  .bg-gradient-test102{
background-color: #045de9;
background-image: linear-gradient(315deg, #045de9 0%, #09c6f9 74%);
}    
</style>

<div class="content-wrapper">

	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a id="cancel" href="#">Work Summary</a> / Tyre  Summary
				</div>
				<div class="pull-right">
					
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Issues Summary Data Report')"
						id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a class="btn btn-danger" id="cancel1" href="#">Cancel</a>
				</div>
			</div>
		</div>
		<input type="hidden" id="companyId">
	</section>
	
	<section class="content2">
	<input type="hidden" id="compId" value="${compId}">
	<input type="hidden" id="isCollaps" value="true">
	<input type="hidden" id="collapsStatusId" >
		<div class="box">
			<div class="box-body">
				<div class="row" style="padding-bottom: 1%;">
					<div class="col col-sm-12 col-sm-12">
						<select  id="locationId" name ="locationName" class="select" 
						style="width:50%" placeholder="All Location" onchange="changeLocation();">
						<option value="0">All</option>
						</select>
					</div>
				</div>
				<div class="row" style="padding-bottom: 1%;">
					<!-- <div class="col col-sm-12 col-md-3 mb-3">
						<div style="background:#FFB6C1;" class="card stretch-card grid-margin text-white">
							<div class="card-header font-weight-bold"><h4>All Tyre</h4></div>
							<div class="card-body">
							<a onclick="getTyreDetails();" title="Click here to see all location count" ><h4 class="count"  id="allCount"></h4></a>
							</div>
						</div>
					</div> -->
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test101 text-white">
							<div class="card-header font-weight-bold"><h4>Assign Tyre Vehicle Count</h4></div>
							<div class="card-body">
							<a onclick="getAllocation();" title="Click here to see all location count" ><h4 class="count" id="assignCount"></h4></a>
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test100 text-white">
							<div class="card-header font-weight-bold"><h4>UnAssign Tyre Vehicle Count</h4></div>
							<div class="card-body">
							<a onclick="showTyreNotAssignModal();" title="Click here to see all location count" ><h4 class="count"  id="tyreNotAssignAllVehicleCount"></h4></a>
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test97 text-white">
							<div class="card-header font-weight-bold"><h4>Maximum Run</h4>
								<div class="row">
								<input id="maxLimit" onkeypress="return isNumberKeyWithDecimal(event,this.id);" style="width:80%;" class="form-control" type="text" placeholder="please set max limit" onkeyup="enterMaxTyreRun(event);"/><button class="btn btn-success"  onclick="getMaxTyreRun();" >Go</button>
								</div>
							</div>
							<div class="card-body">
							<a onclick="getMaxTyre();" title="Click here to see all location count" ><h4 class="count"  id="maxRun"></h4></a>
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-danger text-white">
							<div class="card-header font-weight-bold"><h4>Minimum Run Scraped</h4>
								<div class="row">
								<input id="minLimit" onkeypress="return isNumberKeyWithDecimal(event,this.id);" style="width:80%;" class="form-control" type="text" placeholder="please set min limit" onkeyup="enterMinTyreRun(event);" /><button class="btn btn-success" onclick="getMinTyreRun();" >Go</button>
								</div>
							</div>
							<div class="card-body">
							<a onclick="getMinScrapTyre();" title="Click here to see all location count" ><h4 class="count"  id="minScrap"></h4></a>
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test102 text-white">
							<div class="card-header font-weight-bold"><h4>Issue Tyre</h4>
							</div>
							<div class="card-body">
							<a onclick="showIssueTyreDetails();" title="Click here to see all location count" ><h4 class="count"  id="issueTyreCount"></h4></a>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col col-sm-12 col-sm-12">
	        		<h5 class="text-center" id="rowHeader"></h5>
	        		</div>
	        	</div>	
				<br>
				<div class="row" style="padding-bottom: 1%;">
					<div class="col col-sm-12 col-md-3 mb-3">
						<div style="background:#FFB6C1;" class="card stretch-card grid-margin text-white" >
							<div class="card-header font-weight-bold"><h4>All</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-sm-4">
									<a onclick="getTyreDetails();" title="Click here to see all location count" ><h4 class="count"  id="allCount"></h4></a>
								</div>
									<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(1);">All Location Count</button>
									<button type="button" class="btn" style="" onclick="getTyreDetails(1);">Details</button> -->
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test96 text-white">
							<div class="card-header font-weight-bold"><h4>Available</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-sm-4">
									<a onclick="getAllLocationCount(1);" title="Click here to see all location count" ><h4 class="count"  id="availableCount"></h4></a>
								</div>
									<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(1);">All Location Count</button>
									<button type="button" class="btn" style="" onclick="getTyreDetails(1);">Details</button> -->
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test95 text-white">
							<div class="card-header font-weight-bold"><h4>In-Service</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-md-4">
									<a onclick="getAllLocationCount(2);" title="Click here to see all location count" ><h4 class="count"  id="inServiceCount"></h4></a>
								</div>
								<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(2);">All Location Count</button>
								<button type="button" class="btn" style="" onclick="getTyreDetails(2);">Details</button> -->
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-secondary text-white">
							<div class="card-header font-weight-bold"><h4>Unavailable</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-md-4">
									<a onclick="getAllLocationCount(7);" title="Click here to see all location count" ><h4 class="count"  id="unAvailableCount"></h4></a>
								</div>
								<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(7);">All Location Count</button>
								<button type="button" class="btn" style="" onclick="getTyreDetails(7);">Details</button> -->
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-primary text-white" >
							<div class="card-header font-weight-bold"><h4>Sent-Retread</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-md-4">
									<a onclick="getAllLocationCount(4);" title="Click here to see all location count" ><h4 class="count"  id="sentRetreadCount"></h4></a>
								</div>
								<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(4);">All Location Count</button>
								<button type="button" class="btn" style="" onclick="getTyreDetails(4);">Details</button> -->
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-gradient-test98 text-white"
							onclick="showTripDetails(1);">
							<div class="card-header font-weight-bold"><h4>Scrap</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-md-4">
									<a onclick="getAllLocationCount(3);" title="Click here to see all location count" ><h4 class="count"  id="scrapCount"></h4></a>
								</div>
								<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(3);">All Location Count</button>
								<button type="button" class="btn" style="" onclick="getTyreDetails(3);">Details</button> -->
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-3 mb-3">
						<div class="card stretch-card grid-margin bg-dark text-white"
							onclick="showTripDetails(1);">
						<div class="card-header font-weight-bold"><h4>Sold</h4></div>
							<div class="card-body">
								<div class="col col-sm-12 col-md-4">
									<a onclick="getAllLocationCount(6);" title="Click here to see all location count" ><h4 class="count"  id="soldCount"></h4></a>
								</div>
								<!-- <button type="button" class="btn" style="background: #F4C430;" onclick="getAllLocationCount(6);">All Location Count</button>
								<button type="button" class="btn" onclick="getTyreDetails(6);">Details</button> -->
							</div>
						</div>
					</div>
				</div>
				
				<div id="allLocationRow" class="row collapse"  style="padding-bottom: 1%;">
					 <div class="col col-sm-12 col-sm-12">
	        			<h5 class="text-center" id="locationRowHeader"></h5>
	        		</div>
	        		<div id="allLocationDiv">
	        			
	        		</div>
				</div>
			</div>
		</div>
	</section>
	
	 <div class="modal fade" id="tyreDetailsModel" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Location: <b><span id="locationName"></span></b> Status: <b><span id="tyreStatus"></span></b></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div >
					<table class="table scroll-box " >
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">SR No</th>
							<th class='fit ar' style="text-align: center;">Tyre Serial No</th>
							<th class='fit ar' style="text-align: center;">Tyre Model</th>
							<th class='fit ar' style="text-align: center;">Location</th>
							<th class='fit ar' style="text-align: center;">Status</th>
						</tr>
					</thead>
					<tbody id="tyreDetailsTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="vehicleTyreLayoutPositionModal" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<input type="hidden" id="vid" class="browser-default text-center" style="width:100%" onchange="getAssignedTyreAllocation();">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div >
					<table class="table scroll-box " >
					<thead style="text-align: center;">
						<tr>
								<th class='fit ar' style="text-align: center;">SR No</th>
							<th class='fit ar' style="text-align: center;">Vehicle</th>
							<th class='fit ar' style="text-align: center;">Position</th>
							<th class='fit ar' style="text-align: center;">Tyre Serial No</th>
						</tr>
					</thead>
					<tbody id="vehicleTyreLayoutPositionTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="maxTyreRunModal" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<input type="hidden" id="vid" class="browser-default text-center" style="width:100%" onchange="getAssignedTyreAllocation();">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div >
					<table class="table scroll-box " >
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">SR No</th>
							<th class='fit ar' style="text-align: center;">Tyre Number</th>
							<th class='fit ar' style="text-align: center;">Usage</th>
						</tr>
					</thead>
					<tbody id="maxTyreRunTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="minTyreRunModal" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<input type="hidden" id="vid" class="browser-default text-center" style="width:100%" onchange="getAssignedTyreAllocation();">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div >
					<table class="table scroll-box " >
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">SR No</th>
							<th class='fit ar' style="text-align: center;">Tyre Number</th>
							<th class='fit ar' style="text-align: center;">Usage</th>
						</tr>
					</thead>
					<tbody id="minTyreRunTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="vehicleWithoutTyreModal" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<input type="hidden" id="vid" class="browser-default text-center" style="width:100%" onchange="getAssignedTyreAllocation();">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<div >
				<h5 class="text-center" >Layout Not Created Vehicle</h5>
					<table class="table scroll-box " >
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">SR No</th>
							<th class='fit ar' style="text-align: center;">Vehicle Number</th>
							<th class='fit ar' style="text-align: center;">Vehicle Model</th>
							<th class='fit ar' style="text-align: center;">Action</th>
						</tr>
					</thead>
					<tbody id="tyreLayoutNotCreatedVehicleTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
					<div >
					<h5 class="text-center" >Tyre Not Assigned Vehicle</h5>
					<table class="table scroll-box " id="example">
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">Position</th>
							<th class='fit ar' style="text-align: center;">Vehicle Number</th>
							<th class='fit ar' style="text-align: center;">Action</th>
						</tr>
					</thead>
					<tbody id="vehicleWithoutTyreTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
					
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="issueTyreModal" role="dialog">
			<div class="modal-dialog modal-lg"  >
				<!-- Modal content-->
					<div class="modal-content">
				<div class="modal-header">
					<input type="hidden" id="vid" class="browser-default text-center" style="width:100%" onchange="getAssignedTyreAllocation();">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div >
					<table class="table scroll-box " >
					<thead style="text-align: center;">
						<tr>
							<th class='fit ar' style="text-align: center;">SR No</th>
							<th class='fit ar' style="text-align: center;">Issue Number</th>
							<th class='fit ar' style="text-align: center;">Vehicle Number</th>
							<th class='fit ar' style="text-align: center;">Summary</th>
						</tr>
					</thead>
					<tbody id="issueTyreDetailsTable" style="text-align: center;">
					
					</tbody>

					</table>
				</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/ViewTyreSummary.js"/>"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script> 	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
			<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
</div>
