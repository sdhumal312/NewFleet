<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
	
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
									TripSheet</a> / <small>Add Advance TripSheet</small>
							</div>
							<div class="pull-right"></div>
						</div>
						<sec:authorize access="!hasAuthority('ADD_ADVANCE_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_ADVANCE_TRIPSHEET')">
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
												value="${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName} " /></a></li>
									<li>Driver 2 : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} -${TripSheet.tripSecDriverFatherName}" /></a></li>
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
							<br>
							<table class="table table-bordered table-striped" id="tripAdvanceTable" style="display: none;">
								<thead>
									<tr class="breadcrumb">
										<th class="fit">No</th>
										<th class="fit ar">Advance Place</th>										
										<th class="fit ar">Advance Paid By</th>
										<c:if test="${configuration.showAdvanceDriver}">
											<th class="fit ar">Advance Paid To</th>		
										</c:if>
										<th class="fit ar">Reference</th>
										<th class="fit ar">Amount</th>
										<th class="fit ar">Actions</th>
									</tr>
								</thead>
								<tbody id="tripAdvanceBody">

								</tbody>
							</table>
							<div class="col-md-offset-6">

								<table class="table" id="advanceTotalTab" style="display: none;">
									<tbody>
										<tr data-object-id="" class="ng-scope">
											<td class="key"><h4>Total Advance:</h4></td>
											<td class="value"><h4>
													<i class="fa fa-inr"></i> <span id="advanceTotal"></span>
												</h4></td>
										</tr>
									</tbody>
								</table>
							</div>
								<input type="hidden" name="TripSheetID" id="tripSheetId" value="${TripSheet.tripSheetID}">
								<input type="hidden" id="companyId" value="${companyId}">
								<input type="hidden" id="userId" value="${userId}">
								<input type="hidden" id= "advancePaidbyId" value="${paidById}" />
								<input type="hidden" id= "advancePlaceId" value="${placeId}" />	
								<div class="form-horizontal">
									<fieldset>
										<legend> Advance Payment Details </legend>							
							
										
										<c:if test="${configuration.showAdvanceDriver}">
										<div class="row1" id="paidByDriver">
											<label class="L-size control-label"> Driver:<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<select class="string required form-text" id="advanceDriverId"
													name="advanceDriverId" required="required">
													<option value="0">Please select</option>
													<option value="${TripSheet.tripFristDriverID}">${TripSheet.tripFristDriverName}</option>
													<option value="${TripSheet.tripSecDriverID}">${TripSheet.tripSecDriverName}</option>
													
												</select>
												 <label id="errorDriver1" class="error"></label>
											</div>
										</div>
										</c:if>
										
										
										<div class="row1">
											<label class="L-size control-label">Advance Amount :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="string required form-text"
													name="AdvanceAmount" maxlength="10" type="text"
													required="required" id="AdvanceAmount"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;"> <label class="error"
													id="errorAdvanceAmount" style="display: none"> </label>

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Advance Reference
												:</label>
											<div class="I-size">
												<input class="string required form-text" id="advanceRefence"
													name="advanceRefence" maxlength="50" type="text"
													onkeypress="return IsAdvanceRefence(event);"
													ondrop="return false;"> <label class="error"
													id="errorAdvanceRefence" style="display: none"> </label>
											</div>
										</div>
										<c:if test="${configuration.showPaymentTypeInIncomeExpense}">
											<div class="row1" id="paymentDiv">
											<label class="L-size control-label">Payment Type
												:</label>
										<div class="col-md-2">
											<select class="form-text" name="ADVANCE_PAID_TYPE_ID"
												id="renPT_option" required="required">
													<option value="1">Cash</option>
													<option value="11">UPI</option>
													<option value="3">NEFT</option>
													<option value="4">RTGS</option>
													<option value="5">IMPS</option>
													<option value="6">DD</option>
													<option value="7">CHEQUE</option>
											</select>
										</div>
									</div>
									</c:if>
									
										<c:if test="${configuration.showPaymentTypeInIncomeExpense}">
											<div class="row1">
											<label class="L-size control-label">Paid Date
												:</label>
											<div class="I-size">
												 <div class="input-group input-append date" id="maxTodayDate3">
													<input type="text" class="form-text	form-control invoiceDate" name="paidDateMaxTodate" readonly="readonly"
														id="paidDateMaxTodate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
												</div> </label>
											</div>
										</div>
										</c:if>
									</fieldset>
									<fieldset>
										<legend>Remarks </legend>

										<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<textarea class="form-text" id="advanceRemarks"
													name="advanceRemarks" rows="3" maxlength="250"
													onkeypress="return IsAdvanceRemarks(event);"
													ondrop="return false;">
														
													</textarea>
												<label class="error" id="errorAdvanceRemarks"
													style="display: none"> </label>
											</div>
										</div>
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions col-md-offset-3">
											<input class="btn btn-success" data-disable-with="Saving..."
												name="commit" type="submit" value="Add Advance" onclick="saveTripSheetAdvance();"> <a
												class="btn btn-link"
												href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
								<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
		<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetAdvanceAdd.js" />"></script>	
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
</div>