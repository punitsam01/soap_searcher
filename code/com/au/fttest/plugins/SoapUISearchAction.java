/*     */ package com.au.fttest.plugins;
/*     */ 
/*     */ import com.au.fttest.helpers.ClassUtility;
/*     */ import com.au.fttest.helpers.SearchUtility;
/*     */ import com.eviware.soapui.model.ModelItem;
/*     */ import com.eviware.soapui.model.workspace.Workspace;
/*     */ import com.eviware.soapui.support.UISupport;
/*     */ import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
/*     */ import com.eviware.soapui.support.components.ModelItemListDesktopPanel;
/*     */ import java.awt.Cursor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JDialog;
/*     */ 
/*     */ public class SoapUISearchAction
/*     */   extends AbstractSoapUIAction
/*     */ {
/*  19 */   public Integer windowCloseFlag = Integer.valueOf(0);
/*  20 */   public String token = "";
/*  21 */   public String tokenOrig = "";
/*  22 */   public String selectedItem = "";
/*  23 */   public Integer nameSearchFlag = Integer.valueOf(0);
/*  24 */   public boolean soapUIProFlag = false;
/*     */   public List<ModelItem> modelItemList;
/*     */   public List<ModelItem> projectList;
/*     */   
/*     */   public SoapUISearchAction()
/*     */   {
/*  30 */     super("Search", "Provides SoapUI Search Functionality");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void perform(ModelItem target, Object param)
/*     */   {
/*     */     try
/*     */     {
/*  42 */       if (ClassUtility.ClassExists("com.eviware.soapui.impl.wsdl.WsdlProjectPro"))
/*     */       {
/*  44 */         this.soapUIProFlag = true;
/*     */       }
/*     */       
/*  47 */       List<String> dropDownList = new ArrayList();
/*  48 */       this.modelItemList = new ArrayList();
/*  49 */       this.projectList = new ArrayList();
/*  50 */       this.projectList = ((Workspace)target).getProjectList();
/*  51 */       dropDownList.add(" ");
/*     */       
/*     */       Iterator localIterator2;
/*  54 */       for (Iterator localIterator1 = this.projectList.iterator(); localIterator1.hasNext(); 
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*  59 */           localIterator2.hasNext())
/*     */       {
/*  54 */         ModelItem mItem = (ModelItem)localIterator1.next();
/*     */         
/*  56 */         this.modelItemList.add(mItem);
/*  57 */         dropDownList.add(mItem.getName() + " -> Project");
/*     */         
/*  59 */         localIterator2 = mItem.getChildren().iterator(); continue;ModelItem child = (ModelItem)localIterator2.next();
/*     */         
/*  61 */         this.modelItemList.add(child);
/*  62 */         dropDownList.add(child.getName() + " -> TestSuite");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  67 */       SearchWindow searchWindow = new SearchWindow(this, dropDownList);
/*  68 */       searchWindow.OpenSearchWindow();
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/*  72 */       e1.printStackTrace();
/*  73 */       UISupport.showErrorMessage("Failed during Search Window Load. Check Error log file for details.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void SearchAndDisplayResults(JDialog dialog)
/*     */     throws Exception
/*     */   {
/*  85 */     List<ModelItem> tempSearchResult = new ArrayList();
/*  86 */     List<ModelItem> searchResult = new ArrayList();
/*  87 */     List<ModelItem> searchList = new ArrayList();
/*     */     
/*     */ 
/*  90 */     if (this.windowCloseFlag.intValue() == 0)
/*     */     {
/*     */ 
/*  93 */       if ((this.selectedItem != null) && (this.selectedItem.trim() != ""))
/*     */       {
/*  95 */         this.selectedItem = this.selectedItem.split("->")[0].trim();
/*     */         
/*  97 */         for (ModelItem modelItem : this.modelItemList)
/*     */         {
/*  99 */           if (modelItem.getName().trim().equalsIgnoreCase(this.selectedItem))
/*     */           {
/* 101 */             searchList.add(modelItem);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 107 */       Integer searchListSize = Integer.valueOf(searchList.size());
/*     */       
/*     */ 
/* 110 */       if (searchListSize.intValue() == 0)
/*     */       {
/* 112 */         searchList.addAll(this.projectList);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 117 */       for (ModelItem modelItem : searchList)
/*     */       {
/* 119 */         SearchUtility sUtility = new SearchUtility(this.nameSearchFlag);
/* 120 */         tempSearchResult = sUtility.SearchItemInSoapUI(modelItem, this.token, dialog, Boolean.valueOf(this.soapUIProFlag));
/* 121 */         searchResult.addAll(tempSearchResult);
/*     */       }
/*     */       
/* 124 */       if (searchResult.isEmpty())
/*     */       {
/* 126 */         UISupport.showErrorMessage("No items matching \"" + this.tokenOrig + "\"" + " has been found in project ");
/* 127 */         dialog.setCursor(Cursor.getPredefinedCursor(0));
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 133 */         dialog.setVisible(false);
/* 134 */         dialog.dispose();
/* 135 */         UISupport.showDesktopPanel(new ModelItemListDesktopPanel("Search Result", "The following items matched the search string \"" + this.tokenOrig + "\"", (ModelItem[])searchResult.toArray(new ModelItem[searchResult.size()])));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\plugins\SoapUISearchAction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */