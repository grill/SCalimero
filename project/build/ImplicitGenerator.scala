import sbt._
import java.io._

class ImplicitGeneratorProject(info: ProjectInfo) extends DefaultProject(info)
{
  lazy val genImplicits = task {args =>
    if(args.length > 1) {
      val file = args.subArray(1,args.length).foldLeft(mainScalaSourcePath){(p : Path, s : String) => p / s}.asFile
      if(file.isFile)
        task{genImplicitsImpl(args(0), file); None}
      else
        task{ Some(file.toString + " is not a file!") }
    } else
      task { Some("Usage: gen-implicits <template(%c will be replaced)> <directory>* <file>") }
  }
  
  def genImplicitsImpl(template : String, file : File) {
    val br = new BufferedReader(new FileReader(file))
    var line = br.readLine
    while(line != null) {
      magic(template, line)
      line = br.readLine
    }
  }
  
  def magic(template : String, line : String) {
    "  class ([!\\w]+)".r.
      findPrefixMatchOf(line).
      map(_.group(1)).
      map(s => println("%c".r.replaceAllIn(template, s)))
  }
}
