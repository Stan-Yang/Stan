/**
 * 关闭模态窗口并刷新页面
 * */
function closeReplace(index){
    layer.close(index);
    location.replace(location.href);
}

/**
 * 关闭模态窗口不刷新
 * */
function closeReplace(index,did,attr,value){
    layer.close(index);
    $("#"+did).attr(attr,value);
}