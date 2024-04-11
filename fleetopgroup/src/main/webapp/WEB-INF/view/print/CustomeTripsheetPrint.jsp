<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<head>
<style>
	.center
	{
		margin-left: auto;
      	margin-right: auto;
      	margin-top: 2%;
	}
	
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
		<table class="center" style="width:98%;border:1px solid">
			
			<tr>
				<td style="width:100%">
					<table style="width:100%;">
						<tr>
							<td height="70px" style="width:25%;text-align:center;font-size:22px;">
								 <i><u>KAVI <br>LOGISTICS</u></i>
							</td>
							<td height="70px" style="width:75%;">
								<table style="width:100%;">
									<tr>
										<td height="35px" style="width:100%;">
											<table height="35px" style="width:100%;" border="1">
												<tr>
													<td style="width:45%;"><span style="padding-left:3%;">TripSheet No : ${TripSheet.tripSheetNumber}</span> </td>
													<td style="width:45%;"><span style="padding-left:3%;">Dispatch Date & Time: ${TripSheet.dispatchedByTime}</span></td>
									
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="35px" style="width:100%;">
											<table height="35px" style="width:100%;" border="1">
												<tr>
													<td style="width:45%;"><span style="padding-left:3%;">Vehicle No: ${TripSheet.vehicle_registration}</span> </td>
													<td style="width:45%;"><span style="padding-left:3%;">Date: ${TripSheet.tripOpenDate} To  ${TripSheet.closetripDate}</span></td>
													<td style="width:20%;">Sr. No: </td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="35px" style="width:100%">
											<table height="35px" style="width:100%;" border="1">
												<tr>
													<td style="width:55%">
														<span style="padding-left:3%;"> Driver Name :  ${TripSheet.tripFristDriverName}</span>
													</td>
													<td style="width:45%">
														<span style="padding-left:3%;"> Mobile No:  ${TripSheet.tripFristDriverMobile}</span>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							
						</tr>
						
					</table>
				</td>
			</tr>
			<tr>
				<td style="width:100%">
					<table style="width:100%;" border="1">
						<tr height="28px" style="text-align:center;">
							<td style="width:5%">SR .No</td>
							<td style="width:20%">Date</td>
							<td style="width:25%">Route</td>
							<td style="width:10%">Op Km</td>
							<td style="width:10%">Cl Km</td>
							<td style="width:10%">Total</td>
							<td style="width:10%">Liters</td>
							<td style="width:10%">milege</td>	
						</tr>
						
						 <%
						    for (int i = 0; i <3; i++) {
						  %>
							<tr height="28px">
								<td style="width:5%"></td>
								<td style="width:20%"></td>
								<td style="width:25%"></td>
								<td style="width:10%"></td>
								<td style="width:10%"></td>
								<td style="width:10%"></td>
								<td style="width:10%"></td>
								<td style="width:10%"></td>	
							</tr>
						  <%
						    }
						  %>
						
					</table>
				</td>
			</tr>
			<tr>
				<td style="width:100%">
					<table style="width:100%;" border="1"> 
						<tr height="30px" style="text-align:center;">
							<td style="width:10%"></td>
							<td style="width:10%">Jackey</td>
							<td style="width:10%">Wheel Spanner</td>
							<td style="width:10%">Lever</td>
							<td style="width:10%">Stepheny</td>
							<td style="width:10%">Tools</td>
							<td style="width:10%">Gps</td>
							<td style="width:30%">Verified Person Signature</td>
						</tr>
						<tr height="30px" style="text-align:center;">
							<td  style="width:10%">Departure</td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:30%"></td>
						</tr>
						<tr height="30px" style="text-align:center;">
							<td  style="width:10%">Arrival</td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:10%"></td>
							<td  style="width:30%"></td>
						</tr>
						
					</table>
				</td>
			</tr>
			<tr>
				<td style="width:100%">
					<table style="width:100%;" border="1">
						<tr height="45x" style="text-align:center;">
							<td style="width:15%">Dispatch Date & Time</td>
							<td style="width:15%">Loading Branch</td>
							<td style="width:15%">Unloading Branch</td>
							<td style="width:10%">Articles</td>
							<td style="width:5%">Container Seal No.</td>
							<td style="width:10%">Advance</td>
							<td style="width:10%">Seal & sign</td>
							<td style="width:15%">Unloading Branch Remarks</td>
						</tr>
						
						 <%
						    for (int i = 0; i <10; i++) {
						  %>
						  <tr height="40px">
						    <td></td>
						    <td></td>
						    <td></td>
						    <td></td>
						    <td></td>
						    <td></td>
						    <td></td>
						    <td></td>
						  </tr>
						  <%
						    }
						  %>
					</table>
				</td>
			</tr>
		</table> 
</body>