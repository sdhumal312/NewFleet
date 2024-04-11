<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<style>
.box-partheader, .partboxheader {
	border: none;
	position: relative
}

.box-partheader .partAction, .box-partheader .partInDate {
	float: left;
	padding: 3px;
	width: 30%
}

.partboxheader {
	float: left;
	margin: 0 auto;
	width: 100%
}

.box-partheader .partId {
	float: right;
	width: 30%;
	padding: 3px
}

.partboxheader h3 {
	font-size: 23px;
	font-weight: 700;
	margin-top: 5px;
	text-align: center
}

.partboxheader h4 {
	font-size: 13px;
	margin-top: 0;
	text-align: center
}
</style>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newMasterParts/1.in"/>">Master Parts</a> / <span><c:out
							value="${MasterParts.partnumber}" /> </span>
				</div>
				<div class="pull-right">
				<c:if test="${!commonMasterParts || MasterParts.partManufacturerType == 2}">
					<sec:authorize access="hasAuthority('EDIT_PARTS')">
							<a class="btn btn-success btn-sm"
								href="editNewMasterParts.in?partid=${MasterParts.partid}"> <i
								class="fa fa-pencil"></i> Edit Part
							</a>
					</sec:authorize>
				</c:if>	
				<sec:authorize access="hasAuthority('ADD_PARTS')">
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#addPartPrice" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="Click this add price"> <span
							class="fa fa-plus"></span> Add Price
						</a>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('ADD_PARTS')">
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#addPartLowLevel" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="Click this add low Stock Details"> <span
							class="fa fa-plus"></span> Add LowStock Level
						</a>
				</sec:authorize>
				<c:if test="${config.showPartLocationOption }">
					<sec:authorize access="hasAuthority('LOCATIONWISE_PART_POSITION')">
							<a class="btn btn-success btn-sm" data-toggle="modal"
								data-target="#addPartLocation" data-whatever="@mdo"
								data-toggle="tip"
								data-original-title="Click this add low Stock Details"> <span
								class="fa fa-plus"></span> Add Part Location
							</a>
					</sec:authorize>
				</c:if>
					<a class="btn btn-info btn-sm"
						href="<c:url value="/newMasterParts/1.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_PARTS')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_PARTS')">
				<div class="box-body">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getPartImage/${MasterParts.part_photoid}.in"
							class="zoom" data-title="Part Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getPartImage/${MasterParts.part_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showMasterParts.in?partid=${MasterParts.partid}"> <c:out
									value="${MasterParts.partnumber}" /> - <c:out
									value="${MasterParts.partname}" /> - <c:out
									value="${MasterParts.category}" /></a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Name"> </span> <span
									class="text-muted"><c:out
											value="${MasterParts.partname}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="category"> </span> <span
									class="text-muted"><c:out
											value="${MasterParts.category}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Make"> </span> <span
									class="text-muted"><c:out value="${MasterParts.make}" /></span></li>
							</ul>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<c:if test="${param.Update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Master Parts Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.addLowStock eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Low stock level updated successfully !
			</div>
		</c:if>
		<c:if test="${param.addPrice eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Price updated successfully !
			</div>
		</c:if>
		<c:if test="${param.addLocationDetails eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Part Location Details updated successfully !
			</div>
		</c:if>
		<c:if test="${param.error eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Operation Failed !
			</div>
		</c:if>
		
		<div class="modal fade" id="addPartPrice" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Price</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/savePrice.in"/>" method="post"
						 name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
								<div class="row">
								 <input type="hidden" name="partid" id="partid" value="${MasterParts.partid}" />
								<%--  <input type="hidden" name="partRateId" id="partRateId" value="${partRate.partRateId}" /> --%>
								 
											<label class="L-size control-label">Unit Price</label>
											<div class="I-size">
												<input type="text" required="required" class="form-text" id="unitCost"  onpaste="return false"
												    maxlength="25" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													name="unitCost" value="${partRate.unitCost}"
													placeholder="Enter Unit Price" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label">Discount</label>
											<div class="I-size">
												<input type="text" value="0" required="required"  class="form-text" id="discount"  onpaste="return false"
													maxlength="5" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													name="discount"
													value="${partRate.discount}"
													placeholder="Enter Discount" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div> 
										<div class="row">
											<label class="L-size control-label">Tax</label>
											<div class="I-size">
												<input type="text" value="0"  required="required"  class="form-text" id="tax" maxlength="5"  onpaste="return false"
													name="tax" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													value="${partRate.tax}"
													placeholder="Enter Tax" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div> 
							<br>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="addPartLowLevel" role="dialog">
			<div class="modal-dialog modal-md" style="width:900px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Low Stock Level Details</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/saveLowStockLevel.in"/>" method="post"
						 name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
							<table id="dataTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Location</th>
										<th>Low Stock Level</th>
										<th>Reorder Quantity</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									<c:forEach items="${lowStockList}" var="lowStockList">
										<tr>
											<td>${lowStockList.locationName }</td>
											<td>${lowStockList.lowstocklevel }</td>
											<td>${lowStockList.reorderquantity }</td>
											<td onclick="removeLowStockDetails(${lowStockList.lowStockInventoryId});" style="color: red;"><em class="fa fa-trash"/> Remove</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>	
									<div class="row">
											<label class="L-size control-label">Part Location</label>
											<div class="I-size">
												<select name="locationId" style="width: 100%;" id="partLocationLoStock">
													<option value="-1">Please Select</option>
													<c:forEach items="${Locations}" var="Locations">
														<option value="${Locations.partlocation_id}"><c:out
																value="${Locations.partlocation_name}" /></option>
													</c:forEach>
												</select>	
											</div>
									</div> 
									<br>
								
								<div class="row">
								 <input type="hidden" name="partid" id="partid" value="${MasterParts.partid}" />
								 <%-- <input type="hidden" name="lowStockInventoryId" id="lowStockInventoryId" value="${MasterParts.lowStockInventoryId}" /> --%>
											<label class="L-size control-label">Low Stock Level</label>
											<div class="I-size">
												<input type="text" class="form-text" onkeypress="return isNumberKey(event,this);" id="lowstocklevel"
													name="lowstocklevel" value="${MasterParts.lowstocklevel}"
													placeholder="Enter Low Stock Level" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label">Reorder Quantity</label>
											<div class="I-size">
												<input type="text" class="form-text" id="reorderquantity"
													name="reorderquantity" 	onkeypress="return isNumberKey(event,this);"
													value="${MasterParts.reorderquantity}"
													placeholder="Enter Reorder Quantity" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div> 
										<br>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick=" return validateLowStockLevel();" class="btn btn-success">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="RateHistoryModal" role="dialog">
			<div class="modal-dialog modal-md" style="width:900px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Low Stock Level Details</h4>
					</div>
						<div class="modal-body">
							<table id="dataTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Unit Price</th>
										<th>Discount</th>
										<th>Tax</th>
										<th>Updated Date</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									<c:forEach items="${partRateHistory}" var="partRateHistory">
										<tr>
											<td>${partRateHistory.unitCost }</td>
											<td>${partRateHistory.discount }</td>
											<td>${partRateHistory.tax }</td>
											<td>${partRateHistory.created }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>	
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="addPartLocation" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Part Location Details</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/savePartLocationDetails.in"/>" method="post"
						 name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
								<div class="row1" id="grppartCategory" class="form-group">
											<label class="L-size control-label" for="partCategory">Part Locations
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text select2" name="locationId"
													style="width: 100%;" id="partLocation">
													<option value="0"> Please select</option>
													<c:forEach items="${Locations}" var="Locations">
														<option value="${Locations.partlocation_id}"><c:out
																value="${Locations.partlocation_name}" /></option>
													</c:forEach>
												</select> 
												<input type='hidden' id='partCategoryText' name="category" value=''/>
												<span id="partCategoryIcon" class=""></span>
												<div id="partCategoryErrorMsg" class="text-danger"></div>
											</div>
								</div>
								<div class="row1">
									<input type="hidden" name="partid" id="partid" value="${MasterParts.partid}" />
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
							<br>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="RepairingVendorModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Repairing Vendor Details</h4>
					</div>
						<div class="modal-body">
							<c:if test="${!empty repairableVendors}">
							  <table class="table">
							  	<thead>
							  		<tr>
							  			<th>Vendor Name</th>
							  		</tr>
							  	</thead>
							  <tbody>
								  <c:forEach items="${repairableVendors}" var="repairableVendors">
									<tr>
										<td class="value"><a target="_blank" href="ShowVendor.in?vendorId=${repairableVendors.vendorId}">${repairableVendors.vendorName}</a></td>
									</tr>
								 </c:forEach>
							  </tbody>
							 </table>
							</c:if>
							</div>
						</div>
						</div>
				</div>
		<div class="modal fade" id="PurchaseVendorModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Purchase Vendor Details</h4>
					</div>
						<div class="modal-body">
							<c:if test="${!empty purchaseVendors}">
							  <table class="table">
							  	<thead>
							  		<tr>
							  			<th>Vendor Name</th>
							  		</tr>
							  	</thead>
							  <tbody>
								  <c:forEach items="${purchaseVendors}" var="purchaseVendors">
									<tr>
										<td class="value"><a target="_blank" href="ShowVendor.in?vendorId=${purchaseVendors.vendorId}">${purchaseVendors.vendorName}</a></td>
									</tr>
								 </c:forEach>
							  </tbody>
							 </table>
							</c:if>
							</div>
						</div>
						</div>
						
				</div>
				
		 <div class="modal fade" id="ChildPartModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Child Part Details</h4>
					</div>
						<div class="modal-body">
							<c:if test="${!empty childParts}">
							  <table class="table">
							  	<thead>
							  		<tr>
							  			<th>Child Part Name</th>
							  		</tr>
							  	</thead>
							  <tbody>
								  <c:forEach items="${childParts}" var="childParts">
									<tr>
										<td class="value"><a target="_blank" href="showMasterParts.in?partid=${childParts.partId}">${childParts.childPartName}</a></td>
									</tr>
								 </c:forEach>
							  </tbody>
							 </table>
							</c:if>
							</div>
						</div>
						</div>
				</div>
				
		<div class="modal fade" id="ParentPartModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Parent Part Details</h4>
					</div>
						<div class="modal-body">
							<c:if test="${!empty parentParts}">
							  <table class="table">
							  	<thead>
							  		<tr>
							  			<th>Parent Part Name</th>
							  		</tr>
							  	</thead>
							  <tbody>
								  <c:forEach items="${parentParts}" var="parentParts">
									<tr>
										<td class="value"><a target="_blank" href="showMasterParts.in?partid=${parentParts.mainPartId}">${parentParts.childPartName}</a></td>
									</tr>
								 </c:forEach>
							  </tbody>
							 </table>
							</c:if>
							</div>
						</div>
						</div>
				</div>
				
		 <div class="modal fade" id="SubtitudePartModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Substitude Part Details</h4>
					</div>
						<div class="modal-body">
							<c:if test="${!empty substituDeParts}">
							  <table class="table">
							  	<thead>
							  		<tr>
							  			<th>Substitude Name</th>
							  		</tr>
							  	</thead>
							  <tbody>
								  <c:forEach items="${substituDeParts}" var="substituDeParts">
									<tr>
										<td class="value"><a target="_blank" href="showMasterParts.in?partid=${substituDeParts.partId}">${substituDeParts.childPartName}</a></td>
									</tr>
								 </c:forEach>
							  </tbody>
							 </table>
							</c:if>
							</div>
						</div>
						</div>
				</div>
		
		<sec:authorize access="!hasAuthority('VIEW_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PARTS')">
			<div class="row">
				<input type="hidden" id="partTypeCategoryId" value="${MasterParts.partTypeCategoryId }">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<ul class="tabs">
							<li class=" current" data-tab="tab-1">Details</li>
							<li class="tab-link" data-tab="tab-2">Description</li>
						</ul>
						<div id="tab-1" class="tab-content2 current">
							<div class="row">
								<div class="col-md-6 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Part Information</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<c:if test="${config.showPartTypeCategory}">
														<tr class="row">
															<th class="key">Part Type Category :</th>
															<td class="value" style="width: 2432452px;"><c:out
																	value="${MasterParts.partTypeCategory}" /></td>
														</tr>
													</c:if>
													<tr class="row">
														<th class="key">Part Number :</th>
														<td class="value" style="width: 2432452px;"><c:out
																value="${MasterParts.partnumber}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Part Name :</th>
														<td class="value"><c:out
																value="${MasterParts.partname}" /></td>
													</tr>
													<c:if test="${config.showExtendedPartSave}">
														<tr class="row">
																<th class="key">Part Name(Local) :</th>
																<td class="value">${MasterParts.localPartName}</td>
														</tr>
														<tr class="row">
																<th class="key">Part Name (As per Bill) :</th>
																<td class="value">${MasterParts.partNameOnBill}</td>
														</tr>
														<tr class="row">
																<th class="key">Part Sub Category :</th>
																<td class="value">${MasterParts.partSubCategoryName}</td>
														</tr>
													</c:if>	
													<tr class="row">
														<th class="key">Category :</th>
														<td class="value"><c:out
																value="${MasterParts.category}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Product Brand :</th>
														<td class="value"><c:out value="${MasterParts.make}" /></td>
													</tr>
													<c:if test="${config.showPartType }">
														<tr class="row">
															<th class="key">Type :</th>
															<td class="value"><c:out
																	value="${MasterParts.parttype}" /></td>
														</tr>
													</c:if>
													<c:if test="${config.showExtendedPartSave}">
														<tr class="row">
																<th class="key">Part Name (As per Bill) :</th>
																<td class="value">${MasterParts.partNameOnBill}</td>
														</tr>
														
														<tr class="row">
															<th class="key">Warranty Applicable :</th>
																<c:choose>
																	<c:when test="${MasterParts.warrantyApplicable}">
																		<td class="value">Yes ( ${MasterParts.warrantyInMonths} Months)</td>
																	</c:when>
																	<c:otherwise>
																		<td class="value">No</td>
																	</c:otherwise>
																</c:choose>
														</tr>
														<tr class="row">
															<th class="key">Repairable :</th>
															<c:choose>
																	<c:when test="${MasterParts.repairable}">
																		<td class="value">Yes</td>
																	</c:when>
																	<c:otherwise>
																		<td class="value">No</td>
																	</c:otherwise>
																</c:choose>
														</tr>
														<tr class="row">
															<th class="key">Scrap Available :</th>
															<c:choose>
																	<c:when test="${MasterParts.scrapAvilable}">
																		<td class="value">Yes</td>
																	</c:when>
																	<c:otherwise>
																		<td class="value">No</td>
																	</c:otherwise>
																</c:choose>
														</tr>
														<tr class="row">
															<th class="key">Coupon Available :</th>
																<c:choose>
																	<c:when test="${MasterParts.couponAvailable}">
																		<td class="value">Yes</td>
																	</c:when>
																	<c:otherwise>
																		<td class="value">No</td>
																	</c:otherwise>
																</c:choose>
														</tr>
														<c:if test="${MasterParts.couponAvailable}">
															<tr class="row">
																<th class="key">Coupon Available :</th>
																<td class="value">${MasterParts.couponDetails }</td>
															</tr>
														</c:if>
														<tr class="row">
																<th class="key">Vehicle Make :</th>
																<td class="value">
																	<c:forEach items="${vehicleMake}" var="vehicleMake">
																			${vehicleMake.vehicleManufacturer} <b>/</b>
																	</c:forEach>
																	
																</td>
														</tr>
														<tr class="row">
																<th class="key">Vehicle Model :</th>
																<td class="value">
																	<c:forEach items="${vehicleModal}" var="vehicleModal">
																			${vehicleModal.vehicleModel} <b>/</b>
																	</c:forEach>
																	
																</td>
														</tr>
													</c:if>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-5 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<%-- <tr class="row">
														<th class="key">Low Stock Level :</th>
														<td class="value"><c:out
																value="${MasterParts.lowstocklevel}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Reorder Quantity :</th>
														<td class="value"><c:out
																value="${MasterParts.reorderquantity}" /></td>
													</tr> --%>
													<tr class="row">
														<th class="key">${config.UOMTitle } :</th>
														<td class="value"><c:out
																value="${MasterParts.unittype}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Unit Cost :</th>
														<td class="value"><c:out
																value="${partRate.unitCost}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Discount :</th>
														<td class="value"><c:out
																value="${partRate.discount}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Tax :</th>
														<td class="value"><c:out
																value="${partRate.tax}" /></td>
													</tr>
													<c:if test="${config.showExtendedPartSave}">
														<tr class="row">
																<th class="key">Original OEM/ Brand :</th>
																<td class="value">${extraDetails.originalBrand}</td>
														</tr>
														<%-- <tr class="row">
																<th class="key">Main Packing :</th>
																<td class="value">${extraDetails.mainPacking}</td>
														</tr> --%>
														<tr class="row">
																<th class="key">UOM Packing :</th>
																<td class="value">${extraDetails.uomPacking}</td>
														</tr>
														<tr class="row">
																<th class="key">Loose Item :</th>
																<td class="value">${extraDetails.looseItem}</td>
														</tr>
														<tr class="row">
																<th class="key">Loose UOM :</th>
																<td class="value">${extraDetails.looseUom}</td>
														</tr>
														<tr class="row">
																<th class="key">Bar Code Number :</th>
																<td class="value">${extraDetails.barCodeNumber}</td>
														</tr>
														<tr class="row">
																<th class="key">Item Type :</th>
																<td class="value">${extraDetails.itemType}</td>
														</tr>
														<tr class="row">
																<th class="key">Dimension :</th>
																<td class="value">${extraDetails.dimention}</td>
														</tr>
														<tr class="row">
																<th class="key">Asset Id Required :</th>
																<td class="value">${MasterParts.assetIdRequiredStr}</td>
														</tr>
													</c:if>
												</tbody>
											</table>
											
										</div>
									</div>
								</div>
							</div>
							
							<c:if test="${config.showPartLocationOption }">
							<div class="row">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success ">
											<div class="box-header">
												
												<c:choose>
													<c:when test="${PartLocations.location_quantity > 0}">
														<h3 class="box-title">${PartLocations.location} 
														<a target="_blank"
															href="<c:url value="/showLocationInventory.in?inventory_location_id=${PartLocations.inventory_location_id}"/>">
															<span class="pull-right badge bg-aqua">${PartLocations.location_quantity}</span></a></h3>		
													</c:when>
													<c:otherwise>
													 <h3 class="box-title">${PartLocations.location} 
														<span class="pull-right badge bg-aqua">${PartLocations.location_quantity}</span></h3>
													</c:otherwise>
												</c:choose>
												
												
											</div>
											<div class="box-body box-partheader ">
												<div class="partboxheader">
													<div class="partAction">
														<h4>
															<c:out value="Aisle" />
														</h4>
														<h3>
															<c:out value="${PartLocations.aisle}" />
														</h3>
													</div>
													<div class="partInDate">
														<h4>
															<c:out value="Row" />
														</h4>
														<h3>
															<c:out value="${PartLocations.row}" />
														</h3>
													</div>
													<div class="partId">
														<h4>
															<c:out value="Bin" />
														</h4>
														<h3>
															<c:out value="${PartLocations.bin}" />
														</h3>
													</div>
													
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
							</c:if>
						</div>
						<div id="tab-2" class="tab-content2">
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Description :</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-striped">
										<tbody>
											<tr class="row">

												<td><c:out value="${MasterParts.description}" /></td>
											</tr>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<%@include file="MasterPartsSideMenu.jsp"%>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${MasterParts.createdBy}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${MasterParts.created}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${MasterParts.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${MasterParts.lastupdated}" /></small>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/partmaster/showMasterParts.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>