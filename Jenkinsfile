
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
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