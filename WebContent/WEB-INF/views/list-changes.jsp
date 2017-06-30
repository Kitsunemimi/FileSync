<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="content">
	<h1>List Changes</h1>
	<p>File Sync will now perform these changes.</p>
	
	<table id="changelist">
		<tr>
			<th>Directory 1</th>
			<th>Directory 2</th>
		</tr>
		<tr class="change-add">
			<th>${sessionScope.changes[0][0]}</th>
			<th>${sessionScope.changes[1][0]}</th>
		</tr>
		<tr class="change-move">
			<th>${sessionScope.changes[0][1]}</th>
			<th>${sessionScope.changes[1][1]}</th>
		</tr>
		<tr class="change-del">
			<th>${sessionScope.changes[0][2]}</th>
			<th>${sessionScope.changes[1][2]}</th>
		</tr>
	</table>
	<br/>
	<button>Back</button>
	<button>Sync</button>
</div>
