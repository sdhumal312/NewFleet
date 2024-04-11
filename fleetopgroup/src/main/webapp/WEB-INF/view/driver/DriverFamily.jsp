<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <span>Driver
						family</span>
				</div>
				<div class="pull-right">
					<c:if test="${driver.driverStatusId != 2 && driver.driverStatusId != 6}">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
					</c:if>
					<c:if test="${driver.driverStatusId == 2}">
					<sec:authorize access="hasAuthority('INACTIVE_DRIVER_EDIT')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
						</c:if>
						<c:if test="${driver.driverStatusId != 6 }">
					<sec:authorize access="hasAuthority('ADD_DRIVER')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#DriverFamily"> <i class="fa fa-plus"></i> Add
							Family Details
						</a>
					</sec:authorize>
					</c:if>
					<sec:authorize access="hasAuthority('PRINT_DRIVER')">
						<a href="PrintDriverFamily?id=${driver.driver_id}" target="_blank"
							class="btn btn-default btn-sm"><i class="fa fa-print"></i>
							Print</a>
					</sec:authorize>
					
					<a class="btn btn-link"
						href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>

		</div>
	</section>
	<div class="modal fade" id="DriverFamily" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="formPart" action="<c:url value="/saveDriverFamily.in"/>"
					method="post" enctype="multipart/form-data" name="formOwner"
					role="form" class="form-horizontal">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Driver Family Information</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal ">
							<input type="hidden" name="DRIVERID" value="${driver.driver_id}" />

							<div class="row">
								<div class="col-md-4">
									<label class=" control-label" for="ownerSerial">Family
										Member Name: <abbr title="required">*</abbr>
									</label>
								</div>
								<div class="col-md-2">
									<label class=" control-label" for="OwnerPhone">Gender:
										<abbr title="required">*</abbr>
									</label>
								</div>
								<div class="col-md-1">
									<label class=" control-label" for="OwnerPhone">Age: <abbr
										title="required">*</abbr>
									</label>
								</div>
								<div class="col-md-2">
									<label class=" control-label" for="OwnerName">Family
										Relation ship: <abbr title="required">*</abbr>
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4">
									<input class="form-text" id="OwnerSerial" name="DF_NAME"
										type="text" maxlength="150" required="required">
								</div>
								<div class="col-md-2">
									<select class="form-text " id="OwnerName" name="DF_SEX"
										required="required">
										<option value="1">MALE</option>
										<option value="2">FEMALE</option>
									</select>
								</div>
								<div class="col-md-1">
									<input class="form-text" id="DF_AGE" onkeyup="validateAge()" name="DF_AGE" onkeypress="return isNumberKey(event,this);"
										type="number" required="required">
								</div>
								<div class="col-md-2">
									<select class="form-text " id="OwnerName"
										name="DF_RELATIONSHIP" required="required">
										<option value="1">FATHER</option>
										<option value="2">MOTHER</option>
										<option value="3">SON</option>
										<option value="4">DAUGHTER</option>
										<option value="5">BROTHER</option>
										<option value="6">SISTER</option>
										<option value="7">HUSBAND</option>
										<option value="8">WIFE</option>
									</select>
								</div>
								<div class="input_fields_wrap">
									<button class="add_field_button btn btn-success">
										<i class="fa fa-plus"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">

						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Create
							Driver Family Info">
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.success  eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Family Details created Successfully .
					</div>
				</c:if>
				
				<c:if test="${param.delete eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Family Details  Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Family Details Already Created.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Family Details  Not Created.
					</div>
				</c:if>

				<div class="row">
					<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<div class="main-body">
							<c:if test="${!empty DFamily}">
								<div class="box">
									<div class="box-header">
										<div class="pull-right">
											<div id="langSelect"></div>
										</div>
									</div>
									<!-- /.box-header -->
									<div class="box-body">
										<table id="DriverReminderTable"
											class="table table-hover table-striped">
											<thead>
												<tr>
													<th>ID</th>
													<th>Name</th>
													<th>Relation Ship</th>
													<th>Gender</th>
													<th>Age</th>
													<th class="actions">Actions</th>
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												<c:forEach items="${DFamily}" var="DFamily">
													<tr data-object-id="" class="ng-scope">
														<td class="fit">
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<td class="icon"><c:out value="${DFamily.DF_NAME}" /></td>
														<td class="icon"><c:out
																value="${DFamily.DF_RELATIONSHIP}" /></td>
														<td class="icon"><i></i> <c:out
																value="${DFamily.DF_SEX}" /></td>
														<td class="icon"><c:out value="${DFamily.DF_AGE}" /></td>

														<td>
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
																			<a
																				href="deleteDriverFamily.in?DFID=${DFamily.DFID}&DID=${DFamily.DRIVERID}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<i class="fa fa-trash"></i> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${empty DFamily}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</sec:authorize>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="DriverSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
	<script type="text/javascript">
	$(document).ready(function() {
	    var e = 25,
	        t = $(".input_fields_wrap"),
	        n = $(".add_field_button"),
	        a = 1;
	    $(n).click(function(n) {
	        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row"><div class="col-md-4"><input class="form-text" id="OwnerSerial" name="DF_NAME" type="text" maxlength="150" required="required"> </div><div class="col-md-2"><select class="form-text " id="OwnerName" name="DF_SEX" required="required"><option value="1">MALE</option><option value="2">FEMALE</option></select></div><div class="col-md-1"><input class="form-text" id="DF_AGE" name="DF_AGE" type="number"  required="required"></div><div class="col-md-2"><select class="form-text " id="OwnerName" name="DF_RELATIONSHIP"	required="required"><option value="1">FATHER</option><option value="2">MOTHER</option><option value="3">SON</option><option value="4">DAUGHTER</option><option value="5">BROTHER</option><option value="6">SISTER</option><option value="7">HUSBAND</option><option value="8">WIFE</option></select></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'))
	    }), $(t).on("click", ".remove_field", function(e) {
	        e.preventDefault(), $(this).parent("div").remove(), a--
	    })
	});
	</script>
	<script>
	function validateAge(){
		var driverAge = Number($("#DF_AGE").val());
		if(driverAge > 100){
			showMessage('info','Age Shoud Not Be Greater Than 100');
			$("#DF_AGE").val('');
		}
	}
	</script>
	
</div> 