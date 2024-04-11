<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap.css" />">
<style>
.form-text{
	height: 30px;
}
.column{
	padding: 15px;
}
.switch {
  position: relative;
  display: inline-block;
  width: 90px;
  height: 34px;
}

.switch input {display:none;}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ca2222;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2ab934;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(55px);
  -ms-transform: translateX(55px);
  transform: translateX(55px);
}

/*------ ADDED CSS ---------*/
.on
{
  display: none;
}

.on, .off
{
  color: white;
  position: absolute;
  transform: translate(-50%,-50%);
  top: 50%;
  left: 50%;
  font-size: 14px;
  font-family: Verdana, sans-serif;
}
.on
{
  left: 40%;
}
.off
{
  left: 60%;
}
input:checked+ .slider .on
{display: block;}

input:checked + .slider .off
{display: none;}

/*--------- END --------*/

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="issuesOpen.in"/>">Issues</a> / <small>Add Issue</small>
				</div>
				<div class="pull-right">
					<a href="<c:url value="issuesOpen.in"/>">Close</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_ISSUES')">
			<section class="content">
				<div class="box">
			   		<div class="box-body">
			   			<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="allowGPSIntegration" value="${gpsConfiguration.allowGPSIntegration}">
						<input type="hidden" id="vehicle_ExpectedOdameter" /> 
						<input type="hidden" id="vehicle_Odameter" />
						<input type="hidden" id="backDateMaxOdo" />
						<input type="hidden" id="backDateMinOdo" />
						<input type="hidden" id="reportedById" value="${userId}"/>
						<input type="hidden" id="serverDate" value="${serverDate}"/>
						<input type="hidden" id="serverDateStr" value="${serverDateStr}"/>
						<input type="hidden" id="serverTimeStr" value="${serverTimeStr}"/>
						<input type="hidden" id="issueAnalysisConfig" value="${configuration.issueAnalysis}">
						<input type="hidden" id="multipleIssues" value="${configuration.multipleIssues}">
						<input type="hidden" id="createIssueFromHealth" value="${configuration.createIssueFromHealth}">
						<input type="hidden" id="validateOdometer" value="${configuration.validateOdometer}">
						<input type="hidden" id="VID" value="${VID}">
						<input type="hidden" id="healthStatus" value="${healthStatus}">
						<input type="hidden" id="vehicleRegistration" value="${vehicleRegistration}">
						<input type="hidden" id="userId" value="${userId}">
						
			   			<div class="form-group column">
                            <label class="col-md-4 control-label">Issues Type : <abbr title="required">*</abbr></label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"> <span class="input-group-addon"><i class="glyphicon glyphicon-exclamation-sign"></i>  </span>
                              	 <select class="form-text select2" id="issueType" onchange="showHideBreakDownFeilds();">
                              	 	<c:forEach items="${IssueType}" var="issueType">
										<option value="${issueType.issueTypeId}">
												<c:out value="${issueType.issueTypeName}" />
										</option>
									</c:forEach>
                              	 </select>
                               </div>
                            </div>
                         </div>
                    
						<div id="vehicleDiv" class="form-group column" >
                            <label class="col-md-4 control-label">Vehicle <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon">  <i class="fa fa-bus"></i> </span>
                              	<input type="hidden" id="IssuesSelectVehicle" style="width: 100%;"  placeholder="Please Enter Vehicle Number" />
                               </div>
                            </div>
                         </div>
                         <div id="vGroupDiv" class="form-group column " style="display: none;">
                            <label class="col-md-4 control-label">Vehicle Group <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>  </span>
	                              	<select class="form-text"  id="vGroup">
										<c:forEach items="${vehiclegroup}" var="vehiclegroup">
											<option value="${vehiclegroup.gid}">
													<c:out value="${vehiclegroup.vGroup}" />
											</option>
										</c:forEach>
									</select>
                               </div>
                            </div>
                         </div>
                         <div id="driverDiv" class="form-group column" >
                            <label class="col-md-4 control-label">Driver <abbr id="driverFieldMendotry" title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>  </span>
                              	<input type="hidden" id="driverId" style="width: 100%;"  placeholder="Please Enter Driver Name" />
                               </div>
                            </div>
                         </div>
                         <c:if test="${configuration.customerIssue}">
	                          <div id="customerDiv" class="form-group column" style="display: none;">
	                            <label class="col-md-4 control-label">customer Name <abbr title="required">*</abbr> </label>
	                            <div class="col-md-8 inputGroupContainer">
	                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>  </span>
	                              	<input class="form-text" type="text" maxlength="150" id="customerName">
	                               </div>
	                            </div>
	                         </div>
	                     </c:if>  
	                      <div id="branchDiv" class="form-group column " style="display: none;">
                            <label class="col-md-4 control-label">Branch <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i>  </span>
                              		<input type="hidden" id="issuesBranch" style="width: 100%;" placeholder="Please Enter Branch Name" />
                               </div>
                            </div>
                         </div>
                         <div id="departmentDiv" class="form-group column " style="display: none;">
                            <label class="col-md-4 control-label">Department <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i> </span>
                              		<input type="hidden" id="issuesDepartnment" style="width: 100%;" placeholder="Please Enter Departnment Name" />
                               </div>
                            </div>
                         </div>
                         <div id="odometerDiv" class="form-group column" >
                            <label class="col-md-4 control-label">Odometer </label>
                            <div class="col-md-8 inputGroupContainer">
                              	<div class="input-group"><span class="input-group-addon"><i class="fa fa-tachometer"></i>  </span>
                              	<input class="form-text" id="Issues_Odometer" type="text" maxlength="7"
									onkeypress="return isNumberKey(event);" ondrop="return false;"> 
									<label class="error" id="errorOdometer" style="display: none"></label> 
                               </div>
                            </div>
                         </div>
                         <div id="gpsOdometerRow" class="form-group column " style="display: none;" >
                            <label class="col-md-4 control-label">GPS Odometer <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-tachometer"></i>  </span>
                              <input class="form-text" id="GPS_ODOMETER" readonly="readonly" type="text">
                               </div>
                            </div>
                         </div>
                         <div id="repostDateDiv" class="form-group column" >
                            <label class="col-md-4 control-label">Reported Date <abbr title="required">*</abbr></label> 
                         	<div class="L-size col-md-4 inputGroupContainer">
								<div class="input-group input-append date" id="StartDate">
									<input type="text" class="form-text" id="reportdDate" onchange="getVehicleGpsDetails();"
										name="ISSUES_REPORTED_DATE" required="required" readonly="readonly"
										data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
								<span id="reportDateIcon" class=""></span>
								<div id="reportDateErrorMsg" class="text-danger"></div>
							</div>
							<div class="col-md-4 inputGroupContainer">
								<div class="input-group clockpicker">
									<input type="text" class="form-text" readonly="readonly"
										name="issue_start_time" onchange="getVehicleGpsDetails();" id="issueStartTime" required="required"> <span
										class="input-group-addon"> <i
										class="fa fa-clock-o" aria-hidden="true"></i>
									</span>
								</div>
							</div>
                         </div> 
                        
                     
                         
                         <div class="form-group column hide" >
                            <label class="col-md-4 control-label">Reported By <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               	<div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i>  </span>
                              		<input class="form-text" readonly="readonly" type="text" maxlength="150" id="user"
									value="${userName}" onkeypress="return IsIssues_Reported_By(event);" ondrop="return false;">
                               	</div>
                            </div>
                         </div>
                         <c:if test="${configuration.showRouteOption}">
							<div class="form-group column">
								<label class="col-md-4 control-label">Route :</label>
								<div class="col-md-8 inputGroupContainer"
									style="padding:; padding-top: 7px;">
									<input type="hidden" id="FuelRouteList" name="routeID"
										style="width: 100%;" value="0" required="required"
										placeholder="Please Enter 3 or more Route Name, NO " />
								</div>
							</div>
						</c:if>
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Assign To <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-tags"></i>  </span>
                              	<input id="subscribe" type="hidden" style="width: 100%" placeholder="Please Enter User ">
                               </div>
                            </div>
                         </div>
                         <c:if test="${configuration.showLocation}">
                          <div  class="form-group column " >
                            <label class="col-md-4 control-label">Location </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-book"></i>  </span>
                              	<input class="form-text" type="text" maxlength="150" id="breakDownLocation">
                               </div>
                            </div>
                         </div>
                         </c:if>
                         
                         <div style="display: none;" class="form-group column breakdown" >
                            <label class="col-md-4 control-label">Trip Number </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-book"></i>  </span>
                              	<input class="form-text" type="number" maxlength="150" id="tripSheetNumber" onkeypress="return isNumberKey(event);">
                               </div>
                            </div>
                         </div>
                         <div style="display: none;" class="form-group column breakdown" >
                            <label class="col-md-4 control-label">Vehicle Replaced </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
	                              	<label class="switch">
										<input id="vehicleReplaced" type="checkbox" onclick="showHideVehicleReplaceFeilds();">
											<div class="slider round">
												<span class="on">Yes</span>
  												<span class="off">No</span>
											</div>
									</label>
                               </div>
                            </div>
                         </div>
                         
                         <div style="display: none;" class="form-group column replace" >
                            <label class="col-md-4 control-label">Replaced With Vehicle <abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon">  <i class="fa fa-bus"></i> </span>
                              	<input type="hidden" id="replacedVehicle" style="width: 100%;"  placeholder="Please Enter Vehicle Number"  onchange="validateReplaceVehicle();"/>
                               </div>
                            </div>
                         </div>
                         
                         <div style="display: none;" class="form-group column replace" >
                            <label class="col-md-4 control-label">Replaced Location </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-info-sign"></i>  </span>
                              	<input class="form-text" type="text" maxlength="150" id="replaceLocation">
                               </div>
                            </div>
                         </div>
                         
                         <div style="display: none;" class="form-group column breakdown" >
                            <label class="col-md-4 control-label">Trip Cancelled </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
	                              	<label class="switch">
										<input id="tripCancelled" type="checkbox" onclick="showHideTripCancelFeilds();">
											<div class="slider round">
												<span class="on">Yes</span>
  												<span class="off">No</span>
											</div>
									</label>
                               </div>
                            </div>
                         </div>
                         
                          <div style="display: none;" class="form-group column Cancelled" >
                            <label class="col-md-4 control-label">Cancelled KM </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-info-sign"></i>  </span>
                              	<input class="form-text" type="number" maxlength="150" id="cancelledKM" onkeypress="return isNumberKey(event);">
                               </div>
                            </div>
                         </div>
                              <c:if test="${configuration.showCategoryOption}">
						<div class="form-group column" id="partCategoryDiv"
							class="form-group">
							<label class="col-md-4 control-label" for="partCategory">Category
								:<abbr title="required">*</abbr>
							</label>
							<div class="I-size col-md-8 inputGroupContainer">
								<select class="form-text" name="categoryId" style="width: 100%;"
									id="partCategory">
									<option value=""><!-- Please select --></option>
									<c:forEach items="${PartCategories}" var="PartCategories">
										<option value="${PartCategories.pcid}"><c:out
												value="${PartCategories.pcName}" /></option>
									</c:forEach>
								</select> <input type='hidden' id='partCategoryText' name="category"
									value='' /> <span id="partCategoryIcon" class=""></span>
								<div id="partCategoryErrorMsg" class="text-danger"></div>
							</div>
						</div>
						 </c:if>

						<div id="labelDiv" class="form-group column">
							<label class="col-md-4 control-label">Label</label>
							<div class="col-md-8 inputGroupContainer">
								<input type="hidden" id="validateOdometerInIssues"
									value="${validateOdometerInIssues}"> <input
									type="hidden" id="validateMinOdometerInIssues"
									value="${validateMinOdometerInIssues}">
								<div class="input-group" id="issueLabelDiv">
									<span class="input-group-addon"><i
										class=" glyphicon glyphicon-list-alt "></i> </span> <select
										class="form-text" name="issueLabel" id="issueLabel"
										onchange="return validateIssue(this);">
										<option value="1">NORMAL</option>
										<c:if test="${!configuration.hideHighStatus}">
											<option value="2">HIGH</option>
										</c:if>
										<c:if test="${!configuration.hideLowStatus}">
											<option value="3">LOW</option>
										</c:if>
										<option value="4">URGENT</option>
										<option value="5">VERY URGENT</option>
										<c:if test="${showBreakdownSelection}">
											<option value="6">BREAKDOWN</option>
										</c:if>
									</select>
								</div>
							</div>
						</div>
						<div id="summaryDiv" class="form-group column">
							<label class="col-md-4 control-label">Summary <abbr
								title="required">*</abbr>
							</label>
							<div class="col-md-8 inputGroupContainer">
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-book"></i>
									</span> <input class="form-text" type="text" maxlength="150"
										id="issuesSummary" name="issues_Summary"
										ondrop="return false;">
								</div>
							</div>
						</div>

					</div>
			  	</div>

				<c:if test="${configuration.multipleIssues}">
					<div id="moreParts"></div>
					<div class="row1" id="multiDiv">
						<div class="input_fields_wrap">
							<button class="add_field_button btn btn-info" data-toggle="tip"
								data-original-title="Click add one more Issues">
								<i class="fa fa-plus"></i> Add More
							</button>
						</div>
					</div>
				</c:if>

				<div class="box">
					<div class="box-body">
						<div class="row1">
							<label class="L-size control-label">Description :</label>
							<div class="I-size">
								<textarea class="form-text" id="description" rows="3" maxlength="250"
									 ondrop="return false;">
									</textarea>
							</div>
						</div>
					</div>
				</div>
			
			</section>
			 <fieldset class="form-actions">
				<div class="row1">
					<div class="pull-right">
						<button type="submit" id="saveIssue" class="btn btn-success">Save Issue</button>
						<a class="btn btn-default" href="/issuesOpen/1.in">Cancel</a>
					</div>
				</div>
			</fieldset> 
		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/VehicleGPSDetails.js" />"></script>		
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/IssuesDetailsValidate.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/AddIssuesDetails.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#partCategory").change(function () {
			    $("#partCategoryText").val($("#partCategory").find(":selected").text());
			});
		});
	</script>
	
</div>