<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper" onload="javascript:loadTripSheet();">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
								href="<c:url value="/ViewVehicleInspectionList.in"/>"> Vehicle Inspection
							</a>
				</div>
				<div class="pull-right">
					<a class="btn btn-danger" href="ViewVehicleInspectionList.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripSheet Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripSheet Already Exists
		</div>
	</c:if>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">

				<c:if test="${param.Close eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						You should be close first Driver Account in those Tripsheet
						${CloseTS} <br> ${VMandatory}<br>You can save TripSheet.
						You can't Dispatch.
					</div>
				</c:if>
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
					<form action="saveVehicleInspectionDetails.in" enctype="multipart/form-data" method="post">
						
						<div class="form-horizontal">
							<fieldset>
								<legend>Vehicle Inspection Details</legend>
								<div class="box">
									<div class="box-body">
										<table class="table" border="0">
											<tbody>
												<tr>
													<td>Date : </td>
													<td>${todaysDate}</td>
												</tr>
												<tr>
													<td>Inspected By : </td>
													<td>${inspectedBy}</td>
												</tr>
												<tr>
													<td>Vehicle Number : </td>
													<td>${asingmentDto.vehicle_registration}</td>
												</tr>
												<tr>
													<td>Vehicle Group : </td>
													<td>${asingmentDto.vehicleGroupName}</td>
												</tr>
												<tr>
													<td>Place : </td>
													<td>${place}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								
							</fieldset>

							<fieldset>
								<legend>Parameters Inspection Details</legend>
								<div class="box">
									<div class="box-body">
									
										<table class="table" id="param">
											<thead>
												<tr>
													<th>Parameter</th>
													<th>Test Result</th>
<%-- 													<c:if test="${configuration.failedParameterPenalty}"> --%>
<!-- 													<th>Penalty</th> -->
<%-- 													</c:if> --%>
													<th>Upload File</th>
													<th>Remark</th>
												</tr>
											</thead>
											<tbody>
											
											<c:forEach items="${toParameterList}" var="toParameterList">
												<tr>
													<td>
														${toParameterList.parameterName}
													</td>
													<td>
														${toParameterList.inspectionSucessStr}
													</td>
														<c:if test="${!configuration.compressImage}">
															<c:choose>
																<c:when
																		test="${toParameterList.documentUploaded}">
																	<td><a href="#"
																		onclick="showImageList(${toParameterList.completionToParameterId});">
																			<span class="fa fa-download"> Show Image</span>
																	</a></td>
																</c:when> 
																<c:otherwise>
																<td>
																No File ! </td>
															</c:otherwise>
															</c:choose>
														</c:if>
														<c:if test="${configuration.compressImage}">	
														<c:choose>
															<c:when test="${toParameterList.documentId != null && toParameterList.documentId > 0}">
																 <td>
																 	<a href="${pageContext.request.contextPath}/downloadParameterDocument/${toParameterList.documentId}.in">
																		<span class="fa fa-download"></span>
																	</a>
																</td>
															</c:when>
															<c:otherwise>
																<td>No File !</td>	
															</c:otherwise>
														</c:choose>
														</c:if>
														
													
													<td>
														${toParameterList.description}
													</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
										
									</div>
								</div>
								</fieldset>
										<c:if test="${configuration.failedParameterPenalty}">
								<fieldset>
									<div class="box">
										<div class="box-body">
											<table class="table">
												<tbody>
													<tr>
														<td><label><h4>Total penalty :</label>
															</h4></td>
														<td><h4>
																<i class="fa fa-inr"></i> 
																	<label style="font-weight: bold;" >${inspectionCompletionDetails.totalPenalty}</label>
															</h4></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</c:if>
								
								
								<fieldset>
								<legend>Vehicle Inspection Test Result </legend>
								<div class="box">
									<div class="box-body">

									<table class="table">
										<thead>
											<tr>
												<th>Pass Percentage</th>
												<th>Fail Percentage</th>
												<th>Not Tested Percentage</th>

											</tr>
										</thead>
										<tbody>
											<tr>
												<td id="passPercent"> ${passPer}</td>
												<td id="failPercent"> ${failPer}</td>
												<td id="notTestResultPercent"> ${notTestPer}</td>
											</tr>
										</tbody>
									</table>

								</div>
									</div>
								
								</fieldset>

						</div>
					</form>
				</sec:authorize>
			</div>
		</div>

		<div class="modal fade" id="Popup" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<input type="hidden" id="wotaskTopartId">
						<h4 class="modal-title">Document List</h4>
					</div>
					<div class="modal-body">
						<table id="documentTable" style="width: 100%; display: none;"
							class="table-responsive table">

						</table>

						<div class="modal-footer">
							<button class="btn btn-primary"
								style="width: 50%; margin-left: 25%; margin-top: 20%;"
								data-dismiss="modal">Close</button>
						</div>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/vehicleInspectionAdd.js" />"></script>
</div>