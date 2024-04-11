<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addPartMeasurementUnit.in"/>">New Part
							MeasurementUnit</a> / <span id="NewPartMeasurementUnit">Edit
							Part MeasurementUnit</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addPartMeasurementUnit.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty PartMeasurementUnit}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Part MeasurementUnit</h1>
								</div>
								<div class="panel-body">
									<form action="uploadPartMeasurementUnit.in" method="post"
										onsubmit="return validateStatusUpdate()">

										<input type="hidden" value="${PartMeasurementUnit.pmuid}"
											name="pmuid" />

										<div class="row">
											<label class="L-size control-label" id="Status">Name
												:</label>
											<div class="I-size">
												<input type="text" class="form-text"
													value="${PartMeasurementUnit.pmuName}" name="pmuName"
													placeholder="Enter Part Categorie" maxlength="50"
													id="pmuName" onkeypress="return IspmuName(event);"
													ondrop="return false;" required="required" /> <label
													id="errorpmuName" class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label">Symbol :</label>
											<div class="I-size">
												<input type="text" class="form-text" id="vParts"
													value="${PartMeasurementUnit.pmuSymbol}" name="pmuSymbol"
													placeholder="Enter Part Unit Symbol" maxlength="50"
													id="pmuSymbol" onkeypress="return IspmuSymbol(event);"
													ondrop="return false;" required="required" /> <label
													id="errorpmuSymbol" class="error"></label>
											</div>
										</div>
										
								<div class="row">
									<input type="hidden" id="needToConvert" value="${PartMeasurementUnit.needConversion}">
									<label class="L-size control-label">Need Conversion To :</label>
									<div class="I-size">
										<input type="checkbox" id="needConversion" onclick="showHideConvertionTo();"
											name="needConversion" /> 
									</div>
								</div>
								<div id="convertToDiv" class="row" style="display: none;">
									<label class="L-size control-label">Name :</label>
									<div class="I-size">
										<select class="form-text" name="convertTo" id="convertTo">
											<option value="${PartMeasurementUnit.convertTo}">${PartMeasurementUnit.convertToStr}</option>
											<c:forEach items="${measurementUnit}"
													var="measurementUnit">
												<option value="${measurementUnit.pmuid}">${measurementUnit.pmuName}</option>
											</c:forEach>
										</select>
									</div>
								</div><br/>
								<div id="convertRateDiv" class="row" style="display: none;">
									<label class="L-size control-label">Conversion Rate : </label>
									<div class="I-size">
										<input type="text" class="form-text" name="conversionRate" id="conversionRate"
										   onkeypress="return isNumberKeyWithDecimal(event,this.id);"  value="${PartMeasurementUnit.conversionRate}" />
									</div>
								</div>
										<br/>
										<div class="row">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="text optional form-text"
													name="pmudescription" rows="3" maxlength="150"
													onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">${PartMeasurementUnit.pmudescription}
				                        </textarea>
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>

										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addPartMeasurementUnit.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty PartMeasurementUnit}">
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
				<%@include file="../vehicle/masterSideMenu.jsp"%>
			</div>
		</div>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Parts.validate.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	</section>
</div>
