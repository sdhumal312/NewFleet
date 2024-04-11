
<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html" />"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addJobSubType.in"/>">New Job Sub Type</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_JOB_SUB_TYPE')">
						<a class="btn btn-default" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/6.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default" data-toggle="modal"
							data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addJobSubtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobSubType"> Create Job
								SubType</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveJobSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Sub Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateJobSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Sub Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteJobSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Sub Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyJobSubType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Sub Type Already Exists.
			</div>
		</c:if>
		<c:if test="${alreadyJobSubType}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Sub Type Already Exists.
			</div>
		</c:if>
		<c:if test="${importSave}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Imported Successfully ${CountSuccess} Job Sub Type Data.
			</div>
		</c:if>
		<c:if test="${importSaveError}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Part can not be created with this Import CSV File. <br /> Do not
				Import empty CSV File for ROT Name, ROT Number, JobType, is Required
			</div>
		</c:if>
		<c:if test="${importSaveAlreadyError}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<c:if test="${!empty Duplicate}">
					<c:forEach items="${Duplicate}" var="Duplicate">
				
				      ${CountDuplicate} Duplicate entry Please Check First <c:out
							value="${Duplicate}" /> ROT Name &amp; ROT Number
				
				</c:forEach>
				</c:if>
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addImport" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="post" action="<c:url value="/importJobSubType.in" />"
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
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
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
																		}, 2500);
														var $btn = $(this)
																.button(
																		'loading')
														// business logic...

														$btn.button('reset')
													})
								</script>

							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="addJobSubtypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="formJobType" action="<c:url value="/saveJobSubType.in"/>"
						method="post" enctype="multipart/form-data" name="formJobType"
						role="form" class="form-horizontal">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobSubType">Job Sub Type</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
								<div class="row1" id="grpjobType" class="form-group">
									<label class="L-size control-label" for="jobType">Job
										Type :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<select class="form-text selectType select2" name="Job_TypeId"
											style="width: 100%;" id="jobType">
											<option value="">Select Job type</option>
											<c:forEach items="${JobType}" var="JobType">
												<option value="${JobType.job_id}">${JobType.job_Type}
												</option>
											</c:forEach>
										</select><span id="jobTypeIcon" class=""></span>
										<div id="jobTypeErrorMsg" class="text-danger"></div>
										<label id="errorReType" class="error"></label>
									</div>
								</div>
								<div class="row1" id="grpjobRotName" class="form-group">
									<label class="L-size control-label" for="jobRotName">ROT
										Name :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input type="text" class="form-text" id="jobRot"
											name="Job_ROT" maxlength="150" placeholder="Enter ROT" /> <span
											id="jobRotNameIcon" class=""></span>
										<div id="jobRotNameErrorMsg" class="text-danger"></div>
									</div>
								</div>
								
								<c:if test="${configuration.showROTCode}">
								<div class="row1" id="grpjobRotCode" class="form-group">
									<label class="L-size control-label" for="jobRotCode">ROT
										Code :</label>
									<div class="I-size">
										<input type="text" class="form-text" id="jobRotCode"
											name="Job_ROT_number" maxlength="30"
											placeholder="Enter ROT Number" /> <span id="jobRotCodeIcon"
											class=""></span>
										<div id="jobRotCodeErrorMsg" class="text-danger"></div>
									</div>
								</div>
								</c:if>
								<c:if test="${configuration.showNoOfHour}"> 
								<div class="row1" id="grpjobRotHour" class="form-group">
									<label class="L-size control-label" for="jobRotHour">Number
										of Hour :</label>
									<div class="I-size">
										<input type="number" class="form-text" id="jobRotHour"
											name="Job_ROT_hour" maxlength="2" onkeypress="return isNumberKeyWithDecimal(event,id)"
											placeholder="Enter Number of Hour" /> <span
											id="jobRotHourIcon" class=""></span>
										<div id="jobRotHourErrorMsg" class="text-danger"></div>
									</div>
								</div>
								 </c:if> 
							    <c:if test="${configuration.showROTAmount}"> 
								<div class="row1" id="grpjobRotAmount" class="form-group">
									<label class="L-size control-label" for="jobRotAmount">Total
										Amount :</label>
									<div class="I-size">
										<input type="number" class="form-text" id="jobRotAmount"
											name="Job_ROT_amount" maxlength="6" onkeypress="return isNumberKeyWithDecimal(event,id)"
											placeholder="Enter Total Amount" /><span
											id="jobRotAmountIcon" class=""></span>
										<div id="jobRotAmountErrorMsg" class="text-danger"></div>
									</div>
								</div>
							 	</c:if>	 
								
								<c:if test="${configuration.showRotInServiceReminder}">
								<sec:authorize access="hasAuthority('VIEW_ROT_FOR_SERVICE_REMINDER')">
									<div class="row">
											<div class="col-xs-3"></div>
											<label class="checkbox-inline"> <input
													type="checkbox" name="ROT_Service_Reminder" 
													 id="ROT_Service_Reminder" autocomplete="off" />
													Do you need this ROT in Service Reminder
												</label>
									</div>
								</sec:authorize>
								</c:if>
							
								
							</div>
						</div>
						<div class="modal-footer">
							<div class="row1">
								<button type="submit" class="btn btn-primary" onclick="return ValidateSub()">
									<span id="Save">Create Job sub Type</span>
								</button>

								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close">Close</span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">

					<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						<c:if test="${!empty JobSubType}">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="SubTypeTable"
											class="table table-hover table-striped">
											<thead>
												<tr>
													<th id="TypeName" class="icon">Type Name</th>
													<th class="icon">ROT Name</th>
													<c:if test="${configuration.showROTCode}">
														<th class="icon">ROT Code</th>
													</c:if>
													<c:if test="${configuration.showNoOfHour}">
														<th class="icon">ROT Hour</th>
													</c:if>
													<c:if test="${configuration.showROTAmount}">
														<th class="icon">ROT Amount</th>
													</c:if>
													<th id="Actions" class="icon"></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${JobSubType}" var="JobSubType">
													<tr>
														<td><c:out value="${JobSubType.job_Type}" /></td>
														<td><c:out value="${JobSubType.job_ROT}" /></td>
														<c:if test="${configuration.showROTCode}">
															<td><c:out value="${JobSubType.job_ROT_number}" /></td>
														</c:if>
														<c:if test="${configuration.showNoOfHour}">
															<td><c:out value="${JobSubType.job_ROT_hour}" /></td>
														</c:if>
														<c:if test="${configuration.showROTAmount}">
															<td><c:out value="${JobSubType.job_ROT_amount}" /></td>
														</c:if>
														<td>
														  <c:if test="${JobSubType.companyId > 0 || userCompanyId == 4}">
																<div class="btn-group">
																	<a class="btn btn-Link dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-cog"></span> <span class="caret"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('EDIT_PRIVILEGE')">
																				<a
																					href="<c:url value="/editJobSubType.in?Job_Subid=${JobSubType.job_Subid}" />">
																					<span class="fa fa-pencil"></span> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_PRIVILEGE')">
																				<a
																					href="<c:url value="/deleteJobSubType.in?Job_Subid=${JobSubType.job_Subid}" />"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Sub Type ?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
																</div>
														  </c:if>
														</td>
													</tr>
												</c:forEach>

											</tbody>
										</table>

									</div>
								</div>
							</div>
						</c:if>
						<%-- <c:url var="firstUrl" value="/addJobSubType/1" />
						<c:url var="lastUrl"
							value="/addJobSubType/${deploymentLog.totalPages}" />
						<c:url var="prevUrl" value="/addJobSubType/${currentIndex - 1}" />
						<c:url var="nextUrl" value="/addJobSubType/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/addJobSubType/${i}" />
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
						</div> --%>
					</sec:authorize>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypelanguage.js" />"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
</div>