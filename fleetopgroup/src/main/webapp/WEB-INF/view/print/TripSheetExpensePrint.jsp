<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


<style>
	.caps
	{
		text-transform:uppercase;
	}
	.capt
	{
		text-transform:Capitalize;
	}
	.text-align
	{
		text-align:center;
	}
	.center
	{
		margin-left:auto;
		margin-right:auto;
	}
	.collapse
	{
		border-collapse:collapse;
	}
	.border-right
	{
		border-right:1.5px solid;
	}
	.border-bottom
	{
		border-bottom:1.5px solid;
	}
</style>

</head>
<body>
	
	
	
	
	
	<div class="center">
		
		<h2 class="text-align caps"><u><c:out value="${company.company_name}" /></u></h2>
		<h3 class="text-align caps"><b><c:out value="${company.company_address}" /></b></h3>
		<h4 class="text-align caps">gst no: <c:out value="" /></h4>
		
			
		<div style="margin-top:40px" class="text-align">
			<h4  class="text-center caps"><c:out value="${TripSheet.vehicle_registration}" />
			<br> <c:out value="${TripSheet.routeName}"/></h4>
			<h4 class="caps text-center"></h4>
		</div>
		
		
		<table style="width:88%;" class="center table">
			<tr>
				<td style="text-align:left;" class="text-align"><h3>Trip Number : TS- ${TripSheet.tripSheetNumber}</h3></td> <!-- style="margin-left:45px;" -->
				<td style="text-align:right;"><p class=""><b>Created Date :</b> ${TripSheet.created}</p></td> <!-- style="margin-left:100px;align-item:right"  -->
			</tr>
		</table>
		
		
		<table style="width:88%;border:2px solid;" class="center table">
			<tr>
				<th style="width:6%" class="capt text-align border-bottom border-right ">no</th>
				<th style="width:31%" class="capt text-align border-bottom border-right">expense name</th>
				<th style="width:8%" class="capt text-align border-bottom border-right">type</th>
				<th style="width:40%" class="capt text-align border-bottom border-right">place</th>
				<th style="width:7%" class="capt text-align border-bottom border-right">ref</th>
				<th style="width:10%" class="capt text-align border-bottom border-right">amount</th>	
			</tr>
			<tr>
				<c:if test="${!empty TripSheetExpense}">
				<c:forEach items="${TripSheetExpense}" var="TripSheetExpense" varStatus="loop">
				
					<tr>
						<td class="text-align border-bottom border-right">
							${loop.index+1}
						</td>
						<td class="capt border-bottom text-align border-right"><c:out value="${TripSheetExpense.expenseName}" /></td>
						
					
							
						<td class="capt border-bottom text-align border-right"><c:choose>
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
						
						<td class="capt border-bottom text-align border-right"><c:out value="${TripSheetExpense.expensePlace}" /></td>
						<td class="capt border-bottom text-align border-right"><c:out value="${TripSheetExpense.expenseRefence}" /></td></td>
						<td class="capt border-bottom text-align border-right"><c:out value="${TripSheetExpense.expenseAmount}" /></td>
					</tr>
				</c:forEach>
				</c:if>	
			</tr>
			
			<tr>
				<td colspan="5" class="capt border-bottom border-right" style="text-align:right;padding-right:20px;"><b>total expenses</b></td>
				<td class="text-align border-right">${expenseTotal}</td>
			</tr>
			
		</table>
	</div>
	
		
	
	
</body>
</html>