package app.interfaces.unit;

import app.entities.Unit;
import app.interfaces.behavior.IHaveBehaviors;
import controlP5.Controller;

public interface IUnitState extends IUnitStateMachine, 
									IHaveBehaviors
{
	String getUnitType();
	
	int getColor();
	int getStrokeColor();
	int getStrokeSize();
	int getRadius();
	int getSpeed();
	
	boolean continiousMove();
	
	Unit getUnit();
	void destroy();
	
	Controller<?> getGUI();
}
