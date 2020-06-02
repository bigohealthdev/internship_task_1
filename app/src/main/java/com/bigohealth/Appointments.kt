package com.bigohealth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_appointments.*
import kotlinx.android.synthetic.main.frag_appointments.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Appointments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val request= RetrofitInstance.instance.getRetrofit().create(RetrofitInstance.Request::class.java)
        val data= request.getAuthorization("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImVlYTMzODg3LTViNTktNGZhZS04YTg" +
                "zLWY4MDQwM2I2YTU1YyIsImlhdCI6MTU5MDkwMTM2NCwiZXhwIjoxNzgwMTAxMzY0fQ.utXgTgqXrmEF9DQmQ5sJ5wdIBEdmqdUC7vSVVi4RNeg",
        "eea33887-5b59-4fae-8a83-f80403b6a55c")
        data.enqueue(object :Callback<RetrofitInstance.AppointmentData> {
            override fun onFailure(call: Call<RetrofitInstance.AppointmentData>, t: Throwable) {
                Toast.makeText(this@Appointments, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<RetrofitInstance.AppointmentData>,
                response: Response<RetrofitInstance.AppointmentData>
            ) {
                Manager.instance.appointmentData= response.body()
                showData(response.body()!!.appointments!!)
            }
        })
    }

    private fun showData(data: List<RetrofitInstance.Appointment>) {
        val list= ArrayList<RetrofitInstance.Appointment>()
        val adapter= AppointmentAdapter(list)
        recycler_appointments.adapter= adapter
        recycler_appointments.layoutManager= LinearLayoutManager(this)
        for (i in data.indices) {
            list.add(data[i])
        }
        Collections.sort(list, DateSorter())
        adapter.notifyDataSetChanged()
    }


    private inner class AppointmentAdapter (val list: ArrayList<RetrofitInstance.Appointment>):
        RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AppointmentAdapter.ViewHolder {
            return ViewHolder(LayoutInflater.from(this@Appointments).inflate(R.layout.frag_appointments, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: AppointmentAdapter.ViewHolder, position: Int) {
            Glide.with(this@Appointments).load(list[position].docImgUrl).into(holder.image)
            holder.docName.text= list[position].docName
            holder.specialisation.text= list[position].docSpecialisation
            holder.patientName.text= list[position].patName+" ("+list[position].patAge+")"
            holder.address.text= list[position].patAddress
            holder.datetime.text= list[position].date+" "+list[position].slot
            holder.docName.setOnClickListener {
                val docList= Manager.instance.doctorData!!.doctors!!
                for (i in docList.indices) {
                    if (docList[i].doc_id==list[position].docId) {
                        val bundle= Bundle()
                        bundle.putString("doctor", Gson().toJson(docList[i]))
                        startActivity(Intent(this@Appointments, DoctorDetails::class.java).putExtras(bundle))
                        break
                    }
                }
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image= itemView.imageView_frag_appointments!!
            val docName= itemView.name_frag_appointments!!
            val specialisation= itemView.specialisation_frag_appointment!!
            val patientName= itemView.patientname_frag_appointments!!
            val address= itemView.address_frag_appointments!!
            val datetime= itemView.datetime_frag_appointments!!
        }
    }

    private inner class DateSorter: Comparator<RetrofitInstance.Appointment> {
        override fun compare(
            o1: RetrofitInstance.Appointment?,
            o2: RetrofitInstance.Appointment?
        ): Int {
            return o1!!.date.compareTo(o2!!.date)
        }
    }
}