<%@ include file="taglib.jsp"%>
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
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/issuesOpen/1.in"/>">Issues</a> / <span>Search
						Issues</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchIssues.in"/>" method="post">
							<div class="input-group">
								<input class="form-text" name="Search" type="text"
									required="required" placeholder="I-ID, Vehicle," maxlength="20">
								<span class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="<c:url value="/issuesOpen/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_ALL_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_ALL_ISSUES')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Issues Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="IssuesReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Issues Type :</label>
													<div class="I-size">
														<select class="form-text" name="ISSUES_TYPE_ID"
															id="IssuesType">
															<option value="0">Please select</option>
															<option value="1">Vehicle Issue</option>
															<option value="2">Driver Issue</option>
															<option value="3">Refund Issue</option>
															<option value="4">Other Issue</option>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vehicle </label>
													<div class="I-size">
														<input type="hidden" id="IssuesSelectVehicle"
															name="ISSUES_VID" style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Driver Name :</label>
													<div class="I-size">
														<input type="hidden" id="VehicleTODriverFuel"
															name="ISSUES_DRIVER_ID" style="width: 100%;"
															placeholder="Please Enter 3 or more Driver Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="Type">Branch
														: </label>
													<div class="I-size">
														<input type="hidden" id="IssuesBranch"
															name="ISSUES_BRANCH_ID" style="width: 100%;"
															placeholder="Please Enter 3 or more Branch Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="Type">Department
														: </label>
													<div class="I-size">
														<input type="hidden" id="IssuesDepartnment"
															name="ISSUES_DEPARTNMENT_ID" style="width: 100%;"
															placeholder="Please Enter 2 or more Departnment Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Labels</label>
													<div class="I-size">
														<select class="form-text" name="ISSUES_LABELS_ID"
															id="priority">
															<option value="0">Please select</option>
															<option value="1">NORMAL</option>
															<option value="2">HIGH</option>
															<option value="3">LOW</option>
															<option value="4">URGENT</option>
															<option value="5">VERY URGENT</option>
														</select>
													</div>
												</div>
												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="ISSUES_REPORTED_DATE" required="required" readonly="readonly"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																onclick="this.style.visibility = 'hidden'" name="commit"
																type="submit" value="Search All"> <a
																href="<c:url value="/issues/1.in"/>"
																class="btn btn-info"> <span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/IssuesValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script type="text/javascript">
		$(function() {
			function cb(start, end) {
				$('#reportrange').val(
						start.format('DD-MM-YYYY') + ' to '
								+ end.format('DD-MM-YYYY'));
			}
			cb(moment().subtract(1, 'days'), moment());

			$('#reportrange').daterangepicker(
					{
						maxDate: new Date(),
						format : 'DD-MM-YYYY',
						ranges : {
							'Today' : [ moment(), moment() ],
							'Yesterday' : [ moment().subtract(1, 'days'),
									moment().subtract(1, 'days') ],
							'Last 7 Days' : [ moment().subtract(6, 'days'),
									moment() ]
							
						}
					}, cb);
		});
	</script>
</div>