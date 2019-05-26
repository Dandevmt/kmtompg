package com.dan.kmmpg

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ConversionViewModel : ViewModel() {
    private val kmToMilesFactor: Double = 0.6213712
    private val litersToGallonsFactor: Double = 0.2641729

    private var fromMiles = false
    val kilometers = MutableLiveData<Double>()
    fun setKilometers(km: Double?) {
        if (kilometers.value != km ?: 0.0) {
            kilometers.value = km ?: 0.0
            if (!fromMiles) {
                fromKilometers = true
                miles.value = kmToMiles(km ?: 0.0)
                fromKilometers = false
            }
            updateUsage()
        }
    }
    private var fromKilometers = false
    val miles = MutableLiveData<Double>()
    fun setMiles(mile: Double?) {
        if (miles.value != mile ?: 0.0) {
            miles.value = mile ?: 0.0
            if (!fromKilometers) {
                fromMiles = true
                kilometers.value = milesToKm(mile ?: 0.0)
                fromMiles = false
            }
            updateUsage()
        }
    }
    private var fromGallons = false
    val liters = MutableLiveData<Double>()
    fun setLiters(liter: Double?) {
        if (liters.value != liter ?: 0.0) {
            liters.value = liter ?: 0.0
            if (!fromGallons) {
                fromLiters = true
                gallons.value = litersToGallons(liter ?: 0.0)
                fromLiters = false
            }
            updateUsage()
        }
    }
    private var fromLiters = false
    val gallons = MutableLiveData<Double>()
    fun setGallons(gallon: Double?) {
        if (gallons.value != gallon ?: 0.0) {
            gallons.value = gallon ?: 0.0
            if (!fromLiters) {
                fromGallons = true
                liters.value = gallonsToLiters(gallon ?: 0.0)
                fromGallons  = false
            }
            updateUsage()
        }
    }

    fun formatNumber(num: Double): String {
        return "%.2f".format(num)
    }

    fun clearValues() {
        kilometers.value = 0.0
        miles.value = 0.0
        liters.value = 0.0
        gallons.value = 0.0
    }

    val kilometersPerLiter = MutableLiveData<String>()
    val milesPerGallon = MutableLiveData<String>()
    private fun updateUsage() {
        if (liters.value == null || liters.value == 0.0) {
            kilometersPerLiter.value = "NA"
        } else {
            kilometersPerLiter.value = formatNumber ((kilometers.value ?: 0.0) / liters.value!!)
        }
        if (gallons.value == null || gallons.value == 0.0) {
            milesPerGallon.value = "NA"
        } else {
            milesPerGallon.value = formatNumber ((miles.value ?: 0.0) / gallons.value!!)
        }
    }

    private fun kmToMiles(km: Double): Double {
        return km * kmToMilesFactor
    }

    private fun milesToKm(miles: Double): Double {
        return miles / kmToMilesFactor
    }

    private fun litersToGallons(liters: Double): Double {
        return liters * litersToGallonsFactor
    }

    private fun gallonsToLiters(gallons: Double): Double {
        return gallons / litersToGallonsFactor
    }

}