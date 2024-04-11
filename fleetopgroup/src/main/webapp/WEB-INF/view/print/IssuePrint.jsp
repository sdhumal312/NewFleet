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
padding: 1px
}

.border-none {
  border-collapse: collapse;
   border: none; 
}
tbody{
font-size: 11px;
font-weight: bold; 
}

.border-none td {
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


</style>
<div class="wrapper">
		<section class="invoice">
			<!-- title row -->
			<c:if test="${issue != null }">
				<div class="" style="border-top: 1px solid black;">
				<div class=border-style:solid;>
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
									<td style="text-align:center;" colspan="4"> <h4>Job Cart for issue(II) <c:out value="(I-${issue.ISSUES_NUMBER}, ${issue.location}) "></c:out></h4></td>
								</tr>
								<tr>
								<td>
								<table  style="width: 100%">
								<tr>
									<td>
										<table style="width: 100%;">
											<tr>
											<td style="width:35%;border-right: 1px solid black"> Job card printed on: </td><td style=" border-right: 1px solid black">${date}</td>
											</tr>
											<tr>
											<td style="width:35%;border-right: 1px solid black"> Job Start Date: </td><td style=" border-right: 1px solid black"></td>
											</tr>
											<tr>
											<td style="width:35%;border-right: 1px solid black"> Job End Date: </td><td style=" border-right: 1px solid black"></td>
											</tr>
										</table>
									</td>
									<td>
									<table style="width:100%">
									<tr>
									<td style="width:35%;border-right: 1px solid black">Vehicle No.& Type:</td><td>${issue.ISSUES_VEHICLE_REGISTRATION} & ${issue.vehicleType}</td>
									</tr>
									<tr><td style="width:35%;border-right: 1px solid black">Driver name & No:</td><td> ${issue.ISSUES_DRIVER_NAME} & ${issue.driver_mobnumber}</td></tr>
									<tr><td style="width:35%;border-right: 1px solid black">Odometer:</td><td>${issue.ISSUES_ODOMETER}</td></tr>
									</table>
									</td>
								</table>
								</td>
								</tr>
<!-- 								<tr><td colspan="4"></td></tr> -->
								<tr>
								<td colspan="4">
								<table class="border-none" style="width: 100%;">
								<tr>
								<td >Issue Type:</td> <td colspan="3">${issue.ISSUES_TYPE}</td>
								</tr>
								<tr>
								<td >Issue Category:</td><td colspan="3">${issue.partCategoryName}</td>
								</tr>
								<tr>
								<td >Issue Label:</td><td  colspan="3"> ${issue.ISSUES_LABELS}</td>
								</tr>
								<tr>
								<td >Issue Summary:</td><td colspan="3">${issue.ISSUES_SUMMARY}</td>
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
								<td style="width:100%">
								<table  style="width:100%;" class="border-none">
								<tr>
									<td style="text-align: center;">Job Type</td>
									<td style="text-align: center;">JobSub Type</td>
									<td style="text-align: center;">Job Details</td>
									</tr>
									<tr>
									<td>1</td>
									<td>1</td>
									<td></td>
									</tr>
									<tr>
									<td>2</td>
									<td>2</td>
									<td></td>
									</tr>
									<tr>
									<td>3</td>
									<td>3</td>
									<td></td>
									</tr>
									<tr>
									<td>4</td>
									<td>4</td>
									<td></td>
									</tr>
									<tr>
									<td>5</td>
									<td>5</td>
									<td></td>
									</tr>
									<tr>
									<td>6</td>
									<td>6</td>
									<td></td>
									</tr>
									<tr>
									<td>7</td>
									<td>7</td>
									<td></td>
									</tr>
									<tr>
									<td>8</td>
									<td>8</td>
									<td></td>
									</tr>
									<tr>
									<td>9</td>
									<td>9</td>
									<td></td>
									</tr>
									<tr>
									<td>10</td>
									<td>10</td>
									<td></td>
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
												<table style="width: 100%;">
													<tr>
														<td>Technician name& sign</td>
													</tr>
													<tr>
														<td>Shift:</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="width: 100%;">
													<tr>
														<td>Shift Coord Name& sign:</td>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td>Supervisor Sign :</td>
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
			<c:if test="${issue == null}">
			<div class=border-style:solid;>
					<h2 class="page-header" style="text-align: center;">
								<i class="fa fa-globe"></i>
								Issue Not Found !!!
						</h2>
						</div>
			</c:if>
		</section>
</div>