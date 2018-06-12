package smarthome.servlet;


import net.sf.json.JSONObject;
import smarthome.Command;
import smarthome.appliance.Camera;
import smarthome.appliance.Light;
import smarthome.command.CameraOff;
import smarthome.command.CameraOn;
import smarthome.command.LightOff;
import smarthome.command.LightOn;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestConnect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Light LIGHT = new Light();
	private static final Camera CAMERA = new Camera();
	private static final Map<String, Command> COMMANDS = new HashMap<>();
	
	static {
		COMMANDS.put("lightOn", new LightOn(LIGHT));
		COMMANDS.put("lightOff", new LightOff(LIGHT));
		COMMANDS.put("cameraOn", new CameraOn(CAMERA));
		COMMANDS.put("cameraOff", new CameraOff(CAMERA));
		// 如果要扩展功能, 只需要修改这一处代码即可, 不需要增加额外的分支语句
		// COMMANDS.put("TVOn", new TvOn(TV));
		// COMMANDS.put("TVOff", new TvOff(TV));
		// COMMANDS.put("airControllerOff", new AirControllerOff(airController));
		// .....
	}
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
		String state = "";
		
		if (COMMANDS.containsKey(command)) {
			COMMANDS.get(command).execute();
			state = "success";
		} else {
			state = "failed";
		}

		// 返回
		JSONObject json = new JSONObject();
		json.put("state", state);
		response.getWriter().print(json);
	}
}