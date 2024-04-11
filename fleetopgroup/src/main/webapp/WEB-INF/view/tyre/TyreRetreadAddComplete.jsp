<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
function getTyreRetreadPrint(TRID) {
	
	childwin = window.open('PrintTyreRetread?TRID='+TRID,'newwindow', config='height=300,width=425, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, directories=no, status=no');
}
</script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventoryNew/1.in"/>">Tyre Inventory</a>
					/ <a href="<c:url value="/TyreRetreadNew/1.in"/>">Tyre Retread
						bills</a> / <span> Retread Tyre </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreRetreadNew/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="box">
			<div class="box-body">
				<sec:authorize access="!hasAuthority('RECEIVED_TYRE_RETREAD')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('RECEIVED_TYRE_RETREAD')">
					<div class="row">

						<div class="col-md-7">
							<h4>
								Tyre Retread ${Retread.TRNUMBER } <span
									class="label label-pill label-warning"><c:out
										value="${Retread.TR_STATUS}" /></span>
							</h4>
							<h4>
								<a href="ShowVendor.in?vendorId=${Retread.TR_VENDOR_ID}"
									data-toggle="tip" data-original-title="Click vendor Info">
									<c:out value="${Retread.TR_VENDOR_NAME} - " /> <c:out
										value="${Retread.TR_VENDOR_LOCATION}" />
								</a>
							</h4>
							<div class="secondary-header-title">
									<input type="hidden" id="invoiceId" value="${Retread.TR_INVOICE_NUMBER}"/>			
								<ul class="breadcrumb">
									<li><span class="fa fa-user"> Payment Type:</span> <a
										data-toggle="tip" data-original-title="Terms "><c:out
												value="${Retread.TR_PAYMENT_TYPE}" /></a></li>
									<li><i class="fa fa-bitcoin"> Retread date:</i> <a
										data-toggle="tip" data-original-title="Retread date"><c:out
												value="${Retread.TR_OPEN_DATE}" /></a></li>
									<li><span class="fa fa-user"> Required date:</span> <a
										data-toggle="tip" data-original-title="Required date"><c:out
												value="${Retread.TR_REQUIRED_DATE}" /></a></li>
									<li><span class="fa fa-user"> Quote No:</span> <a
										data-toggle="tip" data-original-title="Quote No"><c:out
												value="${Retread.TR_QUOTE_NO}" /></a></li>
									<li><span class="fa fa-user"> Manual No:</span> <a
										data-toggle="tip" data-original-title=" Manual No"><c:out
												value="${Retread.TR_MANUAL_NO}" /></a></li>
									<li><span class="fa fa-user"> Payment No:</span> <a
										data-toggle="tip" data-original-title=" Payment No"><c:out
												value="${Retread.TR_PAYMENT_NUMBER}" /></a></li>
									<li><span class="fa fa-user"> Invoice No:</span> <a
										data-toggle="tip" data-original-title=" Invoice No"><c:out
												value="${Retread.TR_INVOICE_NUMBER}" /></a></li>
									<li><span class="fa fa-user"> Invoice Date:</span> <a
										data-toggle="tip" data-original-title="Invoice Date"><c:out
												value="${Retread.TR_INVOICE_DATE}" /></a></li>
									<li><span class="fa fa-user"> Vendor Payment Status:</span> 
										<a data-toggle="tip" data-original-title="Invoice Date">
										<c:choose>
											<c:when test="${Retread.TR_VENDOR_PAYMODE_STATUS_ID == 4 || Retread.TR_VENDOR_PAYMODE_STATUS_ID == 5 }">
												<c:out value="${Retread.TR_VENDOR_PAYMODE_STATUS} PAID" />
											</c:when>
											<c:otherwise>
												<c:out value="${Retread.TR_VENDOR_PAYMODE_STATUS}" />
											</c:otherwise>
										</c:choose>
										
										</a>
									</li>
									<li><span class="fa fa-user"> Approval Number:</span> <c:choose>
										<c:when test="${Retread.TR_VENDOR_PAYMODE_STATUS_ID == 6}">
											<a href="AddServiceApproval.in?approvalId=${Retread.approvalId}">
											 <c:out value="${Retread.approvalNumber}" />
											</a>
											</c:when>
											<c:otherwise>
											<a href="ShowApprovalPayment.in?approvalId=${Retread.approvalId}">
											 <c:out value="${Retread.approvalNumber}" />
											</a>
											</c:otherwise>
										</c:choose>
									</li>
								</ul>
							</div>
						</div>
						<div class="col-md-4">
							<div class="row">
								<input type="hidden" id="statues" name="statues"
									value="${Retread.TR_STATUS}">
								<div id="work-order-statuses">
									<div id="work-order-statuses">
									<sec:authorize access="hasAuthority('REOPEN_TYRE_RETREAD')">
										<c:if test="${Retread.TR_VENDOR_PAYMODE_STATUS_ID == 2}">	
											<a data-method="post"
											onclick="reOpenRetreadTyre(${Retread.TRID});"
												<%-- href="ReopenTyreRetread?TRID=${Retread.TRID}" --%>
												data-remote="true" rel="nofollow"> <span
												id="status-in-progress" class="status-led"> <i
													class="fa fa-circle"></i><div>Re-open</div>
													
											</span>
											</a>
										</c:if>
										 </sec:authorize><a data-disable-with="..." data-method="post"
											data-remote="true" rel="nofollow"> <span id="status-open"
											class="status-led-open"> <i class="fa fa-circle"></i>
												Completed
										</span>
										</a>
									</div>
									<div class="pull-right" id="status-close">
										<sec:authorize access="hasAuthority('TYRERETREAD_PRINT')">
											<%-- <c: test="${configuration.showBatteryInventoryPrint}"> --%>
												<input type="button"  class="btn btn-default fa fa-print" 
														onclick="getTyreRetreadPrint(${Retread.TRID});" 
														value="Print" />
												</c>    
										 </sec:authorize>
									</div>
									<sec:authorize access="hasAuthority('DOWNLOND_TYRE_RETREAD')">
										<a class="btn btn-default" style="width: 20%"
											href="${pageContext.request.contextPath}/download/TyreDocument/${Retread.TRID}.in">
											<span class="fa fa-download"> Document</span>
										</a>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
					<br>
					<fieldset>
						<div class="row">
							<div class="col-md-11">
								<div class="table-responsive">
									<table class="table">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">Status</th>
												<th>Retread Tyre</th>
												<th class="fit ar">Tyre Cost</th>
												<th class="fit ar">Discount</th>
												<th class="fit ar">GST</th>
												<th class="fit ar">Total</th>
												<sec:authorize access="hasAuthority('EDIT_TYRE_RETREAD')">
												<c:if test="${Retread.TR_VENDOR_PAYMODE_STATUS_ID == 2}">
													<th class="fit ar">Action</th>
												</c:if>	
												</sec:authorize>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty RetreadAmount}">
												<c:forEach items="${RetreadAmount}" var="RetreadAmount">
													<tr data-object-id="" class="ng-scope">
														<td class="fit"><c:choose>
																<c:when test="${RetreadAmount.TRA_STATUS =='RECEIVED'}">
																	<span class="label label-pill label-success"><c:out
																			value="${RetreadAmount.TRA_STATUS}" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-pill label-danger"><c:out
																			value="${RetreadAmount.TRA_STATUS}" /></span>
																</c:otherwise>
															</c:choose></td>
														<!-- Tast table to assing part value table -->
														<td><h4>
															<input type="hidden" name="tyreNumbers" value="${RetreadAmount.TYRE_ID}"/>
																<c:out value="${RetreadAmount.TYRE_NUMBER}"> - </c:out>
																<c:out value="${TyRetreadAmountYRE_SIZE}"></c:out>
															</h4></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${RetreadAmount.RETREAD_COST}"></c:out></td>
														<td class="fit ar"><c:out
																value="${RetreadAmount.RETREAD_DISCOUNT} %"></c:out></td>
														<td class="fit ar"><c:out
																value="${RetreadAmount.RETREAD_TAX} %"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${RetreadAmount.RETREAD_AMOUNT}"></c:out></td>
														
														<sec:authorize access="hasAuthority('EDIT_TYRE_RETREAD')">	
														<c:if test="${Retread.TR_VENDOR_PAYMODE_STATUS_ID == 2}">	
															<td class="fit">
																<div class="btn-group">
																	<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
																		href="#"> <span class="fa fa-cog"></span> <span
																		class="caret"></span>
																	</a>
	
																	<ul class="dropdown-menu pull-right">
																		<li>
																			<a
																				onclick="javascript:edit_TyreRetreadInput('${RetreadAmount.TR_AMOUNT_ID}', '${Retread.TRID}',  '${RetreadAmount.TYRE_NUMBER}',  '${RetreadAmount.RETREAD_COST}',  '${RetreadAmount.RETREAD_DISCOUNT}',  '${RetreadAmount.RETREAD_TAX}',  '${RetreadAmount.RETREAD_AMOUNT}');"
																				id="editTyreSerialInput"> <span
																				class="fa fa-pencil"></span> Edit Tyre
																			</a>
																		</li>
	
																	</ul>
																</div>
															</td>	
															</c:if>
														</sec:authorize>	
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
							<div class="col-md-11">
								<div class="row">
									<div class="col-md-7">
										<div class="row1">
											<dl>
												<dd>Initial_Note :</dd>
												<dt>${Retread.TR_DESCRIPTION}</dt>
											</dl>
											<dl>
												<dd>Received_Note :</dd>
												<dt>${Retread.TR_RE_DESCRIPTION}</dt>
											</dl>
										</div>
									</div>
									<div class="col-md-4">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key"><h4>SubTotal :</h4></th>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${Retread.TR_AMOUNT}" />
														</h4></td>
												</tr>
												<tr class="row">
													<th class="key"><h4>Retread Total :</h4></th>
													<td class="value"><h4>
															<i class="fa fa-inr"></i><fmt:formatNumber type="number" pattern="#.##" value="${Retread.TR_ROUNT_AMOUNT}" />
														</h4></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</sec:authorize>
			</div>
		</div>
		
		<div class="modal fade" id="editTyreRetreadNumber" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<form action="EditRetreadReceived.in" method="post"
										name="vehicleType" onsubmit="return validateType()">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="VehicleType">Edit Tyre
												Retread Amount</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<label class="L-size control-label" id="Type">Retread
													Tyre No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" class="form-text" value=""
														required="required" name="TR_AMOUNT_ID" id="TR_AMOUNT_ID" />
													<!-- Tyre Retread Id -->
													<input type="hidden" class="form-text" value=""
														required="required" name="TRID" id="TRID" />
													<!-- Tyre Serial Num -->
													<input type="text" class="form-text" value=""
														id="TYRE_NUMBER" readonly="readonly" required="required"
														name="TYRE_NUMBER" maxlength="50"
														placeholder="Enter Tyre Serial Number" /> <label
														id="errorvType" class="error"></label>
												</div>
											</div>
											<div class="row">
												<div class="col-md-2">
													<div class="form-group">
														<label for="exampleInputEmail1">Retread Cost</label> <input
															type="text" class="form-text" data-toggle="tip"
															data-original-title="Tyre Retread Cost"
															name="RETREAD_COST" placeholder="Cost"
															required="required"
															onkeypress="return isLabertimeKeyQut(event);"
															id="laberhourscost"
															onkeyup="javascript:sumthere('laberhourscost', 'laberdiscount', 'labertax', 'totalLaborcost');"
															min="0.0">
													</div>
												</div>
												<div class="col-md-2">
													<div class="form-group">
														<label for="exampleInputEmail1">Retread Dis</label> <input
															type="text" name="RETREAD_DISCOUNT" class="form-text"
															placeholder="dis" required="required" data-toggle="tip"
															data-original-title="Tyre discount"
															onkeypress="return isLaberDisKeyQut(event);"
															id="laberdiscount"
															onkeyup="javascript:sumthere( 'laberhourscost', 'laberdiscount', 'labertax', 'totalLaborcost');">
													</div>
												</div>
												<div class="col-md-2">
													<div class="form-group">
														<label for="exampleInputEmail1">Retread GST</label> <input
															type="text" name="RETREAD_TAX" class="form-text"
															placeholder="GST" required="required" data-toggle="tip"
															data-original-title="Tyre GST"
															onkeypress="return isLaberTaxKeyQut(event);"
															id="labertax"
															onkeyup="javascript:sumthere('laberhourscost', 'laberdiscount', 'labertax', 'totalLaborcost');">
													</div>
												</div>
												<div class="col-md-2">
													<div class="form-group">
														<label for="exampleInputEmail1">Retread Total</label> <input
															type="text" name="totalcost" class="form-text"
															required="required" data-toggle="tip"
															data-original-title="Tyre Total Cost" id="totalLaborcost"
															readonly="readonly">

														<!-- Retread Total OLD AMOUNT Id -->
														<input type="hidden" class="form-text" value=""
															required="required" name="OLD_AMOUNT" id="OLD_AMOUNT" />
													</div>
												</div>
											</div>
											<br />
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary">
												<span id="Save"><spring:message
														code="label.master.Save" /></span>
											</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<span id="Close"><spring:message
														code="label.master.Close" /></span>
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>
		
		
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${Retread.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${Retread.CREATED_DATE}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${Retread.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${Retread.LASTUPDATED_DATE}" /></small>
		</div>
	</section>
	<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/TyreRetreadValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/reOpenMultipleRetreadTyre.js" />"></script>
</div>