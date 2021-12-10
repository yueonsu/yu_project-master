<%@ page contentType="text/html;charset=EUC-KR" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="EUC-KR">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${requestScope.title}</title>
    <link rel="stylesheet" href="/res/css/common.css">
    <link rel="stylesheet" href="/res/css/board/detail.css">
    <link rel="stylesheet" href="/res/css/board/list.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <a href="/board/list"><span>게시판</span></a>
            <a href="/board/regmod"><span>글쓰기</span></a>
        </div>
        <div class="body">
            <jsp:include page="/WEB-INF/jsp/${requestScope.page}.jsp"></jsp:include>
        </div>
        <div class="footer">
            footer
        </div>
    </div>
</body>
</html>
