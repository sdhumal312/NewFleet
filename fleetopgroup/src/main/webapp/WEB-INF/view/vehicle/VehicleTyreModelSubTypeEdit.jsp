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
						href="<c:url value="/addModelSubType.in"/>">New Model Type</a> / <span
						id="NewJobType">Edit Model Type</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/addModelSubType.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<input type="hidden" id="gauageMeasurementLineConfig" value="${configuration.gauageMeasurementLine}">
		<input type="hidden" id="tyreGaugeConfig" value="${configuration.tyreGauge}">
		<input type="hidden" id="tyrePlyConfig" value="${configuration.tyrePly}">
		<input type="hidden" id="tyrePsiConfig" value="${configuration.tyrePsi}">
		<input type="hidden" id="tyreModelSizeIdConfig" value="${configuration.tyreModelSizeId}">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty TyreModelSubType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddJob">Edit Model Type</h1>
								</div>
								<form action="updateModelSubType.in" method="post">
									<!-- onsubmit="return validateSubReTypeUpdate()"> -->
									<div class="panel-body">
										<input name="TYRE_MST_ID"
											value="${TyreModelSubType.TYRE_MST_ID}" type="hidden" />
										<input type="hidden" id="editTyreSizeId" value="${TyreModelSubType.tyreModelSizeId}">	
										<input type="hidden" id="editTyreSize" value="${TyreModelSubType.tyreModelSize}">	
										<div class="row">
											<label class="L-size control-label">Manufacturers
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text selectType"
													name="TYRE_MT_ID" style="width: 100%;" id="selectReType">
													<option value="${TyreModelSubType.TYRE_MT_ID}"
														selected="selected">${TyreModelSubType.TYRE_MODEL}</option>
													<c:forEach items="${TyreModelType}" var="TyreModelType">
														<option value="${TyreModelType.TYRE_MT_ID}">${TyreModelType.TYRE_MODEL}
														</option>
													</c:forEach>
												</select> <label id="errorReType" class="error"></label>
											</div>
										</div>
										<br>
										<div class="row">
											<label class="L-size control-label">Model Name :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" class="form-text" id="SubReType"
													name="TYRE_MODEL_SUBTYPE"
													value="${TyreModelSubType.TYRE_MODEL_SUBTYPE}"
													maxlength="150" placeholder="Enter Brand Name" /> <label
													id="errorSubReType" class="error"></label>
											</div>
										</div>
										<br>
										<c:if test="${configuration.tyreModalTypeId}">
									<div class="row">
										<label class="L-size control-label" id="name"> Tyre Model Type :<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											<select class="form-text selectType" name="tyreModelTypeId" style="width: 100%;" id="tyreModelTypeId">
												<option value="${TyreModelSubType.tyreModelTypeId}" selected="selected">${TyreModelSubType.tyreModelType}</option>
												<option value="1">Radial</option>
												<option value="2">Nylon</option>
											</select>
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreModalTypeId}">
								<input type="hidden" id="tyreModelTypeId" name="tyreModelTypeId"  value=0>
								</c:if>
								<br>
								<c:if test="${configuration.tyreModelSizeId}">
									<div class="row1">
										<label class="L-size control-label" id="name"> Tyre Model Size :<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											 <input type="text" name="tyreModelSizeId" id="tyreModelSizeId" style="width: 100%;" class="select" />
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreModelSizeId}">
								<input type="hidden" name="tyreModelSizeId"  value=0>
								</c:if>
								<br>
								<c:if test="${configuration.gauageMeasurementLine}">
									<div class="row">
										<label class="L-size control-label" id="name"> Gauge Measurement Line:<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="gauageMeasurementLine" 
											 onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="gauageMeasurementLine" value="${TyreModelSubType.gauageMeasurementLine}">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.gauageMeasurementLine}">
								<input type="hidden" id="gauageMeasurementLine" name="gauageMeasurementLine"  value=0>
								</c:if>
								<br>
								<c:if test="${configuration.tyreGauge}">
									<div class="row">
										<label class="L-size control-label" id="name"> Tyre Gauge :<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="tyreGauge" 
											 onkeypress="return isNumberKeyWithDecimal(event,this.id);" name="tyreGauge"  value="${TyreModelSubType.tyreGauge}">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreGauge}">
								<input type="hidden" id="tyreGauge" name="tyreGauge"  value=0>
								</c:if>
								<br>
								<c:if test="${configuration.tyreTubeTypeId}">
									<div class="row">	
										<label class="L-size control-label" id="name"> Tyre Tube Type :<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											<select class="form-text selectType" name="tyreTubeTypeId" style="width: 100%;" id="tyreTubeTypeId">
												<option value="${TyreModelSubType.tyreTubeTypeId}" selected="selected">${TyreModelSubType.tyreTubeType}</option>
												<option value="1">Tube</option>
												<option value="2">TubeLess</option>
											</select>
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyreTubeTypeId}">
								<input type="hidden" id="tyreTubeTypeId" name="tyreTubeTypeId"  value=0>
								</c:if>
								<br>
								<c:if test="${configuration.tyrePly}">
									<div class="row">
										<label class="L-size control-label" > Ply :<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="ply" name="ply"  
											 onkeypress="return isNumberKeyWithDecimal(event,this.id);" value="${TyreModelSubType.ply}">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyrePly}">
								<input type="hidden" id="ply" name="ply"  value=0>
								</c:if>
								<br>
								<c:if test="${configuration.tyrePsi}">
									<div class="row">
										<label class="L-size control-label" id="name"> PSI :<abbr
										title="required">*</abbr></label>
										<div class="I-size">
											<input type="number" class ="form-text" id="psi" name="psi" 
											 onkeypress="return isNumberKeyWithDecimal(event,this.id);" value="${TyreModelSubType.psi}">
										</div>
									</div>
								</c:if>
								<c:if test="${!configuration.tyrePsi}">
								<input type="hidden" id="psi" name="psi"  value=0>
								</c:if>
								<br>

										<!-- Devy Start Code for displaying Warranty Related Fields -->

										<!-- Tyre Warranty Section Code Start -->
										<div class="row1" id="grptimeInterval" class="form-group">
											<label class="L-size control-label" for="time_interval">Tyre
												Warranty <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="col-md-4">
													<input type="number" class="form-text"
														name="warrantyPeriod" min="0" maxlength="2"
														id="warrantyPeriod" ondrop="return false;"
														value="${TyreModelSubType.warrantyPeriod}" /> <span
														id="timeIntervalIcon" class=""></span>
													<p class="help-block">(e.g. 12 month)</p>
												</div>

												<div class="col-md-4">
													<select class="form-text" id="warrantyTypeId"
														name="warrantyTypeId" required="required">
														<c:choose>
															<c:when
																test="${TyreModelSubType.warrantyTypeId == 1 || TyreModelSubType.warrantyTypeId == 2}">
																<c:if test="${TyreModelSubType.warrantyTypeId == 1}">
																	<option value="1">Month(s)</option>
																	<option value="2">Year(s)</option>
																</c:if>
																<c:if test="${TyreModelSubType.warrantyTypeId == 2}">
																	<option value="2">Year(s)</option>
																	<option value="1">Month(s)</option>
																</c:if>
															</c:when>
															<c:otherwise>
																<option value="2">Year(s)</option>
																<option value="1">Month(s)</option>
															</c:otherwise>
														</c:choose>
													</select>
												</div>
											</div>
										</div>
										<!-- Tyre Warranty Section Code Start -->

										<!-- onkeypress="return IsVendorRemark(event);"
													ondrop="return false;" -->
										<!--Tyre Warranty Terms Code Start -->
										<div class="row">
											<label class="L-size control-label">Tyre warranty
												terms :</label>
											<div class="I-size">
												<textarea class="form-text" name="warrentyterm" rows="3"
													maxlength="1000" id="warrentyterm">													
													<c:out value="${TyreModelSubType.warrentyterm}"></c:out>
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>
										<!--Tyre Warranty Terms Code End -->

										<!-- Devy End  Code for displaying Warranty Related Fields -->

										<div class="row">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<input type="text" class="form-text" id="SubReType"
													name="TYRE_MODEL_DESCRITION"
													value="${TyreModelSubType.TYRE_MODEL_DESCRITION}"
													maxlength="200" placeholder="Enter notes" /> <label
													id="errorSubReType" class="error"></label>
											</div>
										</div>

										<div class="row">
											<label class="L-size control-label">Cost Per KM
												(Approx.) :</label>
											<div class="I-size">
												<input type="hidden" name="vehicleCostFixingId"
													id="vehicleCostFixingId"
													value="${VehicleCostFixing.vehicleCostFixingId}" /> <input
													type="number" step="any" class="form-text" id="costPerKM"
													name="costPerKM" maxlength="249"
													value="${VehicleCostFixing.costPerKM}"
													placeholder="Enter costPerKM" />
											</div>
										</div>

										<div class="row">
											<label class="L-size control-label" for="Job_theft">.</label>
											<div class="I-size">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update" onclick="return validateTyreModeltype();"> <a class="btn btn-link"
														href="addModelSubType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty TyreModelSubType}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/VehicleTyreModelSubTypeAdd.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	
</div>