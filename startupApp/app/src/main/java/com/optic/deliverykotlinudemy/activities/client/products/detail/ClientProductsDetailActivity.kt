package com.optic.deliverykotlinudemy.activities.client.products.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.optic.deliverykotlinudemy.R
import com.optic.deliverykotlinudemy.models.Product
import com.optic.deliverykotlinudemy.utils.SharedPref

class ClientProductsDetailActivity : AppCompatActivity() {

    val TAG = "ProductsDetail"
    var product: Product? = null
    val gson = Gson()

    var toolbar: Toolbar? = null
    var imageSlider: ImageSlider? = null
    var textViewName: TextView? = null
    var textViewDescription: TextView? = null
    var textViewPrice: TextView? = null
    var textViewTel: TextView? = null
    var textViewCounter: TextView? = null
    var imageViewAdd: ImageView? = null
    var imageViewWP: ImageView? = null
    var imageViewRemove: ImageView? = null
    var buttonAdd: Button? = null

    var counter = 1
    var productPrice = 0.0

    var sharedPref: SharedPref? = null
    var selectedProducts = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_products_detail)

        product = gson.fromJson(intent.getStringExtra("product"), Product::class.java)
        sharedPref = SharedPref(this)

        imageSlider = findViewById(R.id.imageslider)
        textViewName = findViewById(R.id.textview_name)
        textViewDescription = findViewById(R.id.textview_description)
        textViewPrice = findViewById(R.id.textview_price)
        textViewTel = findViewById(R.id.textview_phone)
        buttonAdd = findViewById(R.id.btn_add_product)
        imageViewWP = findViewById(R.id.imageview_wp)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar?.title = "Detalle del Producto"

        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(product?.image1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image2, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image3, ScaleTypes.CENTER_CROP))

        // mostramos las imagenes en carrucel
        imageSlider?.setImageList(imageList)

        textViewName?.text = product?.name
        textViewDescription?.text = product?.description
        textViewPrice?.text = "${product?.price}$"
        textViewTel?.text = "WhatsApp"
        textViewTel?.backgroundTintList = ColorStateList.valueOf(Color.GREEN)

        imageViewWP?.setOnClickListener(){
            //textViewTel?.text = "${product?.phone}"
        val phone = "${product?.phone}"
            val webIntent: Intent = Uri.parse("https://wa.me/593"+phone).let { webpage ->
                Intent(Intent.ACTION_VIEW, webpage)
            }
            startActivity(webIntent);
        }

        imageViewAdd?.setOnClickListener { addItem() }
        imageViewRemove?.setOnClickListener { removeItem() }
        buttonAdd?.setOnClickListener { addToBag() }

        getProductsFromSharedPref()
    }

    private fun addItem() {
        counter++
        productPrice = product?.price!! * counter
        product?.quantity = counter
        textViewCounter?.text = "${product?.quantity}"
        textViewPrice?.text = "${productPrice}$"
    }

    private fun removeItem() {
        if (counter > 1) {
            counter--
            productPrice = product?.price!! * counter
            product?.quantity = counter
            textViewCounter?.text = "${product?.quantity}"
            textViewPrice?.text = "${productPrice}$"
        }
    }

    private fun addToBag() {

        val index = getIndexOf(product?.id!!) // INDICE DEL PRODUCTO SI ES QUE EXISTE EN SHARED PREF

         if (index == -1) { // ESTE PRODUCTO NO EXISTE AUN EN SHARED PREF
             if (product?.quantity == 0) {
                 product?.quantity = 1
             }
             selectedProducts.add(product!!)
         }
         else { // YA EXISTE EL PRODUCTO EN SHARED PREF - DEBEMOS EDITAR LA CANTIDAD
             selectedProducts[index].quantity = counter
         }

         sharedPref?.save("order", selectedProducts)
         Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()

     }

    private fun getProductsFromSharedPref() {

        if (!sharedPref?.getData("order").isNullOrBlank()) { // EXISTE UNA ORDEN EN SHARED PREF
            val type = object: TypeToken<ArrayList<Product>>() {}.type
            selectedProducts = gson.fromJson(sharedPref?.getData("order"), type)

            val index = getIndexOf(product?.id!!)
            val quantity = 1

            if (index != -1) {

                productPrice = product?.price!!
                textViewPrice?.text = "${productPrice}$"
                buttonAdd?.setText("GUARDADO")
                buttonAdd?.backgroundTintList = ColorStateList.valueOf(Color.CYAN)
            }

            for (p in selectedProducts) {
                Log.d(TAG, "Shared pref: $p")
            }
        }

    }

    // ES PARA COMPARAR SI UN PRODUCTO YA EXISTE EN SHARED PREF Y ASI PODER EDITAR LA CANTIDAD DEL PRODUCTO SELECCIONADO
    private fun getIndexOf(idProduct: String): Int {
        var pos = 0

        for (p in selectedProducts) {
            if (p.id == idProduct) {
                return pos
            }
            pos++
        }

        return -1
    }
}