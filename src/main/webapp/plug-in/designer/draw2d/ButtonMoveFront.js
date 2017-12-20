draw2d.ButtonMoveFront = function(_4296) {
	this.imagePath = '/js/designer/draw2d/';
	draw2d.Button.call(this, _4296, 16, 16);
};
draw2d.ButtonMoveFront.prototype = new draw2d.Button;
draw2d.ButtonMoveFront.prototype.type = "ButtonMoveFront";
draw2d.ButtonMoveFront.prototype.getImageUrl = function() {
	return this.imagePath+this.type + ".png";
};
draw2d.ButtonMoveFront.prototype.execute = function() {
	this.palette.workflow
			.moveFront(this.palette.workflow.getCurrentSelection());
	ToolGeneric.prototype.execute.call(this);
};
