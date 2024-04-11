<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="ViewServiceReminderList.in"> Service
						Reminder</a> / Create Multiple Service Reminder
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="ViewServiceReminderList.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					
						<div class="form-horizontal ">

							<fieldset>
								<legend style="text-align: center;">Service Program Settings</legend><br/>
								<div class="box">
									<div class="box-body">
										<div class="form-horizontal ">
											
											<div class="row1" id="grpvehicleNumber" class="form-group">
												<label class="L-size control-label"
													for="ServiceSelectVehicle">Name<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" id="programName"
														name="programName" style="width: 100%;"
														placeholder="Please Enter Programme Name " /> 
												</div>
											</div>
											
										 <div class="row1">
											<label class="L-size control-label" for="issue_description">Initial
												Notes</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="initial_note" name="initial_note"
																rows="3" maxlength="250"></textarea>
											</div>
										</div>
										
										
										</div>
									</div>
								</div>
							</fieldset>

							<div class="panel-footer">
								<div class="L-size"></div>
								<div class="I-size">
									<button type="submit" class="btn btn-success">Create
										Service Program</button>
									<a class="btn btn-link"
										href="ViewServiceReminderList.in">Cancel</a>
								</div>
							</div>
						</div>
					<br> <br>
				</div>
			</div>
		</sec:authorize>
		<script>
			$(function() {
				$("#to").select2();

				
			});
		</script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/SeviceReminderValidate.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	</section>
</div>