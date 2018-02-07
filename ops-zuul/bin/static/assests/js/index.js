
/*选项卡对象id*/
var tabsId = "#tabs";

var tabName = {"department":"部门管理","section":"科室管理","doctor":"医生管理",
		"berth":"床位管理","userInfo":"用户信息"};

/*按名字生成选项卡*/
function generateTAB(nameStr, url) {
    nameStr = tabName[nameStr];
    console.info(tabName);
    console.info("nameStr:"+nameStr);

    var iframeName = url.substring(url.lastIndexOf('/')+1); //截取问号后面的内容
    /*选项卡存在*/
    if($(tabsId).tabs('exists', nameStr)) {
        $(tabsId).tabs('select', nameStr);
    /*不存在*/
    } else {
        var content = '<iframe name="'+iframeName+'"  scrolling="auto" frameborder="0" src="' + url + '" style="width:100%;height:99.5%;"></iframe>'
        $(tabsId).tabs('add', {
            title:nameStr,
            content:content,
            closable:true,
            fit:true,
            border:false,
            tools:[{
                iconCls:'icon-refresh',
                handler:function(){
                    // 更新选择的面板的新标题和内容
                    var tab = $(tabsId).tabs('getSelected');  // 获取选择的面板
                    $(tabsId).tabs('update', {
                        tab: tab,
                        options: {
                            content:content,
                        }
                    });
                }
            }]
        });
    }
}

/*加载完成时的操作*/
$(function () {
   /*生成tabs*/
   $(tabsId).tabs({
        fit:true,
        border:false,
        pill:true
   });
   generateTAB('department', '/admin/department');
   console.info("执行post");
   $.post(
	   "/userInfo/readImage"
	   ,function(data) {
		   console.info(data);
		   $('#headPortraitsImg').attr('src', data);
	   }
   );
   
});

/**
 * 用户信息
 */
function userInfo() {
	generateTAB('userInfo', '/common/userInfo');
}
