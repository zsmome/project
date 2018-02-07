
/*信息*/
//datagrid数据表格
var targetDg = '#departmentDg';
var targetDatagrid = '#restoreDatagrid';
/*datagrid 工具栏*/
var targetDatagridTb = '#tb';
/*datagrid menu菜单*/
var rightClickRowMenu = '#datagridMenu';
/*保存和取消编辑按钮*/
var saveOrCancel = '#saveBtn,#cancelBtn';

var targetDialog = '#newAndEditWindow';

/*新增和编辑*/
var newAndEditForm = '#newAndEditForm';

departmentRowValid = {
	"depNo" : "length[5,8]",
	"depName" : "length[3,10]",
	"depDescribe" : "length[0,50]"
}; //部门信息行验证

//操作表格对象
dataGridOp = new DataGridOp(targetDg, targetDatagridTb, rightClickRowMenu,
		newAndEditForm);
/**
 * 数据表格的操作
 * 功能：增加一行、删除【一行、多行】、编辑一行、查找数据、撤消编辑
 * @constructor
 * @param targetDg 数据表格对象
 * @param targetDatagridTb 数据表格工具栏
 * @param rightClickRowMenu 右击一行数据时弹出的菜单
 */
function DataGridOp(targetDg, targetDatagridTb, rightClickRowMenu,
		newAndEditForm) {
	this.editRowIndex = -1;
	this.targetDg = targetDg;
	this.targetDatagridTb = targetDatagridTb;
	this.rightClickRowMenu = rightClickRowMenu;
	this.newAndEditForm = newAndEditForm;

	/**
	 * 根据选择的行的索引号
	 * @returns {*|jQuery}
	 */
	this.getSelectedRowIndex = function() {
		var index = -1;
		var row = $(this.targetDg).datagrid('getSelected');
		if (row != null) {
			index = $(this.targetDg).datagrid('getRowIndex', row);
		}
		console.info("行值：" + index);
		return index;
	};

	/**
	 * 查找数据
	 */
	this.search = function(no, name) {
		var depNo = $.trim($(no).val());
		var depName = $.trim($(name).val());
		var department = {
			"depNo" : depNo,
			"depName" : depName
		};
		console.info("查询数据参数");
		console.info(department);
		$(this.targetDg).datagrid('load', department);
	};

	/**
	 * 删除数据
	 * @param target
	 */
	this.removeRow = function() {
		console.info('delete');
		//获取选择的行
		var rows = $(targetDg).datagrid('getSelections');
		if(rows.length == 0) {
			$.messager.show({title: '提示', msg: '请选择您要删除的记录行！'});
		} else {
            $.messager.confirm('确认操作', '您确定要删除这些记录？', function (r) {
                if (r) {
                    var selectedIDs = [];
                    $.each(rows, function(index, data) {
                    	selectedIDs.push(data.id);
                    });
                    console.info(selectedIDs);
                    //ajax成功后
                    $.ajax({
                        url: "/department/updateStatus",
                        data: {
                            "selectedIDs": selectedIDs
                        },
                        traditional: true,//这里设置为true
                        success: function(result) {
                        	if(result > 0) {
                        		$.messager.show({title:'提示', msg:'删除成功！'});
                        		$(targetDg).datagrid('unselectAll'); //取消所有选中的行
                        		$(targetDg).datagrid('reload'); //刷新
                        		$(targetDatagrid).datagrid('reload'); //刷新
                        		
                        	} else {
                        		$.messager.show({title:'提示', msg:'删除失败！'});
                        	}
                        }
                    });
                }
            });
		}
	};

	
	/**
	 * 还原数据
	 */
	this.resotreRow = function() {
		restoreDataDialog();
	}
	
	/**
	 * 编辑弹出窗
	 * @param title
	 */
	this.editWindow = function(title) {
		var iconCls = 'icon-new';
		var titleStr = '新增记录';
		if (title == 'edit') {
			iconCls = 'icon-edit';
			titleStr = '编辑记录';
			//单击编辑时
			//有没有选择一行
			var row = $(targetDg).datagrid('getSelected');
			if (row == null) {
				$.messager.show({
					title : '提示',
					msg : '请选择您要编辑的记录行！'
				});
				return;
			} else {
				//给表单赋上当前编辑行的值
				$(newAndEditForm).form('load', {
					id : row.id,
					depNo : row.depNo,
					depName : row.depName,
					depDescribe : row.depDescribe
				});
			}
		} else {
			$(newAndEditForm).form('clear'); //清空表单
		}

		$(targetDialog).dialog({
			title : titleStr,
			width : 300,
			height : 230,
			closable : false,
			//disabled:false,

			draggable : false,
			align : 'center',
			iconCls : iconCls,
			buttons : [ {
				text : '取消',
				iconCls : 'icon-cancel',
				plain : true,
				handler : function() {
					$(targetDialog).dialog('close');
				}
			}, {
				text : '提交',
				iconCls : 'icon-submit',
				plain : true,
				handler : function() {
					var url = '/department/insert';
					if (title == 'edit') {
						url = '/department/update';
					}

					$(newAndEditForm).form('submit', { //提交数据
						url : url,
						onSubmit : function() {
							var isValid = $(this).form('validate');
							if (isValid) {
								$.messager.progress(); // 验证成功后，显示进度条
							}
							return isValid; // 返回false终止表单提交
						},
						success : function(data) {
							data = JSON.parse(data);
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
							if(data.success) {
								$(targetDialog).dialog('close'); //关闭弹窗
								$(targetDg).datagrid('reload'); //刷新操作
								$.messager.show({
									title : '提示',
									msg : '操作成功！'
								});
							} else {
								$.messager.show({
									title : '提示',
									msg : '操作失败！'
								});
							}
						}
					});
				}
			} ],
		});
	};

	//表格宽度自适应，这里的#dg是datagrid表格生成的div标签
	this.fitCoulms = function() {
		$(targetDg).datagrid({
			fitColumns : true
		});
	};
};

