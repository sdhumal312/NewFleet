<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addModelSubType.in"/>">New Model Type</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addJobSubtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobSubType"> Create Tyre
								Model Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveModelSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Model Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateModelSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Model Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteModelSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Model Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyModelSubType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Model Type Already Exists.
			</div>
		</c:if>
		<c:if test="${alreadyModelSubType}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Model Type Already Exists.
			</div>
		</c:if>
		<input type="hidden" id="gauageMeasurementLineConfig" value="${configuration.gauageMeasurementLine}">
		<input type="hidden" id="tyreGaugeConfig" value="${configuration.tyreGauge}">
		<input type="hidden" id="tyrePlyConfig" value="${configuration.tyrePly}">
		<input type="hidden" id="tyrePsiConfig" value="${configuration.tyrePsi}">
		<input type="hidden" id="tyreModelSizeIdConfig" value="${configuration.tyreModelSizeId}">
		<div class="modal fade" id="addJobSubtypes" role="dialog">
			<div class="modal-dialog" style="width: 60%;">
				<div class="modal-content">
					<form action="saveModelSubType.in" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobSubType">Tyre Model Type</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
								<div class="row1">
									<label class="L-size control-label">Manufacturers Type :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<select class="form-text selectType" name="TYRE_MT_ID"
											style="width: 100%;" id="selectReType" required="required">
											<c:forEach items="${TyreModelType}" var="TyreModelType">
												<option value="${TyreModelType.TYRE_MT_ID}">${TyreModelType.TYRE_MODEL}
												</option>
											</c:forEach>
										</select> <label id="errorReType" class="error"></label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Model Name :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" id="SubReType"
											required="required" name="TYRE_MODEL_SUBTYPE" maxlength="150"
											placeholder="Enter Brand Name" /> <label id="errorSubReType"
											class="error"></label>
									</div>
								</div>
								<c:if test="${configuration.tyreModalTypeId}">
									<div class="row1">
										<label class="L-size control-label" id="name"> Tyre Model Type :<abbr title="required">*</abbr></label>
										<div class="I-size">
											<select id="tyreModelTypeId" name="tyreModelTypeId" class ="form-text">
												<option value="1">Radial</option>
												<option value="2">Nylon</option>
											</select>
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreModalTypeId}">
								<input type="hidden" id="tyreModelTypeId" name="tyreModelTypeId"  value=0>
								</c:if>
								<c:if test="${configuration.tyreModelSizeId}">
									<div class="row1">
										<label class="L-size control-label" id="name"> Tyre Model Size :<abbr title="required">*</abbr></label>
										<div class="I-size">
											 <input type="text" name="tyreModelSizeId" id="tyreModelSizeId" style="width: 100%;" class="select" />
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreModelSizeId}">
								<input type="hidden" name="tyreModelSizeId"  value=0>
								</c:if>
								<c:if test="${configuration.gauageMeasurementLine}">
									<div class="row1">
										<label class="L-size control-label" id="name"> Gauge Measurement Line:<abbr title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="gauageMeasurementLine"
											  onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="gauageMeasurementLine">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.gauageMeasurementLine}">
								<input type="hidden" id="gauageMeasurementLine" name="gauageMeasurementLine"  value=0>
								</c:if>
								<c:if test="${configuration.tyreGauge}">
									<div class="row1">
										<label class="L-size control-label" id="name"> Tyre Gauge :<abbr title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="tyreGauge"
											  onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="tyreGauge">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreGauge}">
								<input type="hidden" id="tyreGauge" name="tyreGauge"  value=0>
								</c:if>
								<c:if test="${configuration.tyreTubeTypeId}">
									<div class="row1">
										<label class="L-size control-label" id="name"> Tyre Tube Type :<abbr title="required">*</abbr></label>
										<div class="I-size">
											<select id="tyreTubeTypeId" name="tyreTubeTypeId" class ="form-text">
												<option value="1">Tube</option>
												<option value="2">TubeLess</option>
											</select>
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreTubeTypeId}">
								<input type="hidden" id="tyreTubeTypeId" name="tyreTubeTypeId"  value=0>
								</c:if>
								<c:if test="${configuration.tyrePly}">
									<div class="row1">
										<label class="L-size control-label" > Ply :<abbr title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="ply" 
											  onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="ply">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyrePly}">
								<input type="hidden" id="ply" name="ply"  value=0>
								</c:if>
								<c:if test="${configuration.tyrePsi}">
									<div class="row1">
										<label class="L-size control-label" id="name"> PSI :<abbr title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="psi" 
											  onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="psi">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyrePsi}">
								<input type="hidden" id="psi" name="psi"  value=0>
								</c:if>
								<div class="row1" id="grptimeInterval" class="form-group">
												<label class="L-size control-label" for="time_interval">Tyre
													Warranty <abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<div class="col-md-4">
														<input type="number" class="form-text"
															name="warrantyPeriod" min="0" maxlength="2" value="1"
															id="warrantyPeriod" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															ondrop="return false;"> <span
															id="timeIntervalIcon" class=""></span>
														<p class="help-block">(e.g. 12 month)</p>
													</div>
													<div class="col-md-4">
														<select class="form-text" id="warrantyTypeId"
															name="warrantyTypeId"
															required="required">
															<option value="1">Month(s)</option>
															<option value="2">Year(s)</option>
														</select>
													</div>

												</div>

											</div>
											
											<!-- here is one spelling mistake "warrenty" -->
											
											<div class="row1">
											<label class="L-size control-label">Tyre warranty terms :</label>
											<div class="I-size">
												<textarea class="form-text" name="warrentyterm" rows="3"
													maxlength="1000" id="warrentyterm" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;"> 
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>
								<div class="row1">
									<label class="L-size control-label">Remarks :</label>
									<div class="I-size">
										<input type="text" class="form-text" id="SubReType"
											name="TYRE_MODEL_DESCRITION" maxlength="249"
											placeholder="Enter notes" /> <label id="errorSubReType"
											class="error"></label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Cost Per KM (Approx.) :</label>
									<div class="I-size">
										<input type="number" step="any" class="form-text" id="costPerKM"
											name="costPerKM" maxlength="249" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
											placeholder="Enter costPerKM" />
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<div class="row1">
								<button type="submit" class="btn btn-primary" onclick="return validateTyreModeltype();" >
									<span id="Save">Save</span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close">Close</span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
	<div class="modal fade" id="editCost" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="editCostPerKm.in" method="post"
						>
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobSubType">Edit Cost</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
								
								<div class="row1">
									<label class="L-size control-label">Cost Per KM (Approx.) :</label>
									<div class="I-size">
									<input type="hidden" id="vehicleCostFixingId" name="vehicleCostFixingId" />
									<input type="hidden" id="TYRE_MST_ID" name="TYRE_MST_ID" />
										<input type="number" step="any" class="form-text" id="costPerKMEdit"
											name="costPerKM" maxlength="249" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
											placeholder="Enter costPerKM" />
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<div class="row1">
								<button type="submit" class="btn btn-primary"  >
									<span id="Save">Save</span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close">Close</span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
								<div class="table-responsive">
									<c:if test="${!empty TyreModelSubType}">
										<table id="SubTypeTable"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th id="TypeName" class="icon">Manufacturers Name</th>
													<th class="icon">Model Name</th>
													<th class="icon">warranty</th>
													<th class="icon">warranty Term</th>
													<th class="icon">Remarks</th>
													<th class="icon">Cost(per KM)</th>
													<c:if test="${!isCommonTyreModel}">
														<th id="Actions" class="icon">Actions</th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${TyreModelSubType}"
													var="TyreModelSubType">
													<tr>
														<td><c:out value="${TyreModelSubType.TYRE_MODEL}" /></td>
														<td><a href="<c:url value="/showVehicleModelSubType.in?id=${TyreModelSubType.TYRE_MST_ID}"/>"><c:out
																value="${TyreModelSubType.TYRE_MODEL_SUBTYPE}" /></a></td>
														<td><c:out
																value="${TyreModelSubType.warrantyPeriod} - ${TyreModelSubType.warrantyType}" /></td>
														<td><c:out
																value="${TyreModelSubType.warrentyterm} " /></td>
															
														<td><c:out
																value="${TyreModelSubType.TYRE_MODEL_DESCRITION}" /></td>
																
														<td><c:out
																value="${TyreModelSubType.costPerKM}" /><a href="#" onclick="editPerKmCost(${TyreModelSubType.costPerKM}, ${TyreModelSubType.vehicleCostFixingId}, ${TyreModelSubType.TYRE_MST_ID });">&emsp;Edit Cost</a></td>
													<c:if test="${!isCommonTyreModel}">
														<td>
															<div class="btn-group">
																<a class="btn btn-Link dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="editModelSubType.in?Id=${TyreModelSubType.TYRE_MST_ID}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteModelSubType.in?Id=${TyreModelSubType.TYRE_MST_ID}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Brand name ?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
													</c:if>	
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty TyreModelSubType}">
										<div class="main-body">
											<p class="lead text-muted text-center t-padded">
												<spring:message code="label.master.noresilts" />
											</p>

										</div>
									</c:if>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/VehicleTyreModelSubTypeAdd.js" />"></script>
	
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  