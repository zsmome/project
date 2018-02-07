registerDialog = '#registerBox';
targetForm = '#registerForm';

$(window).resize(function() {
	$(registerDialog).dialog('center');
});

/**
 * 内容切换
 * @param isRight 一个布尔值
 * @param selector 选择器
 * @param contentJson josn内容
 */
function contentSwitch(isRight, selector, contentJson) {
	//判断是不是单方法
	var isSingleMethod = true;
	if(contentJson.method2) {
		isSingleMethod = false;
	}
	var evalStr = '';
	//单方法名
	if(isSingleMethod) {
		if(isRight) {
			evalStr += '$(selector).'+contentJson.method+'(contentJson.ok);';
		} else {
			evalStr += '$(selector).'+contentJson.method+'(contentJson.error);';
		}
	} else {
		if(isRight) {
			evalStr += '$(selector).'+contentJson.method+'(contentJson.ok);'
			evalStr += '$(selector).'+contentJson.method2+'(contentJson.error);'
		} else {
			evalStr += '$(selector).'+contentJson.method+'(contentJson.error);'
			evalStr += '$(selector).'+contentJson.method2+'(contentJson.ok);'
		}
	}
	//执行方法
	eval(evalStr);
}

/**
 * 是否可以注册
 */
var isUsernameValid = false;
var ispwd1Valid = false;
var ispwd2Valid = false;

/**
 * 验证表单每一个验证框信息
 */
function validFrom() {
	//图标
	var ICON_CLASS = {
			"ok":"icon-right", "error":"icon-error", 
			"method":"addClass", "method2":"removeClass",
	};
	//文字颜色
	var WORLD_COLOR = {
			"ok":"enableWorld", "error":"disableWorld",
			"method":"addClass", "method2":"removeClass",
	};

	//用户长度文字消息
	var USERLEN_MESSAGE = {
			"ok":"*【邮箱地址】有效", "error":"*【6-8英文或数字】无效",
			"method":"html",
	};
	
	//用户文字消息
	var USER_MESSAGE = {
			"ok":"*【6-8英文或数字】可以注册", "error":"*【6-8英文或数字】已经注册",
			"method":"html",
	};

	//密码文字消息
	var PASS1_MESSAGE = {
			"ok":"*【8-20个字符组成】没问题", "error":"*【8-20个字符组成】有问题",
			"method":"html",
	};

	//确认密码文字消息
	var PASS2_MESSAGE = {
			"ok":"*【两次密码相同】", "error":"*【两次密码不相同】",
			"method":"html",
	};

	//帐号
	//失去焦点时，判断帐号是否存在
	$("#username").keyup(function() {
		var username = $('#username').val();
		var mailRegex = /^[\w]+@qq.com$/;
		var isValid = mailRegex.test(username);
		isUsernameValid = isValid;
		//消息框
		var message = $('#userMessage');
		//图标
		contentSwitch(isValid, '#usernameIcon', ICON_CLASS);
		//文字颜色
		contentSwitch(isValid, message, WORLD_COLOR);
		//提示消息
		contentSwitch(isValid, message, USERLEN_MESSAGE);
		
		//验证成功后才会判断存不存在
		var user = {"username" : $('#username').val()};
		if(isValid) {
			$.post('isExistUsername', user, function(result) {
				result = !result;
				//用户名验证
				isUsernameValid = result;
				//图标
				contentSwitch(result, '#usernameIcon', ICON_CLASS);
				//文字颜色
				contentSwitch(result, message, WORLD_COLOR);
				//提示消息
				contentSwitch(result, message, USER_MESSAGE);
			});
		}
	});

	//密码图标
	$("#password").keyup(function(){
		
		$("#password2").trigger('keyup');
		
		//验证
		var p1Len = $('#password').val().length;
		console.info(p1Len);
		var isValid = p1Len >=8 && p1Len <= 20 ? true : false;
		ispwd1Valid = isValid;
		
		//图标
		contentSwitch(isValid, '#passwordIcon', ICON_CLASS);
		//消息框
		var message = $('#pwdMessage');
		//文字颜色
		contentSwitch(isValid, message, WORLD_COLOR);
		//提示消息
		contentSwitch(isValid, message, PASS1_MESSAGE);
	});
	//确认密码
	$("#password2").keyup(function(){
		//验证
		var p1Val = $('#password').val();
		var p2Val = $('#password2').val();
		var isValid = p1Val == p2Val ? true : false;
		ispwd2Valid = isValid;
		//图标
		contentSwitch(isValid, '#password2Icon', ICON_CLASS);
		//消息框
		var message = $('#pwd2Message');
		//文字颜色
		contentSwitch(isValid, message, WORLD_COLOR);
		//提示消息
		contentSwitch(isValid, message, PASS2_MESSAGE);
	});
}
	
$(function() {
	
	validFrom();
	
	$(registerDialog).dialog({
		title : '注册',
		width : 480,
		height : 220,
		closable : false,
		//disabled:false,

		draggable : false,
		align : 'center',
		iconCls : 'icon-register',
		buttons : [ {
			text : '前往登录',
			iconCls : 'icon-login',
			plain : true,
			handler : function() {
				location.href = 'login';
			}
		}, {
			text : '注册',
			iconCls : 'icon-register',
			plain : true,
			handler : function() {
				var isRegister = isUsernameValid && ispwd1Valid && ispwd2Valid;
				console.info(isRegister);
            	if(isRegister) {
            		$.messager.progress();
            		var username = $('input[name="username"]').val();
            		var password = $('input[name="password"]').val();
            		var data = {"username":username,"password":password};
	            	//表单提交
	            	$.ajax({
	            		type: "POST",  
	            	    url: "/registerAction",  
	            	    data: JSON.stringify(data),//将对象序列化成JSON字符串  
	            	    dataType:"json",  
	            	    contentType : 'application/json;charset=utf-8', //设置请求头信息  
	            	    success: function(data){  
	            	    	console.info(data);
	            	    	$.messager.progress('close');
	            	    	/*$.messager.show({
								title : '提示',
								msg : '激活码已经发送，请前往邮箱【激活】再进行登录！',
								timeout:3000,
							});
	            	    	setTimeout(function() {
								window.location.href = '/login';
							}, 3000);*/
							//注册成功
							if (data.responseStatus == 'success') {
								$.messager.show({
									title : '提示',
									msg : data.msg,
									timeout:3000,
								});
								setTimeout(function() {
									window.location.href = '/login';
								}, 3000);
								//注册失败
							} else if(data.responseStatus == 'fail') {
								$.messager.show({
									title : '提示',
									msg : data.msg
								});
							} else {
								$.messager.show({
									title : '提示',
									msg : data
								});
							}
	            	    },  
	            	});
				}
			}
		}],
	});
	

});