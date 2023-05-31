package com.example.martke.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.martke.data.CartProduct
import com.example.martke.firebase.FirebaseCommon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth:FirebaseAuth,
    private val  firebaseCommon: FirebaseCommon
):ViewModel() {
    private val _addToCart = MutableStateFlow<com.example.martke.util.Resource<CartProduct>>(com.example.martke.util.Resource.Unspecified())
        val addToCart = _addToCart.asStateFlow()

    fun addUpdateProductToCart(cartProduct: CartProduct){
        viewModelScope.launch {
            _addToCart.emit(com.example.martke.util.Resource.Loading())
        }
        firestore.collection("user").document(auth.uid!!).collection("cart")
            .whereEqualTo("product.id",cartProduct.product.id).get()
            .addOnSuccessListener {
                it.documents.let {
                    if (it.isEmpty()){
                        //add a new product
                        addNewProduct(cartProduct)

                    }else{
                        val product = it.first().toObject(CartProduct::class.java)
                        if (product == cartProduct){
                            //increase the quantity

                            val documentId =it.first().id
                            increaseQuantity(documentId,cartProduct)
                        }else{
                            addNewProduct(cartProduct)
                        }
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _addToCart.emit(com.example.martke.util.Resource.Error(it.message.toString()))
                }
            }
    }

    private fun addNewProduct(cartProduct: CartProduct){
        firebaseCommon.addProductToCart(cartProduct){
            addedProduct,e->
            viewModelScope.launch {
                if (e == null)
                    _addToCart.emit(com.example.martke.util.Resource.Success(addedProduct!!))
                else
                    _addToCart.emit(com.example.martke.util.Resource.Error(e.message.toString()))
            }
        }
    }
    private fun increaseQuantity(documentId:String,cartProduct: CartProduct){
        firebaseCommon.increaseQuantity(documentId){
            _,e ->

            viewModelScope.launch {
                if (e == null)
                    _addToCart.emit(com.example.martke.util.Resource.Success(cartProduct))
                else
                    _addToCart.emit(com.example.martke.util.Resource.Error(e.message.toString()))
            }
        }
    }
}