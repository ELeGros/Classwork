package basics

object PhoneParsing extends App{
  case class PhoneRecord(name:String, restName:String, areaCode:String, number:String)

  val PhoneRegex = """(\w+( \w+)?), +(.+?)\t+\((\d\d\d)\) (.+)""".r
  val source = io.Source.fromFile("PhoneNumbers")
  val lines = source.getLines()
  val phoneData = (for(PhoneRegex(last, _, rest, area, number) <- lines) yield PhoneRecord(last,rest,area,number)).toArray
  source.close
  phoneData foreach println
}