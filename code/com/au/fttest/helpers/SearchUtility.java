/*     */ package com.au.fttest.helpers;
/*     */ 
/*     */ import com.eviware.soapui.config.RestRequestConfig;
/*     */ import com.eviware.soapui.config.TestStepConfig;
/*     */ import com.eviware.soapui.config.WsdlRequestConfig;
/*     */ import com.eviware.soapui.eventhandlers.support.DefaultSoapUIEventHandler;
/*     */ import com.eviware.soapui.impl.rest.RestMethod;
/*     */ import com.eviware.soapui.impl.rest.RestRepresentation;
/*     */ import com.eviware.soapui.impl.rest.RestRequest;
/*     */ import com.eviware.soapui.impl.rest.RestResource;
/*     */ import com.eviware.soapui.impl.rest.RestService;
/*     */ import com.eviware.soapui.impl.rest.mock.RestMockAction;
/*     */ import com.eviware.soapui.impl.rest.mock.RestMockResponse;
/*     */ import com.eviware.soapui.impl.rest.mock.RestMockService;
/*     */ import com.eviware.soapui.impl.rest.support.RestParamProperty;
/*     */ import com.eviware.soapui.impl.rest.support.RestParamsPropertyHolder;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlInterface;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlOperation;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlProject;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlProjectPro;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlRequest;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlTestCasePro;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
/*     */ import com.eviware.soapui.impl.wsdl.WsdlTestSuitePro;
/*     */ import com.eviware.soapui.impl.wsdl.loadtest.WsdlLoadTest;
/*     */ import com.eviware.soapui.impl.wsdl.mock.WsdlMockOperation;
/*     */ import com.eviware.soapui.impl.wsdl.mock.WsdlMockResponse;
/*     */ import com.eviware.soapui.impl.wsdl.mock.WsdlMockService;
/*     */ import com.eviware.soapui.impl.wsdl.project.WsdlRequirement;
/*     */ import com.eviware.soapui.impl.wsdl.support.connections.DatabaseConnection;
/*     */ import com.eviware.soapui.impl.wsdl.support.connections.DefaultDatabaseConnectionContainer;
/*     */ import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
/*     */ import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
/*     */ import com.eviware.soapui.model.ModelItem;
/*     */ import com.eviware.soapui.model.TestPropertyHolder;
/*     */ import com.eviware.soapui.model.iface.Attachment;
/*     */ import com.eviware.soapui.model.iface.MessagePart;
/*     */ import com.eviware.soapui.reporting.support.ReportTemplate;
/*     */ import com.eviware.soapui.security.SecurityTest;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JDialog;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ public class SearchUtility
/*     */ {
/*  48 */   private List<ModelItem> searchResult = new java.util.ArrayList();
/*     */   
/*     */   private String token;
/*     */   
/*     */   private ModelItem parent;
/*     */   
/*     */   private Integer matchFlag;
/*     */   private Integer nameSearchFlag;
/*     */   private JDialog dialog;
/*     */   private boolean soapUIProFlag;
/*     */   
/*     */   public SearchUtility(Integer nameSearchFlag)
/*     */   {
/*  61 */     this.nameSearchFlag = nameSearchFlag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<ModelItem> SearchItemInSoapUI(ModelItem parent, String token, JDialog dialog, Boolean soapUIProFlag)
/*     */     throws Exception
/*     */   {
/*  70 */     this.token = token.toLowerCase();
/*  71 */     this.parent = parent;
/*  72 */     this.dialog = dialog;
/*  73 */     this.soapUIProFlag = soapUIProFlag.booleanValue();
/*     */     
/*  75 */     SearchParentElement(parent);
/*  76 */     SearchChildElements(parent);
/*  77 */     return this.searchResult;
/*     */   }
/*     */   
/*     */   private void SearchParentElement(ModelItem parent)
/*     */     throws Exception
/*     */   {
/*  83 */     SearchItem(parent);
/*     */   }
/*     */   
/*     */   private void SearchChildElements(ModelItem parent)
/*     */     throws Exception
/*     */   {
/*  89 */     for (ModelItem child : parent.getChildren())
/*     */     {
/*  91 */       SearchItem(child);
/*  92 */       SearchChildElements(child);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void SearchItem(ModelItem modelItem)
/*     */     throws Exception
/*     */   {
/* 104 */     this.matchFlag = Integer.valueOf(0);
/* 105 */     this.token = this.token.toLowerCase();
/* 106 */     String modelItemName = modelItem.getName().toLowerCase();
/* 107 */     CompareStrings(modelItemName);
/*     */     
/*     */ 
/* 110 */     if (this.nameSearchFlag.intValue() == 0)
/*     */     {
/* 112 */       String modelItemDesc = modelItem.getDescription();
/* 113 */       CompareStrings(modelItemDesc);
/*     */       Object localObject3;
/* 115 */       if ((modelItem instanceof WsdlTestStep))
/*     */       {
/* 117 */         CompareStrings(((TestStepConfig)((WsdlTestStep)modelItem).getConfig()).toString());
/*     */ 
/*     */       }
/* 120 */       else if ((modelItem instanceof WsdlLoadTest))
/*     */       {
/* 122 */         CompareStrings(((com.eviware.soapui.config.LoadTestConfig)((WsdlLoadTest)modelItem).getConfig()).toString());
/*     */ 
/*     */       }
/* 125 */       else if ((modelItem instanceof SecurityTest))
/*     */       {
/* 127 */         CompareStrings(((com.eviware.soapui.config.SecurityTestConfig)((SecurityTest)modelItem).getConfig()).toString());
/*     */ 
/*     */       }
/* 130 */       else if ((modelItem instanceof WsdlTestCase))
/*     */       {
/* 132 */         CompareStrings(((WsdlTestCase)modelItem).getSetupScript());
/* 133 */         CompareStrings(((WsdlTestCase)modelItem).getTearDownScript());
/*     */ 
/*     */       }
/* 136 */       else if ((modelItem instanceof WsdlTestSuite))
/*     */       {
/* 138 */         CompareStrings(((WsdlTestSuite)modelItem).getSetupScript());
/* 139 */         CompareStrings(((WsdlTestSuite)modelItem).getTearDownScript());
/*     */ 
/*     */       }
/* 142 */       else if ((modelItem instanceof WsdlMockResponse))
/*     */       {
/* 144 */         CompareStrings(((com.eviware.soapui.config.MockResponseConfig)((WsdlMockResponse)modelItem).getConfig()).toString());
/*     */ 
/*     */       }
/* 147 */       else if ((modelItem instanceof WsdlMockOperation))
/*     */       {
/* 149 */         CompareStrings(((WsdlMockOperation)modelItem).getDefaultResponse());
/* 150 */         CompareStrings(((WsdlMockOperation)modelItem).getScript());
/*     */ 
/*     */       }
/* 153 */       else if ((modelItem instanceof WsdlMockService))
/*     */       {
/* 155 */         CompareStrings(((WsdlMockService)modelItem).getHelpUrl());
/* 156 */         CompareStrings(((WsdlMockService)modelItem).getHost());
/* 157 */         CompareStrings(((WsdlMockService)modelItem).getIconName());
/* 158 */         CompareStrings(((WsdlMockService)modelItem).getIncomingWss());
/* 159 */         CompareStrings(((WsdlMockService)modelItem).getLocalMockServiceEndpoint());
/* 160 */         CompareStrings(((WsdlMockService)modelItem).getMockServiceEndpoint());
/* 161 */         CompareStrings(((WsdlMockService)modelItem).getOutgoingWss());
/* 162 */         CompareStrings(((WsdlMockService)modelItem).getAfterRequestScript());
/* 163 */         CompareStrings(((WsdlMockService)modelItem).getDocroot());
/* 164 */         CompareStrings(((WsdlMockService)modelItem).getLocalEndpoint());
/* 165 */         CompareStrings(((WsdlMockService)modelItem).getOnRequestScript());
/* 166 */         CompareStrings(((WsdlMockService)modelItem).getPath());
/* 167 */         CompareStrings(((WsdlMockService)modelItem).getMockServiceEndpoint());
/* 168 */         CompareStrings(((WsdlMockService)modelItem).getStartScript());
/* 169 */         CompareStrings(((WsdlMockService)modelItem).getStopScript());
/*     */ 
/*     */       }
/* 172 */       else if ((modelItem instanceof RestMockResponse))
/*     */       {
/* 174 */         CompareStrings(((com.eviware.soapui.config.RESTMockResponseConfig)((RestMockResponse)modelItem).getConfig()).toString());
/*     */ 
/*     */       }
/* 177 */       else if ((modelItem instanceof RestMockAction))
/*     */       {
/* 179 */         CompareStrings(((RestMockAction)modelItem).getDefaultResponse());
/* 180 */         CompareStrings(((RestMockAction)modelItem).getScript());
/* 181 */         CompareStrings(((RestMockAction)modelItem).getResourcePath());
/* 182 */         CompareStrings(((RestMockAction)modelItem).getHelpUrl());
/*     */ 
/*     */       }
/* 185 */       else if ((modelItem instanceof RestMockService))
/*     */       {
/* 187 */         CompareStrings(((RestMockService)modelItem).getHelpUrl());
/* 188 */         CompareStrings(((RestMockService)modelItem).getHost());
/* 189 */         CompareStrings(((RestMockService)modelItem).getIconName());
/* 190 */         CompareStrings(((RestMockService)modelItem).getAfterRequestScript());
/* 191 */         CompareStrings(((RestMockService)modelItem).getDocroot());
/* 192 */         CompareStrings(((RestMockService)modelItem).getLocalEndpoint());
/* 193 */         CompareStrings(((RestMockService)modelItem).getOnRequestScript());
/* 194 */         CompareStrings(((RestMockService)modelItem).getPath());
/* 195 */         CompareStrings(((RestMockService)modelItem).getStartScript());
/* 196 */         CompareStrings(((RestMockService)modelItem).getStopScript());
/*     */ 
/*     */       }
/* 199 */       else if ((modelItem instanceof RestRequest))
/*     */       {
/* 201 */         CompareStrings(((RestRequestConfig)((RestRequest)modelItem).getConfig()).toString()); } else { Object localObject2;
/*     */         int j;
/*     */         int i;
/* 204 */         if ((modelItem instanceof RestMethod))
/*     */         {
/* 206 */           CompareStrings(((RestMethod)modelItem).getDefaultRequestMediaType());
/*     */           
/* 208 */           if (((RestMethod)modelItem).getResponseMediaTypes() != null) {
/* 209 */             CompareStrings(((RestMethod)modelItem).getResponseMediaTypes().toString());
/*     */           }
/* 211 */           RestRepresentation[] repList = ((RestMethod)modelItem).getRepresentations();
/*     */           
/* 213 */           j = (localObject2 = repList).length; for (i = 0; i < j; i++) { RestRepresentation restRep = localObject2[i];
/*     */             
/* 215 */             CompareStrings(restRep.getDefaultContent());
/* 216 */             CompareStrings(restRep.getDescription());
/* 217 */             CompareStrings(restRep.getMediaType());
/*     */           }
/*     */           
/* 220 */           if ((((RestMethod)modelItem).getParams() != null) && (((RestMethod)modelItem).getParams().getPropertyNames() != null))
/*     */           {
/* 222 */             j = (localObject2 = ((RestMethod)modelItem).getParams().getPropertyNames()).length; for (i = 0; i < j; i++) { String propName = localObject2[i];
/*     */               
/* 224 */               CompareStrings(propName);
/* 225 */               CompareStrings(((RestMethod)modelItem).getParams().getPropertyValue(propName));
/*     */             }
/*     */           }
/*     */           
/* 229 */           if ((((RestMethod)modelItem).getOverlayParams() != null) && (((RestMethod)modelItem).getOverlayParams().getPropertyNames() != null))
/*     */           {
/* 231 */             j = (localObject2 = ((RestMethod)modelItem).getOverlayParams().getPropertyNames()).length; for (i = 0; i < j; i++) { String propName = localObject2[i];
/*     */               
/* 233 */               CompareStrings(propName);
/* 234 */               CompareStrings(((RestMethod)modelItem).getOverlayParams().getPropertyValue(propName));
/*     */             }
/*     */           }
/*     */         } else { MessagePart[] mRespPartList;
/*     */           MessagePart mRespPart;
/* 239 */           if ((modelItem instanceof RestResource))
/*     */           {
/* 241 */             CompareStrings(((RestResource)modelItem).getFullPath());
/* 242 */             CompareStrings(((RestResource)modelItem).getPath());
/*     */             
/* 244 */             if (((RestResource)modelItem).getRequestMediaTypes() != null) {
/* 245 */               CompareStrings(((RestResource)modelItem).getRequestMediaTypes().toString());
/*     */             }
/* 247 */             if (((RestResource)modelItem).getResponseMediaTypes() != null) {
/* 248 */               CompareStrings(((RestResource)modelItem).getResponseMediaTypes().toString());
/*     */             }
/* 250 */             RestParamProperty[] restParamList = ((RestResource)modelItem).getDefaultParams();
/*     */             
/* 252 */             j = (localObject2 = restParamList).length; for (i = 0; i < j; i++) { RestParamProperty restParam = localObject2[i];
/*     */               
/* 254 */               CompareStrings(restParam.getName());
/* 255 */               CompareStrings(restParam.getDescription());
/* 256 */               CompareStrings(restParam.getValue());
/*     */               
/* 258 */               if (restParam.getOptions() != null)
/* 259 */                 CompareStrings(restParam.getOptions().toString());
/* 260 */               CompareStrings(restParam.getPath());
/*     */             }
/*     */             
/* 263 */             if ((((RestResource)modelItem).getParams() != null) && (((RestResource)modelItem).getParams().getPropertyNames() != null))
/*     */             {
/* 265 */               j = (localObject2 = ((RestResource)modelItem).getParams().getPropertyNames()).length; for (i = 0; i < j; i++) { String propName = localObject2[i];
/*     */                 
/* 267 */                 CompareStrings(propName);
/* 268 */                 CompareStrings(((RestResource)modelItem).getParams().getPropertyValue(propName));
/*     */               }
/*     */             }
/*     */             
/* 272 */             MessagePart[] mReqPartList = ((RestResource)modelItem).getDefaultRequestParts();
/*     */             MessagePart[] arrayOfMessagePart4;
/* 274 */             int k = (arrayOfMessagePart4 = mReqPartList).length; for (j = 0; j < k; j++) { MessagePart mReqPart = arrayOfMessagePart4[j];
/*     */               
/* 276 */               CompareStrings(mReqPart.getName());
/* 277 */               CompareStrings(mReqPart.getDescription());
/*     */             }
/*     */             
/* 280 */             mRespPartList = ((RestResource)modelItem).getDefaultResponseParts();
/*     */             MessagePart[] arrayOfMessagePart6;
/* 282 */             int m = (arrayOfMessagePart6 = mRespPartList).length; for (k = 0; k < m; k++) { mRespPart = arrayOfMessagePart6[k];
/*     */               
/* 284 */               CompareStrings(mRespPart.getName());
/* 285 */               CompareStrings(mRespPart.getDescription());
/*     */             }
/*     */             
/*     */           }
/* 289 */           else if ((modelItem instanceof RestService))
/*     */           {
/* 291 */             CompareStrings(((RestService)modelItem).getBasePath());
/* 292 */             CompareStrings(((RestService)modelItem).getInferredSchema());
/* 293 */             CompareStrings(((RestService)modelItem).getInterfaceType());
/* 294 */             CompareStrings(((RestService)modelItem).getTechnicalId());
/* 295 */             CompareStrings(((RestService)modelItem).getType());
/* 296 */             CompareStrings(((RestService)modelItem).getWadlUrl());
/* 297 */             CompareStrings(((RestService)modelItem).getWadlVersion());
/*     */ 
/*     */           }
/* 300 */           else if ((modelItem instanceof WsdlRequest))
/*     */           {
/* 302 */             CompareStrings(((WsdlRequestConfig)((WsdlRequest)modelItem).getConfig()).toString());
/*     */           }
/* 304 */           else if ((modelItem instanceof WsdlOperation))
/*     */           {
/* 306 */             CompareStrings(((WsdlOperation)modelItem).getBindingOperationName());
/* 307 */             CompareStrings(((WsdlOperation)modelItem).getInputName());
/* 308 */             CompareStrings(((WsdlOperation)modelItem).getOutputName());
/* 309 */             CompareStrings(((WsdlOperation)modelItem).getType());
/*     */             
/* 311 */             MessagePart[] mReqPartList = ((WsdlOperation)modelItem).getDefaultRequestParts();
/*     */             MessagePart[] arrayOfMessagePart3;
/* 313 */             MessagePart[] arrayOfMessagePart2 = (arrayOfMessagePart3 = mReqPartList).length; for (MessagePart[] arrayOfMessagePart1 = 0; arrayOfMessagePart1 < arrayOfMessagePart2; arrayOfMessagePart1++) { MessagePart mReqPart = arrayOfMessagePart3[arrayOfMessagePart1];
/*     */               
/* 315 */               CompareStrings(mReqPart.getName());
/* 316 */               CompareStrings(mReqPart.getDescription());
/*     */             }
/*     */             
/* 319 */             MessagePart[] mRespPartList = ((WsdlOperation)modelItem).getDefaultResponseParts();
/*     */             MessagePart[] arrayOfMessagePart5;
/* 321 */             localObject3 = (arrayOfMessagePart5 = mRespPartList).length; for (Object localObject1 = 0; localObject1 < localObject3; localObject1++) { MessagePart mRespPart = arrayOfMessagePart5[localObject1];
/*     */               
/* 323 */               CompareStrings(mRespPart.getName());
/* 324 */               CompareStrings(mRespPart.getDescription());
/*     */             }
/*     */           }
/* 327 */           else if ((modelItem instanceof WsdlInterface))
/*     */           {
/* 329 */             if (((WsdlInterface)modelItem).getBindingName() != null)
/*     */             {
/* 331 */               CompareStrings(((WsdlInterface)modelItem).getBindingName().getLocalPart());
/* 332 */               CompareStrings(((WsdlInterface)modelItem).getBindingName().getNamespaceURI());
/* 333 */               CompareStrings(((WsdlInterface)modelItem).getBindingName().getPrefix());
/*     */             }
/* 335 */             CompareStrings(((WsdlInterface)modelItem).getInterfaceType());
/* 336 */             CompareStrings(((WsdlInterface)modelItem).getTechnicalId());
/* 337 */             CompareStrings(((WsdlInterface)modelItem).getType());
/* 338 */             CompareStrings(((WsdlInterface)modelItem).getWsaVersion());
/*     */ 
/*     */           }
/* 341 */           else if ((modelItem instanceof WsdlProject))
/*     */           {
/* 343 */             CompareStrings(((WsdlProject)modelItem).getAfterLoadScript());
/* 344 */             CompareStrings(((WsdlProject)modelItem).getBeforeSaveScript());
/* 345 */             CompareStrings(((WsdlProject)modelItem).getAfterRunScript());
/* 346 */             CompareStrings(((WsdlProject)modelItem).getBeforeRunScript());
/* 347 */             CompareStrings(((WsdlProject)modelItem).getHermesConfig());
/* 348 */             CompareStrings(((WsdlProject)modelItem).getPath()); } } }
/*     */       String eventName;
/*     */       String eventTarget;
/* 351 */       Object eventType; if (this.soapUIProFlag)
/*     */       {
/* 353 */         if ((modelItem instanceof WsdlMockService))
/*     */         {
/* 355 */           CompareStrings(((WsdlMockService)modelItem).getStringID());
/*     */ 
/*     */         }
/* 358 */         else if ((modelItem instanceof WsdlMockOperation))
/*     */         {
/*     */ 
/* 361 */           CompareStrings(((WsdlMockOperation)modelItem).getScriptHelpUrl());
/*     */ 
/*     */         }
/* 364 */         else if ((modelItem instanceof RestMockService))
/*     */         {
/* 366 */           CompareStrings(((RestMockService)modelItem).getStringID());
/*     */ 
/*     */ 
/*     */         }
/* 370 */         else if ((modelItem instanceof RestMockAction))
/*     */         {
/*     */ 
/* 373 */           CompareStrings(((RestMockAction)modelItem).getScriptHelpUrl());
/*     */ 
/*     */         }
/* 376 */         else if ((modelItem instanceof WsdlTestCasePro))
/*     */         {
/* 378 */           CompareStrings(((WsdlTestCasePro)modelItem).getOnReportScript());
/*     */           
/* 380 */           if (((WsdlTestCasePro)modelItem).getReportParameters() != null) {
/* 381 */             CompareStrings(((WsdlTestCasePro)modelItem).getReportParameters().toString());
/*     */           }
/* 383 */         } else if ((modelItem instanceof WsdlTestSuitePro))
/*     */         {
/* 385 */           CompareStrings(((WsdlTestSuitePro)modelItem).getOnReportScript());
/*     */           
/* 387 */           if (((WsdlTestSuitePro)modelItem).getReportParameters() != null) {
/* 388 */             CompareStrings(((WsdlTestSuitePro)modelItem).getReportParameters().toString());
/*     */           }
/* 390 */         } else if ((modelItem instanceof WsdlProjectPro))
/*     */         {
/* 392 */           CompareStrings(((WsdlProjectPro)modelItem).getOnReportScript());
/*     */           
/* 394 */           if (((WsdlProjectPro)modelItem).getEnvironmentNames() != null) {
/* 395 */             CompareStrings(((WsdlProjectPro)modelItem).getEnvironmentNames().toString());
/*     */           }
/* 397 */           Integer eventNum = Integer.valueOf(0);
/* 398 */           Integer eventCount = Integer.valueOf(((WsdlProjectPro)modelItem).getEventHandlerCount());
/*     */           
/* 400 */           if ((((WsdlProjectPro)modelItem).getRequirements() != null) && (((WsdlProjectPro)modelItem).getRequirements().getRequirementsList() != null))
/*     */           {
/* 402 */             Object reqList = ((WsdlProjectPro)modelItem).getRequirements().getRequirementsList();
/* 403 */             for (localObject3 = ((List)reqList).iterator(); ((Iterator)localObject3).hasNext();) { WsdlRequirement wsdlReq = (WsdlRequirement)((Iterator)localObject3).next();
/*     */               
/* 405 */               CompareStrings(wsdlReq.getId());
/* 406 */               CompareStrings(wsdlReq.getName());
/* 407 */               CompareStrings(wsdlReq.getDescription());
/* 408 */               CompareStrings(wsdlReq.getStatus());
/* 409 */               CompareStrings(wsdlReq.getDetails());
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 414 */           if (((WsdlProjectPro)modelItem).getReports() != null)
/*     */           {
/* 416 */             Object reportList = ((WsdlProjectPro)modelItem).getReports();
/* 417 */             for (localObject3 = ((List)reportList).iterator(); ((Iterator)localObject3).hasNext();) { ReportTemplate wsdlReq = (ReportTemplate)((Iterator)localObject3).next();
/*     */               
/* 419 */               CompareStrings(wsdlReq.getName());
/* 420 */               CompareStrings(wsdlReq.getDescription());
/* 421 */               CompareStrings(wsdlReq.getData());
/* 422 */               CompareStrings(wsdlReq.getSubreportsList().toString());
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 427 */           if ((((WsdlProjectPro)modelItem).getDatabaseConnectionContainer() != null) && (((WsdlProjectPro)modelItem).getDatabaseConnectionContainer().getDatabaseConnectionList() != null))
/*     */           {
/* 429 */             Object dbConnectionList = ((WsdlProjectPro)modelItem).getDatabaseConnectionContainer().getDatabaseConnectionList();
/* 430 */             for (localObject3 = ((List)dbConnectionList).iterator(); ((Iterator)localObject3).hasNext();) { DatabaseConnection dbConnection = (DatabaseConnection)((Iterator)localObject3).next();
/*     */               
/* 432 */               CompareStrings(dbConnection.getName());
/* 433 */               CompareStrings(dbConnection.getDriver());
/* 434 */               CompareStrings(dbConnection.getConnectionString());
/*     */             }
/*     */           }
/*     */           
/* 438 */           while (eventNum.intValue() < eventCount.intValue())
/*     */           {
/* 440 */             eventName = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getName();
/* 441 */             eventTarget = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getTarget();
/* 442 */             eventType = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getType();
/* 443 */             String eventScriptText = ((WsdlProjectPro)modelItem).getEventHandlerAt(eventNum.intValue()).getScript().getScriptText();
/* 444 */             CompareStrings(eventName);
/* 445 */             CompareStrings(eventTarget);
/* 446 */             CompareStrings((String)eventType);
/* 447 */             CompareStrings(eventScriptText);
/* 448 */             eventNum = Integer.valueOf(eventNum.intValue() + 1);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 453 */       Class<?> classHandle = modelItem.getClass();
/*     */       
/* 455 */       String str2 = (eventType = classHandle.getMethods()).length; for (String str1 = 0; str1 < str2; str1++) { Method m = eventType[str1];
/*     */         Object localObject4;
/* 457 */         int i1; int n; if (m.getName().equals("getPropertyNames"))
/*     */         {
/* 459 */           i1 = (localObject4 = ((TestPropertyHolder)modelItem).getPropertyNames()).length; for (n = 0; n < i1; n++) { String propName = localObject4[n];
/*     */             
/* 461 */             CompareStrings(propName);
/* 462 */             CompareStrings(((TestPropertyHolder)modelItem).getPropertyValue(propName));
/*     */           }
/*     */         }
/*     */         
/* 466 */         if (m.getName().equals("getResponseHeaders"))
/*     */         {
/* 468 */           CompareStrings(m.invoke(modelItem, null).toString());
/*     */         }
/*     */         
/* 471 */         if (m.getName().equals("getResponseContentAsXml"))
/*     */         {
/* 473 */           CompareStrings((String)m.invoke(modelItem, null));
/*     */         }
/*     */         
/* 476 */         if (m.getName().equals("getResponseContent"))
/*     */         {
/* 478 */           CompareStrings((String)m.invoke(modelItem, null));
/*     */         }
/*     */         
/* 481 */         if (m.getName().equals("getAttachments"))
/*     */         {
/* 483 */           i1 = (localObject4 = (Attachment[])m.invoke(modelItem, null)).length; for (n = 0; n < i1; n++) { Attachment attachmentList = localObject4[n];
/*     */             
/* 485 */             CompareStrings(attachmentList.getName());
/* 486 */             CompareStrings(attachmentList.getUrl());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 495 */     if (this.matchFlag.intValue() == 1)
/*     */     {
/* 497 */       this.searchResult.add(modelItem);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void CompareStrings(String stringValue)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 508 */       if ((stringValue != "") && (stringValue != null))
/*     */       {
/* 510 */         stringValue = stringValue.toLowerCase().replaceAll("[\n\r]", "");
/* 511 */         if (stringValue.matches(".*" + this.token + ".*"))
/*     */         {
/* 513 */           this.matchFlag = Integer.valueOf(1);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 518 */       e.printStackTrace();
/* 519 */       this.dialog.setVisible(false);
/* 520 */       this.dialog.dispose();
/* 521 */       com.eviware.soapui.support.UISupport.showErrorMessage("Failed during Search Operation. Check Error log file for details.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\punigupta\Documents\soap plugin\ext\SearchPlugin.jar!\com\au\fttest\helpers\SearchUtility.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */