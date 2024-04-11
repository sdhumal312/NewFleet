<%@ include file="taglib.jsp"%>
<%-- <%@ taglib prefix="sec"%> --%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newUserList/1.in"/>"> User Info</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_USER')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addUser" />"> <span class="fa fa-plus">
								Create User</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.message != null}">
			<h4 class="alert alert-success">
				<spring:message code="message.regSucc"></spring:message>
			</h4>
		</c:if>
		<script type="text/javascript"
			src="<c:url value="/resources/js/status.validate.js" />"/></script>
		<c:if test="${param.message != null}">
			<div class="alert alert-info">${param.message}</div>
		</c:if>
		<c:if test="${param.changetoInActive eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Change to InActive Successfully
			</div>
		</c:if>
		<c:if test="${param.changetoActive eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Change to Active Successfully
			</div>
		</c:if>
		<c:if test="${param.updateUserProfile eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleStatus eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<spring:message code="message.master.AlreadyVehicleStatus" />

			</div>
		</c:if>
		<c:if test="${param.emptyVehicleStatus eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<h4>Warning!</h4>
				<p>
					The page no data to Show.., Please Don't Change any URL ID or
					Number.. <br> Don't Enter any Extra worlds in URL..
				</p>
			</div>
		</c:if>
		<c:if test="${param.success eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This User Created Successfully.
			</div>
		</c:if>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_USER')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_USER')">
				<div class="row">
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-green"><i class="fa fa-tint"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Total User</span> <span
									class="info-box-number">${UserCount}</span>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-6 col-xs-12">
						<div class="info-box">
							<div class="info-box-center">
								<span class="info-box-text">Search User list</span>
								<form action="<c:url value="/searchUserList.in"/>" method="post">
									<div class="input-group">
										<input class="form-text" 
											name="UserName" type="text" required="required"
											placeholder="User Name, Email"> <span
											class="input-group-btn">
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
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<table id="SubTypeTable" class="table table-hover table-bordered">
								<thead>
									<tr>
										<th>Login Id</th>
										<th class="fit ar">Name</th>
										<th>Company</th>
										<th class="fit">Number</th>
										<th class="fit">Status</th>
										<th class="fit">Last Login</th>
										
										<th class="fit">Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty userprofile}">
										<c:forEach items="${userprofile}" var="userprofile">
											<tr>
									<c:if test="${userprofile.user_email ne 'admin' || logedInUser eq 'admin'}">											
											<td> <c:out value="${userprofile.user_email}" /> <br>										
													
													
													 <c:if test="${userprofile.user_email ne 'admin' || logedInUser eq 'admin'}">
													
													 	<sec:authorize access="hasAuthority('EDIT_USER')">
													  		 
															<a
																href="<c:url value="/resetUserPassword?id=${userprofile.user_id}"/>">Reset
																Password</a>
														</sec:authorize>
													</c:if>								
																														
											</td>
												<!-- Safe Yogi  End Perfectly-->	
															
												<td class="fit ar"><c:out
														value="${userprofile.firstName}  " /> <c:out
														value="${userprofile.lastName}" /> <br> <span>
														${userprofile.sex}</span></td>
												<td><c:out value="${userprofile.company_name}" /><br>
													<c:out value="${userprofile.department_name}" /><br>
													<c:out value="${userprofile.branch_name}" /></td>
												<td class="fit"><c:out
														value="${userprofile.home_number}" /><br> <c:out
														value="${userprofile.mobile_number}" /><br> <c:out
														value="${userprofile.work_number}" /></td>
												<td class="fit"><sec:authorize
														access="hasAuthority('ACTIVE_USER')">
														<c:choose>
															<c:when test="${userprofile.markForDelete}">
																
																<span class="label label-danger"><c:out
																		value="In-Active" /></span>
																<br>
																<a
																	href="<c:url value="/adminActive?id=${userprofile.user_id}"/>"
																	style="color: #00a65a;">Change to Active</a>
															</c:when>
															<c:otherwise>
															<span class="label label-success"><c:out
																		value="Active" /></span>
																<br>
																<a
																	href="<c:url value="/adminInActive?id=${userprofile.user_id}"/>"
																	style="color: red;">Change to In-Active</a>
															</c:otherwise>
														</c:choose>
													</sec:authorize></td>
													
													
													<td><c:out value="${userprofile.lastLoginDateStr}" /><br> <c:out
														value="${userprofile.lastLoginIP}" /></td>
													
												
												<td class="fit">
												<%-- <c:if test="${userprofile.user_email ne 'admin' || logedInUser eq 'admin'}"> <!-- Yogi --> --%>
													<div class="btn-group">
														<a class="btn btn-Link dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-cog"></span> <span class="caret"></span>
														</a>
															
														
														<ul class="dropdown-menu pull-right">
															<li><sec:authorize access="hasAuthority('EDIT_USER')">
																	<a
																		href="<c:url value="/editUserProfile?id=${userprofile.user_id}"/>">
																		<span class="fa fa-pencil"></span> Edit
																	</a>
																</sec:authorize></li>
															<li><sec:authorize access="hasAuthority('VIEW_USER')">
																	<a
																		href="<c:url value="/showUserProfile?id=${userprofile.user_id}"/>">
																		<span class="fa fa-user"></span> Show Profile
																	</a>
																</sec:authorize></li>
														</ul>
														
														
													</div>
													
												</td>
												
											</tr>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${empty userprofile}">
										<tr>
											<td colspan="8">
												<div class="callout callout-info">
													<h4>Empty user</h4>
													<p>Click Create User Button.</p>
												</div>

											</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<c:url var="firstUrl" value="/newUserList/1" />
				<c:url var="lastUrl"
					value="/newUserList/${deploymentLog.totalPages}" />
				<c:url var="prevUrl" value="/newUserList/${currentIndex - 1}" />
				<c:url var="nextUrl" value="/newUserList/${currentIndex + 1}" />
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
							<c:url var="pageUrl" value="/newUserList/${i}" />
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
	</section>
</div>