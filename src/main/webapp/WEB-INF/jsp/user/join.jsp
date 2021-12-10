<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div>
        <span class="err">${requestScope.err}</span>
    </div>
    <form action="/user/join" method="post" id="frm" onsubmit="return joinChk();">
        <div>
            <input type="text" name="nm" placeholder="Name" required>
        </div>
        <div>
            <input type="text" name="uid" placeholder="User ID" required>
        </div>
        <div>
            <input type="password" name="upw" placeholder="User Password" required>
        </div>
        <div>
            <input type="password" name="reupw" placeholder="Check Password" required>
        </div>
        <label>
            남자<input type="radio" name="gender" value="1" checked>
            여자<input type="radio" name="gender" value="2">
        </label>
        <div>
            <input type="submit" value="Join">
            <input type="reset" value="Reset">
        </div>
    </form>
</div>
<script src="/res/js/user/join.js"></script>