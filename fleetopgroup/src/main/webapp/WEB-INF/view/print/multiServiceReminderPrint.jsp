<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
@media print {
    .pagebreak { page-break-before: always; } /* page-break-after works, as well */
}

td{
padding: 0px
}

.border-none {
  border-collapse: collapse;
   border: none; 
}
tbody{
font-size: 11px;
font-weight: bold; 
}

.border-none td,th {
  border: 1px solid black;
}

.border-none tr:first-child td {
  border-top: none;
}

.border-none tr:last-child td {
  border-bottom: none;
}

.border-none tr td:first-child {
  border-left: none;
}

.border-none tr td:last-child {
  border-right: none;
}

.border-none tr th:first-child {
  border-left: none;
}

.border-none tr th:last-child {
  border-right: none;
}

.border-none tr:first-child th {
  border-top: none;
}

.border-none tr:last-child th {
  border-bottom: none;
}


</style>
<div class="wrapper">
<%-- 	<sec:authorize access="!hasAuthority('DOWNLOAD_WORKORDER')"> --%>
<%-- 		<spring:message code="message.unauth"></spring:message> --%>
<%-- 	</sec:authorize> --%>
<%-- 	<sec:authorize access="hasAuthority('DOWNLOAD_WORKORDER')"> --%>
		<!-- Main content -->
		<section class="invoice">
			<!-- title row -->
			<c:if test="${ServiceProgramList != null }">
				<div>
				<div>
					<h2 class="page-header" style="text-align: center;">
								<i class="fa fa-globe"></i>
								<c:out value="${company.company_name}" />
						</h2>
						</div>
			</div>
			<div class="row invoice-info">
				<div>
					<div class="box-body no-padding">
						<table class="table table-striped" style="width: 100%;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black">
							<tbody>
								<tr>
									<td style="text-align:center;" colspan="4"> <h4>Job Card for Service Reminder(I) ( 
										<c:forEach var="sreminder" items="${ServiceProgramList}">${sreminder.service_Number},</c:forEach>
									<c:out value=" ${branch}) "></c:out></h4>
									</td>
								</tr>
								<tr>
								<td>
								<table  class="border-none" style="width: 100%">
								<tr>
									<td style="border-right: 1px solid black;vertical-align: top;">
										<table class="border-none" style="width: 100%;" >
											<tr>
											<td style="border-right: none;" > Job card printed on: </td><td style="border-left: none;" >${date}</td>
											</tr>
											<tr>
											<td style="border-right: none;"> Job Start Date: </td><td style="border-left: none;"></td>
											</tr>
											<tr>
											<td style="border-right: none;"> Job End Date: </td><td style="border-left: none;"></td>
											</tr>
										</table>
									</td>
									<td>
									<table class="border-none" style="width:100%;">
									<tr>
									<td style="width:35%;border-right: 1px solid black">Vehicle No.& Type:</td><td>${vehicleNumber} & ${vehicleType}</td>
									</tr>
									
										<tr><td style="width:35%;border-right: 1px solid black">Driver name & No: </td>
										</tr>
										
										<tr><td style="width:35%;border-right: 1px solid black">Odometer:</td><td>${currentOdometer}</td></tr>
									</table>
									</td>
								</table>
								</td>
								</tr>
								<tr><td colspan="4">&nbsp;</td></tr> 
								<tr>
								<td colspan="4">
								<table class="border-none" style="width: 100%;">
								<tr>
									<th>Service Program </th>
									<th>Service Reminder No, Type, SubType</th> 
								</tr>
									<c:forEach items="${ServiceProgramList}" var="sreminder" varStatus="loop"> 
										<tr>
										<td style="padding-left:10%;">
											<c:out value="${sreminder.serviceProgram}"/>
										</td>
										<c:set var="str1" value="${sreminder.serviceProgram}"/>
										<c:set var="str2" value="weekly activity" />
										<c:choose>
											<c:when test="${str1 eq str2}">
												<td style="padding-left:15px;"><c:out value="SR-${sreminder.service_Number} ${sreminder.service_type} - ${sreminder.service_subtype} Every ${sreminder.time_interval} Days"/> </td>
											</c:when>
											
											<c:otherwise>
												<td style="padding-left:15px;"><c:out value="SR-${sreminder.service_Number} ${sreminder.service_type} - ${sreminder.service_subtype} Every ${sreminder.meter_interval} km"/> </td>
											</c:otherwise>
										</c:choose>
										
										
										</tr>
									</c:forEach>
								</table>
								</td>
								</tr>
								<tr>
								<td colspan="4">
								&nbsp;
								</td>
								</tr>
								<tr>
								<td style="width:100%">
								<table  style="width:100%;" class="border-none">
								<tr>
									<td style="text-align: center;">Job Type</td>
									<td style="text-align: center;">JobSub Type</td>
									<td style="text-align: center;">Job Details</td>
									</tr>
									<tr style="height:30px">
									<td>1</td>
									<td>1</td>
									<td></td>
									</tr>
									<tr style="height:30px">
									<td>2</td>
									<td>2</td>
									<td></td>
									</tr>
									<tr style="height:30px">
									<td>3</td>
									<td>3</td>
									<td></td>
									</tr>
									<tr style="height:30px">
									<td>4</td>
									<td>4</td>
									<td></td>
									</tr>
									<tr style="height:30px">
									<td>5</td>
									<td>5</td>
									<td></td>
									</tr>
									</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td colspan="4">
								&nbsp;
								</td>
								</tr>
							<tr>
								<td>

									<table style="width: 100%;">
										<tr>
											<td style="width: 50%; border-right: 1px solid">
												<table style="width: 50%;">
													<tr>
														<td>Technician name& sign :</td><td style="border-left	: 1px solid">&nbsp;</td>
													</tr>
													<c:forEach var="sreminder" items="${ServiceProgramList}">
													<tr>
														<td>&nbsp;</td> <td style="border-left: 1px solid"> <c:out value="(SR-${sreminder.service_Number})"/></td>
													</tr>
													</c:forEach>
													
													<tr>
														<td style="border-right: 1px solid">Shift:</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="width: 50%;">
													<tr>
														<td>Shift Coord Name& sign:</td>
														<td style="border-left: 1px solid">&nbsp;</td>
														<c:forEach var="issue" items="${issueList}">
													<tr>
														<td>&nbsp;</td> <td style="border-left: 1px solid"> <c:out value="${issue.ISSUES_ASSIGN_TO_NAME}"/></td>
													</tr>
													</c:forEach>
													</tr>
													<tr>
														<td style="border-right: 1px solid">Supervisor Sign :</td>
														<td>&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>


									</table>

								</td>
							</tr>
						</tbody>
						</table>
					</div>
					<div class="pagebreak"> </div>
					<div class="box-body">
							<div>
				<div>
					<h2 class="page-header" style="text-align: center;">
								<i class="fa fa-globe"></i>
								<c:out value="${company.company_name}" />
						</h2>
						</div>
			</div>
						<table class="table table-striped" style="width: 100%;border-left: 1px solid black;border-right: 1px solid black;border-bottom: 1px solid black">
							<tbody>
								<tr>
								<td style="text-align:center;" colspan="4"> <h4>Job Card for Service Reminder(I)(<c:forEach var="sreminder" items="${ServiceProgramList}">${sreminder.service_Number},</c:forEach>
									<c:out value=" ${branch})(For Internal Use Only)"></c:out></h4>
									</td>
<%-- 									<td style="text-align:center;" colspan="4"> <h4>Job Cart for issue(I) <c:out value="(I-${issue.ISSUES_NUMBER} , ${issue.location})"></c:out> (For Internal Use Only)</h4> </td> --%>
								</tr>
								
									<tr>
									<td style="text-align:center;" colspan="4">  Standard - Pre & Post Work Check By Supervisor </td>
								</tr>
							<tr>
								<td>
									<table style="width: 100%" class="border-none">
										<tr>
											<td style="text-align: center; font-weight: bold" width="33%">Standard Check Points :</td>
											<td style="text-align: center; font-weight: bold">Pre Work Observations</td>
											<td style="text-align: center; font-weight: bold">Post Work Observations</td>
										</tr>
										<tr>
											<td>Check all Oil levels ( Engine, Steering, Brake )</td>
											<td></td>
											<td></td>
										</tr>

										<tr>
											<td>Check Battery Electrolyte level, sp. Gravity</td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td>Check coolant level, all cooling cut jobs, hoses</td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td>Check all belt tension conditions</td>
											<td></td>
											<td></td>
										</tr>

										<tr>
											<td>Check for fouling of pipes</td>
											<td></td>
											<td></td>
										</tr>

										<tr>
											<td>Check the all kinds of Leakages</td>
											<td></td>
											<td></td>
										</tr>

										<tr>
											<td>Under body check - Nut & Bolts, fouling of lines</td>
											<td></td>
											<td></td>
										</tr>

										<tr>
											<td>Check all Wheels for free rotation</td>
											<td></td>
											<td></td>
										</tr>

										<tr>
											<td>Check all lights for operation</td>
											<td></td>
											<td></td>
										</tr>

									</table>

								</td>
							</tr>
							<!-- 								<tr><td colspan="4"></td></tr> -->
							<tr>
								<td>&nbsp;</td>
								</tr>
								<tr>
								<td>
								<table class="border-none" style="width: 100%;">
								<tr>
								<td style="text-align: center; font-weight: bold" width="35%">Used Part</td>
								<td style="text-align: center; font-weight: bold">Qty.</td>
								<td style="text-align: center; font-weight: bold"  width="5%">Return?(Y/N)</td>
								<td style="text-align: center; font-weight: bold">Used Lubricant</td>
								<td style="text-align: center; font-weight: bold">Qty.</td>
								</tr>
							<tr style="height:30px">
								<td>1</td>
								<td></td>
								<td></td>
								<td>1</td>
								<td></td>
								</tr>
								<tr style="height:30px">
								<td>2</td>
								<td></td>
								<td></td>
								<td>2</td>
								<td></td>
								</tr>
								<tr style="height:30px">
								<td>3</td>
								<td></td>
								<td></td>
								<td>3</td>
								<td></td>
								</tr>
								<tr style="height:30px">
								<td>4</td>
								<td></td>
								<td></td>
								<td>4</td>
								<td></td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td style="width: 100%">
								<table style="width: 100%">
								<tr>
								<td style="border-right: 1px solid black;" width="55%">Extra:</td>
								<td>Extra:</td>
								</tr>
								<tr>
								<td style="border-right: 1px solid black;">&nbsp;</td>
								<td>&nbsp;</td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td>&nbsp;</td>
								</tr>

							<tr>
								<td>
									<table style="width: 100%">
										<tr>
											<td width="40%">
												<table class="border-none" style="width: 100%">
													<tr>
														<td>A. Repair Hours for all jobs as per Standard
															Hours Master</td>
														<td>_________________HRS</td>
													</tr>
													<tr>
														<td>B. Man hours Spent</td>
														<td>_________________HRS</td>
													</tr>
													<tr>
														<td>C. Actual time taken for repairs ( Receipt to
															completion of Job )</td>
														<td>_________________HRS</td>
													</tr>
													<tr>
														<td>D. Variance (A - B)</td>
														<td>_________________HRS</td>
													</tr>
												</table>
											</td>
											<td style="width: 10%;border-right: 1px solid;border-left: 1px solid;">Ancilary Job If Any</td>
											<td width="40%">
												<table class="border-none" style="width: 100%">
													<tr><td> &nbsp; </td></tr>
													<tr><td>&nbsp;</td></tr>
													<tr><td>&nbsp;</td></tr>
													<tr><td>&nbsp;</td></tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
								<tr>
								<td colspan="4">
								&nbsp;
								</td>
								</tr>
							<tr>
								<td>

									<table style="width: 100%;">
										<tr>
											<td style="width: 50%; border-right: 1px solid;">
												<table style="width: 100%;">
													<tr>
														<td style="border-right: 1px solid black;width: 35%;height: 85px;">Reasons for not meeting the delivery schedule :</td>
														<td>&nbsp;</td>
													</tr>
<!-- 													<tr> -->
<!-- 														<td>&nbsp;</td> -->
<!-- 													</tr> -->
												</table>
											</td>
											<td>
												<table style="width: 100%;">
												<tr>
														<td style="border-right: 1px solid black; width: 26%;height: 25px;">Store person Name & sign :</td>
														<td>&nbsp;</td>
													</tr>
												<tr>
														<td style="border-right: 1px solid black;width: 26%;height: 25px;">Supervisor Name & Sign</td>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td style="border-right: 1px solid black;width: 26%;height: 30px;">Shift Coord Name& sign:</td>
														<td>&nbsp;</td>
													</tr>
													
												</table>
											</td>
										</tr>


									</table>

								</td>
							</tr>
						</tbody>
						</table>
					
					</div>
					
				</div>
			</div>
			</c:if>
			<c:if test="${ServiceProgramList == null}">
			<div class=border-style:solid;>
					<h2 class="page-header" style="text-align: center;">
								<i class="fa fa-globe"></i>
								Issue Not Found !!!
						</h2>
						</div>
			</c:if>
		</section>
</div>