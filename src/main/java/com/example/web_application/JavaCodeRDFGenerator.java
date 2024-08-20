package com.example.web_application;



import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class JavaCodeRDFGenerator {

    private Model model;

    public JavaCodeRDFGenerator() {
        model = ModelFactory.createDefaultModel();
    }

    public void generateRDF(JavaCodeAnalyzer.MethodInfo methodInfo) {
        String baseURI = "http://example.org/java#";
        Resource methodResource = model.createResource(baseURI + methodInfo.getName())
                .addProperty(RDF.type, model.createResource(baseURI + "Method"))
                .addProperty(model.createProperty(baseURI + "hasParameters"), methodInfo.getParameters())
                .addProperty(model.createProperty(baseURI + "hasReturnType"), methodInfo.getReturnType())
                .addProperty(model.createProperty(baseURI + "hasSignature"), methodInfo.getSignature())
                .addProperty(model.createProperty(baseURI + "hasComment"), methodInfo.getComment());
    }

    public void writeRDF(String filePath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            model.write(out, "TURTLE");
        }
    }

    public Model getModel() {
        return model;
    }
}
