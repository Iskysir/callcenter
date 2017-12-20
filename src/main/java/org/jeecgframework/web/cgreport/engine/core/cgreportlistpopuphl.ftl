<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
<link href="plug-in/chrhc/style.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
<script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
<script type="text/javascript" src="plug-in/artDiglog/artDialog.js?skin=blue"></script>
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
<script type="text/javascript">
$(function(){
var urlConfigString = '${config}'|| "{}";
var config =eval('('+urlConfigString+')');

$('#${config_id}List').datagrid(
	{
	idField: 'id',
	/*title: '${config_name}',*/
	url:'cgReportControllerHl.do?datagrid&configId=${config_id}',
        fit:true,
	fitColumns:true,
	pageSize: 10,
	pagination:<#if pagination?? && pagination == 'N' >false<#else>true</#if>,
	pageList:[10,30,50,100],
	singleSelect:config.singleSelect || false,
	sortOrder:'asc',
	rownumbers:true,
	showFooter:true,
	frozenColumns:[[]],
	columns:[
		[			<#if (config_fieldList?size>0)>
					{field:'ck',checkbox:true},
					<#list config_fieldList  as x>  
				 	<#if x_has_next>
					{field:'${x['field_name']}',
						title:'${x['field_txt']}',
							<#if x['is_show'] == 'N'>hidden:true,</#if>
						<#if x['field_href'] ?if_exists?html != ''>
                            formatter:function(value,rec,index){
                                var href='';
                                href+=applyHref('字段链接','${x['field_href']}',value,rec,index);
                                return href;
                            },
						</#if>
						width:50
					},
					<#else>
					{field:'${x['field_name']}',
						title:'${x['field_txt']}',
							<#if x['is_show'] == 'N'>hidden:true,</#if>
						<#if x['field_href'] ?if_exists?html != ''>
                            formatter:function(value,rec,index){
                                var href='';
                                href+=applyHref('字段链接','${x['field_href']}',value,rec,index);
                                return href;
                            },
						</#if>
						width:50
					},
				  	</#if>
					</#list>
					</#if>
            {
                field: 'opt', width:30,title: '操作', align: 'center', formatter: function (value, rec, index) {
                if (!rec.id) {
                    return '';
                }
                var href = '';
                href += "[<a href='#' onclick=addsc('" + rec.account + "')>";
                href += "添加收藏</a>]";
				return href;
            }
            }
		]
	],
	onLoadSuccess:function(data){
		$("#${config_id}List").datagrid("clearChecked");
		//selectDefaultValue(config ,data, '${config_id}List');
	},
	onClickRow:function(rowIndex,rowData) {
		rowid=rowData.id;gridname='${config_id}List';
		if($('#${config_id}List').datagrid('options').singleSelect){
			selectDefaultValue = function(){}; 
		}	 	
	}

	});
	$('#${config_id}List').datagrid('getPager').pagination({beforePageText:'',afterPageText:'/{pages}',displayMsg:'{from}-{to}共{total}条',showPageList:true,showRefresh:true});
	$('#${config_id}List').datagrid('getPager').pagination({onBeforeRefresh:function(pageNumber, pageSize){ $(this).pagination('loading');$(this).pagination('loaded'); }});

	var urla = "cgReportControllerHlSc.do?popup&id=hl_user_collection";
    $('#tt').tabs('add',{
        title:'我的收藏',
        content:'<iframe src="' + urla + '" frameborder="0"  id="collection" style="width:100%;height:100%"></iframe>'
    });


});
function  addsc(account){
	var url = "hlVisitItemController.do?addsc&usercode="+account;
    $.post(url,{},function(data){
       // $.dialog.alert('收藏成功');
        reload${config_id}List();
    });
}
function reloadTable(){
		try{
		$('#'+gridname).datagrid('reload');
		$('#'+gridname).treegrid('reload');
		}catch(ex){
			//donothing
		}
	}
	function reload${config_id}List(){$('#${config_id}List').datagrid('reload');}
	function get${config_id}ListSelected(field){return getSelected(field);}
	function getSelected(field){var row = $('#'+gridname).datagrid('getSelected');if(row!=null){value= row[field];}else{value='';}return value;}
	function get${config_id}ListSelections(field){var ids = [];var rows = $('#${config_id}List').datagrid('getSelections');for(var i=0;i<rows.length;i++){ids.push(rows[i][field]);}ids.join(',');return ids};
	function ${config_id}Listsearch(){
 		var queryParams=$('#${config_id}List').datagrid('options').queryParams;$('#${config_id}Listtb').find('*').each(function(){queryParams[$(this).attr('name')]=$(this).val();});$('#${config_id}List').datagrid({url:'cgReportControllerHl.do?datagrid&configId=${config_id}',pageNumber:1});
 	}
	function dosearch(params){var jsonparams=$.parseJSON(params);$('#${config_id}List').datagrid({url:'cgReportControllerHl.do?datagrid&configId=${config_id},',queryParams:jsonparams});}
	function ${config_id}Listsearchbox(value,name){var queryParams=$('#${config_id}List').datagrid('options').queryParams;queryParams[name]=value;queryParams.searchfield=name;$('#${config_id}List').datagrid('reload');}$('#${config_id}Listsearchbox').searchbox({searcher:function(value,name){${config_id}Listsearchbox(value,name);},menu:'#${config_id}Listmm',prompt:'请输入查询关键字'});
	
	function ${config_id}searchReset(name){ 
		$("#"+name+"tb").find("input[type!='hidden']").val("");
		$("#"+name+"tb").find("select").val("");
		${config_id}Listsearch();
	}	
	
	function getSelectRows(){
        var tab = $('#tt').tabs('getSelected');;
        var index = $('#tt').tabs('getTabIndex',tab);
        if(index==0){
            return $('#${config_id}List').datagrid('getChecked');
		}else{
			var ccc = document.getElementById('collection').contentWindow.getSelectRows();
            return ccc;
		}
	}
