<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/res/css/user/login.css">
<div>
    <div>
        <span class="err">${requestScope.err}</span>
    </div>
    <form action="/user/login" method="post">
        <div>
            <input type="text" name="uid" placeholder="User ID">
        </div>
        <div>
            <input type="password" name="upw" placeholder="User Password">
        </div>
        <div>
            <input type="submit" value="Login">
            <a href="/user/join"><input type="button" value="Join"></a>
        </div>
    </form>
</div>