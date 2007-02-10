/* CVS $Id: RDFSERIALIZER.java,v 1.1 2006/10/16 08:31:47 rebach Exp $ */
package org.wymiwyg.rdfserializer; 
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
 
/**
 * Vocabulary definitions from /Users/reto/Development/eclipse/rdfstyles/rdf/rdfserializer.rdf 
 * @author Auto-generated by schemagen on 19 Feb 2004 21:40 
 */
public class RDFSERIALIZER {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabalary as a string ({@value})</p> */
    public static final String NS = "http://wymiwyg.org/ontologies/rdfserializer";
    
    /** <p>The namespace of the vocabalary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabalary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property typeDescriptors = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#typeDescriptors" );
    
    public static final Property propertyDescriptors = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#propertyDescriptors" );
    
    public static final Property anonymousDeepness = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#anonymousDeepness" );
    
    public static final Property nonAnonymousDeepness = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#nonAnonymousDeepness" );
    
    public static final Property nameSpacePrefix = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#nameSpacePrefix" );
    
    public static final Property prefix = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#prefix" );
    
    public static final Property url = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#url" );
    
    public static final Property style = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#style" );
    
    public static final Property type = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#type" );
    
    public static final Property property = m_model.createProperty( "http://wymiwyg.org/ontologies/rdfserializer#property" );
    
    /** <p>Defines how resources can be serialised to a character stream~en</p> */
    public static final Resource Style = m_model.createResource( "http://wymiwyg.org/ontologies/rdfserializer#Style" );
    
    /** <p>Defines a URL-Prefix mapping~en</p> */
    public static final Resource NSPrefix = m_model.createResource( "http://wymiwyg.org/ontologies/rdfserializer#NSPrefix" );
    
    /** <p>Describes the rendering of a specific type~en</p> */
    public static final Resource TypeDescriptor = m_model.createResource( "http://wymiwyg.org/ontologies/rdfserializer#TypeDescriptor" );
    
    /** <p>Indicates that properties of resources of this type are not to be included 
     *  in serialisation~en</p>
     */
    public static final Resource BlockTypeDescriptor = m_model.createResource( "http://wymiwyg.org/ontologies/rdfserializer#BlockTypeDescriptor" );
    
    /** <p>Describes the rendering of a specific property~en</p> */
    public static final Resource PropertyDescriptor = m_model.createResource( "http://wymiwyg.org/ontologies/rdfserializer#PropertyDescriptor" );
    
}
