
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
                echo 'Build'
            }
        }
        stage('Test'){
            steps {
                sh './gradlew test'
                echo 'Test'
            }
        }
    }
}