package com.apicatalog.rdf.impl;

import java.io.Reader;
import java.io.Writer;

import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfLiteral;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTriple;
import com.apicatalog.rdf.RdfValue;
import com.apicatalog.rdf.io.RdfReader;
import com.apicatalog.rdf.io.RdfWriter;
import com.apicatalog.rdf.io.error.UnsupportedContentException;
import com.apicatalog.rdf.io.nquad.NQuadsReader;
import com.apicatalog.rdf.io.nquad.NQuadsWriter;
import com.apicatalog.rdf.spi.RdfProvider;

public final class DefaultRdfProvider extends RdfProvider {

    public static final RdfProvider INSTANCE = new DefaultRdfProvider(); 
    
    @Override
    public RdfDataset createDataset() {
        return new RdfDatasetImpl();
    }

    @Override
    public RdfReader createReader(final MediaType contentType, final Reader reader) throws UnsupportedContentException {
        
        if (reader == null || contentType == null) {
            throw new IllegalArgumentException();
        }
        
        if (MediaType.N_QUADS.match(contentType)) {
            return new NQuadsReader(reader);            
        }
        throw new UnsupportedContentException(contentType.toString());
    }

    @Override
    public RdfWriter createWriter(final MediaType contentType, final Writer writer) throws UnsupportedContentException {

        if (writer == null || contentType == null) {
            throw new IllegalArgumentException();
        }

        if (MediaType.N_QUADS.match(contentType)) {
            return new NQuadsWriter(writer);            
        }
        
        throw new UnsupportedContentException(contentType.toString());
    }

    @Override
    public RdfGraph createGraph() {
        return new RdfGraphImpl();
    }

    @Override
    public RdfTriple createTriple(RdfResource subject, RdfResource predicate, RdfValue object) {
        
        if (subject == null || predicate == null || object == null) {
            throw new IllegalArgumentException();
        }

        return new RdfTripleImpl(subject, predicate, object);
    }

    @Override
    public RdfNQuad createNQuad(RdfResource subject, RdfResource predicate, RdfValue object, RdfResource graphName) {
        
        if (subject == null || predicate == null || object == null) {
            throw new IllegalArgumentException();
        }
        
        return new RdfNQuadImpl(subject, predicate, object, graphName);
    }

    @Override
    public RdfResource createBlankNode(String value) {
        if (value == null || isBlank(value)) {
            throw new IllegalArgumentException();
        }
        
        return new RdfResourceImpl(value, true);
    }

    @Override
    public RdfResource createIRI(String value) {
        if (value == null || isBlank(value)) {
            throw new IllegalArgumentException();
        }
        
        return new RdfResourceImpl(value, false);            
    }

    @Override
    public RdfLiteral createLangString(String lexicalForm, String langTag) {
        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }
        
        return new RdfLiteralImpl(lexicalForm, langTag, null);
    }

    @Override
    public RdfLiteral createTypedString(String lexicalForm, String datatype) {
        if (lexicalForm == null) {
            throw new IllegalArgumentException();
        }
        
        return new RdfLiteralImpl(lexicalForm, null, datatype);
    }
    
    private static final boolean isBlank(String value) {
        return value.isEmpty() 
                || value.isBlank() && value.chars().noneMatch(ch -> ch == '\n' || ch == '\r' || ch == '\t' || ch == '\f');
    }
}