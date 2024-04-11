<%@ include file="taglib.jsp"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close</a> /
						<a href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment</a> /
						<a href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">A/C Closed</a>
						/ <small>Show TripSheet</small>
					</div>


					<div class="col-md-off-5">
						<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">TS-</span></span> <input class="form-text"
										id="searchTripSheet" name="tripStutes" type="number" min="1" onkeyup="searchTripSheetShow(event);"
										required="required" placeholder="TS-ID eg:7878" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm" onclick="searchTripSheet();">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
						<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addTripSheetEntries.in"/>"> <span
								class="fa fa-plus"></span> Create TripSheet
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
							<a href="showTripSheetPrint.in?id=${TripSheet.tripSheetID}"
								target="_blank" class="btn btn-default"><i
								class="fa fa-print"></i> Print</a>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('SHOW_TRIPSHEET_DOCUMENT')">
							<a href="#" onclick="editDocModal();" class="btn btn-default"><i
								class="fa fa-file"></i> TripSheet Document</a>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">

						<sec:authorize access="!hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
							<spring:message code="message.unauth">
								<a href="#" id="printTripsheet" class="btn btn-default"><span
									class="fa fa-print"></span></a>
							</spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
							<div id="div_printTripsheet">
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
										<a style="color: #000000;"
											href="showVehicle.in?vid=${TripSheet.vid}" data-toggle="tip"
											data-original-title="Click Vehicle Info"> <c:out
												value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>
								<div class="row">
									<h4 align="center">${TripSheet.routeName}</h4>
								</div>
								<div class="secondary-header-title">
									<table class="table">
										<tbody>
											<tr>
												<td>Date of Journey : <a data-toggle="tip"
													data-original-title="Trip Open Date"> <c:out
															value="${TripSheet.tripOpenDate}  TO" /></a> <a
													data-toggle="tip" data-original-title="Trip Close Date">
														<c:out value="  ${TripSheet.closetripDate}" />
												</a></td>
												<td>Group Service : <a data-toggle="tip"
													data-original-title="Group Service"><c:out
															value="${TripSheet.vehicle_Group}" /></a></td>
												<td>Booking No : <a data-toggle="tip"
													data-original-title="Booking No"> <c:out
															value="${TripSheet.tripBookref}" /></a></td>
											</tr>
											<tr>
												<td>Trip Route Point : <a data-toggle="tip"
													data-original-title="Fixed Point"> <c:out
															value="${TripSheet.routeAttendancePoint}" /></a></td>
												<td>Trip Route Volume : <a data-toggle="tip"
													data-original-title="Volume Point"> <c:out
															value="${TripSheet.routeTotalLiter}" /></a></td>
												<td>Driver 1 : <a data-toggle="tip"
													data-original-title="Driver 1"> <c:out
															value="${TripSheet.tripFristDriverName } ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName} " /></a> / <c:out
														value="${TripSheet.tripFristDriverMobile }" /></td>
											</tr>
											<tr>
												<td>Driver 2 : <a data-toggle="tip"
													data-original-title="Driver 2"><c:out
															value="${TripSheet.tripSecDriverName}  ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}" /></a> / <c:out
														value="${TripSheet.tripSecDriverMobile}" /></td>
												<td>Cleaner : <a data-toggle="tip"
													data-original-title="Cleaner"><c:out
															value="${TripSheet.tripCleanerName}" /></a> / <c:out
														value="${TripSheet.tripCleanerMobile}" /></td>
														<td></td>
												
											</tr>
											<tr>
												<td>Driver 1 Route Point : <a data-toggle="tip"
													data-original-title="Driver 1"><c:out
															value="${TripSheet.tripFristDriverRoutePoint}" /></a></td>
												<td>Driver 2 Route Point : <a data-toggle="tip"
													data-original-title="Driver 2"><c:out
															value="${TripSheet.tripSecDriverRoutePoint}" /></a></td>
												<td>Cleaner Route Point : <a data-toggle="tip"
													data-original-title="Cleaner"><c:out
															value="${TripSheet.tripCleanerRoutePoint}" /></a></td>
											</tr>
											<tr>
												<td>Opening KM: <a data-toggle="tip"
													data-original-title="Opening KM"><c:out
															value="${TripSheet.tripOpeningKM}" /></a></td>
												<td>Closing KM: <a data-toggle="tip"
													data-original-title="closing KM"> <c:out
															value="${TripSheet.tripClosingKM}" /></a></td>

												<c:choose>
													<c:when
														test="${TripSheet.tripClosingKMStatusId == 1}">
														<td>Meter : <a data-toggle="tip"
															data-original-title="Meter Not Working"> <c:out
																	value="Meter Not Working " /></a></td>

													</c:when>
													<c:otherwise>
														<td>Usage KM: <a data-toggle="tip"
															data-original-title="usage KM"> <c:out
																	value="${TripSheet.tripUsageKM}" /></a></td>

													</c:otherwise>
												</c:choose>
											</tr>
											<tr>
												<td>Dispatch By: <a data-toggle="tip"
													data-original-title="dispatched By"><c:out
															value="${TripSheet.dispatchedBy}" /></a></td>
												<td>Dispatch Location: <a data-toggle="tip"
													data-original-title="location"> <c:out
															value="${TripSheet.dispatchedLocation}" /></a></td>
												<td>Dispatch time: <a data-toggle="tip"
													data-original-title="Time"><c:out
															value="${TripSheet.dispatchedByTime}" /></a></td>
											</tr>
											<tr>
												<td>Closed By: <a data-toggle="tip"
													data-original-title="closed By"><c:out
															value="${TripSheet.closedBy}" /></a></td>
												<td>Closed Location: <a data-toggle="tip"
													data-original-title="location"> <c:out
															value="${TripSheet.cloesdLocation}" /></a></td>
												<td>Closed Time: <a data-toggle="tip"
													data-original-title="closed By"><c:out
															value="${TripSheet.closedByTime}" /></a></td>
											</tr>
											
											<c:if test="${configuration.showSubroute}">
											<tr>
												<td>Sub-Route : <a data-toggle="tip"
													data-original-title="SubRoute"><c:out
															value="${TripSheet.subRouteName}" /></a> 
												</td>
												<td></td>
												<td></td>
											</tr>
											</c:if>
											<c:if test="${configuration.showLoadType}">
												<tr>
													<td colspan="3">Load Type: <a data-toggle="tip"
														data-original-title="Load Type"><c:out
																value="${TripSheet.loadTypeName}" /></a>
													</td>													
												</tr>
											</c:if>
											<tr>
												<td >Route Remark: <a data-toggle="tip"
													data-original-title="Route Remark"><c:out
															value="${TripSheet.routeRemark}" /></a></td>
												<c:if test="${configuration.showPODdetails}">			
													<td >Number of POD: <a data-toggle="tip"
														data-original-title="Number of POD"><c:out
																value="${TripSheet.noOfPOD}" /></a></td>
												</c:if>
												<c:if test="${!configuration.showPODdetails}">
													<td></td>
												</c:if>
												<c:if test="${configuration.showPODdetails}">
													<td >Received POD: <a data-toggle="tip"
														data-original-title="Receive Number of POD"><c:out
																value="${TripSheet.receivedPOD}" /></a></td>
												</c:if>
												<c:if test="${!configuration.showPODdetails}">
													<td></td>
												</c:if>							
											</tr>
											
										</tbody>
									</table>
								</div>
								<c:if test="${!empty TripSheetAdvance}">
									<table class="table table-bordered table-striped">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">No</th>
												<th class="fit ar">Advance Place</th>
												<th class="fit ar">Advance PaidBy</th>
												<th class="fit ar">Reference</th>
												<th class="fit ar">Amount</th>
												<th class="fit ar">Advance Date</th>
											</tr>
										</thead>
										<tbody>
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${TripSheetAdvance}" var="TripSheetAdvance">
												<tr data-object-id="" class="ng-scope">
													<td class="fit">
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>
													<td class="fit ar"><c:out
															value="${TripSheetAdvance.advancePlace}" /></td>
													<td class="fit ar"><c:out
															value="${TripSheetAdvance.advancePaidby}" /></td>

													<td class="fit ar"><c:out
															value="${TripSheetAdvance.advanceRefence}" /></td>
													<td class="fit ar"><c:out
															value="${TripSheetAdvance.advanceAmount}" /></td>
													<td class="fit ar"><c:out
															value="${TripSheetAdvance.createdStr}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="col-md-offset-5">
										<table class="table">
											<tbody>
												<tr data-object-id="" class="ng-scope">
													<td class="key"><h4>Total Advance :</h4></td>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> ${advanceTotal}
														</h4></td>
												</tr>
											</tbody>
										</table>
									</div>
								</c:if>
								
								<c:if test="${!configuration.showCombineTripDetails}">
								<c:if test="${!empty TripSheetExpense}">
									<table class="table table-bordered table-striped">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">No</th>
												<th class="fit ar">Expense Name</th>
												<th class="fit ar">Type</th>
												<th class="fit ar">Place</th>
												<th class="fit ar">Ref</th>
												<th class="fit ar">Amount</th>
												<th class="fit ar">Expense Date</th>
												<c:if test="${configuration.downloadTripExpenseDocument}">
													<th class="fit ar">Doc</th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${TripSheetExpense}" var="TripSheetExpense">

												<tr data-object-id="" class="ng-scope">
													<td class="fit">
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>
													<td class="fit ar"><c:out
															value="${TripSheetExpense.expenseName}" /></td>
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
													<td class="fit ar"> <fmt:formatNumber value="${TripSheetExpense.expenseAmount}" pattern="#.##"/>
															 </td>
													<td class="fit ar"><c:out
															value="${TripSheetExpense.createdStr}" /></td>
															
													<c:if test="${configuration.downloadTripExpenseDocument  && TripSheetExpense.tripSheetExpense_document_id != null}">
														<td class="fit ar">
															<a href="${pageContext.request.contextPath}/download/TripsheetExpenseDocument/${TripSheetExpense.tripSheetExpense_document_id}.in">
																<span class="fa fa-download"> Document</span>
															</a>
														</td>
													</c:if>		
												</tr>


											</c:forEach>

										</tbody>
									</table>
									<div class="col-md-offset-5">

										<table class="table">
											<tbody>
												<tr data-object-id="" class="ng-scope">
													<td class="key"><h4>Total Expense :</h4></td>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> ${expenseTotal}
														</h4></td>

												</tr>
											</tbody>
										</table>
									</div>
								</c:if>
								</c:if>
								
								<c:if test="${configuration.showCombineTripDetails}"> 	
										<tr>
											<td><c:if test="${!empty ExpenseCombineList}">

													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Expense Name</th>
																<th class="fit ar">Amount</th>
															</tr>
															</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${ExpenseCombineList}"
																var="ExpenseCombineList">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	
																	<td class="fit ar">
																		<c:out value="${ExpenseCombineList.expenseName}" />
																	</td>
																	
																	<td class="fit ar">
																		<a 
																		href="#"
																		onclick=" expensePopUp(${TripSheet.tripSheetID},${ExpenseCombineList.expenseId});"> 
																		<c:out value="${ExpenseCombineList.expenseAmount}" />
																		</a>	
																	</td>
																	
																	</tr>
																	</c:forEach>
																	<tr >
																		<td colspan="2" class="key"><h4>Total Expense
																				:</h4>
																		</td>
																		<td  colspan="1" class="value"><h4>
																				<i class="fa fa-inr"></i> ${expenseTotal}
																			</h4>
																		</td>
																	</tr>
														</tbody>
													</table>
												</c:if></td>
										</tr>
										</c:if>	
								
								<c:if test="${!empty TripSheetIncome}">
									<table class="table table-bordered table-striped">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">No</th>
												<th class="fit ar">Income Name</th>
												<th class="fit ar">Income Place</th>
												<th class="fit ar">Collected By</th>
												<th class="fit ar">Reference</th>
												<th class="fit ar">Amount</th>
												<th class="fit ar">Income Date</th>
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
													<td class="fit ar"><c:out
															value="${TripSheetIncome.incomeCollectedBy}" /></td>

													<td class="fit ar"><c:out
															value="${TripSheetIncome.incomeRefence}" /></td>
													<td class="fit ar"> <fmt:formatNumber pattern="#.##" value="${TripSheetIncome.incomeAmount}"/>
															 </td>
													<td class="fit ar"><c:out
															value="${TripSheetIncome.createdStr}" /></td>
												</tr>


											</c:forEach>

										</tbody>
									</table>
									<div class="col-md-offset-5">

										<table class="table">
											<tbody>
												<tr data-object-id="" class="ng-scope">
													<td class="key"><h4>Total Income :</h4></td>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> ${incomeTotal}
														</h4></td>

												</tr>
											</tbody>
										</table>
									</div>

								</c:if>
								
								<c:if test="${!empty TripsheetDueAmount}">
									<table class="table table-bordered table-striped">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">No</th>
												<th class="fit ar">Name</th>
												<th class="fit ar">Job Type</th>
												<th class="fit ar">Approx Date</th>
												<th class="fit ar">Due Date</th>
												<th class="fit ar">Due Amount</th>
												<th class="fit ar">Balance Amount</th>
											</tr>
										</thead>
										<tbody>
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${TripsheetDueAmount}" var="TripsheetDueAmount">

												<tr data-object-id="" class="ng-scope">
													<td class="fit">
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>
													<td class="fit ar"><c:out
															value="${TripsheetDueAmount.driver_firstname} - ${TripsheetDueAmount.driver_Lastname}" /></td>
													<td class="fit ar"><c:out
														 	value="${TripsheetDueAmount.driJobType}" /></td>
													<td class="fit ar"><c:out
															value="${TripsheetDueAmount.approximateDateStr}" /></td>
													<td class="fit ar"><c:out
															value="${TripsheetDueAmount.dueDateStr}" /></td>
													<td class="fit ar"><c:out
															value="${TripsheetDueAmount.dueAmount}" /></td>
													<td class="fit ar"><c:out 
															value="${TripsheetDueAmount.balanceAmount}" /></td>				
												</tr>

											</c:forEach>

										</tbody>
									</table>
									<div class="col-md-offset-5">
										<table class="table">
											<tbody>
												<tr data-object-id="" class="ng-scope">
													<td class="key"><h4>Total Due Amount :</h4></td>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> ${TotalDueAmount}
														</h4>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</c:if>	

								<table class="table table-bordered table-striped">
									<thead>
										<tr class="breadcrumb">
											<th class="fit ar">Paid To</th>
											<th class="fit ar">Paid By</th>
											<th class="fit ar">Amount</th>
											<th class="fit ar">Close Date</th>
											<th class="fit ar">Status</th>
										</tr>
									</thead>
									<tbody>

										<tr data-object-id="" class="ng-scope">

											<td class="fit ar"><c:out
													value="${TripSheet.closeTripStatus}" /></td>
											<td class="fit ar"><c:out
													value="${TripSheet.closeTripNameBy}" /></td>
											<td class="fit ar"> <fmt:formatNumber value="${TripSheet.closeTripAmount}" pattern="#.##"/>
													 </td>
											<td class="fit ar"><c:out
													value="${TripSheet.closetripDate}" /></td>
											<td class="fit ar"><c:choose>

													<c:when test="${TripSheet.tripStutesId ==1}">
														<span class="label label-pill label-info"><c:out
																value="${TripSheet.tripStutes}" /></span>
													</c:when>
													<c:when test="${TripSheet.tripStutesId ==3}">
														<span class="label label-pill label-success"><c:out
																value="${TripSheet.tripStutes}" /></span>
													</c:when>
													<c:otherwise>
														<span class="label label-pill label-warning"><c:out
																value="${TripSheet.tripStutes}" /></span>
													</c:otherwise>
												</c:choose></td>
										</tr>

									</tbody>
								</table>
							</div>

						
							<input type="hidden" id="tripSheetId" value="${TripSheet.tripSheetID}">
							<input type="hidden" id="voucherDate" value="${TripSheet.voucherDateStr}">
							<input type="hidden" id="tallyId" value="${TripSheet.tallyCompanyId}">
							<input type="hidden" id="allowTallyIntegration" value="${configuration.allowTallyIntegration}">
							<input type="hidden" id="companyId" value="${companyId}">
							<input type="hidden" id="userId" value="${paidById}">
							<input type="hidden" id="allowAccountCloseWithoutIncome" value="${configuration.allowAccountCloseWithoutIncome}">
							<input type="hidden" id="tripsheetTotalIncome" value="${TripSheet.tripTotalincome}">
							<input type="hidden" id="permissionToClose" value="${permissionToClose}">
							<input type="hidden" id="fastagFound" value="${fastagFound}">
							<input type="hidden" id="gpsUsageFound" value="${gpsUsageFound}">
							<input type="hidden" id="allowCloseWithNoGPSandFastag" value="${configuration.allowCloseWithNoGPSandFastag}">
								<div class="form-horizontal">
									<table class="table">
										<tbody>
											<tr data-object-id="" class="ng-scope">
												<td class="key"><h4>
														Balance ( Income - Expense ) <i class="fa fa-inr"></i> =
													</h4></td>
												<td class="value"><h4>
												<fmt:formatNumber value="${TripSheet.tripTotalincome - TripSheet.tripTotalexpense}" pattern="#.##" />
												
													</h4></td>
											</tr>
										</tbody>
									</table>
									<fieldset>
										<legend>For office Use</legend>

										<div class="row1">
											<div class="col-md-6">
												<label class="L-size control-label">Trip Account
													Closed By :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">

													<input class="string required form-text" value="${paidBy}"
														name="closeACCTripNameBy" readonly="readonly"
														maxlength="150" type="text" required="required"
														placeholder="" onkeypress="return Isclosepaidto(event);"
														ondrop="return false;"> 
													<input type="hidden" name="closeACCTripNameById" value="${paidById}" />	
														<label class="error"
														id="errorClosepaidto" style="display: none"> </label>

												</div>
											</div>
										</div>
										<div class="row1">
											<div class="col-md-6">
												<label class="L-size control-label">Reference :<abbr
													title="required">*</abbr></label>
												<div class="I-size">

													<input class="string required form-text"
														id="closeACCTripReference" maxlength="10" type="text"
														required="required" placeholder="Ex: XXX777"
														onkeypress="return Isclosepaidto(event);"
														ondrop="return false;"> <label class="error"
														id="errorClosepaidto" style="display: none"> </label>

												</div>
											</div>
											<div class="col-md-5">
												<label class="L-size control-label">Amount :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
												<input class="string required form-text" type="text" id="closeACCTripAmount" 
													value='<fmt:formatNumber  pattern="#.##" value="${TripSheet.tripTotalincome - TripSheet.tripTotalexpense}" />'
													maxlength="10"  readonly="readonly">
												</div>
											</div>
										</div>
										<div class="box-footer h-padded">
											<fieldset class="col-md-offset-3">
												<input class="btn btn-success" data-disable-with="Saving..."
													name="commit" type="submit" onclick="closeTripSheetAccount();" value="Close Trip Account">
												<a class="btn btn-link"
													href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
											</fieldset>
										</div>
									</fieldset>
								</div>
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
		
	</section>
	<div class="modal fade" id="editTripSheetDoc" role="dialog" >
		<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
			<div class="modal-dialog" style="width:1200px;">
				<!-- Modal content-->
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">TripSheet Document</h4>
						</div>
						<input type="hidden" id="tripSheetDocumentId" >
						<div class="modal-body">
						<div class="row">
							<div class="form-group column">
								<label for="comment">TripSheet Document:</label>
								<input type="file" name="input-file-preview" id="tripDocument" data-max-size="512000" /> 
								<span id="renewalFileIcon" 	class=""></span>
								<div id="renewalFileErrorMsg" class="text-danger"></div>
								<span class="help-block">Add an optional document</span>
							</div>
							<div class="form-group">
								<label for="comment">TripSheet Document:</label>
								<a href ="#" onclick="downloadTripSheetDocument();">
								<span class="fa fa-download"> Doc</span>
								</a>
							</div>
							</div>
						</div>
						<div class="modal-footer">
						
						<input type="button" value="Update TripSheet Doc" onclick="updateTripSheetDocument();" id="btnSubmitS" class="btn btn-success" />
						<a class="btn btn-link" data-dismiss="modal">Cancel</a>
						</div>
				</div>
			</div>
			</form>
		</div>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetCombineDetails.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetAccountClose.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetDetails.js" />"></script>		
</div>