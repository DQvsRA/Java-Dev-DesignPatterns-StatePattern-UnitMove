package app.entities;

import java.awt.Point;

import app.entities.states.NullUnitState;
import app.interfaces.behavior.IHaveBehaviors;
import app.interfaces.behavior.IMoveBehavior;
import app.interfaces.behavior.IUpdataBehavior;
import app.interfaces.unit.IUnit;
import app.interfaces.unit.IUnitSelectable;
import app.interfaces.unit.IUnitState;
import app.interfaces.unit.IUnitStateMachine;
import controlP5.Controller;
import hype.H;
import processing.core.PApplet;

public class Unit implements 	IUnit, 
								IUnitStateMachine, 
								IUnitSelectable, 
								IHaveBehaviors,
									IUpdataBehavior,
									IMoveBehavior
{	
	protected IUnitState _state = new NullUnitState();
	protected PApplet _canvas;
	
	private boolean _isSelected = false;
	private int _radius;
	private Point _destinationPoint;
	private IMoveBehavior _moveBehavior;
	private IUpdataBehavior _updateBehavior;
	
	public float x, y;
	
	public Unit(PApplet canvas) { _canvas = canvas; setup(); }
	public Unit(PApplet canvas, Point position) 
	{
		this(canvas);
		this.x = position.x;
		this.y = position.y;
	}
	
	private void setup()
	{
		setRadius(_state.getRadius());
		setMoveBehaviour(_state.getMoveBehaviour());
		setUpdateBehaviour(_state.getUpdateBehaviour());
	}
		
	public void draw() 
	{
		if(_radius > 0) {
			int strokeSize = _state.getStrokeSize();
			int color = _state.getColor();

			if(isSelected()) {
				_canvas.stroke(50);
				_canvas.fill(H.BLACK);
				_canvas.strokeWeight(1);
				_canvas.line(x, y, _canvas.mouseX, _canvas.mouseY);
			}
			
			if(strokeSize > 0) {
				_canvas.stroke(_state.getStrokeColor());
				_canvas.strokeWeight(strokeSize);
			}
						
			_canvas.fill(color);
			_canvas.smooth();
			_canvas.ellipse(x, y, _radius, _radius);
			
			if(_destinationPoint != null && x != _destinationPoint.x && _state.getSpeed() > 0)
			{
				_canvas.strokeWeight(2);
				_canvas.stroke(H.RED);
				_canvas.line(x, y, _destinationPoint.x, _destinationPoint.y);
				_canvas.ellipse(x, y, 3,3);
				_canvas.ellipse(_destinationPoint.x, _destinationPoint.y, 4, 4);
			}
		}
	}
	
	public void OnMoveComplete() {
		if(_state.continiousMove())
		move(
			(int)_canvas.random(_canvas.width*0.1f, _canvas.width*0.9f),
			(int)_canvas.random(_canvas.height*0.1f, _canvas.height*0.9f)
		);
	}
	
	public PApplet getCanvas() { return _canvas; }
	public Point getPosition() { return new Point((int)this.x, (int)this.y); }
	public void update(int deltaTime) {
		if(_updateBehavior != null) _updateBehavior.update(deltaTime);
		if(_state instanceof IUpdataBehavior) { 
			((IUpdataBehavior) _state).update(deltaTime); 
		}
	}
	
	public void move(int toX, int toY) 	{ 
		boolean possible = false;
		if(_moveBehavior != null) {
			possible = _moveBehavior.possible();
			_moveBehavior.move(toX, toY);
		}
		if(_state instanceof IMoveBehavior) {
			IMoveBehavior _movableState = (IMoveBehavior) _state;
			possible = _movableState.possible() || possible;
			_movableState.move(toX, toY); 
		}
		if(possible) _destinationPoint = new Point(toX, toY);
		else _destinationPoint = null;
	}
	
	public String getUnitType() 		{ return _state.getUnitType(); }
	public Controller<?> getGUI() 		{ return _state.getGUI(); }
	
	public int getStrokeSize() 			{ return _state.getStrokeSize(); }
	public int getRadius() 				{ return _radius; }
	public void setRadius(int value) 	{ _radius = value; }

	@Override // IUnitStateMachine
	public boolean transitionToState(int stateID) { return _state.transitionToState(stateID);	}

	@Override // IUnit
	public void setState(IUnitState state) { _state = state; interapt(); setup(); }

	@Override // IUnitSelectable
	public void setSelected(boolean value) { 
		_isSelected = value;
		if(value) interapt(); 
	}

	@Override // IUnitSelectable
	public boolean isSelected() { return _isSelected; }
	
	@Override // IHaveBehaviors
	public IMoveBehavior getMoveBehaviour() { return _moveBehavior; }
	
	@Override // IHaveBehaviors
	public void setMoveBehaviour(IMoveBehavior value) { _moveBehavior = value; }

	@Override // IHaveBehaviors
	public IUpdataBehavior getUpdateBehaviour() { return _updateBehavior; }
	
	@Override // IHaveBehaviors
	public void setUpdateBehaviour(IUpdataBehavior value) { _updateBehavior = value; }
	@Override
	public void interapt() {
		if(_moveBehavior != null) _moveBehavior.interapt();
		_destinationPoint = null;
	}
	@Override
	public boolean possible() {
		return true;
	}
}
