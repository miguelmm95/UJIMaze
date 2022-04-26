package es.uji.al386686.ujimaze

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import es.uji.jvilar.barbariangold.model.CellType
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity() {

    private var width = 0
    private var height = 0

    private val model = MainModel()
    private lateinit var controller : Controller

    private lateinit var graphics: Graphics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()
    }

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        controller = Controller(displayMetrics.widthPixels,
                    displayMetrics.heightPixels, model, this)
        return controller
    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height
        graphics = Graphics(width,height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.GRAY)

        drawMaze()
        drawPrincess()

        return graphics.frameBuffer
    }

    fun drawMaze(){
        for (row in 0 until  model.maze.nRows){
            for (col in 0 until model.maze.nCols){
                if(model.maze[row, col].type == CellType.WALL) {
                    graphics.drawRect(100f * col,100f * row,100f,100f,Color.RED)
                }
                if (model.maze[row, col].type == CellType.HOME){
                    drawMonsters()
                }
                if (model.maze[row, col].type == CellType.POTION){
                    graphics.drawRect(100f * col,100f * row,100f,100f,Color.GREEN)
                }
                if(model.maze[row,col].type == CellType.GOLD){
                    graphics.drawCircle(100f*col + 50f,100f*row + 50f,25f/2,Color.YELLOW)
                }
            }
        }
    }

    fun drawPrincess(){
        graphics.drawCircle(model.princess.xPos * 100f,model.princess.yPos * 100f,100f/2, Color.BLUE)
    }

    fun drawMonsters(){
        for(m in model.monsters){
            graphics.drawRect(m.xPos * 100f - 50f,m.yPos * 100f - 50f,100f,100f,Color.BLUE)
        }
    }
}