/**
 * 查询数据表格
 */
function queryDatagrid() {
	$(dataGridOp.targetDg).datagrid({
		fit : true,
		url : '/department/queryByCondition',
		//分页插件
		pagination : true,
		//        pagePosition: 'top',
		idField : 'id',
//		pageNumber : 1,
		pageSize : 30,
		pageList : [30,50,100],
		//显示行号
		rownumbers : true,
		//只能选择一行
		//        singleSelect: true,
		//列自适应
		fitColumns : true,
		//马线
		striped : true,
		//默认排序
		multiSort : true,
		sortName : 'depNo,depName',
		sortOrder : 'ASC,ASC',
		//唯一标识
		idField : 'id',
		columns : [ [ {
			field : 'id',
			title : '唯一编号',
			width : 30,
			align : 'center',
			checkbox : true
		}, {
			field : 'depNo',
			title : '部门编号',
			width : 120,
			sortable : true,
			align : 'center',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : departmentRowValid.depNo,
				}
			}
		}, {
			field : 'depName',
			title : '部门名称',
			width : 120,
			sortable : true,
			align : 'center',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : departmentRowValid.depName,
				}
			}
		}, {
			field : 'depDescribe',
			title : '部门描述',
			width : 340,
			editor : 'text',
			validType : departmentRowValid.depDescribe,
		}, ] ],
		//        //工具栏
		toolbar : dataGridOp.targetDatagridTb,
		//编辑后修改状态并提交数据
		onAfterEdit : function(index, row) {

			$.post('/department/update', row, function(result) {
				var msg = '';
				if (result > 0) {
					msg = '操作成功！';
					dataGridOp.editRowIndex = -1;
					$(dataGridOp.targetDg).datagrid('refreshRow', index);

					//当ajax成功保存修改
					$(dataGridOp.targetDg).datagrid('acceptChanges');
				} else {
					msg = '操作失败！';
					//重新编辑并定焦
					$(dataGridOp.targetDg).datagrid('beginEdit', index);
					dataGridOp.cellFacus(index, 'depNo');
				}
				$.messager.show({
					title : '提示',
					msg : msg
				});
			});
		},
	});
	//去外边框
	$(dataGridOp.targetDg).datagrid('getPanel').removeClass(
			'lines-both lines-no lines-right lines-bottom').css({
		"border" : "0px"
	});
};



