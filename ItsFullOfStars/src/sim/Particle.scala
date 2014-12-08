package sim

class Particle(private var mx: Point3D, private var mv: Vect3D, val mass: Double, val radius: Double) extends Serializable{
  def x = mx
  def v = mv
  def step(dt: Double):Unit = mx += v*dt
  def accelerate(a: Vect3D, dt: Double):Unit = mv += a * dt
  def advance(a: Vect3D, dt:Double):Unit = {
    accelerate(a, dt)
//    println(dt,v)
    step(dt)
  }
}