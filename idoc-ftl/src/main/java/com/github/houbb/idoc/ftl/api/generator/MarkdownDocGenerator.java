package com.github.houbb.idoc.ftl.api.generator;

import com.github.houbb.idoc.api.core.genenrator.IDocGenerator;
import com.github.houbb.idoc.api.model.metadata.DocClass;
import com.github.houbb.idoc.common.config.IDocConfig;
import com.github.houbb.idoc.common.exception.IDocRuntimeException;
import com.github.houbb.idoc.common.handler.impl.simplify.SimplifyClassHandler;
import com.github.houbb.idoc.common.model.SimplifyDocClass;
import com.github.houbb.idoc.common.util.CollectionUtil;
import com.github.houbb.idoc.common.util.SystemUtil;
import com.github.houbb.idoc.ftl.constant.MarkdownConstant;
import com.github.houbb.idoc.ftl.util.FreemarkerUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.paradise.common.constant.MavenConstant;
import com.github.houbb.paradise.common.util.DateUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * markdown 文档生成器
 * @author binbin.hou
 * @since 0.0.1
 */
public class MarkdownDocGenerator implements IDocGenerator {

    /**
     * 日志
     */
    private final Log log = LogFactory.getLog(MarkdownDocGenerator.class);

    /**
     * 模板
     */
    private Template template;

    /**
     * 是否覆盖编写
     */
    private boolean isOverwriteWhenExists;

    /**
     * 所有的内容生成一个文件
     */
    private boolean isAllInOne;

    /**
     * 项目信息
     */
    private MavenProject project;

    /**
     * 编码
     */
    private String encoding;

    /**
     * 必须要有空构造器
     */
    public MarkdownDocGenerator() {
    }

    public MarkdownDocGenerator(final MavenProject project, final IDocConfig docConfig) {
        this.project = project;
        this.encoding = docConfig.getEncoding();
        this.isOverwriteWhenExists = docConfig.isOverwriteWhenExists();
        this.isAllInOne = docConfig.isAllInOne();

        // 初始化FTL配置
        initFtlConfig();
    }

    /**
     * 生成的时候有两种模式：
     * 1. all-in-one 所有的信息在一起
     * 2. 每个类一个文件，然后生成一个统一的 index.md
     * @param docClasses 文档类原始信息
     */
    @Override
    public void generate(Collection<DocClass> docClasses) {
        String targetPath = buildTargetDirPath();

        //1. 生成目标文件夹
        Path path = Paths.get(targetPath);
        File file = path.toFile();
        if(!file.exists()) {
            log.info("开始创建目标文件夹: {}", targetPath);
            boolean makeDirs = file.mkdirs();
            if(!makeDirs) {
                log.error("目标文件夹创建失败，执行中断：{}", targetPath);
                throw new IDocRuntimeException("目标文件夹创建失败，执行中断！");
            }
        }

        //2.0 生成对应的文件
        List<SimplifyDocClass> simplifyDocClasses = CollectionUtil.buildList(docClasses, new SimplifyClassHandler());

        Map<String, Object> root = new HashMap<>();
        root.put("author", SystemUtil.getCurrentUserName());
        root.put("version", project.getVersion());
        root.put("today", DateUtil.getSimpleDateStr());
        root.put("classes", simplifyDocClasses);

        // 这里等后期才变成可以自由配置的
        String targetFile = targetPath + MarkdownConstant.Generate.IDOC_MARKDOWN_ALL_IN_ONE;
        log.info("Markdown 生成文件路径: {}", targetFile);
        FreemarkerUtil.createFile(template, targetFile, root, isOverwriteWhenExists);
    }

    /**
     * 构建目标文件夹
     * @return 目录
     */
    private String buildTargetDirPath() {
        return project.getBasedir().getPath() + File.separator + MarkdownConstant.Generate.IDOC_MARKDOWN_BASE_PACAKGE
                +File.separator;
    }

    /**
     * 初始化FTL配置
     */
    private void initFtlConfig() {
        try {
            Configuration configuration = FreemarkerUtil.getConfiguration(encoding, true);

            //1. 判断根目录下是否有此文件 如果有则按照这个为准
            final String userDefineFtl = project.getBasedir().getPath() + File.separator
                    + MavenConstant.SRC_MAIN_RESOURCES_PATH +
                    MarkdownConstant.Template.IDOC_MARKDOWN_ALL_IN_ONE_FTL;
            Path path = Paths.get(userDefineFtl);
            if (Files.exists(path)) {
                // 用户根目录自定义
                configuration.setDirectoryForTemplateLoading(new File(project.getBasedir().getPath()
                        + File.separator + MavenConstant.SRC_MAIN_RESOURCES_PATH));
            } else {
                configuration.setClassForTemplateLoading(FreemarkerUtil.class,
                        MarkdownConstant.Template.IDOC_MARKDOWN_BASE_PACKAGE);
            }

            template = configuration.getTemplate(MarkdownConstant.Template.IDOC_MARKDOWN_ALL_IN_ONE_FTL);
        } catch (IOException e) {
            log.error("init config meet ex:{}", e);
            throw new IDocRuntimeException(e);
        }
    }
}