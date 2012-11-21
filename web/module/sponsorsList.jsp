<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<script
	src='<%= request.getContextPath()%>/dwr/interface/TracPortalDWRUtil.js'></script>

<script type="text/javascript">
 function getPatientList(item){
	if (item.value != null && item.value.length > 2){
		TracPortalDWRUtil.findPatient(item.value, function(ret){

			var box = document.getElementById("resultOfSearch");
			box.innerHTML = ret.givenName + " " + ret.familyName;

		}); 
	}
 }	

 function getListOfPatient(item){
		if (item.value != null && item.value.length > 2){
			TracPortalDWRUtil.getPatientList(item.value, function(ret){

				var box = document.getElementById("resultOfSearch");
				box.innerHTML = ret;

			}); 
		}
	 } 
	
</script>
<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="Manage Sponsors" otherwise="/login.htm" redirect="/module/@MODULE_ID@/locationsponsor.form" />

<h2>Sponsors Management</h2>

<a href="sponsor.form">Add new Sponsor</a>
<br/><br/>

<b class="boxHeader">Current Sponsors</b>
<div class="box">
<table>
	<tr>
		<th>#.</th>
		<th>Name</th>
		<th>Description</th>
	</tr>
	<c:forEach items="${sponsors}" var="sponsor" varStatus="status">
		<tr>
			<td>${status.count}</td>
			<td><a href="sponsor.form?sponsorId=${sponsor.sponsorId}">${sponsor.name}</a></td>
			<td>${sponsor.description}</td>
		</tr>
	</c:forEach>
</table>
</div>

<!-- <input type="text" id="search" onKeyUp="javascript:getPatientList(this);" />
<input type="text" onKeyUp="javascript:getListOfPatient(this);" />
<div id="resultOfSearch"></div> -->

<%@ include file="/WEB-INF/template/footer.jsp"%>