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
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/newMasterParts/1.in"/>">Master Parts</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('IMPORT_PARTS')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/5.in">
							<i class="fa fa-download"></i>
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IMPORT_PARTS')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_PARTS')">
						<a href="addNewMasterParts.in" class="btn btn-success btn-sm"><span
							class="fa fa-plus"> Create Parts</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PARTS')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/PartReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PARTS')">
			<div class="row">
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-wrench"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Parts</span> <span
								class="info-box-number">${MasterPartsCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Activity</span> <span
								class="info-box-number">0</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Parts</span>
							<form action="searchMasterParts.in" method="post">
								<div class="input-group">
									<input class="form-text" name="partnumber" type="text"
										required="required" placeholder="Search Parts NO/Name">
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
			<c:if test="${param.Save eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Master Parts Created Successfully.
				</div>
			</c:if>

			<c:if test="${param.Delete eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Master Parts Deleted Successfully.
				</div>
			</c:if>
			<c:if test="${param.alreadyCreate eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Master Parts Already Exists.
				</div>
			</c:if>
			<c:if test="${param.NotUpdate eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Master Parts Not Updated Successfully.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Part was Not created.
				</div>
			</c:if>
			<c:if test="${importSave}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Imported Successfully ${CountSuccess} Parts Data.
				</div>
			</c:if>
			<c:if test="${importSaveError}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Part can not be created with this Import CSV File. <br /> Do not
					Import empty CSV File for Part_No, Part_Name is Required
				</div>
			</c:if>
			<c:if test="${importSaveAlreadyError}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					<c:if test="${!empty Duplicate}">
						<c:forEach items="${Duplicate}" var="Duplicate">
				
				      ${CountDuplicate} Duplicate entry Please Check First <c:out
								value="${Duplicate}" /> Part_No &amp; Part_Name
				
				</c:forEach>
					</c:if>
				</div>
			</c:if>
			<!-- Modal  and create the javaScript call modal -->
			<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<!-- Modal content-->
					<div class="modal-content">
						<form method="post" action="importMasterPart.in"
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
												<label> Import Only CSV File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".csv" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Part Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											id="js-upload-submit" value="Add Document"
											data-toggle="modal" data-target="#processing-modal">Import
											CSV files</button>
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
				<div class="col-xs-10">
					<div class="main-body">
						<h4>Recent Active Parts</h4>
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table id="MasterpartTable" class="table">
										<thead>
											<tr>
												<th>Part Number</th>
												<th class="fit ar">Part Name</th>
												<th class="fit ar">Category</th>
												<th class="fit ar">Make</th>
												<th id="Actions" class="icon">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty MasterParts}">
												<c:forEach items="${MasterParts}" var="MasterParts">
													<tr>
														<td><a
															href="showMasterParts.in?partid=${MasterParts.partid}"
															data-toggle="tip"
															data-original-title="Last Update:<c:out value="${MasterParts.lastupdated}"/>"><c:out
																	value="${MasterParts.partnumber}" /></a></td>
														<td class="fit ar"><c:out
																value="${MasterParts.partname}" /></td>
														<td class="fit ar"><c:out
																value="${MasterParts.category}" /></td>
														<td class="fit ar"><c:out value="${MasterParts.make}" /></td>
													<c:choose>
															<c:when test="${!commonMasterParts || MasterParts.partManufacturerType == 2}">
																<td class="icon">
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize access="hasAuthority('EDIT_PARTS')">
																				<a href="editNewMasterParts.in?partid=${MasterParts.partid}">
																					<span class="fa fa-pencil"></span> Edit
																				</a>
																			
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PARTS')">
																			<a
																				href="deleteMasterParts.in?ID=${MasterParts.partid}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Part?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
															</c:when>
															<c:otherwise>
																<td></td>
															</c:otherwise> 
												</c:choose>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-1 col-md-2"></div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#MasterpartTable").DataTable({
				sScrollX : "100%",
				oLanguage : {
					sEmptyTable : "Empty Parts.."
				},
				bScrollcollapse : !0,
				dom : "Blfrtip",
				buttons : [ "excel", "print" ],
				order : [ 0, "desc" ]
			})
		});
	</script>
</div>