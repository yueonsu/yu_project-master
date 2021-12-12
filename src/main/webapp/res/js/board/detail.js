function delBoardBtn(iboard) {
    document.querySelector('.disableLink').removeAttribute('href');
    if(confirm("정말로 삭제하시겠습니까?")) {
        location.href="/board/del?iboard=" + iboard;
    } else {

    }
}

function delCmt(icmt, iboard) {
    if(confirm("정말로 삭제하시겠습니까?")) {
        location.href = "/board/cmt/del?iboard=" + iboard +"&icmt=" + icmt;
    }
}

var openModContainerElem = document.querySelector('.openModContainer');
function openModForm(icmt, ctnt) {
    openModContainerElem.style.display = 'flex';
    var cmtModFrmElem = document.querySelector('#cmtModFrm');
    cmtModFrmElem.ctnt.value = ctnt;
    cmtModFrmElem.icmt.value = icmt;
}

function cancelCmtMod() {
    openModContainerElem.style.display = 'none';
}

var cmt_upd = document.querySelector('#cmt_upd');
cmt_upd.addEventListener('click', function (e) {
    cmtModContainerElem.style.display = 'flex';
    var cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');
    cmtModFrmElem.dcmt.value = dcmt;
    cmtModFrmElem.ctnt.value = ctnt;
})