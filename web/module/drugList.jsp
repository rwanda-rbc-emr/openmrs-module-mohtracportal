<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="/WEB-INF/view/admin/orders/localHeader.jsp" %>

<h2><spring:message code="@MODULE_ID@.drug.manage.list"/></h2>

<table>
	<tr>
		<th>#.</th>
		<th>Name</th>
		<th>concept Related</th>
		<th>concept Route</th>
		<th>Dosage Form</th>
		<th>maximumDailyDose</th>
		<th>minimumDailyDose</th>
		<th>units</th>
		<th>combination</th>
		<th>Retired</th>
	</tr>
	<c:forEach items="${drugList}" var="drug" varStatus="status">
		<tr>
			<td>${status.count}.</td>
			<td>${drug.name}</td>
			<td>${drug.concept.name}</td>
			<td>${drug.route.name}</td>
			<td>${drug.dosageForm.name}</td>
			<td>${drug.maximumDailyDose}</td>
			<td>${drug.minimumDailyDose}</td>
			<td>${drug.units}</td>
			<td>${drug.combination}</td>
			<td>${drug.retired}</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>