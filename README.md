# Scope: Getting Started

An starter Java project instrumented with [Scope](https://scope.undefinedlabs.com) through [GitHub Actions](https://github.com/features/actions).

This starter project is based on:
- [Java](https://www.java.com/en/download/)
- [Gradle](https://gradle.org/)
- [Spring Boot 1.5](https://spring.io/projects/spring-boot)

## Install Scope on Gradle projects with GitHub Actions

The project needs to add a new `testAgent` configuration, where it will be loaded the `scope-agent` dependency and injected into tests `jvmArgs` as `javaagent`.

```groovy
configurations {
    testAgent
}

dependencies {
    testAgent("com.undefinedlabs.scope:scope-agent:latest.release")
}

test{
    jvmArgs = ["-javaagent:${configurations.testAgent.singleFile}"]
}
```

Finally, the `gradle` action has been configured in the GitHub Workflow `scope.yml` file:

```yaml
name: Scope Gradle JDK v1.8
on: [push]

jobs:
  scope:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v1
      - name: Set up JDK 1.8
        uses: actions/setup-java@v1
        with:
          java-version: 1.8
      - name: Scope for Gradle Action
        uses: undefinedlabs/scope-for-gradle-action@v1
        with:
          dsn: ${{secrets.SCOPE_DSN}}
```

For further information about how to install Scope, go to [Scope Java Agent Installation](https://docs.scope.dev/docs/java-installation)

## Running Scope on GitHub Actions

1. Click on `Use this template` button and create the repository in your namespace.
2. Access to [app.scope.dev](https://app.scope.dev). 
3. Add/Modify your namespace to include your new repository.
4. Get the API Key for your new repository.
5. Go to your repository on [GitHub](https://github.com)
6. Go to `Settings` -> `Secrets`.
7. Add your API Key secret.
    - Name: `SCOPE_APIKEY`
    - Value: `<<your APIKEY>>`
8. Click on `Actions` button and access to the workflow.
9. Click on `Re-run checks`.

Once GitHub Workflow has finished, you can check the test executions report on [app.scope.dev](https://app.scope.dev)

