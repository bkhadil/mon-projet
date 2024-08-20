package com.example.web_application;



import org.apache.jena.rdf.model.Model;
import opennlp.tools.namefind.NameFinderME;

import java.util.List;

public class DocumentationGenerator {

    private final Model rdfModel;
    private final OpenNLPModelLoader nlpModelLoader;

    public DocumentationGenerator(Model rdfModel, OpenNLPModelLoader nlpModelLoader) {
        this.rdfModel = rdfModel;
        this.nlpModelLoader = nlpModelLoader;
    }

    public String generateDocumentation(List<JavaCodeAnalyzer.MethodInfo> methods) {
        StringBuilder documentation = new StringBuilder();

        for (JavaCodeAnalyzer.MethodInfo method : methods) {
            documentation.append("### Method: ").append(method.getName()).append("\n");
            documentation.append("**Signature:** ").append(method.getName()).append(method.getParameters()).append("\n");
            documentation.append("**Parameters:** ").append(method.getParameters()).append("\n");
            documentation.append("**Return Type:** ").append(method.getReturnType()).append("\n");

            if (!method.getComment().equals("No comment")) {
                documentation.append("**Comment:** ").append(method.getComment()).append("\n");

                // Tokenize and tag the comment
                String[] tokens = nlpModelLoader.tokenize(method.getComment());
                String[] posTags = nlpModelLoader.tagPOS(tokens);

                documentation.append("**Detailed Analysis:**\n");
                for (int i = 0; i < tokens.length; i++) {
                    documentation.append(tokens[i]).append(" (").append(posTags[i]).append(") ");
                }
                documentation.append("\n");
            } else {
                documentation.append("**Comment:** None\n");
            }

            documentation.append("\n");
        }

        return documentation.toString();
    }
}
