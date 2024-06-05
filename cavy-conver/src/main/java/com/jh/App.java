package com.jh;

import org.openjdk.nashorn.api.tree.CompilationUnitTree;
import org.openjdk.nashorn.internal.*;
import org.openjdk.nashorn.internal.ir.Block;
import org.openjdk.nashorn.internal.ir.FunctionNode;
import org.openjdk.nashorn.internal.ir.Statement;
import org.openjdk.nashorn.internal.parser.Parser;
import org.openjdk.nashorn.internal.runtime.Context;
import org.openjdk.nashorn.internal.runtime.ErrorManager;
import org.openjdk.nashorn.internal.runtime.Source;
import org.openjdk.nashorn.internal.runtime.options.Options;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Options options = new Options("nashorn");
        options.set("anon.functions", true);
        options.set("parse.only", true);
        options.set("scripting", true);
        ErrorManager errors = new ErrorManager();
        Context context = new Context(options, errors, Thread.currentThread().getContextClassLoader());
        Source source   = Source.sourceFor("test", "function someFunction() { return b + 1; }  ");
        Parser parser = new Parser(context.getEnv(), source, errors);
        FunctionNode functionNode = parser.parse();
        Block block = functionNode.getBody();
        List<Statement> statements = block.getStatements();
        for(Statement statement: statements){
            System.out.println(statement);
        }
    }
}
