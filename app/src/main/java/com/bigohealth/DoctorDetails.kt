package com.bigohealth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_doctor_details.*
import org.json.JSONObject

class DoctorDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        val details= JSONObject(intent.extras!!.getString("doctor")!!)
        name_doctor_details.text= details.getString("doc_firstname")+" "+details.getString("doc_middlename")+" "+details.getString("doc_lastname")
        Glide.with(this).load(details.getString("doc_img_url")).into(image_doctor_details)
        specialisation_doctor_details.text= details.getString("specialisation")
        about_doctor_details.text= details.getString("about")
        address_doctor_details.text= details.getString("address_line1")+", "+details.getString("address_line2")+", "+details.getString("city")
        awards_doctor_details.text= details.getString("awards")
        fees_doctor_details.text= "Rs. "+details.getInt("doc_fee").toString()+"/- per head general fees"
        timing_doctor_details.text= details.getString("general_slot")
    }
}