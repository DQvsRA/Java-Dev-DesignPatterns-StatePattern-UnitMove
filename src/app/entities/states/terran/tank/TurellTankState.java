package app.entities.states.terran.tank;

import app.behavior.unit.move.StandUnitMoveBehavior;
import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.TankState;
import app.interfaces.behavior.IMoveBehavior;
import controlP5.Controller;
import hype.H;

public class TurellTankState extends TankState
{
	public TurellTankState(Unit unit, BaseUnitState prevState) { super(unit, prevState); }
	
	public enum STATES { _BACK_TO_TANK }

	public String getUnitType() 	{ return TankState.STATES.TURELL.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return H.GREEN; }
	public int getStrokeSize() 		{ return 10; }
	public int getRadius() 			{ return 70; }
	public int getSpeed() 			{ return 0; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Turell Tank States:", STATES.values()); }
	
	public boolean transitionToState(int stateID)
	{
		this.getUnit().setState(this.getPrevState());
		destroy();
		return true;
	}
	
	@Override 
	public IMoveBehavior getMoveBehaviour() { return new StandUnitMoveBehavior(this); }
}
