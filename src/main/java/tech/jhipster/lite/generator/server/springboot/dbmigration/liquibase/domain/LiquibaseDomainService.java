package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.logging.domain.Level;
import tech.jhipster.lite.generator.server.springboot.logging.domain.SpringBootLoggingService;
import tech.jhipster.lite.generator.server.springboot.properties.domain.SpringBootPropertiesService;

public class LiquibaseDomainService implements LiquibaseService {

  public static final String SOURCE = "server/springboot/dbmigration/liquibase";
  public static final String LIQUIBASE_PATH = "technical/infrastructure/secondary/liquibase";

  public final ProjectRepository projectRepository;
  public final BuildToolService buildToolService;
  public final SpringBootPropertiesService springBootPropertiesService;
  public final SpringBootLoggingService springBootLoggingService;

  public LiquibaseDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootPropertiesService springBootPropertiesService,
    SpringBootLoggingService springBootLoggingService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootPropertiesService = springBootPropertiesService;
    this.springBootLoggingService = springBootLoggingService;
  }

  @Override
  public void init(Project project) {
    addLiquibase(project);
    addChangelogMasterXml(project);
    addConfigurationJava(project);
    addLoggerInConfiguration(project);
  }

  @Override
  public void addLiquibase(Project project) {
    Dependency liquibaseDependency = Dependency.builder().groupId("org.liquibase").artifactId("liquibase-core").build();
    buildToolService.addDependency(project, liquibaseDependency);

    Dependency h2dependency = Dependency.builder().groupId("com.h2database").artifactId("h2").scope("test").build();
    buildToolService.addDependency(project, h2dependency);
  }

  @Override
  public void addChangelogMasterXml(Project project) {
    projectRepository.add(project, getPath(SOURCE, "resources"), "master.xml", getPath(MAIN_RESOURCES, "config/liquibase"));
  }

  @Override
  public void addConfigurationJava(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToLiquibase(project, packageNamePath, "src", "AsyncSpringLiquibase.java", MAIN_JAVA);
    templateToLiquibase(project, packageNamePath, "src", "LiquibaseConfiguration.java", MAIN_JAVA);
    templateToLiquibase(project, packageNamePath, "src", "SpringLiquibaseUtil.java", MAIN_JAVA);

    templateToLiquibase(project, packageNamePath, "test", "AsyncSpringLiquibaseTest.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "LiquibaseConfigurationIT.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "LogbackRecorder.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "SpringLiquibaseUtilTest.java", TEST_JAVA);
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    addLogger(project, "liquibase", Level.WARN);
    addLogger(project, "LiquibaseSchemaResolver", Level.INFO);
  }

  public void addLogger(Project project, String packageName, Level level) {
    springBootLoggingService.addLogger(project, packageName, level);
    springBootLoggingService.addLoggerTest(project, packageName, level);
  }

  private void templateToLiquibase(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(project, getPath(SOURCE, type), sourceFilename, getPath(destination, source, LIQUIBASE_PATH));
  }
}
