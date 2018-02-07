/*解决布局混乱*/
var pc; 
//不要放在$(function(){});中
//加载完成之后，隐藏div
$.parser.onComplete = function () {
    if (pc) clearTimeout(pc);
    pc = setTimeout(closes, 1000);
} 

function closes() {
    $('#loading').fadeOut('normal', function () {
        $(this).remove();
    });
}
/*解决布局混乱*/