<%@ include file="../../taglib.jsp"%>
<style>
.red{
	color:red;
	font-size: 19px;
	display: inline-block;
	position: absolute;
}
.red1{
	color:red;
	font-size: 19px;
	display: inline-block;
}
.hide{
	display:none;
}
</style>
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
				<button class="btn btn-default" onclick="printDiv('div_print')">
							<span class="fa fa-print"> Print</span>
						</button>
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
						<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
						<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
						<input type="hidden" id="mandatoryRemarkDocIfFail"  value="${configuration.mandatoryRemarkIfFail}">
						<input type="hidden" id="multipleDocumentCompress"  value="${configuration.multiCompress}">
						<div class="form-horizontal" id="div_print">
							<fieldset>
								<legend>Vehicle Inspection Details</legend>
								<div class="box">
									<div class="box-body">
										<table class="table" border="0">
											<tbody>
												<tr>
													<td>Date : </td>
													<td>${dateRangeFormat}</td>
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
								<div id="root"></div>
								<div id="downloadButton" style="display: none;">
									<a id="download" target="_blank" href="#" class="btn btn-success">Download</a>
								</div>
							</fieldset>

							<fieldset>
								<legend>Parameters To Be Inspected </legend>
								<div class="box">
									<div class="table-responsive">
									
										<table class="table">
											<thead>
												<tr>
													<th>Parameter</th>
													<th>Photo</th>
													<th>Test Result</th>
													<c:if test="${configuration.failedParameterPenalty}">
<!-- 													<th>Penalty</th> -->
													</c:if>
													<th>Upload File</th>
													<th>Remark</th>
												</tr>
											</thead>
											<tbody>
											
											<c:forEach items="${inspectionSheetToParameter}" var="inspectionSheetToParameter">
												<tr id="row_${inspectionSheetToParameter.inspectionSheetToParameterId}">
													<td>${inspectionSheetToParameter.parameterName }
														<input type="hidden" name="inspectionSheetToParameterId" id="${inspectionSheetToParameter.inspectionSheetToParameterId}" value="${inspectionSheetToParameter.inspectionSheetToParameterId}">
														<input type="hidden" name="paramName" id="paramName_${inspectionSheetToParameter.inspectionSheetToParameterId}" value="${inspectionSheetToParameter.parameterName}">
													
													</td>
													<td>
													<c:if test="${inspectionSheetToParameter.parameterPhotoId != null}">
														<sec:authorize
																access="hasAuthority('DOWNLOND_DRIVER')">
																<a
																	href="${pageContext.request.contextPath}/download/parameterDocument/${inspectionSheetToParameter.parameterPhotoId}.in"
																	class="zoom" data-title="Part Photo" data-footer=""
																	data-type="image" data-toggle="lightbox"> <img
																	src="${pageContext.request.contextPath}/download/parameterDocument/${inspectionSheetToParameter.parameterPhotoId}.in"
																	class="img-rounded" alt=" " width="50" height="50" />
																</a>
															</sec:authorize>
														</c:if>
														<c:if test="${inspectionSheetToParameter.parameterPhotoId == 0}">
														<%-- <c:out value="Image Not Selected" /> --%>
														</c:if>
													</td>
													<td><div>
														<label>
															<span class="red1 hide" id="required_${inspectionSheetToParameter.inspectionSheetToParameterId}">*</span>Pass 
														</label>
														<input type="radio" value="true" name="testResult${inspectionSheetToParameter.inspectionSheetToParameterId}" 
														id="testResultYes_${inspectionSheetToParameter.inspectionSheetToParameterId}" onclick="Result1()">
														
														<input type="hidden" name="isMandatory" id="isMandatory_${inspectionSheetToParameter.inspectionSheetToParameterId}" value="${inspectionSheetToParameter.mandatoryText}">
														<label>
															Fail 
														</label>
														<input type="radio" value="false" name="testResult${inspectionSheetToParameter.inspectionSheetToParameterId}" 
														id="testResultNo_${inspectionSheetToParameter.inspectionSheetToParameterId}" onclick="Result1()">
														<input type="hidden">
													</div></td>
													
													<c:if test="${configuration.failedParameterPenalty}">
<!-- 													<td> -->
														<div class="penaltyDiv" id="divPenalty_${inspectionSheetToParameter.inspectionSheetToParameterId}">
															<label name="penalty" id="penalty_${inspectionSheetToParameter.inspectionSheetToParameterId}"></label>
														</div>
<!-- 													</td> -->
													</c:if>
														<td>
														<div id="fileDiv${inspectionSheetToParameter.inspectionSheetToParameterId}">
															
															<span class="red" id="requiredFile_${inspectionSheetToParameter.inspectionSheetToParameterId}">*</span>
															<c:if test="${configuration.compressImage}">
															<input type="file" accept="image/png, image/gif, image/jpeg" name="input-file-preview"  id="file_${inspectionSheetToParameter.inspectionSheetToParameterId}" />
															</c:if>
															<c:if test="${!configuration.compressImage}">
															<input type="file" accept="image/png, image/gif, image/jpeg" name="input-file-preview" multiple="multiple" id="file_${inspectionSheetToParameter.inspectionSheetToParameterId}" />
															</c:if>
															<input type="hidden" name="photo" id="photo_${inspectionSheetToParameter.inspectionSheetToParameterId}" value="${inspectionSheetToParameter.photoRequiredText}">
															<input type="hidden" name="failphoto" id="failPhoto_${inspectionSheetToParameter.inspectionSheetToParameterId}">
															<input type="hidden" name="alredyUpload" id="alredyUpload_${inspectionSheetToParameter.inspectionSheetToParameterId}">
															<input type="hidden" name="compressed" id="compressed_${inspectionSheetToParameter.inspectionSheetToParameterId}">
