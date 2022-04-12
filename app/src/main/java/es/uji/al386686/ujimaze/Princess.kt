package es.uji.al386686.ujimaze

import es.uji.al386686.ujimaze.Model.Direction
import es.uji.al386686.ujimaze.Model.Position

class Princess(var xPos : Float, var yPos : Float, var direction: Direction) {

    companion object{
        private const val SPEED = 1.5f
    }

    fun move(deltaTime: Float) {
        xPos  += SPEED * deltaTime * direction.col
        yPos += SPEED * deltaTime * direction.row
    }
}