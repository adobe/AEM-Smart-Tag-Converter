/*
Copyright 2021 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/

/* 
// == Smart Tag Convert Test Servlet ==
// Test Servlet for Dictionary Service
// Sample request:
// GET "http://localhost:4502/bin/smartTagConverter?tag=word"
*/
package com.adobe.aem.smarttag_converter.core.servlets;

import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
// import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.framework.Constants;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = Servlet.class
  , property = {
    Constants.SERVICE_DESCRIPTION + "=Test servlet for Smart Tag Converter Dictionary. e.g.　http://localhost:4502/bin/smartTagConverter?tag=sky ",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET,
    "sling.servlet.paths=" + "/bin/smartTagConverter"
  })

public class SmartTagConverterServlet extends SlingSafeMethodsServlet {
  private static final long serialVersionUID = 5L;
  private static final Logger log = LoggerFactory.getLogger(SmartTagConverterServlet.class);

  @Reference
  private SmartTagConverterDictionaryService dictionaryService;

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException, ServletException {
    String tag = request.getParameter("tag");
    String convertedTag = (!tag.isEmpty())?dictionaryService.getConvertedString(tag):"NA";
    log.info("__________ Smart Tag Converter: {} to {}",tag,convertedTag);

    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    response.getWriter().print(convertedTag);
  }
}

