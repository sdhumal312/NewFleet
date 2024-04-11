<%@ include file="taglib.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<style>
.custom-select {
    display: inline-block;
    width: 100%;
    height: calc(2.25rem + 2px);
    padding: 0.375rem 1.75rem 0.375rem 0.75rem;
    line-height: 1.5;
    color: #495057;
    vertical-align: middle;
    background: #fff url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 4 5'%3E%3Cpath fill='%23343a40' d='M2 0L0 2h4zm0 5L0 3h4z'/%3E%3C/svg%3E") no-repeat right 0.75rem center;
        background-size: auto;
    background-size: 8px 10px;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
}

.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
.fontSizeIni{
font-size: unset;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="issuesOpen/1.in"/>">Issues</a> / Show Issues
						:I-
						<c:out value="${Issues.ISSUES_NUMBER}"></c:out>
					</div>
					<input type="hidden" name="issueId" value="${Issues.ISSUES_ID}" required="required" /> 
					<input type="hidden" id="vid" value="${Issues.ISSUES_VID}" required="required" /> 
					<input type="hidden" id="showIssueId" value="${Issues.ISSUES_ID}" required="required" /> 
					<input type="hidden" id="companyId" value="${companyId}" required="required" /> 
					<input type="hidden" id="companyId" value="${companyId}" required="required" /> 
					<input type="hidden" id="issuetypeId" value="${Issues.ISSUES_TYPE_ID}">
					<input type="hidden" id="categoryId" value="${Issues.categoryId}">
					<input type="hidden" id="tyreAssginFromIssueConfig" value="${configuration.tyreAssginFromIssue}">
					<input type="hidden" id="tyreCategoryConfigId" value="${configuration.tyreCategoryId}">
					<input type="hidden" id="vid" value="${Issues.ISSUES_VID}">
					<c:if test="${!empty vehicleTyreLayoutPosition}">
						<input type="hidden" id="LP_ID" value="${vehicleTyreLayoutPosition.LP_ID}">
						<input type="hidden" id="tyreId" value="${vehicleTyreLayoutPosition.TYRE_ID}">
					<%-- 	<input type="hidden" id="vid" value="${vehicleTyreLayoutPosition.ISSUES_VID}"> --%>
					</c:if>
					<div class="col-md-off-5">
						<div class="col-md-2">
							<sec:authorize access="!hasAuthority('VIEW_ALL_ISSUES')">
								<form action="<c:url value="/SearchIYShow.in"/>" method="post">
									<div class="input-group">
										<span class="input-group-addon"> <span
											aria-hidden="true">I-</span></span> <input class="form-text"
											name="Search" type="number" min="1" required="required"
											placeholder="I-ID eg:324"> <span
											class="input-group-btn">
											<button type="submit" name="search" id="search-btn"
												class="btn btn-success btn-sm">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</form>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_ALL_ISSUES')">
								<form action="<c:url value="/SearchIYAllShow.in"/>"
									method="post">
									<div class="input-group">
										<span class="input-group-addon"> <span
											aria-hidden="true">I-</span></span> <input class="form-text"
											name="Search" type="number" min="1" required="required"
											placeholder="I-ID eg:687" maxlength="20"> <span
											class="input-group-btn">
											<button type="submit" name="search" id="search-btn"
												class="btn btn-success btn-sm">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</form>
							</sec:authorize>
						</div>
						
						
						<c:if test="${fn:contains(Issues.ISSUES_ASSIGN_TO, email) || Issues.CREATEDBYID == createdById || operateAllIssuePermission == true}">
								<c:if test="${Issues.ISSUES_STATUS_ID == 4 || Issues.ISSUES_STATUS_ID == 5 }">
									<sec:authorize access="hasAuthority('CLOSE_ISSUES')">
									<c:if test="${!configuration.closureWithRemark}"> 
										<a class="btn btn-warning " data-toggle="modal"
											data-target="#Closed"> <span class="fa fa-check-circle"></span>
											Close
										</a>
									</c:if>
									<c:if test="${configuration.closureWithRemark}"> 
									<c:choose>
									<c:when test="${Issues.ISSUES_TYPE_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">
									<a class="btn btn-warning " data-toggle="modal"
											data-target="#issueRemark" onclick="setDriverName()"> <span class="fa fa-check-circle"></span>
											Close
										</a>
									</c:when>
									<c:otherwise>
									<a class="btn btn-warning " data-toggle="modal"
											data-target="#Closed"> <span class="fa fa-check-circle"></span>
											Close
										</a>
									</c:otherwise>
									</c:choose>
									</c:if>
									</sec:authorize>
								</c:if>
								<c:choose>
								<c:when test="${Issues.ISSUES_STATUS_ID == 1}">
									<c:if test="${Issues.ISSUES_TYPE_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">

										<sec:authorize
											access="hasAuthority('CHANGE_ISSUE_TYPE_BREAKDOWN')">
											<c:if
												test="${Issues.ISSUES_STATUS_ID == 1 && Issues.ISSUES_TYPE_ID != 6}">
												<input type="hidden" id="issue_ID"
													value="${Issues.ISSUES_ID}">
												<a class="btn btn-success " href="<c:url value="#"/>"
													onclick="changeIssueType();"> <span class="fa fa-plus">
												</span> Change to Vehicle Break down
												</a>
											</c:if>
										</sec:authorize>

										<sec:authorize access="hasAuthority('CREATE_WO_FROM_ISSUE')">
											<a class="btn btn-success "
												href="<c:url value="/createWorkOrder?issue=${Issues.ISSUES_VID},${Issues.ISSUES_ID}"/>">
												<span class="fa fa-plus"> </span> Create WorkOrder
											</a>
										</sec:authorize>
										
										<sec:authorize access="hasAuthority('CREATE_OM_FROM_ISSUE') && hasAuthority('ADD_SERVICE_ENTRIES')">
										<a class="btn btn-success " title="Service Entries"
											href="<c:url value="/createServiceEntries?issue=${Issues.ISSUES_VID},${Issues.ISSUES_ID}"/>">
											<span class="fa fa-plus"> </span> Create Outside Maintenance
										</a>
										</sec:authorize>
										<sec:authorize access="hasAuthority('CREATE_DSE_FROM_ISSUE')">
										<a class="btn btn-success " title="Dealer Service Entries"
											href="<c:url value="/createDealerServiceEntries?issue=${Issues.ISSUES_ID}"/>">
											<span class="fa fa-plus"> </span> Create DSE
										</a>
										</sec:authorize>
									</c:if>
									<c:if test="${Issues.ISSUES_TYPE_ID != 6 || !configuration.resolveIssueViaOnlyWODSE}">
										<a class="btn btn-warning " data-toggle="modal"
											data-target="#Resolve"> <span class="fa fa-check-circle"></span>
											Resolve
										</a>
									</c:if>
									
									<a class="btn btn-danger " data-toggle="modal"
										data-target="#Reject"> <i class="fa fa-times-circle"></i>
										Reject
									</a>
									<a class="btn btn-info "
											href="<c:url value="/editIssuesDetails?Id=${Issues.ISSUES_ID_ENCRYPT}"/>">
											<span class="fa fa-pencil"></span> Edit Issues
									</a>
								</c:when>
								
								<c:when test="${Issues.ISSUES_STATUS_ID == 2 && empty Issues.ISSUES_WORKORDER_ID   && empty Issues.ISSUES_SE_ID  && empty Issues.dealerServiceEntriesId}">
									<sec:authorize access="hasAuthority('REOPEN_ISSUES')">
										<a class="btn btn-success " data-toggle="modal"
											data-target="#Reopen"> <i class="fa fa-repeat"
											aria-hidden="true"> </i>Reopen
										</a>
									</sec:authorize>
	
								</c:when>
								
							</c:choose>
						</c:if>
						<c:if test="${configuration.tyreAssginFromIssue}">
							<c:if test="${Issues.ISSUES_TYPE_ID == 6 && Issues.ISSUES_STATUS_ID == 1}">
								<c:choose>
									<c:when test="${Issues.issueLP_ID > 0 && tyreId == true}">
								 		<a class="btn btn-warning" style="display:none" id="assignTyre" onclick="dismountTyre();"> Tyre Move </a>
									</c:when>
									<c:otherwise>
								 		<a class="btn btn-warning" style="display:none" id="assignTyre" onclick="mountTyre();"> Tyre Assign </a>
									</c:otherwise>
								</c:choose>
							 </c:if>
						 </c:if>
						<a class="btn btn-link" href="<c:url value="/issuesOpen/1.in"/>">
							<span id="AddVehicle"> Cancel</span>
						</a>

					</div>
				</div>
				<div class="box-body">
					<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<c:choose>
							<c:when test="${Issues.ISSUES_TYPE_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">
								<div class="row">
									<div class="col-md-8 col-sm-8 col-xs-12">
										<h3 class="secondary-header-title">
											<a href="showVehicle.in?vid=${Issues.ISSUES_VID}"
												data-toggle="tip"
												data-original-title="Click Vehicle Details"> <c:out
													value="${Issues.ISSUES_VEHICLE_REGISTRATION}" />
											</a>

										</h3>
										<div class="secondary-header-title">
											<ul class="breadcrumb">
												<li><c:choose>
														<c:when test="${Issues.ISSUES_STATUS_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">
															<small class="label label-info"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:when test="${Issues.ISSUES_STATUS_ID == 5}">
														
															<small class="label label-danger"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:when test="${Issues.ISSUES_STATUS_ID == 3}">
															<small class="label label-warning"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:otherwise>
															<small class="label label-success"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:otherwise>
													</c:choose></li>

												<li><span class="fa fa-user" aria-hidden="true"
													data-toggle="tip" data-original-title="Issues Type">
												</span> <a href=""><c:out value="${Issues.ISSUES_TYPE}" /></a></li>

												<li><span class="fa fa-user" aria-hidden="true"
													data-toggle="tip" data-original-title="Driver Details">
												</span> <a
													href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"><c:out
															value="${Issues.ISSUES_DRIVER_NAME}" /></a></li>
											</ul>
										</div>
									</div>
								</div>
							</c:when>
							<c:when test="${Issues.ISSUES_TYPE_ID == 2}">
								<div class="row">
									<div class="col-md-8 col-sm-8 col-xs-12">
										<h3 class="secondary-header-title">
											<a href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"
												data-toggle="tip" data-original-title="Click Driver Details">
												<c:out value="${Issues.ISSUES_DRIVER_NAME}" />
											</a>
										</h3>
										<div class="secondary-header-title">
											<ul class="breadcrumb">
												<li><c:choose>
														<c:when test="${Issues.ISSUES_STATUS_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">
															<small class="label label-info"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:when test="${Issues.ISSUES_STATUS_ID == 5}">
															<small class="label label-danger"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:when test="${Issues.ISSUES_STATUS_ID == 3}">
															<small class="label label-warning"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:otherwise>
															<small class="label label-success"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:otherwise>
													</c:choose></li>

												<li><span class="fa fa-user" aria-hidden="true"
													data-toggle="tip" data-original-title="Issues Type">
												</span> <a href=""><c:out value="${Issues.ISSUES_TYPE}" /></a></li>
											</ul>
										</div>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="row">
									<div class="col-md-8 col-sm-8 col-xs-12">
										<h3 class="secondary-header-title">
											<a href="showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
												data-toggle="tip" data-original-title="Click Branch Details">
												<c:out value="${Issues.ISSUES_BRANCH_NAME}" />
											</a> - <a
												href="showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
												data-toggle="tip" data-original-title="Click Branch Details">
												<c:out value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
											</a>
										</h3>
										<div class="secondary-header-title">
											<ul class="breadcrumb">
												<li><c:choose>
														<c:when test="${Issues.ISSUES_STATUS_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">
															<small class="label label-info"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:when test="${Issues.ISSUES_STATUS_ID == 5}">
															<small class="label label-danger"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:when test="${Issues.ISSUES_STATUS_ID == 3}">
															<small class="label label-warning"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:when>
														<c:otherwise>
															<small class="label label-success"><c:out
																	value="${Issues.ISSUES_STATUS}" /></small>
														</c:otherwise>
													</c:choose></li>

												<li><span class="fa fa-user" aria-hidden="true"
													data-toggle="tip" data-original-title="Issues Type">
												</span> <a href=""><c:out value="${Issues.ISSUES_TYPE}" /></a></li>
											</ul>
										</div>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
				</div>
			</div>
		</div>
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveIssuesComment.in">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Issue Comment</h4>
					</div>
					<div class="modal-body">
						<div >
							<input type="hidden" name="ISSUES_ID" value="${Issues.ISSUES_ID}" />
							<!-- <label class="l-size">Title/Name</label> -->
							<!-- <div class="I-sze">
								<input type="text" name="ISSUE_TITLE" class="form-text"
									maxlength="25" onkeypress="return IsDriverCommentName(event);"
									ondrop="return false;"> <label class="error"
									id="errorCommentName" style="display: none"> </label>
							</div> -->
						</div>
							<div class="row" >
								<label class="l-size">Title/Name <abbr title="required">*</abbr></label> <select
								required="required"	name="COMMENT_TYPE_ID" id="commentTypeId" class="browser-default custom-select">
									<option value="-1"></option>
									<c:forEach items="${commentType}" var="commentType">
										<option value="${commentType.commentTypeId}"><c:out
												value="${commentType.commentTypeName}" /></option>
									</c:forEach>
								</select>
							</div>
							<br>
						<div class="form-group">
							<label class="l-size">Comment: <abbr title="required">*</abbr></label>
							<textarea class="form-text" rows="5" id="comment"  required="required"
								name="ISSUE_COMMENT" maxlength="900"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Save Comment" onclick="return issueCommentValidate();">Save Comment</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	 <div class="modal fade" id="IssuesDocuemnt" role="dialog">
			<div class="modal-dialog" style="width:980px;">
			<!-- Modal content-->
			<div class="modal-content" >
				<form method="post" action="saveIssuesDocument.in"
					enctype="multipart/form-data">
						<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">Issue Document</h3>
						</div>
							<div>
								<table style="width: 100%;" class="table-responsive table">
									<thead>
										<tr>
											<th width="30%">Document Name</th>
											<th width="30%">File Name</th>
											<th width="15%">Upload Date</th>
											<th width="10%">Download</th>
											<th width="10%">Action</th>
										</tr>
									</thead>
									<c:if test="${!empty IssuesDocument}">
										<tbody>
											<c:forEach items="${IssuesDocument}" var="IssuesDocument">
												<tr>
													<td class="fit">${IssuesDocument.ISSUE_DOCUMENTNAME}</td>
													<td class="fit">${IssuesDocument.ISSUE_FILENAME}</td>
													<td class="fit">${IssuesDocument.ISSUE_UPLOADDATE_STR}</td>
													<td class="fit"><a class="fa fa-download"
														href="${pageContext.request.contextPath}/download/issueDocument/${IssuesDocument.ISSUE_DOCUMENTID_ENCRYPT}"
														target="_blank"></a></td>
													<td class="fit"><a class="fa fa-remove"
														href="deleteIssuesDocument?Id=${IssuesDocument.ISSUES_ID_ENCRYPT}&DID=${IssuesDocument.ISSUE_DOCUMENTID_ENCRYPT}"
														href="#">Remove</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</c:if>
								</table>
							</div>


							<input type="hidden" name="ISSUES_ID" value="${Issues.ISSUES_ID}" />

						<div class="panel-body">
							<div class="form-horizontal">

								<div class="row1">
									<div class="L-size">
										<label class="col-md-3">Document Name</label>

									</div>
									<div class="I-size">
										<input type="text" name="ISSUE_DOCUMENTNAME" class="form-text"
											maxlength="148" required="required"
											onkeypress="return IsDriverDocumentName(event);"
											ondrop="return false;"> <label class="error"
											id="errorDocumentName" style="display: none"> </label>
									</div>
								</div>
								<div class="row1">
									<div class="L-size">
										<label class="col-md-3"> Browse: </label>
									</div>
									<div class="I-size">
										<input type="file" accept="image/png, image/jpeg, image/gif"
											name="INPUT_FILE" required="required" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									id="js-upload-submit" value="Add Document">Save
									Document</button>
								<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
							</div>

						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="IssuesPhoto" role="dialog">
			<div class="modal-dialog" style="width:980px;">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveIssuesPhoto.in"
					enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Issue Photo</h4>
					</div>
					<div>
								<table style="width: 100%;" class="table-responsive table">
									<thead>
										<tr>
											<th width="30%">Photo Name</th>
											<th width="30%">File Name</th>
											<th width="15%">Upload Date</th>
											<th width="10%">Download</th>
											<th width="10%">Action</th>
										</tr>
									</thead>
									<c:if test="${!empty IssuesPhoto}">
										<tbody>
											<c:forEach items="${IssuesPhoto}" var="IssuesPhoto">
												<tr>
													<td class="fit">${IssuesPhoto.ISSUE_PHOTONAME}</td>
													<td class="fit">${IssuesPhoto.ISSUE_FILENAME}</td>
													<td class="fit">${IssuesPhoto.ISSUE_UPLOADDATE_STR}</td>
													<td class="fit"><a class="fa fa-download"
														href="${pageContext.request.contextPath}/getIssuePhoto/${IssuesPhoto.ISSUES_PHOTOID_ENCRYPT}"
														target="_blank"></a></td>
													<td class="fit"><a class="fa fa-remove"
														href="deleteIssuesPhoto?PID=${IssuesPhoto.ISSUES_PHOTOID_ENCRYPT}&Id=${IssuesPhoto.ISSUES_ID_ENCRYPT}"
														href="#">Remove</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</c:if>
								</table>
							</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="row1">
								<div class="L-size">
									<input type="hidden" name="ISSUES_ID"
										value="${Issues.ISSUES_ID}" /> <label class="col-md-3">Title/Photo
										Name</label>
								</div>
								<div class="I-size">
									<input type="text" name="ISSUE_PHOTONAME" class="form-text"
										required="required" maxlength="248"
										onkeypress="return IsDriverPhotoName(event);"
										ondrop="return false;"> <label class="error"
										id="errorPhotoName" style="display: none"> </label>
								</div>
							</div>
							<div class="row">
								<div class="L-size"></div>
								<label class="L-size"></label>
								<div class="I-size">
									<input type="file" name="INPUT_FILE" id="file" accept="image/*"
										required="required"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Add Document">Save Photo</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="breakDown" role="dialog">
		<div class="modal-dialog" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Change Issue Type to Vehicle breakdown</h4>
				</div>
				
				<div class="modal-body">
				
					<div class="row">
						<div class="col col-sm-1 col-md-5">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment">Location</label>
							<div class="col col-sm-1 col-md-5">
								<input type="text" class="form-text" id="location" style="width: 100%;" placeholder="Please Enter Vehicle Location" value="${Issues.location}"/>
							</div>
						</div>
						<div class="col col-sm-1 col-md-5">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment">Trip Number</label>
							<div class="col col-sm-1 col-md-5">
									<input class="form-text" type="number" maxlength="150" id="tripNumber" onkeypress="return isNumberKey(event);">
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col col-sm-1 col-md-5">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment">Vehicle Replaced</label> 
							<div class="col col-sm-1 col-md-5">
								<label for="comment" class="fontSizeIni">NO</label>
								<label class="switch"> 
									<input type="checkbox" id="isVehicleReplaced" onclick="checkVehicleReplace();"> <span class="slider round"></span>
								</label>
								<label for="comment" class="fontSizeIni">YES</label>
								</div>
						</div>
					</div>
					<br>
					<div class="row" id="replaceVehicleRow" style="display: none;">
						<div class="col col-sm-1 col-md-5">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment"> Replaced With Vehicle<abbr title="required">*</abbr></label>
							<div class="col col-sm-1 col-md-5">
								<input type="hidden" id="replacedVehicle" style="width: 100%;" placeholder="Please Enter Vehicle Number" onclick="validateReplaceVehicle();"/>
							</div>
						</div>
						<div class="col col-sm-1 col-md-5">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment">Replaced Location</label>
							<div class="col col-sm-1 col-md-5">
								<input type="text" class="form-text" id="replacedLocation" style="width: 100%;" placeholder="Please Enter Vehicle Location" />
							</div>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col col-sm-1 col-md-5">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment">Trip Cancelled</label> 
							<div class="col col-sm-1 col-md-5">
								<label for="comment" class="fontSizeIni">NO</label>
								<label class="switch"> 
									<input type="checkbox" id="isTripCancelled" onclick="checkTripCancelled();"> <span class="slider round"></span>
								</label>
								<label for="comment" class="fontSizeIni">YES</label>
								</div>
						</div>
						<div class="col col-sm-1 col-md-5" id="cancelledKmDiv" style="display: none;">
							<label class="col col-sm-1 col-md-5 fontSizeIni" for="comment">Cancelled KM
							</label>
							<div class="col col-sm-1 col-md-5">
									<input class="form-text" type="number" maxlength="150" id="cancelledKM" onkeypress="return isNumberKey(event);">
							</div>
						</div>
					</div>
					<br>
					
					<div class="row">
						<div class="col col-sm-1 col-md-5">
							<label for="comment" class="col col-sm-1 col-md-5 fontSizeIni">Remark</label>
						</div>
						<div class="col col-sm-1 col-md-10">
							<textarea class="form-text" rows="3" id="brRemark" style="margin-left: 15px;"
								required="required" placeholder="Please Enter Remark...."  maxlength="900"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary"
						onclick="changeToBreakDown()" id="submit" value="Save">Change
						to breakdown</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</section>
	<section class="content">
		<div class="modal fade" id="Reject" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post" action="saveRejectIssues.in">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Reject Issue</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<input type="hidden" name="Id" value="${Issues.ISSUES_ID}"
									required="required" /> <input type="hidden" name="Status"
									value="${Issues.ISSUES_STATUS_ID}" required="required" />
							</div>
							<br>
							<div class="form-group">
								<label for="comment">Reject reason:</label>
								<textarea class="form-text" rows="5" id="comment"
									required="required"
									placeholder="Please Enter Rejecting reason...."
									name="ISSUES_REJECT_REASON" maxlength="900"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								id="js-upload-submit" value="Save Comment">Reject Issue</button>
							<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Modal  and Reopen the javaScript call modal -->
		<div class="modal fade" id="Reopen" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post" action="saveReopenIssues.in">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Reopen Issue</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<input type="hidden" name="Id" value="${Issues.ISSUES_ID}"
									required="required" /> <input type="hidden" name="Status"
									value="${Issues.ISSUES_STATUS_ID}" required="required" />
							</div>
							<br>
							<div class="form-group">
								<label for="comment">Reopen reason:</label>
								<textarea class="form-text" rows="5" id="comment"
									placeholder="Please Enter Reopening reason...."
									name="ISSUES_REOPEN_REASON" maxlength="900"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								id="js-upload-submit" value="Save Comment">Reopen Issue</button>
							<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="Resolve" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post" action="saveResolveIssues.in">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Resolved Issue</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<input type="hidden" name="Id" value="${Issues.ISSUES_ID}"
									required="required" /><input type="hidden" name="Status"
									value="${Issues.ISSUES_STATUS_ID}" required="required" />
							</div>
							<br>
							<div class="form-group">
								<label for="comment">Resolved via:</label>
								<textarea class="form-text" rows="5" id="comment"
									placeholder="Please Enter Resolved information..."
									name="ISSUES_RESOLVE_REASON" maxlength="900"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								id="js-upload-submit" value="Save Comment">Resolved
								Issue</button>
							<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="Closed" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form method="post" action="saveCloseIssues.in">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Close Issue</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<input type="hidden" name="Id" value="${Issues.ISSUES_ID}"
									required="required" /> <input type="hidden" name="Status"
									value="${Issues.ISSUES_STATUS_ID}" required="required" />
							</div>
							<br>
							<div class="form-group">
								<label for="comment">Close reason:</label>
								<textarea class="form-text" rows="5" id="comment"
									required="required"
									placeholder="Please Enter Closeing reason...."
									name="ISSUES_CLOSE_REASON" maxlength="900"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								id="js-upload-submit" value="Save Comment">Close Issue</button>
							<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="issueRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
				<form method="post" action="saveCloseIssues.in">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title"> Issue closure Remark</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="driverIdRemark" value="${Issues.ISSUES_DRIVER_ID}">
						<input type="hidden" id="driverNameRemark" value="${Issues.ISSUES_DRIVER_NAME}">
						<input type="hidden" name="Id" value="${Issues.ISSUES_ID}" /> 
						<input type="hidden" name="Status" value="${Issues.ISSUES_STATUS_ID}" />
						<input type="hidden" name="ISSUE_TITLE" value="Closure Remark" />
						<input type="hidden" name="ISSUES_CLOSE_REASON" value=" " />
						<div class="row1">
							<label class="L-size control-label" for="confirmedWithDriver">Confirmed
								With Driver </label>
							<div class="I-size">
								<input type="hidden" name="driverId" id="confirmedWithDriver"
									style="width: 100%;" />
							</div>
						</div>
						<br />
						<div class="row1">
							<label class="L-size control-label" for="confirmedWithAssignee">Confirmed
								With Assignee </label>
							<div class="I-size">
								<input type="hidden" name="assignee" id="confirmedWithAssignee"
									style="width: 100%;" />
							</div>
						</div>
						<br />

						<div class="row1">
							<label class="L-size control-label" for="woIssueRemark">Remark
							</label>
							<div class="I-size">
								<script language="javascript" src="jquery.maxlength.js"></script>
								<textarea class="text optional form-text" id="woIssueRemark"
									name="ISSUE_COMMENT" rows="3" maxlength="1000"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" onclick="return saveWOIssueRemark();"
							id="btnSubmit" class="btn btn-primary">
							<span>Save Remark</span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span>Cancel</span>
						</button>
					</div>
				</form>
				</div>
			</div>
		</div>

		
		<div class="modal fade" id="tyreAssignModal" role="dialog">
			<div class="modal-dialog" style="width:980px;">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Assigned Tyre</h4>
				</div>
				<div class="modal-body">
					<table style="width: 100%;" class="table-responsive table">
						<thead>
							<tr>
								<th>SR No</th>
								<th>Position</th>
								<th>Tyre Number</th>
							</tr>
						</thead>
						<tbody id="tyreAssignTable">
						</tbody>
						
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
		<c:if test="${param.emptyWO eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Create WorkOrders Required Vehicle Name, Assigned To, Start date,
				Work_Location.. please enter.
			</div>
		</c:if>
		<c:if test="${param.saveWorkOrder eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issues to Work_Order Created successfully and Issues Resolved.
			</div>
		</c:if>
		<c:if test="${param.updateSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Updated successfully .
			</div>
		</c:if>
		<c:if test="${param.closeSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Closed successfully .
			</div>
		</c:if>
		<c:if test="${param.ReopenSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Reopen successfully .
			</div>
		</c:if>
		<c:if test="${param.RejectSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Reject successfully .
			</div>
		</c:if>
		<c:if test="${param.ResolvedSuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Resolved successfully .
			</div>
		</c:if>
		<c:if test="${param.NotAssign eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Was Not Assign to you .. you can't reject and Resolve,
				Close .
			</div>
		</c:if>
		<c:if test="${param.anaysisNotFound eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Please Enter Issue Analysis Details.
			</div>
		</c:if>
		<c:if test="${param.closeStatus eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>

				${VMandatory}<br> You should be close first TripSheet or change
				status or close workOrder .
			</div>
		</c:if>
		
		<c:if test="${param.saveComment eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Comment Uploaded Successfully.
					</div>
				</c:if>

				<c:if test="${param.deleteComment  eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Issue Comment Deleted Successfully .
					</div>
				</c:if>

				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Comment was Not created.
					</div>
				</c:if>
		
		<c:if test="${param.saveDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Document Uploaded successfully.
					</div>
				</c:if>
				<c:if test="${param.deleteDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Issue Document Removed successfully .
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Document file is Empty.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Document Not created.
					</div>
				</c:if>
				<c:if test="${param.savePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Photo was successfully Uploaded.
					</div>
				</c:if>
				<c:if test="${param.deletePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Issue Photo Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue was Not created.
					</div>
				</c:if>
		<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_ISSUES')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<blockquote>
								<p class="media-heading">
									<c:out value="${Issues.ISSUES_SUMMARY}" />
								</p>
								<small> <c:choose>
										<c:when test="${Issues.ISSUES_LABELS == 'NORMAL'}">
											<small class="label label-primary"><c:out
													value="${Issues.ISSUES_LABELS}" /></small>
										</c:when>
										<c:when test="${Issues.ISSUES_LABELS == 'HIGH'}">
											<small class="label label-info"><c:out
													value="${Issues.ISSUES_LABELS}" /></small>
										</c:when>
										<c:when test="${Issues.ISSUES_LABELS == 'LOW'}">
											<small class="label label-default"><c:out
													value="${Issues.ISSUES_LABELS}" /></small>
										</c:when>
										<c:when test="${Issues.ISSUES_LABELS == 'URGENT'}">
											<small class="label label-warning"><c:out
													value="${Issues.ISSUES_LABELS}" /></small>
										</c:when>
										<c:otherwise>
											<small class="label label-danger"><c:out
													value="${Issues.ISSUES_LABELS}" /></small>
										</c:otherwise>
									</c:choose> ${Issues.ISSUES_DESCRIPTION}
								</small>
							</blockquote>
						</div>
						<div class="row">
						<c:if test="${!configuration.additionalInfoInIssueView}">
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Issues Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<c:if test="${Issues.ISSUES_TYPE_ID == 1}">
													<tr class="row">
														<th class="key">Vehicle Group :</th>
														<td class="value"><c:out
																value="${Issues.ISSUES_VEHICLE_GROUP}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Vehicle Odometer :</th>
														<td class="value"><c:out
																value="${Issues.ISSUES_ODOMETER}" /></td>
													</tr>
													<c:if test="${Issues.GPS_ODOMETER != null && Issues.GPS_ODOMETER > 0}">
														<tr class="row">
															<th class="key">GPS Odometer :</th>
															<td class="value"><c:out
																	value="${Issues.GPS_ODOMETER}" /></td>
														</tr>
													</c:if>
													
													<c:if test="${Issues.ISSUES_WORKORDER_ID != null}">
														<tr class="row">
															<th class="key">WorkOrder Details :</th>
															<td class="value"><a
																href="showWorkOrder?woId=${Issues.ISSUES_WORKORDER_ID}"
																data-toggle="tip"
																data-original-title="Click work Order Info"> <c:out
																		value="WO-${Issues.ISSUES_WORKORDER_NUMBER}" />

															</a></td>
														</tr>
														<tr class="row">
															<th class="key">Created WorkOrder Date :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_WORKORDER_DATE}" /></td>
														</tr>
													</c:if>
													
													<c:if test="${Issues.ISSUES_SE_ID != null}">
														<tr class="row">
															<th class="key">Service Entries Details :</th>
															<td class="value"><a
																href="ServiceEntriesParts.in?SEID=${Issues.ISSUES_SE_ID}"
																data-toggle="tip"
																data-original-title="Click work Order Info"> <c:out
																		value="SE-${Issues.serviceEntries_Number}" />

															</a></td>
														</tr>
														<tr class="row">
															<th class="key">Created ServiceEntries Date :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_SE_DATE}" /></td>
														</tr>
													</c:if>

												</c:if>
												<tr class="row">
													<th class="key">Reported Date :</th>
													<td class="value"><c:out
															value="${Issues.ISSUES_REPORTED_DATE}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Reported By :</th>
													<td class="value"><c:out
															value="${Issues.ISSUES_REPORTED_BY}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Assigned To :</th>
													<td class="value">${Issues.ISSUES_ASSIGN_TO_NAME}</td>
												</tr>
												<c:choose><c:when
													test="${Issues.partCategoryName != '' }">
													<tr class="row">
													<th class="key">Category Name :</th>
													<td class="value">${Issues.partCategoryName}</td>
												</tr>
												</c:when></c:choose>
												<c:choose><c:when
													test="${Issues.routeName != '' }">
													<tr class="row">
													<th class="key">Route Name :</th>
													<td class="value">${Issues.routeName}</td>
												</tr>
												</c:when></c:choose>
												
												<c:if test="${!empty breakDownDetails}">
													<tr class="row">
														<th class="key">Break Down Location :</th>
														<td class="value"><c:out
																value="${breakDownDetails.breakDownLocation}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Trip Number :</th>
														<td class="value"><c:out
																value="${breakDownDetails.tripNumber}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Is Vehicle Replaced :</th>
														<td class="value"><c:out
																value="${breakDownDetails.vehicleReplacedStr}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Replaced With Vehicle :</th>
														<td class="value"><c:out
																value="${breakDownDetails.vehicleNumber}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Replaced Location :</th>
														<td class="value"><c:out
																value="${breakDownDetails.breakDownLocation}" /></td>
													</tr>
													<tr class="row">
														<th class="key">IS Trip Cancelled  :</th>
														<td class="value"><c:out
																value="${breakDownDetails.tripCancelledStr}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Cancelled KM :</th>
														<td class="value"><c:out
																value="${breakDownDetails.cancelledKM}" /></td>
													</tr>
													
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							</c:if>
							<c:if test="${configuration.additionalInfoInIssueView}">
								<div class="col-md-5 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Issues Information</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Issue Type & Label :</th>
														<td class="value"><c:out
																value="${Issues.ISSUES_TYPE} & ${Issues.ISSUES_LABELS}" /></td>
													</tr>
													<c:if test="${Issues.ISSUES_TYPE_ID == 1}">

														<tr class="row">
															<th class="key">Vehicle :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_VEHICLE_REGISTRATION}" /></td>
														</tr>

														<tr class="row">
															<th class="key">Vehicle Group :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_VEHICLE_GROUP}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Odometer :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_ODOMETER}" /></td>
														</tr>
														<c:choose>
															<c:when test="${Issues.routeName != '' }">
																<tr class="row">
																	<th class="key">Route Name :</th>
																	<td class="value">${Issues.routeName}</td>
																</tr>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when test="${Issues.partCategoryName != '' }">
																<tr class="row">
																	<th class="key">Category Name :</th>
																	<td class="value">${Issues.partCategoryName}</td>
																</tr>
															</c:when>
														</c:choose>
														<tr class="row">
															<th class="key">Issues Summary :</th>
															<td class="value">${Issues.ISSUES_SUMMARY}</td>
														</tr>
														<tr class="row">
															<th class="key">Location :</th>
															<td class="value"><c:out value="${Issues.location}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Reported Date :</th>
															<td class="value"><c:out value="${Issues.ISSUES_REPORTED_DATE}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Driver Name & Mobile no:</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_DRIVER_NAME} & ${Issues.driver_mobnumber}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Reported By :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_REPORTED_BY}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Assigned To :</th>
															<td class="value">${Issues.ISSUES_ASSIGN_TO_NAME}</td>
														</tr>
														<c:if
															test="${Issues.GPS_ODOMETER != null && Issues.GPS_ODOMETER > 0}">
															<tr class="row">
																<th class="key">GPS Odometer :</th>
																<td class="value"><c:out
																		value="${Issues.GPS_ODOMETER}" /></td>
															</tr>
														</c:if>

														<c:if test="${Issues.ISSUES_WORKORDER_ID != null}">
															<tr class="row">
																<th class="key">WorkOrder Details :</th>
																<td class="value"><a
																	href="showWorkOrder?woId=${Issues.ISSUES_WORKORDER_ID}"
																	data-toggle="tip"
																	data-original-title="Click work Order Info"> <c:out
																			value="WO-${Issues.ISSUES_WORKORDER_NUMBER}" />

																</a></td>
															</tr>
															<tr class="row">
																<th class="key">Created WorkOrder Date :</th>
																<td class="value"><c:out
																		value="${Issues.ISSUES_WORKORDER_DATE}" /></td>
															</tr>
														</c:if>

														<c:if test="${Issues.ISSUES_SE_ID != null}">
															<tr class="row">
																<th class="key">Service Entries Details :</th>
																<td class="value"><a
																	href="ServiceEntriesParts.in?SEID=${Issues.ISSUES_SE_ID}"
																	data-toggle="tip"
																	data-original-title="Click work Order Info"> <c:out
																			value="SE-${Issues.serviceEntries_Number}" />

																</a></td>
															</tr>
															<tr class="row">
																<th class="key">Created ServiceEntries Date :</th>
																<td class="value"><c:out
																		value="${Issues.ISSUES_SE_DATE}" /></td>
															</tr>
														</c:if>

													</c:if>

													<c:if test="${Issues.ISSUES_TYPE_ID == 2}">
														<tr class="row">
															<th class="key">Issues Summary :</th>
															<td class="value">${Issues.ISSUES_SUMMARY}</td>
														</tr>
														<tr class="row">
															<th class="key">Location :</th>
															<td class="value"><c:out value="${Issues.location}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Reported Date :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_REPORTED_DATE}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Driver Name & Mobile no:</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_DRIVER_NAME} & ${Issues.driver_mobnumber}" /></td>
														</tr>
														<tr class="row">
														<th class="key">Reported By :</th>
														<td class="value"><c:out
																value="${Issues.ISSUES_REPORTED_BY}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Assigned To :</th>
														<td class="value">${Issues.ISSUES_ASSIGN_TO_NAME}</td>
													</tr>
													
													</c:if>
													
												<c:if test="${Issues.ISSUES_TYPE_ID != 1 &&  Issues.ISSUES_TYPE_ID != 2 && Issues.ISSUES_TYPE_ID != 6}">	
													<c:choose>
														<c:when test="${Issues.routeName != '' }">
															<tr class="row">
																<th class="key">Route Name :</th>
																<td class="value">${Issues.routeName}</td>
															</tr>
														</c:when>
													</c:choose>
													<c:choose>
														<c:when test="${Issues.partCategoryName != '' }">
															<tr class="row">
																<th class="key">Category Name :</th>
																<td class="value">${Issues.partCategoryName}</td>
															</tr>
														</c:when>
													</c:choose>
													<tr class="row">
														<th class="key">Issues Summary :</th>
														<td class="value">${Issues.ISSUES_SUMMARY}</td>
													</tr>
													<tr class="row">
														<th class="key">Location :</th>
														<td class="value"><c:out value="${Issues.location}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Reported Date :</th>
														<td class="value"><c:out
																value="${Issues.ISSUES_REPORTED_DATE}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Reported By :</th>
														<td class="value"><c:out
																value="${Issues.ISSUES_REPORTED_BY}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Assigned To :</th>
														<td class="value">${Issues.ISSUES_ASSIGN_TO_NAME}</td>
													</tr>
													
												</c:if>
													<c:if test="${Issues.ISSUES_TYPE_ID == 6 }">
														<tr class="row">
															<th class="key">Vehicle :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_VEHICLE_REGISTRATION}" /></td>
														</tr>
																			
														<tr class="row">
															<th class="key">Vehicle Group :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_VEHICLE_GROUP}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Odometer :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_ODOMETER}" /></td>
														</tr>
														<c:choose>
															<c:when test="${Issues.routeName != '' }">
																<tr class="row">
																	<th class="key">Route Name :</th>
																	<td class="value">${Issues.routeName}</td>
																</tr>
															</c:when>
														</c:choose>
														<c:choose>
															<c:when test="${Issues.partCategoryName != '' }">
																<tr class="row">
																	<th class="key">Category Name :</th>
																	<td class="value">${Issues.partCategoryName}</td>
																</tr>
															</c:when>
														</c:choose>
														<tr class="row">
															<th class="key">Issues Summary :</th>
															<td class="value">${Issues.ISSUES_SUMMARY}</td>
														</tr>
														<tr class="row">
															<th class="key">Location :</th>
															<td class="value"><c:out value="${Issues.location}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Reported Date :</th>
															<td class="value"><c:out value="${Issues.ISSUES_REPORTED_DATE}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Driver Name & Mobile no:</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_DRIVER_NAME} & ${Issues.driver_mobnumber}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Reported By :</th>
															<td class="value"><c:out
																	value="${Issues.ISSUES_REPORTED_BY}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Assigned To :</th>
															<td class="value">${Issues.ISSUES_ASSIGN_TO_NAME}</td>
														</tr>
															<c:if test="${Issues.ISSUES_WORKORDER_ID != null}">
															<tr class="row">
																<th class="key">WorkOrder Details :</th>
																<td class="value"><a
																	href="showWorkOrder?woId=${Issues.ISSUES_WORKORDER_ID}"
																	data-toggle="tip"
																	data-original-title="Click work Order Info"> <c:out
																			value="WO-${Issues.ISSUES_WORKORDER_NUMBER}" />

																</a></td>
															</tr>
															<tr class="row">
																<th class="key">Created WorkOrder Date :</th>
																<td class="value"><c:out
																		value="${Issues.ISSUES_WORKORDER_DATE}" /></td>
															</tr>
														</c:if>
														<c:if test="${!empty breakDownDetails}">
															<tr class="row">
																<th class="key">Trip Number :</th>
																<td class="value"><c:out
																		value="${breakDownDetails.tripNumber}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Is Vehicle Replaced :</th>
																<td class="value"><c:out
																		value="${breakDownDetails.vehicleReplacedStr}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Replaced With Vehicle :</th>
																<td class="value"><c:out
																		value="${breakDownDetails.vehicleNumber}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Replaced Location :</th>
																<td class="value"><c:out
																		value="${breakDownDetails.breakDownLocation}" /></td>
															</tr>
															<tr class="row">
																<th class="key">IS Trip Cancelled :</th>
																<td class="value"><c:out
																		value="${breakDownDetails.tripCancelledStr}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Cancelled KM :</th>
																<td class="value"><c:out
																		value="${breakDownDetails.cancelledKM}" /></td>
															</tr>
														</c:if>
													</c:if>
												</tbody>
											</table>
										</div>
									</div>
									<c:if test="${configuration.issueAnalysis}">
									<c:if test="${Issues.ISSUES_TYPE_ID == 6}">
										<sec:authorize access="hasAuthority('ADD_ISSUES')">
											<div class="box box-success">
												<div class="box-header">
													<h3 class="box-title">Issues Analysis</h3>
													<sec:authorize access="hasAuthority('ADD_ISSUES')">

														<c:if test="${Issues.ISSUES_STATUS_ID != 2}">
															<a class="btn btn-success "
																href="<c:url value="/addIssueAnalysis?issueId=${Issues.ISSUES_ID_ENCRYPT}"/>">
																Analysis </a>
														</c:if>

													</sec:authorize>
												</div>
												<div class="box-body no-padding">
													<table class="table table-striped">
														<tbody>
															<tr class="row">
																<th class="key">Reason :</th>
																<td class="value"><span id="showReason"></span></td>
															</tr>
															<tr class="row">
																<th class="key">Avoidable :</th>
																<td class="value"><span id=showIsAvoidable></span></td>
															</tr>
															<tr class="row">
																<th class="key">Temporary Solution :</th>
																<td class="value"><span id="showTempSolution"></span></td>
															</tr>
															<tr class="row">
																<th class="key">Permanent Solution :</th>
																<td class="value"><span id="showFixSolution"></span></td>
															</tr>
															<tr class="row">
																<th class="key">Future Plan :</th>
																<td class="value"><span id="showFuturePlan"></span></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</sec:authorize>
									</c:if>
									</c:if>
								</div>
							</c:if>
							

							<div class="col-md-5 col-sm-5 col-xs-12">
								<c:if test="${!empty Isstask}">
									<div class="box box-success">
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<c:forEach items="${Isstask}" var="Isstask">
														<tr class="row">
															<td><blockquote>
																	<p>
																		<span class="media-heading"><c:choose>
																				<c:when
																					test="${Isstask.ISSUES_TASK_STATUS == 'OPEN'}">
																					<small class="label label-info"><c:out
																							value="${Isstask.ISSUES_TASK_STATUS}" /></small>
																				</c:when>

																				<c:when
																					test="${Isstask.ISSUES_TASK_STATUS == 'REJECT'}">
																					<small class="label label-danger"><c:out
																							value="${Isstask.ISSUES_TASK_STATUS}" /></small>
																				</c:when>
																				<c:when
																					test="${Isstask.ISSUES_TASK_STATUS == 'RESOLVED'}">
																					<small class="label label-warning"><c:out
																							value="${Isstask.ISSUES_TASK_STATUS}" /></small>
																				</c:when>
																				<c:when
																					test="${Isstask.ISSUES_TASK_STATUS == 'CLOSED'}">
																					<small class="label label-success"><c:out
																							value="${Isstask.ISSUES_TASK_STATUS}" /></small>
																				</c:when>
																				<c:otherwise>
																					<small class="label label-default"><c:out
																							value="${Isstask.ISSUES_TASK_STATUS}" /></small>
																				</c:otherwise>
																			</c:choose> To <c:choose>
																				<c:when
																					test="${Isstask.ISSUES_CHANGE_STATUS == 'CLOSED'}">
																					<small class="label label-success"><c:out
																							value="${Isstask.ISSUES_CHANGE_STATUS}" /></small>
																				</c:when>
																				<c:when
																					test="${Isstask.ISSUES_CHANGE_STATUS == 'OPEN'}">
																					<small class="label label-info"><c:out
																							value="${Isstask.ISSUES_CHANGE_STATUS}" /></small>
																				</c:when>

																				<c:when
																					test="${Isstask.ISSUES_CHANGE_STATUS == 'REJECT'}">
																					<small class="label label-danger"><c:out
																							value="${Isstask.ISSUES_CHANGE_STATUS}" /></small>
																				</c:when>
																				<c:when
																					test="${Isstask.ISSUES_CHANGE_STATUS == 'RESOLVED'}">
																					<small class="label label-warning"><c:out
																							value="${Isstask.ISSUES_CHANGE_STATUS}" /></small>
																				</c:when>

																				<c:otherwise>
																					<small class="label label-default"><c:out
																							value="${Isstask.ISSUES_CHANGE_STATUS}" /></small>
																				</c:otherwise>
																			</c:choose> by ${Isstask.ISSUES_CREATEBY_NAME} on
																			${Isstask.ISSUES_CREATED_DATE} </span>
																	</p>

																	<small><i class="fa fa-tags"></i>
																		${Isstask.ISSUES_REASON}</small>
																</blockquote></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</c:if>

							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<ul class="nav nav-list">
						<li class="active"><a
							href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}">Overview</a></li>
						<li><a data-toggle="modal" data-target="#myModal" >Issues Comment
								<span class="pull-right badge bg-aqua">${Comment_Count}</span>
							</a></li>
						<li><a data-toggle="modal" data-target="#IssuesDocuemnt" >Issues Document 
								<span class="pull-right badge bg-aqua">${Document_Count}</span>
							</a></li>
						<li><a data-toggle="modal" data-target="#IssuesPhoto">Issues
								Photos <span class="pull-right badge bg-aqua">${Photo_Count}</span>
						</a></li>
						<li><c:if test="${Issues.ISSUES_STATUS_ID != 2}">
						<c:if test="${Issues.ISSUES_TYPE_ID == 6}">
							<c:if test="${configuration.issueAnalysis}">
								<a  href="<c:url value="/addIssueAnalysis?issueId=${Issues.ISSUES_ID_ENCRYPT}"/>">
									Analysis </a>
							</c:if>
						</c:if>
						</c:if></li>
						
						<c:if test="${configuration.tyreAssginFromIssue}">
							<li>
								<a onclick="showAssignTyre();" > Assign Tyre </a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			
			<div class="row">
					<div class="main-body">
						<div class="main-body">

							<div class="panel panel-default">
								<div class="table-responsive">
									<h2 class="panel-title">Comments</h2>
								</div>

							</div>
						</div>
						<div class="row">

							<ul class="timeline">

								<c:if test="${!empty IssuesComment}">
									<c:forEach items="${IssuesComment}" var="IssuesComment">

										<!-- timeline time label -->
										<li class="time-label"><span class="bg-red">
												${IssuesComment.CREATED_DATE} </span></li>
										<li><i class="fa fa-comments bg-yellow"></i>
											<div class="timeline-item">
												<span class="time"><i class="fa fa-clock-o"></i>
													${IssuesComment.CREATED_DATE_DIFFERENT}</span>
												<h3 class="timeline-header">
												
												<c:choose>
												<c:when test="${IssuesComment.assignee != null && IssuesComment.assignee > 0 }">
													 Confirmed with 
													<a data-toggle="tip"
														data-original-title="Assignee Name"><i
														class="fa fa-user"></i> <c:out
															value="${IssuesComment.assigneeName}" /></a> &amp;
															<a data-toggle="tip"
														data-original-title="Driver"><i
														class="fa fa-user"></i> <c:out
															value="${IssuesComment.driverName} - ${IssuesComment.driverFatherName}" /></a>  
												
												</c:when>
												<c:otherwise>
												<a data-toggle="tip"
														data-original-title="${IssuesComment.CREATED_EMAIL}"><i
														class="fa fa-user"></i> <c:out
															value="${IssuesComment.CREATEDBY}" /></a> commented on 
												</c:otherwise>
												
												
												</c:choose>
															<b><c:out
															value="${IssuesComment.ISSUE_TITLE}" /></b>
												</h3>
												<div class="timeline-body">

													<c:out value="${IssuesComment.ISSUE_COMMENT}" />
												</div>
												<div class="timeline-footer">
													<sec:authorize access="hasAuthority('DELETE_ISSUES')">
														<a class="btn btn-info btn-flat btn-xs"
															href="deleteIssuesComment.in?Id=${IssuesComment.ISSUES_ID_ENCRYPT}&CID=${IssuesComment.ISSUE_COMMENTID_ENCRYPT}">
															<i class="fa fa-trash"> Delete</i>
														</a>
													</sec:authorize>
												</div>
											</div></li>

									</c:forEach>
								</c:if>

								<c:if test="${empty IssuesComment}">
									<div class="main-body">
										<p class="lead text-muted text-center t-padded">
											<spring:message code="label.master.noresilts" />
										</p>

									</div>
								</c:if>
							</ul>


						</div>
					</div>
				</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${Issues.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${Issues.CREATED_DATE}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${Issues.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${Issues.LASTUPDATED_DATE}" /></small>
			</div>
		</sec:authorize>
		
		
		<div class="modal fade" id="dismountModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Close</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col col-sm-12 col-md-5" >
									<label class="has-float-label"> <span >Dismount Date <abbr title="required">*</abbr></span> </label>
								    <div class="input-group input-append date" id="StartDate">
										<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
											id="dismountDate" required="required"
											data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
											<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
									</div>
								</div>
								<div class="col col-sm-12 col-md-5">
									 <label class="has-float-label"> Dismount Odometer</label>
						    		<input type="text" class="form-control browser-default custom-select noBackGround" id="dismountOdometer"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" >
								</div>
							</div>
							<div class="row">
								<div class="col col-sm-12 col-md-5">
									 <label class="has-float-label"> Tyre Move To</label>
						    		<select id="oldTyreMoveId" name="oldTyreMoveName"class="browser-default custom-select">
									<option value="0">Please Select Tyre Move To</option>
									<option value="1">Remould</option>
									<option value="2">Repair</option>
									<option value="3">Blast</option>
									<option value="4">Scrap</option>
									<option value="5">WorkShop</option>
							</select> 
								</div>
								<div class="col col-sm-12 col-md-5">
									 <label class="has-float-label"> Tyre Gauge</label>
						    		 <input type="text" class="form-control browser-default custom-select noBackGround" id="tyreGuageVal" onkeypress="return isNumberKeyWithDecimal(event,this.id);"  >
								</div>
							</div>
							<div class="row">
								<div class="col col-sm-12 col-md-5">
									 <label class="has-float-label"> Remark</label>
						    		<input type="text" class="form-control browser-default custom-select noBackGround" id="remark"  >
								</div>
							
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								id="js-upload-submit" value="Save Comment" onclick="removeTyre();">Dismount</button>
							<button type="button" class="btn btn-link" data-dismiss="modal">Cancel</button>
						</div>
				</div>
			</div>
		</div>
		
		
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/showIssue.js" />"></script>
			<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
		
		
</div>