<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addPartMeasurementUnit.in"/>">New Part
							MeasurementUnit</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addPartMeasurementUnit" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddPartMeasurementUnit"> Add
								Part MeasurementUnit</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.savePartMeasurementUnit eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part MeasurementUnit Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadPartMeasurementUnit eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part MeasurementUnit Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deletePartMeasurementUnit eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part MeasurementUnit Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyPartMeasurementUnit eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part MeasurementUnit Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addPartMeasurementUnit" role="dialog">
			<div class="modal-dialog" style="width: 666px;">
				<div class="modal-content">
					<form action="savePartMeasurementUnit.in" method="post"
						name="vehicleStatu" onsubmit="return validateParts()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="PartMeasurementUnit">Part
								MeasurementUnit</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
								<div class="row1">
									<label class="L-size control-label">Name :</label>
									<div class="I-size">
										<input type="text" class="form-text" id="vParts"
											name="pmuName" placeholder="Enter Part Categorie"
											maxlength="50" id="pmuName"
											onkeypress="return IspmuName(event);" ondrop="return false;"
											required="required" /> <label id="errorpmuName"
											class="error"></label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Symbol :</label>
									<div class="I-size">
										<input type="text" class="form-text" id="vParts"
											name="pmuSymbol" placeholder="Enter Part Unit Symbol"
											maxlength="50" id="pmuSymbol"
											onkeypress="return IspmuSymbol(event);"
											ondrop="return false;" required="required" /> <label
											id="errorpmuSymbol" class="error"></label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Need Conversion To :</label>
									<div class="I-size">
										<input type="checkbox" id="needConversion" onclick="showHideConvertionTo();"
											name="needConversion" /> 
									</div>
								</div>
								<div id="convertToDiv" class="row1" style="display: none;">
									<label class="L-size control-label">Name :</label>
									<div class="I-size">
										<select class="form-text" name="convertTo" id="convertTo">
											<option value="0">Please select</option>
											<c:forEach items="${PartMeasurementUnit}"
													var="measurementUnit">
												<option value="${measurementUnit.pmuid}">${measurementUnit.pmuName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div id="convertRateDiv" class="row1" style="display: none;">
									<label class="L-size control-label">Conversion Rate : </label>
									<div class="I-size">
										<input type="text" class="form-text" name="conversionRate" id="conversionRate" placeholder="" 
											 onkeypress="return isNumberKeyWithDecimal(event,this.id);" />
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
											name="pmudescription" rows="2" maxlength="150"
											onkeypress="return IsVendorRemark(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorvParts" class="error"></label>
									</div>
								</div>
								<br>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return partMeasurementvalidate()">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
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
									<table id="PartMeasurementUnitTable"
										class="table table-hover table-striped">
										<thead>
											<tr>
												<th class="icon">Name</th>
												<th class="icon">Symbol</th>
												<th class="icon">Need Conversion</th>
												<th class="icon">Convert To</th>
												<th class="icon">Conversion Rate</th>
												<th class="icon">Description</th>
												<th id="Actions" class="icon">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty PartMeasurementUnit}">
												<c:forEach items="${PartMeasurementUnit}"
													var="PartMeasurementUnit">
													<tr>
														<td><c:out value="${PartMeasurementUnit.pmuName}" /></td>
														<td><c:out value="${PartMeasurementUnit.pmuSymbol}" /></td>
														<td><c:out value="${PartMeasurementUnit.needConversionStr}" /></td>
														<td><c:out value="${PartMeasurementUnit.convertToStr}" /></td>
														<td><c:out value="${PartMeasurementUnit.conversionRate}" /></td>
														<td><c:out
																value="${PartMeasurementUnit.pmudescription}" /></td>
														
														<td>
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="editPartMeasurementUnit.in?pmuid=${PartMeasurementUnit.pmuid}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deletePartMeasurementUnit.in?pmuid=${PartMeasurementUnit.pmuid}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Part MeasurementUnit?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Parts.validate.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>		
	</section>
</div>