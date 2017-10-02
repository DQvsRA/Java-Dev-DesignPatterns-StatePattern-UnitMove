package app.interfaces.behavior;

public interface IHaveBehaviors 
{
	IMoveBehavior getMoveBehaviour();
	void setMoveBehaviour(IMoveBehavior value);
	
	IUpdataBehavior getUpdateBehaviour();
	void setUpdateBehaviour(IUpdataBehavior value);
}
