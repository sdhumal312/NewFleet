<%@ include file="taglib.jsp"%>
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
						
						<c:if test="${config.showExtendedPartSave}">
							<a class="btn btn-default btn-sm" data-toggle="tip"
								data-original-title="Download Import CSV Format. When Import Don't Remove the header"
								href="${pageContext.request.contextPath}/downloaddocument/2973.in">
								<i class="fa fa-download"></i>
							</a>
						</c:if>
						<c:if test="${!config.showExtendedPartSave}">
							<a class="btn btn-default btn-sm" data-toggle="tip"
								data-original-title="Download Import CSV Format. When Import Don't Remove the header"
								href="${pageContext.request.contextPath}/downloaddocument/5.in">
								<i class="fa fa-download"></i>
							</a>
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IMPORT_PARTS')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_PARTS')">
							<a href="<c:url value="/addNewMasterParts.in"/>"
								class="btn btn-success btn-sm"><span class="fa fa-plus">
									Create Parts</span></a>
						
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
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-wrench"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Parts</span> <span
								class="info-box-number">${MasterPartsCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Parts</span>
							<form action="<c:url value="/searchMasterParts.in"/>"
								method="post">
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
			<c:if test="${param.CountFailed > 0}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					${param.CountFailed} Part not added due to insufficient master data !
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
			<c:if test="${param.importSave}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Imported Successfully ${param.CountSuccess} Parts Data.
				</div>
			</c:if>
			<c:if test="${param.importSaveError}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Part can not be created with this Import CSV File. <br /> Do not
					Import empty CSV File for Part_No, Part_Name is Required
				</div>
			</c:if>
			<c:if test="${param.importSaveAlreadyError}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					<c:if test="${!empty param.Duplicate}">
						<c:forEach items="${param.Duplicate}" var="Duplicate">
				
				      ${CountDuplicate} Duplicate entry Please Check Part No :  <c:out
								value="${param.Duplicate}" /> 
				
				</c:forEach>
					</c:if>
				</div>
			</c:if>
			<!-- Modal  and create the javaScript call modal -->
			<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<!-- Modal content-->
					<div class="modal-content">
					<c:if test="${!config.showExtendedPartSave}">
						<form method="post" action="<c:url value="/importMasterPart.in"/>" enctype="multipart/form-data">
					</c:if>
					<c:if test="${config.showExtendedPartSave}">
						<form method="post" action="<c:url value="/importMasterPartExtended.in"/>" enctype="multipart/form-data">
					</c:if>
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
											data-toggle="modal">Import CSV files</button>
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
				<div class="col-md-11 col-sm-12 col-xs-12">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table id="partTable" class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>Part Number</th>
												<th class="fit ar">Part Name</th>
												<th class="fit ar">Category</th>
												<th class="fit ar">Make</th>
												<c:if test="${config.showPartType }">
													<th class="fit ar">Type</th>
												</c:if>
												<c:if test="${!config.showPartType }">
													<th class="fit ar">${config.UOMTitle}</th>
												</c:if>
												<th id="Actions" class="icon">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty MasterParts}">
												<c:forEach items="${MasterParts}" var="MasterParts">
													<tr>
														<td><a
															href="<c:url value="/showMasterParts.in?partid=${MasterParts.partid}"/>"
															data-toggle="tip"
															data-original-title="Last Update:<c:out value="${MasterParts.lastupdated}"/>"><c:out
																	value="${MasterParts.partnumber}" /></a></td>
														<td class="fit ar"><c:out
																value="${MasterParts.partname}" /></td>
														<td class="fit ar"><c:out
																value="${MasterParts.category}" /></td>
														<td class="fit ar"><c:out value="${MasterParts.make}" /></td>
														<c:if test="${config.showPartType }">
															<td class="fit ar"><c:out value="${MasterParts.parttype}" /></td>
														</c:if>
														<c:if test="${!config.showPartType }">
															<td class="fit ar"><c:out value="${MasterParts.unittype}" /></td>
														</c:if>
													<c:if test="${!commonMasterParts || MasterParts.partManufacturerType == 2}">
														<td class="icon">
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('EDIT_PARTS')">
																					<a href="<c:url value="/editNewMasterParts.in?partid=${MasterParts.partid}"/>">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																			</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PARTS')">
																			<a
																				href="<c:url value="/deleteMasterParts.in?ID=${MasterParts.partid}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Part?')">
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
						</div>
						<c:url var="firstUrl" value="/newMasterParts/1" />
						<c:url var="lastUrl"
							value="/newMasterParts/${deploymentLog.totalPages}" />
						<c:url var="prevUrl" value="/newMasterParts/${currentIndex - 1}" />
						<c:url var="nextUrl" value="/newMasterParts/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/newMasterParts/${i}" />
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
				<div class="col-sm-1 col-md-2"></div>
			</div>
		</sec:authorize>
	</section>
</div>