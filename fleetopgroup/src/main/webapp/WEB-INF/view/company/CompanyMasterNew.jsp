<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/masterCompany/1.in"/>">Company</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/masterCompanyCreate"/>"
							data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Company..">
							<span class="fa fa-plus" id="AddVehicle"> Create New
								Company Setup</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
			<div class="row">
				<%-- <div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-bus"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectStatus} company</span>
							<input type="hidden" value="${Status}" id="statues">
							<span class="info-box-number">${VehicleCount}</span>
						</div>
					</div>
				</div> --%>

				<div class="col-md-5 col-sm-5 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Company</span>
							<form action="<c:url value="/searchVehicleAll.in"/>"
								method="post">
								<div class="input-group">
									<input name="userID" type="hidden" value="${user.id}"
										required="required" /> <input class="form-text"
										placeholder="Company Name" id="vehicle_registrationNumber"
										name="searchvehicle" type="text" data-mask=""
										required="required"> <span class="input-group-btn">
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
			<c:if test="${param.success eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This User Created Successfully.
				</div>
			</c:if>
			<c:if test="${param.successpassword eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				${message} This User Password Update Successfully.
			</div>
		</c:if>

			<div class="row">
				<div class="main-body">

					<sec:authorize access="!hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table class="table table-hover table-bordered">
										<thead>
											<tr>
												<th>Company Name</th>
												<th>Company Code</th>
												<th class="fit ar">City/state</th>
												<th class="fit ar">email</th>
												<th class="fit ar">Mobile</th>
												<th class="fit ar">Status</th>
												<th class="fit">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty company}">
												<c:forEach items="${company}" var="company">
													<tr>
														<td><a
															href="<c:url value="/showMasterCompany?CID=${company.company_id_encode}"/>"
															data-toggle="tip"
															data-original-title="Click company Details"><c:out
																	value="${company.company_name}" /></a></td>
														<td class="fit ar"><c:out
																value="${company.companyCode}" /></td>
														<td class="fit ar"><c:out
																value="${company.company_city}  / " />
															<c:out value="${company.company_state}" /></td>
														<td class="fit ar"><c:out
																value="${company.company_website}" /></td>
														<td class="fit ar"><c:out
																value="${company.company_email}" /></td>
														<td class="fit ar"><c:out
																value="${company.company_mobilenumber}" /></td>
														<td class="fit ar"><c:out
																value="${company.company_status}" /></td>
														<td class="fit"><div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_VEHICLE')">
																			<a
																				href="<c:url value="/editMasterCompany?CID=${company.company_id_encode}"/>">
																				<span class="fa fa-pencil"></span> Edit Company
																			</a>
																		</sec:authorize></li>
																</ul>
															</div></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<c:url var="firstUrl" value="/masterCompany/1" />
						<c:url var="lastUrl"
							value="/masterCompany/${deploymentLog.totalPages}" />
						<c:url var="prevUrl" value="/masterCompany/${currentIndex - 1}" />
						<c:url var="nextUrl" value="/masterCompany/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/masterCompany/${i}" />
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
					</sec:authorize>

				</div>
			</div>
		</sec:authorize>
		<div style="height: 100px;"></div>
	</section>

</div>