<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<style>
.row {
	width: 100%;
	margin: 10px auto;
	padding:1%;
}
.label_font{
	font-weight: bold;
	font-size: larger;
}


.col{
	margin-top: 20px;
}
.custom-select{
	height: 38px;
 }
.select2-container {
	width: 100%;
	padding: 0;
}
.select2-container-multi .select2-choices {
    min-height: 38px;
}

</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> / 
					<a href="newMasterParts/1.in"> Parts</a> / 
					<span id="NewVehicle">Create Part</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="newMasterParts/1.in">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_PARTS')">
			<div class="row">
				<div class="col-sm-8 col-md-12">
					<input type="hidden" id="companyId" value="${companyId}">
					<input type="hidden" id="userId" value="${userId}">
					<input type="hidden" id="validateLocalParts" value="${configuration.validateLocalParts}">
					<input type="hidden" id="validateWarranty" value="${configuration.validateWarranty}">
					<input type="hidden" id="validateDescription" value="${configuration.validateDescription}">
					<input type="hidden" id="validateRepairable" value="${configuration.validateRepairable}">
					<input type="hidden" id="validatePurchaseVendor" value="${configuration.validatePurchaseVendor}">
					<input type="hidden" id="showPartType" value="${configuration.showPartType}">
					
					<div class="tab-content">
							<div class="box">
								<div class="box-body">
									<div id="basicInformationDiv">
									<c:if test="${configuration.showRefreshmentOption}">
										<div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="refreshment" class="browser-default custom-select">
												    	<option value="false">No</option>
														<option value="true">Yes</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Is Refreshment</span>
												  </label>
											</div>
									</c:if>
								<c:if test="${configuration.showPartTypeCategory}">
											   <div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="partTypeCatgory" onchange="showHideChildPArtDetailsDiv();" class="browser-default custom-select">
														<option value="1">Standard</option>
												    	<option value="2">Parent</option>
												    	<option value="3">Child</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Part Type Category<c:if test="${configuration.validateWarranty}"><abbr title="required">*</abbr></c:if></span>
												  </label>
											  </div>
									</c:if>
									<c:if test="${configuration.showPartManufacturerTypeCol}">
										<div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="partManufacturerType" class="browser-default custom-select">
												    	<option value="1">Original Parts</option>
														<option value="2">Local Parts</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Part Manufacturer Type</span>
												  </label>
											</div>
									</c:if>
										<div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											    <input class="form-control" style="text-transform:uppercase;" type="text" value=" " id="partNumber" placeholder="Part Number"/>
											    <span style="color: #2e74e6;font-size: 14px;"> Part Number <abbr title="required">*</abbr> </span>
											  </label>
										  </div>
										  <div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											    <input class="form-control" type="text" value=" " style="text-transform:uppercase;" value=" " id="partName"  placeholder="Part Name"/>
											    <span style="color: #2e74e6;font-size: 14px;"> Part Name <c:if test="${configuration.showExtendedPartSave}">(Technical)</c:if> <abbr title="required">*</abbr> </span>
											  </label>
										  </div>
										   <c:if test="${configuration.showPartNameLocal}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value=" " id="partNameLocal" style="text-transform:uppercase;"  placeholder="Part Number"/>
												    <span style="color: #2e74e6;font-size: 14px;"> Part Name (Local) <c:if test="${configuration.validateLocalParts}"><abbr title="required">*</abbr></c:if> </span>
												  </label>
											  </div>
										   </c:if>
										  <c:if test="${configuration.showPartNameBill}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value=" " id="partNameOnBill" style="text-transform:uppercase;"  placeholder="Part Name"/>
												    <span style="color: #2e74e6;font-size: 14px;"> Part Name (As Per Bill) </span>
												  </label>
											  </div>
										  </c:if>
										  
										  <c:if test="${configuration.showPartType}">
											   <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
													<select id="partType"  class="browser-default custom-select">
														<option value="-1"></option>
														<c:forEach items="${PartType}" var="PartType">
															<option value="${PartType.partTypeId}"><c:out
																	value="${PartType.partTypeName}" /></option>
														</c:forEach>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;"> Part Type <abbr title="required">*</abbr> </span>
												  </label>
											  </div>
										  </c:if>
											  
										  <div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											  <select id="manufacturer"  class="browser-default custom-select">
													<option value=""><!-- Please select --></option>
													<c:forEach items="${partManufacturer}"
														var="PartManufacturer">

														<option value="${PartManufacturer.pmid}"><c:out
																value="${PartManufacturer.pmName}" /></option>
													</c:forEach>
												</select>
											    <span style="color: #2e74e6;font-size: 14px;">${configuration.manufacturerTitle} <abbr title="required">*</abbr> </span>
											  </label>
										  </div>
										   <c:if test="${configuration.showOriginalBrand}">
											   <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <select id="originalBrand"  class="browser-default custom-select">
														<option value=""><!-- Please select --></option>
														<c:forEach items="${partManufacturer}"
															var="PartManufacturer">
															<option value="${PartManufacturer.pmid}"><c:out
																	value="${PartManufacturer.pmName}" /></option>
														</c:forEach>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Original OEM Brand</span>
												  </label>
											  </div>
										   </c:if>
										  
										  <div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											  <select id="partCategory"  class="browser-default custom-select">
													<option value="-1"></option>
													<c:forEach items="${PartCategories}" var="PartCategories">
														<option value="${PartCategories.pcid}"><c:out
																value="${PartCategories.pcName}" /></option>
													</c:forEach>
												</select> 
											    <span style="color: #2e74e6;font-size: 14px;"> Part Category <abbr title="required">*</abbr></span>
											  </label>
										  </div>
										  <c:if test="${configuration.showPartSubCategory}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
													<input class="form-control" type="text" value=" " id="partSubCategory" style="text-transform:uppercase;"  placeholder="Part SubCategory"/>
												    <span style="color: #2e74e6;font-size: 14px;"> Part SubCategory </span>
												  </label>
											  </div>
										  </c:if>
										   <c:if test="${configuration.showVehicleMake}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
													<input id="vehicleMake" style="min-height: 38px;"  type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;">Vehicle Make</span>
												  </label>
											  </div>
										   </c:if>
										  <c:if test="${configuration.showVehicleModel}">
											   <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
													<input id="vehicleModel" style="min-height: 38px;"  type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;">Vehicle Model</span>
												  </label>
											  </div>
										  </c:if>
										  <c:if test="${configuration.showWarranty}">
											   <div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="warranty" onchange="showHideWarrantyMOnthRow();" class="browser-default custom-select">
													    <option selected value="-1"></option>
														<option value="true">Yes</option>
												    	<option value="false">No</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Warranty Applicable<c:if test="${configuration.validateWarranty}"><abbr title="required">*</abbr></c:if></span>
												  </label>
											  </div>
											   <div  id="warrantyInMonthRow" class="col col-sm-12 col-md-3" style="display: none;">
												  <label class="has-float-label">
												    <input class="form-control" type="text" onkeypress="return isNumberKey(event,this);" value=" " id="warrantyInMonth"   placeholder="Warranty"/>
												    <span style="color: #2e74e6;font-size: 14px;">Warranty (in months) <abbr title="required">*</abbr> </span>
												  </label>
											  </div>
										  </c:if>
										  <c:if test="${configuration.showCoupon}">
											  <div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="couponAvailable" onchange="showCouponDetailsRow();" class="browser-default custom-select">
													    <option selected value="-1"></option>
														<option value="true">Yes</option>
												    	<option value="false">No</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Coupon Available</span>
												  </label>
											  </div>
											  <div  id="couponDetailsDiv" class="col col-sm-12 col-md-3" style="display: none;">
												  <label class="has-float-label">
												    <input class="form-control" type="text" onkeypress="return isNumberKey(event,this);" value=" " id="couponDetails"   placeholder="Coupon Details"/>
												    <span style="color: #2e74e6;font-size: 14px;">Coupon Details  </span>
												  </label>
											  </div>
										  </c:if>
										   <c:if test="${configuration.showScrap}">
											  <div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="scrapAvailable" class="browser-default custom-select">
													    <option selected value="-1"></option>
														<option value="true">Yes</option>
												    	<option value="false">No</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Scrap Available</span>
												  </label>
											  </div>
										   </c:if>
									</div>
									
									<div id="packingDetailsDiv">
										<c:if test="${configuration.showMainPacking}">
											 <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" id="mainPacking" value=" " type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;"> Main Packing </span>
												  </label>
											  </div>
										</c:if>
										 <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												  <select id="unitTypeId"  class="browser-default custom-select">
														<option value="-1"></option>
														<c:forEach items="${PartMeasurementUnit}"
															var="PartMeasurementUnit">
															<option value="${PartMeasurementUnit.pmuid}">
																<c:out value="${PartMeasurementUnit.pmuSymbol}" />
															</option>
														</c:forEach>
													</select> 
												    <span style="color: #2e74e6;font-size: 14px;">${configuration.UOMTitle } <abbr title="required">*</abbr> </span>
												  </label>
											  </div>
										 <%--  <c:if test="${configuration.showUOMMainPacking}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value=" " id="uomPacking" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;"> UOM (Main packing)</span>
												  </label>
											  </div>
										  </c:if> --%>
										  <c:if test="${configuration.showLooseItem}">
											   <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" id="looseItem"  value=" " type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;"> Loose Items</span>
												  </label>
											  </div>
										  </c:if>
										  <c:if test="${configuration.showLooseUOM}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" id="uomLoose" value=" " type="text" />
												    <span style="color: #2e74e6;font-size: 14px;"> UOM (Loose)</span>
												  </label>
											  </div>
										  </c:if>
									</div>
									
									<div id="otherDetailsDiv">
									<c:if test="${configuration.showBarCodeNumber}">
										<div class="col col-sm-12 col-md-3">
											  <label class="has-float-label">
											    <input class="form-control" type="text" value=" " id="barCodeNumber" placeholder="Bar Code Number"/>
											    <span style="color: #2e74e6;font-size: 14px;">Bar Code Number</span>
											  </label>
										  </div>
									</c:if>
										  <c:if test="${configuration.showItemType}">
											   <div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="itemType" class="browser-default custom-select">
													    <option selected value="-1"></option>
														<option value="1">Standard Make</option>
												    	<option value="2">Custom Make</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Item Type</span>
												  </label>
											  </div>
										  </c:if>
										  <c:if test="${configuration.showDimention}">
											  <div class="col col-sm-12 col-md-3">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value=" " id="Dimension" placeholder="Dimension"/>
												    <span style="color: #2e74e6;font-size: 14px;">Dimension</span>
												  </label>
											  </div>
										  </c:if>
										  <c:if test="${configuration.showPurchaseVendor}">
											  <div class="col col-sm-1 col-md-6">
												 <label class="has-float-label">
												    <input id="purchaseVendor" style="min-height: 38px;"  type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;">Purchase Vendor <c:if test="${configuration.validatePurchaseVendor}"><abbr title="required">*</abbr></c:if></span>
												  </label>
											  </div>
										  </c:if>
										  <c:if test="${configuration.showReparing}">
											  <div class="col col-sm-1 col-md-3 ">
												 <label class="has-float-label">
												     <select id="repairable" onchange="showHideRepairingVendor();" class="browser-default custom-select">
													    <option selected value="-1"></option>
														<option value="true">Yes</option>
												    	<option value="false">No</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Is Repairable <c:if test="${configuration.validateRepairable}"><abbr title="required">*</abbr></c:if></span>
												  </label>
											  </div>
											  <div id="vendorDiv" style="display: none;" class="col col-sm-12 col-md-6">
												  <label class="has-float-label">
												    <input id="repairingVendor" type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;">Repairing Vendor <abbr title="required">*</abbr></span>
												  </label>
											  </div>
										  </c:if>
										   <c:if test="${configuration.showChildParts}">
											  <div class="col col-sm-1 col-md-3">
												 <label class="has-float-label">
												     <select id="childPart" onchange="showHideChildPArtDetailsDiv();" class="browser-default custom-select">
													    <option selected value="-1"></option>
														<option value="true">Yes</option>
												    	<option value="false">No</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Child Part</span>
												  </label>
											  </div>
										   </c:if>
											  <div id="childPartDiv" style="display: none;" class="col col-sm-1 col-md-6">
												 <label class="has-float-label">
												      <input id="childPartDetails"  type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;" id="partTypeLabel">Child Part Details</span>
												  </label>
											  </div>
										   <c:if test="${configuration.showSubstitute}">
											  <div class="col col-sm-1 col-md-6" style="float:left">
												 <label class="has-float-label">
												     <input id="subtituteParts"  type="text" placeholder=""/>
												    <span style="color: #2e74e6;font-size: 14px;">Subtitude Parts</span>
												  </label>
											  </div>
										   </c:if>
										    <c:if test="${configuration.showAssetIdRequired}">
											  <div class="col col-sm-1 col-md-3">
												  <label class="has-float-label">
												     <select id="assetIdRequired" class="browser-default custom-select">
												    	<option value="false">No</option>
														<option value="true">Yes</option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;">Asset Id Required</span>
												  </label>
											  </div>
										   </c:if>
										   <c:if test="${configuration.showDescription}">
											  <div class="col col-sm-1 col-md-12">
												 <label class="has-float-label">
												 	 <textarea class="form-control" id="description" rows="1"></textarea>
												    <span style="color: #2e74e6;font-size: 14px;">Description <c:if test="${configuration.validateDescription}"><abbr title="required">*</abbr></c:if></span>
												  </label>
											  </div>
										   </c:if>
									</div>
								</div>
							</div>
							<c:if test="${configuration.showPartLocationOption}">
								<fieldset>
									<legend>Location</legend>
									<div class="box">
										<div class="box-body">
											<ul class="nav nav-tabs justify-content-left">
											<c:forEach items="${PartLocations}" var="PartLocations">
													<li class="nav-item">
														<a onclick="showHideReckDetailsRow(this);" id="${PartLocations.partlocation_id}" class="nav-link" href="javascript:void(0)">${PartLocations.partlocation_name}</a>
													</li>
												</c:forEach>
											</ul>	
											<br>
											<c:forEach items="${PartLocations}" var="PartLocations">
												<div class="recDiv" id="rowDiv_${PartLocations.partlocation_id}" style="display: none;">
													 <div class="col col-sm-12 col-md-3">
														  <label class="has-float-label">
														    <input class="form-control" id="aisle_${PartLocations.partlocation_id}" type="text" />
														    <span style="color: #2e74e6;font-size: 14px;">Aisle</span>
														  </label>
													  </div>
													  <div class="col col-sm-12 col-md-3">
														  <label class="has-float-label">
														    <input class="form-control" id="row_${PartLocations.partlocation_id}" type="text" />
														    <span style="color: #2e74e6;font-size: 14px;">Row</span>
														  </label>
													  </div>
													  <div class="col col-sm-12 col-md-3">
														  <label class="has-float-label">
														    <input class="form-control" id="bin_${PartLocations.partlocation_id}" type="text" />
														    <span style="color: #2e74e6;font-size: 14px;">Bin</span>
														  </label>
													  </div>
												</div>
											</c:forEach>
										</div>
									</div>
								</fieldset>
							 </c:if>
						
						<div class="row" id="savePartRow">
							<fieldset class="form-actions">
								<div class="pull-center">
									<button type="submit" onclick="saveNewMasterPartsDetails();"  class="btn btn-success" >Create Part</button>
									<a class=" btn btn-info" href="newMasterParts/1.in">
									<span id="Cancel">Cancel</span></a>
								</div>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/partmaster/addPartMaster.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>	 	

</div>