package es.uji.al386686.ujimaze

import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(private val model : MainModel, private val view : MainActivity) : IGameController {

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        model.update(deltaTime)

        if (touchEvents != null) {
            for (event in touchEvents){
                if (event.type == TouchHandler.TouchType.TOUCH_DOWN){

                }
            }
        }
    }


}