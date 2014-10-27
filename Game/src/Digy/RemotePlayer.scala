package Digy

@remote trait RemotePlayer {
  def upPressed(): Unit
  def downPressed(): Unit
  def leftPressed(): Unit
  def rightPressed(): Unit

}