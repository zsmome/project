loginDialog = '#loginBox';
targetForm = '#loginForm';

$(window).resize(function() {
	$(loginDialog).dialog('center');
});

$(function () {
    $(loginDialog).dialog({
        title:'登录',
        width:400,
        height:240,
        closable:false,
        //disabled:false,

        draggable:false,
        align : 'center',
        iconCls:'icon-login-head',
        buttons:[
            {
                text:'前往注册',
                iconCls:'icon-register',
                plain:true,
                handler:function () {
                	window.location.href = '/register';
                }
            },
            {
                text:'登录',
                iconCls:'icon-login',
                plain:true,
                handler:function () {
                	$.messager.progress();
                	var username = $('input[name="username"]').val();
                	var password = $('input[name="password"]').val();
                	var rememberMe = $('#rememberMe').is(':checked');
                	var data = {"username":username,"password":password,"rememberMe":rememberMe};
                	//表单提交
                	$.ajax({
                		type: "POST",  
                	    url: "/loginAction",  
                	    data: data,
                	    success: function(data){  
                	    	console.info(data);
                	    	console.info(data.success);
                	    	console.info(data.failed);
                	    	$.messager.progress('close');
                	    	if(data.success) {
                	        	window.location.href = '/admin/index';
                	        } else if(data.loginCount) {
            	        		$('#message').html(data.loginCount);
	                	    } else if(data.failed) {
                	    		$('#message').html(data.failed);
	                	    }
                	    },  
                	});
                }
            }
        ],
    });

});