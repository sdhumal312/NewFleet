<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('IMPORT_VEHICLE')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadvendordocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_VENDOR_PAYMENT')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVendorPaymentSheet.in"/>"> <span
							class="fa fa-plus"></span> Add Vendor Payment
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_LORRY_HIRE')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/viewVendorLorryHireDetails.in"/>"> <span
							class="fa fa-list"></span> Lorry Hire Details
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('LORRY_HIRE_PAYMENT')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/makeLorryHirePayment.in"/>">Add Lorry Hire Payment
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_VENDOR_PAYMENT')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/viewVendorPaymentList.in"/>"> <span
							class="fa fa-list"></span> View Vendor Payment List
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
						<a class="btn btn-primary btn-sm"
							href="<c:url value="/findPendingVendorPayment.in"/>"> <span
							class="fa fa-search"></span> Pending Vendor Payment
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_VENDOR')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVendor.in"/>"> <span
							class="fa fa-plus"></span> Add Vendor
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_VENDOR')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/VendorReport"/>"> <span
							class="fa fa-search"></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importVendors.in"/>"
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
												style="width: 0%">Upload Your Vendors Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="this.style.visibility = 'hidden'" name="commit"
											type="submit" value="Import Vehicle load files"
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
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VENDOR')">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectType} Vendors</span> 
							<input
								type="hidden" value="${SelectType}" id="statues"><span
								class="info-box-number">${VendorCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-5 col-sm-5 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Vendor</span>
							<form action="<c:url value="/searchVendor.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="VendorName" type="text" required="required"
										placeholder="VEN-ID, Ven-Name, Ven-Phone"> <span
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
			</sec:authorize>
		</div>
		<c:if test="${param.saveVendor eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Created successfully .
			</div>
		</c:if>
		<c:if test="${param.updateVendor eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Updated successfully .
			</div>
		</c:if>
		<c:if test="${noVendorTypeFound}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				No Record Found!
			</div>
		</c:if>
		<c:if test="${param.deleteVendor eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Deleted successfully .
			</div>
		</c:if>
		<c:if test="${saveVendor}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Created successfully .
			</div>
		</c:if>
		<c:if test="${deleteVendor}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Already Updated.
			</div>
		</c:if>
		<c:if test="${param.errorVendor eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Not Work successfully .
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${updateVendor}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Updated successfully .
			</div>
		</c:if>
		<c:if test="${dangerVendor}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Not Deleted.
			</div>
		</c:if>
		<c:if test="${param.deletedanger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Not Deleted.
			</div>
		</c:if>
		<c:if test="${param.RemoveFuel eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Should Be Delete All this Vendor fuel entries First.
			</div>
		</c:if>
		<c:if test="${param.alreadyExist eq true}">
		<input type="hidden" id="exist" value="00000">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Already Exist.
			</div>
		</c:if>
		<c:if test="${param.successfullyUpdated eq true}">
		<input type="hidden" id="success" value="12345">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Update Successfully.
			</div>
		</c:if>
		<c:if test="${param.vendorType eq true}">
		<input type="hidden" id="success" value="12345">
			<div class="alert alert-info">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Credit Transactions Already Exists For The Current Vendor Type Hence Cannot Edit VendorType. 
			</div>
		</c:if>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VENDOR')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<c:if test="${!empty vendorType}">
										<c:forEach items="${vendorType}" var="vendorType">
											<li role="presentation" id="${vendorType.vendor_TypeName}"><a
												href="<c:url value="/vendor/${vendorType.vendor_Typeid}/1.in"/>">${vendorType.vendor_TypeName}</a></li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="VendorTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th>Vendor Name</th>
											<th class="fit ar">Vendor Type</th>
											<th class="fit ar">Location</th>
											<th class="fit ar">Contact Name</th>
											<th class="fit ar">Phone</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty vendor}">
											<c:forEach items="${vendor}" var="vendor">
												<tr data-object-id="" class="ng-scope">
													<td><a
														href="<c:url value="/${SelectTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
														data-toggle="tip"
														data-original-title="Click this Vendor Details"> <c:out
																value="${vendor.vendorName}" />
													</a></td>
													<td class="fit ar"><c:out value="${vendor.vendorType}" /></td>
													<td class="fir ar"><a
														href="http://maps.google.com/?q=${vendor.vendorLocation}"><c:out
																value="${vendor.vendorLocation}" /></a></td>
													<td class="fir ar"><i class="fa fa-male"></i> <c:out
															value="${vendor.vendorFirName}" />
														<ul class="list-inline no-margin">
															<li><i class="fa fa-male"></i> <c:out
																	value="${vendor.vendorSecName}" /></li>
														</ul></td>
													<td class="fit ar"><i class="fa fa-phone-square"></i>
														<c:out value="${vendor.vendorFirPhone}" />

														<ul class="list-inline no-margin">
															<li><i class="fa fa-phone-square"></i> <c:out
																	value="${vendor.vendorSecPhone}" /></li>
														</ul></td>
													<td>
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-ellipsis-v"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize access="hasAuthority('EDIT_VENDOR')">
																		<a
																			href="<c:url value="/${SelectTypeId}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">
																			<i class="fa fa-edit"></i> Edit
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize
																		access="hasAuthority('DELETE_VENDOR')">
																		<a
																			href="<c:url value="/${SelectTypeId}/deleteVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
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
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/vendor/${SelectTypeId}/1" />
					<c:url var="lastUrl" value="/vendor/${SelectTypeId}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/vendor/${SelectTypeId}/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/vendor/${SelectTypeId}/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/vendor/${SelectTypeId}/${i}" />
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
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			if(!${noVendorTypeFound}){
				var e = document.getElementById("statues").value;
				switch (e) {
				case "ALL":
					document.getElementById("All").className = "active";
					break;
				case e:
					document.getElementById(e).className = "active"
				}
			}
		});
	</script>
</div>