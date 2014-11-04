package Digy

import scala.util.Random

class World(private var wData: Vector[Vector[Earth]], private var entities: Vector[Entity]) {
  def update(): World = {
    var ents = for (e <- entities) yield e.update(wData)
    World(wData, ents) 
  }

  def getEntities = entities
  def getData = wData
}
object World {
  def apply(dat: Vector[Vector[Earth]], ent: Vector[Entity]): World = {
    new World(dat, ent)
  }

  def generate(): Vector[Vector[Earth]] = {
    {
      {for (i <- 1 to 64){ 
        var v2 = {for(j <- 1 to 64) yield Earth(Random.nextInt(2))}.toVector
        
      } yield v2}.toVector
  }
  }

  def apply(ent: Vector[Entity]): World = {
    new World(generate(), ent)
  }

}
