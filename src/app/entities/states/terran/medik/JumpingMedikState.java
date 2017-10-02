package app.entities.states.terran.medik;

import app.behavior.unit.move.JumpUnitMoveBehavior;
import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.MedikState;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.behavior.IUpdataBehavior;
import controlP5.Controller;

public class JumpingMedikState extends MedikState
{
	public enum STATES { _BACK_TO_MEDIK }
	
	public JumpingMedikState(Unit unit, BaseUnitState prevState) { super(unit, prevState); }

	private JumpUnitMoveBehavior _moveUpdateBehavior;
	protected void setup() {
		_moveUpdateBehavior = new JumpUnitMoveBehavior(this);
	}
	
	public String getUnitType() 	{ return super.getUnitType() + " - " + MedikState.STATES.JUMPING.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return super.getStrokeColor(); }
	public int getStrokeSize() 		{ return 5; }
	public int getRadius() 			{ return super.getRadius(); }
	public int getSpeed() 			{ return 10; }
	
	public Controller<?> getGUI() 	{ return BuildDropdownList("Jumping Medik States:", STATES.values()); }
	
	public boolean transitionToState(int stateID) {
		this.getUnit().setState(this.getPrevState());
		destroy();
		return true;
	}
	
	public IUpdataBehavior getUpdateBehaviour() { return _moveUpdateBehavior; }
	public IMoveBehavior getMoveBehaviour() { return _moveUpdateBehavior; }
}
