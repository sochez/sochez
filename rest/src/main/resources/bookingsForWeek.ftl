<html>
	<head>
		<title>Meeting calendar</title>
	</head>
	<body>
		Bookings for this week
		<br />
		<table border="1">
			<th>
				<td>Id</td>
				<td>User name</td>
				<td>Topic</td>
				<td>Date</td>
				<td>Begin time</td>
				<td>End time</td>
			</th>
<#if bookings??>
	<#list bookings as booking>
			<tr>
				<!--td>${booking.getId()}</td-->
				<td>${booking.getUserName()}</td>
				<td>${booking.getTopic()}</td>
				<td>${booking.getDate()}</td>
				<td>${booking.getBeginTime()}</td>
				<td>${booking.getEndTime()}</td>
			</tr>
	</#list>
</#if>
		</table>
		<br />
		<button type="button" onclick="window.location='/'">Back to all bookings</button>
	</body>
</html>