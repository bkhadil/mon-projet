																																											package com.example.web_application;




import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String filePath = "JavaFile.java";
            List<JavaCodeAnalyzer.MethodInfo> methods = JavaCodeAnalyzer.analyzeCode(filePath);

            // Load OpenNLP models
            OpenNLPModelLoader nlpModelLoader = new OpenNLPModelLoader();

            // Generate RDF
            JavaCodeRDFGenerator rdfGenerator = new JavaCodeRDFGenerator();
            for (JavaCodeAnalyzer.MethodInfo method : methods) {
                rdfGenerator.generateRDF(method);
            }
            
            // Use the actual path where you want to save the RDF file
            rdfGenerator.writeRDF("src/main/resources/output.rdf");

            // Generate Documentation
            DocumentationGenerator docGenerator = new DocumentationGenerator(rdfGenerator.getModel(), nlpModelLoader);
            String documentation = docGenerator.generateDocumentation(methods);
            System.out.println(documentation);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

