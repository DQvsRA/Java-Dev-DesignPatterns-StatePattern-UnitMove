package app.entities.states.terran;

import app.entities.Unit;
import app.entities.states.BaseUnitState;
import app.entities.states.terran.medik.JumpingMedikState;
import app.entities.states.terran.medik.TrenchMedikState;
import app.interfaces.unit.IUnitState;
import controlP5.Controller;
import hype.H;

public class MedikState extends BaseUnitState
{
	public enum STATES {
		TRENCH,
		JUMPING,
		_BACK
	}
	
	public MedikState(Unit unit, BaseUnitState prevState) {
		super(unit, prevState);
		setColor(H.MAGENTA);
	}

	public boolean continiousMove() { return false; }
	public String getUnitType() 	{ return BaseUnitState.STATES.MEDIK.name(); }
	public int getColor() 			{ return super.getColor(); }
	public int getStrokeColor() 	{ return H.RED; }
	public int getStrokeSize() 		{ return 5; }
	public int getRadius() 			{ return 40; }
	public int getSpeed() 			{ return 150; }
	
	public Controller<?> getGUI() { return BuildDropdownList("Medik States:", STATES.values()); }
	
	public boolean transitionToState(int stateID) 
	{
		MedikState.STATES state = MedikState.STATES.values()[stateID];
		boolean stateExist = state != null;
		System.out.println(this.getUnitType() + " > transition to state: " + stateID + "|" + state + "|" + stateExist);
		if(stateExist) {
			Unit unit = this.getUnit();
			IUnitState nextState;
			switch (state) 
			{
				case TRENCH: 	nextState = new TrenchMedikState(unit, this); break;
				case JUMPING: 	nextState = new JumpingMedikState(unit, this); break;
				case _BACK: 	nextState = this.getPrevState(); destroy();	break;
				default: 		nextState = this; break;
			}
			unit.setState(nextState);
		}
		return stateExist;
	}
}
