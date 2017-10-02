package app.entities.states;

import java.util.Arrays;

import app.behavior.unit.move.WalkUnitMoveBehavior;
import app.entities.Unit;
import app.entities.states.terran.MarineState;
import app.entities.states.terran.MedikState;
import app.entities.states.terran.TankState;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.behavior.IUpdataBehavior;
import app.interfaces.unit.IUnitState;
import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.DropdownList;
import hype.H;

public class BaseUnitState implements IUnitState 
{
	public enum STATES {
		UNIT,
		MARINE,
		MEDIK,
		TANK
	}
	
	private Unit _unit;
	private BaseUnitState _prevState;
	
	private int _color = H.WHITE;
	
	public Unit getUnit() { return _unit; }
	protected BaseUnitState getPrevState() { return _prevState; }
	protected void setColor(int value) { _color = value; }
	
	protected void setup() {}
	
	public BaseUnitState(Unit unit) { _unit = unit; setup(); }
	public BaseUnitState(Unit unit, BaseUnitState prevState) { 
		this(unit);
		_prevState = prevState;
	}
	
	public boolean continiousMove() { return false; }
	public String getUnitType() 	{ return STATES.UNIT.name(); }
	public int getColor() 			{ return _unit.isSelected() ? H.YELLOW : _color; }
	public int getStrokeColor() 	{ return H.MAGENTA; }
	public int getStrokeSize() 		{ return 2; }
	public int getRadius() 			{ return 25; }
	public int getSpeed() 			{ return 100; }

	
	public Controller<?> getGUI() { return BuildDropdownList("Unit Modes", STATES.values()); }
	
	public boolean transitionToState(int stateID)
	{
		STATES state = STATES.values()[stateID];
		System.out.println("> Unit -> transitionToState: " + stateID + "|" + state);
		boolean stateExist = state != null;
		if(stateExist){
			IUnitState newState;
			switch (state) {
				case MARINE: 	newState = new MarineState(_unit, this); break;
				case MEDIK: 	newState = new MedikState(_unit, this); break;
				case TANK: 		newState = new TankState(_unit, this); break;
				default: 		newState = this; break;
			}
			_unit.setState(newState);
		}
		return stateExist;
	}
	
	public void destroy() {
		this._unit = null;
		this._prevState = null;
	}
	
	protected Controller<?> BuildDropdownList(String name, Enum<?>[] names) {
		DropdownList gui = new ControlP5(_unit.getCanvas()).addDropdownList(name);
		Arrays.asList(names).forEach(v->gui.addItem(v.name(), v.ordinal()));
		return gui;
	}
	
	@Override // IHaveBehaviors
	public IMoveBehavior getMoveBehaviour() { return new WalkUnitMoveBehavior(this); }
	
	@Override // IHaveBehaviors
	public void setMoveBehaviour(IMoveBehavior value) {	}
	
	@Override // IHaveBehaviors
	public IUpdataBehavior getUpdateBehaviour() { return null; }
	
	@Override // IHaveBehaviors
	public void setUpdateBehaviour(IUpdataBehavior value) { }
}
