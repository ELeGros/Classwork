package sim

class Particle(private var mx: Point3D, private var mv: Vect3D, val mass: Double, val radius: Double) extends Serializable {
  def x = mx
  def v = mv
  def step(dt: Double): Unit = mx += v * dt
  def accelerate(a: Vect3D, dt: Double): Unit = mv += a * dt
  def advance(a: Vect3D, dt: Double): Unit = {
    accelerate(a, dt)
    //    println(dt,v)
    step(dt)
  }
}

object Particle {
  def collisionTime(p1: Particle, p2: Particle): Double = {
    val dx = p1.x - p2.x
    val dv = p1.v - p2.v
    val a = dv dot dv
    val b = 2 * (dx dot dv)
    val c = (dx dot dx) - (p1.radius + p2.radius) * (p1.radius + p2.radius)
    val root = (b * b) - (4 * a * c)
    if (root < 0) -1
    else {
      (-b - math.sqrt(root)) / (2 * a)
    }
  }

  def bounce(p1: Particle, p2: Particle, time: Double): Unit = {
    p1.step(time)
    p2.step(time)
    val dx = p1.x - p2.x
    val dnormal = dx / (dx.magnitude)
    val dv = p1.v - p2.v
    val cmv = (p1.v * p1.mass + p2.v * p2.mass) / (p1.mass + p2.mass)
    val cmv1 = p1.v - cmv
    val cmv2 = p2.v - cmv
    val cmv1perp = dnormal * (cmv1 dot dnormal)
    val cmv2perp = dnormal * (cmv2 dot dnormal)
//    println("before: " + p1.mv + ", " + p2.mv + ", " + cmv1perp + ", " + cmv2perp)
    p1.mv = p1.v - cmv1perp * 1.8
    p2.mv = p2.v - cmv2perp * 1.8
//    println("after: " + p1.mv + ", " + p2.mv)
  }
}