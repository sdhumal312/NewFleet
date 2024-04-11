<%@ include file="taglib.jsp"%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="ViewServiceReminderList.in"> Service
						Reminder</a> / Create Multiple Service Reminder
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="ViewServiceReminderList.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<%-- <c:if test="${true}">
						<div class="alert alert-info">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This form lets you create Service Reminders for multiple vehicles
							at once. If you have multiple vehicles that all need the <br>same
							Service Reminder (e.g. Oil Change every 5,000 miles), you can use
							this form instead of creating a single Service Reminder for each
							vehicle. <br> <br>Service Reminders will be created in
							the background. After the Service Reminders have been created,
							you'll be able to edit each one individually.
						</div>
					</c:if> --%>
						<div class="form-horizontal ">

							<fieldset>
								<legend>Service Reminder Details</legend>
								<div class="box">
									<div class="box-body">
									   <input type="hidden" id="companyId" value="${companyId}">
									   <input type="hidden" id="userId" value="${userId}">
									   <input type="hidden" id="service_id" value="${service_id}">
									   <input type="hidden" id="service_Number" value="${service_Number}">
										<div class="form-horizontal ">
										
											 <div class="form-group">
					                            <label class="col-md-2 control-label">Vehicle : <abbr title="required">*</abbr></label>
					                            <div class="col-md-9 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="fa fa-bus"></i>
					                               </span>
					                              	 <input type="hidden" id="vid" name="vid" style="width: 100%;" 
					                              	 	placeholder="select vehicles" />
					                               </div>
					                            </div>
					                         </div>
					                         
					                         <div class="form-group column">
					                            <label class="col-md-4 control-label">Service
													Jobs <abbr title="required">*</abbr></label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-cog"></i>
					                               </span>
					                              	 <input type="hidden" id="from" name="serviceTypeId"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Job Name" />
					                               </div>
					                            </div>
					                         </div>
					                         <div class="form-group column">
					                            <label class="col-md-4 control-label">Service
													Sub Jobs<abbr title="required">*</abbr></label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-cog"></i>
					                               </span>
					                              	 <select style="width: 100%;" name="serviceSubTypeId" id='to'>

													</select>
					                               </div>
					                            </div>
					                         </div>
					                         
					                         <div class="form-group column">
					                            <label class="col-md-4 control-label">Service Type :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-star-empty"></i>
					                               </span>
					                              	 <select class="form-control" id="serviceType"
															name="serviceType"
															onchange="OnChangeDueThreshold(this)" required="required">
															<option value="1">OPTIONAL</option>
															<option value="2">MANDATORY</option>
													</select>
					                               </div>
					                            </div>
					                         </div>
					                         <div class="form-group column">
					                            <label class="col-md-4 control-label">Meter Interval :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-road"></i>
					                               </span>
					                              	<input type="text" class="form-control"
														name="meter_interval" min="0" maxlength="6"
														id="meter_interval" placeholder="ex: oil change every 5000 km"
														onkeypress="return isNumberKey(event);"
														ondrop="return false;">
					                               </div>
					                            </div>
					                         </div>
					                         
					                          <div class="form-group column">
					                            <label class="col-md-4 control-label">Time Interval :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-time"></i>
					                               </span>
					                              	<input type="text" class="form-control"
															name="time_interval" min="0" maxlength="2"
															id="time_interval" placeholder="ex: Car Wash every 1 month"
															onkeypress="return isNumberKey(event);"
															ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                          <div class="form-group column">
					                            <label class="col-md-4 control-label">Interval Type :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-eye-open"></i>
					                               </span>
					                              	<select class="form-control" id="time_intervalperiod"
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
					                         <div class="form-group column">
					                            <label class="col-md-4 control-label">Set First Service</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                              
					                              	<input type="checkbox" class="form-control" id="firstService"
													  data-on-text="Yes" data-off-text="No" name="firstService" onclick="setFirstServiceDetails();">
					                               </div>
					                            </div>
					                         </div>
					                          <div id="firstMeter" class="form-group column" style="display: none;">
					                            <label class="col-md-4 control-label">First Meter Interval :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-road"></i>
					                               </span>
					                              	<input type="text" class="form-control"
														min="0" maxlength="6"
														id="first_meter_interval" placeholder="ex: oil change every 5000 km"
														onkeypress="return isNumberKey(event);"
														ondrop="return false;">
					                               </div>
					                            </div>
					                         </div>
					                          
					                          <div id="firstTime" class="form-group column" style="display: none;">
					                            <label class="col-md-4 control-label">First Time Interval :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-time"></i>
					                               </span>
					                              	<input type="text" class="form-control"
															min="0" maxlength="2"
															id="first_time_interval" placeholder="ex: Car Wash every 1 month"
															onkeypress="return isNumberKey(event);"
															ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                          <div id="firstTimeType" class="form-group column" style="display: none;">
					                            <label class="col-md-4 control-label">Interval Type :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-eye-open"></i>
					                               </span>
					                              	<select class="form-control" id="first_time_intervalperiod"
															onchange="OnChangeDueThreshold(this)" required="required">
															<option value="1">day(s)</option>
															<option value="2">Week(s)</option>
															<option value="3">Month(s)</option>
															<option value="4">Year(s)</option>
														</select>
					                               </div>
					                            </div>
					                         </div>
					                        
					                     <!--   <div id="preServiousOdometer" class="form-group column" style="display: none;">
					                            <label class="col-md-4 control-label">Previous Service Odometer :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                              	<input type="text" class="form-control"
															min="0" maxlength="2"
															id="first_time_interval" placeholder=""
															onkeypress="return isNumberKey(event);"
															ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div> -->
