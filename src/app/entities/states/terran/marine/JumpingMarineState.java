package app.entities.states.terran.marine;

import app.behavior.unit.move.JumpUnitMoveBehavior;
import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.MarineState;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.behavior.IUpdataBehavior;
import controlP5.Controller;
import hype.H;

public class JumpingMarineState extends MarineState implements IUpdataBehavior, IMoveBehavior
{
	public enum STATES { _BACK_TO_MARINE }
	
	private JumpUnitMoveBehavior _moveUpdateBehavior;
	
	public JumpingMarineState(Unit unit, BaseUnitState prevState) { super(unit, prevState); }

	protected void setup() {
		_moveUpdateBehavior = new JumpUnitMoveBehavior(this);
	}
	
	public String getUnitType() 	{ return super.getUnitType() + " - " + MarineState.STATES.JUMPING.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return H.GREEN; }
	public int getStrokeSize() 		{ return 5; }
	public int getRadius() 			{ return super.getRadius(); }
	public int getSpeed() 			{ return 1; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Jumping Marin States:", STATES.values()); }
	
	public boolean transitionToState(int stateID)
	{
		this.getUnit().setState(this.getPrevState());
		destroy();
		return true;
	}
	
	public void destroy() {
		super.destroy();
		_moveUpdateBehavior = null;
	}
	
	@Override
	public void move(int toX, int toY) 
	{
		System.out.println(this.getUnitType() + " > additional move call: " + toX + "x" + toY );
	}
 
	@Override
	public void update(int deltaTime) 
	{
		if(_moveUpdateBehavior.isJumpPossible())
			System.out.println(this.getUnitType() + " > additional update call: " + deltaTime );
	}
	
	@Override // IHaveBehaviors
	public IUpdataBehavior getUpdateBehaviour() { return _moveUpdateBehavior; }
	@Override // IHaveBehaviors
	public IMoveBehavior getMoveBehaviour() { return _moveUpdateBehavior; }

	@Override
	public void interapt() { _moveUpdateBehavior.interapt(); }

	@Override
	public boolean possible() { return _moveUpdateBehavior.possible(); }
}
