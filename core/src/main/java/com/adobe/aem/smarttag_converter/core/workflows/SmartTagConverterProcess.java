// Servletを使わずDictServiceで動作するバージョン
// こちらの方が高速な反面、AEMの外に出しにくいかも
package com.adobe.aem.smarttag_converter.core.workflows;

import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.RepositoryException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.AccessDeniedException;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(property = {
    Constants.SERVICE_DESCRIPTION + "=Convert Smart tag into another tag defined in dictionary csv",
    Constants.SERVICE_VENDOR + "=Adobe Systems", "process.label" + "=Smart Tag Converter Process"})

public class SmartTagConverterProcess implements WorkflowProcess {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private BundleContext bundleContext;

  @Reference
    private SmartTagConverterDictionaryService dictionaryService;

  @Activate
  protected void activate(ComponentContext context) {
    log.info("Smart Tag Converter Workflow process Activated");
    this.bundleContext = context.getBundleContext();
    log.info("bundleContext"+this.bundleContext.toString());
  }

  @Override
  public final void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {

    try {
      log.info("■■■■■■■■■■■■■■■■ START SMART TAG CONVERT ■■■■■■■■■■■■■■■■");
      Node assetNode = getPayloadAssetNode(workItem, workflowSession);

      Node predictNode = assetNode.getNode("jcr:content/metadata/predictedTags");
      NodeIterator predictTags = predictNode.getNodes();

      while(predictTags.hasNext()) {
        Node smarttag = predictTags.nextNode();
        String tagname = smarttag.getProperty("name").getString();
        String converted = dictionaryService.getConvertedString(tagname);
        if ( converted.equals(tagname) ) continue;
        smarttag.setProperty("name", converted);
        
        Session session = workflowSession.adaptTo( Session.class );
        session.save();
        log.info( "__________■■■■■■ SMART TAG Converted : {} to {}　■■■■■■__________",tagname,converted );
      }

    } catch (Exception e) {
      log.info( "■■■■■■■■■■■■■■■■ SMART TAG Converter Error {}", e.toString() );
      e.printStackTrace();
    }

  }

  private Node getPayloadAssetNode(WorkItem workItem, WorkflowSession workflowSession)  throws ItemNotFoundException,AccessDeniedException,RepositoryException {
    Session session = workflowSession.adaptTo( Session.class );
    Node payLoadAsset = session.getNode( workItem.getWorkflowData().getPayload().toString() );
    // search for dam:AssetNode
    for(; !payLoadAsset.isNodeType("dam:Asset"); payLoadAsset=payLoadAsset.getParent()){}
    return payLoadAsset;
  }

}
