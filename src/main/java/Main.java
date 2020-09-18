import io.github.retrooper.mathevaluator.MathExpression;

public class Main {

    public static void main(String[] args) {
        MathExpression expr = new MathExpression("(3 * 4) - (4 - 1) * 5 * 14591");
        //Compiles to java code, the compilation is what takes long.
        expr.prepare();
        long start = System.nanoTime();
        //Executes compiled java code
        System.out.println("Result: = " + expr.execute());
        System.out.println("Took: " + (System.nanoTime() - start) + " nanoseconds");
        //On my system the execution took ~0ms(12495200 nanoseconds).

        //Yes preparation might be a tiny bit slow(~30ms), but execution is very very fast.
    }

}
