/**
 * 会签任务对象
 */
draw2d.SignTask=function(){
	draw2d.Task.call(this);
	this.taskId = "signTask" + indexId;
	this.taskName="会签任务";
	this.setTitle("会签任务");
	this.documentation="任务描述";
	this.expression = "${assignee}";
	this.setIcon();
};
draw2d.SignTask.prototype=new draw2d.Task();
draw2d.SignTask.prototype.type="draw2d.SignTask";
draw2d.SignTask.newInstance=function(signTaskXMLNode){
	var task = new draw2d.SignTask();
	task.id=signTaskXMLNode.attr('id');
	task.taskId=signTaskXMLNode.attr('id');
	task.taskName=signTaskXMLNode.attr('name');
	task.setContent(signTaskXMLNode.attr('name'));
	return task;
};
draw2d.SignTask.prototype.setWorkflow=function(_5019){
	draw2d.Task.prototype.setWorkflow.call(this,_5019);
};
draw2d.SignTask.prototype.getContextMenu=function(){
	var menu = draw2d.Task.prototype.getContextMenu.call(this);
  return menu;
};
draw2d.SignTask.prototype.setIcon = function(){
	var icon=draw2d.Task.prototype.setIcon.call(this);
	icon.className="Sign-task-icon";
};
draw2d.SignTask.prototype.getStartElementXML=function(){
	var name = this.taskId;
	var taskName = trim(this.taskName);
	if(taskName != null && taskName != "")
		name = taskName;
	var xml='<userTask id="'+this.taskId+'" name="'+name+'" ';
	if(this.isUseExpression){
		if(this.performerType=='assignee'){
			xml=xml+'activiti:assignee="'+this.expression+'" ';
		}else if(this.performerType=='candidateUsers'){
			xml=xml+'activiti:candidateUsers="'+this.expression+'" ';
		}else if(this.performerType=='candidateGroups'){
			xml=xml+'activiti:candidateGroups="'+this.expression+'" ';
		}
	}
	if(this.formKey != null && this.formKey != ""){
		xml=xml+'activiti:formKey="'+this.formKey+'" ';
	}
	xml=xml+'>\n';
	return xml;
};
draw2d.SignTask.prototype.getEndElementXML=function(){
	var xml = '</userTask>\n';
	return xml;
};
draw2d.SignTask.prototype.getDocumentationXML=function(){
	if(this.documentation==null||this.documentation=='')return '';
	var xml='<documentation>';
	xml=xml+this.documentation;
	xml=xml+'</documentation>';
	return xml;
};
draw2d.SignTask.prototype.getExtensionElementsXML=function(){
	var xml = '<extensionElements>\n';
		if(this.forms.getSize()!=0)
		{
			xml=xml+this.getFormsXML();
		}
		//if(this.listeners.getSize()!=0)
		//{
			xml=xml+this.getListenersXML();
		//}
		if(this.myforms.getSize()!=0)
		{
		 xml=xml+this.getMyFormXML();
		}
		if(this.actions.getSize()!=0){
			xml = xml + this.getActionXML();
		}
		if(this.mylisteners.getSize()!=0)
		{
		 xml=xml+this.getMyListenerXML();
		}
		if(this.users.getSize()!=0){
			xml=xml+this.getUserXML();
		}
	xml=xml+'</extensionElements>\n';
	return xml;
};
draw2d.SignTask.prototype.getListenersXML=function(){
//	var xml = '';
//	for(var i=0;i<this.listeners.getSize();i++){
//		var listener = this.listeners.get(i);
//		xml=xml+listener.toXML();
//	}
	var xml = '';
	xml=xml+'<activiti:taskListener id="createevent'+this.taskId+'" delegateExpression="${createTaskListener}" event="create"/>\n';
	xml=xml+'<activiti:taskListener id="assingmentevent'+this.taskId+'" delegateExpression="${assignTaskListener}" event="assignment"/>\n';
	xml=xml+'<activiti:taskListener id="completeevent'+this.taskId+'" delegateExpression="${completeTaskListener}" event="complete"/>\n';
	return xml;
};
/*流程引擎表单*/
draw2d.SignTask.prototype.getFormsXML=function(){
	var xml = '';
	for(var i=0;i<this.forms.getSize();i++){
		var form = this.forms.get(i);
		xml=xml+form.toXML();
	}
	return xml;
};
/*自定义表单*/
draw2d.SignTask.prototype.getMyFormXML=function(){
	var xml = '';
	for(var i=0;i<this.myforms.getSize();i++){
		var form = this.myforms.get(i);
		xml=xml+form.toXML();
	}
	return xml;
};
draw2d.SignTask.prototype.getActionXML=function(){
	var xml = '';
	for(var i=0;i<this.actions.getSize();i++){
		var action = this.actions.get(i);
		xml=xml+action.toXML();
	}
	return xml;
};
draw2d.SignTask.prototype.getUserXML = function(){
	var xml = '';
	for(var i=0;i<this.users.getSize();i++){
		var user = this.users.get(i);
		xml=xml+user.toXML();
	}
	return xml;
};
draw2d.SignTask.prototype.getMyListenerXML=function(){
	var xml = '';
	for(var i=0;i<this.mylisteners.getSize();i++){
		var listener = this.mylisteners.get(i);
		xml=xml+listener.toXML();
	}
	return xml;
};
draw2d.SignTask.prototype.getMultiInstanceXML=function(){
	var xml = '';
    xml+='<multiInstanceLoopCharacteristics  activiti:elementVariable="assignee" activiti:collection="${activitiService.getSignUser(execution)}" isSequential="'+this.isSequential+'"> ';
	xml+='<completionCondition>${activitiService.isComplete(execution,"'+this.signType+'","'+this.signNum+'") }</completionCondition>';
	xml+='</multiInstanceLoopCharacteristics>';
	return xml;
};
draw2d.SignTask.prototype.getPerformersBPMNXML=function(){
	var xml = '';
	if(this.performerType=='candidateUsers'){
		if(this.candidateUsers.getSize()!=0){
			xml=xml+'<potentialOwner>\n';
			xml=xml+'<resourceAssignmentExpression>\n';
			xml=xml+'<formalExpression>\n';
			xml=xml+'<![CDATA[';
			for(var i=0;i<this.candidateUsers.getSize();i++){
				var user = this.candidateUsers.get(i);
				xml=xml+'user('+user.sso+'),';
			}
			xml=xml.substring(0, xml.length-1);
			xml=xml+']]>\n';
			xml=xml+'</formalExpression>\n';
			xml=xml+'</resourceAssignmentExpression>\n';
			xml=xml+'</potentialOwner>\n';
		}
	}else if(this.performerType=='candidateGroups'){
		if(this.candidateGroups.getSize()!=0){
			xml=xml+'<potentialOwner>\n';
			xml=xml+'<resourceAssignmentExpression>\n';
			xml=xml+'<formalExpression>\n';
			xml=xml+'<![CDATA[';
			for(var i=0;i<this.candidateGroups.getSize();i++){
				var group = this.candidateGroups.get(i);
				xml=xml+'group('+group+'),';
			}
			xml=xml.substring(0, xml.length-1);
			xml=xml+']]>\n';
			xml=xml+'</formalExpression>\n';
			xml=xml+'</resourceAssignmentExpression>\n';
			xml=xml+'</potentialOwner>\n';
		}
	}
	return xml;
};
draw2d.SignTask.prototype.toXML=function(type){
	var xml=this.getStartElementXML();
	xml=xml+this.getDocumentationXML();
	xml=xml+this.getExtensionElementsXML();
	xml=xml+this.getMultiInstanceXML();
	xml=xml+this.getPerformersBPMNXML();
	xml=xml+this.getEndElementXML();
	return xml;
};
draw2d.SignTask.prototype.toBpmnDI=function(){
	var w=this.getWidth();
	var h=this.getHeight();
	var x=this.getAbsoluteX();
	var y=this.getAbsoluteY();
	var xml='<bpmndi:BPMNShape bpmnElement="'+this.taskId+'" id="BPMNShape_'+this.taskId+'">\n';
	xml=xml+'<omgdc:Bounds height="'+h+'" width="'+w+'" x="'+x+'" y="'+y+'"/>\n';
	xml=xml+'</bpmndi:BPMNShape>\n';
	return xml;
};