package es.uji.al386686.ujimaze

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.uji.jvilar.barbariangold.model.CellType
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

        for (row in 0 until  model.maze.nRows){
            for (col in 0 until model.maze.nCols){
                if(model.maze[row, col].type == CellType.WALL) {
                    graphics.drawRect(100f * col,100f * row,100f,100f,Color.RED)
                }
                if (model.maze[row, col].type == CellType.HOME){
                    graphics.drawRect(100f * col,100f * row,100f,100f,Color.BLUE)
                }
                if (model.maze[row, col].type == CellType.POTION){
                    graphics.drawRect(100f * col,100f * row,100f,100f,Color.GREEN)
                }
                if (model.maze[row, col].type == CellType.ORIGIN){
                    graphics.drawRect(100f * col,100f * row,100f,100f,Color.CYAN)
                }
            }
        }

        //graphics.drawRect(100f,100f,100f,100f,Color.RED)

        return graphics.frameBuffer
    }
}