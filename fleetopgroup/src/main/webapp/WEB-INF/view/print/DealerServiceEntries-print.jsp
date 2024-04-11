<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	th{
text-align:left;
font-size: 13px;
}

td
{
font-size: 13px;
 overflow: hidden;
 text-overflow: ellipsis;
 white-space: nowrap;
 padding-left: 5px;

}
@media print {
    .col-print-1 {
       width: 50%; /* 1/12 for printing */
    }
    .div-with-line-break::before {
        content: "\A"; /* Insert a line break before the div */
        white-space: pre; /* Preserve white space */
	}
}
</style>
<div class="wrapper">
	<section class="invoice">
		<div class="row">
			<div class="col-xs-12">
				<h2 class="page-header">
					<c:choose>
						<c:when test="${company.company_id != null}">
							<img src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
								class="img-rounded" alt="Company Logo" width="280" height="40" />
						</c:when>
						<c:otherwise>
							<i class="fa fa-globe"></i>
							<c:out value="${company.company_name}" />
						</c:otherwise>
					</c:choose>
					<small class="pull-right"> Print By: ${company.firstName}_${company.lastName} I. </small> 
					<small>Branch :<c:out value=" ${company.branch_name}  , " /> Department :<c:out value=" ${company.department_name}" />
					</small>
				</h2>
			</div>
		</div>
		<!-- info row -->
		<div class="row invoice-info">
			<h3>${dealerServiceEntries.dealerServiceEntriesNumberStr}</h3>
			<div class="col-sm-12 col-xs-12 invoice-col">
				<div class="box-body no-padding">
					<div class="row">

						<a class="float-left"><h5 id="dealerSENumber"></h5></a>
					</div>
					<div class="row ">
						
						<div class="col col-sm-1 col-md-6 col-print-1">
							<table>
								<tbody>
									<tr>
										<th>Vendor Name:</th>
										<td> <c:out value="${dealerServiceEntries.vendorName}" /></td>
									</tr>
									<tr> 
										<th>Vendor Add:</th>
										<td> <c:out value="${vendorAddress1}" /></td>
									</tr>
									<tr> 
										<th></th>
										<td> <c:out value="${vendorAddress2}" /></td>
									</tr>
									<tr>
										<th>Payment Type:</th>
										<td><c:out value="${dealerServiceEntries.paymentType}" /></td>
									</tr>
									<tr>
										<th>Paid Date :</th>
										<td><c:out value="${dealerServiceEntries.paidDateStr}" /></td>
									</tr>
									<tr>
										<th>DSE Status :</th>
										<td><c:out value="${dealerServiceEntries.status}" /></td>
									</tr>
										<tr>
										<th>Invoice Number :</th>
										<td><c:out value="${dealerServiceEntries.invoiceNumber}" /></td>
									</tr>
									<tr>
										<th>Invoice Date :</th>
										<td class="value"><c:out value="${dealerServiceEntries.invoiceDateStr}" /></td>
									</tr>
									<tr>
										<th>Invoice Amount :</th>
										<td><fmt:formatNumber type="number" pattern="#.##" value="${dealerServiceEntries.totalInvoiceCost}" /></td>
									</tr>

								</tbody>
							</table>
						</div>
						<div class="col col-sm-1 col-md-2">
							<%-- <table class="table">
								<tbody>
									<tr class="row">
										<th class="key">Vendor Name:</th>
										<td class="value"> <c:out value="${dealerServiceEntries.vendorName}" /></td>
									</tr>
									<tr class="row">
										<th class="key">Payment Type:</th>
										<td class="value"><c:out value="${dealerServiceEntries.paymentType}" /></td>
									</tr>
									<tr class="row">
										<th class="key">Paid Date:</th>
										<td class="value"><c:out value="${dealerServiceEntries.paidDateStr}" /></td>
									</tr>
								</tbody> --%>
							</table>
						</div>
						<div class="col col-sm-1 col-md-4 col-print-1 div-with-line-break">
							<table class="table ">
								<tbody>
									<tr>
										<th>Vehicle Number :</th>
										<td><c:out value="${dealerServiceEntries.vehicleNumber}" /></td>
									</tr>
									<tr>
										<th>Vehicle Odometer :</th>
										<td class="value"><c:out value="${dealerServiceEntries.vehicleOdometer}" /></td>
									</tr>
									<tr>
										<th>Vehicle Chasis No :</th>
										<td class="value"><c:out value="${dealerServiceEntries.vehicleChasisNumber}" /></td>
									</tr>
									<tr>
										<th>Vehicle Engine No :</th>
										<td class="value"><c:out value="${dealerServiceEntries.vehicleEngineNumber}" /></td>
									</tr>
									
									<c:if test="${dealerServiceEntries.issueId > 0}">
										<tr>
											<th>Issue No :</th>
											<td class="value"><c:out value="${dealerServiceEntries.issueNumberStr} , ${dealerServiceEntries.issueSummary}" /></td>
										</tr>
									</c:if>
									<tr>
										<th>Driver :</th>
										<td class="value"><c:out value="${dealerServiceEntries.driverFullName}" /></td>
									</tr>
									<tr>
										<th>Part Status :</th>
										<td class="value"><c:out value="${dealerServiceEntries.dsePartStatus}" /></td>
									</tr>
									<tr>
										<th>Labour Status :</th>
										<td class="value"><c:out value="${dealerServiceEntries.labourStatus}" /></td>
									</tr>
									<tr>
										<th>Assign To :</th>
										<td class="value"><c:out value="${dealerServiceEntries.assignTo}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 ">
				<div class="row"  style="line-height: 0.4;">
				<div class="box border border-success">
					<div class="box-body">
						<div class="row">
						<button type="button"  id="reminderHeader" class="btn btn-info"  >Service Reminder</button>
						</div>
						<br>
						<div class="table-responsive">
							<table class="table table-hover table-bordered">
							<thead>
								<tr class="breadcrumb">
									<th>ServiceNo</th>
									<th>Service Schedule</th>
									<th>Due</th>
								</tr>
							</thead>
							<tbody >
								<c:if test="${!empty scheduleList}">
									<c:forEach items="${scheduleList}" var="scheduleList">
										<tr style="line-height: 1.4;">
											 <td> <c:out value="S-${scheduleList.service_Number}" /></td>
											<td>${scheduleList.taskAndSchedule}</td> 
											<td> ${scheduleList. diffrent_time_days}<br> ${scheduleList. diffrent_meter_oddmeter} </td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						</div>
					</div>
				</div>
		</div>
		<c:set var="partSubTotalCost" value="${0}"/>
		<c:set var="partDiscountCost" value="${0}"/>
		<c:set var="partTaxCost" value="${0}"/>
		<c:set var="partTotalCost" value="${0}"/>
				<div class="row" id="partTable" style="line-height: 0.4;">
				<div class="box border border-success">
					<div class="box-body">
						<div class="row">
						<button type="button"  id="partHeader" class="btn btn-info" style="background:lightseagreen;" >Part Details</button>
						</div>
						<br>
						<div class="table-responsive">
							<table class="table table-hover table-bordered">
							<thead>
								<tr class="breadcrumb">
									<th>Part</th>
									<th>Qty</th>
									<th>Each</th>
									<th>Dis</th>
									<th>GST</th>
									<th>Total</th>
								</tr>
							</thead>
							<tbody id="dealerServiceEntriesPartTable">
								<c:if test="${!empty dealerServiceEntriesPart}">
								<c:set var="totalPartCost" value="${0}"/>
									<c:forEach items="${dealerServiceEntriesPart}" var="dealerServiceEntriesPart">
										<tr style="line-height: 1.4;">
											<td> <c:out value="${dealerServiceEntriesPart.partName}  " /></td>
											<td> <c:out value="${dealerServiceEntriesPart.partQuantity}  " /></td>
											<td> <c:out value="${dealerServiceEntriesPart.partEchCost}  " /></td>
											<td> <c:out value="${dealerServiceEntriesPart.partDiscount}  " /></td>
											<td> <c:out value="${dealerServiceEntriesPart.partTax}  " /></td>
											<td> <c:out value="${dealerServiceEntriesPart.partTotalCost}  " /></td>
										</tr>
										<c:set var="partSubTotalCost" value="${partSubTotalCost + (dealerServiceEntriesPart.partQuantity * dealerServiceEntriesPart.partEchCost)}" />
										<c:set var="partDiscountCost" value="${partDiscountCost + dealerServiceEntriesPart.partDiscountCost}" />
										<c:set var="partTaxCost" value="${partTaxCost + dealerServiceEntriesPart.partTaxCost}" />
										<c:set var="partTotalCost" value="${partTotalCost + dealerServiceEntriesPart.partTotalCost}" />
									</c:forEach>
									<tr style="line-height: 1.4;">
										<td  colspan='5'> Total Part Cost</td>
										<td  colspan='1'> <c:out value="${partTotalCost}  " /></td>
									</tr>
								</c:if>
								
							</tbody>
		
							</table>
						</div>
					</div>
				</div>
		</div>
		<c:set var="labourSubTotalCost" value="${0}"/>
		<c:set var="labourDiscountCost" value="${0}"/>
		<c:set var="labourTaxCost" value="${0}"/>
		<c:set var="labourTotalCost" value="${0}"/>
		<div class="row" id="labourTable" style="line-height: 0.4;">
				<div class="box border border-warning" >
					<div class="box-body">
						<div class="row">
							<button type="button"  id="labourHeader" class="btn btn-info" style="background:salmon;" >Labour Details</button>
						</div>
						<br>
						<div class="table-responsive">
							<table class="table table-hover table-bordered">
							<thead>
								<tr class="breadcrumb">
									<th>Labour</th>
									<th>Hrs</th>
									<th>Each</th>
									<th>Dis</th>
									<th>GST</th>
									<th>Total</th>
								</tr>
							</thead>
							<tbody id="dealerServiceEntriesLabourTable">
								<c:if test="${!empty dealerServiceEntriesLabour}">
									<c:forEach items="${dealerServiceEntriesLabour}" var="dealerServiceEntriesLabour">
											<tr style="line-height: 1.4;">
											<td> <c:out value="${dealerServiceEntriesLabour.labourName}  " /></td>
											<td> <c:out value="${dealerServiceEntriesLabour.labourWorkingHours}  " /></td>
											<td> <c:out value="${dealerServiceEntriesLabour.labourPerHourCost}  " /></td>
											<td> <c:out value="${dealerServiceEntriesLabour.labourDiscount}  " /></td>
											<td> <c:out value="${dealerServiceEntriesLabour.labourTax}  " /></td>
											<td> <c:out value="${dealerServiceEntriesLabour.labourTotalCost}  " /></td>
										</tr>
										<c:set var="labourSubTotalCost" value="${labourSubTotalCost +(dealerServiceEntriesLabour.labourWorkingHours*dealerServiceEntriesLabour.labourPerHourCost)}" />
										<c:set var="labourDiscountCost" value="${labourDiscountCost + dealerServiceEntriesLabour.labourDiscountCost}" />
										<c:set var="labourTaxCost" value="${labourTaxCost + dealerServiceEntriesLabour.labourTaxCost}" />
										<c:set var="labourTotalCost" value="${labourTotalCost + dealerServiceEntriesLabour.labourTotalCost}" />
									</c:forEach>
										<tr style="line-height: 1.4;">
										<td  colspan='5'> Total Labour Cost</td>
										<td  colspan='1'> <c:out value="${labourTotalCost}  " /></td>
										</tr>
								</c:if>		
							</tbody>
		
							</table>
						</div>
					</div>
			</div>
			</div>
		</div>
		</div>
		<div class="row col-xs-12" id="summaryTable" style="line-height: 0.4;">
			<div class="box border border-success ">
				<div class="box-body">
					<div class="row">
						<button type="button"  class="btn btn-info" style="background:green;" >Summary</button>
					</div>
					<br>
					<div class="table-responsive">
						<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th class="fit ar"  style='font-size: 15px;'>Part Summary</th>
								<th class="fit ar" style='font-size: 15px;'>Labour Summary</th>
							</tr>
						</thead>
						<tbody >
							<tr>
							<td><table style="width: 100%;">
								<tbody>
									<tr >
										<th>Sub Amount: <i class="fa fa-inr"></i><fmt:formatNumber type="number" pattern="#.##" value="${partSubTotalCost}" /></th>
									</tr>
									<tr >
										<th>Discount Amount: <i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${partDiscountCost}" /></th>
									</tr>
									<tr>
										<th>Taxable Amount: <i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${partTaxCost}" /></th>
									</tr>
									<tr >
										<th>Total Amount: <i class="fa fa-inr"></i><fmt:formatNumber type="number" pattern="#.##" value="${partTotalCost}" /></th>
									</tr>
								</tbody>
							</table></td>
							<td><table style="width: 100%; ">
								<tbody>
									<tr >
										<th>Sub Amount: <i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${labourSubTotalCost}" /></th>
									</tr>
									<tr >
										<th>Discount Amount: <i class="fa fa-inr"></i><fmt:formatNumber type="number" pattern="#.##" value="${labourDiscountCost}" /></th>
									</tr>
									<tr>
										<th>Taxable Amount: <i class="fa fa-inr"></i><fmt:formatNumber type="number" pattern="#.##" value="${labourTaxCost}" /></th>
									</tr>
									<tr >
										<th>Total Amount: <i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${labourTotalCost}" /></th>
									</tr>
								</tbody>
							</table></td>
							</tr>
						</tbody>
						</table>
					</div>
					<div class="row">
						<table style="width: 100%; ">
							<tbody>
								<tr >
									<th style="padding-right: 10%; font-size: 18px;"> Total Dealer Service Cost : <i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${dealerServiceEntries.totalInvoiceCost}" /></th>
								</tr>
							</tbody>
						</table>
					</div>
					<%-- <div class="row" style="padding-bottom: 15px;">
						<table style="width: 100%;">
							<tbody>
								
								<tr>
									<th style="font-size: 18px; text-align: left;" > Extra Issue :  <c:out value="${dealerServiceEntries.dseExtraIssue}"/></th>
								</tr>
								
							</tbody>
						</table>
					</div>
					<div class="row">
						<table style="width: 100%; ">
							<tbody>
								
								<tr>
									<th style="font-size: 18px;text-align: left;"> Remark :  <c:out value="${dealerServiceEntries.remark}"/></th>
								</tr>
							</tbody>
						</table>
					</div> --%>
				</div>
			</div>
		</div>
		<br>
		<c:if test="${!empty dealerServiceExtraIssueList}">
		<div class="row">
			<button type="button"  class="btn btn-info" style="background:skyblue;" >Extra Issues</button>
		</div>
		<br>
		<div class="row">
			<ul class="timeline">
				
					<c:forEach items="${dealerServiceExtraIssueList}" var="dealerServiceExtraIssueList">
						<li class="time-label">
						 <span style="background-color: red;"> ${dealerServiceExtraIssueList.creationDate}</span> 
						 
						 </li>
						<li><i class="fa fa-comments bg-blue"></i>
							<div class="timeline-item">
								<div class="timeline-body">
									<c:out value="${dealerServiceExtraIssueList.description}" />
								</div>
							</div></li>

					</c:forEach>
			
			</ul>
		</div>
			</c:if>
		<br>
		<div class="row">
		<div class="col-xs-12">
			<c:if test="${!empty dealerServiceRemarkList}">
				<div class="row">
					<button type="button"  class="btn btn-info" style="background:blue;" >Remark</button>
				</div>
			    <br>
				<div class="row">
					<ul class="timeline">
						
							<c:forEach items="${dealerServiceRemarkList}" var="dealerServiceRemarkList">
								<li class="time-label">
									<span style="background-color: orange;"> ${dealerServiceRemarkList.remarkType} </span>
									<span style="background-color: red;"> ${dealerServiceRemarkList.creationDate}</span> 
									<c:if test="${dealerServiceRemarkList.assignee != null && dealerServiceRemarkList.assignee > 0}">
										<span class="bg-orange">Confirmed With Assignee : ${dealerServiceRemarkList.assigneeName}</span>
									</c:if> 
									<c:if test="${dealerServiceRemarkList.driverId != null && dealerServiceRemarkList.driverId  > 0}">
										<span class="bg-orange">Confirmed With Driver : ${dealerServiceRemarkList.driverName}</span>
									</c:if>
								</li>
								<li><i class="fa fa-comments bg-yellow"></i>
									<div class="timeline-item">
										<div class="timeline-body">
											<c:out value="${dealerServiceRemarkList.remark}" />
										</div>
									</div></li>
		
							</c:forEach>
						
					</ul>
				</div>
			</c:if>
		</div>	
		</div>
	</section>
</div>

<script>
	$('#back-to-top').hide();
</script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>	
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/ShowDealerServiceEntries.js"></script>	
<!-- ./wrapper -->
