package com.bigohealth

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

class RetrofitInstance {

    private var retrofit: Retrofit?= null

    private object Holder {
        val instance= RetrofitInstance()
    }

    companion object {
        val instance: RetrofitInstance by lazy { Holder.instance }
    }

    interface Request {
        @POST("/doctors")
        fun getDoctorData(): Call<DoctorData>
        @FormUrlEncoded
        @POST("/patientAppt")
        fun getAuthorization(@Header("authorization") token: String, @Field("patId") patId: String): Call<AppointmentData>
    }

    fun getRetrofit(): Retrofit {
        if (retrofit==null) {
            retrofit= Retrofit.Builder().baseUrl("https://bigobackend.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }

    inner class DoctorData {
        @SerializedName("city")
        val cities: List<City>?= null
        @SerializedName("data")
        val doctors: List<Doctor>?= null
    }

    inner class City {
        @SerializedName("city")
        val name: String= ""
    }

    inner class Doctor {
        @SerializedName("doc_id")
        val doc_id: String= ""
        @SerializedName("doc_img_url")
        val doc_img_url: String= ""
        @SerializedName("experience")
        val experience: Int= 0
        @SerializedName("qualification")
        val qualification: String= ""
        @SerializedName("pincode")
        val pincode: Int= 0
        @SerializedName("doc_fee")
        val doc_fee: Int= 0
        @SerializedName("from_hospital")
        val from_hospital: Int= 0
        @SerializedName("hospital_id")
        val hospital_id: String= ""
        @SerializedName("rank")
        val rank: Int= 0
        @SerializedName("book_appt")
        val book_appt: Int= 0
        @SerializedName("doc_discounted_fee")
        val doc_discounted_fee: Int= 0
        @SerializedName("doc_url")
        val doc_url: String= ""
        @SerializedName("numBeds")
        val numBeds: Int= 0
        @SerializedName("firebase_token")
        val firebase_token: String= ""
        @SerializedName("onlinePayFee")
        val onlinePayFee: Int= 0
        @SerializedName("ayush")
        val ayush: Int= 0
        @SerializedName("language_id")
        val language_id: Int= 0
        @SerializedName("doc_firstname")
        val doc_firstname: String= ""
        @SerializedName("doc_middlename")
        val doc_middlename: String= ""
        @SerializedName("doc_lastname")
        val doc_lastname: String= ""
        @SerializedName("specialisation")
        val specialisation: String= ""
        @SerializedName("address_line1")
        val address_line1: String= ""
        @SerializedName("address_line2")
        val address_line2: String= ""
        @SerializedName("city")
        val city: String= ""
        @SerializedName("state")
        val state: String= ""
        @SerializedName("landmark")
        val landmark: String= ""
        @SerializedName("general_slot")
        val general_slot: String= ""
        @SerializedName("about")
        val about: String= ""
        @SerializedName("awards")
        val awards: String= ""
        @SerializedName("NumRating")
        val NumRating: Int= 0
        @SerializedName("TotalRatings")
        val TotalRatings: Int= 0
        @SerializedName("searchTerm")
        val searchTerm: String= ""
    }

    inner class AppointmentData {
        @SerializedName("data")
        val appointments: List<Appointment>?= null
    }

    inner class Appointment {
        @SerializedName("consultId")
        val consultId: String= ""
        @SerializedName("patID")
        val patID: String= ""
        @SerializedName("docId")
        val docId: String= ""
        @SerializedName("docName")
        val docName: String= ""
        @SerializedName("docSpecialisation")
        val docSpecialisation: String= ""
        @SerializedName("docImgUrl")
        val docImgUrl: String= ""
        @SerializedName("docFirebaseToken")
        val docFirebaseToken: String= ""
        @SerializedName("date")
        val date: String= ""
        @SerializedName("patName")
        val patName: String= ""
        @SerializedName("patPhone")
        val patPhone: String= ""
        @SerializedName("patAge")
        val patAge: Int= 0
        @SerializedName("patAddress")
        val patAddress: String= ""
        @SerializedName("status")
        val status: Int= 0
        @SerializedName("slot")
        val slot: String= ""
        @SerializedName("onlinePay")
        val onlinePay: Int= 0
        @SerializedName("paymentId")
        val paymentId: String= ""
        @SerializedName("orderAmount")
        val orderAmount: Int= 0
        @SerializedName("orderStatus")
        val orderStatus: Int= 0
        @SerializedName("apptType")
        val apptType: Int= 0
        @SerializedName("docChannelizeId")
        val docChannelizeId: Int= 0
    }
}