<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripRoute/1.in"/>">New Trip Route</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createTripRoute.in"/>"> <span
							class="fa fa-plus"> Create Trip Route</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="main-body">
			<input type="hidden" id="showIncomeExpenseVal" value="${routeConfig.showIncomeExpense}">
			<input type="hidden" id="expenseOutOfRange" value="${expenseOutOfRange}">
			<div class="row">
				<c:if test="${param.alreadyTripRoute  eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Route Already Exists.
					</div>
				</c:if>
				<c:if test="${param.sequenceNotFound  eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Sequence Not Found Please Contact To System Administrator !
					</div>
				</c:if>
				<div class="col-xs-9">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="form-horizontal ">
									<form id="formTripRoute"
										action="<c:url value="/saveTripRoute.in"/>" method="post"
										enctype="multipart/form-data" name="formTripRoute" role="form"
										class="form-horizontal">
										<c:if test="${subRouteTypeNeeded}">
											<div class="row1">
												<label class="L-size control-label">Route Type :</label>
												<div class="I-size">
													<select name="routeType" id="routeType" class="form-text select2" style="width: 100%;"
														required="required">
														<option value="1">MAIN ROUTE</option>
														<option value="2">SUB ROUTE</option>
													</select>
												</div>
											</div>
											<div class="row1" id="mainRoute" style="display: none;">
												<label class="L-size control-label">Main Route :</label>
												<div class="I-size">
													<select class="form-text" name="mainRouteId" id="mainRouteId" required="required">
														<option value="0">
																		<c:out value="Select Main Route" />
														</option>
															<c:forEach items="${routeList}" var="routeList">
																<option value="${routeList.routeID}">
																		<c:out value="${routeList.routeName} ${routeList.routeNo}" />
																</option>
															</c:forEach>
														</select>
												</div>
													<span id="mainRouteIcon" class=""></span>
													<div id="mainRouteErrorMsg" class="text-danger"></div>
											</div>
										</c:if>
										<div class="row1" id="grprouteName" class="form-group">
											<label class="L-size control-label">Route Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" class="form-text" name="routeName" required="required"
													placeholder="Enter Route Name" maxlength="200"
													id="routeName" onkeypress="return RouteName(event);"
													ondrop="return false;" /><span id="routeNameIcon" class=""></span>
												<div id="routeNameErrorMsg" class="text-danger"></div>
												<label id="errorRouteName" class="error"></label>
											</div>
											<input type="hidden"  name="subRouteTypeNeeded" value="${subRouteTypeNeeded}" />
										</div>
										<div class="row1" id="grpVehicleGroup">
												<div class="form-group">
													<label class="L-size string required control-label">Group
														Service :</label>
													<div class="I-size">
														<select class="form-text" name="vehicleGroupId" id="vehiclegroupid">
														<option value="0">
																		<c:out value="Select Group" />
														</option>
															<c:forEach items="${vehicleGroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																		<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
														<input type="hidden" name = "driver_group" id= "driver_group"/>
													</div>
													<span id="vehicleGroupIcon" class=""></span>
													<div id="vehicleGroupErrorMsg" class="text-danger"></div>
												</div>
										</div>
										<div class="row1" id="grprouteNumber" class="form-group">
											<label class="L-size control-label">Route Number : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" class="form-text" name="routeNo" required="required"
													placeholder="Enter Route NO" maxlength="25"
													id="routeNumber" 
													ondrop="return false;" /><span id="routeNumberIcon"
													class=""></span>
												<div id="routeNumberErrorMsg" class="text-danger"></div>
												<label id="errorRouteNo" class="error"></label>
											</div>
										</div>
										<c:if test="${routeConfig.showFromTo}">
											<div class="row1">
												<label class="L-size control-label" id="ClassofVehicle">Route
													Time From :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" value="00:00" readonly="readonly"
																name="routeTimeFrom" required="required"> <span
																class="input-group-addon"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
													</div>
													<label class="L-size control-label" id="ClassofVehicle">Route
														Time To : <abbr title="required">*</abbr>
													</label>
													<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" value="00:00" readonly="readonly"
																name="routeTimeTo" required="required"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${routeConfig.showAttandancePoint}">
											<div class="row1" id="grproutePoint" class="form-group">
												<label class="L-size control-label">Route
													Point/attendance : <abbr title="required">*</abbr>
												</label>
												<div class="L-size">
													<input type="text" class="form-text" name="routrAttendance"
														placeholder="eg: 2" maxlength="8" id="routePoint"
														onkeypress="return RouteKM(event);" ondrop="return false;" />
													<span id="routePointIcon" class=""></span>
													<div id="routePointErrorMsg" class="text-danger"></div>
													<label id="errorRouteKM" class="error"></label>
												</div>
											</div>
										</c:if>	
										<div class="row1" id="grprouteHour" class="form-group">
											<label class="L-size control-label">Route Total Hour
												: <abbr title="required">*</abbr>
											</label>
											<div class="L-size">
												<div class="input-group">
													<input type="text" class="form-text" name="routeTotalHour" required="required"
														placeholder="eg: 48" maxlength="8" id="routeHour"
														 ondrop="return false;"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														 />
													<span class="input-group-addon"> <span
														aria-hidden="true">HH</span>
													</span>
												</div>
												<span id="routeHourIcon" class=""></span>
												<div id="routeHourErrorMsg" class="text-danger"></div>
												<label id="errorRouteKM" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grprouteLiter" class="form-group">
											<label class="L-size control-label" for="routeLiter">Route
												Total Volume : <abbr title="required">*</abbr>
											</label>
											<div class="L-size">
												<div class="input-group">
													<input type="text" class="form-text" name="routeTotalLiter" required="required"
														placeholder="eg: 30" maxlength="8" id="routeLiter"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"" ondrop="return false;" />
													<span class="input-group-addon"> <span
														aria-hidden="true">L</span>
													</span>
												</div>
												<span id="routeLiterIcon" class=""></span>
												<div id="routeLiterErrorMsg" class="text-danger"></div>
												<label id="errorRouteKM" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grprouteKM" class="form-group">
											<label class="L-size control-label">Route Approximate
												KM :<abbr title="required">*</abbr>
											</label>
											<div class="L-size">
												<div class="input-group">
													<input type="text" class="form-text"
														name="routeApproximateKM" placeholder="eg: 160" required="required"
														maxlength="8" id="routeKM"
														onkeypress="return isNumberKey(event);" ondrop="return false;" />
													<span class="input-group-addon"> <span
														aria-hidden="true">KM</span>
													</span>
												</div>
												<span id="routeKMIcon" class=""></span>
												<div id="routeKMErrorMsg" class="text-danger"></div>
												<label id="errorRouteKM" class="error"></label>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="text optional form-text"
													name="routeRemarks" rows="3" maxlength="500"
													onkeypress="return RouteRemarks(event);"
													ondrop="return false;"> 
				                                </textarea>
												<label id="errorRouteRemarks" class="error"></label>
											</div>
										</div>
									<div id="showIncomeExpense" style="display: none;">
									<fieldset>
											<legend>Expense Details</legend>
											<div class="row1" class="form-group" id="grprouteExpense">
 												<input type="hidden" id="maxlimitConfig" value="${routeConfig.tripRouteExpenseMaxLimit}">
												<div class="col-md-3">
													<select class="form-text select2" style="width: 100%;" onChange="getExpenseMaxLimit(1);"
														name="expenseName" id="Expense1" >

													</select> <span id="routeExpenseIcon" class=""></span>
													<div id="routeExpenseErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="number" id="maxAmountOnCreate1" class="form-text" min="0" name="maxAmount" onblur="validateMaxAmount(1);" placeholder="Max Amount" onkeypress="return isNumberKeyWithDecimal(event,this.id)" >
												</div>
												<div class="col-md-2" id="grprouteAmount" >
													<input type="number" class="form-text" name="Amount" onkeyup="return validateExpenseRange(1);"
														id="Amount1" placeholder="Amount" min="0" onkeypress="return isNumberKeyWithDecimal(event,this.id)"> <span
														id="routeAmountIcon" class=""></span>
													<div id="routeAmountErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-3" id="grprouteReference">
													<input type="text" class="form-text" name="expenseRefence"
														id="routeReference" placeholder="Reference" value="A00">
													<span id="routeReferenceIcon" class=""></span>
													<div id="routeReferenceErrorMsg" class="text-danger"></div>
												</div>

												<div class="input_fields_wrap">
													<button class="add_field_button btn btn-success">
														<i class="fa fa-plus"></i>
													</button>

												</div>

											</div>

										</fieldset>
										<fieldset>
											<legend>Income Details</legend>
											<div class="row1" class="form-group">

												<div class="col-md-3">
													<select class="form-text select2" name="incomeName"
														id="Income" >
													</select>
												</div>
												
												<div class="col-md-2">
													<input type="number" class="form-text" min="0" id="incomeamount"
														name="incomeAmount" placeholder="Amount"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														>
												</div>
												<div class="col-md-3">
													<input type="text" class="form-text" name="incomeRefence"
														placeholder="Reference" value="X0">
												</div>
												<div class="input_fields_income">
													<button class="add_field_button_income btn btn-success">
														<i class="fa fa-plus"></i>
													</button>
												</div>

											</div>

										</fieldset>
									</div>
										
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<button type="submit" id="createTripRoute" onclick="return validateMainRoute();" class="btn btn-success">Create
														Trip Route Name</button>
													<a class="btn btn-link" href="<c:url value="/newTripRoute/1.in"/>">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>

						</div>
					</sec:authorize>
				</div>
				<div class="col-sm-1 col-md-2">
					<%@include file="../vehicle/masterSideMenu.jsp"%>
				</div>
			</div>
		</div>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetExpense.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripRouteCreate.js " />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			if($('#showIncomeExpenseVal').val() == 'true' || $('#showIncomeExpenseVal').val() == true){
				$('#showIncomeExpense').show();
			}
			$(".select2").select2();
			$('.clockpicker').clockpicker();
			
			$("#routeType").change(function() {
		        if($("#routeType").val() == 1){
		        	$('#mainRoute').hide();
		        }else{
		        	$('#mainRoute').show();
		        }
		    }
		    );
			
		});			
		function validateMainRoute(){
	    	if(Number($('#routeType').val()) == 2){
	    		if(Number($('#mainRouteId').val()) == 0){
	    			showMessage('errors', 'Please Select Main Route!');
	    			 document.getElementById("mainRoute").className = 'form-group has-error has-feedback';
	    			return false;
	    		}else{
		    		return true;
	    		}
	    	}
	    	return true;
	    }
	</script>
</div>