package Digy

import scala.util.Random

class World(private val wData: Vector[Vector[Earth]], private val entities: Vector[Entity]) extends Serializable{
  def update(): World = {
    var ents = for (e <- entities) yield e.update(wData)
    World(wData, ents.toVector)
  }

  def getEntities = entities
  
  def getData = wData
  def addEntity(e: Entity):World = {
    new World(wData, entities :+ e)
  }
  def sendUp(playerID: Int): World = {
    val players = entities.collect { case p: Player => p }
    println(players.size)
    println(entities.size, entities)
    val index = entities.indexOf(players(playerID))
    new World(wData, entities.updated(index, players(playerID).upPressed()))
  }

  def sendDown(playerID: Int): World = {
    val players = entities.collect { case p: Player => p }
    val index = entities.indexOf(players(playerID))
    new World(wData, entities.updated(index, players(playerID).downPressed())) 
  }

  def sendLeft(playerID: Int): World = {
    val players = entities.collect { case p: Player => p }
    val index = entities.indexOf(players(playerID))
    new World(wData, entities.updated(index, players(playerID).leftPressed()))
  }

  def sendRight(playerID: Int): World = {
    val players = entities.collect { case p: Player => p }
    val index = entities.indexOf(players(playerID))
    new World(wData, entities.updated(index, players(playerID).rightPressed()))
  }
}
object World {
  def apply(dat: Vector[Vector[Earth]], ent: Vector[Entity]): World = {
    new World(dat, ent)
  }

  def generate(): Vector[Vector[Earth]] = {
    {
      {
        for (i <- 1 to 64) yield {
          {
            for (j <- 1 to 64) yield {
              if (j > 44) Earth(1)
              else Earth(Random.nextInt(2) + 1)
            }
          }.toVector
        }
      }.toVector
    }
  }

  def generate(xDim: Integer, yDim: Integer): Vector[Vector[Earth]] = {
    {
      {
        for (i <- 1 to xDim) yield {
          { for (j <- 1 to yDim) yield Earth(Random.nextInt(2)) }.toVector
        }
      }.toVector
    }
  }

  def apply(ent: Vector[Entity]): World = {
    new World(generate(), ent)
  }

}
