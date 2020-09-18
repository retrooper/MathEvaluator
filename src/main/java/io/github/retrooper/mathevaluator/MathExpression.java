package io.github.retrooper.mathevaluator;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MathExpression {
    private final String expression;
    private Method executeMethod;
    private static File root;
    private static File sourceFile;
    private Class<?> cls;

    public MathExpression(String expression) {
        if (root == null) {
            root = new File("/java");
        }
        if (sourceFile == null) {
            sourceFile = new File(root, "test/Test.java");
            sourceFile.getParentFile().mkdirs();
        }
        this.expression = expression;
    }

    public void prepare() {
        String source = "package test; " +
                "public class Test { " +
                "public static double getValue() { " +
                "return " + expression + ";" +
                " } " +
                "}";
        // Save source in .java file.
        try {
            Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        URLClassLoader classLoader = null;
        try {
            classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            cls = Class.forName("test.Test", true, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert cls != null;
            executeMethod = cls.getMethod("getValue");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public double execute() {
        try {
            return (double) executeMethod.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public double prepareAndExecute() {
        prepare();
        return execute();
    }
}
