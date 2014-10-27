package Digy

import swing._
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D

class renderPlot(wData: World, xmin: Double, xmax: Double, ymin: Double, ymax: Double) extends BorderPanel {
  override def paint(g: Graphics2D) {
    g.setPaint(Color.blue)
    g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    g.setPaint(Color.white)
    for (ent <- wData.getEntities) {
      val dx = (size.width * (ent.location.x - xmin) / (xmax - xmin))
      val dy = (size.height - size.height * (ent.location.y - ymin) / (ymax - ymin))
      val sx = (ent.bounds._1 * size.width / (xmax - xmin))
      val sy = (ent.bounds._2 * size.height / (ymax - ymin))
      g.fill(new Ellipse2D.Double(dx - sx / 2, dy - sy / 2, sx, sy))
    }
  }

  preferredSize = new Dimension(600, 600)
}
