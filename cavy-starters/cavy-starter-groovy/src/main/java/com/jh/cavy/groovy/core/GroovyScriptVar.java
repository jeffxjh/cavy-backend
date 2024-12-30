package com.jh.cavy.groovy.core;

import com.jh.cavy.groovy.mapper.ScriptMapper;
import com.jh.cavy.groovy.domain.Script;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.script.CompiledScript;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class GroovyScriptVar {

    // 缓存编译后的脚本
    private static final Map<String, CompiledScript> SCRIPT_MAP = new ConcurrentHashMap<>();

    @Resource
    public ScriptMapper scriptMapper;


    @PostConstruct
    public void init() {
        refresh();
    }

    @SneakyThrows
    public void refresh() {
        synchronized (GroovyScriptVar.class) {
            // 从数据库中加载脚本
            List<Script> list = scriptMapper.selectList(null);
            SCRIPT_MAP.clear();
            if(CollectionUtils.isEmpty(list)) return;
            for (Script script : list) {
                SCRIPT_MAP.put(script.getUniqueKey(), GroovyEngineUtils.compile(script.getScript()));
            }
            log.info("//// Groovy脚本初始化，加载数量：{}",list.size());
        }
    }


    public CompiledScript get(String uniqueKey){
        return SCRIPT_MAP.get(uniqueKey);
    }

}