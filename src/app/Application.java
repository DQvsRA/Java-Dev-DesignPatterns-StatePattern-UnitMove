package app;

import java.awt.Point;
import java.util.ArrayList;

import app.entities.Unit;
import app.entities.states.BaseUnitState;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.Controller;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Application extends PApplet implements CallbackListener 
{
	static private final int SIZE = 800;
	public void settings() { 
		size( SIZE, SIZE );
	}
	
	private boolean 
		_isMovePossible,
		_isUnitStateChanged = false;
	
	private int 	
		_closestClickToUnitDistance,
		_lastEventHash = 0,
		_milliseconds = 0,
		_deltaTime = 0,
		_lastTime = 0
	;
	
	
	private Unit _unit;
	private ArrayList<Unit> _units;
	private Controller<?> _gui;
	
	public void setup()
	{
		Ani.init(this);
		Ani.autostart();
		Ani.overwrite();
		ellipseMode(RADIUS); 
		
		_units = new ArrayList<>();
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.add(new Unit(this, getRandomPositionOnCanvas()));
		_units.forEach(u-> {
			u.setState(new BaseUnitState(u));
			u.OnMoveComplete();
		});
	}
	
	public void mouseReleased(MouseEvent event) 
	{
		if(_gui != null && _gui.isMouseOver()) return;
		if(_isUnitStateChanged) { _isUnitStateChanged = false; return; }
		
		int clickX = event.getX();
		int clickY = event.getY();
		
		_closestClickToUnitDistance = SIZE;
		_isMovePossible = true;
		
		_units.forEach(u -> {
			int hypot = (int)Math.hypot(clickX - u.x, clickY - u.y);
			if(hypot < (u.getRadius() + u.getStrokeSize()) && hypot < _closestClickToUnitDistance) 
			{
				if(_unit != null && _unit == u) return;
				SelectUnit(u);
				ChangeGUI(u);
				_closestClickToUnitDistance = hypot;
			}
		});
		
		if(_isMovePossible && _unit != null) 
		{
			int hypot = (int)Math.hypot(clickX - _unit.x, clickY - _unit.y);
			if(hypot > (_unit.getRadius() - _unit.getStrokeSize()))
				_unit.move(clickX, clickY);
		}
	}
	
	private void SelectUnit(Unit u) 
	{
		if(_unit != null) _unit.setSelected(false);
		
		_unit = u;
		_unit.setSelected(true);
		
		_isMovePossible = false;
		
		System.out.println("Unit Selected: radius = " + _unit.getUnitType() + "; closesDistance = " + _closestClickToUnitDistance + "; position: " + _unit.getPosition().toString());
	}
	
	private void ChangeGUI(Unit unit) 
	{
		if(_gui != null) {
			_gui.removeCallback();
			_gui.remove();
			_gui = null;
		}
		
		_gui = unit.getGUI();
		if(_gui != null) {
			_gui.bringToFront();
			_gui.addCallback(this);
		}
	}

	public void draw() 
	{
		this.clear();
		this.background(20);
		_milliseconds = millis(); 
		_deltaTime = _milliseconds - _lastTime;
		this.beginShape();
		_units.forEach(u -> {
			u.update(_deltaTime);
			u.draw();
		});
		this.endShape();
		_lastTime = _milliseconds;
	}

	@Override
	public void controlEvent(CallbackEvent evt) 
	{
		if(_lastEventHash  == evt.hashCode()) return;
		
		if(evt.getAction() == 100)
		{
			Controller<?> guiController = evt.getController();
			String guiName = evt.getController().getAddress();
			int selectedMenuID = (int)guiController.getValue();
			
			System.out.println("\n> GUI SELECTED: guiName = " + guiName);
			System.out.println("> GUI SELECTED: selectedMenuID = " + selectedMenuID);
			System.out.println("> GUI SELECTED: _isUnitStateChanged = " + _isUnitStateChanged);
			
			_isUnitStateChanged = _unit.transitionToState(selectedMenuID);
			if(_isUnitStateChanged) ChangeGUI(_unit);
			
			_lastEventHash = evt.hashCode();
		}
	}
	
	public Point getRandomPositionOnCanvas() {
		return new Point(	
				(int)random(this.width * 0.1f, this.width * 0.9f),
				(int)random(this.height * 0.1f, this.height * 0.9f)
		);
	}
}
