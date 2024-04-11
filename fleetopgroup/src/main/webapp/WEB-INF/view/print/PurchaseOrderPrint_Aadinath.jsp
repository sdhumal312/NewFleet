<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	.border-bottom
	{
		border-bottom:1.5px solid;
	}
	.border-top
	{
		border-top:1.5px solid;
	}
	.border-right
	{
		border-right:1.5px solid;
	}
	.text-align
	{
		text-align:center;
	}
	.bg-color
	{
		background-color:black;
	}
	.color
	{
		color:white;
	}
	.center
	{
		margin-left:auto;
		margin-right:auto;
	}
</style>

</head>
<body>

	<table style="border:2px solid;width:98%;margin-top:2%;border-collapse:collapse;" class="center">
		
		<tr class="text-align">
			<td class="caps border-bottom"><b><c:out value="${PurchaseOrder.purchaseorder_buyer}" /><!-- aadhinath bulk pvt ltd --></b></td>
		</tr>
		<tr class="text-align">
			<td class="capt"><c:out value="${PurchaseOrder.purchaseorder_buyeraddress}" /> <!-- 246, Madhupura GanjBazar, Opp Ambajimata Temple, Ahmedabad-380004 --></td>
		</tr>
		<tr class="text-align bg-color color">
			<td class="caps">purchase order</td>
		</tr>
		<tr>
			<td style="height:15px"></td>
		</tr>
		<tr>
			<td style="width:100%">
				<table style="width:100%;border-collapse:collapse;">
					<tr style="width:100%">
						<td style="width:100%">
							<table style="width:100%;border-collapse:collapse">
								<tr>
									<td style="width:19%" class="capt border-top border-right border-bottom"><b>vendor name:</b></td>
									<td style="width:23%" class="caps border-top border-right border-bottom"><c:out value="${PurchaseOrder.purchaseorder_vendor_name}" /><!-- wipec industries --></td>
									<td style="width:6%" class="capt border-top border-right border-bottom"><b>ship to:</b></td>
									<td style="width:27%" class="caps border-top border-right text-align border-bottom"><c:out value="${PurchaseOrder.purchaseorder_shiplocation}" /><!-- chandola --></td>
									<td style="width:7%" class="caps border-top border-right border-bottom"><b>po no:</b></td>
									<td style="width:18%" class="caps border-top text-align border-bottom"><c:out value="${PurchaseOrder.purchaseorder_Number }" />  <!-- gsrtc/feb/4994 --></td>
								</tr>
								<tr>
									<td class="capt border-right border-bottom"><b>contact person:</b></td><!-- purchaseorder_vendor_name -->
									<td class="capt border-bottom"><c:out value="${PurchaseOrder.contactPerson}  ${PurchaseOrder.vendorFirPhone}" /> <!-- #N/A --> </td>
									<td class="capt border-bottom"></td>
									<td class="border-right border-bottom"></td>
									<td class="capt border-right border-bottom"><b>Wo/ indent no:</b></td>
									<td class="text-align border-bottom">
										<c:out value="${PurchaseOrder.purchaseorder_workordernumber}" />
										/ <c:out value="${PurchaseOrder.purchaseorder_indentno}" />
									</td>
								</tr>
								<tr>
									<td class="capt border-right border-bottom"><b>address:</b></td><!-- purchaseorder_vendor_location -->
									<td class="capt border-bottom "><c:out value="${PurchaseOrder.vendorAddress1}" /> <!-- #N/A --></td>
									<td class="border-bottom"></td>
									<td class="border-right border-bottom"></td>
									<td class="capt border-right border-bottom"><b>date:</b></td>
									<td class="text-align border-bottom"><c:out value="${PurchaseOrder.purchaseorder_created_on}" /></td><!-- ${PurchaseOrder.purchaseorderOrderdate} -->
								</tr>
								<tr>
									<td style="width:15%;" class="capt border-right border-bottom"><b>invoice no:</b></td>
									<td style="width:27%;" class="capt text-align border-right border-bottom">
	 									<c:if test="${PurchaseOrder.purchaseorder_invoiceno != null}">
											<c:out value="${PurchaseOrder.purchaseorder_invoiceno}" />
										</c:if>
									</td>
									<td style="width:8%;" class="capt border-right border-bottom"><b>invoice date:</b></td>
									
									<td style="width:27%;" class="capt text-align border-right border-bottom">
										<c:if test="${PurchaseOrder.purchaseorder_invoice_date != null}">
											<c:out value="${PurchaseOrder.purchaseorder_invoice_date}" />
										</c:if>
									</td>
									<td style="width:8%;" class="capt border-right border-bottom"><!-- <b>remark:</b> --></td>
									<td style="width:18%;" class="capt text-align border-bottom">
										<%-- <c:if test="${PurchaseOrder.purchaseorder_status == 'RECEIVED'}">
										<c:out value="${PurchaseOrder.purchaseorder_received_remark}" />
										</c:if> --%>
									</td>
									
								</tr>
								<c:if test="${PurchaseOrder.purchaseorder_received_remark == null}">
								<tr>
									<td style="width:15%;" class="capt border-right border-bottom">
										<b>remark:</b>
									</td>
									<td style="width:85%;" colspan="5" class="capt border-bottom"><c:out value="${PurchaseOrder.purchaseorder_received_remark}" /></td>
								</tr>
								</c:if>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td style="height:15px"></td>
		</tr>
		
		<tr>
			<td style="width:100%">
				<table style="width:100%;border-collapse:collapse">
					<tr style="height:30px;">
						<td style="width:6%;" class="text-align capt border-top border-right border-bottom"><b>sr. no.</b></td>
						
						
						<c:if test="${PurchaseOrder.purchaseorder_typeId == 1 || PurchaseOrder.purchaseorder_typeId == 5}">
							<td style="width:13%" class="text-align capt border-top border-right border-bottom"><b>part number</b></td>
							<td style="width:46%" class="text-align capt border-top border-right border-bottom"><b>part description</b></td>
						</c:if>
						
						<c:if test="${PurchaseOrder.purchaseorder_typeId == 3}"><!-- ${PurchaseOrder.purchaseorder_type == 'BATTERY-PO'} -->
							<td style="" class="text-align capt border-top border-right border-bottom"><b>Battery Manufacturer</b></td>
							<td class="text-align capt border-top border-right border-bottom"><b>Battery Capacity</b></td>
							<td class="text-align capt border-top border-right border-bottom"><b>Battery model</b></td>
						</c:if>
						
						<c:if test="${PurchaseOrder.purchaseorder_typeId == 2}"><!-- ${PurchaseOrder.purchaseorder_type == 'TYRE-PO'} -->
							<td class="text-align capt border-top border-right border-bottom"><b>tyre manufacturer</b></td>
							<td class="text-align capt border-top border-right border-bottom"><b>tyre size</b></td>
							<td class="text-align capt border-top border-right border-bottom"><b>tyre model</b></td>
						</c:if>
						
						
						
						<td style="width:5%;" class="text-align capt border-top border-right border-bottom"><b>qty.</b></td>
						
						<c:if test="${PurchaseOrder.purchaseorder_status == 'RECEIVED'}">
							<td style="width:5%" class="text-align capt border-top border-right border-bottom"><b>qty rec.</b></td>
						</c:if>
						
						<td style="width:5%;" class="text-align capt border-top border-right border-bottom"><b>basic rate.</b></td>
						<td style="width:5%;" class="text-align caps border-top border-right border-bottom"><b>dis</b></td>
						<td style="width:5%;" class="text-align caps border-top border-right border-bottom"><b>tax</b></td>
						<td style="width:10%;" class="text-align capt border-top border-bottom"><b>amount rupees.</b></td>
					</tr>
					
					<c:if test="${!empty PurchaseOrderPart}">
					
					<%-- <c:set var="tbr" value="${0}" />  --%>
					
					<c:set var="totalcost" value="${0}" /> <!--in received case  -->
						<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart" varStatus="loop">
						
						<tr>
							<td class="text-align capt border-top border-right border-bottom">
								<b>
									 ${loop.index+1}
								</b>
							</td>
							
							<c:if test="${PurchaseOrder.purchaseorder_typeId == 2}"> <!-- ${PurchaseOrder.purchaseorder_type == 'TYRE-PO'} -->
								<td class="text-align capt border-top border-right border-bottom">${PurchaseOrderPart.TYRE_MANUFACTURER}</td>
								<td class="text-align capt border-top border-right border-bottom">${PurchaseOrderPart.TYRE_SIZE}</td>
								<td class="text-align capt border-top border-right border-bottom">${PurchaseOrderPart.TYRE_MANUFACTURER} ${PurchaseOrderPart.TYRE_MODEL}</td>
							</c:if>
							
							<c:if test="${PurchaseOrder.purchaseorder_typeId == 3}"> <!-- ${PurchaseOrder.purchaseorder_type == 'BATTERY-PO'} -->
								<td class="text-align capt border-top border-right border-bottom">${PurchaseOrderPart.TYRE_MANUFACTURER}</td>
								<td class="text-align capt border-top border-right border-bottom">${PurchaseOrderPart.TYRE_SIZE}</td>
								<td class="text-align capt border-top border-right border-bottom">${PurchaseOrderPart.TYRE_MODEL}</td>
							</c:if>
							
							<c:if test="${PurchaseOrder.purchaseorder_typeId == 1 || PurchaseOrder.purchaseorder_typeId == 5}">
							<td class="text-align capt border-top border-right border-bottom"><c:out value="${PurchaseOrderPart.purchaseorder_partnumber}" /></td>
							<td class="capt border-top border-right border-bottom">
							
								<%-- <c:choose>
									<c:when test="${PurchaseOrder.purchaseorder_typeId == 2}">
										<c:out value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
										<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>
									</c:when>
									<c:when test="${PurchaseOrder.purchaseorder_typeId == 3}">
										<c:out value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
										<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>								
									</c:when>
									<c:when test="${PurchaseOrder.purchaseorder_typeId == 4}">
										<c:out value="${PurchaseOrderPart.clothTypeName}" />
									</c:when>
									<c:otherwise>
										<c:out value="${PurchaseOrderPart.purchaseorder_partname}" /> 
										<c:out value="${PurchaseOrderPart.purchaseorder_partnumber}" /><br>
									</c:otherwise>
								</c:choose> --%>
								
								<c:out value="${PurchaseOrderPart.purchaseorder_partname}" /> 
								
							</td>
							</c:if>
							
							<td class="text-align capt border-top border-right border-bottom"><c:out value="${PurchaseOrderPart.quantity}"/></td>
							<c:if test="${PurchaseOrder.purchaseorder_status == 'RECEIVED'}">
								<td class="text-align capt border-top border-right border-bottom"><c:out value="${PurchaseOrderPart.received_quantity}"/></td>
							</c:if>
							
							<td class="text-align capt border-top border-right border-bottom"><c:out value="${PurchaseOrderPart.parteachcost}"/> </td>
							<td class="text-align caps border-top border-right border-bottom"><c:out value="${PurchaseOrderPart.discount}"/></td>
							<td class="text-align caps border-top border-right border-bottom"><c:out value="${PurchaseOrderPart.tax}"/></td>
							
							<c:if test="${PurchaseOrder.purchaseorder_status == 'RECEIVED'}">
							
								<td class="text-align capt border-top border-bottom">
									<c:set var="subcost" value="${PurchaseOrderPart.received_quantity * PurchaseOrderPart.parteachcost}" />
									<c:set var="Gst" value="${(PurchaseOrderPart.tax/100) * subcost}" />
									<c:set var="costWithGst" value="${Gst + subcost}"/>
									<c:set var="dis" value="${(PurchaseOrderPart.discount/100) * costWithGst}"/>
									<c:set var="finalCost" value="${costWithGst-dis}"/>
									<c:out value="${finalCost}"/>
								</td>
								
							</c:if>
							
							
							<c:if test="${PurchaseOrder.purchaseorder_status != 'RECEIVED'}">
								<td class="text-align capt border-top border-bottom">
									<c:out value="${PurchaseOrderPart.totalcost}"/>
								</td>
							</c:if>
								
						
						</tr>
					 <%-- <c:set var=totalcost"tbr" value="${tbr + PurchaseOrderPart.parteachcost}" /> --%>
					 
					 <c:set var="totalcost" value="${totalcost+finalCost}"/>
					 
					</c:forEach>
					</c:if>
					
					<tr>
						<td class="text-align capt border-top border-bottom"><b></b></td>
						
						<c:if test="${PurchaseOrder.purchaseorder_typeId == 2 || PurchaseOrder.purchaseorder_typeId == 3}">
						 	<td class="text-align capt border-top border-bottom"><b></b></td>
						</c:if>
						
						<td class="caps border-top border-right border-bottom"><b>total</b></td>
						<td class="text-align capt border-top border-right border-bottom"><b></b></td>
						
						<td class="text-align capt border-top border-right border-bottom"><b><c:out value="${TotalOrdered}" /></b></td>
						
						<c:if test="${PurchaseOrder.purchaseorder_status == 'RECEIVED'}">
							<td class="text-align capt border-top border-right border-bottom"><b>${TotalRecevied}<%-- <c:out value="${PurchaseOrderPart.received_quantity}"/> --%></b></td>
						</c:if>
						
						<td class="text-align capt border-top border-right border-bottom"><b><%-- <c:out value="${tbr}" /> --%></b></td>
						
						
						<td class="text-align caps border-top border-right border-bottom ">
							<b>
							<%-- <c:if test="${poConfiguration.totalDiscountCost}">
								<c:out value="${TotalDiscountCost}"/>
							</c:if> --%>
							</b>
						</td>
						
						<td class="text-align caps border-top border-right border-bottom"><b><%-- <c:out value="${PurchaseOrder.purchaseorder_totaltax_cost}" /> --%></b></td>
						
						<c:if test="${PurchaseOrder.purchaseorder_status == 'RECEIVED'}">
							<td class="text-align capt border-top border-bottom text-align"><b><c:out value="${totalcost}"/></b></td>
						</c:if>
						<c:if test="${PurchaseOrder.purchaseorder_status != 'RECEIVED'}">
							<td class="text-align capt border-top border-bottom text-align"><b><c:out value="${PurchaseOrder.purchaseorder_totalcost}"/></b></td>
						</c:if>
						
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="height:15px"></td>
		</tr>
	</table>
	
	
	<p class="text-align">Note: Invoice supporting this purchase order will be considered valid please find this along with Invoice.</p>
	
	<table class="center" border=2 style="width:35%;border-collapse:collapse">
		<tr>
			<td style="width:15%" class="bg-color caps color text-align">approved by</td>
			<td style="width:20%" class="bg-color caps color text-align">approved by</td>
		</tr>
		<tr style="height:80px">
			<td style="width:15%"></td>
			<td style="width:20%"></td>
		</tr>
		<tr>
			<td style="width:15%" class="caps text-align"><b>nikhil trivedi</b></td>
			<td style="width:20%" class="caps text-align"><b>jinal jain</b></td>
		</tr>
	</table>
	
	
</body>
</html>