<%-- 															<input type="hidden" name="fileCount" id="fileCount_${inspectionSheetToParameter.inspectionSheetToParameterId}" value ="0"> --%>
<%-- 															<input type="file" class="hide" name="compressed" multiple="multiple" id="compressed_${inspectionSheetToParameter.inspectionSheetToParameterId}"> --%>
														</div>
													</td>
													<td>
														<div>
															<span class="red" id="requiredText_${inspectionSheetToParameter.inspectionSheetToParameterId}">*</span>&nbsp;&nbsp;
															<textarea name="remark" id="remark_${inspectionSheetToParameter.inspectionSheetToParameterId}" value=" -" rows="" cols=""></textarea>
															<input type="hidden" name="completionToParameterId" id="completionToParameterId_${inspectionSheetToParameter.inspectionSheetToParameterId}">
															<input type="hidden" name="rt" id="remarkText_${inspectionSheetToParameter.inspectionSheetToParameterId}" value="${inspectionSheetToParameter.textRequiredText}">
															<input type="hidden" name="failrt" id="failRemarkText_${inspectionSheetToParameter.inspectionSheetToParameterId}" >
														</div>
													</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
										<input type="hidden" name="dateRange" id="dateRange" value="${dateRange}">
										<input type="hidden" name="saveTypeId" id="saveTypeId">
										<input type="hidden" name="vid" value="${asingmentDto.vid}">
										<input type="hidden" name="inspectionSheetId" id="inspectionSheetId" value="${asingmentDto.inspectionSheetId}"> 
										<input type="hidden" name="vehicleType" id="vehicleType" value="${asingmentDto.vehicleTypeId}"> 
										<input type="hidden" name="vehicleInspctionSheetAsingmentId" value="${asingmentDto.vehicleInspctionSheetAsingmentId}">
										<input type="hidden" name="fileNames" id="fileNames">
										<input type="hidden" name="testResult" id="testResult">
										<input type="hidden" name="parameterIds" id="parameterIds">
										<input type="hidden" name="completionDetailsId" id="completionDetailsId" value="${asingmentDto.completionDetailsId }">
										<c:if test="${!configuration.failedParameterPenalty}">
										<input type="hidden" name="totalPenalty" id="totalPenalty"  value="0">
										</c:if>
										<input type="hidden" name="oldTotalPenalty" id="oldTotalPenalty"  value="${inspectionCompletionDetails.totalPenalty}">
										<input type="hidden" name="failedParameterPenalty" id="failedParameterPenalty"  value="${configuration.failedParameterPenalty}">
										<input type="hidden" name="compressImage" id="compressImage"  value="${configuration.compressImage}">
										<input type="hidden" name="penaltyAmountDouble" id="penaltyAmountDouble"  value="${penaltyAmountDouble}">
									</div>
								</div>
							<br>
							<div id="multiCompressedImageDiv">
							<input type="hidden" name="multiCompressed" value="">
							</div>
							</fieldset>

							<c:if test="${configuration.failedParameterPenalty}">
								<fieldset>
									<div class="box" id="failedParameterPenaltyBox">
										<div class="box-body">
											<table class="table">
												<tbody>
													<tr>
														<td><label><h4>Total penalty :</label>
															</h4></td>
														<td><h4>
																<i class="fa fa-inr"></i> <input type="text"
																	name="totalPenalty" id="totalPenalty" value="0"
																	onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																	maxlength="10" style="width: 31%; font-weight: bold;">
															</h4></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</c:if>

							<fieldset class="form-actions">
								<div class="row1">

									<div class="pull-right" id="saveInsp">
										<button type="submit" class="btn btn-primary"
											onclick="return validateInspectionForm(2);">Submit</button>
										<button type="submit" class="btn btn-success"
											onclick="return validateInspectionForm(1);">Save
											</button>
										<a class="btn btn-default" href="ViewVehicleInspectionList.in">Cancel</a>
									</div>
								</div>
							</fieldset>
							
							
								<legend>Vehicle Inspection Test Result </legend>
								<div class="box">
									<div class="box-body">

									<table class="table" border="1">
										<thead>
											<tr>
												<th>Pass Percentage</th>
												<th>Fail Percentage</th>
												<th>Not Tested Percentage</th>

											</tr>
										</thead>
										<tbody>
											<tr>
												<td id="passPercent">0</td>
												<td id="failPercent">0</td>
												<td id="notTestResultPercent"> 100</td>
											</tr>
										</tbody>
									</table>

								</div>
									</div>
							
						</div>
					</form>
				</sec:authorize>
			</div>
		</div>
	</section>
	
	<!-- to get list from controller -->
	<script type="text/javascript">
		$(document).ready(function() {
			setVehicleInspectedDetails('${toParameterList}');
			Result1();
			if($('#failedParameterPenalty').val() == true || $('#failedParameterPenalty').val() == "true"){
				var oldTotalPenalty= $('#oldTotalPenalty').val();
				$('#totalPenalty').val(oldTotalPenalty);
			}
		});
	</script>
	
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/compressImageUtility.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
</div>