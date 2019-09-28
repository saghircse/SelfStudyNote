package com.sh.test

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import scala.util.Random
import java.text.SimpleDateFormat
import java.util.Date

object TarGzipRenameFiles {

	def main(args: Array[String]): Unit = {
	  val SOURCE_FOLDER = "/home/hadoop1/Documents/TEST/SRC";
	  val TARGET_FOLDER = "/home/hadoop1/Documents/TEST/TGT";
		createTarGZIPFile(SOURCE_FOLDER,TARGET_FOLDER);
	}
	
	// Get file name with timestamp
  def getFileNameWithTS(fname : String):String = {
      val fileName = new SimpleDateFormat(s"'${fname.split('.')(0)}_'yyyyMMddHHmmssSSS'.txt'").format(new Date());
      fileName
	}
	
	// Get Random Zip file name
  def getZipFileName():String = {
			val randomName = s"zip_${Random.alphanumeric take 10 mkString}"
      val fileName = new SimpleDateFormat(s"'${randomName}_'yyyyMMddHHmmssSSS'.tar.gz'").format(new Date());
      fileName
	}

  // Function to create tar gzip file
	def createTarGZIPFile(sourceDir :String, targetDir : String) : Unit={
		
	  // 1. Create unique name for target zip folder(tar.gz)
	  val target = targetDir+"/"+getZipFileName()
	  
	  // 2. Create FileOutputStream of target
	  val fos = new FileOutputStream(target);
	  
	  // 3. Wrap the fos with GZIPOutputStream
	  val gos = new GZIPOutputStream(new BufferedOutputStream(fos));
	  
	  // 4. Wrap the gos with TarArchiveOutputStream
	  val tarOs = new TarArchiveOutputStream(gos);
	  
	  // 5. Add the files to source directory to tarOs
	  addFilesToTarGZ(sourceDir, tarOs);     
	  tarOs.close();
	}

	
	def addFilesToTarGZ(sourceDir : String,tarArchive : TarArchiveOutputStream) :Unit= {
		// List the files of the directory and add to tarArchive
		val files=new File(sourceDir).listFiles()
		files.foreach{file=> 
		  // Create unique filename with TS
		  val targetFileName = getFileNameWithTS(file.getName());
		  tarArchive.putArchiveEntry(new TarArchiveEntry(file, targetFileName));
		  val fis = new FileInputStream(file);
			val bis = new BufferedInputStream(fis);
			// Write file content to archive
			IOUtils.copy(bis, tarArchive);
			tarArchive.closeArchiveEntry();
			bis.close();        
		}  
	}


}