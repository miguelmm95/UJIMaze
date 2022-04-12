package es.uji.al386686.ujimaze

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity() {

    private var width = 0
    private var height = 0

    private val model = MainModel()

    private lateinit var graphics: Graphics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
    }

    override fun buildGameController() = Controller(model,this)

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height
        graphics = Graphics(width,height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.WHITE)
        graphics.drawRect(100f,100f,100f,100f,Color.RED)

        return graphics.frameBuffer
    }
}