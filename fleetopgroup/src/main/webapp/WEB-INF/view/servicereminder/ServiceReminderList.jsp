<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="ViewServiceReminderList.in"> Service
						Reminder</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">S-</span></span>
								<input class="form-text" id="serviceNumber"
									name="Search" type="number" min="1" required="required"
									placeholder="S-ID eg:3245" onkeyup="searchServiceByNumberOnEnter(event);"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn" onclick="searchServiceReminderByNumber();"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
					</div>
					<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addServiceReminderEntry.in"/>"> <span
							class="fa fa-plus"></span> Add Multiple Service Reminders
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/ServiceReminderReport.in"/>"> <span
							class="fa fa-search"></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER_CALENDAR')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/ShowServiceReminderCalender.in"/>"> <span
							class="fa fa-calendar"></span> Service Reminder Calendar
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
			<div class="row">
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-bell-slash"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total</span> <a data-toggle="tip"
								data-original-title="Total Service Reminder Count"
								href="#" onclick="showList(1);"><span
								class="info-box-number" id="allServiceCount"></span></a>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-red"><i
							class="fa fa-bell-slash"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">TODAY</span> <a data-toggle="tip"
								data-original-title="Click Today Details"
								href="#" onclick="showTodaysOverDueServiceList(1);"><span
								class="info-box-number" id="overDueTodayCount"></span></a>
						</div>
					</div>
				</div>
			
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-orange"><i
							class="fa fa-volume-up"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">UPCOMING</span> <a data-toggle="tip"
								data-original-title="Click OverDue Details"
								href="#" onclick="showUpcomingOverDueServiceList(1);"><span
								class="info-box-number" id="overUpcomingDueCount"></span></a>
						</div>
					</div>
				</div>
				
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-red"><i
							class="fa fa-bell-slash"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Overdue</span> <a data-toggle="tip"
								data-original-title="Click OverDue Details"
								href="#" onclick="showOverDueServiceList(1);"><span
								class="info-box-number" id="overeDueCount"></span></a>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-orange"><i
							class="fa fa-volume-up"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Due Soon</span> <a data-toggle="tip"
								data-original-title="Click DueSoon Details"
								href="#" onclick="showDueSoonServiceList();"><span
								class="info-box-number" id="DueSoonCount"></span></a>

						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Service</span>
								<div class="input-group">
									<input class="form-text" id="serviceMultipleSearch" onkeyup="searchServiceByMultipleOnEnter(event);"
										name="Search" type="text" min="1" required="required"
										placeholder="S-ID, Ven-No, Tash"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm" onclick="searchServiceReminderByMultiple();">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
		<div class="row" id="tabsHeading">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
						
					</sec:authorize>
				</div>
			</div>
		</div>
		
		<div class="row" id="tabsData">
			<div class="main-body">
					<h4 id="dataType">Recent Service Reminder</h4>
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<input type="hidden" id="startPage" value="${SelectPage}"> 
							<input type="hidden" id="companyId" value="${companyId}">
							<input type="hidden" id="userId" value="${userId}">
							<input type="hidden" id="createWoOnService" value="${createWoOnService}">
							<input type="hidden" id="editServiceReminder" value="${editServiceReminder}">
							<input type="hidden" id="deleteServiceReminder" value="${deleteServiceReminder}">
							<input type="hidden" id="skipServiceReminder" value="${skipServiceReminder}">
							<input type="hidden" id="createDSEOnService" value="${createDSEOnService}">
							<input type="hidden" id="groupListByVehicleNumber" value="${configuration.groupListByVehicleNumber}">
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

		<div class="row hide filterByDateData">
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
		<div class="row hide filterByDifferentData">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<input type="hidden" id="startPage" value="${SelectPage}"> 
							<table id="VendorPaymentTable2" class="table table-hover table-bordered">
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
				
		<div class="modal fade" id="vehicleReminder" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1276px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Vehicle Service Reminder</h4>
					</div>
					<div class="modal-body">
					
					<fieldset>
					<div class="box">
					<table class="table" id="vehicleReminderTab" border="1" width="100%">
						<thead>
											<tr>
												<th>NO.</th>
												<th>Vehicle</th>
												<th>Service Task And Schedule</th>
												<th>Next Due</th>
												<th>Current Odometer</th>
												<th>Meter Threshold</th>
												<th>DueSoon Odometer</th>
												<th>Service Odometer</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody id="vehiclereminderbody">
										
										</tbody>
					</table>
					</div>
					</fieldset>
					<br />
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default"
					data-dismiss="modal">
					<span id="Close">Close</span>
					</button>
					</div>
					</div>
					</div>
			</div>
			
				<div class="modal fade" id="skipSrRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Skip Remark</h4><br><samp style="color: blue;">Service reminder will be skipped and rescheduled .</samp>
						</div>
						<div class="modal-body">
						<input type="hidden" id="skipSrId" >
					
							<div class="row1">
											<label class="L-size control-label" for="skipRemark">Remark
												</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="skipRemark" name="skipRemark"
																rows="3" maxlength="1000"></textarea>
											</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="skipServiceReminder();" id="btnSubmit" class="btn btn-primary">
								<span>Skip SR</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
	</section>
	<input type="hidden" value="1" id="statues">
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/ViewServiceReminderList.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/ServiceReminderUtility.js" />"></script>
	
</div>
