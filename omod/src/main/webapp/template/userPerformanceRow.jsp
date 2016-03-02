<tr class="${status.count%2!=0?'even':''}">
	<td rowspan="3" class="rowValue">${user.personName}</td>
	<td rowspan="3" class="rowValue">${user.username}</td>
	<td rowspan="3" class="rowValue">${user.roles}</td>
	<td rowspan="3" class="rowValue">${mohtractag:dateFirstObjectCreatedByUser(user.id,0)}&nbsp;/&nbsp;${mohtractag:dateLastObjectCreatedByUser(user.id,0)}</td>
	<td class="rowValue" style="background-color: cadetblue;">${mohtractag:numberOfObjectCreatedByUser(user.id,0,0)}</td>
	<td class="rowValue" style="background-color: cadetblue;">${mohtractag:numberOfObjectCreatedByUser(user.id,0,2)}</td>
</tr>
<tr class="${status.count%2!=0?'even':''}">
	<td class="rowValue" style="background-color: aliceblue;">${mohtractag:numberOfObjectCreatedByUser(user.id,1,0)}</td>
	<td class="rowValue" style="background-color: aliceblue;">${mohtractag:numberOfObjectCreatedByUser(user.id,1,2)}</td>
</tr>
<tr class="${status.count%2!=0?'even':''}">
	<td class="rowValue" style="background-color: #DDDDDD;">${mohtractag:numberOfObjectCreatedByUser(user.id,2,0)}</td>
	<td class="rowValue" style="background-color: #DDDDDD;">${mohtractag:numberOfObjectCreatedByUser(user.id,2,2)}</td>
</tr>