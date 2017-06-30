<div id="content">
	<h1>Select Directories</h1>
	<p class="error">${errorMsg}</p>
	<p>Please select the two folder locations that you would like to perform sync on.</p>
	
	<form id="dir-select-form">
<!-- 		<input type="file" id="dir1" onchange="getfolder(event)" webkitdirectory mozdirectory msdirectory odirectory directory multiple required/> -->
<!-- 		<input type="file" id="dir2" webkitdirectory mozdirectory msdirectory odirectory directory multiple required/> -->
		<input name="dir1" type="text" placeholder="Directory 1" required></input>
		<input name="dir2" type="text" placeholder="Directory 2" required></input>
		<br/>
		<br/>
		<input id="submit-button" type="submit" value="Continue">
	</form>
	
</div>
