userInfo = new UserInfo();

function UserInfo() {
	/**
	 * 上传头像
	 */
	this.uploadHead = function() {
		var length = $('#selectFile').val().length;
		if(length <= 0) {
			$.messager.show({title:'提示', msg:'请选择头像！'});
			return;
		} else {
			$.messager.progress();
			$.ajax({
		        url: '/userInfo/uploadImage',
		        type: 'POST',
		        cache: false,
		        data: new FormData($('#imageUploadForm')[0]),
		        processData: false,
		        contentType: false,
		        success:function(data) {
		        	$.messager.progress('close');
		        	if(data.responseStatus == 'success') {
		            	$.messager.show({title:'上传', msg:'上传成功！'});
		        	} else {
		        		$.messager.show({title:'上传', msg:'上传失败！'});
		        	}
		        }
		    });
		}
	}

	/**
	 * 触发选择文件
	 */
	this.selectFile = function() {
		$('#selectFile').click();
	}

	/**
	 * 改变头像时
	 */
	this.changeImg = function(_this) {
	    var fr = new FileReader();
	    var f = _this.files[0];
	    fr.readAsDataURL(f);
	    /*读取完成时*/
	    fr.onload = function (e) {
	        $('#headPortraitsImg').attr('src', e.target.result);
	    }
	}
}

$(function() {
	
   $.post(
		   "/userInfo/readImage"
		   ,function(data) {
			   $('#headPortraitsImg').attr('src', data);
		   }
	   );
	
	$('#userInfoTabs').tabs({  
		fit:true,
		pill:true,
	    border:false,  
	    tabPosition:'left',
	    tabHeight:60,
	    headerWidth:100,
	    onSelect:function(title){    
	        
	    }    
	});
	
	/**
	 * 单击图片打开选中文件
	 */
	$('#headPortraitsImg').click(function() {
		userInfo.selectFile();
	});
	
	/**
	 * 改变文件时
	 */
	$('#selectFile').change(function() {
		userInfo.changeImg(this);
	});
	
	$('#saveUserInfo').click(function() {
		userInfo.uploadHead();
	});
	
})
