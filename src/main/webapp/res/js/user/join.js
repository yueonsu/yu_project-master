function joinChk() {
    var frm = document.querySelector('#frm');
    if(frm.upw.value !== frm.reupw.value) {
        alert('비밀번호를 확인해 주세요.');
        return false;
    }
    return true;
}