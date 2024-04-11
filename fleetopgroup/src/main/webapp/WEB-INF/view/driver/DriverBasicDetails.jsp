<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/getDriversList"/>">Driver</a> / 
					<a href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>">
						<c:out value="${driver.driver_firstname} " /> 
						<c:out value="${driver.driver_Lastname}" /></a> / 
						<span>Driver Basic Details</span>
				</div>
				<div class="pull-right">
				<input type="hidden" id="driverStatusId" value="${driver.driverStatusId}">
				<c:if test="${driver.driverStatusId != 6}">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-default" href="editDriver.in?driver_id=${driver.driver_id}"> 
						<i class="fa fa-pencil"></i> Edit Driver
						</a>

					</sec:authorize>
					<a class="btn btn-success btn-sm" data-toggle="modal" data-target="#addBasicDetails">
						 <i class="fa fa-plus"></i> Add Basic Details
					</a>
				</c:if>	
					<sec:authorize access="hasAuthority('PRINT_DRIVER')">
						<a href="PrintDriverFamily?id=${driver.driver_id}" target="_blank" class="btn btn-default btn-sm">
							<i class="fa fa-print"></i> Print
						</a>
					</sec:authorize>
					
					<a class="btn btn-link" href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer="" data-type="image" data-toggle="lightbox"> 
							<img src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
								class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> 
							<c:out value="${driver.driver_firstname}" /> 
							<c:out value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>
									<span class="fa fa-black-tie" aria-hidden="true" data-toggle="tip" data-original-title="Job Role"> 
									</span>
									<span class="text-muted">
										<c:out value="${driver.driver_jobtitle}" />
									</span>
								</li>
								<li>
									<span class="fa fa-users" aria-hidden="true" data-toggle="tip" data-original-title="Group Service"> 
									</span>
									<a href=""><c:out value="${driver.driver_group}" /></a>
								</li>
								<li>
									<span class="fa fa-empire" aria-hidden="true" data-toggle="tip" data-original-title="Emp Number"> 
									</span> 
									<span class="text-muted">
										<c:out value="${driver.driver_empnumber}" />
									</span>
								</li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<div class="modal fade" id="addBasicDetails" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Driver Basic Details</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" > Type Of Details :</label>
							<div class="I-size">
								<input type="hidden" class="select" id="addDetailTypeId" style="width:100%">
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" >Quantity :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="addQuantity"
									maxlength="249"  onkeypress="return isNumberKey(event);"
									placeholder="Enter Quantity" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label">Assign Date :</label>
							<div class="I-size">
								<div class="input-group input-append date" id="StartDate">
									<input type="text" class="form-text left-margin" id="addAssignDate" maxlength="10"
										data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
									<span class="input-group-addon add-on">
										<span class="fa fa-calendar"></span>
									</span>
								</div>
								<span id="joinDateIcon" class=""></span>
								<div id="joinDateErrorMsg" class="text-danger"></div>
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" >Remark :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="addRemark"
									maxlength="249" name="description"
									placeholder="Enter Remark" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="saveBasicDetails" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="editBasicDetails" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Driver Basic Details</h4>
					</div>
					<div class="modal-body">
						<div class="row">
								<input type="hidden"  id="driverBasicDetailsId">
							<label class="L-size control-label"> Type Of Details :</label>
							<div class="I-size">
								<input type="hidden" class="select" id="editDetailTypeId" style="width:100%">
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" >Quantity :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editQuantity"
									maxlength="249" onkeypress="return isNumberKey(event);"
									placeholder="Enter Quantity" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Assign Date :</label>
							<div class="I-size">
								<div class="input-group input-append date" id="StartDate1">
									<input type="text" class="form-text left-margin" id="editAssignDate" maxlength="10"
										data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
									<span class="input-group-addon add-on">
										<span class="fa fa-calendar"></span>
									</span>
								</div>
								<span id="joinDateIcon" class=""></span>
								<div id="joinDateErrorMsg" class="text-danger"></div>
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" >Remark :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editRemark"
									maxlength="249" name="description"
									placeholder="Enter Remark" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="updateDriverBasicDetials" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
	<input type="hidden" id="driverId" value="${driver.driver_id}">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table class="table">
									<thead>
										<tr>
											<th class="fit ar">Sr No</th>
											<th class="fit ar">Detail Type</th>
											<th class="fit ar">Quantity</th>
											<th class="fit ar">Assign Date</th>
											<th class="fit ar">Remark</th>
											<th class="fit ar">Action</th>
										</tr>
									</thead>
									<tbody id="driverBasicDetailsTable">
									
									</tbody>
				
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="DriverSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/driverBasicDetails.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	
</div>