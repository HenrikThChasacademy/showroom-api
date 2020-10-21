  
def version = "${env.BUILD_NUMBER}"

node('docker') {
    stage('Checkout') {
        scm checkout
    }

    //это стадия сборки
    stage 'Build'
    sh "./gradlew -Pversion=${version} build"

    stage('Sonar') {
    // withSonarQubeEnv('sonar') {
    //   sh "./gradlew -Pversion=${version} --info sonarqube"
    // }
    //
    // sleep 5
    //
    // def sonarStatus = waitForQualityGate().status
    // if (sonarStatus != 'OK') {
    //   if (sonarStatus == 'WARN') {
    //     currentBuild.result = 'UNSTABLE'
    //   } else {
    //     error "Quality gate is broken"
    //   }
    // }
    }

    stage 'Publish JAR'
    uploadJarToNexus(version)

    stage 'Publish Docker'
    sh "curl -o app.jar http://nexus:8081/repository/maven-releases/com/example/demo/${version}/demo-${version}.jar"
    docker.withServer('tcp://socatdockersock:2375') {
        docker.withRegistry('http://nexus:20000', 'god') {
            docker.build("demo").push("${version}")
        }
    }
}

node('docker') {
  def containerName = "demo${version}"

  stage('Deploy') {
    docker.withServer('tcp://socatdockersock:2375') {
      sh """docker run -d --name ${containerName} --net jenkinspipelineworkshop_default \
      -p 10080 nexus:20000/demo:${version}"""
    }
  }

  try {
    postDeployCheck(containerName)
  } finally {
    stage('Finalize') {
      docker.withServer('tcp://socatdockersock:2375') {
        sh "docker rm -f ${containerName}"
        sh "docker rmi -f nexus:20000/demo:${version}"
      }
    }
  }
}
