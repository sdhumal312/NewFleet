<%@ include file="taglib.jsp"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventory/1.in"/>">New Tyre Inventory</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadinventorytyredocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryTyreReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a href="<c:url value="/addTyreInventory.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Tyre Inventory</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
					<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>	
						<a href="<c:url value="/TyreExpenseDetails.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Tyre Expense</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
					<div class="btn-group">
						<a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"> Filter
						</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<sec:authorize access="hasAuthority('ADD_SOLD_FILTER')">
								<a href="<c:url value="/soldFilter.in"/>" class="btn btn-sm">
								<span class="fa fa-search">Sold Filter</span></a>
							</sec:authorize>
						</li>
						<li>
							<sec:authorize access="hasAuthority('ADD_TYRE_RETREAD')">
								<a href="<c:url value="/RetreadFilter"/>" class="btn btn-sm">
								<span class="fa fa-search">Retread Filter</span></a>
							</sec:authorize>
						</li>
						<li>
							<sec:authorize access="hasAuthority('ADD_TYRE_SCRAP')">
								<a href="<c:url value="/ScrapFilter"/>" class="btn btn-sm">
								<span class="fa fa-search">Scrap Filter</span></a>
							</sec:authorize>
						</li>
					</ul>
					</div>
					</sec:authorize>
					<div class="btn-group">
						<a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"> Transfer
						</a>
					<ul class="dropdown-menu pull-right">
						<li>
					<sec:authorize access="hasAuthority('TRANSFER_MULTI_TYRE')">
						<a class="btn btn-sm"
							href="<c:url value="/multiTransferTyreInventory.in"/>"> <span
							class="fa fa-exchange"></span>Transfered Multiple Tyre
						</a>
					</sec:authorize>
						</li>
						<li>
					<sec:authorize access="hasAuthority('RECEIVE_MULTI_TYRE')">
						<a class="btn btn-sm"
							href="<c:url value="/receiveMultipleTyreTransfered.in"/>"> <span
							class="fa fa-check-circle "></span> Receive Transfered Tyre
						</a>
					</sec:authorize>
					</li>
					</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importInventoryTyre.in"/>"
							enctype="multipart/form-data">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h3 class="panel-title">Import File</h3>
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<br>
										<div class="row1">
											<div class="L-size">
												<label> Import Only xlsx File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".xlsx" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Inventory Tyre Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="this.style.visibility = 'hidden'" name="commit"
											type="submit" value="Import Inventory Tyre files"
											class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											autocomplete="off" id="js-upload-submit" value="Add Document"
											data-toggle="modal">
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
									<script>
										$('#myButton')
												.on(
														'click',
														function() {
															//alert("hi da")
															$(".progress-bar")
																	.animate(
																			{
																				width : "100%"
																			},
																			2500);
															var $btn = $(this)
																	.button(
																			'loading')
															// business logic...

															$btn
																	.button('reset')
														})
									</script>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		<sec:authorize access="!hasAuthority('VIEW_TYRE_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
			<input type="hidden" id="valuesInserted" value="<%= request.getParameter("valuesInserted")%>">
			<input type="hidden" id="valuesRejected" value="<%= request.getParameter("valuesRejected")%>">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Tyre Invoice</span> <span
								class="info-box-number">${TyreCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Tyre Invoice</span>
							<form action="<c:url value="/searchInvoiceInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" name="Search" type="text"
										required="required" placeholder="Only Invoice No"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Tyre No</span>
							<form action="<c:url value="/searchTyreInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" name="Search" type="text"
										required="required" placeholder="Only Tyre No, Size">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<c:if test="${!empty availableCount}">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">All Available Tyre</span> 
								<a href="#" onclick="getTyreList(1,1,0);">
								<span id="allAvailableTyreCount" class="info-box-number" >${availableCount}</span>
								</a>
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${!empty serviceCount}">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Tyre In Service</span> 
							<a href="#" onclick="getTyreList(1,2,0);">
							<span id="allTyreServiceCount" class="info-box-number">${serviceCount}</span>
							</a>
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${!empty scrapedCount}">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Scraped Tyre</span> 
							<a href="#" onclick="getTyreList(1,3,0);">
							<span id="allScrapedTyreCount" class="info-box-number">${scrapedCount}</span>
							</a>
						</div>
					</div>
				</div>
				</c:if>
			</div>
			
			<c:if test="${param.saveInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Created Successfully.
				</div>
			</c:if>
			<c:if test="${param.updateInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Updated Successfully.
				</div>
			</c:if>
			<c:if test="${param.tyreSaved eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					 Tyre Inventory Saved Successfully.
				</div>
			</c:if>
			<c:if test="${param.deleteSuccess eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Invoice Deleted Successfully.
				</div>
			</c:if>

			<c:if test="${param.alreadyInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.deleteFrist eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Should be Delete First Tyre Serial Number and Amount

				</div>
			</c:if>

			<c:if test="${duplicateInventory}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists Part Number = ${alreadyInventory} .
				</div>
			</c:if>

			<c:if test="${param.sequenceNotFound eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Sequence Counter Not Defined Please Contact to Administrator!

				</div>
			</c:if>
			<div class="row">
				<div class="col-xs-11">
					<div class="main-tabs">
						<ul class="nav nav-pills">
							<li role="presentation" id="All" class="active"><a
								href="<c:url value="/TyreInventory/1"/>">TYRE INVOICE</a></li>
							<li role="presentation" id="All" class=""><a
								href="<c:url value="/TyreRetreadNew/1"/>">TYRE RETREAD
									INVOICE</a></li>
							<sec:authorize access="hasAuthority('ADD_SOLD_FILTER')">		
							<li role="presentation" id="sold" class=""><a
								href="<c:url value="/allTyreSoldInvoice"/>">TYRE SOLD INVOICE</a></li>	
							</sec:authorize>	

							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<li class="tab-link" id="${PartLocations.partlocation_name}"><a
										class="btn btn-link"
										href="<c:url value="/locationTyreInventory/1.in?loc=${PartLocations.partlocation_id}"/>">
											${PartLocations.partlocation_name}</a></li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
					<br>
					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable" class="table">
											<thead>
												<tr>
													<th class="fit ar">ID</th>
													<th class="fit ar">Vendor</th>
													<th class="fit ar">Location</th>
													<c:if test="${showSubLocation}">
													<th class="fit ar">Sub Location</th>
													</c:if>
													<th class="fit ar">Invoice</th>
													<th class="fit ar">Invoice Date</th>
													<th class="fit ar">Amount</th>
													<th class="fit ar">Created By</th>
													
											<c:if test="${configuration.viewTyreDocument}"><th class="fit ar">Doc</th>	</c:if>			
													
													<th class="fit ar">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty TyreInvoice}">
													<c:forEach items="${TyreInvoice}" var="TyreInvoice">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"/>"
																data-toggle="tip"
																data-original-title="Click Tyre Inventory INFO"><c:out
																		value="TI-${TyreInvoice.ITYRE_NUMBER}" /></a></td>
															<td class="fit ar"><a
																href="<c:url value="/showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${TyreInvoice.VENDOR_NAME}" /> </a></td>
															<td class="fit ar"><c:out
																	value="${TyreInvoice.WAREHOUSE_LOCATION}" /></td>
															<c:if test="${showSubLocation}">
															<td class="fit ar"><c:out
																	value="${TyreInvoice.subLocation}" /></td>
															</c:if>
															<td class="fit ar"><c:out
																	value="${TyreInvoice.INVOICE_NUMBER}" /></td>
															<td class="fit ar"><c:out
																	value="${TyreInvoice.INVOICE_DATE}" /></td>
															<c:if test="${!configuration.roundupAmount}">
																<td class="fit ar">
																<fmt:formatNumber value="${TyreInvoice.INVOICE_AMOUNT}" pattern="#.##" />
																		 </td>
															</c:if>
															<c:if test="${configuration.roundupAmount}">
																<td class="fit ar"><fmt:formatNumber type="number"
																		maxFractionDigits="0"
																		value='${TyreInvoice.INVOICE_AMOUNT}' /></td>
															</c:if>
															<td class="fit ar"><c:out
																	value="${TyreInvoice.firstName}" /></td>	
															<c:if test="${configuration.viewTyreDocument}">		
															<td>
															<c:choose>
																<c:when test="${TyreInvoice.tyre_document == true}">
																	<a
																		href="${pageContext.request.contextPath}/download/TyreInventoryDocument/${TyreInvoice.tyre_document_id}.in">
																		<span class="fa fa-download"> Doc</span>
																	</a>
																</c:when>
															</c:choose>
															</td>
															</c:if>
															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('VIEW_TYRE_INVENTORY')">
																				<a
																					href="<c:url value="/showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"/>">
																					<span class="fa fa-external-link"
																					aria-hidden="true"></span> Show Tyre Invoice
																				</a>
																			</sec:authorize></li>
																	
																		<c:choose>
																		<c:when test="${(TyreInvoice.PAYMENT_TYPE_ID != 2 || TyreInvoice.VENDOR_PAYMODE_STATUS_ID == 2) && TyreInvoice.VENDOR_PAYMODE_STATUS_ID != 0 }">
																			<li><sec:authorize
																					access="hasAuthority('EDIT_TYRE_INVENTORY')">
																					<a
																						href="<c:url value="/editTyreInventory?Id=${TyreInvoice.ITYRE_ID}"/>">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_TYRE_INVENTORY')">
																					<a
																						href="<c:url value="/deleteTyreInvoice.in?Id=${TyreInvoice.ITYRE_ID}"/>"
																						class="confirmation"
																						onclick="return confirm('Are you sure you Want to delete Invoice And Tyre?')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																			</c:when>
																			<c:otherwise>
																			<li><sec:authorize
																						access="hasAuthority('VIEW_TYRE_INVENTORY')">
																						<a href="#"> <span class="fa fa-money"></span> 
																						<c:out value="${TyreInvoice.VENDOR_PAYMODE_STATUS}  " />
																						</a>
																					</sec:authorize></li>
																			</c:otherwise>

																			
																		</c:choose>
																			
																	</ul>
																</div>
															</td>															
															
																													
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>

							</div>
							<c:url var="firstUrl" value="/TyreInventory/1" />
							<c:url var="lastUrl"
								value="/TyreInventory/${deploymentLog.totalPages}" />
							<c:url var="prevUrl" value="/TyreInventory/${currentIndex - 1}" />
							<c:url var="nextUrl" value="/TyreInventory/${currentIndex + 1}" />
							<div class="text-center">
								<ul class="pagination pagination-lg pager">
									<c:choose>
										<c:when test="${currentIndex == 1}">
											<li class="disabled"><a href="#">&lt;&lt;</a></li>
											<li class="disabled"><a href="#">&lt;</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${firstUrl}">&lt;&lt;</a></li>
											<li><a href="${prevUrl}">&lt;</a></li>
										</c:otherwise>
									</c:choose>
									<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
										<c:url var="pageUrl" value="/TyreInventory/${i}" />
										<c:choose>
											<c:when test="${i == currentIndex}">
												<li class="active"><a href="${pageUrl}"><c:out
															value="${i}" /></a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:choose>
										<c:when test="${currentIndex == deploymentLog.totalPages}">
											<li class="disabled"><a href="#">&gt;</a></li>
											<li class="disabled"><a href="#">&gt;&gt;</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${nextUrl}">&gt;</a></li>
											<li><a href="${lastUrl}">&gt;&gt;</a></li>
										</c:otherwise>
									</c:choose>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	
	<div class="content" >
<div class="modal fade" id="tyreModelList" role="dialog">
	<div class="modal-dialog" style="width:750px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="text-align: center;">
				<span id="headerData"></span>
			</div>
			<div class="modal-body">
				<table  style="width: 100%;" id="list1" border="2"></table>
			</div>
			<div class="text-center">
				<ul id="navigationBar6" class="pagination pagination-lg pager"> </ul>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>
</div>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/viewInventoryTyreList.js"/>"></script>	
	<script type="text/javascript">
		$(document).ready(function(){
			if($("#valuesInserted").val() != undefined && $("#valuesRejected").val() != undefined && $("#valuesInserted").val() >= 0 && $("#valuesRejected").val() >= 0) {
	   	 		showMessage('success', $("#valuesInserted").val()+ " Tyres has been inserted and " + $("#valuesRejected").val() + " Tyres has been rejected!");
	   	 	}
		});
		
	</script>

</div>