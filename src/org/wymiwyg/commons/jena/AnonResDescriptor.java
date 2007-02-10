/*
 * Copyright  2002-2004 WYMIWYG (www.wymiwyg.org)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.wymiwyg.commons.jena;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class AnonResDescriptor {

    public class PropertyValuePair {
        Property predicate;
        RDFNode object;
        
        public PropertyValuePair(Property predicate, RDFNode object) {
           this.predicate = predicate;
           this.object = object;
        }
        public boolean equals(Object obj) {
            if (!obj.getClass().equals(getClass())) {
                return false;
            }
            PropertyValuePair other = (PropertyValuePair)obj;
            return ((other.predicate.equals(predicate)) && (other.object.equals(object)));
        }
        
        public int hashCode() {
            return object.hashCode();
        }

    }
    Set properties = new HashSet();
    public AnonResDescriptor(Resource resource) {
       StmtIterator propertiesStmtIter = resource.listProperties();
       while (propertiesStmtIter.hasNext()) {
           Statement current = propertiesStmtIter.nextStatement();
           PropertyValuePair pair = new PropertyValuePair(current.getPredicate(), current.getObject());
           properties.add(pair);
       }
       propertiesStmtIter.close();
    }

    public boolean equals(Object obj) {
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AnonResDescriptor other = (AnonResDescriptor)obj;
        return other.properties.equals(properties);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return properties.hashCode();
    }
    
}
