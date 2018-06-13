station_cfg={}
station_cfg.ssid="smart-home"
station_cfg.pwd="smart-home"
wifi.setmode(wifi.STATION)
wifi.sta.config(station_cfg)
wifi.sta.connect()
print(wifi.sta.getip())

switch=0
singal=4
gpio.mode(switch,gpio.OUTPUT)
gpio.mode(singal, gpio.OUTPUT)
gpio.write(singal,gpio.HIGH)
gpio.write(switch,gpio.HIGH)

tmr.alarm(1, 1000, tmr.ALARM_AUTO, function()
    if wifi.sta.getip() == nil then
        print('Waiting for IP ...')
        gpio.write(singal,gpio.LOW)
        gpio.write(singal,gpio.HIGH)
    else
        print('IP is ' .. wifi.sta.getip())
        gpio.write(singal,gpio.LOW)
    tmr.stop(1)
    end
end)



srv=net.createServer(net.TCP) 
srv:listen(80,function(conn) 
    conn:on("receive",function(conn,payload) 
    print(payload) 

    local _, _, method, vars = string.find(payload, "([A-Z]+) /(.+) HTTP")
           if(vars == "off")then
                 gpio.write(switch, gpio.HIGH)
                 conn:send("<h1> Light OFF.</h1>")
           elseif(vars == "on")then
                 gpio.write(switch, gpio.LOW)
                 conn:send("<h1> Light ON.</h1>")
           end
    
    end) 
end)


