<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addJobSubType.in"/>">New Job Sub Type</a> / <span
						id="NewJobType">Edit Job Sub Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addJobSubType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty JobSubType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddJob">Edit Job Sub Type</h1>
								</div>
								<form id="formEditJobType"
									action="<c:url value="/updateJobSubType.in"/>" method="post"
									enctype="multipart/form-data" name="formEditJobType"
									role="form" class="form-horizontal">
									<div class="panel-body">
										<input name="Job_Subid" value="${JobSubType.job_Subid}"
											type="hidden" />

										<div class="row1" id="grpjobType" class="form-group">
											<label class="L-size control-label" for="jobType">Job
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text selectType select2" name="Job_TypeId"
													style="width: 100%;" id="jobType">
													<option value="${JobSubType.job_TypeId}"><c:out
															value="${JobSubType.job_Type}" /></option>
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
												<input type="text" class="form-text" id="jobRotName"
													value="${JobSubType.job_ROT}" name="Job_ROT"
													maxlength="150" placeholder="Enter ROT" /> <span
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
													value="${JobSubType.job_ROT_number}"
													placeholder="Enter ROT Number" /> <span
													id="jobRotCodeIcon" class=""></span>
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
													name="Job_ROT_hour" maxlength="2"
													value="${JobSubType.job_ROT_hour}"
													onkeypress="return isNumberKeyWithDecimal(event,id)"
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
													name="Job_ROT_amount" maxlength="6"
													value="${JobSubType.job_ROT_amount}"
													placeholder="Enter Total Amount" /><span
													id="jobRotAmountIcon" class=""></span>
												<div id="jobRotAmountErrorMsg" class="text-danger"></div>
											</div>
										</div>
									 	</c:if> 

										<c:if test="${configuration.showRotInServiceReminder}">
											<sec:authorize
												access="hasAuthority('VIEW_ROT_FOR_SERVICE_REMINDER')">
												<div class="row">
													<div class="col-xs-3"></div>
													<label class="checkbox-inline"> <input
														type="checkbox" name="ROT_Service_Reminder"
														<c:if test="${JobSubType.ROT_Service_Reminder eq true}">checked=checked</c:if>
														id="ROT_Service_Reminder" autocomplete="off" /> Do you
														need this ROT in Service Reminder
													</label>
												</div>
											</sec:authorize>
										</c:if>

										<div class="row">
											<label class="L-size control-label" for="Job_theft"></label>
											<div class="I-size">
												<fieldset class="form-actions">
													<button type="submit" class="btn btn-success" onclick="return validateEditSubType()">Update
														Job Sub Type</button>
													 <a class="btn btn-link"
														href="addJobSubType/1.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty JobSubType}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="../vehicle/masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
</div>