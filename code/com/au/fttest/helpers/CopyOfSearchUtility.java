/*      */ package com.au.fttest.helpers;
/*      */ 
/*      */ import com.eviware.soapui.config.HttpRequestConfig;
/*      */ import com.eviware.soapui.config.JMSPropertyConfig;
/*      */ import com.eviware.soapui.impl.rest.RestMethod;
/*      */ import com.eviware.soapui.impl.rest.RestRequest;
/*      */ import com.eviware.soapui.impl.rest.RestResource;
/*      */ import com.eviware.soapui.impl.rest.RestService;
/*      */ import com.eviware.soapui.impl.rest.mock.RestMockAction;
/*      */ import com.eviware.soapui.impl.rest.mock.RestMockResponse;
/*      */ import com.eviware.soapui.impl.rest.mock.RestMockService;
/*      */ import com.eviware.soapui.impl.wsdl.HttpAttachmentPart;
/*      */ import com.eviware.soapui.impl.wsdl.WsdlInterface;
/*      */ import com.eviware.soapui.impl.wsdl.WsdlOperation;
/*      */ import com.eviware.soapui.impl.wsdl.WsdlProject;
/*      */ import com.eviware.soapui.impl.wsdl.WsdlProjectPro;
/*      */ import com.eviware.soapui.impl.wsdl.WsdlRequest;
/*      */ import com.eviware.soapui.impl.wsdl.loadtest.WsdlLoadTest;
/*      */ import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;
/*      */ import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
/*      */ import com.eviware.soapui.impl.wsdl.support.jms.header.JMSHeaderConfig;
/*      */ import com.eviware.soapui.impl.wsdl.support.wsa.WsaConfig;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.AMFRequestTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.JdbcRequestTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.ManualTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.PropertyTransfersTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.WsdlDataSinkTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.WsdlDataSourceTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.WsdlGotoTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.WsdlMockResponseTestStep;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.assertions.ProXPathContainsAssertion;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathContentAssertion;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathExistenceAssertion;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.assertions.json.JsonPathRegExAssertion;
/*      */ import com.eviware.soapui.impl.wsdl.teststeps.assertions.support.AssertionEntry;
/*      */ import com.eviware.soapui.model.ModelItem;
/*      */ import com.eviware.soapui.model.TestPropertyHolder;
/*      */ import com.eviware.soapui.model.iface.MessagePart;
/*      */ import com.eviware.soapui.model.security.SecurityScan;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ 
/*      */ public class CopyOfSearchUtility
/*      */ {
/*   48 */   private List<ModelItem> searchResult = new java.util.ArrayList();
/*      */   
/*      */   private String token;
/*      */   
/*      */   private ModelItem parent;
/*      */   
/*      */   private Integer matchFlag;
/*      */   private Integer nameSearchFlag;
/*      */   private javax.swing.JDialog dialog;
/*      */   private boolean soapUIProFlag;
/*      */   
/*      */   public CopyOfSearchUtility(Integer nameSearchFlag)
/*      */   {
/*   61 */     this.nameSearchFlag = nameSearchFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<ModelItem> SearchItemInSoapUI(ModelItem parent, String token, javax.swing.JDialog dialog, Boolean soapUIProFlag)
/*      */     throws Exception
/*      */   {
/*   70 */     this.token = token.toLowerCase();
/*   71 */     this.parent = parent;
/*   72 */     this.dialog = dialog;
/*   73 */     this.soapUIProFlag = soapUIProFlag.booleanValue();
/*      */     
/*   75 */     SearchParentElement(parent);
/*   76 */     SearchChildElements(parent);
/*   77 */     return this.searchResult;
/*      */   }
/*      */   
/*      */   private void SearchParentElement(ModelItem parent)
/*      */     throws Exception
/*      */   {
/*   83 */     SearchItem(parent);
/*      */   }
/*      */   
/*      */   private void SearchChildElements(ModelItem parent)
/*      */     throws Exception
/*      */   {
/*   89 */     for (ModelItem child : parent.getChildren())
/*      */     {
/*   91 */       SearchItem(child);
/*   92 */       SearchChildElements(child);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void SearchParentAssertion(AssertionEntry parentAssertionEntry)
/*      */     throws Exception
/*      */   {
/*  100 */     CheckAssertion(parentAssertionEntry.getAssertion());
/*      */   }
/*      */   
/*      */ 
/*      */   private void SearchChildAssertions(AssertionEntry parentAssertionEntry)
/*      */     throws Exception
/*      */   {
/*  107 */     for (AssertionEntry childAssertionEntry : parentAssertionEntry.getChildList())
/*      */     {
/*  109 */       CheckAssertion(childAssertionEntry.getAssertion());
/*  110 */       SearchChildAssertions(childAssertionEntry);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void SearchItem(ModelItem modelItem)
/*      */     throws Exception
/*      */   {
/*  121 */     this.matchFlag = Integer.valueOf(0);
/*  122 */     this.token = this.token.toLowerCase();
/*  123 */     String modelItemName = modelItem.getName().toLowerCase();
/*  124 */     CompareStrings(modelItemName);
/*      */     
/*      */ 
/*  127 */     if (this.nameSearchFlag.intValue() == 0)
/*      */     {
/*  129 */       String modelItemDesc = modelItem.getDescription();
/*  130 */       CompareStrings(modelItemDesc);
/*      */       MessagePart mRespPart;
/*  132 */       Object mRespPart; if ((modelItem instanceof JdbcRequestTestStep))
/*      */       {
/*      */ 
/*  135 */         CompareStrings(((JdbcRequestTestStep)modelItem).getAssertableContent());
/*  136 */         CompareStrings(((JdbcRequestTestStep)modelItem).getConnectionString());
/*  137 */         CompareStrings(((JdbcRequestTestStep)modelItem).getDefaultAssertableContent());
/*  138 */         CompareStrings(((JdbcRequestTestStep)modelItem).getDefaultSourcePropertyName());
/*  139 */         CompareStrings(((JdbcRequestTestStep)modelItem).getDriver());
/*  140 */         CompareStrings(((JdbcRequestTestStep)modelItem).getFetchSize());
/*  141 */         CompareStrings(((JdbcRequestTestStep)modelItem).getQuery());
/*  142 */         CompareStrings(((JdbcRequestTestStep)modelItem).getResponseContent());
/*  143 */         CompareStrings(((JdbcRequestTestStep)modelItem).getLabel());
/*      */         
/*  145 */         CompareStrings(((JdbcRequestTestStep)modelItem).getDefaultTargetPropertyName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*  152 */       else if ((modelItem instanceof AMFRequestTestStep))
/*      */       {
/*      */ 
/*  155 */         CompareStrings(((AMFRequestTestStep)modelItem).getAssertableContent());
/*  156 */         CompareStrings(((AMFRequestTestStep)modelItem).getDefaultAssertableContent());
/*  157 */         CompareStrings(((AMFRequestTestStep)modelItem).getDefaultSourcePropertyName());
/*  158 */         CompareStrings(((AMFRequestTestStep)modelItem).getLabel());
/*      */         
/*  160 */         CompareStrings(((AMFRequestTestStep)modelItem).getDefaultTargetPropertyName());
/*  161 */         CompareStrings(((AMFRequestTestStep)modelItem).getScript());
/*  162 */         CompareStrings(((AMFRequestTestStep)modelItem).getAmfCall());
/*  163 */         CompareStrings(((AMFRequestTestStep)modelItem).getEndpoint());
/*      */         
/*  165 */         if (((AMFRequestTestStep)modelItem).getAmfHeaders() != null) {
/*  166 */           CompareStrings(((AMFRequestTestStep)modelItem).getAmfHeaders().toXml());
/*      */         }
/*  168 */         if (((AMFRequestTestStep)modelItem).getHttpHeaders() != null) {
/*  169 */           CompareStrings(((AMFRequestTestStep)modelItem).getHttpHeaders().toXml());
/*      */         }
/*      */         
/*      */       }
/*  173 */       else if ((modelItem instanceof RestTestRequestStep))
/*      */       {
/*      */ 
/*  176 */         CompareStrings(((RestTestRequestStep)modelItem).getAssertableContent());
/*  177 */         CompareStrings(((RestTestRequestStep)modelItem).getDefaultAssertableContent());
/*  178 */         CompareStrings(((RestTestRequestStep)modelItem).getDefaultSourcePropertyName());
/*  179 */         CompareStrings(((RestTestRequestStep)modelItem).getLabel());
/*  180 */         CompareStrings(((RestTestRequestStep)modelItem).getDefaultTargetPropertyName());
/*  181 */         CompareStrings(((RestTestRequestStep)modelItem).getResourcePath());
/*      */         
/*  183 */         if (((RestTestRequestStep)modelItem).getRequestStepConfig() != null) {
/*  184 */           CompareStrings(((RestTestRequestStep)modelItem).getRequestStepConfig().getResourcePath());
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*  189 */       else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep))
/*      */       {
/*      */ 
/*  192 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep)modelItem).getAssertableContent());
/*  193 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep)modelItem).getDefaultAssertableContent());
/*  194 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep)modelItem).getDefaultSourcePropertyName());
/*  195 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep)modelItem).getLabel());
/*  196 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStep)modelItem).getDefaultTargetPropertyName());
/*      */ 
/*      */ 
/*      */       }
/*  200 */       else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep))
/*      */       {
/*      */ 
/*  203 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep)modelItem).getSource());
/*  204 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep)modelItem).getTarget());
/*  205 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep)modelItem).getDefaultSourcePropertyName());
/*  206 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep)modelItem).getLabel());
/*  207 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlPropertiesTestStep)modelItem).getDefaultTargetPropertyName());
/*      */ 
/*      */ 
/*      */       }
/*  211 */       else if ((modelItem instanceof HttpTestRequestStep))
/*      */       {
/*      */ 
/*  214 */         CompareStrings(((HttpTestRequestStep)modelItem).getAssertableContent());
/*  215 */         CompareStrings(((HttpTestRequestStep)modelItem).getDefaultAssertableContent());
/*  216 */         CompareStrings(((HttpTestRequestStep)modelItem).getDefaultSourcePropertyName());
/*  217 */         CompareStrings(((HttpTestRequestStep)modelItem).getLabel());
/*  218 */         CompareStrings(((HttpTestRequestStep)modelItem).getDefaultTargetPropertyName());
/*      */         
/*  220 */         if ((((HttpTestRequestStep)modelItem).getRequestStepConfig() != null) && (((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig() != null))
/*      */         {
/*  222 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getClientID());
/*  223 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getDurableSubscriptionName());
/*  224 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getJMSCorrelationID());
/*      */           
/*  226 */           if (((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getJMSDeliveryMode() != null) {
/*  227 */             CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getJMSDeliveryMode().toString());
/*      */           }
/*  229 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getJMSPriority());
/*  230 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getJMSReplyTo());
/*  231 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getJMSType());
/*  232 */           CompareStrings(((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsConfig().getMessageSelector());
/*      */         }
/*      */         
/*  235 */         if ((((HttpTestRequestStep)modelItem).getRequestStepConfig() != null) && (((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsPropertyConfig() != null))
/*      */         {
/*      */ 
/*  238 */           List<JMSPropertyConfig> jmsPropertyList = ((HttpTestRequestStep)modelItem).getRequestStepConfig().getJmsPropertyConfig().getJmsPropertiesList();
/*      */           
/*  240 */           for (JMSPropertyConfig jmsProp : jmsPropertyList)
/*      */           {
/*  242 */             CompareStrings(jmsProp.getName());
/*  243 */             CompareStrings(jmsProp.getValue());
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*  249 */       else if ((modelItem instanceof PropertyTransfersTestStep))
/*      */       {
/*      */ 
/*  252 */         Integer transferNum = Integer.valueOf(0);
/*  253 */         Integer transferCount = Integer.valueOf(((PropertyTransfersTestStep)modelItem).getTransferCount());
/*      */         
/*  255 */         CompareStrings(((PropertyTransfersTestStep)modelItem).getDefaultSourcePropertyName());
/*  256 */         CompareStrings(((PropertyTransfersTestStep)modelItem).getLabel());
/*      */         
/*  258 */         CompareStrings(((PropertyTransfersTestStep)modelItem).getDefaultTargetPropertyName());
/*      */         
/*  260 */         while (transferNum.intValue() < transferCount.intValue())
/*      */         {
/*  262 */           String transferName = ((PropertyTransfersTestStep)modelItem).getTransferAt(transferNum.intValue()).getName();
/*  263 */           String sourcePath = ((PropertyTransfersTestStep)modelItem).getTransferAt(transferNum.intValue()).getSourcePath();
/*  264 */           String targetPath = ((PropertyTransfersTestStep)modelItem).getTransferAt(transferNum.intValue()).getTargetPath();
/*  265 */           CompareStrings(transferName);
/*  266 */           CompareStrings(sourcePath);
/*  267 */           CompareStrings(targetPath);
/*      */           
/*  269 */           if (((PropertyTransfersTestStep)modelItem).getXPathReferences() != null) {
/*  270 */             CompareStrings(((PropertyTransfersTestStep)modelItem).getXPathReferences().toString());
/*      */           }
/*  272 */           transferNum = Integer.valueOf(transferNum.intValue() + 1);
/*      */         }
/*      */       }
/*      */       else {
/*      */         Object cExpression;
/*      */         String cStep;
/*  278 */         if ((modelItem instanceof WsdlGotoTestStep))
/*      */         {
/*  280 */           Integer conditionNum = Integer.valueOf(0);
/*  281 */           Integer conditionCount = Integer.valueOf(((WsdlGotoTestStep)modelItem).getConditionCount());
/*      */           
/*  283 */           CompareStrings(((WsdlGotoTestStep)modelItem).getDefaultSourcePropertyName());
/*  284 */           CompareStrings(((WsdlGotoTestStep)modelItem).getLabel());
/*      */           
/*  286 */           CompareStrings(((WsdlGotoTestStep)modelItem).getDefaultTargetPropertyName());
/*      */           
/*  288 */           if (((WsdlGotoTestStep)modelItem).getXPathReferences() != null) {
/*  289 */             CompareStrings(((WsdlGotoTestStep)modelItem).getXPathReferences().toString());
/*      */           }
/*  291 */           while (conditionNum.intValue() < conditionCount.intValue())
/*      */           {
/*  293 */             cExpression = ((WsdlGotoTestStep)modelItem).getConditionAt(conditionNum.intValue()).getExpression();
/*  294 */             String cName = ((WsdlGotoTestStep)modelItem).getConditionAt(conditionNum.intValue()).getName();
/*  295 */             cStep = ((WsdlGotoTestStep)modelItem).getConditionAt(conditionNum.intValue()).getTargetStep();
/*  296 */             String cType = ((WsdlGotoTestStep)modelItem).getConditionAt(conditionNum.intValue()).getType();
/*      */             
/*  298 */             CompareStrings((String)cExpression);
/*  299 */             CompareStrings(cName);
/*  300 */             CompareStrings(cStep);
/*  301 */             CompareStrings(cType);
/*      */             
/*  303 */             conditionNum = Integer.valueOf(conditionNum.intValue() + 1);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  308 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep))
/*      */         {
/*      */ 
/*      */ 
/*  312 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep)modelItem).getDefaultSourcePropertyName());
/*  313 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep)modelItem).getLabel());
/*  314 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep)modelItem).getDefaultTargetPropertyName());
/*  315 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlDelayTestStep)modelItem).getDelayString());
/*      */ 
/*      */ 
/*      */         }
/*  319 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep))
/*      */         {
/*      */ 
/*      */ 
/*  323 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep)modelItem).getDefaultSourcePropertyName());
/*  324 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep)modelItem).getLabel());
/*  325 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep)modelItem).getDefaultTargetPropertyName());
/*  326 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlGroovyScriptTestStep)modelItem).getScript());
/*      */ 
/*      */ 
/*      */         }
/*  330 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep))
/*      */         {
/*      */ 
/*      */ 
/*  334 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep)modelItem).getDefaultSourcePropertyName());
/*  335 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep)modelItem).getLabel());
/*  336 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep)modelItem).getDefaultTargetPropertyName());
/*      */           
/*  338 */           if (((com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep)modelItem).getReturnProperties() != null) {
/*  339 */             CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlRunTestCaseTestStep)modelItem).getReturnProperties().toString());
/*      */           }
/*      */           
/*      */         }
/*  343 */         else if ((modelItem instanceof ManualTestStep))
/*      */         {
/*      */ 
/*  346 */           CompareStrings(((ManualTestStep)modelItem).getExpectedResult());
/*  347 */           CompareStrings(((ManualTestStep)modelItem).getDefaultSourcePropertyName());
/*  348 */           CompareStrings(((ManualTestStep)modelItem).getDefaultTargetPropertyName());
/*  349 */           CompareStrings(((ManualTestStep)modelItem).getLabel());
/*      */           
/*  351 */           if (((ManualTestStep)modelItem).getManualTestStepConfig() != null) {
/*  352 */             CompareStrings(((ManualTestStep)modelItem).getManualTestStepConfig().toString());
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */         }
/*  359 */         else if ((modelItem instanceof WsdlLoadTest))
/*      */         {
/*  361 */           CompareStrings(((WsdlLoadTest)modelItem).getSetupScript());
/*  362 */           CompareStrings(((WsdlLoadTest)modelItem).getTearDownScript());
/*  363 */           CompareStrings(((WsdlLoadTest)modelItem).getStatisticsLogFolder());
/*      */           
/*  365 */           if ((((WsdlLoadTest)modelItem).getLoadStrategy() != null) && (((WsdlLoadTest)modelItem).getLoadStrategy().getConfig() != null))
/*  366 */             CompareStrings(((WsdlLoadTest)modelItem).getLoadStrategy().getConfig().toString());
/*      */         } else { SecurityScan secScan;
/*      */           Object localObject1;
/*      */           com.eviware.soapui.model.testsuite.TestAssertion assertion;
/*  370 */           if ((modelItem instanceof com.eviware.soapui.security.SecurityTest))
/*      */           {
/*  372 */             CompareStrings(((com.eviware.soapui.security.SecurityTest)modelItem).getStartupScript());
/*  373 */             CompareStrings(((com.eviware.soapui.security.SecurityTest)modelItem).getTearDownScript());
/*      */             
/*  375 */             java.util.HashMap<String, List<SecurityScan>> securityScanHMap = ((com.eviware.soapui.security.SecurityTest)modelItem).getSecurityScansMap();
/*      */             
/*  377 */             for (cExpression = securityScanHMap.entrySet().iterator(); ((Iterator)cExpression).hasNext(); 
/*      */                 
/*      */ 
/*  380 */                 cStep.hasNext())
/*      */             {
/*  377 */               java.util.Map.Entry<String, List<SecurityScan>> securityScanMap = (java.util.Map.Entry)((Iterator)cExpression).next();
/*      */               
/*  379 */               CompareStrings((String)securityScanMap.getKey());
/*  380 */               cStep = ((List)securityScanMap.getValue()).iterator(); continue;secScan = (SecurityScan)cStep.next();
/*      */               
/*  382 */               CompareStrings(secScan.getName());
/*  383 */               CompareStrings(secScan.getDescription());
/*  384 */               CompareStrings(secScan.getConfig().toString());
/*  385 */               CompareStrings(secScan.getConfigDescription());
/*  386 */               CompareStrings(secScan.getConfigName());
/*  387 */               CompareStrings(secScan.getHelpURL());
/*  388 */               CompareStrings(secScan.getType());
/*  389 */               if (secScan.getSecurityScanResult() != null)
/*      */               {
/*  391 */                 CompareStrings(secScan.getSecurityScanResult().getLogIconStatusString());
/*  392 */                 CompareStrings(secScan.getSecurityScanResult().getResultType());
/*  393 */                 CompareStrings(secScan.getSecurityScanResult().getSecurityScanName());
/*  394 */                 CompareStrings(secScan.getSecurityScanResult().getSecurityTestLog());
/*  395 */                 CompareStrings(secScan.getSecurityScanResult().getStatusString());
/*      */                 
/*  397 */                 for (localObject1 = secScan.getSecurityScanResult().getSecurityRequestResultList().iterator(); ((Iterator)localObject1).hasNext();) { com.eviware.soapui.security.result.SecurityScanRequestResult secScanRequestResult = (com.eviware.soapui.security.result.SecurityScanRequestResult)((Iterator)localObject1).next();
/*      */                   
/*  399 */                   CompareStrings(secScanRequestResult.getMessages().toString());
/*      */                 }
/*      */               }
/*      */               
/*  403 */               for (localObject1 = secScan.getAssertionList().iterator(); ((Iterator)localObject1).hasNext();) { assertion = (com.eviware.soapui.model.testsuite.TestAssertion)((Iterator)localObject1).next();
/*      */                 
/*  405 */                 CheckAssertion(assertion);
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*  414 */           else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase))
/*      */           {
/*  416 */             CompareStrings(((com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase)modelItem).getSetupScript());
/*  417 */             CompareStrings(((com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase)modelItem).getTearDownScript());
/*      */ 
/*      */ 
/*      */           }
/*  421 */           else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.WsdlTestSuite))
/*      */           {
/*  423 */             CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlTestSuite)modelItem).getSetupScript());
/*  424 */             CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlTestSuite)modelItem).getTearDownScript());
/*      */ 
/*      */ 
/*      */           }
/*  428 */           else if ((modelItem instanceof WsdlProject))
/*      */           {
/*      */ 
/*      */ 
/*  432 */             CompareStrings(((WsdlProject)modelItem).getAfterLoadScript());
/*  433 */             CompareStrings(((WsdlProject)modelItem).getBeforeSaveScript());
/*  434 */             CompareStrings(((WsdlProject)modelItem).getAfterRunScript());
/*  435 */             CompareStrings(((WsdlProject)modelItem).getBeforeRunScript());
/*  436 */             CompareStrings(((WsdlProject)modelItem).getHermesConfig());
/*  437 */             CompareStrings(((WsdlProject)modelItem).getPath());
/*      */ 
/*      */ 
/*      */           }
/*  441 */           else if ((modelItem instanceof WsdlMockService))
/*      */           {
/*  443 */             CompareStrings(((WsdlMockService)modelItem).getHelpUrl());
/*  444 */             CompareStrings(((WsdlMockService)modelItem).getHost());
/*  445 */             CompareStrings(((WsdlMockService)modelItem).getIconName());
/*  446 */             CompareStrings(((WsdlMockService)modelItem).getIncomingWss());
/*  447 */             CompareStrings(((WsdlMockService)modelItem).getLocalMockServiceEndpoint());
/*  448 */             CompareStrings(((WsdlMockService)modelItem).getMockServiceEndpoint());
/*  449 */             CompareStrings(((WsdlMockService)modelItem).getOutgoingWss());
/*  450 */             CompareStrings(((WsdlMockService)modelItem).getAfterRequestScript());
/*  451 */             CompareStrings(((WsdlMockService)modelItem).getDocroot());
/*  452 */             CompareStrings(((WsdlMockService)modelItem).getLocalEndpoint());
/*  453 */             CompareStrings(((WsdlMockService)modelItem).getOnRequestScript());
/*  454 */             CompareStrings(((WsdlMockService)modelItem).getPath());
/*  455 */             CompareStrings(((WsdlMockService)modelItem).getMockServiceEndpoint());
/*  456 */             CompareStrings(((WsdlMockService)modelItem).getStartScript());
/*  457 */             CompareStrings(((WsdlMockService)modelItem).getStopScript());
/*      */ 
/*      */           }
/*  460 */           else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation))
/*      */           {
/*  462 */             CompareStrings(((com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation)modelItem).getDefaultResponse());
/*  463 */             CompareStrings(((com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation)modelItem).getScript());
/*      */ 
/*      */           }
/*  466 */           else if ((modelItem instanceof com.eviware.soapui.model.mock.MockResponse))
/*      */           {
/*  468 */             CompareStrings(((com.eviware.soapui.model.mock.MockResponse)modelItem).getResponseContent());
/*      */             
/*  470 */             if (((com.eviware.soapui.model.mock.MockResponse)modelItem).getResponseHeaders() != null)
/*  471 */               CompareStrings(((com.eviware.soapui.model.mock.MockResponse)modelItem).getResponseHeaders().toXml());
/*  472 */             CompareStrings(((com.eviware.soapui.model.mock.MockResponse)modelItem).getScript());
/*  473 */             CompareStrings(((com.eviware.soapui.model.mock.MockResponse)modelItem).getScriptHelpUrl());
/*      */ 
/*      */           }
/*  476 */           else if ((modelItem instanceof WsdlMockResponseTestStep))
/*      */           {
/*      */ 
/*  479 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getAssertableContent());
/*  480 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getDefaultAssertableContent());
/*  481 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getDefaultSourcePropertyName());
/*  482 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getDefaultTargetPropertyName());
/*  483 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getHost());
/*  484 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getLabel());
/*  485 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getMatch());
/*  486 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getOutgoingWss());
/*  487 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getPath());
/*  488 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getQuery());
/*  489 */             CompareStrings(((WsdlMockResponseTestStep)modelItem).getStartStep());
/*      */             
/*      */ 
/*  492 */             if (((WsdlMockResponseTestStep)modelItem).getMockResponse() != null)
/*      */             {
/*  494 */               CompareStrings(((WsdlMockResponseTestStep)modelItem).getMockResponse().getScript());
/*  495 */               CompareStrings(((WsdlMockResponseTestStep)modelItem).getMockResponse().getResponseContent());
/*      */               
/*  497 */               if (((WsdlMockResponseTestStep)modelItem).getMockResponse().getResponseHeaders() != null) {
/*  498 */                 CompareStrings(((WsdlMockResponseTestStep)modelItem).getMockResponse().getResponseHeaders().toXml());
/*      */               }
/*  500 */               CompareStrings(((WsdlMockResponseTestStep)modelItem).getMockResponse().getScriptHelpUrl());
/*      */             }
/*      */           } else {
/*      */             MessagePart[] mRespPartList;
/*      */             MessagePart mRespPart;
/*  505 */             if ((modelItem instanceof WsdlMockResponse))
/*      */             {
/*      */ 
/*  508 */               CompareStrings(((WsdlMockResponse)modelItem).getContentType());
/*  509 */               CompareStrings(((WsdlMockResponse)modelItem).getOutgoingWss());
/*  510 */               CompareStrings(((WsdlMockResponse)modelItem).getScriptHelpUrl());
/*  511 */               CompareStrings(((WsdlMockResponse)modelItem).getScript());
/*      */               
/*  513 */               HttpAttachmentPart[] attachPartList = ((WsdlMockResponse)modelItem).getDefinedAttachmentParts();
/*      */               
/*  515 */               secScan = (cStep = attachPartList).length; for (SecurityScan localSecurityScan1 = 0; localSecurityScan1 < secScan; localSecurityScan1++) { HttpAttachmentPart attachPart = cStep[localSecurityScan1];
/*      */                 
/*  517 */                 CompareStrings(attachPart.getName());
/*  518 */                 CompareStrings(attachPart.getDescription());
/*      */                 
/*  520 */                 if (attachPart.getContentTypes() != null) {
/*  521 */                   CompareStrings(attachPart.getContentTypes().toString());
/*      */                 }
/*      */               }
/*  524 */               MessagePart[] mReqPartList = ((WsdlMockResponse)modelItem).getRequestParts();
/*      */               
/*  526 */               cStep = (assertion = mReqPartList).length; for (secScan = 0; secScan < cStep; secScan++) { MessagePart mReqPart = assertion[secScan];
/*      */                 
/*  528 */                 CompareStrings(mReqPart.getName());
/*  529 */                 CompareStrings(mReqPart.getDescription());
/*      */               }
/*      */               
/*  532 */               mRespPartList = ((WsdlMockResponse)modelItem).getResponseParts();
/*      */               
/*  534 */               assertion = (localObject1 = mRespPartList).length; for (cStep = 0; cStep < assertion; cStep++) { mRespPart = localObject1[cStep];
/*      */                 
/*  536 */                 CompareStrings(mRespPart.getName());
/*  537 */                 CompareStrings(mRespPart.getDescription());
/*      */ 
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */             }
/*  544 */             else if ((modelItem instanceof RestMockService))
/*      */             {
/*      */ 
/*  547 */               CompareStrings(((RestMockService)modelItem).getHelpUrl());
/*  548 */               CompareStrings(((RestMockService)modelItem).getHost());
/*  549 */               CompareStrings(((RestMockService)modelItem).getIconName());
/*  550 */               CompareStrings(((RestMockService)modelItem).getAfterRequestScript());
/*  551 */               CompareStrings(((RestMockService)modelItem).getDocroot());
/*  552 */               CompareStrings(((RestMockService)modelItem).getLocalEndpoint());
/*  553 */               CompareStrings(((RestMockService)modelItem).getOnRequestScript());
/*  554 */               CompareStrings(((RestMockService)modelItem).getPath());
/*  555 */               CompareStrings(((RestMockService)modelItem).getStartScript());
/*  556 */               CompareStrings(((RestMockService)modelItem).getStopScript());
/*      */ 
/*      */ 
/*      */             }
/*  560 */             else if ((modelItem instanceof RestMockAction))
/*      */             {
/*  562 */               CompareStrings(((RestMockAction)modelItem).getDefaultResponse());
/*  563 */               CompareStrings(((RestMockAction)modelItem).getScript());
/*  564 */               CompareStrings(((RestMockAction)modelItem).getResourcePath());
/*  565 */               CompareStrings(((RestMockAction)modelItem).getHelpUrl());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*  571 */             else if ((modelItem instanceof RestMockResponse))
/*      */             {
/*      */ 
/*  574 */               CompareStrings(((RestMockResponse)modelItem).getResponseContent());
/*      */               
/*  576 */               if (((RestMockResponse)modelItem).getResponseHeaders() != null) {
/*  577 */                 CompareStrings(((RestMockResponse)modelItem).getResponseHeaders().toXml());
/*      */               }
/*  579 */               CompareStrings(((RestMockResponse)modelItem).getScript());
/*  580 */               CompareStrings(((RestMockResponse)modelItem).getScriptHelpUrl());
/*  581 */               CompareStrings(((RestMockResponse)modelItem).getResponseCompression());
/*      */ 
/*      */ 
/*      */             }
/*  585 */             else if ((modelItem instanceof RestService))
/*      */             {
/*      */ 
/*  588 */               CompareStrings(((RestService)modelItem).getBasePath());
/*  589 */               CompareStrings(((RestService)modelItem).getInferredSchema());
/*  590 */               CompareStrings(((RestService)modelItem).getInterfaceType());
/*  591 */               CompareStrings(((RestService)modelItem).getTechnicalId());
/*  592 */               CompareStrings(((RestService)modelItem).getType());
/*  593 */               CompareStrings(((RestService)modelItem).getWadlUrl());
/*  594 */               CompareStrings(((RestService)modelItem).getWadlVersion());
/*      */ 
/*      */ 
/*      */             }
/*  598 */             else if ((modelItem instanceof WsdlInterface))
/*      */             {
/*      */ 
/*  601 */               if (((WsdlInterface)modelItem).getBindingName() != null)
/*  602 */                 CompareStrings(((WsdlInterface)modelItem).getBindingName().getLocalPart());
/*  603 */               CompareStrings(((WsdlInterface)modelItem).getBindingName().getNamespaceURI());
/*  604 */               CompareStrings(((WsdlInterface)modelItem).getBindingName().getPrefix());
/*  605 */               CompareStrings(((WsdlInterface)modelItem).getInterfaceType());
/*  606 */               CompareStrings(((WsdlInterface)modelItem).getTechnicalId());
/*  607 */               CompareStrings(((WsdlInterface)modelItem).getType());
/*  608 */               CompareStrings(((WsdlInterface)modelItem).getWsaVersion());
/*      */             }
/*      */             else {
/*      */               Object mRespPartList;
/*      */               MessagePart mRespPart;
/*  613 */               if ((modelItem instanceof RestResource))
/*      */               {
/*      */ 
/*  616 */                 CompareStrings(((RestResource)modelItem).getFullPath());
/*  617 */                 CompareStrings(((RestResource)modelItem).getPath());
/*      */                 
/*  619 */                 if (((RestResource)modelItem).getRequestMediaTypes() != null) {
/*  620 */                   CompareStrings(((RestResource)modelItem).getRequestMediaTypes().toString());
/*      */                 }
/*  622 */                 if (((RestResource)modelItem).getResponseMediaTypes() != null) {
/*  623 */                   CompareStrings(((RestResource)modelItem).getResponseMediaTypes().toString());
/*      */                 }
/*  625 */                 com.eviware.soapui.impl.rest.support.RestParamProperty[] restParamList = ((RestResource)modelItem).getDefaultParams();
/*      */                 
/*  627 */                 mRespPart = (cStep = restParamList).length; for (MessagePart localMessagePart1 = 0; localMessagePart1 < mRespPart; localMessagePart1++) { com.eviware.soapui.impl.rest.support.RestParamProperty restParam = cStep[localMessagePart1];
/*      */                   
/*  629 */                   CompareStrings(restParam.getName());
/*  630 */                   CompareStrings(restParam.getDescription());
/*  631 */                   CompareStrings(restParam.getValue());
/*      */                   
/*  633 */                   if (restParam.getOptions() != null)
/*  634 */                     CompareStrings(restParam.getOptions().toString());
/*  635 */                   CompareStrings(restParam.getPath());
/*      */                 }
/*      */                 
/*  638 */                 if ((((RestResource)modelItem).getParams() != null) && (((RestResource)modelItem).getParams().getPropertyNames() != null))
/*      */                 {
/*  640 */                   mRespPart = (cStep = ((RestResource)modelItem).getParams().getPropertyNames()).length; for (MessagePart localMessagePart2 = 0; localMessagePart2 < mRespPart; localMessagePart2++) { String propName = cStep[localMessagePart2];
/*      */                     
/*  642 */                     CompareStrings(propName);
/*  643 */                     CompareStrings(((RestResource)modelItem).getParams().getPropertyValue(propName));
/*      */                   }
/*      */                 }
/*      */                 
/*  647 */                 MessagePart[] mReqPartList = ((RestResource)modelItem).getDefaultRequestParts();
/*      */                 
/*  649 */                 cStep = (assertion = mReqPartList).length; for (mRespPart = 0; mRespPart < cStep; mRespPart++) { MessagePart mReqPart = assertion[mRespPart];
/*      */                   
/*  651 */                   CompareStrings(mReqPart.getName());
/*  652 */                   CompareStrings(mReqPart.getDescription());
/*      */                 }
/*      */                 
/*  655 */                 mRespPartList = ((RestResource)modelItem).getDefaultResponseParts();
/*      */                 
/*  657 */                 assertion = (localObject1 = mRespPartList).length; for (cStep = 0; cStep < assertion; cStep++) { mRespPart = localObject1[cStep];
/*      */                   
/*  659 */                   CompareStrings(mRespPart.getName());
/*  660 */                   CompareStrings(mRespPart.getDescription());
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/*      */                 Object mReqPartList;
/*      */                 MessagePart[] mRespPartList;
/*      */                 Object localObject2;
/*  668 */                 if ((modelItem instanceof RestRequest))
/*      */                 {
/*      */ 
/*  671 */                   CompareStrings(((RestRequest)modelItem).getPath());
/*      */                   
/*  673 */                   if (((RestRequest)modelItem).getJMSHeaderConfig() != null)
/*      */                   {
/*  675 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getClientID());
/*  676 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getDurableSubscriptionName());
/*  677 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getJMSCorrelationID());
/*  678 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getJMSDeliveryMode());
/*  679 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getJMSPriority());
/*  680 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getJMSReplyTo());
/*  681 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getJMSType());
/*  682 */                     CompareStrings(((RestRequest)modelItem).getJMSHeaderConfig().getMessageSelector());
/*      */                   }
/*  684 */                   CompareStrings(((RestRequest)modelItem).getMediaType());
/*      */                   
/*  686 */                   if (((RestRequest)modelItem).getResponseMediaTypes() != null) {
/*  687 */                     CompareStrings(((RestRequest)modelItem).getResponseMediaTypes().toString());
/*      */                   }
/*  689 */                   CompareStrings(((RestRequest)modelItem).getAuthType());
/*  690 */                   CompareStrings(((RestRequest)modelItem).getUsername());
/*      */                   
/*  692 */                   if ((((RestRequest)modelItem).getJMSPropertiesConfig() != null) && (((RestRequest)modelItem).getJMSPropertiesConfig().getJMSProperties() != null))
/*      */                   {
/*  694 */                     List<JMSPropertyConfig> jmsPropertyList = ((RestRequest)modelItem).getJMSPropertiesConfig().getJMSProperties();
/*      */                     
/*  696 */                     for (mRespPartList = jmsPropertyList.iterator(); ((Iterator)mRespPartList).hasNext();) { JMSPropertyConfig jmsProp = (JMSPropertyConfig)((Iterator)mRespPartList).next();
/*      */                       
/*  698 */                       CompareStrings(jmsProp.getName());
/*  699 */                       CompareStrings(jmsProp.getValue());
/*      */                     }
/*      */                   }
/*      */                   
/*  703 */                   HttpAttachmentPart[] attachPartList = ((RestRequest)modelItem).getDefinedAttachmentParts();
/*      */                   
/*  705 */                   mRespPart = (cStep = attachPartList).length; for (MessagePart localMessagePart3 = 0; localMessagePart3 < mRespPart; localMessagePart3++) { HttpAttachmentPart attachPart = cStep[localMessagePart3];
/*      */                     
/*  707 */                     CompareStrings(attachPart.getName());
/*  708 */                     CompareStrings(attachPart.getDescription());
/*      */                     
/*  710 */                     if (attachPart.getContentTypes() != null) {
/*  711 */                       CompareStrings(attachPart.getContentTypes().toString());
/*      */                     }
/*      */                   }
/*  714 */                   if ((((RestRequest)modelItem).getParams() != null) && (((RestRequest)modelItem).getParams().getPropertyNames() != null))
/*      */                   {
/*  716 */                     mRespPart = (cStep = ((RestRequest)modelItem).getParams().getPropertyNames()).length; for (MessagePart localMessagePart4 = 0; localMessagePart4 < mRespPart; localMessagePart4++) { String propName = cStep[localMessagePart4];
/*      */                       
/*  718 */                       CompareStrings(propName);
/*  719 */                       CompareStrings(((RestRequest)modelItem).getParams().getPropertyValue(propName));
/*      */                     }
/*      */                   }
/*      */                   
/*  723 */                   com.eviware.soapui.impl.rest.RestRepresentation[] repList = ((RestRequest)modelItem).getRepresentations();
/*      */                   
/*  725 */                   cStep = (assertion = repList).length; for (mRespPart = 0; mRespPart < cStep; mRespPart++) { com.eviware.soapui.impl.rest.RestRepresentation restRep = assertion[mRespPart];
/*      */                     
/*  727 */                     CompareStrings(restRep.getDefaultContent());
/*  728 */                     CompareStrings(restRep.getDescription());
/*  729 */                     CompareStrings(restRep.getMediaType());
/*      */                   }
/*      */                   
/*  732 */                   mReqPartList = ((RestRequest)modelItem).getRequestParts();
/*      */                   
/*  734 */                   assertion = (localObject1 = mReqPartList).length; for (cStep = 0; cStep < assertion; cStep++) { MessagePart mReqPart = localObject1[cStep];
/*      */                     
/*  736 */                     CompareStrings(mReqPart.getName());
/*  737 */                     CompareStrings(mReqPart.getDescription());
/*      */                   }
/*      */                   
/*  740 */                   mRespPartList = ((RestRequest)modelItem).getResponseParts();
/*      */                   MessagePart[] arrayOfMessagePart2;
/*  742 */                   localObject2 = (arrayOfMessagePart2 = mRespPartList).length; for (assertion = 0; assertion < localObject2; assertion++) { mRespPart = arrayOfMessagePart2[assertion];
/*      */                     
/*  744 */                     CompareStrings(mRespPart.getName());
/*  745 */                     CompareStrings(mRespPart.getDescription());
/*      */                   }
/*      */                 }
/*      */                 else {
/*      */                   MessagePart[] mRespPartList;
/*      */                   MessagePart mRespPart;
/*  751 */                   if ((modelItem instanceof WsdlRequest))
/*      */                   {
/*      */ 
/*  754 */                     CompareStrings(((WsdlRequest)modelItem).getIncomingWss());
/*  755 */                     CompareStrings(((WsdlRequest)modelItem).getOutgoingWss());
/*      */                     
/*  757 */                     if (((WsdlRequest)modelItem).getJMSHeaderConfig() != null)
/*      */                     {
/*  759 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getClientID());
/*  760 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getDurableSubscriptionName());
/*  761 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getJMSCorrelationID());
/*  762 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getJMSDeliveryMode());
/*  763 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getJMSPriority());
/*  764 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getJMSReplyTo());
/*  765 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getJMSType());
/*  766 */                       CompareStrings(((WsdlRequest)modelItem).getJMSHeaderConfig().getMessageSelector());
/*      */                     }
/*      */                     
/*  769 */                     if (((WsdlRequest)modelItem).getWsaConfig() != null)
/*      */                     {
/*  771 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getAction());
/*  772 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getFaultTo());
/*  773 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getFaultToRefParams());
/*  774 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getFrom());
/*  775 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getMessageID());
/*  776 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getRelatesTo());
/*  777 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getRelationshipType());
/*  778 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getReplyTo());
/*  779 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getReplyToRefParams());
/*  780 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getTo());
/*  781 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getAction());
/*  782 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getAction());
/*  783 */                       CompareStrings(((WsdlRequest)modelItem).getWsaConfig().getAction());
/*      */                     }
/*      */                     
/*  786 */                     if (((WsdlRequest)modelItem).getWsrmConfig() != null)
/*      */                     {
/*  788 */                       CompareStrings(((WsdlRequest)modelItem).getWsrmConfig().getOfferEndpoint());
/*  789 */                       CompareStrings(((WsdlRequest)modelItem).getWsrmConfig().getUuid());
/*  790 */                       CompareStrings(((WsdlRequest)modelItem).getWsrmConfig().getVersionNameSpace());
/*      */                     }
/*      */                     
/*  793 */                     CompareStrings(((WsdlRequest)modelItem).getAuthType());
/*  794 */                     CompareStrings(((WsdlRequest)modelItem).getUsername());
/*      */                     
/*      */ 
/*  797 */                     if ((((WsdlRequest)modelItem).getJMSPropertiesConfig() != null) && (((WsdlRequest)modelItem).getJMSPropertiesConfig().getJMSProperties() != null))
/*      */                     {
/*  799 */                       List<JMSPropertyConfig> jmsPropertyList = ((WsdlRequest)modelItem).getJMSPropertiesConfig().getJMSProperties();
/*      */                       
/*  801 */                       for (mReqPartList = jmsPropertyList.iterator(); ((Iterator)mReqPartList).hasNext();) { JMSPropertyConfig jmsProp = (JMSPropertyConfig)((Iterator)mReqPartList).next();
/*      */                         
/*  803 */                         CompareStrings(jmsProp.getName());
/*  804 */                         CompareStrings(jmsProp.getValue());
/*      */                       }
/*      */                     }
/*      */                     
/*  808 */                     HttpAttachmentPart[] attachPartList = ((WsdlRequest)modelItem).getDefinedAttachmentParts();
/*      */                     
/*  810 */                     mRespPartList = (mRespPart = attachPartList).length; for (MessagePart[] arrayOfMessagePart1 = 0; arrayOfMessagePart1 < mRespPartList; arrayOfMessagePart1++) { HttpAttachmentPart attachPart = mRespPart[arrayOfMessagePart1];
/*      */                       
/*  812 */                       CompareStrings(attachPart.getName());
/*  813 */                       CompareStrings(attachPart.getDescription());
/*      */                       
/*  815 */                       if (attachPart.getContentTypes() != null) {
/*  816 */                         CompareStrings(attachPart.getContentTypes().toString());
/*      */                       }
/*      */                     }
/*      */                     
/*  820 */                     MessagePart[] mReqPartList = ((WsdlRequest)modelItem).getRequestParts();
/*      */                     
/*  822 */                     mRespPart = (assertion = mReqPartList).length; for (mRespPartList = 0; mRespPartList < mRespPart; mRespPartList++) { MessagePart mReqPart = assertion[mRespPartList];
/*      */                       
/*  824 */                       CompareStrings(mReqPart.getName());
/*  825 */                       CompareStrings(mReqPart.getDescription());
/*      */                     }
/*      */                     
/*  828 */                     mRespPartList = ((WsdlRequest)modelItem).getResponseParts();
/*      */                     
/*  830 */                     assertion = (localObject2 = mRespPartList).length; for (mRespPart = 0; mRespPart < assertion; mRespPart++) { mRespPart = localObject2[mRespPart];
/*      */                       
/*  832 */                       CompareStrings(mRespPart.getName());
/*  833 */                       CompareStrings(mRespPart.getDescription());
/*      */ 
/*      */                     }
/*      */                     
/*      */ 
/*      */                   }
/*  839 */                   else if ((modelItem instanceof RestMethod))
/*      */                   {
/*      */ 
/*  842 */                     CompareStrings(((RestMethod)modelItem).getDefaultRequestMediaType());
/*      */                     
/*  844 */                     if (((RestMethod)modelItem).getResponseMediaTypes() != null) {
/*  845 */                       CompareStrings(((RestMethod)modelItem).getResponseMediaTypes().toString());
/*      */                     }
/*  847 */                     com.eviware.soapui.impl.rest.RestRepresentation[] repList = ((RestMethod)modelItem).getRepresentations();
/*      */                     
/*  849 */                     mRespPart = (mRespPart = repList).length; for (MessagePart localMessagePart5 = 0; localMessagePart5 < mRespPart; localMessagePart5++) { com.eviware.soapui.impl.rest.RestRepresentation restRep = mRespPart[localMessagePart5];
/*      */                       
/*  851 */                       CompareStrings(restRep.getDefaultContent());
/*  852 */                       CompareStrings(restRep.getDescription());
/*  853 */                       CompareStrings(restRep.getMediaType());
/*      */                     }
/*      */                     
/*  856 */                     if ((((RestMethod)modelItem).getParams() != null) && (((RestMethod)modelItem).getParams().getPropertyNames() != null))
/*      */                     {
/*  858 */                       mRespPart = (mRespPart = ((RestMethod)modelItem).getParams().getPropertyNames()).length; for (MessagePart localMessagePart6 = 0; localMessagePart6 < mRespPart; localMessagePart6++) { String propName = mRespPart[localMessagePart6];
/*      */                         
/*  860 */                         CompareStrings(propName);
/*  861 */                         CompareStrings(((RestMethod)modelItem).getParams().getPropertyValue(propName));
/*      */                       }
/*      */                     }
/*      */                     
/*  865 */                     if ((((RestMethod)modelItem).getOverlayParams() != null) && (((RestMethod)modelItem).getOverlayParams().getPropertyNames() != null))
/*      */                     {
/*  867 */                       mRespPart = (mRespPart = ((RestMethod)modelItem).getOverlayParams().getPropertyNames()).length; for (MessagePart localMessagePart7 = 0; localMessagePart7 < mRespPart; localMessagePart7++) { String propName = mRespPart[localMessagePart7];
/*      */                         
/*  869 */                         CompareStrings(propName);
/*  870 */                         CompareStrings(((RestMethod)modelItem).getOverlayParams().getPropertyValue(propName));
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     
/*      */ 
/*      */                   }
/*  877 */                   else if ((modelItem instanceof WsdlOperation))
/*      */                   {
/*      */ 
/*  880 */                     CompareStrings(((WsdlOperation)modelItem).getBindingOperationName());
/*  881 */                     CompareStrings(((WsdlOperation)modelItem).getInputName());
/*  882 */                     CompareStrings(((WsdlOperation)modelItem).getOutputName());
/*  883 */                     CompareStrings(((WsdlOperation)modelItem).getType());
/*      */                     
/*  885 */                     MessagePart[] mReqPartList = ((WsdlOperation)modelItem).getDefaultRequestParts();
/*      */                     
/*  887 */                     mRespPart = (mRespPart = mReqPartList).length; for (MessagePart localMessagePart8 = 0; localMessagePart8 < mRespPart; localMessagePart8++) { MessagePart mReqPart = mRespPart[localMessagePart8];
/*      */                       
/*  889 */                       CompareStrings(mReqPart.getName());
/*  890 */                       CompareStrings(mReqPart.getDescription());
/*      */                     }
/*      */                     
/*  893 */                     MessagePart[] mRespPartList = ((WsdlOperation)modelItem).getDefaultResponseParts();
/*      */                     
/*  895 */                     mRespPart = (assertion = mRespPartList).length; for (mRespPart = 0; mRespPart < mRespPart; mRespPart++) { mRespPart = assertion[mRespPart];
/*      */                       
/*  897 */                       CompareStrings(((MessagePart)mRespPart).getName());
/*  898 */                       CompareStrings(((MessagePart)mRespPart).getDescription());
/*      */                     }
/*      */                   }
/*      */                 } } } } } }
/*      */       String eventName;
/*      */       String eventType;
/*  904 */       if (this.soapUIProFlag)
/*      */       {
/*  906 */         if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.ProJdbcRequestTestStep))
/*      */         {
/*  908 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.ProJdbcRequestTestStep)modelItem).getDbConnectionName());
/*      */         }
/*  910 */         else if ((modelItem instanceof WsdlDataSourceTestStep))
/*      */         {
/*      */ 
/*      */ 
/*  914 */           CompareStrings(((WsdlDataSourceTestStep)modelItem).getDefaultSourcePropertyName());
/*  915 */           CompareStrings(((WsdlDataSourceTestStep)modelItem).getLabel());
/*      */           
/*  917 */           CompareStrings(((WsdlDataSourceTestStep)modelItem).getDefaultTargetPropertyName());
/*      */           
/*  919 */           if (((WsdlDataSourceTestStep)modelItem).getDataSource() != null)
/*      */           {
/*  921 */             CompareStrings(((WsdlDataSourceTestStep)modelItem).getDataSource().getType());
/*  922 */             CompareStrings(((WsdlDataSourceTestStep)modelItem).getDataSource().getDescription());
/*      */           }
/*      */           
/*  925 */           if (((WsdlDataSourceTestStep)modelItem).getConfig() != null) {
/*  926 */             CompareStrings(((com.eviware.soapui.config.TestStepConfig)((WsdlDataSourceTestStep)modelItem).getConfig()).toString());
/*      */           }
/*  928 */           if (((WsdlDataSourceTestStep)modelItem).getDataSourceStepConfig() != null)
/*      */           {
/*  930 */             CompareStrings(((WsdlDataSourceTestStep)modelItem).getDataSourceStepConfig().toString());
/*  931 */             if (((WsdlDataSourceTestStep)modelItem).getDataSourceStepConfig().getPropertyArray() != null)
/*  932 */               CompareStrings(((WsdlDataSourceTestStep)modelItem).getDataSourceStepConfig().getPropertyArray().toString());
/*      */           }
/*  934 */           if (((WsdlDataSourceTestStep)modelItem).getOrderedPropertyNames() != null) {
/*  935 */             CompareStrings(((WsdlDataSourceTestStep)modelItem).getOrderedPropertyNames().toString());
/*      */           }
/*  937 */           if (((WsdlDataSourceTestStep)modelItem).getPreparedProperties() != null) {
/*  938 */             CompareStrings(((WsdlDataSourceTestStep)modelItem).getPreparedProperties().toXml());
/*      */           }
/*  940 */           if (((WsdlDataSourceTestStep)modelItem).getXPathReferences() != null) {
/*  941 */             CompareStrings(((WsdlDataSourceTestStep)modelItem).getXPathReferences().toString());
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  946 */         else if ((modelItem instanceof WsdlDataSinkTestStep))
/*      */         {
/*  948 */           CompareStrings(((WsdlDataSinkTestStep)modelItem).getDefaultSourcePropertyName());
/*  949 */           CompareStrings(((WsdlDataSinkTestStep)modelItem).getLabel());
/*      */           
/*  951 */           CompareStrings(((WsdlDataSinkTestStep)modelItem).getDefaultTargetPropertyName());
/*      */           
/*  953 */           if (((WsdlDataSinkTestStep)modelItem).getDataSink() != null)
/*      */           {
/*  955 */             CompareStrings(((WsdlDataSinkTestStep)modelItem).getDataSink().getType());
/*  956 */             CompareStrings(((WsdlDataSinkTestStep)modelItem).getDataSink().getDescription());
/*      */           }
/*      */           
/*  959 */           if (((WsdlDataSinkTestStep)modelItem).getDataSinkStepConfig() != null)
/*      */           {
/*  961 */             CompareStrings(((WsdlDataSinkTestStep)modelItem).getDataSinkStepConfig().toString());
/*      */             
/*  963 */             if (((WsdlDataSinkTestStep)modelItem).getDataSinkStepConfig().getProperties() != null) {
/*  964 */               CompareStrings(((WsdlDataSinkTestStep)modelItem).getDataSinkStepConfig().getProperties().toString());
/*      */             }
/*      */           }
/*      */           
/*  968 */           if (((WsdlDataSinkTestStep)modelItem).getXPathReferences() != null) {
/*  969 */             CompareStrings(((WsdlDataSinkTestStep)modelItem).getXPathReferences().toString());
/*      */           }
/*  971 */           CompareStrings(((WsdlDataSinkTestStep)modelItem).getSharedDataSinkPropertyName());
/*      */ 
/*      */         }
/*  974 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.datagen.WsdlDataGeneratorTestStep))
/*      */         {
/*      */ 
/*      */ 
/*  978 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.datagen.WsdlDataGeneratorTestStep)modelItem).getDefaultSourcePropertyName());
/*  979 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.datagen.WsdlDataGeneratorTestStep)modelItem).getLabel());
/*      */           
/*  981 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.datagen.WsdlDataGeneratorTestStep)modelItem).getDefaultTargetPropertyName());
/*      */           
/*  983 */           if (((com.eviware.soapui.impl.wsdl.teststeps.datagen.WsdlDataGeneratorTestStep)modelItem).getDataGeneratorStepConfig() != null) {
/*  984 */             CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.datagen.WsdlDataGeneratorTestStep)modelItem).getDataGeneratorStepConfig().toString());
/*      */           }
/*      */         }
/*  987 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.teststeps.AssertionTestStep))
/*      */         {
/*      */ 
/*  990 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.AssertionTestStep)modelItem).getDefaultSourcePropertyName());
/*  991 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.AssertionTestStep)modelItem).getLabel());
/*      */           
/*  993 */           CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.AssertionTestStep)modelItem).getDefaultTargetPropertyName());
/*  994 */           List<AssertionEntry> assertionEList = ((com.eviware.soapui.impl.wsdl.teststeps.AssertionTestStep)modelItem).getAssertionEntryList();
/*      */           
/*  996 */           for (mRespPart = assertionEList.iterator(); ((Iterator)mRespPart).hasNext();) { AssertionEntry assertionEntry = (AssertionEntry)((Iterator)mRespPart).next();
/*      */             
/*  998 */             SearchParentAssertion(assertionEntry);
/*  999 */             SearchChildAssertions(assertionEntry);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1004 */         else if ((modelItem instanceof WsdlMockService))
/*      */         {
/* 1006 */           CompareStrings(((WsdlMockService)modelItem).getStringID());
/*      */ 
/*      */         }
/* 1009 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation))
/*      */         {
/*      */ 
/* 1012 */           CompareStrings(((com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation)modelItem).getScriptHelpUrl());
/*      */ 
/*      */         }
/* 1015 */         else if ((modelItem instanceof RestMockService))
/*      */         {
/* 1017 */           CompareStrings(((RestMockService)modelItem).getStringID());
/*      */ 
/*      */ 
/*      */         }
/* 1021 */         else if ((modelItem instanceof RestMockAction))
/*      */         {
/*      */ 
/* 1024 */           CompareStrings(((RestMockAction)modelItem).getScriptHelpUrl());
/*      */ 
/*      */ 
/*      */         }
/* 1028 */         else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.WsdlLoadTestPro))
/*      */         {
/* 1030 */           CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlLoadTestPro)modelItem).getOnReportScript());
/* 1031 */           if (((com.eviware.soapui.impl.wsdl.WsdlLoadTestPro)modelItem).getReportPapameters() != null) {
/* 1032 */             CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlLoadTestPro)modelItem).getReportPapameters().toString());
/*      */           }
/* 1034 */         } else if ((modelItem instanceof com.eviware.soapui.security.ProSecurityTest))
/*      */         {
/* 1036 */           CompareStrings(((com.eviware.soapui.security.ProSecurityTest)modelItem).getOnReportScript());
/* 1037 */           if (((com.eviware.soapui.security.ProSecurityTest)modelItem).getReportPapameters() != null) {
/* 1038 */             CompareStrings(((com.eviware.soapui.security.ProSecurityTest)modelItem).getReportPapameters().toString());
/*      */           }
/* 1040 */         } else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.WsdlTestCasePro))
/*      */         {
/* 1042 */           CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlTestCasePro)modelItem).getOnReportScript());
/*      */           
/* 1044 */           if (((com.eviware.soapui.impl.wsdl.WsdlTestCasePro)modelItem).getReportParameters() != null) {
/* 1045 */             CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlTestCasePro)modelItem).getReportParameters().toString());
/*      */           }
/* 1047 */         } else if ((modelItem instanceof com.eviware.soapui.impl.wsdl.WsdlTestSuitePro))
/*      */         {
/* 1049 */           CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlTestSuitePro)modelItem).getOnReportScript());
/*      */           
/* 1051 */           if (((com.eviware.soapui.impl.wsdl.WsdlTestSuitePro)modelItem).getReportParameters() != null) {
/* 1052 */             CompareStrings(((com.eviware.soapui.impl.wsdl.WsdlTestSuitePro)modelItem).getReportParameters().toString());
/*      */           }
/* 1054 */         } else if ((modelItem instanceof WsdlProjectPro))
/*      */         {
/* 1056 */           CompareStrings(((WsdlProjectPro)modelItem).getOnReportScript());
/*      */           
/* 1058 */           if (((WsdlProjectPro)modelItem).getEnvironmentNames() != null) {
/* 1059 */             CompareStrings(((WsdlProjectPro)modelItem).getEnvironmentNames().toString());
/*      */           }
/* 1061 */           Integer eventNum = Integer.valueOf(0);
/* 1062 */           Integer eventCount = Integer.valueOf(((WsdlProjectPro)modelItem).getEventHandlerCount());
/*      */           
/* 1064 */           if ((((WsdlProjectPro)modelItem).getRequirements() != null) && (((WsdlProjectPro)modelItem).getRequirements().getRequirementsList() != null))
/*      */           {
/* 1066 */             Object reqList = ((WsdlProjectPro)modelItem).getRequirements().getRequirementsList();
/* 1067 */             for (com.eviware.soapui.impl.wsdl.project.WsdlRequirement wsdlReq : (List)reqList)
/*      */             {
/* 1069 */               CompareStrings(wsdlReq.getId());
/* 1070 */               CompareStrings(wsdlReq.getName());
/* 1071 */               CompareStrings(wsdlReq.getDescription());
/* 1072 */               CompareStrings(wsdlReq.getStatus());
/* 1073 */               CompareStrings(wsdlReq.getDetails());
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1078 */           if (((WsdlProjectPro)modelItem).getReports() != null)
/*      */           {
/* 1080 */             Object reportList = ((WsdlProjectPro)modelItem).getReports();
/* 1081 */             for (com.eviware.soapui.reporting.support.ReportTemplate wsdlReq : (List)reportList)
/*      */             {
/* 1083 */               CompareStrings(wsdlReq.getName());
/* 1084 */               CompareStrings(wsdlReq.getDescription());
/* 1085 */               CompareStrings(wsdlReq.getData());
/* 1086 */               CompareStrings(wsdlReq.getSubreportsList().toString());
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1091 */           if ((((WsdlProjectPro)modelItem).getDatabaseConnectionContainer() != null) && (((WsdlProjectPro)modelItem).getDatabaseConnectionContainer().getDatabaseConnectionList() != null))
/*      */           {
/* 1093 */             Object dbConnectionList = ((WsdlProjectPro)modelItem).getDatabaseConnectionContainer().getDatabaseConnectionList();
/* 1094 */             for (com.eviware.soapui.impl.wsdl.support.connections.DatabaseConnection dbConnection : (List)dbConnectionList)
/*      */             {
/* 1096 */               CompareStrings(dbConnection.getName());
/* 1097 */               CompareStrings(dbConnection.getDriver());
/* 1098 */               CompareStrings(dbConnection.getConnectionString());
/*      */             }
/*      */           }
/*      */           
/* 1102 */           while (eventNum.intValue() < eventCount.intValue())
/*      */           {
/* 1104 */             eventName = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getName();
/* 1105 */             eventTarget = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getTarget();
/* 1106 */             eventType = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getType();
/* 1107 */             String eventScriptText = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getScript().getScriptText();
/* 1108 */             CompareStrings(eventName);
/* 1109 */             CompareStrings(eventTarget);
/* 1110 */             CompareStrings(eventType);
/* 1111 */             CompareStrings(eventScriptText);
/* 1112 */             eventNum = Integer.valueOf(eventNum.intValue() + 1);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1119 */       Class<?> classHandle = modelItem.getClass();
/*      */       
/* 1121 */       String eventTarget = (eventType = classHandle.getMethods()).length; for (String str1 = 0; str1 < eventTarget; str1++) { Method m = eventType[str1];
/*      */         Object localObject3;
/* 1123 */         int j; int i; if (m.getName().equals("getPropertyNames"))
/*      */         {
/* 1125 */           j = (localObject3 = ((TestPropertyHolder)modelItem).getPropertyNames()).length; for (i = 0; i < j; i++) { String propName = localObject3[i];
/*      */             
/* 1127 */             CompareStrings(propName);
/* 1128 */             CompareStrings(((TestPropertyHolder)modelItem).getPropertyValue(propName));
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1133 */         if (m.getName().equals("getEndpoints"))
/*      */         {
/* 1135 */           j = (localObject3 = (String[])m.invoke(modelItem, null)).length; for (i = 0; i < j; i++) { String endPoint = localObject3[i];
/*      */             
/* 1137 */             CompareStrings(endPoint);
/*      */           }
/*      */         }
/*      */         
/* 1141 */         if (m.getName().equals("getDefinition"))
/*      */         {
/* 1143 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1146 */         if (m.getName().equals("getRequestContent"))
/*      */         {
/* 1148 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1151 */         if (m.getName().equals("getRequestHeaders"))
/*      */         {
/* 1153 */           CompareStrings(m.invoke(modelItem, null).toString());
/*      */         }
/*      */         
/* 1156 */         if (m.getName().equals("getResponseHeaders"))
/*      */         {
/* 1158 */           CompareStrings(m.invoke(modelItem, null).toString());
/*      */         }
/*      */         
/* 1161 */         if (m.getName().equals("getResponseContentAsXml"))
/*      */         {
/* 1163 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1166 */         if (m.getName().equals("getResponseContent"))
/*      */         {
/* 1168 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1171 */         if (m.getName().equals("getAttachments"))
/*      */         {
/* 1173 */           j = (localObject3 = (com.eviware.soapui.model.iface.Attachment[])m.invoke(modelItem, null)).length; for (i = 0; i < j; i++) { com.eviware.soapui.model.iface.Attachment attachmentList = localObject3[i];
/*      */             
/* 1175 */             CompareStrings(attachmentList.getName());
/* 1176 */             CompareStrings(attachmentList.getUrl());
/*      */           }
/*      */         }
/*      */         
/* 1180 */         if (m.getName().equals("getBindAddress"))
/*      */         {
/* 1182 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1185 */         if (m.getName().equals("getDomain"))
/*      */         {
/* 1187 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1190 */         if (m.getName().equals("getEndpoint"))
/*      */         {
/* 1192 */           CompareStrings((String)m.invoke(modelItem, null));
/*      */         }
/*      */         
/* 1195 */         if (m.getName().equals("getAssertionList"))
/*      */         {
/*      */ 
/* 1198 */           if ((modelItem.getClass().getName().matches(".*WsdlLoadTestPro.*")) || (modelItem.getClass().getName().matches(".*WsdlLoadTest.*")))
/*      */           {
/* 1200 */             List<com.eviware.soapui.impl.wsdl.loadtest.LoadTestAssertion> assertionList = (List)m.invoke(modelItem, null);
/* 1201 */             for (com.eviware.soapui.impl.wsdl.loadtest.LoadTestAssertion assertion : assertionList)
/*      */             {
/* 1203 */               CompareStrings(assertion.getName());
/* 1204 */               CompareStrings(assertion.getDescription());
/* 1205 */               CompareStrings(assertion.getTargetStep());
/*      */               
/* 1207 */               if (assertion.getConfiguration() != null) {
/* 1208 */                 CompareStrings(assertion.getConfiguration().toString());
/*      */               }
/*      */             }
/*      */           }
/*      */           else {
/* 1213 */             List<com.eviware.soapui.model.testsuite.TestAssertion> assertionList = (List)m.invoke(modelItem, null);
/* 1214 */             for (com.eviware.soapui.model.testsuite.TestAssertion assertion : assertionList)
/*      */             {
/* 1216 */               CheckAssertion(assertion);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1225 */     if (this.matchFlag.intValue() == 1)
/*      */     {
/* 1227 */       this.searchResult.add(modelItem);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void CompareStrings(String stringValue)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1238 */       if ((stringValue != "") && (stringValue != null))
/*      */       {
/* 1240 */         stringValue = stringValue.toLowerCase().replaceAll("[\n\r]", "");
/* 1241 */         if (stringValue.matches(".*" + this.token + ".*"))
/*      */         {
/* 1243 */           this.matchFlag = Integer.valueOf(1);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1248 */       e.printStackTrace();
/* 1249 */       this.dialog.setVisible(false);
/* 1250 */       this.dialog.dispose();
/* 1251 */       com.eviware.soapui.support.UISupport.showErrorMessage("Failed during Search Operation. Check Error log file for details.");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void CheckAssertion(com.eviware.soapui.model.testsuite.TestAssertion assertion)
/*      */     throws Exception
/*      */   {
/* 1262 */     CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion)assertion).getName());
/* 1263 */     CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion)assertion).getLabel());
/* 1264 */     CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion)assertion).getDescription());
/*      */     
/* 1266 */     if (((com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion)assertion).getConfiguration() != null) {
/* 1267 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion)assertion).getConfiguration().toString());
/*      */     }
/* 1269 */     if (assertion.getClass().getName().matches(".*XPathContainsAssertion.*"))
/*      */     {
/* 1271 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.XPathContainsAssertion)assertion).getExpectedContent());
/* 1272 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.XPathContainsAssertion)assertion).getPath());
/*      */ 
/*      */     }
/* 1275 */     else if (assertion.getClass().getName().matches(".*XQueryContainsAssertion.*"))
/*      */     {
/* 1277 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.XQueryContainsAssertion)assertion).getExpectedContent());
/* 1278 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.XQueryContainsAssertion)assertion).getPath());
/*      */ 
/*      */     }
/* 1281 */     else if (assertion.getClass().getName().matches(".*SimpleContainsAssertion.*"))
/*      */     {
/* 1283 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleContainsAssertion)assertion).getToken());
/*      */ 
/*      */     }
/* 1286 */     else if (assertion.getClass().getName().matches(".*SimpleNotContainsAssertion.*"))
/*      */     {
/* 1288 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.SimpleNotContainsAssertion)assertion).getToken());
/*      */ 
/*      */ 
/*      */     }
/* 1292 */     else if (assertion.getClass().getName().matches(".*GroovyScriptAssertion.*"))
/*      */     {
/* 1294 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.GroovyScriptAssertion)assertion).getScriptText());
/*      */ 
/*      */     }
/* 1297 */     else if (assertion.getClass().getName().matches(".*ResponseSLAAssertion.*"))
/*      */     {
/* 1299 */       CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.basic.ResponseSLAAssertion)assertion).getSLA());
/*      */     }
/*      */     
/* 1302 */     if (this.soapUIProFlag)
/*      */     {
/* 1304 */       if (assertion.getClass().getName().matches(".*ProXPathContainsAssertion.*"))
/*      */       {
/* 1306 */         CompareStrings(((ProXPathContainsAssertion)assertion).getExpectedContent());
/* 1307 */         CompareStrings(((ProXPathContainsAssertion)assertion).getConfigurationDialogTitle());
/* 1308 */         CompareStrings(((ProXPathContainsAssertion)assertion).getContentAreaBorderTitle());
/* 1309 */         CompareStrings(((ProXPathContainsAssertion)assertion).getPath());
/* 1310 */         CompareStrings(((ProXPathContainsAssertion)assertion).getPathAreaBorderTitle());
/* 1311 */         CompareStrings(((ProXPathContainsAssertion)assertion).getPathAreaDescription());
/* 1312 */         CompareStrings(((ProXPathContainsAssertion)assertion).getPathAreaTitle());
/*      */ 
/*      */       }
/* 1315 */       else if (assertion.getClass().getName().matches(".*JsonPathContentAssertion.*"))
/*      */       {
/* 1317 */         CompareStrings(((JsonPathContentAssertion)assertion).getExpectedContent());
/* 1318 */         CompareStrings(((JsonPathContentAssertion)assertion).getConfigurationDialogTitle());
/* 1319 */         CompareStrings(((JsonPathContentAssertion)assertion).getContentAreaBorderTitle());
/* 1320 */         CompareStrings(((JsonPathContentAssertion)assertion).getPath());
/* 1321 */         CompareStrings(((JsonPathContentAssertion)assertion).getPathAreaBorderTitle());
/* 1322 */         CompareStrings(((JsonPathContentAssertion)assertion).getPathAreaDescription());
/* 1323 */         CompareStrings(((JsonPathContentAssertion)assertion).getPathAreaTitle());
/*      */ 
/*      */       }
/* 1326 */       else if (assertion.getClass().getName().matches(".*JsonPathRegExAssertion.*"))
/*      */       {
/* 1328 */         CompareStrings(((JsonPathRegExAssertion)assertion).getExpectedContent());
/* 1329 */         CompareStrings(((JsonPathRegExAssertion)assertion).getConfigurationDialogTitle());
/* 1330 */         CompareStrings(((JsonPathRegExAssertion)assertion).getContentAreaBorderTitle());
/* 1331 */         CompareStrings(((JsonPathRegExAssertion)assertion).getPath());
/* 1332 */         CompareStrings(((JsonPathRegExAssertion)assertion).getPathAreaBorderTitle());
/* 1333 */         CompareStrings(((JsonPathRegExAssertion)assertion).getPathAreaDescription());
/* 1334 */         CompareStrings(((JsonPathRegExAssertion)assertion).getPathAreaTitle());
/*      */ 
/*      */       }
/* 1337 */       else if (assertion.getClass().getName().matches(".*JsonPathExistenceAssertion.*"))
/*      */       {
/* 1339 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getExpectedContent());
/* 1340 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getConfigurationDialogTitle());
/* 1341 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getContentAreaBorderTitle());
/* 1342 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getPath());
/* 1343 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getPathAreaBorderTitle());
/* 1344 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getPathAreaDescription());
/* 1345 */         CompareStrings(((JsonPathExistenceAssertion)assertion).getPathAreaTitle());
/*      */       }
/* 1347 */       else if (assertion.getClass().getName().matches(".*ProGroovyScriptAssertion.*"))
/*      */       {
/* 1349 */         CompareStrings(((com.eviware.soapui.impl.wsdl.teststeps.assertions.ProGroovyScriptAssertion)assertion).getScriptText());
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\helpers\CopyOfSearchUtility.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */