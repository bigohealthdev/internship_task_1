package com.bigohealth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_city.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cities(val listener: OnDataReceived): Fragment() {

    private var responseData: RetrofitInstance.DoctorData?= null
    private var cities: ArrayList<String>?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_city, container, false)
        val request= RetrofitInstance.instance.getRetrofit().create(RetrofitInstance.Request::class.java)
        val data= request.getDoctorData()
        view.doctor_title_frag_city.setOnClickListener {
            startActivity(Intent(context!!, Appointments::class.java))
        }
        data.enqueue(object : Callback<RetrofitInstance.DoctorData> {
            override fun onFailure(call: Call<RetrofitInstance.DoctorData>, t: Throwable) {
                Toast.makeText(context!!, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<RetrofitInstance.DoctorData>,
                response: Response<RetrofitInstance.DoctorData>
            ) {
                responseData= response.body()
                Manager.instance.doctorData= responseData
                listener.onReceived(responseData!!, "all cities")
                processData(view, responseData!!)
            }
        })

        view.cities_frag_city.onItemSelectedListener= object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                listener.onReceived(responseData!!, cities!![position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        return view
    }

    fun processData(view: View, response: RetrofitInstance.DoctorData) {
        cities= ArrayList<String>()
        cities!!.add("All Cities")
        for (i in response.cities!!.indices) {
            cities!!.add(response.cities[i].name)
        }
        val adapter= ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, cities!!)
        view.cities_frag_city.adapter= adapter
    }

    interface OnDataReceived {
        fun onReceived(response: RetrofitInstance.DoctorData, city: String)
    }
}