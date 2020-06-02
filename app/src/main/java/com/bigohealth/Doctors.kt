package com.bigohealth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_doctor.view.*
import kotlinx.android.synthetic.main.fragment_doctor.view.*

class Doctors: Fragment() {

    private var listener: Cities.OnDataReceived?= null
    private var adapter: DoctorAdapter?= null
    private var docList: ArrayList<RetrofitInstance.Doctor>?= null
    private var view_: View?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view_= inflater.inflate(R.layout.fragment_doctor, container, false)
        docList= ArrayList()
        adapter= DoctorAdapter(docList!!)
        view_!!.recycler_frag_doctor.adapter= adapter
        view_!!.recycler_frag_doctor.layoutManager= LinearLayoutManager(context!!)
        return view_
    }

    fun getListener(): Cities.OnDataReceived {
        if (listener==null) {
            listener = object : Cities.OnDataReceived {
                override fun onReceived(response: RetrofitInstance.DoctorData, city: String) {
                    view_!!.progressBar_frag_doctor.visibility= View.GONE
                    val doc= response.doctors
                    docList!!.clear()
                    for (i in doc!!.indices) {
                        if (city.toLowerCase().trim()=="all cities") {
                            docList!!.add(doc[i])
                        }else if (city.toLowerCase().trim()==doc[i].city.toLowerCase().trim()) {
                            docList!!.add(doc[i])
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }
        }
        return listener!!
    }


    private inner class DoctorAdapter(val list: ArrayList<RetrofitInstance.Doctor>):
        RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.frag_doctor, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Glide.with(context!!).load(list[position].doc_img_url).into(holder.image)
            holder.name.text= list[position].doc_firstname+" "+list[position].doc_middlename+" "+list[position].doc_lastname
            holder.specialisation.text= list[position].specialisation
            holder.experience.text= list[position].experience.toString()+" yrs exp"
            holder.city.text= list[position].city
            holder.viewProfile.setOnClickListener {
                val bundle= Bundle()
                bundle.putString("doctor", Gson().toJson(list[position]))
                startActivity(Intent(context!!, DoctorDetails::class.java).putExtras(bundle))
            }

            holder.bookNow.setOnClickListener {
                Toast.makeText(context!!, "Book Now", Toast.LENGTH_LONG).show()
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image= itemView.ppic_frag_doctor!!
            val name= itemView.name_frag_doctor!!
            val specialisation= itemView.specialisation_frag_doctor!!
            val experience= itemView.experience_frag_doctor!!
            val city= itemView.city_frag_doctor!!
            val viewProfile= itemView.viewprofile_frag_doctor!!
            val bookNow= itemView.booknow_frag_doctor!!
        }
    }
}