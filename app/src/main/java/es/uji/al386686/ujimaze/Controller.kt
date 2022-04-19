package es.uji.al386686.ujimaze

import es.uji.jvilar.barbariangold.controller.GestureDetector
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(var width:Int, var height : Int, private val model : MainModel, private val view : MainActivity) : IGameController {

    private val gesture = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        model.update(deltaTime)

        if (touchEvents != null) {
            for (event in touchEvents){
                /*Log.d("WIDTH", "$width")
                Log.d("HEIGHT","$height")
                Log.d("EVENT X", "${event.x}")
                Log.d("EVENT Y", "${event.y}")
                Log.d("X / WIDHT", ((event.x).toFloat() / (width).toFloat()).toString())
                Log.d("Y / HEIGHT", ((event.y / height).toFloat()).toString())*/
                val normalizedX : Float = ((event.x).toFloat() / (width).toFloat())
                val normalizedY : Float = ((event.y).toFloat() / (height).toFloat()).toFloat()
                //Log.d("COORDENADAS EVENT", "$normalizedX - $normalizedY")


                when(event.type){
                    TouchHandler.TouchType.TOUCH_DOWN ->{
                        gesture.onTouchDown(normalizedX,normalizedY)
                    }

                    TouchHandler.TouchType.TOUCH_UP ->{
                        var action = gesture.onTouchUp(normalizedX,normalizedY)

                        if( action == GestureDetector.Gestures.SWIPE){
                            model.princess.direction = gesture.direction
                        }
                    }
                }
            }
        }
    }


}