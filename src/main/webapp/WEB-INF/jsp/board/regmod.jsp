<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <form action="/board/regmod" method="post"
        <div>
            <input type="hidden" name="iboard" value="${updBoard.iboard}">
            <input type="text" name="title" value="${updBoard.title}">
        </div>
        <div>
            <textarea name="ctnt">${updBoard.ctnt}</textarea>
        </div>
        <div>
            <input type="submit" value="save">
            <input type="reset" value="reset">
        </div>
    </form>
</div>