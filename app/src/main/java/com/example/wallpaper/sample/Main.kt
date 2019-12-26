package com.example.wallpaper.sample

interface Car {
    fun turnOnEngine()
    fun accelerate()
}

class Engine {
    fun powerOn() {}
    fun accelerate() {}
}

class Battery {
    fun powerOn() {}
    fun accelerate() {}
    fun checkIfDischarged() {}
    fun charge() {}
}

class MotorCar : Car {

    private val engine = Engine()

    override fun turnOnEngine() {
        engine.powerOn()
    }

    override fun accelerate() {
        engine.accelerate()
    }
}

class ElectricCar : Car {

    private val battery = Battery()

    override fun turnOnEngine() {
        battery.powerOn()
    }

    override fun accelerate() {
        battery.accelerate()
    }

    fun charge() {
        battery.charge()
    }
}

class Main {

    fun main() {
        val motorCar = MotorCar()
        val electricCar = ElectricCar()

        processCar(motorCar)
        processCar(electricCar)
    }

    private fun processCar(car: Car) {
        car.accelerate()
        car.turnOnEngine()
    }
}