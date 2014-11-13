package basics

import scala.util.parsing.combinator.JavaTokenParsers

class EnglishParser(s:String) extends JavaTokenParsers {
  def sentence: Parser[Any] = nounPhrase ~ verbPhrase
  def nounPhrase:Parser[Any] = det ~ noun
  def verbPhrase:Parser[Any] = verb ~ nounPhrase
  def det:Parser[Any] = "the" | "a" | "one" 
  def noun:Parser[Any] = "cat" | "dog" | "bird" | "student" | "parser"
  def verb:Parser[Any] = "flew" | "chased" | "coded"
  
  val result = parseAll(sentence,s)
  println(result)
}

object EnglishParser extends App{
  val s = new EnglishParser("the dog chased the cat")
  val s2 = new EnglishParser("the student coded a parser")
  
}