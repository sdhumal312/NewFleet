<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">	
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
		  
		  <section class="content-header">
			<div class="box">
				<div class="box-body">
						<input type="hidden" id="addPartToken" value="${addPartToken}">
						<input type="hidden" id="addLabourToken" value="${addLabourToken}">
						<input type="hidden" id="showVehicleHealthStatus" value="${config.showVehicleHealthStatus}">
						<input type="hidden" id="showVehicleHealthStatus" value="${config.showVehicleHealthStatus}">
						<input type="hidden" id="completeSEToken" value="${completeSEToken}">
						<input type="hidden" id="addSECompletionRemark" value="${config.addSECOmpletionRemark}">
						<input type="hidden" id="allowGSTbifurcation" value="${config.allowGSTbifurcation}">
						
						<div class="pull-left">
							<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
							<a href="<c:url value="/viewServiceEntries.in"/>"> Service Entries</a> /
							<span>Service Entries </span> <a id="serviceNumber" > </a>
						</div>
							
						<div class="pull-right">
						
							<c:if test="${configuration.showAddInvoiceDeatils}">
								<c:if test="${ValidateInvoiceDetails}">
									<button class="btn btn-danger btn-sm" onclick="AddInvoiceDeatils();">
										Add Invoice Details
									</button>
								</c:if>
							</c:if>
							
							<sec:authorize access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
								<a 
									href="PrintServiceEntries?id=${ServiceEntries.serviceEntries_id}"
									target="_blank" class="btn btn-warning btn-sm "><em
									class="fa fa-print"></em> Print</a>
							</sec:authorize>
						
							<sec:authorize	access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
								<a 
									href="download/serviceDocument/${ServiceEntries.service_document_id}.in"
									target="_blank" class="btn btn-info btn-sm "><em
									class="fa fa-print"></em> Download</a>
							</sec:authorize>
							
								<button type="button" class="btn btn-default btn-sm"
									data-toggle="modal" data-target="#addServiceDocument"
									data-whatever="@mdo">
									<em class="fa fa-upload"></em>
								</button>
							
							<a href="<c:url value="/viewServiceEntries.in"/>">Cancel</a>
							
						</div>
							
						<div class="col-md-2">
							<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">SE-</span></span>
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="ID eg: 2323"> <span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachServiceEntryByNumber();" class="btn btn-success btn-sm">
									<em class="fa fa-search"></em>
								</button>
							</span>
							</div>
						</div>
						
				</div>
			</div>
		</section>
			
			<div class="box">
				<div class="box-body">
					<div class="row">
					
						<div class="col-md-7 col-sm-7 col-xs-7">
							<h4 style="text-align: center;">
								<a id="vehicleNumber"></a>
							</h4>
							
							<div class="secondary-header-title">
								<input type="hidden" id="invoiceDate">
								<input type="hidden" id="serviceId" value="${serviceEntryId}">
								
								<ul class="breadcrumb">
									<li><span  class="fa fa-user"> Odometer :</span> <a
										id="odometer" data-toggle="tip" data-original-title="Odometer "></a></li>
									<li><em class="fa fa-bitcoin"> Job Number :</em> <a
										id="jobNumber" data-toggle="tip" data-original-title="Job Number"></a></li>
									<li><span class="fa fa-user"> Invoice Number :</span> <a
										id="invoiceNumbers" data-toggle="tip" data-original-title="Invoice Number"></a></li>
									<li><span class="fa fa-user"> Invoice Date :</span> <a
										id="invoiceDateTime" data-toggle="tip" data-original-title="Invoice Date"></a></li>
									<li><span class="fa fa-user"> Driver Name :</span> <a
										id="driverName" data-toggle="tip" data-original-title="Driver Name"></a></li>
									<li><span class="fa fa-user"> Vendor Name :</span> <a
										id="vendorName" data-toggle="tip" data-original-title="Vendor Name"></a></li>
									<li><em class="fa fa-bitcoin"> Vendor Location :</em> <a
										id="vendorLocation" data-toggle="tip" data-original-title="Location"></a></li>
									<li><span class="fa fa-user"> Payment Mode :</span> <a
										id="paymentMode" data-toggle="tip" data-original-title="Pay Number"></a></li>			
									<li><span class="fa fa-user"> Receipt No :</span> <a
										id="receiptNo" data-toggle="tip" data-original-title="Pay Number"></a></li>
									<c:if test="${config.showPaidDateForServEnt}">	
										<li><span class="fa fa-user"> Paid Date :</span> <a
											id="paidDate" data-toggle="tip" data-original-title="Paid Date"></a></li>
									</c:if>	
									<li><span class="fa fa-user"> Paid By :</span> <a
										id="paidBy" data-toggle="tip" data-original-title="Cashier"></a></li>
									<c:if test="${config.TallyCompanyMasterInSE}">			
										<li><span class="fa fa-user"> Tally company :</span> <a
											id="tallyCompany" data-toggle="tip" data-original-title="Tally"></a></li>
										<li><span class="fa fa-user"> Tally Expense Name :</span> <a
											id="tallyExpenseName" data-toggle="tip" data-original-title="Tally"></a></li>	
									</c:if>
									<c:if test="${config.showTripSheet}">			
										<li><span class="fa fa-user"> TripSheet Number :</span> <a
											id="tripsheetNumber" data-toggle="tip" data-original-title="TripSheet"></a></li>
									</c:if>
									<c:if test="${ServiceEntries.gpsOdometer != null && ServiceEntries.gpsOdometer > 0}">
										<li><span class="fa fa-user"> GPS Odometer :</span> <a
											id="gpsOdometer" data-toggle="tip" data-original-title="Cashier"></a></li>
									</c:if>
									<c:if test="${configuration.workshopInvoiceAmount}">
										<li><span class="fa fa-user"> WorkshopInvoice Amount :</span> <a
											id="workshopAmount" data-toggle="tip" data-original-title="Cashier"></a></li>
									</c:if>
								</ul>
							</div>
						</div>
						
						<div class="col-md-3 col-sm-3 col-xs-3">
							<div class="row">
								<input type="hidden" id="statues" name="statues" value="${ServiceEntries.serviceEntries_status}">
								<input type="hidden" id="statuesId" name="statuesId" value="${ServiceEntries.serviceEntries_statusId}">
								
								<div id="work-order-statuses">
									<div id="work-order-statuses">
										
										<a data-disable-with="..." data-method="post" data-remote="true" rel="nofollow">
											 <span id="status-open" class="status-led"> <em class="fa fa-circle fa-2x"></em> 
											  	<span class="status-text">Open </span>
											 </span>
										</a>
										
										<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
											<a data-method="post" data-remote="true" rel="nofollow" onclick="return changeStatusToInProgress(${ServiceEntries.serviceEntries_id});">
												<span id="status-in-progress" class="status-led">  <em class="fa fa-circle fa-2x"></em>
												  <span	class="status-text">In Progress </span>
												</span>
											</a>
										</sec:authorize>
										
									</div>
								</div>
									
								<div id="status-close" class="pull-right">
									<c:if test="${!ValidateInvoiceDetails}">
										<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
											<a class="btn btn-success" data-disable-with="..." 
											onclick="return completeServiceEntry(${ServiceEntries.serviceEntries_id});">Complete </a>
										</sec:authorize>
									</c:if>
									
									<c:if test="${ValidateInvoiceDetails}">
										<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
											<button class="btn btn-success" onclick="valInvDet()" >
												Complete
											</button>
										</sec:authorize>
									</c:if>
										
									
								</div>
								
							</div>
						</div>
						
					</div>
					<br>
					<div>
						<span class="bold" Id="healthStatusId" ></span>
						<span  Id="healthStatusName"></span>
					</div>
					<br>
					<div class="col-md-11">
						<div class="table-responsive">
							
							<table class="table">
								<thead>
									<tr class="breadcrumb">
									<c:if test="${configuration.showServRemindWhileCreatingWO}">
										<th>Service Number</th>
									</c:if>
										<th class="fit"></th>
										<th>Task</th>
										<th class="fit ar">Parts Cost</th>
									<c:if test="${!configuration.removeFeildFromLaborTab}">
										<th class="fit ar">Labour Cost</th>
									</c:if>	
									<th class="fit ar">Total</th>
										<th class="fit">Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty ServiceEntriesTask}">
										<c:forEach items="${ServiceEntriesTask}" var="ServiceEntriesTask">
											<tr data-object-id="" class="ng-scope">
											
												<c:if test="${configuration.showServRemindWhileCreatingWO}">		
													<td>
														<c:if test="${ServiceEntriesTask.service_Number == null}">
															<c:out value="-"> </c:out>
														</c:if>
														<c:if test="${ServiceEntriesTask.service_Number != null}">
															<a href="ShowService.in?service_id=${ServiceEntriesTask.service_id}" target="_blank"> <span style="font-size: 12px;"> S-${ServiceEntriesTask.service_Number} </span> </a>
														</c:if>	
													</td>
												</c:if>
													
												<td class="fit">
													<c:if test="${ServiceEntriesTask.mark_complete == 1}">
														<h4>
															<em class="fa fa-check" style="color: green"></em>
														</h4>
													</c:if> 
													<c:if test="${ServiceEntriesTask.mark_complete != 1}">
														<h4>
															<em class="fa fa-wrench" style="color: red"></em>
														</h4>
													</c:if>
												</td>
												
												<td>
													<h4>
														<c:out value="${ServiceEntriesTask.service_typetask} - " />
														<c:out value="${ServiceEntriesTask.service_subtypetask}" />
													</h4>
													<c:if test="${configuration.showJobTypeRemarkCol}">															
														<h5>
															<b>Remark : </b><span id="workordertaskremark_${ServiceEntriesTask.servicetaskid}"><c:out value="${ServiceEntriesTask.taskRemark}"></c:out></span>
														</h5>
													</c:if>
													<div class="row">
														<a id="addParts${ServiceEntriesTask.servicetaskid}"
															onclick="javascript:getInventoryList('inventory_name${ServiceEntriesTask.servicetaskid}');"
															href="javascript:toggle2('changePart${ServiceEntriesTask.servicetaskid}','addParts${ServiceEntriesTask.servicetaskid}');">
															Add Parts </a> | <a
															id="addLabor${ServiceEntriesTask.servicetaskid}"
															onclick="javascript:getROT_COST_Hour('${ServiceEntriesTask.service_subtypetask_id}', 'laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}' );"
															href="javascript:toggle2Labor('changeLabor${ServiceEntriesTask.servicetaskid}','addLabor${ServiceEntriesTask.servicetaskid}');">
															Add Labour</a>
													</div>
													
													<div class="row">
														<div class="col-md-11">
															<table class="table">
																
																<c:if test="${!empty ServiceEntriesTaskPart}">
																	<thead>
																		<tr class="breadcrumb">
																			<th class="icon">Part</th>
																			<th class="icon ar">Qty</th>
																			<th class="icon ar">Each</th>
																			<th class="icon ar">Dis</th>
																			<c:choose>
																				<c:when test="${config.allowGSTbifurcation}">
																					<th class="icon ar">IGST</th>
																					<th class="icon ar">CGST</th>
																					<th class="icon ar">SGST</th>
																					<th class="icon ar">Tax In amount</th>
																				</c:when>
																				<c:otherwise>
																					<th class="icon ar">GST</th>
																				</c:otherwise>
																			</c:choose>
																			<th class="icon ar">Total</th>
																			<th class="fit">Action</th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${ServiceEntriesTaskPart}" var="ServiceEntriesTaskPart">
																			<c:if test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																				<tr data-object-id="" class="ng-scope">
																					<td class="fit">
																						<c:out value="${ServiceEntriesTaskPart.partname}" />
																						<c:out value="${ServiceEntriesTaskPart.partnumber}" />
																					</td>
																					<td class="fit ar">
																						${ServiceEntriesTaskPart.quantity}
																					</td>
																					<td class="fit ar"><em class="fa fa-inr"></em>
																						${ServiceEntriesTaskPart.parteachcost}
																					</td>
																					<td class="fit ar">
																						${ServiceEntriesTaskPart.partdisc} %
																					</td>
																					
																					<c:choose>
																						<c:when test="${config.allowGSTbifurcation}">
																							<td class="fit ar">${ServiceEntriesTaskPart.partIGST} %</td>
																					        <td class="fit ar">${ServiceEntriesTaskPart.partCGST} %</td>
																							<td class="fit ar">${ServiceEntriesTaskPart.partSGST} %</td>
																							<td class="fit ar"><span class="fa fa-inr"></span>${ServiceEntriesTaskPart.taxInAmount}</td>
																						</c:when>
																						<c:otherwise>
																							<td class="fit ar">
																								${ServiceEntriesTaskPart.parttax} %
																							</td>
																						</c:otherwise>
																					</c:choose>
																					
																					<td class="fit ar"><em class="fa fa-inr"></em>
																						${ServiceEntriesTaskPart.totalcost}
																					</td>
																					<td class="fit">
																						<div class="btn-group">
																							<a class="btn-sm dropdown-toggle"
																								data-toggle="dropdown" href="#"> <span
																								class="fa fa-cog"></span> <span class="caret"></span>
																							</a>
																							<ul class="dropdown-menu pull-right">
																								<li>
																									<sec:authorize	access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																										<a
																											class="confirmation"
																											onclick="return deletePartDetails(${ServiceEntriesTaskPart.serviceEntriesTaskto_partid})">
																											<span class="fa fa-trash"></span> Delete
																										</a>
																									</sec:authorize>
																								</li>
																							</ul>
																						</div>
																					</td>
																				</tr>
																			</c:if>
																		</c:forEach>
																	</tbody>
																</c:if>
																
																<tbody>
																	<tr data-object-id="" class="ng-scope">
																		<td colspan="6">
																			<div class="">
																				<div id="changePart${ServiceEntriesTask.servicetaskid}"
																					style="display: none">
																					
																						<div class="row">
																							<div class="col-md-3">
																								<input type="hidden" name="serviceEntries_id" value="${ServiceEntries.serviceEntries_id}" id="serviceEntries_id">
																								<input type="hidden" name="Servicetaskid" value="${ServiceEntriesTask.servicetaskid}" id="ServiceEntriestaskid">
																								<input type="hidden" name="partid"	required="required" id="inventory_name${ServiceEntriesTask.servicetaskid}"
																									style="width: 100%;" required="required"
																									onchange="javascript:getlastdetailswithpart('${ServiceEntriesTask.service_typetaskId}','${ServiceEntriesTask.service_subtypetask_id}','${ServiceEntriesTask.vid}', 'last_occurred${ServiceEntriesTask.servicetaskid}','${ServiceEntriesTask.servicetaskid}');"
																									placeholder="Please Enter 2 or more Part Name" />
																							</div>
																							<div class="col-md-1">
																								<input type="text" class="form-text" placeholder="Qty" name="quantity" data-toggle="tip"
																									required="required" min="0" max="999" id="quantity${ServiceEntriesTask.servicetaskid}"
																									data-original-title="enter Quantiry"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																									onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																									'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','false');"
																									min="0.0">
																							</div>
																							<div class="col-md-1">
																								<input type="text" name="parteachcost"	class="form-text" placeholder="e-cost" 	required="required" min="0" max="9999"
																									id="parteachcost${ServiceEntriesTask.servicetaskid}" data-toggle="tip" data-original-title="enter each Cost"
																									 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																									onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																									'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','false');">
																							</div>
																							<div class="col-md-1">
																								<div class="input-group input-append date">
																									<input type="text" name="partdisc"	class="form-text" placeholder="Discount" required="required" min="0" max="99"
																										id="partdis${ServiceEntriesTask.servicetaskid}" data-toggle="tip" data-original-title="enter Discount"
																									 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																										'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','false');">
																										<span class="input-group-addon add-on">
																										% </span>
																								</div>
																							</div>
																							
																							
																							
																							
																							<!--  new code -->
																							<c:choose>
																								<c:when test="${!config.allowGSTbifurcation}">
																									<div class="col-md-2">
																										<div class="input-group input-append date">
																											<input type="text" name="parttax" class="form-text" placeholder="GSTtest" required="required" min="0" max="99"
																												id="parttax${ServiceEntriesTask.servicetaskid}" data-toggle="tip" data-original-title="enter GST"
																												 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																												'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','false');">
																												<span class="input-group-addon add-on">
																												% </span>
																										</div>
																									</div>
																								</c:when>
																								
																								<c:otherwise>
																									<div class="col-md-1">
																										<div class="input-group input-append date">
																											<input type="text" name="parttax" class="form-text" placeholder="IGST" required="required" min="0" max="99"
																												id="partIGST${ServiceEntriesTask.servicetaskid}" data-toggle="tip" data-original-title="enter IGST"
																												 onkeypress="return isNumberKeyWithDecimal(event,this.id);" value=0
																												onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																												'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','false');">
																												<span class="input-group-addon add-on">
																												% </span>
																										</div>
																									</div>
																									
																									<div class="col-md-1">
																										<div class="input-group input-append date">
																											<input type="text" name="parttax" class="form-text" placeholder="CGST" required="required" min="0" max="99"
																												id="partCGST${ServiceEntriesTask.servicetaskid}" data-toggle="tip" data-original-title="enter CGST"
																												 onkeypress="return isNumberKeyWithDecimal(event,this.id);" value=0
																												onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																												'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','true');">
																												<span class="input-group-addon add-on">
																												% </span>
																										</div>
																									</div>
																									
																									<div class="col-md-1">
																										<div class="input-group input-append date">
																											<input type="text" name="parttax" class="form-text" placeholder="SGST" required="required" min="0" max="99"
																												id="partSGST${ServiceEntriesTask.servicetaskid}" data-toggle="tip" data-original-title="enter SGST"
																												 onkeypress="return isNumberKeyWithDecimal(event,this.id);" value=0
																												onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}',
																												'partIGST${ServiceEntriesTask.servicetaskid}', 'partCGST${ServiceEntriesTask.servicetaskid}', 'partSGST${ServiceEntriesTask.servicetaskid}','true');">
																												<span class="input-group-addon add-on">
																												% </span>
																										</div>
																									</div> 
																								</c:otherwise>
																							</c:choose>
																							
																						</div>
																						<br>
																						<div class="row">
																							<div class="col-md-3 col-md-offset-5">
																								<div class="input-group input-append date">
																									<span class="input-group-addon add-on"> Total : </span> 
																									<input type="text" name="totalcost" class="form-text" required="required"
																									id="tatalcost${ServiceEntriesTask.servicetaskid}" data-toggle="tip"
																									data-original-title="Total cost" readonly="readonly">
																								</div>
																							</div>
																						</div>
																						<div class="help-block" id="last_occurred${ServiceEntriesTask.servicetaskid}">
																							<span class="loading ng-hide" id="loading">
																								<img alt="Loading" class="loading-img" src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
																							</span>
																						</div>
																						<br>
																						<div class="row">
																							<div class="col-md-5 col-md-offset-2">
																								<input class="btn btn-success" type="submit" id="savePart" onclick="savePartDetails(${ServiceEntriesTask.servicetaskid});" value="Save Parts">
																							</div>
																						</div>
																					
																				</div>
																			</div>
																		</td>
																	</tr>
																</tbody>
																
																<c:if test="${!empty ServiceEntriesTaskLabor}">
																	<thead>
																		<tr class="breadcrumb">
																			<th class="icon">Technician</th>
																			<th class="icon ar">Hours</th>
																		<c:if test="${!configuration.removeFeildFromLaborTab}">
																			<th class="icon ar">Rate</th>
																			<th class="icon ar">Dis</th>
																			
																			<c:choose>
																				<c:when test="${config.allowGSTbifurcation}">
																					<th class="icon ar">IGST</th>
																					<th class="icon ar">CGST</th>
																					<th class="icon ar">SGST</th>
																					<th class="icon ar">Tax In amount</th>
																				</c:when>
																				<c:otherwise>
																					<th class="icon ar">GST</th>
																				</c:otherwise>
																			</c:choose>
																			
																		</c:if>	
																			<th class="icon ar">Total</th>
																			<th class="fit"></th>
																		</tr>
																	</thead>
																	<tbody>
																		<c:forEach items="${ServiceEntriesTaskLabor}" var="ServiceEntriesTaskLabor">
																			<c:if test="${ServiceEntriesTaskLabor.servicetaskid == ServiceEntriesTask.servicetaskid}">
																				<tr data-object-id="" class="ng-scope">
																					<td class="icon">
																						<c:out value="${ServiceEntriesTaskLabor.labername}" />
																					</td>
																					<td class="icon ar">
																					<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.laberhourscost}"/>
																						
																					</td>
																				<c:if test="${!configuration.removeFeildFromLaborTab}">
																					<td class="icon ar"><em class="fa fa-inr"></em>
																					<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.eachlabercost}"/>
																					</td>
																					<td class="icon ar">
																					<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.laberdiscount}"/> %
																					</td>
																					
																					<c:choose>
																						<c:when test="${config.allowGSTbifurcation}">
																							<td class="icon ar">
																							<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.labourIGST}"/> %
																							</td>
																							<td class="icon ar">
																							<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.labourCGST}"/> %
																							</td>
																							<td class="icon ar">
																							<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.labourSGST}"/> %
																							</td>
																							<td class="icon ar"><em class="fa fa-inr"></em>
																							<fmt:formatNumber pattern="#.##" value="${ (ServiceEntriesTaskLabor.labertax/100)* ServiceEntriesTaskLabor.totalcost}"/> 
																							</td>
																						</c:when>
																						<c:otherwise>
																							<td class="icon ar">
																							<fmt:formatNumber pattern="#.##" value="${ServiceEntriesTaskLabor.labertax}"/> %
																							</td>
																						</c:otherwise>
																					</c:choose>
																					
																				</c:if>
																					<td class="icon ar"><em class="fa fa-inr"></em>
																						<fmt:formatNumber pattern="#.##" value=" ${ServiceEntriesTaskLabor.totalcost}"/>
																					</td>
																					<td class="fit">
																						<div class="btn-group">
																							<a class="btn-sm dropdown-toggle"
																								data-toggle="dropdown" href="#"> <span
																								class="fa fa-cog"></span> <span class="caret"></span>
																							</a>
																							<ul class="dropdown-menu pull-right">
																								<li><sec:authorize access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																									<a
																										class="confirmation"
																										onclick="return deleteLabourDetails(${ServiceEntriesTaskLabor.serviceEntriesto_laberid})">
																										<span class="fa fa-trash"></span> Delete
																									</a>
																								</sec:authorize></li>
																							</ul>
																						</div>
																					</td>
																				</tr>
																			</c:if>
																		</c:forEach>
																	</tbody>
																</c:if>
																
																<tbody>
																	<tr data-object-id="" class="ng-scope">
																		<td colspan="6">
																			<div class="">
																				<div id="changeLabor${ServiceEntriesTask.servicetaskid}"
																					style="display: none">
																					
																						<div class="row">
																							<div class="col-md-2">
																								<input type="hidden" name="serviceEntries_id" value="${ServiceEntries.serviceEntries_id}" id="serviceEntries_id">
																								<input type="hidden" name="Servicetaskid" value="${ServiceEntriesTask.servicetaskid}" id="ServiceEntriestaskid">
																								<input type="text" class="form-text" name="labername" data-toggle="tip" required="required" maxlength="150"
																									id="labername${ServiceEntriesTask.servicetaskid}" data-original-title="enter Techinician Name" placeholder=" Enter Techinician Name">
																							</div>
																							
																							<div class="col-md-1">
																								<input type="text" class="form-text" name="laberhourscost" placeholder="time" required="required" data-toggle="tip"
																									data-original-title="enter time"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																									id="laberhourscost${ServiceEntriesTask.servicetaskid}"
																									onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																									'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','false');"
																									min="0.0">
																							</div>
																							
																					  <c:if test="${!configuration.removeFeildFromLaborTab}">			
																							<c:if test="${config.allowDecimalValue}"> <!-- srisai only-->
																								<div class="col-md-1">
																									<input type="text" name="eachlabercost" class="form-text" placeholder="cost" required="required" data-toggle="tip"
																										data-original-title="enter cost"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										id="eachlabercost${ServiceEntriesTask.servicetaskid}"
																										onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}');">
																								</div>
																							</c:if>
																							
																							<c:if test="${!config.allowDecimalValue}"> <!--  others-->
																								<div class="col-md-1">
																									<input type="text" name="eachlabercost" class="form-text" placeholder="cost" required="required" data-toggle="tip"
																										data-original-title="enter cost"   onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																										id="eachlabercost${ServiceEntriesTask.servicetaskid}"
																										onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																										'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','false');">
																								</div>
																							</c:if>
																							
																							<div class="col-md-1">
																								<input type="text" name="laberdiscount" class="form-text" placeholder="dis" required="required" value="0"
																									data-toggle="tip" data-original-title="enter discount"   onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																									id="laberdiscount${ServiceEntriesTask.servicetaskid}"
																									onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																									'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','false' );">
																							</div>
																							
																							
																							<!-- new code for labour -->									
																							
																							
																							<c:choose>
																								<c:when test="${!config.allowGSTbifurcation}">
																									<div class="col-md-1">
																										<input type="text" name="labertax" class="form-text" placeholder="GST" required="required" value="0"
																											data-toggle="tip" data-original-title="enter GST"   onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																											id="labertax${ServiceEntriesTask.servicetaskid}"
																											onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																											'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','false');">
																									</div>
																								 </c:when>
																							 
																							 
																								<c:otherwise>
																									<div class="col-md-1">
																										<div class="input-group input-append date">
																											<input type="text" name="labourIGST" class="form-text" placeholder="IGST" required="required" value="0"
																												data-toggle="tip" data-original-title="enter IGST"   onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												id="labourIGST${ServiceEntriesTask.servicetaskid}"
																												onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																												'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','false');">
																												<span class="input-group-addon add-on">% </span>
																										</div>
																									</div>
																									<div class="col-md-1">
																										<div class="input-group input-append date">
																											<input type="text" name="labourCGST" class="form-text" placeholder="CGST" required="required" value="0"
																												data-toggle="tip" data-original-title="enter CGST"   onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												id="labourCGST${ServiceEntriesTask.servicetaskid}"
																												onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																												'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','true');">
																												<span class="input-group-addon add-on">% </span>
																										</div>
																									</div>
																									<div class="col-md-1">
																										<div class="input-group input-append date">
																											<input type="text" name="labourSGST" class="form-text" placeholder="SGST" required="required" value="0"
																												data-toggle="tip" data-original-title="enter SGST"   onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												id="labourSGST${ServiceEntriesTask.servicetaskid}"
																												onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}',
																												'labourIGST${ServiceEntriesTask.servicetaskid}','labourCGST${ServiceEntriesTask.servicetaskid}','labourSGST${ServiceEntriesTask.servicetaskid}','true');">
																												<span class="input-group-addon add-on">% </span>
																										</div>
																									</div>
																									
																								</c:otherwise>
																							</c:choose>
																							
																							
																						</c:if>	
																						
																						
																						
																							<br>
																							<div class="col-md-2">
																								<input type="text" name="totalcost" class="form-text" required="required" data-toggle="tip" 
																									data-original-title="Total cost" id="totalLaborcost${ServiceEntriesTask.servicetaskid}" readonly="readonly">
																							</div>
																							<div class="fit">
																							</div>
																						</div>
																						<br>
																						<div class="row">
																							<div class="col-md-5 col-md-offset-2">
																								<input class="btn btn-success" type="submit" id="saveLabour" onclick="saveLabourDetails(${ServiceEntriesTask.servicetaskid});" value="Save Labour">
																							</div>
																						</div>
																				</div>
																			</div>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</td>
												<td class="fit ar"><em class="fa fa-inr"></em> 
													<c:out value="${ServiceEntriesTask.totalpart_cost}"></c:out>
												</td>
											<c:if test="${!configuration.removeFeildFromLaborTab}">
												<td class="fit ar"><em class="fa fa-inr"></em> 
													<c:out value="${ServiceEntriesTask.totallaber_cost}"></c:out>
												</td>
											</c:if>
												<td class="fit ar"><em class="fa fa-inr"></em> 
													<c:out value="${ServiceEntriesTask.totaltask_cost}"></c:out>
												</td>
												<td class="fit">
													<div class="btn-group">
														<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
															href="#"> <span class="fa fa-cog"></span> <span
															class="caret"></span>
														</a>
														<ul class="dropdown-menu pull-right">
															<li><sec:authorize
																	access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																	<a
																		class="confirmation"
																		onclick="return deleteTaskDetails(${ServiceEntriesTask.servicetaskid})">
																		<span class="fa fa-trash"></span> Delete
																	</a>
															</sec:authorize></li>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr class="breadcrumb">
										<th colspan="6"><sec:authorize
												access="hasAuthority('ADD_SERVICE_ENTRIES')">
												<a id="addwork"
													href="javascript:toggle2Task('changeServiceEntries','addwork');"
													onclick="javascript:getJob_subtypetask('job_subtypetask');">
													Add Task</a>
											</sec:authorize><label class="error" id="errorINEACH" style="display: none">
										</label><label class="error" id="errorLABOR" style="display: none">
										</label></th>
									</tr>
									<tr data-object-id="" class="ng-scope">
										<td colspan="6">
											<div class="">
												<div id="changeServiceEntries" style="display: none">
													
													<div class="col-md-6">
														<input type="hidden" name="serviceEntries_id" value="${ServiceEntries.serviceEntries_id}" id="serviceEntries_id">
														<input type="hidden" name="vid" id="vehicle" value="${ServiceEntries.vid}">
														<input type="hidden" id="vehicleNO" value="${ServiceEntries.vehicle_registration}">
														<input type="hidden" id="driver_id" value="${ServiceEntries.driver_id}">
														
														<div class="row" id="grpjobType" class="form-group">
															<label class="L-size control-label" for="from">Job
																Type :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input type="hidden" id="from" name="service_typetaskId" style="width: 100%;"
																	required="required" placeholder="Please Enter 3 or more Job Type Name" />
																<span id="jobTypeIcon" class=""></span>
																<div id="jobTypeErrorMsg" class="text-danger"></div>
															</div>
														</div>
														<br>
														<div class="row" id="grpjobSubType" class="form-group">
															<label class="L-size control-label" for="to">Service
																Sub Jobs :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input type="hidden" id="to" name="service_subtypetask_id" style="width: 100%;"
																	required="required" placeholder="Please Enter 3 or more Job Sub Type Name" />
																<span id="jobSubTypeIcon" class=""></span>
																<div id="jobSubTypeErrorMsg" class="text-danger"></div>
															</div>
														</div>
														<c:if test="${configuration.showJobTypeRemarkCol}">
															<div class="row1 form-group jobTypeRemarkCol col-md-12">
																<label class="L-size control-label" for="to">Remark:</label>
																<div class="I-size">
																	<div class="col-md-12">
																		<input type="text" class="form-text" id="taskRemark" name="taskRemark" maxlength="250"
																			placeholder="Enter Remark" />
																	</div>
																</div>
															</div>																	
														</c:if>
													</div>
													<br>
													<div class="row">
														<div class="col-md-5 col-md-offset-2">
															<button type="submit" onclick="saveTaskDetails();" class="btn btn-success">Create
																New Task</button>
														</div>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>	
					<div class="col-md-11">
						<div class="col-md-offset-8">
							<table class="table">
								<tbody>
									<tr data-object-id="" class="ng-scope">
										<td class="fit ar"><h4>Total</h4></td>
										<td class="fit ar">:</td>
										<td class="fit ar"><h4>
											<em class="fa fa-inr"></em>
											${ServiceEntries.totalservice_cost}</h4>
										</td>
										<td class="fit"></td>
									</tr>
									<c:if test="${configuration.workshopInvoiceAmount}">
										<tr data-object-id="" class="ng-scope">
											<td class="fit ar"><h4>Workshop Amount</h4></td>
											<td class="fit ar">:</td>
											<td class="fit ar"><h4>
													<em class="fa fa-inr"></em>
													${ServiceEntries.workshopInvoiceAmount}</h4>
											</td>
											<td class="fit"></td>
										</tr>
									</c:if>
									<tr data-object-id="" class="ng-scope">
										<td class="fit ar"><h4>Total Round of Cost</h4></td>
										<td class="fit ar">:</td>
										<td class="fit ar"><h4>
												<a id="tax_cost" data-remote="true"
													href="javascript:toggle2Tax('work_order-tax_cost','tax_cost');">
													<em class="fa fa-inr"></em>
												</a> ${ServiceEntries.totalserviceROUND_cost}
											</h4>
											<div class="popup-edit hide-on-escape"
												id="work_order-tax_cost">
												<div class="row">
													<label class="control-label">Total Round Cost</label>
													<div class="input float optional work_order_tax_cost">
														<input type="hidden" name="SId" value="${ServiceEntries.serviceEntries_id}">
														<input autofocus="autofocus" class="form-text" id="totalRoundCost"
															required="required" name="TOTALROUNT_COST" step="any"
															type="number" value="${ServiceEntries.totalserviceROUND_cost}">
													</div>
												</div>
												<div class="row">
													<input class="btn btn-success" name="commit" onclick="saveRoundOfDetails(${ServiceEntries.serviceEntries_id});"
														type="submit" value="Apply">
												</div>
											</div>
										</td>
										<td class="fit"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<input type="hidden" id="completeInvDeatils" value="false">
					
					<div class="modal fade" id="invoiceDetails" role="dialog">
							<div class="modal-dialog" style="width:650px;">
								<!-- Modal content-->
								<div class="modal-content">
									
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="JobType">Invoice
												Details</h4>
										</div>
										<div class="modal-body">
											<div class="box">
												<div class="box-body">
											 	
											 	<div class="row1" id="grpstartDate" class="form-group">
													<label class="L-size control-label" for="invoicestartDate">Invoice
														Date <abbr title="required">*</abbr> 
													</label>
													<div class="I-size">
														<div class="input-group input-append date" id="StartDate">
															<input type="text" class="form-text" name="invoiceDate"
																id="invoicestartDate" 
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
																class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
														<span id="startDateIcon" class=""></span>
														<div id="startDateErrorMsg" class="text-danger"></div>
													</div>
												  <br>
												  <br>
												</div>
												
												<div class="row1" id="grpinvoiceNO" class="form-group">
													<label class="L-size control-label" for="invoiceNumber">Invoice
														Number <abbr title="required">*</abbr> 
													</label>
													<div class="I-size">
														<input class="form-text" id="invoiceNumber"
															placeholder="eg: SDS34343"
															name="invoiceNumber" type="text" maxlength="25"> <span
															id="invoiceNOIcon" class=""></span>
														<div id="invoiceNOErrorMsg" class="text-danger"></div>
													</div>
												  <br>	
												  <br>	
												</div>
												
												<div class="row1" id="grppaidDate" class="form-group">
													<label class="L-size control-label" for="servicepaiddate">Paid
														Date :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group input-append date" id="StartDate1">
															<input type="text" class="form-text"
																name="service_paiddate" id="servicepaiddate" required="required"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
																class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span>
														</div>
														<span id="paidDateIcon" class=""></span>
														<div id="paidDateErrorMsg" class="text-danger"></div>
													</div>
												  <br>	
												  <br>	
												</div>
													
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary">
												<span id="Save" onclick="saveServEntInvDetails();">Update Invoice Details</span>
											</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<span id="Close"><spring:message
														code="label.master.Close" /></span>
											</button>
										</div>
								</div>
							</div>
						</div>
						
						<div class="modal fade" id="addServiceDocument" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="post" enctype="multipart/form-data" id="fileUploadForm">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">Service Document</h4>
										</div>
										<div class="modal-body">
											<input type="hidden" name="serviceEntries_id" id="seId"
												value="${ServiceEntries.serviceEntries_id}">
											<div class="row1">
												<div class="L-size">
													<label class="L-size control-label"> Browse: </label>
												</div>
												<div class="I-size">
													<input type="file"
														accept="image/png, image/jpeg, image/gif"
														name="input-file-preview" required="required" />
												</div>
											</div>
											<br />
										</div>
										<div class="modal-footer">
											<input type="button" value="Submit" id="btnSubmit" class="btn btn-success"/>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<span>Cancel</span>
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					
				</div>
			</div>
			
			<div class="modal fade" id="completionRemark" role="dialog">
				<div class="modal-dialog" style="width: 980px;">
					<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title">Service Entry Completion Remark</h4>
							</div>
							<div class="modal-body">
								<div class="row1">
									<label class="L-size control-label" for="issue_description">Remark
										</label>
									<div class="I-size">
									<script language="javascript" src="jquery.maxlength.js"></script>
		                                 <textarea class="text optional form-text"
														id="woRemark" name="woRemark"
														rows="3" maxlength="1000"></textarea>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" onclick="completeSEWithRemark(${ServiceEntries.serviceEntries_id});" id="btnSubmit" class="btn btn-primary">
									<span>Complete SE</span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal" onclick="$('#status-close').show();">
									<span>Cancel</span>
								</button>
							</div>
					</div>
				</div>
			</div>
			
			
				
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <a id ="created"></a> </small> | 
				<small class="text-muted"><b>Created date: </b> <a id ="createdDates"></a> </small> | 
				<small class="text-muted"><b>Last updated by :</b> <a id ="lastUpdatedBy"></a> </small> |
				<small class="text-muted"><b>Last updated date:</b> <a id ="lastUpdatedDates"></a> </small>
			</div>
			
		</sec:authorize>
	</section>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ShowServiceEntryDetails.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>	
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var a = document.getElementById("statues").value;
						$("#work-order-statuses");
						switch (a) {
						case "OPEN":
							document.getElementById("status-open").className = "status-led-open";
							break;
						case "INPROCESS":
							document.getElementById("status-in-progress").className = "status-led-in-progress";
							break;
						case "COMPLETE":
							document.getElementById("status-ReOpen").style.display = "block"
						}
					});
</script>

<!-- get the Inventory Drop Down down -->
<c:url var="findInventoryURL" value="getInventoryList.in" />

<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>

<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
