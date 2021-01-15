package LAB02;

import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestNumber {
    public static void main(String[] args) throws ScriptException {
        String s = " 8 - 2 >>> 19 7 6 * 5 2 / + * + ";
        System.out.println(new TestNumber().calculate(s));
    }

    String calculate(String s) throws ScriptException {
        String[] s1 = s.split("\\s+");
        boolean flag = true;
        for (int i = 0; i < s1.length; i++) {
            Pattern pattern = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
            if (pattern.matcher(s1[i]).matches())
                flag = false;
        }

        if (flag) {
            StackOfNumber<Number> stackOfNumber = new StackOfNumber();
            for (String i : s1)
                try {
                    int newNum = Integer.parseInt(i);
                    stackOfNumber.push(newNum);
                } catch (NumberFormatException e) {
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("JavaScript");
                    Number a1 = stackOfNumber.pop();
                    Number a2 = stackOfNumber.pop();
                    Object result;
                    result = engine.eval(a2 + i + a1 + "");
                    stackOfNumber.push((Number) result);
                }
            return stackOfNumber.pop() + "";
        } else {
            StackOfNumber<Number> stackOfNumber = new StackOfNumber();
            for (String i : s1) {
                try {
                    double newNum = Double.parseDouble(i);
                    stackOfNumber.push(newNum);
                } catch (NumberFormatException e) {
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("JavaScript");

                    Number a1 = stackOfNumber.pop();
                    Number a2 = stackOfNumber.pop();
                    Object result = engine.eval(a2 + i + a1);
                    stackOfNumber.push((Number) result);
                }
            }
            return stackOfNumber.pop() + "";
        }
    }

}
