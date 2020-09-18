public class Main {

    public static void main(String[] args) {
        MathExpression expr = new MathExpression("1 + 1");
        //Compiles to java code, the compilation is what takes long.
        expr.prepare();
        long start = System.nanoTime();
        //Executes compiled java code
        System.out.println("1 + 1 = " + expr.execute());
        System.out.println("Took: " + (System.nanoTime() - start) + " nanoseconds");
        //On my system the execution took ~0ms(12495200 nanoseconds)
    }

}
