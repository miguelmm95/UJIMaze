package es.uji.al386686.ujimaze

import es.uji.jvilar.barbariangold.controller.GestureDetector
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(var width: Int, var height: Int, private val model: MainModel, private val view: MainActivity) : IGameController {

    private val gesture = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if (model.state == GameStates.GAME){
            model.update(deltaTime)
            view.calculateMeasures(model)
        }

        if (touchEvents != null) {
            for (event in touchEvents) {

                val normalizedX: Float = ((event.x).toFloat() / (width).toFloat())
                val normalizedY: Float = ((event.y).toFloat() / (height).toFloat())

                when (event.type) {
                    TouchHandler.TouchType.TOUCH_DOWN -> {
                        gesture.onTouchDown(normalizedX, normalizedY)
                    }

                    TouchHandler.TouchType.TOUCH_UP -> {
                        if (model.state == GameStates.GAME) {
                            var action = gesture.onTouchUp(normalizedX, normalizedY)

                            if (action == GestureDetector.Gestures.SWIPE) {
                                model.changePrincessDirection(gesture.direction)
                            }
                        }

                        if (model.state == GameStates.GAMEOVER) {
                            var action = gesture.onTouchUp(normalizedX,normalizedY)

                            if(action == GestureDetector.Gestures.CLICK){
                                model.restartGame()
                            }
                        }
                    }
                }
            }
        }
    }


}