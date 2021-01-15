package LAB02;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class TestOperator {
    public static void main(String[] args) {

        String s = "{ ( - 8 >>> 2 ) + 19 } * ( 7 * 6 + 5 / 2 )";

        System.out.println(new TestOperator().intoPostfix(s));
    }

    String intoPostfix(String s) {
        s = s.replaceAll("\\{", "\\(");
        s = s.replaceAll("\\}", "\\)");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            Object result = engine.eval(s);
         //   System.out.println(result);
        } catch (ScriptException e) {
            System.out.println("无效的表达式");
            return ""; }
        StackOfOperator stackOfOperator = new StackOfOperator();
        String[] s1 = s.split("\\s+");
        String post = new String();
        for (String aS1 : s1) {
            try {
                Float.parseFloat(aS1);
                post += aS1;
            } catch (NumberFormatException e) {
                post += (stackOfOperator.doPush(aS1)); }
            post += (" "); }
        ArrayList<String> array = stackOfOperator.clear();
        for (String anArray : array)
            post += (anArray) + (" ");
        post = post.replaceAll("\\s+", " ");
        return post;
    }

}
