package com.sh.test

import java.nio.file.{Files, Paths, StandardCopyOption}
import java.io.File
import scala.util.Random
import java.text.SimpleDateFormat
import java.util.Date

object MoveRenameFiles {
  
  def main(args: Array[String]): Unit = {
    //val src_file="/home/hadoop1/Documents/TEST/SRC/f1.txt"
    //val tgt_file="/home/hadoop1/Documents/TEST/TGT/f1_xyz.txt"
    //moveRenameFile(src_file,tgt_file)
    //copyRenameFile(src_file,tgt_file)
    
    val src_path="/home/hadoop1/Documents/TEST/SRC" // Give your source directory
    val tgt_path="/home/hadoop1/Documents/TEST/TGT" // Give your target directory
    
    val FileList=getListOfFiles(src_path)
    
    FileList.foreach{f => 
      val src_file = f.toString()
      val tgt_file = tgt_path + "/" + getFileNameWithTS(f.getName)
      copyRenameFile(src_file,tgt_file)
      //moveRenameFile(src_file,tgt_file) - Try this if you want to delete source file
      println("File Copied : " + tgt_file)
    }
    
  }
  
  // Get Random file name - Not required now
  def getRandomFileName():String = {
			val randomName = s"file_${Random.alphanumeric take 15 mkString}"
      val fileName = new SimpleDateFormat(s"'${randomName}_'yyyyMMddHHmmss'.txt'").format(new Date());
      fileName
	}
  
  // Get file name with timestamp
  def getFileNameWithTS(fname : String):String = {
      val fileName = new SimpleDateFormat(s"'${fname.split('.')(0)}_'yyyyMMddHHmmssSSS'.txt'").format(new Date());
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
  
  // Function to move file from source to target - Deletes the source file
  def moveRenameFile(source: String, destination: String): Unit = {
    
    val path = Files.move(
        Paths.get(source),
        Paths.get(destination),
        StandardCopyOption.REPLACE_EXISTING
        //StandardCopyOption.ATOMIC_MOVE  
    )
  }
  
  // Function to copy file from source to target - Retain the source file
  def copyRenameFile(source: String, destination: String): Unit = {
    
    val path = Files.copy(
        Paths.get(source),
        Paths.get(destination),
        StandardCopyOption.REPLACE_EXISTING
        //StandardCopyOption.ATOMIC_MOVE  
    )
  }
}