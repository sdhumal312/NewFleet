<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<ul class="nav nav-list">
	<li class="active"><a
		href="<c:url value="/${SelectJob}/${SelectPage}/showDriver?driver_id=${driver.driver_id}"/>">Overview</a></li>
	
	<li><sec:authorize access="hasAuthority('DRIVER_BASIC_DETAILS')">
			<a href="<c:url value="/driverBasicDetails.in?driver_id=${driver.driver_id}"/>">Basic details </a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<a
				href="<c:url value="/showDriverFamily.in?driver_id=${driver.driver_id}"/>">Family details </a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('VIEW_DRIVER_BATA')">
			<a
				href="<c:url value="/showDriverBata.in?driver_id=${driver.driver_id}"/>">Bata
				Details</a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<a href="<c:url value="/ShowDriverAd.in?Id=${driver.driver_id}"/>">Attendance
			</a>
		</sec:authorize></li>

	<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">
		<li><sec:authorize access="hasAuthority('VIEW_DRIVER_ATTENDANCEPOINT')">
				<a
					href="<c:url value="/ShowDriverAdPOINT.in?Id=${driver.driver_id}"/>">Attendance
					Point </a>
			</sec:authorize></li>
	</sec:authorize>

	<li><sec:authorize
			access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
			<a
				href="<c:url value="/ShowDriverReminder.in?driver_id=${driver.driver_id}"/>">Reminders
				<span class="pull-right badge bg-aqua">${ReminderCount}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize
			access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
			<a
				href="<c:url value="/ShowDriverDocument.in?driver_id=${driver.driver_id}"/>">Documents
				<span class="pull-right badge bg-aqua">${DocumentCount}</span>
			</a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
			<a
				href="<c:url value="/ShowDriverComment.in?driver_id=${driver.driver_id}"/>">Comments
				<span class="pull-right badge bg-aqua">${CommentCount}</span>
			</a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">
			<a
				href="<c:url value="/ShowDriverPhoto.in?driver_id=${driver.driver_id}"/>">Photos
				<span class="pull-right badge bg-aqua">${PhotoCount}</span>
			</a>
		</sec:authorize></li>
	<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">

		<li><sec:authorize access="hasAuthority('VIEW_DRIVER_ADVANCE')">
				<a
					href="<c:url value="/DriverSalaryAdvance.in?ID=${driver.driver_id}"/>">Driver
					Advance </a>
			</sec:authorize></li>

		<li><sec:authorize access="hasAuthority('VIEW_DRIVER_SALARY')">
				<a href="<c:url value="/DriverSalary.in?ID=${driver.driver_id}"/>">Driver
					Salary </a>
			</sec:authorize></li>

	</sec:authorize>

	<sec:authorize access="hasAuthority('FLAVOR_THREE_PRIVILEGE')">
		<!-- //DWD TRIP DRIVER ADVANCE -->
		<li><sec:authorize access="hasAuthority('VIEW_DRIVER_ADVANCE')">
				<a
					href="<c:url value="/addDriverAdvance.in?ID=${driver.driver_id}"/>">Driver
					Advance </a>
			</sec:authorize></li>

	</sec:authorize>
	<sec:authorize access="hasAuthority('FLAVOR_TWO_PRIVILEGE')">

		<li><sec:authorize access="hasAuthority('VIEW_DRIVER_ADVANCE')">
				<a
					href="<c:url value="/DriverSalaryAdvance.in?ID=${driver.driver_id}"/>">Driver
					Advance </a>
			</sec:authorize></li>

		<li><sec:authorize access="hasAuthority('VIEW_DRIVER_SALARY')">
				<a href="<c:url value="/DriverSalary.in?ID=${driver.driver_id}"/>">Driver
					Salary </a>
			</sec:authorize></li>

	</sec:authorize>

</ul>