<!-- 					                         <div id="paidDate" class="form-group column" style="display: none;">
												<label class="col-md-4 control-label">Paid Date :</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group  input-append date" id="paidDateSr">
														 <input type="text" class="form-control" 
															name="" id="" placeholder=""
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span
															class="input-group-addon add-on">
															<span
															class="fa fa-calendar"></span> 
															</span>
														
													</div>
													<span id="paidDateIcon" class=""></span>
													<div id="paidDateErrorMsg" class="text-danger"></div> 
												</div>
											</div> -->
					                      
					                      <br>
											
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Due Soon Details</legend>
								<div class="box">
									<div class="box-body">
										<div class="form-horizontal ">
										 <div class="form-group">
					                            <label class="col-md-2 control-label">Meter Threshold : <abbr title="required">*</abbr></label>
					                            <div class="col-md-9 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-road"></i>
					                               </span>
					                              	 <input type="number" class="form-control"
														name="dueMeterThreshold" maxlength="6" min="0" value="0"
														id="dueMeterThreshold"
														onkeypress="return isNumberKey(event);"
														ondrop="return false;">
													<p class="help-block">Number of miles/km/hours in
														advance you consider this reminder to be "due soon" (ex:
														500 miles is common for a typical fleet vehicle)</p>
					                               </div>
					                            </div>
					                         </div>
					                         
					                         
					                          <div class="form-group column">
					                            <label class="col-md-4 control-label">Time Threshold :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-time"></i>
					                               </span>
					                              	<input type="number" class="form-control"
															name="time_threshold" min="0" maxlength="2" value="0"
															id="time_threshold"
															onkeypress="return isNumberKey(event);">
					                               </div>
					                            </div>
					                         </div>
					                          <div class="form-group column">
					                            <label class="col-md-4 control-label">Threshold Type :</label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="glyphicon glyphicon-info"></i>
					                               </span>
					                              		<select class="form-control" id="time_thresholdperiod"
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
					                         <br>
											 <div class="form-group" style="margin-top: 50px;">
					                            <label class="col-md-2 control-label">Subscribed Users : <abbr title="required">*</abbr></label>
					                            <div class="col-md-9 inputGroupContainer">
					                               <div class="input-group">
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

									</div>
								</div>
							</fieldset>
							<div class="panel-footer">
								<div class="L-size"></div>
								<div class="I-size">
									<button type="submit" class="btn btn-success" onclick="updateServiceReminder();">Update
										Service Reminder</button>
									<a class="btn btn-link"
										href="ViewServiceReminderList.in">Cancel</a>
								</div>
							</div>
						</div>
					<br> <br>
				</div>
			</div>
		</sec:authorize>
		<script>
			$(function() {
				$("#to").select2();

				
			$(function() {
					$("#paidDateSr").datepicker({
					       autoclose: !0,
					       todayHighlight: !0,
					       format: "dd-mm-yyyy",
					       endDate: new Date()
					   })
				}); 
				
			});
			
		</script>
		
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/SeviceReminderValidate.js" />"></script>
	    <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/SeviceReminderEdit.js" />"></script>		
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	</section>
</div>
