package app.behavior.unit.move;

import java.awt.Point;

import app.entities.Unit;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.behavior.IUpdataBehavior;
import app.interfaces.unit.IUnitState;

public class JumpUnitMoveBehavior implements IMoveBehavior, IUpdataBehavior
{
	private IUnitState _unitState;

	private Boolean _jumping = false;
	private Point _jumpDestinationPosition;
	private int _originalRadius;
	
	public JumpUnitMoveBehavior(IUnitState unitState) {
		this._unitState = unitState;
	}

	@Override
	public void move(int toX, int toY) 
	{
		if(_jumping) return;
		_jumpDestinationPosition = new Point(toX, toY);
		_originalRadius = _unitState.getRadius();
		
		_jumping = true;
		
		System.out.println(_unitState.getUnitType() + " > JumpUnitMoveBehavior -> move to " + toX + "x" + toY + "; _originalRadius = " + _originalRadius );
	}

	@Override
	public void update(int deltaTime) 
	{
		if(_jumping && deltaTime > 0)
		{
			Unit unit = _unitState.getUnit();
			int increament = _originalRadius / (int)(deltaTime * 1f);
			int radius = unit.getRadius();
			radius = radius + (_jumpDestinationPosition != null ? -increament : increament);
			if(radius >= _originalRadius) {
				radius = _originalRadius;
				_jumping = false;
				unit.OnMoveComplete();
			}
			else if(radius < 0) 
			{
				unit.x = _jumpDestinationPosition.x;
				unit.y = _jumpDestinationPosition.y;
				_jumpDestinationPosition = null;
				radius = 0;
			}
			unit.setRadius(radius);
		}
	}
	
	public void interapt() {
		if(_unitState != null) _unitState.getUnit().setRadius(_originalRadius);
	}
	
	public boolean isJumpPossible() { return _jumping; }

	public boolean possible() { return _jumping == false; }
}
