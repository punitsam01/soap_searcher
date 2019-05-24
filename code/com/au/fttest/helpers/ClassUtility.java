/*    */ package com.au.fttest.helpers;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ClassUtility
/*    */ {
/*    */   public static boolean ClassExists(String className)
/*    */   {
/*    */     try
/*    */     {
/* 13 */       ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 14 */       Class.forName(className, false, classLoader);
/* 15 */       return true;
/*    */     }
/*    */     catch (ClassNotFoundException ex) {}
/*    */     
/* 19 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\helpers\ClassUtility.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */