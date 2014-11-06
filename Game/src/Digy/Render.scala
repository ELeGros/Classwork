package Digy

import swing._
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D

class Render(wData: World, xmin: Double, xmax: Double, ymin: Double, ymax: Double) extends BorderPanel {
  override def paint(g: Graphics2D) {
    g.setPaint(Color.cyan)
    g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    for (i <- 0 to wData.getData.size - 1) {
      for (j <- 0 to wData.getData(i).size - 1) {
        wData.getData(i)(j).getKind match {
          case 2 => {
            g.setPaint(Color.DARK_GRAY)
            val dx = (size.width * (i) / (64))
            val dy = (size.height - size.height * (j) / (64))
            val sx = (size.width / (64))
            val sy = (size.height / (64))
            g.fill(new Rectangle2D.Double(dx - sx / 2, dy - sy / 2, sx, sy))
//          println("DEBUG:  Should be drawing a rectangle here")
          }
          case 1 => {}
        }
      }
    }
    g.setPaint(Color.white)
    for (ent <- wData.getEntities) {
      val dx = (size.width * (ent.location.x))
      val dy = (size.height - size.height * (ent.location.y))
      val sx = (ent.bounds._1 * size.width / (64))
      val sy = (ent.bounds._2 * size.height / (64))
      g.fill(new Ellipse2D.Double(dx - sx / 2, dy - sy / 2, sx, sy))
    }
  }

  preferredSize = new Dimension(640, 640)
}