//将字段href中的变量替换掉
function applyHref(tabname,href,value,rec,index){
    //addOneTab(tabname,href);
   /* var hrefnew = href;
    var re = "";
    var p1 = /\#\{(\w+)\}/g;
    try{
        var vars =hrefnew.match(p1);
        for(var i=0;i<vars.length;i++){
            var keyt = vars[i];
            var p2 = /\#\{(\w+)\}/g;
            var key = p2.exec(keyt);
            hrefnew =  hrefnew.replace(keyt,rec[key[1]]);
        }
    }catch(ex){
    }*/

	//var jsonString = {};
	//jsonString.content = value;
	var id = rec.id;
    var re = "";
	var dd = encodeURI(value);
    re += "<a href = '#'  onclick=\"viewdetail('"+id+"')\" ><u>"+value+"</u></a>";
    return re;
}
	function viewdetail(id){
       // value = decodeURI(value);

        $.dialog({
            content:"url:HlSoundRecordController.do?zixundetail&id="+id,
            title:"查看明细",
            zIndex:9999999999,
            width:600,
            height: 500,
            parent:windowapi,
            cache:false,
            cancelVal: '关闭',
            cancel: function(){
            }
        }).zindex();
	}
</script>
<div id="tt" class="easyui-tabs" style="width: 900px;height:500px;">
    <div title="全部人员" >
        <table width="100%"   id="${config_id}List" toolbar="#${config_id}Listtb"></table>
        <div id="${config_id}Listtb" style="padding:3px; height: auto">
            <div name="searchColums">
			<#list config_queryList  as x>
                <span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: auto; float:left; margin-left:15px; margin-top:5px; text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;" title="${x['field_txt']}">${x['field_txt']}：</span>
					<#if x['search_mode']=="group">
                        <input type="text" name="${x['field_name']}_begin"  style="width: 94px"  <#if x['field_type']=="Date">class="easyui-datebox"</#if> />
			<span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
			<input type="text" name="${x['field_name']}_end"  style="width: 94px" <#if x['field_type']=="Date">class="easyui-datebox"</#if> />
					</#if>
					<#if x['search_mode']=="single">
						<#if  (x['field_dictlist']?size >0)>
                            <select name = "${x['field_name']}" WIDTH="100" style="width: 104px">
                                <option value = "">---请选择---</option>
								<#list x['field_dictlist']  as xd>
                                    <option value = "${xd['typecode']}">${xd['typename']}</option>
								</#list>
                            </select>
						</#if>
						<#if  (x['field_dictlist']?size <= 0)>
                            <input type="text" name="${x['field_name']}"  style="width: 150px" <#if x['field_type']=="Date">class="easyui-datebox"</#if>  />
						</#if>
					</#if>
		</span>
			</#list>
			<#if  (config_queryList?size >0)>
                <span style="position:absolute;      top: 5px; right:10px; ">
			<a href="#" class="findbtn-new" iconCls="icon-search" onclick="${config_id}Listsearch()" title="查询">&nbsp;</a>
			<a href="#" class="resetbtn-new" iconCls="icon-reload" onclick="${config_id}searchReset('${config_id}List')" title="重置">&nbsp;</a>
		</span>
			</#if>
            </div>

        </div>
    </div>



</div>

