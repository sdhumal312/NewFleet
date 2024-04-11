<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <span>Search
						TripSheets</span>
				</div>
				<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchTripSheet.in"/>" method="post">
								<div class="input-group">
									<input class="form-text"
										id="tripStutes" name="tripStutes" type="text"
										required="required"
										placeholder="TS-ID, Vehicle,T-Route, T-Bookref" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					<a href="<c:url value="/newTripSheetEntries.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Trip Sheet Search</legend>

						<div class="row">

							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="TripSheetReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">

													<label class="L-size control-label">Vehicle Name :</label>

													<div class="I-size">
														<input type="hidden" id="RenewalSelectVehicle" name="vid"
															style="width: 100%;" required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleGroup">
														Group :</label>
													<div class="I-size">
														<select name="vehicleGroupId" id="vehicle_Group"
															style="width: 100%;">
															<option value=""></option>
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																	<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleGroup">
														Trip Status :</label>
													<div class="I-size">
														<select name="closeTripStatusId" id="closeTripStatus"
															style="width: 100%;">
															<option value=""></option>
															<option value="1">SAVED</option>
															<option value="2">DISPATCHED</option>
															<option value="3">CLOSED</option>
															<option value="4">ACCOUNT CLOSED</option>
														</select>
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
																name="Tripsheet_daterange" required="required" readonly="readonly"
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
																	name="commit" type="submit" value="Search All">
																	
															<a href="<c:url value="/newTripSheetEntries.in"/>"
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#vehicle_Group, #closeTripStatus").select2({
				placeholder : "Please Enter"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
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
								ranges : {
									Today : [ moment(), moment() ],
									Yesterday : [ moment().subtract(1, "days"),
											moment().subtract(1, "days") ],
									"Last 7 Days" : [
											moment().subtract(6, "days"),
											moment() ],
									"Last 30 Days" : [
												moment().subtract(30, "days"),
												moment() ],
									"Last Month" : [
													moment().subtract(1, 'months').startOf('month'),
													moment().subtract(1, 'months').endOf('month') ]
								}
							}, e) 
		})
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>