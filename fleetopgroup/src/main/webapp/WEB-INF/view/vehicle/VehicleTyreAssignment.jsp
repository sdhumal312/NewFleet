<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<style>
.row {
	width: 100%;
	margin: 10px auto;
	padding: 1%;
}

.label_font {
	font-weight: bold;
	font-size: larger;
}

.noBackGround {
	background: none;
}

.col {
	margin-top: 20px;
}

.custom-select {
	height: 42px;
	font-size: 15px;
}

.select2-container {
	width: 100%;
	padding: 0;
}

.select2-container-multi .select2-choices {
	min-height: 38px;
}

.select2-container .select2-choice {
	height: 36px;
}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> 
					<spring:message code="label.master.home" /> </a>/ Tyre Assignment
				</div>
				<div class="col-md-3">
						<div class="input-group">
							 <!-- <div class="input-group-prepend">
							    <span class="input-group-text btn-success" style="color:white" id="basic-addon1">Vehicle Layout</span>
							  </div> -->
							  <input type="hidden" class="form-control" id="layoutVid" name="Search" type="number" min="1" required="required" placeholder="Search Vehicle For Layout" aria-describedby="basic-addon1">
							<button type="submit" onclick="return showVehicleTyreLayout();" class="btn btn-success btn-sm"><em class="fa fa-search"></em></button>
						</div>
					</div>
				<h4 style="margin-top:0px"><a id="recentAssignVehicleId" class="hide" target="_blank">Recent <span id="recentNoOfAssignTyre"></span> Tyre Assign To Vehicle: <span id="recentAssignVehicleNumber"><span></a></h4>
				<div class="pull-right">
					<a href="<c:url value="/vehicleModel.in"/>"></a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VEHICLE_TYRE_ASSIGNMENT')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VEHICLE_TYRE_ASSIGNMENT')">
			<input type="hidden" id="companyId" value="${companyId}">
			<input type="hidden" id="userId" value="${userId}">
			<input type="hidden" id="LP_ID" class="LP_ID" name="LP_ID">
			<!-- <input type="hidden" id="tyreModelId" class="tyreModelId" name="tyreModelId"> -->
			<input type="hidden" id="mountVehicleId" value="${vid}">
			<input type="hidden" id="showPosition" value="${position}">
			<input type="hidden" id="positionStr" value="${positionStr}">
			<input type="hidden" id="vehicleReg" value="${vehicleRegistration}">
			<input type="hidden" id="showVehicleModelId" value="${showVehicleModelId}">
			<input type="hidden" id="otherVehicleLP_ID">
			<input type="hidden" id="isMultipleTyreAdded">
			<input type="hidden" id="issueId" value="${issueId}">
			<input type="hidden" id="encIssueId" value="${encIssueId}">
			<input type="hidden" id="transactionId" value="${transactionId}">
			<input type="hidden" id="transactionSubId" value="${transactionSubId}">
			<input type="hidden" id="transactionTypeId" value="${transactionTypeId}">
			<input type="hidden" id="tyreModel"  value="${tyreModel}">
			<input type="hidden" id="lpid"  value="${lpid}">
			<input type="hidden" id="accessToken" value="${accessToken}">
			<input type="hidden" id="tyreSizeId" >
			<input type="hidden" id="assignDiffTyreModelConfig" value="${configuration.assignDiffTyreModel}">
			<input type="hidden" id="validateTyreSizeConfig" value="${configuration.validateTyreSize}">
			<div class="tab-content">
				<div class="box">
					<div class="box-body">
						<label class="has-float-label "> 
							<span style="color: #2e74e6; font-size: 24px;">Vehicle Tyre Assignment</span>
						</label>
						<div class="row">
							<div class="col col-sm-1 col-md-3">
								<div class="info-box">
									<span class="info-box-icon bg-green"><em class="fa fa-clock-o"></em></span>
									<div class="info-box-content">
										<span class="info-box-text">Assign Tyre <h3> <span id="assignTyreCount"></span> </h3> </span> 
										<span class="info-box-number"></span>
									</div>
								</div>
							</div>

							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> 
								<select id="operationId" class="browser-default custom-select">
										<option value="1">Assignment</option>
										<option value="2">Rotation</option>
								</select> <span style="color: #2e74e6; font-size: 18px;">Operation <abbr title="required">*</abbr>
								</span>
								</label>
							</div>
							<div class="col col-sm-1 col-md-3">
								<button type="submit" id="availableTyre" onclick="showAvailableTyre();" class="btn btn-success pull-right">Available Tyre</button>
							</div>
							<c:choose>
								<c:when test="${!empty transactionId}">
									<div class="col col-sm-1 col-md-3">
										<div class="info-box">
											<span class="info-box-icon bg-green"><em
												class="fa fa-clock-o"></em></span>
											<div class="info-box-content">
											<c:choose>
												<c:when test="${transactionTypeId == 1}">
												<span class="info-box-text">Issue Number
													<h3>
														<c:out value="I-${transactionNumber}"></c:out>
													</h3>
												</span> <span class="info-box-number"></span>
												</c:when>
												<c:when test="${transactionTypeId == 2}">
													<span class="info-box-text">WO Number
													<h3>
														<c:out value="WO-${transactionNumber}"></c:out>
													</h3>
												</span> <span class="info-box-number"></span>
												</c:when>
												<c:when test="${transactionTypeId == 3}">
													<span class="info-box-text">DSE Number
													<h3>
														<c:out value="${transactionNumber}"></c:out>
													</h3>
												</span> <span class="info-box-number"></span>
												</c:when>
												</c:choose>
											</div>
										</div>
									</div>
								</c:when>
							</c:choose>
						</div>
					</div>
				</div>
				<div id="assignmentDiv">
					<div class="box">
						<div class="box-body">
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label">
									<div class="input-group input-append date" id="tyreAsignDate1">
										<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" onchange="getDateWiseOdometer();"
											name="invoiceDate" readonly="readonly" id="assignDate" required="required" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
											<span class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
									</div> <span style="color: #2e74e6; font-size: 18px;">Assign Date <abbr title="required">*</abbr>
								</span>
								</label>
								<span class="text-danger" id="errAssignDate"></span>
							</div>
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> 
									<c:choose>
										<c:when test="${vid > 0}">
											<input type="hidden" id="vid" class="browser-default abc" readonly="readonly">
										</c:when>
										<c:otherwise>
											<input type="hidden" id="vid" class="browser-default abc">
										</c:otherwise>
									</c:choose> <span style="color: #2e74e6; font-size: 18px;">Vehicle <abbr title="required">*</abbr>
								</span>
								</label>
								<span  class="text-danger" id="errAssignVehicle"></span>
							</div>
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> 
									<input type="text" class="form-control browser-default custom-select" id="vehicleOdometer"
										onkeypress="return isNumberKeyWithDecimal(event,this.id);">
									<span style="color: #2e74e6; font-size: 18px;">Vehicle odometer</span>
								</label>
								<span  class="text-danger" id="errAssignOdo"></span>
							</div>
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> 
									<input type="text" class="form-control browser-default custom-select" id="remark">
									<span style="color: #2e74e6; font-size: 18px;">Remark </span>
								</label>
								<span  class="text-danger" id="errAssignRemark"></span>
							</div>
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> 
									<select id="assignFromId" class="browser-default custom-select">
										<option value="0">Please Select Assign From</option>
										<option value="1">New</option>
										<option value="3">Vehicle Spare</option>
										<option value="4">Other Vehicle Spare</option>
										<option value="5">Workshop</option>
									</select> 
									<span style="color: #2e74e6; font-size: 18px;">Assign From <abbr title="required">*</abbr>
								</span>
								</label>
								<span  class="text-danger" id="errAssignFrom"></span>
							</div>
						</div>
					</div>
					<div class="addMoreAssignTyreDiv">
					</div>
					<div class="box" id="tyreDiv">
						<div class="box-body">
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label"> 
									<select id="tyrePositionId" name="tyrePositionName" class="browser-default custom-select tyrePositionId"  onchange="changePosition(this.id);"> </select> 
										<span style="color: #2e74e6; font-size: 18px;">Tyre Position <abbr title="required">*</abbr>
									</span>
								</label>
								<span  class="text-danger errTyrePositionId" id="errTyrePositionId" ></span>
							</div>
							<c:if test="${configuration.assignDiffTyreModel}">
								<div class="col col-sm-1 col-md-2" id="tyreModelDiv">
									<label class="has-float-label"> 
										<input type="hidden" id="tyreModelId" name="tyreModelId" class="form-control browser-default tyreModelId" onchange="tyreModelChange();">  
											<span style="color: #2e74e6; font-size: 18px;">Tyre Model <abbr title="required">*</abbr>
										</span>
									</label>
									<span  class="text-danger errTyreModelId" id="errTyreModelId" ></span>
								</div>
							</c:if>
							<c:if test="${!configuration.assignDiffTyreModel}">
								<div class="col col-sm-1 col-md-2">
									<label class="has-float-label"> 
										<input type="hidden" id="tyreModelId" name="tyreModelId" class="form-control browser-default tyreModelId" onchange="tyreModelChange();" readonly="readonly">  
											<span style="color: #2e74e6; font-size: 18px;">Tyre Model <abbr title="required">*</abbr>
										</span>
									</label>
									<span  class="text-danger errTyreModelId" id="errTyreModelId" ></span>
								</div>
							</c:if>
							<div class="col col-sm-1 col-md-2" style="display: none" id="assignFromVidDiv">
								<label class="has-float-label"> 
									<input type="hidden" id="assignFromVid" class="browser-default"> 
									<span style="color: #2e74e6; font-size: 18px;">Other Vehicle <abbr title="required">*</abbr>
									</span>
								</label>
									<span  class="text-danger errAssignFromVehicle" id="errAssignFromVehicle" ></span>
							</div>
							<div id="locationDiv" class="col col-sm-1 col-md-2">
								<label class="has-float-label"> <input type="hidden" id="locationId" name ="locationName" onchange="resetTyre();" class="browser-default"> 
									<span style="color: #2e74e6; font-size: 18px;">Location 
									<abbr title="required">*</abbr></span>
								</label>
								
							</div>
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label">
									<input type="hidden" class="newTyreId" id="newTyreId" name="tyreName" class="browser-default"> 
									<span style="color: #2e74e6; font-size: 18px;">Tyre 
									<abbr title="required">*</abbr></span>
								</label>
								<span  class="text-danger errNewTyreId" id="errNewTyreId" ></span>
							</div>
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label"> 
								<input type="text" class="form-control browser-default custom-select" id="tyreGuage" name="tyreGuage" required
									onkeypress="return isNumberKeyWithDecimal(event,this.id);">
									<span style="color: #2e74e6; font-size: 18px;">Tyre Gauge <abbr title="required">*</abbr>
								</span>
								</label>
								
							</div>
							<div id="oldTyreDiv" class="col col-sm-1 col-md-2 oldTyreDiv" style="display: none">
								<label class="has-float-label"> 
								<input type="hidden" id="oldTyreId" name="oldTyreName" class="oldTyreId"> 
								<input type="hidden" id="oldTyreLP_ID" name="oldTyreLP_ID" class="oldTyreId"> 
								<input type="text" class="form-control browser-default custom-select oldTyre" id="oldTyre" name="oldTyre"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);" readonly="readonly"> 
									<span style="color: #2e74e6; font-size: 18px;">Old Tyre</span>
								</label>
							</div>
							<div id="oldTyreMoveDiv" class="col col-sm-1 col-md-2 oldTyreMoveDiv" >
								<label class="has-float-label"> 
									<select id="oldTyreMoveId" name="oldTyreMoveName"class="browser-default custom-select">
										<option value="0">Please Select Old Tyre Move To</option>
										<option value="1">Remould</option>
										<option value="2">Repair</option>
										<option value="3">Blast</option>
										<option value="4">Scrap</option>
										<option value="5">WorkShop</option>
									</select> 
									<span style="color: #2e74e6; font-size: 18px;">Old Tyre Move <abbr title="required">*</abbr> </span>
								</label>
								<span  class="text-danger errOldyreMoveId" id="errOldyreMoveId" ></span>
							</div>
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label"> 
									<select id="alignmentId" name="alignmentName" class="browser-default custom-select">
										<option value="0">Please Select Alignment</option>
										<option value="1">Done Automatic</option>
										<option value="2">Done Manual</option>
										<option value="2">Not Done</option>
									</select> 
									<span style="color: #2e74e6; font-size: 18px;">Alignment <abbr title="required">*</abbr> </span>
								</label>
								<span  class="text-danger errAlignmentId" id="errAlignmentId"></span>
							</div>
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label"> 
									<select id="kinpinId" name="kinpinName" class="browser-default custom-select">
										<option value="0">Please Select Kinpin</option>
										<option value="1">Working</option>
										<option value="2">Not Working</option>
									</select> 
									<span style="color: #2e74e6; font-size: 18px;">Kinpin <abbr title="required">*</abbr></span>
								</label>
								<span  class="text-danger errKinpinId" id="errKinpinId" ></span>
							</div>
							<div class="col col-sm-1 col-md-1" id="moreAssign">
								<button type="button" class="btn btn-info addMoreAssignTyre">
									<span class="fa fa-plus"></span>
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="rotationDiv" style="display: none">
					<div class="box">
						<div class="box-body">
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label">
									<div class="input-group input-append date" id="tyreRotateDate1">
										<input type="text"
											class="form-control  browser-default custom-select noBackGround	invoiceDate" onchange="getDateWiseOdometer();"
											name="invoiceDate" readonly="readonly" id="rotationDate"
											required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
											data-mask="" /> <span
											class="  input-group-addon add-on btn btn-sm"><em
											class="fa fa-calendar"></em></span>
									</div> <span style="color: #2e74e6; font-size: 18px;">Rotation
										Date <abbr title="required">*</abbr>
								</span>
								</label>
							</div>

							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> <c:choose>
										<c:when test="${vid > 0}">
											<input type="hidden" id="rotateVid"
												class="browser-default abc" readonly="readonly">
										</c:when>
										<c:otherwise>
											<input type="hidden" id="rotateVid"
												class="browser-default abc">
										</c:otherwise>
									</c:choose> <span style="color: #2e74e6; font-size: 18px;">Vehicle
										<abbr title="required">*</abbr>
								</span>
								</label>
							</div>

							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> <input type="text"
									class="form-control browser-default custom-select"
									id="rotateOdometer"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);">
									<span style="color: #2e74e6; font-size: 18px;">Vehicle
										odometer</span>
								</label>
							</div>
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> <input type="text"
									class="form-control browser-default custom-select"
									id="rotationRemark"> <span
									style="color: #2e74e6; font-size: 18px;">Remark <abbr
										title="required">*</abbr></span>
								</label>
							</div>
						</div>
					</div>
					<div class="addMoreRotationTyreDiv">
					</div>	
					<div class="box rotationDiv " style="display: none">
						<div class="box-body">
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> <select
									id="rotateFromPositionId" class="browser-default custom-select rotateFromPositionId"
									name="rotateFromPositionId" onchange="changeFromPosition(this.id);">

								</select> <span style="color: #2e74e6; font-size: 18px;">Rotate
										From <abbr title="required">*</abbr>
								</span>
								</label>
							</div>
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label"> <input type="text"
									class="form-control browser-default rotateFromTyreId" id="rotateFromTyreId" name="rotateFromTyreId"
									readonly="readonly"> <span
									style="color: #2e74e6; font-size: 18px;">Rotate From
										Tyre <abbr title="required">*</abbr>
								</span>
								</label>
							</div>
							<div class="col col-sm-1 col-md-3">
								<label class="has-float-label"> <select
									id="rotateToPositionId" class="browser-default custom-select rotateToPositionId"
									name="rotateToPositionId" onchange="changeToPosition(this.id);">

								</select> <span style="color: #2e74e6; font-size: 18px;">Rotate To
										<abbr title="required">*</abbr>
								</span>
								</label>
							</div>
							<div class="col col-sm-1 col-md-2">
								<label class="has-float-label"> <input type="text"
									class="form-control browser-default rotateToTyreId" id="rotateToTyreId" name="rotateToTyreId"
									readonly="readonly"> <span
									style="color: #2e74e6; font-size: 18px;">Rotate To Tyre
										<abbr title="required">*</abbr>
								</span>
								</label>
							</div>
							<div class="col col-sm-1 col-md-1" id="moreRotation">
							<button type="button" class="btn btn-info addMoreRotationTyre ">
								<span class="fa fa-plus"></span>
							</button>
						</div>
						
						</div>
					</div>
				</div>
			</div>
			<div class="row" id="assignedDiv">
				<button type="submit" id="submit" class="btn btn-success">Assign</button> &nbsp;&nbsp; 
				<a class=" btn btn-info" > <span id="Cancel">Cancel</span> </a>
			</div>
			<div class="row" id="rotateDiv" style="display: none">
				<button type="submit" id="rotate" class="btn btn-success">Rotate</button> &nbsp;&nbsp; 
				<a class=" btn btn-info" > <span id="Cancel">Cancel</span> </a>
			</div>

		</sec:authorize>
		<div class="modal" id="availableTyreModal"  tabindex="-1">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">
							Available Tyre 
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							onclick="closeModal();" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<table class="table">
							<thead>
								<tr>
									<th class="fit ar">Sr No</th>
									<th class="fit ar">Tyre Number</th>
									<th class="fit ar">Tyre Model</th>
									<th class="fit ar">Location</th>
								</tr>
							</thead>
							<tbody id="availableTyreTable">

							</tbody>

						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							onclick="closeModal();" data-dismiss="modal">Close</button>

					</div>
				</div>
			</div>
		</div>
		<div class="modal" id="assignTyreModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">
							Assign Tyre : 
						</h5>
						<button type="button" class="close" data-dismiss="modal"
							onclick="closeModal();" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<table class="table">
							<thead>
								<tr>
									<th class="fit ar">Sr No</th>
									<th class="fit ar">Position</th>
									<th class="fit ar">Tyre Number</th>
								</tr>
							</thead>
							<tbody id="assignTyreDetailsTable">

							</tbody>

						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							onclick="closeModal();" data-dismiss="modal">Close</button>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleTyreAssignment.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleTyreRotation.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleTyreAssignmentCommon.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>