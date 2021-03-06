package renetik.android.view.extensions

import android.content.res.ColorStateList
import android.widget.CompoundButton
import androidx.annotation.ColorInt
import renetik.android.java.event.*
import renetik.android.java.event.property.CSEventProperty
import renetik.android.java.event.property.isTrue

fun CompoundButton.onChecked(function: (CompoundButton) -> Unit) = apply {
    setOnCheckedChangeListener { buttonView, _ -> function(buttonView) }
}

fun CompoundButton.buttonTint(@ColorInt value: Int?) = apply {
    buttonTintList = if (value != null) ColorStateList.valueOf(value) else null
}

fun CompoundButton.isCheckedIf(parent: CSVisibleEventOwner, property: CSEventProperty<Boolean>) = apply {
    val onChangeRegistration = parent.whileShowing(property.onChange {
        isChecked = it
    })
    onChecked {
        onChangeRegistration.pause()
        property.value(it.isChecked)
        onChangeRegistration.resume()
    }
    isChecked = property.isTrue
}

fun CompoundButton.isCheckedIf(property: CSEventProperty<Boolean>) = apply {
    onChecked { property.value(it.isChecked) }
    isChecked = property.isTrue
}