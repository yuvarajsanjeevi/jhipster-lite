package tech.jhipster.forge;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

@IntegrationTest
class JhforgeAppIT {

  @Test
  void shouldMain() {
    assertThatCode(() -> JhforgeApp.main(new String[] {})).doesNotThrowAnyException();
  }
}