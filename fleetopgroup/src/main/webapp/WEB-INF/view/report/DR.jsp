<%@ include file="taglib.jsp"%>
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
					/ <a href="<c:url value="/DR.in"/>">Driver Report</a>
				</div>
				<div class="pull-right">

					<button type="button" class="btn btn-success" data-toggle="modal"
						data-target="#addImport" data-whatever="@mdo">
						<span class="fa fa-file-excel-o"> Generate Driver Last month
							Salary Details</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">

			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">


				<div class="row">
					<c:if test="${param.save eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Select Driver Salary created successfully .
						</div>
					</c:if>
					<c:if test="${param.already eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Select Driver Salary was Already created.
						</div>
					</c:if>
				</div>

				<!-- Modal  and create the javaScript call modal -->
				<div class="modal fade" id="addImport" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content">
							<form method="post"
								action="<c:url value="/UpdateLastSalaryDetails.in"/>"
								enctype="multipart/form-data">
								<div class="panel panel-default">
									<div class="panel-heading clearfix">
										<h3 class="panel-title">Driver Last month Salary Details</h3>
									</div>
									<div class="panel-body">
										<div class="form-horizontal">
											<br>
											<div class="row1">
												<label class="L-size control-label">Group: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="SelectFuelGroup"
														name="DRIVER_GROUP_ID" style="width: 100%;"
														required="required" placeholder="Please Select Group" />
													<p class="help-block">Select One Group</p>
												</div>
											</div>
											<br>
											<div class="row1">
												<label class="L-size control-label"> Job Title :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="AttGroupDriverJob_ID"
														required="required" name="DRIVER_JOBTITLE"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Job Type" />
												</div>
											</div>
											<br>
											<div class="row1">
												<label class="L-size control-label">Current Month
													date:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="rangeFuelMileage" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
													<p class="help-block">Select Current Month date to
														Gender last month driver salary details</p>
												</div>
											</div>
										</div>

										<div class="row1 progress-container">
											<div class="progress progress-striped active">
												<div class="progress-bar progress-bar-success"
													style="width: 0%">Upload Last month salary details
													Please wait..</div>
											</div>
										</div>
										<div class="modal-footer">
											<input class="btn btn-success"
												onclick="this.style.visibility = 'hidden'" name="commit"
												type="submit" value="Update last month salary" id="myButton"
												data-loading-text="Loading..." class="btn btn-primary"
												autocomplete="off" id="js-upload-submit"
												value="Add Document" data-toggle="modal"
												data-target="#processing-modal">
											<button type="button" class="btn btn-link"
												data-dismiss="modal">Close</button>
										</div>
										<script>
											$('#myButton')
													.on(
															'click',
															function() {
																//alert("hi da")
																$(
																		".progress-bar")
																		.animate(
																				{
																					width : "100%"
																				},
																				2500);
																var $btn = $(
																		this)
																		.button(
																				'loading')
																// business logic...

																$btn
																		.button('reset')
															})
										</script>

									</div>
								</div>
							</form>
						</div>
					</div>
				</div>

				<sec:authorize access="hasAuthority('VIEW_DR_DE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverDetailsReport"/>">Driver details Report  </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('NEW_DRIVER_DETAILS_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/driverDetailsReport"/>">Driver
									details Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DR_CO_RE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverCommentRemarkReport"/>">Driver / Conductor All Remarks
									Report  </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DL_EX_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverDLEDReport"/>">DL Expiry Date Range Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('DRIVER_RENEWAL_REMINDER_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/driverRenewalReminderReport"/>">Driver Renewal Reminder Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('VIEW_DR_MO_ES_PF_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverMonthEsiPFReport"/>">Driver Month wise ESI, PF Date
									Range Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('DRIVER_LEDGER_ACCOUNT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/driverLedgerAccountReport"/>">Driver Ledger Account Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">
					<!-- SRS TRAVELS SHOW  -->
					
					<!--SRS Travels Driver Local Halt Report By Dev Yogi Start -->
					<sec:authorize access="hasAuthority('VIEW_DR_LO_HA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverlocalHaltReport"/>">Driver Local Halt Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>				
				
					<!--SRS Travels Driver Local Halt Report By Dev Yogi End  -->
					
					<!--SRS Travels TripSheet HaltBata Report Dev Yogi Start-->
					<sec:authorize access="hasAuthority('VIEW_TS_HA_BA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/HaltDateRangeReport"/>">TripSheet HaltBata Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					<!--SRS Travels TripSheet HaltBata Report Dev Yogi End-->
		
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DE_PE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DepotWisePenaltyReport"/>">Depot Wise Penalty Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DR_CO_PE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DepotDriConPenaltyReport"/>">Driver / Conductor Wise Penalty
									Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
		
			<sec:authorize access="hasAuthority('VIEW_DO_AD_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/DepotWiseAdvanceReport"/>">Depot Wise Advance Report </a>
							</h4>
						</div>
					</div>
			</sec:authorize>
													
		<sec:authorize access="hasAuthority('VIEW_DR_CO_AD_REPORT')">
				<div class="panel box box-primary">
				<div class="box-header with-border">
					<h4 class="box-title">
						<a 
							href="<c:url value="/DepotDriConAdvanceReport"/>">Driver / Conductor Wise Advance
									Report </a>
					</h4>
				</div>
				</div>
			</sec:authorize>
			
				
				<sec:authorize access="hasAuthority('VIEW_AD_PE_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a 
									href="<c:url value="/DepotAdvancePenaltyReport"/>">Advance / Penalty Depot Wise
									Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('DRIVER_COMMENT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverCommentReport"/>">Driver Comment Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('DRIVER_COMMENT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverEngagementAndPerformanceReport"/>">Driver Engagement & Performance Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('DRIVER_TRIPSHEET_ADVANCE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverTripsheetAdvanceReport"/>">Driver Tripsheet Advance Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/driverESIPFTripWiseReport"/>">Driver PF ESI Report </a>
								</h4>
							</div>
						</div>
						
								<sec:authorize access="hasAuthority('DRIVER_INCIDENT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/driverIncidentReport"/>">Driver Basic Incident Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
						
						<sec:authorize access="hasAuthority('DRIVER_BASIC_DETAILS_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverBasicDetailsReport"/>">Driver Basic Details Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('DRIVERS_LEDGER_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a 
									href="<c:url value="/DriverLedgerReport"/>">Driver Ledger Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('DRIVER_SALARY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a 
										href="<c:url value="/DriverSalary_Report"/>">Driver Salary Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
			</div>
		</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/validateReport1.js"/>"></script>	
		
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>
