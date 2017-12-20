/**
 * 设置流程属性
 */
setPropertyNames = function() {
	var type = processNode.type;
	if (type == "MyCanvas") {
		pn.id = "ID";
		pn.name = myDesktopApp.i18n.processName;
		pn.category = myDesktopApp.i18n.nameSpace;
		pn.documentation = myDesktopApp.i18n.processDescription;
		ce.id = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.name = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.category = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.documentation = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
	}
	if (type == "draw2d.Start" || type == "draw2d.End"
			|| type == "draw2d.CancelEnd" || type == "draw2d.ErrorEnd") {
		pn.eventId = "ID";
		pn.eventName = myDesktopApp.i18n.Name;
		pn.expression = myDesktopApp.i18n.startPersion;
		ce.eventId = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.eventName = Ext.create('Ext.form.field.Text', {});
		ce.expression = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
	}
	if (type == "draw2d.UserTask" || type == "draw2d.SignTask") {
		pn.taskId = "ID";
		pn.taskName = myDesktopApp.i18n.Name;
		pn.documentation = myDesktopApp.i18n.description;
		pn.performerType = myDesktopApp.i18n.persionType;
		pn.expression = myDesktopApp.i18n.joinPersion;
		pn.formId = myDesktopApp.i18n.Form;
		pn.signNum = myDesktopApp.i18n.signNum;
		if (processNode.taskId.indexOf('signTask') != -1) {
			pn.signType = myDesktopApp.i18n.signType;
			pn.isSequential = myDesktopApp.i18n.runStatus;
			ce.signType = Ext.create('Ext.form.ComboBox', {
						valueField : 'value',
						displayField : 'name',
						store : this.getSignTypeStore(),
						listeners : {
							change : function(obj, newVal, oldVal) {
								if (newVal == 'passByProportion') {
									workflowpg.setProperty("signNum",
											processNode.signNum, true);
								} else {
									workflowpg.removeProperty("signNum");
								}
							}
						}
					});
			ce.isSequential = Ext.create('Ext.form.ComboBox', {
						valueField : 'value',
						displayField : 'name',
						store : this.getStateStore()
					});
		}
		ce.formId = Ext.create('Ext.form.field.Text', {});
		ce.taskId = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.taskName = Ext.create('Ext.form.field.Text', {});
		ce.documentation = Ext.create('Ext.form.field.Text', {});
		ce.performerType = Ext.create('Ext.form.ComboBox', {
					valueField : 'value',
					displayField : 'name',
					store : this.getPerformerStore()
				});
		if (type == "draw2d.SignTask") {
			ce.performerType.setReadOnly(true);
		}
		ce.expression = Ext.create('Ext.form.field.Text', {
			listeners : {
				focus : function(obj, e, eOpts) {
					var performerType = processNode.performerType;
					var sw;
					if (performerType == "assignee") {
						sw = Ext.create('baseUx.selector.UserSelectorFront', {
									issingle : true,
									myvalue : processNode.expression,
									// data返回的是json类型的数据
									uswcallback : function(data) {
										data = Ext.decode(data);
										if (data) {
											obj.setValue(data[0].accountName);
											workflowpg.setProperty(
													"expression",
													data[0].accountName);
										}
									}
								});
					}
					if (performerType == "candidateGroups") {
						sw = Ext.create('baseUx.selector.RoleSelector', {
									issingle : true,
									uswcallback : function(data) {
										source.expression = data.roles[0].name;
										processNode.expression = data.roles[0].name;
										workflowpg.setSource(source);
									}
								});
					}
					if (performerType == "candidateUsers") {
						sw = Ext.create('baseUx.selector.UserSelectorFront', {
									myvalue : processNode.expression,
									// data返回的是json类型的数据
									uswcallback : function(data) {
										var result = '';
										data = Ext.decode(data);
										if (data) {
											for (var i = 0; i < data.length; i++) {
												result += data[i].accountName
														+ ',';
											}
											result = result.substring(0,
													result.length - 1);
											obj.setValue(result);
											workflowpg.setProperty(
													"expression", result);
										}
									}
								});
					}
					sw.show();
				}
			}
		});
		if (type == "draw2d.SignTask") {
			ce.expression.suspendEvents(true);
			ce.expression.setReadOnly(true);
		}
	}
	if (type == "draw2d.DecoratedConnection") {
		pn.lineId = "ID";
		pn.lineName = myDesktopApp.i18n.note;
		pn.condition = myDesktopApp.i18n.condition;
		ce.lineId = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.lineName = Ext.create('Ext.form.field.Text', {});
		ce.condition = Ext.create('Ext.form.ComboBox', {
					store : this.getHandleStore(),
					valueField : 'shortClassName',
					displayField : 'handleName',
					multiSelect : true
				});
	}
	if (type == "draw2d.ExclusiveGateway" || type == "draw2d.ParallelGateway") {
		pn.gatewayId = "ID";
		pn.gatewayName = myDesktopApp.i18n.Name;
		ce.gatewayId = Ext.create('Ext.form.field.Text', {
					readOnly : true
				});
		ce.gatewayName = Ext.create('Ext.form.field.Text', {});
	}
};
/* 设置属性数据 */
setSource = function() {
	Ext.getCmp('assignmentlistenertxt').setValue('');
	Ext.getCmp('createlistenertxt').setValue('');
	Ext.getCmp('completelistenertxt').setValue('');
	Ext.getCmp('actioncombo').setValue('');
	Ext.getCmp('formtxt').setValue('');
	Ext.getCmp('formpg').setSource({});
	Ext.getCmp('delegatestr').setValue('');
	var type = processNode.type;
	source = {};
	if (type == "MyCanvas") {
		source.id = workflow.process.id;
		source.name = workflow.process.name;
		source.category = workflow.process.category;
		source.documentation = workflow.process.documentation;
	}
	nodeId = processNode.id;
	if (type == "draw2d.Start" || type == "draw2d.End"
			|| type == "draw2d.CancelEnd" || type == "draw2d.ErrorEnd") {
		source.eventId = processNode.eventId;
		source.eventName = processNode.eventName;
		source.expression = processNode.expression;
		workflowpg.customRenderers = {
			expression : function(v) {
				if (v = "applyUserId") {
					return "系统内置参数";
				}
			}
		};
	}
	if (type == "draw2d.UserTask" || type == "draw2d.SignTask") {
		source.taskId = processNode.taskId;
		source.taskName = processNode.taskName;
		source.documentation = processNode.documentation;
		source.performerType = processNode.performerType;
		source.expression = processNode.expression;
		source.formId = processNode.formId;
		if (processNode.taskId.indexOf('signTask') != -1) {
			source.isSequential = processNode.isSequential;
			source.signType = processNode.signType;
		}
		workflowpg.customRenderers = {
			performerType : function(v) {
				if (v == "assignee")
					return "办理人";
				if (v == "candidateUsers")
					return "备选人员";
				if (v == "candidateGroups")
					return "备选角色";
			},
			isSequential : function(v) {
				if (v == "true") {
					return '串行';
				}
				if (v == "false") {
					return '并行';
				}
			},
			signType : function(v) {
				if (v == 'oneVoteVeto') {
					return '一票否决';
				} else if (v == 'oneVotePass') {
					return '一票通过';
				} else if (v == 'passByProportion') {
					return '按比例通过';
				}
			},
			expression : function(v) {
				if(v=='${currentuser}'){
					return '发起人';
				}
				var result = "";
				if (source.performerType == "assignee") {
					Ext.Ajax.request({
								async : false,
								url : '/user/getusernamebyassignee',
								params : {
									accountName : v
								},
								success : function(response) {
									result = response.responseText;
								}
							});
				} else if (source.performerType == "candidateUsers") {
					Ext.Ajax.request({
								async : false,
								url : '/user/getusernamebycandidateusers',
								params : {
									accountName : v
								},
								success : function(response) {
									result = response.responseText;
								}
							});
				} else if (source.performerType == "candidateGroups") {
					Ext.Ajax.request({
								async : false,
								url : '/user/getusernamebycandidategroups',
								params : {
									roleName : v
								},
								success : function(response) {
									result = response.responseText;
								}
							});
				}
				return result;
			}
		};
		if (processNode.myforms.getSize() > 0) {
			var form = processNode.myforms.get(0);
			formtxt.setValue(form.name);
			formpg.setFormProperties(JSON.parse(form.config
					.replace(/\'/g, '\"')));
		}
		if (processNode.users.getSize() > 0) {
			var user = processNode.users.get(0);
			var arr = Ext.decode(user.value, true), names = '';
			if (arr) {
				for (var i = 0; i < arr.length; ++i) {
					names = names + arr[i].userName + ',';
				}
				delegatestr.setValue(names.substring(0, names.length - 1));
			}
		}
		if (processNode.mylisteners.getSize() > 0) {
			var listener = processNode.mylisteners;
			for(var i=0;i<listener.size;i++){
				var nameArray = listener.get(i).name.split(',');
				for(var j=0;j<nameArray.length;j++){
					if(j==0){
						Ext.getCmp('assignmentlistenertxt').setValue(nameArray[j].replace(/\'/g,""));
					}else if(j==1){
						Ext.getCmp('createlistenertxt').setValue(nameArray[j].replace(/\'/g,""));
					}else if(j==2){
						Ext.getCmp('completelistenertxt').setValue(nameArray[j].replace(/\'/g,""));
					}
				}
			}
			
		}
		if (processNode.actions.getSize() > 0) {
			var action = processNode.actions.get(0);
			Ext.getCmp('actioncombo').setValue(action.id);
		}

	}
	if (type == "draw2d.DecoratedConnection") {
		source.lineId = processNode.lineId;
		source.lineName = processNode.lineName;
		if (typeof processNode.condition == 'string') {
			source.condition = processNode.condition;
		} else {
			source.condition = '';
			if (processNode.condition && processNode.condition.length > 0) {
				for (var i = 0; i < processNode.condition.length; i++) {
					var con = processNode.condition[i];
					source.condition += con + ',';
				}
			}
			source.condition = source.condition.substring(0,
					source.condition.length - 1);
			processNode.condition = source.condition;
		}
		workflowpg.customRenderers = {
			condition : function(v) {
				var newhandlers = '';
				Ext.Ajax.request({
					url : '/handler/manage',
					async : false,// 同步请求数据
					success : function(response) {
						var myhandlers = Ext.decode(response.responseText);
						if (typeof v == 'string') {
							v = (v.trim()).split(',');
						}
						for (var i = 0; i < v.length; i++) {
							for (var j = 0; j < myhandlers.handlers.length; j++) {
								if (myhandlers.handlers[j].shortClassName
										.trim() == v[i].trim()) {
									newhandlers += myhandlers.handlers[j].handleName
											+ ',';
								}
							}
						}
					}
				});
				return newhandlers.substring(0, newhandlers.length - 1);
			}
		};
	}
	if (type == "draw2d.ExclusiveGateway" || type == "draw2d.ParallelGateway") {
		source.gatewayId = processNode.gatewayId;
		source.gatewayName = processNode.gatewayName;
	}
	workflowpg.setSource(source);
};
/* 属性表格数据改变事件 */
startPnChange = function(source, recordId, value, oldValue, eOpts) {
	var type = processNode.type;
	processNode[recordId] = value;
	if (type == "draw2d.UserTask") {
		processNode.setId(processNode['taskId']);
		processNode.setContent(processNode['taskName']);
		processNode.isUseExpression = true;
		processNode.performerType = processNode['performerType'];
		processNode.expression = processNode['expression'];
		processNode.formId = processNode['formId'];
	}
	if (type == 'draw2d.SignTask') {
		processNode.setId(processNode['taskId']);
		processNode.setContent(processNode['taskName']);
		processNode.isUseExpression = true;
		processNode.performerType = processNode['performerType'];
		processNode.expression = processNode['expression'];
		processNode.formId = processNode['formId'];
		processNode.signNum = processNode['signNum'];
	}
	if (type == "draw2d.DecoratedConnection") {
		processNode.setLabel(processNode['lineName']);
		processNode.condition = processNode['condition'];
	}
};

getPerformerStore = function() {
	var performerStore = Ext.create('Ext.data.Store', {
				fields : [{
							name : 'name',
							type : 'string'
						}, {
							name : 'value',
							type : 'string'
						}],
				data : [{
							name : myDesktopApp.i18n.assignee,
							value : 'assignee'
						}, {
							name : myDesktopApp.i18n.candidateUsers,
							value : 'candidateUsers'
						}, {
							name : myDesktopApp.i18n.candidateGroups,
							value : 'candidateGroups'
						}]
			});
	return performerStore;
};

getSignTypeStore = function() {
	var signTypeStore = Ext.create('Ext.data.Store', {
				fields : [{
							name : 'name',
							type : 'string'
						}, {
							name : 'value',
							type : 'string'
						}],
				data : [{
							name : myDesktopApp.i18n.oneVoteVeto,
							value : 'oneVoteVeto'
						}, {
							name : myDesktopApp.i18n.oneVotePass,
							value : 'oneVotePass'
						}, {
							name : myDesktopApp.i18n.passByProportion,
							value : 'passByProportion'
						}]
			});
	return signTypeStore;
};

getStateStore = function() {
	var stateStore = Ext.create('Ext.data.Store', {
				fields : [{
							name : 'name',
							type : 'string'
						}, {
							name : 'value',
							type : 'string'
						}],
				data : [{
							name : myDesktopApp.i18n.serial,
							value : 'true'
						}, {
							name : myDesktopApp.i18n.parallel,
							value : 'false'
						}]
			});
	return stateStore;
};
getHandleStore = function() {
	var handleStore = Ext.create('Ext.data.Store', {
				fields : [{
							name : 'handleName',
							type : 'string'
						}, {
							name : 'shortClassName',
							type : 'string'
						}],
				proxy : {
					type : 'ajax',
					url : '/handle/manage',
					reader : {
						type : 'json',
						root : 'handles',
						totalProperty : 'totalCount'
					},
					writer : {
						type : 'json'
					}
				}
			});
	return handleStore;
};