<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<ul class="nav nav-list">
	<li><sec:authorize access="hasAuthority('EDIT_VENDOR')">
			<a
				href="<c:url value="/${SelectType}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">
				Edit Vendor </a>
		</sec:authorize></li>
	<li><sec:authorize
			access="hasAuthority('ADDEDIT_VENDOR_FIXEDPART')">
			<a href="<c:url value="/VendorPartPrice/${vendor.vendorId}/1.in"/>">
				Fixed Vendor Part Price</a>
		</sec:authorize></li>
	<li><sec:authorize
			access="hasAuthority('ADDEDIT_VENDOR_FIXEDFUEL')">
			<a href="<c:url value="/VendorFuelPrice/${vendor.vendorId}/1.in"/>">
				Fixed Daily Fuel Price</a>
		</sec:authorize></li>
	<li><sec:authorize
			access="hasAuthority('ADDEDIT_VENDOR_DOCUMENT')">
			<a
				href="<c:url value="/addVendorDoc.in?vendorId=${vendor.vendorId}"/>">
				Vendor Document</a>
		</sec:authorize></li>
	<li><sec:authorize
			access="hasAuthority('ADD_LAUNDRY_RATE')">
			<a
				href="<c:url value="/VendorLaundryPrice.in?Id=${vendor.vendorId}"/>">
				Add Vendor Laundry Rate</a>
		</sec:authorize></li>
</ul>