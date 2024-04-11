<%@ include file="taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<div class="content-wrapper">
	<section class="content">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="serviceProgram.in">Service Program</a>
					/ <span>View Service Program</span>
				</div>
				<div class="pull-right">
					<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_SERVICE_PROGRAM"))) {
						%>
					  		<a  onclick="openServiceAsignPopup();" class="btn btn-info btn-sm" href="#">Asign Service Program</a>
					  		<a style="display: none;" id="servicePopup" onclick="openServiceSchedulePopup();" class="btn btn-success btn-sm" href="#">Add Service Schedule</a>
					<% } %>
					
					<a href="serviceProgram.in">Cancel</a>
				</div>
				
			</div>
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_PROGRAM')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_PROGRAM')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<br/>
						<h4>Vehicle Service Program Details</h4>
						<input type="hidden" id="vehicleServiceProgramId" value="${vehicleServiceProgramId}"/>
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="userId" value="${userId}">
						<input type="hidden" id="deleteServiceShedule" value="${deleteServiceShedule}">
						<input type="hidden" id="deleteServiceAssignment" value="${deleteServiceAssignment}">
						<input type="hidden" id="vehicleBranchWiseProgramConfig" value="${configuration.vehicleBranchWiseProgram}">
					
						<br/>
						<h4 id="vendorinfo" align="center">
						</h4>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="row">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="text-align: left;">
					<div class="col-md-6 col-sm-6 col-xs-11">
					
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row" >
											<th class="key">Service Program Name :</th>
											<td class="value" id="programName"></td>
										</tr>
										<tr class="row">
											<th class="key">Description :</th>
											<td class="value" id="descrption"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-5 col-sm-6 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Created By :</th>
											<td class="value" id="createdBy"></td>
										</tr>
										<tr class="row">
											<th class="key">Created On:</th>
											<td class="value" id="createdOn"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
				</div>
				<fieldset>
				
				<div class="modal fade" id="serviceSchedulePopUp" role="dialog">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Service Schedule details</h4>
									</div>
									<div class="modal-body" id="modelBody">
										<div class="row1" id="grprenewalType" class="form-group">
													<label class="L-size control-label" for="from">Service
														Jobs <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="from" name="serviceTypeId"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Job Name" /> <span
															id="renewalTypeIcon" class=""></span>
														<div id="renewalTypeErrorMsg" class="text-danger"></div>
														<p class="help-block">Select an existing Service Jobs</p>
													</div>
										</div>
										<br/>
											<div class="row1" id="grprenewalSubType" class="form-group">
													<label class="L-size control-label" for='to'>Service
														Sub Jobs <abbr title="required">*</abbr>
													</label>
	
													<div class="I-size">
														<select style="width: 100%;" name="serviceSubTypeId" id='to'>
	
														</select> <span id="renewalSubTypeIcon" class=""></span>
														<div id="renewalSubTypeErrorMsg" class="text-danger"></div>
														<p class="help-block">Select an existing Service Sub
															Jobs</p>
													</div>
											</div>
											<br/>
											<div class="row1" id="grpmeterInterval" class="form-group">
												<label class="L-size control-label" for="meter_interval">Meter
													Interval <abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<input type="number" class="form-text"
														name="meter_interval" min="0" maxlength="6" 
														id="meter_interval"
														onkeypress="return Ismeter_interval(event);"
														ondrop="return false;"> 
													<p class="help-block">Repeat based on usage(e.g. Oil
														Change every 5000 miles). Leave blank if you don't want to
														use this option. Sub Types</p>
												</div>
											</div>
											<div class="row1" id="grptimeInterval" class="form-group">
												<label class="L-size control-label" for="time_interval">Time
													Interval <abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<div class="col-md-4">
														<input type="number" class="form-text"
															name="time_interval" min="0" maxlength="2" 
															id="time_interval"
															onkeypress="return Istime_interval(event);"
															ondrop="return false;"> 
														<p class="help-block">(e.g. every 1 month)</p>
													
													</div>
													<div class="col-md-4">
														<select class="form-text" id="time_intervalperiod"
															name="time_intervalperiodId"
															onchange="OnChangeDueThreshold(this)" required="required">
															<option value="1">day(s)</option>
															<option value="2">Week(s)</option>
															<option value="3">Month(s)</option>
															<option value="4">Year(s)</option>
														</select>
													</div>

												</div>
											</div>
											<div class="row1" id="grpmeterThreshold" class="form-group">
												<label class="L-size control-label"
													for="renewal_timethreshold">Due Meter Threshold <abbr
													title="required">*</abbr>
												</label>

												<div class="I-size">
													<input type="number" class="form-text"
														name="meter_threshold" maxlength="6" min="0" value="0"
														id="meter_threshold"
														onkeypress="return Ismeter_threshold(event);"
														ondrop="return false;"> 
													<p class="help-block">Number of miles/km/hours in
														advance you consider this reminder to be "due soon" (ex:
														500 miles is common for a typical fleet vehicle)</p>
												</div>
											</div>


											<div class="row1" id="grptimeThreshold" class="form-group">

												<label class="L-size control-label" for="time_threshold">Due
													Time Threshold <abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<div class="col-md-4">
														<input type="number" class="form-text"
															name="time_threshold" min="0" maxlength="2" value="0"
															id="time_threshold"
															onkeypress="return Istime_threshold(event);"
															ondrop="return false;"> 
														<p class="help-block">(e.g. 7 Days before)</p>
													</div>
													<div class="col-md-4">
														<select class="form-text" id="time_thresholdperiod"
															name="time_thresholdperiodId"
															onchange="OnChangeDueThreshold(this)" required="required">
															<option value="1">day(s)</option>
															<option value="2">Week(s)</option>
															<option value="3">Month(s)</option>
															<option value="4">Year(s)</option>
														</select>
													</div>

												</div>
											</div>
											
											<div class="row1" id="grpmeterThreshold" class="form-group">
												<label class="L-size control-label"
													for="user">Subscribed Users :  <abbr
													title="required">*</abbr>
												</label>

												<div class="I-size">
													<div class="col-md-9 inputGroupContainer">
						                               <div class="input-group" >
						                               <span class="input-group-addon">
						                              	 <i class="fa fa-user"></i>
						                               </span>
						                              	 <input class="" placeholder="Subscribe users"
															id="subscribe" type="hidden" style="width: 100%"
															name="service_subScribedUserId"
															onkeypress="return Isservice_subscribeduser(event);"
															required="required" ondrop="return false;"> 
														<p class="help-block">The users you select will get an
															email when this reminder is due soon or overdue</p>
						                               </div>
					                            	</div>
												</div>
											</div>
											<br/><br/>
											
											<div class="row1" id="grprenewalSubType" class="form-group">
													<label class="L-size control-label" for='to'>Service
														Type <abbr title="required">*</abbr>
													</label>
	
													<div class="I-size">
														<select style="width: 100%;" name="serviceTypeIds" id='serviceTypeIds'>
															<option value="1">OPTIONAL</option>
															<option value="2">MANDATORY</option>
														</select> 
													</div>
											</div>
											<br/><br/>
										
											<div class="row1" style="padding-top: 50px;" class="form-group">
												<label class="L-size control-label" for="time_threshold">
												</label>
												<div class="I-size">
													<button type="submit" id="saveSchedule" onclick="return saveServiceProramSchedule();" class="btn btn-primary">
														Save
													</button>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">
														<span id="Close">Close</span>
													</button>
												</div>
											</div>
											
								</div>
									<div class="modal-footer">
												
									</div>
							</div>

						</div>
					</div>
					
					<div class="modal fade" id="serviceAsignPopup" role="dialog">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Asign Service Program</h4>
									</div>
									<div class="modal-body" id="modelBody">
									
										<table class="table">
											<thead>
												<tr>
													<td>Sr.</td>
													<td>Vehicle Type</td>
													<td>Vehicle Modal</td>
													<c:if test="${configuration.vehicleBranchWiseProgram}">
													<td>Vehicle Branch</td>
													</c:if>
													<% 
														if(permission.contains(new SimpleGrantedAuthority("DELETE_SERVICE_ASSIGNMENT"))) {
													 %>
													  		<td>Action</td>
													<% } %>
												</tr>
											</thead>
											<tbody id="aissgnedBody">
												
											</tbody>
										</table>
									
										<div class="row1" id="grprenewalType" class="form-group">
													<label class="L-size control-label" for="from">Vehicle Type
														 <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
															
														<select class="select2" id="vehicleType" name="vehicleType" onchange="getVehicleListForCreateServiceProgram();"
															style="width: 100%;">
														</select>	
													</div>
										</div>
										<br/><br/>
											<div class="row1" id="grprenewalSubType" class="form-group">
													<label class="L-size control-label" for='to'>Vehicle Model
														<abbr title="required">*</abbr>
													</label>
	
													<div class="I-size">
														<select class="select2" id="vehicleModal" name="vehicleModal" onchange="getVehicleListForCreateServiceProgram();"
															style="width: 100%;">
														</select>	
													</div>
											</div>
											<c:if test="${configuration.vehicleBranchWiseProgram}">
											<br/>
											<div class="row1" id="" class="form-group">
													<label class="L-size control-label" for='to'>Vehicle Branch
														<abbr title="required">*</abbr>
													</label>
	
													<div class="I-size">
														<input type="hidden" id="branchId"  style="width: 100%;" onchange="getVehicleListForCreateServiceProgram();" placeholder="Please Enter Branch Name" />
													</div>
											</div>
											</c:if>
											<br>
											<br>
											<div class="table-responsive">
												<table class="table hide" id="vehicleTable">
													<thead>
														<tr>
															<td>Select</td>
															<td>Vehicle</td>
														</tr>
													</thead>
													<tbody id="vehicleTableBody">
	
													</tbody>
												</table>
											</div>
									<div class="row1" style="padding-top: 50px;" class="form-group">
												<label class="L-size control-label" for="time_threshold">
												</label>
												<div class="I-size">
													<button type="submit" id="saveServiceAsign" onclick="return saveServiceProramAsign();" class="btn btn-primary">
														Save
													</button>
													<button type="button" class="btn btn-default"
														data-dismiss="modal">
														<span id="Close">Close</span>
													</button>
												</div>
											</div>
											
								</div>
									<div class="modal-footer">
												
									</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table" id="dataTable" style="display: none;">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th class="fit ar">Job Type</th>
											<th class="fit ar">Job SubType</th>
											<th class="fit ar">Intervals</th>
											<th class="fit ar">Threshold Details</th>
											<th class="fit ar">Service Type</th>
											<th class="fit">Action</th>
										</tr>
									</thead>
									<tbody id="serviceScheduleBody">
										
									</tbody>

								</table>
							</div>
						</div>
						<br/><br/>
					</div>
				</fieldset>
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy1"></span></small> | <small class="text-muted"><b>Created
					date: </b> <span id="createdOn1"></span></small> | <small
				class="text-muted"><b>Last updated by :</b> <span id="lastupdatedBy"></span></small> | <small
				class="text-muted"><b>Last updated date:</b><span id="lastUpdated"></span></small>
		</div>
	</section>


<script type="text/javascript">
	$(document).ready(function() {
		$("#vehicleModal").select2();
		$("#vehicleType").select2();
	});
	$(function() {
		$('[data-toggle="popover"]').popover()
	});
</script>

<script
	src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/SR/ViewServiceProgramDetails.js"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
		
</div>
