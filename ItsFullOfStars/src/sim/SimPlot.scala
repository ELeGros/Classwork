package sim

import swing._
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D

class SimPlot(sim: Simulation, xmin: Double, xmax: Double, ymin: Double, ymax: Double) extends BorderPanel {
  override def paint(g: Graphics2D) {
    g.setPaint(Color.black)
    g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
    g.setPaint(Color.white)
    for (part <- sim.getParticles) {
      val dx = (size.width * (part.x.x - xmin) / (xmax - xmin))
      val dy = (size.height - size.height * (part.x.y - ymin) / (ymax - ymin))
      val sx = (part.radius * size.width / (xmax - xmin))*.1
      val sy = (part.radius * size.height / (ymax - ymin))*.1
      g.fill(new Ellipse2D.Double(dx - sx / 2, dy - sy / 2, sx, sy))
    }
  }

  preferredSize = new Dimension(600, 600)
}

