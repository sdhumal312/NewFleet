<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">	
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
									Trip Payment</a> / <small>Add A/C Expense TripSheet</small>
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
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="userId" value="${userId}">
						<input type="hidden" id="vid" value="${TripSheet.vid}">
						<input type="hidden" id="tripSheetId" value="${TripSheet.tripSheetID}">
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
							<div class="row">
							   <c:if test="${configuration.includeRenewalAsExpense}">
									<div class="pull-left">
										<span><b>Pending Renewal :</b> <a href="#" onclick="getPendingRenewal();"> ${renewalCount}</a></span>
									</div>
							   </c:if>
								 <c:if test="${configuration.includeServiceEntryAsExpense}">
									<div class="pull-right">
										<span style="padding-right: 20px;"><b>Pending Service Entry :</b> <a href="#" onclick="getPendingServiceEntries(${serviceCount});">${serviceCount}</a></span>
									</div>
								 </c:if>
							</div>
							<br/>
							<c:if test="${!empty TripSheetExpense}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Expense Name</th>
														<c:if test="${configuration.showCreditAndVendorAtExpense}">
															<th class="fit ar">Tally Company Name</th>
														</c:if>
														<c:if test="${!configuration.showCreditAndVendorAtExpense}">
															<th class="fit ar">Type</th>
															<th class="fit ar">Place</th>
															<th class="fit ar">Ref</th>
														</c:if>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Created</th>
														<c:if test="${configuration.showCreditAndVendorAtExpense}">
															<th class="fit ar">Vendor</th>
															<th class="fit ar">Remark</th>
														</c:if>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripSheetExpense}"
														var="TripSheetExpense">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripSheetExpense.expenseName}" /></td>
															
															<c:if test="${configuration.showCreditAndVendorAtExpense}">
																<td class="fit ar"><c:out
																	value="${TripSheetExpense.tallyCompanyName}" /></td>
															</c:if>	
																	
															<c:if test="${!configuration.showCreditAndVendorAtExpense}">		
																<td class="fit ar"><c:choose>
																		<c:when
																			test="${TripSheetExpense.expenseFixed == 'FIXED'}">
																			<small class="label label-success"><c:out
																					value="${TripSheetExpense.expenseFixed}" /></small>
																		</c:when>
																		<c:otherwise>
																			<small class="label label-warning"><c:out
																					value="${TripSheetExpense.expenseFixed}" /></small>
																		</c:otherwise>
																	</c:choose></td>
																<td class="fit ar"><c:out
																		value="${TripSheetExpense.expensePlace}" /></td>
																
																<td class="fit ar"><c:out
																		value="${TripSheetExpense.expenseRefence}" /></td>
															</c:if>
															
															<td class="fit ar"><c:out
																	value="${TripSheetExpense.expenseAmount}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheetExpense.created}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheetExpense.vendorName}" /></td>		
															<td class="fit ar">
																	<a href="#" onclick="openRemarkUpdateModal('${TripSheetExpense.remark}', '${TripSheetExpense.tripExpenseID}');">${TripSheetExpense.remark}</a>
																</td>
															<c:if test="${!TripSheetExpense.avoidToDelete}">
															<td class="fit ar"><a
																href="removeExpenseAccount.in?tripExpenseID=${TripSheetExpense.tripExpenseID}"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>
															</c:if>	
															<c:if test="${TripSheetExpense.avoidToDelete}">
																<td class="fit ar"><a
																	href="#"
																	data-toggle="tip" data-original-title="Do Nothing">${TripSheetExpense.expenseFixed}</a></td>
															</c:if>	
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr class="breadcrumb">
													</tr>
												</tfoot>
											</table>
										</div>
										<div class="col-md-offset-4">
											<table class="table">
												<tbody>
													<tr data-object-id="" class="ng-scope">
														<td class="key"><h4>Total Expense:</h4></td>
														<td class="value"><h4>
																<i class="fa fa-inr"></i> ${expenseTotal}
															</h4></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</c:if>
							
							<input type="hidden" id="allowShortCut" value="${AllowShortCutInTripSheet}"/>
							<input type="hidden" id="showCreditAndVendorAtExpense" value="${configuration.showCreditAndVendorAtExpense}"/>

							<c:if test="${configuration.showCreditAndVendorAtExpense}">
							<form action="updateExpenseAccount.in" method="post">
								<input type="hidden" name="TripSheetID"
									value="${TripSheet.tripSheetID}">
								<input type="hidden" name="voucherDateStr"
									value="${TripSheet.voucherDateStr}">		
								<div class="form-horizontal">

									<fieldset>
										<legend>New Expense Details 
										
											<div class="pull-right">
												<button type="button" class="btn btn-success" data-toggle="modal"
												data-target="#addManufacturer" data-whatever="@mdo">
												<span class="fa fa-plus" id="AddJobType"> Add Expense</span>
												</button>
											</div>
										
										</legend>
									</fieldset>
									
									<div class="modal fade" id="addManufacturer" role="dialog">
											<div class="modal-dialog" style="width:750px;">
												<!-- Modal content-->
												<div class="modal-content">
													
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">&times;</span>
															</button>
															<h4 class="modal-title" id="JobType">Add Expense </h4>
																
														</div>
														<div class="modal-body">
															
														<div class="panel panel-success">
								        		    		<div class="panel-body"> 
																<div class="row1" id="grpmanufacturer" class="form-group">
																	
																	<div class="row" class="form-group">
																		<label for="manufacurer">Is Credit :<abbr title="required">*</abbr>
																		</label>
																		<input type="checkbox" name="creditCheckBox" id ="creditCheckBox" onclick="setCredit('creditCheckBox', 'isCredit');">
																		<input type="hidden" name="isCredit" id ="isCredit" value="false">
								        		    				</div>
								        		    			
																	<label class="L-size control-label" for="manufacurer">Vendor Name :<abbr title="required">*</abbr>
																	</label>
																	<div class="I-size">
																	<input type="hidden" id="vendorId" name="vendorId" style="width: 100%;" 
																	 value="0" placeholder="Please Enter Vendor Name" />
																	</div>
																</div>
															
																<div class="row">
																	<label class="L-size control-label" id="Type">Expense Name :
																	<abbr title="required">*</abbr>
																	</label>
																	<div class="I-size">
																		<select class="select2" style="width: 100%;"
																			name="expenseName" id="Expense" required="required">
																		</select>
																	</div>
																</div>
																<br/>
																
																<div class="row" id="grpmanufacturer" class="form-group">
																
																	<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr>
																	</label>
																	<div class="I-size">
																	<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" 
																	 placeholder="Please Enter Tally Company Name" />
																	</div>
																</div>
																
																<br/>
																
																<div class="row">
																	<div class="col-md-3">
																		<label for="manufacurer">Amount :<abbr title="required">*</abbr>
																		</label>
																		<input type="number" class="form-text" name="Amount" id ="Amount"
																			placeholder="Amount" min="0" required="required">
																	</div>
																	<div class="col-md-3">
																		<label for="manufacurer">Reference :<abbr title="required">*</abbr>
																		</label>
																		<input type="text" class="form-text" name="expenseRefence"
																			placeholder="Reference" value="0">
																	</div>
																	
																</div>
																<br/>
														
																<div class="row">
																	<label class="L-size control-label" for="manufacurer">Remark :</label>
																	<div class="I-size">
																		<input type="text" class="form-text" id="description"
																		maxlength="249" name="description" value=" "
																		placeholder="Enter Remark" />
																	</div>
																</div>																
																
															</div>
															<br> <label class="error" id="errorINEACH"
																style="display: none"></label>
														</div>	
															
															<!-- <div class="row1">
																<div class="input_fields_wrap">
																	<button class="add_field_button btn btn-info"
																		data-toggle="tip"
																		data-original-title="Click add one more upholstery type">
																		<i class="fa fa-plus"></i> Add More
																	</button>
																</div>
															</div> -->
															
														</div>
														<div class="modal-footer">
															<fieldset class="form-actions">
																<input class="btn btn-success"  onclick="return validateExpense();"
																	name="commit" type="submit" value="Save Expense" id="expenseSave"> 
																<button type="button" class="btn btn-default"
																	data-dismiss="modal">
																	<span id="Close"><spring:message
																			code="label.master.Close" /></span>
																</button>	
															</fieldset>
														</div>
												</div>
											</div>
										</div>
									
								</div>
							</form>
							</c:if>
							
							<c:if test="${!configuration.showCreditAndVendorAtExpense}">
							<form action="updateExpenseAccount.in" method="post">
								<input type="hidden" name="TripSheetID"
									value="${TripSheet.tripSheetID}">
									<input type="hidden" name="isCredit" id="isCredit" value="false">
									<input type="hidden" name="vendorId" id="NovendorId" value="0">
									<input type="hidden" name="tallyCompanyId" id="tallyCompanyId1" value="0">
									<input type="hidden" name="description" id="description" value=" ">
									<input type="hidden" name="voucherDate" id="voucherDate1" value="">
								<div class="form-horizontal">
									<fieldset>
										<legend>New Expense Details
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
													<span>  Alt + R (Remove) </span>
												</div>
											</div> 
										</legend>
										<div class="row1">
											<label class="L-size control-label"></label>

											<div class="col-md-9">
												<div class="col-md-4">
													<label class="control-label">Expense Name</label>

												</div>
												<div class="col-md-2">
													<label class="control-label">Expense Amount</label>

												</div>
												<div class="col-md-2">
													<label class="control-label">Reference</label>
												</div>
											</div>
										</div>
										<div class="row1">

											<div class="col-md-4">
												<select class="form-text select2" style="width: 100%;"
													name="expenseName" id="Expense" required="required">

												</select>
											</div>
											<div class="col-md-3">
												<input type="number" class="form-text" name="Amount" id="Amount"
													placeholder="Amount" min="0" required="required">
											</div>

											<div class="col-md-3">
												<input type="text" class="form-text" name="expenseRefence"
													placeholder="Reference" value="0">
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
											<input class="btn btn-success" data-disable-with="Saving..." onclick=" return validateExpense();"
												name="commit" type="submit" value="Save Expense" id="expenseSave"> <a
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
		
		<div class="modal fade" id="ExpenseCombineDetails" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1000px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Trip Expenses</h4>
					</div>
					<div class="modal-body">
					
					<fieldset>
					<div class="box">
					<table class="box-body" id="modelBodyExpenseDetails" border="1" width="100%">
					
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
		<div class="modal fade" id="renewalPending" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Renewal Reminder Details</h4>
						</div>
						<div class="modal-body">
							
							<table id="dataTable" style="width: 100%; display: none;" class="table-responsive table">
								<thead>
									<tr>
										<th>Sr</th>
										<th>Renewal Number</th>
										<th>Vehicle</th>
										<th>Renewal Type</th>
										<th>Renewal SubType</th>
										<th>Renewal Amount</th>
										<th>Vendor</th>
										<th>Type</th>
										<th>Tally Company</th>
										<th>Expense Head</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									
								</tbody>
							</table>	
								
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success"
								>
								<span id="renewalSave" onclick="saveRenewalPendingDetails();">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="serviceEntiresPending" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Service Entries Details</h4>
						</div>
						<div class="modal-body">
							
							<table id="sdataTable" style="width: 100%; display: none;" class="table-responsive table">
								<thead>
									<tr>
										<th>Select</th>
										<th>SE Number</th>
										<th>Vehicle</th>
										<th>Service Amount</th>
										<th>Vendor</th>
										<th>Type</th>
										<th>Tally Company</th>
										<th>Expense Head</th>
									</tr>
								</thead>
								<tbody id="stableBody">
									
								</tbody>
							</table>	
								
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success">
								<span id="saveServiceExpense" onclick="saveServiceExpenseToTrip();">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="expenseRemark" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1000px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Trip Expenses</h4>
					</div>
					<div class="modal-body">
					<input type="hidden" id="tripSheetExpenseId">
					<fieldset>
						<div class="box">
							<div class="row">
								<label class="L-size control-label" for="manufacurer">Remark :</label>
									<div class="I-size">
										<input type="text" class="form-text" id="updateRemark" maxlength="250" name="remark" />
									</div>
							</div>								
						</div>
					</fieldset>
					<br />
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-info"
						>
						<span onclick="updateExpenseRemark();">Update</span>
					</button>
					<button type="button" class="btn btn-default"
						data-dismiss="modal">
						<span id="Close">Close</span>
					</button>
					</div>
					</div>
					</div>
			</div>
		
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
		<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetExpense.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
</div>