package com.apicatalog.jsonld.context;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.apicatalog.jsonld.grammar.DirectionType;
import com.apicatalog.jsonld.grammar.Version;

/**
 * A context that is used to resolve terms while the processing algorithm is running.
 * 
 */
public class ActiveContext {

	// the active term definitions which specify how keys and values have to be interpreted
	Map<String, TermDefinition> terms;
	
	// the current base IRI 
	URI baseUri;
	
	// the original base URL
	URI baseUrl;
	
	Object inverseContext;	//TODO
	
	// an optional previous context, used when a non-propagated context is defined.
	ActiveContext previousContext;
	
	// an optional vocabulary mapping
	URI vocabularyMapping;
	
	// an optional default language 
	String defaultLanguage;
	
	// an optional default base direction ("ltr" or "rtl")
	DirectionType defaultBaseDirection;
	
	Version processingMode;
	
	public ActiveContext(final URI baseUri, final URI baseUrl, Version processingMode) {
		this.baseUri = baseUri;
		this.baseUrl = baseUrl;
		this.terms = new HashMap<>();
		this.processingMode = processingMode;
	}

	public ActiveContext(final URI baseUri, final URI baseUrl, final ActiveContext previousContext, Version processingMode) {
		this.baseUri = baseUri;
		this.baseUrl = baseUrl;
		this.previousContext = previousContext;
		this.terms = new HashMap<>();
		this.processingMode = processingMode;
	}

	// copy constructor
	public ActiveContext(final ActiveContext origin) {
		this.terms = origin.terms;
		this.baseUri = origin.baseUri;
		this.baseUrl = origin.baseUrl;
		this.inverseContext = origin.inverseContext;
		this.previousContext = origin.previousContext;
		this.vocabularyMapping = origin.vocabularyMapping;
		this.defaultLanguage = origin.defaultLanguage;
		this.defaultBaseDirection = origin.defaultBaseDirection;
		this.processingMode = origin.processingMode;
	}

	public boolean containsTerm(String term) {
		return terms.containsKey(term);
	}

	public boolean containsProtectedTerm() {
		return terms.values().stream().anyMatch(d -> d.protectedFlag);
	}

	public TermDefinition removeTerm(String term) {

		if (terms.containsKey(term)) {
			TermDefinition def = terms.get(term);
			terms.remove(term);
			return def;
		}
		
		return null;
	}

	public void setTerm(String term, TermDefinition definition) {
		terms.put(term, definition);
	}

	public TermDefinition getTerm(String value) {
		return terms.get(value);
	}	
	
	public DirectionType getDefaultBaseDirection() {
		return defaultBaseDirection;
	}
	
	public String getDefaultLanguage() {
		return defaultLanguage;
	}
	
	public URI getBaseUri() {
		return baseUri;
	}
	
	public URI getVocabularyMapping() {
		return vocabularyMapping;
	}

	public boolean inMode(Version version) {
		return processingMode != null && processingMode.equals(version);
	}

	public boolean hasPreviousContext() {
		return previousContext != null;
	}
	
	public ActiveContext getPreviousContext() {
		return previousContext;
	}
}
