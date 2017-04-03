package engine;

import java.util.List;

import engine.game.eventobserver.ObservableEntity;

public interface EntityInterface extends ObservableEntity
{
	// external
	void setEvent(EventInterface event);

	// internal
	void update();
	List<EventInterface> getEvents();

	void setYAcceleration(double accel);

	void setXAcceleration(double accel);

	void setYSpeed(double speed);

	void setXSpeed(double speed);

	void setY(double y);

	void setX(double x);
	
	void setHeight(double height);
	
	void setWidth(double width);
}