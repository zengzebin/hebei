<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<title></title>
</head>
<body>
	<button id="testBtn">测试</button>
	<div id="testDiv" />
</body>
</html>
<script>
	$("#testBtn").click(function() {
		var para = {
			Codes : [ "411A9050" ]
		}
		var obj = {
			Type : "get",
			Uri : "/api/work/attendance",
			Parameter : para
		}
		$.ajax({
			cache : false,
			dataType : 'json',
			headers : {
				Authorization : '2356b367-0bc6-4cb5-99fc-7949cdbefde6'
			},
			contentType : "application/json;charset=utf-8",
			type : "Post",
			data : JSON.stringify(obj),
			url : "/opapi/api/work/attendance",
			success : function(data) {
				var tres = JSON.parse(data)
				alert(JSON.stringify(data));
			}
		});
	});
</script>
