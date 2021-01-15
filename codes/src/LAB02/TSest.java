package LAB02;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TSest {
    public static void main(String[] args){
        String s = "{ ( 8 >>> 2 ) + 19 } * ( 7 * 6 + 5 / 2 )";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            Object result = engine.eval(s);
            System.out.println(result);
        } catch (ScriptException e) {
            System.out.println("无效的表达式");
        }

        System.out.println();
    }
}
