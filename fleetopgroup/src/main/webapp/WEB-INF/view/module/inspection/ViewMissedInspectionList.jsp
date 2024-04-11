<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/ViewVehicleInspectionList.in"/>"> Vehicle Inspection </a>/
					Missed Inspection Vehicles
				</div>
				</div>
				
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		
		<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<div class="row">
				<div class="main-body">
				
					<div class="panel box box-primary">
						<div class="box-body">
							<div class="form-horizontal ">
								
								<div class="row1">
									<label class="L-size control-label">Vehicle Name <abbr
										title="required">*</abbr>
									</label>
									<div class="I-size">
											<select id="ReportSelectVehicle" style="width: 100%;" onchange="getMissedDatesForVehicle();">
												<option value="-1">Please Select</option>
												<c:forEach items="${vehicleList}" var="vehicleList">
													<option value="${vehicleList.vid}">${vehicleList.vehicle_registration}</option>
												</c:forEach>
											</select>
									</div>
								</div>
								
								<div class="row1">
									<label class="L-size control-label">Date : 
										<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
											<select id="reportDate" type="text" class="form-text" name="CASH_DATE"
												placeholder="dd-mm-yyyy" required="required"
												data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" ></select> 
									</div>
								</div>
								
								<div class="row1">
									<label class="L-size control-label"></label>

									<div class="I-size">
										<div class="pull-left">
											<button  name="commit"
												class="btn btn-success" id="btn-save">
												<i class="fa fa-search"> Search</i>
											</button>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>
					
					<div class="box">
						<div class="box-body">
						 
							<div class="table-responsive">
								<table id="VendorPaymentTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>		
					
				</div>
			</div>
		</sec:authorize>

	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>		
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/ViewMissedInspectionList.js" />"></script>
</div>