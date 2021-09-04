package tech.jhipster.forge.init.seconday;

import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Repository;
import tech.jhipster.forge.common.domain.Project;
import tech.jhipster.forge.common.secondary.ProjectRepository;
import tech.jhipster.forge.init.domain.Inits;

@Repository
public class InitRepository extends ProjectRepository implements Inits {

  public static final String SOURCE = "init";

  @Override
  public void init(Project project) {
    addReadme(project);
    addGitConfiguration(project);
    addEditorConfiguration(project);
    addPrettier(project);
    addPackageJson(project);
  }

  @Override
  public void addPackageJson(Project project) {
    project.addDefaultConfig("nodeVersion");
    project.addDefaultConfig("projectName");
    project.addDefaultConfig("baseName");

    String baseName = project.getConfig("baseName").orElse("");
    project.addConfig("dasherizedBaseName", getDasherized(baseName));

    template(project, SOURCE, "package.json.mustache", ".", "package.json");
  }

  @Override
  public void addReadme(Project project) {
    project.addDefaultConfig("projectName");

    template(project, SOURCE, "README.md.mustache");
  }

  @Override
  public void addGitConfiguration(Project project) {
    add(project, SOURCE, "gitignore", ".", ".gitignore");
    add(project, SOURCE, "gitattributes", ".", ".gitattributes");
  }

  @Override
  public void addEditorConfiguration(Project project) {
    project.addDefaultConfig("prettierDefaultIndent");
    project.addDefaultConfig("prettierJavaIndent");

    template(project, SOURCE, ".editorconfig.mustache", ".", ".editorconfig");
    add(project, SOURCE, ".eslintignore", ".", ".eslintignore");
  }

  @Override
  public void addPrettier(Project project) {
    add(project, SOURCE, ".lintstagedrc.js", ".", ".lintstagedrc.js");
    add(project, SOURCE, ".prettierignore", ".", ".prettierignore");
    add(project, SOURCE, ".huskyrc", ".", ".huskyrc");

    template(project, SOURCE, ".prettierrc.mustache", ".", ".prettierrc");
  }

  private String getDasherized(String value) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, value);
  }
}