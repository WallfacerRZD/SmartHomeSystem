package smarthome.command;

import smarthome.Command;
import smarthome.appliance.Light;

public class LightOff implements Command {

	private Light light;

	public LightOff(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.off();
	}

}
