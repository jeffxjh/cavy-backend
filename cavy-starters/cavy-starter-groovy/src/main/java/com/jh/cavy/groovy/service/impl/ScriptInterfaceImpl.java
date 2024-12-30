package com.jh.cavy.groovy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jh.cavy.groovy.core.GroovyEngineUtils;
import com.jh.cavy.groovy.core.GroovyScriptVar;
import com.jh.cavy.groovy.domain.Script;
import com.jh.cavy.groovy.mapper.ScriptMapper;
import com.jh.cavy.groovy.service.ScriptInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.CompiledScript;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ScriptInterfaceImpl extends ServiceImpl<ScriptMapper, Script> implements ScriptInterface {
    @Autowired
    private GroovyScriptVar groovyScriptVar;


    @Override
    public void exec() {
        Map<String, Object> args = new HashMap<>();
        args.put("status", "COMPLETED");
        CompiledScript compiledScript = groovyScriptVar.get("test");
        Object result = GroovyEngineUtils.eval(compiledScript, args);
        log.info("/// 脚本执行结果{}", result);
    }

    @Override
    public void refresh() {
        groovyScriptVar.refresh();
    }
}
