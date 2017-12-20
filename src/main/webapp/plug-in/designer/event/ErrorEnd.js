draw2d.ErrorEnd = function() {
  _url = "/js/designer/icons/enderror.png";
  draw2d.ResizeImage.call(this, _url);
  this.rightInputPort = null;
  this.leftInputPort = null;
  this.topInputPort = null;
  this.bottomInputPort = null;
  this.resizeable = false;// 不让改变大小
  this.eventId = 'ErrorEnd';
  this.eventName = "错误";
  this.setDimension(40, 40);
};
draw2d.ErrorEnd.prototype = new draw2d.Node();
draw2d.ErrorEnd.prototype.type = "draw2d.ErrorEnd";
draw2d.ErrorEnd.prototype.generateId = function() {
  this.id = "ErrorEnd";
  this.eventId = this.id;
};
draw2d.ErrorEnd.prototype.createHTMLElement = function() {
  var item = draw2d.ResizeImage.prototype.createHTMLElement.call(this);
  return item;
};
draw2d.ErrorEnd.prototype.setDimension = function(w, h) {
  draw2d.ResizeImage.prototype.setDimension.call(this, w, h);
};
draw2d.ErrorEnd.prototype.setWorkflow = function(_505d) {
  draw2d.ResizeImage.prototype.setWorkflow.call(this, _505d);
  if (_505d !== null && this.rightInputPort === null) {
    this.rightInputPort = new draw2d.MyInputPort();
    this.rightInputPort.setName("RightInputPort");
    this.rightInputPort.setWorkflow(_505d);
    this.rightInputPort.setBackgroundColor(new draw2d.Color(115, 115, 245));
    this.addPort(this.rightInputPort, this.width, this.height / 2);
  }
  if (_505d !== null && this.leftInputPort === null) {
    this.leftInputPort = new draw2d.MyInputPort();
    this.leftInputPort.setName("leftInputPort");
    this.leftInputPort.setWorkflow(_505d);
    this.leftInputPort.setBackgroundColor(new draw2d.Color(115, 115, 245));
    this.addPort(this.leftInputPort, 0, this.height / 2);
  }
  if (_505d !== null && this.topInputPort === null) {
    this.topInputPort = new draw2d.MyInputPort();
    this.topInputPort.setName("RightInputPort");
    this.topInputPort.setWorkflow(_505d);
    this.topInputPort.setBackgroundColor(new draw2d.Color(115, 115, 245));
    this.addPort(this.topInputPort, this.width / 2, 0);
  }
  if (_505d !== null && this.bottomInputPort === null) {
    this.bottomInputPort = new draw2d.MyInputPort();
    this.bottomInputPort.setName("RightInputPort");
    this.bottomInputPort.setWorkflow(_505d);
    this.bottomInputPort.setBackgroundColor(new draw2d.Color(115, 115, 245));
    this.addPort(this.bottomInputPort, this.width / 2, this.height);
  }
};
draw2d.ErrorEnd.prototype.figureDoubleClick = function() {
	if(!workflow.readOnly){
  processNode = this;
  Ext.getCmp('formSetting').hide();
  Ext.getCmp('actionSetting').hide();
  Ext.getCmp('listenerSetting').hide();
  Ext.getCmp('delegateSetting').hide();
  setPropertyNames();
  setSource();
	}
};
draw2d.ErrorEnd.prototype.getContextMenu = function() {
  if (this.workflow.disabled) return null;
  var menu = new draw2d.ContextMenu(100, 50);
  processNode = this;
  menu.appendMenuItem(new draw2d.ContextMenuItem("属性", "properties-icon", processNode, function(x, y) {
   
    setPropertyNames();
    setSource();
  }));
  
  return menu;
};
draw2d.ErrorEnd.prototype.toXML = function() {
  var xml = '<endEvent id="' + this.eventId + '" name="' + this.eventName + '"><errorEventDefinition errorRef="error123" /></endEvent>\n';
  return xml;
};
draw2d.ErrorEnd.prototype.toBpmnDI = function() {
  var w = this.getWidth();
  var h = this.getHeight();
  var x = this.getAbsoluteX();
  var y = this.getAbsoluteY();
  var xml = '<bpmndi:BPMNShape bpmnElement="' + this.eventId + '" id="BPMNShape_' + this.eventId + '">\n';
  xml = xml + '<omgdc:Bounds height="' + h + '" width="' + w + '" x="' + x + '" y="' + y + '"/>\n';
  xml = xml + '</bpmndi:BPMNShape>\n';
  return xml;
};
