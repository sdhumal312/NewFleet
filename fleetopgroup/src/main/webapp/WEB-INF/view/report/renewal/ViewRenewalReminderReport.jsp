<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="open.html"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/viewRenewalReminder.in"/>">Renewal Reminders</a> /
					<span>Search Renewal Reminder Report</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-2">
						<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">RR-</span></span>
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="ID eg: 2323"> <span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachRenewalReminderByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
			<div class="row">
				<!-- <div class="col-md-offset-1 col-md-9"> -->
					<fieldset>
						<legend>Renewal Reminder Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<div class="form-horizontal ">
										<fieldset>
										<input type="hidden" id="createApprovalPermission" value="${createApprovalPermission}">
											<div class="row1">
												<label class="L-size control-label">Vehicle Name :</label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="hidden" id="RenewalSelectVehicle"
															name="vehicle_registration" style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Renewal Type :</label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="hidden" id="RenewalTypeSelect"
															name="renewalTypeId" style="width: 100%;"
															placeholder="Please Enter 3 or more Renewal Type Name" />
													</div>
												</div>
											</div>
											
											<div class="row1">
												<label class="L-size control-label">Renewal Sub
													Type :</label>
												<div class="I-size">
													<div class="col-md-9">
														<select style="width: 100%;" name="renewal_Subid"
															id='to'>
														</select>
													</div>
												</div>
											</div>
											
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label"> Date range: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="reportrange" class="form-text"
															name="Renewal_daterange" required="required" readonly="readonly"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											
											<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Approval Status :</label>
													<div class="I-size">
														<select class="form-text" id="renewalStatusId">
															<option value="0">All</option>
															<option value="1">Not Approved</option>
															<option value="2">Approved</option>
														</select>
													</div>
												</div>
											</div>
											
										</fieldset>
										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>
												<div class="I-size">
													<div class="pull-left">
														<input class="btn btn-success" onclick="searchRenRemndReport()" name="commit" type="submit" value="Search All">
														<a class="btn btn-danger" href="<c:url value="/viewRenewalReminder.in"/>" class="btn btn-info"> <span id="Can">Cancel</span> </a>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
					
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
					
					<div style="height: 200px"></div>
				<!-- </div> -->
			</div>
		</sec:authorize>
		
		<div class="modal fade" id="createApprovalModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Create Approval</h4>
						</div>
						<input type="hidden" id="renewalId">
						<div class="modal-body">
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Approval Remark <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<textarea class="form-text" id="approvalRemark" name="approvalRemark" rows="3" maxlength="240">	
												</textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" value="Create Approval" id="createApproval" class="btn btn-success" />
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close"><spring:message code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<!--Necessary in all pages  -->
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/ViewRenewalReminderReport.js" />"></script>	

	<script>
		$(function() {
			function e(e, t) {
				$("#reportrange").val(
						e.format("DD-MM-YYYY") + " to "
								+ t.format("DD-MM-YYYY"))
			}
			e(moment().subtract(1, "days"), moment()), $("#reportrange")
					.daterangepicker(
							{
								maxDate: new Date(),
								format : 'DD-MM-YYYY',
								ranges : {
									Today : [ moment(), moment() ],
									Yesterday : [ moment().subtract(1, "days"),
											moment().subtract(1, "days") ],
									"Last 7 Days" : [
											moment().subtract(6, "days"),
											moment() ]
								}
							}, e)
		})
	</script>

	<script>
		$(function() {
			$("#to").select2({
				placeholder : "Please Select Type"
			});
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>