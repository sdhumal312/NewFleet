<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css"/> ">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">		
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Trip
									Payment</a> / <a
									href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>">Show
									Trip Payment</a> / <small>Add A/C Income TripSheet</small>
							</div>
							<div class="pull-right"></div>
						</div>
						<sec:authorize access="!hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
							<div class="row">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripSheet.created}</h5>
									</div>
								</div>
								<div class="row">
									<h4 align="center">
										<a href="showVehicle.in?vid=${TripSheet.vid}"
											data-toggle="tip" data-original-title="Click Vehicle Info">
											<c:out value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>
								<div class="col-md-3"></div>
							</div>
							<div class="row">
								<h4 align="center">${TripSheet.routeName}</h4>
							</div>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <c:out
												value="${TripSheet.tripOpenDate}  TO" /></a> <a
										data-toggle="tip" data-original-title="Trip Close Date"> <c:out
												value="  ${TripSheet.closetripDate}" /></a></li>
									<li>Group Service : <a data-toggle="tip"
										data-original-title="Group Service"><c:out
												value="${TripSheet.vehicle_Group}" /></a></li>
									<li>Booking No : <a data-toggle="tip"
										data-original-title="Booking No"> <c:out
												value="${TripSheet.tripBookref}" /></a></li>
									<li>Driver 1 : <a data-toggle="tip"
										data-original-title="Driver 1"> <c:out
												value="${TripSheet.tripFristDriverName}" /></a></li>
									<li>Driver 2 : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripSheet.tripSecDriverName}" /></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><c:out
												value="${TripSheet.tripCleanerName}" /></a></li>
									<li>Opening KM : <a data-toggle="tip"
										data-original-title="Opening KM"><c:out
												value="${TripSheet.tripOpeningKM}" /></a></li>
									<li>Closing KM : <a data-toggle="tip"
										data-original-title="closing KM"> <c:out
												value="${TripSheet.tripClosingKM}" /></a></li>
								</ul>
							</div>
							<br>
							<c:if test="${!empty TripSheetIncome}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Income Name</th>
														<th class="fit ar">Income Place</th>
														<c:if test="${configuration.showTripsheetNetIncomeAmount}">
															<th class="fit ar">Route</th>
														</c:if>
														<th class="fit ar">Collected By</th>
														<th class="fit ar">Reference</th>
														<c:if test="${configuration.showTripsheetNetIncomeAmount}">
															<th class="fit ar">GST</th>
															<th class="fit ar">Commission</th>
														</c:if>
														<th class="fit ar">Amount</th>
														<c:if test="${configuration.showTripsheetNetIncomeAmount}">
															<th class="fit ar">Net Amount</th>
														</c:if>
														<th class="fit ar">Created</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripSheetIncome}" var="TripSheetIncome">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripSheetIncome.incomeName}" /></td>

															<td class="fit ar"><c:out
																	value="${TripSheetIncome.incomePlace}" /></td>
																	
															<c:if test="${configuration.showTripsheetNetIncomeAmount}">
																<td class="fit ar"><c:out
																	value="${TripSheetIncome.routeName}" /></td>
															</c:if>
																	
															<td class="fit ar"><c:out
																	value="${TripSheetIncome.incomeCollectedBy}" /></td>

															<td class="fit ar"><c:out
																	value="${TripSheetIncome.incomeRefence}" /></td>
															
															<c:if test="${configuration.showTripsheetNetIncomeAmount}">	
																<td class="fit ar"><c:out
																	value="${TripSheetIncome.gst}" /></td>
																<td class="fit ar"><c:out
																	value="${TripSheetIncome.commission}" /></td>
															</c:if>
															
															<c:if test="${configuration.showTripsheetNetIncomeAmount}">	
																<td class="fit ar"><c:out
																		value="${TripSheetIncome.netIncomeAmount}" /></td>
															</c:if>
															<c:if test="${!configuration.showTripsheetNetIncomeAmount}">	
																<td class="fit ar"><c:out
																		value="${TripSheetIncome.incomeAmount}" /></td>
															</c:if>
															
															<c:if test="${configuration.showTripsheetNetIncomeAmount}">	
																<td class="fit ar"><c:out
																	value="${TripSheetIncome.incomeAmount}" /></td>
															</c:if>	
																	
															<td class="fit ar"><c:out
																	value="${TripSheetIncome.created}" /></td>
															<sec:authorize access="hasAuthority('REMOVE_TRIP_INCOME')">
															  	<c:if test="${TripSheetIncome.ticketIncomeApiId == 0}">	
																	<td class="fit ar"><a
																		href="removeIncomeAccount.in?tripincomeID=${TripSheetIncome.tripincomeID}"
																		data-toggle="tip" data-original-title="Click Remove"><font
																			color="red"><i class="fa fa-times"> Remove</i></font></a></td>
																</c:if>
																<c:if test="${TripSheetIncome.ticketIncomeApiId != 0}">	
																	<td class="fit ar"><c:out
																	value="-" /></td>
																</c:if>			
															</sec:authorize>		
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr class="breadcrumb">
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="row">
											<div class="col-md-11">
												<div class="col-md-offset-6">
													<table class="table">
														<tbody>
															<tr data-object-id="" class="ng-scope">
																<td class="key"><h4>Total Income:</h4></td>
																<td class="value"><h4>
																		<i class="fa fa-inr"></i> ${incomeTotal}
																	</h4></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</fieldset>
							</c:if>
							
							<c:if test="${configuration.showTripsheetNetIncomeAmount}">
							<div class="pull-right">
								<button type="button" class="btn btn-success" onclick="showAddIncome();">
								<span class="fa fa-plus" id="AddJobType"> Add Income</span>
								</button>
							</div>
							</c:if>
							
							<input type="hidden" id="allowShortCut" value="${AllowShortCutInTripSheet}"/>
								<c:if test="${!configuration.showTripsheetNetIncomeAmount}">
							<form action="updateIncomeAccount.in" method="post">
								<input type="hidden" name="TripSheetID"
									value="${TripSheet.tripSheetID}">
								<div class="form-horizontal">
									<fieldset>
										<legend>New Income Details
											<div class="pull-right" style="font-size:13px;">
												<div>
													<span>Short-Cut Keys : </span>
														<a style ="padding: 2px; height :13px;" class="btn btn-success">
															<i class="fa fa-plus"></i>
														</a>
													<span>  Alt + N (Add)</span>
													
													<a style= "padding: 0.5px 5px;"> </a>
													
													<a class="remove_field1" style= "padding: 0.1px 2px; height :25px;">
														<font color="FF00000">
															<i style= "height :25px;" class="fa fa-trash"></i>
														</font>
													</a>
													<span>  Alt + R (Remove)</span>
												</div>
											</div> 
										</legend>
										<div class="row1">
											<label class="L-size control-label"></label>

											<div class="col-md-9">
												<div class="col-md-4">
													<label class="control-label">Income Name</label>

												</div>
												<div class="col-md-2">
													<label class="control-label">Income Amount</label>

												</div>
												<div class="col-md-2">
													<label class="control-label">Reference</label>
												</div>
											</div>
										</div>
										<div class="row1">
										
										<input type="hidden" name="route" value="${TripSheet.routeID}">
											<input type="hidden" name="incomeDate" value="${currentDate}">
											<input type="hidden" name="netAmount" value="0">
											<input type="hidden" name="remark" value="Income">
											<input type="hidden" name="gst" value="0">
											<input type="hidden" name="commission" value="0">
											<div class="col-md-4">
												<select class="form-text select2" name="incomeName"
													id="Income" required="required">
												</select>
											</div>
											<div class="col-md-3">
												<input type="number" class="form-text" min="0" name="Amount"
													placeholder="Amount" required="required">
											</div>
											<div class="col-md-3">
												<input type="text" class="form-text" name="incomeRefence"
													placeholder="Reference" value="0">
											</div>

											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-success">
													<i class="fa fa-plus"></i>
												</button>

											</div>
										</div>
										<br>
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..." onclick="return validateIncome();"
												name="commit" type="submit" value="Save Income"> <a
												class="btn btn-link"
												href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
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
		<c:if test="${configuration.showTripsheetNetIncomeAmount}">
		<div class="modal fade" id="addFixRateIncome" role="dialog">
			<input type="hidden" id="config" value="${showNetIncomeAmount}">
		
			<div class="modal-dialog" style="width: 750px;">
				<!-- Modal content-->
				<form action="updateIncomeAccount.in" method="post">
				<input type="hidden" name="TripSheetID" value="${TripSheet.tripSheetID}">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType">Add Income</h4>

					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="name"> Income Name :</label>
							<div class="I-size">
								<select class="form-text " name="incomeName" onChange="getIncomeDetails(0);" id="Income" required="required">
								</select>
							</div>
						</div>
						<br>
						<div class="row">
							<label class="L-size control-label" style="text-align-last: center;" id="Type">Route :</label>
							<div class="I-size">
								<select class="form-text " name="route" id="route" required="required"> </select>
							</div>
						</div>
						<br>
						<div class="row" style="padding-left: 45px;"> 
							<div class="col-md-1">
								<label class="control-label">GST :</label>
							</div>
							<div class="col-md-1">
								<label class="control-label">Commission :</label>
							</div>
							<div class="col-md-3">
								<label class="control-label">Amount :</label>
							</div>
							<div class="col-md-3">
								<label class="control-label">Net Amount :</label>
							</div>
						</div>
						
						<div class="row" style="padding-left: 45px;">
							<input class="hidden" name="fixRate" readOnly="readOnly" id="fixRate" required="required">
							
							<div class="col-md-1 ">
								<input class="form-text" name="gst" readOnly="readOnly"
									id="gst" required="required">
							</div>
							<div class="col-md-1 ">
								<input class="form-text" name="commission" readOnly="readOnly"
									id="commission" required="required">
							</div>
							
							<div class="col-md-3">
								<input type="number" class="form-text" min="0" name="Amount" id ="Amount" onkeypress="return isNumberKeyWithDecimal(event,this);"
								onkeyup="calculateAmount(this,0);" placeholder="Amount"  required="required">
							</div>
							
							<div class="col-md-3">
								<input type="text" class="form-text" min="0" name="netAmount" id ="netAmount" readOnly="readOnly"
									placeholder="Amount"  required="required">
							</div>
						</div>
						<br>
						
						<div class="row" style="padding-left: 45px;">
							<div class="col-md-3">
								<label class="control-label">Reference :</label>
							</div>
							<div class="col-md-3">
								<label class="control-label">Date :</label>
							</div>
							
						</div>
						
						<div class="row" style="padding-left: 45px;">
							<div class="col-md-3">
								<input type="text" class="form-text" name="incomeRefence" id="incomeRefence" value="0" placeholder="Reference">
							</div>
							<div class="col-md-3 input-group input-append date" id="opendDate" >
								<input type="text" id="tripsheetIncomeDate" class="form-text" name="incomeDate" required="required" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" readOnly="readOnly"/>
								<div class="input-group-addon add-on"> <span class="fa fa-calendar"></span></div>
							</div>
						</div>		
						<br>
						<div class="row">
							<label class="L-size control-label" style="text-align-last: center;" id="Type">Remark :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="remark" maxlength="249" name="remark" placeholder="Enter description" />
							</div>
						</div>
						<br>
						<div class="input_fields_wrap">
							<button class="add_field_button btn btn-success">
								<i class="fa fa-plus"></i>
							</button>
						</div>
					</div>
					<div class="modal-footer">
						<fieldset class="form-actions">
							<input class="btn btn-success"
								onclick="return validateIncome();" name="commit" type="submit"
								value="Save Expense" id="expenseSave">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</fieldset>
					</div>
				</div>
				</form>
			</div>
		</div>
</c:if>
		
		
	</section>
	<script type="text/javascript" 
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"/> "></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js"/> "></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetIncome.js"/> "></script>
		<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
</div>
a
