package engine.events;

import engine.Event;
import engine.Parameter;
import engine.game.eventobserver.TimerObservable;

public class TimerEvent extends Event {
	private boolean lessThan;
	private boolean equalTo;

	public TimerEvent() {
		addParam(new Parameter("Time", Integer.class, 0));
	}

	public void setComparsion(boolean lessThan, boolean equalTo) {
		this.lessThan = lessThan;
		this.equalTo = equalTo;
	}

	@Override
	public boolean act() {
		int time = 0;
		try{
			time = getGameInfo().getObservableBundle().getTimerObservable().getTimeInMilliseconds();
		}
		catch(NullPointerException e){ 
			System.out.println("Game Info = " + getGameInfo());
		}
		if (lessThan && equalTo) {
			return time <= (Integer) getParam("Time");
		} else if (lessThan && !equalTo) {
			return time < (Integer) getParam("Time");
		} else if (!lessThan && !equalTo) {
			return time > (Integer) getParam("Time");
		}
		return time >= (Integer) getParam("Time");
	}
}
