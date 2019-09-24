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

object TarGzipFolder {

	def main(args: Array[String]): Unit = {
			val SOURCE_FOLDER = "/home/hadoop1/Documents/TEST1/SRC";
			val TARGET_FOLDER = "/home/hadoop1/Documents/TEST1/TGT";
			createTarFile(SOURCE_FOLDER,TARGET_FOLDER);
	}
	
	// Creating tar.gz File
	def createTarFile(sourceDir :String, targetDir : String) : Unit={
	  var tarOs : TarArchiveOutputStream = null;
	  val source = new File(sourceDir);
	  // Using input name to create output name
	  val target = targetDir+"/"+getZipFileName()
	  //val target = source.getAbsolutePath().concat(".tar.gz")
	  val fos = new FileOutputStream(target);
	  val gos = new GZIPOutputStream(new BufferedOutputStream(fos));
	  tarOs = new TarArchiveOutputStream(gos);
	  addFilesToTarGZ(sourceDir, "", tarOs);     
	  tarOs.close();
	}

	// Get Random Zip file name
	def getZipFileName():String = {
			val randomName = s"zip_${Random.alphanumeric take 10 mkString}"
			val fileName = new SimpleDateFormat(s"'${randomName}_'yyyyMMddHHmmssSSS'.tar.gz'").format(new Date());
			fileName
	}

	// Get Folder name
	def getFolderName():String = {
			val fileName = new SimpleDateFormat(s"yyyyMMddHHmmssSSS").format(new Date());
			fileName
	}
	
	// Add Files/Folder to tar.gz
	def addFilesToTarGZ(filePath : String,parent : String, tarArchive : TarArchiveOutputStream) :Unit= {
			val file = new File(filePath);
			
			// Create entry name relative to parent file path 
			var entryName = parent + file.getName();
			if(file.isDirectory()){
			  entryName = parent + getFolderName
			}
			// Add tar ArchiveEntry
			tarArchive.putArchiveEntry(new TarArchiveEntry(file, entryName));
			if(file.isFile()){
				val fis = new FileInputStream(file);
				val bis = new BufferedInputStream(fis);
				// Write file content to archive
				IOUtils.copy(bis, tarArchive);
				tarArchive.closeArchiveEntry();
				bis.close();
			}else if(file.isDirectory()){
				// no need to copy any content since it is
				// a directory, just close the outputstream
				tarArchive.closeArchiveEntry();
				// for files in the directories
				val files=file.listFiles()
						files.foreach{f=>
						// recursively call the method for all the subdirectories
						addFilesToTarGZ(f.getAbsolutePath(), entryName+File.separator, tarArchive);
				}
			}          
	}



}