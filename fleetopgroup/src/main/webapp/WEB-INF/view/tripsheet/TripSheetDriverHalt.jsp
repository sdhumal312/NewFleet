<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">	
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
									href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Show
									TripSheet</a> / <small>Add Halt TripSheet</small>
							</div>
							<div class="pull-right"></div>
						</div>
						<sec:authorize access="!hasAuthority('ADD_EXPENSE_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
							<div class="row">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripSheet.created}</h5>
									</div>
								</div>

								<div class="row">
									<h4 align="center">
										<a href="showVehicle.in?vid=${TripSheet.vid}"
											data-toggle="tip" data-original-title="Click Vehicle Info">
											<c:out value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>
								<div class="col-md-3"></div>
							</div>
							<div class="row">
								<h4 align="center">${TripSheet.routeName}</h4>
							</div>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <c:out
												value="${TripSheet.tripOpenDate}  TO" /></a> <a
										data-toggle="tip" data-original-title="Trip Close Date"> <c:out
												value="  ${TripSheet.closetripDate}" /></a></li>
									<li>Group Service : <a data-toggle="tip"
										data-original-title="Group Service"><c:out
												value="${TripSheet.vehicle_Group}" /></a></li>
									<li>Booking No : <a data-toggle="tip"
										data-original-title="Booking No"> <c:out
												value="${TripSheet.tripBookref}" /></a></li>
									<li>Driver 1 : <a data-toggle="tip"
										data-original-title="Driver 1"> <c:out
												value="${TripSheet.tripFristDriverName}  ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}" /></a></li>
									<li>Driver 2 : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}" /></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><c:out
												value="${TripSheet.tripCleanerName}" /></a></li>
									<li>Opening KM : <a data-toggle="tip"
										data-original-title="Opening KM"><c:out
												value="${TripSheet.tripOpeningKM}" /></a></li>
									<li>Closing KM : <a data-toggle="tip"
										data-original-title="closing KM"> <c:out
												value="${TripSheet.tripClosingKM}" /></a></li>
								</ul>
							</div>
							<c:if test="${!empty TripSheetHalt}">
								<fieldset>
									<div class="row">
										<c:if test="${param.success eq true}">
											<div class="alert alert-success">
												<button class="close" data-dismiss="alert" type="button">x</button>
												This Trip Halt created successfully .
											</div>
										</c:if>
										<c:if test="${param.delete eq true}">
											<div class="alert alert-success">
												<button class="close" data-dismiss="alert" type="button">x</button>
												This Trip Halt Removed Successfully.
											</div>
										</c:if>
										<c:if test="${param.already eq true}">
											<div class="alert alert-danger">
												<button class="close" data-dismiss="alert" type="button">x</button>
												This Trip Halt was Already created.
											</div>
										</c:if>
										<div class="">
											<input type="hidden" id="vid" value="${TripSheet.vid}">
											<input type="hidden" id="tripStatusId" value="${TripSheet.tripStutesId}">
											
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Name</th>
														<th class="fit ar" colspan="2">Halt date</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Place</th>
														<th class="fit ar">Paid By</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripSheetHalt}" var="TripSheetHalt">
														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripSheetHalt.DRIVER_NAME}" /></td>
															<td class="fit ar" colspan="2"><c:out
																	value="${TripSheetHalt.HALT_DATE_FROM} to " /> <c:out
																	value="${TripSheetHalt.HALT_DATE_TO}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheetHalt.HALT_AMOUNT}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheetHalt.HALT_PLACE}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheetHalt.HALT_PAIDBY}" /></td>
															<td class="fit ar"><a
																href="#" onclick="removeDriverHalt('${TripSheetHalt.DHID}','${TripSheetHalt.HALT_AMOUNT}');"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</c:if>
							<br>
						<input type="hidden" name="TRIPSHEETID" id="tripSheetId" value="${TripSheet.tripSheetID}"> 
						<input type="hidden" name="TRIP_ROUTE_NAME" id="routeName" value="${TripSheet.routeName}">
						<input type="hidden" name="TRIP_ROUTE_ID" id="routeId" value="${TripSheet.routeID}">
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="userId" value="${userId}">
								<div class="form-horizontal">

									<fieldset>
										<legend>Tripsheet Driver Halt Bata Details</legend>
										<div class="row1">
											<label class="L-size control-label">Driver Name <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="col-md-9">
													<select class="form-text select2"
														name="DRIVERID" id="DRIVERID">
														<option value="${TripSheet.tripFristDriverID}">${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}</option>
														<c:if test="${TripSheet.tripSecDriverID != null && TripSheet.tripSecDriverID > 0}">
															<option value="${TripSheet.tripSecDriverID}">${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}</option>
														</c:if>
														<c:if test="${TripSheet.tripCleanerID != null && TripSheet.tripCleanerID > 0}">
															<option value="${TripSheet.tripCleanerID}">${TripSheet.tripCleanerName}</option>
														</c:if>
													</select>
												</div>
											</div>
										</div>
										<div class="row1">
											<input type="hidden" value="${TripSheet.tripOpenDate}" id="trpiOpendate">
											<input type="hidden" value="${TripSheet.closetripDate}" id="trpiClosedate">
											<input type="hidden" value="${TripSheet.tripStutesId}" id="tripStatusId">
											<input type="hidden" value="${TripSheet.closedByTimeOn}" id="closeTime">
											
											<label class="L-size control-label">Halt Date From
												&amp; To <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date">
													<input type="text" class="form-text" name="HALT_DATE" readonly="readonly"
														data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
														data-mask="" required id="reservation" maxlength="26" onChange="validateHaltDate();">
													<span class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Halt per day Bata
												Amount <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" required="required"
													name="HALT_AMOUNT" id="haltAmount" maxlength="10" onkeypress="return isNumberKeyWithDecimal(event,this.id)">

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Halt Reason <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" required="required"
													name="HALT_REASON" maxlength="200" id="haltReason">

											</div>
										</div>
										<div class="row1" style="display: none;">
											<label class="L-size control-label">Halt Place <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													required="required" value="${place}" name="HALT_PLACE">

											</div>
											<input name="HALT_PLACE_ID" id="HALT_PLACE_ID" type="hidden"
												value="${placeId}" />
										</div>

										<div class="row1" style="display: none;">
											<label class="L-size control-label">Halt By <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													required="required" value="${haltBy}" name="HALT_PAIDBY">

											</div>
										</div>
										<input type="hidden" id="TallyCompanyMasterInDriverHalt" value="${configuration.TallyCompanyMasterInDriverHalt}">
										<c:if test="${configuration.TallyCompanyMasterInDriverHalt}">
											<div class="row1">
												<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" required="required"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
											
											</c:if>


									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..." id="haltSave"
												name="commit" type="submit" value="Save Halt" onclick="saveDriverHalt();"> <a
												class="btn btn-link"
												href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addDriverHalt.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	<script>
		$(function() {
			$('#reservation').daterangepicker();
			$(".select2").select2({
				placeholder : "Please Select Driver Name"
			});
		});
		$(document).ready(function() {
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
		});	
	</script>
</div>
