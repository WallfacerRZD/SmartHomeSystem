local LED_PIN1, LED_PIN2= 0, 4
gpio.mode(LED_PIN1, gpio.OUTPUT)
gpio.mode(LED_PIN2, gpio.OUTPUT)


LED1_OPEN = false
LED2_OPEN = false


tmr.alarm(0, 40, tmr.ALARM_AUTO, function()
    if LED1_OPEN then
        gpio.write(LED_PIN1, gpio.HIGH)
    else
        gpio.write(LED_PIN1, gpio.LOW)
    end
    LED1_OPEN = not LED1_OPEN
end)

tmr.alarm(1, 50, tmr.ALARM_AUTO, function()
    if LED2_OPEN then
        gpio.write(LED_PIN2, gpio.HIGH)
    else
        gpio.write(LED_PIN2, gpio.LOW)
    end
    LED2_OPEN = not LED2_OPEN
end)