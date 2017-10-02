package app.entities.states.terran.marine;

import app.behavior.unit.move.StandUnitMoveBehavior;
import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.MarineState;
import app.interfaces.behavior.IMoveBehavior;
import controlP5.Controller;
import hype.H;

public class TrenchMarineState extends MarineState
{
	public enum STATES { _BACK_TO_MARINE }

	public TrenchMarineState(Unit unit, BaseUnitState prevState) { 
		super(unit, prevState); 
	}
	
	public String getUnitType() { return MarineState.STATES.TRENCH.name(); }
	public int getColor() 		{ return super.getColor(); }
	public int getStrokeColor() { return H.BLUE; }
	public int getStrokeSize() 	{ return super.getStrokeSize(); }
	public int getRadius() 		{ return super.getRadius(); }
	public int getSpeed() 		{ return 0; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Trench Marin States:", STATES.values()); }
	
	public boolean transitionToState(int stateID)
	{
		this.getUnit().setState(this.getPrevState());
		destroy();
		return true;
	}
	
	@Override 
	public IMoveBehavior getMoveBehaviour() { return new StandUnitMoveBehavior(this); }
}
