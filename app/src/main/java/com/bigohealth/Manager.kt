package com.bigohealth

class Manager private constructor() {

    var doctorData: RetrofitInstance.DoctorData?= null
    var appointmentData: RetrofitInstance.AppointmentData?= null

    private object Holder {
        val instance= Manager()
    }

    companion object {
        val instance: Manager by lazy { Holder.instance }
    }
}