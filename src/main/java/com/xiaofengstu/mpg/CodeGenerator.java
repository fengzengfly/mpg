package com.xiaofengstu.mpg;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName com.xiaofengstu.mpg.CodeGenerator
 * @Author fengzeng
 * @Date 2022/8/9 0009 15:37
 */
public class CodeGenerator {
  public static void generator(String url, String username,String password,String rootDir,String moduleName, String... tableNames) {
    List<String> tables = new ArrayList<>(Arrays.asList(tableNames));
    FastAutoGenerator.create(url,
            username, password)
        .globalConfig(builder -> {
          builder.author("fengzeng")               //作者
              .outputDir(System.getProperty("user.dir") + "\\src\\main\\java")    //输出路径(写到java目录)
              //.enableSwagger()           //开启swagger
              .commentDate("yyyy-MM-dd")
              .fileOverride();            //开启覆盖之前生成的文件
        })
        .packageConfig(builder -> {
          builder.parent(rootDir)
              .moduleName(moduleName)
              .entity("entity")
              .service("service")
              .serviceImpl("service.impl")
              .controller("controller")
              .mapper("mapper")
              .xml("mapper")
              .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "\\src\\main\\resources\\mapper"));
        })
        .strategyConfig(builder -> {
          builder.addInclude(tables)
              //.addTablePrefix("p_")
              .serviceBuilder()
              .formatServiceFileName("%sService")
              .formatServiceImplFileName("%sServiceImpl")
              .entityBuilder()
              .enableLombok()
              .logicDeleteColumnName("deleted")
              .enableTableFieldAnnotation()
              .controllerBuilder()
              .formatFileName("%sController")
              .enableRestStyle()
              .mapperBuilder()
              .superClass(BaseMapper.class)
              .formatMapperFileName("%sMapper")
              .enableMapperAnnotation()
              .formatXmlFileName("%sMapper");
        })
        // 使用 Freemarker 模板引擎，默认的是 Velocity 模板引擎
        .templateEngine(new FreemarkerTemplateEngine())
        .execute();
  }
}
