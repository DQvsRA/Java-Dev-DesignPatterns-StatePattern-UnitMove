package app.behavior.unit.move;

import java.awt.Point;

import app.entities.Unit;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.unit.IUnitState;
import de.looksgood.ani.Ani;

public class WalkUnitMoveBehavior implements IMoveBehavior 
{
	private IUnitState _unitState;
	
	private Ani _xpos, _ypos;
	
	public WalkUnitMoveBehavior(IUnitState unitState) {
		this._unitState = unitState;
	}

	@Override
	public void move(int toX, int toY) 
	{
		int speed = _unitState.getSpeed();
		if(speed > 0)
		{
			Unit unit = _unitState.getUnit();
			float distance = (float)Math.abs(Point.distance(unit.x, unit.y, toX, toY));
			float time = distance / speed;
	
			if(time < 0.1) time = 0.1f;
		
			_ypos = Ani.to(unit, time, "y", toY, Ani.LINEAR);
			_xpos = Ani.to(unit, time, "x", toX, Ani.LINEAR, "onEnd:OnMoveComplete");
		}
	}

	@Override
	public void interapt() {
		if(_ypos != null && _ypos.isPlaying()) {
			_ypos.pause();
			_ypos.setEnd(_ypos.getPosition());
			_ypos.end();
		}
		if(_xpos != null && _xpos.isPlaying()) {
			_xpos.pause();
			_xpos.setEnd(_xpos.getPosition());
			_xpos.end();
		}
	}
	public boolean possible() { return true; }
}
