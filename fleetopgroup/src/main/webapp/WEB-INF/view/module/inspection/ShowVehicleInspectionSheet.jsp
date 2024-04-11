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
						<a href="AddInspectionSheet.in" class="btn btn-success" >
							<span class="fa fa-plus" id="AddJobType"> Create New Inspection Sheet</span>
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
		
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						
								<%-- <div class="box-body">
									<table class="table" style="width: 60%">
										<thead>
											
										</thead>
										<tbody>
											<tr>
												<td>Inspection Sheet Name :</td>
												<td>${inspectionSheet.inspectionSheetName}</td>
											</tr>
											<tr>
												<td>Groups Applicable :</td>
												<td>${inspectionSheet.vehicleGroup}</td>
											</tr>
										</tbody>
									</table>
									
								</div> <br/><br/> --%>
							
		<section class="content-header" style="padding: 0px 15px 0;">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<!-- Show the User Profile -->
						
						<div class="col-md-10 col-sm-8 col-xs-7">
							<h4 class="secondary-header-title">
									
									Inspection Sheet Name : <a href="#"> <span style="font-size: 16px;"> ${inspectionSheet.inspectionSheetName} </span> </a>
							</h4>
							<h4 class="secondary-header-title">
								<c:if test="${!configuration.sheetToVehicleType}">
									Applicable Groups</c:if>
									<c:if test="${configuration.sheetToVehicleType}">
									Applicable Vehicle Types</c:if> <span style="font-size: 16px; padding-left: 5px;">:</span> <a href="#"> <span style="font-size: 16px;"> ${inspectionSheet.vehicleGroup} </span> </a>
							</h4>
							<h4 class="secondary-header-title">
								
									No Of Vehicle Assigned <span style="font-size: 16px; padding-left: 5px;">:</span> <a href="#"> <span style="font-size: 16px;"> ${inspectionSheet.noOfVehicleAsigned} </span> </a>
							</h4>
							
						</div>
					</div>

				</div>
			</div>
						
						
						<c:if test="${configuration.sheetToVehicleType}">
								<div class="box-body">
								<div class="table-responsive">
									<c:if test="${!empty vehicleInspectionSheetDtoList}">
										<table id="typeTable"
											class="table table-bordered table-striped">

											<thead style="background-color: aqua;">
												<tr>
													<th class="icon">Branch</th>
													<th class="icon">Vehicle Type</th>
													<th class="icon">Assign By</th>
													<th class="icon">Assign Date</th>
													<th class="icon">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${vehicleInspectionSheetDtoList}" var="vehicleInspectionSheetDto">
													<tr>
														<td><c:out value="${vehicleInspectionSheetDto.branchName}" /></td>
														<td><c:out value="${vehicleInspectionSheetDto.vehicleType}" /></td>
														<td><c:out value="${vehicleInspectionSheetDto.assignByName}" /></td>
														<td><c:out value="${vehicleInspectionSheetDto.assignOnStr}" /></td>
														<td> <a class="fa fa-remove" href="#" style="color: red" onclick="removevehicleType(${inspectionSheet.vehicleInspectionSheetId},${vehicleInspectionSheetDto.vehicleTypeId},${vehicleInspectionSheetDto.branchId})">Remove</a> </td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty vehicleInspectionSheetDtoList}">
										<div class="main-body">
											<p class="lead text-muted text-center t-padded">
												<spring:message code="label.master.noresilts" />
											</p>
										</div>
									</c:if>
								</div>
							</div>
						</c:if>
						
						
						
						
							<div class="box-body">
								<div class="table-responsive">
									<c:if test="${!empty sheetToParameterDtoList}">
										<table id="typeTable"
											class="table table-bordered table-striped">

											<thead style="background-color: aqua;">
												<tr>
													<th class="icon">Inspection Parameter Name</th>
													<th class="icon">Frequency (In Days)</th>
													<th class="icon">Is Mandatory</th>
													<th class="icon">Photo Required</th>
													<th class="icon">Text Required</th>
												</tr>
											</thead>
											<tbody>
												
												<c:forEach items="${sheetToParameterDtoList}" var="sheetToParameterDtoList">
													<tr>
														<td><c:out value="${sheetToParameterDtoList.parameterName}" /></td>
														<td><c:out value="${sheetToParameterDtoList.frequency}" /></td>
														<td><c:out value="${sheetToParameterDtoList.mandatoryText}" /></td>
														<td><c:out value="${sheetToParameterDtoList.photoRequiredText}" /></td>
														<td><c:out value="${sheetToParameterDtoList.textRequiredText}" /></td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty sheetToParameterDtoList}">
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
</div>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/error/error.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/showVehicleInspectionSheet.js" />"></script>