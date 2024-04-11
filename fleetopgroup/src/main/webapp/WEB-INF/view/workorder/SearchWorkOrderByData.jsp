<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> /
					<span>Work Order Report Search</span> 
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">WO-</span></span>
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="WO-NO eg:6878"> <span
								class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachWoByNumber();" class="btn btn-success btn-sm">
								<i class="fa fa-search"></i>	
								</button>
							</span>
						</div>
					</div>
				</div>
				<div class="pull-right">
					<a class="btn btn-danger" href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>WorkOrders Search</legend>

						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
										<div class="form-horizontal ">
											<fieldset>
												
												<div class="row1">
													<label class="L-size control-label">Vehicle Name :</label>
													<div class="I-size">
														<input type="hidden" id="RenewalSelectVehicle"
															name="vehicle_vid" style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>

												<div class="row1" id="grpwoAssigned" class="form-group">
													<label class="L-size control-label" for="subscribe">Assigned To </label>
													<div class="I-size">
														<input class="" placeholder="assignee users" id="subscribe"
															type="hidden" style="width: 100%" name="assigneeId"
															onkeypress="return Isservice_subscribeduser(event);"
															required="required" ondrop="return false;"> <span
															id="woAssignedIcon" class=""></span>
														<div id="woAssignedErrorMsg" class="text-danger"></div>
													</div>
												</div>
												
												<div class="row1">
													<label class="L-size control-label">Work location :
													</label>
													<div class="I-size">
														<select class="select2" name="workorders_location_ID"
															id="location" style="width: 100%;">
															<option value=""></option>
															<c:forEach items="${PartLocations}" var="PartLocations">
																<option value="${PartLocations.partlocation_id}"><c:out
																		value="${PartLocations.partlocation_name}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
												
												<div class="row1">
													<label class="L-size control-label" for="priority">Priority
													</label>
													<div class="I-size">
														<div class="col-md-9">
															<select style="width: 100%;" name="priorityId"
																id="priority">
																<option value=""></option>
																<option value="1">NORMAL</option>
																<option value="2">HIGH</option>
																<option value="3">LOW</option>
																<option value="4">URGENT</option>
																<option value="5">VERY URGENT</option>
															</select>
														</div>
													</div>
												</div>
												
												<div class="row1">
													<label class="L-size control-label"> Date range: <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="dateRange" class="form-text"
																name="PART_RANGE_DATE" required="required" readonly="readonly"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>
							
												<br/>
						
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit" class="btn btn-success"
																id="btn-save">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
												
											</fieldset>
										</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
			
			<div class="row">
				<div class="main-body">
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
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#priority, #location").select2({
				placeholder : "Please select"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js"/>"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/SearchWorkOrderByData.js" />"></script>	

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
</div>