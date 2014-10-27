package Digy

class World(private var wData: Map[Point2D, Earth], private var entities: Vector[Entity]) {
    def update(): World = {
      var ents = for(e <- entities) yield e.update(wData)
      World(wData, ents)
    }
    
    def getEntities = entities
}
object World {
  def apply(dat: Map[Point2D, Earth], ent: Vector[Entity]): World = {
    new World(dat, ent)
  }
}