/*
 * Copyright 2006-2007 the original author or authors.
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
package org.springframework.batch.core.configuration.xml;

import java.util.Collection;

import org.springframework.batch.core.job.flow.DecisionState;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.flow.StateTransition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Internal parser for the &lt;decision/&gt; elements inside a job. A decision
 * element references a bean definition for a {@link JobExecutionDecider} and
 * goes on to list a set of transitions to other states with &lt;next
 * on="pattern" to="stepName"/&gt;. Used by the {@link JobParser}.
 * 
 * @see JobParser
 * 
 * @author Dave Syer
 * 
 */
public class DecisionParser {

	/**
	 * Parse the decision and turn it into a list of transitions.
	 * 
	 * @param element the &lt;decision/gt; element to parse
	 * @param parserContext the parser context for the bean factory
	 * @return a collection of bean definitions for {@link StateTransition}
	 * instances objects
	 */
	public Collection<RuntimeBeanReference> parse(Element element, ParserContext parserContext) {

		String refAttribute = element.getAttribute("decider");
		String idAttribute = element.getAttribute("id");

		BeanDefinitionBuilder stateBuilder = BeanDefinitionBuilder.genericBeanDefinition(DecisionState.class);
		stateBuilder.addConstructorArgValue(new RuntimeBeanReference(refAttribute));
		stateBuilder.addConstructorArgValue(idAttribute);
		return StepParser.getNextElements(parserContext, stateBuilder.getBeanDefinition(), element);

	}
}