<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> /
					<span id="AllVehicl">Open Work Orders</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">WO-</span></span>
							<input type="hidden" id="showSubLocation" value="${showSubLocation}">
							<input type="hidden" id="companyId" value="${companyId}">
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="WO-NO eg:6878"> <span
								class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachWoByNumber();" class="btn btn-success btn-sm">
								<i class="fa fa-search"></i>	
								</button>
							</span>
						</div>
					</div>
					
					<sec:authorize access="hasAuthority('ADD_WORKORDER')">
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/createWorkOrder?issue=0,0"/>"> <span
							class="fa fa-plus"></span> Create Work Order
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/searchWorkOrderByData.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('JOB_CARD_PRINT')">
						<a class="btn btn-info btn-sm"
							href="javascript:void(0)" onclick="showPrintIssueModule();"> <span
							class="fa fa-print"></span> Job Card Print
						</a>
				    </sec:authorize>
				    <sec:authorize access="hasAuthority('JOB_CARD_SERVICE_REMINDER_PRINT')">
						<a class="btn btn-info btn-sm"
							href="javascript:void(0)" onclick="showPrintServiceRemiderModule();"> <span
							class="fa fa-print"></span> Service Reminder Card Print
						</a>
				    </sec:authorize>
				    
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			
			<div class="row" id="woDetails">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-file-text-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text"> <span id="woStatus"> </span> Work Orders</span>
							<span id="count" class="info-box-number"></span> 
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Work Orders</span>
							<div class="input-group">
								<input class="form-text" id="searchByDifferentFilter"
									name="Search" type="text" required="required"
									placeholder="WO-NO, Vehicle"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn" onclick="return searchWOByDifferentFilter();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row" id="tabsHeading">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
							<input type="hidden" id="showDocumentColumn" value="${configuration.showDocumentColumn}">
							<input type="hidden" id="showIssueSummary" value="${configuration.showIssueSummaryInList}">
							<input type="hidden" id="showTaskInList" value="${configuration.showTaskInList}">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="open"><a
										onclick="showList(1,1)">OPEN <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="Open WorkOrder Count" id="openWOCount"></span></a></li>
									<li role="presentation" id="inProcess"><a
										onclick="showList(2,1)">IN PROCESS <span
										data-toggle="tip" title="" class="badge bg-yellow"
										data-original-title="In Process WorkOrder Count" id="inProressWOCount"></span></a></li>
									<li role="presentation" id="onHold"><a
										onclick="showList(3,1)">ON HOLD <span
											data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="On Hold WorkOrder Count" id="onHoldWOCount"></span></a></li>
									<li role="presentation" id="completed"><a
										onclick="showList(4,1)">COMPLETED <span
											data-toggle="tip" title="" class="badge bg-yellow"
											data-original-title="Completed WorkOrder Count" id="completedWOCount"></span></a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row" id="tabsData">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<input type="hidden" id="startPage" value="${SelectPage}"> 
								<table id="VendorPaymentTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
						</ul>
					</div>
				</div>
			</div>
			
			<div class="row hide filterByDifferentData">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<input type="hidden" id="startPage" value="${SelectPage}"> 
								<table id="VendorPaymentTable1" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>		
				<div class="modal fade" id="taskDetails" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">WorkOrder Tasks :</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="taskTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
				</div>
						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="issuePrintModal" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">Job Card Print</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
					<div class="box">
						<div class="box-body">
										<div class="row1 form-group" id="grpvehicleName">
											<label class="L-size control-label" for="select3">Vehicle
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="hidden" id="select3" name="vehicle_vid"
														style="width: 100%;" required="required" onchange="checkIssueDetail()"
														placeholder="Please Enter 2 or more Vehicle Name" /> <span
														id="vehicleNameIcon" class=""></span>
												</div>
												
											</div>
										</div>
										<br>
											<div class="row1" id="issueDiv">
												<label class="L-size control-label" for="issue">Issue Details</label>
												<div class="I-size">
													 <select id="issue" style="width: 75%;" multiple="multiple">
													 	<option value="-1"></option>
													 </select>
												</div>
											</div>
						</div>
					</div>
				</div>
						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success" onclick="printJobCard()">
								<em class="fa fa-print"></em><span>Print</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
	
		<!-- service Reminder Print modal starts here -->
		<div class="modal fade" id="serviceReminderPrintModal" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">Service Reminder Print</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
						<div class="box">
							<div class="box-body">
									<div class="row1 form-group" id="grpvehicleName">
										<label class="L-size control-label" for="serviceReminderVehicle">Vehicle
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="col-md-9">
												<input type="hidden" id="serviceReminderVehicle" name="vehicle_vid"
													style="width: 100%;" required="required"
													placeholder="Please Enter 2 or more Vehicle Name" /> <span
													id="vehicleNameIcon" class=""></span>
											</div>
											
										</div>
									</div>
									<br>
									<div class="row1" id="serviceReminder">
										<label class="L-size control-label" for="ServiceReminderInput">Service Reminder Details</label>
										<div class="I-size">
											 <input type="hidden" id="ServiceReminderInput" onchange="getServiceSecheduleList2(this);"
												name="service_id" style="width: 100%;"/>
											 </select>
										</div>
									</div>
							</div>
						</div>
						</div>
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-success" onclick="printJobCard2()">
							<em class="fa fa-print"></em><span>Print</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- service Reminder Print modal starts here -->
		</sec:authorize>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/ViewWorkOrder.js" />"></script>	
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
			<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/workOrderUtility.js" />"></script>	
		
</div>
