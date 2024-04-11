<%@ include file="../../taglib.jsp"%>

<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addBatteryType.in"/>">New Battery Model</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addJobSubtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobSubType"> Create Battery
								Model</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saved eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.costUpdated eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Cost Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Type Already Exists.
			</div>
		</c:if>

		<div class="modal fade" id="addJobSubtypes" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<form action="saveBatteryType" method="post"
						>
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobSubType">Battery Model</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
								<div class="row1">
									<label class="L-size control-label">Manufacturers Type :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<select class="form-text selectType select2" name="batteryManufacturerId"
											style="width: 100%;" id="selectReType" required="required">
											<c:forEach items="${manufacturer}" var="manufacturer">
												<option value="${manufacturer.batteryManufacturerId}">${manufacturer.manufacturerName}
												</option>
											</c:forEach>
										</select> <label id="errorReType" class="error"></label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Battery Model :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" id="batteryType"
											name="batteryType" maxlength="150"
											placeholder="Enter Type Name" /> <label id="errorSubReType"
											class="error"></label>
									</div>
								</div>
								
								<c:if test="${configuration.showBatteryPartNumber}">
								<div class="row1">
									<label class="L-size control-label">Battery Part Number :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" id="partNumber"
											name="partNumber" maxlength="150"
											placeholder="Enter Type Name" /> <label id="errorSubReType"
											class="error"></label>
									</div>
								</div>
								</c:if>
								
								<div class="row1">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<input type="text" class="form-text" id="SubReType"
											name="description" maxlength="249"
											placeholder="Enter description" /> <label id="errorSubReType"
											class="error"></label>
									</div>
								</div>
								<div class="row1" id="grptimeInterval" class="form-group">
												<label class="L-size control-label" for="time_interval">Battery
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
									<div class="row1">
											<label class="L-size control-label">warranty terms :</label>
											<div class="I-size">
												<textarea class="form-text" name="warrentyterm" rows="3"
													maxlength="1000" id="warrentyterm" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;"> 
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>

							</div>
						</div>
						<div class="modal-footer">
							<div class="row1">
								<button type="submit" onclick="return batteryModelValidate()" class="btn btn-primary">
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
					<form action="editCostPerDay.in" method="post"
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
									<label class="L-size control-label">Cost Per Day (Approx.) :</label>
									<div class="I-size">
									<input type="hidden" id="vehicleCostFixingId" name="vehicleCostFixingId" />
									<input type="hidden" id="batteryTypeId" name="batteryTypeId" />
										<input type="number" step="any" class="form-text" id="costPerDayEdit"
											name="costPerDay" maxlength="249"
											placeholder="Enter costPerDay" />
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<div class="row1">
								<button type="submit" class="btn btn-primary" >
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
									<c:if test="${!empty batteryType}">
										<table id="SubTypeTable"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th id="TypeName" class="icon">Manufacturers Name</th>
													<th class="icon">Battery Model</th>
													<c:if test="${configuration.showBatteryPartNumber}">
													<th class="icon">Battery Part Number</th>
													</c:if>
													
													<th class="icon">Description</th>
												
													<th class="icon">Warranty</th>
													<th class="icon">Warrenty Term</th>
													<th class="icon">Cost Per Day</th>
													<!-- <th id ="Action" class="icon">Action</th> -->
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${batteryType}"
													var="batteryType">
													<tr>
														<td><c:out value="${batteryType.manufacturerName}" /></td>
														
														<td><c:out
																value="${batteryType.batteryType}" /></td>
																<c:if test="${configuration.showBatteryPartNumber}">
														
														<td><c:out
																value="${batteryType.partNumber}" /></td>
																</c:if>
														<td><c:out
																value="${batteryType.description}" /></td>
														<td><c:out
																value="${batteryType.warrantyPeriod} - ${batteryType.warrantyType}" />
														</td>
														
														<td><c:out
																value="${batteryType.warrentyterm} " />
														</td>
														<td><c:out
																value="${batteryType.costPerDay} " />
															<a href="#" onclick="editPerKmCost(${batteryType.costPerDay}, ${batteryType.vehicleCostFixingId}, ${batteryType.batteryTypeId });">&emsp;Edit Cost</a>
														</td>
																
														<%-- <td>
															<div class="btn-group">
																<a class="btn btn-Link dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="<c:url value="editbatteryType.in?batteryTypeId=${batteryType.batteryTypeId}"/>">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																			
																		</sec:authorize></li>
																	
																</ul>
															</div>
														</td> --%>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty batteryType}">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	
		function batteryModelValidate(){
			if($("#batteryType").val() == undefined || ($("#batteryType").val()).trim() == "" ){
				showMessage('info','Please Enter Battery Model')
				return false;
			
			return true;
		}
		}
		function editPerKmCost(cost, vehicleCostFixingId, batteryTypeId){
			$('#editCost').modal('show');
			$('#vehicleCostFixingId').val(vehicleCostFixingId);
			$('#costPerDayEdit').val(cost);
			$('#batteryTypeId').val(batteryTypeId);
		}
	</script>
</div>