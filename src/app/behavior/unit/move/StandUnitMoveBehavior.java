package app.behavior.unit.move;

import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.unit.IUnitState;

public class StandUnitMoveBehavior implements IMoveBehavior 
{
	private IUnitState _unitState;

	public StandUnitMoveBehavior(IUnitState unitState) {
		this._unitState = unitState; 
	}

	@Override
	public void move(int toX, int toY) 
	{
		System.out.println(_unitState.getUnitType() + " > Stand Move Behaviour");
	}
	
	@Override
	public void interapt() { }
	public boolean possible() { return false; }
}
