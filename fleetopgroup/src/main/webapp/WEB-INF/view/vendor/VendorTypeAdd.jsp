<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVendorType.in"/>"> New Vendor Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('SAVE_NEW_VENDOR_TYPE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addVendorType" data-whatever="@mdo">
							<span class="fa fa-plus"></span> New Vendor Type
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveVendorType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateVendorType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteVendorType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Type Deleted Successfully.
			</div>
		</c:if>

		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Type Error 404 Exists
			</div>
		</c:if>
		<c:if test="${param.alreadyVendorType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vendor Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addVendorType" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="saveVendorType.in" method="post"
						onsubmit="return validateStatus()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleVendor">New Vendor Types</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label">Vendor Type :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="vStatus"
										name="vendor_TypeName" 
										placeholder="Enter Vendor Type Name" /> <label
										id="errorvStatus" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save"><spring:message code="label.master.Save" /></span>
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
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
								<div class="table-responsive">
									<table id="StatusTable" class="table table-striped">
										<thead>
											<tr>
												<th class="icon">No</th>
												<th class="icon">Name</th>
												<th class="icon">Usage</th>
												<th class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vendorType}">
												<c:forEach items="${vendorType}" var="vendorType">

													<tr data-object-id="" class="ng-scope">
														<td class="icon"><c:out
																value="${vendorType.vendor_Typeid}" /></td>
														<td class="icon"><c:out
																value="${vendorType.vendor_TypeName}" /></td>
														<td class="icon"><a href="#.." rel="facebox">0
																vehicles</a></td>
														<c:choose>
															<c:when
																test="${vendorType.vendor_TypeName == 'FUEL-VENDOR'}">

															</c:when>
															<c:when
																test="${vendorType.vendor_TypeName == 'PART-VENDOR'}">

															</c:when>
															<c:when
																test="${vendorType.vendor_TypeName == 'SERVICE-VENDOR'}">

															</c:when>
															<c:when
																test="${vendorType.isCommonMaster == 1}">

															</c:when>
															<c:otherwise>
																<td class="icon">
																	<div class="btn-group">
																		<a class="btn btn-default dropdown-toggle"
																			data-toggle="dropdown" href="#"> <span
																			class="fa fa-cog"></span> <span class="caret"></span>
																		</a>
																		<ul class="dropdown-menu pull-right">
																			<li><sec:authorize
																					access="hasAuthority('EDIT_PRIVILEGE')">
																					<a
																						href="editVendorType.in?vendor_Typeid=${vendorType.vendor_Typeid}"><span
																						class="fa fa-pencil"></span> Edit</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_PRIVILEGE')">
																					<a
																						href="deleteVendorType.in?vendor_Typeid=${vendorType.vendor_Typeid}"
																						class="confirmation"
																						onclick="return confirm('Are you sure you Want to delete Vendor Type ?')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																		</ul>
																	</div>
																</td>
															</c:otherwise>
														</c:choose>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/status.validate.js" />"></script>
</div>