/**
 * 主执行脚本
 */
$(function() {
	//查询
	queryDatagrid();

	$('#depNo').textbox({
		prompt : '部门编号',
		iconCls : 'icon-no',
		iconAlign : 'left',
		required : true,
		validType : departmentRowValid.depNo
	});

	$('#depName').textbox({
		prompt : '部门名称',
		iconCls : 'icon-name',
		iconAlign : 'left',
		required : true,
		validType : departmentRowValid.depName
	});
	$('#depDescribe').textbox({
		height : 66,
		multiline : true,
		prompt : '部门描述',
		iconCls : 'icon-describe',
		iconAlign : 'left',
	});

/*	//初始化列宽
	$(document).ready(function() {
		dataGridOp.fitCoulms();
	});
	//重置列宽
	$(window).resize(function() {
		dataGridOp.fitCoulms();
	});*/

})

/**
 * 还原数据
 */
function restoreDataDialog() {
	var targetDialog = '#datagridDialog';
	$(targetDialog).window({
		iconCls:'icon-restore',
		title:'回收站',
		width:380,
		height:250,
		minimizable:false
	});
	/*数据表格*/
	restoreDatagrid();
}
/**
 * 数据表格
 */
function restoreDatagrid() {
	$(targetDatagrid).datagrid({
        fit: true,
        url: '/department/queryRestore',
        //分页插件
        pagination: true,
        pageNumber: 1,
        pageSize: 5,
        pageList: [5, 20, 50],
        //显示行号
        rownumbers: true,
        //列自适应
        fitColumns: true,
        //马线
        striped: true,
        //默认排序
        columns: [[
            {field: 'id', title: '唯一编号', width: 30, align: 'center', checkbox: true},
            {
                field: 'depNo', title: '部门编号', width: 120, align: 'center',
            },
            {
                field: 'depName', title: '部门名称', width: 120, align: 'center',
            },
            
        ]],
        toolbar: [{
        	text:'还原记录',
        	iconCls: 'icon-redo',
    		handler: function(){
    			var rows = $(targetDatagrid).datagrid('getSelections');
    			if(rows.length == 0) {
    				$.messager.show({title: '提示', msg: '请选择您要还原的记录行！'});
    			} else {
	                $.messager.confirm('确认操作', '您确定要还原这些记录？', function (r) {
	                    if (r) {
	                        var rows = $(targetDatagrid).datagrid('getSelections');
	                        var selectedIDs = [];
	                        $.each(rows, function(index, data) {
	                        	selectedIDs.push(data.id);
	                        });
	                        //ajax成功后
	                        $.ajax({
	                            url: "/department/updateStatus",
	                            data: {
	                                "selectedIDs": selectedIDs
	                            },
	                            traditional: true,//这里设置为true
	                            success: function(result) {
	                            	if(result > 0) {
	                            		$.messager.show({title:'提示', msg:'还原成功！'});
	                            		$(targetDatagrid).datagrid('unselectAll'); //取消所有选中的行
	                            		$(targetDatagrid).datagrid('reload'); //刷新
	                            		$(targetDg).datagrid('reload');
	                            	} else {
	                            		$.messager.show({title:'提示', msg:'还原失败！'});
	                            	}
	                            }
	                        });
	                    }
	                });
    			}
    		}
        }]
	});
	
	//分页属性设置
	var page = $(targetDatagrid).datagrid('getPager');
	$(page).pagination({  
		beforePageText: '',//页数文本框前显示的汉字  
		afterPageText: '共{pages}页',  
		displayMsg: ''
	});
}
