package aej.android.enthusiast.gofood_clone.designsystem.utils

sealed class ButtonType {
    object Primary : ButtonType()
    object Outline : ButtonType()
    object Text : ButtonType()
}