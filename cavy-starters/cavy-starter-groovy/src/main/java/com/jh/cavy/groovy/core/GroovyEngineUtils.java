package com.jh.cavy.groovy.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.*;
import java.util.Map;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroovyEngineUtils {

    private static final GroovyScriptEngineImpl GROOVY_ENGINE = (GroovyScriptEngineImpl) new ScriptEngineManager().getEngineByName("groovy");

    /**
     * 编译脚本
     *
     * @param script
     * @return
     * @throws ScriptException
     */
    public static CompiledScript compile(String script) throws ScriptException {
        return GROOVY_ENGINE.compile(script);
    }

    /**
     * 执行脚本
     *
     * @param compiledScript
     * @param args           脚本参数
     * @return
     */
    public static Object eval(CompiledScript compiledScript, Map<String, Object> args) {
        try {
            return compiledScript.eval(getScriptContext(args));
        } catch (ScriptException e) {
            log.error("//// 执行 GroovyEngineUtils.eval 异常 ", e);
        }
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @return
     */
    public static Object eval(String script) {
        try {
            return GROOVY_ENGINE.eval(script);
        } catch (ScriptException e) {
            log.error("//// 执行 GroovyEngineUtils.eval 异常 ", e);
        }
        return null;
    }

    /**
     * 执行脚本
     *
     * @param script 脚本
     * @param args   脚本参数
     * @return
     */
    public static Object eval(String script, Map<String, Object> args) {
        try {
            return GROOVY_ENGINE.eval(script, getScriptContext(args));
        } catch (ScriptException e) {
            log.error("//// 执行 GroovyEngineUtils.eval 异常 ", e);
        }
        return null;
    }

    private static ScriptContext getScriptContext(Map<String, Object> args) {
        ScriptContext scriptContext = new SimpleScriptContext();
        if (Objects.nonNull(args)) {
            args.forEach((k, v) -> scriptContext.setAttribute(k, v, ScriptContext.ENGINE_SCOPE));
        }
        return scriptContext;
    }
}
