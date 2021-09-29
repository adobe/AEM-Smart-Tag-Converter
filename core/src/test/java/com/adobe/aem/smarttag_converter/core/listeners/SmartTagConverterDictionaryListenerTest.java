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
package com.adobe.aem.smarttag_converter.core.listeners;

import com.adobe.aem.smarttag_converter.core.services.SmartTagConverterDictionaryService;

import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChange.ChangeType;
// import org.apache.sling.api.resource.observation.ResourceChangeListener;

import java.util.Collections;
import java.util.List;

// import org.apache.sling.api.SlingConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.osgi.service.event.Event;

import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class SmartTagConverterDictionaryListenerTest {
    @Mock
    private SmartTagConverterDictionaryService dictionaryService;

    @InjectMocks
    private SmartTagConverterDictionaryListener fixture;

    @BeforeEach
    private void initMocks(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(dictionaryService.getDictionaryName()).thenReturn("sample-dictionary.csv");
    }



    @Test
    void handleEvent() {
        TestLogger logger = TestLoggerFactory.getTestLogger(fixture.getClass());
        String eventPath ="/content/dam/aem-smarttag-converter/sample-dictionary.csv";

        List<ResourceChange> resourceChangeList = Collections.singletonList( 
             new ResourceChange(ChangeType.CHANGED, eventPath, false));
        
        fixture.onChange( resourceChangeList );

        // listenerはvoidなので、Logの出力結果からテストを行う
        List<LoggingEvent> events = logger.getLoggingEvents();
        assertEquals(2, events.size()); // ログが２回出力 = importDictionary()が完了したという意味

        LoggingEvent event = events.get(0); // 出力されるLogのうち最初を使う

        assertAll(
                () -> assertEquals(Level.INFO, event.getLevel()),
                () -> assertEquals(2, event.getArguments().size()),
                () -> assertEquals("sample-dictionary.csv", event.getArguments().get(0)),
                () -> assertEquals("/content/dam/aem-smarttag-converter/sample-dictionary.csv", event.getArguments().get(1)),
                () -> assertTrue(event.getArguments().get(0).toString().contains("sample-dictionary.csv"))
        );
    }
}
