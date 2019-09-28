package com.sh.test

import java.io.File
import scala.util.Random
import java.text.SimpleDateFormat
import java.util.Date


object ZipFiles {
  
  def main(args: Array[String]): Unit = {
      val src_path="/home/hadoop1/Documents/TEST/SRC"
      val tgt_path="/home/hadoop1/Documents/TEST/TGT"
    	val l = getListOfFiles(src_path)
    	val f=l.map(f=>f.toString())
    	
    	println("======>Source Files")
    	println(f)
    	
    	println("======>Target .tar.gz file")
    	val out=tgt_path+"/"+getRandomFileName()
    	zip(out,f)
    	println("Files are compressed at : "+ out)
    
  }
  
    // Get Random file name
  def getRandomFileName():String = {
			val randomName = s"zip_${Random.alphanumeric take 15 mkString}"
      val fileName = new SimpleDateFormat(s"'${randomName}_'yyyyMMddHHmmss'.tar.gz'").format(new Date());
      fileName
	}
  
  // Get list of Files
  	def getListOfFiles(dir: String):List[File] = {
			val d = new File(dir)
					if (d.exists && d.isDirectory) {
						d.listFiles.filter(_.isFile).toList
					} else {
						List[File]()
					}
	}
  
  def zip(out: String, files: Iterable[String]) = {
		  import java.io.{ BufferedInputStream, FileInputStream, FileOutputStream }
		  import java.util.zip.{ ZipEntry, ZipOutputStream }

		  val zip = new ZipOutputStream(new FileOutputStream(out))

				  files.foreach { name =>
				  zip.putNextEntry(new ZipEntry(name))
				  val in = new BufferedInputStream(new FileInputStream(name))
				  var b = in.read()
				  while (b > -1) {
					  zip.write(b)
					  b = in.read()
				  }
				  in.close()
				  zip.closeEntry()
		  }
		  zip.close()
  }
  
}