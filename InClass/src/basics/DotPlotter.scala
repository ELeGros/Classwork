package basics

import swing._
import java.awt.Color
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D

case class Dot(x: Double, y: Double, size: Double)

class Plotter(dots:Seq[Dot], xmin:Double, xmax:Double, ymin:Double, ymax: Double) extends Panel {
  override def paint(g:Graphics2D) {
    g.setPaint(Color.black)
    g.fill(new Rectangle2D.Double(0,0,size.width, size.height))
    g.setPaint(Color.white)
    for(dot <- dots)
    {
      val dx = size.width*(dot.x-xmin)/(xmax-xmin)
      val dy = size.height - size.height*(dot.y-ymin)/(ymax-ymin)
      val sx = dot.size*size.width/(xmax-xmin)
      val sy = dot.size*size.height/(ymax-ymin)
      g.fill(new Ellipse2D.Double(dx-sx/2,dy-sy/2,sx,sy))
    }
  }
  
  preferredSize = new Dimension(600,600)
}


object DotPlotter extends App {
  val plotter = new Plotter(Seq(Dot(0,0,1), Dot(0,.5,2)), -10, 10, -10, 10)
  val frame = new MainFrame {
    title = "Dot Plotter"
    contents = plotter
  }

}