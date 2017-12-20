draw2d.ButtonMoveBack = function(_465c) {
	this.imagePath = '/js/designer/draw2d/';
	draw2d.Button.call(this, _465c, 16, 16);
};
draw2d.ButtonMoveBack.prototype = new draw2d.Button;
draw2d.ButtonMoveBack.prototype.type = "ButtonMoveBack";
draw2d.ButtonMoveBack.prototype.getImageUrl = function() {
	return this.imagePath+this.type + ".png";
};
draw2d.ButtonMoveBack.prototype.execute = function() {
	this.palette.workflow.moveBack(this.palette.workflow.getCurrentSelection());
	ToolGeneric.prototype.execute.call(this);
};
