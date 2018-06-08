station_cfg={}
station_cfg.ssid="boy"
station_cfg.pwd="youdiansao"

wifi.setmode(wifi.STATION)
wifi.sta.config(station_cfg)
wifi.sta.connect()
print(wifi.sta.getip())

tmr.alarm(1, 1000, tmr.ALARM_AUTO, function()
    if wifi.sta.getip() == nil then
        print('Waiting for IP ...')
    else
        print('IP is ' .. wifi.sta.getip())
    tmr.stop(1)
    end
end)

pin=0
gpio.mode(pin,gpio.OUTPUT)
gpio.write(pin,gpio.HIGH)

srv=net.createServer(net.TCP) 
srv:listen(80,function(conn) 
    conn:on("receive",function(conn,payload) 
    print(payload) 

    local _, _, method, vars = string.find(payload, "([A-Z]+) /(.+) HTTP")
           if(vars == "off")then
                 gpio.write(pin, gpio.HIGH)
                 conn:send("<h1> Light OFF.</h1>")
           elseif(vars == "on")then
                 gpio.write(pin, gpio.LOW)
                 conn:send("<h1> Light ON.</h1>")
           end
    
    end) 
end)


