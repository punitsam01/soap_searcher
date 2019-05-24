/*     */ package com.au.fttest.plugins;
/*     */ 
/*     */ import com.au.fttest.helpers.SearchActionListener;
/*     */ import com.eviware.soapui.support.UISupport;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SpringLayout;
/*     */ 
/*     */ public class SearchWindow
/*     */ {
/*  22 */   public Integer windowCloseFlag = Integer.valueOf(0);
/*     */   
/*     */   SoapUISearchAction searchAction;
/*     */   
/*     */   List<String> dropDownList;
/*  27 */   JFrame frame = new JFrame();
/*  28 */   JDialog dialog = new JDialog(this.frame, "SoapUI Search");
/*  29 */   JComboBox comboBox = new JComboBox();
/*  30 */   JTextField textField = new JTextField("", 25);
/*  31 */   JCheckBox regExCheckBox = new JCheckBox("Use Search String as Regular Expression");
/*  32 */   JCheckBox onlyNameCheckBox = new JCheckBox("Search item names ONLY");
/*  33 */   JButton searchButton = new JButton("Search !!");
/*  34 */   JButton cancelButton = new JButton("Cancel");
/*     */   
/*     */   public SearchWindow(SoapUISearchAction searchAction, List<String> dropDownList)
/*     */   {
/*  38 */     this.searchAction = searchAction;
/*  39 */     this.dropDownList = dropDownList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void OpenSearchWindow()
/*     */   {
/*     */     try
/*     */     {
/*  51 */       ClassLoader classLoader = getClass().getClassLoader();
/*  52 */       java.io.InputStream searchImage = classLoader.getResourceAsStream("SearchImage.png");
/*  53 */       java.io.InputStream searchTitle = classLoader.getResourceAsStream("SearchTitle.jpg");
/*     */       
/*     */ 
/*     */ 
/*  57 */       JLabel comboLabel = new JLabel("Select Project/TestSuite (Optional)");
/*  58 */       JLabel textBoxLabel = new JLabel("<html><font color='red'>*</font> Look For</html>");
/*  59 */       JLabel imageLabel = new JLabel(new javax.swing.ImageIcon(javax.imageio.ImageIO.read(searchImage)));
/*  60 */       this.comboBox = new JComboBox(this.dropDownList.toArray());
/*  61 */       SpringLayout layout = new SpringLayout();
/*  62 */       JPanel contentPane = new JPanel();
/*  63 */       java.awt.Image img = javax.imageio.ImageIO.read(searchTitle);
/*     */       
/*     */ 
/*     */ 
/*  67 */       comboLabel.setFont(new Font("Calibri", 0, 14));
/*  68 */       comboLabel.setLabelFor(this.comboBox);
/*  69 */       textBoxLabel.setFont(new Font("Calibri", 0, 14));
/*  70 */       textBoxLabel.setLabelFor(this.textField);
/*  71 */       this.comboBox.setFont(new Font("Calibri", 0, 14));
/*  72 */       this.textField.setFont(new Font("Calibri", 0, 14));
/*  73 */       this.searchButton.setName("SearchButton");
/*  74 */       this.searchButton.setFont(new Font("Calibri", 0, 14));
/*  75 */       this.searchButton.setPreferredSize(new Dimension(80, 30));
/*  76 */       this.cancelButton.setName("CancelButton");
/*  77 */       this.cancelButton.setFont(new Font("Calibri", 0, 14));
/*  78 */       this.cancelButton.setPreferredSize(new Dimension(80, 30));
/*  79 */       contentPane.setLayout(layout);
/*  80 */       contentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));
/*  81 */       contentPane.setOpaque(true);
/*  82 */       this.dialog.setIconImage(img);
/*  83 */       this.dialog.setSize(new Dimension(570, 340));
/*  84 */       this.dialog.setLocationRelativeTo(this.frame);
/*  85 */       this.dialog.setModal(true);
/*     */       
/*     */ 
/*  88 */       this.textField.addKeyListener(new SearchActionListener(this.textField, null, this.dialog, this));
/*  89 */       this.regExCheckBox.addKeyListener(new SearchActionListener(this.regExCheckBox, null, this.dialog, this));
/*  90 */       this.onlyNameCheckBox.addKeyListener(new SearchActionListener(this.onlyNameCheckBox, null, this.dialog, this));
/*  91 */       this.searchButton.addActionListener(new SearchActionListener(this.searchButton, this.textField, this.dialog, this));
/*  92 */       this.searchButton.addKeyListener(new SearchActionListener(this.searchButton, this.textField, this.dialog, this));
/*  93 */       this.cancelButton.addActionListener(new SearchActionListener(this.cancelButton, null, this.dialog, this));
/*  94 */       this.cancelButton.addKeyListener(new SearchActionListener(this.cancelButton, null, this.dialog, this));
/*  95 */       this.dialog.addWindowListener(new java.awt.event.WindowAdapter() { public void windowClosing(WindowEvent e) { SearchWindow.this.windowCloseFlag = Integer.valueOf(1);
/*     */         }
/*     */ 
/*  98 */       });
/*  99 */       contentPane.add(comboLabel);
/* 100 */       contentPane.add(this.comboBox);
/* 101 */       contentPane.add(textBoxLabel);
/* 102 */       contentPane.add(this.textField);
/* 103 */       contentPane.add(imageLabel);
/* 104 */       contentPane.add(this.regExCheckBox);
/* 105 */       contentPane.add(this.onlyNameCheckBox);
/* 106 */       contentPane.add(this.searchButton);
/* 107 */       contentPane.add(this.cancelButton);
/* 108 */       this.dialog.setContentPane(contentPane);
/*     */       
/*     */ 
/* 111 */       layout.putConstraint("West", comboLabel, 150, "West", contentPane);
/* 112 */       layout.putConstraint("North", comboLabel, 10, "North", contentPane);
/* 113 */       layout.putConstraint("West", this.comboBox, 150, "West", contentPane);
/* 114 */       layout.putConstraint("North", this.comboBox, 30, "North", contentPane);
/* 115 */       layout.putConstraint("West", textBoxLabel, 150, "West", contentPane);
/* 116 */       layout.putConstraint("North", textBoxLabel, 65, "North", contentPane);
/* 117 */       layout.putConstraint("West", this.textField, 150, "West", contentPane);
/* 118 */       layout.putConstraint("North", this.textField, 85, "North", contentPane);
/* 119 */       layout.putConstraint("West", this.regExCheckBox, 150, "West", contentPane);
/* 120 */       layout.putConstraint("North", this.regExCheckBox, 130, "North", contentPane);
/* 121 */       layout.putConstraint("West", this.onlyNameCheckBox, 150, "West", contentPane);
/* 122 */       layout.putConstraint("North", this.onlyNameCheckBox, 155, "North", contentPane);
/* 123 */       layout.putConstraint("North", this.searchButton, 110, "North", this.textField);
/* 124 */       layout.putConstraint("West", this.searchButton, 160, "West", contentPane);
/* 125 */       layout.putConstraint("North", this.cancelButton, 110, "North", this.textField);
/* 126 */       layout.putConstraint("West", this.cancelButton, 270, "West", contentPane);
/*     */       
/* 128 */       this.dialog.setVisible(true);
/*     */ 
/*     */     }
/*     */     catch (IOException e1)
/*     */     {
/* 133 */       e1.printStackTrace();
/* 134 */       UISupport.showErrorMessage("Failed during Search Window Load. Check Error log file for details.");
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 138 */       e1.printStackTrace();
/* 139 */       UISupport.showErrorMessage("Failed during Search Window Load. Check Error log file for details.");
/*     */     }
/*     */   }
/*     */   
/*     */   public void ExecuteSearch()
/*     */   {
/*     */     try {
/* 146 */       String token = this.textField.getText();
/* 147 */       String tokenOrig = token;
/* 148 */       String selectedItem = this.comboBox.getSelectedItem().toString();
/* 149 */       Integer nameSearchFlag = Integer.valueOf(0);
/* 150 */       if (!this.regExCheckBox.isSelected())
/*     */       {
/* 152 */         token = com.au.fttest.helpers.StringUtility.ReplaceSpecialCharacters(token);
/*     */       }
/*     */       
/*     */ 
/* 156 */       if (this.onlyNameCheckBox.isSelected())
/*     */       {
/* 158 */         nameSearchFlag = Integer.valueOf(1);
/*     */       }
/*     */       
/*     */ 
/* 162 */       this.searchAction.windowCloseFlag = this.windowCloseFlag;
/* 163 */       this.searchAction.tokenOrig = tokenOrig;
/* 164 */       this.searchAction.token = token;
/* 165 */       this.searchAction.selectedItem = selectedItem;
/* 166 */       this.searchAction.nameSearchFlag = nameSearchFlag;
/*     */       
/* 168 */       this.searchAction.SearchAndDisplayResults(this.dialog);
/*     */     }
/*     */     catch (Exception e1) {
/* 171 */       e1.printStackTrace();
/*     */       
/*     */ 
/* 174 */       UISupport.showErrorMessage("Failed during Search Operation. Check Error log file for details.");
/* 175 */       this.dialog.setCursor(java.awt.Cursor.getPredefinedCursor(0));
/* 176 */       this.textField.requestFocusInWindow();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\plugins\SearchWindow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */