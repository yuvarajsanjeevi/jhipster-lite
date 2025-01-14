package tech.jhipster.lite.generator.project.domain;

import java.io.File;

public class Constants {

  private Constants() {}

  public static final String MAIN_JAVA = String.join(File.separator, "src", "main", "java");
  public static final String MAIN_RESOURCES = String.join(File.separator, "src", "main", "resources");
  public static final String TEMPLATE_FOLDER = "generator";

  public static final String TEST_JAVA = String.join(File.separator, "src", "test", "java");
  public static final String TEST_RESOURCES = String.join(File.separator, "src", "test", "resources");
  public static final String TEST_TEMPLATE_RESOURCES = String.join(File.separator, TEST_RESOURCES, "generator");
}
