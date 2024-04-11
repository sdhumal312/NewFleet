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
					<a href="open.html"><spring:message code="label.master.home" /></a>
					/ <a href="<c:url value="/RenewalReminder/1/1.in"/>">Renewal
						Reminders</a> / <span>Search Renewal Reminder</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchRenewalReminder.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="vehicle_registration" type="text" required="required"
									placeholder="RR-ID, Rep-Che-No, Pay-Date"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="<c:url value="/RenewalReminder/1/1.in"/>">Cancel</a>
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
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Renewal Reminder Search</legend>

						<div class="row">

							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="RenewalReportFilters.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
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
																name="Renewal_daterange" required="required"
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
																href="<c:url value="/RenewalReminder/1/1.in"/>"
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
					<div style="height: 200px"></div>
				</div>
			</div>
		</sec:authorize>
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

	<script>
		$(function() {
			function e(e, t) {
				$("#reportrange").val(
						e.format("YYYY-MM-DD") + " to "
								+ t.format("YYYY-MM-DD"))
			}
			e(moment().subtract(1, "days"), moment()), $("#reportrange")
					.daterangepicker(
							{
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