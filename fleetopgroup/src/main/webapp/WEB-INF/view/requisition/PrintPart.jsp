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
padding: 2px;
}

	#header tr td {
		font-size: 14px;
	}
	table
		th {
  text-align: left;
}

body {
	background-color: #FFF;
}

<style>
  #test {
    width:100%;
    height:100%;
  }
  table {
    margin: 0 auto; /* or margin: 0 auto 0 auto */
  }
</style>

	


				<% int i = 0; %>
						
								<table border="1" style="width:90%;border-collapse: collapse; ">
									<tr  bgcolor="LightGray">
										<td></td>
										<th colspan="2" style="font-size:20px"><c:out value="${company.company_name}"></c:out></th>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									
									<tr>
										<th> Date</th>
										<td ><c:out value="${requisitionDto.requireOnStr}"></c:out> </td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									
									<tr>
										<th>GIN No.</th>
										<td><c:out value="${requisitionDto.refNumber}"></c:out></td>
										<th>Issued</th>
										<td><c:out value="${requisitionDto.locationName}"></c:out></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<th><font size="3">Details of Parts Issued</font></th>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										
									</tr>
									<tr id="header">
     
         								<th>Sr. No</th>
										<th>Description</th>
										<th>Issued From</th>
										<th>Make</th>
										<th>Item Code</th>
										<th>Date</th>
										<th>Qty</th>
										<th>UOM</th>
									</tr>
									<c:forEach var="approvaldto" items="${requisitionApprovalDetailsDtoList}">
										<c:if test="${approvaldto.transferedQuantity-approvaldto.receivedQuantity > 0}">
											<tr id="tableData">
												<td><% out.print(++i); %></td>
												<td>${approvaldto.partName}</td>
												<td>${approvaldto.branchName}</td>
												<td>${approvaldto.pmName}</td>
												<td>${approvaldto.partNumber}</td>
												<td>${approvaldto.transferedDateStr}</td>
												<td>${approvaldto.transferedQuantity-approvaldto.receivedQuantity}</td>
												<td>${approvaldto.pmuName}</td>
												
											</tr>
										</c:if>
									</c:forEach>
									
									<tr>
										<td></td>
										<th>Issued by</th>
										<td></td>
										<th><c:out value="${requisitionApprovalDetailsDto.userName}"></c:out></th>
										<td></td>
										<td></td>
										
									</tr>
								</table>
								
</tbody>
</div>

