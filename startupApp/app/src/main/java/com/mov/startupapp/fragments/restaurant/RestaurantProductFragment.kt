package com.mov.startupapp.fragments.restaurant

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mov.startupapp.R
import java.io.File


class RestaurantProductFragment : Fragment() {

    var myView: View? = null
    var editTextName: EditText? = null
    var editTextDescription: EditText? = null
    var editTextPrice: EditText? = null
    var imageViewProduct1: ImageView? = null
    var imageViewProduct2: ImageView? = null
    var imageViewProduct3: ImageView? = null
    var buttonCreate: Button? = null

    var imageFile1: File? = null
    var imageFile2: File? = null
    var imageFile3: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        myView = inflater.inflate(R.layout.fragment_restaurant_product, container, false)

        editTextName = myView?.findViewById(R.id.edittext_name)
        editTextDescription = myView?.findViewById(R.id.edittext_description)
        editTextPrice = myView?.findViewById(R.id.edittext_price)
        imageViewProduct1 = myView?.findViewById(R.id.imageview_image1)
        imageViewProduct2 = myView?.findViewById(R.id.imageview_image2)
        imageViewProduct3 = myView?.findViewById(R.id.imageview_image3)
        buttonCreate = myView?.findViewById(R.id.btn_create)

        buttonCreate?.setOnClickListener { createProduct() }

        imageViewProduct1?.setOnClickListener { selectImage(101) }
        imageViewProduct2?.setOnClickListener { selectImage(102) }
        imageViewProduct3?.setOnClickListener { selectImage(103) }

        return myView

    }

    private fun createProduct() {
        val name = editTextName?.text.toString()
        val description = editTextDescription?.text.toString()
        val priceText = editTextPrice?.text.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val fileUri = data?.data

            if (requestCode == 101) {
                imageFile1 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                imageViewProduct1?.setImageURI(fileUri)
            }
            else if (requestCode == 102) {
                imageFile2 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                imageViewProduct2?.setImageURI(fileUri)
            }

            else if (requestCode == 103) {
                imageFile3 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                imageViewProduct3?.setImageURI(fileUri)
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage(requestCode: Int) {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(requestCode)
    }
}