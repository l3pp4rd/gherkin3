package gherkin;

import gherkin.ast.Feature;
import gherkin.compiler.Compiler;
import gherkin.deps.com.google.gson.Gson;
import pickles.Pickle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GeneratePickles {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        Parser<Feature> parser = new Parser<>(new AstBuilder());
        TokenMatcher matcher = new TokenMatcher();
        Compiler compiler = new Compiler();
        List<Pickle> pickles = new ArrayList<>();

        for (String fileName : args) {
            InputStreamReader in = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            try {
                Feature feature = parser.parse(in, matcher);
                pickles.addAll(compiler.compile(feature));
            } catch (ParserException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        System.out.println(gson.toJson(pickles));
    }
}
