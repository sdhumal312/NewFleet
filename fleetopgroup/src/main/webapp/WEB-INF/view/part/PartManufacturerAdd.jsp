<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addPartManufacturer.in"/>">New Part
							Manufacturer</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addPartManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddPartManufacturer"> Add
								Part Manufacturer</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.savePartManufacturer eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Manufacturer Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadPartManufacturer eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Manufacturer Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deletePartManufacturer eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Manufacturer Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyPartManufacturer eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Manufacturer Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addPartManufacturer" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="savePartManufacturer.in" method="post"
						name="vehicleStatu" onsubmit="return validateParts()">

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="PartManufacturer">Part
								Manufacturer</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
								<div class="row1">
									<label class="L-size control-label">Name :</label>
									<div class="I-size">
										<input type="text" class="form-text"  name="pmName"
											placeholder="Enter Part Categorie" maxlength="50" id="pmName"
											onkeypress="return IspmName(event);" ondrop="return false;"
											required="required" /> <label id="errorpmName" class="error"></label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Description :</label>
									<div class="I-size">
										<textarea class="text optional form-text" name="pmdescription"
											rows="2" maxlength="150"
											onkeypress="return IsVendorRemark(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorvParts" class="error"></label>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return validatePartManufacturer()">
								<span><spring:message code="label.master.Save" /></span>
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
									<table id="PartManufacturerTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th class="icon">Name</th>
												<th class="icon">Description</th>
												<th id="Usage" class="icon">Usage</th>
												<c:if test="${!commonManufacturer}">
												  <th id="Actions" class="icon">Actions</th>
												 </c:if>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty PartManufacturer}">
												<c:forEach items="${PartManufacturer}"
													var="PartManufacturer">
													<tr>
														<td><c:out value="${PartManufacturer.pmName}" /></td>
														<td><c:out value="${PartManufacturer.pmdescription}" /></td>
														<td><a href="#.." rel="facebox"> Part</a></td>
														<c:if test="${!commonManufacturer}">
														<td>
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="editPartManufacturer.in?pmid=${PartManufacturer.pmid}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deletePartManufacturer.in?pmid=${PartManufacturer.pmid}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Part Manufacturer?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
														</c:if>
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
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/PartManufacturerlanguage.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Parts.validate.js" />"></script>
	</section>
</div>