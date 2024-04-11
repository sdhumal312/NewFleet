<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/mdi/css/simplepicker.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/consumption/consumption.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a id="cancel" href="#">Consumption</a> / Upholstery Consumption
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'TripSheet Summary Data Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a class="btn btn-danger" id="cancel1" href="#">Cancel</a>
				</div>
			</div>
		</div>
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
		<input type="hidden" id="companyId">
	</section>
	<section>
		<!-- <div class="col-md-7 "> -->
			<div class="row">
				<div id="fuelConsumptionDiv" class="col-sm-3 stretch-card grid-margin">
					<div class="card bg-gradient-blueThree card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Upholstery Entry Count <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="entryCount"></h2>
						</div>
					</div>
				</div>
				<div id="fuelConsumptionDiv" class="col-sm-3 stretch-card grid-margin">
					<div class="card bg-gradient-blueThree card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Upholstery Total Consumption <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="totalConsumption"></h2>
						</div>
					</div>
				</div>
				<div id="fuelConsumptionDiv" class="col-sm-3 stretch-card grid-margin">
					<div class="card bg-gradient-blueThree card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Upholstery Total Remove Count <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="totalRemove"></h2>
						</div>
					</div>
				</div>
				
					
					
			</div>
			<div class="row">
				<input type="hidden" id="defaultCompanyId" value="${companyId}">
				<input type="hidden" id="userId" value="${companyId}">
				<input type="hidden" id="startDate" value="${startDate}">
				<input type="hidden" id="endDate" value="${endDate}">
				<input type="hidden" id="dateType" value="${dateType}">
				<input type="hidden" id="consumptionType" value="${consumptionType}">
				<div class="table-responsive">
					<table class="table">
					<thead>
						<tr class="bg-gradient-blueThree text-white ">
							<th class="fit ar">Upholstery Name</th>
							<th class="fit ar">Upholstery Type</th>
							<th class="fit ar">Vehicle</th>
							<th class="fit ar">Assign Quantity</th>
							<th class="fit ar">Remove Quantity</th>
							<th class="fit ar">Date</th>
							
						</tr>
					</thead>
					<tbody id="consumptionDataBody">
					</tbody>
					</table>
				</div>
				<!-- <div class="text-center">
					<ul id="navigationBar" class="pagination pagination-lg pager">
						
					</ul>
				</div> -->
			</div>
		<!-- </div> -->
		
		<!-- <div class="col-md-5 "> </div> -->
	</section>
</div>  
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/consumption/viewConsumptionData.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/consumption/viewUpholsteryConsumptionData.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>    
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script> 			
   

