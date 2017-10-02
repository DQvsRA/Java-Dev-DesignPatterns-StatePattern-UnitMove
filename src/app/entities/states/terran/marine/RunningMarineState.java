package app.entities.states.terran.marine;

import app.behavior.unit.move.WalkUnitMoveBehavior;
import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.MarineState;
import app.interfaces.behavior.IMoveBehavior;
import controlP5.Controller;
import hype.H;

public class RunningMarineState extends MarineState
{
	public RunningMarineState(Unit unit, BaseUnitState prevState) { super(unit, prevState); }
	
	public enum STATES { _BACK_TO_MARINE }

	public String getUnitType() 	{ return MarineState.STATES.RUNNING.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return H.MAGENTA; }
	public int getStrokeSize() 		{ return 2; }
	public int getRadius() 			{ return super.getRadius(); }
	public int getSpeed() 			{ return 600; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Running Marin States:", STATES.values()); }
	
	public boolean transitionToState(int stateID)
	{
		this.getUnit().setState(this.getPrevState());
		destroy();
		return true;
	}
	
	@Override 
	public IMoveBehavior getMoveBehaviour() { return new WalkUnitMoveBehavior(this); }
}
