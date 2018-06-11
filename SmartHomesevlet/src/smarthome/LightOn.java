package smarthome;

public class LightOn implements Command {

	 private Light lighton;

	 LightOn(Light light) {
		 this.lighton = light;
		 }
	@Override
	public void execute() {
		lighton.on();
		}

}
