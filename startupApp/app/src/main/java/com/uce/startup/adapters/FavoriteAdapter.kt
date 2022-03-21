package com.uce.startup.adapters

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.uce.startup.activities.client.products.detail.ClientProductsDetailActivity
import com.uce.startup.models.Product
import com.uce.startup.models.User
import com.uce.startup.utils.SharedPref
import com.uce.startup.R

class FavoriteAdapter(val context: Activity, val products: ArrayList<Product>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    val sharedPref = SharedPref(context)
    var user: User? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_shopping_bar, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        val product = products[position] // CADA UNA DE LAS CATEGORIAS

        holder.textViewName.text = product.name
        holder.textViewNameE.text = product.phone
        Glide.with(context).load(product.image1).into(holder.imageViewProduct)

        holder.textViewPrice.text = "${product.price}"


        getUserFromSession()

        holder.textViewNameE.setOnClickListener(){
            val phone = "${product.phone}"
            val callIntent: Intent = Uri.parse("tel:"+phone).let { number ->
                Intent(Intent.ACTION_DIAL, number)
            }
            context.startActivity(callIntent);
        }

        holder.imageview_wp.setOnClickListener(){
            val phone = "${product.phone}"
            val webIntent: Intent = Uri.parse("https://wa.me/593"+phone).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            context.startActivity(webIntent);
        }

        holder.imageViewDelete.setOnClickListener { deleteItem(position) }


       // holder.itemView.setOnClickListener { goToDetail(product) }
    }

    private fun getUserFromSession() {

        val gson = Gson()

        if (!sharedPref?.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }

    }

    private fun goToDetail(product: Product) {
        val i = Intent(context, ClientProductsDetailActivity::class.java)
        i.putExtra("product", product.toJson())
        context.startActivity(i)
    }
    private fun deleteItem(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, products.size)
        sharedPref.save("order", products)

    }

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textViewName: TextView
        val textViewPrice: TextView
        val imageViewProduct: ImageView
        val imageViewDelete: ImageView
        val imageview_wp: ImageView
        val textViewNameE: TextView

        init {
            textViewName = view.findViewById(R.id.textview_name)
            textViewPrice = view.findViewById(R.id.textview_price)
            imageViewProduct = view.findViewById(R.id.imageview_product)
            imageViewDelete = view.findViewById(R.id.imageview_delete)
            textViewNameE = view.findViewById(R.id.textview_nameem)
            imageview_wp = view.findViewById(R.id.imageview_wp)
        }

    }

}