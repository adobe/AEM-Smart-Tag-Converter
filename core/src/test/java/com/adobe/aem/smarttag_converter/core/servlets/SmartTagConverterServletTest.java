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
package com.adobe.aem.smarttag_converter.core.servlets;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryService;

@ExtendWith(AemContextExtension.class)
class SmartTagConverterServletTest {
    AemContext aemContext = new AemContext();

    @Mock
    private SmartTagConverterDictionaryService dictionaryService;

    @InjectMocks
    private SmartTagConverterServlet servlet;

    @BeforeEach
    private void initMocks(){
        MockitoAnnotations.initMocks(this);
        String tag = "japanese";
        String expected ="日本語";
        Mockito.when(dictionaryService.getConvertedString(tag)).thenReturn(expected);
    }

    @Test
    public void testServlet() throws Exception {

        MockSlingHttpServletRequest request = aemContext.request();
        MockSlingHttpServletResponse response = aemContext.response();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tag","japanese");
        request.setParameterMap(params);

        servlet.doGet(request, response);

        assertEquals("日本語", response.getOutputAsString());

    }

}
