<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">

	<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
		<section class="invoice">
			<div class="row">
				<table class="table table-hover table-bordered table-striped">
					<tbody>
						<tr>
							<td><c:choose>
									<c:when test="${company.company_id != null}">
										<img
											src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
											class="img-rounded" alt="Company Logo" width="280"
											height="40" />

									</c:when>
									<c:otherwise>
										<i class="fa fa-globe"></i>
										<c:out value="${company.company_name}" />
									</c:otherwise>
								</c:choose></td>
							<td>Print By: ${company.firstName}_${company.lastName}</td>
						</tr>
						<tr>
							<td colspan="2">Branch :<c:out
									value=" ${company.branch_name}  , " /> Department :<c:out
									value=" ${company.department_name}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="row invoice-info">
				<h3>TripSheet ${TripSheet.tripSheetNumber}</h3>
			</div>

			<div class="row ">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<tbody>
							<tr class="row">
								<th>Vehicle :</th>
								<td><h3><c:out value="${TripSheet.vehicle_registration}" /></h3></td>
								<c:if test="${!configuration.showTripBookngRef}">
									<th></th>
									<td></td>
								</c:if>
								<c:if test="${configuration.showTripBookngRef}">
									<th>Booking:</th>
									<td><c:out value="${TripSheet.tripBookref}" /></td>
								</c:if>
							</tr>
							<tr class="row">
								<th>Route :</th>
								<td><c:out value="${TripSheet.routeName}" /></td>

								<th>Date :</th>
								<td><c:out value="${TripSheet.tripOpenDate}  TO" /> <br>
									<c:out value="  ${TripSheet.closetripDate}" /></td>
							</tr>

							<tr class="row">
								<th>Driver 1:</th>
								<td><c:out value="${TripSheet.tripFristDriverName}" /> / <c:out
										value="${TripSheet.tripFristDriverMobile}" /></td>
								<c:if test="${!configuration.driver2}">
									<th></th>
									<td></td>
								</c:if>	
								<c:if test="${configuration.driver2}">
									<th>Driver 2:</th>
									<td> 
										<c:out value="${TripSheet.tripSecDriverName}" /> / <c:out value="${TripSheet.tripSecDriverMobile}" />
									</td>
								</c:if>					
								
							</tr>
							<tr class="row">
								<c:if test="${!configuration.cleaner}">
									<th></th>
									<td></td>
								</c:if>		
								<c:if test="${configuration.cleaner}">
									<th>Cleaner :</th>
									<td><c:out value="${TripSheet.tripCleanerName}" /> / <c:out
											value="${TripSheet.tripCleanerMobile}" /></td>
								</c:if>		
									<th>Trip Route Volume :</th>
									<td><c:out value="${TripSheet.routeTotalLiter}" />  </td>
							</tr>
							<tr class="row">
								<th>Open KM:</th>
								<td><c:out value="${TripSheet.tripOpeningKM}" /></td>
								<th>Trip Fixed KM :</th>
								<td><c:out value="${TripSheet.routeApproximateKM}" /></td>
							</tr>
							<c:if test="${configuration.showSubroute}">
							<tr class="row">
								<th>SubRoute Service:</th>
								<td><c:out value="${TripSheet.subRouteName}" /></td>
							</tr>
							</c:if>
							<c:if test="${configuration.showPODdetails}">
								<tr class="row">
									<th>Number of POD:</th>
									<td><c:out value="${TripSheet.noOfPOD}" /></td>
								</tr>
							</c:if>
							<tr class="row">
								<th>Route Remark:</th>
								<td colspan="3"><c:out value="${TripSheet.routeRemark}" /></td>
								
							</tr>
							<c:if test="${configuration.showLoadType}">
							<tr class="row">
								<th>Load Type:</th>
								<td colspan="3"><c:out value="${TripSheet.loadTypeName}" /></td>								
							</tr>
							</c:if>
							
						</tbody>
					</table>
				</div>
			</div>

			<br>
			
			<c:if test="${!configuration.showCombineTripDetails}"> 
			    <c:if test="${configuration.showExpenseInPrintBeforeClosing}"> 
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
															<c:if test="${configuration.showTypePlaceRefInIncomeExpense}">
																<th class="fit ar">Type</th>
																<th class="fit ar">Place</th>
																<th class="fit ar">Ref</th>
															</c:if>	
														</c:if>
														<th class="fit ar">Expense Date</th>
														<th class="fit ar">Amount</th>
														<c:if test="${configuration.showCreditAndVendorAtExpense}">
																	<th class="fit ar">Vendor</th>
														</c:if>
														<c:if test="${configuration.showPFAmount}">
																<th class="fit ar">PF</th>
																<th class="fit ar">ESI</th>
																<th class="fit ar">Balance</th>						
															</c:if>	
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
																<c:if test="${configuration.showTypePlaceRefInIncomeExpense}">
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
																	</c:choose>
																</td>
																<td class="fit ar"><c:out
																		value="${TripSheetExpense.expensePlace}" /></td>
															
																<td class="fit ar"><c:out
																		value="${TripSheetExpense.expenseRefence}" /></td>
																</c:if>		
															</c:if>	
															
															<td class="fit ar"><c:out
																			value="${TripSheetExpense.created}" /></td>			
															<td class="fit ar"><c:out
																	value="${TripSheetExpense.expenseAmount}" /></td>
															<c:if test="${configuration.showCreditAndVendorAtExpense}">
																		<td class="fit ar"><c:out
																				value="${TripSheetExpense.vendorName}" /></td>
															</c:if>	
															<c:if test="${configuration.showPFAmount}">
																		<td class="fit ar"><c:out
																				value="${TripSheetExpense.pfAmount}" /></td>
																	<td class="fit ar"><c:out
																				value="${TripSheetExpense.esiAmount}" /></td>
																	<td class="fit ar"><c:out
																				value="${TripSheetExpense.balanceAmount}" /></td>						
															</c:if>			
														</tr>
													</c:forEach>
												</tbody>
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
							  </c:if>
							</c:if>
							<c:if test="${configuration.driverPenaltyDetails}">
											<tr>
												<td>
													<c:if test="${!empty DriverAdvanvce}">
														<table class="table table-bordered table-striped">
															<thead>
																<tr class="breadcrumb">
																	<th class="fit">No</th>
																	<th class="fit ar">Driver Name</th>
																	<th class="fit ar">Penalty Date</th>
																	<th class="fit ar">Penatly Amount</th>
																	<th class="fit ar">Paid By</th>
																</tr>
															</thead>
															<tbody>
																<%
																	Integer hitsCount = 1;
																%>
																<c:forEach items="${DriverAdvanvce}" var="DriverAdvanvce">
																	<tr class="ng-scope ">
																		<td class="fit">
																			<% out.println(hitsCount); hitsCount += 1; %>
																		</td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.driver_empnumber}-${DriverAdvanvce.driver_firstname}" /></td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.ADVANCE_PAID_BY}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																<td colspan="3" class="key"><h4>Total Penalty :</h4></td>
																<td colspan="2" class="value"><h4> <i class="fa fa-inr"></i> ${penaltyTotal} </h4></td>
															</tr>
															</tbody>
														</table>
													</c:if>
												</td>
											</tr>
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
										
										<c:if test="${configuration.showTripsheetDueAmount}">
											<tr>
												<td>
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
																	<tr class="ng-scope ">
																		<td class="fit">
																			<% out.println(hitsCount); hitsCount += 1; %>
																		</td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.driver_firstname}-${DriverAdvanvce.driver_Lastname}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.driJobType}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.approximateDateStr}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.dueDateStr}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.dueAmount}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.balanceAmount}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																<td colspan="5" class="key"><h4>Total Due Amount :</h4></td>
																<td colspan="2" class="value"><h4> <i class="fa fa-inr"></i> ${TotalDueAmount} </h4></td>
															</tr>
															</tbody>
														</table>
													</c:if>
												</td>
											</tr>
										</c:if>
			
			<br>
			<!-- Table row -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>No</th>
								<th>Advance Place</th>
								<th>Advance Paid By</th>
								<th>Reference</th>
								<th>Amount</th>
								<th>Advance Date</th>
							</tr>
						</thead>
						<tbody>
							<%
								Integer hitsCount = 1;
							%>
							<c:forEach items="${TripSheetAdvance}" var="TripSheetAdvance">

								<tr>
									<td>
										<%
											out.println(hitsCount);
													hitsCount += 1;
										%>
									</td>
									<td><c:out value="${TripSheetAdvance.advancePlace}" /></td>
									<td><c:out value="${TripSheetAdvance.advancePaidby}" /></td>

									<td><c:out value="${TripSheetAdvance.advanceRefence}" /></td>
									<td><c:out value="${TripSheetAdvance.advanceAmount}" /></td>
									<td><c:out value="${TripSheetAdvance.created}" /></td>
								</tr>


							</c:forEach>

						</tbody>
						<tbody>
							<tr data-object-id="" class="ng-scope">

								<td colspan="4" class="key">Total Advance :</td>
								<td><i class="fa fa-inr"></i> ${advanceTotal}</td>
							</tr>
						</tbody>
					</table>


				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<!-- Table Trip Fuel Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>TRIP FUEL DATA</caption>
						<thead>
							<tr>
								<th>NO</th>
								<th>DOF</th>
								<th>PLACE</th>
								<th>N O L</th>
								<th>PRICE/LITER</th>
								<th>TOTAL</th>
								<th>ODA</th>
								<th>BILL NO</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="13px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="13px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="13px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>

			<!-- Table ONWARD JOURNEY LINE CHECKING REPORT Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>ONWARD JOURNEY LINE CHECKING REPORT</caption>
						<thead>
							<tr>
								<th>DATE &amp; TIME</th>
								<th>PLACE</th>
								<th>TOTAL SEATS</th>
								<th>EXTRA SEAT</th>
								<th>EXTRA LUGGAGE</th>
								<th>CHECKED BY NAME AND SIGN</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>


				</div>
				<!-- /.col -->
			</div>

			<!-- Table LAST DESTINATION REACHING REPORT Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>LAST DESTINATION REACHING REPORT</caption>
						<thead>
							<tr>
								<th>DESTINATION</th>
								<th>REACHING TIME</th>
								<th>DRIVER 1</th>
								<th>DRIVER 2</th>
								<th>CLEANER</th>
								<th>SIGNATURE</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>
			<!-- Table RETURN JOURNEY LINE CHECKING REPORT Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>RETURN JOURNEY LINE CHECKING REPORT</caption>
						<thead>
							<tr>
								<th>DATE &amp; TIME</th>
								<th>PLACE</th>
								<th>TOTAL SEATS</th>
								<th>EXTRA SEAT</th>
								<th>EXTRA LUGGAGE</th>
								<th>CHECKED BY NAME AND SIGN</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>
			<!-- Table RETURN JOURNEY LINE CHECKING REPORT Data -->
			<div class="row">
				<div class="col-xs-11 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>NOTE:</caption>
						<tbody>
							<tr>
								<td height="10px;"></td>
							</tr>

						</tbody>
					</table>
				</div>
				<div class="col-xs-12" align="center">
					<p class="lead">HAPPY JOURNEY</p>
				</div>
				<!-- /.col -->
			</div>
		</section>
	</sec:authorize>
</div>