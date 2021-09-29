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
package com.adobe.aem.smarttag_converter.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.adobe.aem.smarttag_converter.core.services.impl.SmartTagConverterDictionaryServiceImpl;

import java.util.HashMap;

@ExtendWith(AemContextExtension.class)
public class SmartTagConverterDictionaryServiceTest {

    @Mock
    private HashMap<String, String> dict;
        
    @InjectMocks
    private SmartTagConverterDictionaryServiceImpl dictServiceImpl;
 
    @BeforeEach
    private void initMocks(){
        MockitoAnnotations.initMocks(this);
        String tag = "japanese";
        String expected ="日本語";
        Mockito.when(dict.get(tag)).thenReturn(expected);
    }    
 
    @Test
    void testService() throws Exception {
        String convertedString = dictServiceImpl.getConvertedString("japanese");
        assertEquals("日本語",convertedString);
    }
}
