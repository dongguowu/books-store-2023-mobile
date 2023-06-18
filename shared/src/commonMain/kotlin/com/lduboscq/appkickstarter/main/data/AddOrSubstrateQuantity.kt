package com.lduboscq.appkickstarter.main.data

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddOrSubstrateQuantity(
    cartLine: CartLine,
    update: (cartLine: CartLineData) -> Unit,
    delete: (cartLineId: String) -> Unit
) {

    Button(
        content = { Text("+") },
        onClick = {
            update(CartLineData(id = cartLine._id, quantity = cartLine.quantity + 1))
        },
    )
    Spacer(modifier = Modifier.height(18.dp))
    Text(text = "${cartLine?.quantity?.toString()}")
    Spacer(modifier = Modifier.height(18.dp))
    Button(
        content = { Text("-") },
        onClick = {
            var quantityToUpdate = cartLine.quantity - 1
            if (quantityToUpdate <= 0) {
                delete(cartLine._id)
            } else {
                update(CartLineData(id = cartLine._id, quantity = quantityToUpdate))
            }
        },
    )
}