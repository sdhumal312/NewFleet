<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newMasterParts/1.in"/>">Master Parts</a> / <span
						id="NewVehi">Add New Part</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/newMasterParts/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_PARTS')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_PARTS')">
				<div class="col-md-offset-1 col-md-9">
					<form id="formMasterPart"
						action="<c:url value="/saveMasterParts.in"/>" method="post"
						enctype="multipart/form-data" name="formMasterPart" role="form"
						class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Part Info</legend>
								<div class="box">
									<div class="box-body">
									<input type="hidden" id="refreshment" value="0" name="refreshment">
									<c:if test="${configuration.showRefreshmentOption}">
										<div class="row1" id="extendedTrip">
											<label class="L-size control-label">IS Refreshment Item :</label>
											<div class="I-size">
													<div class="">
														<div class="btn-group" id="status" data-toggle="buttons" onchange="onRefreshmentSelect();">
															<label class="btn btn-default btn-on btn-lg">
																<input type="radio" value="1" name="refreshmentGroup"
																id="isRefreshmentYes">Yes
															</label> <label class="btn btn-default btn-off btn-lg active"> <input
																type="radio" value="0" name="refreshmentGroup" id="isRefreshmentNo" checked="checked"">No
															</label>
														</div>
													</div>
												</div>
										</div>
									</c:if>
									
										<div class="row" id="grppartNumber" class="form-group">
											<label class="L-size control-label" for="partNumber">Part
												Number :<abbr title="required" id="partAbbr">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="partNumber"
													name="partnumber" placeholder="Part Number 0001"
													required="required" /><span id="partNumberIcon" class=""></span>
												<div id="partNumberErrorMsg" class="text-danger"></div>
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>
										<c:if test="${configuration.showPartManufacturerTypeCol}">
											<div class="row">
												<label class="L-size control-label" id="primarymeterunit"><abbr
													title="required">*</abbr>Part Manufacturer Type : </label>
												<div class="I-size3">
													<span class="radio"> <label
														for="vehicle_meter_unit_km"> <input
															 class="radio_buttons required"
															id="manufacturerOriginal" name="0"
															type="radio" value="1" checked="checked"> <span id="Kilometers">Original Parts</span>
													</label>
													</span> <span class="radio"> <label
														for="vehicle_meter_unit_mi"> <input
															class="radio_buttons required" id="manufacturerLocal"
															name="partManufacturerType" type="radio" value="2">
															<span id="Miles">Local Parts</span>
													</label>
													</span>
													<p class="help-block" id="meterunithelp"></p>
	
												</div>
											</div>
										</c:if>
										<div class="row" id="grppartName" class="form-group">
											<label class="L-size control-label" for="partName">Part
												Name :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="partName"
													name="partname" placeholder="Enter Part Name" /><span
													id="partNameIcon" class=""></span>
												<div id="partNameErrorMsg" class="text-danger"></div>
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>
										<div class="row1" class="form-group">
											<label class="L-size control-label" for="partName">Part
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text select2" id="parttype" name="partTypeId" style="width: 100%;">
													<c:forEach items="${PartType}" var="PartType">
														<option value="${PartType.partTypeId}"><c:out
																value="${PartType.partTypeName}" /></option>
													</c:forEach>
												</select>
												<input type='hidden' id='partyTypeText' name="parttype" value=''/>
												<span id="partTypeIcon" class=""></span>
												<div id="partTypeErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grppartCategory" class="form-group">
											<label class="L-size control-label" for="partCategory">Category
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="categoryId"
													style="width: 100%;" id="partCategory">
													<option value=""><!-- Please select --></option>
													<c:forEach items="${PartCategories}" var="PartCategories">
														<option value="${PartCategories.pcid}"><c:out
																value="${PartCategories.pcName}" /></option>
													</c:forEach>
												</select> 
												<input type='hidden' id='partCategoryText' name="category" value=''/>
												<span id="partCategoryIcon" class=""></span>
												<div id="partCategoryErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<br>
										<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacturer">Manufacturer
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="makerId"
													style="width: 100%;" id="manufacturer">
													<option value=""><!-- Please select --></option>
													<c:forEach items="${PartManufacturer}"
														var="PartManufacturer">

														<option value="${PartManufacturer.pmid}"><c:out
																value="${PartManufacturer.pmName}" /></option>
													</c:forEach>
												</select>
												<input type='hidden' id='manufacturerText' name="make" value=''/>
												<span id="manufacturerIcon" class=""></span>
												<div id="manufacturerErrorMsg" class="text-danger"></div>
												<label id="errormake" class="error"></label>
											</div>
										</div>
										<br>
										<div class="row">
											<label class="L-size control-label">Description</label>
											<div class="I-size">
												<input type="text" class="form-text" id="description"
													maxlength="150" name="description"
													placeholder="Enter Description" /> <label
													id="errorDescription" class="error"></label>

											</div>
										</div>
										<!-- <div class="row">
											<label class="L-size control-label">Low Stock Level</label>
											<div class="I-size">
												<input type="number" class="form-text" id="lowstocklevel"
													name="lowstocklevel" placeholder="Enter Low Stock Level" />
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label">Reorder Quantity</label>
											<div class="I-size">
												<input type="number" class="form-text" id="reorderquantity"
													name="reorderquantity" placeholder="Enter Reorder Quantity" />
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>
 -->										<div class="row">
											<label class="L-size control-label">Unit Type :</label>
											<div class="I-size">
												<select class="form-text select2" name="unitTypeId"
													id="select3" style="width: 100%;">
													<c:forEach items="${PartMeasurementUnit}"
														var="PartMeasurementUnit">
														<option value="${PartMeasurementUnit.pmuid}">
															<c:out value="${PartMeasurementUnit.pmuSymbol}" />
														</option>
													</c:forEach>
												</select> 
												<input type='hidden' id='unittypeText' name="unittype" value=''/>
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>
										<!-- Set Default photo id in part create time -->
										<input type="hidden" class="form-text" name="part_photoid"
											value="1" required="required" />
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Location</legend>
								<div class="box">
									<div class="box-body">
										<ul class="tabs">
											<c:forEach items="${PartLocations}" var="PartLocations">
												<li class="tab-link"
													data-tab="${PartLocations.partlocation_name}">
													${PartLocations.partlocation_name}</li>
											</c:forEach>
										</ul>
										<br>
										<c:forEach items="${PartLocations}" var="PartLocations">
											<div id="${PartLocations.partlocation_name}"
												class="tab-content2">
												<div class="row1">
													<input type="hidden" class="form-text col-md-3"
														name="location_ID" value="${PartLocations.partlocation_id}">
													<div class="row1">
														<label class="L-size control-label" for="issue_vehicle_id">Aisle
															:</label>
														<div class="col-md-3">
															<input type="text" class="form-text" name="Aisle"
																maxlength="50"> <label class="error"
																id="errorAisle" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label" for="issue_vehicle_id">Row
															:</label>
														<div class="col-md-3">
															<input type="text" class="form-text" name="row"
																maxlength="50"> <label class="error"
																id="errorRow" style="display: none"> </label>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label" for="issue_vehicle_id">Bin
															:</label>
														<div class="col-md-3">
															<input type="text" class="form-text" name="bin"
																maxlength="50"> <label class="error"
																id="errorBin" style="display: none"> </label>
														</div>
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row1">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success">Create
											Master Part</button>
										<a class="btn btn-link"
											href="<c:url value="/newMasterParts/1.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IP/MasterParts.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#partyTypeText").val($("#parttype").find(":selected").text());
			$("#parttype").change(function () {
			    $("#partyTypeText").val($("#parttype").find(":selected").text());
			});
			$("#partCategory").change(function () {
			    $("#partCategoryText").val($("#partCategory").find(":selected").text());
			});
			$("#manufacturer").change(function () {
			    $("#manufacturerText").val($("#manufacturer").find(":selected").text());
			});
			
			$("#unittypeText").val($("#select3").find(":selected").text().trim());
			$("#select3").change(function () {
			    $("#unittypeText").val($("#select3").find(":selected").text().trim());
			});
		});
	</script>
</div>