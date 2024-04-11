<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
								<a href="<c:url value="/showTripSheet?tripSheetID=${tripsheetId}"/>">Show Tripsheet</a>
							</div>
							<div class="pull-right"></div>
						</div>

						<sec:authorize access="!hasAuthority('ADD_INCOME_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_INCOME_TRIPSHEET')">
							<div class="row">
								<div class="pull-left">
									<h4>
										Trip Number : <a
											href="showTripSheet?tripSheetID=${tripsheetId}">
											<span id="showTripNumber" > </span>
											<input type="hidden" id="tripNumber">
											</a>
									</h4>
								</div>
								<div class="pull-right">
									<h5>Created Date : <span id="createdDate"></span></h5>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<a id="vehicleRegistration"></a>
									<br>
									<span id="route"></span>
								</h4>
							</div>

							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <span id="tripOpenDate"></span></a></li>
									<li>Depot : <a data-toggle="tip"
										data-original-title="Group Service"><span id="vehicleGroup"></span></a></li>

									<li>Driver: <a data-toggle="tip"
										data-original-title="Driver"> <span id="driver"></span></a></li>
									<li>Conductor : <a data-toggle="tip"
										data-original-title="Driver 2"><span id="conductor"></span></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><span id="cleaner"></span></a></li>
									<li>Opening KM: <a data-toggle="tip"
										data-original-title="Opening KM"><span id="tripOpenKm"></span></a></li>
									<li>Closing KM: <a data-toggle="tip"
										data-original-title="closing KM"><span id="tripCloseDate"></span></a></li>
									<li>Usage KM: <a data-toggle="tip"
										data-original-title="usage KM"><span id="tripUsage"></span></a></li>

								</ul>
							</div>
							<br>
							<fieldset>
								<div class="row">
									<div class="">
										<table class="table">
										<thead>
												<tr class="breadcrumb">
													<th class="fit ar" >No</th>
													<th class="fit ar">Name</th>
													<th class="fit ar">Penalty date</th>
													<th class="fit ar">Amount</th>
													<th class="fit ar">Paid By</th>
													<th class="fit ar">Actions</th>
												</tr>
											</thead>
											
											<tbody id="penaltyTable" >
											</tbody>
										</table>
								</div>
							</fieldset>
								<input type="hidden" id="tripsheetId" value="${tripsheetId}">
								
								<div class="form-horizontal">
									<fieldset>
										<legend>TripSheet Driver Penalty Details</legend>
										<div class="row1">
											<label class="L-size control-label">Conductor/Driver Name <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text select2" id="driverId">
												</select>
											</div>
										</div>
										<div class="row1">
											<div class="L-size control-label">
												<label> Penalty Amount : <abbr title="required">*</abbr>
												</label>
											</div>
											<div class="I-size">
												<input type="text" onkeypress="return isNumberKeyWithDecimal(event,this.id);" maxlength="10" class="form-text" id="advanceAmount" required="required" placeholder="eg: 1000"> 
												<label class="error" id="errorNumber" style="display: none"></label>
											</div>
										</div>
										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Penalty
												Date: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="ADVANCE_DATE" readOnly="readOnly"
														id="penaltyDate" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>

										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="issue_description">Penalty Remarks :</label>
											<div class="I-size">
												<textarea class="text optional form-text" id="remark" name="ADVANCE_REMARK" rows="3"> </textarea>
											</div>
										</div>

									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..."
												onclick="saveDriverPenalty();" name="commit"
												name="commit" type="submit" value="Save Penalty"> <a
												class="btn btn-link"
												href="<c:url value="/showTripSheet?tripSheetID=${tripsheetId}"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addDriverPenalty.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	
</div>
