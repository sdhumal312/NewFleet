<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">	
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
									<div class="table-responsive">
										<table id="dueAmountTable" class="table table-hover table-bordered">

										</table>
									</div>
									<!-- <div class="">
										<table class="table">
										<thead>
												<tr class="breadcrumb">
													<th class="fit ar">No</th>
													<th class="fit ar">Name</th>
													<th class="fit ar">Due Amount</th>
													<th class="fit ar">Approx Date</th>
													<th class="fit ar">Due Date</th>
													<th class="fit ar">Actions</th>
												</tr>
											</thead>
											
											<tbody id="dueAmountTable" >
											</tbody>
										</table>
									</div> -->	
								</div>
							</fieldset>
								<input type="hidden" id="tripsheetId" value="${tripsheetId}">
								<input type="hidden" id="vid">
								<input type="hidden" id="B_Income">
								<input type="hidden" id="E_Income">
								<input type="hidden" id="showbilltypeDropdown" value="${configuration.showbilltypeDropdown}">
								<input type="hidden" id="showDueIncomeType" value="${configuration.showDueIncomeType}"> 
								
								<div class="form-horizontal">
									<fieldset>
										<legend>TripSheet Due Amount Details</legend>
										
										<c:if test="${configuration.showbilltypeDropdown}">
											<div class="row1">
												<label class="L-size control-label">Select Income Type <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" id="billselectionId">
														<option value="1">B Income</option>
														<option value="2">E Income</option>
													</select>
												</div>
											</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label">
												<c:choose>
													<c:when test="${configuration.changedDriverFieldName}">Driver/Agent</c:when>	
													 <c:otherwise>Driver</c:otherwise>
												</c:choose>
												Name <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="driverId" 
												name="clothTypes" style="width: 100%;" 
												placeholder="Please Enter 3 or more Driver Name, No" />
											</div>
										</div>
										
										<div class="row1">
											<div class="L-size control-label">
												<label> Due Amount : <abbr title="required">*</abbr>
												</label>
											</div>
											<div class="I-size">
												<input type="number" onkeypress="return isNumberKeyWithDecimal(event,this.id);" class="form-text" id="dueAmount" required="required" placeholder="eg: 1000"> 
												<label class="error" id="errorNumber" style="display: none"></label>
											</div>
										</div>
										
										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Approximate
												Date: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="LeaveDate">
													<input type="text" class="form-text" name="ADVANCE_DATE" readOnly="readOnly"
														id="approxDate" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Due
												Date: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="damageReceiveDate">
													<input type="text" class="form-text" name="ADVANCE_DATE" readOnly="readOnly"
														id="dueDate" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" for="issue_description"> Remarks :</label>
											<div class="I-size">
												<textarea class="text optional form-text" id="remark" name="ADVANCE_REMARK" rows="3"> </textarea>
											</div>
										</div>

									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..."
												onclick="saveDueAmount();" name="commit"
												name="commit" type="submit" value="Save Due Amount"> <a
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addDueAmount.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>	
	
</div>
