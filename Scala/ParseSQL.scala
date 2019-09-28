package com.sh.test


object ParseSQL {
  def main(args: Array[String]): Unit = {
    val keys1 = List("SELECT", "FROM", "WHERE", ";")
    val keys = List("SELECT", "FROM", "WHERE", "GROUP", "ORDER","BY",";")
   
    val q1 = """ select x , y , z from T1
              where c=1 ;
            """
    val q = """ select x , y , count(*) from T1
              where c=1 group by x,y order by x,y ;
            """
    //way1(q1,keys1)
    way2(q,keys)
    
  }
  
  def formatQueryList(q:String):List[String]={
    val fq = q.replaceAll("\n", " ").trim().replaceAll(" +", " ")//.replaceAll("GROUP ", "GROUP_BY");
    val fqList = fq.split(" ").toList.filter(p=>p!=" ")
    fqList
  }
  
  def way2(q:String,keys:List[String]):Unit={
    var last_key=""
    var key_part="" 
    val fql = formatQueryList(q)
    fql.foreach{t=>
      //val isKey=keys.contains(t.toUpperCase())
      val ki=keys.indexWhere(p=>p.toUpperCase()==t.toUpperCase())
      if(ki >= 0){
        val current_key=keys(ki)
        if(last_key==""){
          last_key=current_key
          key_part=""
        }else{
          // BY Rule for GROUP BY/ SORT BY/ ORDER BY
          if(current_key.equalsIgnoreCase("BY")){
            last_key=last_key+" "+current_key
          }else{
            println(last_key+" : "+key_part.trim()) // Write to a file or else
            last_key=current_key
            key_part=""
          }
          
        }
      }else{
        key_part=key_part+" "+t
      }
    }
  }
  
  def way1(q:String,keys:List[String]):Unit={
    val fql = formatQueryList(q)
    println(fql)
    for( i <- 0 until keys.length-1){ 
      val j=i+1
      val ki=fql.indexWhere(p=>p.toUpperCase()==keys(i))
      val kj=fql.indexWhere(p=>p.toUpperCase()==keys(j))
      if((ki >= 0) & (kj >= 0)){
        val key_part= fql.slice(ki+1,kj).mkString(" ")
        println(keys(i)+" : "+key_part) // Need to write in file
      }    
    }
  }
}