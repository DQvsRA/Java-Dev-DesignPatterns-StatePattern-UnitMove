package app.entities.states.terran;

import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.marine.JumpingMarineState;
import app.entities.states.terran.marine.RunningMarineState;
import app.entities.states.terran.marine.TrenchMarineState;
import app.interfaces.unit.IUnitState;
import controlP5.Controller;
import hype.H;

public class MarineState extends BaseUnitState
{	
	public enum STATES {
		TRENCH,
		RUNNING,
		JUMPING,
		_BACK
	}
	
	public MarineState(Unit unit, BaseUnitState prevState) {
		super(unit, prevState);
		setColor(H.BLUE);
	}

	public boolean continiousMove() { return false; }
	public String getUnitType() 	{ return BaseUnitState.STATES.MARINE.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return H.WHITE; }
	public int getStrokeSize() 		{ return 3; }
	public int getRadius() 			{ return 30; }
	public int getSpeed() 			{ return 200; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Marin States:", STATES.values()); }
	
	public boolean transitionToState(int stateID) 
	{
		MarineState.STATES state = MarineState.STATES.values()[stateID];
		boolean stateExist = state != null;
		System.out.println(this.getUnitType() + " > transition to state: " + state + " | " + stateID + " | " + stateExist);
		if(stateExist) {
			Unit unit = this.getUnit();
			IUnitState nextState;
			switch (state) 
			{
				case TRENCH: 	nextState = new TrenchMarineState(unit, this); break;
				case RUNNING: 	nextState = new RunningMarineState(unit, this); break;
				case JUMPING: 	nextState = new JumpingMarineState(unit, this); break;
				
				case _BACK: 	nextState = this.getPrevState(); destroy();	break;
				default: 		nextState = this; break;
			}
			unit.setState(nextState);
		}
		return stateExist;
	}
}
