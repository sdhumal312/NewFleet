<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a>
						User Profile</a>
				</div>
				<div class="pull-right">

					<a class="btn btn-success" data-toggle="modal"
						data-target="#myModalPhoto"> <i class="fa fa-plus"></i> Add
						Photo
					</a> <a class="btn btn-link" href="open.html">Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveUserPhoto eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Photo Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.updateUserPhoto eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Photo Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.emptyDocument eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Photo is empty.
			</div>
		</c:if>
		<div class="row">
			<div class="col-md-3 col-sm-4 col-xs-12">
				<div class="box">
					<div class="box-body">

						<div class="col-md-9 col-sm-10 col-xs-12">
							<c:choose>
								<c:when test="${userprofile.photo_id != null}">
									<a
										href="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
										class="zoom" data-title="User Profile"
										data-footer="${userprofile.firstName}" data-type="image"
										data-toggle="lightbox"> <img
										src="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
										class="img-circle"
										alt="<c:out value="${userprofile.firstName} " />" width="200"
										height="200" align="left" />
									</a>
								</c:when>
								<c:otherwise>
									<img src="resources/images/User-Icon.png" alt="User Profile"
										class="img-circle img-responsive" width="200" height="200"
										align="left" />
								</c:otherwise>
							</c:choose>


						</div>
						<div class="col-md-9 col-sm-9 col-xs-12">

							<h3>
								<c:out value="${userprofile.firstName} " />
								<c:out value="${userprofile.lastName}" />
							</h3>
							<small><cite
								title="${userprofile.city},  ${userprofile.state}, ${userprofile.country}">${userprofile.city}
									${userprofile.state}, ${userprofile.country} <i
									class="fa fa-map-marker"> </i>
							</cite></small>
							<p>
								<i class="fa fa-envelope"></i>
								<c:out value=" ${userprofile.user_email}"></c:out>
								<br /> <i class="fa fa-globe"></i><a> <c:out
										value=" ${userprofile.personal_email}"></c:out></a> <br /> <i
									class="fa fa-gift"></i>
								<c:out value=" ${userprofile.dateofbirth}"></c:out>
							</p>
						</div>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<fieldset>
								<legend>Company </legend>
								<br>
								<h5>
									Company :<a ><c:out
											value=" ${userprofile.company_name}" /></a>
								</h5>
								<h5>
									Branch :<a ><c:out
											value=" ${userprofile.branch_name}" /></a>
								</h5>
								<h5>
									Depart. :
									<c:out value=" ${userprofile.department_name}" />
								</h5>
								<h5>
									Design. :
									<c:out value="${userprofile.designation}" />
								</h5>

							</fieldset>
						</div>
						<div class="col-md-9 col-sm-9 col-xs-12">
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="box">
					<div class="box-body">
						<fieldset>
							<legend>Personal Details </legend>
							<table class="table">
								<tbody>
									<tr class="row">
										<th class="">Name :</th>
										<td class="" style="width: 2432452px;"><c:out
												value="${userprofile.firstName} " /> <c:out
												value="${userprofile.lastName}" /></td>
									</tr>
									<tr class="row">
										<th class="">Date of Birth :</th>
										<td class=""><c:out value="${userprofile.dateofbirth}" />
											<div class="text-muted">
												<!-- <small>3 years old</small> -->
											</div></td>
									</tr>
									<tr class="row">
										<th class="">Sex :</th>
										<td class="value"><c:out value="${userprofile.sex}" /></td>
									</tr>
									<tr class="row">
										<th class="">Personal Email :</th>
										<td class="value"><c:out
												value="${userprofile.personal_email}" /></td>
									</tr>
									<tr class="row">
										<th class="">Mobile Number :</th>
										<td class="value"><c:out
												value="${userprofile.home_number}" /><br> <c:out
												value="${userprofile.mobile_number}" /><br> <c:out
												value="${userprofile.work_number}" /></td>
									</tr>
									<tr class="row">
										<th class="">Address :</th>
										<td class="value"><address>
												<c:out value="${userprofile.address_line1}" />
												,<br>
												<c:out value="${userprofile.city} " />
												,
												<c:out value="${userprofile.state}" />
												,<br>
												<c:out value="${userprofile.country}" />
												, Pin :
												<c:out value="${userprofile.pincode}" />
												.
											</address></td>
									</tr>
									<tr class="row"></tr>
									<tr class="row">
										<th class="">Emergency Person :</th>
										<td class="value"><c:out
												value="${userprofile.emergency_person}" /></td>
									</tr>
									<tr class="row">
										<th class="">Emergency Number :</th>
										<td class="value"><c:out
												value="${userprofile.emergency_number}" /></td>
									</tr>
							</table>
						</fieldset>
						<fieldset style="margin: 0px 0;">
							<legend>Company Details </legend>
							<table class="table">
								<tbody>
									<tr class="row">
										<th class="">Company Email :</th>
										<td class=""><c:out value="${userprofile.user_email} " /></td>
									</tr>
									<tr class="row">
										<th class="">Employee ID :</th>
										<td class=""><c:out value="${userprofile.employes_id}" />
											<div class="text-muted">
												<!-- <small>3 years old</small> -->
											</div></td>
									</tr>
									<tr class="row">
										<th class="">Working Time :</th>
										<td class="value"><c:out
												value="${userprofile.working_time_from} To " /> <c:out
												value="${userprofile.working_time_to}" /></td>
									</tr>
									<tr class="row">
										<th class="">ESI Number :</th>
										<td class="value"><c:out
												value="${userprofile.esi_number}" /></td>
									</tr>
									<tr class="row">
										<th class="">PF Number :</th>
										<td class="value"><c:out value="${userprofile.pf_number}" /></td>
									</tr>
									<tr class="row">
										<th class="">Insurance Number :</th>
										<td class="value"><c:out
												value="${userprofile.insurance_number}" /></td>
									</tr>
							</table>
						</fieldset>
					</div>
				</div>
			</div>
			<!-- Modal -->
			<div class="modal fade" id="myModalPhoto" role="dialog">

				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<form method="post" action="uploadUserProfilePhoto.in"
							enctype="multipart/form-data">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Upload User Photo</h4>
							</div>
							<div class="modal-body">
								<div class="form-horizontal">
									<div class="row1">
										<div class="L-size">
											<input type="hidden" name="userprofile_id"
												value="${userprofile.userprofile_id}" /> <input
												type="hidden" name="user_id" value="${userprofile.user_id}" />
											<label class="col-md-3">Title/Photo Name</label>
										</div>
										<div class="I-size">
											<input type="text" name="photoname" class="form-text"
												maxlength="25" onkeypress="return IsDriverPhotoName(event);"
												required="required" ondrop="return false;"> <label
												class="error" id="errorPhotoName" style="display: none">
											</label>
										</div>
									</div>
									<div class="row">
										<div class="L-size"></div>
										<label class="L-size"></label>
										<div class="I-size">
											<input type="file" name="file" id="file" accept="image/*"
												required="required"></input>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									id="js-upload-submit" value="Add Document">Upload
									Photo</button>
								<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>