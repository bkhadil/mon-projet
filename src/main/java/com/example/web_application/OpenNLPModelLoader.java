package com.example.web_application;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;

import java.io.IOException;
import java.io.InputStream;

public class OpenNLPModelLoader {

    private TokenizerME tokenizer;
    private POSTaggerME posTagger;
    private ChunkerME chunker;
    private NameFinderME nameFinder;

    public OpenNLPModelLoader() throws IOException {
        // Correct paths without "src/main/resources/"
        String tokenizerModelPath = "models/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin";
        String posModelPath = "models/en-pos-maxent.bin";
        String chunkerModelPath = "models/en-chunker.bin";
        String nameFinderModelPath = "models/en-ner-person.bin";

        // Load the tokenizer model
        TokenizerModel tokenizerModel = loadModel(tokenizerModelPath, TokenizerModel.class);
        tokenizer = new TokenizerME(tokenizerModel);

        // Load the POS model
        POSModel posModel = loadModel(posModelPath, POSModel.class);
        posTagger = new POSTaggerME(posModel);

        // Load the chunker model
        ChunkerModel chunkerModel = loadModel(chunkerModelPath, ChunkerModel.class);
        chunker = new ChunkerME(chunkerModel);

        // Load the name finder model
        TokenNameFinderModel tokenNameFinderModel = loadModel(nameFinderModelPath, TokenNameFinderModel.class);
        nameFinder = new NameFinderME(tokenNameFinderModel);
    }

    private <T> T loadModel(String modelPath, Class<T> modelClass) throws IOException {
        try (InputStream modelIn = getClass().getClassLoader().getResourceAsStream(modelPath)) {
            if (modelIn == null) {
                throw new IOException("Model file not found: " + modelPath);
            }
            return modelClass.getConstructor(InputStream.class).newInstance(modelIn);
        } catch (ReflectiveOperationException e) {
            throw new IOException("Failed to load model from " + modelPath, e);
        }
    }

    public String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    public String[] tagPOS(String[] tokens) {
        return posTagger.tag(tokens);
    }

    public String[] chunk(String[] tokens) {
        return chunker.chunk(tokens, posTagger.tag(tokens));
    }

    public NameFinderME getNameFinder() {
        return nameFinder;
    }
}
