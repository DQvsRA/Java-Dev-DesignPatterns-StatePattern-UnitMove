package app.entities.states;

import app.entities.Unit;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.behavior.IUpdataBehavior;
import app.interfaces.unit.IUnitState;
import app.interfaces.unit.IUnitStateMachine;
import controlP5.Controller;

public final class NullUnitState implements IUnitState, IUnitStateMachine {

	public NullUnitState( ) { }
	@Override
	public boolean transitionToState(int stateID) { return false; }

	@Override
	public String getUnitType() { return "NullUnit"; }

	@Override
	public int getColor() { return 0; }

	@Override
	public int getStrokeColor() { return 0; }

	@Override
	public int getStrokeSize() { return 0; }

	@Override
	public int getRadius() { return 0; }

	@Override
	public int getSpeed() { return 0; }

	@Override
	public Controller<?> getGUI() { return null; }

	@Override
	public Unit getUnit() { return null; }
	
	@Override
	public void destroy() { }
	
	@Override // IHaveBehaviors
	public IMoveBehavior getMoveBehaviour() { return null; }
	
	@Override // IHaveBehaviors
	public void setMoveBehaviour(IMoveBehavior value) { }
	
	@Override // IHaveBehaviors
	public IUpdataBehavior getUpdateBehaviour() { return null; }
	
	@Override // IHaveBehaviors
	public void setUpdateBehaviour(IUpdataBehavior value) { }
	
	@Override
	public boolean continiousMove() { return false; }
}
