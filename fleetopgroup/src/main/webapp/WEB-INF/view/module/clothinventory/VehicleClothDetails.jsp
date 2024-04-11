<%@ include file="../../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="vehicle/1/1">Vehicle</a> / <a id="showvehicle"
						href="#"></a> / <span>
						Vehicle Upholstery Details</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="#" id="cancel">Cancel</a>
				</div>
				<div class="pull-right">
					<a target="_blank" href="<c:url value="/addVehicleClothMaxAllowed.in"/>" class="btn btn-warning btn-sm" >
   							 <span class="fa fa-plus">
								Configure Max Qty</span>
					</a>
					<a class="btn btn-success btn-sm" onclick="showHideClothDetails();">
   							 <span class="fa fa-plus">
								Show Upholstery Assign/Remove History</span></a>
  					</a>
				</div>
				
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a id="vehicleStatus"
										href="#"></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#" id="odometer"></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#" id="vehicleType"></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#" id="vehicleLocation"></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#" id="vehicleGroup"></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#" id="vehicleRoute"></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
				<input type="hidden" id="vid" value="${vid}">
			</div>
		</div>
	</section>

	<section class="content-body">
	
	
	<div class="modal fade" id="addClothDetails" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Assign Upholstery To Vehicle </h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="newQuantity">
						<input type="hidden" id="usedQuantity">
						<input type="hidden" id="remainingMaxAllowed">
						<input type="hidden" id="allowMultipleLocations">
						<div class="row">
							<fieldset>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Location
													  <abbr title="required">*</abbr>
													</label> 
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="locationId" id="locationId"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Location Name" /> 
															</div>
													</div>
												</div>
											
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Upholstery
														Name <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="clothTypes" id="clothTypes"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Cloth Name" /> 
															</div>
													</div>
												</div>
												
											<div class="row1" class="form-group">
												<label class="L-size control-label">Types of Upholstery
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" name="typeOfCloth"
														id="typeOfCloth" required="required">
														<option value="1">NEW/FRESH</option>
														<option value="2">OLD/USED</option>
													</select>
												</div>
											</div>
												<!-- <div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Stock 
														Qty :</label>
													<div class="I-size">
															<div class="input-group">
																<input type="text" class="form-text" 
																	name="stockQuantity"  id="stockQuantity"
																	readonly="readonly" min="0.00">
															</div>
													</div>
												</div> -->
												
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="col-md-9">
														<div class="col-md-1">
															<label class="control-label">Stock Qty</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Max Allowed Qty</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Old Assigned Qty</label>
														</div>
													</div>
												</div>	
												
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" 
																	name="stockQuantity"  id="stockQuantity"
																	readonly="readonly" min="0.00">
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" 
																	name="maxAllowedQuantity"  id="maxAllowedQuantity"
																	readonly="readonly" min="0.00">
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" 
																	name="remainingQuantity"  id="remainingQuantity"
																	readonly="readonly" min="0.00">
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>	
												
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Upholstery
														Quantity : <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
															<div class="input-group">
																<input type="number" class="form-text" placeholder="Quantity"
																	name="quantity" required="required" id="quantity"
																	maxlength="5" data-toggle="tip"
																	ondrop="return false;"  onkeyup="validateQuantity();" onblur="validateQuantity();"
																	min="0.00">
																	
															</div>
													</div>
												</div>
											
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" onclick="saveClothInventoryDetails();" class="btn btn-success">Add Upholstery To Vehicle
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close"><spring:message
											code="label.master.Close" /></span>
										</button>
										<!-- <a id="cancel" class="btn btn-link"
											href="">Cancel</a> -->
									</div>
								</div>
							</fieldset>
					</div>				
					</div>
					<input type="hidden" id="clothTypesIds">

				</div>
			</div>
		</div>
		
			<div class="modal fade" id="removeClothDetails" role="dialog">
			<div class="modal-dialog modal-md" style="width:850px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"> Remove Upholstery From Vehicle</h4>
					</div>
					<div class="modal-body">

						<div class="row">
							<fieldset>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<input type="hidden" id="vehicleClothInventoryDetailsId">
											<input type="hidden" id="locationId">
											<input type="hidden" id="removeClothTypesId">
											<div class="form-horizontal ">
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Upholstery
														Name <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="row">
																<input class="form-text" type="text" name="removeClothTypes" id="removeClothTypes"
																	required="required" style="width: 100%;"
																    readonly="readonly"	/> 
															</div>
													</div>
												</div>
										
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Upholstery
														Quantity : <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="input-group">
																<input type="number" class="form-text" readonly="readonly"
																	name="quantity" required="required" id="asignedQuantity"
																	maxlength="5" data-toggle="tip"
																	ondrop="return false;"
																	min="0.00">
															</div>
													</div>
												</div>
												
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Remove Location
														 <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="removelocationId" 
																   id="removelocationId" required="required" 
																    style="width: 100%;"/> 
															</div>
													</div>
												</div>
												
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Remove
														Quantity : <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="input-group">
																<input type="number" class="form-text"
																	name="quantity" required="required" id="removeQuantity"
																	maxlength="5" data-toggle="tip"
																	ondrop="return false;"
																	min="0.00">
															</div>
													</div>
												</div>
												
											
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" onclick="removeClothInventoryDetails();" class="btn btn-success">Remove Upholstery From Vehicle
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close"><spring:message
											code="label.master.Close" /></span>
										</button>
									</div>
								</div>
							</fieldset>
					</div>				
					</div>

				</div>
			</div>
		</div>
	
	

		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<c:if test="${param.deleteSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Battery Layout Deleted Successfully.
						</div>
					</c:if>
					<div class="row" id="showData">
								<div class="box">
									<div class="box-body">
											<table style="width: 100%" class="table">
												<thead>
													<tr>
														<th>Sr.</th>
														<th>Upholstery Type</th>
														<th>Quantity</th>
														<th>Max Allowed Qty</th>
														<th>Remove Upholstery</th>
													</tr>
												</thead>
												<tbody id="clothDetails">
													
												</tbody>
											</table>
											<br/>
											<br/>
											<div>
												<a id="addMore" href="#" onclick="openAddClothDetailsModel();">Add More Upholstery Details</a>
											</div>
									</div>
								</div>
					</div>
				</div>
			</sec:authorize>
		</div>
		
		<div class="row" id="clothDetailsTable">
			<div class="box">
				<div class="box-body">
				 
					<div class="table-responsive">
						<input type="hidden" id="startPage" value="${SelectPage}"> 
						<table id="VendorPaymentTable" class="table table-hover table-bordered">
						
						</table>
					</div>
				</div>
				<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
						</ul>
				</div>
			</div>
		</div>
		
	</section>

<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/vehicleClothInventoryDetails.js"></script>
</div>