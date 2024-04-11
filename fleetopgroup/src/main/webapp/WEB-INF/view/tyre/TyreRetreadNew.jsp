<%@ include file="taglib.jsp"%>
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
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-list-alt"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Tyre Retread </span><span
								class="info-box-number">${TyreQuentity}</span>

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
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="main-tabs">
						<ul class="nav nav-pills">
							<li role="presentation" id="All" class=""><a
								href="<c:url value="/TyreInventory/1"/>" >TYRE INVOICE</a></li>
							<li role="presentation" id="TYRE" class="active"><a
								href="<c:url value="/TyreRetreadNew/1"/>">TYRE RETREAD INVOICE</a></li>
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
													<th class="fit ar">Open Date</th>
													<th class="fit ar">Required Date</th>
													<th class="fit ar">Vendor</th>
													<th class="fit ar">Terms</th>
													<th class="fit ar">Quote No</th>
													<th class="fit ar">Status</th>
													<th class="fit ar">Actions</th>

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty TyreRetread}">
													<c:forEach items="${TyreRetread}" var="TyreRetread">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/ShowRetreadTyre?RID=${TyreRetread.TRID}"/>"
																data-toggle="tip"
																data-original-title="Click Tyre Retread INFO"><c:out
																		value="TR-${TyreRetread.TRNUMBER}" /></a></td>

															<td class="fit ar"><c:out
																	value="${TyreRetread.TR_OPEN_DATE}" /></td>
															<td class="fit ar"><c:out
																	value="${TyreRetread.TR_REQUIRED_DATE}" /></td>

															<td class="fit ar"><a
																href="<c:url value="/ShowVendor.in?vendorId=${TyreRetread.TR_VENDOR_ID}"/>"
																data-toggle="tip"
																data-original-title="Click Vendor INFO"><c:out
																		value="${TyreRetread.TR_VENDOR_NAME}" /></a></td>
															<td class="fit ar"><c:out
																	value="${TyreRetread.TR_PAYMENT_TYPE}" /></td>

															<td class="fit ar"><c:out
																	value="${TyreRetread.TR_QUOTE_NO}" /></td>

															<td class="fit ar"><c:out
																	value="${TyreRetread.TR_STATUS}" /></td>

															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('VIEW_INVENTORY')">
																				<a
																					href="<c:url value="/ShowRetreadTyre?RID=${TyreRetread.TRID}"/>">
																					<span class="fa fa-pencil"></span> Show Retread
																				</a>
																			</sec:authorize></li>
																			<c:choose>
																				<c:when test="${TyreRetread.TR_VENDOR_PAYMODE_STATUS_ID == 2}">
																					<c:if test="${TyreRetread.TR_STATUS != 'COMPLETED'}">
																						<li><sec:authorize
																							access="hasAuthority('EDIT_TYRE_RETREAD')">
																							<a
																								href="<c:url value="/editRetreadTyre?Id=${TyreRetread.TRID}"/>">
																								<span class="fa fa-pencil"></span> Edit Retread
																							</a>
																						</sec:authorize></li>
																					</c:if>
																					<li><sec:authorize
																						access="hasAuthority('DELETE_INVENTORY')">
																						<a
																							href="<c:url value="/DeleteRetread?RID=${TyreRetread.TRID}"/>"
																							class="confirmation"
																							onclick="return confirm('Are you sure? Delete ')">
																							<span class="fa fa-trash"></span> Delete Retread
																						</a>
																					</sec:authorize></li>
																				</c:when>	
																				<c:when test="${TyreRetread.TR_VENDOR_PAYMODE_STATUS_ID == 4 || TyreRetread.TR_VENDOR_PAYMODE_STATUS_ID == 5}">
																					<li><sec:authorize
																					access="hasAuthority('VIEW_TYRE_INVENTORY')">
																					<a href="#"> <span class="fa fa-money"></span> 
																					<c:out value="${TyreRetread.TR_VENDOR_PAYMODE_STATUS} PAID " />
																					</a>
																					</sec:authorize></li>
																				</c:when>
																				<c:otherwise>
																					<li><sec:authorize
																					access="hasAuthority('VIEW_INVENTORY')">
																					<a href="#"> <span class="fa fa-money"></span> 
																						<c:out value="${TyreRetread.TR_VENDOR_PAYMODE_STATUS}" />
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
							<c:url var="firstUrl" value="/TyreRetreadNew/1" />
							<c:url var="lastUrl"
								value="/TyreRetreadNew/${deploymentLog.totalPages}" />
							<c:url var="prevUrl" value="/TyreRetreadNew/${currentIndex - 1}" />
							<c:url var="nextUrl" value="/TyreRetreadNew/${currentIndex + 1}" />
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
										<c:url var="pageUrl" value="/TyreRetreadNew/${i}" />
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
</div>