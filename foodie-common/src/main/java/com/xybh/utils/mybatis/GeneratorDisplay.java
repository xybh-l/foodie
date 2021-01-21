package com.xybh.utils.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Author: xybh
 * @Description:
 * @Date: Created in 9:06 2021/1/21
 * @Modified:
 */
public class GeneratorDisplay {
    public void generator() throws IOException, XMLParserException, SQLException, InterruptedException, InvalidConfigurationException {
        ArrayList<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        File configFile = new File("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) {
        try{
            GeneratorDisplay generatorDisplay = new GeneratorDisplay();
            generatorDisplay.generator();
        } catch (IOException | SQLException | InterruptedException | XMLParserException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
