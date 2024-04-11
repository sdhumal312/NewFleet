<%@ include file="taglib.jsp"%>
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
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="pull-left">
							<a href="<c:url value="/open"/>"><spring:message
									code="label.master.home" /></a> / <a
								href="<c:url value="/newServiceEntries/1/1.in"/>"> Service
								Entries</a> / <span>Service Entries
								${ServiceEntries.serviceEntries_Number}</span>
						</div>
						<div class="col-md-off-5">
							<div class="col-md-3">
								<form action="<c:url value="/searchServiceEntriesShow.in"/>"
									method="post">
									<div class="input-group">
										<span class="input-group-addon"> <span
											aria-hidden="true">SE-</span></span> <input class="form-text"
											id="vehicle_registrationNumber" name="Search" type="number"
											required="required" min="1" placeholder="ID eg: 1234">
										<span class="input-group-btn">
											<button type="submit" name="search" id="search-btn"
												class="btn btn-success btn-sm">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</form>
							</div>
							<a href="<c:url value="/newServiceEntries/1/1.in"/>">Cancel</a>
							
							<c:if test="${configuration.showAddInvoiceDeatils}">
								<c:if test="${ValidateInvoiceDetails}">
									<div class="pull-right">
										<button class="btn btn-success btn-sm" onclick="AddInvoiceDeatils();">
										Add Invoice Details
										</button>
									</div>	
								</c:if>
							</c:if>	
							
						</div>
						
					</div>
				</div>
			</div>
			<div class="box">
				<div class="box-body">

					<div class="row">
						<div class="col-md-7 col-sm-7 col-xs-7">
							<h4 align="center">
								<a
									href="<c:url value="/VehicleServiceEntriesDetails.in?vid=${ServiceEntries.vid}"/>"
									data-toggle="tip" data-original-title="Click Vehicle Info">
									<c:out value="${ServiceEntries.vehicle_registration}" />
								</a>
							</h4>
							<div class="secondary-header-title">
								<input type="hidden" id="invoiceDate" value="${ServiceEntries.invoiceDate}">
								<ul class="breadcrumb">
									<li><span class="fa fa-user"> Odometer :</span> <a
										data-toggle="tip" data-original-title="Odometer "><c:out
												value="${ServiceEntries.vehicle_Odometer}" /></a></li>
									<li><i class="fa fa-bitcoin"> Job Number :</i> <a
										data-toggle="tip" data-original-title="Job Number"><c:out
												value="${ServiceEntries.jobNumber}" /></a></li>
									<li><span class="fa fa-user"> Invoice Number :</span> <a
										data-toggle="tip" data-original-title="Invoice Number"><c:out
												value="${ServiceEntries.invoiceNumber}" /></a></li>
									<li><span class="fa fa-user"> Invoice Date :</span> <a
										data-toggle="tip" data-original-title="Invoice Date"><c:out
												value="${ServiceEntries.invoiceDate}" /></a></li>
												
									<li><span class="fa fa-user"> Driver Name :</span> <a
										data-toggle="tip" data-original-title="Driver Name"><c:out
												value="${ServiceEntries.driver_name}" /></a></li>
									<li><span class="fa fa-user"> Vendor Name :</span> <a
										data-toggle="tip" data-original-title="Vendor Name"><c:out
												value="${ServiceEntries.vendor_name}" /></a></li>
									<li><i class="fa fa-bitcoin"> Vendor Location :</i> <a
										data-toggle="tip" data-original-title="Location"><c:out
												value="${ServiceEntries.vendor_location}" /></a></li>
									<li><span class="fa fa-user"> Payment Mode :</span> <a
										data-toggle="tip" data-original-title="Pay Number"><c:out
												value="${ServiceEntries.service_paymentType} " /></a></li>			
									<li><span class="fa fa-user"> ${ServiceEntries.service_paymentType} Receipt No :</span> <a
										data-toggle="tip" data-original-title="Pay Number"><c:out
												value="${ServiceEntries.service_PayNumber}" /></a></li>
									<li><span class="fa fa-user"> Paid Date :</span> <a
										data-toggle="tip" data-original-title="Paid Date"><c:out
												value="${ServiceEntries.service_paiddate}" /></a></li>
									<li><span class="fa fa-user"> Paid By :</span> <a
										data-toggle="tip" data-original-title="Cashier"><c:out
												value="${ServiceEntries.service_paidby}" /></a></li>
									<c:if test="${config.TallyCompanyMasterInSE}">			
										<li><span class="fa fa-user"> Tally company :</span> <a
											data-toggle="tip" data-original-title="Tally"><c:out
													value="${ServiceEntries.tallyCompanyName}" /></a></li>
									</c:if>
									<c:if test="${config.showTripSheet}">			
										<li><span class="fa fa-user"> TripSheet Number :</span> <a
											data-toggle="tip" data-original-title="TripSheet"><c:out
													value="${ServiceEntries.tripSheetNumber}" /></a></li>
									</c:if>
									<c:if test="${ServiceEntries.gpsOdometer != null && ServiceEntries.gpsOdometer > 0}">
										<li><span class="fa fa-user"> GPS Odometer :</span> <a
											data-toggle="tip" data-original-title="Cashier"><c:out
													value="${ServiceEntries.gpsOdometer}" /></a></li>
									</c:if>
									<c:if test="${configuration.workshopInvoiceAmount}">
										<li><span class="fa fa-user"> WorkshopInvoice Amount :</span> <a
											data-toggle="tip" data-original-title="Cashier"><c:out
													value="${ServiceEntries.workshopInvoiceAmount}" /></a></li>
									</c:if>
									
								</ul>
							</div>
						</div>
						<div class="col-md-3 col-sm-3 col-xs-3">
							<div class="row">
								<input type="hidden" id="statues" name="statues"
									value="${ServiceEntries.serviceEntries_status}">
								<div id="work-order-statuses">
									<div id="work-order-statuses">
										<a data-disable-with="..." data-method="post"
											data-remote="true" rel="nofollow"> <span id="status-open"
											class="status-led"> <i class="fa fa-circle"></i> <span
												class="status-text">Open</span>
										</span>
										</a>
										<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
											<a data-method="post" data-remote="true"
												href="ServiceEntries_INPROCESS.in?serviceEntries_id=${ServiceEntries.serviceEntries_id}"
												rel="nofollow"> <span id="status-in-progress"
												class="status-led"> <i class="fa fa-circle"></i> <span
													class="status-text">In Progress</span>
											</span>
											</a>
										</sec:authorize>
										<sec:authorize
											access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
											<a style="width: 15%"
												href="PrintServiceEntries?id=${ServiceEntries.serviceEntries_id}"
												target="_blank" class="btn btn-default "><i
												class="fa fa-print"></i> Print</a>
										</sec:authorize>
										<sec:authorize
											access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
											<a style="width: 15%"
												href="download/serviceDocument/${ServiceEntries.service_document_id}.in"
												target="_blank" class="btn btn-default "><i
												class="fa fa-print"></i> Download</a>
										</sec:authorize>
									</div>
									
									<div id="status-close">
										<c:if test="${!ValidateInvoiceDetails}">
											<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
												<a class="btn btn-success" data-disable-with="..."
													href="ServiceEntries_COMPLETE.in?serviceEntries_id=${ServiceEntries.serviceEntries_id}">Complete</a>
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
						<div class="col-md-1 col-sm-1 col-xs-1">
							<div class="row">
								<div id="status-close">
									<button type="button" class="btn btn-default"
										data-toggle="modal" data-target="#addServiceDocument"
										data-whatever="@mdo">
										<i class="fa fa-upload"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<br>
					
					<%-- <c:if test="${configuration.showAddInvoiceDeatils}">
					<a id ="AddDetails" onload=""
						href="javascript:AddInvoiceDeatils();">
						Add Invoice Details
					<br>
					<br>
					</a>
					</c:if> --%>
					
					<input type="hidden" id="completeInvDeatils"	value="false">
					
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
														<div class="input-group input-append date" id="paidDate">
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
					
					<fieldset>
						<div class="modal fade" id="addServiceDocument" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="post" action="uploadServiceDocument"
										enctype="multipart/form-data">
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
											<button type="submit" class="btn btn-primary">
												<span>Upload</span>
											</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<span>Cancel</span>
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="row">
							<c:if test="${deleteFristParts}">
								<div class="alert alert-danger">
									<button class="close" data-dismiss="alert" type="button">x</button>
									Should be Delete First Task Parts and Technician

								</div>
							</c:if>
							<c:if test="${param.SUCCESSFULLY_ROUND eq true}">
								<div class="alert alert-success">
									<button class="close" data-dismiss="alert" type="button">x</button>
									This Service Entries Successfully Rounded the Total cost.

								</div>
							</c:if>
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
												<th class="fit ar">Labour Cost</th>
												<th class="fit ar">Total</th>
												<th class="fit">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty ServiceEntriesTask}">
												<c:forEach items="${ServiceEntriesTask}"
													var="ServiceEntriesTask">
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
														<td class="fit"><c:if
																test="${ServiceEntriesTask.mark_complete == 1}">
																<h4>
																	<i class="fa fa-check" style="color: green"></i>
																</h4>
															</c:if> <c:if test="${ServiceEntriesTask.mark_complete != 1}">
																<h4>
																	<i class="fa fa-wrench" style="color: red"></i>
																</h4>
															</c:if></td>
														<!-- Tast table to assing part value table -->
														<td><h4>
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
																					<th class="icon ar">GST</th>
																					<th class="icon ar">Total</th>
																					<th class="fit"></th>
																				</tr>
																			</thead>
																			<tbody>
																				<c:forEach items="${ServiceEntriesTaskPart}"
																					var="ServiceEntriesTaskPart">
																					<c:if
																						test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																						<tr data-object-id="" class="ng-scope">
																							<td class="fit"><c:out
																									value="${ServiceEntriesTaskPart.partname}" />
																								<c:out
																									value="${ServiceEntriesTaskPart.partnumber}" /></td>
																							<td class="fit ar">${ServiceEntriesTaskPart.quantity}</td>
																							<td class="fit ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskPart.parteachcost}</td>
																							<td class="fit ar">
																								${ServiceEntriesTaskPart.partdisc} %</td>
																							<td class="fit ar">
																								${ServiceEntriesTaskPart.parttax} %</td>
																							<td class="fit ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskPart.totalcost}</td>
																							<td class="fit">
																								<div class="btn-group">
																									<a class="btn-sm dropdown-toggle"
																										data-toggle="dropdown" href="#"> <span
																										class="fa fa-cog"></span> <span class="caret"></span>
																									</a>
																									<ul class="dropdown-menu pull-right">
																										<li><sec:authorize
																												access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																												<a
																													href="deleteServiceEntriesTaskToPart.in?serviceEntriesTaskto_partid=${ServiceEntriesTaskPart.serviceEntriesTaskto_partid}"
																													class="confirmation"
																													onclick="return confirm('Are you sure? Delete ')">
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
																						<div
																							id="changePart${ServiceEntriesTask.servicetaskid}"
																							style="display: none">
																							<form action="saveServiceEntriesTaskPart.in"
																								method="post" onsubmit="return checkForm(this);">
																								<div class="row">
																									<div class="col-md-3">
																										<input type="hidden" name="serviceEntries_id"
																											value="${ServiceEntries.serviceEntries_id}"
																											id="serviceEntries_id"> <input
																											type="hidden" name="Servicetaskid"
																											value="${ServiceEntriesTask.servicetaskid}"
																											id="ServiceEntriestaskid"> <input
																											type="hidden" name="partid"
																											required="required"
																											id="inventory_name${ServiceEntriesTask.servicetaskid}"
																											style="width: 100%;" required="required"
																											onchange="javascript:getlastdetailswithpart('${ServiceEntriesTask.service_typetaskId}','${ServiceEntriesTask.service_subtypetask_id}','${ServiceEntriesTask.vid}', 'last_occurred${ServiceEntriesTask.servicetaskid}','${ServiceEntriesTask.servicetaskid}');"
																											placeholder="Please Enter 2 or more Part Name" />

																									</div>
																									<div class="col-md-1">
																										<input type="text" class="form-text"
																											placeholder="Qty" name="quantity"
																											required="required" min="0" max="999"
																											id="quantity${ServiceEntriesTask.servicetaskid}"
																											data-toggle="tip"
																											data-original-title="enter Quantiry"
																											onkeypress="return isNumberKeyQut(event);"
																											onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}', 'partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}');"
																											min="0.0">
																									</div>
																									<div class="col-md-1">
																										<input type="text" name="parteachcost"
																											class="form-text" placeholder="e-cost"
																											required="required" min="0" max="9999"
																											id="parteachcost${ServiceEntriesTask.servicetaskid}"
																											data-toggle="tip"
																											data-original-title="enter each Cost"
																											onkeypress="return isNumberKeyEach(event);"
																											onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}');">
																									</div>

																									<div class="col-md-2">
																										<div class="input-group input-append date">

																											<input type="text" name="partdisc"
																												class="form-text" placeholder="Discount"
																												required="required" min="0" max="99"
																												id="partdis${ServiceEntriesTask.servicetaskid}"
																												data-toggle="tip"
																												data-original-title="enter Discount"
																												onkeypress="return isNumberKeyDis(event);"
																												onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}', 'partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}');">
																											<span class="input-group-addon add-on">
																												% </span>
																										</div>
																									</div>
																									<div class="col-md-2">
																										<div class="input-group input-append date">

																											<input type="text" name="parttax"
																												class="form-text" placeholder="GST"
																												required="required" min="0" max="99"
																												id="parttax${ServiceEntriesTask.servicetaskid}"
																												data-toggle="tip"
																												data-original-title="enter GST"
																												onkeypress="return isNumberKeyTax(event);"
																												onkeyup="javascript:sumthere('quantity${ServiceEntriesTask.servicetaskid}', 'parteachcost${ServiceEntriesTask.servicetaskid}','partdis${ServiceEntriesTask.servicetaskid}', 'parttax${ServiceEntriesTask.servicetaskid}', 'tatalcost${ServiceEntriesTask.servicetaskid}');">
																											<span class="input-group-addon add-on">
																												% </span>
																										</div>
																									</div>
																								</div>
																								<br>
																								<div class="row">
																									<div class="col-md-3 col-md-offset-5">
																										<div class="input-group input-append date">
																											<span class="input-group-addon add-on">
																												Total : </span> <input type="text" name="totalcost"
																												class="form-text" required="required"
																												id="tatalcost${ServiceEntriesTask.servicetaskid}"
																												data-toggle="tip"
																												data-original-title="Total cost"
																												readonly="readonly">
																										</div>
																									</div>
																								</div>
																								<div class="help-block"
																									id="last_occurred${ServiceEntriesTask.servicetaskid}">
																									<span class="loading ng-hide" id="loading">
																										<img alt="Loading" class="loading-img"
																										src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
																									</span>

																								</div>
																								<br>
																								<div class="row">
																									<div class="col-md-5 col-md-offset-2">

																										<input class="btn btn-success" type="submit"
																											value="Save Parts">
																									</div>
																								</div>
																							</form>
																						</div>
																					</div>
																				</td>
																			</tr>
																		</tbody>
																		<!-- labor cost add -->
																		<c:if test="${!empty ServiceEntriesTaskLabor}">
																			<thead>
																				<tr class="breadcrumb">
																					<th class="icon">Technician</th>
																					<th class="icon ar">Hours</th>
																					<th class="icon ar">Rate</th>
																					<th class="icon ar">Dis</th>
																					<th class="icon ar">GST</th>
																					<th class="icon ar">Total</th>
																					<th class="fit"></th>
																				</tr>
																			</thead>
																			<tbody>
																				<c:forEach items="${ServiceEntriesTaskLabor}"
																					var="ServiceEntriesTaskLabor">
																					<c:if
																						test="${ServiceEntriesTaskLabor.servicetaskid == ServiceEntriesTask.servicetaskid}">
																						<tr data-object-id="" class="ng-scope">
																							<td class="icon"><c:out
																									value="${ServiceEntriesTaskLabor.labername}" /></td>
																							<td class="icon ar">${ServiceEntriesTaskLabor.laberhourscost}</td>
																							<td class="icon ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskLabor.eachlabercost}</td>
																							<td class="icon ar">
																								${ServiceEntriesTaskLabor.laberdiscount} %</td>
																							<td class="icon ar">
																								${ServiceEntriesTaskLabor.labertax} %</td>
																							<td class="icon ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskLabor.totalcost}</td>
																							<td class="fit">
																								<div class="btn-group">
																									<a class="btn-sm dropdown-toggle"
																										data-toggle="dropdown" href="#"> <span
																										class="fa fa-cog"></span> <span class="caret"></span>
																									</a>

																									<ul class="dropdown-menu pull-right">
																										<li><sec:authorize
																												access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																												<a
																													href="deleteServiceEntriesTaskToLabor.in?serviceEntriesto_laberid=${ServiceEntriesTaskLabor.serviceEntriesto_laberid}"
																													class="confirmation"
																													onclick="return confirm('Are you sure? Delete ')">
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
																						<div
																							id="changeLabor${ServiceEntriesTask.servicetaskid}"
																							style="display: none">
																							<form action="saveServiceEntriesTaskLabor"
																								method="post">
																								<div class="row">
																									<div class="col-md-2">
																										<input type="hidden" name="serviceEntries_id"
																											value="${ServiceEntries.serviceEntries_id}"
																											id="serviceEntries_id"> <input
																											type="hidden" name="Servicetaskid"
																											value="${ServiceEntriesTask.servicetaskid}"
																											id="ServiceEntriestaskid"> <input
																											type="text" class="form-text"
																											name="labername" data-toggle="tip"
																											required="required" maxlength="150"
																											data-original-title="enter Techinician Name"
																											placeholder=" Enter Techinician Name">
																									</div>
																									<div class="col-md-1">
																										<input type="text" class="form-text"
																											name="laberhourscost" placeholder="time"
																											required="required" data-toggle="tip"
																											data-original-title="enter time"
																											onkeypress="return isLabertimeKeyQut(event);"
																											id="laberhourscost${ServiceEntriesTask.servicetaskid}"
																											onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}');"
																											min="0.0">
																									</div>
																									<c:if test="${config.allowDecimalValue}">
																									<div class="col-md-1">
																										<input type="text" name="eachlabercost"
																											class="form-text" placeholder="cost"
																											required="required" data-toggle="tip"
																											data-original-title="enter cost"
																											onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																											id="eachlabercost${ServiceEntriesTask.servicetaskid}"
																											onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}');">
																									</div>
																									</c:if>
																									<c:if test="${!config.allowDecimalValue}">
																									<div class="col-md-1">
																										<input type="text" name="eachlabercost"
																											class="form-text" placeholder="cost"
																											required="required" data-toggle="tip"
																											data-original-title="enter cost"
																											onkeypress="return isLaberCostKeyQut(event);"
																											id="eachlabercost${ServiceEntriesTask.servicetaskid}"
																											onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}');">
																									</div>
																									</c:if>
																									
																									<div class="col-md-1">
																										<input type="text" name="laberdiscount"
																											class="form-text" placeholder="dis"
																											required="required" value="0"
																											data-toggle="tip"
																											data-original-title="enter discount"
																											onkeypress="return isLaberDisKeyQut(event);"
																											id="laberdiscount${ServiceEntriesTask.servicetaskid}"
																											onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}' );">
																									</div>
																									<div class="col-md-1">
																										<input type="text" name="labertax"
																											class="form-text" placeholder="GST"
																											required="required" value="0"
																											data-toggle="tip"
																											data-original-title="enter GST"
																											onkeypress="return isLaberTaxKeyQut(event);"
																											id="labertax${ServiceEntriesTask.servicetaskid}"
																											onkeyup="javascript:sumthereLaber('laberhourscost${ServiceEntriesTask.servicetaskid}', 'eachlabercost${ServiceEntriesTask.servicetaskid}', 'laberdiscount${ServiceEntriesTask.servicetaskid}', 'labertax${ServiceEntriesTask.servicetaskid}', 'totalLaborcost${ServiceEntriesTask.servicetaskid}');">
																									</div>
																									<div class="col-md-2">
																										<input type="text" name="totalcost"
																											class="form-text" required="required"
																											data-toggle="tip"
																											data-original-title="Total cost"
																											id="totalLaborcost${ServiceEntriesTask.servicetaskid}"
																											readonly="readonly">
																									</div>
																									<div class="fit"></div>
																								</div>
																								<br>
																								<div class="row">
																									<div class="col-md-5 col-md-offset-2">

																										<input class="btn btn-success" type="submit"
																											value="Save Labour">
																									</div>
																								</div>
																							</form>
																						</div>
																					</div>
																				</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</div></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${ServiceEntriesTask.totalpart_cost}"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${ServiceEntriesTask.totallaber_cost}"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${ServiceEntriesTask.totaltask_cost}"></c:out></td>
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
																				href="deleteServiceEntriesTask.in?Servicetaskid=${ServiceEntriesTask.servicetaskid}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
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
															<form id="formServiceEntriesOpenTask"
																action="<c:url value="/ServiceEntriesOpenTask.in"/>"
																method="post" enctype="multipart/form-data"
																name="formServiceEntriesOpenTask" role="form"
																class="form-horizontal">
																<div class="col-md-6">
																	<input type="hidden" name="serviceEntries_id"
																		value="${ServiceEntries.serviceEntries_id}"
																		id="serviceEntries_id"> <input type="hidden"
																		name="vid" value="${ServiceEntries.vid}">

																	<div class="row" id="grpjobType" class="form-group">
																		<label class="L-size control-label" for="from">Job
																			Type :<abbr title="required">*</abbr>
																		</label>
																		<div class="I-size">
																			<input type="hidden" id="from"
																				name="service_typetaskId" style="width: 100%;"
																				required="required"
																				placeholder="Please Enter 3 or more Job Type Name" />
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
																			<input type="hidden" id="to"
																				name="service_subtypetask_id" style="width: 100%;"
																				required="required"
																				placeholder="Please Enter 3 or more Job Sub Type Name" />
																			<span id="jobSubTypeIcon" class=""></span>
																			<div id="jobSubTypeErrorMsg" class="text-danger"></div>
																		</div>
																	</div>
																	<c:if test="${configuration.showJobTypeRemarkCol}">
																		<div class="row1 form-group jobTypeRemarkCol col-md-12">
																			<label class="L-size control-label" for="to">Remark:</label>
																			<div class="I-size">
																				<div class="col-md-12">
																					<input type="text" class="form-text" id="taskRemark"
																						name="taskRemark" maxlength="250"
																						placeholder="Enter Remark" />
																				</div>
																			</div>
																		</div>																	
																	</c:if>
																</div>
																<br>
																<div class="row">
																	<div class="col-md-5 col-md-offset-2">
																		<button type="submit" class="btn btn-success">Create
																			New Task</button>
																	</div>
																</div>
															</form>
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
														<i class="fa fa-inr"></i>
														${ServiceEntries.totalservice_cost}
													</h4></td>
												<td class="fit"></td>
											</tr>
											<c:if test="${configuration.workshopInvoiceAmount}">
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><h4>Workshop Amount</h4></td>
												<td class="fit ar">:</td>
												<td class="fit ar"><h4>
														<i class="fa fa-inr"></i>
														${ServiceEntries.workshopInvoiceAmount}
													</h4></td>
												<td class="fit"></td>
											</tr>
											</c:if>
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><h4>Total Round of Cost</h4></td>
												<td class="fit ar">:</td>
												<td class="fit ar"><h4>
														<a id="tax_cost" data-remote="true"
															href="javascript:toggle2Tax('work_order-tax_cost','tax_cost');">
															<i class="fa fa-inr"></i>
														</a> ${ServiceEntries.totalserviceROUND_cost}
													</h4>
													<div class="popup-edit hide-on-escape"
														id="work_order-tax_cost">
														<form accept-charset="UTF-8"
															action="ServiceEntriesRount_cost" method="post"
															novalidate="novalidate">
															<div class="row">
																<label class="control-label">Total Round Cost</label>
																<div class="input float optional work_order_tax_cost">
																	<input type="hidden" name="SId"
																		value="${ServiceEntries.serviceEntries_id}"> <input
																		autofocus="autofocus" class="form-text"
																		required="required" name="TOTALROUNT_COST" step="any"
																		type="number"
																		value="${ServiceEntries.totalserviceROUND_cost}">
																</div>
															</div>
															<div class="row">
																<input class="btn btn-success" name="commit"
																	type="submit" value="Apply">
															</div>
														</form>
													</div></td>
												<td class="fit"></td>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- <table class="table">
								<tfoot>
									<tr class="breadcrumb">
										<th colspan="6"><i class="fa fa-plus"></i></th>
									</tr>
								</tfoot>
							</table> -->
							</div>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${ServiceEntries.createdBy}" /></small> | <small
					class="text-muted"><b>Created date: </b> <c:out
						value="${ServiceEntries.created}" /></small> | <small class="text-muted"><b>Last
						updated by :</b> <c:out value="${ServiceEntries.lastModifiedBy}" /></small> |
				<small class="text-muted"><b>Last updated date:</b> <c:out
						value="${ServiceEntries.lastupdated}" /></small>
			</div>
		</sec:authorize>
	</section>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ServiceEntriesValidate.js" />"></script>
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

<!-- get the Inventory Quantity and Unit price -->
<c:url var="findInventoryURL" value="getInventoryQuantityList.in" />

<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	