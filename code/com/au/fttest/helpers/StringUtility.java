/*    */ package com.au.fttest.helpers;
/*    */ 
/*    */ import java.text.StringCharacterIterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StringUtility
/*    */ {
/*    */   public static String ReplaceSpecialCharacters(String aRegexFragment)
/*    */   {
/* 15 */     StringBuilder result = new StringBuilder();
/*    */     
/* 17 */     StringCharacterIterator iterator = 
/* 18 */       new StringCharacterIterator(aRegexFragment);
/*    */     
/* 20 */     char character = iterator.current();
/* 21 */     while (character != 65535)
/*    */     {
/*    */ 
/*    */ 
/* 25 */       if (character == '.') {
/* 26 */         result.append("\\.");
/*    */       }
/* 28 */       else if (character == '\\') {
/* 29 */         result.append("\\\\");
/*    */       }
/* 31 */       else if (character == '?') {
/* 32 */         result.append("\\?");
/*    */       }
/* 34 */       else if (character == '*') {
/* 35 */         result.append("\\*");
/*    */       }
/* 37 */       else if (character == '+') {
/* 38 */         result.append("\\+");
/*    */       }
/* 40 */       else if (character == '&') {
/* 41 */         result.append("\\&");
/*    */       }
/* 43 */       else if (character == ':') {
/* 44 */         result.append("\\:");
/*    */       }
/* 46 */       else if (character == '{') {
/* 47 */         result.append("\\{");
/*    */       }
/* 49 */       else if (character == '}') {
/* 50 */         result.append("\\}");
/*    */       }
/* 52 */       else if (character == '[') {
/* 53 */         result.append("\\[");
/*    */       }
/* 55 */       else if (character == ']') {
/* 56 */         result.append("\\]");
/*    */       }
/* 58 */       else if (character == '(') {
/* 59 */         result.append("\\(");
/*    */       }
/* 61 */       else if (character == ')') {
/* 62 */         result.append("\\)");
/*    */       }
/* 64 */       else if (character == '^') {
/* 65 */         result.append("\\^");
/*    */       }
/* 67 */       else if (character == '$') {
/* 68 */         result.append("\\$");
/*    */       }
/*    */       else {
/* 71 */         result.append(character);
/*    */       }
/* 73 */       character = iterator.next();
/*    */     }
/* 75 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\helpers\StringUtility.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */