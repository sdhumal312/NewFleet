<%@ include file="taglib.jsp"%>
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
							value="${driver.driver_Lastname}" /></a> / <span>New Driver
						Renewal Reminder</span>
				</div>
				<div class="pull-right">
				<c:if test="${driver.driverStatusId != 6}">
						<c:if test="${driver.driverStatusId != 2}">
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
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<a class="btn btn-success"
							href="addDriverReminder.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-plus"></i> Add Driver Renewal
						</a>
					</sec:authorize>
					</c:if>
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
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.addDriverReminder  eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder created Successfully .
					</div>
				</c:if>
				<c:if test="${param.saveDriverReminderHis eq true}">
					<div class="alert alert-info">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Old Renewal Reminder is Moved to History.
					</div>
				</c:if>
				<c:if test="${param.updateDriverReminder eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder Updated Successfully .
					</div>
				</c:if>
				<c:if test="${param.deleteDriverReminder eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Renewal Reminder Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder Document is Empty.
					</div>
				</c:if>
				<c:if test="${param.alreadyExist eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Driver Renewal Already Exists for selected dates .
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder Not created.
					</div>
				</c:if>

				<div class="row">
					<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<div class="main-body">
							<c:if test="${!empty driverReminder}">
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
													<th>Type</th>
													<th>Number</th>
													<th>Validity_From</th>
													<th>Validity_To</th>
													<th>Download</th>
													<th>Revise</th>
													<th class="actions">Actions</th>
												</tr>
											</thead>
											<tbody>

												<c:forEach items="${driverReminder}" var="driverReminder">



													<tr data-object-id="" class="ng-scope">

														<td class="icon"><c:out
																value="${driverReminder.driver_remindertype}" /></td>
														<td class="icon"><i></i> <c:out
																value="${driverReminder.driver_dlnumber}" /></td>
														<td class="icon"><c:out
																value="${driverReminder.driver_dlfrom_show}" /></td>

														<td class="icon"><c:out
																value="${driverReminder.driver_dlto_show}" /><font
															color="#FF6666"> ( <c:out
																	value="${driverReminder.driver_dueDifference}" /> )
														</font>
															<ul class="list-inline no-margin">
																<li><font color="#999999"> Due soon on <c:out
																			value="${driverReminder.driver_renewaldate}" />
																</font></li>

															</ul></td>
														<c:if test="${!empty driverReminder.driver_content}">
														<td class="icon"><sec:authorize
																access="hasAuthority('DOWNLOND_DRIVER')">
																<a
																	href="${pageContext.request.contextPath}/download/driverReminder/${driverReminder.driver_remid}.in">
																	<span class="fa fa-download"> Download</span>
																</a>
															</sec:authorize></td>
														</c:if>
														<c:if test="${empty driverReminder.driver_content}">
														<td>
														</td>
														</c:if>
														<td class="icon"><c:if test="${driver.driverStatusId != 6}"><sec:authorize
																access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
																<a
																	href="editDriverReminder.in?driver_remid=${driverReminder.driver_remid}">
																	<span class="fa fa-upload"> Revise</span>
																</a>
															</sec:authorize></c:if></td>

														<td>
														<c:if test="${driver.driverStatusId != 6}">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <i
																	class="fa fa-cog fa fa-md-settings"></i> <span
																	class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
																			<a
																				href="editDriverReminder.in?driver_remid=${driverReminder.driver_remid}">
																				<i class="fa fa-pencil"></i> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_DRIVER_REMINDER')">
																			<a
																				href="deleteDriverReminder.in?driver_remid=${driverReminder.driver_remid}&driver_id=${driverReminder.driver_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<i class="fa fa-trash"></i> Delete
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
							</c:if>
							<c:if test="${empty driverReminder}">
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
</div>