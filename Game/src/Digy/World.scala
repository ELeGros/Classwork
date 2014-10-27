package Digy

import scala.util.Random

class World(private var wData: scala.collection.Seq[Earth], private var entities: Vector[Entity]) {
  def update(): World = {
    var ents = for (e <- entities) yield e.update(wData)
    World(wData, ents)
  }

  def getEntities = entities
}
object World {
  def apply(dat: Seq[Earth], ent: Vector[Entity]): World = {
    new World(dat, ent)
  }

  def generate(): scala.collection.Seq[Earth] = {
    for (i <- 1 to 64; j <- 1 to 64) yield Earth(Point2D(i, j), Random.nextInt(4))
  }

  def apply(ent: Vector[Entity]): World = {
    new World(generate(), ent)
  }

}
