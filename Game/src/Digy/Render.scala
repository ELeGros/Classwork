package Digy

import swing._
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D

class Render(var wData: World, xmin: Double, xmax: Double, ymin: Double, ymax: Double) extends Panel {
  def setWorld(n: World) = wData = n
  override def paint(g: Graphics2D) {
    g.setPaint(Color.cyan)
    g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    val sx = (size.width / (64.0))
    val sy = (size.height / (64.0))
    g.setPaint(Color.DARK_GRAY)
    for (i <- 0 to wData.getData.size - 1) {
      for (j <- 0 to wData.getData(i).size - 1) {
        wData.getData(i)(j).getKind match {
          case 2 => {
            val dx = (size.width * (i) / (64.0))
            val dy = (size.height - size.height * (j + 1) / (64.0))
            g.fill(new Rectangle2D.Double(dx, dy, sx, sy))
            //          println("DEBUG:  Should be drawing a rectangle here")
          }
          case 1 => {}
        }
      }
    }
    g.setPaint(Color.white)
    for (ent <- wData.getEntities) {
//      println(ent.location.y)
      val hx = (sx * ent.location.x)
      val hy = (size.height - sy * ent.location.y)
//      println("RENDER DEBUG:     Should be drawing at (" + hx + "," + hy + ")")
      val ix = (ent.bounds._1 * sx)
      val iy = (ent.bounds._2 * sy)
      g.fill(new Ellipse2D.Double(hx - ix / 2, hy - iy / 2, ix, iy))
    }
  }

  preferredSize = new Dimension(640, 640)
}
