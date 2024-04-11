<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
	
<style>
  table {
    table-layout:fixed;
}

table td {
    overflow:hidden;
}
}
</style> 	
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="open.html"><spring:message code="label.master.home" /></a>
								/ <a href="<c:url value="/newTripRoute/1.in"/>">New Trip
									Route</a>
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_TRIP_ROUTE_EXPENSE_RANGE')">
									<button type="button" class="btn btn-success" id="showExpenseRange">
										<span class="fa fa-plus" id="AddJobType">Add Expense Max Limit</span>
									</button>
								</sec:authorize>
								<c:if test="${routeConfig.addAllowanceToRoute}">
									<button type="button" class="btn btn-success" id="addAllowance">
											<span class="fa fa-plus" id="allowance" onclick="addAllowanceDetails();">Add Allowance Details</span>
									</button>
								</c:if>
								<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
									<a class="btn btn-warning btn-sm"
										href="<c:url value="/editTripRoute.in?routeID=${TripRoute.routeID}"/>">
										<span class="fa fa-pencil"></span> Edit Trip Route
									</a>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/createTripRoute.in"/>"> <span
										class="fa fa-plus"> Create Trip Route</span>
									</a>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_TRIP_CHECK_POINTS')">
									<a class="btn btn-info btn-sm" href="#" onclick="addTripCheckPoints()">
									<span class="fa fa-plus">Trip CheckPoints</span>
									</a>
								</sec:authorize>
							</div>
						</div>
						<c:if test="${param.saveTripRoute eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Route Fixed Entries created Successfully.
							</div>
						</c:if>
						<c:if test="${param.updateTripRoute  eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Route Updated Successfully.
							</div>
						</c:if>
						<c:if test="${param.notUpdateTripRoute  eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Route Not Updated Successfully.
							</div>
						</c:if>
						<c:if test="${param.updateRouteExpense eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Route Fixed Entries Updated Successfully.
							</div>
						</c:if>
						<c:if test="${param.alreadyRouteExpense eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Route Fixed Entries was Already created.
							</div>
						</c:if>

						<c:if test="${param.deleteTripRoute eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Route Fixed Entries Deleted Successfully.
							</div>
						</c:if>
						<c:if test="${param.notDeleteTripRoute eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Route Fixed Entries Not Deleted Successfully.
							</div>
						</c:if>
						<c:if test="${param.alreadyTripRoute eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Route Already Exists !
							</div>
						</c:if>
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="row">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Route : ${TripRoute.routeNumber}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripRoute.created}</h5>
									</div>
								</div>

								<div class="row">
									<h4 align="center">
										<a data-toggle="tip" data-original-title="Route Name"> <c:out
												value="${TripRoute.routeName}" />
										</a>
										<input type="hidden" id="routeId" value="${TripRoute.routeID}" >
										<input type="hidden" id="maxlimitConfig" value="${expenseMaxtLimitConfig}">
										<input type="hidden" id="expenseOutOfRange" value="${expenseOutOfRange}">
										 
										 
									</h4>
								</div>
								<div class="col-md-3"></div>
							</div>


							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Route No : <a data-toggle="tip"
										data-original-title="Trip Route No"> <c:out
												value="${TripRoute.routeNo}" /></a></li>
									<c:if test="${routeConfig.showFromTo}">
										<li>Route Time From : <a data-toggle="tip"
											data-original-title="From time"><c:out
													value="${TripRoute.routeTimeFrom}" /></a></li>
										<li>TO : <a data-toggle="tip"
											data-original-title="To time"><c:out
													value="${TripRoute.routeTimeTo}" /></a></li>
									</c:if>
									<li>Route Total Hour : <a data-toggle="tip"
										data-original-title="Hour"><c:out
												value="${TripRoute.routeTotalHour}" /></a></li>
									<li>Route Total Volume : <a data-toggle="tip"
										data-original-title="Volume"><c:out
												value="${TripRoute.routeTotalLiter}" /></a></li>
									<c:if test="${routeConfig.showAttandancePoint}">
										<li>Route Point : <a data-toggle="tip"
											data-original-title="Point"><c:out
													value="${TripRoute.routrAttendance}" /></a></li>
									</c:if>			
									<li>Approximate KM : <a data-toggle="tip"
										data-original-title="Approximate Km"><c:out
												value="${TripRoute.routeApproximateKM}" /></a></li>

									<li>Description : <a data-toggle="tip"
										data-original-title="Remarks"> <c:out
												value="${TripRoute.routeRemarks}" /></a></li>
									<c:if test="${TripRoute.mainRouteId > 0}">			
									<li>Main Route : <a data-toggle="tip"
										data-original-title="Remarks"> <c:out
												value="${TripRoute.mainRoute}" /></a></li>
									</c:if>			
								</ul>
							</div>
							<br>
							<c:if test="${!empty allowanceList}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Job Title</th>
														<th class="fit ar">Expense Type</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${allowanceList}"
														var="allowanceList">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${allowanceList.driverJobType}" /></td>
															<td class="fit ar"><small
																><c:out
																		value="${allowanceList.expenseType}" /></small></td>
															<td class="fit ar"><c:out
																	value="${allowanceList.amount}" /></td>
															<td class="fit ar"><a
																href="#" onclick="removeAllowanceDetails(${allowanceList.routeFixedAllowanceId});"
																data-toggle="tip" data-original-title="Click Remove"><span
																	style="color: red;"><em class="fa fa-times"> Remove</em></span></a></td>

														</tr>


													</c:forEach>

												</tbody>
												<tfoot>
													<tr class="breadcrumb">

													</tr>

												</tfoot>
											</table>
										</div>
										<div class="col-md-offset-6">

										</div>

									</div>
								</fieldset>
							</c:if>
							<br>
							<c:if test="${routeConfig.showIncomeExpense}">
							<c:if test="${!empty TripRouteExpense}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Expense Name</th>
														<th class="fit ar">Type</th>
														<th class="fit ar">Expense Place</th>
														<th class="fit ar">Reference</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripRouteExpense}"
														var="TripRouteExpense">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripRouteExpense.expenseName}" /></td>
															<td class="fit ar"><small
																class="label label-success"><c:out
																		value="${TripRouteExpense.expenseFixed}" /></small></td>
															<td class="fit ar"><c:out
																	value="${TripRouteExpense.expensePlace}" /></td>

															<td class="fit ar"><c:out
																	value="${TripRouteExpense.expenseRefence}" /></td>
															<td class="fit ar"><c:out
																	value="${TripRouteExpense.expenseAmount}" /></td>
															<td class="fit ar"><a
																href="removeTripRouteExpense.in?routefixedID=${TripRouteExpense.routefixedID}"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>

														</tr>


													</c:forEach>

												</tbody>
												<tfoot>
													<tr class="breadcrumb">

													</tr>

												</tfoot>
											</table>
										</div>
										<div class="col-md-offset-6">

										</div>

									</div>
								</fieldset>
							</c:if>
							<c:if test="${!empty TripRouteIncome}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Income Name</th>
														<th class="fit ar">Type</th>
														<th class="fit ar">Income Place</th>
														<th class="fit ar">Reference</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount_Income = 1;
													%>
													<c:forEach items="${TripRouteIncome}" var="TripRouteIncome">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount_Income);
																				hitsCount_Income += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripRouteIncome.incomeName}" /></td>
															<td class="fit ar"><small
																class="label label-success"><c:out
																		value="${TripRouteIncome.incomeFixed}" /></small></td>
															<td class="fit ar"><c:out
																	value="${TripRouteIncome.incomePlace}" /></td>

															<td class="fit ar"><c:out
																	value="${TripRouteIncome.incomeRefence}" /></td>
															<td class="fit ar"><c:out
																	value="${TripRouteIncome.incomeAmount}" /></td>
															<td class="fit ar"><a
																href="removeTripRouteIncome.in?routefixedID=${TripRouteIncome.routefixedID}"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>

														</tr>


													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</c:if>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
						<c:if test="${routeConfig.showIncomeExpense}">
							<form action="updateTripRouteExpense.in" method="post">
								<input type="hidden" name="routeID" value="${TripRoute.routeID}">
								<div class="form-horizontal">

									<fieldset>
										<legend>Expense Details</legend>
										<div class="row1"> 
											 <input type="hidden" id="maxAmount1">
											<div class="col-md-4">
												<select class="form-text select2" style="width: 100%;" onChange="getExpenseMaxLimit(1);"
													name="expenseName" id="Expense1" required="required">

												</select>
											</div>
											<div class="col-md-3">
												<input type="number" class="form-text" name="Amount" id="Amount1" readonly="readonly" onkeyup="return validateExpenseRange(1);"
													placeholder="Amount" min="0" required="required">
											</div>
											<div class="col-md-3">
												<input type="text" class="form-text" name="expenseRefence"
													placeholder="Reference" value="X00">
											</div>

											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-success">
													<i class="fa fa-plus"></i>
												</button>

											</div>

										</div>

									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..."
												name="commit" type="submit" value="Save Expense"> <a
												class="btn btn-link"
												href="<c:url value="/newTripRoute/1.in"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
							</form>
							<form action="updateTripRouteIncome.in" method="post">
								<input type="hidden" name="routeID" value="${TripRoute.routeID}">
								<div class="form-horizontal">

									<fieldset>
										<legend>Income Details</legend>
										<div class="row1">

											<div class="col-md-3">
												<select class="form-text select2" name="incomeName"
													id="Income" required="required">
												</select>
											</div>
											<div class="col-md-2">
												<input type="number" class="form-text" min="0"
													name="incomeAmount" placeholder="Amount"
													required="required">
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
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..."
												name="commit" type="submit" value="Save Income"> <a
												class="btn btn-link"
												href="<c:url value="/newTripRoute/1.in"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
							</form>
							</c:if>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<div class="modal fade" id="addRouteExpenseRange" role="dialog">
		<div class="modal-dialog" style="width:80%" >
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Add Expense Max Limit</h4>
					</div>
					<div class="modal-body">
						<fieldset>
						<div class="row">
							<div class="table-responsive">
								<table  class="table table-hover table-bordered">
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary" onclick="saveExpenseRange();">
										<span><spring:message code="label.master.Save" /></span>
									</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">
										<span id="Close"><spring:message code="label.master.Close" /></span>
									</button>
								</div>
								
									<thead>
										<tr>
											<th><h5>Sr No</h5></th>
											<th><h5>Expense Name</h5></th>
											<th><h5>Expense Max Range</h5></th>
											<!-- <th>Action</th> -->
										</tr>
									</thead>
									<tbody id="expenseMaxRangeTable">
									</tbody>
								
								</table>
							</div>
							<div class="text-center">
								<ul id="navigationBar" class="pagination pagination-lg pager"> </ul>
							</div>
						</div>
						
						</fieldset>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="saveExpenseRange();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	
			<div class="modal fade" id="addAllowanceModal" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Add Allowance Details</h4>
								</div>
								<div class="modal-body">
								<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Job
														Title :</label>
													<div class="I-size">
														<select class="form-text" name="driJobId" id="driJobId">
															<c:forEach items="${driverJobType}" var="driverJobType">
																<option value="${driverJobType.driJobId}">
																		<c:out value="${driverJobType.driJobType}" />
															</c:forEach>
														</select> 
													</div>
												</div>
								</div>
								
								<div class="row">
												<div class="form-group">
													<label class="L-size string required control-label">Expense Type:</label>
													<div class="I-size">
														<select class="form-text" name="expenseId" id="allowanceExpenseId">
														<option value="0">Please Select</option>
															<c:forEach items="${tripExpense}" var="tripExpense">
																<option value="${tripExpense.expenseID}">
																		<c:out value="${tripExpense.expenseName}" />
															</c:forEach>
														</select> 
													</div>
												</div>
								</div>
									
								</div>
								<div class="row1" id="grprenewalDate" class="form-group">

										<label class="L-size control-label" for="reservation">Amount
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
										<input class="form-text" id="allowanceAmount"  
											required="required" name="allowanceAmount" type="number"></input>
										</div>
								</div>
									<br>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary" onclick="saveAllowanceDetails();"
										id="js-upload-submit" value="Save Odometer">Save
										</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
						</div>
					</div>
				</div>
	
	<div class="modal fade" id="addAllowanceModal2" role="dialog">
		<div class="modal-dialog" style="width:80%" >
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Add Allowance Details</h4>
					</div>
					<div class="modal-body">
											<div class="row1">
												<div class="form-group">
													<label class="L-size string required control-label">Job
														Title :</label>
													<div class="I-size">
														<select class="form-text" name="driJobId">
															<c:forEach items="${driverJobType}" var="driverJobType">
																<option value="${driverJobType.driJobId}">
																		<c:out value="${driverJobType.driJobType}" />
															</c:forEach>
														</select> 
													</div>
												</div>
											</div>
							<div class="row1">
												<div class="form-group">
													<label class="L-size control-label">Amount:
														<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="driver_perdaySalary"
															onkeypress="return IsNumericMob(event);"
															ondrop="return false;" value="0" maxlength="5">
													</div>
												</div>
											</div>				
					
						
					</div>
					<div class="modal-footer" id="saveAllowanceDiv">
						<button type="submit" class="btn btn-primary" onclick="saveAllowanceDetails();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="addTripCheckPointModal" role="dialog">
		<div class="modal-dialog" style="width:80%" >
			<!-- Modal content-->
			<div class="modal-content">
			<input type="hidden" id="companyId" value="${companyId}">
				<input type="hidden" id="userId" value="${userId}">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<div class="pull-left">
							<h4>Add Trip CheckPoints</h4>
						</div>
						<div style="text-align: center;">
							<h4>Trip Route : ${TripRoute.routeName}</h4>
						</div>
					</div>
					<div class="modal-body">
						<fieldset>
						<div class="row box-body">
							<div class="table-responsive">
								<table  class="table table-hover table-bordered">
									<thead>
										<tr>
											<th><h5>Sr No</h5></th>
											<th><h5>CheckPoint Name</h5></th>
											<th><h5>Description</h5></th>
											<th><h5>Action</h5></th>
										</tr>
									</thead>
									<tbody id="tripCheckPointTable">
									</tbody>
								
								</table>
							</div>
						</div>
						<br>
						</fieldset>
						<fieldset>
						<div class="row box">
							<div class=" col-sm-8 col-md-2" style="font-weight: bold;">CheckPoint Name :
									<abbr title="required">*</abbr>
							</div>
						    <div class=" col-sm-8 col-md-3">
						    	<div class="col-lg-9">
									<input type="text" class="form-text" required="required"
									maxlength="150"  id="checkPointName"
									placeholder="Enter  Name" /> 
								</div>
						    </div>
						    <div class=" col-sm-8 col-md-1" style="font-weight: bold;">Description :
						    	
						    </div>
						   	<div class=" col-sm-8 col-md-3">
						   		<div class="I-size">
						   			<textarea class="form-text" id="addDescription" rows="3"
						   			style="width: 411px; height: 50px;"  maxlength="250">
									</textarea>
								</div>
							</div>
					   	</div>
						</fieldset>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="saveTripCheckPoints();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetExpense.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripRouteFixedAdd.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCheckPoint.js" />"></script>
			<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
</div>