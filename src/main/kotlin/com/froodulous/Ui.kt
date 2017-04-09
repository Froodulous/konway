package com.froodulous

import javafx.scene.text.FontWeight
import tornadofx.*

/**
 * JavaFX Application UI.
 * Created by luke on 09/04/2017.
 */

class Ui : App(MyView::class, Styles::class)

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c("#cecece")
        }
    }
}

class MyView : View() {

    override val root = hbox {
        label("Hello, World!")
    }
}