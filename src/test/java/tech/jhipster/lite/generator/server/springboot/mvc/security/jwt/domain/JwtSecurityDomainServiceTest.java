package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.project.infrastructure.secondary.GitUtils;
import tech.jhipster.lite.generator.server.springboot.properties.domain.SpringBootPropertiesService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JwtSecurityDomainServiceTest {

  @Mock
  SpringBootPropertiesService springBootPropertiesService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @InjectMocks
  JwtSecurityDomainService jwtSecurityDomainService;

  @Test
  void shouldInitBasicAuth() throws GitAPIException {
    Project project = tmpProjectWithPomXml();
    GitUtils.init(project.getFolder());

    jwtSecurityDomainService.initBasicAuth(project);

    verify(buildToolService).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService, times(6)).addDependency(any(Project.class), any(Dependency.class));

    // 14 classes + 6 tests
    verify(projectRepository, times(20)).template(any(Project.class), anyString(), anyString(), anyString());

    // 1 patch
    verify(projectRepository, times(1)).add(any(Project.class), anyString(), anyString(), anyString());

    verify(springBootPropertiesService, times(11)).addProperties(any(Project.class), anyString(), any());
    verify(springBootPropertiesService, times(11)).addPropertiesTest(any(Project.class), anyString(), any());

    // 2 git patchs
    verify(projectRepository, times(1)).gitApplyPatch(any(Project.class), anyString());
  }
}
