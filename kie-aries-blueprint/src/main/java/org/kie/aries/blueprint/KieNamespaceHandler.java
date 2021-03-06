/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.aries.blueprint;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.aries.blueprint.ParserContext;
import org.kie.aries.blueprint.namespace.AbstractElementParser;
import org.kie.aries.blueprint.namespace.KieBaseElementParser;
import org.kie.aries.blueprint.namespace.KieContainerElementParser;
import org.kie.aries.blueprint.namespace.KieEnvironmentElementParser;
import org.kie.aries.blueprint.namespace.KieEventListenersElementParser;
import org.kie.aries.blueprint.namespace.KieRuntimeManagerElementParser;
import org.kie.aries.blueprint.namespace.KieRuntimeManagerSessionElementParser;
import org.kie.aries.blueprint.namespace.KieSessionElementParser;
import org.kie.aries.blueprint.namespace.KieStoreElementParser;
import org.kie.aries.blueprint.namespace.ReleaseIdElementParser;
import org.osgi.service.blueprint.container.ComponentDefinitionException;
import org.osgi.service.blueprint.reflect.ComponentMetadata;
import org.osgi.service.blueprint.reflect.Metadata;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class KieNamespaceHandler implements org.apache.aries.blueprint.NamespaceHandler {

    /** The Drools Aries Blueprint namespace */
    public static final String NS_URI = "http://drools.org/schema/kie-aries-blueprint/1.0.0";
    /** The standard blueprint namespace */
    private static final String BLUEPRINT_NS = "http://www.osgi.org/xmlns/blueprint/v1.0.0";

    public static final String ELEMENT_RELEASE_ID = "releaseId";
    public static final String ELEMENT_KBASE = "kbase-ref";
    public static final String ELEMENT_KCONTAINER = "kcontainer-ref";
    public static final String ELEMENT_KSTORE = "kstore";
    public static final String ELEMENT_KSESSION = "ksession";
    public static final String ELEMENT_KSESSION_REF = "ksession-ref";
    public static final String ELEMENT_EVENT_LISTENERS = "eventListeners";
    public static final String ELEMENT_ENVIRONMENT = "environment";
    public static final String ELEMENT_KRUNTIMEMANAGER = "kruntimeManager";
    public static final String ELEMENT_KSESSION_RUNTIMEMANAGER = "kruntimeManagerSession";

    protected static Map<String, AbstractElementParser> droolsElementParserMap = new HashMap<String, AbstractElementParser>();
    static {
        droolsElementParserMap.put(ELEMENT_RELEASE_ID, new ReleaseIdElementParser());
        droolsElementParserMap.put(ELEMENT_KBASE, new KieBaseElementParser());
        droolsElementParserMap.put(ELEMENT_KSESSION, new KieSessionElementParser());
        droolsElementParserMap.put(ELEMENT_KSESSION_REF, new KieSessionElementParser());
        droolsElementParserMap.put(ELEMENT_EVENT_LISTENERS, new KieEventListenersElementParser());
        droolsElementParserMap.put(ELEMENT_KSTORE, new KieStoreElementParser());
        droolsElementParserMap.put(ELEMENT_ENVIRONMENT, new KieEnvironmentElementParser());
        droolsElementParserMap.put(ELEMENT_KCONTAINER, new KieContainerElementParser());
        droolsElementParserMap.put(ELEMENT_KRUNTIMEMANAGER, new KieRuntimeManagerElementParser());
        droolsElementParserMap.put(ELEMENT_KSESSION_RUNTIMEMANAGER, new KieRuntimeManagerSessionElementParser());
    }

    @Override
    public URL getSchemaLocation(String namespace) {
        if(NS_URI.equals(namespace)) {
            return getClass().getResource("kie-aries-blueprint.xsd");
        } else {
            return null;
        }
    }

    @Override
    public Set<Class> getManagedClasses() {
        //System.out.println("getManagedClasses ::");
        return null;
    }

    @Override
    public Metadata parse(Element element, ParserContext parserContext) {
        String elementName = element.getLocalName();
        AbstractElementParser elementParser = droolsElementParserMap.get(elementName);
        if ( elementParser == null) {
            throw new ComponentDefinitionException("Unsupported Kie Blueprint Element '"+elementName+"'");
        }
        return elementParser.parseElement(parserContext, element);
    }

    @Override
    public ComponentMetadata decorate(Node node, ComponentMetadata componentMetadata, ParserContext parserContext) {
        //System.out.println("decorate :: "+ node.getNodeName());
        return null;
    }
}
