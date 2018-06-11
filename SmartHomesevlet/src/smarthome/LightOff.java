package smarthome;

public class LightOff implements Command {

	private Light lightoff ;

    LightOff(Light light) {
        this.lightoff = light;
    }
	@Override
	public void execute() {
		lightoff.off();
	}

}
