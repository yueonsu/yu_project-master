function moveToDetail(iboard) {
    console.log(iboard);
    location.href = "/board/detail?iboard=" + iboard;
};

var frm = document.querySelector('#frm');
if(frm) {
   frm.rowCnt.addEventListener('change', function () {
       frm.submit();
   });
}

var loginFrm = document.querySelector('#loginFrm');
if(loginFrm) {
    loginFrm.chkPw.addEventListener('click', function () {
        if(loginFrm.upw.type === 'password') {
            loginFrm.upw.type = 'text';
            loginFrm.chkPw.value = '비밀번호 감추기';
        } else {
            loginFrm.upw.type = 'password';
            loginFrm.chkPw.value = '비밀번호 보이기';
        }
    });
}