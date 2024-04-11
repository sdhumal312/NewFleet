<%@page import="ch.qos.logback.classic.Logger"%>
<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/ViewInspectionSheet"/>">Inspection Sheet
					</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a href="AddInspectionSheet.in" class="btn btn-success"> <span
							class="fa fa-plus" id="AddJobType"> Create New Inspection
								Sheet</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saved eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Vehicle Inspection Sheet Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.delete eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Vehicle Inspection Sheet Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.duplicate eq true}">
			<div class="alert alert-info">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Duplicate Vehicle Inspection Sheet Not Saved !
			</div>
		</c:if>
		<c:if test="${param.error eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Vehicle Inspection Sheet Not Saved Some Error Occurred !
			</div>
		</c:if>
		<c:if test="${param.vehicleAssigned eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Vehicle Inspection Sheet is Assigned To Vehicle Please Remove It
				First !
			</div>
		</c:if>
		<c:if test="${param.InspectionsheetHavingParameters eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Please Remove Parameters First To delete InspectionSheet !
			</div>
		</c:if>

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
									<c:if test="${!empty inspectionSheetList}">
										<table id="typeTable"
											class="table table-bordered table-striped">

											<thead>
												<tr>
													<th id="TypeName" class="icon">Inspection Sheet Name</th>
													<c:if test="${configuration.sheetToVehicleType}">
													<th id="Usage" class="icon">Applicable Vehicle Type</th>
													</c:if>
														<c:if test="${!configuration.sheetToVehicleType}">
													<th id="Usage" class="icon">Group Applicable</th>
													</c:if>
													<th id="Usage" class="icon">View</th>
													<th id="Usage" class="icon">Delete</th>
													<th id="Usage" class="icon">Assigned To No Of Vehicle</th>
													<sec:authorize access="hasAuthority('VEHICLE_INSPECTION_SHEET_EDIT')">
													<th class="actions" class="fit ar">Actions</th>
													</sec:authorize>
													<!-- <th id ="Action" class="icon">Action</th> -->
												</tr>
											</thead>
											<tbody>

												<c:forEach items="${inspectionSheetList}"
													var="inspectionSheetList">
													<tr>
														<td><c:out
																value="${inspectionSheetList.inspectionSheetName}" /></td>
														<td><c:out
																value="${inspectionSheetList.vehicleGroup}" /></td>
														<td><a
															href="showInspectionSheetDetails.in?vehicleInspectionSheetId=${inspectionSheetList.vehicleInspectionSheetId}">View</a></td>
														<td><a
															href="deleteInspectionSheet.in?ID=${inspectionSheetList.vehicleInspectionSheetId}">Delete</a></td>
														<td ><a data-toggle="modal" href="#" onclick ="showVehicles(${inspectionSheetList.noOfVehicleAsigned},${inspectionSheetList.vehicleInspectionSheetId})">
														<c:out value="${inspectionSheetList.noOfVehicleAsigned}" /></a></td>
														<sec:authorize access="hasAuthority('VEHICLE_INSPECTION_SHEET_EDIT')">
														<td class="fit ar">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"><span
																	class="fa fa-ellipsis-v"></span> </a>
																<ul class="dropdown-menu pull-right">
																		<c:if test="${configuration.sheetToVehicleType}">
																		<li><a
																			href="<c:url value="/AssignInspectionSheetToVehicleType.in?ISID_id=${inspectionSheetList.vehicleInspectionSheetId}"/>">
																				<span class="fa fa-tasks"></span> Assign
																		</a></li>
																		</c:if>
																		<li><a
																			href="<c:url value="/EditInspectionSheet.in?ISID_id=${inspectionSheetList.vehicleInspectionSheetId}"/>">
																				<span class="fa fa-pencil"></span> Edit
																		</a></li>
																	</ul>
															</div>
														</td>
														</sec:authorize>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty inspectionSheetList}">
										<div class="main-body">
											<p class="lead text-muted text-center t-padded">
												<spring:message code="label.master.noresilts" />
											</p>

										</div>
									</c:if>
								</div>
							</div>	
							<!--Pop Up Logic Start-->
							<div class="modal fade" id="myModal" role="dialog">
								<div class="modal-dialog modal-md">
									<input type="hidden" id="  " name="  " value=" " />
									<div class="modal-content">
										<div class="modal-body">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<div class="row invoice-info" id="reportHeader"												
												style="font-size: 15px; font-weight: bold;"></div>
										</div>
										<div class="row invoice-info">
											<table id="inspectNoOfVehicleToSheet" style="width: 100%;"
																class="table-hover table-bordered">
												
											</table>
										</div>
									</div>
									
									
								</div>
							</div>
							<!--Pop Up Logic End-->
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VIP/VehicleInspectionParameterAdd.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
</div>