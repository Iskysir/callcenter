<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:9080/jeecg-bpm/dec_wmController.do?decodeindex">
<input type="hidden" name ="decodeindex" id ="decodeindex"/>
<input type ="file" name ="ipath" id="ipath" value=""/>
<input type="text" name ="fileID" id ="fileID"/>
<input type="submit" value ="提交"> 
</form>
<br/>
二维码信息：${content}
</body>
</html>