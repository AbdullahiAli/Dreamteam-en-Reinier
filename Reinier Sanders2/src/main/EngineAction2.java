package main;

import main.Engine.EngineAction;

public class EngineAction2 {
	private EngineAction e;
	private long time;

	public EngineAction2(EngineAction e, long time) {
		this.e = e;
		this.time = time;
	}

	public EngineAction getE() {
		return e;
	}

	public void setE(EngineAction e) {
		this.e = e;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
