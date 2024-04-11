<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.printcolor
{
color:  #873600;
}
</style>
<c:if test="${configuration.tripPrintFixedSize}">
<style>
 .table-bordered, .table-bordered > tbody > tr > td, .table-bordered > tbody > tr > th, .table-bordered > tfoot > tr > td, .table-bordered > tfoot > tr > th, .table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
      font-size: initial;
    }
  
  </style>
 </c:if> 
 
 <style>
 	   .highlight {
            color: white;
            background-color:green;
            padding:1px;
        }
       .hightlight2
       {
       		color: white;
            background-color:orange;
            padding:1px;
       }
       .center
	{
		margin-left: auto;
	      	margin-right: auto;
	      	margin-top: 2%;
	}
        .box
        {
        	align-items: center;
        	height:25px;
        	width : 60px;
        	border: 1px solid;
        }
        
 </style>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_ACCOUNT_TRIPSHEET')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_ACCOUNT_TRIPSHEET')">
			<!-- title row -->
			<div class="" style="border-bottom: 1px solid black;border-top: 1px solid black;">
				<div class=border-style:solid;>
					<h2 class="page-header">
						<c:choose>
							<c:when test="${company.company_id != null}">
							
								<c:choose>
									<c:when test="${SubCompany.subCompanyId != null}">
										<img                                                   
											src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
											class="img-rounded" alt="Company Logo" width="280" height="40" />
										<h2 style="text-align: center;" >
											<i class="fa fa-globe"></i>
										<u><c:out value="${SubCompany.subCompanyName}" /></u>
									    </h2>
										<!--  SubCompany-->
									</c:when>
								
								<c:otherwise>
										<img                                                   
											src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
											class="img-rounded" alt="Company Logo" width="280" height="40" />
										<h2 style="text-align: center;" >
											<i class="fa fa-globe"></i>
										<u><c:out value="${company.company_name}" /></u>
									    </h2>
								</c:otherwise>
									</c:choose>
							    
							    
                            </c:when>	
                                     
						    <c:otherwise>
								<i class="fa fa-globe"></i>
								<c:out value="${company.company_name}" />
							</c:otherwise>
						</c:choose>
						</h2>
						</div>
						<div>
						 	<h3 style="text-align: center;" >
							<i class="pull-right"></i>
								<c:out value="${company.company_address}" />
								</h3>
								<center><c:out value="${company.companyEmail}" /></center>
								</div>
				<!-- /.col -->
			</div>
			<div class="row invoice-info">
				<h3 style="text-align: center;" >
					<c:if test="${configuration.vehicleHeaderOnPrint}"> Vehicle : ${TripSheet.vehicle_registration}
					</c:if>
					&nbsp;&nbsp;
					<c:if test="${configuration.routeHeaderOnPrint}"> Route : ${TripSheet.routeName}
					</c:if> 
				</h3>
			</div>
			
			<h3>
			<small class="pull-right"> Print By:
							${company.firstName}_${company.lastName}</small> 
							
							
							<small>Branch
							:<c:out value=" ${company.branch_name}  , " /> Department :<c:out
								value=" ${company.department_name}" />
						</small>
                    	</h3>
                    	
               <div class="row invoice-info">
				<h3>TripSheet ${TripSheet.tripSheetNumber}&nbsp;&nbsp;
					
				</h3>
			</div>
			
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped" style="width: 96%">
						<tbody>
							<tr class="row">
								<c:if test="${!configuration.vehicleHeaderOnPrint}">
									<th>Vehicle :</th>
									<td>
										<c:choose>
											<c:when test="${configuration.tripsheetColorPrint}">
												
												<span style="color:red;"><a class="printcolor"
															href="<c:url value="/VehicleFuelDetails/1.in?vid=${TripSheet.vid}"/>"<b><c:out value="${TripSheet.vehicle_registration}" /></b></a></span>
															
											</c:when>
											<c:otherwise>
												<c:out value="${TripSheet.vehicle_registration}" />
											</c:otherwise>
										</c:choose>
									</td>
			
								</c:if>	
								<c:if test="${configuration.vehicleHeaderOnPrint}">
									<th></th>
									<td></td>
								</c:if>
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
								<c:if test="${!configuration.routeHeaderOnPrint}">
									<th>Route :</th>
									<td><c:out value="${TripSheet.routeName}" /></td>
								</c:if>	
								<c:if test="${configuration.routeHeaderOnPrint}">
									<th></th>
									<td></td>
								</c:if>	
								<th>Date :</th>
								<td><c:out value="${TripSheet.tripOpenDate}  TO" /> <br>
									<c:out value="  ${TripSheet.closetripDate}" /></td>
							</tr>
							<tr class="row">
								<th>Driver 1:</th>
								<c:if test="${configuration.showDriverFullName}">
									<td><c:out value="${TripSheet.tripFristDriverName}  ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}" /> / <c:out
										value="${TripSheet.tripFristDriverMobile}" /></td>
								</c:if>
								<c:if test="${!configuration.showDriverFullName}">
								<td><c:out value="${TripSheet.tripFristDriverName}" /> / <c:out
										value="${TripSheet.tripFristDriverMobile}" /></td>
								</c:if>
								<c:if test="${!configuration.driver2}">
									<th></th>
									<td></td>
								</c:if>	
								<c:if test="${configuration.driver2}">
									<th>Driver 2:</th>
								<c:if test="${configuration.showDriverFullName}">	
									<td><c:out value="${TripSheet.tripSecDriverName}  ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}" /> / <c:out
											value="${TripSheet.tripSecDriverMobile}" /></td>
								</c:if>
								<c:if test="${!configuration.showDriverFullName}">
									<td><c:out value="${TripSheet.tripSecDriverName}" /> / <c:out
											value="${TripSheet.tripSecDriverMobile}" /></td>
								</c:if>			
								</c:if>		
							</tr>
							<tr class="row">
								<c:if test="${!configuration.cleaner}">
									<th></th>
									<td></td>
								</c:if>	
								<c:if test="${configuration.cleaner}">
									<th>Cleaner :</th>
									<td>
									<c:if test="${configuration.showDriverFullName}"> <c:out value="${TripSheet.tripCleanerName} ${TripSheet.tripCleanerMidleName} ${TripSheet.tripCleanerLastName}" /> </c:if>
									<c:if test="${!configuration.showDriverFullName}"><c:out value="${TripSheet.tripCleanerName}"/></c:if>
									
									 / <c:out
											value="${TripSheet.tripCleanerMobile}" /></td>
								</c:if>
								<th>Trip Route Volume :</th>
								<td><c:out value="${TripSheet.routeTotalLiter}" /></td>
							</tr>
							<tr class="row">
								<th>Open KM:</th>
								<td><c:out value="${TripSheet.tripOpeningKM}" /></td>
								<th>Close KM :</th>
								<td><c:out value="${TripSheet.tripClosingKM}" /></td>
							</tr>
							<tr class="row">
								<th>Usage KM:</th>
								<td><c:out value="${TripSheet.tripUsageKM}" /></td>
								<th>Trip Fixed KM :</th>
								<td><c:out value="${TripSheet.routeApproximateKM}" /></td>
							</tr>
							<c:if test="${gpsConfig.allowGPSIntegration}">
							<tr class="row">
								<th>GPS Opening KM:</th>
								<td><c:out value="${TripSheet.tripGpsOpeningKM}" /></td>
								<th>GPS Closing KM :</th>
								<td><c:out value="${TripSheet.tripGpsClosingKM}" /></td>
							</tr>
							<tr class="row">
								<th>GPS Usage KM:</th>
								<td><c:out value="${TripSheet.tripGpsUsageKM}" /></td>
								
							</tr>
							</c:if>
							                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
							
							<c:if test="${configuration.showRoutePoint}">
								<tr class="row">
									<th>Driver 1 Route Point :</th>
									<td><c:out value="${TripSheet.tripFristDriverRoutePoint}" /></td>
									
									<c:if test="${configuration.driver2}">
										<th>Driver 2 Route Point :</th>
										<td><c:out value="${TripSheet.tripSecDriverRoutePoint}" /></td>
									</c:if>	
								</tr>
								<c:if test="${configuration.cleaner}">
									<tr class="row"> 
										<th>Cleaner Route Point :</th>
										<td><c:out value="${TripSheet.tripCleanerRoutePoint}" /></td>
										<th>Expected Mileage :</th>
										<td><c:out value="${vehicle.vehicle_ExpectedMileage} TO ${vehicle.vehicle_ExpectedMileage_to} " /> </td>
									</tr>
								</c:if>		
							</c:if>
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
									<th>Received POD:</th>
									<td><c:out value="${TripSheet.receivedPOD}" /></td>
								</tr>
							</c:if>
							<c:if test="${configuration.showLoadType}">
								<tr class="row">
									<th>SubRoute Service:</th>
									<td><c:out value="${TripSheet.loadTypeStr}" /></td>
								</tr>
							</c:if>
							<c:if test="${configuration.showLoadType}">
							<tr class="row">
								<th>Load Type:</th>
								<td colspan="3"><c:out value="${TripSheet.loadTypeName}" /></td>								
							</tr>
							</c:if>
							<tr class="row">
								<th>Route Remark:  </th>
								<td><c:out value="${TripSheet.routeRemark}" /></td>
							</tr>
							<c:if test="${configuration.tripOpenCloseFuelRequired}">
							<tr class="row">
								<th>Last Fuel:</th>
								<td><c:out value="${TripSheet.tripStartDiesel}" /></td>
								<th>Purchased Fuel :</th>
								<td><c:out value="${fTDiesel}" /></td>
							</tr>
							<tr class="row">
								<th>Total Fuel:</th>
								<td><c:out value="${TripSheet.tripStartDiesel + fTDiesel}" /></td>
								<c:if test="${configuration.tripOpenCloseFuelRequired && TripSheet.tripEndDiesel != null}">
									<th>Balance Fuel:</th>
									<td><c:out value="${TripSheet.tripEndDiesel}" /></td>
								</c:if>
							</tr>
							</c:if>
							<c:if test="${TripSheet.tripEndDiesel != null}">
							<tr class="row">
								<th>Used Fuel:</th>
								<td><c:out value="${TripSheet.tripStartDiesel + fTDiesel - TripSheet.tripEndDiesel}" /></td>
								<th>Fuel Kmpl:</th>
								<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${TripSheet.tripUsageKM /(TripSheet.tripStartDiesel + fTDiesel - TripSheet.tripEndDiesel)}"/></td>
							</tr>
							</c:if>			
						</tbody>
					</table>
					
					<c:if test="${configuration.customeTripsheetPrint}">
						 <table class="table">
						 	<tbody>
						 		<tr>
						 			<td>
					 				<table class="table table-bordered table-striped" style="width: 96%">
										<thead>
											<tr class="breadcrumb">
												<td style="width:10%">Jackey</td>
												<td style="width:10%">Wheel Spanner</td>
												<td style="width:10%">Lever</td>
												<td style="width:15%">Stepheny</td>
												<td style="width:10%">Tools</td>
												<td style="width:15%">Gps</td>
												<td style="width:20%">Verified Person Signature</td>
										</thead>
										<tbody style="width:70px;">
											<tr class="breadcrumb" style="width:70px;">
												<td><div class="center box"></div></td>
												<td><div class="center box"></div></td>
												<td><div class="center box"></div></td>
												<td><div class="center box"></div></td>
												<td><div class="center box"></div></td>
												<td><div class="center box"></div></td>
												<td><div class="center box" style="width:85px" ></div></td>
											</tr>
										</tbody>
									</table>
						 			</td>
						 		</tr>
						 	</tbody>
						 </table>
					
						 <table class="table">
						 	<tbody>
						 		<tr>
						 			<td>
					 				<table class="table table-bordered table-striped" style="width: 96%">
										<thead>
											<tr class="breadcrumb">
												<th>V/h Arrived</th>
												<th>V/h Dispatched</th>
												<th>Remark</th>
												<th>Signature</th>
											</tr>
										</thead>
										 <tbody style="width:70px;"> 
										 	
										 	
										 	<%
											    for (int i = 1; i <=4; i++) {
											  %>
												<tr height="28px">
													<td>Branch <%= i %></td>
													<td><div class="center box"></div></td>
													<td><div class="center box"></div></td>
													<td><div class="center box" style="width:85px"></div></td>
												</tr>
											  <%
											    }
											  %>
								
										</tbody> 
									</table>
						 			</td>
						 		</tr>
						 	</tbody>
						 </table>
					</c:if>
					
					<table class="table">

						<tbody>
							<tr>
								<td><c:if test="${!empty TripSheetAdvance}">

										<table class="table table-bordered table-striped" style="width: 96%">
											<thead>
												<tr class="breadcrumb">
													<th>No</th>
													<th>Advance Place</th>
													<th>Advance PaidBy</th>
													
													<c:choose>
														<c:when test="${configuration.exchangeTypeWithAmountRefWithAmountPrint}">
															<th>Amount</th>
															<th>Reference</th>
														</c:when>
														<c:otherwise>
															<th>Reference</th>
															<th>Amount</th>
														</c:otherwise>
													</c:choose>
													
													<th>Advance Date</th>
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												<c:forEach items="${TripSheetAdvance}"
													var="TripSheetAdvance">

													<tr data-object-id="" class="ng-scope">
														<td>
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<td><c:out value="${TripSheetAdvance.advancePlace}" /></td>
														<td><c:out value="${TripSheetAdvance.advancePaidby}" /></td>
														
														<c:choose>
															<c:when test="${configuration.exchangeTypeWithAmountRefWithAmountPrint}">
																<td>
																<fmt:formatNumber value="${TripSheetAdvance.advanceAmount}" maxFractionDigits="2" /> 
																</td>
																<td><c:out value="${TripSheetAdvance.advanceRefence}" /></td>
															</c:when>
															<c:otherwise>
																<td><c:out value="${TripSheetAdvance.advanceRefence}" /></td>
																<td>
																<fmt:formatNumber value="${TripSheetAdvance.advanceAmount}" maxFractionDigits="2" /> 
																</td>
															</c:otherwise>
														</c:choose>
														
														<td><c:out value="${TripSheetAdvance.createdStr}" /></td>
													</tr>
												</c:forEach>
												<tr data-object-id="" class="ng-scope">
													<td colspan="4" class="key"
														style="font-size: 12px; font-weight: bold;">Total
														Advance :</td>
													<td colspan="2" class="value"
														style="font-size: 12px; font-weight: bold;"><i
														class="fa fa-inr"></i> ${advanceTotal}</td>
												</tr>
											</tbody>
										</table>
									</c:if></td>
							</tr>
							
							<c:if test="${!configuration.showCombineTripDetails}"> 
							<tr>
								<td><c:if test="${!empty TripSheetExpense}">
										<table class="table table-bordered table-striped" style="width: 96%">
											<thead>
												<tr class="breadcrumb">
													<th>No</th>
													<th>Expense Name</th>
													<c:if test="${configuration.showCreditAndVendorAtExpense}">
															<th class="fit ar">Tally Company Name</th>
													</c:if>
													<c:if test="${!configuration.showCreditAndVendorAtExpense}">
														<c:if test="${configuration.showTypePlaceRefInIncomeExpense}">
														
															<c:choose>
																<c:when test="${configuration.exchangeTypeWithAmountRefWithAmountPrint}">
																	<th>Amount</th>
																</c:when>
																<c:otherwise>
																	<th class="fit ar">Type</th>
																</c:otherwise>
															</c:choose>
															<th class="fit ar">Place</th>
															<th class="fit ar">Ref</th> 
														</c:if>	
													</c:if>
													<th>Expense Date</th>
													
													<c:choose>
														<c:when test="${configuration.exchangeTypeWithAmountRefWithAmountPrint}">
															<th class="fit ar">Type</th>
														</c:when>
														<c:otherwise>
															<th>Amount</th>
														</c:otherwise>
													</c:choose>
															
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
														<td>
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<td><c:out value="${TripSheetExpense.expenseName}" /></td>
														
														<c:if test="${configuration.showCreditAndVendorAtExpense}">
																		<td class="fit ar"><c:out
																			value="${TripSheetExpense.tallyCompanyName}" /></td>
															</c:if>	
																	
															<c:if test="${!configuration.showCreditAndVendorAtExpense}">

																<c:if test="${configuration.showTypePlaceRefInIncomeExpense}">
																	
																	
																	<c:choose>
																		<c:when test="${configuration.exchangeTypeWithAmountRefWithAmountPrint}">
																			<td class="fit ar"><c:out value="${TripSheetExpense.expenseAmount}" /></td>
																		</c:when>
																		<c:otherwise>
																			<td class="fit ar">
																				<c:choose>
																					<c:when test="${TripSheetExpense.expenseFixed == 'FIXED'}">
																						<c:choose>
																							<c:when test="${configuration.tripsheetColorPrint}">
																								<small id="warning3" class="PrinttypeColor"><b><c:out
																						               value="${TripSheetExpense.expenseFixed}" /></b></small>
																							</c:when>
																							<c:otherwise>
																								<small class="label label-success"><c:out
																										value="${TripSheetExpense.expenseFixed}" /></small>
																							</c:otherwise>
																						</c:choose>						
																					</c:when>
		
																					<c:otherwise>
																						<c:choose>
																							<c:when test="${configuration.tripsheetColorPrint}">
																									<small id="warning4" class="PrinttypeColor"><b><c:out
																										value="${TripSheetExpense.expenseFixed}" /></b></small>
																							</c:when>
																							<c:otherwise>
																								<small class="label label-warning"><c:out
																										value="${TripSheetExpense.expenseFixed}" /></small>
																							</c:otherwise>
																						</c:choose>	
																					</c:otherwise>
																				</c:choose>
																			</td>
																		</c:otherwise>
																		
																	</c:choose>		
																	
																	<td class="fit ar"><c:out
																			value="${TripSheetExpense.expensePlace}" /></td>
																
																	<td class="fit ar"><c:out
																			value="${TripSheetExpense.expenseRefence}" /></td>
																</c:if>		
															</c:if>	
														
														<td><c:out value="${TripSheetExpense.createdStr}" /></td>
														
														<c:choose>
															<c:when test="${configuration.exchangeTypeWithAmountRefWithAmountPrint}"></td>
																<td class="fit ar">
																	 <c:choose>
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
															</c:when>
															<c:otherwise>
																<td><c:out value="${TripSheetExpense.expenseAmount}" /></td>
															</c:otherwise>
														</c:choose> 
														
														
														
														<c:if test="${configuration.showCreditAndVendorAtExpense}">
																		<td class="fit ar"><c:out
																				value="${TripSheetExpense.vendorName}" /></td>
														</c:if>	
														<c:if test="${configuration.showPFAmount}">
														
																		<td class="fit ar"><fmt:formatNumber maxFractionDigits="2"
																				value="${TripSheetExpense.pfAmount}" /></td>
																	<td class="fit ar"><fmt:formatNumber maxFractionDigits="2"
																				value="${TripSheetExpense.esiAmount}" /></td>
																	<td class="fit ar"><fmt:formatNumber maxFractionDigits="2"
																				value="${TripSheetExpense.balanceAmount}" /></td>						
															</c:if>	
													</tr>


												</c:forEach>
												<tr>
												<c:if test="${!configuration.showPFAmount}">
												<td colspan="4" class="key"
														style="font-size: 12px; font-weight: bold;">Total
														Expense :</td>
													<td  class="value" colspan="3"
														style="font-size: 12px; font-weight: bold;"><i
														class="fa fa-inr"></i> <fmt:formatNumber value="${TripSheetExpense.stream().map(TripSheetExpenseDto -> TripSheetExpenseDto.expenseAmount).sum()}" maxFractionDigits="2"/> </td>
												</c:if>
												<c:if test="${configuration.showPFAmount}">
												<td colspan="4" class="key"
														style="font-size: 12px; font-weight: bold;">Total
														Expense :</td>
													<td  class="value"
														style="font-size: 12px; font-weight: bold;"><i
														class="fa fa-inr"></i><fmt:formatNumber value="${TripSheetExpense.stream().map(TripSheetExpenseDto -> TripSheetExpenseDto.expenseAmount).sum()}" maxFractionDigits="2"/> </td>
														<td></td>
														<td>${TripSheetExpense.stream().map(TripSheetExpenseDto -> TripSheetExpenseDto.pfAmount).sum()}</td>
														<td>${TripSheetExpense.stream().map(TripSheetExpenseDto -> TripSheetExpenseDto.esiAmount).sum()}</td>
														<td>${TripSheetExpense.stream().map(TripSheetExpenseDto -> TripSheetExpenseDto.balanceAmount).sum()}</td>
												</c:if>

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
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.driver_firstname} ${TripsheetDueAmount.driver_Lastname}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.driJobType}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.approximateDateStr}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.dueDateStr}" /></td>
																		<td class="fit ar"><fmt:formatNumber maxFractionDigits="2" value="${TripsheetDueAmount.dueAmount}" /></td>
																		<td class="fit ar"><fmt:formatNumber maxFractionDigits="2" value="${TripsheetDueAmount.balanceAmount}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																<td colspan="5" class="key"><h4>Total Due Amount :</h4></td>
																<td colspan="2" class="value"><h4> <i class="fa fa-inr"></i> <fmt:formatNumber maxFractionDigits="2" value="${TotalDueAmount}" />  </h4></td>
															</tr>
															</tbody>
														</table>
													</c:if>
												</td>
											</tr>
										</c:if>
							
							<c:if test="${configuration.driverPenaltyDetails}">
											<tr>
												<td>
													<c:if test="${!empty DriverAdvanvce}">
														<table class="table table-bordered table-striped" style="width: 96%">
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
																		<td class="fit ar"><c:out value="${DriverAdvanvce.driver_empnumber}-${DriverAdvanvce.driver_firstname} ${DriverAdvanvce.driver_Lastname} - ${DriverAdvanvce.driverFatherName}" /></td>
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




						<tr>
							<td><c:if test="${!empty refreshmentList}">


									<table class="table table-bordered table-striped"
										style="width: 96%">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">R.NO</th>
												<th class="fit ar">Date</th>
												<th class="fit ar">Quantity</th>
												<th class="fit ar">Returned Qty</th>
												<th class="fit ar">Consumption</th>
												<th class="fit ar">Amount</th>
												<th class="fit ar">Return Amt</th>
												<th class="fit ar">Balance</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${refreshmentList}" var="refreshmentList">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:out
															value="R-${refreshmentList.refreshmentEntryNumber}" /></td>

													<td class="fit ar"><c:out
															value="${refreshmentList.asignmentDateStr}" /></td>

													<td class="fit ar"><fmt:formatNumber
															value="${refreshmentList.quantity}" maxFractionDigits="2" />
													</td>
													<td class="fit ar"><fmt:formatNumber
															value="${refreshmentList.returnQuantity}"
															maxFractionDigits="2" /></td>

													<td class="fit ar"><fmt:formatNumber
															value="${refreshmentList.quantity - refreshmentList.returnQuantity}"
															maxFractionDigits="2" /></td>


													<td class="fit ar"><fmt:formatNumber
															value="${refreshmentList.totalAmount}"
															maxFractionDigits="2" /></td>


													<td class="fit ar"><fmt:formatNumber
															value="${refreshmentList.returnQuantity * refreshmentList.unitprice}"
															maxFractionDigits="2" /></td>

													<td class="fit ar"><fmt:formatNumber
															value="${refreshmentList.totalAmount-(refreshmentList.returnQuantity * refreshmentList.unitprice)}"
															maxFractionDigits="2" /></td>


												</tr>
											</c:forEach>
											<tr>
												<td colspan="2" class="key"><h4>Total:</h4></td>
												<td><h4>
														<fmt:formatNumber value="${totalQty}"
															maxFractionDigits="2" />
													</h4></td>
												<td><h4>
														<fmt:formatNumber value="${totalRQty}"
															maxFractionDigits="2" />
													</h4></td>
												<td><h4>
														<fmt:formatNumber value="${totalConsumption}"
															maxFractionDigits="2" />
													</h4></td>
												<td><h4>
														<fmt:formatNumber value="${grandTotal}"
															maxFractionDigits="2" />
													</h4></td>
												<td><h4>
														<fmt:formatNumber value="${totalReturnAmount}"
															maxFractionDigits="2" />
													</h4></td>
												<td><h4>
														<fmt:formatNumber value="${refreshmentTotalAmount}"
															maxFractionDigits="2" />
													</h4></td>
											</tr>
										</tbody>
									</table>
								</c:if></td>
						</tr>


						<c:if test="${configuration.showCombineTripDetails}"> 	
										<tr>
											<td><c:if test="${!empty ExpenseCombineList}">

													<table class="table table-bordered table-striped" style="width: 96%">
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
							
							
							<!-- Script for displaying TripSheetTollExpense Logic Start By Devy -->
							<tr>
								<td>
								<c:if test="${!empty TripSheetTollExpense}">
								<table class="table table-bordered table-striped" border=0 cellpadding="5" cellspacing="5" style="width: 96%" >
										<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Expense Name</th>
																<th class="fit ar">Amount</th>
															</tr>
										</thead>
										<tbody>
										<%
													Integer counter = 1;
										%>
										
										<tr data-object-id="" class="ng-scope">
										
												<td class="fit">
													<%
														out.println(counter);
														counter += 1;
													%>
												</td>
												
												
												<td><c:out
															value="${TripSheetTollExpenseName}" /></td>
													<td><c:out
															value="${TripSheetTollExpenseTotalAmount}" /></td>
													
										</tr>
										</tbody>
								</table>
								</c:if>
								</td>
							</tr>
							<!-- Script for displaying TripSheetTollExpense Logic Start By Devy -->
							<c:choose>
							<c:when test="${!configuration.showLsSourceAndDestination}"> 
								<tr>
									<td><c:if test="${!empty TripSheetIncome}">
											<table class="table table-bordered table-striped" style="width: 96%">
												<thead>
													<tr class="breadcrumb">
														<th>No</th>
														<th>Income Name</th>
														<th>Income Place</th>
														<th>Collected By</th>
														<c:if test="${configuration.showLsDestination}">
														<th>LsSourceDestination</th>
														</c:if>
														<th>Reference</th>
														<th>Amount</th>
														<th>Income Date</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripSheetIncome}" var="TripSheetIncome">
	
														<tr data-object-id="" class="ng-scope">
															<td>
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="${TripSheetIncome.incomeName}" /></td>
															<td><c:out value="${TripSheetIncome.incomePlace}" /></td>
															<td><c:out
																	value="${TripSheetIncome.incomeCollectedBy}" /></td>
															<c:if test="${configuration.showLsDestination}">
																<td><c:out value="${TripSheetIncome.lsDestinationBranch}" /></td>
															</c:if>
															<td style="width:15%"><c:out value="${TripSheetIncome.incomeRefence}" /></td>
															<td style="width:15%"><c:out value="${TripSheetIncome.incomeAmount}" /></td>
															<td><c:out value="${TripSheetIncome.createdStr}" /></td>
														</tr>
													</c:forEach>
													<tr>
														<td colspan="5" class="key"
															style="font-size: 12px; font-weight: bold;">Total
															Income :</td>
														<td colspan="2" class="value"
															style="font-size: 12px; font-weight: bold;"><i
															class="fa fa-inr"></i> ${incomeTotal}</td>
													</tr>
												</tbody>
											</table>
	
										</c:if></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td>
									<c:if test="${!empty LoadingSheetDetails}">
										<table class="table table-bordered table-striped" style="width: 96%">
											<thead>
												<tr>
													<th>No</th>
													<th>LS Number</th>
													<th>LS Source Branch</th>
													<th>LS Destination Branch</th>
													<th>TripIncome Amount</th>
													<th>Trip Date Time</th>
												</tr>
											</thead>
											<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${LoadingSheetDetails}" var="loadingSheet">
														<tr data-object-id="" class="ng-scope">
															<td>
																<%
																	out.println(hitsCount);
																	hitsCount += 1;
																%>
															</td>
															<td><c:out value="Loading Sheet - ${loadingSheet.lsNumber}"/></td>
															<td><c:out value="${loadingSheet.lsSourceBranch}"/></td>
															<td><c:out value="${loadingSheet.lsDestinationBranch}"/></td>
															<td><c:out value="${loadingSheet.tripincome}"/></td>
															<td><c:out value="${loadingSheet.tripDateTime}"/></td>
														</tr>
													</c:forEach>
											</tbody>
										</table>
									</c:if>
									</td>
								</tr>
							  </c:otherwise>
							</c:choose>
							
							<c:if test="${!configuration.showDriverBalance}">
							
							<tr>
								<td>
									<table class="table table-bordered table-striped" style="width: 96%">
										<thead>
											<tr class="breadcrumb">
												<th>Paid To</th>
												<th>Paid By</th>
												<th>(Advance - Expense)</th>
												<th>Reference</th>
												<th>TS-Close Date</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody>

											<tr data-object-id="" class="ng-scope">

												<td><c:out value="${TripSheet.closeTripStatus}" /></td>
												<td><c:out value="${TripSheet.closeTripNameBy}" /></td>
												<td><c:out value="${balance}" /></td>
												<td><c:out value="${TripSheet.closeTripReference}" /></td>
												<td><c:out value="${TripSheet.closetripDate}" /></td>
												<td><c:choose>

														<c:when test="${TripSheet.tripStutesId == 4}">
														
															<c:choose>
																<c:when test="${configuration.tripsheetColorPrint}">
																	<span id="success1" class="printColorSuccess"><b><c:out
																	value="CLOSED" /></b></span> 
																</c:when>
																<c:otherwise>
																	<span class="label label-pill label-success"><b><c:out
																	value="CLOSED" /></b></span>
																</c:otherwise>
															</c:choose>
														</c:when>	
														<c:otherwise>
															<c:choose>
																<c:when test="${configuration.tripsheetColorPrint}">
																	<span id="warning1" class="printColorWarning"><b><c:out
																	value="${TripSheet.tripStutes}" /></b></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-pill label-warning"><c:out
																	value="${TripSheet.tripStutes}" /></span>
																</c:otherwise>
															</c:choose>
														</c:otherwise>  
											
													</c:choose>
													
													</td>
											</tr>

										</tbody>
									</table>
								</td>
							</tr>
							</c:if>
							<tr>
								<td><c:if test="${TripSheet.tripStutesId == 4}">
										<table class="table table-bordered table-striped" style="width: 96%">
											<thead>
												<tr class="breadcrumb">

													<th>A/C Closed By</th>
													<th>(Income - Expense)</th>
													<th>A/C Reference</th>
													<th>A/C Closed Date</th>
													<th>Status</th>
												</tr>
											</thead>
											<tbody>

												<tr data-object-id="" class="ng-scope">

													<td><c:out value="${TripSheet.closeACCTripNameBy}" /></td>

													<td><c:out value="${TripSheet.closeACCTripAmount}" /></td>
													<td><c:out value="${TripSheet.closeACCTripReference}" /></td>
													<td><c:out value="${TripSheet.closeACCtripDateStr}" /></td>
													<td>
													
													<c:choose>

															<c:when test="${TripSheet.tripStutesId == 4}">
																
																<c:choose>
																	<c:when test="${configuration.tripsheetColorPrint}">
																		<span id="success2" class="printColorSuccess"><b><c:out
																		value="${TripSheet.tripStutes}" /></b></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-success"><c:out
																		value="${TripSheet.tripStutes}" /></span>
																	</c:otherwise>
																</c:choose>
																
																		
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${configuration.tripsheetColorPrint}">
																		<span id="warning2" class="printColorWarning"><c:out
																		value="${TripSheet.tripStutes}" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-warning"><c:out
																		value="${TripSheet.tripStutes}" /></span>
																	</c:otherwise>
																</c:choose>
																
															</c:otherwise>
														</c:choose>
													
														</td>
												</tr>

											</tbody>
										</table>
									</c:if></td>
							</tr>
							<tr>
								<td>
									<!-- Trip sheet driver halt details --> <c:if
										test="${!empty TripSheetHalt}">
										<div class="row">
											<div class="">
												<table class="table table-bordered table-striped" style="width: 96%">
													<thead>
														<tr class="breadcrumb">
															<th>No</th>
															<th>Name</th>
															<th colspan="2">Halt date</th>
															<th>Amount</th>
															<th>Place</th>
															<th>Paid By</th>
														</tr>
													</thead>
													<tbody>
														<%
															Integer hitsCount = 1;
														%>
														<c:forEach items="${TripSheetHalt}" var="TripSheetHalt">
															<tr data-object-id="" class="ng-scope">
																<td>
																	<%
																		out.println(hitsCount);
																					hitsCount += 1;
																	%>
																</td>
																<td><c:out value="${TripSheetHalt.DRIVER_NAME}" /></td>
																<td colspan="2"><c:out
																		value="${TripSheetHalt.HALT_DATE_FROM} to " /> <c:out
																		value="${TripSheetHalt.HALT_DATE_TO}" /></td>
																<td><c:out value="${TripSheetHalt.HALT_AMOUNT}" /></td>
																<td><c:out value="${TripSheetHalt.HALT_PLACE}" /></td>
																<td><c:out value="${TripSheetHalt.HALT_PAIDBY}" /></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</c:if>
								</td>
							</tr>
							<tr>
								<td>
									<!--  Trip Sheet closed  fuel entries --> <c:if
										test="${!empty fuel}">
										<table id="FuelTable" class="table table-hover table-bordered" style="width: 96%">
											<thead>
												<tr class="breadcrumb">
													<th>ID</th>
													<th>Vehicle</th>
													<th>Date</th>
													<th>Close(Km)</th>
													<th>Usage</th>
													<th>Volume</th>
													<th>Amount</th>
													<th>FE</th>
													<th>Cost</th>
													<c:if test="${configuration.showDiffAvgCost}">
													<th>Diff Avg Cost</th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${fuel}" var="fuel">
													<tr data-object-id="" class="ng-scope">
														<td>
														<c:choose>
														<c:when test="${configuration.tripsheetColorPrint }">
															<a class="printcolor"
																href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
																data-toggle="tip"
																data-original-title="Click Fuel Details"><c:out
																		value="FT-${fuel.fuel_Number}" /></a>
														</c:when>
														<c:otherwise>
															<a
																href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
																data-toggle="tip"
																data-original-title="Click Fuel Details"><c:out
																		value="FT-${fuel.fuel_Number}" /></a>
														</c:otherwise>
														</c:choose>
														</td>
															
														<td>
														<c:choose>
														<c:when test="${configuration.tripsheetColorPrint }">
															<a class="printcolor"
																href="<c:url value="/VehicleFuelDetails/1.in?vid=${fuel.vid}"/>"
																data-toggle="tip"
																data-original-title="Click Vehicle Details"><c:out
																		value="${fuel.vehicle_registration}" /> </a>
														</c:when>
														<c:otherwise>
															<a
																href="<c:url value="/VehicleFuelDetails/1.in?vid=${fuel.vid}"/>"
																data-toggle="tip"
																data-original-title="Click Vehicle Details"><c:out
																		value="${fuel.vehicle_registration}" /> </a>
														</c:otherwise>
														</c:choose>			
														</td>
														<td><c:out value="${fuel.fuel_date}" /><br>
															<h6>
															<c:choose>
																<c:when test="${configuration.tripsheetColorPrint }">
																	<a class="printcolor" data-toggle="tip" data-original-title="Vendor Name">
																		<c:out value="${fuel.vendor_name}" />-( <c:out
																			value="${fuel.vendor_location}" /> )
																	</a>
																</c:when>
																<c:otherwise>
																	<a data-toggle="tip" data-original-title="Vendor Name">
																		<c:out value="${fuel.vendor_name}" />-( <c:out
																			value="${fuel.vendor_location}" /> )
																	</a>
																</c:otherwise>
															</c:choose>
																
															</h6></td>
														<td><c:out value="${fuel.fuel_meter}" /></td>

														<td><c:out value="${fuel.fuel_usage} km" /></td>

														<td><abbr data-toggle="tip"
															data-original-title="Liters"><c:out
																	value="${fuel.fuel_liters}" /></abbr> <c:if
																test="${fuel.fuel_tank_partial==1}">
																<abbr data-toggle="tip"
																	data-original-title="Partial fuel-up"> <i
																	class="fa fa-adjust"></i>
																</abbr>
															</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
														<td><i class="fa fa-inr"></i> <c:out
																value="${fuel.fuel_amount}" /> <br> <abbr
															data-toggle="tip" data-original-title="Price"> <c:out
																	value="${fuel.fuel_price}/liters" />
														</abbr></td>
														<td><c:out value="${fuel.fuel_kml} " /> <c:if
																test="${fuel.fuel_kml != null}">
																<c:choose>
																	<c:when
																		test="${vehicle.vehicle_ExpectedMileage <= fuel.fuel_kml}">
																		<c:choose>
																			<c:when
																				test="${vehicle.vehicle_ExpectedMileage_to >= fuel.fuel_kml}">
																				<abbr data-toggle="tip"
																					data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage} to ${vehicle.vehicle_ExpectedMileage_to}">
																					<i class="fa fa-stop-circle"
																					style="color: #1FB725; font-size: 19px;"></i>
																				</abbr>
																			</c:when>
																			<c:otherwise>
																				<abbr data-toggle="tip"
																					data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage} to ${vehicle.vehicle_ExpectedMileage_to}">
																					<i class="fa fa-chevron-circle-up"
																					style="color: blue; font-size: 19px;"></i>
																				</abbr>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<abbr data-toggle="tip"
																			data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage}  to ${vehicle.vehicle_ExpectedMileage_to}">

																			<i class="fa fa-chevron-circle-down"
																			style="color: red; font-size: 19px;"></i>
																		</abbr>
																	</c:otherwise>

																</c:choose>
															</c:if></td>
														<td><c:out value="${fuel.fuel_cost} " /> <c:if
																test="${fuel.fuel_cost != null}">
													/Km
													</c:if></td>
												<c:if test="${configuration.showDiffAvgCost}">
													<td><fmt:formatNumber maxFractionDigits="2" value="${fuel.fuelPriceDiff}" /> </td>
												</c:if>		
													
													</tr>
												</c:forEach>
												 <c:if test="${configuration.tripOpenCloseFuelRequired && TripSheet.tripEndDiesel != null}">
																<tr>
																	<td colspan="1" class="text-right"
																		style="font-size: 15px; font-weight: bold;">Trip Km/L
																		:</td>
																	<td style="font-size: 15px; font-weight: bold;">
																		<fmt:formatNumber type="number" maxFractionDigits="2" value="${TripSheet.tripUsageKM /(TripSheet.tripStartDiesel + fTDiesel - TripSheet.tripEndDiesel)}"/>
																	</td>	
																	<td colspan="2" class="text-right"
																		style="font-size: 15px; font-weight: bold;">Total
																		:</td>
																	<td style="font-size: 15px; font-weight: bold;"><c:out
																			value="${fTUsage}" /></td>
																	<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${fTDiesel}" /></td>
																	<td colspan="3"
																		style="font-size: 15px; font-weight: bold;"><c:out
																			value="${fTAmount} " /></td>
																</tr>
															</c:if>	
															
															<c:if test="${!configuration.tripOpenCloseFuelRequired}">
															  <tr>
																<td colspan="4" class="text-right"
																	style="font-size: 15px; font-weight: bold;">Total
																	:</td>
																<td style="font-size: 15px; font-weight: bold;"><c:out
																		value="${fTUsage}" /></td>
																<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${fTDiesel}" /></td>
																
																<c:if test="${configuration.showTotalKmpl}">
																	<td style="font-size: 15px; font-weight: bold;">
																		<c:out value="${fTAmount} " />
																	</td>
																	<td colspan="1" style="font-size: 15px; font-weight: bold;">
																		<fmt:formatNumber type="number" maxFractionDigits="2" value="${fTUsage/fTDiesel}" />
																	</td>
																	<td colspan="1" style="font-size: 15px; font-weight: bold;">
																		<fmt:formatNumber type="number" maxFractionDigits="2" value="${fTAmount/fTUsage}" />
																	</td>
																	<c:if test="${configuration.showDiffAvgCost}">
																		<td colspan="1" style="font-size: 15px; font-weight: bold;">
																			<fmt:formatNumber type="number" maxFractionDigits="2" value="${diffAvgCost}" />
																		</td>
																	</c:if>
																</c:if>	
																<c:if test="${!configuration.showTotalKmpl}">
																	<td colspan="1" style="font-size: 15px; font-weight: bold;">
																		<c:out value="${fTAmount} " />
																	</td>
																	<td colspan="1" style="font-size: 15px; font-weight: bold;">
																	</td>
																	<td colspan="1" style="font-size: 15px; font-weight: bold;">
																	</td>
																</c:if>	
															  </tr>
															 </c:if>
												
												
												
											</tbody>
										</table>
									</c:if>
								</td>
							</tr>
							<tr>
										<td>
											<c:if test="${!empty urea}">
												<table id="FuelTable" class="table table-hover table-bordered" style="width: 96%">
													<thead>
														<tr>
															<th>ID</th>
															<th>Vehicle</th>
															<th>Urea Manufacturer</th>
															<th>Date</th>
															<th>Close(Km)</th>
															<th>Volume</th>
															<th>Amount</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${urea}" var="urea">
															<tr data-object-id="" class="ng-scope">
																<td><a
																	href="<c:url value="/showUreaDetails.in?Id=${urea.ureaEntriesId}"/>"
																	data-toggle="tip" data-original-title="Click Urea Details"><c:out
																			value="UE-${urea.ureaEntriesNumber}" /></a>
																</td>
																<td><a
																	href="<c:url value="/showVehicle.in?vid=${urea.vid}"/>"
																	data-toggle="tip"
																	data-original-title="Click Vehicle Details"><c:out
																			value="${urea.vehicle_registration}" /> </a>
																</td>
																<td>
																	<c:out value="${urea.manufacturerName}" /><br>
																</td>
																<td>
																	<c:out value="${urea.ureaDateStr}" /><br>
																</td>
																<td>
																	<c:out value="${urea.ureaOdometer}" />
																</td>
																<td>
																	<c:out value="${urea.ureaLiters}" />
																</td>
																<td><i class="fa fa-inr"></i> <c:out
																		value="${urea.ureaAmount}" /> <br> <abbr
																	data-toggle="tip" data-original-title="Price"> <c:out
																			value="${urea.ureaRate}/liters" />
																	</abbr>
																</td>
															</tr>
														</c:forEach>
														<tr>
															<td colspan="5" class="text-right"
																style="font-size: 15px; font-weight: bold;">Total
																:</td>
															<td style="font-size: 15px; font-weight: bold;"><c:out
																	value="${totalUrea} " /></td>
															<td style="font-size: 15px; font-weight: bold;"><c:out
																	value="${totalUreaAmnt} " /></td>
														</tr>
													</tbody>
												</table>
											</c:if>	
										</td>
								</tr>
								
							<tr>
								<td>
									<table class="table table-bordered table-striped"  style="width: 96%">
										<tr>
											<td  class="key"><h4>Trip
													Balance:</h4></td>
											<td class="value"><h4>
													<i class="fa fa-inr"></i> ${tripTotalincome}
												</h4></td>
										</tr>
										<c:if test="${configuration.showTripsheetDueAmount}">
														<tr>
															<td colspan="0" class="key"><h4>Due Amount:</h4></td>
															<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${TotalDueAmount}</h4></td>
														</tr>	
										</c:if>
										<c:if test="${configuration.showDriverBalance}">
											
											<c:choose>
											
												<c:when test="${configuration.showDriverBalConfigInTripsheetPrint}">
													<c:if test="${TripSheet.tripStutesId >2 }">
														<tr>
															<td class="key"><h5><b>Driver TripSheet Balance (Advance - Expense)</b></h5></td>
															<td class="value"><h4><c:out value = "${TripSheet.closeTripAmount+TripSheet.driverBalance}"/></h4></td>
														</tr>
														<tr>
														 	<c:choose>
														 		<c:when test="${TripSheet.closeTripStatusId == 1}">
														 			<td class="key"><h5><b>Cash Statement(Credit)</b></h5></td>
														 		</c:when>
														 		<c:otherwise>
														 			<td class="key"><h5><b>Cash Statement(Debit)</b></h5></td>
														 		</c:otherwise>
														 	</c:choose>
															<td class="value"><h4><c:out value = "${TripSheet.closeTripAmount}"/></h4></td>
														</tr>
														<tr>
															<td class="key"><h5><b>Push To Tally</b></h5></td>
															<td class="value"><h4><c:out value = "${TripSheet.driverBalance}"/></h4></td>
														</tr>
													</c:if>
												</c:when>
												<c:otherwise>
													<tr>
														<td  class="key"><h4>Driver Balance:</h4></td>
														<td  class="value"><h4><i class="fa fa-inr"></i> ${driverBalance}</h4></td>
													</tr>
												</c:otherwise>
											</c:choose>
										   
									
										</c:if>
										<c:if test="${configuration.showTripsheetDueAmount}">
														<tr>
															<td class="key"><h4>Due Amount:</h4></td>
															<td class="value"><h4><i class="fa fa-inr"></i> ${TotalDueAmount}</h4></td>
														</tr>	
										</c:if>
										<c:if test="${configuration.driverBalanceWithNarration}">
											<tr>
												<td  class="key"><h4><b>${driverBalanceKey}:</b></h4></td>
												<td class="value"><h4><i class="fa fa-inr"></i> <b> ${balanceAmount}</b></h4></td>
											</tr>	
										</c:if>
									</table>
								</td>
							</tr>
							<td><c:if
										test="${TripSheet.tripStutesId ==3 || TripSheet.tripStutesId == 4}">
										<table class="table table-bordered table-striped" style="width: 96%">
											<tbody>
											<thead>
												<tr class="breadcrumb">
													<th></th>
													<th>Fixed</th>
													<th>Actual</th>
													<th>Differences</th>
												</tr>
											</thead>
											<tbody>
												<tr class="breadcrumb">
													<td class="text-right">Differences of Trip Volume :</td>
													<td><c:out value="${TripSheet.routeTotalLiter}" /></td>
													<%--<td><c:out value=" ${fTDiesel}" /></td>--%>
													<%--<td><c:out value=" =  ${TripSheet.routeTotalLiter - fTDiesel}" /></td>--%>
													<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${fTDiesel}" /></td>
													<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${TripSheet.routeTotalLiter - fTDiesel}" /></td>
															
												</tr>
												<tr class="breadcrumb">
													<td class="text-right">Differences of Trip KM Usage :</td>
													<td><c:out value="${TripSheet.routeApproximateKM}" /></td>
													<td><c:out value="${TripSheet.tripUsageKM}" /></td>
													<td><c:choose>
															<c:when
																test="${TripSheet.tripClosingKMStatusId == 1}">
																
																<c:choose>
																	<c:when test="${configuration.tripsheetColorPrint }">
																	Meter : <a data-toggle="tip" class="printcolor"
																			data-original-title="Meter Not Working"> <c:out
																				value="Meter Not Working " /></a>
																	</c:when>
																	<c:otherwise>
																		Meter : <a data-toggle="tip"
																			data-original-title="Meter Not Working"> <c:out
																				value="Meter Not Working " /></a>
																	</c:otherwise>
																</c:choose>		
															</c:when>
															<c:otherwise>
																<c:out
																	value=" = ${TripSheet.routeApproximateKM - TripSheet.tripUsageKM}" />

															</c:otherwise>
														</c:choose></td>
												</tr>
											</tbody>
										</table>
									</c:if></td>
							</tr>
							<c:if test="${configuration.preparedByAndAuthorizedBy}">
							<tr>
								<td>
									<table class="table table-bordered table-striped"  style="width: 96%; margin-top: 20px;">
										<tr>
											<td>Prepared By: </td>
											<td>${preparedBy}</td>
											<td>Authorized By:</td>
											<td>${authorizedBy}</td>
											
										</tr>
									</table>
								</td>
							</tr>
							</c:if>
							
							<c:if test="${configuration.showTripCommentInPrint}">
								<tr>
									<td><c:if test="${!empty TripComment}">
											<table class="table table-bordered table-striped">
												<tbody>
													<c:forEach items="${TripComment}" var="TripComment">
														<tr>
															<td><div class="timeline-item">
																	<strong><a data-toggle="tip"
																		data-original-title="${TripComment.CREATED_EMAIL}"><i
																			class="fa fa-user"></i> <c:out
																				value="${TripComment.CREATEDBY}" /></a> commented
																		from <b><c:out
																				value="${TripComment.CREATED_PLACE}" /></b> <c:out
																			value="${TripComment.CREATED_DATE}" /></strong>
																	<div class="pull-right">
																		<span class="time"><i class="fa fa-clock-o"></i>
																			${TripComment.CREATED_DATE_DIFFERENT}</span>
																	</div>
																	<div class="timeline-body">
																		<c:out value="${TripComment.TRIP_COMMENT}" />
																	</div>
																</div></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
									</td>
								</tr>
							</c:if>
							
							
						</tbody>
					</table>
				</div>
			</div>
		</section>
	</sec:authorize>
</div>
<script>
	window.addEventListener('beforeprint', highlightElements);
	
	function handlePrint() {
	  // Function to be called when printing is triggered
	  console.log('Printing event detected!');
	  var element = document.getElementById("webpage");
      element.style.color = "green";
      console.log("after css applied");
	  // Add your custom logic or actions here
	}
	
	function highlightElements() {
		
		var elementIds1 = ["warning1", "warning2", "warning3", "warning4"];
        var selector1 = elementIds1.map(id => "#" + id).join(", ");
        var elements1 = document.querySelectorAll(selector1);
        elements1.forEach(element => {
            element.classList.add("hightlight2");
        });
	        
        var elementIds = ["success1", "success2", "success3"];
        var selector = elementIds.map(id => "#" + id).join(", ");
        var elements = document.querySelectorAll(selector);
        elements.forEach(element => {
            element.classList.add("highlight");
        });
        
      
        
    }
</script>
<!-- ./wrapper -->
