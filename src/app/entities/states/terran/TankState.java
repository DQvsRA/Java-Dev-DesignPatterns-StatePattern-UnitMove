package app.entities.states.terran;

import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.tank.TurellTankState;
import app.interfaces.unit.IUnitState;
import controlP5.Controller;
import hype.H;

public class TankState extends BaseUnitState
{
	public enum STATES {
		TURELL,
		_BACK
	}
	
	public TankState(Unit unit, BaseUnitState prevState) {
		super(unit, prevState);
		setColor(H.LGREY);
	}

	public boolean continiousMove() { return false; }
	public String getUnitType() 	{ return BaseUnitState.STATES.TANK.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return H.RED; }
	public int getStrokeSize() 		{ return 2; }
	public int getRadius() 			{ return 40; }
	public int getSpeed() 			{ return 150; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Tank Modes:", STATES.values()); }
	
	public boolean transitionToState(int stateID) 
	{
		STATES state = STATES.values()[stateID];
		boolean stateExist = state != null;
		System.out.println(this.getUnitType() + " > transition to state: " + stateID + "|" + state + "|" + stateExist);
		if(stateExist) {
			Unit unit = this.getUnit();
			IUnitState nextState;
			switch (state) 
			{
				case TURELL: 	nextState = new TurellTankState(unit, this); break;
				case _BACK: 	nextState = getPrevState(); break;
				default:		nextState = this; break;
			}
			unit.setState(nextState);
		}
		return stateExist;
	}
}
