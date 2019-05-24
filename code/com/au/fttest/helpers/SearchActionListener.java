/*    */ package com.au.fttest.helpers;
/*    */ 
/*    */ import com.au.fttest.plugins.SearchWindow;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.awt.event.KeyEvent;
/*    */ import java.awt.event.KeyListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JOptionPane;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class SearchActionListener
/*    */   implements ActionListener, KeyListener
/*    */ {
/*    */   Object jObject;
/*    */   JTextField jTextField;
/*    */   JDialog dialog;
/*    */   String searchString;
/*    */   SearchWindow searchWindow;
/*    */   
/*    */   public SearchActionListener(Object jObject, JTextField jTextField, JDialog dialog, SearchWindow searchWindow)
/*    */   {
/* 26 */     this.jObject = jObject;
/* 27 */     this.jTextField = jTextField;
/* 28 */     this.dialog = dialog;
/* 29 */     this.searchWindow = searchWindow;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void actionPerformed(ActionEvent submitClicked)
/*    */   {
/* 37 */     if (((this.jObject instanceof JButton)) && (((JButton)this.jObject).getName() == "SearchButton"))
/*    */     {
/* 39 */       if (this.jTextField.getText().trim().equals(""))
/*    */       {
/* 41 */         JOptionPane.showMessageDialog(null, "Please enter a search string.");
/* 42 */         this.jTextField.requestFocusInWindow();
/*    */       }
/*    */       else
/*    */       {
/* 46 */         this.dialog.setCursor(Cursor.getPredefinedCursor(3));
/* 47 */         this.searchWindow.ExecuteSearch();
/*    */       }
/*    */     }
/* 50 */     else if (((this.jObject instanceof JButton)) && (((JButton)this.jObject).getName() == "CancelButton"))
/*    */     {
/* 52 */       this.searchWindow.windowCloseFlag = Integer.valueOf(1);
/* 53 */       this.dialog.setVisible(false);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void keyPressed(KeyEvent e)
/*    */   {
/* 61 */     if (e.getKeyCode() == 10)
/*    */     {
/* 63 */       if ((this.jObject instanceof JTextField))
/*    */       {
/* 65 */         this.jTextField = ((JTextField)this.jObject);
/*    */       }
/*    */       
/* 68 */       if ((((this.jObject instanceof JButton)) && (((JButton)this.jObject).getName() == "SearchButton")) || ((this.jObject instanceof JTextField)))
/*    */       {
/* 70 */         if (this.jTextField.getText().trim().equals(""))
/*    */         {
/* 72 */           JOptionPane.showMessageDialog(null, "Please enter a search string.");
/* 73 */           this.jTextField.requestFocusInWindow();
/*    */         }
/*    */         else
/*    */         {
/* 77 */           this.dialog.setCursor(Cursor.getPredefinedCursor(3));
/* 78 */           this.searchWindow.ExecuteSearch();
/*    */         }
/*    */       }
/* 81 */       else if (((this.jObject instanceof JButton)) && (((JButton)this.jObject).getName() == "CancelButton"))
/*    */       {
/* 83 */         this.searchWindow.windowCloseFlag = Integer.valueOf(1);
/* 84 */         this.dialog.setVisible(false);
/*    */ 
/*    */ 
/*    */       }
/* 88 */       else if ((this.jObject instanceof JCheckBox))
/*    */       {
/* 90 */         if (((JCheckBox)this.jObject).isSelected())
/*    */         {
/* 92 */           ((JCheckBox)this.jObject).setSelected(false);
/*    */         }
/*    */         else
/*    */         {
/* 96 */           ((JCheckBox)this.jObject).setSelected(true);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void keyReleased(KeyEvent arg0) {}
/*    */   
/*    */   public void keyTyped(KeyEvent arg0) {}
/*    */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\helpers\SearchActionListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */