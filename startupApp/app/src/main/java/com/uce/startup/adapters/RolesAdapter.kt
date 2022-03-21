package com.uce.startup.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uce.startup.activities.client.home.ClientHomeActivity
import com.uce.startup.activities.undertake.home.UndertakeHomeActivity
import com.uce.startup.models.Rol
import com.uce.startup.utils.SharedPref
import com.uce.startup.R

class RolesAdapter(val context: Activity, val roles: ArrayList<Rol>): RecyclerView.Adapter<RolesAdapter.RolesViewHolder>() {

    val sharedPref = SharedPref(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_roles, parent, false)
        return RolesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {

        val rol = roles[position] // CADA ROL

        holder.textViewRol.text = rol.name
        Glide.with(context).load(rol.image).into(holder.imageViewRol)

        holder.itemView.setOnClickListener { goToRol(rol) }
    }

    private fun goToRol(rol: Rol) {
        if (rol.name == "EMPRENDEDOR") {

            sharedPref.save("rol", "EMPRENDEDOR")

            val i = Intent(context, UndertakeHomeActivity::class.java)
            context.startActivity(i)
        }
        else if (rol.name == "CLIENTE") {
            sharedPref.save("rol", "CLIENTE")

            val i = Intent(context, ClientHomeActivity::class.java)
            context.startActivity(i)
        }
    }

    class RolesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textViewRol: TextView
        val imageViewRol: ImageView

        init {
            textViewRol = view.findViewById(R.id.textview_rol)
            imageViewRol = view.findViewById(R.id.imageview_rol)
        }

    }

}